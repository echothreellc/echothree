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

package com.echothree.control.user.invoice.server.command;

import com.echothree.control.user.invoice.common.form.GetInvoiceTypeForm;
import com.echothree.control.user.invoice.common.result.InvoiceResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetInvoiceTypeCommand
        extends BaseSimpleCommand<GetInvoiceTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.InvoiceType.name(), SecurityRoles.Review.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("InvoiceTypeName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetInvoiceTypeCommand */
    public GetInvoiceTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var invoiceControl = Session.getModelController(InvoiceControl.class);
        var result = InvoiceResultFactory.getGetInvoiceTypeResult();
        var invoiceTypeName = form.getInvoiceTypeName();
        var invoiceType = invoiceControl.getInvoiceTypeByName(invoiceTypeName);
        
        if(invoiceType != null) {
            result.setInvoiceType(invoiceControl.getInvoiceTypeTransfer(getUserVisit(), invoiceType));
            
            sendEvent(invoiceType.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        } else {
            addExecutionError(ExecutionErrors.UnknownInvoiceTypeName.name(), invoiceTypeName);
        }
        
        return result;
    }
    
}
