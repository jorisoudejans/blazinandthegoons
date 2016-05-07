import {Action} from "./api/action";
import {Preset} from "./api/preset";

export class Script {

    constructor(
        public id: number,
        public name: string,
        public creationdate: string,
        public actions: Action[]
    ) {  }


}