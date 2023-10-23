import * as React from 'react';
import {Component, Fragment} from 'react';
import {State, water, SaltUi, WaterUi, Result} from "../model/index";
import {connect} from "react-redux";
import {Button, Form, FormGroup, Glyphicon, PageHeader, Badge} from "react-bootstrap";
import './Rabdo.css'
import { Actions } from "../actions";
import 'react-select/dist/react-select.css'
import TargetWater from "../components/TargetWater";
import Waters from "../components/Waters";
import Salts from "../components/Salts";
import {SolutionWaters} from "../components/SolutionWaters";
import {SolutionSalts} from "../components/SolutionSalts";
import {BottomAnchor} from "../components/BottomAnchor";
import * as ButtonToolbar from "react-bootstrap/lib/ButtonToolbar";
import * as Alert from "react-bootstrap/lib/Alert";
import {translate, TranslateConf} from "../components/Translate";
import msg, {FlagEn, FlagIta} from "../i18n/msg";

interface RabdoProps {
    target: WaterUi
    sources: Array<WaterUi>
    salts: Array<SaltUi>
    result: Result
    findRecipe: (waters: Array<WaterUi>, salts: Array<SaltUi>, target: WaterUi) => void
    addWater: (w: WaterUi) => void
    sourceChanged: (idx: number, w: WaterUi) => void
    sourceEndFocus: () => void
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
    changeLang: (lang: string) => void
    lang: string
}

