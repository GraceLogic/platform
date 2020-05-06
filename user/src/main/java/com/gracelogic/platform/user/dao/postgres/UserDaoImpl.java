package com.gracelogic.platform.user.dao.postgres;

import com.gracelogic.platform.db.condition.OnPostgreSQLConditional;
import com.gracelogic.platform.user.dao.AbstractUserDaoImpl;
import com.gracelogic.platform.user.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Conditional(OnPostgreSQLConditional.class)
public class UserDaoImpl extends AbstractUserDaoImpl {
    private static Logger logger = Logger.getLogger(UserDaoImpl.class);

    @Override
    public Integer getUsersCount(String identifierValue, Boolean approved, Boolean blocked, Map<String, String> fields) {
        BigInteger count = null;
        StringBuilder queryStr = new StringBuilder("select count(DISTINCT el.ID) from {h-schema}cmn_user el " +
                "inner join {h-schema}cmn_identifier iden on el.id = iden.user_id where 1=1 ");

        Map<String, Object> params = new HashMap<>();
        if (!StringUtils.isEmpty(identifierValue)) {
            queryStr.append("and iden.value = :identifierValue ");
            params.put("identifierValue", identifierValue);
        }
        if (approved != null) {
            queryStr.append("and el.is_approved = :approved ");
            params.put("approved", approved);
        }
        if (blocked != null) {
            queryStr.append("and el.is_blocked = :blocked ");
            params.put("blocked", blocked);
        }
        if (fields != null && !fields.isEmpty()) {
            for (String key : fields.keySet()) {
                queryStr.append(String.format("and el.fields ->> '%s' = '%s' ", key, fields.get(key)));
            }
        }

        try {
            Query query = getEntityManager().createNativeQuery(queryStr.toString());

            for (String key : params.keySet()) {
                query.setParameter(key, params.get(key));
            }

            count = (BigInteger) query.getSingleResult();
        } catch (Exception e) {
            logger.error("Failed to get users count", e);
        }

        return count != null ? count.intValue() : null;
    }

    @Override
    public List<User> getUsers(String identifierValue, Boolean approved, Boolean blocked, Map<String, String> fields, String sortField, String sortDir, Integer startRecord, Integer recordsOnPage) {
        List<User> users = Collections.emptyList();
        StringBuilder queryStr = new StringBuilder(String.format("select DISTINCT ON (%s,  el.id) * from {h-schema}cmn_user el ", sortField) +
                "inner join {h-schema}cmn_identifier iden on el.id = iden.user_id  where 1=1 ");

        Map<String, Object> params = new HashMap<>();
        if (!StringUtils.isEmpty(identifierValue)) {
            queryStr.append("and iden.value = :identifierValue ");
            params.put("identifierValue", identifierValue);
        }
        if (approved != null) {
            queryStr.append("and el.is_approved = :approved ");
            params.put("approved", approved);
        }
        if (blocked != null) {
            queryStr.append("and el.is_blocked = :blocked ");
            params.put("blocked", blocked);
        }
        if (fields != null && !fields.isEmpty()) {
            for (String key : fields.keySet()) {
                queryStr.append(String.format("and el.fields ->> '%s' = '%s' ", key, fields.get(key)));
            }
        }

        sortField+=", el.id ";

        appendSortClause(queryStr, sortField, sortDir);

        try {
            Query query = getEntityManager().createNativeQuery(queryStr.toString(), User.class);
            appendPaginationClause(query, params, recordsOnPage, startRecord);

            for (String key : params.keySet()) {
                query.setParameter(key, params.get(key));
            }

            users = query.getResultList();

        } catch (Exception e) {
            logger.error("Failed to get users", e);
        }

        return users;
    }

    @Override
    public List<Object[]> getLastActiveUsersSessions() {
        List<Object[]> result = Collections.emptyList();

        String queryStr =
                "SELECT " +
                "  CAST(x.user_id AS text) as user_id, x.session_id " +
                "FROM ( " +
                "  SELECT " +
                "    ROW_NUMBER() OVER (PARTITION BY user_id ORDER BY created_dt desc) AS r, " +
                "    t.* " +
                "  FROM " +
                "    {h-schema}cmn_user_session t where t.is_valid = true) x " +
                "WHERE " +
                "  x.r <= 1";

        try {
            Query query = getEntityManager().createNativeQuery(queryStr);
            result = query.getResultList();

        } catch (Exception e) {
            logger.error("Failed to get last active users sessions", e);
        }

        return result;
    }
}
