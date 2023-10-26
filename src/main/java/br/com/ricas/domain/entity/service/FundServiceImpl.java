package br.com.ricas.domain.entity.service;

import br.com.ricas.application.config.MongoConfig;
import br.com.ricas.domain.entity.Fund;
import br.com.ricas.domain.util.CollectionEnum;
import br.com.ricas.domain.util.DatabaseEnum;
import br.com.ricas.domain.util.FundsFieldEnum;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.gte;

public class FundServiceImpl implements FundService {
    private static final Logger logger = Logger.getLogger(String.valueOf(MongoConfig.class));
    private final MongoCollection<Document> fundsCollection = MongoConfig.getInstance().getDatabase(DatabaseEnum.CERTIFICATION.name().toLowerCase()).getCollection(CollectionEnum.FUNDS.name().toLowerCase());

    @Override
    public void create(Fund fund) {
        logger.info("Preparing to create a new Document: " + fund);

        InsertOneResult insertOneResult = fundsCollection.insertOne(createDocument(fund));

        logger.info("Fund create successfully " + insertOneResult.getInsertedId());

    }

    @Override
    public List<Fund> filterGreaterThan100() {

        FindIterable<Document> values = fundsCollection.find(Filters.and(gte(FundsFieldEnum.VALUE.name().toLowerCase(), 100.0)));
        List<Fund> funds = new ArrayList<>();
        values.forEach(
                it ->
                    funds.add(new Fund(
                            (String) it.get(FundsFieldEnum.NAME.name().toLowerCase()),
                            (Double) it.get(FundsFieldEnum.VALUE.name().toLowerCase()),
                            (Date) it.get(FundsFieldEnum.DATE.name().toLowerCase())
                    ))
        );

       return funds;

    }


    public Document createDocument(Fund fund) {
        return new Document()
                .append(FundsFieldEnum.NAME.name().toLowerCase(), fund.getName())
                .append(FundsFieldEnum.VALUE.name().toLowerCase(), fund.getValue())
                .append(FundsFieldEnum.DATE.name().toLowerCase(), fund.getDate());
    }
}
