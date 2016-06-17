import {Script} from "./api/script";
import {ScriptService} from "./api/script.service";
import {OnInit, Component} from "angular2/core"

@Component({
    selector:    'script-add',
    templateUrl: './assets/app/partials/script-add.component.html',
    directives:  [],
    providers:   [ScriptService]
})
export class ScriptAddComponent {
    constructor (private _heroService: ScriptService) {}
    model = new Script(0, "New script", "vandaag", [], null, []);
    submitted = false;
    onSubmit() {
        console.log("Hello");
        this.submitted = true;
        this._heroService.createScript(this.model.name).subscribe(
                script  => console.log("Script "+script),
                error =>  console.log(error));
    }
}
