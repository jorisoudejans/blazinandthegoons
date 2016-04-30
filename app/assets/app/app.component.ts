import {Component} from "angular2/core"
import { HTTP_PROVIDERS }    from "angular2/http";

import {ScriptService} from "./api/script.service";
import {ScriptListComponent} from "./scriptlist.component";
import {ScriptAddComponent} from "./scriptadd.component";


@Component({
    selector: "script-dd",
    template: "<h1>My First Angular 2 App for Blazin and the Goons!</h1><script-add></script-add><script-list></script-list>",
    directives: [ScriptListComponent, ScriptAddComponent],
    providers:  [
        HTTP_PROVIDERS,
        ScriptService,
    ]
})
export class AppComponent { }