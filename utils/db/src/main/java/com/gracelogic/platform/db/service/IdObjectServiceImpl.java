package com.gracelogic.platform.db.service;

import com.gracelogic.platform.db.dao.IdObjectDao;
import com.gracelogic.platform.db.model.IdObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class IdObjectServiceImpl implements IdObjectService {

    @Autowired
    private IdObjectDao idObjectDao;

    @Override
    public <T extends IdObject> T lockObject(Class<T> clazz, Object id) {
        return idObjectDao.lockObject(clazz, id);
    }

    @Override
    public void refresh(Object object) {
        idObjectDao.getEntityManager().refresh(object);
    }

    @Override
    public <T extends IdObject> T getObjectById(Class<T> clazz, Object id) {
        if (id == null) {
            return null;
        }

        return idObjectDao.getObjectById(clazz, null, id);
    }

    @Override
    public <T extends IdObject> T getObjectById(Class<T> clazz, String fetches, Object id) {
        if (id == null) {
            return null;
        }

        return idObjectDao.getObjectById(clazz, fetches, id);
    }

    @Override
    public Integer checkExist(Class clazz, String fetches, String cause, Map<String, Object> params, Integer maxCount) {
        return idObjectDao.checkExist(clazz, fetches, cause, params, maxCount);
    }

    @Override
    public <T extends IdObject> T save(T entity) {
        return idObjectDao.save(entity);
    }

    @Override
    public <T> List<T> getList(Class<T> clazz) {
        return idObjectDao.getList(clazz);
    }

    @Override
    public void delete(Class clazz, Object id) {
        idObjectDao.delete(clazz, id);
    }

    @Override
    public void delete(Class clazz, String cause, Map<String, Object> params) {
        idObjectDao.delete(clazz, cause, params);
    }

    @Override
    public Long getSum(Class clazz, String fieldName, String fetches, String cause, Map<String, Object> params) {
        return idObjectDao.getSum(clazz, fieldName, fetches, cause, params);
    }

    @Override
    public Integer getCount(Class clazz, String column, String fetches, String cause, Map<String, Object> params) {
        return idObjectDao.getCount(clazz, column, fetches, cause, params);
    }

    @Override
    public <T extends IdObject> T setIfModified(Class<T> clazz, T oldObject, Object newId) {
        if (oldObject != null && isEquals(oldObject.getId(), newId)) {
            return oldObject;
        } else {
            if (newId != null) {
                return getObjectById(clazz, newId);
            }
            return null;
        }
    }


    @Override
    public void offsetFieldValue(Class clazz, Object id, String fieldName, Integer offsetValue) {
        idObjectDao.offsetFieldValue(clazz, id, fieldName, offsetValue);
    }

    public <T> List<T> getList(Class<T> clazz, String fetches, String cause, Map<String, Object> params, String sortField, String sortDirection, Integer startRecord, Integer maxResult) {
        String sortFieldWithDirection = null;
        if (!StringUtils.isEmpty(sortField)) {
            if (StringUtils.equalsIgnoreCase(sortDirection, "asc")) {
                sortDirection = "asc";
            }
            else {
                sortDirection = "desc";
            }
            sortFieldWithDirection = sortField + " " + sortDirection;
        }

        return getList(clazz, fetches, cause, params, sortFieldWithDirection, startRecord, maxResult);
    }

    @Override
    public <T> List<T> getList(Class<T> clazz, String fetches, String cause, Map<String, Object> params, String sortFieldWithDirection, Integer startRecord, Integer maxResult) {
        return idObjectDao.getList(clazz, fetches, cause, params, sortFieldWithDirection, startRecord, maxResult);
    }

    @Override
    public Integer getMaxInteger(Class clazz, String fieldName, String cause, Map<String, Object> params) {
        return idObjectDao.getMaxInteger(clazz, fieldName, cause, params);
    }

    @Override
    public Date getMaxDate(Class clazz, String fieldName, String cause, Map<String, Object> params) {
        return idObjectDao.getMaxDate(clazz, fieldName, cause, params);
    }

    @Override
    public void updateFieldValue(Class clazz, Object id, String fieldName, Object val) {
        idObjectDao.updateFieldValue(clazz, id, fieldName, val);
    }

    @Override
    public void updateTwoFieldValue(Class clazz, Object id, String field1Name, Object val1, String field2Name, Object val2) {
        idObjectDao.updateTwoFieldValue(clazz, id, field1Name, val1, field2Name, val2);
    }

    public static boolean isEquals(Object first, Object second) {
        if (first == null) {
            return second == null;
        }
        return first.equals(second);
    }

    public static String valuesAsString(Collection values) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean first = true;
        for (Object val : values) {
            if (!first) {
                stringBuilder.append(",");
            } else {
                first = false;
            }
            stringBuilder.append("'");
            stringBuilder.append(val.toString());
            stringBuilder.append("'");
        }
        return stringBuilder.toString();
    }
}
