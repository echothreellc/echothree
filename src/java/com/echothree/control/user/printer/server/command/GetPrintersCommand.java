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

import com.echothree.control.user.printer.common.form.GetPrintersForm;
import com.echothree.control.user.printer.common.result.PrinterResultFactory;
import com.echothree.model.control.printer.server.control.PrinterControl;
import com.echothree.model.data.printer.server.entity.Printer;
import com.echothree.model.data.printer.server.entity.PrinterGroup;
import com.echothree.model.data.printer.server.factory.PrinterFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetPrintersCommand
        extends BasePaginatedMultipleEntitiesCommand<Printer, GetPrintersForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PrinterGroupName", FieldType.ENTITY_NAME, false, null, null)
        );
    }

    @Inject
    PrinterControl printerControl;

    /** Creates a new instance of GetPrintersCommand */
    public GetPrintersCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    PrinterGroup printerGroup;

    @Override
    protected void handleForm() {
        var printerGroupName = form.getPrinterGroupName();

        if(printerGroupName != null) {
            printerGroup = printerControl.getPrinterGroupByName(printerGroupName);

            if(printerGroup == null) {
                addExecutionError(com.echothree.util.common.message.ExecutionErrors.UnknownPrinterGroupName.name(), printerGroupName);
            }
        }
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null :
                printerGroup == null ? printerControl.countPrinters() : printerControl.countPrintersByPrinterGroup(printerGroup);
    }

    @Override
    protected Collection<Printer> getEntities() {
        return hasExecutionErrors() ? null :
                printerGroup == null ? printerControl.getPrinters() : printerControl.getPrintersByPrinterGroup(printerGroup);
    }

    @Override
    protected BaseResult getResult(Collection<Printer> entities) {
        var result = PrinterResultFactory.getGetPrintersResult();

        if(entities != null) {
            if(printerGroup != null) {
                result.setPrinterGroup(printerControl.getPrinterGroupTransfer(getUserVisit(), printerGroup));
            }

            if(session.hasLimit(PrinterFactory.class)) {
                result.setPrinterCount(getTotalEntities());
            }

            result.setPrinters(printerControl.getPrinterTransfers(getUserVisit(), entities));
        }

        return result;
    }

}
