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

package com.echothree.control.user.license.server.command;

import com.echothree.control.user.license.common.edit.LicenseEditFactory;
import com.echothree.control.user.license.common.edit.LicenseTypeEdit;
import com.echothree.control.user.license.common.form.EditLicenseTypeForm;
import com.echothree.control.user.license.common.result.EditLicenseTypeResult;
import com.echothree.control.user.license.common.result.LicenseResultFactory;
import com.echothree.control.user.license.common.spec.LicenseTypeSpec;
import com.echothree.model.control.license.server.control.LicenseControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.license.server.entity.LicenseType;
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

public class EditLicenseTypeCommand
        extends BaseAbstractEditCommand<LicenseTypeSpec, LicenseTypeEdit, EditLicenseTypeResult, LicenseType, LicenseType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.LicenseType.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LicenseTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LicenseTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditLicenseTypeCommand */
    public EditLicenseTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditLicenseTypeResult getResult() {
        return LicenseResultFactory.getEditLicenseTypeResult();
    }

    @Override
    public LicenseTypeEdit getEdit() {
        return LicenseEditFactory.getLicenseTypeEdit();
    }

    @Override
    public LicenseType getEntity(EditLicenseTypeResult result) {
        var licenseControl = Session.getModelController(LicenseControl.class);
        LicenseType licenseType;
        var licenseTypeName = spec.getLicenseTypeName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            licenseType = licenseControl.getLicenseTypeByName(licenseTypeName);
        } else { // EditMode.UPDATE
            licenseType = licenseControl.getLicenseTypeByNameForUpdate(licenseTypeName);
        }

        if(licenseType == null) {
            addExecutionError(ExecutionErrors.UnknownLicenseTypeName.name(), licenseTypeName);
        }

        return licenseType;
    }

    @Override
    public LicenseType getLockEntity(LicenseType licenseType) {
        return licenseType;
    }

    @Override
    public void fillInResult(EditLicenseTypeResult result, LicenseType licenseType) {
        var licenseControl = Session.getModelController(LicenseControl.class);

        result.setLicenseType(licenseControl.getLicenseTypeTransfer(getUserVisit(), licenseType));
    }

    @Override
    public void doLock(LicenseTypeEdit edit, LicenseType licenseType) {
        var licenseControl = Session.getModelController(LicenseControl.class);
        var licenseTypeDescription = licenseControl.getLicenseTypeDescription(licenseType, getPreferredLanguage());
        var licenseTypeDetail = licenseType.getLastDetail();

        edit.setLicenseTypeName(licenseTypeDetail.getLicenseTypeName());
        edit.setIsDefault(licenseTypeDetail.getIsDefault().toString());
        edit.setSortOrder(licenseTypeDetail.getSortOrder().toString());

        if(licenseTypeDescription != null) {
            edit.setDescription(licenseTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(LicenseType licenseType) {
        var licenseControl = Session.getModelController(LicenseControl.class);
        var licenseTypeName = edit.getLicenseTypeName();
        var duplicateLicenseType = licenseControl.getLicenseTypeByName(licenseTypeName);

        if(duplicateLicenseType != null && !licenseType.equals(duplicateLicenseType)) {
            addExecutionError(ExecutionErrors.DuplicateLicenseTypeName.name(), licenseTypeName);
        }
    }

    @Override
    public void doUpdate(LicenseType licenseType) {
        var licenseControl = Session.getModelController(LicenseControl.class);
        var partyPK = getPartyPK();
        var licenseTypeDetailValue = licenseControl.getLicenseTypeDetailValueForUpdate(licenseType);
        var licenseTypeDescription = licenseControl.getLicenseTypeDescriptionForUpdate(licenseType, getPreferredLanguage());
        var description = edit.getDescription();

        licenseTypeDetailValue.setLicenseTypeName(edit.getLicenseTypeName());
        licenseTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        licenseTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        licenseControl.updateLicenseTypeFromValue(licenseTypeDetailValue, partyPK);

        if(licenseTypeDescription == null && description != null) {
            licenseControl.createLicenseTypeDescription(licenseType, getPreferredLanguage(), description, partyPK);
        } else {
            if(licenseTypeDescription != null && description == null) {
                licenseControl.deleteLicenseTypeDescription(licenseTypeDescription, partyPK);
            } else {
                if(licenseTypeDescription != null && description != null) {
                    var licenseTypeDescriptionValue = licenseControl.getLicenseTypeDescriptionValue(licenseTypeDescription);

                    licenseTypeDescriptionValue.setDescription(description);
                    licenseControl.updateLicenseTypeDescriptionFromValue(licenseTypeDescriptionValue, partyPK);
                }
            }
        }
    }
    
}
