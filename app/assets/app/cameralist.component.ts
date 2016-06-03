import {Preset} from "./api/preset";
import {Component, Input, OnInit} from "angular2/core"

import {PresetService} from "./api/preset.service";
import {ActiveScript} from "./api/script";

@Component({
    selector:    'camera-list',
    templateUrl: './assets/app/partials/camera-list.component.html',
    directives:  [],
    providers:   [PresetService]
})
export class CameraListComponent {
    @Input()
    scriptData: ActiveScript;
    constructor (private _heroService: PresetService) {}
    presets: Preset[];
    ngOnInit() { this.getPresets(); }

    playVideo(i: number, movieplayer: HTMLVideoElement) {
        if (i === this.scriptData.actionIndex)
            movieplayer.play();
        else
            movieplayer.pause();
        return i !== this.scriptData.actionIndex;
    }

    getPresets() {
        this._heroService.getPresets()
            .subscribe(
                presets => this.presets = presets
            );
    }
}