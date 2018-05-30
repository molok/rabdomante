import {WaterDef} from "./model/index";
import {Salt} from "./model/index";

export const ADD_SOURCE = 'ADD_SOURCE';
export const CALCULATE = 'CALCULATE';
export const SOURCE_CHANGED = 'SOURCE_CHANGED';
export const TARGET_CHANGED = 'TARGET_CHANGED';
export const REMOVE_SOURCE = 'REMOVE_SOURCE';
export const SALT_ADD = 'SALT_ADD';
export const SALT_REMOVE = 'SALT_REMOVE';
export const SALT_CHANGED = 'SALT_CHANGED';

export function targetChanged(water: WaterDef) { return { type: TARGET_CHANGED, payload: water } }

export function addSource(w: WaterDef) { return { type: ADD_SOURCE, payload: w } }
export function sourceChanged(idx: number, water: WaterDef) { return { type: SOURCE_CHANGED, payload: { idx, water } } }
export function removeSource(idx: number) { return { type: REMOVE_SOURCE, payload: idx}}

export function addSalt(s: Salt) { return { type: SALT_ADD, payload: s } }
export function removeSalt(idx: number) { return { type: SALT_REMOVE, payload: idx } }
export function changedSalt(idx: number, salt: Salt) { return { type: SALT_CHANGED, payload: { idx, salt } } }

export function calculate() { return { type: CALCULATE } }
