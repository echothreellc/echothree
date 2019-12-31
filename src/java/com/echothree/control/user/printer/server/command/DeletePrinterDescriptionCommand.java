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

package com.echothree.control.user.printer.server.command;

import com.echothree.control.user.printer.common.form.DeletePrinterDescriptionForm;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.printer.server.PrinterControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.printer.server.entity.Printer;
import com.echothree.model.data.printer.server.entity.PrinterDescription;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DeletePrinterDescriptionCommand
        extends BaseSimpleCommand<DeletePrinterDescriptionForm> {
    
   private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PrinterName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }

    /** Creates a new instance of DeletePrinterDescriptionCommand */
    public DeletePrinterDescriptionCommand(UserVisitPK userVisitPK, DeletePrinterDescriptionForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
   @Override
    protected BaseResult execute() {
        var printerControl = (PrinterControl)Session.getModelController(PrinterControl.class);
        String printerName = form.getPrinterName();
        Printer printer = printerControl.getPrinterByName(printerName);
        
        if(printer != null) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = form.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                PrinterDescription printerDescription = printerControl.getPrinterDescriptionForUpdate(printer, language);
                
                if(printerDescription != null) {
                    printerControl.deletePrinterDescription(printerDescription, getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.UnknownPrinterDescription.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPrinterName.name(), printerName);
        }
        
        return null;
    }
    
}
