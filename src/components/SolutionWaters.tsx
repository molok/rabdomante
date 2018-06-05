import * as React from 'react'
import {Component} from 'react';
import {Col, ControlLabel, FormControl, FormGroup, Glyphicon, Panel, PanelGroup, Row} from "react-bootstrap";
import {WaterUi} from "../model/index";
import MineralForm from "./MineralForm";
import MineralInput from "./MineralInput";

interface WatersProps {
    waters: Array<WaterUi>
    changedWater: (idx: number, w: WaterUi) => void
}

export class SolutionWaters extends Component<WatersProps, {}> {
    sourceChanged(idx: number, attrName: string, e: React.ChangeEvent<HTMLInputElement>) {
        e.stopPropagation();
        this.props.changedWater(idx, {...this.props.waters[idx], [attrName]: e.target.value});
    }

    render() {
        console.log('waters:', this.props.waters);
        let res = this.props.waters
            .map((w: WaterUi, idx: number) => this.waterToPanel(idx, w));

        return (
            <PanelGroup id="solution_waters">
                {res}
            </PanelGroup>
        );
    }

    private waterToPanel(idx: number, w: WaterUi) {
        console.log("solWater", idx, w);
        return <Panel key={idx} eventKey={idx}
                      className="waterPanel"
                      expanded={this.props.waters[idx].visible}
                      onToggle={(ignored: any) => {
                      }}>
            <Panel.Heading onClick={this.togglePanel.bind(this, idx)}>
                <Panel.Title toggle className="clearfix"><Glyphicon glyph="tint"/> {w.name || "Acqua base #" + (idx + 1)}</Panel.Title>
            </Panel.Heading>
            <Panel.Body collapsible>
                <FormGroup>
                    <Row>
                        <Col componentClass={ControlLabel} sm={2}>Nome</Col>
                        <Col sm={4}>
                            <FormControl name="name" bsSize="small" type="text" placeholder=""
                                         value={w.name} onChange={() => {}} />
                        </Col>
                        <MineralInput
                            label="litri" symbol="L"
                            value={w.l}
                            onChange={() => {}}
                            editable={false} />
                    </Row>
                </FormGroup>
                <MineralForm water={w} attrChanged={() => {}} editable={false}/>
            </Panel.Body>
        </Panel>;
    }

    togglePanel(idx: number, e: any) {
        e.preventDefault();
        this.props.changedWater(idx, {...this.props.waters[idx], "visible": !this.props.waters[idx].visible});
    }
}
