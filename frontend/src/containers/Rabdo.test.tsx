import {Water, WaterUi} from "../model";

it('render', () => {
    let waters = [{
    ca: 26,
    cl : 0,
    custom : true,
    hco3 : 140,
    l : 1,
    mg : 12,
    na : 0,
    name : "ABRAU",
    so4 : 0,
    visible : false
    }]
    let ws = waters.map((w) => wToUi(w));
    console.log(ws);
});

const wToUi = (w: Water): WaterUi => { return {...w, visible: true, custom: true} }
