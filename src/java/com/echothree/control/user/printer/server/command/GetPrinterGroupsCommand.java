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

import com.echothree.control.user.printer.common.form.GetPrinterGroupsForm;
import com.echothree.control.user.printer.common.result.PrinterResultFactory;
import com.echothree.model.control.printer.server.control.PrinterControl;
import com.echothree.model.data.printer.server.entity.PrinterGroup;
import com.echothree.model.data.printer.server.factory.PrinterGroupFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetPrinterGroupsCommand
        extends BasePaginatedMultipleEntitiesCommand<PrinterGroup, GetPrinterGroupsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of();
    }

    @Inject
    PrinterControl printerControl;

    /** Creates a new instance of GetPrinterGroupsCommand */
    public GetPrinterGroupsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        // No form fields.
    }

    @Override
    protected Long getTotalEntities() {
        return printerControl.countPrinterGroups();
    }

    @Override
    protected Collection<PrinterGroup> getEntities() {
        return printerControl.getPrinterGroups();
    }

    @Override
    protected BaseResult getResult(Collection<PrinterGroup> entities) {
        var result = PrinterResultFactory.getGetPrinterGroupsResult();

        if(entities != null) {
            if(session.hasLimit(PrinterGroupFactory.class)) {
                result.setPrinterGroupCount(getTotalEntities());
            }

            result.setPrinterGroups(printerControl.getPrinterGroupTransfers(getUserVisit(), entities));
        }

        return result;
    }
    
}
