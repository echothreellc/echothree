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
import com.echothree.control.user.offer.common.edit.UseEdit;
import com.echothree.control.user.offer.common.result.EditUseResult;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.control.user.offer.common.spec.UseSpec;
import com.echothree.model.control.offer.server.control.UseControl;
import com.echothree.model.control.offer.server.logic.UseLogic;
import com.echothree.model.control.offer.server.logic.UseTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.offer.server.entity.Use;
import com.echothree.model.data.offer.server.entity.UseType;
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
public class EditUseCommand
        extends BaseAbstractEditCommand<UseSpec, UseEdit, EditUseResult, Use, Use> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Use.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("UseName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("UseName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UseTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    UseControl useControl;

    @Inject
    UseLogic useLogic;

    @Inject
    UseTypeLogic useTypeLogic;

    /** Creates a new instance of EditUseCommand */
    public EditUseCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditUseResult getResult() {
        return OfferResultFactory.getEditUseResult();
    }

    @Override
    public UseEdit getEdit() {
        return OfferEditFactory.getUseEdit();
    }

    @Override
    public Use getEntity(EditUseResult result) {
        return useLogic.getUseByName(this, spec.getUseName(), editModeToEntityPermission(editMode));
    }

    @Override
    public Use getLockEntity(Use use) {
        return use;
    }

    @Override
    public void fillInResult(EditUseResult result, Use use) {
        result.setUse(useControl.getUseTransfer(getUserVisit(), use));
    }

    private UseType useType;

    @Override
    public void doLock(UseEdit edit, Use use) {
        var useDescription = useControl.getUseDescription(use, getPreferredLanguage());
        var useDetail = use.getLastDetail();

        edit.setUseName(useDetail.getUseName());
        edit.setUseTypeName(useDetail.getUseType().getLastDetail().getUseTypeName());
        edit.setIsDefault(useDetail.getIsDefault().toString());
        edit.setSortOrder(useDetail.getSortOrder().toString());

        if(useDescription != null) {
            edit.setDescription(useDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(Use use) {
        var useName = edit.getUseName();
        var duplicateUse = useControl.getUseByName(useName);

        if(duplicateUse == null || use.equals(duplicateUse)) {
            useType = useTypeLogic.getUseTypeByName(this, edit.getUseTypeName());
        } else {
            addExecutionError(ExecutionErrors.DuplicateUseName.name(), useName);
        }
    }

    @Override
    public void doUpdate(Use use) {
        var partyPK = getPartyPK();
        var useDetailValue = useControl.getUseDetailValueForUpdate(use);
        var useDescription = useControl.getUseDescriptionForUpdate(use, getPreferredLanguage());
        var description = edit.getDescription();

        useDetailValue.setUseName(edit.getUseName());
        useDetailValue.setUseTypePK(useType.getPrimaryKey());
        useDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        useDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        useLogic.updateUseFromValue(useDetailValue, partyPK);

        if(useDescription == null && description != null) {
            useControl.createUseDescription(use, getPreferredLanguage(), description, partyPK);
        } else if(useDescription != null && description == null) {
            useControl.deleteUseDescription(useDescription, partyPK);
        } else if(useDescription != null && description != null) {
            var useDescriptionValue = useControl.getUseDescriptionValue(useDescription);

            useDescriptionValue.setDescription(description);
            useControl.updateUseDescriptionFromValue(useDescriptionValue, partyPK);
        }
    }

}
