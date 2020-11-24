// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.tag.server.control.TagControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityTypeDetail;
import com.echothree.model.data.tag.server.entity.EntityTag;
import com.echothree.model.data.tag.server.entity.Tag;
import com.echothree.model.data.tag.server.entity.TagScope;
import com.echothree.model.data.tag.server.entity.TagScopeEntityType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, true, null, null),
                new FieldDefinition("TagScopeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TagName", FieldType.TAG, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateEntityTagCommand */
    public CreateEntityTagCommand(UserVisitPK userVisitPK, CreateEntityTagForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var coreControl = getCoreControl();
        String entityRef = form.getEntityRef();
        EntityInstance taggedEntityInstance = coreControl.getEntityInstanceByEntityRef(entityRef);
        
        if(taggedEntityInstance != null) {
            var tagControl = Session.getModelController(TagControl.class);
            String tagScopeName = form.getTagScopeName();
            TagScope tagScope = tagControl.getTagScopeByName(tagScopeName);
            
            if(tagScope != null) {
                TagScopeEntityType tagScopeEntityType = tagControl.getTagScopeEntityType(tagScope, taggedEntityInstance.getEntityType());
                
                if(tagScopeEntityType != null) {
                    String tagName = form.getTagName();
                    Tag tag = tagControl.getTagByName(tagScope, tagName);
                    EntityTag entityTag = null;
                    BasePK createdBy = getPartyPK();
                    
                    if(tag == null) {
                        tag = tagControl.createTag(tagScope, tagName, createdBy);
                    } else {
                        entityTag = tagControl.getEntityTag(taggedEntityInstance, tag);
                    }
                    
                    if(entityTag == null) {
                        tagControl.createEntityTag(taggedEntityInstance, tag, createdBy);
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateEntityTag.name(), entityRef, tagScopeName, tagName);
                    }
                } else {
                    EntityTypeDetail entityTypeDetail = taggedEntityInstance.getEntityType().getLastDetail();
                    
                    addExecutionError(ExecutionErrors.UnknownTagScopeEntityType.name(), tagScopeName,
                            entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(), entityTypeDetail.getEntityTypeName());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownTagScopeName.name(), tagScopeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownEntityRef.name(), entityRef);
        }
        
        return null;
    }
    
}
