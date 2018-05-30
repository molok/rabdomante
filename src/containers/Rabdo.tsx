import * as React from 'react';
import {Component} from 'react';
import {State, water, Salt, WaterDef} from "../model/index";
import {connect} from "react-redux";
import {Button, Form, FormGroup, Glyphicon, PageHeader} from "react-bootstrap";
import './Rabdo.css'
import { Actions } from "../actions";
import 'react-select/dist/react-select.css'
import TargetWater from "../components/TargetWater";
import SourceWaters from "../components/SourceWaters";
import Salts from "../components/Salts";

interface RabdoProps {
    target: WaterDef
    sources: Array<WaterDef>
    salts: Array<Salt>
    submit: () => void
    addWater: (w: WaterDef) => void
    sourceChanged: (idx: number, w: WaterDef) => void
    targetChanged: (w: WaterDef) => void
    removeSource: (idx: number) => void
    addSalt: (s: Salt) => void
    saltChanged: (idx: number, s: Salt) => void
    removeSalt: (idx: number) => void
}

class XRabdo extends Component<RabdoProps, {}> {
    render() {
        return (
            <div className="Rabdo container">
                <PageHeader className={"text-center"}>
                    XRabdomante <small>Water Profile Calculator</small>
                </PageHeader>
                <Form horizontal>
                    <FormGroup>
                        <TargetWater target={this.props.target} targetChanged={this.props.targetChanged}/>
                        <SourceWaters sources={this.props.sources} removeSource={this.props.removeSource} sourceChanged={this.props.sourceChanged}/>
                        <Salts salts={this.props.salts} removeSalt={this.props.removeSalt} saltChanged={this.props.saltChanged} />
                        <Button bsSize="small" onClick={this.addWater.bind(this)}><Glyphicon glyph="plus"/> <Glyphicon glyph="tint"/> <Glyphicon glyph="list"/></Button>
                        <Button bsSize="small" onClick={this.addCustomWater.bind(this)}><Glyphicon glyph="plus"/> <Glyphicon glyph="tint"/> <Glyphicon glyph="pencil"/></Button>
                        <Button bsSize="small" onClick={this.addSalt.bind(this)}><Glyphicon glyph="plus"/> <Glyphicon glyph="unchecked"/></Button>
                    </FormGroup>

                    <FormGroup>
                        {/*<RenderSalts salts={this.props.salts} saltChanged={this.saltsChanged.bind(this)} />*/}
                    </FormGroup>

                    <FormGroup>
                        <Button type="submit" bsStyle="primary" ><Glyphicon glyph="play"/> Calcola la combinazione migliore</Button>
                    </FormGroup>
                </Form>
            </div>
        )
    }

    addSalt(e: any) {
        e.preventDefault();
        let s:Salt = {
            name: "",
            g: 0,
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
    // saltsChanged(e: any) {
    //     console.log("event:", e);
    //     let salts = e.map((sel: any) => sel.value);
    //     this.props.saltSelectionChanged(salts);
    // }
}

function mapStateToProps (state: State) {
    return state;
}
function mapDispatchToProps (dispatch: Function) {
    return {
        submit: () => { dispatch(Actions.calculate())},
        addWater: (w :WaterDef) => { dispatch(Actions.addWater(w))},
        sourceChanged: (idx: number, w: WaterDef) => { dispatch(Actions.changedWater(idx, w))},
        targetChanged: (w: WaterDef) => { dispatch(Actions.targetChanged(w))},
        removeSource: (idx: number) => { dispatch(Actions.removeWater(idx))},
        addSalt: (s: Salt) => { dispatch(Actions.addSalt(s))},
        saltChanged: (idx: number, s: Salt) => { dispatch(Actions.changedSalt(idx, s))},
        removeSalt: (idx: number) => { dispatch(Actions.removeSalt(idx))},
    }
}

let Rabdo = connect(
    mapStateToProps,
    mapDispatchToProps
)(XRabdo);

export default Rabdo;
