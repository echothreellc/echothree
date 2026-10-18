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

package com.echothree.control.user.accounting.server.command;

import com.echothree.control.user.accounting.common.edit.AccountingEditFactory;
import com.echothree.control.user.accounting.common.edit.GlAccountClassEdit;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.result.EditGlAccountClassResult;
import com.echothree.control.user.accounting.common.spec.GlAccountClassSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.accounting.server.logic.GlAccountClassLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.GlAccountClass;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditGlAccountClassCommand
        extends BaseAbstractEditCommand<GlAccountClassSpec, GlAccountClassEdit, EditGlAccountClassResult, GlAccountClass, GlAccountClass> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.GlAccountClass.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("GlAccountClassName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("GlAccountClassName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentGlAccountClassName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    AccountingControl accountingControl;

    @Inject
    GlAccountClassLogic glAccountClassLogic;

    /** Creates a new instance of EditGlAccountClassCommand */
    public EditGlAccountClassCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditGlAccountClassResult getResult() {
        return AccountingResultFactory.getEditGlAccountClassResult();
    }

    @Override
    public GlAccountClassEdit getEdit() {
        return AccountingEditFactory.getGlAccountClassEdit();
    }

    @Override
    public GlAccountClass getEntity(EditGlAccountClassResult result) {
        return glAccountClassLogic.getGlAccountClassByName(this, spec.getGlAccountClassName(), editModeToEntityPermission(editMode));
    }

    @Override
    public GlAccountClass getLockEntity(GlAccountClass glAccountClass) {
        return glAccountClass;
    }

    @Override
    public void fillInResult(EditGlAccountClassResult result, GlAccountClass glAccountClass) {
        result.setGlAccountClass(accountingControl.getGlAccountClassTransfer(getUserVisit(), glAccountClass));
    }

    private GlAccountClass parentGlAccountClass;

    @Override
    public void doLock(GlAccountClassEdit edit, GlAccountClass glAccountClass) {
        var glAccountClassDescription = accountingControl.getGlAccountClassDescription(glAccountClass, getPreferredLanguage());
        var glAccountClassDetail = glAccountClass.getLastDetail();
        var parentGlAccountClass = glAccountClassDetail.getParentGlAccountClass();

        edit.setGlAccountClassName(glAccountClassDetail.getGlAccountClassName());
        edit.setParentGlAccountClassName(parentGlAccountClass == null? null: parentGlAccountClass.getLastDetail().getGlAccountClassName());
        edit.setIsDefault(glAccountClassDetail.getIsDefault().toString());
        edit.setSortOrder(glAccountClassDetail.getSortOrder().toString());

        if(glAccountClassDescription != null) {
            edit.setDescription(glAccountClassDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(GlAccountClass glAccountClass) {
        var glAccountClassName = edit.getGlAccountClassName();
        var duplicateGlAccountClass = accountingControl.getGlAccountClassByName(glAccountClassName);

        if(duplicateGlAccountClass == null || glAccountClass.equals(duplicateGlAccountClass)) {
            var parentGlAccountClassName = edit.getParentGlAccountClassName();

            parentGlAccountClass = parentGlAccountClassName == null? null: accountingControl.getGlAccountClassByName(parentGlAccountClassName);

            if(parentGlAccountClassName == null || parentGlAccountClass != null) {
                if(!accountingControl.isParentGlAccountClassSafe(glAccountClass, parentGlAccountClass)) {
                    addExecutionError(ExecutionErrors.InvalidParentGlAccountClass.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownParentGlAccountClassName.name(), parentGlAccountClassName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateGlAccountClassName.name(), glAccountClassName);
        }
    }

    @Override
    public void doUpdate(GlAccountClass glAccountClass) {
        var partyPK = getPartyPK();
        var glAccountClassDetailValue = accountingControl.getGlAccountClassDetailValueForUpdate(glAccountClass);
        var glAccountClassDescription = accountingControl.getGlAccountClassDescriptionForUpdate(glAccountClass, getPreferredLanguage());
        var description = edit.getDescription();

        glAccountClassDetailValue.setGlAccountClassName(edit.getGlAccountClassName());
        glAccountClassDetailValue.setParentGlAccountClassPK(parentGlAccountClass == null? null: parentGlAccountClass.getPrimaryKey());
        glAccountClassDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        glAccountClassDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        accountingControl.updateGlAccountClassFromValue(glAccountClassDetailValue, partyPK);

        if(glAccountClassDescription == null && description != null) {
            accountingControl.createGlAccountClassDescription(glAccountClass, getPreferredLanguage(), description, partyPK);
        } else if(glAccountClassDescription != null && description == null) {
            accountingControl.deleteGlAccountClassDescription(glAccountClassDescription, partyPK);
        } else if(glAccountClassDescription != null && description != null) {
            var glAccountClassDescriptionValue = accountingControl.getGlAccountClassDescriptionValue(glAccountClassDescription);

            glAccountClassDescriptionValue.setDescription(description);
            accountingControl.updateGlAccountClassDescriptionFromValue(glAccountClassDescriptionValue, partyPK);
        }
    }

}
