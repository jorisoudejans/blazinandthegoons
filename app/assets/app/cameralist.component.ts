import {Preset} from "./api/preset";
import {Component, Input, OnInit} from "angular2/core"

import {PresetService} from "./api/preset.service";

@Component({
    selector:    'camera-list',
    templateUrl: './assets/app/partials/camera-list.component.html',
    directives:  [],
    providers:   [PresetService]
})
export class CameraListComponent implements OnInit {
    constructor (private _heroService: PresetService) {}
    presets: Preset[];
    ngOnInit() { this.getPresets(); }
    getPresets() {
        this._heroService.getPresets()
            .subscribe(
                presets => this.presets = presets
            );
    }
}