import {WaterDef} from "./water";

export const ADD_SOURCE = 'ADD_SOURCE';
export const CALCULATE = 'CALCULATE';
export const SOURCE_CHANGED = 'SOURCE_CHANGED';
export const TARGET_CHANGED = 'TARGET_CHANGED';


export function addSource(w: WaterDef) {
    return {
        type: ADD_SOURCE,
        payload: w
    }
}

export function sourceChanged(idx: number, water: WaterDef) {
    return {
        type: SOURCE_CHANGED,
        payload: { idx, water }
    }
}

export function targetChanged(water: WaterDef) {
    return {
        type: TARGET_CHANGED,
        payload: water
    }
}

export function calculate() {
    return {
        type: CALCULATE
    }
}
