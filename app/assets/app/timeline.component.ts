import {Script, ActiveScript} from "./api/script";
import {Action} from "./api/action";
import {Component, Input} from "angular2/core";
import {ScriptService} from "./api/script.service";
declare var jQuery:any;

@Component({
    selector:    'timeline',
    templateUrl: './assets/app/partials/timeline.component.html',
    directives:  [],
    providers:   [ScriptService]
})
export class TimelineComponent {
    @Input()
    scriptData: ActiveScript;
    @Input()
    socket: WebSocket;

    ngOnInit() {
        setTimeout(function(){ jQuery('[data-toggle="tooltip"]').tooltip() }, 100);
    }

    ngOnChanges() {
        if (this.scriptData != null) {
            var totalTime = 0;
            for (var action of this.scriptData.script.actions) {
                totalTime += action.duration;
            }
            for (var action of this.scriptData.script.actions) {
                function random(seed: number) {
                    var x = Math.sin(seed++) * 10000;
                    return x - Math.floor(x);
                }

                var perc = action.duration / totalTime * 100;
                action.percentage = perc + "%";
                action.color = "#00" + Math.round(113 + random(action.duration) * 70).toString(16) + "00";
            }

            this.scriptData.percentage = this.scriptData.runningTime / totalTime + "%";
        }
    }

    clickAction(index: number) {
        this.scriptData.actionIndex = index;
        ScriptService.putScript(this.scriptData, this.socket);
    }

}
