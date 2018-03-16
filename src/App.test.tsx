import * as React from 'react';
import * as ReactDOM from 'react-dom';
import App from './App';
import {createStore} from 'redux';

// it('renders without crashing', () => {
//     let store = createStore((s, a) => s, { counter: 0 });
//     const div = document.createElement('div');
//     ReactDOM.render(<App store={store}/>, div);
// });

function removeElement(array :Array<any>, pos :number): Array<any> {
    return array.slice(0, pos).concat(array.slice(pos+1));
}

function removeElement6(array :Array<any>, pos :number): Array<any> {
    return [
        ...array.slice(0, pos),
        ...array.slice(pos+1)
        ];
}

it('array slice', () => {
    let arr = ['a', 'b', 'c', 'd'];
    expect(removeElement(arr, 2)).toEqual(['a', 'b', 'd']);
});

it('array slice6', () => {
    let arr = ['a', 'b', 'c', 'd'];
    expect(removeElement6(arr, 2)).toEqual(['a', 'b', 'd']);
});

