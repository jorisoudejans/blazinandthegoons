import {Script} from "./api/script";

export class Preset {

    constructor(
        public id: number,
        public name: String,
        public cameraId: number,
        public realPresetId: number
    ) {  }


}
