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

import com.echothree.control.user.item.common.edit.HarmonizedTariffScheduleCodeUnitEdit;
import com.echothree.control.user.item.common.edit.ItemEditFactory;
import com.echothree.control.user.item.common.form.EditHarmonizedTariffScheduleCodeUnitForm;
import com.echothree.control.user.item.common.result.EditHarmonizedTariffScheduleCodeUnitResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.control.user.item.common.spec.HarmonizedTariffScheduleCodeUnitSpec;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeUnit;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditHarmonizedTariffScheduleCodeUnitCommand
        extends BaseAbstractEditCommand<HarmonizedTariffScheduleCodeUnitSpec, HarmonizedTariffScheduleCodeUnitEdit, EditHarmonizedTariffScheduleCodeUnitResult, HarmonizedTariffScheduleCodeUnit, HarmonizedTariffScheduleCodeUnit> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.HarmonizedTariffScheduleCodeUnit.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("HarmonizedTariffScheduleCodeUnitName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("HarmonizedTariffScheduleCodeUnitName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditHarmonizedTariffScheduleCodeUnitCommand */
    public EditHarmonizedTariffScheduleCodeUnitCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditHarmonizedTariffScheduleCodeUnitResult getResult() {
        return ItemResultFactory.getEditHarmonizedTariffScheduleCodeUnitResult();
    }

    @Override
    public HarmonizedTariffScheduleCodeUnitEdit getEdit() {
        return ItemEditFactory.getHarmonizedTariffScheduleCodeUnitEdit();
    }

    @Override
    public HarmonizedTariffScheduleCodeUnit getEntity(EditHarmonizedTariffScheduleCodeUnitResult result) {
        var itemControl = Session.getModelController(ItemControl.class);
        HarmonizedTariffScheduleCodeUnit harmonizedTariffScheduleCodeUnit;
        var harmonizedTariffScheduleCodeUnitName = spec.getHarmonizedTariffScheduleCodeUnitName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            harmonizedTariffScheduleCodeUnit = itemControl.getHarmonizedTariffScheduleCodeUnitByName(harmonizedTariffScheduleCodeUnitName);
        } else { // EditMode.UPDATE
            harmonizedTariffScheduleCodeUnit = itemControl.getHarmonizedTariffScheduleCodeUnitByNameForUpdate(harmonizedTariffScheduleCodeUnitName);
        }

        if(harmonizedTariffScheduleCodeUnit == null) {
            addExecutionError(ExecutionErrors.UnknownHarmonizedTariffScheduleCodeUnitName.name(), harmonizedTariffScheduleCodeUnitName);
        }

        return harmonizedTariffScheduleCodeUnit;
    }

    @Override
    public HarmonizedTariffScheduleCodeUnit getLockEntity(HarmonizedTariffScheduleCodeUnit harmonizedTariffScheduleCodeUnit) {
        return harmonizedTariffScheduleCodeUnit;
    }

    @Override
    public void fillInResult(EditHarmonizedTariffScheduleCodeUnitResult result, HarmonizedTariffScheduleCodeUnit harmonizedTariffScheduleCodeUnit) {
        var itemControl = Session.getModelController(ItemControl.class);

        result.setHarmonizedTariffScheduleCodeUnit(itemControl.getHarmonizedTariffScheduleCodeUnitTransfer(getUserVisit(), harmonizedTariffScheduleCodeUnit));
    }

    @Override
    public void doLock(HarmonizedTariffScheduleCodeUnitEdit edit, HarmonizedTariffScheduleCodeUnit harmonizedTariffScheduleCodeUnit) {
        var itemControl = Session.getModelController(ItemControl.class);
        var harmonizedTariffScheduleCodeUnitDescription = itemControl.getHarmonizedTariffScheduleCodeUnitDescription(harmonizedTariffScheduleCodeUnit, getPreferredLanguage());
        var harmonizedTariffScheduleCodeUnitDetail = harmonizedTariffScheduleCodeUnit.getLastDetail();

        edit.setHarmonizedTariffScheduleCodeUnitName(harmonizedTariffScheduleCodeUnitDetail.getHarmonizedTariffScheduleCodeUnitName());
        edit.setIsDefault(harmonizedTariffScheduleCodeUnitDetail.getIsDefault().toString());
        edit.setSortOrder(harmonizedTariffScheduleCodeUnitDetail.getSortOrder().toString());

        if(harmonizedTariffScheduleCodeUnitDescription != null) {
            edit.setDescription(harmonizedTariffScheduleCodeUnitDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(HarmonizedTariffScheduleCodeUnit harmonizedTariffScheduleCodeUnit) {
        var itemControl = Session.getModelController(ItemControl.class);
        var harmonizedTariffScheduleCodeUnitName = edit.getHarmonizedTariffScheduleCodeUnitName();
        var duplicateHarmonizedTariffScheduleCodeUnit = itemControl.getHarmonizedTariffScheduleCodeUnitByName(harmonizedTariffScheduleCodeUnitName);

        if(duplicateHarmonizedTariffScheduleCodeUnit != null && !harmonizedTariffScheduleCodeUnit.equals(duplicateHarmonizedTariffScheduleCodeUnit)) {
            addExecutionError(ExecutionErrors.DuplicateHarmonizedTariffScheduleCodeUnitName.name(), harmonizedTariffScheduleCodeUnitName);
        }
    }

    @Override
    public void doUpdate(HarmonizedTariffScheduleCodeUnit harmonizedTariffScheduleCodeUnit) {
        var itemControl = Session.getModelController(ItemControl.class);
        var partyPK = getPartyPK();
        var harmonizedTariffScheduleCodeUnitDetailValue = itemControl.getHarmonizedTariffScheduleCodeUnitDetailValueForUpdate(harmonizedTariffScheduleCodeUnit);
        var harmonizedTariffScheduleCodeUnitDescription = itemControl.getHarmonizedTariffScheduleCodeUnitDescriptionForUpdate(harmonizedTariffScheduleCodeUnit, getPreferredLanguage());
        var description = edit.getDescription();

        harmonizedTariffScheduleCodeUnitDetailValue.setHarmonizedTariffScheduleCodeUnitName(edit.getHarmonizedTariffScheduleCodeUnitName());
        harmonizedTariffScheduleCodeUnitDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        harmonizedTariffScheduleCodeUnitDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        itemControl.updateHarmonizedTariffScheduleCodeUnitFromValue(harmonizedTariffScheduleCodeUnitDetailValue, partyPK);

        if(harmonizedTariffScheduleCodeUnitDescription == null && description != null) {
            itemControl.createHarmonizedTariffScheduleCodeUnitDescription(harmonizedTariffScheduleCodeUnit, getPreferredLanguage(), description, partyPK);
        } else if(harmonizedTariffScheduleCodeUnitDescription != null && description == null) {
            itemControl.deleteHarmonizedTariffScheduleCodeUnitDescription(harmonizedTariffScheduleCodeUnitDescription, partyPK);
        } else if(harmonizedTariffScheduleCodeUnitDescription != null && description != null) {
            var harmonizedTariffScheduleCodeUnitDescriptionValue = itemControl.getHarmonizedTariffScheduleCodeUnitDescriptionValue(harmonizedTariffScheduleCodeUnitDescription);

            harmonizedTariffScheduleCodeUnitDescriptionValue.setDescription(description);
            itemControl.updateHarmonizedTariffScheduleCodeUnitDescriptionFromValue(harmonizedTariffScheduleCodeUnitDescriptionValue, partyPK);
        }
    }

}
