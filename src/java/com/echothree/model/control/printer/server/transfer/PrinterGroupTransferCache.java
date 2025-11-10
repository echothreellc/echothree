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
import com.echothree.model.control.printer.common.PrinterOptions;
import com.echothree.model.control.printer.common.transfer.PrinterGroupTransfer;
import com.echothree.model.control.printer.common.workflow.PrinterGroupStatusConstants;
import com.echothree.model.control.printer.server.control.PrinterControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.printer.server.entity.PrinterGroup;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.UnitOfMeasureUtils;

public class PrinterGroupTransferCache
        extends BasePrinterTransferCache<PrinterGroup, PrinterGroupTransfer> {
    
    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    UomControl uomControl = Session.getModelController(UomControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    boolean includePrinters;
    
    /** Creates a new instance of PrinterGroupTransferCache */
    public PrinterGroupTransferCache(UserVisit userVisit, PrinterControl printerControl) {
        super(userVisit, printerControl);

        var options = session.getOptions();
        if(options != null) {
            includePrinters = options.contains(PrinterOptions.PrinterGroupIncludePrinters);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public PrinterGroupTransfer getPrinterGroupTransfer(PrinterGroup printerGroup) {
        var printerGroupTransfer = get(printerGroup);
        
        if(printerGroupTransfer == null) {
            var printerGroupDetail = printerGroup.getLastDetail();
            var timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);
            var printerGroupName = printerGroupDetail.getPrinterGroupName();
            var unformattedKeepPrintedJobsTime = printerGroupDetail.getKeepPrintedJobsTime();
            var keepPrintedJobsTime = UnitOfMeasureUtils.getInstance().formatUnitOfMeasure(userVisit, timeUnitOfMeasureKind, unformattedKeepPrintedJobsTime);
            var isDefault = printerGroupDetail.getIsDefault();
            var sortOrder = printerGroupDetail.getSortOrder();
            var description = printerControl.getBestPrinterGroupDescription(printerGroup, getLanguage(userVisit));

            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(printerGroup.getPrimaryKey());
            var printerGroupStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    PrinterGroupStatusConstants.Workflow_PRINTER_GROUP_STATUS, entityInstance);
            
            printerGroupTransfer = new PrinterGroupTransfer(printerGroupName, unformattedKeepPrintedJobsTime, keepPrintedJobsTime, isDefault, sortOrder,
                    printerGroupStatusTransfer, description);
            put(userVisit, printerGroup, printerGroupTransfer);

            if(includePrinters) {
                printerGroupTransfer.setPrinters(new ListWrapper<>(printerControl.getPrinterTransfersByPrinterGroup(userVisit, printerGroup)));
            }
        }
        
        return printerGroupTransfer;
    }
    
}
