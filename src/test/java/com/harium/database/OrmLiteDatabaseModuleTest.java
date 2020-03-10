package com.harium.database;

import com.harium.database.module.OrmLiteDatabaseModule;
import com.j256.ormlite.support.ConnectionSource;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class OrmLiteDatabaseModuleTest {

    OrmLiteDatabaseModule module;

    @Before
    public void setUp() {
        module = spy(new OrmLiteDatabaseModule() {
            @Override
            protected ConnectionSource initConnection() {
                return null;
            }
        });
    }

    @Test
    public void testInit() throws Exception {
        module.init(true);
        verify(module).clear();
    }

}
