package com.harium.database.module;

import com.harium.database.model.BaseDAO;
import com.harium.database.module.DatabaseModule;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class OrmLiteDatabaseModule implements DatabaseModule<ConnectionSource> {

    protected ConnectionSource connectionSource;

    protected List<BaseDAO<?, ConnectionSource>> registers = new ArrayList<BaseDAO<?, ConnectionSource>>();
    protected Map<Class<?>, BaseDAO<?, ConnectionSource>> daos = new HashMap<Class<?>, BaseDAO<?, ConnectionSource>>();

    public void init(boolean clearDatabase) {
        try {
            connectionSource = initConnection();
            createDAOs();

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
        for (BaseDAO<?, ConnectionSource> dao : registers) {
            TableUtils.dropTable(connectionSource, dao.getKlass(), true);
        }
    }

    private void setupDatabase(ConnectionSource connectionSource) throws SQLException {
        for (BaseDAO<?, ConnectionSource> dao : registers) {
            TableUtils.createTableIfNotExists(connectionSource, dao.getKlass());
        }
    }

    private void createDAOs() {
        for (BaseDAO<?, ConnectionSource> register : registers) {
            addDAO(register);
        }
    }

    private void initDAOs(ConnectionSource connectionSource) {
        for (BaseDAO<?, ConnectionSource> dao : registers) {
            dao.init(connectionSource);
        }
    }

    private BaseDAO addDAO(BaseDAO<?, ConnectionSource> baseDAO) {
        daos.put(baseDAO.getKlass(), baseDAO);
        return baseDAO;
    }

    public BaseDAO getDAO(Class klass) {
        return daos.get(klass);
    }

    public void register(BaseDAO<?, ConnectionSource> dao) {
        registers.add(dao);
    }

}
