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

class MineralInput extends Component {
    static defaultProps = {
        labelClassName: "col-sm-2",
        inputClassName: "col-sm-1",
        symbol: ""
    };

    render() {
        let label = "label";
        let symbol = "symbol";
        return (
            <Fragment>
                <ControlLabel>{label}</ControlLabel>
                <FormControl type="number" placeholder=""/>
                {/*<label className={`col-form-label col-form-label-sm text-right ${this.props.labelClassName}`}>{label} <small>{symbol}</small></label>*/}
                {/*<div className={this.props.inputClassName}>*/}
                    {/*<input required={true}*/}
                           {/*className="form-control form-control-sm"*/}
                           {/*type="number"*/}
                           {/*value={this.props.value||""}*/}
                           {/*placeholder={this.props.placeholder}*/}
                           {/*onChange={this.changed.bind(this)}/>*/}
                {/*</div>*/}
            </Fragment>
        );
    }
}

interface RabdoProps {
    submit: () => void,
    addWater: () => void,
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
            <><Col componentClass={ControlLabel} sm={2}>{show}</Col><Col sm={1}> <FormControl bsSize="small" type="number" placeholder=""/></Col></>
        )
    }


// render2() {
    //     return (
    //         <div className="Rabdo container">
    //             <div className="page-header mb-5">
    //                 <h1>Rabdomante <small>Water Profile Calculator</small></h1>
    //             </div>
    //             <form onSubmit={this.props.submit.bind(this)}>
    //                 <div>
    //                     <div className="form-group">
    //                         <h5>Obiettivo</h5>
    //                         <fieldset className="form-group">
    //                             <div className="form-group row">
    //                                 <MineralInput />
    //                             </div>
    //                             <MineralsForm />
    //                         </fieldset>
    //
    //                         <fieldset className="form-group">
    //                             <h5>Sorgenti</h5>
    //                             <div id="accordion">
    //                                 {this.renderWaters()}
    //                             </div>
    //                         </fieldset>
    //                         <button className="btn btn-secondary btn-sm" type="button" onClick={this.props.addWater.bind(this)}>
    //                             Aggiungi acqua
    //                         </button>
    //                     </div>
    //                 </div>
    //                 <button type="submit" className="btn btn-primary mt-5" onClick={this.props.submit.bind(this)}>Calcola la combinazione migliore</button>
    //             </form>
    //         </div>
    //     )
    // }
}


function mapStateToProps (state: State) { return {}; }
function mapDispatchToProps (dispatch: Function) {
    return {
        submit: () => { dispatch({ type: 'calc'})},
        addWater: () => { dispatch({ type: 'add_water'})}
    }
}

let Rabdo = connect(
    mapStateToProps,
    mapDispatchToProps
)(XRabdo);

export default Rabdo;
