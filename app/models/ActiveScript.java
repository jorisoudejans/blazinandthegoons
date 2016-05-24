package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;


/**
 * Class to represent an active script.
 */
@Entity
public class ActiveScript extends Model {

    @Id
    public int id;

    @OneToOne(cascade = CascadeType.PERSIST)
    public Script script;

    @Constraints.Required
    public long runningTime = 0;

    @Constraints.Required
    public int actionIndex;

    public static Finder<Long, ActiveScript> find =
            new Finder<Long, ActiveScript>(ActiveScript.class);

}
