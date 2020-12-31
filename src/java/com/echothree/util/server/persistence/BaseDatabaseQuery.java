// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDatabaseQuery<R extends BaseDatabaseResult> {
    
    protected final String sql;
    protected final EntityPermission entityPermission;
    
    protected Class<R> baseDatabaseResultClass;
    
    List<DatabaseResultMethod> databaseResultMethods;
    
    @SuppressWarnings("unchecked")
    protected BaseDatabaseQuery(final String sql, final EntityPermission entityPermission) {
        this.sql = sql;
        this.entityPermission = entityPermission;
        
        this.baseDatabaseResultClass = (Class<R>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
    private DatabaseResultMethod getDatabaseResultMethod(String columnLabel) {
        DatabaseResultMethod databaseResultMethod = null;

        try {
            Method getMethod = baseDatabaseResultClass.getMethod("get" + columnLabel);
            Class<?> returnType = getMethod.getReturnType();
            Method setMethod = baseDatabaseResultClass.getMethod("set" + columnLabel, returnType);
            Object factoryInstance = null;
            Constructor<?> pkConstructor = null;
            Method getEntityFromPkMethod = null;
            
            if(!returnType.equals(Long.class) && !returnType.equals(Integer.class) && !returnType.equals(String.class)) {
                Class<?> superclass = returnType.getSuperclass();

                if(superclass != null) {
                    if(superclass.equals(BasePK.class)) {
                        pkConstructor = returnType.getDeclaredConstructor(Long.class);
                    } else if(superclass.equals(BaseEntity.class)) {
                        String name = returnType.getName();
                        String[] nameComponents = Splitter.on('.').trimResults().omitEmptyStrings().splitToList(name).toArray(new String[0]);
                        String factoryName = null;
                        String pkName = null;

                        for(int i = 0 ; i < nameComponents.length ; i++) {
                            if(nameComponents[i].equals("server")) {
                                int j = i;
                                String baseClassName = nameComponents[i + 2];

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
                            ClassLoader classLoader = returnType.getClassLoader();
                            Class<?> pkType = classLoader.loadClass(pkName);
                            Class<?> factoryType = classLoader.loadClass(factoryName);
                            Method getInstanceMethod = factoryType.getDeclaredMethod("getInstance");

                            factoryInstance = getInstanceMethod.invoke(null);
                            pkConstructor = pkType.getDeclaredConstructor(Long.class);
                            getEntityFromPkMethod = factoryType.getDeclaredMethod("getEntityFromPK", Session.class, EntityPermission.class, pkType);
                        } catch(ClassNotFoundException cnfe) {
                            throw new PersistenceDatabaseException(cnfe);
                        } catch(java.lang.IllegalAccessException iae) {
                            throw new PersistenceDatabaseException(iae);
                        } catch(java.lang.reflect.InvocationTargetException ite) {
                            throw new PersistenceDatabaseException(ite);
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

    private List<DatabaseResultMethod> getDatabaseResultMethods(ResultSet rs) {
        if(databaseResultMethods == null) {
            try {
                ResultSetMetaData rsmd = rs.getMetaData();

                databaseResultMethods = new ArrayList<>();
                for(int columnIndex = 1; columnIndex <= rsmd.getColumnCount(); columnIndex++) {
                    databaseResultMethods.add(getDatabaseResultMethod(rsmd.getColumnLabel(columnIndex)));
                }
            } catch(SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        }

        return databaseResultMethods;
    }
    
    protected List<R> execute(final Object... params) {
        List<R> results = new ArrayList<>();
        Session session = ThreadSession.currentSession();
        PreparedStatement ps = session.prepareStatement(sql);

        Session.setQueryParams(ps, params);

        try {
            ps.execute();
            
            try (ResultSet rs = ps.getResultSet()) {
                while(rs.next()) {
                    int columnIndex = 0;

                    try {
                        R baseDatabaseResult = baseDatabaseResultClass.getDeclaredConstructor().newInstance();

                        for(DatabaseResultMethod databaseResultMethod : getDatabaseResultMethods(rs)) {
                            Class<?> type = databaseResultMethod.type;
                            Object param = null;

                            columnIndex++;
                            if(type.equals(Long.class)) {
                                param = rs.getLong(columnIndex);
                            } else if(type.equals(Integer.class)) {
                                param = rs.getInt(columnIndex);
                            } else if(type.equals(String.class)) {
                                param = rs.getString(columnIndex);
                            } else {
                                Class<?> superclass = type.getSuperclass();

                                if(superclass != null) {
                                    if(superclass.equals(BasePK.class)) {
                                        param = databaseResultMethod.pkConstructor.newInstance(rs.getLong(columnIndex));
                                    } else if(superclass.equals(BaseEntity.class)) {
                                        Object pk = databaseResultMethod.pkConstructor.newInstance(rs.getLong(columnIndex));

                                        param = databaseResultMethod.getEntityFromPkMethod.invoke(databaseResultMethod.factoryInstance, session, entityPermission, pk);
                                    }
                                }

                                if(param == null) {
                                    throw new PersistenceDatabaseException("unsupported Class in execute, " + type);
                                }
                            }

                            databaseResultMethod.setMethod.invoke(baseDatabaseResult, param);
                        }

                        results.add(baseDatabaseResult);
                    } catch (NoSuchMethodException nsme) {
                        throw new PersistenceDatabaseException(nsme);
                    } catch (InstantiationException ie) {
                        throw new PersistenceDatabaseException(ie);
                    } catch (IllegalAccessException iae) {
                        throw new PersistenceDatabaseException(iae);
                    } catch (InvocationTargetException ite) {
                        throw new PersistenceDatabaseException(ite);
                    }
                }
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return results;
    }
    
}
