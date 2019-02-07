import * as React from 'react';
import {Component} from 'react';
import {FormGroup, Container, Card, Row} from "react-bootstrap";
import {WaterUi} from "../model/index";
import MineralInput from "./MineralInput";
import MineralForm from "./MineralForm";
import {translate} from "./Translate";
import msg from "../i18n/msg";

interface TargetWaterProps {
    target: WaterUi,
    targetChanged: (w: WaterUi) => void,
    enoughLiters: boolean
}

class TargetWater extends Component<TargetWaterProps, {}> {
    render() {
        let qty = (this.props.target.l >= 0 ? this.props.target.l + "L " : "");
        return (
            <Container id="target_water">
            <Card className={this.props.enoughLiters ? "" : "panel-danger"}>
                <Card.Header>
                    <Card.Title>{qty + translate(msg.target)}</Card.Title>
                </Card.Header>
                <Card.Body>
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
                </Card.Body>
            </Card>
        </Container>
        )
    }

    targetChanged(attrName: string, value: number) {
        this.props.targetChanged({...this.props.target, [attrName]: value});
    }
}

export default TargetWater;