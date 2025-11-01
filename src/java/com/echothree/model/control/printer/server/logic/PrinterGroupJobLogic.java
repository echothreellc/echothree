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

package com.echothree.model.control.printer.server.logic;

import com.echothree.model.control.document.common.DocumentConstants;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.control.document.server.logic.DocumentLogic;
import com.echothree.model.control.printer.common.workflow.PrinterGroupJobStatusConstants;
import com.echothree.model.control.printer.server.control.PrinterControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.printer.server.entity.PrinterGroup;
import com.echothree.model.data.printer.server.entity.PrinterGroupJob;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class PrinterGroupJobLogic
        extends BaseLogic {

    protected PrinterGroupJobLogic() {
        super();
    }

    public static PrinterGroupJobLogic getInstance() {
        return CDI.current().select(PrinterGroupJobLogic.class).get();
    }
    
    private void insertPrinterGroupJobIntoWorkflow(final PrinterGroupJob printerGroupJob, final PartyPK createdBy) {
        var workflowControl = Session.getModelController(WorkflowControl.class);

        workflowControl.addEntityToWorkflowUsingNames(null, PrinterGroupJobStatusConstants.Workflow_PRINTER_GROUP_JOB_STATUS, null,
                getEntityInstanceByBaseEntity(printerGroupJob), null, null, createdBy);
    }

    public PrinterGroupJob createPrinterGroupJob(final ExecutionErrorAccumulator ema, final PrinterGroup printerGroup, final Integer copies,
            final Integer priority, final MimeType mimeType, final Language preferredLanguage, final String description, final ByteArray blob, final String clob,
            final PartyPK createdBy) {
        var documentControl = Session.getModelController(DocumentControl.class);
        PrinterGroupJob printerGroupJob = null;
        var documentType = documentControl.getDocumentTypeByName(DocumentConstants.DocumentType_PRINTER_GROUP_JOB);

        if(documentType == null) {
            addExecutionError(ema, ExecutionErrors.UnknownDocumentTypeName.name(), DocumentConstants.DocumentType_PRINTER_GROUP_JOB);
        } else {
            var document = DocumentLogic.getInstance().createDocument(ema, documentType, mimeType, preferredLanguage, description, blob, clob, createdBy);

            if(document != null) {
                var printerControl = Session.getModelController(PrinterControl.class);

                printerGroupJob = printerControl.createPrinterGroupJob(printerGroup, document, copies, priority, createdBy);
                insertPrinterGroupJobIntoWorkflow(printerGroupJob, createdBy);
            }
        }

        return printerGroupJob;
    }

    public void deletePrinterGroupJob(final ExecutionErrorAccumulator ema, final PrinterGroupJob printerGroupJob, final PartyPK deletedBy) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var entityInstance = getEntityInstanceByBaseEntity(printerGroupJob);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(PrinterGroupJobStatusConstants.Workflow_PRINTER_GROUP_JOB_STATUS, entityInstance);
        var workflowStepName = workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName();

        if(workflowStepName.equals(PrinterGroupJobStatusConstants.WorkflowStep_QUEUED)
                || workflowStepName.equals(PrinterGroupJobStatusConstants.WorkflowStep_PRINTED)
                || workflowStepName.equals(PrinterGroupJobStatusConstants.WorkflowStep_ERRORED)) {
            var keepPrintedJobsTime = printerGroupJob.getLastDetail().getPrinterGroup().getLastDetail().getKeepPrintedJobsTime();

            if(keepPrintedJobsTime == null) {
                var printerControl = Session.getModelController(PrinterControl.class);
                
                printerControl.removePrinterGroupJob(printerGroupJob);
            } else {
                var workflowDestinationName = workflowStepName + "_TO_DELETED";

                workflowControl.transitionEntityInWorkflowUsingNames(null, workflowEntityStatus, workflowDestinationName, keepPrintedJobsTime, deletedBy);
            }
        } else {
            addExecutionError(ema, ExecutionErrors.CannotDeletePrinterGroupJob.name(), workflowStepName);
        }
    }

}
