import {Component} from "angular2/core"
import {ScriptElement} from "./scriptelement"

@Component({
    selector: "script-queue",
    template: "" +
    "<h1>{{title}}</h1>" +
    "<ul class='list-group'>" +
    "   <li class='list-group-item col-md-12' *ngFor='#screl of scriptEl'>" +
    "       <div class='col-md-8'>ID: {{ screl.id }}<br> {{ screl.preset }}<br> Camera ID: {{ screl.camId }}</div>" +
    "       <div class='col-md-4'><iframe src='{{ screl.camSource }}' frameborder='0' scrolling='no' width='100%' height='auto' allowfullscreen></iframe></div>" +
    "   </li> " +
    "</ul>"
})
export class ScriptQueue {
    title = "Queue of the script";
    scriptEl = [
        new ScriptElement(0, "Preset 0", 3, "http://media.tumblr.com/79ab6f3b5d898c59c6b13e392057eb21/tumblr_mlam9zTyxv1s72ffzo1_500.gif"),
        new ScriptElement(1, "Preset 5", 1, "http://byt.wpengine.netdna-cdn.com/wp-content/uploads/2014/09/01-gustavo_1211121230340.gif"),
        new ScriptElement(2, "Preset 2", 2, "http://i42.tinypic.com/1hp3q0.gif"),
        new ScriptElement(3, "Preset 7", 5, "https://media.giphy.com/media/3o85xHk7WPNdFVIuIg/giphy.gif")
    ]
}