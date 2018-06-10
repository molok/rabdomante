import {SaltUi} from "../model/index";
import * as React from "react";
import {FormControl, PanelGroup} from "react-bootstrap";
import * as Panel from "react-bootstrap/lib/Panel";
import * as FormGroup from "react-bootstrap/lib/FormGroup";
import * as Row from "react-bootstrap/lib/Row";
import * as Col from "react-bootstrap/lib/Col";
import * as ControlLabel from "react-bootstrap/lib/ControlLabel";
import MineralInput from "./MineralInput";
import MineralForm from "./MineralForm";
import {Component} from "react";
import {SaltIcon} from "./SaltIcon";

interface SolutionSaltsProps {
    salts: Array<SaltUi>
    saltChanged: (idx: number, s: SaltUi) => void
}

export class SolutionSalts extends Component<SolutionSaltsProps, {}> {
    render() {
        let res = this.props.salts
            .map((s: SaltUi, idx: number) => this.saltToPanel(idx, s));

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

    saltToPanel(idx: number, s: SaltUi) {
        let qty = (s.dg >= 0 ? s.dg + "dg " : "");
        return <Panel key={idx} eventKey={idx}
                      className="saltPanel panel-success"
                      expanded={this.props.salts[idx].visible}
                      onToggle={(ignored: any) => {
                      }}>
            <Panel.Heading onClick={this.togglePanel.bind(this, idx)}>
                <Panel.Title toggle className="clearfix"><SaltIcon fill="#227442"/> {qty + s.name}</Panel.Title>
            </Panel.Heading>
            <Panel.Body collapsible>
                <FormGroup>
                    <Row>
                        <Col componentClass={ControlLabel} sm={2}>Name</Col>
                        <Col sm={4}>
                            <FormControl name="name" bsSize="small" type="text" placeholder=""
                                         value={s.name}
                                         onChange={() => {}} />
                        </Col>
                        <MineralInput
                            label="Decigrams" symbol="dg"
                            value={s.dg}
                            onChange={this.attrChanged.bind(this, idx, "dg")}
                            editable={false} />
                    </Row>
                </FormGroup>
                <MineralForm water={s} attrChanged={this.attrChanged.bind(this, idx)} editable={false}/>
            </Panel.Body>
        </Panel>;
    }
}
