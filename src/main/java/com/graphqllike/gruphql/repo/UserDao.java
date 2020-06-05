package com.graphqllike.gruphql.repo;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@RequiredArgsConstructor(onConstructor_ = @__({@Autowired}))
public class UserDao {

    private final EntityManager em;

    @Transactional
    public List<Tuple> getDataById(Long id, List<String> params, String object) {

        String query = buildQuery(params, object, id);

        return em.createQuery(query, Tuple.class)
                .setParameter("id", id)
                .getResultList();

    }

    private String buildQuery(List<String> params, String objectName, Long id) {
        StringBuilder sb = new StringBuilder();
        sb.append("Select ");
        if (params.isEmpty()) {
            sb.append("o");
        }
        int p = 0;
        for (String param : params) {
            if (p != 0 && p < params.size()) {
                sb.append(", ");
            }
            sb.append("o.");
            sb.append(param);
            p += 1;
        }

        sb.append(" from ").append(objectName).append(" o where o.id = :id");

        return sb.toString();

    }
}
