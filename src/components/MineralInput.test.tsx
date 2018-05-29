import MineralInput from "./MineralInput";
import {create} from "react-test-renderer";
import * as React from "react";
import * as renderer from 'react-test-renderer';
import {shallow} from 'enzyme';

it("diofa", () => {
    let callback = (e: any) => { console.log("chiamato!")};
    const component = renderer.create(
        <MineralInput label="mylabel" symbol="symbol" value={666} onChange={callback} editable={true} />
    );
    // console.log(component.toJSON());
    console.log(component.toTree());
});
