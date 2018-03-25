import {WaterDef} from "./model/index";
import {Salt} from "./model/index";

export const ADD_SOURCE = 'ADD_SOURCE';
export const CALCULATE = 'CALCULATE';
export const SOURCE_CHANGED = 'SOURCE_CHANGED';
export const TARGET_CHANGED = 'TARGET_CHANGED';
export const REMOVE_SOURCE = 'REMOVE_SOURCE';
export const SALT_CHANGED = 'SALT_CHANGED';
export const SALT_SELECTION_CHANGED = 'SALT_SELECTION_CHANGED';

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

export function targetChanged(water: WaterDef) { return { type: TARGET_CHANGED, payload: water } }
export function calculate() { return { type: CALCULATE } }
export function removeSource(idx: number) { return { type: REMOVE_SOURCE, idx}}
export function saltChanged(salt: Salt) { return { type: SALT_CHANGED, salt}}
export function saltSelectionChanged(salts: Array<string>) { return { type: SALT_SELECTION_CHANGED, salts}}
