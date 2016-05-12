import { HTTP_PROVIDERS }    from "angular2/http";
import {OnInit, Component} from "angular2/core"

import {ScriptService} from "./api/script.service";
import {Script, ActiveScript} from "./api/script";
import {ActionListComponent} from "./actionlist.component";
import {PresetListComponent} from "./presetlist.component";
import {CameraListComponent} from "./cameralist.component";


@Component({
    selector: "script-dd",
    templateUrl: './assets/app/partials/director-main.component.html',
    directives: [ActionListComponent, PresetListComponent, CameraListComponent],
    providers:  [
        HTTP_PROVIDERS,
        ScriptService,
    ]
})
export class AppComponent implements OnInit {
    constructor (private _heroService: ScriptService) {}
    currentScript: ActiveScript;
    ngOnInit() { this.startScript(1); }
    getStatus(id: number) {
        this._heroService.getStatus(id)
            .subscribe(
                currentScript => {this.currentScript = currentScript}
        );
    }
    startScript(id: number) {
        this._heroService.startScript(id)
            .subscribe(
                currentScript => {this.currentScript = currentScript}
        );
    }
}