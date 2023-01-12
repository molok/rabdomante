import * as React from 'react';
import {Component} from 'react';
import {Col, FormGroup, Glyphicon, Panel, PanelGroup, Row} from "react-bootstrap";
import {WaterUi} from "../model/index";
import MineralInput from "./MineralInput";
import MineralForm from "./MineralForm";
import {translate} from "./Translate";
import msg from "../i18n/msg";
import Select from "react-select";
import KNOWN_TARGET_WATERS from "../data/known_target_waters";

interface TargetWaterProps {
    target: WaterUi,
    targetChanged: (w: WaterUi) => void,
    enoughLiters: boolean
}

class TargetWater extends Component<TargetWaterProps, {}> {
    render() {
        let qty = (this.props.target.l >= 0 ? this.props.target.l + "L " : "");
        let options = KNOWN_TARGET_WATERS.map((x, idx) => ({ label: x.name, value: idx }));
        return (
            <PanelGroup id="target_water">
            <Panel className={this.props.enoughLiters ? "" : "panel-danger"}>
                <Panel.Heading>
                    <Panel.Title ><Glyphicon glyph="flag"/> {qty + translate(msg.target)}</Panel.Title>
                </Panel.Heading>
                <Panel.Body style={{marginLeft: '15px', marginRight: '15px'}}>
                    <FormGroup className={this.props.enoughLiters && this.props.target.l >= 1 ? "" : "has-error"}>
                        <Row>
                            <MineralInput
                                label={translate(msg.liters)} symbol="L"
                                value={this.props.target.l}
                                onChange={this.targetChanged.bind(this, "l")}
                                editable
                                minValue={0}
                            />
                        </Row>
                    </FormGroup>
                    <MineralForm
                        water={this.props.target}
                        attrChanged={this.targetChanged.bind(this)}
                        editable
                    />

                    <Select clearable={false}
                            options={options}
                            placeholder={translate(msg.select)}
                            onChange={this.waterProfileChanged.bind(this)}
                            value={options.map(w => w.label).indexOf(this.props.target.name)} /* FIXME */
                    />
                </Panel.Body>
            </Panel>
        </PanelGroup>
        )
    }

    waterProfileChanged(v: KnownValue) {
        // console.log(`new water: ${JSON.stringify(v, null, 4)}`)
        let water = KNOWN_TARGET_WATERS.find(x => x.name === v.label)
        if (water) {
            this.props.targetChanged({...this.props.target,
                custom: false,
                name: water.name,
                ca: water.ca,
                mg: water.mg,
                na: water.na,
                so4: water.so4,
                cl: water.cl,
                hco3: water.hco3
            });
        }
    }

    targetChanged(attrName: string, value: number) {
        this.props.targetChanged({...this.props.target, [attrName]: value, custom: true});
    }
}

interface KnownValue {
    label: string, value: number
}

export default TargetWater;
