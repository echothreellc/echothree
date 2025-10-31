// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.control.user.tag.server.command;

import com.echothree.control.user.tag.common.form.GetTagScopeEntityTypeForm;
import com.echothree.control.user.tag.common.result.TagResultFactory;
import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.tag.server.control.TagControl;
import com.echothree.model.control.tag.server.logic.TagScopeLogic;
import com.echothree.model.data.tag.server.entity.TagScopeEntityType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetTagScopeEntityTypeCommand
        extends BaseSingleEntityCommand<TagScopeEntityType, GetTagScopeEntityTypeForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TagScopeEntityType.name(), SecurityRoles.Review.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TagScopeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetTagScopeEntityTypeCommand */
    public GetTagScopeEntityTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected TagScopeEntityType getEntity() {
        var tagControl = Session.getModelController(TagControl.class);
        var tagScope = TagScopeLogic.getInstance().getTagScopeByName(this, form.getTagScopeName());
        var entityType = EntityTypeLogic.getInstance().getEntityTypeByName(this, form.getComponentVendorName(), form.getEntityTypeName());
        TagScopeEntityType tagScopeEntityType = null;

        if(!hasExecutionErrors()) {
            tagScopeEntityType = tagControl.getTagScopeEntityType(tagScope, entityType);

            if(tagScopeEntityType == null) {
                var entityTypeDetails = entityType.getLastDetail();

                addExecutionError(ExecutionErrors.UnknownTagScopeEntityType.name(), tagScope.getLastDetail().getTagScopeName(),
                        entityTypeDetails.getComponentVendor().getLastDetail().getComponentVendorName(),
                        entityTypeDetails.getEntityTypeName());
            }
        }

        return tagScopeEntityType;
    }

    @Override
    protected BaseResult getResult(TagScopeEntityType tagScopeEntityType) {
        var result = TagResultFactory.getGetTagScopeEntityTypeResult();

        if(tagScopeEntityType != null) {
            var tagControl = Session.getModelController(TagControl.class);

            result.setTagScopeEntityType(tagControl.getTagScopeEntityTypeTransfer(getUserVisit(), tagScopeEntityType));
        }

        return result;
    }

}
