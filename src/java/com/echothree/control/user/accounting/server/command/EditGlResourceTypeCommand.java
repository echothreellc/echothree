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
import com.echothree.control.user.accounting.common.edit.GlResourceTypeEdit;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.result.EditGlResourceTypeResult;
import com.echothree.control.user.accounting.common.spec.GlResourceTypeSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.accounting.server.logic.GlResourceTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
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
public class EditGlResourceTypeCommand
        extends BaseAbstractEditCommand<GlResourceTypeSpec, GlResourceTypeEdit, EditGlResourceTypeResult, GlResourceType, GlResourceType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.GlResourceType.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("GlResourceTypeName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("GlResourceTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    AccountingControl accountingControl;

    @Inject
    GlResourceTypeLogic glResourceTypeLogic;

    /** Creates a new instance of EditGlResourceTypeCommand */
    public EditGlResourceTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditGlResourceTypeResult getResult() {
        return AccountingResultFactory.getEditGlResourceTypeResult();
    }

    @Override
    public GlResourceTypeEdit getEdit() {
        return AccountingEditFactory.getGlResourceTypeEdit();
    }

    @Override
    public GlResourceType getEntity(EditGlResourceTypeResult result) {
        return glResourceTypeLogic.getGlResourceTypeByName(this, spec.getGlResourceTypeName(), editModeToEntityPermission(editMode));
    }

    @Override
    public GlResourceType getLockEntity(GlResourceType glResourceType) {
        return glResourceType;
    }

    @Override
    public void fillInResult(EditGlResourceTypeResult result, GlResourceType glResourceType) {
        result.setGlResourceType(accountingControl.getGlResourceTypeTransfer(getUserVisit(), glResourceType));
    }

    @Override
    public void doLock(GlResourceTypeEdit edit, GlResourceType glResourceType) {
        var glResourceTypeDescription = accountingControl.getGlResourceTypeDescription(glResourceType, getPreferredLanguage());
        var glResourceTypeDetail = glResourceType.getLastDetail();

        edit.setGlResourceTypeName(glResourceTypeDetail.getGlResourceTypeName());
        edit.setIsDefault(glResourceTypeDetail.getIsDefault().toString());
        edit.setSortOrder(glResourceTypeDetail.getSortOrder().toString());

        if(glResourceTypeDescription != null) {
            edit.setDescription(glResourceTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(GlResourceType glResourceType) {
        var glResourceTypeName = edit.getGlResourceTypeName();
        var duplicateGlResourceType = accountingControl.getGlResourceTypeByName(glResourceTypeName);

        if(duplicateGlResourceType != null && !glResourceType.equals(duplicateGlResourceType)) {
            addExecutionError(ExecutionErrors.DuplicateGlResourceTypeName.name(), glResourceTypeName);
        }
    }

    @Override
    public void doUpdate(GlResourceType glResourceType) {
        var partyPK = getPartyPK();
        var glResourceTypeDetailValue = accountingControl.getGlResourceTypeDetailValueForUpdate(glResourceType);
        var glResourceTypeDescription = accountingControl.getGlResourceTypeDescriptionForUpdate(glResourceType, getPreferredLanguage());
        var description = edit.getDescription();

        glResourceTypeDetailValue.setGlResourceTypeName(edit.getGlResourceTypeName());
        glResourceTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        glResourceTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        accountingControl.updateGlResourceTypeFromValue(glResourceTypeDetailValue, partyPK);

        if(glResourceTypeDescription == null && description != null) {
            accountingControl.createGlResourceTypeDescription(glResourceType, getPreferredLanguage(), description, partyPK);
        } else if(glResourceTypeDescription != null && description == null) {
            accountingControl.deleteGlResourceTypeDescription(glResourceTypeDescription, partyPK);
        } else if(glResourceTypeDescription != null && description != null) {
            var glResourceTypeDescriptionValue = accountingControl.getGlResourceTypeDescriptionValue(glResourceTypeDescription);

            glResourceTypeDescriptionValue.setDescription(description);
            accountingControl.updateGlResourceTypeDescriptionFromValue(glResourceTypeDescriptionValue, partyPK);
        }
    }

}
