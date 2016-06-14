/// <reference path="../../../typings/jquery.d.ts" />
import {Preset} from "./api/preset";
import {Component, Input, OnInit} from "angular2/core";/*
import {Dragula} from 'ng2-dragula/src/app/directives/dragula.directive';
import {DragulaService} from 'ng2-dragula/src/app/providers/dragula.provider';*/

import {PresetService} from "./api/preset.service";
import {ActiveScript, Script, Location, Camera} from "./api/script";

@Component({
    selector:    'preset-list',
    templateUrl: './assets/app/partials/preset-list.component.html',
    directives:  [],
    viewProviders: [],
    providers:   [PresetService]
})
export class PresetListComponent {
    @Input() scriptData: ActiveScript;
    cameras: Camera[] = [];
    constructor (private _heroService: PresetService) {}
    presets: Preset[];
    selectedCameras: boolean[] = [];
    ngOnChanges(item: any) {
        this.preparePresets();
        this.prepareCameras();
    }
    preparePresets() {
        if (!this.scriptData) {
            return;
        }
        var p: Preset[] = [];
        for (var a of this.scriptData.script.actions) {
            if (!this.findPreset(p, a.preset)) {
                p.push(a.preset);
            }
        }
        this.presets = p;
        console.log(this.presets);
    }
    prepareCameras() {
        if (!this.scriptData) {
            return;
        }
        this.cameras = this.scriptData.script.location.cameras;
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
