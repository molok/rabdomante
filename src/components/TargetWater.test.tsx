import MineralInput from "./MineralInput";
import {create} from "react-test-renderer";
import * as React from "react";
import * as renderer from 'react-test-renderer';
import {shallow} from 'enzyme';
import * as enzyme from 'enzyme';
import * as Adapter from 'enzyme-adapter-react-16';
import MineralForm from "./MineralForm";
import TargetWater from "./TargetWater";
import {water} from "../model/index";
import {WaterUi} from "../model";
enzyme.configure({ adapter: new Adapter() });


it('target', () => {
    let start = water("mytarget");
    const targetChanged = (w: WaterUi) => { console.log("ricevuto ", w)};
    let wrapper = shallow(<TargetWater target={start} targetChanged={targetChanged}/>);
    // wrappter
});