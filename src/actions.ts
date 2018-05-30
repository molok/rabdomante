import {WaterDef} from "./model/index";
import {Salt} from "./model/index";
import {ActionUnions, createAction} from "./action-helper";

export enum ActionTypes {
    SALT_REMOVE = 'SALT_REMOVE',
    SALT_ADD = 'SALT_ADD',
    SALT_CHANGED = 'SALT_CHANGED',
    WATER_REMOVE = 'WATER_REMOVE',
    WATER_CHANGED = 'WATER_CHANGED',
    WATER_ADD = 'WATER_ADD',
    TARGET_CHANGED = 'TARGET_CHANGED',
    CALCULATE = 'CALCULATE',
}

export const Actions = {
    removeSalt: (idx: number) => createAction(ActionTypes.SALT_REMOVE, idx),
    addSalt: (s: Salt) => createAction(ActionTypes.SALT_ADD, s),
    changedSalt: (idx: number, salt: Salt) => createAction(ActionTypes.SALT_CHANGED, { idx, salt }),
    addWater: (w: WaterDef) => createAction(ActionTypes.WATER_ADD, w),
    changedWater: (idx: number, water: WaterDef) => createAction(ActionTypes.WATER_CHANGED, { idx, water }),
    removeWater: (idx: number) => createAction(ActionTypes.WATER_REMOVE, idx),

    targetChanged: (water: WaterDef) => createAction(ActionTypes.TARGET_CHANGED, water),

    calculate: () => createAction(ActionTypes.CALCULATE),
};

export type Actions = ActionUnions<typeof Actions>
