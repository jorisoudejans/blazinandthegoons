import {Script} from "./script";

export class Preset {

    constructor(
        public id: number,
        public name: String,
        public cameraId: number,
        public realPresetId: number,
        public status: String,
        public script: Script
    ) {  }


}
