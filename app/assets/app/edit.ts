/**
 * Created by floris on 18/05/2016.
 */
import {OnInit, Component, Input} from "angular2/core";
import {Observable} from 'rxjs/Observable';
import { HTTP_PROVIDERS }    from "angular2/http";
import {bootstrap}    from "angular2/platform/browser"


import {ScriptService} from "./api/script.service";
import {Script, ActiveScript} from "./api/script";
import {Action} from "./api/action";
import {PresetListComponent} from "./presetlist.component";
import {Preset} from "./api/preset";
import {PresetService} from "./api/preset.service";

import 'rxjs/Rx';

@Component({
    selector: "edit",
    templateUrl: '../../assets/app/partials/edit.component.html',
    directives: [PresetListComponent],
    providers:  [
        HTTP_PROVIDERS,
        ScriptService,
        PresetService
    ]
})
export class Edit implements OnInit {
    constructor (private _scriptService: ScriptService, private _presetService: PresetService) {}
    scriptid: number;
    presets: Preset[];
    scriptData: Script;
    errorMessage: string;
    ngOnInit() {
        var urlarr = window.location.href.split('/');
        this.scriptid = parseInt(urlarr[urlarr.length - 1]);
        this.getScript(this.scriptid);
        this.getPresets();
    }
    getScript(id: number) {
        this._scriptService.getScriptWithPrefix(id, '../')
            .map((script: Script) => {
                if(script) {
                    script.actions.forEach((action) => {
                        action.active = false;
                    })
                }
                return script;
            })
            .subscribe(
                scriptData => this.scriptData = scriptData,
                error =>  this.errorMessage = <any>error);
    }
    saveScript() {
        this._scriptService.updateScript(this.scriptData)
            .subscribe(
                scriptData => this.scriptData = scriptData,
                error =>  this.errorMessage = <any>error);
        console.log("BINNENGEKOMEN DATA");
        console.log(this.scriptData);
    }
    getPresets() {
        this._presetService.getPresets()
            .subscribe(
                presets => this.presets = presets
            );
    }
    updateAction(action: Action, event:string) {
        var corrPreset: Preset = null;
        this.presets.forEach((preset) => {
            if(preset.name === event)
                corrPreset = preset;
        })
        action.preset = corrPreset;
    }
}

bootstrap(Edit);
