import {asyncFindRecipe} from "./Api";
import {SaltUi} from "./model";

it('api', () => {
    const waters = [
          {
            name: "Sant'anna", l: 10,
            ca: 200, mg: 100, na: 1, so4: 1, cl: 1, hco3: 1, visible: false, custom: true
          }
    ];
    const salts:Array<SaltUi> = [];
    const target = {
        name: "Sant'anna", l: 10,
        ca: 200, mg: 100, na: 100, so4: 100, cl: 100, hco3: 100, visible: false, custom: true
    };

    asyncFindRecipe(waters, salts, target).then(
        (resp) => console.log(resp)
    )
});