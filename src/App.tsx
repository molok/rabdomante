import * as React from 'react';
import './App.css';
import { Store } from 'redux';

interface State {
    counter: number;
}

interface PropsType { store: Store<State>; }

class App extends React.Component<PropsType, State> {

  increment(e: MouseEvent) {
      e.preventDefault();
      this.props.store.dispatch( { type: 'increment' } );
  }

  decrement(e: MouseEvent) {
      e.preventDefault();
      this.props.store.dispatch( { type: 'decrement' } );
  }

  render() {
    return (
        <div>
          counter: {this.props.store.getState().counter}
          <form>
              <button onClick={this.increment.bind(this)}>+</button>
              <button onClick={this.decrement.bind(this)}>-</button>
          </form>
        </div>
    );
  }
}

export default App;