class XRabdo extends Component<RabdoProps, {}> {
    renderSolution(result: Result) {
        if (result.solution) {
            let solution = result.solution;
            let recipe = solution.recipe.recipe ? [{...solution.recipe.recipe, visible: true}] : [];
            let delta = solution.recipe.delta ? [{...solution.recipe.delta, visible: true}] : [];
            let incomplete  = solution.searchCompleted ? <></> :
                              <Alert bsStyle="warning">
                                  <p>Search was interrupted for taking too long, the result might no be optimal</p>
                              </Alert>;
            return (
                <>
                {incomplete}
                <h3>{translate(msg.solutionIngredients)}</h3>
                    <h4>{translate(msg.infoSolutionIngredients)}</h4>
                <SolutionWaters waters={solution.recipe.waters} changedWater={this.props.resultWaterChanged}/>
                <SolutionSalts salts={solution.recipe.salts} saltChanged={this.props.resultSaltChanged}/>
                <h3>{translate(msg.totals)}</h3>
                <h4>{translate(msg.infoTotal)}</h4>
                <SolutionWaters skipName waters={recipe} changedWater={this.props.recipeWaterChanged}/>
                <h4>{translate(msg.infoDelta)}</h4>
                <SolutionWaters skipName skipQty waters={delta} changedWater={this.props.deltaWaterChanged}/>
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
        let buttonMsg = this.props.result.running ? translate(msg.searching) : translate(msg.find);

        return (
            <div className="Rabdo container">
                <PageHeader className={"text-center"}>
                    <img style={{verticalAlign: "middle"}} width="40" src={"./images/logo.png"}/>
                    <span style={{verticalAlign: "middle"}}>Rabdomante </span>
                    <span><small>{translate(msg.subtitle)}</small></span>
                    <span className="pull-right" >
                        <span style={{paddingRight: 5}}>
                            <FlagIta onClick={this.props.changeLang.bind(this, "it")} selected={this.props.lang === "it"}/>
                        </span>
                        <span>
                            <FlagEn onClick={this.props.changeLang.bind(this, "en")} selected={this.props.lang === "en"}/>
                        </span>
                    </span>
                </PageHeader>
                <Form horizontal style={{marginLeft: '15px', marginRight: '15px'}}>
                    <FormGroup>
                        <h4>{translate(msg.intro)}</h4>
                    </FormGroup>

                    <FormGroup>
                        <h3>{translate(msg.target)}</h3>
                        <h4><Badge>1</Badge>  {translate(msg.intro1)}<a href={"https://www.brunwater.com"}>Bru'n Water</a></h4>
                        <h4>{translate(msg.intro1b)}</h4>
                        <TargetWater target={this.props.target} targetChanged={this.props.targetChanged} enoughLiters={this.validLiters()}/>
                    </FormGroup>
                    <FormGroup>
                        <h3>{translate(msg.availableWaters)}</h3>
                        <h4><Badge>2</Badge>  {translate(msg.intro2)}</h4>
                        <Waters waters={this.props.sources}
                                removeWater={this.props.removeSource}
                                changedWater={this.props.sourceChanged}
                                enoughLiters={this.validLiters()}
                                sourceEndFocus={this.props.sourceEndFocus}
                                supportInfinite
                        />
                        {TranslateConf.usrLang === "it" ?
                            <Button bsStyle={'info'} bsSize="small" onClick={this.addWater.bind(this)} style={{marginRight: '1rem', marginBottom: '1rem'}}><Glyphicon glyph="plus"/>
                                <Glyphicon glyph="tint"/> {translate(msg.addWater)}</Button>
                            : <Fragment/>
                        }
                        <Button bsStyle={'info'} bsSize="small" onClick={this.addCustomWater.bind(this)} style={{marginBottom: '1rem'}}><Glyphicon glyph="plus"/> <Glyphicon glyph="tint"/> {translate(msg.addCustomWater)}</Button>
                        <h3>{translate(msg.availableSalts)}</h3>
                        <h4><Badge>3</Badge>  {translate(msg.intro3)}</h4>
                        <Salts supportInfinite salts={this.props.salts} removeSalt={this.props.removeSalt} saltChanged={this.props.saltChanged} />
                        <Button bsStyle={'info'} bsSize="small" onClick={this.addSalt.bind(this)}><Glyphicon glyph="plus"/> <Glyphicon glyph="th-large"/> {translate(msg.addSalt)}</Button>
                    </FormGroup>

                    <FormGroup>
                        <h4><Badge>4</Badge>  {translate(msg.intro4)}</h4>
                        <ButtonToolbar>
                            <Button type="submit" bsStyle="primary"  onClick={this.findRecipe.bind(this)}><Glyphicon glyph="play"/> {buttonMsg}</Button>
                            <Button className="pull-right" bsStyle="danger" onClick={this.clearState.bind(this)}><Glyphicon glyph="repeat"/> {translate(msg.clearAll)}</Button>
                        </ButtonToolbar>
                    </FormGroup>

                    <FormGroup>
                        {resultComponent}
                        <BottomAnchor toggleScroll={this.props.toggleScrollToSolution} shouldScrollToBottom={this.props.result.shouldScrollHere}/>
                        <h5>{translate(msg.intro5)}</h5>
                    </FormGroup>
                </Form>
                <div style={{textAlign: "center"}}>
                    <small>{"Copyright \u00a9 2018-2020 "}<a href={"mailto:rabdo@alebolo.33mail.com"}>Alessio Bolognino</a></small>
                </div>
            </div>
        )
    }

    clearState(e: any){
        e.preventDefault();
        this.props.clearState();
    }

    findRecipe(e: any) {
        e.preventDefault();
        this.props.sourceEndFocus()
        this.props.findRecipe(this.props.sources, this.props.salts, this.props.target);
    }

    addSalt(e: any) {
        e.preventDefault();
        let s:SaltUi = {
            name: "",
            g: Number.MAX_SAFE_INTEGER,
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
        sourceEndFocus: () => { dispatch(Actions.sourceEndFocus())},
        targetChanged: (w: WaterUi) => { dispatch(Actions.targetChanged(w))},
        waterProfileChanged: (w: WaterUi) => { dispatch(Actions.targetChanged(w))},
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
        clearState: () => { dispatch(Actions.clearState())},
        changeLang: (lang: string) =>  {
            let xlang = lang.toLowerCase();
            dispatch(Actions.changeLang(xlang === "gb" ? "en" : xlang))
        },
    }
}

let Rabdo = connect(
    mapStateToProps,
    mapDispatchToProps
)(XRabdo);

export default Rabdo;
