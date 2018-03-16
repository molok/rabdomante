import * as React from 'react';
import * as ReactDOM from 'react-dom';
import App from './App';
import './index.css';
import { createStore } from 'redux';
import { Provider} from 'react-redux';

interface ActionType {
    type: string;
}

export interface State {
    counter: number;
    todos: Array<string>;
    todoText: any;
}

function actAddTodo(text: string) {
    return { type: 'add_todo'
           , text: text };
}

function actTodoTextChanged(text: string) {
    return { type: 'todo_text_changed'
           , text: text };
}

function reducers(state: State, action: any) {
    // console.log("state:", state);
    // console.log("action:", action);
    switch ( action.type ) {
        case 'increment':
            return { ...state, counter: state.counter + 1 };
        case 'decrement':
            return { ...state, counter: state.counter - 1 };
        case 'todo_text_changed':
            return { ...state, todoText: action.text };
        case 'add_todo':
            let newState = { ...state, todos: [...state.todos, action.text]};
            console.log("newState:", newState, "action was:", action.text);
            return newState;
        default:
            console.log("non gestito: ", action);
            return state;
    }
}

let store = createStore(reducers, { counter: 0, todos: [], todoText: "" });

function render() {
    ReactDOM.render(
        <Provider store={store}>
            <App />
        </Provider>,
        document.getElementById('root') as HTMLElement );
};
render();

export {actAddTodo, actTodoTextChanged};

// registerServiceWorker();
