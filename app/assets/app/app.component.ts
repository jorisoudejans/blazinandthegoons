import { HTTP_PROVIDERS }    from "angular2/http";
import {OnInit, Component} from "angular2/core";
import {Observable} from 'rxjs/Observable';

import {ScriptService} from "./api/script.service";
import {Action} from "./api/action";
import {Script, ActiveScript} from "./api/script";
import {ActionListComponent} from "./actionlist.component";
import {ScriptListComponent} from "./scriptlist.component";
import {PresetListComponent} from "./presetlist.component";
import {CameraListComponent} from "./cameralist.component";
import {TimelineComponent} from "./timeline.component";

@Component({
    selector: "script-dd",
    templateUrl: './assets/app/partials/director-main.component.html',
    directives: [ScriptListComponent, ActionListComponent, PresetListComponent, CameraListComponent, TimelineComponent],
    providers:  [
        HTTP_PROVIDERS,
        ScriptService,
    ]
})
export class AppComponent implements OnInit {
    observable: Observable<MessageEvent>;
    socket: WebSocket;
    constructor (private _scriptService: ScriptService) { }
    currentScript: ActiveScript;
    errorMessage: string;
    connectionTries: number;
    connectionTimeout: number;

    ngOnInit() {
        this.connectionTries = 0;
        this.connect();

    }
    onMessage(ev: MessageEvent) {
        console.log(ev);
        this.currentScript = JSON.parse(ev.data);
        console.log(this.currentScript);
    }
    connect() {
        console.log("Trying to connect...");
        this.socket = this._scriptService.connectScript();

        this.observable = Observable.create((observer: any) => {
                    this.socket.onmessage = (msg) => observer.next(msg);
                    this.socket.onclose = (msg) => observer.error(msg);
                    this.socket.onerror = (msg) => observer.error(msg);
            }
        );

        this.observable.subscribe(
            (data) => {
                this.connectionTries = 0;
                this.errorMessage = null;
                var response = JSON.parse(data.data);
                if (Object.prototype.toString.call( response ) !== '[object Array]') {
                    this.currentScript = response;
                    this.currentScript.script.actions.sort(function(a:Action, b:Action) {
                        return a.index - b.index;
                    })
                } else {
                    this.currentScript = null;
                }
                console.log(this.currentScript);
            },
            (error) => {
                // show error message
                this.retryConnect();
                console.log(error);
            },
            () => {
                console.log('completed');
            });
    }
    retryConnect() {
        this.connectionTries++;
        this.connectionTimeout = this.connectionTries * 5;
        setTimeout(() => this.connect(), Math.min(this.connectionTimeout, 30) * 1000);
        this.setErrorMessage();
    }
    setErrorMessage() {
        this.errorMessage = "Connection to server was lost. Retrying in " + (this.connectionTimeout--) + " seconds...";
        if (this.connectionTimeout > 0) {
            setTimeout(() => this.setErrorMessage(), 1000);
        }
    }
    advance(c: number) {
        if (this.currentScript.actionIndex + c < 0) {
            this.currentScript.actionIndex = 0;
        } else {
            this.currentScript.actionIndex = this.currentScript.actionIndex + c;
        }
        this.save();
    }
    stop() {
        ScriptService.stopScript(this.socket);
    }
    getSelectedActionIndex() {
        if (this.currentScript.selectedIndex === undefined) {
            return this.currentScript.actionIndex;
        } else {
            return this.currentScript.selectedIndex;
        }
    }
    setFlagged() {
        // ugly method to set boolean flag...
        var action = this.currentScript.script.actions[this.currentScript.actionIndex];
        action.flagged = !!((action.flagDescription !== null && action.flagDescription !== "") || action.flagType !== null);
    }
    save() {
        ScriptService.putScript(this.currentScript, this.socket);
    }
}