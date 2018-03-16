import * as React from "react";
import {State} from "./index";
import {connect} from "react-redux";

class XCounter extends React.Component<Props, {}> {
    increment(e: MouseEvent) {
        e.preventDefault();
        this.props.increment();
    }

    decrement(e: MouseEvent) {
        e.preventDefault();
        this.props.decrement();
    }

    render() {
        return (
            <div>
                zcounter: {this.props.counter}
                <form>
                    <button onClick={this.increment.bind(this)}>+</button>
                    <button onClick={this.decrement.bind(this)}>-</button>
                </form>
            </div>
        );
    }
}

interface Props {
    counter: number,
    increment: () => void,
    decrement: () => void
}

function mapStateToProps (state: State) { return { counter: state.counter }; }
function mapDispatchToProps (dispatch: Function) {
    return {
        increment: () => { dispatch({ type: 'increment'})},
        decrement: () => { dispatch({ type: 'decrement'})}
    }
}

let Counter = connect(
    mapStateToProps,
    mapDispatchToProps
)(XCounter);

export default Counter;