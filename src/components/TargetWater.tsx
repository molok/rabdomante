import * as React from 'react';
import {Component} from 'react';
import {FormGroup, Glyphicon, Panel, PanelGroup, Row} from "react-bootstrap";
import {WaterDef} from "../model/index";
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
                    <Panel.Title ><Glyphicon glyph="flag"/> Obiettivo</Panel.Title>
                </Panel.Heading>
                <Panel.Body>
                    <FormGroup>
                        <FormGroup>
                            <Row>
                                <MineralInput
                                    label="litri" symbol="L"
                                    value={this.props.target.l}
                                    onChange={this.targetChanged.bind(this, "l")}
                                    editable={true}
                                />
                            </Row>
                        </FormGroup>
                        <MineralForm
                            water={this.props.target}
                            attrChanged={this.targetChanged.bind(this)}
                            editable={true}
                        />
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