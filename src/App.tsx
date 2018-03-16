import * as React from 'react';
import './App.css';
import Counter from "./Counter";
import Todos from "./Todos";

class App extends React.Component {
    render() {
        return (
            <div>
                <Counter />
                <Todos />
            </div>
        )
    }
}

export default App;
