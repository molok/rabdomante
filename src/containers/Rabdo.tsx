import * as React from 'react';
import {Component} from 'react';
import {State, water, SaltUi, WaterUi, CalcResult, Water, Salt, RecipeUi, CalcResultUi, Result} from "../model/index";
import {connect} from "react-redux";
import {Button, ButtonGroup, Form, FormGroup, Glyphicon, PageHeader} from "react-bootstrap";
import './Rabdo.css'
import { Actions } from "../actions";
import 'react-select/dist/react-select.css'
import TargetWater from "../components/TargetWater";
import Waters from "../components/Waters";
import Salts from "../components/Salts";
import {SolutionWaters} from "../components/SolutionWaters";
import {SolutionSalts} from "../components/SolutionSalts";
import {BottomAnchor} from "../components/BottomAnchor";
import {SaltIcon} from "../components/SaltIcon";
import * as ButtonToolbar from "react-bootstrap/lib/ButtonToolbar";
import * as Alert from "react-bootstrap/lib/Alert";

interface RabdoProps {
    target: WaterUi
    sources: Array<WaterUi>
    salts: Array<SaltUi>
    result: Result
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
    recipeWaterChanged: (idx: number, w: WaterUi) => void
    deltaWaterChanged: (idx: number, w: WaterUi) => void
    toggleScrollToSolution: () => void
    clearState: () => void
}

class XRabdo extends Component<RabdoProps, {}> {
    renderSolution(result: Result) {
        if (result.solution) {
            let solution = result.solution;
            let recipe = solution.recipe.recipe ? [solution.recipe.recipe] : [];
            let delta = solution.recipe.delta ? [solution.recipe.delta] : [];
            let incomplete  = solution.searchCompleted ? <></> :
                              <Alert bsStyle="warning">
                                  <p>Search was interrupted for taking too long, the result might no be optimal</p>
                              </Alert>;
            return (
                <>
                {incomplete}
                <h3>Solution Ingredients</h3>
                <SolutionWaters waters={solution.recipe.waters} changedWater={this.props.resultWaterChanged}/>
                <SolutionSalts salts={solution.recipe.salts} saltChanged={this.props.resultSaltChanged}/>
                <h3>Totals</h3>
                <SolutionWaters waters={recipe} changedWater={this.props.recipeWaterChanged}/>
                <SolutionWaters skipQty waters={delta} changedWater={this.props.deltaWaterChanged}/>
                    <div style={{textAlign: "center"}}>
                    <small>{"Copyright \u00a9 2018 "}<a href={"mailto:rabdo@alebolo.33mail.com"}>Alessio Bolognino</a></small>
                    </div>
                </>
            )
        } else if (result.error) {
            return (
                <Alert bsStyle="danger">
                    <p>Problem: {result.error}</p>
                </Alert>
            )
        } else {
            return <></>
        }
    }

    validLiters(): boolean {
        return (this.props.sources.length > 0 && this.props.sources.map(w => w.l).reduce((a, b) => a+b))
               >= this.props.target.l;
    }

    render() {
        let resultComponent = this.props.result ? (<>{this.renderSolution(this.props.result)}</>) : (<></>);
        let buttonMsg = this.props.result.running ? "Searching for the best combination..." : "Find the best combination";

        return (
            <div className="Rabdo container">
                <PageHeader className={"text-center"}>Rabdomante <small>Water Profile Calculator</small></PageHeader>
                <Form horizontal>
                    <FormGroup>
                        <h3>Target</h3>
                        <TargetWater target={this.props.target} targetChanged={this.props.targetChanged} enoughLiters={this.validLiters()}/>
                    </FormGroup>
                    <FormGroup>
                        <h3>Available Waters</h3>
                        <Waters waters={this.props.sources}
                                removeWater={this.props.removeSource}
                                changedWater={this.props.sourceChanged}
                                enoughLiters={this.validLiters()}
                                supportInfinite
                        />
                        <Button bsSize="small" onClick={this.addWater.bind(this)}><Glyphicon glyph="plus"/> <Glyphicon glyph="tint"/></Button>
                        <Button bsSize="small" onClick={this.addCustomWater.bind(this)}><Glyphicon glyph="plus"/> <Glyphicon glyph="tint"/> Custom</Button>
                        <h3>Available Salts</h3>
                        <Salts supportInfinite salts={this.props.salts} removeSalt={this.props.removeSalt} saltChanged={this.props.saltChanged} />
                        <Button bsSize="small" onClick={this.addSalt.bind(this)}><Glyphicon glyph="plus"/> <SaltIcon fill="#000000"/></Button>
                    </FormGroup>

                    <FormGroup>
                        <ButtonToolbar>
                            <Button type="submit" bsStyle="primary"  onClick={this.findRecipe.bind(this)}><Glyphicon glyph="play"/> {buttonMsg}</Button>
                            <Button className="pull-right" bsStyle="danger" onClick={this.clearState.bind(this)}><Glyphicon glyph="repeat"/> Clear all</Button>
                        </ButtonToolbar>
                    </FormGroup>

                    <FormGroup>
                        {resultComponent}
                        <BottomAnchor toggleScroll={this.props.toggleScrollToSolution} shouldScrollToBottom={this.props.result.shouldScrollHere}/>
                    </FormGroup>
                </Form>
            </div>
        )
    }

    clearState(e: any){
        e.preventDefault();
        this.props.clearState();
    }

    findRecipe(e: any) {
        e.preventDefault();
        this.props.findRecipe(this.props.sources, this.props.salts, this.props.target);
    }

    addSalt(e: any) {
        e.preventDefault();
        let s:SaltUi = {
            name: "",
            dg: Number.MAX_SAFE_INTEGER,
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
        var w = water("", Number.MAX_SAFE_INTEGER);
        w.custom = true;
        this.props.addWater(w);
    }

    addWater(e: any) {
        e.preventDefault();
        var w = water("", Number.MAX_SAFE_INTEGER);
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
        findRecipe: (waters: Array<WaterUi>, salts: Array<SaltUi>, target: WaterUi) => { dispatch(Actions.findRecipe(waters, salts, target)); },
        resultWaterChanged: (idx: number, w: WaterUi) => { dispatch(Actions.solutionWaterChanged(idx, w))},
        resultSaltChanged: (idx: number, s: SaltUi) => { dispatch(Actions.solutionSaltChanged(idx, s))},
        toggleScrollToSolution: () => { dispatch(Actions.toggleScrollToSolution())},
        recipeWaterChanged: (idx: number, w: WaterUi) => {dispatch(Actions.recipeWaterChanged(w))},
        deltaWaterChanged: (idx: number, w: WaterUi) => {dispatch(Actions.deltaWaterChanged(w))},
        clearState: () => { dispatch(Actions.clearState())}
    }
}

let Rabdo = connect(
    mapStateToProps,
    mapDispatchToProps
)(XRabdo);

export default Rabdo;
