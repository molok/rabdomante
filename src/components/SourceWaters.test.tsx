import * as React from "react";
import {shallow} from 'enzyme';
import * as enzyme from 'enzyme';
import * as Adapter from 'enzyme-adapter-react-16';
import {water, WaterDef} from "../water";
import SourceWaters from "./SourceWaters";
import {Provider} from "react-redux";
import {createStore} from "redux";
import reducers from "../reducers";
import defaultState from "../index";
enzyme.configure({ adapter: new Adapter() });
import {createMockStore} from 'redux-test-utils';
import shallowWithStore from "../shallowWithStore";
import Rabdo from "../containers/Rabdo";

it('sources', () => {
    const store = createMockStore(defaultState);
    let sources: Array<WaterDef> = [ water("water1"), water("water2") ];
    const removeSources = (idx: number) => {};
    const sourceChanged = (idx: number, w:WaterDef) => {};
    let wrapper = shallow(<SourceWaters sources={sources} removeSource={removeSources} sourceChanged={sourceChanged}/>);
    expect(wrapper.find('.waterPanel')).toHaveLength(2);
});


it('full integration', () => {
    // const component = shallowWithStore(<Rabdo />, store);
    // expect(component.find('.waterPanel')).toHaveLength(0);
});
