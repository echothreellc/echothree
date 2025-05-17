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

package com.echothree.control.user.carrier.server.command;

import com.echothree.control.user.carrier.common.edit.CarrierEditFactory;
import com.echothree.control.user.carrier.common.edit.CarrierServiceEdit;
import com.echothree.control.user.carrier.common.form.EditCarrierServiceForm;
import com.echothree.control.user.carrier.common.result.CarrierResultFactory;
import com.echothree.control.user.carrier.common.result.EditCarrierServiceResult;
import com.echothree.control.user.carrier.common.spec.CarrierServiceSpec;
import com.echothree.model.control.carrier.server.control.CarrierControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.selector.common.SelectorKinds;
import com.echothree.model.control.selector.common.SelectorTypes;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.carrier.server.entity.CarrierService;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.selector.server.entity.Selector;
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

public class EditCarrierServiceCommand
        extends BaseAbstractEditCommand<CarrierServiceSpec, CarrierServiceEdit, EditCarrierServiceResult, CarrierService, CarrierService> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.CarrierService.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CarrierName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CarrierServiceName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CarrierServiceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("GeoCodeSelectorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ItemSelectorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditCarrierServiceCommand */
    public EditCarrierServiceCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditCarrierServiceResult getResult() {
        return CarrierResultFactory.getEditCarrierServiceResult();
    }

    @Override
    public CarrierServiceEdit getEdit() {
        return CarrierEditFactory.getCarrierServiceEdit();
    }

    Party carrierParty;

    @Override
    public CarrierService getEntity(EditCarrierServiceResult result) {
        var carrierControl = Session.getModelController(CarrierControl.class);
        CarrierService carrierService = null;
        var carrierName = spec.getCarrierName();
        var carrier = carrierControl.getCarrierByName(carrierName);

        if(carrier != null) {
            var carrierServiceName = spec.getCarrierServiceName();
            
            carrierParty = carrier.getParty();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                carrierService = carrierControl.getCarrierServiceByName(carrierParty, carrierServiceName);
            } else { // EditMode.UPDATE
                carrierService = carrierControl.getCarrierServiceByNameForUpdate(carrierParty, carrierServiceName);
            }

            if(carrierService != null) {
                result.setCarrierService(carrierControl.getCarrierServiceTransfer(getUserVisit(), carrierService));
            } else {
                addExecutionError(ExecutionErrors.UnknownCarrierServiceName.name(), carrierName, carrierServiceName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCarrierName.name(), carrierName);
        }

        return carrierService;
    }

    @Override
    public CarrierService getLockEntity(CarrierService carrierService) {
        return carrierService;
    }

    @Override
    public void fillInResult(EditCarrierServiceResult result, CarrierService carrierService) {
        var carrierControl = Session.getModelController(CarrierControl.class);

        result.setCarrierService(carrierControl.getCarrierServiceTransfer(getUserVisit(), carrierService));
    }

    Selector geoCodeSelector;
    Selector itemSelector;

    @Override
    public void doLock(CarrierServiceEdit edit, CarrierService carrierService) {
        var carrierControl = Session.getModelController(CarrierControl.class);
        var carrierServiceDescription = carrierControl.getCarrierServiceDescription(carrierService, getPreferredLanguage());
        var carrierServiceDetail = carrierService.getLastDetail();

        geoCodeSelector = carrierServiceDetail.getGeoCodeSelector();
        itemSelector = carrierServiceDetail.getItemSelector();

        edit.setCarrierServiceName(carrierServiceDetail.getCarrierServiceName());
        edit.setGeoCodeSelectorName(itemSelector == null ? null: geoCodeSelector.getLastDetail().getSelectorName());
        edit.setItemSelectorName(itemSelector == null ? null: itemSelector.getLastDetail().getSelectorName());
        edit.setIsDefault(carrierServiceDetail.getIsDefault().toString());
        edit.setSortOrder(carrierServiceDetail.getSortOrder().toString());

        if(carrierServiceDescription != null) {
            edit.setDescription(carrierServiceDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(CarrierService carrierService) {
        var carrierControl = Session.getModelController(CarrierControl.class);
        var carrierServiceName = edit.getCarrierServiceName();
        var duplicateCarrierService = carrierControl.getCarrierServiceByName(carrierParty, carrierServiceName);

        if(duplicateCarrierService == null || carrierService.equals(duplicateCarrierService)) {
            var geoCodeSelectorName = edit.getGeoCodeSelectorName();

            if(geoCodeSelectorName != null) {
                var selectorControl = Session.getModelController(SelectorControl.class);
                var selectorKind = selectorControl.getSelectorKindByName(SelectorKinds.POSTAL_ADDRESS.name());

                if(selectorKind != null) {
                    var selectorType = selectorControl.getSelectorTypeByName(selectorKind,
                            SelectorTypes.CARRIER.name());

                    if(selectorType != null) {
                        geoCodeSelector = selectorControl.getSelectorByName(selectorType, geoCodeSelectorName);
                    } else {
                        addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), SelectorKinds.POSTAL_ADDRESS.name(),
                                SelectorTypes.CARRIER_SERVICE.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), SelectorKinds.POSTAL_ADDRESS.name());
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
                            addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), SelectorKinds.ITEM.name(),
                                    SelectorTypes.CARRIER_SERVICE.name());
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
            addExecutionError(ExecutionErrors.DuplicateCarrierServiceName.name(), carrierServiceName);
        }
    }

    @Override
    public void doUpdate(CarrierService carrierService) {
        var carrierControl = Session.getModelController(CarrierControl.class);
        var partyPK = getPartyPK();
        var carrierServiceDetailValue = carrierControl.getCarrierServiceDetailValueForUpdate(carrierService);
        var carrierServiceDescription = carrierControl.getCarrierServiceDescriptionForUpdate(carrierService, getPreferredLanguage());
        var description = edit.getDescription();

        carrierServiceDetailValue.setCarrierServiceName(edit.getCarrierServiceName());
        carrierServiceDetailValue.setGeoCodeSelectorPK(geoCodeSelector == null ? null : geoCodeSelector.getPrimaryKey());
        carrierServiceDetailValue.setItemSelectorPK(itemSelector == null ? null : itemSelector.getPrimaryKey());
        carrierServiceDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        carrierServiceDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        carrierControl.updateCarrierServiceFromValue(carrierServiceDetailValue, partyPK);

        if(carrierServiceDescription == null && description != null) {
            carrierControl.createCarrierServiceDescription(carrierService, getPreferredLanguage(), description, partyPK);
        } else {
            if(carrierServiceDescription != null && description == null) {
                carrierControl.deleteCarrierServiceDescription(carrierServiceDescription, partyPK);
            } else {
                if(carrierServiceDescription != null && description != null) {
                    var carrierServiceDescriptionValue = carrierControl.getCarrierServiceDescriptionValue(carrierServiceDescription);

                    carrierServiceDescriptionValue.setDescription(description);
                    carrierControl.updateCarrierServiceDescriptionFromValue(carrierServiceDescriptionValue, partyPK);
                }
            }
        }
    }
    
}
