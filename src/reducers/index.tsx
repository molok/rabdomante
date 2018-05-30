import * as React from 'react';
import {Salt, State, WaterDef} from "../model/index";
import {Actions, ActionTypes} from "../actions";

function saltReducer(salts: Array<Salt>, action: Actions): Array<Salt> {
    switch (action.type) {
        case ActionTypes.SALT_REMOVE:
            return salts.filter((_, idx) => idx !== action.payload);
        case ActionTypes.SALT_ADD:
            return [...salts, action.payload];
        case ActionTypes.SALT_CHANGED:
            return salts.map(
                (s, i) => i === action.payload.idx ? action.payload.salt : s);
        default: return salts;
    }
}

function targetReducer(target: WaterDef, action: Actions): WaterDef {
    switch (action.type) {
        case ActionTypes.TARGET_CHANGED:
            return action.payload;
        default:
            return target;
    }
}

function sourceReducer(sources: Array<WaterDef>, action: Actions): Array<WaterDef> {
    switch ( action.type ) {
        case ActionTypes.WATER_ADD:
            return [...sources, action.payload];
        case ActionTypes.WATER_CHANGED:
            return sources.map(
                (w, i) => i === action.payload.idx ? action.payload.water : w);
        case ActionTypes.WATER_REMOVE:
            return sources.filter((_, idx) => idx !== action.payload);
        default:
            return sources;
    }
}

function coreReducer(state: State, action: any): State {
    console.log("action", action, "state before:", state);
    const nextState= {
        sources: sourceReducer(state.sources, action),
        target: targetReducer(state.target, action),
        salts: saltReducer(state.salts, action)
    };
    console.log("state after", nextState);
    return nextState;
}

export function reducers (state: State, action: any): State {
    return coreReducer(state, action);
}
