/**
 * Created by floris on 04/05/2016.
 */
import {Script} from "./api/script";
import {Component, Input} from "angular2/core"

@Component({
    selector:    'preset-list',
    templateUrl: './assets/app/partials/action-list.component.html',
    directives:  [],
    providers:   []
})
export class PresetListComponent {
    @Input()
    scriptData: Script;
}
