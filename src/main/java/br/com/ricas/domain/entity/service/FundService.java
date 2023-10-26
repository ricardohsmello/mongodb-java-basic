package br.com.ricas.domain.entity.service;

import br.com.ricas.domain.entity.Fund;
import org.bson.Document;

import java.util.List;

public interface FundService {
     void create(Fund fund);
     List<Fund> filterGreaterThan100();
}
