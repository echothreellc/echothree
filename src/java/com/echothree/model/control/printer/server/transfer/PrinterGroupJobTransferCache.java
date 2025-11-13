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

package com.echothree.model.control.printer.server.transfer;

import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.control.printer.common.transfer.PrinterGroupJobTransfer;
import com.echothree.model.control.printer.common.workflow.PrinterGroupJobStatusConstants;
import com.echothree.model.control.printer.server.control.PrinterControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.printer.server.entity.PrinterGroupJob;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PrinterGroupJobTransferCache
        extends BasePrinterTransferCache<PrinterGroupJob, PrinterGroupJobTransfer> {
    
    DocumentControl documentControl = Session.getModelController(DocumentControl.class);
    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of PrinterGroupJobTransferCache */
    public PrinterGroupJobTransferCache(PrinterControl printerControl) {
        super(printerControl);
        
        setIncludeEntityInstance(true);
    }
    
    public PrinterGroupJobTransfer getPrinterGroupJobTransfer(PrinterGroupJob printerGroupJob) {
        var printerGroupJobTransfer = get(printerGroupJob);
        
        if(printerGroupJobTransfer == null) {
            var printerGroupJobDetail = printerGroupJob.getLastDetail();
            var printerGroupJobName = printerGroupJobDetail.getPrinterGroupJobName();
            var printerGroupTransfer = printerControl.getPrinterGroupTransfer(userVisit, printerGroupJobDetail.getPrinterGroup());
            var documentTransfer = documentControl.getDocumentTransfer(userVisit, printerGroupJobDetail.getDocument());
            var copies = printerGroupJobDetail.getCopies();
            var priority = printerGroupJobDetail.getPriority();

            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(printerGroupJob.getPrimaryKey());
            var printerGroupJobStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    PrinterGroupJobStatusConstants.Workflow_PRINTER_GROUP_JOB_STATUS, entityInstance);
            
            printerGroupJobTransfer = new PrinterGroupJobTransfer(printerGroupJobName, printerGroupTransfer, documentTransfer, copies, priority,
                    printerGroupJobStatusTransfer);
            put(userVisit, printerGroupJob, printerGroupJobTransfer);
        }
        
        return printerGroupJobTransfer;
    }
    
}
