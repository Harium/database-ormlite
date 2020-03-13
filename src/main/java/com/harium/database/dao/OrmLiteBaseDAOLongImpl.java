package com.harium.database.dao;

public class OrmLiteBaseDAOLongImpl<T> extends OrmLiteBaseDAOImpl<T, Long> {

    public OrmLiteBaseDAOLongImpl() {}

    public OrmLiteBaseDAOLongImpl(Class<T> klass) {
        super(klass);
    }

}
