import lombok.Data;

import java.util.List;

@Data
public class Question {
    private String question;
    private List<Answer> answers;
    private Answer pickedAnswer;
    private Boolean isOpen;
}
//pytanie=odpowiedz.klasa-waga;odpowiedz2.klasa-waga