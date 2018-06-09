import {CalcResult, Salt, SaltUi, State, Water, WaterUi} from "../model";
import {Actions, ActionTypes} from "../actions";
import {asyncFindRecipe} from "../Api";

export const wToUi = (w: Water): WaterUi => { return {...w, visible: false, custom: true} };
export const sToUi = (s: Salt): SaltUi => { return {...s, visible: false, custom: true} };

export const apiMiddleware = (store: {getState(): State, dispatch(action: Actions):void}) => (next: any) => (action: Actions) => {
    switch (action.type) {
        case ActionTypes.FIND_START: {
            let {waters, salts, target} = action.payload;
            store.dispatch(Actions.searchRunning(true));
            asyncFindRecipe(waters, salts, target)
                .then(
                    (result: CalcResult) => {
                        store.dispatch(Actions.searchRunning(false));
                        if (result && result.recipe) {
                            store.dispatch(Actions.findRecipeSuccess(
                                {
                                    recipe: {
                                        waters: (result.recipe) ? result.recipe.waters.map(w => wToUi(w)) : [],
                                        salts: (result.recipe) ? result.recipe.salts.map(s => sToUi(s)) : [],
                                        distance: (result.recipe) ? result.recipe.distance : 0,
                                        target: wToUi(result.recipe.target),
                                        recipe: wToUi(result.recipe.recipe),
                                        delta: wToUi(result.recipe.delta)
                                    },
                                    searchCompleted: result.searchCompleted,
                                }));
                        } else {
                            store.dispatch(Actions.findRecipeFailure("No solution found!"));
                        }
                    })
                .catch(err => store.dispatch(Actions.findRecipeFailure("No solution found: " + err)));
        }
        default: next(action);
    }
};

export const filterMiddleware = (store: {getState(): State, dispatch(action: Actions):void}) => (next: any) => (action: Actions) => {
    if (store.getState().result.running && action.type !== ActionTypes.FIND_RUNNING) {
        console.log("action ignored:", action);
        /* block the UI */
    } else {
        next(action)
    }
};
