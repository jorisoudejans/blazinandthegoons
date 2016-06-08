package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import play.data.validation.Constraints;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Represents a camera to record with.
 */
@Entity
public class Camera extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String name;

    @Constraints.Required
    public String ip;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    public Location location;

    public static Finder<Long, Camera> find = new Finder<>(Camera.class);

}
