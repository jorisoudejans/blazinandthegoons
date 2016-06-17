import {Script, ActiveScript} from "./api/script";
import {Component, Input} from "angular2/core";
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
    flagActionId: number;
    errorMessage: string;

    constructor (private scriptService: ScriptService) {}

    clickAction(index: number) {
        this.scriptData.actionIndex = index;
        ScriptService.putScript(this.scriptData, this.socket);
    }

    setFlagId(i:number) {
        this.flagActionId = i;
    }

    flagAction() {
        this.scriptData.script.actions[this.flagActionId].flagged = true;
        this.scriptData.script.actions[this.flagActionId].flagDescription = jQuery('#flagModal textarea').val();
        this.cleanUpModal();
        console.log(this.scriptData.script);
        this.scriptService.saveScript(this.scriptData.script)
            .map((script: Script) => {
                if(script) {
                    script.actions.sort(function(a:Action, b:Action) {
                        return a.index - b.index;
                    })
                }
                return script;
            })
            .subscribe(
                scriptData => {this.scriptData.script = scriptData},
                error =>  this.errorMessage = <any>error);
    }

    cleanUpModal() {
        jQuery('#flagModal textarea').val('');
    }

}
