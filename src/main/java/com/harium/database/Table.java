package com.harium.database;

import com.harium.database.model.BaseDAO;
import com.harium.database.module.OrmLiteDatabaseModule;
import com.j256.ormlite.field.DatabaseFieldConfig;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table<T> {

    private Class<T> klass;
    private OrmLiteDatabaseModule module;
    private DatabaseTableConfig<T> config;
    private Map<String, Column> fields;

    public Table(Class<T> klass) {
        this.klass = klass;
        this.fields = new HashMap<>();
        this.config = new DatabaseTableConfig<>();
        config.setTableName(klass.getSimpleName().toLowerCase());
        config.setDataClass(klass);
    }

    public Column createColumn(String name) {
        Column column = Column.create().field(name).columnName(name);
        fields.put(name, column);
        return column;
    }

    public Column getColumn(String name) {
        return fields.get(name);
    }

    public void register(OrmLiteDatabaseModule module) {
        this.module = module;

        List<DatabaseFieldConfig> list = new ArrayList<>();
        for (Column column : fields.values()) {
            list.add(column.config());
        }
        config.setFieldConfigs(list);
        module.register(klass, config);
    }

    public BaseDAO getDAO(Class klass) {
        return module.getDAO(klass);
    }

}
