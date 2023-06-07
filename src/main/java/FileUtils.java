import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtils {
    private final String questionPath = "D:\\projekt poli\\PSI\\hybrydowe\\target\\classes\\pytania.txt";//getClass().getClassLoader().getResource("pytania.txt");
    private final String openQuestionPath = "D:\\projekt poli\\PSI\\hybrydowe\\target\\classes\\pytania_otwarte.txt";//getClass().getClassLoader().getResource("pytania_otwarte.txt");
    public List<String> getRules() throws FileNotFoundException {
        File file = new File("");
        Scanner scanner = new Scanner(file);
        List<String> rules = new ArrayList<>();
        while (scanner.hasNext()){
            rules.add(scanner.nextLine());
        }
        return rules;
    }

    public List<Question> getQuestions() throws FileNotFoundException {
        File file = new File(questionPath);
        Scanner scanner = new Scanner(file);
        List<Question> questions = new ArrayList<>();
        while (scanner.hasNext()){
            Question question = new Question();
            String line = scanner.nextLine();

            String[] questionAnswerSplit = line.split("=");
            question.setQuestion(questionAnswerSplit[0]);

            String[] answers = questionAnswerSplit[1].split(";");
            List<Answer> answerList = new ArrayList<>();
            for (String answer : answers) {
                Answer a = new Answer();
                String[] s = answer.split("\\.");
                a.setValue(s[0]);
                String[] s2 = s[1].split("-");
                a.setFireClass(s2[0]);
                a.setWeight(s2[1]);
                answerList.add(a);
            }
            question.setAnswers(answerList);
            question.setIsOpen(Boolean.FALSE);
            questions.add(question);
        }
        return questions;
    }

    public List<Question> getOpenQuestions() throws FileNotFoundException {
        File file = new File(openQuestionPath);//.toString().substring(6)
        Scanner scanner = new Scanner(file);
        List<Question> questions = new ArrayList<>();
        while (scanner.hasNext()){
            Question question = new Question();
            question.setQuestion(scanner.nextLine());
            question.setAnswers(new ArrayList<>());
            question.setIsOpen(Boolean.TRUE);
            questions.add(question);
        }
        return questions;
    }

    /*public static List<String> getFireHazards(){

    }*/
}
//pytanie=odpowiedz.klasa-waga;odpowiedz2.klasa-waga