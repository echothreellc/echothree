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

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

final class DatabaseResultMethod {

    enum Kind { SCALAR, PRIMARY_KEY, ENTITY }

    final String columnLabel;
    final Kind kind;
    final Method setMethod;
    final Class<?> type;
    final Object factoryInstance;
    final Constructor<?> pkConstructor;
    final Method getEntityFromPkMethod;

    DatabaseResultMethod(String columnLabel, Method setMethod, Class<?> type, Object factoryInstance, Constructor<?> pkConstructor, Method getEntityFromPkMethod) {
        this.columnLabel = columnLabel;
        this.kind = pkConstructor == null ? Kind.SCALAR : getEntityFromPkMethod == null ? Kind.PRIMARY_KEY : Kind.ENTITY;
        this.setMethod = setMethod;
        this.type = type;
        this.factoryInstance = factoryInstance;
        this.pkConstructor = pkConstructor;
        this.getEntityFromPkMethod = getEntityFromPkMethod;
    }

}
