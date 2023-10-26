package br.com.ricas.domain.entity.service;

import br.com.ricas.application.config.MongoConfig;
import br.com.ricas.domain.entity.Fund;
import br.com.ricas.domain.util.CollectionEnum;
import br.com.ricas.domain.util.DatabaseEnum;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;

import java.util.logging.Logger;

public class FundServiceImpl implements FundService {
    private static final Logger logger = Logger.getLogger(String.valueOf(MongoConfig.class));
    private final MongoCollection<Document> fundsCollection = MongoConfig.getInstance().getDatabase(DatabaseEnum.CERTIFICATION.name().toLowerCase()).getCollection(CollectionEnum.FUNDS.name().toLowerCase());

    @Override
    public void create(Fund fund) {
        logger.info("Preparing to create a new Document: " + fund);

        InsertOneResult insertOneResult = fundsCollection.insertOne(createDocument(fund));

        logger.info("Fund create successfully " + insertOneResult.getInsertedId());

    }


    public Document createDocument(Fund fund) {
        return new Document()
                .append("name", fund.getName())
                .append("value", fund.getValue())
                .append("date", fund.getCreatedAt());

    }
}
