/**
 * Created by floris on 11/05/2016.
 */
import {OnInit, Component} from "angular2/core";
import { HTTP_PROVIDERS }    from "angular2/http";
import {bootstrap}    from "angular2/platform/browser"
import {ScriptListComponent} from "./scriptlist.component"
import {ScriptService} from "./api/script.service";
import {Script, ActiveScript} from "./api/script";
import 'rxjs/Rx';

@Component({
    selector: "overview",
    templateUrl: './assets/app/partials/overview.component.html',
    directives: [ScriptListComponent],
    providers:  [
        HTTP_PROVIDERS,
        ScriptService
    ]
})
export class Overview implements OnInit {
    socket: WebSocket;
    constructor (private _scriptService: ScriptService) {}
    errorMessage: string;
    scripts:Script[];
    scriptData: Script;
    activeScript: ActiveScript;
    ngOnInit() {
        this.connect();
        this.getScripts();
        this.socket.onmessage = this.onMessage;
    }
    getScripts() {
        this._scriptService.getScripts()
            .subscribe(
                scripts => this.scripts = scripts,
                error =>  this.errorMessage = <any>error);
    }
    onMessage(ev: MessageEvent) {
        console.log(ev);
        this.activeScript = JSON.parse(ev.data);
    }
    connect() {
        this.socket = this._scriptService.connectScript();
        /*let socket = this.socket;
         this.socket.onopen = function(ev) {
         console.log(ev);
         socket.send("hoi");
         };
         this.socket.onmessage = function(ev) {
         console.log(ev);
         console.log("Received data from websocket: ", ev.data);
         };
         this.socket.onerror = function(ev) {
         console.log("Error from websocket: ", ev.data);
         };
         console.log(this.socket);*/
    }

    makeActive(newscript: Script) {
        this.activeScript = new ActiveScript();
        this.activeScript.script = newscript;
        this.socket.send(JSON.stringify(this.activeScript));
    }

    gotoview(which: string) {
        if(this.activeScript == null) {
            document.location.href = './'+which;
        } else {
            alert('An active script is needed in order to perfom this action.');
        }
    }
}

bootstrap(Overview);