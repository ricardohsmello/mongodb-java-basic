package br.com.ricas.application.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import java.util.logging.Logger;

public class MongoConfig {
    private static final String CONNECTION_STRING = "mongodb://localhost:27017,localhost:27018,localhost:27019/?replicaSet=rs1";
    private static MongoClient mongoClient;
    private static final Logger logger = Logger.getLogger(String.valueOf(MongoConfig.class));

    // Private constructor to prevent direct instantiation
    private MongoConfig() {
        try {
            mongoClient = MongoClients.create(CONNECTION_STRING);
        }catch (Exception exception) {
            logger.info(exception.getLocalizedMessage());
        }
    }
    // Public method to get the instance of the MongoClient
    public static MongoClient getInstance() {
        if (mongoClient == null) {
            synchronized (MongoConfig.class) {
                if (mongoClient == null) {
                    new MongoConfig();
                }
            }
        }
        return mongoClient;
    }
}
