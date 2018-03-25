export interface Salt {
    g: number
    name: string,
    selected: boolean,
}

export interface WaterDef {
    name: string,
    l: number,
    ca: number,
    mg: number,
    na: number,
    so4: number,
    cl: number,
    hco3: number
    visible: boolean
}

export function water(name: string = "",
                      l: number = 0,
                      ca: number = 0,
                      mg: number = 0,
                      na: number = 0,
                      so4: number = 0,
                      cl: number = 0,
                      hco3: number = 0,
                      visible: boolean = true) {
    return {name, l, ca, mg, na, so4, cl, hco3, visible}
}

export interface State {
    readonly target: WaterDef;
    readonly sources: Array<WaterDef>
    readonly salts: Array<Salt>
}

export const defaultState =
    {
        target: water("target"),
        sources: [water()],
        salts: [
            {g: 0, name: "gypsum", selected: false},
            {g: 0, name: "table salt", selected: false},
            {g: 0, name: "epsom salt", selected: false},
            {g: 0, name: "calcium chloride", selected: false},
            {g: 0, name: "baking soda", selected: false},
            {g: 0, name: "chalk", selected: false},
            {g: 0, name: "pickling lime", selected: false},
            {g: 0, name: "magnesium chloride", selected: false},
        ]
    };