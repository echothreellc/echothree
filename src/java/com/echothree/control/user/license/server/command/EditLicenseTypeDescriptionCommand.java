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
import com.echothree.control.user.license.common.edit.LicenseTypeDescriptionEdit;
import com.echothree.control.user.license.common.form.EditLicenseTypeDescriptionForm;
import com.echothree.control.user.license.common.result.EditLicenseTypeDescriptionResult;
import com.echothree.control.user.license.common.result.LicenseResultFactory;
import com.echothree.control.user.license.common.spec.LicenseTypeDescriptionSpec;
import com.echothree.model.control.license.server.control.LicenseControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.license.server.entity.LicenseType;
import com.echothree.model.data.license.server.entity.LicenseTypeDescription;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditLicenseTypeDescriptionCommand
        extends BaseAbstractEditCommand<LicenseTypeDescriptionSpec, LicenseTypeDescriptionEdit, EditLicenseTypeDescriptionResult, LicenseTypeDescription, LicenseType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.LicenseType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LicenseTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditLicenseTypeDescriptionCommand */
    public EditLicenseTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditLicenseTypeDescriptionResult getResult() {
        return LicenseResultFactory.getEditLicenseTypeDescriptionResult();
    }

    @Override
    public LicenseTypeDescriptionEdit getEdit() {
        return LicenseEditFactory.getLicenseTypeDescriptionEdit();
    }

    @Override
    public LicenseTypeDescription getEntity(EditLicenseTypeDescriptionResult result) {
        var licenseControl = Session.getModelController(LicenseControl.class);
        LicenseTypeDescription licenseTypeDescription = null;
        var licenseTypeName = spec.getLicenseTypeName();
        var licenseType = licenseControl.getLicenseTypeByName(licenseTypeName);

        if(licenseType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    licenseTypeDescription = licenseControl.getLicenseTypeDescription(licenseType, language);
                } else { // EditMode.UPDATE
                    licenseTypeDescription = licenseControl.getLicenseTypeDescriptionForUpdate(licenseType, language);
                }

                if(licenseTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownLicenseTypeDescription.name(), licenseTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownLicenseTypeName.name(), licenseTypeName);
        }

        return licenseTypeDescription;
    }

    @Override
    public LicenseType getLockEntity(LicenseTypeDescription licenseTypeDescription) {
        return licenseTypeDescription.getLicenseType();
    }

    @Override
    public void fillInResult(EditLicenseTypeDescriptionResult result, LicenseTypeDescription licenseTypeDescription) {
        var licenseControl = Session.getModelController(LicenseControl.class);

        result.setLicenseTypeDescription(licenseControl.getLicenseTypeDescriptionTransfer(getUserVisit(), licenseTypeDescription));
    }

    @Override
    public void doLock(LicenseTypeDescriptionEdit edit, LicenseTypeDescription licenseTypeDescription) {
        edit.setDescription(licenseTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(LicenseTypeDescription licenseTypeDescription) {
        var licenseControl = Session.getModelController(LicenseControl.class);
        var licenseTypeDescriptionValue = licenseControl.getLicenseTypeDescriptionValue(licenseTypeDescription);
        licenseTypeDescriptionValue.setDescription(edit.getDescription());

        licenseControl.updateLicenseTypeDescriptionFromValue(licenseTypeDescriptionValue, getPartyPK());
    }
    
}
