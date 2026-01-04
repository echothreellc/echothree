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
import com.echothree.control.user.offer.common.edit.OfferNameElementEdit;
import com.echothree.control.user.offer.common.form.EditOfferNameElementForm;
import com.echothree.control.user.offer.common.result.EditOfferNameElementResult;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.control.user.offer.common.spec.OfferNameElementUniversalSpec;
import com.echothree.model.control.offer.server.control.OfferNameElementControl;
import com.echothree.model.control.offer.server.logic.OfferNameElementLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.offer.server.entity.OfferNameElement;
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
public class EditOfferNameElementCommand
        extends BaseAbstractEditCommand<OfferNameElementUniversalSpec, OfferNameElementEdit, EditOfferNameElementResult, OfferNameElement, OfferNameElement> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.OfferNameElement.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OfferNameElementName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OfferNameElementName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Offset", FieldType.UNSIGNED_INTEGER, true, null, null),
                new FieldDefinition("Length", FieldType.UNSIGNED_INTEGER, true, null, null),
                new FieldDefinition("ValidationPattern", FieldType.REGULAR_EXPRESSION, false, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditOfferNameElementCommand */
    public EditOfferNameElementCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditOfferNameElementResult getResult() {
        return OfferResultFactory.getEditOfferNameElementResult();
    }
    
    @Override
    public OfferNameElementEdit getEdit() {
        return OfferEditFactory.getOfferNameElementEdit();
    }
    
    @Override
    public OfferNameElement getEntity(EditOfferNameElementResult result) {
        return OfferNameElementLogic.getInstance().getOfferNameElementByUniversalSpec(this, spec, editModeToEntityPermission(editMode));
    }
    
    @Override
    public OfferNameElement getLockEntity(OfferNameElement offerNameElement) {
        return offerNameElement;
    }
    
    @Override
    public void fillInResult(EditOfferNameElementResult result, OfferNameElement offerNameElement) {
        var offerNameElementControl = Session.getModelController(OfferNameElementControl.class);
        
        result.setOfferNameElement(offerNameElementControl.getOfferNameElementTransfer(getUserVisit(), offerNameElement));
    }
    
    @Override
    public void doLock(OfferNameElementEdit edit, OfferNameElement offerNameElement) {
        var offerNameElementControl = Session.getModelController(OfferNameElementControl.class);
        var offerNameElementDescription = offerNameElementControl.getOfferNameElementDescription(offerNameElement, getPreferredLanguage());
        var offerNameElementDetail = offerNameElement.getLastDetail();
        
        edit.setOfferNameElementName(offerNameElementDetail.getOfferNameElementName());
        edit.setOffset(offerNameElementDetail.getOffset().toString());
        edit.setLength(offerNameElementDetail.getLength().toString());
        edit.setValidationPattern(offerNameElementDetail.getValidationPattern());

        if(offerNameElementDescription != null) {
            edit.setDescription(offerNameElementDescription.getDescription());
        }
    }
        
    @Override
    public void canUpdate(OfferNameElement offerNameElement) {
        var offerNameElementControl = Session.getModelController(OfferNameElementControl.class);
        var offerNameElementName = edit.getOfferNameElementName();
        var duplicateOfferNameElement = offerNameElementControl.getOfferNameElementByName(offerNameElementName);

        if(duplicateOfferNameElement != null && !offerNameElement.equals(duplicateOfferNameElement)) {
            addExecutionError(ExecutionErrors.DuplicateOfferNameElementName.name(), offerNameElementName);
        }
    }
    
    @Override
    public void doUpdate(OfferNameElement offerNameElement) {
        var offerNameElementControl = Session.getModelController(OfferNameElementControl.class);
        var partyPK = getPartyPK();
        var offerNameElementDetailValue = offerNameElementControl.getOfferNameElementDetailValueForUpdate(offerNameElement);
        var offerNameElementDescription = offerNameElementControl.getOfferNameElementDescriptionForUpdate(offerNameElement, getPreferredLanguage());
        var description = edit.getDescription();

        offerNameElementDetailValue.setOfferNameElementName(edit.getOfferNameElementName());
        offerNameElementDetailValue.setOffset(Integer.valueOf(edit.getOffset()));
        offerNameElementDetailValue.setLength(Integer.valueOf(edit.getLength()));
        offerNameElementDetailValue.setValidationPattern(edit.getValidationPattern());

        offerNameElementControl.updateOfferNameElementFromValue(offerNameElementDetailValue, partyPK);

        if(offerNameElementDescription == null && description != null) {
            offerNameElementControl.createOfferNameElementDescription(offerNameElement, getPreferredLanguage(), description, partyPK);
        } else if(offerNameElementDescription != null && description == null) {
            offerNameElementControl.deleteOfferNameElementDescription(offerNameElementDescription, partyPK);
        } else if(offerNameElementDescription != null && description != null) {
            var offerNameElementDescriptionValue = offerNameElementControl.getOfferNameElementDescriptionValue(offerNameElementDescription);

            offerNameElementDescriptionValue.setDescription(description);
            offerNameElementControl.updateOfferNameElementDescriptionFromValue(offerNameElementDescriptionValue, partyPK);
        }
    }
    
}
