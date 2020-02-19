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

package com.echothree.control.user.carrier.server.command;

import com.echothree.control.user.carrier.common.edit.CarrierEditFactory;
import com.echothree.control.user.carrier.common.edit.CarrierServiceEdit;
import com.echothree.control.user.carrier.common.form.EditCarrierServiceForm;
import com.echothree.control.user.carrier.common.result.CarrierResultFactory;
import com.echothree.control.user.carrier.common.result.EditCarrierServiceResult;
import com.echothree.control.user.carrier.common.spec.CarrierServiceSpec;
import com.echothree.model.control.carrier.server.CarrierControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.selector.common.SelectorConstants;
import com.echothree.model.control.selector.server.SelectorControl;
import com.echothree.model.data.carrier.server.entity.Carrier;
import com.echothree.model.data.carrier.server.entity.CarrierService;
import com.echothree.model.data.carrier.server.entity.CarrierServiceDescription;
import com.echothree.model.data.carrier.server.entity.CarrierServiceDetail;
import com.echothree.model.data.carrier.server.value.CarrierServiceDescriptionValue;
import com.echothree.model.data.carrier.server.value.CarrierServiceDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.selector.server.entity.SelectorKind;
import com.echothree.model.data.selector.server.entity.SelectorType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
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
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditCarrierServiceCommand */
    public EditCarrierServiceCommand(UserVisitPK userVisitPK, EditCarrierServiceForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
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
        var carrierControl = (CarrierControl)Session.getModelController(CarrierControl.class);
        CarrierService carrierService = null;
        String carrierName = spec.getCarrierName();
        Carrier carrier = carrierControl.getCarrierByName(carrierName);

        if(carrier != null) {
            String carrierServiceName = spec.getCarrierServiceName();
            
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
        var carrierControl = (CarrierControl)Session.getModelController(CarrierControl.class);

        result.setCarrierService(carrierControl.getCarrierServiceTransfer(getUserVisit(), carrierService));
    }

    Selector geoCodeSelector;
    Selector itemSelector;

    @Override
    public void doLock(CarrierServiceEdit edit, CarrierService carrierService) {
        var carrierControl = (CarrierControl)Session.getModelController(CarrierControl.class);
        CarrierServiceDescription carrierServiceDescription = carrierControl.getCarrierServiceDescription(carrierService, getPreferredLanguage());
        CarrierServiceDetail carrierServiceDetail = carrierService.getLastDetail();

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
        var carrierControl = (CarrierControl)Session.getModelController(CarrierControl.class);
        String carrierServiceName = edit.getCarrierServiceName();
        CarrierService duplicateCarrierService = carrierControl.getCarrierServiceByName(carrierParty, carrierServiceName);

        if(duplicateCarrierService == null || carrierService.equals(duplicateCarrierService)) {
            String geoCodeSelectorName = edit.getGeoCodeSelectorName();

            if(geoCodeSelectorName != null) {
                var selectorControl = (SelectorControl)Session.getModelController(SelectorControl.class);
                SelectorKind selectorKind = selectorControl.getSelectorKindByName(SelectorConstants.SelectorKind_POSTAL_ADDRESS);

                if(selectorKind != null) {
                    SelectorType selectorType = selectorControl.getSelectorTypeByName(selectorKind,
                            SelectorConstants.SelectorType_CARRIER);

                    if(selectorType != null) {
                        geoCodeSelector = selectorControl.getSelectorByName(selectorType, geoCodeSelectorName);
                    } else {
                        addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), SelectorConstants.SelectorKind_POSTAL_ADDRESS,
                                SelectorConstants.SelectorType_CARRIER_SERVICE);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), SelectorConstants.SelectorKind_POSTAL_ADDRESS);
                }
            }

            if(geoCodeSelectorName == null || geoCodeSelector != null) {
                String itemSelectorName = edit.getItemSelectorName();

                if(itemSelectorName != null) {
                    var selectorControl = (SelectorControl)Session.getModelController(SelectorControl.class);
                    SelectorKind selectorKind = selectorControl.getSelectorKindByName(SelectorConstants.SelectorKind_ITEM);

                    if(selectorKind != null) {
                        SelectorType selectorType = selectorControl.getSelectorTypeByName(selectorKind,
                                SelectorConstants.SelectorType_CARRIER);

                        if(selectorType != null) {
                            itemSelector = selectorControl.getSelectorByName(selectorType, itemSelectorName);
                        } else {
                            addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), SelectorConstants.SelectorKind_ITEM,
                                    SelectorConstants.SelectorType_CARRIER_SERVICE);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), SelectorConstants.SelectorKind_ITEM);
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
        var carrierControl = (CarrierControl)Session.getModelController(CarrierControl.class);
        PartyPK partyPK = getPartyPK();
        CarrierServiceDetailValue carrierServiceDetailValue = carrierControl.getCarrierServiceDetailValueForUpdate(carrierService);
        CarrierServiceDescription carrierServiceDescription = carrierControl.getCarrierServiceDescriptionForUpdate(carrierService, getPreferredLanguage());
        String description = edit.getDescription();

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
                    CarrierServiceDescriptionValue carrierServiceDescriptionValue = carrierControl.getCarrierServiceDescriptionValue(carrierServiceDescription);

                    carrierServiceDescriptionValue.setDescription(description);
                    carrierControl.updateCarrierServiceDescriptionFromValue(carrierServiceDescriptionValue, partyPK);
                }
            }
        }
    }
    
}
