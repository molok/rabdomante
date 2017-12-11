package alebolo.rabdomante;

import org.apache.commons.collections4.ListUtils;

import java.util.*;
import java.util.stream.Collectors;

public class WaterMerger {
    public static Water2 merge(Water2 w1, Water2 w2) {
        double totLiters = w1.liters() + w2.liters();
        return new Water2(totLiters,
                new Recipe(combineProfiles(w1, w2, totLiters), Arrays.asList() /* TODO */));
    }

    private static List<ProfileRatio> combineProfiles(Water2 w1, Water2 w2, double totLiters) {
        Map<String, ProfileRatio> profiles = new HashMap<>();
        for (ProfileRatio currProfile : ListUtils.union(profiles(w1, totLiters),
                                                        profiles(w2, totLiters))) {
            profiles.merge(
                    currProfile.profile().name(),  /* assunzione che a parità di */
                    currProfile,
                    (a, b) -> new ProfileRatio(a.profile(), a.ratio() + b.ratio()) );
        }
        return new ArrayList<>(profiles.values());
    }

    private static List<ProfileRatio> profiles(Water2 w, double totLiters) {
        return w.recipe().profilesRatio().stream()
                .map(pr -> new ProfileRatio(pr.profile(), pr.ratio() * (w.liters() / totLiters)))
                .collect(Collectors.toList());
    }
}
