/**
 * Created by floris on 04/05/2016.
 */
import {Script} from "./api/script";
import {Preset} from "./api/preset";

export class Action {

    constructor(
        public id: number,
        public description: string,
        public script: string,
        public active: boolean,
        public preset: Preset
    ) { active = false; }


}
