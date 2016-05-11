package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;


/**
 * Class to represent an active script.
 *
 * Created by hidde on 5/10/16.
 */
@Entity
public class ActiveScript extends Model {

    @OneToOne(cascade= CascadeType.ALL)
    public Script script;

    @Constraints.Required
    public int runningTime = 0;

    @Constraints.Required
    public int actionIndex;

    public static Finder<Long, ActiveScript> find = new Finder<Long,ActiveScript>(ActiveScript.class);

}
