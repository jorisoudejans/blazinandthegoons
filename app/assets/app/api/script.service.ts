import {Injectable}     from "angular2/core";
import {Http, Response, Headers, RequestOptions} from "angular2/http";
import {Script, ActiveScript}           from "./script";
import {Observable}     from "rxjs/Observable";

@Injectable()
export class ScriptService {
    constructor (private http: Http) {
    }
    private _heroesUrl = "api/scripts";  // URL to web api
    connectScript (): WebSocket {
        var base = location.hostname + (location.port ? ':'+location.port: '');
        return new WebSocket("ws://" + base +"/" + this._heroesUrl + "/connect")
    }
    getScripts (): Observable<Script[]> { // returns all scripts
        return this.http.get(this._heroesUrl)
            .map(ScriptService.extractData)
            .catch(ScriptService.handleError);
    }
    getScriptWithPrefix (id: number, prefix: String): Observable<Script> {
        return this.http.get(prefix + this._heroesUrl + "/" + id)
            .map(ScriptService.extractData)
            .catch(ScriptService.handleError);
    }
    getScript (id: number): Observable<Script> {
        return this.http.get(this._heroesUrl + "/" + id)
            .map(ScriptService.extractData)
            .catch(ScriptService.handleError);
    }
    saveScript(script: Script): Observable<Script> {
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });
        return this.http.post('../' + this._heroesUrl + "/" + script.id, JSON.stringify(script), options)
            .map(ScriptService.extractData)
            .catch(ScriptService.handleError);
    }
    getLocations (): Observable<Location[]> { // returns all scripts
        return this.http.get("api/locations")
            .map(ScriptService.extractData)
            .catch(ScriptService.handleError);
    }
    addLocation (name: String): Observable<Location> { // adds a new location
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });
        return this.http.post("api/locations/create", JSON.stringify({ "name": name}), options)
            .map(ScriptService.extractData)
            .catch(ScriptService.handleError);
    }
    static putScript(script: ActiveScript, socket: WebSocket): void {
        var activeData = {
            "actionIndex": script.actionIndex
        };
        socket.send(JSON.stringify(activeData));
        console.log("sending: "+JSON.stringify(activeData))
    }
    static startScript (activeScriptId: number, socket: WebSocket): void {
        socket.send(JSON.stringify({"start": activeScriptId}));
    }
    static stopScript (socket: WebSocket): void {
        socket.send(JSON.stringify({"stop": 0}));
    }
    createScript (name: string): Observable<Script> {

        let body = JSON.stringify({ "name": name });
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });

        return this.http.post(this._heroesUrl + "/create", body, options)
            .map(ScriptService.extractData)
            .catch(ScriptService.handleError);
    }
    private static extractData(res: Response) {
        if (res.status < 200 || res.status >= 300) {
            throw new Error('Bad response status: ' + res.status);
        }
        let body = res.json();
        console.log(body);
        return body || { };
    }
    private static handleError (error: any) {
        // In a real world app, we might send the error to remote logging infrastructure
        let errMsg = error.message || 'Server error';
        console.error(errMsg); // log to console instead
        return Observable.throw(errMsg);
    }
}