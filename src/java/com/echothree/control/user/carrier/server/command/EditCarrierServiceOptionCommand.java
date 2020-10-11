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
import com.echothree.control.user.carrier.common.edit.CarrierServiceOptionEdit;
import com.echothree.control.user.carrier.common.form.EditCarrierServiceOptionForm;
import com.echothree.control.user.carrier.common.result.CarrierResultFactory;
import com.echothree.control.user.carrier.common.result.EditCarrierServiceOptionResult;
import com.echothree.control.user.carrier.common.spec.CarrierServiceOptionSpec;
import com.echothree.model.control.carrier.server.control.CarrierControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.selector.common.SelectorConstants;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.carrier.server.entity.Carrier;
import com.echothree.model.data.carrier.server.entity.CarrierOption;
import com.echothree.model.data.carrier.server.entity.CarrierService;
import com.echothree.model.data.carrier.server.entity.CarrierServiceOption;
import com.echothree.model.data.carrier.server.value.CarrierServiceOptionValue;
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

public class EditCarrierServiceOptionCommand
        extends BaseAbstractEditCommand<CarrierServiceOptionSpec, CarrierServiceOptionEdit, EditCarrierServiceOptionResult, CarrierServiceOption, CarrierServiceOption> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.CarrierServiceOption.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CarrierName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CarrierServiceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CarrierOptionName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("IsRecommended", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("IsRequired", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("RecommendedGeoCodeSelectorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("RequiredGeoCodeSelectorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("RecommendedItemSelectorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("RequiredItemSelectorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("RecommendedOrderSelectorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("RequiredOrderSelectorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("RecommendedShipmentSelectorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("RequiredShipmentSelectorName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of EditCarrierServiceOptionCommand */
    public EditCarrierServiceOptionCommand(UserVisitPK userVisitPK, EditCarrierServiceOptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditCarrierServiceOptionResult getResult() {
        return CarrierResultFactory.getEditCarrierServiceOptionResult();
    }

    @Override
    public CarrierServiceOptionEdit getEdit() {
        return CarrierEditFactory.getCarrierServiceOptionEdit();
    }

    @Override
    public CarrierServiceOption getEntity(EditCarrierServiceOptionResult result) {
        var carrierControl = (CarrierControl)Session.getModelController(CarrierControl.class);
        CarrierServiceOption carrierServiceOption = null;
        String carrierName = spec.getCarrierName();
        Carrier carrier = carrierControl.getCarrierByName(carrierName);

        if(carrier != null) {
            Party carrierParty = carrier.getParty();
            String carrierServiceName = spec.getCarrierServiceName();
            CarrierService carrierService = carrierControl.getCarrierServiceByName(carrierParty, carrierServiceName);

            if(carrierService != null) {
                String carrierOptionName = spec.getCarrierOptionName();
                CarrierOption carrierOption = carrierControl.getCarrierOptionByName(carrierParty, carrierOptionName);

                if(carrierOption != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        carrierServiceOption = carrierControl.getCarrierServiceOption(carrierService, carrierOption);
                    } else { // EditMode.UPDATE
                        carrierServiceOption = carrierControl.getCarrierServiceOptionForUpdate(carrierService, carrierOption);
                    }

                    if(carrierServiceOption != null) {
                        result.setCarrierServiceOption(carrierControl.getCarrierServiceOptionTransfer(getUserVisit(), carrierServiceOption));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownCarrierServiceOption.name(), carrierName, carrierService, carrierOptionName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownCarrierOptionName.name(), carrierName, carrierOptionName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCarrierServiceName.name(), carrierName, carrierServiceName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCarrierName.name(), carrierName);
        }

        return carrierServiceOption;
    }

    @Override
    public CarrierServiceOption getLockEntity(CarrierServiceOption carrierServiceOption) {
        return carrierServiceOption;
    }

    @Override
    public void fillInResult(EditCarrierServiceOptionResult result, CarrierServiceOption carrierServiceOption) {
        var carrierControl = (CarrierControl)Session.getModelController(CarrierControl.class);

        result.setCarrierServiceOption(carrierControl.getCarrierServiceOptionTransfer(getUserVisit(), carrierServiceOption));
    }

    Selector recommendedGeoCodeSelector;
    Selector requiredGeoCodeSelector;
    Selector recommendedItemSelector;
    Selector requiredItemSelector;
    Selector recommendedOrderSelector;
    Selector requiredOrderSelector;
    Selector recommendedShipmentSelector;
    Selector requiredShipmentSelector;

    @Override
    public void doLock(CarrierServiceOptionEdit edit, CarrierServiceOption carrierServiceOption) {
        recommendedGeoCodeSelector = carrierServiceOption.getRecommendedGeoCodeSelector();
        requiredGeoCodeSelector = carrierServiceOption.getRequiredGeoCodeSelector();
        recommendedItemSelector = carrierServiceOption.getRecommendedItemSelector();
        requiredItemSelector = carrierServiceOption.getRequiredItemSelector();
        recommendedOrderSelector = carrierServiceOption.getRecommendedOrderSelector();
        requiredOrderSelector = carrierServiceOption.getRequiredOrderSelector();
        recommendedShipmentSelector = carrierServiceOption.getRecommendedShipmentSelector();
        requiredShipmentSelector = carrierServiceOption.getRequiredShipmentSelector();

        edit.setIsRecommended(carrierServiceOption.getIsRecommended().toString());
        edit.setIsRequired(carrierServiceOption.getIsRequired().toString());
        edit.setRecommendedGeoCodeSelectorName(recommendedGeoCodeSelector == null? null: recommendedGeoCodeSelector.getLastDetail().getSelectorName());
        edit.setRequiredGeoCodeSelectorName(requiredGeoCodeSelector == null? null: requiredGeoCodeSelector.getLastDetail().getSelectorName());
        edit.setRecommendedItemSelectorName(recommendedItemSelector == null? null: recommendedItemSelector.getLastDetail().getSelectorName());
        edit.setRequiredItemSelectorName(requiredItemSelector == null? null: requiredItemSelector.getLastDetail().getSelectorName());
        edit.setRecommendedOrderSelectorName(recommendedOrderSelector == null? null: recommendedOrderSelector.getLastDetail().getSelectorName());
        edit.setRequiredOrderSelectorName(requiredOrderSelector == null? null: requiredOrderSelector.getLastDetail().getSelectorName());
        edit.setRecommendedShipmentSelectorName(recommendedShipmentSelector == null? null: recommendedShipmentSelector.getLastDetail().getSelectorName());
        edit.setRequiredShipmentSelectorName(requiredShipmentSelector == null? null: requiredShipmentSelector.getLastDetail().getSelectorName());
    }

    @Override
    public void canUpdate(CarrierServiceOption carrierServiceOption) {
        var selectorControl = (SelectorControl)Session.getModelController(SelectorControl.class);
        SelectorKind selectorKind = selectorControl.getSelectorKindByName(SelectorConstants.SelectorKind_POSTAL_ADDRESS);

        if(selectorKind != null) {
            SelectorType selectorType = selectorControl.getSelectorTypeByName(selectorKind, SelectorConstants.SelectorType_CARRIER_OPTION);

            if(selectorType != null) {
                String recommendedGeoCodeSelectorName = edit.getRecommendedGeoCodeSelectorName();

                if(recommendedGeoCodeSelectorName != null) {
                    recommendedGeoCodeSelector = selectorControl.getSelectorByName(selectorType, recommendedGeoCodeSelectorName);
                }

                if(recommendedGeoCodeSelectorName == null || recommendedGeoCodeSelector != null) {
                    String requiredGeoCodeSelectorName = edit.getRequiredGeoCodeSelectorName();

                    if(requiredGeoCodeSelectorName != null) {
                        requiredGeoCodeSelector = selectorControl.getSelectorByName(selectorType, requiredGeoCodeSelectorName);
                    }

                    if(requiredGeoCodeSelectorName == null || requiredGeoCodeSelector != null) {
                        selectorKind = selectorControl.getSelectorKindByName(SelectorConstants.SelectorKind_ITEM);

                        if(selectorKind != null) {
                            selectorType = selectorControl.getSelectorTypeByName(selectorKind, SelectorConstants.SelectorType_CARRIER_OPTION);

                            if(selectorType != null) {
                                String recommendedItemSelectorName = edit.getRecommendedItemSelectorName();

                                if(recommendedItemSelectorName != null) {
                                    recommendedItemSelector = selectorControl.getSelectorByName(selectorType, recommendedItemSelectorName);
                                }

                                if(recommendedItemSelectorName == null || recommendedItemSelector != null) {
                                    String requiredItemSelectorName = edit.getRequiredItemSelectorName();

                                    if(requiredItemSelectorName != null) {
                                        requiredItemSelector = selectorControl.getSelectorByName(selectorType, requiredItemSelectorName);
                                    }

                                    if(requiredItemSelectorName == null || requiredItemSelector != null) {
                                        selectorKind = selectorControl.getSelectorKindByName(SelectorConstants.SelectorKind_ORDER);

                                        if(selectorKind != null) {
                                            selectorType = selectorControl.getSelectorTypeByName(selectorKind, SelectorConstants.SelectorType_CARRIER_OPTION);

                                            if(selectorType != null) {
                                                String recommendedOrderSelectorName = edit.getRecommendedOrderSelectorName();

                                                if(recommendedOrderSelectorName != null) {
                                                    recommendedOrderSelector = selectorControl.getSelectorByName(selectorType, recommendedOrderSelectorName);
                                                }

                                                if(recommendedOrderSelectorName == null || recommendedOrderSelector != null) {
                                                    String requiredOrderSelectorName = edit.getRequiredOrderSelectorName();

                                                    if(requiredOrderSelectorName != null) {
                                                        requiredOrderSelector = selectorControl.getSelectorByName(selectorType, requiredOrderSelectorName);
                                                    }

                                                    if(requiredOrderSelectorName == null || requiredOrderSelector != null) {
                                                        selectorKind = selectorControl.getSelectorKindByName(SelectorConstants.SelectorKind_SHIPMENT);

                                                        if(selectorKind != null) {
                                                            selectorType = selectorControl.getSelectorTypeByName(selectorKind, SelectorConstants.SelectorType_CARRIER_OPTION);

                                                            if(selectorType != null) {
                                                                String recommendedShipmentSelectorName = edit.getRecommendedShipmentSelectorName();

                                                                if(recommendedShipmentSelectorName != null) {
                                                                    recommendedShipmentSelector = selectorControl.getSelectorByName(selectorType, recommendedShipmentSelectorName);
                                                                }

                                                                if(recommendedShipmentSelectorName == null || recommendedShipmentSelector != null) {
                                                                    String requiredShipmentSelectorName = edit.getRequiredShipmentSelectorName();

                                                                    if(requiredShipmentSelectorName != null) {
                                                                        requiredShipmentSelector = selectorControl.getSelectorByName(selectorType, requiredShipmentSelectorName);
                                                                    }

                                                                    if(requiredShipmentSelectorName != null && requiredShipmentSelector == null) {
                                                                        addExecutionError(ExecutionErrors.UnknownRequiredShipmentSelectorName.name(), requiredShipmentSelectorName);
                                                                    }
                                                                } else {
                                                                    addExecutionError(ExecutionErrors.UnknownRecommendedShipmentSelectorName.name(), recommendedShipmentSelectorName);
                                                                }
                                                            } else {
                                                                addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(),
                                                                        SelectorConstants.SelectorKind_SHIPMENT,
                                                                        SelectorConstants.SelectorType_CARRIER_OPTION);
                                                            }
                                                        } else {
                                                            addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), SelectorConstants.SelectorKind_SHIPMENT);
                                                        }
                                                    } else {
                                                        addExecutionError(ExecutionErrors.UnknownRequiredOrderSelectorName.name(), requiredOrderSelectorName);
                                                    }
                                                } else {
                                                    addExecutionError(ExecutionErrors.UnknownRecommendedOrderSelectorName.name(), recommendedOrderSelectorName);
                                                }
                                            } else {
                                                addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(),
                                                        SelectorConstants.SelectorKind_ORDER, SelectorConstants.SelectorType_CARRIER_OPTION);
                                            }
                                        } else {
                                            addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), SelectorConstants.SelectorKind_ORDER);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownRequiredItemSelectorName.name(), requiredItemSelectorName);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownRecommendedItemSelectorName.name(), recommendedItemSelectorName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), SelectorConstants.SelectorKind_ITEM,
                                        SelectorConstants.SelectorType_CARRIER_OPTION);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), SelectorConstants.SelectorKind_ITEM);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownRequiredGeoCodeSelectorName.name(), requiredGeoCodeSelectorName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownRecommendedGeoCodeSelectorName.name(), recommendedGeoCodeSelectorName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), SelectorConstants.SelectorKind_POSTAL_ADDRESS,
                        SelectorConstants.SelectorType_CARRIER_OPTION);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), SelectorConstants.SelectorKind_POSTAL_ADDRESS);
        }
    }

    @Override
    public void doUpdate(CarrierServiceOption carrierServiceOption) {
        var carrierControl = (CarrierControl)Session.getModelController(CarrierControl.class);
        var partyPK = getPartyPK();
        CarrierServiceOptionValue carrierServiceOptionValue = carrierControl.getCarrierServiceOptionValue(carrierServiceOption);
    
        carrierServiceOptionValue.setIsRecommended(Boolean.valueOf(edit.getIsRecommended()));
        carrierServiceOptionValue.setIsRequired(Boolean.valueOf(edit.getIsRequired()));
        carrierServiceOptionValue.setRecommendedGeoCodeSelectorPK(recommendedGeoCodeSelector.getPrimaryKey());
        carrierServiceOptionValue.setRequiredGeoCodeSelectorPK(requiredGeoCodeSelector.getPrimaryKey());
        carrierServiceOptionValue.setRecommendedItemSelectorPK(recommendedItemSelector.getPrimaryKey());
        carrierServiceOptionValue.setRequiredItemSelectorPK(requiredItemSelector.getPrimaryKey());
        carrierServiceOptionValue.setRecommendedOrderSelectorPK(recommendedOrderSelector.getPrimaryKey());
        carrierServiceOptionValue.setRequiredOrderSelectorPK(requiredOrderSelector.getPrimaryKey());
        carrierServiceOptionValue.setRecommendedShipmentSelectorPK(recommendedShipmentSelector.getPrimaryKey());
        carrierServiceOptionValue.setRequiredShipmentSelectorPK(requiredShipmentSelector.getPrimaryKey());

        carrierControl.updateCarrierServiceOptionFromValue(carrierServiceOptionValue, partyPK);
    }
    
}
