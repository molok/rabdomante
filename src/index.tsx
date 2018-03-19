import * as React from 'react';
import * as ReactDOM from 'react-dom';
import App from './App';
import './index.css';
import { createStore } from 'redux';
import { Provider} from 'react-redux';
import {ADD_SOURCE, CALCULATE, SOURCE_CHANGED, TARGET_CHANGED} from "./actions";
import {water, WaterDef} from "./water";

export interface State {
    readonly target: WaterDef;
    readonly sources: Array<WaterDef>
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
        default:
            console.log("non gestito: ", action);
            return state;
    }
}

let store = createStore(reducers, { target: water("target"), sources: [water()] } );

function render() {
    ReactDOM.render(
        <Provider store={store}>
            <App />
        </Provider>,
        document.getElementById('root') as HTMLElement );
};
render();

// registerServiceWorker();
