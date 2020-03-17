package com.harium.database;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseFieldConfig;

public class Column {

    private DatabaseFieldConfig config;

    protected Column(DatabaseFieldConfig config) {
        this.config = config;
    }

    public static Column create() {
        return new Column(new DatabaseFieldConfig());
    }

    public Column id() {
        config.setId(true);
        return this;
    }

    public Column idAutoIncrement() {
        config.setId(false);
        config.setGeneratedId(true);
        config.setAllowGeneratedIdInsert(true);
        config.setDataType(DataType.INTEGER);
        return this;
    }

    public Column field(String name) {
        config.setFieldName(name);
        return this;
    }

    public Column columnName(String name) {
        config.setColumnName(name);
        return this;
    }

    public Column nullable(boolean nullable) {
        config.setCanBeNull(nullable);
        return this;
    }

    public Column notNull() {
        return nullable(false);
    }

    public Column defaultValue(String value) {
        config.setDefaultValue(value);
        return this;
    }

    public Column index(String name) {
        config.setIndex(true);
        config.setIndexName(name);
        return this;
    }

    public Column uniqueCombo() {
        config.setUniqueCombo(true);
        return this;
    }

    public Column uniqueCombo(boolean uniqueCombo) {
        config.setUniqueCombo(uniqueCombo);
        return this;
    }

    public Column unique() {
        config.setUnique(true);
        return this;
    }

    public Column readOnly() {
        config.setReadOnly(true);
        return this;
    }

    public Column typeBoolean() {
        config.setDataType(DataType.BOOLEAN);
        return this;
    }

    public Column typeByte() {
        config.setDataType(DataType.BYTE);
        return this;
    }

    public Column typeChar() {
        config.setDataType(DataType.CHAR);
        return this;
    }

    public Column typeDate() {
        config.setDataType(DataType.DATE);
        return this;
    }

    public Column typeDouble() {
        config.setDataType(DataType.DOUBLE);
        return this;
    }

    public Column typeFloat() {
        config.setDataType(DataType.FLOAT);
        return this;
    }

    public Column typeInteger() {
        config.setDataType(DataType.INTEGER);
        return this;
    }

    public Column typeLong() {
        config.setDataType(DataType.LONG);
        return this;
    }

    public Column typeShort() {
        config.setDataType(DataType.SHORT);
        return this;
    }

    public Column typeSQLDate() {
        config.setDataType(DataType.SQL_DATE);
        return this;
    }

    public Column typeString() {
        config.setDataType(DataType.STRING);
        return this;
    }

    public Column typeUUID() {
        config.setDataType(DataType.UUID);
        return this;
    }

    public Column type(DataType type) {
        config.setDataType(type);
        return this;
    }

    public DatabaseFieldConfig config() {
        return config;
    }
}
