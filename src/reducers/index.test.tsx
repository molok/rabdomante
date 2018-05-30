import {defaultSalt, water} from "../model/index";
import {createStore} from "redux";
import {reducers} from "./index";
import {Actions} from "../actions";

it('filter salts', () => {
    const defaultState =
        { target: water("target"),
            sources: [water("water #1")],
            salts: [ defaultSalt() ]
        };
    const store = createStore(reducers, defaultState);

    expect(store.getState().salts).toHaveLength(1);
    store.dispatch(Actions.removeSalt(0));
    expect(store.getState().salts).toHaveLength(0);
});

interface A {
    type: "A",
    payload: {a: number}
}

interface B {
    type: "B",
    payload: {b: number}
}
type AorB = A | B;

const doit = (x :AorB) => {
    switch (x.type) {
        case "A": return x.payload.a + 10
        case "B": return x.payload.b + 20
    }
}

it('typescript is cool', () => {
    console.log(doit({ type: "A", payload: {a: 100}}));
    console.log(doit({ type: "B", payload: {b: 100}}));
})
