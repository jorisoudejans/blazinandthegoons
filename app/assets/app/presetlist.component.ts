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
        this.presets = this.scriptData.script.presets;
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
        this.updateDragListeners();
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
        return 1000;
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
    createPreset() {
        var preset:Preset = new Preset(null, $('#presetModal .description').val(), $('#presetModal textarea').val(), null, null, null, null, null, null, null, null);
        console.log(preset);
        this.scriptData.script.presets.push(preset);
        this.presets = this.scriptData.script.presets;
        this.cleanUpModal();
        this.updateDragListeners();
    }
    cleanUpModal() {
        $('#presetModal .description').val('');
        $('#presetModal textarea').val('');
    }
}
