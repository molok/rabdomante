import * as React from 'react';
import './App.css';
import {ChangeEvent, Component} from "react";
import {State} from "./index";
import {connect} from "react-redux";
import {
    ControlLabel, Form, FormControl, FormGroup, Col, Row, PageHeader, Panel, Button,
    PanelGroup} from "react-bootstrap";
import './Rabdo.css'
import {addSource, calculate, sourceChanged, targetChanged} from "./actions";
import {water, WaterDef} from "./water";

interface RabdoProps {
    target: WaterDef
    sources: Array<WaterDef>
    submit: () => void
    addWater: (w: WaterDef) => void
    sourceChanged: (idx: number, w: WaterDef) => void
    targetChanged: (w: WaterDef) => void
}

class XRabdo extends Component<RabdoProps, {}> {
    render() {
        return (
            <div className="Rabdo container">
                <PageHeader>
                    Rabdomante <small>Water Profile Calculator</small>
                </PageHeader>
                <Form horizontal>
                    <FormGroup>
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
                                    {this.mineralForm(this.props.target, this.targetChanged.bind(this))}
                                </FormGroup>
                            </Panel.Body>
                        </Panel>

                        {this.renderWaters()}

                        <Button bsSize="small" onClick={this.addWater.bind(this)}>Aggiungi sorgente</Button>
                    </FormGroup>

                    <FormGroup>
                        <Button type="submit" bsStyle="primary" >Calcola la combinazione migliore</Button>
                    </FormGroup>
                </Form>
            </div>
        )
    }

    addWater() {
        this.props.addWater(water());
    }

    togglePanel(idx: number) {
        this.props.sourceChanged(
            idx,
            { ...this.props.sources[idx], visible: !this.props.sources[idx].visible }
        );
    }

    isExpanded(idx: number) {
        // TODO
        return true;
    }

    sourceChanged(idx: number, attrName: string, e: React.ChangeEvent<HTMLInputElement>) {
        this.props.sourceChanged(idx, { ...this.props.sources[idx], [attrName]: e.target.value });
    }

    targetChanged(attrName: string, e: React.ChangeEvent<HTMLInputElement>) {
        this.props.targetChanged({ ...this.props.target, [attrName]: e.target.value });
    }

    renderWaters() {
        let watersPanel: JSX.Element[] = this.props.sources
                     .map((w, idx) =>

             // TODO capire differenza tra onToggle, onClick
            <Panel key={idx} eventKey={idx}
                   expanded={this.props.sources[idx].visible}>
                <Panel.Heading onClick={this.togglePanel.bind(this, idx)}>
                    <Panel.Title toggle>{w.name || "Sorgente #" + (idx + 1)}</Panel.Title>
                </Panel.Heading>
                <Panel.Body collapsible>
                    <FormGroup>
                        <Row>
                            <Col componentClass={ControlLabel} sm={2}>Nome</Col>
                            <Col sm={4}>
                                <FormControl name="name" bsSize="small" type="text" placeholder=""
                                             value={w.name||""}
                                             onChange={this.sourceChanged.bind(this, idx, "name")}/>
                            </Col>
                            <MineralInput
                                label="litri" symbol=""
                                value={this.props.sources[idx].l}
                                onChange={this.sourceChanged.bind(this, idx, "l")} />
                        </Row>
                    </FormGroup>
                    { this.mineralForm(this.props.sources[idx], this.sourceChanged.bind(this, idx)) }
                </Panel.Body>
            </Panel>
        );

        return (
            <PanelGroup id="source_waters">
                {watersPanel}
            </PanelGroup>
        );
    }

    private mineralForm(water: WaterDef, onChange: (attrName: String, event: ChangeEvent<HTMLInputElement>) => void) {
        return (
        <>
            <FormGroup>
                <Row>
                    <MineralInput label="Calcio" symbol="Ca" value={water.ca} onChange={onChange.bind(this,"ca")} />
                    <MineralInput label="Magnesio" symbol="Mg" value={water.mg} onChange={onChange.bind(this,"mg")} />
                    <MineralInput label="Sodio" symbol="Na" value={water.na} onChange={onChange.bind(this,"na")} />
                </Row>
            </FormGroup>
            <FormGroup>
                <Row>
                    <MineralInput label="solfati" symbol="SO4" value={water.so4} onChange={onChange.bind( "so4")} />
                    <MineralInput label="cloruro" symbol="Cl" value={water.cl} onChange={onChange.bind("cl")} />
                    <MineralInput label="bicarbonati" symbol="HCO3" value={water.hco3} onChange={onChange.bind("hco3")} />
                </Row>
            </FormGroup>
        </>);
    }

    titleCase(str: string) {
        return str.charAt(0).toUpperCase() +
            str.substr(1).toLowerCase();
    }
}

interface MineralInputProps {
    label: string,
    symbol: string,
    value: number,
    onChange: (e: any) => void,
}

class MineralInput extends Component<MineralInputProps, {}> {
    render() {
        let show = this.titleCase(this.props.label) + (this.props.symbol ? " (" + this.props.symbol + ")" : "");
        return (
            <>
                <Col componentClass={ControlLabel} sm={2}>{show}</Col>
                <Col sm={1}> <FormControl value={this.props.value} bsSize="small" type="number" placeholder="" onChange={this.props.onChange}/></Col>
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
        targetChanged: (w: WaterDef) => { dispatch(targetChanged(w))}
    }
}

let Rabdo = connect(
    mapStateToProps,
    mapDispatchToProps
)(XRabdo);

export default Rabdo;
