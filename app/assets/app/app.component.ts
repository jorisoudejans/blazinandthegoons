import {Component} from "angular2/core"
import { HTTP_PROVIDERS }    from "angular2/http";

import {ScriptService} from "./api/script.service";
import {ScriptListComponent} from "./scriptlist.component";

@Component({
    selector: "script-dd",
    template: "<h1>My First Angular 2 App for Blazin and the Goons!</h1><script-list></script-list>",
    directives: [ScriptListComponent],
    providers:  [
        HTTP_PROVIDERS,
        ScriptService,
    ]
})
export class AppComponent { }