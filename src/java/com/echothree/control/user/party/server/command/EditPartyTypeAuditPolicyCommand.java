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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.edit.PartyEditFactory;
import com.echothree.control.user.party.common.edit.PartyTypeAuditPolicyEdit;
import com.echothree.control.user.party.common.result.EditPartyTypeAuditPolicyResult;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.control.user.party.common.spec.PartyTypeUniversalSpec;
import com.echothree.model.control.party.common.exception.UnknownRetainUserVisitsTimeUnitOfMeasureTypeNameException;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PartyTypeAuditPolicyLogic;
import com.echothree.model.control.party.server.logic.PartyTypeLogic;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureKindLogic;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.control.uom.server.util.Conversion;
import com.echothree.model.data.party.server.entity.PartyTypeAuditPolicy;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditPartyTypeAuditPolicyCommand
        extends BaseEditCommand<PartyTypeUniversalSpec, PartyTypeAuditPolicyEdit,
                EditPartyTypeAuditPolicyResult, PartyTypeAuditPolicy, PartyTypeAuditPolicy> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PartyTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("AuditCommands", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("RetainUserVisitsTime", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("RetainUserVisitsTimeUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null)
        );
    }

    private UnitOfMeasureKind timeUnitOfMeasureKind;
    private UnitOfMeasureType retainUserVisitsTimeUnitOfMeasureType;

    @Inject
    PartyControl partyControl;

    @Inject
    UomControl uomControl;

    @Inject
    PartyTypeAuditPolicyLogic partyTypeAuditPolicyLogic;

    @Inject
    PartyTypeLogic partyTypeLogic;

    @Inject
    UnitOfMeasureKindLogic unitOfMeasureKindLogic;

    @Inject
    UnitOfMeasureTypeLogic unitOfMeasureTypeLogic;

    /** Creates a new instance of EditPartyTypeAuditPolicyCommand */
    public EditPartyTypeAuditPolicyCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditPartyTypeAuditPolicyResult getResult() {
        return PartyResultFactory.getEditPartyTypeAuditPolicyResult();
    }

    @Override
    public PartyTypeAuditPolicyEdit getEdit() {
        return PartyEditFactory.getPartyTypeAuditPolicyEdit();
    }

    @Override
    public PartyTypeAuditPolicy getEntity(EditPartyTypeAuditPolicyResult result) {
        var partyType = partyTypeLogic.getPartyTypeByUniversalSpec(this, spec, false);
        PartyTypeAuditPolicy partyTypeAuditPolicy = null;

        if(!hasExecutionErrors()) {
            partyTypeAuditPolicy = partyTypeAuditPolicyLogic.getPartyTypeAuditPolicy(this, partyType, editModeToEntityPermission(editMode));
        }

        return partyTypeAuditPolicy;
    }

    @Override
    public PartyTypeAuditPolicy getLockEntity(PartyTypeAuditPolicy partyTypeAuditPolicy) {
        return partyTypeAuditPolicy;
    }

    @Override
    public void fillInResult(EditPartyTypeAuditPolicyResult result, PartyTypeAuditPolicy partyTypeAuditPolicy) {
        result.setPartyType(partyControl.getPartyTypeTransfer(getUserVisit(), partyTypeAuditPolicy.getLastDetail().getPartyType()));
    }

    @Override
    public void canEdit(PartyTypeAuditPolicy partyTypeAuditPolicy) {
        timeUnitOfMeasureKind = unitOfMeasureKindLogic.getTimeUnitOfMeasureKind(this);
    }

    @Override
    public void doLock(PartyTypeAuditPolicyEdit edit, PartyTypeAuditPolicy partyTypeAuditPolicy) {
        var partyTypeAuditPolicyDetail = partyTypeAuditPolicy.getLastDetail();
        var retainUserVisitsTime = partyTypeAuditPolicyDetail.getRetainUserVisitsTime();
        var retainUserVisitsTimeConversion = retainUserVisitsTime == null ? null
                : new Conversion(uomControl, timeUnitOfMeasureKind, retainUserVisitsTime).convertToHighestUnitOfMeasureType();

        edit.setAuditCommands(partyTypeAuditPolicyDetail.getAuditCommands().toString());
        if(retainUserVisitsTimeConversion != null) {
            edit.setRetainUserVisitsTime(retainUserVisitsTimeConversion.getQuantity().toString());
            edit.setRetainUserVisitsTimeUnitOfMeasureTypeName(retainUserVisitsTimeConversion.getUnitOfMeasureType().getLastDetail().getUnitOfMeasureTypeName());
        }
    }

    @Override
    public void canUpdate(PartyTypeAuditPolicy partyTypeAuditPolicy) {
        var rawRetainUserVisitsTime = edit.getRetainUserVisitsTime();
        var retainUserVisitsTimeUnitOfMeasureTypeName = edit.getRetainUserVisitsTimeUnitOfMeasureTypeName();

        if((rawRetainUserVisitsTime == null) == (retainUserVisitsTimeUnitOfMeasureTypeName == null)) {
            retainUserVisitsTimeUnitOfMeasureType = retainUserVisitsTimeUnitOfMeasureTypeName == null ? null
                    : unitOfMeasureTypeLogic.getUnitOfMeasureTypeByName(this, timeUnitOfMeasureKind, retainUserVisitsTimeUnitOfMeasureTypeName,
                            UnknownRetainUserVisitsTimeUnitOfMeasureTypeNameException.class, ExecutionErrors.UnknownRetainUserVisitsTimeUnitOfMeasureTypeName.name());
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    public void doUpdate(PartyTypeAuditPolicy partyTypeAuditPolicy) {
        var partyTypeAuditPolicyDetailValue = partyControl.getPartyTypeAuditPolicyDetailValueForUpdate(partyTypeAuditPolicy);
        var rawRetainUserVisitsTime = edit.getRetainUserVisitsTime();
        var auditCommands = Boolean.valueOf(edit.getAuditCommands());
        var retainUserVisitsTime = rawRetainUserVisitsTime == null ? null : Long.valueOf(rawRetainUserVisitsTime);
        var retainUserVisitsTimeConversion = retainUserVisitsTime == null ? null
                : new Conversion(uomControl, retainUserVisitsTimeUnitOfMeasureType, retainUserVisitsTime).convertToLowestUnitOfMeasureType();

        partyTypeAuditPolicyDetailValue.setAuditCommands(auditCommands);
        partyTypeAuditPolicyDetailValue.setRetainUserVisitsTime(retainUserVisitsTimeConversion == null ? null : retainUserVisitsTimeConversion.getQuantity());

        partyControl.updatePartyTypeAuditPolicyFromValue(partyTypeAuditPolicyDetailValue, getPartyPK());
    }

}
