import * as React from 'react';
import './App.css';
import {ChangeEvent, Component} from "react";
import {Salt, State} from "./index";
import {connect} from "react-redux";
import {
    ControlLabel, Form, FormControl, FormGroup, Col, Row, PageHeader, Panel, Button,
    PanelGroup, Glyphicon, Checkbox
} from "react-bootstrap";
import './Rabdo.css'
import {
    addSource, calculate, removeSource, saltSelectionChanged, sourceChanged,
    targetChanged
} from "./actions";
import {water, WaterDef} from "./water";
import Select from 'react-select';
import 'react-select/dist/react-select.css'

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

interface TargetWaterProps {
    target: WaterDef
    targetChanged: (w: WaterDef) => void
}

class TargetWater extends Component<TargetWaterProps, {}> {
    render() {
        return (
            <Panel>
                <Panel.Heading>
                    <Panel.Title>Obiettivo</Panel.Title>
                </Panel.Heading>
                <Panel.Body>
                    <FormGroup>
                        <FormGroup>
                            <Row>
                                <MineralInput
                                    label="litri" symbol=""
                                    value={this.props.target.l}
                                    onChange={this.targetChanged.bind(this, "l")} />
                            </Row>
                        </FormGroup>
                        <MineralForm water={this.props.target} attrChanged={this.targetChanged.bind(this)}/>
                    </FormGroup>
                </Panel.Body>
            </Panel>
            )
    }

    targetChanged(attrName: string, value: number) {
        this.props.targetChanged({ ...this.props.target, [attrName]: value });
    }
}

interface MineralFormProps {
    water: WaterDef
    attrChanged: (attrName: string, value: number) => void
}

class MineralForm extends Component<MineralFormProps, {}> {
    render() {
        return (
            <>
            <FormGroup>
                <Row>
                    <MineralInput label="Calcio" symbol="Ca" value={this.props.water.ca} onChange={this.props.attrChanged.bind(this.props, "ca")} />
                    <MineralInput label="Magnesio" symbol="Mg" value={this.props.water.mg} onChange={this.props.attrChanged.bind(this.props, "mg")} />
                    <MineralInput label="Sodio" symbol="Na" value={this.props.water.na} onChange={this.props.attrChanged.bind(this.props, "na")} />
                </Row>
            </FormGroup>
            <FormGroup>
            <Row>
                <MineralInput label="solfati" symbol="SO4" value={this.props.water.so4} onChange={this.props.attrChanged.bind(this.props, "so4")} />
                <MineralInput label="cloruro" symbol="Cl" value={this.props.water.cl} onChange={this.props.attrChanged.bind(this.props, "cl")} />
                <MineralInput label="bicarbonati" symbol="HCO3" value={this.props.water.hco3} onChange={this.props.attrChanged.bind(this.props, "hco3")} />
            </Row>
            </FormGroup>
            </>
        );
    }
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
                        <Button bsSize="small" onClick={this.addWater.bind(this)}><Glyphicon glyph="plus"/>Aggiungi acqua base</Button>
                    </FormGroup>

                    <FormGroup>
                        {this.renderSalts()}
                    </FormGroup>

                    <FormGroup>
                        <Button type="submit" bsStyle="primary" ><Glyphicon glyph="play"/>Calcola la combinazione migliore</Button>
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

    renderSalts() {
        let options = this.props.salts.map(salt => ({ label: salt.name, value: salt.name}));
        let value = this.props.salts
                        .filter(salt => salt.selected)
                        .map( salt => ({ label: salt.name, value: salt.name}));
        return (
            <Select multi value={value} options={options} onChange={this.saltsChanged.bind(this)}></Select>
        )
    }
}

interface SourceWatersProps {
    sources: Array<WaterDef>
    removeSource: (idx: number) => void
    sourceChanged: (idx: number, w: WaterDef) => void
}

class SourceWaters extends Component<SourceWatersProps, {}> {
    sourceChanged(idx: number, attrName: string, e: React.ChangeEvent<HTMLInputElement>) {
        console.log("sourceChanged!");
        e.stopPropagation();
        this.props.sourceChanged(idx, { ...this.props.sources[idx], [attrName]: e.target.value });
    }

    removeSource(idx: number, e: any) {
        e.preventDefault();
        e.stopPropagation();
        this.props.removeSource(idx);
    }

    render() {
        // this.props.sources.forEach(w => { console.log("wwwater:", w)});
        let res =  this.props.sources
            .map((w: WaterDef, idx: number) => {
                console.log("xwater:", w, "litri:", w.l, "idx:", idx);
                    return (
                        <Panel key={idx} eventKey={idx}
                               expanded={this.props.sources[idx].visible}>
                            <Panel.Heading onClick={this.togglePanel.bind(this, idx)}>
                                <Panel.Title toggle className="clearfix">{w.name || "Acqua base #" + (idx + 1)}
                                    <span className={"pull-right"}>
                                <Button bsSize="xsmall" onClick={this.removeSource.bind(this, idx)}><Glyphicon
                                    glyph="remove"/></Button>
                            </span>
                                </Panel.Title>
                            </Panel.Heading>
                            <Panel.Body collapsible>
                                <FormGroup>
                                    <Row>
                                        <Col componentClass={ControlLabel} sm={2}>Nome</Col>
                                        <Col sm={4}>
                                            <FormControl name="name" bsSize="small" type="text" placeholder=""
                                                         value={w.name}
                                                         onChange={this.sourceChanged.bind(this, idx, "name")}/>
                                        </Col>
                                        <MineralInput
                                            label="litri" symbol=""
                                            value={w.l}
                                            onChange={this.attrChanged.bind(this, idx, "l")}/>
                                    </Row>
                                </FormGroup>
                                <MineralForm water={w} attrChanged={this.attrChanged.bind(this, idx)}/>
                            </Panel.Body>
                        </Panel>
                    )
                }
            );

        return (
            <PanelGroup id="source_waters">
                {res}
            </PanelGroup>
        );
    }

    attrChanged(idx: number, attrName: string, value: any) {
        this.props.sourceChanged(idx, { ...this.props.sources[idx], [attrName]: value});
    }

    togglePanel(idx: number, e: any) {
        e.preventDefault();
        this.attrChanged(idx, "visible", !this.props.sources[idx].visible);
    }
}

interface MineralInputProps {
    label: string,
    symbol: string,
    value: number,
    onChange: (e: number) => void,
}

class MineralInput extends Component<MineralInputProps, {}> {
    changed(e: ChangeEvent<HTMLInputElement>) {
        this.props.onChange(parseFloat(e.target.value));
    }

    render() {
        let show = this.titleCase(this.props.label) + (this.props.symbol ? " (" + this.props.symbol + ")" : "");
        return (
            <>
                <Col componentClass={ControlLabel} sm={2}>{show}</Col>
                <Col sm={1}> <FormControl value={this.props.value} bsSize="small" type="number" placeholder="" onChange={this.changed.bind(this)}/></Col>
            </>
        )
    }
    titleCase(str: string) {
        return str.charAt(0).toUpperCase() +
            str.substr(1).toLowerCase();
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
