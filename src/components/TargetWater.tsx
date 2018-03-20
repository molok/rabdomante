import * as React from 'react';
import {Component} from 'react';
import { FormGroup, Panel, Row } from "react-bootstrap";
import {WaterDef} from "../water";
import MineralInput from "./MineralInput";
import MineralForm from "./MineralForm";

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
                                    onChange={this.targetChanged.bind(this, "l")}/>
                            </Row>
                        </FormGroup>
                        <MineralForm water={this.props.target} attrChanged={this.targetChanged.bind(this)}/>
                    </FormGroup>
                </Panel.Body>
            </Panel>
        )
    }

    targetChanged(attrName: string, value: number) {
        this.props.targetChanged({...this.props.target, [attrName]: value});
    }
}

export default TargetWater;