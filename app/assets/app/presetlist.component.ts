/// <reference path="../../../typings/jquery.d.ts" />
import {Preset} from "./api/preset";
import {Component, Input, OnInit} from "angular2/core";/*
import {Dragula} from 'ng2-dragula/src/app/directives/dragula.directive';
import {DragulaService} from 'ng2-dragula/src/app/providers/dragula.provider';*/

import {PresetService} from "./api/preset.service";

@Component({
    selector:    'preset-list',
    templateUrl: './assets/app/partials/preset-list.component.html',
    directives:  [],
    viewProviders: [],
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
    activatePreset(id: number) {
        this._heroService.activatePreset(id)
            .subscribe(
                res => console.log("Activation result: " + res)
            );

        // save thumbnail to image
        $('#preset-image-'+id).attr("src", "api/presets/" + id + "/thumbnail?" + (new Date()).getTime());
    }
}
