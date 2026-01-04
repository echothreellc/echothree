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

package com.echothree.control.user.security.server.command;

import com.echothree.control.user.security.common.edit.SecurityEditFactory;
import com.echothree.control.user.security.common.edit.SecurityRoleGroupEdit;
import com.echothree.control.user.security.common.form.EditSecurityRoleGroupForm;
import com.echothree.control.user.security.common.result.EditSecurityRoleGroupResult;
import com.echothree.control.user.security.common.result.SecurityResultFactory;
import com.echothree.control.user.security.common.spec.SecurityRoleGroupSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.data.security.server.entity.SecurityRoleGroup;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditSecurityRoleGroupCommand
        extends BaseAbstractEditCommand<SecurityRoleGroupSpec, SecurityRoleGroupEdit, EditSecurityRoleGroupResult, SecurityRoleGroup, SecurityRoleGroup> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SecurityRoleGroup.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SecurityRoleGroupName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SecurityRoleGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentSecurityRoleGroupName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditSecurityRoleGroupCommand */
    public EditSecurityRoleGroupCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditSecurityRoleGroupResult getResult() {
        return SecurityResultFactory.getEditSecurityRoleGroupResult();
    }
    
    @Override
    public SecurityRoleGroupEdit getEdit() {
        return SecurityEditFactory.getSecurityRoleGroupEdit();
    }
    
    @Override
    public SecurityRoleGroup getEntity(EditSecurityRoleGroupResult result) {
        var securityControl = Session.getModelController(SecurityControl.class);
        SecurityRoleGroup securityRoleGroup;
        var securityRoleGroupName = spec.getSecurityRoleGroupName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            securityRoleGroup = securityControl.getSecurityRoleGroupByName(securityRoleGroupName);
        } else { // EditMode.UPDATE
            securityRoleGroup = securityControl.getSecurityRoleGroupByNameForUpdate(securityRoleGroupName);
        }

        if(securityRoleGroup != null) {
            result.setSecurityRoleGroup(securityControl.getSecurityRoleGroupTransfer(getUserVisit(), securityRoleGroup));
        } else {
            addExecutionError(ExecutionErrors.UnknownSecurityRoleGroupName.name(), securityRoleGroupName);
        }

        return securityRoleGroup;
    }
    
    @Override
    public SecurityRoleGroup getLockEntity(SecurityRoleGroup securityRoleGroup) {
        return securityRoleGroup;
    }
    
    @Override
    public void fillInResult(EditSecurityRoleGroupResult result, SecurityRoleGroup securityRoleGroup) {
        var securityControl = Session.getModelController(SecurityControl.class);
        
        result.setSecurityRoleGroup(securityControl.getSecurityRoleGroupTransfer(getUserVisit(), securityRoleGroup));
    }
    
    SecurityRoleGroup parentSecurityRoleGroup = null;
    
    @Override
    public void doLock(SecurityRoleGroupEdit edit, SecurityRoleGroup securityRoleGroup) {
        var securityControl = Session.getModelController(SecurityControl.class);
        var securityRoleGroupDescription = securityControl.getSecurityRoleGroupDescription(securityRoleGroup, getPreferredLanguage());
        var securityRoleGroupDetail = securityRoleGroup.getLastDetail();
        
        parentSecurityRoleGroup = securityRoleGroupDetail.getParentSecurityRoleGroup();
        if(parentSecurityRoleGroup != null && SecurityRoleGroups.ROOT.name().equals(parentSecurityRoleGroup.getLastDetail().getSecurityRoleGroupName())) {
            parentSecurityRoleGroup = null;
        }
        
        edit.setSecurityRoleGroupName(securityRoleGroupDetail.getSecurityRoleGroupName());
        edit.setParentSecurityRoleGroupName(parentSecurityRoleGroup == null? null: parentSecurityRoleGroup.getLastDetail().getSecurityRoleGroupName());
        edit.setIsDefault(securityRoleGroupDetail.getIsDefault().toString());
        edit.setSortOrder(securityRoleGroupDetail.getSortOrder().toString());

        if(securityRoleGroupDescription != null) {
            edit.setDescription(securityRoleGroupDescription.getDescription());
        }
    }
        
    @Override
    public void canUpdate(SecurityRoleGroup securityRoleGroup) {
        var securityControl = Session.getModelController(SecurityControl.class);
        var securityRoleGroupName = edit.getSecurityRoleGroupName();
        var duplicateSecurityRoleGroup = securityControl.getSecurityRoleGroupByName(securityRoleGroupName);

        if(duplicateSecurityRoleGroup == null || securityRoleGroup.equals(duplicateSecurityRoleGroup)) {
            var parentSecurityRoleGroupName = edit.getParentSecurityRoleGroupName();
            
            parentSecurityRoleGroup = securityControl.getSecurityRoleGroupByName(parentSecurityRoleGroupName == null? SecurityRoleGroups.ROOT.name(): parentSecurityRoleGroupName);

            if(parentSecurityRoleGroup != null) {
                if(!securityControl.isParentSecurityRoleGroupSafe(securityRoleGroup, parentSecurityRoleGroup)) {
                    addExecutionError(ExecutionErrors.InvalidParentSecurityRoleGroup.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownParentSecurityRoleGroupName.name(), parentSecurityRoleGroupName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateSecurityRoleGroupName.name(), securityRoleGroupName);
        }
    }
    
    @Override
    public void doUpdate(SecurityRoleGroup securityRoleGroup) {
        var securityControl = Session.getModelController(SecurityControl.class);
        var partyPK = getPartyPK();
        var securityRoleGroupDetailValue = securityControl.getSecurityRoleGroupDetailValueForUpdate(securityRoleGroup);
        var securityRoleGroupDescription = securityControl.getSecurityRoleGroupDescriptionForUpdate(securityRoleGroup, getPreferredLanguage());
        var description = edit.getDescription();

        securityRoleGroupDetailValue.setSecurityRoleGroupName(edit.getSecurityRoleGroupName());
        securityRoleGroupDetailValue.setParentSecurityRoleGroupPK(parentSecurityRoleGroup == null? null: parentSecurityRoleGroup.getPrimaryKey());
        securityRoleGroupDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        securityRoleGroupDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        securityControl.updateSecurityRoleGroupFromValue(securityRoleGroupDetailValue, partyPK);

        if(securityRoleGroupDescription == null && description != null) {
            securityControl.createSecurityRoleGroupDescription(securityRoleGroup, getPreferredLanguage(), description, partyPK);
        } else if(securityRoleGroupDescription != null && description == null) {
            securityControl.deleteSecurityRoleGroupDescription(securityRoleGroupDescription, partyPK);
        } else if(securityRoleGroupDescription != null && description != null) {
            var securityRoleGroupDescriptionValue = securityControl.getSecurityRoleGroupDescriptionValue(securityRoleGroupDescription);

            securityRoleGroupDescriptionValue.setDescription(description);
            securityControl.updateSecurityRoleGroupDescriptionFromValue(securityRoleGroupDescriptionValue, partyPK);
        }
    }
    
}
