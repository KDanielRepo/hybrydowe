public class QuestionValidator {

    public boolean validate(String question){
        return question.contains("?") &&
                question.contains(":") &&
                question.substring(question.lastIndexOf(":")).length() > 1;
    }
}
