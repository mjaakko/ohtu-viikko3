package ohtu;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
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
}
