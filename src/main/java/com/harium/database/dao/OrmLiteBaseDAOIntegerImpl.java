package com.harium.database.dao;

public class OrmLiteBaseDAOIntegerImpl<T> extends OrmLiteBaseDAOImpl<T, Integer> {

    public OrmLiteBaseDAOIntegerImpl() {}

    public OrmLiteBaseDAOIntegerImpl(Class<T> klass) {
        super(klass);
    }

}
