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
import com.echothree.control.user.offer.common.edit.UseTypeDescriptionEdit;
import com.echothree.control.user.offer.common.result.EditUseTypeDescriptionResult;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.control.user.offer.common.spec.UseTypeDescriptionSpec;
import com.echothree.model.control.offer.server.control.UseTypeControl;
import com.echothree.model.control.offer.server.logic.UseTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.offer.server.entity.UseType;
import com.echothree.model.data.offer.server.entity.UseTypeDescription;
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
public class EditUseTypeDescriptionCommand
        extends BaseAbstractEditCommand<UseTypeDescriptionSpec, UseTypeDescriptionEdit, EditUseTypeDescriptionResult, UseTypeDescription, UseType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.UseType.name(), SecurityRoles.Description.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("UseTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    UseTypeControl useTypeControl;

    @Inject
    UseTypeLogic useTypeLogic;

    /** Creates a new instance of EditUseTypeDescriptionCommand */
    public EditUseTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditUseTypeDescriptionResult getResult() {
        return OfferResultFactory.getEditUseTypeDescriptionResult();
    }

    @Override
    public UseTypeDescriptionEdit getEdit() {
        return OfferEditFactory.getUseTypeDescriptionEdit();
    }

    @Override
    public UseTypeDescription getEntity(EditUseTypeDescriptionResult result) {
        return useTypeLogic.getUseTypeDescriptionBySpec(this, spec, editModeToEntityPermission(editMode));
    }

    @Override
    public UseType getLockEntity(UseTypeDescription useTypeDescription) {
        return useTypeDescription.getUseType();
    }

    @Override
    public void fillInResult(EditUseTypeDescriptionResult result, UseTypeDescription useTypeDescription) {
        result.setUseTypeDescription(useTypeControl.getUseTypeDescriptionTransfer(getUserVisit(), useTypeDescription));
    }

    @Override
    public void doLock(UseTypeDescriptionEdit edit, UseTypeDescription useTypeDescription) {
        edit.setDescription(useTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(UseTypeDescription useTypeDescription) {
        var useTypeDescriptionValue = useTypeControl.getUseTypeDescriptionValue(useTypeDescription);

        useTypeDescriptionValue.setDescription(edit.getDescription());

        useTypeControl.updateUseTypeDescriptionFromValue(useTypeDescriptionValue, getPartyPK());
    }

}
