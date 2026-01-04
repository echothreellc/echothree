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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDatabaseQuery<R extends BaseDatabaseResult> {
    
    protected final String sql;
    protected final EntityPermission entityPermission;
    
    protected final Class<R> baseDatabaseResultClass;
    
    List<DatabaseResultMethod> databaseResultMethods;
    
    @SuppressWarnings("unchecked")
    protected BaseDatabaseQuery(final String sql, final EntityPermission entityPermission) {
        this.sql = sql;
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

    private List<DatabaseResultMethod> getDatabaseResultMethods(final ResultSet rs) {
        if(databaseResultMethods == null) {
            try {
                final var rsmd = rs.getMetaData();

                databaseResultMethods = new ArrayList<>();
                for(var columnIndex = 1; columnIndex <= rsmd.getColumnCount(); columnIndex++) {
                    databaseResultMethods.add(getDatabaseResultMethod(rsmd.getColumnLabel(columnIndex)));
                }
            } catch(SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        }

        return databaseResultMethods;
    }
    
    protected List<R> execute(final Object... params) {
        final var results = new ArrayList<R>();
        final var session = ThreadSession.currentSession();
        final var ps = session.prepareStatement(sql);

        Session.setQueryParams(ps, params);

        try {
            ps.execute();
            
            try(final var rs = ps.getResultSet()) {
                while(rs.next()) {
                    var columnIndex = 0;

                    try {
                        final var baseDatabaseResult = baseDatabaseResultClass.getDeclaredConstructor().newInstance();

                        for(var databaseResultMethod : getDatabaseResultMethods(rs)) {
                            final var type = databaseResultMethod.type;
                            Object param = null;

                            columnIndex++;
                            if(type.equals(Long.class)) {
                                param = rs.getLong(columnIndex);
                            } else if(type.equals(Integer.class)) {
                                param = rs.getInt(columnIndex);
                            } else if(type.equals(String.class)) {
                                param = rs.getString(columnIndex);
                            } else {
                                final var superclass = type.getSuperclass();

                                if(superclass != null) {
                                    if(superclass.equals(BasePK.class)) {
                                        param = databaseResultMethod.pkConstructor.newInstance(rs.getLong(columnIndex));
                                    } else if(superclass.equals(BaseEntity.class)) {
                                        var pk = databaseResultMethod.pkConstructor.newInstance(rs.getLong(columnIndex));

                                        param = databaseResultMethod.getEntityFromPkMethod.invoke(databaseResultMethod.factoryInstance, entityPermission, pk);
                                    }
                                }

                                if(param == null) {
                                    throw new PersistenceDatabaseException("unsupported Class in execute, " + type);
                                }
                            }

                            databaseResultMethod.setMethod.invoke(baseDatabaseResult, param);
                        }

                        results.add(baseDatabaseResult);
                    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                             InvocationTargetException ex) {
                        throw new PersistenceDatabaseException(ex);
                    }
                }
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return results;
    }
    
}
