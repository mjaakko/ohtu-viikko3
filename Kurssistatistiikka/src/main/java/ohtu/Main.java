package ohtu;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.apache.http.client.fluent.Request;

public class Main {

    public static void main(String[] args) throws IOException {
        String studentNr = "012345678";
        if (args.length > 0) {
            studentNr = args[0];
        }

        
        System.out.println("Opiskelija "+studentNr+"\n");
        
        Course[] courses = getCourses();
        Submission[] submissions = getUserSubmissions(studentNr);
        Arrays.stream(courses).forEach(course -> {
            try {
                System.out.println();
                System.out.println(course.getFullName()+" ("+course.getTerm()+" "+course.getYear()+"):");
                
                List<Submission> submissionsForCourse = Arrays.stream(submissions)
                        .filter(s -> s.getCourse().equalsIgnoreCase(course.getName()))
                        .sorted(Comparator.comparingInt(Submission::getWeek))
                        .collect(Collectors.toList());
                
                submissionsForCourse.forEach(submission -> {
                    System.out.println();
                    System.out.println("Viikko "+submission.getWeek());
                    System.out.println("Tehdyt tehtävät: "+String.join(", ", Arrays.stream(submission.getExercises())
                            .mapToObj(Integer::toString)
                            .collect(Collectors.toList()))
                            +" ("+submission.getExercises().length+"/"+course.getExercises()[submission.getWeek()]+"), "+submission.getHours()+" tuntia käytetty");
                });
                
                System.out.println();
                System.out.println("Yhteensä: "+submissionsForCourse.stream().mapToInt(s -> s.getExercises().length).sum()+"/"+Arrays.stream(course.getExercises()).sum()+", "+submissionsForCourse.stream().mapToInt(s -> s.getHours()).sum()+" tuntia");
                
                Map<String, WeekStats> weekStatsMap = getWeekStatsForCourse(course.getName());
                
                
                System.out.println("Kurssilla yhteensä "+weekStatsMap.values()
                                                            .stream()
                                                            .mapToInt(ws -> ws.getExercises().length)
                                                            .sum()
                                    +" palautusta, "+weekStatsMap.values().stream().mapToInt(ws -> ws.getExercise_total()).sum()+"kpl tehtyjä tehtäviä, "
                                    +weekStatsMap.values().stream().mapToDouble(ws -> ws.getHour_total()).sum()+" tuntia käytetty");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
    
    private static Submission[] getUserSubmissions(String studentNumber) throws IOException {
        String url = "https://studies.cs.helsinki.fi/courses/students/"+studentNumber+"/submissions";

        String bodyText = Request.Get(url).execute().returnContent().asString();

        Gson mapper = new Gson();
        return mapper.fromJson(bodyText, Submission[].class);
    }
    
    private static Course[] getCourses() throws IOException {
        String url = "https://studies.cs.helsinki.fi/courses/courseinfo";

        String bodyText = Request.Get(url).execute().returnContent().asString();

        Gson mapper = new Gson();
        return mapper.fromJson(bodyText, Course[].class);
    }
    
    private static Map<String, WeekStats> getWeekStatsForCourse(String course) throws IOException {
        String url = "https://studies.cs.helsinki.fi/courses/"+course+"/stats";

        String bodyText = Request.Get(url).execute().returnContent().asString();

        Type weekStatsMap = new TypeToken<HashMap<String, WeekStats>>(){}.getType();

        Gson mapper = new Gson();
        return mapper.fromJson(bodyText, weekStatsMap);
    }
}
