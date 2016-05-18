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

export class ActiveScript {

    constructor(
        public runningTime: number,
        public actionIndex: number,
        public script: Script
    ) {  }


}