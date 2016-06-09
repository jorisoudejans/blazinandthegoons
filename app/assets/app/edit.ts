/**
 * Created by floris on 18/05/2016.
 */
import {OnInit, Component, Input} from "angular2/core";
import {Observable} from 'rxjs/Observable';
import { HTTP_PROVIDERS }    from "angular2/http";
import {bootstrap}    from "angular2/platform/browser"
import {Router, ROUTER_PROVIDERS}     from "angular2/router"
import {Dragula, DragulaService}     from "ng2-dragula/ng2-dragula"


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
    directives: [PresetListComponent, Dragula],
    providers:  [
        HTTP_PROVIDERS,
        ScriptService,
        PresetService,
        ROUTER_PROVIDERS,
    ],
    viewProviders: [DragulaService]
})
export class Edit implements OnInit {
    constructor (private _scriptService: ScriptService, private _presetService: PresetService, private router: Router) {}
    scriptid: number;
    presets: Preset[];
    scriptData: Script;
    errorMessage: string;
    actionInsertPos: number;
    ngOnInit() {
        var urlarr = window.location.href.split('/');
        this.scriptid = parseInt(urlarr[urlarr.length - 1]);
        if(urlarr[urlarr.length - 1] !== 'edit')
            this.getScript(this.scriptid);
        else
            this.buildNewScript();
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
        this._scriptService.saveScript(this.scriptData)
            .subscribe(
                scriptData => this.scriptData = scriptData,
                error =>  this.errorMessage = <any>error);
        console.log("BINNENGEKOMEN DATA");
        console.log(this.scriptData);
        var base = 'http://' + location.hostname + (location.port ? ':'+location.port: '')
        window.location.href = base;
    }
    getPresets() {
        this._presetService.getPresets()
            .subscribe(
                presets => this.presets = presets
            );
    }
    updateAction(action: Action, event:string) {
        var corrPreset:Preset = null;
        this.presets.forEach((preset) => {
            if (preset.name === event)
                corrPreset = preset;
        })
        action.preset = corrPreset;
    }
    addAction() {
        var corrPreset:Preset = null;
        this.presets.forEach((preset) => {
            if (preset.name === $('#actionModal .preset').val())
                corrPreset = preset;
        })
        var act = new Action(null, this.actionInsertPos+1, $('#actionModal .description').val(), $('#actionModal .duration').val(), corrPreset);
        this.scriptData.actions.splice(this.actionInsertPos+1, 0, act);
        this.fixActionIndices();
        this.cleanUpModal();
    }
    deleteAction(index: number) {
        console.log('ja');
        console.log(index);
        this.scriptData.actions.splice(index, 1);
        this.fixActionIndices();
    }
    fixActionIndices() {
        for(var i = 0; i < this.scriptData.actions.length; i++) {
            this.scriptData.actions[i].index = i;
        }
        console.log(this.scriptData.actions);
    }
    cleanUpModal() {
        $('#actionModal .description').val('');
        $('#actionModal .duration').val('');
        $('#actionModal .preset').val('');
        this.actionInsertPos == null;
    }
    selectInsertPos(index:number) {
        this.actionInsertPos = index;
    }
    buildNewScript() {
        this.scriptData = new Script(-1, "new Script", (new Date()).toString(), [new Action(null, 0, "Mock action", 5, new Preset(null, " Mock preset", 0,0,0,0,0))]);
    }
}

bootstrap(Edit);
