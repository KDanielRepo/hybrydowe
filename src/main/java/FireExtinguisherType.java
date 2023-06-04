import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum FireExtinguisherType {

    PROSZKOWA(Lists.newArrayList(FireType.A, FireType.B, FireType.C)),
    PIANOWA(Lists.newArrayList(FireType.A, FireType.B, FireType.F)),
    SNIEGOWA(Lists.newArrayList(FireType.B, FireType.E)),
    WODNA(Lists.newArrayList(FireType.A, FireType.F));

    private List<FireType> type;
}
