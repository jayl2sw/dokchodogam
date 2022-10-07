package com.ssafy.dockchodogam.repository;

import org.json.JSONObject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlantMongoRepository extends MongoRepository<JSONObject, String> {

}
