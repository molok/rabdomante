import 'jsdom-global/register';
import * as React from "react";
import {mount, shallow} from 'enzyme';
import * as enzyme from 'enzyme';
import * as Adapter from 'enzyme-adapter-react-16';
import {water} from "../model/index";
import SourceWaters from "./SourceWaters";
import {Provider} from "react-redux";
enzyme.configure({ adapter: new Adapter() });
import {createMockStore} from 'redux-test-utils';
import Rabdo from "../containers/Rabdo";
import {WaterDef} from "../model";

it('sources', () => {
    let sources: Array<WaterDef> = [ water("water1"), water("water2") ];
    const removeSources = (idx: number) => {};
    const sourceChanged = (idx: number, w:WaterDef) => {};
    let wrapper = shallow(<SourceWaters sources={sources} removeSource={removeSources} sourceChanged={sourceChanged}/>);
    expect(wrapper.find('.waterPanel')).toHaveLength(2);
});

it('connects', () => {
    const defaultState =
        { target: water("target"),
            sources: [water("water #1"), water("water #2")],
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
    const store = createMockStore(defaultState);
    const component = mount(<Rabdo/>, { context: { store } });
    expect(component.find('.panel.waterPanel')).toHaveLength(2);
});

it('full integration', () => {
    const defaultState =
        { target: water("target"),
            sources: [water("foobar")],
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
    const store = createMockStore(defaultState);
    // const component = shallowWithStore(<Rabdo />, store);
    // const store = createStore(reducers, defaultState);
    const component = shallow(
        <Provider store={store}>
            <Rabdo/>
        </Provider>
    )
    // console.log(component.toJSON());
    expect(component.find('.waterPanel')).toHaveLength(1);
});
