import * as React from 'react';
import * as act from '../actions';
import {Salt, State, WaterDef} from "../model/index";

function saltReducer(salts: Array<Salt>, action: any): Array<Salt> {
    switch (action.type) {
        case act.SALT_CHANGED:
            return salts.map( salt => (salt.name === action.salt.name) ? action.salt : salt);
        case act.SALT_SELECTION_CHANGED:
            return salts.map( salt => ({ ...salt, selected: action.salts.includes(salt.name) }));
        default: return salts;
    }
}

function targetReducer(target: WaterDef, action: any): WaterDef {
    switch (action.type) {
        case act.TARGET_CHANGED:
            return action.payload;
        default:
            return target;
    }
}

function sourceReducer(sources: Array<WaterDef>, action: any): Array<WaterDef> {
    switch ( action.type ) {
        case act.ADD_SOURCE:
            return [...sources, action.payload];
        case act.SOURCE_CHANGED:
            return sources.map(
                (w, i) => i === action.payload.idx ? action.payload.water : w);
        case act.REMOVE_SOURCE:
            return sources.filter((_, idx) => idx !== action.idx);
        default:
            return sources;
    }
}

function coreReducer(state: State, action: any): State {
    // console.log("action", action, "state before:", state);
    const nextState= {
        sources: sourceReducer(state.sources, action),
        target: targetReducer(state.target, action),
        salts: saltReducer(state.salts, action)
    };
    // console.log("state after", nextState);
    return nextState;
}

export function reducers (state: State, action: any): State {
    return coreReducer(state, action);
}
