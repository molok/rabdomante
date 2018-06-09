import * as React from 'react';
import * as ReactDOM from 'react-dom';
import './index.css';
import {createStore, applyMiddleware } from 'redux';
import {Provider} from 'react-redux';
import Rabdo from "./containers/Rabdo";
import {defaultState, State} from "./model";
import {reducers} from "./reducers";

const loadState = ():State => {
    try {
        const serializedState = localStorage.getItem('state');
        if (serializedState === null) {
            return defaultState;
        } else {
            return JSON.parse(serializedState);
        }
    } finally { }
};

let store = createStore(
    reducers,
    loadState()
);

const saveState = (state: State) => {
    try {
        const serializedState = JSON.stringify(state);
        localStorage.setItem('state', serializedState);
    } finally {}
};

store.subscribe(() => {
    saveState(store.getState())
});

function render() {
    ReactDOM.render(
        <Provider store={store}>
            <Rabdo />
        </Provider>,
        document.getElementById('root') as HTMLElement );
}
render();

