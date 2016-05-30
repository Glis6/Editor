package mongo;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import domain.definition.NpcDefinition;
import domain.drop.DropChance;
import domain.drop.NpcDrop;
import domain.drop.NpcDropItem;
import domain.item.EquipmentType;
import domain.item.ItemDefinition;
import exceptions.InvalidCollectionException;
import exceptions.InvalidDatabaseException;
import exceptions.NoClientException;
import org.apache.commons.lang.ArrayUtils;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Gilles on 13/05/2016.
 */
public class OnlineMongoConnection implements MongoConnection {
    private MongoClient mongoClient;

    public OnlineMongoConnection(String serverAddress, int portNumber, String loginUsername, String loginPassword, String authenticationDatabase) {
        this.mongoClient = new MongoClient(new ServerAddress(serverAddress, portNumber),
                Collections.singletonList(MongoCredential.createCredential(loginUsername, authenticationDatabase, loginPassword.toCharArray())));
    }

    public OnlineMongoConnection(String serverAddress, int portNumber, String loginUsername, String loginPassword) {
        this(serverAddress, portNumber, loginUsername, loginPassword, "admin");
    }

    public OnlineMongoConnection(String serverAddress, int portNumber) {
        this.mongoClient = new MongoClient(new ServerAddress(serverAddress, portNumber));
    }

    public OnlineMongoConnection(String serverAddress) {
        this(serverAddress, 27017);
    }

    public boolean pingMongo() throws NoClientException {
        if (mongoClient == null)
            throw new NoClientException();
        return mongoClient.getAddress() != null;
    }

    @Override
    public List<NpcDefinition> loadNpcDefinitions(String databaseName, String collectionName) throws InvalidCollectionException, NoClientException, InvalidDatabaseException {
        if (mongoClient == null)
            throw new NoClientException();
        if (!hasDatabase(mongoClient, databaseName))
            throw new InvalidDatabaseException();
        MongoDatabase mongoDatabase = mongoClient.getDatabase(databaseName);
        if (mongoDatabase == null)
            throw new InvalidDatabaseException();
        if (!hasCollection(mongoDatabase, collectionName))
            throw new InvalidCollectionException();
        List<NpcDefinition> npcDefinitions = new ArrayList<>();

        mongoDatabase.getCollection(collectionName).find().forEach((Block<Document>) document -> {
            npcDefinitions.add(
                    new NpcDefinition(
                            document.getInteger("npc-id"),
                            document.getString("name"),
                            document.getString("examine"),
                            document.getInteger("combat"),
                            document.getInteger("size"),
                            document.getBoolean("attackable"),
                            document.getBoolean("aggressive"),
                            document.getBoolean("retreats"),
                            document.getBoolean("poisonous"),
                            document.getInteger("respawn"),
                            document.getInteger("max-hit"),
                            document.getInteger("hit-points"),
                            document.getInteger("attack-speed"),
                            document.getInteger("attack-animation"),
                            document.getInteger("defence-animation"),
                            document.getInteger("death-animation"),
                            document.getInteger("attack-bonus"),
                            document.getInteger("melee-defence"),
                            document.getInteger("ranged-defence"),
                            document.getInteger("magic-defence"),
                            document.getInteger("slayer-required")
                    ));
        });

        return npcDefinitions;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ItemDefinition> loadItemDefinitions(String databaseName, String collectionName) throws InvalidCollectionException, NoClientException, InvalidDatabaseException {
        if (mongoClient == null)
            throw new NoClientException();
        if (!hasDatabase(mongoClient, databaseName))
            throw new InvalidDatabaseException();
        MongoDatabase mongoDatabase = mongoClient.getDatabase(databaseName);
        if (mongoDatabase == null)
            throw new InvalidDatabaseException();
        if (!hasCollection(mongoDatabase, collectionName))
            throw new InvalidCollectionException();
        List<ItemDefinition> itemDefinitions = new ArrayList<>();

        mongoDatabase.getCollection(collectionName).find().forEach((Block<Document>) document -> {
            List<Double> bonusList = (ArrayList<Double>) document.get("bonusses");
            Double[] bonusses = bonusList.toArray(new Double[bonusList.size()]);
            List<Integer> requirementList = (ArrayList<Integer>) document.get("requirements");
            Integer[] requirements = requirementList.toArray(new Integer[requirementList.size()]);
            itemDefinitions.add(new ItemDefinition(
                    document.getInteger("item-id"),
                    document.getString("name"),
                    document.getString("examine"),
                    document.getBoolean("stackable"),
                    document.getInteger("value"),
                    document.getBoolean("noted"),
                    document.getBoolean("double-handed"),
                    document.getBoolean("weapon"),
                    EquipmentType.valueOf(document.getString("equipment-type")),
                    ArrayUtils.toPrimitive(bonusses),
                    ArrayUtils.toPrimitive(requirements)
            ));
        });

        return itemDefinitions;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<NpcDrop> loadNpcDrops(String databaseName, String collectionName) throws InvalidCollectionException, NoClientException, InvalidDatabaseException {
        if (mongoClient == null)
            throw new NoClientException();
        if (!hasDatabase(mongoClient, databaseName))
            throw new InvalidDatabaseException();
        MongoDatabase mongoDatabase = mongoClient.getDatabase(databaseName);
        if (mongoDatabase == null)
            throw new InvalidDatabaseException();
        if (!hasCollection(mongoDatabase, collectionName))
            throw new InvalidCollectionException();
        List<NpcDrop> npcDrops = new ArrayList<>();

        mongoDatabase.getCollection(collectionName).find().forEach((Block<Document>) document -> {
            List<Document> dropItemsDocument = (List<Document>) document.get("drops");
            List<NpcDropItem> dropItems = dropItemsDocument.stream().map(entry -> new NpcDropItem(
                    entry.getInteger("item-id"),
                    entry.getInteger("amount"),
                    DropChance.valueOf(entry.getString("dropchance"))
            )).collect(Collectors.toList());
            npcDrops.add(new NpcDrop(document.getString("npc-name"), dropItems.toArray(new NpcDropItem[dropItems.size()])));
        });

        return npcDrops;
    }

    private static boolean hasDatabase(MongoClient mongoClient, String databaseName) {
        Iterator<String> databaseNameIterator = mongoClient.listDatabaseNames().iterator();
        boolean foundDatabase = false;
        while (databaseNameIterator.hasNext()) {
            if (databaseNameIterator.next().equals(databaseName))
                foundDatabase = true;
        }
        return foundDatabase;
    }

    private static boolean hasCollection(MongoDatabase mongoDatabase, String databaseName) {
        Iterator<String> collectionNameIterator = mongoDatabase.listCollectionNames().iterator();
        boolean foundCollection = false;
        while (collectionNameIterator.hasNext()) {
            if (collectionNameIterator.next().equals(databaseName))
                foundCollection = true;
        }
        return foundCollection;
    }
}
