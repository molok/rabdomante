export interface MineralContent {
    name: string,
    ca: number,
    mg: number,
    na: number,
    so4: number,
    cl: number,
    hco3: number,
}

export interface UiIngredient { visible: boolean, custom: boolean }
export interface Salt extends MineralContent { dg: number }
export interface Water extends MineralContent { l: number }
export interface SaltUi extends UiIngredient, Salt { }
export interface WaterUi extends UiIngredient, Water { }

export interface CalcResult {
    recipe: Recipe,
    searchCompleted: boolean
}
export interface Recipe {
    waters: [Water],
    salts: [Salt],
    distance: number,
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
                      ):WaterUi {
    return {name, l: l, ca, mg, na, so4, cl, hco3, visible, custom}
}

export interface State {
    readonly target: WaterUi
    readonly sources: Array<WaterUi>
    readonly salts: Array<SaltUi>
    readonly result: { solution: CalcResult|null, error: string|null }
}

export const defaultSalt = ():SaltUi => {
    let s:SaltUi = {
        name: "",
        dg: 0,
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

let customWater: WaterUi = water();
customWater.custom = false;

export const defaultState =
    {
        target: water("target"),
        sources: [customWater],
        salts: [defaultSalt()]
    };