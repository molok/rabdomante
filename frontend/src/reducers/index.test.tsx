import {defaultSalt, Recipe, State, water} from "../model/index";
import {reducers} from "./index";
import {Actions} from "../actions";
import {createStore} from "redux";

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

it('promises', () => {
    let x = new Promise<string>((resolve, reject) => {
        console.log('bef');
        // setTimeout(2000);
        console.log('aft');
        resolve('ok')
    });
    x.catch(r => {
        console.log('errr: ', r);
    }).then(res => {
        console.log('res:', res);
    });
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

it('mapped', () => {
    type Point = { x: string, y: string};
    var z : Point["x"] = "foo";
    console.log(typeof z);
    // var z: Readonly<Point> = { x: 10, y: 20};
    // type Stringify<T> = {
    //     [P in keyof T]: string;
    // };
    // console.log(keyof Point);
})
