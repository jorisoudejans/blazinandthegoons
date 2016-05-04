/**
 * Created by floris on 04/05/2016.
 */
import {Script} from "./api/script";

export class Preset {

    constructor(
        public camid: number,
        public name: String,
        public pan: number,
        public tilt: number,
        public zoom: number,
        public focus: number
    ) {  }


}
