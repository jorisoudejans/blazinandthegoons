package models;

import com.avaje.ebean.Model;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

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

    public static Finder<Long, Script> find = new Finder<Long,Script>(Script.class);

}
