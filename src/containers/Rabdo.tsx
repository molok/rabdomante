import * as React from 'react';
import {Component} from 'react';
import {Salt, State} from "../index";
import {connect} from "react-redux";
import {Button, Form, FormGroup, Glyphicon, PageHeader} from "react-bootstrap";
import './Rabdo.css'
import {addSource, calculate, removeSource, saltSelectionChanged, sourceChanged, targetChanged} from "../actions";
import {water, WaterDef} from "../water";
import Select from 'react-select';
import 'react-select/dist/react-select.css'
import TargetWater from "../components/TargetWater";
import SourceWaters from "../components/SourceWaters";

interface RabdoProps {
    target: WaterDef
    sources: Array<WaterDef>
    salts: Array<Salt>
    submit: () => void
    addWater: (w: WaterDef) => void
    sourceChanged: (idx: number, w: WaterDef) => void
    targetChanged: (w: WaterDef) => void
    removeSource: (idx: number) => void
    saltSelectionChanged: (salts: Array<string>) => void
}

const RenderSalts = ({ salts, saltChanged } : { salts: Array<Salt>;
                                                saltChanged: (e: any) => void }) => {
    let options = salts.map(salt => ({ label: salt.name, value: salt.name}));
    let value = salts
        .filter(salt => salt.selected)
        .map( salt => ({ label: salt.name, value: salt.name}));
    return (
        <Select multi value={value} options={options} onChange={saltChanged}></Select>
    )
};

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
                        <Button bsSize="small" onClick={this.addWater.bind(this)}><Glyphicon glyph="plus"/> Aggiungi acqua base</Button>
                    </FormGroup>

                    <FormGroup>
                        <RenderSalts salts={this.props.salts} saltChanged={this.saltsChanged.bind(this)} />
                    </FormGroup>

                    <FormGroup>
                        <Button type="submit" bsStyle="primary" ><Glyphicon glyph="play"/> Calcola la combinazione migliore</Button>
                    </FormGroup>
                </Form>
            </div>
        )
    }

    addWater(e: any) {
        e.preventDefault();
        this.props.addWater(water());
    }

    saltsChanged(e: any) {
        console.log("event:", e);
        let salts = e.map((sel: any) => sel.value);
        this.props.saltSelectionChanged(salts);
    }
}

function mapStateToProps (state: State) {
    return state;
}
function mapDispatchToProps (dispatch: Function) {
    return {
        submit: () => { dispatch(calculate())},
        addWater: (w :WaterDef) => { dispatch(addSource(w))},
        sourceChanged: (idx: number, w: WaterDef) => { dispatch(sourceChanged(idx, w))},
        targetChanged: (w: WaterDef) => { dispatch(targetChanged(w))},
        removeSource: (idx: number) => { dispatch(removeSource(idx))},
        saltSelectionChanged: (salts: Array<string>) => { dispatch(saltSelectionChanged(salts))}
    }
}

let Rabdo = connect(
    mapStateToProps,
    mapDispatchToProps
)(XRabdo);

export default Rabdo;
