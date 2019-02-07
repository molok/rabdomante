import {MineralContent, SaltUi} from "../model/index";
import * as React from "react";
import {Button, Card, Col, FormControl, FormGroup, FormLabel, Row} from "react-bootstrap";
import MineralInput from "./MineralInput";
import MineralForm from "./MineralForm";
import {Component} from "react";
import {KNOWN_SALTS} from "../data/known_salts";
import {SaltIcon} from "./SaltIcon";
import {numberToQtyStr} from "./utils";
import {translate} from "./Translate";
import msg from "../i18n/msg";
import Container from "react-bootstrap/Container";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import Select from "react-select";

interface SaltsProps {
    salts: Array<SaltUi>
    removeSalt: (idx: number) => void
    saltChanged: (idx: number, s: SaltUi) => void
    supportInfinite?: boolean
}
class Salts extends Component<SaltsProps, {}> {
    public static defaultProps:Partial<SaltsProps> = {
        supportInfinite: false
    };
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

    saltChanged(idx: number, attrName: string, e: React.ChangeEvent<HTMLInputElement>) {
        e.stopPropagation();
        this.props.saltChanged(idx, {...this.props.salts[idx], [attrName]: e.target.value});
    }

    removeSalt(idx: number, e: any) {
        e.preventDefault();
        e.stopPropagation();
        this.props.removeSalt(idx);
    }

    knownSaltChanged(idx: number, prevSalt: SaltUi, comboSelection: any): void {
        if (comboSelection == null || comboSelection.value == null) return;
        let x = KNOWN_SALTS[comboSelection.value];
        let newSalt:MineralContent = {
            name: x.name(),
            ca: x.ca,
            mg: x.mg,
            na: x.na,
            so4: x.so4,
            cl: x.cl,
            hco3: x.hco3
        };
        this.props.saltChanged(idx, {...prevSalt, ...newSalt});
    }

    saltToPanel(idx: number, s: SaltUi) {
        // let qty = (s.dg >= 0 ? s.dg + "g " : "");
        return <Card key={idx}
                      className="saltPanel"
                      >
            <Card.Header onClick={this.togglePanel.bind(this, idx)}>
                <Card.Title className="clearfix"><SaltIcon fill="#000000"/> {numberToQtyStr(s.dg, "dg") + " " + (s.name || translate(msg.salt) + " #" + (idx + 1))}
                    <span className={"pull-right"}>
                                <Button size="sm" onClick={this.removeSalt.bind(this, idx)}><FontAwesomeIcon icon="times"/></Button>
                            </span>
                </Card.Title>
            </Card.Header>
            <Card.Body>
                <FormGroup>
                    <Row>
                        <Col as={FormLabel} sm={2}>{translate(msg.name)}</Col>
                        <Col sm={4}>
                            <NameOrSelect w={s} idx={idx}
                                          saltChanged={__ => {}}
                                          // saltChanged={this.saltChanged.bind(this, idx, "name")}
                                          knownSaltChanged={this.knownSaltChanged.bind(this, idx, s)} />
                        </Col>
                        <MineralInput
                            label={translate(msg.decigrams)} symbol="dg"
                            value={s.dg}
                            supportInfinite
                            minValue={1}
                            onChange={this.attrChanged.bind(this, idx, "dg")}
                            editable={true} />
                    </Row>
                </FormGroup>
                <MineralForm water={s} attrChanged={this.attrChanged.bind(this, idx)} editable={s.custom}/>
            </Card.Body>
        </Card>;
    }
}

interface NameOrSelectProps {
    saltChanged: (idx: number, w: SaltUi) => void
    w: SaltUi
    idx: number
    knownSaltChanged(idx: number, prevSalt: SaltUi, comboSelection: any): void
}

class NameOrSelect extends Component<NameOrSelectProps, {}> {
    render() {
        const w = this.props.w;
        if (w.custom) {
            return (
                <>
                    <FormControl name={translate(msg.name)} size="sm" type="text" placeholder={translate(msg.select)}
                                 value={w.name}
                                 // onChange={this.props.saltChanged.bind(this)}
                    />
                </>);
        } else {
            let options = KNOWN_SALTS.map((x, idx) => ({ label: x.name(), value: idx }));
            return (
                <>
                    <Select //clearable={false}
                            options={options}
                            placeholder={translate(msg.select)}
                            // onChange={this.props.knownSaltChanged.bind(this)}
                            // value={options.map(w => w.label).indexOf(w.name)} /* FIXME */
                    />
                </>
            );
        }
    }
}


export default Salts;
