import * as React from 'react';
import * as ReactDOM from 'react-dom';
import './index.css';
import {createStore, applyMiddleware } from 'redux';
import {Provider} from 'react-redux';
import Rabdo from "./containers/Rabdo";
import {defaultState} from "./model";
import {reducers} from "./reducers";
import thunk from 'redux-thunk';

let store = createStore(
    reducers,
    defaultState,
    applyMiddleware(thunk)
);

function render() {
    ReactDOM.render(
        <Provider store={store}>
            <Rabdo />
        </Provider>,
        document.getElementById('root') as HTMLElement );
}
render();
// registerServiceWorker();
