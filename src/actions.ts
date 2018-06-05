import {CalcResult, CalcResultUi, defaultSalt, Recipe, State, water, WaterUi} from "./model/index";
import {SaltUi} from "./model/index";
import {ActionUnions, createAction, Action} from "./action-helper";

export enum ActionTypes {
    SALT_REMOVE = 'SALT_REMOVE',
    SALT_ADD = 'SALT_ADD',
    SALT_CHANGED = 'SALT_CHANGED',
    WATER_REMOVE = 'WATER_REMOVE',
    WATER_CHANGED = 'WATER_CHANGED',
    WATER_ADD = 'WATER_ADD',
    TARGET_CHANGED = 'TARGET_CHANGED',
    FIND_SUCCESS = 'FIND_SUCCESS',
    FIND_FAILURE = 'FIND_FAILURE',
    SOLUTION_WATER_CHANGED = 'SOLUTION_WATER_CHANGED',
    SOLUTION_SALT_CHANGED = 'SOLUTION_SALT_CHANGED',
    TOGGLE_SCROLL_TO_SOLUTION = 'TOGGLE_SCROLL_TO_SOLUTION',
}

export const Actions = {
    removeSalt: (idx: number) => createAction(ActionTypes.SALT_REMOVE, idx),
    addSalt: (s: SaltUi) => createAction(ActionTypes.SALT_ADD, s),
    changedSalt: (idx: number, salt: SaltUi) => createAction(ActionTypes.SALT_CHANGED, { idx, salt }),
    addWater: (w: WaterUi) => createAction(ActionTypes.WATER_ADD, w),
    changedWater: (idx: number, water: WaterUi) => createAction(ActionTypes.WATER_CHANGED, { idx, water }),
    removeWater: (idx: number) => createAction(ActionTypes.WATER_REMOVE, idx),

    targetChanged: (water: WaterUi) => createAction(ActionTypes.TARGET_CHANGED, water),

    findRecipeSuccess: (solution: CalcResultUi) => createAction(ActionTypes.FIND_SUCCESS, solution),
    findRecipeFailure: (error: string) => createAction(ActionTypes.FIND_FAILURE, error),

    solutionWaterChanged: (idx: number, water: WaterUi) => createAction(ActionTypes.SOLUTION_WATER_CHANGED, { idx, water }),
    solutionSaltChanged: (idx: number, salt: SaltUi) => createAction(ActionTypes.SOLUTION_SALT_CHANGED, { idx, salt }),
    toggleScrollToSolution: () => createAction(ActionTypes.TOGGLE_SCROLL_TO_SOLUTION)
};

export type Actions = ActionUnions<typeof Actions>
