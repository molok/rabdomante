import * as React from 'react'
import {Component} from 'react';
import {Col, FormLabel, FormControl, FormGroup, Container, Row} from "react-bootstrap";
import {WaterUi} from "../model/index";
import MineralForm from "./MineralForm";
import MineralInput from "./MineralInput";
import {translate} from "./Translate";
import msg from "../i18n/msg";
import Card from "react-bootstrap/Card";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

interface WatersProps {
    waters: Array<WaterUi>
    changedWater: (idx: number, w: WaterUi) => void
    skipQty?: boolean
}

export class SolutionWaters extends Component<WatersProps, {}> {
    public static defaultProps:Partial<WatersProps> = {
        skipQty: false
    };

    sourceChanged(idx: number, attrName: string, e: React.ChangeEvent<HTMLInputElement>) {
        e.stopPropagation();
        this.props.changedWater(idx, {...this.props.waters[idx], [attrName]: e.target.value});
    }

    render() {
        let res = this.props.waters
            .map((w: WaterUi, idx: number) => this.waterToPanel(idx, w));

        return (
            <Container id="solution_waters">
                {res}
            </Container>
        );
    }

    private waterToPanel(idx: number, w: WaterUi) {
        let qty;
        if (this.props.skipQty) {
            qty = " ";
        } else {
            qty = (w.l >= 0 ? w.l + "L " : "");
        }

        return <Card key={idx}
                      className="waterPanel panel-success"
                      // expanded={this.props.waters[idx].visible}
                      >
            <Card.Header onClick={this.togglePanel.bind(this, idx)}>
                <Card.Title className="clearfix">
                    <FontAwesomeIcon icon="tint"/> {qty + w.name}
                </Card.Title>
            </Card.Header>
            <Card.Body>
                <FormGroup>
                    <Row>
                        <Col as={FormLabel} sm={2}>{translate(msg.name)}</Col>
                        <Col sm={4}>
                            <FormControl name={translate(msg.name)} size="sm" type="text" placeholder=""
                                         value={w.name} onChange={() => {}} />
                        </Col>
                        <MineralInput
                            label={translate(msg.liters)} symbol="L"
                            value={w.l}
                            onChange={() => {}}
                            editable={false} />
                    </Row>
                </FormGroup>
                <MineralForm minValue={Number.MIN_SAFE_INTEGER} water={w} attrChanged={() => {}} editable={false}/>
            </Card.Body>
        </Card>;
    }

    togglePanel(idx: number, e: any) {
        e.preventDefault();
        this.props.changedWater(idx, {...this.props.waters[idx], "visible": !this.props.waters[idx].visible});
    }
}
