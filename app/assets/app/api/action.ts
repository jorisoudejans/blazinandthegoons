import {Script} from "./api/script";
import {Preset} from "./api/preset";

export class Action {

    constructor(
        public id: number,
        public description: string,
        public estTime: number,
        public preset: Preset,
        public active: boolean
    ) { active = false; }


}
