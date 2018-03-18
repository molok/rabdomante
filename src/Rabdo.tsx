import * as React from 'react';
import './App.css';
import {Component, Fragment} from "react";
import {State} from "./index";
import {connect} from "react-redux";
import {
    ControlLabel, Form, FormControl, FormGroup, Label, Col, Row, PageHeader, Panel, Button,
    PanelGroup, ButtonToolbar
} from "react-bootstrap";
import './Rabdo.css'
import {addSource, calculate} from "./actions";
import {WaterDef} from "./water";

interface RabdoProps {
    target: WaterDef
    sources: Array<WaterDef>
    submit: () => void
    addWater: (w: WaterDef) => void
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

                        <Button bsSize="small">Aggiungi sorgente</Button>
                    </FormGroup>

                    <FormGroup>
                        <Button type="submit" bsStyle="primary" >Calcola la combinazione migliore</Button>
                    </FormGroup>
                </Form>
            </div>
        )
    }

    togglePanel(idx: number) {
        // TODO
    }

    isExpanded(idx: number) {
        // TODO
        return true;
    }

    renderWaters() {
        let watersPanel: JSX.Element[] = ["boario", "sant'anna"]
                     .map((w, idx) =>
            <Panel eventKey={idx} expanded={true} /*expanded={this.isExpanded.bind(this, idx)}*/ onClick={this.togglePanel.bind(this, idx)}>
                <Panel.Heading>
                    <Panel.Title toggle>Sorgente #{idx}</Panel.Title>
                </Panel.Heading>
                <Panel.Body collapsible>
                    <FormGroup>
                        <Row>
                            <Col componentClass={ControlLabel} sm={2}>Nome</Col>
                            <Col sm={4}>
                                <FormControl bsSize="small" type="text" placeholder=""/>
                            </Col>
                            {this.mineralInput("litri")}
                        </Row>
                    </FormGroup>
                    {this.mineralForm()}
                </Panel.Body>
            </Panel> );
        return (
            <PanelGroup accordion>
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
        addWater: (w :WaterDef) => { dispatch(addSource(w))}
    }
}

let Rabdo = connect(
    mapStateToProps,
    mapDispatchToProps
)(XRabdo);

export default Rabdo;
