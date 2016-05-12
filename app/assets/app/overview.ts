/**
 * Created by floris on 11/05/2016.
 */
import {OnInit, Component} from "angular2/core";
import { HTTP_PROVIDERS }    from "angular2/http";
import {bootstrap}    from "angular2/platform/browser"
import {ScriptListComponent} from "./scriptlist.component"
import {ScriptService} from "./api/script.service";
import {Script, ActiveScript} from "./api/script";
import 'rxjs/Rx';

@Component({
    selector: "overview",
    templateUrl: './assets/app/partials/overview.component.html',
    directives: [ScriptListComponent],
    providers:  [
        HTTP_PROVIDERS,
        ScriptService
    ]
})
export class Overview implements OnInit {
    constructor (private _heroService: ScriptService) {}
    errorMessage: string;
    scripts:Script[];
    scriptData: Script;
    activeScript: ActiveScript;
    ngOnInit() { this.getScripts(); this.getActive();}
    getScripts() {
        this._heroService.getScripts()
            .subscribe(
                scripts => this.scripts = scripts,
                error =>  this.errorMessage = <any>error);
    }
    getActive() {
        this._heroService.getStatus(1)
            .subscribe(
                activeScript => this.activeScript = activeScript,
                error =>  this.errorMessage = <any>error);
    }
    showScript(id: number) {
        this._heroService.getScript(id)
            .subscribe(
                scriptData => this.scriptData = scriptData
            );
    }

    activateScript(id: number) {
        this._heroService.startScript(id)
            .subscribe(
                activeScript => this.activeScript = activeScript    ,
                error =>  this.errorMessage = <any>error);
    }

    gotoview(which: string) {
        if(this.activeScript == null) {
            document.location.href = './'+which;
        } else {
            alert('An active script is needed in order to perfom this action.');
        }
    }
}

bootstrap(Overview);