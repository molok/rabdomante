package alebolo.rabdomante;

import com.google.common.math.DoubleMath;

import java.util.List;
import java.util.Objects;

public interface IWater {
     double calcioMg();
     double magnesioMg();
     double sodioMg();
     double bicarbonatiMg();
     double solfatoMg();
     double cloruroMg();
     double liters();

     default boolean isSameAs(IWater water) {
          return DoubleMath.fuzzyCompare(water.liters(), this.liters(), 0.001) == 0 &&
                  DoubleMath.fuzzyCompare(water.calcioMg(), this.calcioMg(), 0.001) == 0 &&
                  DoubleMath.fuzzyCompare(water.magnesioMg(), this.magnesioMg(), 0.001) == 0 &&
                  DoubleMath.fuzzyCompare(water.sodioMg(), this.sodioMg(), 0.001) == 0 &&
                  DoubleMath.fuzzyCompare(water.bicarbonatiMg(), this.bicarbonatiMg(), 0.001) == 0 &&
                  DoubleMath.fuzzyCompare(water.solfatoMg(), this.solfatoMg(), 0.001) == 0 &&
                  DoubleMath.fuzzyCompare(water.cloruroMg(), this.cloruroMg(), 0.001) == 0;
     }

    default WaterProfile profile() {
        return new WaterProfile(calcioMg() / liters(),
                magnesioMg() / liters(),
                sodioMg() / liters(),
                bicarbonatiMg() / liters(),
                solfatoMg() / liters(),
                cloruroMg() / liters(), "");
    }


    class SourceRatio {
         private final WaterProfile profile;
         private final double ratio;
         public SourceRatio(WaterProfile profile, double ratio) {
             this.profile = profile;
             this.ratio = ratio;
         }
         WaterProfile profile() { return profile; }
         double ratio() { return ratio(); }

         @Override public boolean equals(Object o) {
             if (this == o) return true;
             if (o == null || getClass() != o.getClass()) return false;
             SourceRatio that = (SourceRatio) o;
             return Double.compare(that.ratio, ratio) == 0 &&
                     Objects.equals(profile, that.profile);
         }

         @Override public int hashCode() { return Objects.hash(profile, ratio); }
     }

    List<SourceRatio> sources();
}
