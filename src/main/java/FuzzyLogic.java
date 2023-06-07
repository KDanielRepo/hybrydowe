import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class FuzzyLogic {

    Map<FireTimeTerms, Map<Integer, Double>> fireTimeTermsMap;
    Map<BurningRoomsTerms, Map<Integer, Double>> burningRoomsTermsMap;
    Map<ExtinguishingMaterialTerms, Map<Integer, Double>> extinguishingMaterialTermsMap;
    Map<PeopleInjuredTerms, Map<Integer, Double>> peopleInjuredTermsMap;

    public void answer(List<Question> openQuestions, FireExtinguisherType fireExtinguisherType) {
        createTerms(openQuestions);
        createAnswer(openQuestions, fireExtinguisherType);


        /*czas trwania pozaru w minutach - > zamien na krótko średnio długo;
        ilość palących się pokojów -> mało średnio dużo;
        ilość materiału gaszącego -> mało średnio dużo;*/
    }

    public void createAnswer(List<Question> openQuestions, FireExtinguisherType fireExtinguisherType) {
        Integer fireTimeValue = Integer.parseInt(openQuestions.get(0).getPickedAnswer().getValue());
        Integer burningRoomValue = Integer.parseInt(openQuestions.get(1).getPickedAnswer().getValue());
        Integer extinguishingMaterialValue = Integer.parseInt(openQuestions.get(2).getPickedAnswer().getValue());
        Integer peopleInjuredValue = Integer.parseInt(openQuestions.get(3).getPickedAnswer().getValue());

        AtomicReference<FireTimeTerms> fireTimeTerm = new AtomicReference<>();
        AtomicReference<BurningRoomsTerms> burningRoomsTerms = new AtomicReference<>();
        AtomicReference<ExtinguishingMaterialTerms> extinguishingMaterialTerms = new AtomicReference<>();
        AtomicReference<PeopleInjuredTerms> peopleInjuredTerms = new AtomicReference<>();

        AtomicReference<Double> comparison = new AtomicReference<>(-1D);
        fireTimeTermsMap.forEach((key, value) -> {
            //System.out.println(key);
            if (comparison.get() < value.get(fireTimeValue)) {
                comparison.set(value.get(fireTimeValue));
                fireTimeTerm.set(key);
            }
            //System.out.println(value.get(fireTimeValue));
        });
        comparison.set(-1D);
        burningRoomsTermsMap.forEach((key, value) -> {
            ///System.out.println(key);
            if (comparison.get() < value.get(burningRoomValue)) {
                comparison.set(value.get(burningRoomValue));
                burningRoomsTerms.set(key);
            }
            //System.out.println(value.get(burningRoomValue));
        });
        comparison.set(-1D);
        extinguishingMaterialTermsMap.forEach((key, value) -> {
            //System.out.println(key);
            if (comparison.get() < value.get(extinguishingMaterialValue)) {
                comparison.set(value.get(extinguishingMaterialValue));
                extinguishingMaterialTerms.set(key);
            }
            //System.out.println(value.get(extinguishingMaterialValue));
        });
        comparison.set(-1D);
        peopleInjuredTermsMap.forEach((key, value) -> {
            //System.out.println(key);
            if (comparison.get() < value.get(peopleInjuredValue)) {
                comparison.set(value.get(peopleInjuredValue));
                peopleInjuredTerms.set(key);
            }
            //System.out.println(value.get(peopleInjuredValue));
        });
        FireTimeTerms fireTerm = fireTimeTerm.get();
        BurningRoomsTerms burningTerm = burningRoomsTerms.get();
        ExtinguishingMaterialTerms extinguishingTerm = extinguishingMaterialTerms.get();
        PeopleInjuredTerms peopleTerm = peopleInjuredTerms.get();
        String response = "Należy szybko ugasić pożar przy użyciu gaśnicy: "+ fireExtinguisherType;
        String escapeResponse = "Należy natychmiast opuścić budynek objęty pożarem oraz zawiadomić służby ratunkowe.";
        String emergencyResponse = " Należy zadzwonić po karetkę oraz w miarę umiejętności udzielić pierwszej pomocy";
        if(FireTimeTerms.SHORT == fireTerm){
            if(BurningRoomsTerms.MANY != burningTerm){
                if(ExtinguishingMaterialTerms.NONE != extinguishingTerm){
                    System.out.println(response);
                }else{
                    System.out.println(escapeResponse);
                }
            }else{
                System.out.println(escapeResponse);
            }
        }else if(FireTimeTerms.MEDIUM == fireTerm){
            if(BurningRoomsTerms.FEW == burningTerm){
                if(ExtinguishingMaterialTerms.PLENTY == extinguishingTerm){
                    System.out.println(response);
                }else{
                    System.out.println(escapeResponse);
                }
            }else{
                System.out.println(escapeResponse);
            }
        }else{
            System.out.println(escapeResponse);
        }
        if(PeopleInjuredTerms.NONE != peopleTerm){
            System.out.println(emergencyResponse);
        }

        /*jezeli czas ognia krótko
        jezeli pokoje malo;
        jezeli gasniczy inny niz NONE;
        gas;
        jezeli ofiary w ludziach inne niz NONE;
                else
        uciekaj;
        jezeli pokoje srednio;
        jezeli gasniczy duzo;
        gas;
        jezeli ofiary w ludziach inne niz NONE;
                else
        uciekaj;
        jezeli pokoje duzo;
        uciekaj;
        jezeli czas ognia srednio;
        jezeli pokoje malo;
        jezeli gasniczy duzo;
        gas;
        jezeli ofiary w ludziach inne niz NONE;
                else
        uciekaj;
        jezeli pokoje srednio;
        uciekaj
        jezeli pokoje duzo;
        uciekaj;
        jezeli czas ognia długo;
        jezeli pokoje malo;
        jezeli gasniczy duzo;
        gas;
        jezeli ofiary w ludziach inne niz NONE;
                else uciekaj*/
    }

    public void createTerms(List<Question> openQuestions) {
        for (int i = 0; i < openQuestions.size(); i++) {
            if (i == 0) {
                Integer value = Integer.parseInt(openQuestions.get(0).getPickedAnswer().getValue()) * 60;
                createTermsForFireTime();
                /*fireTimeTermsMap.forEach((key, value1) -> {
                    System.out.println(key);
                    System.out.println(value1.get(value));
                });*/
            } else if (i == 1) {
                Integer value = Integer.parseInt(openQuestions.get(1).getPickedAnswer().getValue());
                createTermsForBurningRooms();
            } else if (i == 2) {
                Integer value = Integer.parseInt(openQuestions.get(2).getPickedAnswer().getValue());
                createTermsForNumberOfPeopleInjured();
            } else {
                Integer value = Integer.parseInt(openQuestions.get(3).getPickedAnswer().getValue());
                createTermsForExtinguishingMaterial();
            }
            //openQuestions.get(i);
        }
    }

    public void createTermsForFireTime() {
        for (FireTimeTerms value : FireTimeTerms.values()) {
            Double termValue;
            Map<Integer, Double> timeValueMap1 = new HashMap<>();
            Map<Integer, Double> timeValueMap2 = new HashMap<>();
            Map<Integer, Double> timeValueMap3 = new HashMap<>();
            for (int i = 0; i < (10 * 60); i++) {
                if (i <= 2 * 60) {
                    switch (value) {
                        case SHORT:
                            termValue = 1D;
                            timeValueMap1.put(i, termValue);
                            break;
                        case MEDIUM:
                            if (i > 1 * 60) {
                                termValue = (double) i / (2 * 60);
                            } else {
                                termValue = 0D;
                            }
                            timeValueMap2.put(i, termValue);
                            break;
                        case LONG:
                            termValue = 0D;
                            timeValueMap3.put(i, termValue);
                            break;
                    }
                } else if ((i > 2 * 60 && i <= 4 * 60)) {
                    switch (value) {
                        case SHORT:
                            if (i < 3 * 60) {
                                termValue = (double) (180 - i) / 60;
                            } else {
                                termValue = 0D;
                            }
                            timeValueMap1.put(i, termValue);
                            break;
                        case MEDIUM:
                            if (i < 3 * 60) {
                                termValue = (double) i / (3 * 60);
                            } else {
                                termValue = 1D;
                            }
                            timeValueMap2.put(i, termValue);
                            break;
                        case LONG:
                            if (i < 3 * 60) {
                                termValue = 0D;
                            } else {
                                termValue = (double) i / (4 * 60);
                            }
                            timeValueMap3.put(i, termValue);
                            break;
                    }
                } else {
                    switch (value) {
                        case SHORT:
                            termValue = 0D;
                            timeValueMap1.put(i, termValue);
                            break;
                        case MEDIUM:
                            if (i > 4 * 60) {
                                termValue = (double) (240 - i) / 60;
                            } else {
                                termValue = 0D;
                            }
                            timeValueMap2.put(i, termValue);
                            break;
                        case LONG:
                            termValue = 1D;
                            timeValueMap3.put(i, termValue);
                            break;
                    }
                }
            }
            if (FireTimeTerms.SHORT == value) {
                fireTimeTermsMap.put(value, timeValueMap1);
            } else if (FireTimeTerms.MEDIUM == value) {
                fireTimeTermsMap.put(value, timeValueMap2);
            } else {
                fireTimeTermsMap.put(value, timeValueMap3);
            }
        }

    }

    public void createTermsForBurningRooms() {
        for (BurningRoomsTerms value : BurningRoomsTerms.values()) {
            Double termValue;
            Map<Integer, Double> timeValueMap1 = new HashMap<>();
            Map<Integer, Double> timeValueMap2 = new HashMap<>();
            Map<Integer, Double> timeValueMap3 = new HashMap<>();
            for (int i = 1; i < 7; i++) {
                if (i <= 2) {
                    switch (value) {
                        case FEW:
                            termValue = 1D;
                            timeValueMap1.put(i, termValue);
                            break;
                        case SOME:
                            termValue = 0D;
                            timeValueMap2.put(i, termValue);
                            break;
                        case MANY:
                            termValue = 0D;
                            timeValueMap3.put(i, termValue);
                            break;
                    }
                } else if (i > 2 && i <= 4) {
                    switch (value) {
                        case FEW:
                            termValue = 0D;
                            timeValueMap1.put(i, termValue);
                            break;
                        case SOME:
                            termValue = 1D;
                            timeValueMap2.put(i, termValue);
                            break;
                        case MANY:
                            termValue = 0D;
                            timeValueMap3.put(i, termValue);
                            break;
                    }
                } else {
                    switch (value) {
                        case FEW:
                            termValue = 0D;
                            timeValueMap1.put(i, termValue);
                            break;
                        case SOME:
                            termValue = 0D;
                            timeValueMap2.put(i, termValue);
                            break;
                        case MANY:
                            termValue = 1D;
                            timeValueMap3.put(i, termValue);
                            break;
                    }
                }
                if (BurningRoomsTerms.FEW == value) {
                    burningRoomsTermsMap.put(value, timeValueMap1);
                } else if (BurningRoomsTerms.SOME == value) {
                    burningRoomsTermsMap.put(value, timeValueMap2);
                } else {
                    burningRoomsTermsMap.put(value, timeValueMap3);
                }
            }
        }
    }

    public void createTermsForExtinguishingMaterial() {
        for (ExtinguishingMaterialTerms value : ExtinguishingMaterialTerms.values()) {
            Double termValue;
            Map<Integer, Double> timeValueMap1 = new HashMap<>();
            Map<Integer, Double> timeValueMap2 = new HashMap<>();
            Map<Integer, Double> timeValueMap3 = new HashMap<>();

            for (int i = 0; i < 4; i++) {
                if(i==0){
                    switch (value){
                        case NONE:
                            termValue = 1D;
                            timeValueMap1.put(i, termValue);
                            break;
                        case SOME:
                            termValue = 0D;
                            timeValueMap2.put(i, termValue);
                            break;
                        case PLENTY:
                            termValue = 0D;
                            timeValueMap3.put(i, termValue);
                            break;
                    }
                }else if(i > 0 && i <= 2){
                    switch (value){
                        case NONE:
                            termValue = 0D;
                            timeValueMap1.put(i, termValue);
                            break;
                        case SOME:
                            termValue = 1D;
                            timeValueMap2.put(i, termValue);
                            break;
                        case PLENTY:
                            termValue = 0D;
                            timeValueMap3.put(i, termValue);
                            break;
                    }
                }else{
                    switch (value){
                        case NONE:
                            termValue = 0D;
                            timeValueMap1.put(i, termValue);
                            break;
                        case SOME:
                            termValue = 0D;
                            timeValueMap2.put(i, termValue);
                            break;
                        case PLENTY:
                            termValue = 1D;
                            timeValueMap3.put(i, termValue);
                            break;
                    }
                }
            }
            if (ExtinguishingMaterialTerms.NONE == value) {
                extinguishingMaterialTermsMap.put(value, timeValueMap1);
            } else if (ExtinguishingMaterialTerms.SOME == value) {
                extinguishingMaterialTermsMap.put(value, timeValueMap2);
            } else {
                extinguishingMaterialTermsMap.put(value, timeValueMap3);
            }
        }
    }

    public void createTermsForNumberOfPeopleInjured() {
        for (PeopleInjuredTerms value : PeopleInjuredTerms.values()) {
            Double termValue;
            Map<Integer, Double> timeValueMap1 = new HashMap<>();
            Map<Integer, Double> timeValueMap2 = new HashMap<>();
            Map<Integer, Double> timeValueMap3 = new HashMap<>();
            Map<Integer, Double> timeValueMap4 = new HashMap<>();
            for (int i = 0; i < 6; i++) {
                if(i==0){
                    switch (value){
                        case NONE:
                            termValue = 1D;
                            timeValueMap1.put(i, termValue);
                            break;
                        case FEW:
                            termValue = 0D;
                            timeValueMap2.put(i, termValue);
                            break;
                        case SOME:
                            termValue = 0D;
                            timeValueMap3.put(i, termValue);
                            break;
                        case MANY:
                            termValue = 0D;
                            timeValueMap4.put(i, termValue);
                            break;
                    }
                }else if(i>0 && i<=2){
                    switch (value){
                        case NONE:
                            termValue = 0D;
                            timeValueMap1.put(i, termValue);
                            break;
                        case FEW:
                            termValue = 1D;
                            timeValueMap2.put(i, termValue);
                            break;
                        case SOME:
                            termValue = 0D;
                            timeValueMap3.put(i, termValue);
                            break;
                        case MANY:
                            termValue = 0D;
                            timeValueMap4.put(i, termValue);
                            break;
                    }
                }else if(i >2 && i<=4){
                    switch (value){
                        case NONE:
                            termValue = 0D;
                            timeValueMap1.put(i, termValue);
                            break;
                        case FEW:
                            termValue = 0D;
                            timeValueMap2.put(i, termValue);
                            break;
                        case SOME:
                            termValue = 1D;
                            timeValueMap3.put(i, termValue);
                            break;
                        case MANY:
                            termValue = 0D;
                            timeValueMap4.put(i, termValue);
                            break;
                    }
                }else {
                    switch (value){
                        case NONE:
                            termValue = 0D;
                            timeValueMap1.put(i, termValue);
                            break;
                        case FEW:
                            termValue = 0D;
                            timeValueMap2.put(i, termValue);
                            break;
                        case SOME:
                            termValue = 0D;
                            timeValueMap3.put(i, termValue);
                            break;
                        case MANY:
                            termValue = 1D;
                            timeValueMap4.put(i, termValue);
                            break;
                    }
                }
            }
            if (PeopleInjuredTerms.NONE == value) {
                peopleInjuredTermsMap.put(value, timeValueMap1);
            } else if (PeopleInjuredTerms.FEW == value) {
                peopleInjuredTermsMap.put(value, timeValueMap2);
            } else if(PeopleInjuredTerms.SOME == value){
                peopleInjuredTermsMap.put(value, timeValueMap3);
            }else {
                peopleInjuredTermsMap.put(value, timeValueMap4);
            }
        }
    }

    public FuzzyLogic() {
        fireTimeTermsMap = new HashMap<>();
        burningRoomsTermsMap = new HashMap<>();
        extinguishingMaterialTermsMap = new HashMap<>();
        peopleInjuredTermsMap = new HashMap<>();
    }
}
