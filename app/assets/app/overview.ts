/**
 * Created by floris on 11/05/2016.
 */
import { HTTP_PROVIDERS }    from "angular2/http";
import {bootstrap}    from "angular2/platform/browser"
import {ScriptListComponent} from "./scriptlist.component"
import 'rxjs/Rx';

bootstrap(ScriptListComponent, [HTTP_PROVIDERS]);