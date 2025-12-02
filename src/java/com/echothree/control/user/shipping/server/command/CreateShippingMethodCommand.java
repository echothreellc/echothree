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

package com.echothree.control.user.shipping.server.command;

import com.echothree.control.user.shipping.common.form.CreateShippingMethodForm;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.selector.common.SelectorKinds;
import com.echothree.model.control.selector.common.SelectorTypes;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.control.shipping.server.control.ShippingControl;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class CreateShippingMethodCommand
        extends BaseSimpleCommand<CreateShippingMethodForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ShippingMethod.name(), SecurityRoles.Create.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ShippingMethodName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("GeoCodeSelectorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ItemSelectorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateShippingMethodCommand */
    public CreateShippingMethodCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var shippingControl = Session.getModelController(ShippingControl.class);
        var shippingMethodName = form.getShippingMethodName();
        var shippingMethod = shippingControl.getShippingMethodByName(shippingMethodName);
        
        if(shippingMethod == null) {
            var geoCodeSelectorName = form.getGeoCodeSelectorName();
            Selector geoCodeSelector = null;

            if(geoCodeSelectorName != null) {
                var selectorControl = Session.getModelController(SelectorControl.class);
                var selectorKind = selectorControl.getSelectorKindByName(SelectorKinds.POSTAL_ADDRESS.name());

                if(selectorKind != null) {
                    var selectorType = selectorControl.getSelectorTypeByName(selectorKind,
                            SelectorTypes.CARRIER.name());

                    if(selectorType != null) {
                        geoCodeSelector = selectorControl.getSelectorByName(selectorType, geoCodeSelectorName);
                    } else {
                        addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), SelectorTypes.SHIPPING_METHOD.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), SelectorKinds.POSTAL_ADDRESS.name());
                }
            }

            if(geoCodeSelectorName == null || geoCodeSelector != null) {
                var itemSelectorName = form.getItemSelectorName();
                Selector itemSelector = null;

                if(itemSelectorName != null) {
                    var selectorControl = Session.getModelController(SelectorControl.class);
                    var selectorKind = selectorControl.getSelectorKindByName(SelectorKinds.ITEM.name());

                    if(selectorKind != null) {
                        var selectorType = selectorControl.getSelectorTypeByName(selectorKind,
                                SelectorTypes.CARRIER.name());

                        if(selectorType != null) {
                            itemSelector = selectorControl.getSelectorByName(selectorType, itemSelectorName);
                        } else {
                            addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), SelectorTypes.SHIPPING_METHOD.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), SelectorKinds.ITEM.name());
                    }
                }

                if(itemSelectorName == null || itemSelector != null) {
                    var createdBy = getPartyPK();
                    var sortOrder = Integer.valueOf(form.getSortOrder());
                    var description = form.getDescription();

                    shippingMethod = shippingControl.createShippingMethod(shippingMethodName, geoCodeSelector, itemSelector, sortOrder, createdBy);

                    if(description != null) {
                        shippingControl.createShippingMethodDescription(shippingMethod, getPreferredLanguage(), description,
                                createdBy);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownItemSelectorName.name(), itemSelectorName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownGeoCodeSelectorName.name(), geoCodeSelectorName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateShippingMethodName.name(), shippingMethodName);
        }
        
        return null;
    }
    
}
