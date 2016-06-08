/**
 * Created by floris on 11/05/2016.
 */
import {OnInit, Component} from "angular2/core";
import {Observable} from 'rxjs/Observable';
import { HTTP_PROVIDERS }    from "angular2/http";
import {bootstrap}    from "angular2/platform/browser"

import {ScriptService} from "./api/script.service";
import {Script, ActiveScript, Location} from "./api/script";
import 'rxjs/Rx';

@Component({
    selector: "overview",
    templateUrl: './assets/app/partials/overview.component.html',
    directives: [],
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
    locations: Location[];
    activeScript: ActiveScript;
    ngOnInit() {
        this.connect();
        this.getData();
    }
    getData() {
        // Get the script and location data
        this._scriptService.getScripts()
            .subscribe(
                scripts => {this.scripts = scripts; },
                error =>  this.errorMessage = <any>error);
        this._scriptService.getLocations()
            .subscribe(
                locations => {this.locations = locations; },
                error =>  this.errorMessage = <any>error);
    }
    connect() {
        this.socket = this._scriptService.connectScript();

        this.observable = Observable.create((observer: any) =>
            this.socket.onmessage = (msg) => observer.next(msg)
        );

        this.observable.subscribe(
            (data) => {
                var response = JSON.parse(data.data);
                if (Object.prototype.toString.call( response ) !== '[object Array]') {
                    this.activeScript = response;
                } else {
                    this.activeScript = null;
                }
                console.log("Data: " + data.data);
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
        ScriptService.startScript(newscript.id, this.socket);
    }
    deactivateScript() {
        ScriptService.stopScript(this.socket);
    }
    gotoview(which: string) {
        if(this.activeScript == null) {
            document.location.href = './'+which;
        } else {
            alert('An active script is needed in order to perfom this action.');
        }
    }
    addLocation() {
        name = prompt("Location name", "Location1");
        this._scriptService.addLocation(name).subscribe(
                location => document.location.href = '/locations/' + location.id,
                error =>  this.errorMessage = <any>error
        );

    }
}

bootstrap(Overview);