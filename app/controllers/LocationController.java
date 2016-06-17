package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Camera;
import models.Location;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;

/**
 * Manages locations and cameras.
 */
public class LocationController extends Controller {

    /**
     * Get all locations in the database.
     * @return locations result in json
     */
    public Result getAll() {
        return ok(Json.toJson(Location.find.all()));
    }

    /**
     * Get a single location.
     * @param id the id of the location
     * @return location or 404 not found
     */
    public Result get(Long id) {
        Location location = Location.find.byId(id);
        if (location != null) {
            return ok(Json.toJson(location));
        }
        return notFound();
    }

    /**
     * Create a new location.
     * @return new location
     */
    @BodyParser.Of(BodyParser.Json.class)
    public Result create() {
        JsonNode json = request().body().asJson();
        String name = json.findPath("name").textValue();
        Location l = new Location();
        l.name = name;
        l.cameras = new ArrayList<>();
        l.save();
        return ok(Json.toJson(l));
    }

    /**
     * Update a location
     * @param id location id
     * @return updated location object
     */
    @BodyParser.Of(BodyParser.Json.class)
    public Result update(Long id) {
        Location l = Json.fromJson(request().body().asJson(), Location.class);
        l.update();
        return ok(Json.toJson(l));
    }

}
