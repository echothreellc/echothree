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

package com.echothree.control.user.index.server.command;

import com.echothree.control.user.index.common.form.GetIndexFieldsForm;
import com.echothree.control.user.index.common.result.IndexResultFactory;
import com.echothree.model.control.index.server.control.IndexControl;
import com.echothree.model.control.index.server.logic.IndexTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.index.server.entity.IndexField;
import com.echothree.model.data.index.server.entity.IndexType;
import com.echothree.model.data.index.server.factory.IndexFieldFactory;
import com.echothree.util.common.command.BaseResult;
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
public class GetIndexFieldsCommand
        extends BasePaginatedMultipleEntitiesCommand<IndexField, GetIndexFieldsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.IndexField.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("IndexTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    @Inject
    IndexControl indexControl;

    @Inject
    IndexTypeLogic indexTypeLogic;

    /** Creates a new instance of GetIndexFieldsCommand */
    public GetIndexFieldsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    IndexType indexType;

    @Override
    protected void handleForm() {
        indexType = indexTypeLogic.getIndexTypeByName(this, form.getIndexTypeName());
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : indexControl.countIndexFieldsByIndexType(indexType);
    }

    @Override
    protected Collection<IndexField> getEntities() {
        return hasExecutionErrors() ? null : indexControl.getIndexFields(indexType);
    }

    @Override
    protected BaseResult getResult(Collection<IndexField> entities) {
        var result = IndexResultFactory.getGetIndexFieldsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setIndexType(indexControl.getIndexTypeTransfer(userVisit, indexType));

            if(session.hasLimit(IndexFieldFactory.class)) {
                result.setIndexFieldCount(getTotalEntities());
            }

            result.setIndexFields(indexControl.getIndexFieldTransfersByIndexType(userVisit, indexType));
        }

        return result;
    }
    
}
