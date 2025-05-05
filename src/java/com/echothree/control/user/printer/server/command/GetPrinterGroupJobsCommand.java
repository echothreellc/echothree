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

import com.echothree.control.user.printer.common.form.GetPrinterGroupJobsForm;
import com.echothree.control.user.printer.common.result.PrinterResultFactory;
import com.echothree.model.control.printer.server.control.PrinterControl;
import com.echothree.model.control.printer.common.workflow.PrinterGroupJobStatusConstants;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetPrinterGroupJobsCommand
        extends BaseSimpleCommand<GetPrinterGroupJobsForm> {
    
   private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PrinterGroupName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PrinterGroupJobStatusChoice", FieldType.ENTITY_NAME, false, null, null)
                ));
    }

    /** Creates a new instance of GetPrinterGroupJobsCommand */
    public GetPrinterGroupJobsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
   @Override
    protected BaseResult execute() {
       var result = PrinterResultFactory.getGetPrinterGroupJobsResult();
       var printerGroupName = form.getPrinterGroupName();
       var printerGroupJobStatusChoice = form.getPrinterGroupJobStatusChoice();
        var parameterCount = (printerGroupName == null ? 0 : 1) + (printerGroupJobStatusChoice == null ? 0 : 1);

        if(parameterCount == 1) {
            var printerControl = Session.getModelController(PrinterControl.class);
            var userVisit = getUserVisit();

            if(printerGroupName != null) {
                var printerGroup = printerControl.getPrinterGroupByName(printerGroupName);

                if(printerGroup != null) {
                    result.setPrinterGroup(printerControl.getPrinterGroupTransfer(userVisit, printerGroup));
                    result.setPrinterGroupJobs(printerControl.getPrinterGroupJobTransfersByPrinterGroup(getUserVisit(), printerGroup));
                } else {
                    addExecutionError(ExecutionErrors.UnknownPrinterGroupName.name(), printerGroupName);
                }
            } else if(printerGroupJobStatusChoice != null) {
                var workflowControl = Session.getModelController(WorkflowControl.class);
                var workflowStep = workflowControl.getWorkflowStepByName(workflowControl.getWorkflowByName(PrinterGroupJobStatusConstants.Workflow_PRINTER_GROUP_JOB_STATUS),
                        printerGroupJobStatusChoice);

                if(workflowStep != null) {
                    result.setPrinterGroupJobs(printerControl.getPrinterGroupJobTransfersByPrinterGroupJobStatus(userVisit, workflowStep));
                } else {
                    addExecutionError(ExecutionErrors.UnknownPrinterGroupJobStatusChoice.name(), printerGroupJobStatusChoice);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
