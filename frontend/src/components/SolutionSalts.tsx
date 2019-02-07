import {SaltUi} from "../model/index";
import * as React from "react";
import {FormControl, Container, Card, FormGroup, Row, Col, FormLabel} from "react-bootstrap";
import MineralInput from "./MineralInput";
import MineralForm from "./MineralForm";
import {Component} from "react";
import {SaltIcon} from "./SaltIcon";
import {translate} from "./Translate";
import msg from "../i18n/msg";

interface SolutionSaltsProps {
    salts: Array<SaltUi>
    saltChanged: (idx: number, s: SaltUi) => void
}

export class SolutionSalts extends Component<SolutionSaltsProps, {}> {
    render() {
        let res = this.props.salts
            .map((s: SaltUi, idx: number) => this.saltToPanel(idx, s));

        return (
            <Container id="salts">
                {res}
            </Container>
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
        return <Card key={idx}
                      className="saltPanel panel-success"
                      // expanded={this.props.salts[idx].visible}
                      // onToggle={(ignored: any) => { // }}
        >
            <Card.Header onClick={this.togglePanel.bind(this, idx)}>
                <Card.Title className="clearfix"><SaltIcon fill="#227442"/> {qty + s.name}</Card.Title>
            </Card.Header>
            <Card.Body>
                <FormGroup>
                    <Row>
                        <Col as={FormLabel} sm={2}>{translate(msg.name)}</Col>
                        <Col sm={4}>
                            <FormControl name={translate(msg.name)} size="sm" type="text" placeholder=""
                                         value={s.name}
                                         onChange={() => {}} />
                        </Col>
                        <MineralInput
                            label={translate(msg.decigrams)} symbol="dg"
                            value={s.dg}
                            onChange={this.attrChanged.bind(this, idx, "dg")}
                            editable={false} />
                    </Row>
                </FormGroup>
                <MineralForm water={s} attrChanged={this.attrChanged.bind(this, idx)} editable={false}/>
            </Card.Body>
        </Card>;
    }
}
