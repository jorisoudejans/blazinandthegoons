/// <reference path="../../../typings/jquery.d.ts" />
import {Preset} from "./api/preset";
import {Script} from "./api/script";
import {Component, Input, OnInit} from "angular2/core";

import {PresetService} from "./api/preset.service";

declare var jQuery:any;

@Component({
    selector:    'preset-list',
    templateUrl: './assets/app/partials/preset-list.component.html',
    directives:  [],
    viewProviders: [],
    providers:   [PresetService]
})
export class PresetListComponent implements OnInit {
    constructor (private _heroService: PresetService) {}
    @Input() scriptData: Script;
    pageX: number;
    pageY: number;
    draggin: boolean;
    presets: Preset[];
    ngOnInit() {
        this.getPresets();
        jQuery(document).ready(function() {
            setTimeout(function() {
                jQuery('.preset-list-item').draggable( {
                    cursor: 'move',
                    containment: 'document',
                    helper: "clone"
                })
            }, 1000)
        })
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
