/// <reference path="../../../typings/jquery.d.ts" />
import {Preset} from "./api/preset";
import {Component, Input, OnInit} from "angular2/core";

import {PresetService} from "./api/preset.service";
import {ActiveScript, Script, Location, Camera} from "./api/script";

declare var jQuery:any;

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
    // init to true so all cameras will be shown in the beginning
    selectedCameras: boolean[] = [true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true];
    ngOnChanges(item: any) {
        console.log(this.scriptData);
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
        this.updateDragListeners();
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
    getCameraIndex(id: number): number {
        for (var i = 0; i < this.cameras.length; i++) {
            if (this.cameras[i].id === id) {
                return i;
            }
        }
        return -1;
    }
    updateDragListeners() {
        console.log("Here dem prestes be");
        console.log(jQuery('.preset-list-item .inner-item'));
        jQuery(document).ready(function() {
            setTimeout(function() {
                jQuery('.preset-list-item .inner-item').draggable( {
                    cursor: 'move',
                    containment: 'document',
                    helper: "clone"
                })
            }, 1000)
        })
    }
}
