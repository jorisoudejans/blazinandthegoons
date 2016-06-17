import {Script} from "./script";

export class Preset {

    constructor(
        public id: number,
        public name: String,
        public description: String,
        public cameraId: number,
        public status: String,
        public script: Script,
        public pan: number,
        public tilt: number,
        public zoom: number,
        public focus: number,
        public iris: number
    ) {  }
}