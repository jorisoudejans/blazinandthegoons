import { HTTP_PROVIDERS }    from "angular2/http";
import {OnInit, Component} from "angular2/core";
import {Observable} from 'rxjs/Observable';


import {ScriptService} from "./api/script.service";
import {Script, ActiveScript} from "./api/script";
import {ScriptListComponent} from "./scriptlist.component";
import {ActionListComponent} from "./actionlist.component";
import {PresetListComponent} from "./presetlist.component";
import {CameraListComponent} from "./cameralist.component";

@Component({
    selector: "script-dd",
    templateUrl: './assets/app/partials/director-main.component.html',
    directives: [ScriptListComponent, ActionListComponent, PresetListComponent, CameraListComponent],
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

        this.observable = Observable.create((observer: any) =>
                this.socket.onmessage = (msg) => observer.next(msg)
        );

        this.observable.subscribe(
            (data) => {
                this.currentScript = JSON.parse(data.data);
                console.log(this.currentScript);
            },
            (error) => {
                console.log(error);
            },
            () => {
                console.log('completed');
            });
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