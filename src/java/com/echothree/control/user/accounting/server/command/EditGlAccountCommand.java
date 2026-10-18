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
import com.echothree.control.user.accounting.common.edit.GlAccountEdit;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.result.EditGlAccountResult;
import com.echothree.control.user.accounting.common.spec.GlAccountUniversalSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.accounting.server.logic.GlAccountCategoryLogic;
import com.echothree.model.control.accounting.server.logic.GlAccountClassLogic;
import com.echothree.model.control.accounting.server.logic.GlAccountLogic;
import com.echothree.model.control.accounting.server.logic.GlResourceTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.accounting.server.entity.GlAccountCategory;
import com.echothree.model.data.accounting.server.entity.GlAccountClass;
import com.echothree.model.data.accounting.server.entity.GlResourceType;
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
public class EditGlAccountCommand
        extends BaseAbstractEditCommand<GlAccountUniversalSpec, GlAccountEdit, EditGlAccountResult, GlAccount, GlAccount> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.GlAccount.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("GlAccountName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("GlAccountName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentGlAccountName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("GlAccountClassName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("GlAccountCategoryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("GlResourceTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, false, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    AccountingControl accountingControl;

    @Inject
    GlAccountCategoryLogic glAccountCategoryLogic;

    @Inject
    GlAccountClassLogic glAccountClassLogic;

    @Inject
    GlAccountLogic glAccountLogic;

    @Inject
    GlResourceTypeLogic glResourceTypeLogic;

    /** Creates a new instance of EditGlAccountCommand */
    public EditGlAccountCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditGlAccountResult getResult() {
        return AccountingResultFactory.getEditGlAccountResult();
    }

    @Override
    public GlAccountEdit getEdit() {
        return AccountingEditFactory.getGlAccountEdit();
    }

    @Override
    public GlAccount getEntity(EditGlAccountResult result) {
        return glAccountLogic.getGlAccountByUniversalSpec(this, spec, false, null, editModeToEntityPermission(editMode));
    }

    @Override
    public GlAccount getLockEntity(GlAccount glAccount) {
        return glAccount;
    }

    @Override
    public void fillInResult(EditGlAccountResult result, GlAccount glAccount) {
        result.setGlAccount(accountingControl.getGlAccountTransfer(getUserVisit(), glAccount));
    }

    private GlAccount parentGlAccount;
    private GlAccountClass glAccountClass;
    private GlAccountCategory glAccountCategory;
    private GlResourceType glResourceType;

    @Override
    public void doLock(GlAccountEdit edit, GlAccount glAccount) {
        var glAccountDescription = accountingControl.getGlAccountDescription(glAccount, getPreferredLanguage());
        var glAccountDetail = glAccount.getLastDetail();
        var parentGlAccount = glAccountDetail.getParentGlAccount();
        var glAccountCategory = glAccountDetail.getGlAccountCategory();
        var isDefault = glAccountDetail.getIsDefault();

        edit.setGlAccountName(glAccountDetail.getGlAccountName());
        edit.setParentGlAccountName(parentGlAccount == null? null: parentGlAccount.getLastDetail().getGlAccountName());
        edit.setGlAccountClassName(glAccountDetail.getGlAccountClass().getLastDetail().getGlAccountClassName());
        edit.setGlAccountCategoryName(glAccountCategory == null? null: glAccountCategory.getLastDetail().getGlAccountCategoryName());
        edit.setGlResourceTypeName(glAccountDetail.getGlResourceType().getLastDetail().getGlResourceTypeName());
        edit.setIsDefault(isDefault == null? null: isDefault.toString());

        if(glAccountDescription != null) {
            edit.setDescription(glAccountDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(GlAccount glAccount) {
        var glAccountName = edit.getGlAccountName();
        var duplicateGlAccount = accountingControl.getGlAccountByName(glAccountName);

        if(duplicateGlAccount == null || glAccount.equals(duplicateGlAccount)) {
            var parentGlAccountName = edit.getParentGlAccountName();

            parentGlAccount = parentGlAccountName == null? null: accountingControl.getGlAccountByName(parentGlAccountName);

            if(parentGlAccountName == null || parentGlAccount != null) {
                if(accountingControl.isParentGlAccountSafe(glAccount, parentGlAccount)) {
                    glAccountClass = glAccountClassLogic.getGlAccountClassByName(this, edit.getGlAccountClassName());

                    if(!hasExecutionErrors()) {
                        var glAccountCategoryName = edit.getGlAccountCategoryName();

                        glAccountCategory = glAccountCategoryName == null? null: glAccountCategoryLogic.getGlAccountCategoryByName(this, glAccountCategoryName);

                        if(!hasExecutionErrors()) {
                            glResourceType = glResourceTypeLogic.getGlResourceTypeByName(this, edit.getGlResourceTypeName());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.InvalidParentGlAccount.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownParentGlAccountName.name(), parentGlAccountName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateGlAccountName.name(), glAccountName);
        }
    }

    @Override
    public void doUpdate(GlAccount glAccount) {
        var partyPK = getPartyPK();
        var glAccountDetailValue = accountingControl.getGlAccountDetailValueForUpdate(glAccount);
        var glAccountDescription = accountingControl.getGlAccountDescriptionForUpdate(glAccount, getPreferredLanguage());
        var isDefault = glAccountCategory == null? null: Boolean.valueOf(edit.getIsDefault());
        var description = edit.getDescription();

        glAccountDetailValue.setGlAccountName(edit.getGlAccountName());
        glAccountDetailValue.setParentGlAccountPK(parentGlAccount == null? null: parentGlAccount.getPrimaryKey());
        glAccountDetailValue.setGlAccountClassPK(glAccountClass.getPrimaryKey());
        glAccountDetailValue.setGlAccountCategoryPK(glAccountCategory == null? null: glAccountCategory.getPrimaryKey());
        glAccountDetailValue.setGlResourceTypePK(glResourceType.getPrimaryKey());
        glAccountDetailValue.setIsDefault(isDefault);

        glAccountLogic.updateGlAccountFromValue(glAccountDetailValue, partyPK);

        if(glAccountDescription == null && description != null) {
            accountingControl.createGlAccountDescription(glAccount, getPreferredLanguage(), description, partyPK);
        } else if(glAccountDescription != null && description == null) {
            accountingControl.deleteGlAccountDescription(glAccountDescription, partyPK);
        } else if(glAccountDescription != null && description != null) {
            var glAccountDescriptionValue = accountingControl.getGlAccountDescriptionValue(glAccountDescription);

            glAccountDescriptionValue.setDescription(description);
            accountingControl.updateGlAccountDescriptionFromValue(glAccountDescriptionValue, partyPK);
        }
    }

}
