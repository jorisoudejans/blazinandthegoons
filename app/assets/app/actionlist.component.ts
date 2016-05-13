import {Script, ActiveScript} from "./api/script";
import {Component, Input} from "angular2/core";
import {ScriptService} from "./api/script.service";


@Component({
    selector:    'action-list',
    templateUrl: './assets/app/partials/action-list.component.html',
    directives:  [],
    providers:   [ScriptService]
})
export class ActionListComponent {
    @Input()
    scriptData: ActiveScript;
    @Input()
    socket: WebSocket;

    clickAction(index: number) {
        this.scriptData.actionIndex = index;
        ScriptService.putScript(this.scriptData, this.socket);
    }

}
