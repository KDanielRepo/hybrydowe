import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum FireExtinguisherType {

    PROSZKOWA(Lists.newArrayList(FireType.A, FireType.B, FireType.C, FireType.D)),
    PIANOWA(Lists.newArrayList(FireType.A, FireType.B, FireType.F)),
    SNIEGOWA(Lists.newArrayList(FireType.B, FireType.E)),
    WODNA(Lists.newArrayList(FireType.A, FireType.F));

    private List<FireType> type;

    public static List<FireExtinguisherType> findContainingFireType(FireType fireType){
        return Stream.of(FireExtinguisherType.values())
                .filter(a->a.getType().contains(fireType))
                .collect(Collectors.toList());
    }
}
