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
    location: Location;
    locationid: number;
    newCamera: Camera = new Camera(0, "", "");

    ngOnInit() {
        var urlarr = window.location.href.split('/');
        this.locationid = parseInt(urlarr[urlarr.length - 1]);

        this._scriptService.getLocation(this.locationid).subscribe(
                location => {
                    this.location = location;
                },
                error =>  console.log(error)
        );
    }
    addAction() {
        this._scriptService.addCamera(this.newCamera, this.location.id).subscribe(
                location => {
                    console.log(location);
                    this.location = location;
                    this.newCamera = new Camera(0, "", "") },
                error => {
                    console.log(error)
                }
        );
    }
    removeCamera(i: number) {
        this._scriptService.removeCamera(this.location.cameras[i], this.location.id).subscribe(
                location => {
                this.location = location;
            },
                error =>  console.log(error)
        );
    }

}

bootstrap(LocationComponent);
