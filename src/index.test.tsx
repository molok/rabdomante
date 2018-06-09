import 'jsdom-global/register';
import * as React from "react";
import {mount} from 'enzyme';
import * as enzyme from 'enzyme';
import * as Adapter from 'enzyme-adapter-react-16';
enzyme.configure({ adapter: new Adapter() });
import {State, water} from "./model";
import Rabdo from "./containers/Rabdo";
import {reducers} from "./reducers";
import {createStore} from "redux";
import {Actions} from "./actions";
it('mount integration', () => {
    const defaultState =
        { target: water("target"),
            sources: [water("water #1"), water("water #2")],
            salts: [ ]
        };
    const store = createStore(reducers, defaultState);
    const component = mount(<Rabdo/>, { context: { store } });
    expect(component.find('.panel.waterPanel')).toHaveLength(2);

    store.dispatch(Actions.addWater(water("water #3")));

    const componentAfter = mount(<Rabdo/>, { context: { store } });
    expect(componentAfter.find('.panel.waterPanel')).toHaveLength(3);
});

it('solution waters', () => {
    const defaultState:State =
        { target: water("target"),
          sources: [],
          salts: [],
          result: {
            running: false,
            solution: {
                recipe: {
                    waters: [],
                    salts: [],
                    distance: 0,
                    recipe: null,
                    delta: null,
                    target: null
                },
                searchCompleted: true
              }, error: null, shouldScrollHere: false }
        };
    const store = createStore(reducers, defaultState)
    const component = mount(<Rabdo/>, { context: { store } });

    // console.log(component.debug());
    expect(component.find('.panel.waterPanel')).toHaveLength(0);

    store.dispatch(Actions.findRecipeSuccess(
        { recipe: {
                waters: [water("water #sol1")],
                salts: [],
                distance: 0,
                recipe: null,
                delta: null,
                target: null
            },
            searchCompleted: true
        }));

    const componentAfter = mount(<Rabdo/>, { context: { store } });
    expect(componentAfter.find('.panel.waterPanel')).toHaveLength(1);

    let newWater = water("water #sol1");
    newWater.visible = false;
    store.dispatch(Actions.solutionWaterChanged(0, newWater));

    const componentAfter2 = mount(<Rabdo/>, { context: { store } });
    expect(componentAfter2.find('.panel.waterPanel')).toHaveLength(1);
})
