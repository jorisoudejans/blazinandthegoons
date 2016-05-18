import com.google.inject.AbstractModule;
import models.DatabaseSeeder;
import models.Script;


/**
 * Seeds the database with some initial data.
 */
public class Module extends AbstractModule {

    /**
     * Configure database.
     */
    @Override
    protected void configure() {
        // What do?
        System.out.println("Configure stack");

        bind(DatabaseSeeder.class).asEagerSingleton();
    }
}
