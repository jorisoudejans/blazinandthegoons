package models;

import com.avaje.ebean.Model;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hidde on 4/28/16.
 */
@Entity
public class Script extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String name;

    @Formats.DateTime(pattern="dd/MM/yyyy")
    public Date creationDate = new Date();

    @OneToMany(mappedBy = "script", cascade= CascadeType.ALL)
    public List<Action> actions = new ArrayList<Action>();

    @OneToOne(mappedBy = "script", cascade= CascadeType.ALL)
    public ActiveScript activeScript;

    public static Finder<Long, Script> find = new Finder<Long,Script>(Script.class);

}
