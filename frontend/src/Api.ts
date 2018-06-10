import 'isomorphic-fetch'

import {CalcResult, Recipe, Salt, Water} from "./model";

interface WsWater extends Water { }
interface WsSalt extends Salt { } /* TODO dg -> g & g -> dg */

interface WSInput {
    target: WsWater,
    waters: Array<WsWater>,
    salts: Array<WsSalt>
}

interface WSSolution {
    readonly recipe: Recipe,
    readonly searchCompleted: boolean
}

interface WSResponse {
    input: WSInput,
    output: WSSolution
}

export const MAX_INT_32BIT = 2147483647;

const waterToWs = (water: Water): WsWater => {
    return {
        l: Math.min(water.l, MAX_INT_32BIT),
        name: water.name,
        ca: water.ca,
        mg: water.mg,
        na: water.na,
        so4: water.so4,
        cl: water.cl,
        hco3: water.hco3
    }
}

const saltToWs = (salt: Salt): WsSalt => {
    return {
        dg: Math.min(salt.dg, MAX_INT_32BIT),
        name: salt.name,
        ca: salt.ca,
        mg: salt.mg,
        na: salt.na,
        so4: salt.so4,
        cl: salt.cl,
        hco3: salt.hco3
    }
};

const toWsInput = (waters: Array<Water>, salts: Array<Salt>, target: Water) => {
    return {
        target: waterToWs(target),
        waters: waters.map(w => waterToWs(w)),
        salts: salts.map(s => saltToWs(s))
    };
};

export const asyncFindRecipe = (waters: Array<Water>, salts: Array<Salt>, target: Water):Promise<CalcResult> => {
    let input: WSInput = toWsInput(waters, salts, target);
    console.log("wsinput:", input);
    return fetch('https://m1ust5mpoa.execute-api.eu-west-1.amazonaws.com/Prod/calc', {
        body: JSON.stringify(input),
        method: 'POST',
        })
        .then(resp => resp.text())
        .then(txt => {console.log("API RESPONSE:", txt); return txt;})
        .then(txt => JSON.parse(txt).output)
};
