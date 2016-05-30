package mongo;

import domain.definition.NpcDefinition;
import domain.drop.NpcDrop;
import domain.item.ItemDefinition;
import exceptions.InvalidCollectionException;
import exceptions.InvalidDatabaseException;
import exceptions.NoClientException;

import java.util.List;

/**
 * Created by Gilles on 13/05/2016.
 */
public interface MongoConnection {
    boolean pingMongo() throws NoClientException;
    List<NpcDefinition> loadNpcDefinitions(String databaseName, String collectionName) throws InvalidCollectionException, NoClientException, InvalidDatabaseException;
    List<ItemDefinition> loadItemDefinitions(String databaseName, String collectionName) throws InvalidCollectionException, NoClientException, InvalidDatabaseException;
    List<NpcDrop> loadNpcDrops(String databaseName, String collectionName) throws InvalidCollectionException, NoClientException, InvalidDatabaseException;
}
