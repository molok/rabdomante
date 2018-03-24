import * as React from 'react';
import {Component} from 'react';
import {Button, Col, ControlLabel, FormControl, FormGroup, Glyphicon, Panel, PanelGroup, Row} from "react-bootstrap";
import {WaterDef} from "../water";
import MineralForm from "./MineralForm";
import MineralInput from "./MineralInput";

interface SourceWatersProps {
    sources: Array<WaterDef>
    removeSource: (idx: number) => void
    sourceChanged: (idx: number, w: WaterDef) => void
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
            .map((w: WaterDef, idx: number) =>
                <Panel key={idx} eventKey={idx}
                       className="waterPanel"
                       expanded={this.props.sources[idx].visible}
                       onToggle={(ignored: any) => {
                       }} /* se non presente una callback spara fuori un warning */ >
                    <Panel.Heading onClick={this.togglePanel.bind(this, idx)}>
                        <Panel.Title toggle className="clearfix">{w.name || "Acqua base #" + (idx + 1)}
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
                                    <FormControl name="name" bsSize="small" type="text" placeholder=""
                                                 value={w.name}
                                                 onChange={this.sourceChanged.bind(this, idx, "name")}/>
                                </Col>
                                <MineralInput
                                    label="litri" symbol=""
                                    value={w.l}
                                    onChange={this.attrChanged.bind(this, idx, "l")}/>
                            </Row>
                        </FormGroup>
                        <MineralForm water={w} attrChanged={this.attrChanged.bind(this, idx)}/>
                    </Panel.Body>
                </Panel>
            );

        return (
            <PanelGroup id="source_waters">
                {res}
            </PanelGroup>
        );
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