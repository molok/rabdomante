import * as React from 'react';
import * as ReactDOM from 'react-dom';
import './index.css';
import { createStore } from 'redux';
import { Provider} from 'react-redux';
import * as act from './actions';
import {water, WaterDef} from "./water";
import Rabdo from "./containers/Rabdo";
import reducers from "./reducers";

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

const defaultState =
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
    };

let store = createStore(reducers, defaultState);

function render() {
    ReactDOM.render(
        <Provider store={store}>
            <Rabdo />
        </Provider>,
        document.getElementById('root') as HTMLElement );
};
render();

export default defaultState;
// registerServiceWorker();
