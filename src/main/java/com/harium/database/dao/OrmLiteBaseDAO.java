package com.harium.database.dao;

import com.harium.database.model.BaseDAO;
import com.j256.ormlite.support.ConnectionSource;

public interface OrmLiteBaseDAO<T> extends BaseDAO<T, ConnectionSource> {

}
