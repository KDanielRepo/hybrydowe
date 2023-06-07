public class QuestionValidator {

    public boolean validate(Question question){
        return true;/*question.contains("?") &&
                question.contains(":") &&
                question.substring(question.lastIndexOf(":")).length() > 1;*/
    }
}
