import * as React from 'react';
import * as act from '../actions';
import {State} from "../index";

function reducers(state: State, action: any) {
    console.log("state:", state);
    console.log("action:", action);
    switch ( action.type ) {
        case act.ADD_SOURCE:
            return {
                ...state,
                sources: [...state.sources, action.payload]
            };
        case act.SOURCE_CHANGED:
            return {
                ...state,
                sources: state.sources.map(
                    (w,i) => i === action.payload.idx ? action.payload.water : w)
            };
        case act.TARGET_CHANGED:
            return {
                ...state,
                target: action.payload
            };
        case act.CALCULATE:
            return state;
        case act.REMOVE_SOURCE:
            return {
                ...state,
                sources: state.sources.filter((_, idx) => idx !== action.idx )
            };
        case act.SALT_CHANGED:
            return {
                ...state,
                salts: state.salts.map( salt => { (salt.name === action.salt.name) ? action.salt : salt })
            };
        case act.SALT_SELECTION_CHANGED:
            return {
                ...state,
                salts: state.salts.map( salt => ({ ...salt, selected: action.salts.includes(salt.name) }) )
            };
        default:
            console.log("non gestito: ", action);
            return state;
    }
}

export default reducers;
