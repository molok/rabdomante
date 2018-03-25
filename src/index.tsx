import * as React from 'react';
import * as ReactDOM from 'react-dom';
import './index.css';
import {createStore} from 'redux';
import {Provider} from 'react-redux';
import Rabdo from "./containers/Rabdo";
import {defaultState} from "./model";
import reducers from "./reducers";

let store = createStore(reducers, defaultState);

function render() {
    ReactDOM.render(
        <Provider store={store}>
            <Rabdo />
        </Provider>,
        document.getElementById('root') as HTMLElement );
};
render();
// registerServiceWorker();
