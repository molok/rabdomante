import * as React from 'react';
import {Component} from 'react';
import {State, water, SaltUi, WaterUi, CalcResult, Water, Salt, RecipeUi, CalcResultUi} from "../model/index";
import {connect} from "react-redux";
import {Button, Form, FormGroup, Glyphicon, PageHeader} from "react-bootstrap";
import './Rabdo.css'
import { Actions } from "../actions";
import 'react-select/dist/react-select.css'
import TargetWater from "../components/TargetWater";
import Waters from "../components/Waters";
import Salts from "../components/Salts";
import {asyncFindRecipe} from "../Api";
import {SolutionWaters} from "../components/SolutionWaters";
import {SolutionSalts} from "../components/SolutionSalts";

interface RabdoProps {
    target: WaterUi
    sources: Array<WaterUi>
    salts: Array<SaltUi>
    result: { solution: CalcResultUi|null, error: string|null }
    findRecipe: (waters: Array<WaterUi>, salts: Array<SaltUi>, target: WaterUi) => void
    addWater: (w: WaterUi) => void
    sourceChanged: (idx: number, w: WaterUi) => void
    targetChanged: (w: WaterUi) => void
    removeSource: (idx: number) => void
    addSalt: (s: SaltUi) => void
    saltChanged: (idx: number, s: SaltUi) => void
    removeSalt: (idx: number) => void
    resultWaterChanged: (idx: number, w: WaterUi) => void
    resultSaltChanged: (idx: number, s: SaltUi) => void
}

export const wToUi = (w: Water): WaterUi => { return {...w, visible: true, custom: true} };
export const sToUi = (s: Salt): SaltUi => { return {...s, visible: true, custom: true} };

class XRabdo extends Component<RabdoProps, {}> {
    renderSolution(solution: CalcResultUi) {
        return (
            <>
            <SolutionWaters waters={solution.recipe.waters} changedWater={this.props.resultWaterChanged}/>
            <SolutionSalts salts={solution.recipe.salts} saltChanged={this.props.resultSaltChanged}/>
            </>
        )
    }

    render() {
        let resultComponent = this.props.result.solution ? (<>{this.renderSolution(this.props.result.solution)}</>) : (<></>);

        return (
            <div className="Rabdo container">
                <PageHeader className={"text-center"}>Rabdomante <small>Water Profile Calculator</small></PageHeader>
                <Form horizontal>
                    <FormGroup>
                        <TargetWater target={this.props.target} targetChanged={this.props.targetChanged}/>
                    </FormGroup>
                    <FormGroup>
                        <Waters waters={this.props.sources} removeWater={this.props.removeSource} changedWater={this.props.sourceChanged}/>
                        <Salts salts={this.props.salts} removeSalt={this.props.removeSalt} saltChanged={this.props.saltChanged} />
                        <Button bsSize="small" onClick={this.addWater.bind(this)}><Glyphicon glyph="plus"/> <Glyphicon glyph="tint"/> <Glyphicon glyph="list"/></Button>
                        <Button bsSize="small" onClick={this.addCustomWater.bind(this)}><Glyphicon glyph="plus"/> <Glyphicon glyph="tint"/> <Glyphicon glyph="pencil"/></Button>
                        <Button bsSize="small" onClick={this.addSalt.bind(this)}><Glyphicon glyph="plus"/> <Glyphicon glyph="unchecked"/></Button>
                    </FormGroup>

                    <FormGroup>
                        <Button type="submit" bsStyle="primary"  onClick={this.findRecipe.bind(this)}><Glyphicon glyph="play"/> Cerca la combinazione migliore</Button>
                    </FormGroup>

                    <FormGroup>
                        {resultComponent}
                    </FormGroup>
                </Form>
            </div>
        )
    }

    findRecipe(e: any) {
        e.preventDefault();
        this.props.findRecipe(this.props.sources, this.props.salts, this.props.target);
    }

    addSalt(e: any) {
        e.preventDefault();
        let s:SaltUi = {
            name: "",
            dg: 0,
            ca: 0,
            mg: 0,
            na: 0,
            so4: 0,
            cl: 0,
            hco3: 0,
            visible: true,
            custom: false
        };
        this.props.addSalt(s);
    }

    addCustomWater(e: any) {
        e.preventDefault();
        var w = water();
        w.custom = true;
        this.props.addWater(w);
    }

    addWater(e: any) {
        e.preventDefault();
        var w = water();
        w.custom = false;
        this.props.addWater(w);
    }
}

function mapStateToProps (state: State) {
    return state;
}
function mapDispatchToProps (dispatch: Function) {
    return {
        addWater: (w :WaterUi) => { dispatch(Actions.addWater(w))},
        sourceChanged: (idx: number, w: WaterUi) => { dispatch(Actions.changedWater(idx, w))},
        targetChanged: (w: WaterUi) => { dispatch(Actions.targetChanged(w))},
        removeSource: (idx: number) => { dispatch(Actions.removeWater(idx))},
        addSalt: (s: SaltUi) => { dispatch(Actions.addSalt(s))},
        saltChanged: (idx: number, s: SaltUi) => { dispatch(Actions.changedSalt(idx, s))},
        removeSalt: (idx: number) => { dispatch(Actions.removeSalt(idx))},
        findRecipe: (waters: Array<WaterUi>, salts: Array<SaltUi>, target: WaterUi) => {
            asyncFindRecipe(waters, salts, target)
                .then(
                (result: CalcResult) => {
                    if (result && result.recipe) {
                        dispatch(Actions.findRecipeSuccess(
                            {
                                recipe: {
                                    waters: (result.recipe) ? result.recipe.waters.map(w => wToUi(w)) : [],
                                    salts: (result.recipe) ? result.recipe.salts.map(s => sToUi(s)) : [],
                                    distance: (result.recipe) ? result.recipe.distance : 0
                                },
                                searchCompleted: result.searchCompleted
                            }));
                    } else {
                        dispatch(Actions.findRecipeFailure("No solution found!"));
                    }
                })
                .catch(err => dispatch(Actions.findRecipeFailure("No solution found: " + err)));
        },
        resultWaterChanged: (idx: number, w: WaterUi) => { dispatch(Actions.solutionWaterChanged(idx, w))},
        resultSaltChanged: (idx: number, s: SaltUi) => { dispatch(Actions.solutionSaltChanged(idx, s))},
    }
}

let Rabdo = connect(
    mapStateToProps,
    mapDispatchToProps
)(XRabdo);

export default Rabdo;
