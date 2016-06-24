import {Script, ActiveScript, Camera, Location} from "./api/script";
import {Component, Input, Output, EventEmitter} from "angular2/core";
import {ScriptService} from "./api/script.service";
import {Action} from "./api/action"
import {Preset} from "./api/preset";

declare var jQuery:any;

@Component({
    selector:    'action-list',
    templateUrl: './assets/app/partials/action-list.component.html',
    directives:  [],
    providers:   [ScriptService]
})
export class ActionListComponent {
    @Input()
    scriptData: ActiveScript;
    @Input()
    socket: WebSocket;
    cameras: Camera[] = [];
    presets: Preset[];
    // init to true so all cameras will be shown in the beginning
    selectedCameras: boolean[] = [true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true];
    constructor (private scriptService: ScriptService) {}
    ngOnChanges(item: any) {
        if (this.scriptData != null) {
            this.cameras = this.scriptData.script.location.cameras;
            console.log(this.cameras);
        }
    }
    toggleCamera(i: number) {
        this.selectedCameras[i] = !this.selectedCameras[i];
    }
    findPreset(array: Preset[], needle: Preset): boolean {
        for (var a of array) {
            if (a.id === needle.id) {
                return true;
            }
        }
        return false;
    }
    indexOfCamera(needle: number): number {
        for (var i = 0; i < this.cameras.length; i++) {
            if (this.cameras[i].id === needle) {
                return i;
            }
        }
        return -1;
    }
    clickAction(index: number) {
        this.scriptData.actionIndex = index;
        ScriptService.putScript(this.scriptData, this.socket);
    }
    makeSelected(index: number) {
        this.scriptData.selectedIndex = index;
    }
}
