package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import play.data.validation.Constraints;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * The model class for Actions. This is the representation used for the database.
 */
@Entity
public class Action extends Model implements Comparable {


    @Id
    public Long id;

    @Constraints.Required
    public int index;

    @Constraints.Required
    public String description;

    @Constraints.Required
    public int timestamp;

    @Constraints.Required
    public int duration;

    @ManyToOne(cascade = CascadeType.ALL)
    public Preset preset;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    public Script script;

    //Finder Object used for finding objects in the database.
    public static Finder<Long, Action> find = new Finder<>(Action.class);


    /**
     * A static create function which can be called to create an Action object
     * with the specified parameters.
     * @param des   Description of the action
     * @param timestamp Timestamp at which the action begins.
     * @param duration  The estimated duration the action will take.
     * @param preset    The preset the action will use.
     * @param script    The script to which the action belongs.
     * @param ind The action index
     * @return  The created Action object.
     */
    public static Action createAction(
            int ind, String des, int timestamp, int duration, Preset preset, Script script) {
        Action act =  new Action();
        act.index = ind;
        act.description = des;
        act.timestamp = timestamp;
        act.duration = duration;
        act.preset = preset;
        act.script = script;

        act.save();
        return act;
    }

    @Override
    public int compareTo(Object o) {
        Action act = (Action) o;
        return this.index - act.index;
    }
}
