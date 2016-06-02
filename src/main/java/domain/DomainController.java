package domain;

import domain.controllers.DropsController;
import domain.controllers.ItemDefinitionsController;
import domain.controllers.NpcDefinitionsController;
import exceptions.InvalidCollectionException;
import exceptions.InvalidDatabaseException;
import exceptions.NoClientException;
import exceptions.NoConnectionException;
import mongo.MongoConnection;
import mongo.OnlineMongoConnection;

import java.util.logging.Logger;

/**
 * Created by Gilles on 13/05/2016.
 */
public final class DomainController {
    private static final Logger LOGGER = Logger.getLogger("DropChecker");
    private final ItemDefinitionsController itemDefinitionsController = new ItemDefinitionsController();
    private final DropsController dropsController = new DropsController();
    private final NpcDefinitionsController npcDefinitionsController = new NpcDefinitionsController();
    private MongoConnection mongoConnection;

    public Logger getLogger() {
        return LOGGER;
    }

    public boolean connectMongo(String serverAddress, int portNumber, String loginUsername, String loginPassword, String authenticationDatabase) throws NoConnectionException, NoClientException {
        mongoConnection = new OnlineMongoConnection(serverAddress, portNumber, loginUsername, loginPassword, authenticationDatabase);
        return pingMongo();
    }

    public boolean pingMongo() throws NoConnectionException, NoClientException {
        if(mongoConnection == null)
            throw new NoConnectionException();
        return mongoConnection.pingMongo();
    }

    public void loadNpcDefinitions(String databaseName, String collectionName) throws NoConnectionException, InvalidDatabaseException, InvalidCollectionException, NoClientException {
        if(mongoConnection == null)
            throw new NoConnectionException();
        mongoConnection.loadNpcDefinitions(databaseName, collectionName).forEach(npcDefinition -> getNpcDefinitionsController().addDefinition(npcDefinition, false));
    }

    public void loadItemDefinitions(String databaseName, String collectionName) throws NoConnectionException, InvalidDatabaseException, InvalidCollectionException, NoClientException {
        if(mongoConnection == null)
            throw new NoConnectionException();
        mongoConnection.loadItemDefinitions(databaseName, collectionName).forEach(itemDefinition -> getItemDefinitionsController().addDefinition(itemDefinition, false));
    }

    public void loadNpcDrops(String databaseName, String collectionName) throws NoConnectionException, InvalidDatabaseException, InvalidCollectionException, NoClientException {
        if(mongoConnection == null)
            throw new NoConnectionException();
        mongoConnection.loadNpcDrops(databaseName, collectionName).forEach(npcDrop -> getDropsController().addDefinition(npcDrop, false));
    }

    public NpcDefinitionsController getNpcDefinitionsController() {
        return npcDefinitionsController;
    }

    public ItemDefinitionsController getItemDefinitionsController() {
        return itemDefinitionsController;
    }

    public boolean isConnected() {
        return mongoConnection != null;
    }

    public DropsController getDropsController() {
        return dropsController;
    }
}
