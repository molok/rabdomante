import * as React from 'react';
import {Component} from 'react';
import { FormGroup, Row } from "react-bootstrap";
import MineralInput from "./MineralInput";
import {MineralContent} from "../model/index";

interface MineralFormProps {
    water: MineralContent
    attrChanged: (attrName: string, value: number) => void
    editable: boolean
}

class MineralForm extends Component<MineralFormProps, {}> {
    render() {
        return (
            <>
                <FormGroup>
                    <Row>
                        <MineralInput label="Calcio" symbol="Ca" value={this.props.water.ca}
                                      onChange={this.props.attrChanged.bind(this.props, "ca")}
                                      editable={this.props.editable}
                                      minValue={0} />
                        <MineralInput label="Magnesio" symbol="Mg" value={this.props.water.mg}
                                      onChange={this.props.attrChanged.bind(this.props, "mg")}
                                      editable={this.props.editable}
                                      minValue={0} />
                        <MineralInput label="Sodio" symbol="Na" value={this.props.water.na}
                                      onChange={this.props.attrChanged.bind(this.props, "na")}
                                      editable={this.props.editable}
                                      minValue={0} />
                    </Row>
                </FormGroup>
                <FormGroup>
                    <Row>
                        <MineralInput label="solfati" symbol={"SO\u2084\u00B2\u207B"} value={this.props.water.so4}
                                      onChange={this.props.attrChanged.bind(this.props, "so4")}
                                      editable={this.props.editable}
                                      minValue={0} />
                        <MineralInput label="cloruro" symbol={"Cl\u207B"} value={this.props.water.cl}
                                      onChange={this.props.attrChanged.bind(this.props, "cl")}
                                      editable={this.props.editable}
                                      minValue={0} />
                        <MineralInput label="bicarbonati" symbol={"HCO\u2083\u207B"} value={this.props.water.hco3}
                                      onChange={this.props.attrChanged.bind(this.props, "hco3")}
                                      editable={this.props.editable}
                                      minValue={0} />

                    </Row>
                </FormGroup>
            </>
        );
    }
}

export default MineralForm;