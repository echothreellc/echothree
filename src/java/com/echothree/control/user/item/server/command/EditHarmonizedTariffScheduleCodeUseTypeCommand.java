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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.edit.HarmonizedTariffScheduleCodeUseTypeEdit;
import com.echothree.control.user.item.common.edit.ItemEditFactory;
import com.echothree.control.user.item.common.form.EditHarmonizedTariffScheduleCodeUseTypeForm;
import com.echothree.control.user.item.common.result.EditHarmonizedTariffScheduleCodeUseTypeResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.control.user.item.common.spec.HarmonizedTariffScheduleCodeUseTypeSpec;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeUseType;
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
public class EditHarmonizedTariffScheduleCodeUseTypeCommand
        extends BaseAbstractEditCommand<HarmonizedTariffScheduleCodeUseTypeSpec, HarmonizedTariffScheduleCodeUseTypeEdit, EditHarmonizedTariffScheduleCodeUseTypeResult, HarmonizedTariffScheduleCodeUseType, HarmonizedTariffScheduleCodeUseType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.HarmonizedTariffScheduleCodeUseType.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("HarmonizedTariffScheduleCodeUseTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("HarmonizedTariffScheduleCodeUseTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditHarmonizedTariffScheduleCodeUseTypeCommand */
    public EditHarmonizedTariffScheduleCodeUseTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditHarmonizedTariffScheduleCodeUseTypeResult getResult() {
        return ItemResultFactory.getEditHarmonizedTariffScheduleCodeUseTypeResult();
    }

    @Override
    public HarmonizedTariffScheduleCodeUseTypeEdit getEdit() {
        return ItemEditFactory.getHarmonizedTariffScheduleCodeUseTypeEdit();
    }

    @Override
    public HarmonizedTariffScheduleCodeUseType getEntity(EditHarmonizedTariffScheduleCodeUseTypeResult result) {
        var itemControl = Session.getModelController(ItemControl.class);
        HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType;
        var harmonizedTariffScheduleCodeUseTypeName = spec.getHarmonizedTariffScheduleCodeUseTypeName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            harmonizedTariffScheduleCodeUseType = itemControl.getHarmonizedTariffScheduleCodeUseTypeByName(harmonizedTariffScheduleCodeUseTypeName);
        } else { // EditMode.UPDATE
            harmonizedTariffScheduleCodeUseType = itemControl.getHarmonizedTariffScheduleCodeUseTypeByNameForUpdate(harmonizedTariffScheduleCodeUseTypeName);
        }

        if(harmonizedTariffScheduleCodeUseType == null) {
            addExecutionError(ExecutionErrors.UnknownHarmonizedTariffScheduleCodeUseTypeName.name(), harmonizedTariffScheduleCodeUseTypeName);
        }

        return harmonizedTariffScheduleCodeUseType;
    }

    @Override
    public HarmonizedTariffScheduleCodeUseType getLockEntity(HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType) {
        return harmonizedTariffScheduleCodeUseType;
    }

    @Override
    public void fillInResult(EditHarmonizedTariffScheduleCodeUseTypeResult result, HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType) {
        var itemControl = Session.getModelController(ItemControl.class);

        result.setHarmonizedTariffScheduleCodeUseType(itemControl.getHarmonizedTariffScheduleCodeUseTypeTransfer(getUserVisit(), harmonizedTariffScheduleCodeUseType));
    }

    @Override
    public void doLock(HarmonizedTariffScheduleCodeUseTypeEdit edit, HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType) {
        var itemControl = Session.getModelController(ItemControl.class);
        var harmonizedTariffScheduleCodeUseTypeDescription = itemControl.getHarmonizedTariffScheduleCodeUseTypeDescription(harmonizedTariffScheduleCodeUseType, getPreferredLanguage());
        var harmonizedTariffScheduleCodeUseTypeDetail = harmonizedTariffScheduleCodeUseType.getLastDetail();

        edit.setHarmonizedTariffScheduleCodeUseTypeName(harmonizedTariffScheduleCodeUseTypeDetail.getHarmonizedTariffScheduleCodeUseTypeName());
        edit.setIsDefault(harmonizedTariffScheduleCodeUseTypeDetail.getIsDefault().toString());
        edit.setSortOrder(harmonizedTariffScheduleCodeUseTypeDetail.getSortOrder().toString());

        if(harmonizedTariffScheduleCodeUseTypeDescription != null) {
            edit.setDescription(harmonizedTariffScheduleCodeUseTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType) {
        var itemControl = Session.getModelController(ItemControl.class);
        var harmonizedTariffScheduleCodeUseTypeName = edit.getHarmonizedTariffScheduleCodeUseTypeName();
        var duplicateHarmonizedTariffScheduleCodeUseType = itemControl.getHarmonizedTariffScheduleCodeUseTypeByName(harmonizedTariffScheduleCodeUseTypeName);

        if(duplicateHarmonizedTariffScheduleCodeUseType != null && !harmonizedTariffScheduleCodeUseType.equals(duplicateHarmonizedTariffScheduleCodeUseType)) {
            addExecutionError(ExecutionErrors.DuplicateHarmonizedTariffScheduleCodeUseTypeName.name(), harmonizedTariffScheduleCodeUseTypeName);
        }
    }

    @Override
    public void doUpdate(HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType) {
        var itemControl = Session.getModelController(ItemControl.class);
        var partyPK = getPartyPK();
        var harmonizedTariffScheduleCodeUseTypeDetailValue = itemControl.getHarmonizedTariffScheduleCodeUseTypeDetailValueForUpdate(harmonizedTariffScheduleCodeUseType);
        var harmonizedTariffScheduleCodeUseTypeDescription = itemControl.getHarmonizedTariffScheduleCodeUseTypeDescriptionForUpdate(harmonizedTariffScheduleCodeUseType, getPreferredLanguage());
        var description = edit.getDescription();

        harmonizedTariffScheduleCodeUseTypeDetailValue.setHarmonizedTariffScheduleCodeUseTypeName(edit.getHarmonizedTariffScheduleCodeUseTypeName());
        harmonizedTariffScheduleCodeUseTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        harmonizedTariffScheduleCodeUseTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        itemControl.updateHarmonizedTariffScheduleCodeUseTypeFromValue(harmonizedTariffScheduleCodeUseTypeDetailValue, partyPK);

        if(harmonizedTariffScheduleCodeUseTypeDescription == null && description != null) {
            itemControl.createHarmonizedTariffScheduleCodeUseTypeDescription(harmonizedTariffScheduleCodeUseType, getPreferredLanguage(), description, partyPK);
        } else if(harmonizedTariffScheduleCodeUseTypeDescription != null && description == null) {
            itemControl.deleteHarmonizedTariffScheduleCodeUseTypeDescription(harmonizedTariffScheduleCodeUseTypeDescription, partyPK);
        } else if(harmonizedTariffScheduleCodeUseTypeDescription != null && description != null) {
            var harmonizedTariffScheduleCodeUseTypeDescriptionValue = itemControl.getHarmonizedTariffScheduleCodeUseTypeDescriptionValue(harmonizedTariffScheduleCodeUseTypeDescription);

            harmonizedTariffScheduleCodeUseTypeDescriptionValue.setDescription(description);
            itemControl.updateHarmonizedTariffScheduleCodeUseTypeDescriptionFromValue(harmonizedTariffScheduleCodeUseTypeDescriptionValue, partyPK);
        }
    }

}
