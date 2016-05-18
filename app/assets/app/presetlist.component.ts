import {Preset} from "./api/preset";
import {Component, Input, OnInit} from "angular2/core"

import {PresetService} from "./api/preset.service";

@Component({
    selector:    'preset-list',
    templateUrl: './assets/app/partials/preset-list.component.html',
    directives:  [],
    providers:   [PresetService]
})
export class PresetListComponent implements OnInit {
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
