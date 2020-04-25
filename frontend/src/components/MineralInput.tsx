import * as React from 'react';
import {ChangeEvent} from 'react';
import { Col, ControlLabel, FormControl } from "react-bootstrap";
import {isInfinite, numberToQtyStr, qtyToNumber} from "./utils";

interface MineralInputProps {
    label: string,
    symbol: string,
    value: number,
    onChange: (e: number) => void,
    editable: boolean
    supportInfinite?: boolean
    minValue?: number
}

class MineralInput extends React.Component<MineralInputProps, {}> {
    public static defaultProps:Partial<MineralInputProps> = {
        supportInfinite: false,
        minValue: 0
    };

    toNum(value: string): number {
        let number:number;
        if (this.props.supportInfinite) {
            number = qtyToNumber(value);
        } else {
            number = parseFloat(value);
        }

        if (this.props.minValue !== undefined) {
            number = Math.max(this.props.minValue, number);
        }

        return Math.round(number);
    }

    changed(e: ChangeEvent<HTMLInputElement>) {
        this.props.onChange(this.toNum(e.target.value));
    }

    toUiValue(it:number):string {
        var number = it;
        if (this.props.minValue !== undefined) {
            number = Math.max(this.props.minValue, number);
        }
        if (isNaN(this.props.value)) {
            number = this.props.minValue!;
        }
        return numberToQtyStr(number);
    }

    render() {
        let show = this.titleCase(this.props.label) + " ";
        return (
            <>
                <Col componentClass={ControlLabel} md={2} xs={6} style={{marginTop: '15px'}}>{show}<small style={{color: "#666666", fontWeight: "lighter"}}>({this.props.symbol})</small></Col>
                <Col md={1} xs={6} style={{marginTop: '15px'}}>
                    <FormControl value={this.toUiValue(this.props.value)}
                                 bsSize="small"
                                 type={isInfinite(this.props.value) ? "text" : "number"}
                                 placeholder=""
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
