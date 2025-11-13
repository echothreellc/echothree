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
import com.echothree.model.control.printer.common.transfer.PrinterTransfer;
import com.echothree.model.control.printer.server.control.PrinterControl;
import com.echothree.model.control.printer.common.workflow.PrinterStatusConstants;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.printer.server.entity.Printer;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PrinterTransferCache
        extends BasePrinterTransferCache<Printer, PrinterTransfer> {
    
    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of PrinterTransferCache */
    public PrinterTransferCache(PrinterControl printerControl) {
        super(printerControl);
        
        setIncludeEntityInstance(true);
    }
    
    public PrinterTransfer getPrinterTransfer(Printer printer) {
        var printerTransfer = get(printer);
        
        if(printerTransfer == null) {
            var printerDetail = printer.getLastDetail();
            var printerName = printerDetail.getPrinterName();
            var printerGroupTransfer = printerControl.getPrinterGroupTransfer(userVisit, printerDetail.getPrinterGroup());
            var priority = printerDetail.getPriority();
            var description = printerControl.getBestPrinterDescription(printer, getLanguage(userVisit));

            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(printer.getPrimaryKey());
            var printerStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    PrinterStatusConstants.Workflow_PRINTER_STATUS, entityInstance);
            
            printerTransfer = new PrinterTransfer(printerName, printerGroupTransfer, priority, printerStatusTransfer, description);
            put(userVisit, printer, printerTransfer);
        }
        
        return printerTransfer;
    }
    
}
