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

import com.echothree.control.user.printer.common.form.CreatePrinterGroupForm;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.printer.common.workflow.PrinterGroupStatusConstants;
import com.echothree.model.control.printer.server.control.PrinterControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreatePrinterGroupCommand
        extends BaseSimpleCommand<CreatePrinterGroupForm> {
    
   private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PrinterGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("KeepPrintedJobsTime", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("KeepPrintedJobsTimeUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of CreatePrinterGroupCommand */
    public CreatePrinterGroupCommand(UserVisitPK userVisitPK, CreatePrinterGroupForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
   @Override
    protected BaseResult execute() {
        var printerControl = Session.getModelController(PrinterControl.class);
       var printerGroupName = form.getPrinterGroupName();
       var printerGroup = printerControl.getPrinterGroupByName(printerGroupName);
        
        if(printerGroup == null) {
            var unitOfMeasureTypeLogic = UnitOfMeasureTypeLogic.getInstance();
            var keepPrintedJobsTime = unitOfMeasureTypeLogic.checkUnitOfMeasure(this, UomConstants.UnitOfMeasureKindUseType_TIME,
                    form.getKeepPrintedJobsTime(), form.getKeepPrintedJobsTimeUnitOfMeasureTypeName(),
                    null, ExecutionErrors.MissingRequiredKeepPrintedJobsTime.name(), null, ExecutionErrors.MissingRequiredKeepPrintedJobsTimeUnitOfMeasureTypeName.name(),
                    null, ExecutionErrors.UnknownKeepPrintedJobsTimeUnitOfMeasureTypeName.name());

            if(!hasExecutionErrors()) {
                var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
                var workflowControl = Session.getModelController(WorkflowControl.class);
                var isDefault = Boolean.valueOf(form.getIsDefault());
                var sortOrder = Integer.valueOf(form.getSortOrder());
                var description = form.getDescription();
                var createdBy = getPartyPK();

                printerGroup = printerControl.createPrinterGroup(printerGroupName, keepPrintedJobsTime, isDefault, sortOrder, createdBy);

                if(description != null) {
                    var language = getPreferredLanguage();

                    printerControl.createPrinterGroupDescription(printerGroup, language, description, createdBy);
                }

                var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(printerGroup.getPrimaryKey());
                workflowControl.addEntityToWorkflowUsingNames(null, PrinterGroupStatusConstants.Workflow_PRINTER_GROUP_STATUS,
                        PrinterGroupStatusConstants.WorkflowEntrance_NEW_PRINTER_GROUP, entityInstance, null, null, createdBy);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicatePrinterGroupName.name(), printerGroupName);
        }
        
        return null;
    }
    
}
