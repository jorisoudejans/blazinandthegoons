/**
 * Created by floris on 11/05/2016.
 */
import {OnInit, Component} from "angular2/core";
import {Observable} from 'rxjs/Observable';
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
    observable: Observable<MessageEvent>;
    socket: WebSocket;
    constructor (private _scriptService: ScriptService) {}
    errorMessage: string;
    scripts:Script[];
    scriptData: Script;
    activeScript: ActiveScript;
    ngOnInit() {
        this.connect();
        this.getScripts();
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
        console.log(this.activeScript);
    }
    connect() {
        this.socket = this._scriptService.connectScript();

        this.observable = Observable.create((observer: any) =>
            this.socket.onmessage = (msg) => observer.next(msg)
        );

        this.observable.subscribe(
            (data) => {
                this.activeScript = JSON.parse(data.data);
                console.log(data.data);
                console.log(this.activeScript);
            },
            (error) => {
                console.log(error);
            },
            () => {
                console.log('completed');
            });
    }

    makeActive(newscript: Script) {
        var scr = new ActiveScript(newscript, 0, 0);
        console.log("MAKE NEW ACTIONSCRIPT:");
        console.log(JSON.stringify(scr));
        this.socket.send(JSON.stringify(scr));
    }
    editScript(script: Script) {
        document.location.href = './edit/'+script.id;
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