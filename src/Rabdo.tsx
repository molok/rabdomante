import * as React from 'react';
import './App.css';
import {Component, Fragment} from "react";
import {State} from "./index";
import {connect} from "react-redux";
import {ControlLabel, Form, FormControl, FormGroup, Label, Col, Row} from "react-bootstrap";
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
    renderWaters() {
        return (<div></div>)
    }

    titleCase(str: string) {
        return str.charAt(0).toUpperCase() +
               str.substr(1).toLowerCase();
    }

    formInput(label: string, symbol: string = "") {
        let show = this.titleCase(label) + (symbol ? " (" + symbol + ")" : "");
        return (
            <><Col componentClass={ControlLabel} sm={2}>{show}</Col><Col sm={1}> <FormControl bsSize="small" type="number" placeholder=""/></Col></>
        )
    }

    render() {
        return (
            <div className="Rabdo container">
                <div className="page-header mb-5">
                    <h1>Rabdomante <small>Water Profile Calculator</small></h1>
                </div>
                <Form horizontal>
                    <h6 className="text-muted form-heading">OBIETTIVO</h6>
                    <FormGroup>
                        <Row>
                            {this.formInput("litri")}
                        </Row>
                    </FormGroup>
                    <FormGroup>
                        <Row>
                            {this.formInput("Calcio", "Ca")}
                            {this.formInput("Magnesio", "Mg")}
                            {this.formInput("Sodio", "Na")}
                        </Row>
                    </FormGroup>
                    <FormGroup>
                        <Row>
                            {this.formInput("solfati", "SO4")}
                            {this.formInput("cloruro", "Cl")}
                            {this.formInput("bicarbonati", "HCO3")}
                        </Row>
                    </FormGroup>
                </Form>
            </div>
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
