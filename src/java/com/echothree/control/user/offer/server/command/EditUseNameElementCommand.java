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

package com.echothree.control.user.offer.server.command;

import com.echothree.control.user.offer.common.edit.OfferEditFactory;
import com.echothree.control.user.offer.common.edit.UseNameElementEdit;
import com.echothree.control.user.offer.common.form.EditUseNameElementForm;
import com.echothree.control.user.offer.common.result.EditUseNameElementResult;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.control.user.offer.common.spec.UseNameElementUniversalSpec;
import com.echothree.model.control.offer.server.control.UseNameElementControl;
import com.echothree.model.control.offer.server.logic.UseNameElementLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.offer.server.entity.UseNameElement;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditUseNameElementCommand
        extends BaseAbstractEditCommand<UseNameElementUniversalSpec, UseNameElementEdit, EditUseNameElementResult, UseNameElement, UseNameElement> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.UseNameElement.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("UseNameElementName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("UseNameElementName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Offset", FieldType.UNSIGNED_INTEGER, true, null, null),
                new FieldDefinition("Length", FieldType.UNSIGNED_INTEGER, true, null, null),
                new FieldDefinition("ValidationPattern", FieldType.REGULAR_EXPRESSION, false, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditUseNameElementCommand */
    public EditUseNameElementCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditUseNameElementResult getResult() {
        return OfferResultFactory.getEditUseNameElementResult();
    }
    
    @Override
    public UseNameElementEdit getEdit() {
        return OfferEditFactory.getUseNameElementEdit();
    }
    
    @Override
    public UseNameElement getEntity(EditUseNameElementResult result) {
        return UseNameElementLogic.getInstance().getUseNameElementByUniversalSpec(this, spec, editModeToEntityPermission(editMode));
    }
    
    @Override
    public UseNameElement getLockEntity(UseNameElement useNameElement) {
        return useNameElement;
    }
    
    @Override
    public void fillInResult(EditUseNameElementResult result, UseNameElement useNameElement) {
        var useNameElementControl = Session.getModelController(UseNameElementControl.class);
        
        result.setUseNameElement(useNameElementControl.getUseNameElementTransfer(getUserVisit(), useNameElement));
    }
    
    @Override
    public void doLock(UseNameElementEdit edit, UseNameElement useNameElement) {
        var useNameElementControl = Session.getModelController(UseNameElementControl.class);
        var useNameElementDescription = useNameElementControl.getUseNameElementDescription(useNameElement, getPreferredLanguage());
        var useNameElementDetail = useNameElement.getLastDetail();
        
        edit.setUseNameElementName(useNameElementDetail.getUseNameElementName());
        edit.setOffset(useNameElementDetail.getOffset().toString());
        edit.setLength(useNameElementDetail.getLength().toString());
        edit.setValidationPattern(useNameElementDetail.getValidationPattern());

        if(useNameElementDescription != null) {
            edit.setDescription(useNameElementDescription.getDescription());
        }
    }
        
    @Override
    public void canUpdate(UseNameElement useNameElement) {
        var useNameElementControl = Session.getModelController(UseNameElementControl.class);
        var useNameElementName = edit.getUseNameElementName();
        var duplicateUseNameElement = useNameElementControl.getUseNameElementByName(useNameElementName);

        if(duplicateUseNameElement != null && !useNameElement.equals(duplicateUseNameElement)) {
            addExecutionError(ExecutionErrors.DuplicateUseNameElementName.name(), useNameElementName);
        }
    }
    
    @Override
    public void doUpdate(UseNameElement useNameElement) {
        var useNameElementControl = Session.getModelController(UseNameElementControl.class);
        var partyPK = getPartyPK();
        var useNameElementDetailValue = useNameElementControl.getUseNameElementDetailValueForUpdate(useNameElement);
        var useNameElementDescription = useNameElementControl.getUseNameElementDescriptionForUpdate(useNameElement, getPreferredLanguage());
        var description = edit.getDescription();

        useNameElementDetailValue.setUseNameElementName(edit.getUseNameElementName());
        useNameElementDetailValue.setOffset(Integer.valueOf(edit.getOffset()));
        useNameElementDetailValue.setLength(Integer.valueOf(edit.getLength()));
        useNameElementDetailValue.setValidationPattern(edit.getValidationPattern());

        useNameElementControl.updateUseNameElementFromValue(useNameElementDetailValue, partyPK);

        if(useNameElementDescription == null && description != null) {
            useNameElementControl.createUseNameElementDescription(useNameElement, getPreferredLanguage(), description, partyPK);
        } else if(useNameElementDescription != null && description == null) {
            useNameElementControl.deleteUseNameElementDescription(useNameElementDescription, partyPK);
        } else if(useNameElementDescription != null && description != null) {
            var useNameElementDescriptionValue = useNameElementControl.getUseNameElementDescriptionValue(useNameElementDescription);

            useNameElementDescriptionValue.setDescription(description);
            useNameElementControl.updateUseNameElementDescriptionFromValue(useNameElementDescriptionValue, partyPK);
        }
    }
    
}
