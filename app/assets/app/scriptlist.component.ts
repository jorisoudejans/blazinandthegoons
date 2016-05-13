import {Script, ActiveScript} from "./api/script";
import {Component, Input} from "angular2/core";

@Component({
    selector:    'script-list',
    templateUrl: './assets/app/partials/script-list.component.html',
    directives:  [],
    providers:   []
})
export class ScriptListComponent {
    @Input() scripts:Script[];
    @Input() scriptData: Script;
    @Input() activeScript: ActiveScript;

    activateScript(script: Script) {
        this.activeScript = new ActiveScript();
        this.activeScript.script = script;
    }

    deactivateScript() {
        this.activeScript = null;
    }
}
