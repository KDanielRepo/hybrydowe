import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class IOInterface {

    private QuestionValidator questionValidator;
    private Scanner scanner;
    private Decision decision;
    private FileUtils fileUtils;

    public void start() throws IOException {
        List<String> answers = new ArrayList<>();
        List<String> questions = fileUtils.getQuestions()
                .stream()
                .filter(q->questionValidator.validate(q))
                .collect(Collectors.toList());
        questions.forEach(question->{
            System.out.println(question);
            String answer = scanner.nextLine();
            answers.add(answer);
        });
        System.out.println(decision.makeDecision(answers));
    }

    public void init(){
        while(true) {
            System.out.println("Co chciałbyś zrobić?");
            System.out.println("1 : GASIC POZAR");
            System.out.println("2 : WYJSCIE \n");
            int response = scanner.nextInt();
            if (response == 1) {
                try {
                    start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (response == 2) {
                System.exit(0);
            }else{
                System.out.println("Wybrano niepoprawną opcję \n");
            }
        }
    }

    public IOInterface() {
        questionValidator = new QuestionValidator();
        decision = new Decision();
        fileUtils = new FileUtils();
        scanner = new Scanner(System.in);
    }
}
