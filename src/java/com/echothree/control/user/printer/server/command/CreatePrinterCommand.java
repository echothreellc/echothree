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

package com.echothree.control.user.printer.server.command;

import com.echothree.control.user.printer.common.form.CreatePrinterForm;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.printer.common.workflow.PrinterStatusConstants;
import com.echothree.model.control.printer.server.control.PrinterControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class CreatePrinterCommand
        extends BaseSimpleCommand<CreatePrinterForm> {
    
   private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PrinterName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PrinterGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Priority", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of CreatePrinterCommand */
    public CreatePrinterCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
   @Override
    protected BaseResult execute() {
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var printerControl = Session.getModelController(PrinterControl.class);
        var printerName = form.getPrinterName();
        var printer = printerControl.getPrinterByName(printerName);
        
        if(printer == null) {
            var printerGroupName = form.getPrinterGroupName();
            var printerGroup = printerControl.getPrinterGroupByName(printerGroupName);
            
            if(printerGroup != null) {
                var workflowControl = Session.getModelController(WorkflowControl.class);
                var priority = Integer.valueOf(form.getPriority());
                var description = form.getDescription();
                var createdBy = getPartyPK();
                
                printer = printerControl.createPrinter(printerName, printerGroup, priority, createdBy);
                
                if(description != null) {
                    var language = getPreferredLanguage();
                    
                    printerControl.createPrinterDescription(printer, language, description, createdBy);
                }

                var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(printer.getPrimaryKey());
                workflowControl.addEntityToWorkflowUsingNames(null, PrinterStatusConstants.Workflow_PRINTER_STATUS, PrinterStatusConstants.WorkflowEntrance_NEW_PRINTER, entityInstance, null, null,
                        createdBy);
            } else {
                addExecutionError(ExecutionErrors.UnknownPrinterGroupName.name(), printerGroupName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicatePrinterName.name(), printerName);
        }
        
        return null;
    }
    
}
