package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.EnumValue;
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
    public void setFlag(FlagType ft, String desc) {
        this.flagged = true;
        this.flagType = ft;
        this.flagDescription = desc;
        this.save();
    }

    @Override
    public int compareTo(Object o) {
        Action act = (Action) o;
        return this.index - act.index;
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
        INCOMAPIBLE,
    }
}
