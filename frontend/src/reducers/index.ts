import {defaultState, SaltUi, State, WaterUi} from "../model/index";
import {Actions, ActionTypes} from "../actions";

function saltReducer(salts: Array<SaltUi>, action: Actions): Array<SaltUi> {
    switch (action.type) {
        case ActionTypes.SALT_REMOVE:
            return salts.filter((_, idx) => idx !== action.payload);
        case ActionTypes.SALT_ADD:
            return [...salts, action.payload];
        case ActionTypes.SALT_CHANGED:
            return salts.map(
                (s, i) =>
                    i === action.payload.idx
                    ? action.payload.salt
                    : s);
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
        case ActionTypes.FIND_RUNNING:
            return {...state, result: {...state.result, running: action.payload}};
        case ActionTypes.TOGGLE_SCROLL_TO_SOLUTION:
            return {...state, result: {...state.result, shouldScrollHere: state.result.shouldScrollHere ? false : true}};
        case ActionTypes.FIND_SUCCESS:
            let show = {
                ...state,
                result: {
                    ...state.result,
                    error: null,
                    solution: action.payload ,
                    shouldScrollHere: true
                }
            };
            return {
                ...show,
                sources: show.sources.map(w => ({...w, visible: false})),
                salts: show.salts.map(s => ({...s, visible: false}))
            };
        case ActionTypes.FIND_FAILURE:
            return {...state, result: { ...state.result, error: action.payload, solution: null }};
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
        case ActionTypes.DELTA_CHANGED:
            if (state.result.solution && state.result.solution.recipe.delta) {
                return {
                    ...state, result: {
                        ...state.result,
                        solution: {
                            ...state.result.solution,
                            recipe: {
                                ...state.result.solution.recipe,
                                delta: action.payload }}}};
            }
        case ActionTypes.RECIPE_CHANGED:
            if (state.result.solution && state.result.solution.recipe.recipe) {
                return {
                    ...state, result: {
                        ...state.result,
                        solution: {
                            ...state.result.solution,
                            recipe: {
                                ...state.result.solution.recipe,
                                recipe: action.payload }}}};
            }
        default:
            return state;
    }
}

const clearReducer = (state: State, action: Actions) => {
    switch (action.type) {
        case ActionTypes.CLEAR_STATE:
            return {...defaultState(), result: {...defaultState().result, running: false}};
        default:
            return state
    }
};

const langReducer = (lang: string|null|undefined, action: Actions): string|null|undefined => {
    let newLang;
    switch (action.type) {
        case ActionTypes.CHANGE_LANG:
            newLang = action.payload;
            break;
        default:
            newLang = lang;
            break;
    }
    if (!newLang) {
        newLang = navigator.language.toLowerCase().split("-")[0];
    }
    newLang = (["it", "en"].indexOf(newLang) > -1) ? newLang : "en";
    return newLang;
};

function coreReducer(state: State, action: any): State {
    console.log("action", action, "state before:", state);

    var nextState = clearReducer(state, action);

    nextState = {
        ...nextState,
        sources: sourceReducer(nextState.sources, action),
        target: targetReducer(nextState.target, action),
        salts: saltReducer(nextState.salts, action),
        lang: langReducer(nextState.lang, action),
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
