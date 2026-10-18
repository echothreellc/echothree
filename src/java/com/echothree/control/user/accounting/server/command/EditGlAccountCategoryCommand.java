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
import com.echothree.control.user.accounting.common.edit.GlAccountCategoryEdit;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.result.EditGlAccountCategoryResult;
import com.echothree.control.user.accounting.common.spec.GlAccountCategorySpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.accounting.server.logic.GlAccountCategoryLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.GlAccountCategory;
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
public class EditGlAccountCategoryCommand
        extends BaseAbstractEditCommand<GlAccountCategorySpec, GlAccountCategoryEdit, EditGlAccountCategoryResult, GlAccountCategory, GlAccountCategory> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.GlAccountCategory.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("GlAccountCategoryName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("GlAccountCategoryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentGlAccountCategoryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    AccountingControl accountingControl;

    @Inject
    GlAccountCategoryLogic glAccountCategoryLogic;

    /** Creates a new instance of EditGlAccountCategoryCommand */
    public EditGlAccountCategoryCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditGlAccountCategoryResult getResult() {
        return AccountingResultFactory.getEditGlAccountCategoryResult();
    }

    @Override
    public GlAccountCategoryEdit getEdit() {
        return AccountingEditFactory.getGlAccountCategoryEdit();
    }

    @Override
    public GlAccountCategory getEntity(EditGlAccountCategoryResult result) {
        return glAccountCategoryLogic.getGlAccountCategoryByName(this, spec.getGlAccountCategoryName(), editModeToEntityPermission(editMode));
    }

    @Override
    public GlAccountCategory getLockEntity(GlAccountCategory glAccountCategory) {
        return glAccountCategory;
    }

    @Override
    public void fillInResult(EditGlAccountCategoryResult result, GlAccountCategory glAccountCategory) {
        result.setGlAccountCategory(accountingControl.getGlAccountCategoryTransfer(getUserVisit(), glAccountCategory));
    }

    private GlAccountCategory parentGlAccountCategory;

    @Override
    public void doLock(GlAccountCategoryEdit edit, GlAccountCategory glAccountCategory) {
        var glAccountCategoryDescription = accountingControl.getGlAccountCategoryDescription(glAccountCategory, getPreferredLanguage());
        var glAccountCategoryDetail = glAccountCategory.getLastDetail();
        var parentGlAccountCategory = glAccountCategoryDetail.getParentGlAccountCategory();

        edit.setGlAccountCategoryName(glAccountCategoryDetail.getGlAccountCategoryName());
        edit.setParentGlAccountCategoryName(parentGlAccountCategory == null? null: parentGlAccountCategory.getLastDetail().getGlAccountCategoryName());
        edit.setIsDefault(glAccountCategoryDetail.getIsDefault().toString());
        edit.setSortOrder(glAccountCategoryDetail.getSortOrder().toString());

        if(glAccountCategoryDescription != null) {
            edit.setDescription(glAccountCategoryDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(GlAccountCategory glAccountCategory) {
        var glAccountCategoryName = edit.getGlAccountCategoryName();
        var duplicateGlAccountCategory = accountingControl.getGlAccountCategoryByName(glAccountCategoryName);

        if(duplicateGlAccountCategory == null || glAccountCategory.equals(duplicateGlAccountCategory)) {
            var parentGlAccountCategoryName = edit.getParentGlAccountCategoryName();

            parentGlAccountCategory = parentGlAccountCategoryName == null? null: accountingControl.getGlAccountCategoryByName(parentGlAccountCategoryName);

            if(parentGlAccountCategoryName == null || parentGlAccountCategory != null) {
                if(!accountingControl.isParentGlAccountCategorySafe(glAccountCategory, parentGlAccountCategory)) {
                    addExecutionError(ExecutionErrors.InvalidParentGlAccountCategory.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownParentGlAccountCategoryName.name(), parentGlAccountCategoryName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateGlAccountCategoryName.name(), glAccountCategoryName);
        }
    }

    @Override
    public void doUpdate(GlAccountCategory glAccountCategory) {
        var partyPK = getPartyPK();
        var glAccountCategoryDetailValue = accountingControl.getGlAccountCategoryDetailValueForUpdate(glAccountCategory);
        var glAccountCategoryDescription = accountingControl.getGlAccountCategoryDescriptionForUpdate(glAccountCategory, getPreferredLanguage());
        var description = edit.getDescription();

        glAccountCategoryDetailValue.setGlAccountCategoryName(edit.getGlAccountCategoryName());
        glAccountCategoryDetailValue.setParentGlAccountCategoryPK(parentGlAccountCategory == null? null: parentGlAccountCategory.getPrimaryKey());
        glAccountCategoryDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        glAccountCategoryDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        accountingControl.updateGlAccountCategoryFromValue(glAccountCategoryDetailValue, partyPK);

        if(glAccountCategoryDescription == null && description != null) {
            accountingControl.createGlAccountCategoryDescription(glAccountCategory, getPreferredLanguage(), description, partyPK);
        } else if(glAccountCategoryDescription != null && description == null) {
            accountingControl.deleteGlAccountCategoryDescription(glAccountCategoryDescription, partyPK);
        } else if(glAccountCategoryDescription != null && description != null) {
            var glAccountCategoryDescriptionValue = accountingControl.getGlAccountCategoryDescriptionValue(glAccountCategoryDescription);

            glAccountCategoryDescriptionValue.setDescription(description);
            accountingControl.updateGlAccountCategoryDescriptionFromValue(glAccountCategoryDescriptionValue, partyPK);
        }
    }

}
