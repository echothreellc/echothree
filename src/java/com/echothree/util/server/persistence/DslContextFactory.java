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
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.RenderNameCase;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;

public class DslContextFactory {
    
    private static final Log log = LogFactory.getLog(DslContextFactory.class);
    
    private static final String DS = "java:/EchoThreeDS";
    private static final String NTDS = "java:/EchoThreeNTDS";
    
    private static final DslContextFactory instance = new DslContextFactory();
    
    private final DataSource ds;
    private final DataSource ntds;

    @SuppressWarnings("BanJNDI")
    protected DslContextFactory() {
        try {
            var jndiContext = new InitialContext();
            ds = (DataSource)jndiContext.lookup(DS);
            ntds = (DataSource)jndiContext.lookup(NTDS);
        } catch (NamingException ne) {
            throw new PersistenceDatabaseException(ne);
        }
    }
    
    public static DslContextFactory getInstance() {
        return instance;
    }
    
    public DSLContext getDslContext() {
        var settings = new Settings()
                .withRenderQuotedNames(RenderQuotedNames.NEVER)     // Defaults to EXPLICIT_DEFAULT_QUOTED
                .withRenderNameCase(RenderNameCase.LOWER);          // Defaults to AS_IS
        var dslContent = DSL.using(ds, SQLDialect.MYSQL, settings);
        
        if(PersistenceDebugFlags.LogConnections)
            log.info("getDslContext() returning " + dslContent);

        return dslContent;
    }
    
    public DSLContext getNTDslContext() {
        var dslContent = DSL.using(ntds, SQLDialect.MYSQL);
        
        if(PersistenceDebugFlags.LogConnections)
            log.info("getNTDslContext() returning " + dslContent);

        return dslContent;
    }
    
}
