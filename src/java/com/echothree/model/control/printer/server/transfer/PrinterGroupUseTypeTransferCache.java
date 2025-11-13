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

import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.printer.common.transfer.PrinterGroupUseTypeTransfer;
import com.echothree.model.control.printer.server.control.PrinterControl;
import com.echothree.model.data.printer.server.entity.PrinterGroupUseType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PrinterGroupUseTypeTransferCache
        extends BasePrinterTransferCache<PrinterGroupUseType, PrinterGroupUseTypeTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);

    /** Creates a new instance of PrinterGroupUseTypeTransferCache */
    public PrinterGroupUseTypeTransferCache(PrinterControl printerControl) {
        super(printerControl);
        
        setIncludeEntityInstance(true);
    }

    public PrinterGroupUseTypeTransfer getPrinterGroupUseTypeTransfer(UserVisit userVisit, PrinterGroupUseType printerGroupUseType) {
        var printerGroupUseTypeTransfer = get(printerGroupUseType);

        if(printerGroupUseTypeTransfer == null) {
            var printerGroupUseTypeDetail = printerGroupUseType.getLastDetail();
            var printerGroupUseTypeName = printerGroupUseTypeDetail.getPrinterGroupUseTypeName();
            var isDefault = printerGroupUseTypeDetail.getIsDefault();
            var sortOrder = printerGroupUseTypeDetail.getSortOrder();
            var description = printerControl.getBestPrinterGroupUseTypeDescription(printerGroupUseType, getLanguage(userVisit));

            printerGroupUseTypeTransfer = new PrinterGroupUseTypeTransfer(printerGroupUseTypeName, isDefault, sortOrder, description);
            put(userVisit, printerGroupUseType, printerGroupUseTypeTransfer);
        }

        return printerGroupUseTypeTransfer;
    }

}
