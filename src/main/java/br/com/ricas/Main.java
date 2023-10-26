package br.com.ricas;

import br.com.ricas.domain.entity.Fund;
import br.com.ricas.domain.entity.service.FundServiceImpl;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    static FundServiceImpl fundService = new FundServiceImpl();

    public static void main(String[] args) {

//        createFund("01",
//                12.0,
//                new Date());
//
//        createFund("02",
//                50.30,
//                new Date());
//
//        createFund("03",
//                230.30,
//                new Date());
        findOne("03");
    }

    private static void createFund(String name, double value, Date date) {
        fundService.create(new Fund(name, value, date));
    }

    private static void filterGreaterThan100() {
        fundService.filterGreaterThan100().forEach(
                it -> System.out.println(it)
        );
    }

    private static void findOne(String name) {
        Fund one = fundService.findOne(name);
        System.out.println(one);
    }

}