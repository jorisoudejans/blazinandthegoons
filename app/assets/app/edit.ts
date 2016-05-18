/**
 * Created by floris on 18/05/2016.
 */
import {OnInit, Component, Input} from "angular2/core";
import {Observable} from 'rxjs/Observable';
import { HTTP_PROVIDERS }    from "angular2/http";
import {bootstrap}    from "angular2/platform/browser"


import {ScriptService} from "./api/script.service";
import {Script, ActiveScript} from "./api/script";
import 'rxjs/Rx';

@Component({
    selector: "edit",
    templateUrl: '../../assets/app/partials/edit.component.html',
    directives: [],
    providers:  [
        HTTP_PROVIDERS,
        ScriptService
    ]
})
export class Edit implements OnInit {
    constructor (private _scriptService: ScriptService) {}
    scriptid: number;
    scriptData: Script;
    errorMessage: string;
    ngOnInit() {
        var urlarr = window.location.href.split('/');
        this.scriptid = parseInt(urlarr[urlarr.length - 1]);
        this.getScript(this.scriptid);
        console.log(this.scriptData);
    }
    getScript(id: number) {
        this._scriptService.getScriptWithPrefix(id, '../')
            .subscribe(
                scriptData => this.scriptData = scriptData,
                error =>  this.errorMessage = <any>error);
    }
}

bootstrap(Edit);
