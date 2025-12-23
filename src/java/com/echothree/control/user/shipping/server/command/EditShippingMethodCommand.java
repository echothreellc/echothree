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

package com.echothree.control.user.shipping.server.command;

import com.echothree.control.user.shipping.common.edit.ShippingEditFactory;
import com.echothree.control.user.shipping.common.edit.ShippingMethodEdit;
import com.echothree.control.user.shipping.common.form.EditShippingMethodForm;
import com.echothree.control.user.shipping.common.result.EditShippingMethodResult;
import com.echothree.control.user.shipping.common.result.ShippingResultFactory;
import com.echothree.control.user.shipping.common.spec.ShippingMethodSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.selector.common.SelectorKinds;
import com.echothree.model.control.selector.common.SelectorTypes;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.control.shipping.server.control.ShippingControl;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditShippingMethodCommand
        extends BaseAbstractEditCommand<ShippingMethodSpec, ShippingMethodEdit, EditShippingMethodResult, ShippingMethod, ShippingMethod> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ShippingMethod.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ShippingMethodName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ShippingMethodName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("GeoCodeSelectorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ItemSelectorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditShippingMethodCommand */
    public EditShippingMethodCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditShippingMethodResult getResult() {
        return ShippingResultFactory.getEditShippingMethodResult();
    }

    @Override
    public ShippingMethodEdit getEdit() {
        return ShippingEditFactory.getShippingMethodEdit();
    }

    @Override
    public ShippingMethod getEntity(EditShippingMethodResult result) {
        var shippingControl = Session.getModelController(ShippingControl.class);
        ShippingMethod shippingMethod;
        var shippingMethodName = spec.getShippingMethodName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            shippingMethod = shippingControl.getShippingMethodByName(shippingMethodName);
        } else { // EditMode.UPDATE
            shippingMethod = shippingControl.getShippingMethodByNameForUpdate(shippingMethodName);
        }

        if(shippingMethod != null) {
            result.setShippingMethod(shippingControl.getShippingMethodTransfer(getUserVisit(), shippingMethod));
        } else {
            addExecutionError(ExecutionErrors.UnknownShippingMethodName.name(), shippingMethodName);
        }

        return shippingMethod;
    }

    @Override
    public ShippingMethod getLockEntity(ShippingMethod shippingMethod) {
        return shippingMethod;
    }

    @Override
    public void fillInResult(EditShippingMethodResult result, ShippingMethod shippingMethod) {
        var shippingControl = Session.getModelController(ShippingControl.class);

        result.setShippingMethod(shippingControl.getShippingMethodTransfer(getUserVisit(), shippingMethod));
    }

    Selector geoCodeSelector = null;
    Selector itemSelector = null;

    @Override
    public void doLock(ShippingMethodEdit edit, ShippingMethod shippingMethod) {
        var shippingControl = Session.getModelController(ShippingControl.class);
        var shippingMethodDescription = shippingControl.getShippingMethodDescription(shippingMethod, getPreferredLanguage());
        var shippingMethodDetail = shippingMethod.getLastDetail();

        geoCodeSelector = shippingMethodDetail.getGeoCodeSelector();
        shippingMethodDetail.getItemSelector();

        edit.setShippingMethodName(shippingMethodDetail.getShippingMethodName());
        edit.setGeoCodeSelectorName(itemSelector == null? null: geoCodeSelector.getLastDetail().getSelectorName());
        edit.setItemSelectorName(itemSelector == null? null: itemSelector.getLastDetail().getSelectorName());
        edit.setSortOrder(shippingMethodDetail.getSortOrder().toString());

        if(shippingMethodDescription != null) {
            edit.setDescription(shippingMethodDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(ShippingMethod shippingMethod) {
        var shippingControl = Session.getModelController(ShippingControl.class);
        var shippingMethodName = edit.getShippingMethodName();
        var duplicateShippingMethod = shippingControl.getShippingMethodByName(shippingMethodName);

        if(duplicateShippingMethod == null || shippingMethod.equals(duplicateShippingMethod)) {
            var geoCodeSelectorName = edit.getGeoCodeSelectorName();

            if(geoCodeSelectorName != null) {
                var selectorControl = Session.getModelController(SelectorControl.class);
                var selectorKind = selectorControl.getSelectorKindByName(SelectorKinds.ITEM.name());

                if(selectorKind != null) {
                    var selectorType = selectorControl.getSelectorTypeByName(selectorKind,
                            SelectorTypes.CARRIER.name());

                    if(selectorType != null) {
                        geoCodeSelector = selectorControl.getSelectorByName(selectorType, geoCodeSelectorName);
                    } else {
                        addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), SelectorTypes.SHIPPING_METHOD.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), SelectorKinds.ITEM.name());
                }
            }

            if(geoCodeSelectorName == null || geoCodeSelector != null) {
                var itemSelectorName = edit.getItemSelectorName();

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

                if(itemSelectorName != null && itemSelector == null) {
                    addExecutionError(ExecutionErrors.UnknownItemSelectorName.name(), itemSelectorName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownGeoCodeSelectorName.name(), geoCodeSelectorName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateShippingMethodName.name(), shippingMethodName);
        }
    }

    @Override
    public void doUpdate(ShippingMethod shippingMethod) {
        var shippingControl = Session.getModelController(ShippingControl.class);
        var partyPK = getPartyPK();
        var shippingMethodDetailValue = shippingControl.getShippingMethodDetailValueForUpdate(shippingMethod);
        var shippingMethodDescription = shippingControl.getShippingMethodDescriptionForUpdate(shippingMethod, getPreferredLanguage());
        var description = edit.getDescription();

        shippingMethodDetailValue.setShippingMethodName(edit.getShippingMethodName());
        shippingMethodDetailValue.setGeoCodeSelectorPK(geoCodeSelector == null ? null : geoCodeSelector.getPrimaryKey());
        shippingMethodDetailValue.setItemSelectorPK(itemSelector == null ? null : itemSelector.getPrimaryKey());
        shippingMethodDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        shippingControl.updateShippingMethodFromValue(shippingMethodDetailValue, partyPK);

        if(shippingMethodDescription == null && description != null) {
            shippingControl.createShippingMethodDescription(shippingMethod, getPreferredLanguage(), description, partyPK);
        } else {
            if(shippingMethodDescription != null && description == null) {
                shippingControl.deleteShippingMethodDescription(shippingMethodDescription, partyPK);
            } else {
                if(shippingMethodDescription != null && description != null) {
                    var shippingMethodDescriptionValue = shippingControl.getShippingMethodDescriptionValue(shippingMethodDescription);

                    shippingMethodDescriptionValue.setDescription(description);
                    shippingControl.updateShippingMethodDescriptionFromValue(shippingMethodDescriptionValue, partyPK);
                }
            }
        }
    }

}
