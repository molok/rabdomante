import * as React from "react";
import {shallow} from 'enzyme';
import {Provider} from "react-redux";
import Rabdo from "./containers/Rabdo";
import {createStore} from "redux";
import reducers from "./reducers";
import defaultState from "./index";
import * as ReactDOM from "react-dom";
import {create} from 'react-test-renderer';


it('full integration', () => {
    // let store = createStore(reducers, defaultState);
    // const app = create(
    //     {/*<Provider store={store}>*/}
    //         <Rabdo />
    //     // </Provider>
    // )
/*    //     {/!*<Provider store={store}>*!/}
    //         {/!*<Rabdo />*!/}
    //     {/!*</Provider>,*!/}
    //     {/!*document.getElementById('root') as HTMLElement );*!/}*/
    // let wrapper = shallow(
    // <Provider store={store}>
    // <Rabdo />
    // </Provider>
    // ).dive();
    // expect(wrapper.find('.waterPanel')).toHaveLength(0);
});
