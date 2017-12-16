package alebolo.rabdomante;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Core {
    private static Logger log = LoggerFactory.getLogger(Core.class);
    public static void main(String[] args){
        while(true){
            findClosestReal();
        }
    }
    public static void findClosestReal() {
        Profile levissima = new Profile( 21, 1.7, 1.9, 57.1, 17, 0, "levissima");
        Profile boario = new Profile( 131, 40, 5, 303, 240, 4, "boario");
        Profile eva = new Profile( 10.2, 4, 0.28, 48, 1.7, 0.17, "evaProfile");
        Profile santanna = new Profile( 10.5, 0, 0.9, 26.2, 7.8, 0, "santanna");
        Profile norda = new Profile( 10.8, 3, 2.3, 52.3, 6.3, 0.6, "norda");
        Profile vera = new Profile( 35, 12.6, 2, 148, 19.2, 2.6, "vera");
        Profile vitasnella = new Profile( 86, 26, 3, 301, 83, 2, "vitasnella");
        Profile dolomiti = new Profile(8, 8.7, 1.3, 94.6, 22, 1.1, "dolomiti");
        Profile sanbern = new Profile(9.5, 0.6, 0.6, 30.2, 2.3, 0.7, "sanbernardo");

        MineralProfile gypsum = new MineralProfile.Builder().name("gypsum").calcioRatio(0.2328).solfatoRatio(0.558).build();
        MineralProfile tableSalt = new MineralProfile.Builder().name("tableSalt").sodioRatio(0.3934).cloruroRatio(0.6066).build();

        int liters = 10;
        Water blackMediumTarget =
                new Water(liters,
                        Recipe.create(
                                new Profile(50, 10, 33, 142, 57, 44, "black medium")));

        long startTime = System.currentTimeMillis();

        List<Water> waters = Arrays.asList(
                new Water(liters, Recipe.create(levissima)),
                new Water(liters, Recipe.create(boario)),
                new Water(liters, Recipe.create(eva)),
                new Water(liters, Recipe.create(santanna)),
                new Water(liters, Recipe.create(norda)),
                new Water(liters, Recipe.create(vera)),
                new Water(liters, Recipe.create(vitasnella)),
                new Water(liters, Recipe.create(sanbern)),
                new Water(liters, Recipe.create(dolomiti)),
                new Water(liters,
                        new Recipe(Arrays.asList(new ProfileRatio(vera, 1.0)),
                                Arrays.asList(new MineralRatio(tableSalt, 70),
                                        new MineralRatio(gypsum, 70))))
        );
        Finder finder = new Finder();
        List<Water> xxx = finder.top(liters, blackMediumTarget
                , waters,
                Arrays.asList(
                        new MineralAddition(1000, gypsum),
                        new MineralAddition(1000, tableSalt)
                ));

        System.out.println("Execution took " + (System.currentTimeMillis() - startTime) + "ms");

        log.warn("\n"+blackMediumTarget.toString());

        waters.stream().forEach(w -> {
            log.warn("w: " + w.toString());
            log.warn("delta w: " + DistanceCalculator.distanceCoefficient(blackMediumTarget, w)); });


        log.warn("res:\n" + xxx.stream()
                .map(w -> "xdelta " +
                        String.format("%.2f", DistanceCalculator.distanceCoefficient(blackMediumTarget, w))
                        + " = " + info(w) )
                .collect(Collectors.joining("\n")));
    }

    private static String info(Water w) {
        return w.description();
    }
}
