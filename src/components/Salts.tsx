import {MineralContent, Salt} from "../model/index";
import * as React from "react";
import {Button, FormControl, Glyphicon, PanelGroup} from "react-bootstrap";
import * as Panel from "react-bootstrap/lib/Panel";
import * as FormGroup from "react-bootstrap/lib/FormGroup";
import * as Row from "react-bootstrap/lib/Row";
import * as Col from "react-bootstrap/lib/Col";
import * as ControlLabel from "react-bootstrap/lib/ControlLabel";
import MineralInput from "./MineralInput";
import MineralForm from "./MineralForm";
import {Component} from "react";
import {KNOWN_SALTS} from "../data/known_salts";
import Select from 'react-select';

interface SaltsProps {
    salts: Array<Salt>
    removeSalt: (idx: number) => void
    saltChanged: (idx: number, s: Salt) => void
}

function NameOrSelect(props: any) {
    const w = props.w;
    console.log("w.name:" + w.name);
    if (w.custom) {
        return (
            <>
            <FormControl name="name" bsSize="small" type="text" placeholder=""
                         value={w.name}
                         onChange={props.saltChanged}/>
            </>);
    } else {
        let options = KNOWN_SALTS.map(x => ({ label: x.name, value: x.id }));
        return (
            <>
            <Select clearable={false}
                    options={options}
                    onChange={props.knownSaltChanged}
                    value={{label: w.name, value: w.name}}/>
            </>
        );
    }
}

class Salts extends Component<SaltsProps, {}> {
    render() {
        console.log('salts:', this.props.salts);
        let res = this.props.salts
            .map((s: Salt, idx: number) => this.saltToPanel(idx, s));

        return (
            <PanelGroup id="salts">
                {res}
            </PanelGroup>
        );
    }

    attrChanged(idx: number, attrName: string, value: any) {
        this.props.saltChanged(idx, {...this.props.salts[idx], [attrName]: value});
    }

    togglePanel(idx: number, e: any) {
        e.preventDefault();
        this.attrChanged(idx, "visible", !this.props.salts[idx].visible);
    }

    saltChanged(idx: number, attrName: string, e: React.ChangeEvent<HTMLInputElement>) {
        console.log("sourceChanged!");
        e.stopPropagation();
        this.props.saltChanged(idx, {...this.props.salts[idx], [attrName]: e.target.value});
    }

    removeSalt(idx: number, e: any) {
        e.preventDefault();
        e.stopPropagation();
        this.props.removeSalt(idx);
    }

    knownSaltChanged(idx: number, prevSalt: Salt, comboSelection: any): void {
        if (comboSelection == null || comboSelection.value == null) return;
        console.log("cambiato", idx, comboSelection);
        let x = KNOWN_SALTS[comboSelection.value-1];
        let newSalt:MineralContent = {
            name: x.name,
            ca: x.ca,
            mg: x.mg,
            na: x.na,
            so4: x.so4,
            cl: x.cl,
            hco3: x.hco3
        };
        this.props.saltChanged(idx, {...prevSalt, ...newSalt});
    }

    saltToPanel(idx: number, s: Salt) {
        console.log('idx: ', idx, 'salt:', s);
        return <Panel key={idx} eventKey={idx}
                      className="saltPanel"
                      expanded={this.props.salts[idx].visible}
                      onToggle={(ignored: any) => {
                      }}>
            <Panel.Heading onClick={this.togglePanel.bind(this, idx)}>
                <Panel.Title toggle className="clearfix"><Glyphicon glyph="unchecked"/> {s.name || "Salt #" + (idx + 1)}
                    <span className={"pull-right"}>
                                <Button bsSize="xsmall" onClick={this.removeSalt.bind(this, idx)}><Glyphicon
                                    glyph="remove"/></Button>
                            </span>
                </Panel.Title>
            </Panel.Heading>
            <Panel.Body collapsible>
                <FormGroup>
                    <Row>
                        <Col componentClass={ControlLabel} sm={2}>Nome</Col>
                        <Col sm={4}>
                            <NameOrSelect w={s} idx={idx}
                                          saltChanged={this.saltChanged.bind(this, idx, "name")}
                                          knownSaltChanged={this.knownSaltChanged.bind(this, idx, s)} />
                        </Col>
                        <MineralInput
                            label="grammi" symbol=""
                            value={s.g}
                            onChange={this.attrChanged.bind(this, idx, "g")}
                            editable={true} />
                    </Row>
                </FormGroup>
                <MineralForm water={s} attrChanged={this.attrChanged.bind(this, idx)} editable={s.custom}/>
            </Panel.Body>
        </Panel>;
    }
}

export default Salts;
