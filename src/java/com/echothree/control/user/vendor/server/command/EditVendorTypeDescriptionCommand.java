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

package com.echothree.control.user.vendor.server.command;

import com.echothree.control.user.vendor.common.edit.VendorEditFactory;
import com.echothree.control.user.vendor.common.edit.VendorTypeDescriptionEdit;
import com.echothree.control.user.vendor.common.result.EditVendorTypeDescriptionResult;
import com.echothree.control.user.vendor.common.result.VendorResultFactory;
import com.echothree.control.user.vendor.common.spec.VendorTypeDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.vendor.server.entity.VendorType;
import com.echothree.model.data.vendor.server.entity.VendorTypeDescription;
import com.echothree.util.common.command.EditMode;
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
public class EditVendorTypeDescriptionCommand
        extends BaseAbstractEditCommand<VendorTypeDescriptionSpec, VendorTypeDescriptionEdit, EditVendorTypeDescriptionResult, VendorTypeDescription, VendorType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.VendorType.name(), SecurityRoles.Description.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("VendorTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }
    
    /** Creates a new instance of EditVendorTypeDescriptionCommand */
    public EditVendorTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Inject
    VendorControl vendorControl;

    @Inject
    PartyControl partyControl;

    @Override
    protected EditVendorTypeDescriptionResult getResult() {
        return VendorResultFactory.getEditVendorTypeDescriptionResult();
    }

    @Override
    protected VendorTypeDescriptionEdit getEdit() {
        return VendorEditFactory.getVendorTypeDescriptionEdit();
    }

    @Override
    protected VendorTypeDescription getEntity(EditVendorTypeDescriptionResult result) {
        VendorTypeDescription vendorTypeDescription = null;
        var vendorTypeName = spec.getVendorTypeName();
        var vendorType = vendorControl.getVendorTypeByName(vendorTypeName);

        if(vendorType != null) {
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    vendorTypeDescription = vendorControl.getVendorTypeDescription(vendorType, language);
                } else { // EditMode.UPDATE
                    vendorTypeDescription = vendorControl.getVendorTypeDescriptionForUpdate(vendorType, language);
                }

                if(vendorTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownVendorTypeDescription.name(), vendorTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownVendorTypeName.name(), vendorTypeName);
        }

        return vendorTypeDescription;
    }

    @Override
    protected VendorType getLockEntity(VendorTypeDescription vendorTypeDescription) {
        return vendorTypeDescription.getVendorType();
    }

    @Override
    protected void fillInResult(EditVendorTypeDescriptionResult result, VendorTypeDescription vendorTypeDescription) {
        result.setVendorTypeDescription(vendorControl.getVendorTypeDescriptionTransfer(getUserVisit(), vendorTypeDescription));
    }

    @Override
    protected void doLock(VendorTypeDescriptionEdit edit, VendorTypeDescription vendorTypeDescription) {
        edit.setDescription(vendorTypeDescription.getDescription());
    }

    @Override
    protected void doUpdate(VendorTypeDescription vendorTypeDescription) {
        var vendorTypeDescriptionValue = vendorControl.getVendorTypeDescriptionValue(vendorTypeDescription);

        vendorTypeDescriptionValue.setDescription(edit.getDescription());

        vendorControl.updateVendorTypeDescriptionFromValue(vendorTypeDescriptionValue, getPartyPK());
    }
    
}
