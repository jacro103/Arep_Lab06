package edu.eci.arep;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;


public class MongoUtil {

    private static final String CONNECTION_STRING = "mongodb://mongodatabase:27017/";
    private static final String DATABASE_NAME = "logsdb";
    private static final Logger LOGGER = LoggerFactory.getLogger(MongoUtil.class);
    private MongoClient mongoClient;
    private MongoCollection<Document> logsCollection;


    public void addLog(String message) {
        this.connect();
        Document newLog = new Document("message", message).append("issuedAt", LocalDateTime.now());
        LOGGER.info("Adding log: {}", newLog);
        logsCollection.insertOne(newLog);
        this.close();
    }


    public List<String> getLogs() {
        this.connect();
        List<String> logs = new ArrayList<>();
        logsCollection.find().sort(Sorts.descending("_id")).limit(10).forEach(log -> logs.add(log.toJson()));
        this.close();
        return logs;
    }


    private void connect() {
        this.mongoClient = MongoClients.create(new ConnectionString(CONNECTION_STRING));
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        this.logsCollection = database.getCollection("logs");
    }

  
    private void close() {
        this.mongoClient.close();
    }

}
