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

package com.echothree.control.user.printer.server.command;

import com.echothree.control.user.printer.common.form.GetPrinterGroupJobsForm;
import com.echothree.control.user.printer.common.result.PrinterResultFactory;
import com.echothree.model.control.printer.common.workflow.PrinterGroupJobStatusConstants;
import com.echothree.model.control.printer.server.control.PrinterControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowStepLogic;
import com.echothree.model.data.printer.server.entity.PrinterGroup;
import com.echothree.model.data.printer.server.entity.PrinterGroupJob;
import com.echothree.model.data.printer.server.factory.PrinterGroupJobFactory;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetPrinterGroupJobsCommand
        extends BasePaginatedMultipleEntitiesCommand<PrinterGroupJob, GetPrinterGroupJobsForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PrinterGroupName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PrinterGroupJobStatusChoice", FieldType.ENTITY_NAME, false, null, null)
        );
    }

    @Inject
    PrinterControl printerControl;

    @Inject
    WorkflowControl workflowControl;

    @Inject
    WorkflowStepLogic workflowStepLogic;

    /** Creates a new instance of GetPrinterGroupJobsCommand */
    public GetPrinterGroupJobsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    PrinterGroup printerGroup;
    WorkflowStep workflowStep;

    @Override
    protected void handleForm() {
        var printerGroupName = form.getPrinterGroupName();
        var printerGroupJobStatusChoice = form.getPrinterGroupJobStatusChoice();
        var parameterCount = (printerGroupName == null ? 0 : 1) + (printerGroupJobStatusChoice == null ? 0 : 1);

        if(parameterCount == 1) {
            if(printerGroupName != null) {
                printerGroup = printerControl.getPrinterGroupByName(printerGroupName);

                if(printerGroup == null) {
                    addExecutionError(ExecutionErrors.UnknownPrinterGroupName.name(), printerGroupName);
                }
            } else {
                var workflow = workflowControl.getWorkflowByName(PrinterGroupJobStatusConstants.Workflow_PRINTER_GROUP_JOB_STATUS);

                workflowStep = workflowStepLogic.getWorkflowStepByName(this, workflow, printerGroupJobStatusChoice);
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null :
                printerGroup != null ? printerControl.countPrinterGroupJobsByPrinterGroup(printerGroup) :
                        printerControl.countPrinterGroupJobsByPrinterGroupJobStatus(workflowStep);
    }

    @Override
    protected Collection<PrinterGroupJob> getEntities() {
        return hasExecutionErrors() ? null :
                printerGroup != null ? printerControl.getPrinterGroupJobsByPrinterGroup(printerGroup) :
                        printerControl.getPrinterGroupJobsByPrinterGroupJobStatus(workflowStep);
    }

    @Override
    protected BaseResult getResult(Collection<PrinterGroupJob> entities) {
        var result = PrinterResultFactory.getGetPrinterGroupJobsResult();

        if(entities != null) {
            if(printerGroup != null) {
                result.setPrinterGroup(printerControl.getPrinterGroupTransfer(getUserVisit(), printerGroup));
            }

            if(session.hasLimit(PrinterGroupJobFactory.class)) {
                result.setPrinterGroupJobCount(getTotalEntities());
            }

            result.setPrinterGroupJobs(printerControl.getPrinterGroupJobTransfers(getUserVisit(), entities));
        }

        return result;
    }

}
