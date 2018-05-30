import {defaultSalt, water} from "../model/index";
import {createStore} from "redux";
import {reducers} from "./index";
import {removeSalt} from "../actions";

it('filter salts', () => {
    const defaultState =
        { target: water("target"),
            sources: [water("water #1")],
            salts: [ defaultSalt() ]
        };
    const store = createStore(reducers, defaultState);

    expect(store.getState().salts).toHaveLength(1);
    store.dispatch(removeSalt(0));
    expect(store.getState().salts).toHaveLength(0);
});
