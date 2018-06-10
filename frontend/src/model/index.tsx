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
    readonly recipe: Recipe,
    readonly searchCompleted: boolean
}

export interface CalcResultUi {
    readonly recipe: RecipeUi,
    readonly searchCompleted: boolean
}
export interface Recipe {
    readonly waters: Array<Water>,
    readonly salts: Array<Salt>
    readonly distance: number,
    readonly target: Water,
    readonly recipe: Water,
    readonly delta: Water
}

export interface RecipeUi {
    readonly waters: Array<WaterUi>,
    readonly salts: Array<SaltUi>
    readonly distance: number,
    readonly target: WaterUi|null,
    readonly recipe: WaterUi|null,
    readonly delta: WaterUi|null
}

export function water(name: string = "",
                      l: number = 1,
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

export interface Result {
    running: boolean
    solution: CalcResultUi|null,
    error: string|null,
    shouldScrollHere: boolean|null
}

export interface State {
    readonly target: WaterUi
    readonly sources: Array<WaterUi>
    readonly salts: Array<SaltUi>
    readonly result: Result
    readonly lang?: string|null
}

export const defaultSalt = ():SaltUi => {
    let s:SaltUi = {
        name: "",
        dg: Number.MAX_SAFE_INTEGER,
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

export const defaultState = ():State =>(
    { target: { ...water("target"), l:20 },
      sources: [{...water(), custom: false, l:Number.MAX_SAFE_INTEGER}],
      salts: [{...defaultSalt(), dg:Number.MAX_SAFE_INTEGER}],
      result: { running: false, solution: null, error: null, shouldScrollHere: false }
    });