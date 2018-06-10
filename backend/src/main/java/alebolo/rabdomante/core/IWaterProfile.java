package alebolo.rabdomante.core;

import alebolo.rabdomante.Msg;
import alebolo.rabdomante.cli.RabdoInputException;

public interface IWaterProfile {
    static boolean sameProfile(IWaterProfile a, IWaterProfile b) {
        return a.ca() == b.ca() &&
               a.mg() == b.mg() &&
               a.na() == b.na() &&
               a.so4() == b.so4() &&
               a.cl() == b.cl() &&
               a.hco3() == b.hco3();
    }

    default int validate(int v) {
        if (v >= 0) {
            return v;
        } else if (v == -1) {
            return 0; // I use -1 for N/A, in the future I may reject this too
        } else {
            throw new RabdoInputException(Msg.mineralValueCanTBeLt1Was() +v);
        }
    }

    int ca();
    int mg();
    int na();
    int so4();
    int cl();
    int hco3();
    String name();

}
