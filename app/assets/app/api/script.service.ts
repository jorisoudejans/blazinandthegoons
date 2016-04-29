import {Injectable}     from "angular2/core";
import {Http, Response} from "angular2/http";
import {Script}           from "./script";
import {Observable}     from "rxjs/Observable";

@Injectable()
export class ScriptService {
    constructor (private http: Http) {}
    private _heroesUrl = "scripts";  // URL to web api
    getScripts (): Observable<Script[]> {
        return this.http.get(this._heroesUrl)
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