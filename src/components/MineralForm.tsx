import * as React from 'react';
import {Component} from 'react';
import { FormGroup, Row } from "react-bootstrap";
import {WaterDef} from "../water";
import MineralInput from "./MineralInput";

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
                        <MineralInput label="Calcio" symbol="Ca" value={this.props.water.ca}
                                      onChange={this.props.attrChanged.bind(this.props, "ca")}/>
                        <MineralInput label="Magnesio" symbol="Mg" value={this.props.water.mg}
                                      onChange={this.props.attrChanged.bind(this.props, "mg")}/>
                        <MineralInput label="Sodio" symbol="Na" value={this.props.water.na}
                                      onChange={this.props.attrChanged.bind(this.props, "na")}/>
                    </Row>
                </FormGroup>
                <FormGroup>
                    <Row>
                        <MineralInput label="solfati" symbol="SO4" value={this.props.water.so4}
                                      onChange={this.props.attrChanged.bind(this.props, "so4")}/>
                        <MineralInput label="cloruro" symbol="Cl" value={this.props.water.cl}
                                      onChange={this.props.attrChanged.bind(this.props, "cl")}/>
                        <MineralInput label="bicarbonati" symbol="HCO3" value={this.props.water.hco3}
                                      onChange={this.props.attrChanged.bind(this.props, "hco3")}/>
                    </Row>
                </FormGroup>
            </>
        );
    }
}

export default MineralForm;