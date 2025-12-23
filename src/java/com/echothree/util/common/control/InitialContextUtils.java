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

package com.echothree.util.common.control;

import java.io.IOException;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class InitialContextUtils {
    
    private InitialContextUtils() {
        super();
    }
    
    private static class InitialContextUtilsHolder {
        static InitialContextUtils instance = new InitialContextUtils();
    }
    
    public static InitialContextUtils getInstance() {
        return InitialContextUtilsHolder.instance;
    }

    public InitialContext getInitialContext()
            throws NamingException {
        var env = new Properties();
        var is = InitialContextUtils.class.getClassLoader().getResourceAsStream("echothree-jndi.properties");
        var loaded = false;

        if(is != null) {
            try {
                env.load(is);
                loaded = true;
            } catch (IOException ioe) {
                // Fall through, leave loaded = false.
            }
        }

        if(!loaded) {
            env.put(Context.PROVIDER_URL, "remote+http://127.0.0.1:8080");
        }

        env.put(Context.INITIAL_CONTEXT_FACTORY, org.wildfly.naming.client.WildFlyInitialContextFactory.class.getName());

        return new InitialContext(env);
    }

}
