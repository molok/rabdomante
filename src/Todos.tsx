import * as React from 'react';
import {actAddTodo, actTodoTextChanged, State} from "./index";
import {connect} from "react-redux";

interface Props {
    todos: Array<string>;
    todoText: string;
    addTodo: (text: string) => void;
    todoTextChanged: (text: string) => void;
}

class XTodos extends React.Component<Props, {}> {
    changed(e: any) {
        this.props.todoTextChanged(e.target.value);
    }

    addTodo(e: any) {
        e.preventDefault();
        this.props.addTodo(this.props.todoText);
    }

    render() {
       let res = this.props.todos.map((t, i) => <p key={i}>{i}:{t}</p>);
       return (
           <div>
               <p>xTODOS:</p>
               {res}
               <div>
                   <form onSubmit={this.addTodo.bind(this)}>
                       <input type="text" name="todoText" value={this.props.todoText} onChange={this.changed.bind(this)} />
                       <button onClick={this.addTodo.bind(this)}>Clicca!</button>
                   </form>
               </div>
           </div>
       )
    }
}

let mapStateToProps = (state: State) => {
    return {
        todos: state.todos,
        todoText: state.todoText,
    }};
let mapDispatchToProps = (dispatch: Function) => {
    return {
        addTodo: (text: string) => { dispatch(actAddTodo(text)) },
        todoTextChanged: (text: string) => { dispatch(actTodoTextChanged(text)) }
    }
};

let Todos = connect(mapStateToProps, mapDispatchToProps)(XTodos);

export default Todos;
