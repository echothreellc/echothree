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
import com.echothree.control.user.offer.common.edit.UseNameElementDescriptionEdit;
import com.echothree.control.user.offer.common.result.EditUseNameElementDescriptionResult;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.control.user.offer.common.spec.UseNameElementDescriptionSpec;
import com.echothree.model.control.offer.server.control.UseNameElementControl;
import com.echothree.model.control.offer.server.logic.UseNameElementLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.offer.server.entity.UseNameElement;
import com.echothree.model.data.offer.server.entity.UseNameElementDescription;
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
public class EditUseNameElementDescriptionCommand
        extends BaseAbstractEditCommand<UseNameElementDescriptionSpec, UseNameElementDescriptionEdit, EditUseNameElementDescriptionResult, UseNameElementDescription, UseNameElement> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.UseNameElement.name(), SecurityRoles.Description.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("UseNameElementName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    UseNameElementControl useNameElementControl;

    @Inject
    UseNameElementLogic useNameElementLogic;

    /** Creates a new instance of EditUseNameElementDescriptionCommand */
    public EditUseNameElementDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditUseNameElementDescriptionResult getResult() {
        return OfferResultFactory.getEditUseNameElementDescriptionResult();
    }

    @Override
    public UseNameElementDescriptionEdit getEdit() {
        return OfferEditFactory.getUseNameElementDescriptionEdit();
    }

    @Override
    public UseNameElementDescription getEntity(EditUseNameElementDescriptionResult result) {
        return useNameElementLogic.getUseNameElementDescriptionBySpec(this, spec, editModeToEntityPermission(editMode));
    }

    @Override
    public UseNameElement getLockEntity(UseNameElementDescription useNameElementDescription) {
        return useNameElementDescription.getUseNameElement();
    }

    @Override
    public void fillInResult(EditUseNameElementDescriptionResult result, UseNameElementDescription useNameElementDescription) {
        result.setUseNameElementDescription(useNameElementControl.getUseNameElementDescriptionTransfer(getUserVisit(), useNameElementDescription));
    }

    @Override
    public void doLock(UseNameElementDescriptionEdit edit, UseNameElementDescription useNameElementDescription) {
        edit.setDescription(useNameElementDescription.getDescription());
    }

    @Override
    public void doUpdate(UseNameElementDescription useNameElementDescription) {
        var useNameElementDescriptionValue = useNameElementControl.getUseNameElementDescriptionValue(useNameElementDescription);

        useNameElementDescriptionValue.setDescription(edit.getDescription());

        useNameElementControl.updateUseNameElementDescriptionFromValue(useNameElementDescriptionValue, getPartyPK());
    }

}
