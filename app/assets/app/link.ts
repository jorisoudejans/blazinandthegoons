import {OnInit, Component, Input} from "angular2/core";
import {Observable} from 'rxjs/Observable';
import { HTTP_PROVIDERS }    from "angular2/http";
import {bootstrap}    from "angular2/platform/browser"
import {Router, ROUTER_PROVIDERS}     from "angular2/router"


import {ScriptService} from "./api/script.service";
import {Script, ActiveScript} from "./api/script";
import {Action} from "./api/action";
import {PresetListComponent} from "./presetlist.component";
import {Preset} from "./api/preset";
import {PresetService} from "./api/preset.service";

import 'rxjs/Rx';

@Component({
    selector: "linker",
    templateUrl: '../../assets/app/partials/link.component.html',
    directives: [PresetListComponent],
    providers:  [
        HTTP_PROVIDERS,
        ScriptService,
        PresetService,
        ROUTER_PROVIDERS
    ]
})
export class Link implements OnInit {
    constructor (private _scriptService: ScriptService, private _presetService: PresetService, private router: Router) {}
    scriptId: number;
    presets: Preset[];
    activeIndex: number;
    scriptData: Script;
    ngOnInit() {
        var urlarr = window.location.href.split('/');
        this.scriptId = parseInt(urlarr[urlarr.length - 1]);
        this.getScript(this.scriptId);
    }
    getScript(id: number) {
        this._scriptService.getScriptWithPrefix(id, '../')
            .subscribe(
                scriptData => {
                    this.scriptData = scriptData;
                    this.presets = scriptData.presets;
                });
    }
    linkPreset(preset: Preset, val: number) {
        this._presetService.linkPreset(preset.id, val)
            .subscribe(
                linkedPreset => {
                    var newPresets: Preset[] = [];
                    this.presets.forEach(oldPreset => {
                        if (oldPreset.id === linkedPreset.id) {
                            newPresets.push(linkedPreset);
                            console.log(preset);
                        } else {
                            newPresets.push(oldPreset);
                        }
                    });
                    this.presets = newPresets;
                }
            )
    }
    unlinkPreset(preset: Preset) {
        this._presetService.unlinkPreset(preset.id)
            .subscribe(
                unlinkedPreset => {
                    var newPresets: Preset[] = [];
                    this.presets.forEach(oldPreset => {
                        if (oldPreset.id === unlinkedPreset.id) {
                            newPresets.push(unlinkedPreset);
                            console.log(preset);
                        } else {
                            newPresets.push(oldPreset);
                        }
                    });
                    this.presets = newPresets;
                }
            )
    }
    isLinked(preset: Preset) {
        return preset.cameraId !== 0;
    }
    makeActive(index: number) {
        this.activeIndex = index;
    }
}

bootstrap(Link);
