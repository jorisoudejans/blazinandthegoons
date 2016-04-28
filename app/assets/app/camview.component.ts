/**
 * Created by Floris on 4/28/2016.
 */
import {Component} from "angular2/core"
import {ScriptElement} from "./scriptelement"

@Component({
    selector: "camview",
    template: "<iframe src='{{ currentCam.camSource }}' frameborder='0' scrolling='no' width='640' height='320.00' allowfullscreen></iframe>"
})
export class CamView {
    currentCam = new ScriptElement(0, "Preset 1", 0, "http://i42.tinypic.com/4sz30z.gif")
}

