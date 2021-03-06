import {Preset} from "./preset";

export class Action {

    constructor(
        public id: number,
        public index: number,
        public description: string,
        public duration: number,
        public preset: Preset,
        public flagged: boolean,
        public flagDescription: string,
        public flagType: string = null,
        public active: boolean = false,
        public percentage: string = "2%",
        public color: string = "rgb(0, 153, 0)"
    ) {  }


}
