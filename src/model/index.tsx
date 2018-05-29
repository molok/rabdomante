export interface MineralContent {
    name: string,
    ca: number,
    mg: number,
    na: number,
    so4: number,
    cl: number,
    hco3: number,
}

export interface UiMineralContent extends MineralContent {
    visible: boolean,
    custom: boolean
}

export interface Salt extends UiMineralContent {
    g: number
}

export interface WaterDef extends UiMineralContent {
    l: number,
}

export function water(name: string = "",
                      l: number = 0,
                      ca: number = 0,
                      mg: number = 0,
                      na: number = 0,
                      so4: number = 0,
                      cl: number = 0,
                      hco3: number = 0,
                      visible: boolean = true,
                      custom: boolean = true
                      ):WaterDef {
    return {name, l, ca, mg, na, so4, cl, hco3, visible, custom}
}

export interface State {
    readonly target: WaterDef;
    readonly sources: Array<WaterDef>
    readonly salts: Array<Salt>
}

const defaultSalt = ():Salt => {
    let s:Salt = {
        name: "",
        g: 0,
        ca: 0,
        mg: 0,
        na: 0,
        so4: 0,
        cl: 0,
        hco3: 0,
        visible: true,
        custom: false
    };
    return s;
};

export const defaultState =
    {
        target: water("target"),
        sources: [water()],
        salts: [defaultSalt()]
        // salts: [
        //     {g: 0, name: "gypsum", selected: false},
        //     {g: 0, name: "table salt", selected: false},
        //     {g: 0, name: "epsom salt", selected: false},
        //     {g: 0, name: "calcium chloride", selected: false},
        //     {g: 0, name: "baking soda", selected: false},
        //     {g: 0, name: "chalk", selected: false},
        //     {g: 0, name: "pickling lime", selected: false},
        //     {g: 0, name: "magnesium chloride", selected: false},
        // ]
    };