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

package com.echothree.control.user.printer.server.command;

import com.echothree.control.user.printer.common.form.DeletePrinterGroupDescriptionForm;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.printer.server.PrinterControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.printer.server.entity.PrinterGroup;
import com.echothree.model.data.printer.server.entity.PrinterGroupDescription;
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

public class DeletePrinterGroupDescriptionCommand
        extends BaseSimpleCommand<DeletePrinterGroupDescriptionForm> {
    
   private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PrinterGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }

    /** Creates a new instance of DeletePrinterGroupDescriptionCommand */
    public DeletePrinterGroupDescriptionCommand(UserVisitPK userVisitPK, DeletePrinterGroupDescriptionForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
   @Override
    protected BaseResult execute() {
        var printerControl = (PrinterControl)Session.getModelController(PrinterControl.class);
        String printerGroupName = form.getPrinterGroupName();
        PrinterGroup printerGroup = printerControl.getPrinterGroupByName(printerGroupName);
        
        if(printerGroup != null) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = form.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                PrinterGroupDescription printerGroupDescription = printerControl.getPrinterGroupDescriptionForUpdate(printerGroup, language);
                
                if(printerGroupDescription != null) {
                    printerControl.deletePrinterGroupDescription(printerGroupDescription, getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.UnknownPrinterGroupDescription.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPrinterGroupName.name(), printerGroupName);
        }
        
        return null;
    }
    
}
