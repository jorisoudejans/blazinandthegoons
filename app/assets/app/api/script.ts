import {Action} from "./action";

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
        public percentage: string,
        public actionIndex: number,
        public script: Script
    ) {  }


}