package br.com.ricas.domain.entity.service;

import br.com.ricas.domain.entity.Fund;
import java.util.Date;
import java.util.List;

public interface FundService {
     void insertOne(Fund fund);
     List<Fund> filterGreaterThan100();
     Fund findFirst(String name);
     void updateOne(Fund fund);
     void updateMany(String filterKey, Double newValue, Date newDate);

     void deleteOne(String name);
     void deleteMany(String name);
     void transactionExample();
     void filterAggregate();
     void filterAggregateGroupExportedLanguage();
     void filterAggregateGroup();
     void filterSortWithProject();
}
