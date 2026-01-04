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

package com.echothree.control.user.purchase.server.command;

import com.echothree.control.user.purchase.common.form.GetPurchaseOrderStatusChoicesForm;
import com.echothree.control.user.purchase.common.result.PurchaseResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.purchase.server.logic.PurchaseOrderLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetPurchaseOrderStatusChoicesCommand
        extends BaseSimpleCommand<GetPurchaseOrderStatusChoicesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.PurchaseOrderStatus.name(), SecurityRoles.Choices.name())
                    )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("OrderName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("DefaultOrderStatusChoice", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetPurchaseOrderStatusChoicesCommand */
    public GetPurchaseOrderStatusChoicesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var purchaseOrderLogic = PurchaseOrderLogic.getInstance();
        var result = PurchaseResultFactory.getGetPurchaseOrderStatusChoicesResult();
        var orderName = form.getOrderName();
        var order = purchaseOrderLogic.getOrderByName(this, orderName);
        
        if(!hasExecutionErrors()) {
            var defaultOrderStatusChoice = form.getDefaultPurchaseOrderStatusChoice();
            var allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());
            
            result.setPurchaseOrderStatusChoices(purchaseOrderLogic.getPurchaseOrderStatusChoices(defaultOrderStatusChoice, getPreferredLanguage(), allowNullChoice, order,
                    getPartyPK()));
        }
        
        return result;
    }
    
}
