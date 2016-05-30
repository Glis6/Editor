package domain;

import domain.controllers.DropsController;
import domain.controllers.ItemDefinitionsController;
import domain.definition.NpcDefinition;
import exceptions.InvalidCollectionException;
import exceptions.InvalidDatabaseException;
import exceptions.NoClientException;
import exceptions.NoConnectionException;
import mongo.MongoConnection;
import mongo.OnlineMongoConnection;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Gilles on 13/05/2016.
 */
public final class DomainController {
    private static final Logger LOGGER = Logger.getLogger("DropChecker");
    private final ItemDefinitionsController itemDefinitionsController = new ItemDefinitionsController();
    private final DropsController dropsController = new DropsController();
    private final Map<Integer, NpcDefinition> npcDefinitions = new HashMap<>();
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
        mongoConnection.loadNpcDefinitions(databaseName, collectionName).parallelStream().forEach(npcDefinition -> npcDefinitions.put(npcDefinition.getId(), npcDefinition));
    }

    public void loadItemDefinitions(String databaseName, String collectionName) throws NoConnectionException, InvalidDatabaseException, InvalidCollectionException, NoClientException {
        if(mongoConnection == null)
            throw new NoConnectionException();
        mongoConnection.loadItemDefinitions(databaseName, collectionName).parallelStream().forEach(itemDefinition -> getItemDefinitionsController().getItemDefinitions().put(itemDefinition.getId(), itemDefinition));
    }

    public void loadNpcDrops(String databaseName, String collectionName) throws NoConnectionException, InvalidDatabaseException, InvalidCollectionException, NoClientException {
        if(mongoConnection == null)
            throw new NoConnectionException();
        mongoConnection.loadNpcDrops(databaseName, collectionName).parallelStream().forEach(npcDrop -> getDropsController().getNpcDrops().put(npcDrop.getNpcName(), npcDrop));
    }

    public Map<Integer, NpcDefinition> getNpcDefinitions() {
        return npcDefinitions;
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
