// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// --------------------------------------------------------------------------------

package com.echothree.util.server.persistence;

import com.echothree.util.common.exception.PersistenceDatabaseException;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.JDBCUtils;

public class DslContextFactory {
    
    private static final String DS = "java:/EchoThreeDS";
    private static final String NTDS = "java:/EchoThreeNTDS";

    // Template for both contexts; must not be modified after initialization.
    private static final Settings SETTINGS = createSettings();
    
    private final DataSource ds;
    private final DataSource ntds;
    private final SQLDialect dsDialect;
    private final SQLDialect ntdsDialect;

    // Shared across requests; configurations and settings must not be modified after initialization.
    private final DSLContext dslContext;
    private final DSLContext ntDslContext;

    @SuppressWarnings("BanJNDI")
    protected DslContextFactory() {
        try {
            var jndiContext = new InitialContext();
            ds = (DataSource)jndiContext.lookup(DS);
            ntds = (DataSource)jndiContext.lookup(NTDS);
            dsDialect = detectDialect(ds);
            ntdsDialect = detectDialect(ntds);

            if(dsDialect != ntdsDialect) {
                throw new PersistenceDatabaseException("""
                        Likely JDBC misconfiguration: %s uses %s but %s uses %s; both DataSources must use the same database dialect
                        """.formatted(DS, dsDialect, NTDS, ntdsDialect));
            }

            dslContext = DSL.using(ds, dsDialect, SETTINGS);
            ntDslContext = DSL.using(ntds, ntdsDialect, SETTINGS);
        } catch (NamingException ne) {
            throw new PersistenceDatabaseException(ne);
        }
    }
    
    private static class DslContextFactoryHolder {
        private static final DslContextFactory instance = new DslContextFactory();
    }

    public static DslContextFactory getInstance() {
        return DslContextFactoryHolder.instance;
    }

    private SQLDialect detectDialect(DataSource dataSource) {
        try(var connection = dataSource.getConnection()) {
            var dialect = JDBCUtils.dialect(connection);

            if(dialect == SQLDialect.DEFAULT) {
                throw new PersistenceDatabaseException("Unable to determine database dialect");
            }

            // List exists in DatabaseMutexFlavorProducer also to support Mutexes.
            return switch(dialect.family()) {
                case MYSQL, MARIADB, POSTGRES -> dialect;
                default -> throw new PersistenceDatabaseException("Unsupported database dialect: " + dialect);
            };
        } catch(SQLException e) {
            throw new PersistenceDatabaseException(e);
        }
    }

    private static Settings createSettings() {
        // The datasource selects the tenant database. Generated schema names
        // describe the model and must not qualify runtime SQL.
        //
        // PostgreSQL connections must use a search_path that resolves the intended schema.
        // Disabling schema rendering also disables catalog qualification.
        return new Settings()
                .withRenderSchema(false);
    }

    public DSLContext getDslContext() {
        return dslContext;
    }
    
    public DSLContext getNTDslContext() {
        return ntDslContext;
    }
    
}
