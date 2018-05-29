import * as React from 'react';
import {ChangeEvent, Component} from 'react';
import { Col, ControlLabel, FormControl } from "react-bootstrap";

interface MineralInputProps {
    label: string,
    symbol: string,
    value: number,
    onChange: (e: number) => void,
    editable: boolean
}
class MineralInput extends React.Component<MineralInputProps, {}> {
    changed(e: ChangeEvent<HTMLInputElement>) {
        this.props.onChange(parseFloat(e.target.value));
    }

    render() {
        let show = this.titleCase(this.props.label) + (this.props.symbol ? " (" + this.props.symbol + ")" : "");
        let valuePresent = this.props.value != -1;
        return (
            <>
                <Col componentClass={ControlLabel} sm={2}>{show}</Col>
                <Col sm={1}>
                    <FormControl value={valuePresent ? this.props.value : "N/A"} bsSize="small" type={valuePresent ? "number" : "text"} placeholder=""
                                 disabled={!this.props.editable}
                                 onChange={this.changed.bind(this)}/>
                </Col>
            </>
        )
    }

    titleCase(str: string) {
        return str.charAt(0).toUpperCase() +
            str.substr(1).toLowerCase();
    }
}

export default MineralInput;