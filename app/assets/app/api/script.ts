import {Action} from "./api/action";

export class Script {

    constructor(
        public id: number,
        public name: string,
        public creationdate: string,
        public actions: Action[]
    ) {  }


}