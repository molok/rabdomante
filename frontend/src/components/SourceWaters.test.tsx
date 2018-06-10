import 'jsdom-global/register';
import * as React from "react";
import {mount, shallow} from 'enzyme';
import * as enzyme from 'enzyme';
import * as Adapter from 'enzyme-adapter-react-16';
import {water} from "../model/index";
import Waters from "./Waters";
import {Provider} from "react-redux";
enzyme.configure({ adapter: new Adapter() });
import {WaterUi} from "../model";

it('sources', () => {
    let sources: Array<WaterUi> = [ water("water1"), water("water2") ];
    const removeSources = (idx: number) => {};
    const sourceChanged = (idx: number, w:WaterUi) => {};
    let wrapper = shallow(<Waters waters={sources} removeWater={removeSources} changedWater={sourceChanged} enoughLiters={true}/>);
    expect(wrapper.find('.waterPanel')).toHaveLength(2);
});
