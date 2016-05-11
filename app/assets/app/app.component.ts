import { HTTP_PROVIDERS }    from "angular2/http";
import {OnInit, Component} from "angular2/core"

import {ScriptService} from "./api/script.service";
import {Script, ActiveScript} from "./api/script";
import {ScriptListComponent} from "./scriptlist.component";
import {ActionListComponent} from "./actionlist.component";
import {PresetListComponent} from "./presetlist.component";
import {CameraListComponent} from "./cameralist.component";


@Component({
    selector: "script-dd",
    templateUrl: './assets/app/partials/director-main.component.html',
    directives: [ScriptListComponent, ActionListComponent, PresetListComponent, CameraListComponent],
    providers:  [
        HTTP_PROVIDERS,
        ScriptService,
    ]
})
export class AppComponent implements OnInit {
    constructor (private _heroService: ScriptService) {}
    currentScript: ActiveScript;
    ngOnInit() { this.getStatus(); }
    getStatus() {
        this._heroService.getStatus()
            .subscribe(
                currentScript => {this.currentScript = currentScript}
        );
    }
}