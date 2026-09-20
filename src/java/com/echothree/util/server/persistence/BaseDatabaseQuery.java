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
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.ResultQuery;
import org.jooq.exception.DataAccessException;

public abstract class BaseDatabaseQuery<R extends BaseDatabaseResult> {

    private final EntityPermission entityPermission;
    private final Class<R> baseDatabaseResultClass;
    private final Constructor<R> baseDatabaseResultConstructor;
    
    @SuppressWarnings("unchecked")
    protected BaseDatabaseQuery(final EntityPermission entityPermission) {
        this.entityPermission = entityPermission;
        
        this.baseDatabaseResultClass = (Class<R>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        try {
            baseDatabaseResultConstructor = baseDatabaseResultClass.getDeclaredConstructor();
        } catch(NoSuchMethodException ex) {
            throw new PersistenceDatabaseException(ex);
        }
    }

    protected DSLContext getDslContext() {
        return ThreadSession.currentSession().getDslContext();
    }

    private PersistenceDatabaseException mappingException(final String columnLabel, final Class<?> type, final Throwable cause) {
        var exception = new PersistenceDatabaseException("Cannot map column '" + columnLabel + "' to "
                + baseDatabaseResultClass.getName() + "; expected type: " + (type == null ? "unresolved" : type.getName()));

        exception.initCause(cause instanceof InvocationTargetException invocation ? invocation.getCause() : cause);

        return exception;
    }

    private DatabaseResultMethod getDatabaseResultMethod(final String columnLabel) {
        Class<?> returnType = null;

        try {
            final var getMethod = baseDatabaseResultClass.getMethod("get" + columnLabel);
            
            returnType = getMethod.getReturnType();

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

                        final var classLoader = returnType.getClassLoader();
                        final var pkType = classLoader.loadClass(pkName);
                        final var factoryType = classLoader.loadClass(factoryName);
                        final var getInstanceMethod = factoryType.getDeclaredMethod("getInstance");

                        factoryInstance = getInstanceMethod.invoke(null);
                        pkConstructor = pkType.getDeclaredConstructor(Long.class);
                        getEntityFromPkMethod = factoryType.getDeclaredMethod("getEntityFromPK", EntityPermission.class, pkType);
                    }
                }

                if(pkConstructor == null) {
                    throw new IllegalArgumentException("Unsupported result type: " + returnType.getName());
                }
            }

            return new DatabaseResultMethod(columnLabel, setMethod, returnType, factoryInstance, pkConstructor, getEntityFromPkMethod);
        } catch(ReflectiveOperationException | RuntimeException ex) {
            throw mappingException(columnLabel, returnType, ex);
        }
    }

    private List<DatabaseResultMethod> getDatabaseResultMethods(final Field<?>[] fields) {
        final var methods = new ArrayList<DatabaseResultMethod>(fields.length);

        for(var field : fields) {
            methods.add(getDatabaseResultMethod(field.getName()));
        }
        return methods;
    }

    private Object getDatabaseResultValue(final Record record, final int columnIndex, final DatabaseResultMethod method)
            throws ReflectiveOperationException {
        return switch(method.kind) {
            case SCALAR -> record.get(columnIndex, method.type);
            case PRIMARY_KEY, ENTITY -> {
                var key = record.get(columnIndex);
                if(key == null) {
                    yield null;
                }
                if(!(key instanceof BasePK)) {
                    key = method.pkConstructor.newInstance(record.get(columnIndex, Long.class));
                }
                yield method.kind == DatabaseResultMethod.Kind.PRIMARY_KEY ? key
                        : method.getEntityFromPkMethod.invoke(method.factoryInstance, entityPermission, key);
            }
        };
    }

    protected List<R> execute(final ResultQuery<?> query) {
        final var results = new ArrayList<R>();

        try(final var cursor = query.fetchLazy()) {
            if(cursor.hasNext()) {
                final var methods = getDatabaseResultMethods(cursor.fields());

                for(var record : cursor) {
                    final R result;

                    try {
                        result = baseDatabaseResultConstructor.newInstance();
                    } catch(InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                        var exception = new PersistenceDatabaseException("Cannot instantiate result " + baseDatabaseResultClass.getName());

                        exception.initCause(ex instanceof InvocationTargetException invocation ? invocation.getCause() : ex);

                        throw exception;
                    }

                    for(var columnIndex = 0; columnIndex < methods.size(); columnIndex++) {
                        final var method = methods.get(columnIndex);

                        try {
                            method.setMethod.invoke(result, getDatabaseResultValue(record, columnIndex, method));
                        } catch(ReflectiveOperationException | RuntimeException ex) {
                            throw mappingException(method.columnLabel, method.type, ex);
                        }
                    }
                    results.add(result);
                }
            }
        } catch(DataAccessException ex) {
            throw new PersistenceDatabaseException(ex);
        }

        return results;
    }

}
