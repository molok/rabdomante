import * as React from 'react';
import * as ReactDOM from 'react-dom';
import App from './App';
import './index.css';
import { createStore } from 'redux';
import { Provider} from 'react-redux';
import {
    ADD_SOURCE, CALCULATE, REMOVE_SOURCE, SALT_CHANGED, SALT_SELECTION_CHANGED, SOURCE_CHANGED,
    TARGET_CHANGED
} from "./actions";
import {water, WaterDef} from "./water";

export interface Salt {
    g: number
    name: string,
    selected: boolean,
}

export interface State {
    readonly target: WaterDef;
    readonly sources: Array<WaterDef>
    readonly salts: Array<Salt>
}

function reducers(state: State, action: any) {
    console.log("state:", state);
    console.log("action:", action);
    switch ( action.type ) {
        case ADD_SOURCE:
            return {
                ...state,
                sources: [...state.sources, action.payload]
            };
        case SOURCE_CHANGED:
            return {
                ...state,
                sources: state.sources.map(
                    (w,i) => i === action.payload.idx ? action.payload.water : w)
            };
        case TARGET_CHANGED:
            return {
                ...state,
                target: action.payload
            };
        case CALCULATE:
            return state;
        case REMOVE_SOURCE:
            return {
                ...state,
                sources: state.sources.filter((_, idx) => idx !== action.idx )
            };
        case SALT_CHANGED:
            return {
                ...state,
                salts: state.salts.map( salt => { (salt.name === action.salt.name) ? action.salt : salt })
            };
        case SALT_SELECTION_CHANGED:
            return {
                ...state,
                salts: state.salts.map( salt => ({ ...salt, selected: action.salts.includes(salt.name) }) )
            };
        default:
            console.log("non gestito: ", action);
            return state;
    }
}

let store = createStore(reducers,
    { target: water("target"),
      sources: [water()],
      salts: [
          { g: 0, name: "gypsum", selected: false },
          { g: 0, name: "table salt", selected: false },
          { g: 0, name: "epsom salt", selected: false },
          { g: 0, name: "calcium chloride", selected: false },
          { g: 0, name: "baking soda", selected: false },
          { g: 0, name: "chalk", selected: false },
          { g: 0, name: "pickling lime", selected: false },
          { g: 0, name: "magnesium chloride", selected: false },
          ]
    } );

function render() {
    ReactDOM.render(
        <Provider store={store}>
            <App />
        </Provider>,
        document.getElementById('root') as HTMLElement );
};
render();

// registerServiceWorker();
