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
import com.echothree.control.user.carrier.common.edit.CarrierOptionEdit;
import com.echothree.control.user.carrier.common.form.EditCarrierOptionForm;
import com.echothree.control.user.carrier.common.result.CarrierResultFactory;
import com.echothree.control.user.carrier.common.result.EditCarrierOptionResult;
import com.echothree.control.user.carrier.common.spec.CarrierOptionSpec;
import com.echothree.model.control.carrier.server.CarrierControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.selector.common.SelectorConstants;
import com.echothree.model.control.selector.server.SelectorControl;
import com.echothree.model.data.carrier.server.entity.Carrier;
import com.echothree.model.data.carrier.server.entity.CarrierOption;
import com.echothree.model.data.carrier.server.entity.CarrierOptionDescription;
import com.echothree.model.data.carrier.server.entity.CarrierOptionDetail;
import com.echothree.model.data.carrier.server.value.CarrierOptionDescriptionValue;
import com.echothree.model.data.carrier.server.value.CarrierOptionDetailValue;
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

public class EditCarrierOptionCommand
        extends BaseAbstractEditCommand<CarrierOptionSpec, CarrierOptionEdit, EditCarrierOptionResult, CarrierOption, CarrierOption> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.CarrierOption.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CarrierName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CarrierOptionName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CarrierOptionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsRecommended", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("IsRequired", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("RecommendedGeoCodeSelectorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("RequiredGeoCodeSelectorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("RecommendedItemSelectorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("RequiredItemSelectorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("RecommendedOrderSelectorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("RequiredOrderSelectorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("RecommendedShipmentSelectorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("RequiredShipmentSelectorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditCarrierOptionCommand */
    public EditCarrierOptionCommand(UserVisitPK userVisitPK, EditCarrierOptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditCarrierOptionResult getResult() {
        return CarrierResultFactory.getEditCarrierOptionResult();
    }

    @Override
    public CarrierOptionEdit getEdit() {
        return CarrierEditFactory.getCarrierOptionEdit();
    }

    Party carrierParty;

    @Override
    public CarrierOption getEntity(EditCarrierOptionResult result) {
        var carrierControl = (CarrierControl)Session.getModelController(CarrierControl.class);
        CarrierOption carrierOption = null;
        String carrierName = spec.getCarrierName();
        Carrier carrier = carrierControl.getCarrierByName(carrierName);

        if(carrier != null) {
            String carrierOptionName = spec.getCarrierOptionName();

            carrierParty = carrier.getParty();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                carrierOption = carrierControl.getCarrierOptionByName(carrierParty, carrierOptionName);
            } else { // EditMode.UPDATE
                carrierOption = carrierControl.getCarrierOptionByNameForUpdate(carrierParty, carrierOptionName);
            }

            if(carrierOption != null) {
                result.setCarrierOption(carrierControl.getCarrierOptionTransfer(getUserVisit(), carrierOption));
            } else {
                addExecutionError(ExecutionErrors.UnknownCarrierOptionName.name(), carrierName, carrierOptionName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCarrierName.name(), carrierName);
        }

        return carrierOption;
    }

    @Override
    public CarrierOption getLockEntity(CarrierOption carrierOption) {
        return carrierOption;
    }

    @Override
    public void fillInResult(EditCarrierOptionResult result, CarrierOption carrierOption) {
        var carrierControl = (CarrierControl)Session.getModelController(CarrierControl.class);

        result.setCarrierOption(carrierControl.getCarrierOptionTransfer(getUserVisit(), carrierOption));
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
    public void doLock(CarrierOptionEdit edit, CarrierOption carrierOption) {
        var carrierControl = (CarrierControl)Session.getModelController(CarrierControl.class);
        CarrierOptionDescription carrierOptionDescription = carrierControl.getCarrierOptionDescription(carrierOption, getPreferredLanguage());
        CarrierOptionDetail carrierOptionDetail = carrierOption.getLastDetail();

        recommendedGeoCodeSelector = carrierOptionDetail.getRecommendedGeoCodeSelector();
        requiredGeoCodeSelector = carrierOptionDetail.getRequiredGeoCodeSelector();
        recommendedItemSelector = carrierOptionDetail.getRecommendedItemSelector();
        requiredItemSelector = carrierOptionDetail.getRequiredItemSelector();
        recommendedOrderSelector = carrierOptionDetail.getRecommendedOrderSelector();
        requiredOrderSelector = carrierOptionDetail.getRequiredOrderSelector();
        recommendedShipmentSelector = carrierOptionDetail.getRecommendedShipmentSelector();
        requiredShipmentSelector = carrierOptionDetail.getRequiredShipmentSelector();

        edit.setCarrierOptionName(carrierOptionDetail.getCarrierOptionName());
        edit.setIsRecommended(carrierOptionDetail.getIsRecommended().toString());
        edit.setIsRequired(carrierOptionDetail.getIsRequired().toString());
        edit.setRecommendedGeoCodeSelectorName(recommendedGeoCodeSelector == null? null: recommendedGeoCodeSelector.getLastDetail().getSelectorName());
        edit.setRequiredGeoCodeSelectorName(requiredGeoCodeSelector == null? null: requiredGeoCodeSelector.getLastDetail().getSelectorName());
        edit.setRecommendedItemSelectorName(recommendedItemSelector == null? null: recommendedItemSelector.getLastDetail().getSelectorName());
        edit.setRequiredItemSelectorName(requiredItemSelector == null? null: requiredItemSelector.getLastDetail().getSelectorName());
        edit.setRecommendedOrderSelectorName(recommendedOrderSelector == null? null: recommendedOrderSelector.getLastDetail().getSelectorName());
        edit.setRequiredOrderSelectorName(requiredOrderSelector == null? null: requiredOrderSelector.getLastDetail().getSelectorName());
        edit.setRecommendedShipmentSelectorName(recommendedShipmentSelector == null? null: recommendedShipmentSelector.getLastDetail().getSelectorName());
        edit.setRequiredShipmentSelectorName(requiredShipmentSelector == null? null: requiredShipmentSelector.getLastDetail().getSelectorName());
        edit.setIsDefault(carrierOptionDetail.getIsDefault().toString());
        edit.setSortOrder(carrierOptionDetail.getSortOrder().toString());

        if(carrierOptionDescription != null) {
            edit.setDescription(carrierOptionDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(CarrierOption carrierOption) {
        var carrierControl = (CarrierControl)Session.getModelController(CarrierControl.class);
        String carrierOptionName = edit.getCarrierOptionName();
        CarrierOption duplicateCarrierOption = carrierControl.getCarrierOptionByName(carrierParty, carrierOptionName);

        if(duplicateCarrierOption == null || carrierOption.equals(duplicateCarrierOption)) {
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
        } else {
            addExecutionError(ExecutionErrors.DuplicateCarrierOptionName.name(), carrierOptionName);
        }
    }

    @Override
    public void doUpdate(CarrierOption carrierOption) {
        var carrierControl = (CarrierControl)Session.getModelController(CarrierControl.class);
        var partyPK = getPartyPK();
        CarrierOptionDetailValue carrierOptionDetailValue = carrierControl.getCarrierOptionDetailValueForUpdate(carrierOption);
        CarrierOptionDescription carrierOptionDescription = carrierControl.getCarrierOptionDescriptionForUpdate(carrierOption, getPreferredLanguage());
        String description = edit.getDescription();

        carrierOptionDetailValue.setCarrierOptionName(edit.getCarrierOptionName());
        carrierOptionDetailValue.setIsRecommended(Boolean.valueOf(edit.getIsRecommended()));
        carrierOptionDetailValue.setIsRequired(Boolean.valueOf(edit.getIsRequired()));
        carrierOptionDetailValue.setRecommendedGeoCodeSelectorPK(recommendedGeoCodeSelector.getPrimaryKey());
        carrierOptionDetailValue.setRequiredGeoCodeSelectorPK(requiredGeoCodeSelector.getPrimaryKey());
        carrierOptionDetailValue.setRecommendedItemSelectorPK(recommendedItemSelector.getPrimaryKey());
        carrierOptionDetailValue.setRequiredItemSelectorPK(requiredItemSelector.getPrimaryKey());
        carrierOptionDetailValue.setRecommendedOrderSelectorPK(recommendedOrderSelector.getPrimaryKey());
        carrierOptionDetailValue.setRequiredOrderSelectorPK(requiredOrderSelector.getPrimaryKey());
        carrierOptionDetailValue.setRecommendedShipmentSelectorPK(recommendedShipmentSelector.getPrimaryKey());
        carrierOptionDetailValue.setRequiredShipmentSelectorPK(requiredShipmentSelector.getPrimaryKey());
        carrierOptionDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        carrierOptionDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        carrierControl.updateCarrierOptionFromValue(carrierOptionDetailValue, partyPK);

        if(carrierOptionDescription == null && description != null) {
            carrierControl.createCarrierOptionDescription(carrierOption, getPreferredLanguage(), description, partyPK);
        } else {
            if(carrierOptionDescription != null && description == null) {
                carrierControl.deleteCarrierOptionDescription(carrierOptionDescription, partyPK);
            } else {
                if(carrierOptionDescription != null && description != null) {
                    CarrierOptionDescriptionValue carrierOptionDescriptionValue = carrierControl.getCarrierOptionDescriptionValue(carrierOptionDescription);

                    carrierOptionDescriptionValue.setDescription(description);
                    carrierControl.updateCarrierOptionDescriptionFromValue(carrierOptionDescriptionValue, partyPK);
                }
            }
        }
    }
    
}
