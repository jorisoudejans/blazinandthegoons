import {Script, ActiveScript} from "./api/script";
import {Component, Input, OnInit} from "angular2/core";
import {ScriptService} from "./api/script.service";

@Component({
    selector:    'script-list',
    templateUrl: './assets/app/partials/script-list.component.html',
    directives:  [],
    providers:   []
})
export class ScriptListComponent implements OnInit {
    constructor (private _heroService: ScriptService) {}
    errorMessage: string;
    scripts:Script[];
    scriptData: Script;
    ngOnInit() { this.getScripts(); }
    getScripts() {
        this._heroService.getScripts()
            .subscribe(
                scripts => this.scripts = scripts,
                error =>  this.errorMessage = <any>error);
    }
}
