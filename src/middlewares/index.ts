import {CalcResult, Salt, SaltUi, State, Water, WaterUi} from "../model";
import {Actions, ActionTypes} from "../actions";
import {asyncFindRecipe} from "../Api";
import {translate} from "../components/Translate";
import msg from "../i18n/msg";

export const wToUi = (w: Water, name?: string): WaterUi => { return {...w, visible: false, custom: true, name: (name) ? name : w.name} };
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
                                        recipe: wToUi(result.recipe.recipe, translate(msg.recipe)),
                                        delta: wToUi(result.recipe.delta, translate(msg.delta))
                                    },
                                    searchCompleted: result.searchCompleted,
                                }));
                        } else {
                            store.dispatch(Actions.searchRunning(false));
                            store.dispatch(Actions.findRecipeFailure("No solution found!"));
                        }
                    })
                .catch(err => {
                    store.dispatch(Actions.searchRunning(false));
                    console.log("ERROR API", err);
                    store.dispatch(Actions.findRecipeFailure("No solution found: " + err))});
        }
        default: next(action);
    }
};

export const filterMiddleware = (store: {getState(): State, dispatch(action: Actions):void}) => (next: any) => (action: Actions) => {
    if (store.getState().result.running) {
        switch(action.type) {
            case ActionTypes.FIND_RUNNING:
                next(action);
            case ActionTypes.CLEAR_STATE:
                next(action);
        }
    } else {
        next(action);
    }
};
