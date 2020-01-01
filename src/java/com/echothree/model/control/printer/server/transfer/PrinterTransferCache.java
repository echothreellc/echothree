// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.printer.common.transfer.PrinterGroupTransfer;
import com.echothree.model.control.printer.common.transfer.PrinterTransfer;
import com.echothree.model.control.printer.server.PrinterControl;
import com.echothree.model.control.printer.common.workflow.PrinterStatusConstants;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.printer.server.entity.Printer;
import com.echothree.model.data.printer.server.entity.PrinterDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PrinterTransferCache
        extends BasePrinterTransferCache<Printer, PrinterTransfer> {
    
    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of PrinterTransferCache */
    public PrinterTransferCache(UserVisit userVisit, PrinterControl printerControl) {
        super(userVisit, printerControl);
        
        setIncludeEntityInstance(true);
    }
    
    public PrinterTransfer getPrinterTransfer(Printer printer) {
        PrinterTransfer printerTransfer = get(printer);
        
        if(printerTransfer == null) {
            PrinterDetail printerDetail = printer.getLastDetail();
            String printerName = printerDetail.getPrinterName();
            PrinterGroupTransfer printerGroupTransfer = printerControl.getPrinterGroupTransfer(userVisit, printerDetail.getPrinterGroup());
            Integer priority = printerDetail.getPriority();
            String description = printerControl.getBestPrinterDescription(printer, getLanguage());
            
            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(printer.getPrimaryKey());
            WorkflowEntityStatusTransfer printerStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    PrinterStatusConstants.Workflow_PRINTER_STATUS, entityInstance);
            
            printerTransfer = new PrinterTransfer(printerName, printerGroupTransfer, priority, printerStatusTransfer, description);
            put(printer, printerTransfer);
        }
        
        return printerTransfer;
    }
    
}
