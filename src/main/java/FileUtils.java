import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtils {
    private final URL questionPath = getClass().getClassLoader().getResource("pytania.txt");
    public List<String> getRules() throws FileNotFoundException {
        File file = new File("");
        Scanner scanner = new Scanner(file);
        List<String> rules = new ArrayList<>();
        while (scanner.hasNext()){
            rules.add(scanner.nextLine());
        }
        return rules;
    }

    /*public static List<String> getExtinguisherTypes() {

    }*/

    public List<String> getQuestions() throws FileNotFoundException {
        File file = new File(questionPath.toString().substring(6));
        Scanner scanner = new Scanner(file);
        List<String> questions = new ArrayList<>();
        while (scanner.hasNext()){
            questions.add(scanner.nextLine());
        }
        return questions;
    }

    /*public static List<String> getFireHazards(){

    }*/
}
