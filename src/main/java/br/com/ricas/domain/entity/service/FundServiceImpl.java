package br.com.ricas.domain.entity.service;

import br.com.ricas.application.config.MongoConfig;
import br.com.ricas.domain.entity.Fund;
import br.com.ricas.domain.util.CollectionEnum;
import br.com.ricas.domain.util.DatabaseEnum;
import br.com.ricas.domain.util.FundsFieldEnum;
import com.mongodb.client.*;
import com.mongodb.client.model.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.util.*;
import java.util.logging.Logger;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Filters.gte;

public class FundServiceImpl implements FundService {
    private static final Logger logger = Logger.getLogger(String.valueOf(MongoConfig.class));
    public MongoCollection<Document> fundsCollection =
            MongoConfig.getInstance().getDatabase(
                    DatabaseEnum.CERTIFICATION.name().toLowerCase()
            ).getCollection(CollectionEnum.FUNDS.name().toLowerCase());

    @Override
    public void insertOne(Fund fund) {
        logger.info("Preparing to create a new Document: " + fund);
        InsertOneResult insertOneResult = fundsCollection.insertOne(createDocument(fund));
        logger.info("Fund create successfully " + insertOneResult.getInsertedId());
    }

    @Override
    public List<Fund> filterGreaterThan100() {
        var and = Filters.and(gte(FundsFieldEnum.VALUE.name().toLowerCase(), 100.0));
        FindIterable<Document> values = fundsCollection.find(and);
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

    @Override
    public Fund findFirst(String name) {
        MongoIterable<Fund> map = fundsCollection.find(Filters.eq(FundsFieldEnum.NAME.name().toLowerCase(), name)).map(
                it -> new Fund(
                        (String) it.get(FundsFieldEnum.NAME.name().toLowerCase()),
                        (Double) it.get(FundsFieldEnum.VALUE.name().toLowerCase()),
                        (Date) it.get(FundsFieldEnum.DATE.name().toLowerCase())
                )
        );

        return map.first();
    }

    @Override
    public void updateOne(Fund fund) {
        Bson eq = Filters.eq(FundsFieldEnum.NAME.name().toLowerCase(), fund.getName());
        Bson set = Updates.set(FundsFieldEnum.VALUE.name().toLowerCase(), fund.getValue());

        UpdateResult updateResult = fundsCollection.updateOne(eq, set);

        System.out.println(updateResult.getMatchedCount());
        System.out.println(updateResult.getModifiedCount());
        System.out.println(updateResult.getUpsertedId());
    }

    @Override
    public void updateMany(String filterKey, Double newValue, Date newDate) {
        Bson eq = Filters.eq(FundsFieldEnum.NAME.name().toLowerCase(), filterKey);
        Bson set = Updates.combine(
                Updates.set(FundsFieldEnum.VALUE.name().toLowerCase(), newValue),
                Updates.set(FundsFieldEnum.DATE.name().toLowerCase(), newDate)
        );

        UpdateResult updateResult = fundsCollection.updateMany(eq, set);

        System.out.println(updateResult.getMatchedCount());
        System.out.println(updateResult.getModifiedCount());
        System.out.println(updateResult.getUpsertedId());
    }

    @Override
    public void deleteOne(String name) {
        Bson eq = Filters.eq(FundsFieldEnum.NAME.name().toLowerCase(), name);
        DeleteResult deleteResult = fundsCollection.deleteOne(eq);
        System.out.println(deleteResult.getDeletedCount());
    }

    @Override
    public void deleteMany(String name) {
        Bson eq = Filters.eq(FundsFieldEnum.NAME.name().toLowerCase(), name);
        DeleteResult deleteResult = fundsCollection.deleteMany(eq);
        System.out.println(deleteResult.getDeletedCount());
    }

    @Override
    public void transactionExample() {
        ClientSession clientSession = MongoConfig.getInstance().startSession();
        TransactionBody txnBody = new TransactionBody() {
            @Override
            public String execute() {
                // here we can do all the updates that will be validate during the session.
                return "transaction succesfully";
            }
        };
        try {

        } catch (RuntimeException e) {

        } finally {
            clientSession.close();
        }
    }

    @Override
    public void filterAggregate() {
        Bson eq1 = Filters.eq(FundsFieldEnum.NAME.name().toLowerCase(), "03");
        Bson gte = Filters.gte(FundsFieldEnum.VALUE.name().toLowerCase(), 101.0);
        Bson include = Projections.include(FundsFieldEnum.NAME.name().toLowerCase());
        Bson exclude = Projections.exclude("_id");

        AggregateIterable<Document> aggregate = fundsCollection.aggregate(
                List.of(
                        Aggregates.match(eq1),
                        Aggregates.match(gte),
                        Aggregates.project(include),
                        Aggregates.project(exclude)
                )
        );

        for (Document document : aggregate) {
            System.out.println(document);
        }
    }

    @Override
    public void filterAggregateGroupExportedLanguage() {
        AggregateIterable<Document> aggregate = fundsCollection.aggregate(Arrays.asList(new Document("$group",
                        new Document("_id", "$name")
                                .append("total",
                                        new Document("$count",
                                                new Document()))),
                new Document("$project",
                        new Document("_id", 0L)
                                .append("name", "$_id")
                                .append("total", "$total"))));

        for (Document document : aggregate) {
            System.out.println(document);
        }
    }

    @Override
    public void filterAggregateGroup() {
        Bson match = Aggregates.match(Filters.eq(FundsFieldEnum.NAME.name().toLowerCase(), "01"));
        Bson group = Aggregates.group("$name", sum("soma", "$value"));
        fundsCollection.aggregate(Arrays.asList(match, group)).forEach(
                System.out::println
        );
    }

    @Override
    public void filterSortWithProject() {
        Bson eq = Filters.eq(FundsFieldEnum.NAME.name().toLowerCase(), "03");
        Bson sort = Aggregates.sort(Sorts.descending(FundsFieldEnum.VALUE.name().toLowerCase()));
        Bson project = Aggregates.project(Projections.exclude("_id"));
        AggregateIterable<Document> aggregate = fundsCollection.aggregate(List.of(
                        Aggregates.match(eq),
                        sort,
                        project
                )
        );

        aggregate.forEach(
                System.out::println
        );
    }
    @Override
    public void createIndex() {
        Bson eq = Filters.eq(FundsFieldEnum.NAME.name().toLowerCase(), 1);
        fundsCollection.createIndex(eq);
    }

    @Override
    public void createCollection() {


    }

    public Document createDocument(Fund fund) {
        return new Document()
                .append(FundsFieldEnum.NAME.name().toLowerCase(), fund.getName())
                .append(FundsFieldEnum.VALUE.name().toLowerCase(), fund.getValue())
                .append(FundsFieldEnum.DATE.name().toLowerCase(), fund.getDate());
    }
}
