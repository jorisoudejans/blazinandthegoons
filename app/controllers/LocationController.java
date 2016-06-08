package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Camera;
import models.Location;
import models.Script;
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
     * Add a new camera to this location
     * @param location identifier
     * @return updated location
     */
    @BodyParser.Of(BodyParser.Json.class)
    public Result addCamera(Long location) {
        Location l = Location.find.byId(location);
        if (l != null) {
            JsonNode json = request().body().asJson();
            Camera c = new Camera();
            c.name = json.findPath("name").textValue();
            c.ip = json.findPath("ip").textValue();
            c.location = l;
            c.save();
            return ok(Json.toJson(l));
        }
        return notFound();
    }

    /**
     * Remove camera from this location
     * @param location identifier
     * @param camera camera identifier
     * @return updated location
     */
    public Result removeCamera(Long location, Long camera) {
        Location l = Location.find.byId(location);
        if (l != null) {
            Camera c = Camera.find.byId(camera);
            if (c != null) {
                c.delete();
                return ok(Json.toJson(l));
            }
            return notFound("Camera not found");
        }
        return notFound("Location not found");
    }

}
