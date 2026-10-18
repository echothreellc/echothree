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
import com.echothree.control.user.offer.common.edit.OfferNameElementDescriptionEdit;
import com.echothree.control.user.offer.common.result.EditOfferNameElementDescriptionResult;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.control.user.offer.common.spec.OfferNameElementDescriptionSpec;
import com.echothree.model.control.offer.server.control.OfferNameElementControl;
import com.echothree.model.control.offer.server.logic.OfferNameElementLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.offer.server.entity.OfferNameElement;
import com.echothree.model.data.offer.server.entity.OfferNameElementDescription;
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
public class EditOfferNameElementDescriptionCommand
        extends BaseAbstractEditCommand<OfferNameElementDescriptionSpec, OfferNameElementDescriptionEdit, EditOfferNameElementDescriptionResult, OfferNameElementDescription, OfferNameElement> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.OfferNameElement.name(), SecurityRoles.Description.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("OfferNameElementName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    OfferNameElementControl offerNameElementControl;

    @Inject
    LanguageLogic languageLogic;

    @Inject
    OfferNameElementLogic offerNameElementLogic;

    /** Creates a new instance of EditOfferNameElementDescriptionCommand */
    public EditOfferNameElementDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditOfferNameElementDescriptionResult getResult() {
        return OfferResultFactory.getEditOfferNameElementDescriptionResult();
    }

    @Override
    public OfferNameElementDescriptionEdit getEdit() {
        return OfferEditFactory.getOfferNameElementDescriptionEdit();
    }

    @Override
    public OfferNameElementDescription getEntity(EditOfferNameElementDescriptionResult result) {
        OfferNameElementDescription offerNameElementDescription = null;
        var offerNameElementName = spec.getOfferNameElementName();
        var offerNameElement = offerNameElementLogic.getOfferNameElementByName(this, offerNameElementName);

        if(!hasExecutionErrors()) {
            var languageIsoName = spec.getLanguageIsoName();
            var language = languageLogic.getLanguageByName(this, languageIsoName);

            if(!hasExecutionErrors()) {
                offerNameElementDescription = offerNameElementControl.getOfferNameElementDescription(offerNameElement, language,
                        editModeToEntityPermission(editMode));

                if(offerNameElementDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownOfferNameElementDescription.name(), offerNameElementName, languageIsoName);
                }
            }
        }

        return offerNameElementDescription;
    }

    @Override
    public OfferNameElement getLockEntity(OfferNameElementDescription offerNameElementDescription) {
        return offerNameElementDescription.getOfferNameElement();
    }

    @Override
    public void fillInResult(EditOfferNameElementDescriptionResult result, OfferNameElementDescription offerNameElementDescription) {
        result.setOfferNameElementDescription(offerNameElementControl.getOfferNameElementDescriptionTransfer(getUserVisit(), offerNameElementDescription));
    }

    @Override
    public void doLock(OfferNameElementDescriptionEdit edit, OfferNameElementDescription offerNameElementDescription) {
        edit.setDescription(offerNameElementDescription.getDescription());
    }

    @Override
    public void doUpdate(OfferNameElementDescription offerNameElementDescription) {
        var offerNameElementDescriptionValue = offerNameElementControl.getOfferNameElementDescriptionValue(offerNameElementDescription);

        offerNameElementDescriptionValue.setDescription(edit.getDescription());

        offerNameElementControl.updateOfferNameElementDescriptionFromValue(offerNameElementDescriptionValue, getPartyPK());
    }

}
