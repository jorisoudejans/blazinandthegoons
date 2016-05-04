import {Script} from "./api/script";
import {ScriptService} from "./api/script.service";
import {OnInit, Component} from "angular2/core"

@Component({
    selector:    'script-list',
    templateUrl: './assets/app/partials/script-list.component.html',
    directives:  [],
    providers:   [ScriptService]
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
    showScript(id: number) {
        this._heroService.getScript(id)
            .subscribe(
                scriptData => this.scriptData = scriptData
        );
    }
}