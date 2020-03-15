package com.harium.database.dao;

import com.harium.database.DatabaseError;
import com.harium.database.model.BaseDAO;
import com.j256.ormlite.dao.*;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.*;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.support.DatabaseResults;
import com.j256.ormlite.table.DatabaseTableConfig;
import com.j256.ormlite.table.ObjectFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class OrmLiteBaseDAOImpl<T, ID> implements BaseDAO<T, ConnectionSource> {

    private Class<T> klass;

    protected Dao<T, ID> dao;

    public OrmLiteBaseDAOImpl() {
    }

    public OrmLiteBaseDAOImpl(Class<T> klass) {
        this.klass = klass;
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

    /**
     * ORMLite Specific Methods
     */
    public Dao<T, ID> getDao() {
        return dao;
    }

    public T queryForFirst(PreparedQuery<T> preparedQuery) throws SQLException {
        return dao.queryForFirst(preparedQuery);
    }

    public List<T> queryForAll() throws SQLException {
        return dao.queryForAll();
    }

    public List<T> queryForMatching(T t) throws SQLException {
        return dao.queryForMatching(t);
    }

    public List<T> queryForMatchingArgs(T t) throws SQLException {
        return dao.queryForMatchingArgs(t);
    }

    public List<T> queryForFieldValues(Map<String, Object> map) throws SQLException {
        return dao.queryForFieldValues(map);
    }

    public List<T> queryForFieldValuesArgs(Map<String, Object> map) throws SQLException {
        return dao.queryForFieldValuesArgs(map);
    }

    public T queryForSameId(T t) throws SQLException {
        return dao.queryForSameId(t);
    }

    public QueryBuilder<T, ID> queryBuilder() {
        return dao.queryBuilder();
    }

    public UpdateBuilder<T, ID> updateBuilder() {
        return dao.updateBuilder();
    }

    public DeleteBuilder<T, ID> deleteBuilder() {
        return dao.deleteBuilder();
    }

    public List<T> query(PreparedQuery<T> preparedQuery) throws SQLException {
        return dao.query(preparedQuery);
    }

    public int create(Collection<T> collection) throws SQLException {
        return dao.create(collection);
    }

    public T createIfNotExists(T t) throws SQLException {
        return dao.createIfNotExists(t);
    }

    public int updateId(T t, ID id) throws SQLException {
        return dao.updateId(t, id);
    }

    public int update(PreparedUpdate<T> preparedUpdate) throws SQLException {
        return dao.update(preparedUpdate);
    }

    public int refresh(T t) throws SQLException {
        return dao.refresh(t);
    }

    public int deleteById(ID id) throws SQLException {
        return dao.deleteById(id);
    }

    public int delete(Collection<T> collection) throws SQLException {
        return dao.delete(collection);
    }

    public int deleteIds(Collection<ID> collection) throws SQLException {
        return dao.deleteIds(collection);
    }

    public int delete(PreparedDelete<T> preparedDelete) throws SQLException {
        return dao.delete(preparedDelete);
    }

    public CloseableIterator<T> iterator() {
        return dao.iterator();
    }

    public CloseableIterator<T> iterator(int i) {
        return dao.iterator(i);
    }

    public CloseableIterator<T> iterator(PreparedQuery<T> preparedQuery) throws SQLException {
        return dao.iterator(preparedQuery);
    }

    public CloseableIterator<T> iterator(PreparedQuery<T> preparedQuery, int i) throws SQLException {
        return dao.iterator(preparedQuery, i);
    }

    public CloseableWrappedIterable<T> getWrappedIterable() {
        return dao.getWrappedIterable();
    }

    public CloseableWrappedIterable<T> getWrappedIterable(PreparedQuery<T> preparedQuery) {
        return dao.getWrappedIterable(preparedQuery);
    }

    public void closeLastIterator() throws IOException {
        dao.closeLastIterator();
    }

    public GenericRawResults<String[]> queryRaw(String s, String... strings) throws SQLException {
        return dao.queryRaw(s, strings);
    }

    public <UO> GenericRawResults<UO> queryRaw(String s, RawRowMapper<UO> rawRowMapper, String... strings) throws SQLException {
        return dao.queryRaw(s, rawRowMapper, strings);
    }

    public <UO> GenericRawResults<UO> queryRaw(String s, DataType[] dataTypes, RawRowObjectMapper<UO> rawRowObjectMapper, String... strings) throws SQLException {
        return dao.queryRaw(s, dataTypes, rawRowObjectMapper, strings);
    }

    public GenericRawResults<Object[]> queryRaw(String s, DataType[] dataTypes, String... strings) throws SQLException {
        return dao.queryRaw(s, dataTypes, strings);
    }

    public <UO> GenericRawResults<UO> queryRaw(String s, DatabaseResultsMapper<UO> databaseResultsMapper, String... strings) throws SQLException {
        return dao.queryRaw(s, databaseResultsMapper, strings);
    }

    public long queryRawValue(String s, String... strings) throws SQLException {
        return dao.queryRawValue(s, strings);
    }

    public int executeRaw(String s, String... strings) throws SQLException {
        return dao.executeRaw(s, strings);
    }

    public int executeRawNoArgs(String s) throws SQLException {
        return dao.executeRawNoArgs(s);
    }

    public int updateRaw(String s, String... strings) throws SQLException {
        return dao.updateRaw(s, strings);
    }

    public <CT> CT callBatchTasks(Callable<CT> callable) throws Exception {
        return dao.callBatchTasks(callable);
    }

    public String objectToString(T t) {
        return dao.objectToString(t);
    }

    public boolean objectsEqual(T t, T t1) throws SQLException {
        return dao.objectsEqual(t, t1);
    }

    public ID extractId(T t) throws SQLException {
        return dao.extractId(t);
    }

    public Class<T> getDataClass() {
        return dao.getDataClass();
    }

    public FieldType findForeignFieldType(Class<?> aClass) {
        return dao.findForeignFieldType(aClass);
    }

    public boolean isUpdatable() {
        return dao.isUpdatable();
    }

    public boolean isTableExists() throws SQLException {
        return dao.isTableExists();
    }

    public long countOf(PreparedQuery<T> preparedQuery) throws SQLException {
        return dao.countOf(preparedQuery);
    }

    public void assignEmptyForeignCollection(T t, String s) throws SQLException {
        dao.assignEmptyForeignCollection(t, s);
    }

    public <FT> ForeignCollection<FT> getEmptyForeignCollection(String s) throws SQLException {
        return dao.getEmptyForeignCollection(s);
    }

    public void setObjectCache(boolean b) throws SQLException {
        dao.setObjectCache(b);
    }

    public void setObjectCache(ObjectCache objectCache) throws SQLException {
        dao.setObjectCache(objectCache);
    }

    public ObjectCache getObjectCache() {
        return dao.getObjectCache();
    }

    public void clearObjectCache() {
        dao.clearObjectCache();
    }

    public T mapSelectStarRow(DatabaseResults databaseResults) throws SQLException {
        return dao.mapSelectStarRow(databaseResults);
    }

    public GenericRowMapper<T> getSelectStarRowMapper() throws SQLException {
        return dao.getSelectStarRowMapper();
    }

    public RawRowMapper<T> getRawRowMapper() {
        return dao.getRawRowMapper();
    }

    public boolean idExists(ID id) throws SQLException {
        return dao.idExists(id);
    }

    public DatabaseConnection startThreadConnection() throws SQLException {
        return dao.startThreadConnection();
    }

    public void endThreadConnection(DatabaseConnection databaseConnection) throws SQLException {
        dao.endThreadConnection(databaseConnection);
    }

    public void setAutoCommit(DatabaseConnection databaseConnection, boolean b) throws SQLException {
        dao.setAutoCommit(databaseConnection, b);
    }

    public boolean isAutoCommit(DatabaseConnection databaseConnection) throws SQLException {
        return dao.isAutoCommit(databaseConnection);
    }

    public void commit(DatabaseConnection databaseConnection) throws SQLException {
        dao.commit(databaseConnection);
    }

    public void rollBack(DatabaseConnection databaseConnection) throws SQLException {
        dao.rollBack(databaseConnection);
    }

    public ConnectionSource getConnectionSource() {
        return dao.getConnectionSource();
    }

    public void setObjectFactory(ObjectFactory<T> objectFactory) {
        dao.setObjectFactory(objectFactory);
    }

    public void registerObserver(Dao.DaoObserver daoObserver) {
        dao.registerObserver(daoObserver);
    }

    public void unregisterObserver(Dao.DaoObserver daoObserver) {
        dao.unregisterObserver(daoObserver);
    }

    public String getTableName() {
        return dao.getTableName();
    }

    public void notifyChanges() {
        dao.notifyChanges();
    }

    public CloseableIterator<T> closeableIterator() {
        return dao.closeableIterator();
    }

}
