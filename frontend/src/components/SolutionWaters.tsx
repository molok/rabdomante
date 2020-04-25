import * as React from 'react'
import {Component, Fragment} from 'react';
import {Col, ControlLabel, FormControl, FormGroup, Glyphicon, Panel, PanelGroup, Row} from "react-bootstrap";
import {WaterUi} from "../model/index";
import MineralForm from "./MineralForm";
import MineralInput from "./MineralInput";
import {translate} from "./Translate";
import msg from "../i18n/msg";

interface WatersProps {
    waters: Array<WaterUi>
    changedWater: (idx: number, w: WaterUi) => void
    skipQty?: boolean
    skipName?: boolean
}

export class SolutionWaters extends Component<WatersProps, {}> {
    public static defaultProps:Partial<WatersProps> = {
        skipQty: false,
        skipName: true
    };

    sourceChanged(idx: number, attrName: string, e: React.ChangeEvent<HTMLInputElement>) {
        e.stopPropagation();
        this.props.changedWater(idx, {...this.props.waters[idx], [attrName]: e.target.value});
    }

    render() {
        let res = this.props.waters
            .map((w: WaterUi, idx: number) => this.waterToPanel(idx, w));

        return (
            <PanelGroup id="solution_waters">
                {res}
            </PanelGroup>
        );
    }

    private waterToPanel(idx: number, w: WaterUi) {
        let qty;
        if (this.props.skipQty) {
            qty = " ";
        } else {
            qty = (w.l >= 0 ? w.l + "L " : "");
        }

        return <Panel key={idx} eventKey={idx}
                      className="waterPanel panel-success"
                      expanded={this.props.waters[idx].visible}
                      onToggle={(ignored: any) => {
                      }}>
            <Panel.Heading onClick={this.togglePanel.bind(this, idx)}>
                <Panel.Title toggle className="clearfix">
                    <Glyphicon glyph="tint"/> {qty + w.name}
                </Panel.Title>
            </Panel.Heading>
            <Panel.Body collapsible  style={{marginLeft: '15px', marginRight: '15px'}}>
                <FormGroup>
                    <Row>
                        {this.props.skipName
                            ? <Fragment></Fragment>
                            : <Fragment>
                            <Col componentClass={ControlLabel} sm={2}>{translate(msg.name)}</Col>
                            <Col sm={4}>
                                <FormControl name={translate(msg.name)} bsSize="small" type="text" placeholder=""
                                             value={w.name} onChange={() => {
                                }}/>
                            </Col>
                            </Fragment>
                        }
                        <MineralInput
                            label={translate(msg.liters)} symbol="L"
                            value={w.l}
                            onChange={() => {}}
                            editable={false} />
                    </Row>
                </FormGroup>
                <MineralForm minValue={Number.MIN_SAFE_INTEGER} water={w} attrChanged={() => {}} editable={false}/>
            </Panel.Body>
        </Panel>;
    }

    togglePanel(idx: number, e: any) {
        e.preventDefault();
        this.props.changedWater(idx, {...this.props.waters[idx], "visible": !this.props.waters[idx].visible});
    }
}
