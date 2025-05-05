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

package com.echothree.control.user.printer.server.command;

import com.echothree.control.user.printer.common.edit.PrinterEditFactory;
import com.echothree.control.user.printer.common.edit.PrinterGroupEdit;
import com.echothree.control.user.printer.common.form.EditPrinterGroupForm;
import com.echothree.control.user.printer.common.result.EditPrinterGroupResult;
import com.echothree.control.user.printer.common.result.PrinterResultFactory;
import com.echothree.control.user.printer.common.spec.PrinterGroupSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.printer.server.control.PrinterControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.data.printer.server.entity.PrinterGroup;
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

public class EditPrinterGroupCommand
        extends BaseAbstractEditCommand<PrinterGroupSpec, PrinterGroupEdit, EditPrinterGroupResult, PrinterGroup, PrinterGroup> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PrinterGroup.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PrinterGroupName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PrinterGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("KeepPrintedJobsTime", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("KeepPrintedJobsTimeUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditPrinterGroupCommand */
    public EditPrinterGroupCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditPrinterGroupResult getResult() {
        return PrinterResultFactory.getEditPrinterGroupResult();
    }

    @Override
    public PrinterGroupEdit getEdit() {
        return PrinterEditFactory.getPrinterGroupEdit();
    }

    @Override
    public PrinterGroup getEntity(EditPrinterGroupResult result) {
        var printerControl = Session.getModelController(PrinterControl.class);
        PrinterGroup printerGroup;
        var printerGroupName = spec.getPrinterGroupName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            printerGroup = printerControl.getPrinterGroupByName(printerGroupName);
        } else { // EditMode.UPDATE
            printerGroup = printerControl.getPrinterGroupByNameForUpdate(printerGroupName);
        }

        if(printerGroup != null) {
            result.setPrinterGroup(printerControl.getPrinterGroupTransfer(getUserVisit(), printerGroup));
        } else {
            addExecutionError(ExecutionErrors.UnknownPrinterGroupName.name(), printerGroupName);
        }

        return printerGroup;
    }

    @Override
    public PrinterGroup getLockEntity(PrinterGroup printerGroup) {
        return printerGroup;
    }

    @Override
    public void fillInResult(EditPrinterGroupResult result, PrinterGroup printerGroup) {
        var printerControl = Session.getModelController(PrinterControl.class);

        result.setPrinterGroup(printerControl.getPrinterGroupTransfer(getUserVisit(), printerGroup));
    }

    @Override
    public void doLock(PrinterGroupEdit edit, PrinterGroup printerGroup) {
        var printerControl = Session.getModelController(PrinterControl.class);
        var unitOfMeasureTypeLogic = UnitOfMeasureTypeLogic.getInstance();
        var printerGroupDescription = printerControl.getPrinterGroupDescription(printerGroup, getPreferredLanguage());
        var printerGroupDetail = printerGroup.getLastDetail();
        UnitOfMeasureTypeLogic.StringUnitOfMeasure stringUnitOfMeasure;

        edit.setPrinterGroupName(printerGroupDetail.getPrinterGroupName());
        stringUnitOfMeasure = unitOfMeasureTypeLogic.unitOfMeasureToString(this, UomConstants.UnitOfMeasureKindUseType_TIME, printerGroupDetail.getKeepPrintedJobsTime());
        edit.setKeepPrintedJobsTimeUnitOfMeasureTypeName(stringUnitOfMeasure.getUnitOfMeasureTypeName());
        edit.setKeepPrintedJobsTime(stringUnitOfMeasure.getValue());
        edit.setIsDefault(printerGroupDetail.getIsDefault().toString());
        edit.setSortOrder(printerGroupDetail.getSortOrder().toString());

        if(printerGroupDescription != null) {
            edit.setDescription(printerGroupDescription.getDescription());
        }
    }

    Long keepPrintedJobsTime;

    @Override
    public void canUpdate(PrinterGroup printerGroup) {
        var printerControl = Session.getModelController(PrinterControl.class);
        var printerGroupName = edit.getPrinterGroupName();
        var duplicatePrinterGroup = printerControl.getPrinterGroupByName(printerGroupName);

        if(duplicatePrinterGroup != null && !printerGroup.equals(duplicatePrinterGroup)) {
            addExecutionError(ExecutionErrors.DuplicatePrinterGroupName.name(), printerGroupName);
        } else {
            var unitOfMeasureTypeLogic = UnitOfMeasureTypeLogic.getInstance();

            keepPrintedJobsTime = unitOfMeasureTypeLogic.checkUnitOfMeasure(this, UomConstants.UnitOfMeasureKindUseType_TIME,
                    edit.getKeepPrintedJobsTime(), edit.getKeepPrintedJobsTimeUnitOfMeasureTypeName(),
                    null, ExecutionErrors.MissingRequiredKeepPrintedJobsTime.name(), null, ExecutionErrors.MissingRequiredKeepPrintedJobsTimeUnitOfMeasureTypeName.name(),
                    null, ExecutionErrors.UnknownKeepPrintedJobsTimeUnitOfMeasureTypeName.name());
        }
    }

    @Override
    public void doUpdate(PrinterGroup printerGroup) {
        var printerControl = Session.getModelController(PrinterControl.class);
        var partyPK = getPartyPK();
        var printerGroupDetailValue = printerControl.getPrinterGroupDetailValueForUpdate(printerGroup);
        var printerGroupDescription = printerControl.getPrinterGroupDescriptionForUpdate(printerGroup, getPreferredLanguage());
        var description = edit.getDescription();

        printerGroupDetailValue.setPrinterGroupName(edit.getPrinterGroupName());
        printerGroupDetailValue.setKeepPrintedJobsTime(keepPrintedJobsTime);
        printerGroupDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        printerGroupDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        printerControl.updatePrinterGroupFromValue(printerGroupDetailValue, partyPK);

        if(printerGroupDescription == null && description != null) {
            printerControl.createPrinterGroupDescription(printerGroup, getPreferredLanguage(), description, partyPK);
        } else if(printerGroupDescription != null && description == null) {
            printerControl.deletePrinterGroupDescription(printerGroupDescription, partyPK);
        } else if(printerGroupDescription != null && description != null) {
            var printerGroupDescriptionValue = printerControl.getPrinterGroupDescriptionValue(printerGroupDescription);

            printerGroupDescriptionValue.setDescription(description);
            printerControl.updatePrinterGroupDescriptionFromValue(printerGroupDescriptionValue, partyPK);
        }
    }

}
