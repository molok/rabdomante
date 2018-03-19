import * as React from 'react';
import './App.css';
import {ChangeEvent, Component} from "react";
import {State} from "./index";
import {connect} from "react-redux";
import {
    ControlLabel, Form, FormControl, FormGroup, Col, Row, PageHeader, Panel, Button,
    PanelGroup} from "react-bootstrap";
import './Rabdo.css'
import {addSource, calculate, sourceChanged} from "./actions";
import {water, WaterDef} from "./water";

interface RabdoProps {
    target: WaterDef
    sources: Array<WaterDef>
    submit: () => void
    addWater: (w: WaterDef) => void
    sourceChanged: (idx: number, w: WaterDef) => void
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
                                        <Row>{this.mineralInput("litri")}</Row>
                                    </FormGroup>
                                    {this.mineralForm()}
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

    sourceChanged(idx: number, e: any) {
        this.props.sourceChanged(
            idx,
            { ...this.props.sources[idx], name: e.target.value }
        )
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
                                <FormControl bsSize="small" type="text" placeholder="" value={w.name} onChange={this.sourceChanged.bind(this, idx)}/>
                            </Col>
                            {this.mineralInput("litri")}
                        </Row>
                    </FormGroup>
                    {this.mineralForm()}
                </Panel.Body>
            </Panel>
        );

        return (
            <PanelGroup id="source_waters">
                {watersPanel}
            </PanelGroup>
        );
    }

    private mineralForm() {
        return (
            <>
                <FormGroup>
                    <Row>
                        {this.mineralInput("Calcio", "Ca")}
                        {this.mineralInput("Magnesio", "Mg")}
                        {this.mineralInput("Sodio", "Na")}
                    </Row>
                </FormGroup>
                <FormGroup>
                    <Row>
                        {this.mineralInput("solfati", "SO4")}
                        {this.mineralInput("cloruro", "Cl")}
                        {this.mineralInput("bicarbonati", "HCO3")}
                    </Row>
                </FormGroup>
            </>);
    }

    titleCase(str: string) {
        return str.charAt(0).toUpperCase() +
            str.substr(1).toLowerCase();
    }

    mineralInput(label: string, symbol: string = "") {
        let show = this.titleCase(label) + (symbol ? " (" + symbol + ")" : "");
        return (
            <>
              <Col componentClass={ControlLabel} sm={2}>{show}</Col>
              <Col sm={1}> <FormControl bsSize="small" type="number" placeholder=""/></Col>
            </>
        )
    }

}

function mapStateToProps (state: State) {
    return state;
}
function mapDispatchToProps (dispatch: Function) {
    return {
        submit: () => { dispatch(calculate())},
        addWater: (w :WaterDef) => { dispatch(addSource(w))},
        sourceChanged: (idx: number, w: WaterDef) => { dispatch(sourceChanged(idx, w))}
    }
}

let Rabdo = connect(
    mapStateToProps,
    mapDispatchToProps
)(XRabdo);

export default Rabdo;
