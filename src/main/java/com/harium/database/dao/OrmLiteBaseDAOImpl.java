package com.harium.database.dao;

import com.harium.database.DatabaseError;
import com.harium.database.model.BaseDAO;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrmLiteBaseDAOImpl<T, ID> implements BaseDAO<T, ConnectionSource> {

    private Class<T> klass;

    protected Dao<T, ID> dao;

    public OrmLiteBaseDAOImpl() {}

    public OrmLiteBaseDAOImpl(Class<T> klass) {
        this.klass = klass;
    }

    public OrmLiteBaseDAOImpl(Class<T> klass, ConnectionSource connectionSource, DatabaseTableConfig<T> config) {
        this.klass = klass;
        init(connectionSource, config);
    }

    public void init(ConnectionSource connectionSource) {
        try {
            dao = DaoManager.createDao(connectionSource, klass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void init(ConnectionSource connectionSource, DatabaseTableConfig<T> config) {
        try {
            dao = DaoManager.createDao(connectionSource, config);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<T> queryForEq(String column, Object o) throws SQLException {
        return dao.queryForEq(column, o);
    }

    public T queryForId(ID id) {
        try {
            return dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<T> queryAll() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<T>();
    }

    public int create(T item) {
        try {
            return dao.create(item);
        } catch (SQLException e) {
            e.printStackTrace();
            return DatabaseError.ON_CREATE;
        }
    }

    public int update(T item) {
        try {
            return dao.update(item);
        } catch (SQLException e) {
            e.printStackTrace();
            return DatabaseError.ON_UPDATE;
        }
    }

    public int createOrUpdate(T item) {
        Dao.CreateOrUpdateStatus status = null;
        try {
            status = dao.createOrUpdate(item);
            return status.getNumLinesChanged();
        } catch (SQLException e) {
            e.printStackTrace();
            if (status.isCreated()) {
                return DatabaseError.ON_UPDATE;
            }
            return DatabaseError.ON_CREATE;
        }
    }

    public int delete(T model) {
        try {
            return dao.delete(model);
        } catch (SQLException e) {
            e.printStackTrace();
            return DatabaseError.ON_DELETE;
        }
    }

    public long count() {
        try {
            return dao.countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Class<T> getKlass() {
        return klass;
    }
}
