package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;


/**
 * Class to represent an active script.
 */
@Entity
public class ActiveScript extends Model {

    @OneToOne(cascade = CascadeType.ALL)
    public Script script;

    @Constraints.Required
    public long runningTime = 0;

    @Constraints.Required
    public int actionIndex;

    public static Finder<Long, ActiveScript> find =
            new Finder<Long, ActiveScript>(ActiveScript.class);

}
