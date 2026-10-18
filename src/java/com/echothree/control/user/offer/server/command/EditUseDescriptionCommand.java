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
import com.echothree.control.user.offer.common.edit.UseDescriptionEdit;
import com.echothree.control.user.offer.common.result.EditUseDescriptionResult;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.control.user.offer.common.spec.UseDescriptionSpec;
import com.echothree.model.control.offer.server.control.UseControl;
import com.echothree.model.control.offer.server.logic.UseLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.offer.server.entity.Use;
import com.echothree.model.data.offer.server.entity.UseDescription;
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
public class EditUseDescriptionCommand
        extends BaseAbstractEditCommand<UseDescriptionSpec, UseDescriptionEdit, EditUseDescriptionResult, UseDescription, Use> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Use.name(), SecurityRoles.Description.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("UseName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    UseControl useControl;

    @Inject
    LanguageLogic languageLogic;

    @Inject
    UseLogic useLogic;

    /** Creates a new instance of EditUseDescriptionCommand */
    public EditUseDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditUseDescriptionResult getResult() {
        return OfferResultFactory.getEditUseDescriptionResult();
    }

    @Override
    public UseDescriptionEdit getEdit() {
        return OfferEditFactory.getUseDescriptionEdit();
    }

    @Override
    public UseDescription getEntity(EditUseDescriptionResult result) {
        UseDescription useDescription = null;
        var useName = spec.getUseName();
        var use = useLogic.getUseByName(this, useName);

        if(!hasExecutionErrors()) {
            var languageIsoName = spec.getLanguageIsoName();
            var language = languageLogic.getLanguageByName(this, languageIsoName);

            if(!hasExecutionErrors()) {
                useDescription = useControl.getUseDescription(use, language, editModeToEntityPermission(editMode));

                if(useDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownUseDescription.name(), useName, languageIsoName);
                }
            }
        }

        return useDescription;
    }

    @Override
    public Use getLockEntity(UseDescription useDescription) {
        return useDescription.getUse();
    }

    @Override
    public void fillInResult(EditUseDescriptionResult result, UseDescription useDescription) {
        result.setUseDescription(useControl.getUseDescriptionTransfer(getUserVisit(), useDescription));
    }

    @Override
    public void doLock(UseDescriptionEdit edit, UseDescription useDescription) {
        edit.setDescription(useDescription.getDescription());
    }

    @Override
    public void doUpdate(UseDescription useDescription) {
        var useDescriptionValue = useControl.getUseDescriptionValue(useDescription);

        useDescriptionValue.setDescription(edit.getDescription());

        useControl.updateUseDescriptionFromValue(useDescriptionValue, getPartyPK());
    }

}
