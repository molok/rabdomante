import {titleCase} from "../components/utils";

const KNOWN_TARGET_WATERS = [
    {name: '--'          , ca: 0 , mg: 0 , na: 0 , so4: 0 , cl: 0 , hco3: 0},
    {name: 'Pale Ale'        , ca: 140 , mg: 18 , na: 25 , so4: 300 , cl: 55 , hco3: 110},
    {name: 'Mild Ale'        , ca: 50  , mg: 0  , na: 20 , so4: 40  , cl: 65 , hco3: 45},
    {name: 'American Lager'  , ca: 13  , mg: 6  , na: 8  , so4: 37  , cl: 13 , hco3: 20},
    {name: 'Yellow Full'     , ca: 50  , mg: 5  , na: 5  , so4: 55  , cl: 70 , hco3: 0},
    {name: 'Yellow Balanced' , ca: 50  , mg: 7  , na: 5  , so4: 75  , cl: 60 , hco3: 0},
    {name: 'Yellow Dry'      , ca: 50  , mg: 10 , na: 5  , so4: 105 , cl: 45 , hco3: 0},
    {name: 'Amber Full'      , ca: 50  , mg: 5  , na: 15 , so4: 55  , cl: 65 , hco3: 35},
    {name: 'Amber Balanced'  , ca: 50  , mg: 10 , na: 15 , so4: 75  , cl: 63 , hco3: 40},
    {name: 'Amber Dry'       , ca: 50  , mg: 15 , na: 15 , so4: 110 , cl: 50 , hco3: 45},
    {name: 'Brown Full'      , ca: 50  , mg: 5  , na: 27 , so4: 50  , cl: 60 , hco3: 85},
    {name: 'Brown Balanced'  , ca: 50  , mg: 10 , na: 27 , so4: 70  , cl: 55 , hco3: 90},
    {name: 'Brown Dry'       , ca: 50  , mg: 15 , na: 27 , so4: 99  , cl: 45 , hco3: 95},
    {name: 'Black Full'      , ca: 50  , mg: 5  , na: 33 , so4: 35  , cl: 45 , hco3: 140},
    {name: 'Black Balanced'  , ca: 50  , mg: 10 , na: 33 , so4: 57  , cl: 44 , hco3: 142},
    {name: 'Black Dry'       , ca: 50  , mg: 15 , na: 33 , so4: 84  , cl: 39 , hco3: 145},

];

interface KWater {
    name: string; ca: number; mg: number; na: number; so4: number; cl: number; hco3: number
}

const clean = (x: number):number => {
    return Math.max(Math.round(x), 0);
}

const sanitize = (x: KWater):KWater => {
    return {
        name: titleCase(x.name),
        ca: clean(x.ca),
        mg: clean(x.mg),
        na: clean(x.na),
        so4: clean(x.so4),
        cl: clean(x.cl),
        hco3: clean(x.hco3)
    }
}

export default KNOWN_TARGET_WATERS.map(w => sanitize(w));
