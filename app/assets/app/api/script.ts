import {Action} from "./action";
import {Preset} from "./preset";

export class Script {

    constructor(
        public id: number,
        public name: string,
        public creationdate: string,
        public actions: Action[],
        public location: Location,
        public presets: Preset[]
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

export class Camera {
    constructor(
        public id: number,
        public name: string,
        public ip: string
    ) { }
}

export class Location {

    constructor(
        public id: number,
        public name: string,
        public cameras: Camera[]
    ) { }

}