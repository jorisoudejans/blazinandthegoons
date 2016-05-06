import { HTTP_PROVIDERS }    from "angular2/http";
import {OnInit, Component} from "angular2/core"

import {ScriptService} from "./api/script.service";
import {Script} from "./api/script";
import {ScriptListComponent} from "./scriptlist.component";
import {ActionListComponent} from "./actionlist.component";
import {PresetListComponent} from "./presetlist.component";


@Component({
    selector: "script-dd",
    templateUrl: './assets/app/partials/director-main.component.html',
    directives: [ScriptListComponent, ActionListComponent, PresetListComponent],
    providers:  [
        HTTP_PROVIDERS,
        ScriptService,
    ]
})
export class AppComponent implements OnInit {
    constructor (private _heroService: ScriptService) {}
    currentScript: Script;
    ngOnInit() { this.getScript(1); }
    getScript(id: number) {
        this._heroService.getScript(id)
            .subscribe(
                currentScript => {this.currentScript = currentScript}
            );
    }
}