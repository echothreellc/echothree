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

import com.echothree.control.user.tag.common.form.CreateEntityTagForm;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.tag.server.control.TagControl;
import com.echothree.model.data.tag.server.entity.EntityTag;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.EntityInstanceUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CreateEntityTagCommand
        extends BaseSimpleCommand<CreateEntityTagForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityTag.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null),
                new FieldDefinition("TagScopeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TagName", FieldType.TAG, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateEntityTagCommand */
    public CreateEntityTagCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var possibleEntitySpecs = EntityInstanceLogic.getInstance().countPossibleEntitySpecs(form);

        if(possibleEntitySpecs == 1) {
            var taggedEntityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form);

            if(!hasExecutionErrors()) {
                var tagControl = Session.getModelController(TagControl.class);
                var tagScopeName = form.getTagScopeName();
                var tagScope = tagControl.getTagScopeByName(tagScopeName);

                if(tagScope != null) {
                    var tagScopeEntityType = tagControl.getTagScopeEntityType(tagScope, taggedEntityInstance.getEntityType());

                    if(tagScopeEntityType != null) {
                        var tagName = form.getTagName();
                        var tag = tagControl.getTagByName(tagScope, tagName);
                        EntityTag entityTag = null;
                        var createdBy = getPartyPK();

                        if(tag == null) {
                            tag = tagControl.createTag(tagScope, tagName, createdBy);
                        } else {
                            entityTag = tagControl.getEntityTag(taggedEntityInstance, tag);
                        }

                        if(entityTag == null) {
                            tagControl.createEntityTag(taggedEntityInstance, tag, createdBy);
                        } else {
                            addExecutionError(ExecutionErrors.DuplicateEntityTag.name(),
                                    EntityInstanceUtils.getInstance().getEntityRefByEntityInstance(taggedEntityInstance),
                                    tagScopeName, tagName);
                        }
                    } else {
                        var entityTypeDetail = taggedEntityInstance.getEntityType().getLastDetail();

                        addExecutionError(ExecutionErrors.UnknownTagScopeEntityType.name(), tagScopeName,
                                entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(), entityTypeDetail.getEntityTypeName());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownTagScopeName.name(), tagScopeName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return null;
    }
    
}
