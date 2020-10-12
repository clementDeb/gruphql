package com.gowyn.service;

import com.gowyn.exceptions.NoEntityObjectFound;
import com.gowyn.exceptions.NoPrimaryKeyFoundException;
import com.gowyn.invariant.DataInput;
import lombok.RequiredArgsConstructor;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor(onConstructor_ = @__({@Autowired}))
public class RequestRepo {

    private final EntityManager em;

    private final ReflectionService reflectionService;

    @Transactional
    public Object getDataByPrimaryKey(final Long id, final DataInput data) throws NoPrimaryKeyFoundException, NoEntityObjectFound {

        String query = buildQuery(data);

        return Optional.ofNullable(em.createQuery(query)
                .unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new Transformer())
                .setParameter("id", id)
                .uniqueResult()).orElseThrow(() -> new NoEntityObjectFound("the request returned no result"));


    }

    private String buildQuery(final DataInput data) throws NoPrimaryKeyFoundException {
        List<String> fiedls = data.getFields();
        String objectName = data.getObjectName();

        StringBuilder sb = new StringBuilder();
        sb.append("Select ");
        if (data.getFields().isEmpty()) {
            sb.append("o");
        }

        String primaryKeyField = reflectionService.retrievePrimaryKey(objectName).getName();
        addParamToQuery(fiedls, sb);
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

    private static class Transformer implements ResultTransformer {
        @Override
        public Object transformTuple(Object[] objects, String[] strings) {
            return Arrays.asList(objects);
        }

        @Override
        public List<?> transformList(List list) {
            return list;
        }
    }
}
