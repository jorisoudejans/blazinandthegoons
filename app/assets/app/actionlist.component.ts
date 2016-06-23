import {Script, ActiveScript} from "./api/script";
import {Component, Input, Output, EventEmitter} from "angular2/core";
import {ScriptService} from "./api/script.service";
import {Action} from "./api/action"

declare var jQuery:any;

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
    @Output() actionSelect = new EventEmitter();
    flagActionId: number;

    constructor (private scriptService: ScriptService) {}

    clickAction(index: number) {
        this.scriptData.actionIndex = index;
        ScriptService.putScript(this.scriptData, this.socket);
    }
    makeSelected(index: number) {
        this.scriptData.selectedIndex = index;
    }
}
