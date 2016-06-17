package controllers;


import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

/**
 * Controller for the different views that are available.
 */
public class ApplicationController extends Controller {

    /**
     * Returns the homepage.
     * @return result of homepage
     */
    public Result index() {
        return ok(overview.render("Home"));
    }

    /**
     * Return the director view.
     * @return  result of the director view
     */
    public Result directorView() {

        return ok(director.render("Director View"));
    }

    /**
     * Return the overview view.
     * @return result of the overview view
     */
    public Result overviewView() {
        return ok(overview.render("ScriptController selection"));
    }

    /**
     * Returns the edit view of a certain script.
     * @param id Id of the script to be edited
     * @return  result of edit view
     */
    public Result editView(Long id) {
        return ok(edit.render("Edit script", id));
    }

    /**
     * Returns the edit view of a new script.
     * @return result of edit view
     */
    public Result editNewView() {
        return ok(edit.render("Edit script", (long) -1));
    }

    /**
     * Return the location view of a certain location.
     * @param id Id of the location to be viewed
     * @return result of location view
     */
    public Result locationView(Long id) {
        return ok(location.render("Location", id));
    }

    /**
     * Return the link view
     * @param id id of the script
     * @return link view response
     */
    public Result linkView(Long id) {
        return ok(link.render("Link", id));
    }

}
