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

package com.echothree.util.server.control;

import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.util.common.exception.BaseException;
import com.echothree.util.common.message.Message;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.message.SecurityMessageAccumulator;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.ThreadSession;
import java.lang.reflect.InvocationTargetException;
import javax.inject.Inject;

public abstract class BaseLogic {

    @Inject
    protected CoreControl coreControl;

    protected BaseLogic() {
        super();
    }

    public Session getSession() {
        return ThreadSession.currentSession();
    }
    
    public void addExecutionError(final ExecutionErrorAccumulator eea, final String key, final Object... values) {
        if(eea != null) {
            eea.addExecutionError(key, values);
        }
    }

    public void addSecurityMessage(final SecurityMessageAccumulator sma, final String key, final Object... values) {
        if(sma != null) {
            sma.addSecurityMessage(key, values);
        }
    }

    public EntityInstance getEntityInstanceByBasePK(final BasePK pk) {
        return coreControl.getEntityInstanceByBasePK(pk);
    }

    public EntityInstance getEntityInstanceByBaseEntity(final BaseEntity baseEntity) {
        return coreControl.getEntityInstanceByBaseEntity(baseEntity);
    }

    // If eea is specified, we'll consider this to be a non-fatal error, and add this error to it. Otherwise, if a BaseException class
    // is specified, we'll instantiate it and throw it. If neither was specified, the error cannot be handled - abort.
    public void handleExecutionError(final Class<? extends BaseException> exceptionClass, final ExecutionErrorAccumulator eea, final String key, final Object... values) {
        var message = new Message(key, values);

        if(eea == null) {
            if(exceptionClass != null) {
                try {
                    throw exceptionClass.getConstructor(Message.class).newInstance(message);
                } catch (NoSuchMethodException nsme) {
                    throw new RuntimeException(nsme);
                } catch (InstantiationException ie) {
                    throw new RuntimeException(ie);
                } catch (IllegalAccessException iae) {
                    throw new RuntimeException(iae);
                } catch (InvocationTargetException ite) {
                    throw new RuntimeException(ite);
                }
            } else {
                throw new RuntimeException("BaseException or ExecutionErrorAccumulator must be specified");
            }
        } else {
            eea.addExecutionError(message);
        }
    }
    
    // If sma is specified, we'll consider this to be a non-fatal error, and add this error to it. Otherwise, if a BaseException class
    // is specified, we'll instantiate it and throw it. If neither was specified, the error cannot be handled - abort.
    public void handleSecurityMessage(final Class<? extends BaseException> exceptionClass, final SecurityMessageAccumulator sma, final String key, final Object... values) {
        var message = new Message(key, values);

        if(sma == null) {
            if(exceptionClass != null) {
                try {
                    throw exceptionClass.getConstructor(Message.class).newInstance(message);
                } catch (NoSuchMethodException nsme) {
                    throw new RuntimeException(nsme);
                } catch (InstantiationException ie) {
                    throw new RuntimeException(ie);
                } catch (IllegalAccessException iae) {
                    throw new RuntimeException(iae);
                } catch (InvocationTargetException ite) {
                    throw new RuntimeException(ite);
                }
            } else {
                throw new RuntimeException("BaseException or SecurityMessageAccumulator must be specified");
            }
        } else {
            sma.addSecurityMessage(message);
        }
    }
    
    // This is only safe to use if all function are using handleError. If eea is null in that case, an Exception will be thrown, and this
    // code will never be needed. Otherwise, eea will be checked and the value of hasExecutionErrors() returned.
    public boolean hasExecutionErrors(final ExecutionErrorAccumulator eea) {
        var hasExecutionErrors = false;
        
        if(eea != null) {
            hasExecutionErrors = eea.hasExecutionErrors();
        }
        
        return hasExecutionErrors;
    }

}
