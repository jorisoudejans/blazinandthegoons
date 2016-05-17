package models;

import com.avaje.ebean.config.dbplatform.IdType;
import com.avaje.ebean.config.dbplatform.PostgresPlatform;
import com.avaje.ebean.config.dbplatform.DbIdentity;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.event.ServerConfigStartup;

// This is needed to change the way Postgres handles
// id generation

/**
 * Configuration for postgres on heroku instances.
 */
public class MyServerConfigStartup implements ServerConfigStartup {
    @Override
    public void onStart(ServerConfig serverConfig) {
        //System.out.println("Platform: " + serverConfig.get());

        /*PostgresPlatform postgresPlatform = new PostgresPlatform();
        DbIdentity dbIdentity = postgresPlatform.getDbIdentity();
        dbIdentity.setSupportsGetGeneratedKeys(false);
        dbIdentity.setSupportsSequence(true);
        dbIdentity.setIdType(IdType.GENERATOR);
        serverConfig.setDatabasePlatform(postgresPlatform);*/

        serverConfig.setDatabaseSequenceBatchSize(1); // for postgres id sequencing
    }
}