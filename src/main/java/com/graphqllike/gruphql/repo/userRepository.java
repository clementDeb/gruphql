package com.graphqllike.gruphql.repo;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor(onConstructor_ = @__({@Autowired}))
public class userRepository {

    private final EntityManager em;

    public Map<String, String> getDataById(Long id, List<String> params) {
        //create query using resultTransformer to build the map with the property name as key and tuple value as value
        return new HashMap<>();
    }
}
