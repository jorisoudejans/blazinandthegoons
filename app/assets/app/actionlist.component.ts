import {Script, ActiveScript} from "./api/script";
import {Component, Input} from "angular2/core"

@Component({
    selector:    'action-list',
    templateUrl: './assets/app/partials/action-list.component.html',
    directives:  [],
    providers:   []
})
export class ActionListComponent {
    @Input()
    scriptData: ActiveScript;
}
