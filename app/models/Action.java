package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import play.data.validation.Constraints;

import javax.persistence.*;

/**
 * The model class for Actions. This is the representation used for the database.
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Action extends Model implements Comparable {


    @Id
    public Long id;

    @Constraints.Required
    @Column(name = "position")
    public int index;

    @Constraints.Required
    public String description;

    @Constraints.Required
    @Column(name = "moment")
    public int timestamp;

    @Constraints.Required
    public int duration;

    @ManyToOne(cascade = {})
    public Preset preset;

    @ManyToOne(cascade = {})
    @JsonIgnore
    public Script script;

    @Constraints.Required
    public boolean flagged;

    public FlagType flagType;

    public String flagDescription;

    //Finder Object used for finding objects in the database.
    public static Finder<Long, Action> find = new Finder<>(Action.class);

    /**
     * Creates a flag for the action.
     * @param ft The type of flag
     * @param desc Description of the flag
     */
    public static Action createAction(
            int ind, String des, int timestamp, int duration, Preset preset, Script script) {
        Action act = new Action();
        act.index = ind;
        act.description = des;
        act.timestamp = timestamp;
        act.duration = duration;
        act.preset = preset;
        act.script = script;
        act.flagged = false;

        act.save();
        return act;
    }

    @Override
    public int compareTo(Object o) {
        Action act = (Action) o;
        return this.index - act.index;
    }

    /**
     * Creates a flag for the action.
     * @param ft The type of flag
     * @param desc Description of the flag
     */
    public void setFlag(FlagType ft, String desc) {
        this.flagged = true;
        this.flagType = ft;
        this.flagDescription = desc;
        this.save();
    }

    /**
     * The different types of reasons a action could be flagged with.
     */
    public enum FlagType {

        @EnumValue("OBSTRUCTED")
        OBSTRUCTED,

        @EnumValue("TOOFAST")
        TOOFAST,

        @EnumValue("BADANGLE")
        BADANGLE,

        @EnumValue("INCOMPATIBLE")
        INCOMPATIBLE,
    }
}
