import * as React from 'react';
import {Component} from 'react';
import {Button, Col, ControlLabel, FormControl, FormGroup, Glyphicon, Panel, PanelGroup, Row} from "react-bootstrap";
import {WaterDef} from "../model/index";
import MineralForm from "./MineralForm";
import MineralInput from "./MineralInput";
import Select from 'react-select';
import KNOWN_WATERS from '../data/known_waters'

interface SourceWatersProps {
    sources: Array<WaterDef>
    removeSource: (idx: number) => void
    sourceChanged: (idx: number, w: WaterDef) => void
}

function NameOrSelect(props: any) {
    const w = props.w;
    console.log("w.name:" + w.name);
    if (w.custom) {
        return (
            <>
            <FormControl name="name" bsSize="small" type="text" placeholder=""
                         value={w.name}
                         onChange={props.sourceChanged}/>
            </>);
    } else {
        let options = KNOWN_WATERS.map((x, idx) => ({ label: x.name, value: idx }));
        return (
            <>
            <Select clearable={false}
                    options={options}
                    onChange={props.knownWaterChanged}
                    value={{label: w.name, value: w.name}}/>
            </>
        );
    }
}

class SourceWaters extends Component<SourceWatersProps, {}> {
    sourceChanged(idx: number, attrName: string, e: React.ChangeEvent<HTMLInputElement>) {
        console.log("sourceChanged!");
        e.stopPropagation();
        this.props.sourceChanged(idx, {...this.props.sources[idx], [attrName]: e.target.value});
    }

    removeSource(idx: number, e: any) {
        e.preventDefault();
        e.stopPropagation();
        this.props.removeSource(idx);
    }

    render() {
        let res = this.props.sources
            .map((w: WaterDef, idx: number) => this.waterToPanel(idx, w));

        return (
            <PanelGroup id="source_waters">
                {res}
            </PanelGroup>
        );
    }

    knownWaterChanged(idx: number, prevWater: WaterDef, comboSelection: any): void {
        if (comboSelection == null || comboSelection.value == null) return;
        console.log("cambiato", idx, comboSelection);
        let x = KNOWN_WATERS[comboSelection.value];
        let newWater = {
            name: x.name,
            ca: x.ca,
            mg: x.mg,
            na: x.na,
            so4: x.so4,
            cl: x.cl,
            hco3: x.hco3
        };
        this.props.sourceChanged(idx, {...prevWater, ...newWater});
    }

    private waterToPanel(idx: number, w: WaterDef) {
        return <Panel key={idx} eventKey={idx}
                      className="waterPanel"
                      expanded={this.props.sources[idx].visible}
                      onToggle={(ignored: any) => {
                      }}>
            <Panel.Heading onClick={this.togglePanel.bind(this, idx)}>
                <Panel.Title toggle className="clearfix"><Glyphicon glyph="tint"/> {w.name || "Acqua base #" + (idx + 1)}
                    <span className={"pull-right"}>
                                <Button bsSize="xsmall" onClick={this.removeSource.bind(this, idx)}><Glyphicon
                                    glyph="remove"/></Button>
                            </span>
                </Panel.Title>
            </Panel.Heading>
            <Panel.Body collapsible>
                <FormGroup>
                    <Row>
                        <Col componentClass={ControlLabel} sm={2}>Nome</Col>
                        <Col sm={4}>
                            <NameOrSelect w={w} idx={idx}
                                          sourceChanged={this.sourceChanged.bind(this, idx, "name")}
                                          knownWaterChanged={this.knownWaterChanged.bind(this, idx, w)}
                            />
                        </Col>
                        <MineralInput
                            label="litri" symbol="L"
                            value={w.l}
                            onChange={this.attrChanged.bind(this, idx, "l")}
                            editable={true} />
                    </Row>
                </FormGroup>
                <MineralForm water={w} attrChanged={this.attrChanged.bind(this, idx)} editable={w.custom}/>
            </Panel.Body>
        </Panel>;
    }

    attrChanged(idx: number, attrName: string, value: any) {
        this.props.sourceChanged(idx, {...this.props.sources[idx], [attrName]: value});
    }

    togglePanel(idx: number, e: any) {
        e.preventDefault();
        this.attrChanged(idx, "visible", !this.props.sources[idx].visible);
    }
}

export default SourceWaters;