package com.gowyn.service;

import com.gowyn.exceptions.NoPrimaryKeyFoundException;
import com.gowyn.service.ReflectionService;
import lombok.RequiredArgsConstructor;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    private final ReflectionService reflectionService;

    @SuppressWarnings("unchecked")
    @Transactional
    public Object getDataByPrimaryKey(Long id, List<String> params, String object) throws NoPrimaryKeyFoundException {

        String query = buildQuery(params, object);

        return em.createQuery(query)
                .unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new Transformer())
                .setParameter("id", id)
                .uniqueResult();

    }

    private String buildQuery(List<String> params, String objectName) throws NoPrimaryKeyFoundException {
        StringBuilder sb = new StringBuilder();
        sb.append("Select ");
        if (params.isEmpty()) {
            sb.append("o");
        }

        String primaryKeyField = reflectionService.retrievePrimaryKey(objectName).getName();
        addParamToQuery(params, sb);
        sb.append(" from ").append(objectName).append(" o where o.").append(primaryKeyField).append("= :id");

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

    private class Transformer implements ResultTransformer {
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
}
