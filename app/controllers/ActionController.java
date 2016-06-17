package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

/**
 * Controller for the ActionController class. This controller is used to handle
 * the json requests from the client.
 */
public class ActionController extends Controller {

    /**
     * Get a list of all ActionController objects in the database.
     * @return The list of all ActionController objects.
     */
    public Result getAll() {
        List<models.Action> actionList = models.Action.find.all();
        return ok(Json.toJson(actionList));
    }

    /**
     * Looks in the database to find the ActionController object with id.
     * @param id    The id of the ActionController object to be found.
     * @return  ok with the found object.
     */
    public Result get(Long id) {
        models.Action action = models.Action.find.byId(id);
        return ok(Json.toJson(action));
    }

}
