package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Model for scripts.
 */
@Entity
public class Script extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String name;

    @Formats.DateTime(pattern = "dd/MM/yyyy")
    public Date creationDate = new Date();

    @OneToMany(mappedBy = "script", cascade = CascadeType.ALL)
    public List<Action> actions = new ArrayList<Action>();

    @ManyToOne
    public Location location;

    @OneToOne(mappedBy = "script", cascade = CascadeType.ALL)
    @JsonIgnore
    public ActiveScript activeScript;

    public static Finder<Long, Script> find
            = new Finder<Long, Script>(Script.class);

}
