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

import com.echothree.control.user.tag.common.form.DeleteEntityTagForm;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.tag.server.control.TagControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.tag.server.entity.EntityTag;
import com.echothree.model.data.tag.server.entity.Tag;
import com.echothree.model.data.tag.server.entity.TagScope;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DeleteEntityTagCommand
        extends BaseSimpleCommand<DeleteEntityTagForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityTag.name(), SecurityRoles.Delete.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, true, null, null),
                new FieldDefinition("TagScopeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TagName", FieldType.TAG, true, null, null)
                ));
    }
    
    /** Creates a new instance of DeleteEntityTagCommand */
    public DeleteEntityTagCommand(UserVisitPK userVisitPK, DeleteEntityTagForm form) {
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
                String tagName = form.getTagName();
                Tag tag = tagControl.getTagByNameForUpdate(tagScope, tagName);
                
                if(tag != null) {
                    EntityTag entityTag = tagControl.getEntityTagForUpdate(taggedEntityInstance, tag);
                    
                    if(entityTag != null) {
                        var partyPK = getPartyPK();

                        tagControl.deleteEntityTag(entityTag, partyPK);

                        if(tagControl.countEntityTagsByTag(tag) == 0) {
                            tagControl.deleteTag(tag, partyPK);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownEntityTag.name(), entityRef, tagScopeName, tagName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownTagName.name(), tagScopeName, tagName);
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
