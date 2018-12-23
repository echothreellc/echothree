// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.model.control.printer.common.PrinterOptions;
import com.echothree.model.control.printer.common.transfer.PrinterGroupTransfer;
import com.echothree.model.control.printer.server.PrinterControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.control.printer.common.workflow.PrinterGroupStatusConstants;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.printer.server.entity.PrinterGroup;
import com.echothree.model.data.printer.server.entity.PrinterGroupDetail;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.UnitOfMeasureUtils;
import java.util.Set;

public class PrinterGroupTransferCache
        extends BasePrinterTransferCache<PrinterGroup, PrinterGroupTransfer> {
    
    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
    WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
    boolean includePrinters;
    
    /** Creates a new instance of PrinterGroupTransferCache */
    public PrinterGroupTransferCache(UserVisit userVisit, PrinterControl printerControl) {
        super(userVisit, printerControl);

        Set<String> options = session.getOptions();
        if(options != null) {
            includePrinters = options.contains(PrinterOptions.PrinterGroupIncludePrinters);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public PrinterGroupTransfer getPrinterGroupTransfer(PrinterGroup printerGroup) {
        PrinterGroupTransfer printerGroupTransfer = get(printerGroup);
        
        if(printerGroupTransfer == null) {
            PrinterGroupDetail printerGroupDetail = printerGroup.getLastDetail();
            UnitOfMeasureKind timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);
            String printerGroupName = printerGroupDetail.getPrinterGroupName();
            Long unformattedKeepPrintedJobsTime = printerGroupDetail.getKeepPrintedJobsTime();
            String keepPrintedJobsTime = UnitOfMeasureUtils.getInstance().formatUnitOfMeasure(userVisit, timeUnitOfMeasureKind, unformattedKeepPrintedJobsTime);
            Boolean isDefault = printerGroupDetail.getIsDefault();
            Integer sortOrder = printerGroupDetail.getSortOrder();
            String description = printerControl.getBestPrinterGroupDescription(printerGroup, getLanguage());
            
            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(printerGroup.getPrimaryKey());
            WorkflowEntityStatusTransfer printerGroupStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    PrinterGroupStatusConstants.Workflow_PRINTER_GROUP_STATUS, entityInstance);
            
            printerGroupTransfer = new PrinterGroupTransfer(printerGroupName, unformattedKeepPrintedJobsTime, keepPrintedJobsTime, isDefault, sortOrder,
                    printerGroupStatusTransfer, description);
            put(printerGroup, printerGroupTransfer);

            if(includePrinters) {
                printerGroupTransfer.setPrinters(new ListWrapper<>(printerControl.getPrinterTransfersByPrinterGroup(userVisit, printerGroup)));
            }
        }
        
        return printerGroupTransfer;
    }
    
}
