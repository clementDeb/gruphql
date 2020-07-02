package com.gowyn.repo;

import lombok.RequiredArgsConstructor;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor(onConstructor_ = @__({@Autowired}))
public class RequestRepo {


    private final EntityManager em;

    @SuppressWarnings("unchecked")
    @Transactional
    public List<List<Object>> getDataById(Long id, List<String> params, String object) {

        String query = buildQuery(params, object);

        return em.createQuery(query)
                .unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new ResultTransformer() {
                                          @Override
                                          public Object transformTuple(Object[] objects, String[] strings) {
                                              List<Object> responseFields = new ArrayList<>();
                                              List<Object> fields = Arrays.asList(objects);
                                              fields.forEach(object -> responseFields.add(objects[fields.indexOf(object)]));

                                              return responseFields;
                                          }

                                          @Override
                                          public List transformList(List list) {
                                              return list;
                                          }
                                      }
                )
                .setParameter("id", id)
                .getResultList();

    }

    private String buildQuery(List<String> params, String objectName) {
        StringBuilder sb = new StringBuilder();
        sb.append("Select ");
        if (params.isEmpty()) {
            sb.append("o");
        }
        addParamToQuery(params, sb);
        sb.append(" from ").append(objectName).append(" o where o.id = :id");

        return sb.toString();

    }

    private void addParamToQuery(List<String> params, StringBuilder sb) {
        int p = 0;
        for (String param : params) {
            if (p != 0 && p < params.size()) {
                sb.append(", ");
            }
            sb.append("o.");
            sb.append(param);
            p += 1;
        }
    }
}
