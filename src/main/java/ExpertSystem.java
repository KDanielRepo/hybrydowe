import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class ExpertSystem {
    private FuzzyLogic fuzzyLogic;

    public void makeDecision(List<Question> questions, List<Question> openQuestions) {

        Map<FireExtinguisherType, Integer> fireExtinguisherTypeIntegerMap = new HashMap<>();
        for (FireExtinguisherType value : FireExtinguisherType.values()) {
            fireExtinguisherTypeIntegerMap.put(value, 0);
        }
        for (Question question : questions) {
            FireExtinguisherType type = FireExtinguisherType.valueOf(question.getPickedAnswer().getFireClass());
            fireExtinguisherTypeIntegerMap.entrySet().stream().filter(f -> f.getKey().equals(type)).forEach(k -> {
                k.setValue(k.getValue() + Integer.parseInt(question.getPickedAnswer().getWeight()));
            });
        }

        System.out.println("Powinieneś użyć gaśnicy: "+fireExtinguisherTypeIntegerMap.entrySet().stream().max(Map.Entry.comparingByValue()));
        fuzzyLogic.answer(openQuestions, fireExtinguisherTypeIntegerMap.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey());
    }

    public ExpertSystem() {
        fuzzyLogic = new FuzzyLogic();
    }
}
