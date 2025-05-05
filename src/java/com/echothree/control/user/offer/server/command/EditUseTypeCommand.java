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

package com.echothree.control.user.offer.server.command;

import com.echothree.control.user.offer.common.edit.OfferEditFactory;
import com.echothree.control.user.offer.common.edit.UseTypeEdit;
import com.echothree.control.user.offer.common.form.EditUseTypeForm;
import com.echothree.control.user.offer.common.result.EditUseTypeResult;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.control.user.offer.common.spec.UseTypeUniversalSpec;
import com.echothree.model.control.offer.server.control.UseTypeControl;
import com.echothree.model.control.offer.server.logic.UseTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.offer.server.entity.UseType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditUseTypeCommand
        extends BaseAbstractEditCommand<UseTypeUniversalSpec, UseTypeEdit, EditUseTypeResult, UseType, UseType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.UseType.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("UseTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("UseTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditUseTypeCommand */
    public EditUseTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditUseTypeResult getResult() {
        return OfferResultFactory.getEditUseTypeResult();
    }
    
    @Override
    public UseTypeEdit getEdit() {
        return OfferEditFactory.getUseTypeEdit();
    }
    
    @Override
    public UseType getEntity(EditUseTypeResult result) {
        return UseTypeLogic.getInstance().getUseTypeByUniversalSpec(this, spec, false, editModeToEntityPermission(editMode));
    }
    
    @Override
    public UseType getLockEntity(UseType useType) {
        return useType;
    }
    
    @Override
    public void fillInResult(EditUseTypeResult result, UseType useType) {
        var useTypeControl = Session.getModelController(UseTypeControl.class);
        
        result.setUseType(useTypeControl.getUseTypeTransfer(getUserVisit(), useType));
    }
    
    @Override
    public void doLock(UseTypeEdit edit, UseType useType) {
        var useTypeControl = Session.getModelController(UseTypeControl.class);
        var useTypeDescription = useTypeControl.getUseTypeDescription(useType, getPreferredLanguage());
        var useTypeDetail = useType.getLastDetail();
        
        edit.setUseTypeName(useTypeDetail.getUseTypeName());
        edit.setIsDefault(useTypeDetail.getIsDefault().toString());
        edit.setSortOrder(useTypeDetail.getSortOrder().toString());

        if(useTypeDescription != null) {
            edit.setDescription(useTypeDescription.getDescription());
        }
    }
        
    @Override
    public void canUpdate(UseType useType) {
        var useTypeControl = Session.getModelController(UseTypeControl.class);
        var useTypeName = edit.getUseTypeName();
        var duplicateUseType = useTypeControl.getUseTypeByName(useTypeName);

        if(duplicateUseType != null && !useType.equals(duplicateUseType)) {
            addExecutionError(ExecutionErrors.DuplicateUseTypeName.name(), useTypeName);
        }
    }
    
    @Override
    public void doUpdate(UseType useType) {
        var useTypeControl = Session.getModelController(UseTypeControl.class);
        var partyPK = getPartyPK();
        var useTypeDetailValue = useTypeControl.getUseTypeDetailValueForUpdate(useType);
        var useTypeDescription = useTypeControl.getUseTypeDescriptionForUpdate(useType, getPreferredLanguage());
        var description = edit.getDescription();

        useTypeDetailValue.setUseTypeName(edit.getUseTypeName());
        useTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        useTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        useTypeControl.updateUseTypeFromValue(useTypeDetailValue, partyPK);

        if(useTypeDescription == null && description != null) {
            useTypeControl.createUseTypeDescription(useType, getPreferredLanguage(), description, partyPK);
        } else if(useTypeDescription != null && description == null) {
            useTypeControl.deleteUseTypeDescription(useTypeDescription, partyPK);
        } else if(useTypeDescription != null && description != null) {
            var useTypeDescriptionValue = useTypeControl.getUseTypeDescriptionValue(useTypeDescription);

            useTypeDescriptionValue.setDescription(description);
            useTypeControl.updateUseTypeDescriptionFromValue(useTypeDescriptionValue, partyPK);
        }
    }
    
}
