import * as React from 'react';
import {SaltUi, State, WaterUi} from "../model/index";
import {Actions, ActionTypes} from "../actions";

function saltReducer(salts: Array<SaltUi>, action: Actions): Array<SaltUi> {
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

function targetReducer(target: WaterUi, action: Actions): WaterUi {
    switch (action.type) {
        case ActionTypes.TARGET_CHANGED:
            return action.payload;
        default:
            return target;
    }
}

function sourceReducer(sources: Array<WaterUi>, action: Actions): Array<WaterUi> {
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

function recipeReducer(state: State, action: Actions): State {
    switch ( action.type ) {
        case ActionTypes.FIND_SUCCESS:
            return {...state, result: { error: null, solution: action.payload }};
        case ActionTypes.FIND_FAILURE:
            return {...state, result: { error: action.payload, solution: null }};
        case ActionTypes.SOLUTION_SALT_CHANGED:
            if (state.result.solution && state.result.solution.recipe) {
                return {
                    ...state,
                    result: {
                        ...state.result,
                        solution: {
                            ...state.result.solution,
                            recipe: {
                                ...state.result.solution.recipe,
                                salts: [
                                    ...state.result.solution.recipe.salts.map(
                                        (w, i) => i === action.payload.idx ? action.payload.salt : w )]}}}}
            } else {
                return state;
            }
        case ActionTypes.SOLUTION_WATER_CHANGED:
            if (state.result.solution && state.result.solution.recipe) {
               return {
                   ...state,
                   result: {
                       ...state.result,
                       solution: {
                           ...state.result.solution,
                           recipe: {
                               ...state.result.solution.recipe,
                               waters: [
                                   ...state.result.solution.recipe.waters.map(
                                   (w, i) => i === action.payload.idx ? action.payload.water : w )]}}}}
            } else {
                return state;
            }
        default:
            return state;
    }
}

function coreReducer(state: State, action: any): State {
    console.log("action", action, "state before:", state);
    var nextState = {
        ...state,
        sources: sourceReducer(state.sources, action),
        target: targetReducer(state.target, action),
        salts: saltReducer(state.salts, action),
    };
    nextState = {
        ...nextState,
        ...recipeReducer(nextState, action)
    };

    console.log("state after", nextState);
    return nextState;
}

export function reducers (state: State, action: any): State {
    return coreReducer(state, action);
}
