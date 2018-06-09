import * as React from 'react';
import {Component} from 'react';
import {FormGroup, Glyphicon, Panel, PanelGroup, Row} from "react-bootstrap";
import {WaterUi} from "../model/index";
import MineralInput from "./MineralInput";
import MineralForm from "./MineralForm";

interface TargetWaterProps {
    target: WaterUi,
    targetChanged: (w: WaterUi) => void,
    enoughLiters: boolean
}

class TargetWater extends Component<TargetWaterProps, {}> {
    render() {
        let qty = (this.props.target.l >= 0 ? this.props.target.l + "L " : "");
        return (
            <PanelGroup id="target_water">
            <Panel className={this.props.enoughLiters ? "" : "panel-danger"}>
                <Panel.Heading>
                    <Panel.Title ><Glyphicon glyph="flag"/> {qty + "Target"}</Panel.Title>
                </Panel.Heading>
                <Panel.Body>
                    <FormGroup className={this.props.enoughLiters ? "" : "has-error"}>
                        <Row>
                            <MineralInput
                                label="litri" symbol="L"
                                value={this.props.target.l}
                                onChange={this.targetChanged.bind(this, "l")}
                                editable
                                minValue={1}
                            />
                        </Row>
                    </FormGroup>
                    <MineralForm
                        water={this.props.target}
                        attrChanged={this.targetChanged.bind(this)}
                        editable
                    />
                </Panel.Body>
            </Panel>
        </PanelGroup>
        )
    }

    targetChanged(attrName: string, value: number) {
        this.props.targetChanged({...this.props.target, [attrName]: value});
    }
}

export default TargetWater;