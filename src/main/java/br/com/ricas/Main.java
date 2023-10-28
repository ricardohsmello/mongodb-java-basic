package br.com.ricas;

import br.com.ricas.domain.entity.Fund;
import br.com.ricas.domain.entity.service.FundServiceImpl;

import java.util.Date;

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
//        findOne("03");
//        updateOne();
//        updateMany();
//        deleteOne();
//        deleteMany();
//        filterAggregate();
//        filterAggregateGroupExportedLanguage();
//        filterAggregateGroup();
        filterSortWithProject();
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
        Fund one = fundService.findFirst(name);
        System.out.println(one);
    }

    private static void updateOne() {
         fundService.updateOne(new Fund("03",
                 504.0,
                 new Date()));
     }
    private static void updateMany() {
        String filterKey = "03";
        Double newValue = 121.0;
        Date newDate = new Date();
        fundService.updateMany(filterKey, newValue, newDate);
    }

    private static void deleteOne() {
        fundService.deleteOne("03");
    }
    private static void deleteMany() {

        fundService.deleteMany("03");
    }

    private static void filterAggregate() {
        fundService.filterAggregate();
    }

    private static void filterAggregateGroupExportedLanguage() {
        fundService.filterAggregateGroupExportedLanguage();
    }

    private static void filterAggregateGroup() {
        fundService.filterAggregateGroup();
    }
    private static void filterSortWithProject() {
        fundService.filterSortWithProject();
    }

}