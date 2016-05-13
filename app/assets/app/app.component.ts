import { HTTP_PROVIDERS }    from "angular2/http";
import {OnInit, Component} from "angular2/core";
import {Observable} from 'rxjs/Observable';


import {ScriptService} from "./api/script.service";
import {Script, ActiveScript} from "./api/script";
import {ActionListComponent} from "./actionlist.component";
import {PresetListComponent} from "./presetlist.component";
import {CameraListComponent} from "./cameralist.component";

@Component({
    selector: "script-dd",
    templateUrl: './assets/app/partials/director-main.component.html',
    directives: [ActionListComponent, PresetListComponent, CameraListComponent],
    providers:  [
        HTTP_PROVIDERS,
        ScriptService,
    ]
})
export class AppComponent implements OnInit {
    observable: Observable;
    socket: WebSocket;
    constructor (private _scriptService: ScriptService) { }
    currentScript: ActiveScript;

    ngOnInit() {
        this.connect();

    }
    onMessage(ev: MessageEvent) {
        console.log(ev);
        this.currentScript = JSON.parse(ev.data);
        console.log(this.currentScript);
    }
    connect() {
        this.socket = this._scriptService.connectScript();

        this.observable = Observable.create(observer =>
                this.socket.onmessage = (msg) => observer.next(msg)
        );

        this.observable.subscribe(
            (data) => {
                this.currentScript = JSON.parse(data.data);
                console.log(this.currentScript);
                console.log(this.currentScript.script.actions);
            },
            (error) => {
                console.log(error);
            },
            () => {
                console.log('completed');
            });
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
    /*getStatus(id: number) {
        this._scriptService.getStatus(id)
            .subscribe(
                currentScript => {this.currentScript = currentScript}
        );
    }*/
    startScript(id: number) {
        ScriptService.startScript(this.currentScript, this.socket);
    }
}