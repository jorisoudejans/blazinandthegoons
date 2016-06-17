/**
 * Created by floris on 18/05/2016.
 */
import {OnInit, Component, Input} from "angular2/core";
import {Observable} from 'rxjs/Observable';
import { HTTP_PROVIDERS }    from "angular2/http";
import {bootstrap}    from "angular2/platform/browser"
import {Router, ROUTER_PROVIDERS}     from "angular2/router"


import {ScriptService} from "./api/script.service";
import {Script, ActiveScript, Location, Camera} from "./api/script";

import 'rxjs/Rx';

@Component({
    selector: "location",
    templateUrl: '../../assets/app/partials/location.component.html',
    directives: [],
    providers:  [
        HTTP_PROVIDERS,
        ScriptService,
        ROUTER_PROVIDERS
    ]
})
export class LocationComponent implements OnInit {
    constructor (private _scriptService: ScriptService) {}
    location: Location = new Location(0, null, null);
    newCamera: Camera = new Camera(0, "", "");
    hasChanges: boolean = false;

    ngOnInit() {
        var urlarr = window.location.href.split('/');
        this.location.id = parseInt(urlarr[urlarr.length - 1]);

        this._scriptService.getLocation(this.location.id).subscribe(
                location => {
                    this.location = location;
                },
                error => null // do nothing
        );
    }
    addLocation() {
        this._scriptService.addLocation(this.location.name).subscribe(
            location => {
                window.history.pushState("", "", '/locations/'+location.id);
                this.location = location;
            },

            error =>  console.log(error)
        );
    }
    save() {
        this._scriptService.updateLocation(this.location).subscribe(
            location => {
                console.log(location);
                this.location = location;
                this.hasChanges = false;
            },
            error => {
                console.log(error)
            }
        );
    }
    addAction() {
        this.location.cameras.push(this.newCamera);
        this.newCamera = new Camera(0, "", "");
        this.hasChanges = true;
    }
    removeCamera(i: number) {
        this.location.cameras.splice(i, 1);
        this.hasChanges = true;
    }
}

bootstrap(LocationComponent);
