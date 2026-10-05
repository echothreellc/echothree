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

package com.echothree.util.server.persistence.mutex;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;

@Dependent
public class DatabaseMutexProducer {

    // CDI producer parameters are injection points and require the flavor qualifier.
    @SuppressWarnings("UnnecessaryQualifier")
    @Produces
    public DatabaseMutex produceMutex(final DatabaseFlavor.Flavor flavor,
            @DatabaseFlavor(DatabaseFlavor.Flavor.MYSQL) final DatabaseMutex mySqlMutex,
            @DatabaseFlavor(DatabaseFlavor.Flavor.POSTGRES) final DatabaseMutex postgresMutex) {
        return switch(flavor) {
            case MYSQL -> mySqlMutex;
            case POSTGRES -> postgresMutex;
        };
    }

}
