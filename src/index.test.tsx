import 'jsdom-global/register';
import * as React from "react";
import {mount, shallow} from 'enzyme';
import * as enzyme from 'enzyme';
import * as Adapter from 'enzyme-adapter-react-16';
enzyme.configure({ adapter: new Adapter() });
import {water} from "./model";
import Rabdo from "./containers/Rabdo";
import {addSource} from "./actions";
import {reducers} from "./reducers";
import {createStore} from "redux";
it('mount integration', () => {
    const defaultState =
        { target: water("target"),
            sources: [water("water #1"), water("water #2")],
            salts: [ ]
        };
    const store = createStore(reducers, defaultState);
    const component = mount(<Rabdo/>, { context: { store } });
    expect(component.find('.panel.waterPanel')).toHaveLength(2);

    store.dispatch(addSource(water("water #3")));

    const componentAfter = mount(<Rabdo/>, { context: { store } });
    expect(componentAfter.find('.panel.waterPanel')).toHaveLength(3);
});
