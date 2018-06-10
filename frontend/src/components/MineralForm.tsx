import * as React from 'react';
import {Component} from 'react';
import { FormGroup, Row } from "react-bootstrap";
import MineralInput from "./MineralInput";
import {MineralContent} from "../model/index";
import {translate} from "./Translate";
import msg from "../i18n/msg";

interface MineralFormProps {
    water: MineralContent
    attrChanged: (attrName: string, value: number) => void
    editable: boolean
    minValue?: number
}

class MineralForm extends Component<MineralFormProps, {}> {
    public static defaultProps: Partial<MineralFormProps> = {
        minValue: 0
    }

    render() {
        return (
            <>
                <FormGroup>
                    <Row>
                        <MineralInput label={translate(msg.calcium)} symbol="Ca" value={this.props.water.ca}
                                      onChange={this.props.attrChanged.bind(this.props, "ca")}
                                      editable={this.props.editable}
                                      minValue={this.props.minValue} />
                        <MineralInput label={translate(msg.magnesium)} symbol="Mg" value={this.props.water.mg}
                                      onChange={this.props.attrChanged.bind(this.props, "mg")}
                                      editable={this.props.editable}
                                      minValue={this.props.minValue} />
                        <MineralInput label={translate(msg.sodium)} symbol="Na" value={this.props.water.na}
                                      onChange={this.props.attrChanged.bind(this.props, "na")}
                                      editable={this.props.editable}
                                      minValue={this.props.minValue} />
                    </Row>
                </FormGroup>
                <FormGroup>
                    <Row>
                        <MineralInput label={translate(msg.sulfate)} symbol={"SO\u2084\u00B2\u207B"} value={this.props.water.so4}
                                      onChange={this.props.attrChanged.bind(this.props, "so4")}
                                      editable={this.props.editable}
                                      minValue={this.props.minValue} />
                        <MineralInput label={translate(msg.chloride)} symbol={"Cl\u207B"} value={this.props.water.cl}
                                      onChange={this.props.attrChanged.bind(this.props, "cl")}
                                      editable={this.props.editable}
                                      minValue={this.props.minValue} />
                        <MineralInput label={translate(msg.bicarbonates)} symbol={"HCO\u2083\u207B"} value={this.props.water.hco3}
                                      onChange={this.props.attrChanged.bind(this.props, "hco3")}
                                      editable={this.props.editable}
                                      minValue={this.props.minValue} />

                    </Row>
                </FormGroup>
            </>
        );
    }
}

export default MineralForm;