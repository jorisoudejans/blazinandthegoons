package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a recording location. Basically just a list of cameras.
 */
@Entity
public class Location extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String name;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    public List<Camera> cameras = new ArrayList<Camera>();

    public static Finder<Long, Location> find = new Finder<>(Location.class);


}
