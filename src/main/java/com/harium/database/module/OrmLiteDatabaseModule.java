package com.harium.database.module;

import com.harium.database.dao.OrmLiteBaseDAOImpl;
import com.harium.database.model.BaseDAO;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class OrmLiteDatabaseModule<ID> implements DatabaseModule<ConnectionSource> {

    protected boolean ignoreErrors = true;

    protected ConnectionSource connectionSource;

    protected Map<Class<?>, BaseDAO<?, ConnectionSource>> daos = new HashMap<>();
    protected List<BaseDAO<?, ConnectionSource>> entities = new ArrayList<>();
    protected Map<Class<?>, DatabaseTableConfig<?>> configEntities = new HashMap<>();

    public void init() {
        init(false);
    }

    public void init(boolean clearDatabase) {
        try {
            connectionSource = initConnection();
            if (clearDatabase) {
                clear();
            }
            setupDatabase(connectionSource);
            initDAOs(connectionSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract ConnectionSource initConnection() throws SQLException;

    public void clear() throws Exception {
        for (BaseDAO<?, ConnectionSource> dao : entities) {
            TableUtils.dropTable(connectionSource, dao.getKlass(), ignoreErrors);
        }
        for (Map.Entry<Class<?>, DatabaseTableConfig<?>> config : configEntities.entrySet()) {
            TableUtils.dropTable(connectionSource, config.getValue(), ignoreErrors);
        }
    }

    private void setupDatabase(ConnectionSource connectionSource) throws SQLException {
        for (BaseDAO<?, ConnectionSource> dao : entities) {
            TableUtils.createTableIfNotExists(connectionSource, dao.getKlass());
        }
        for (DatabaseTableConfig<?> config : configEntities.values()) {
            TableUtils.createTableIfNotExists(connectionSource, config);
        }
    }

    protected void initDAOs(ConnectionSource connectionSource) {
        for (BaseDAO<?, ConnectionSource> dao : entities) {
            initDAO(connectionSource, dao);
        }
        for (Map.Entry<Class<?>, DatabaseTableConfig<?>> entry : configEntities.entrySet()) {
            initDAO(connectionSource, entry.getKey(), entry.getValue());
        }
    }

    private void initDAO(ConnectionSource connectionSource, BaseDAO<?, ConnectionSource> dao) {
        dao.init(connectionSource);
        addDAO(dao);
    }

    private void initDAO(ConnectionSource connectionSource, Class<?> klass, DatabaseTableConfig<?> config) {
        OrmLiteBaseDAOImpl dao = new OrmLiteBaseDAOImpl<>(klass);
        dao.init(connectionSource, config);
        addDAO(klass, dao);
    }

    private BaseDAO addDAO(BaseDAO<?, ConnectionSource> baseDAO) {
        daos.put(baseDAO.getKlass(), baseDAO);
        return baseDAO;
    }

    private BaseDAO addDAO(Class<?> klass, BaseDAO<?, ConnectionSource> baseDAO) {
        daos.put(klass, baseDAO);
        return baseDAO;
    }

    public BaseDAO getDAO(Class klass) {
        return daos.get(klass);
    }

    public void register(BaseDAO<?, ConnectionSource> dao) {
        entities.add(dao);
    }

    public void register(Class<?> klass, DatabaseTableConfig<?> dao) {
        configEntities.put(klass, dao);
    }

    public boolean isIgnoreErrors() {
        return ignoreErrors;
    }

    public void setIgnoreErrors(boolean ignoreErrors) {
        this.ignoreErrors = ignoreErrors;
    }
}
