package ohtu;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.Arrays;
import org.apache.http.client.fluent.Request;

public class Main {

    public static void main(String[] args) throws IOException {
        String studentNr = "012345678";
        if (args.length > 0) {
            studentNr = args[0];
        }

        String url = "https://studies.cs.helsinki.fi/courses/students/"+studentNr+"/submissions";

        String bodyText = Request.Get(url).execute().returnContent().asString();

        //System.out.println("json-muotoinen data:");
        //System.out.println( bodyText );

        Gson mapper = new Gson();
        Submission[] subs = mapper.fromJson(bodyText, Submission[].class);
        
        System.out.println("Opiskelijan "+studentNr+" tulokset:\n");
        for (Submission submission : subs) {
            System.out.println(submission);
        }
        System.out.println();
        System.out.println("Yhteensä "+Arrays.stream(subs).mapToInt(s -> s.getExercises().length).sum()+" tehtävää "+Arrays.stream(subs).mapToInt(s -> s.getHours()).sum()+" tunnissa");
    }
}
