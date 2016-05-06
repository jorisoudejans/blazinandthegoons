package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import play.data.validation.Constraints;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Created by hidde on 4/30/16.
 */
@Entity
public class Action extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String description;

    @Constraints.Required
    public int timestamp;

    @Constraints.Required
    public int estTime;

    @ManyToOne(cascade = CascadeType.ALL)
    public Preset preset;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    public Script script;

    public static Finder<Long, Action> find = new Finder<Long,Action>(Action.class);

    public static Action createAction(String des, int timestamp, int estTime, Preset preset, Script script) {
        Action act =  new Action();
        act.description = des;
        act.timestamp = timestamp;
        act.estTime = estTime;
        act.preset = preset;
        act.script = script;

        act.save();
        return act;
    }

}
