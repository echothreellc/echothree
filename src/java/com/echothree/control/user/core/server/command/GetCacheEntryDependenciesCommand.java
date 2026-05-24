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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.form.GetCacheEntryDependenciesForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.server.control.CacheEntryControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.CacheEntry;
import com.echothree.model.data.core.server.entity.CacheEntryDependency;
import com.echothree.model.data.core.server.factory.CacheEntryDependencyFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetCacheEntryDependenciesCommand
        extends BasePaginatedMultipleEntitiesCommand<CacheEntryDependency, GetCacheEntryDependenciesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.CacheEntryDependency.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CacheEntryKey", FieldType.STRING, true, 1L, 200L)
        );
    }
    
    @Inject
    CacheEntryControl cacheEntryControl;

    /** Creates a new instance of GetCacheEntryDependenciesCommand */
    public GetCacheEntryDependenciesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    CacheEntry cacheEntry;

    @Override
    protected void handleForm() {
        var cacheEntryKey = form.getCacheEntryKey();

        cacheEntry = cacheEntryControl.getCacheEntryByCacheEntryKey(cacheEntryKey);

        if(cacheEntry == null) {
            addExecutionError(ExecutionErrors.UnknownCacheEntryKey.name(), cacheEntryKey);
        }
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : cacheEntryControl.countCacheEntryDependenciesByCacheEntry(cacheEntry);
    }

    @Override
    protected Collection<CacheEntryDependency> getEntities() {
        return hasExecutionErrors() ? null : cacheEntryControl.getCacheEntryDependenciesByCacheEntry(cacheEntry);
    }

    @Override
    protected BaseResult getResult(Collection<CacheEntryDependency> entities) {
        var result = CoreResultFactory.getGetCacheEntryDependenciesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setCacheEntry(cacheEntryControl.getCacheEntryTransfer(userVisit, cacheEntry));

            if(session.hasLimit(CacheEntryDependencyFactory.class)) {
                result.setCacheEntryDependencyCount(getTotalEntities());
            }

            result.setCacheEntryDependencies(cacheEntryControl.getCacheEntryDependencyTransfers(userVisit, entities));
        }

        return result;
    }
    
}
