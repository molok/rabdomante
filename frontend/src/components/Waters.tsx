import * as React from 'react';
import {Component} from 'react';
import {WaterUi} from "../model/index";
import MineralForm from "./MineralForm";
import MineralInput from "./MineralInput";
import Select from 'react-select';
import KNOWN_WATERS from '../data/known_waters'
import {numberToQtyStr} from "./utils";
import {translate} from "./Translate";
import msg from "../i18n/msg";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import {FormControl, FormGroup, FormLabel, Row} from "react-bootstrap";
import Card from "react-bootstrap/Card";
import Container from "react-bootstrap/Container";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

interface WatersProps {
    waters: Array<WaterUi>
    removeWater: (idx: number) => void
    changedWater: (idx: number, w: WaterUi) => void
    enoughLiters: boolean
    supportInfinite?: boolean
}

interface NameOrSelectProps {
    changedWater: (idx: number, w: WaterUi) => void
    w: WaterUi
    idx: number
    knownWaterChanged(idx: number, prevWater: WaterUi, comboSelection: any): void
}

class NameOrSelect extends Component<NameOrSelectProps, {}> {
    render() {
        const w = this.props.w;
        if (w.custom) {
            return (
                <>
                    <FormControl name={translate(msg.name)} size="sm" type="text" placeholder=""
                                 value={w.name}
                                 // onChange={this.props.changedWater.bind(this)}
                    />
                </>);
        } else {
            let options = KNOWN_WATERS.map((x, idx) => ({ label: x.name, value: idx }));
            return (
                <>
                    <Select //clearable={false}
                            options={options}
                            placeholder={translate(msg.select)}
                            // onChange={this.props.knownWaterChanged.bind(this)}
                            // value={options.map(w => w.label).indexOf(w.name)} /* FIXME */
                    />
                </>
            );
        }
    }
}

class Waters extends Component<WatersProps, {}> {
    public static defaultProps:Partial<WatersProps> = {
        supportInfinite: false
    };
    sourceChanged(idx: number, attrName: string, e: React.ChangeEvent<HTMLInputElement>) {
        console.log("changedWater!");
        e.stopPropagation();
        this.props.changedWater(idx, {...this.props.waters[idx], [attrName]: e.target.value});
    }

    removeSource(idx: number, e: any) {
        e.preventDefault();
        e.stopPropagation();
        this.props.removeWater(idx);
    }

    render() {
        let res = this.props.waters
            .map((w: WaterUi, idx: number) => this.waterToPanel(idx, w));

        return (
            <Container id="source_waters">
                {res}
            </Container>
        );
    }

    knownWaterChanged(idx: number, prevWater: WaterUi, comboSelection: any): void {
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
        this.props.changedWater(idx, {...prevWater, ...newWater});
    }

    private waterToPanel(idx: number, w: WaterUi) {
        return <Card key={idx}
                      className={"waterPanel" + (this.props.enoughLiters ? "" : " panel-warning")}
                      // expanded={this.props.waters[idx].visible}
                      // onToggle={(ignored: any) => { }}
                >
            <Card.Header onClick={this.togglePanel.bind(this, idx)}>
                <Card.Title className="clearfix">
                    <FontAwesomeIcon icon="tint"/> {numberToQtyStr(w.l, "L") + " " + (w.name || translate(msg.baseWater) + " #" + (idx + 1))}
                    <span className={"pull-right"}>
                        <Button size="sm" onClick={this.removeSource.bind(this, idx)}><FontAwesomeIcon icon="times"/></Button>
                    </span>
                </Card.Title>
            </Card.Header>
            <Card.Body>
                <FormGroup>
                    <Row>
                        <Col as={FormLabel} sm={2}>{translate(msg.name)}</Col>
                        <Col sm={4}>
                            <NameOrSelect w={w} idx={idx}
                                             changedWater={_ => {}}
                                          // changedWater={this.sourceChanged.bind(this, idx, "name")}
                                          knownWaterChanged={this.knownWaterChanged.bind(this, idx, w)}
                            />
                        </Col>
                        <div className={this.props.enoughLiters ? "" : "has-warning"}>
                        <MineralInput
                            label={translate(msg.liters)} symbol="L"
                            value={w.l}
                            onChange={this.attrChanged.bind(this, idx, "l")}
                            minValue={1}
                            supportInfinite
                            editable />
                        </div>
                    </Row>
                </FormGroup>
                <MineralForm water={w} attrChanged={this.attrChanged.bind(this, idx)} editable={w.custom}/>
            </Card.Body>
        </Card>;
    }

    attrChanged(idx: number, attrName: string, value: any) {
        this.props.changedWater(idx, {...this.props.waters[idx], [attrName]: value});
    }

    togglePanel(idx: number, e: any) {
        e.preventDefault();
        this.attrChanged(idx, "visible", !this.props.waters[idx].visible);
    }
}

export default Waters;