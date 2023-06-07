import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IOInterface {

    private QuestionValidator questionValidator;
    private Scanner scanner;
    private ExpertSystem expertSystem;
    private FileUtils fileUtils;

    public void start() throws IOException {
        List<Question> questions = fileUtils.getQuestions()
                .stream()
                .filter(q -> questionValidator.validate(q))
                .collect(Collectors.toList());
        List<Question> openQuestions = fileUtils.getOpenQuestions()
                .stream()
                .filter(q -> questionValidator.validate(q))
                .collect(Collectors.toList());
        questions.forEach(question -> {
            System.out.println(question.getQuestion());
            String answer = scanner.next();
            List<String> choices = question.getAnswers()
                    .stream()
                    .map(Answer::getValue)
                    .collect(Collectors.toList());
            while (!choices.contains(answer)) {
                System.out.println("Podano nieprawidłową odpowiedź, spróbuj jeszcze raz");
                answer = scanner.next();
            }
            for (Answer questionAnswer : question.getAnswers()) {
                if (questionAnswer.getValue().equals(answer)) {
                    question.setPickedAnswer(questionAnswer);
                }
            }
        });
        openQuestions.forEach(question -> {
            System.out.println(question.getQuestion());
            String answer = scanner.next();
            Answer a = new Answer();
            a.setValue(answer);
            question.setPickedAnswer(a);
        });
        expertSystem.makeDecision(questions, openQuestions);
    }

    public void init() {
        while (true) {
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
            } else {
                System.out.println("Wybrano niepoprawną opcję \n");
            }
        }
    }

    public IOInterface() {
        questionValidator = new QuestionValidator();
        expertSystem = new ExpertSystem();
        fileUtils = new FileUtils();
        scanner = new Scanner(System.in);
    }
}
