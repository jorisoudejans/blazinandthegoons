import {Injectable}     from "angular2/core";
import {Http, Response, Headers, RequestOptions} from "angular2/http";
import {Script, ActiveScript}           from "./script";
import {Observable}     from "rxjs/Observable";

@Injectable()
export class ScriptService {
    constructor (private http: Http) {}
    private _heroesUrl = "api/scripts";  // URL to web api
    getScripts (): Observable<Script[]> {
        return this.http.get(this._heroesUrl)
            .map(this.extractData)
            .catch(this.handleError);
    }
    getScript (id: number): Observable<Script> {
        return this.http.get(this._heroesUrl + "/" + id)
            .map(this.extractData)
            .catch(this.handleError);
    }
    getStatus (): Observable<ActiveScript> {
        return this.http.get(this._heroesUrl + "/status")
            .map(this.extractData)
            .catch(this.handleError);
    }
    createScript (name: string): Observable<Script> {

        let body = JSON.stringify({ "name": name });
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });

        return this.http.post(this._heroesUrl + "/create", body, options)
            .map(this.extractData)
            .catch(this.handleError);
    }
    private extractData(res: Response) {
        if (res.status < 200 || res.status >= 300) {
            throw new Error('Bad response status: ' + res.status);
        }
        let body = res.json();
        console.log(body);
        return body || { };
    }
    private handleError (error: any) {
        // In a real world app, we might send the error to remote logging infrastructure
        let errMsg = error.message || 'Server error';
        console.error(errMsg); // log to console instead
        return Observable.throw(errMsg);
    }
}