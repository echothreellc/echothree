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
import com.echothree.util.common.persistence.BasePK;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.ResultQuery;
import org.jooq.exception.DataAccessException;

public abstract class BaseDatabaseQuery<R extends BaseDatabaseResult> {
    
    protected final EntityPermission entityPermission;
    
    protected final Class<R> baseDatabaseResultClass;
    
    List<DatabaseResultMethod> databaseResultMethods;
    
    @SuppressWarnings("unchecked")
    protected BaseDatabaseQuery(final EntityPermission entityPermission) {
        this.entityPermission = entityPermission;
        
        this.baseDatabaseResultClass = (Class<R>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
    private DatabaseResultMethod getDatabaseResultMethod(final String columnLabel) {
        DatabaseResultMethod databaseResultMethod;

        try {
            final var getMethod = baseDatabaseResultClass.getMethod("get" + columnLabel);
            final var returnType = getMethod.getReturnType();
            final var setMethod = baseDatabaseResultClass.getMethod("set" + columnLabel, returnType);
            Object factoryInstance = null;
            Constructor<?> pkConstructor = null;
            Method getEntityFromPkMethod = null;
            
            if(!returnType.equals(Long.class) && !returnType.equals(Integer.class) && !returnType.equals(String.class)) {
                final var superclass = returnType.getSuperclass();

                if(superclass != null) {
                    if(superclass.equals(BasePK.class)) {
                        pkConstructor = returnType.getDeclaredConstructor(Long.class);
                    } else if(superclass.equals(BaseEntity.class)) {
                        final var name = returnType.getName();
                        final var nameComponents = Splitter.on('.').trimResults().omitEmptyStrings().splitToList(name).toArray(new String[0]);
                        String factoryName = null;
                        String pkName = null;

                        for(var i = 0; i < nameComponents.length ; i++) {
                            if(nameComponents[i].equals("server")) {
                                var j = i;
                                var baseClassName = nameComponents[i + 2];

                                nameComponents[++i] = "factory";
                                nameComponents[++i] = baseClassName + "Factory";
                                factoryName = Joiner.on(".").join(nameComponents);

                                nameComponents[j++] = "common";
                                nameComponents[j++] = "pk";
                                nameComponents[j] = baseClassName + "PK";
                                pkName = Joiner.on(".").join(nameComponents);

                                break;
                            }
                        }

                        try {
                            final var classLoader = returnType.getClassLoader();
                            final var pkType = classLoader.loadClass(pkName);
                            final var factoryType = classLoader.loadClass(factoryName);
                            final var getInstanceMethod = factoryType.getDeclaredMethod("getInstance");

                            factoryInstance = getInstanceMethod.invoke(null);
                            pkConstructor = pkType.getDeclaredConstructor(Long.class);
                            getEntityFromPkMethod = factoryType.getDeclaredMethod("getEntityFromPK", EntityPermission.class, pkType);
                        } catch(ClassNotFoundException | IllegalAccessException | InvocationTargetException ex) {
                            throw new PersistenceDatabaseException(ex);
                        }
                    }
                }

                if(pkConstructor == null) {
                    throw new PersistenceDatabaseException("unsupported Class in getDatabaseResultMethod, " + returnType);
                }
            }

            databaseResultMethod = new DatabaseResultMethod(setMethod, returnType, factoryInstance, pkConstructor, getEntityFromPkMethod);
        } catch(NoSuchMethodException nsme) {
            throw new PersistenceDatabaseException(nsme);
        }

        return databaseResultMethod;
    }

    protected DSLContext getDslContext() {
        return ThreadSession.currentSession().getDslContext();
    }

    private List<DatabaseResultMethod> getDatabaseResultMethods(final Record record) {
        if(databaseResultMethods == null) {
            databaseResultMethods = new ArrayList<>();
            for(var field : record.fields()) {
                databaseResultMethods.add(getDatabaseResultMethod(field.getName()));
            }
        }

        return databaseResultMethods;
    }

    protected List<R> execute(final ResultQuery<?> query) {
        final var results = new ArrayList<R>();

        try(final var cursor = query.fetchLazy()) {
            for(var record : cursor) {
                try {
                    final var result = baseDatabaseResultClass.getDeclaredConstructor().newInstance();
                    var columnIndex = 0;

                    for(var method : getDatabaseResultMethods(record)) {
                        final var type = method.type;
                        Object value;

                        if(type.equals(Long.class)) {
                            value = record.get(columnIndex, Long.class);
                        } else if(type.equals(Integer.class)) {
                            value = record.get(columnIndex, Integer.class);
                        } else if(type.equals(String.class)) {
                            value = record.get(columnIndex, String.class);
                        } else {
                            var key = record.get(columnIndex);
                            if(key == null) {
                                value = null;
                            } else {
                                if(!(key instanceof BasePK)) {
                                    var id = record.get(columnIndex, Long.class);
                                    key = method.pkConstructor.newInstance(id);
                                }
                                value = type.getSuperclass().equals(BasePK.class) ? key
                                        : method.getEntityFromPkMethod.invoke(method.factoryInstance, entityPermission, key);
                            }
                        }

                        method.setMethod.invoke(result, value);
                        columnIndex++;
                    }

                    results.add(result);
                } catch(NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                    throw new PersistenceDatabaseException(ex);
                }
            }
        } catch(DataAccessException ex) {
            throw new PersistenceDatabaseException(ex);
        }

        return results;
    }

}
