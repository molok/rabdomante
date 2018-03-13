import * as React from 'react';
import * as ReactDOM from 'react-dom';
import App from './App';
import registerServiceWorker from './registerServiceWorker';
import './index.css';
import { createStore } from 'redux';

interface ActionType {
    type: string;
}

interface State {
    counter: number;
}

function reducers(state: State, action: ActionType) {
    // console.log("state:", state);
    // console.log("action:", action);
    switch ( action.type ) {
        case 'increment':
            return { counter: state.counter + 1 };
        case 'decrement':
            return { counter: state.counter - 1 };
        default:
            return state;
    }
}

let store = createStore(reducers, { counter: 0 });

function render() {
    ReactDOM.render(
        <App store={store}/>,
        document.getElementById('root') as HTMLElement );
};

render();

store.subscribe(render);
registerServiceWorker();