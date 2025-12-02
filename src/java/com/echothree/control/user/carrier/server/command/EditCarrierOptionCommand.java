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
import com.echothree.control.user.carrier.common.edit.CarrierOptionEdit;
import com.echothree.control.user.carrier.common.form.EditCarrierOptionForm;
import com.echothree.control.user.carrier.common.result.CarrierResultFactory;
import com.echothree.control.user.carrier.common.result.EditCarrierOptionResult;
import com.echothree.control.user.carrier.common.spec.CarrierOptionSpec;
import com.echothree.model.control.carrier.server.control.CarrierControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.selector.common.SelectorKinds;
import com.echothree.model.control.selector.common.SelectorTypes;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.carrier.server.entity.CarrierOption;
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
import javax.enterprise.context.Dependent;

@Dependent
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
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditCarrierOptionCommand */
    public EditCarrierOptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
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
        var carrierControl = Session.getModelController(CarrierControl.class);
        CarrierOption carrierOption = null;
        var carrierName = spec.getCarrierName();
        var carrier = carrierControl.getCarrierByName(carrierName);

        if(carrier != null) {
            var carrierOptionName = spec.getCarrierOptionName();

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
        var carrierControl = Session.getModelController(CarrierControl.class);

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
        var carrierControl = Session.getModelController(CarrierControl.class);
        var carrierOptionDescription = carrierControl.getCarrierOptionDescription(carrierOption, getPreferredLanguage());
        var carrierOptionDetail = carrierOption.getLastDetail();

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
        var carrierControl = Session.getModelController(CarrierControl.class);
        var carrierOptionName = edit.getCarrierOptionName();
        var duplicateCarrierOption = carrierControl.getCarrierOptionByName(carrierParty, carrierOptionName);

        if(duplicateCarrierOption == null || carrierOption.equals(duplicateCarrierOption)) {
            var selectorControl = Session.getModelController(SelectorControl.class);
            var selectorKind = selectorControl.getSelectorKindByName(SelectorKinds.POSTAL_ADDRESS.name());

            if(selectorKind != null) {
                var selectorType = selectorControl.getSelectorTypeByName(selectorKind, SelectorTypes.CARRIER_OPTION.name());

                if(selectorType != null) {
                    var recommendedGeoCodeSelectorName = edit.getRecommendedGeoCodeSelectorName();

                    if(recommendedGeoCodeSelectorName != null) {
                        recommendedGeoCodeSelector = selectorControl.getSelectorByName(selectorType, recommendedGeoCodeSelectorName);
                    }

                    if(recommendedGeoCodeSelectorName == null || recommendedGeoCodeSelector != null) {
                        var requiredGeoCodeSelectorName = edit.getRequiredGeoCodeSelectorName();

                        if(requiredGeoCodeSelectorName != null) {
                            requiredGeoCodeSelector = selectorControl.getSelectorByName(selectorType, requiredGeoCodeSelectorName);
                        }

                        if(requiredGeoCodeSelectorName == null || requiredGeoCodeSelector != null) {
                            selectorKind = selectorControl.getSelectorKindByName(SelectorKinds.ITEM.name());

                            if(selectorKind != null) {
                                selectorType = selectorControl.getSelectorTypeByName(selectorKind, SelectorTypes.CARRIER_OPTION.name());

                                if(selectorType != null) {
                                    var recommendedItemSelectorName = edit.getRecommendedItemSelectorName();

                                    if(recommendedItemSelectorName != null) {
                                        recommendedItemSelector = selectorControl.getSelectorByName(selectorType, recommendedItemSelectorName);
                                    }

                                    if(recommendedItemSelectorName == null || recommendedItemSelector != null) {
                                        var requiredItemSelectorName = edit.getRequiredItemSelectorName();

                                        if(requiredItemSelectorName != null) {
                                            requiredItemSelector = selectorControl.getSelectorByName(selectorType, requiredItemSelectorName);
                                        }

                                        if(requiredItemSelectorName == null || requiredItemSelector != null) {
                                            selectorKind = selectorControl.getSelectorKindByName(SelectorKinds.ORDER.name());

                                            if(selectorKind != null) {
                                                selectorType = selectorControl.getSelectorTypeByName(selectorKind, SelectorTypes.CARRIER_OPTION.name());

                                                if(selectorType != null) {
                                                    var recommendedOrderSelectorName = edit.getRecommendedOrderSelectorName();

                                                    if(recommendedOrderSelectorName != null) {
                                                        recommendedOrderSelector = selectorControl.getSelectorByName(selectorType, recommendedOrderSelectorName);
                                                    }

                                                    if(recommendedOrderSelectorName == null || recommendedOrderSelector != null) {
                                                        var requiredOrderSelectorName = edit.getRequiredOrderSelectorName();

                                                        if(requiredOrderSelectorName != null) {
                                                            requiredOrderSelector = selectorControl.getSelectorByName(selectorType, requiredOrderSelectorName);
                                                        }

                                                        if(requiredOrderSelectorName == null || requiredOrderSelector != null) {
                                                            selectorKind = selectorControl.getSelectorKindByName(SelectorKinds.SHIPMENT.name());

                                                            if(selectorKind != null) {
                                                                selectorType = selectorControl.getSelectorTypeByName(selectorKind, SelectorTypes.CARRIER_OPTION.name());

                                                                if(selectorType != null) {
                                                                    var recommendedShipmentSelectorName = edit.getRecommendedShipmentSelectorName();

                                                                    if(recommendedShipmentSelectorName != null) {
                                                                        recommendedShipmentSelector = selectorControl.getSelectorByName(selectorType, recommendedShipmentSelectorName);
                                                                    }

                                                                    if(recommendedShipmentSelectorName == null || recommendedShipmentSelector != null) {
                                                                        var requiredShipmentSelectorName = edit.getRequiredShipmentSelectorName();

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
                                                                            SelectorKinds.SHIPMENT.name(),
                                                                            SelectorTypes.CARRIER_OPTION.name());
                                                                }
                                                            } else {
                                                                addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), SelectorKinds.SHIPMENT.name());
                                                            }
                                                        } else {
                                                            addExecutionError(ExecutionErrors.UnknownRequiredOrderSelectorName.name(), requiredOrderSelectorName);
                                                        }
                                                    } else {
                                                        addExecutionError(ExecutionErrors.UnknownRecommendedOrderSelectorName.name(), recommendedOrderSelectorName);
                                                    }
                                                } else {
                                                    addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(),
                                                            SelectorKinds.ORDER.name(), SelectorTypes.CARRIER_OPTION.name());
                                                }
                                            } else {
                                                addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), SelectorKinds.ORDER.name());
                                            }
                                        } else {
                                            addExecutionError(ExecutionErrors.UnknownRequiredItemSelectorName.name(), requiredItemSelectorName);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownRecommendedItemSelectorName.name(), recommendedItemSelectorName);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), SelectorKinds.ITEM.name(),
                                            SelectorTypes.CARRIER_OPTION.name());
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), SelectorKinds.ITEM.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownRequiredGeoCodeSelectorName.name(), requiredGeoCodeSelectorName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownRecommendedGeoCodeSelectorName.name(), recommendedGeoCodeSelectorName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), SelectorKinds.POSTAL_ADDRESS.name(),
                            SelectorTypes.CARRIER_OPTION.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), SelectorKinds.POSTAL_ADDRESS.name());
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateCarrierOptionName.name(), carrierOptionName);
        }
    }

    @Override
    public void doUpdate(CarrierOption carrierOption) {
        var carrierControl = Session.getModelController(CarrierControl.class);
        var partyPK = getPartyPK();
        var carrierOptionDetailValue = carrierControl.getCarrierOptionDetailValueForUpdate(carrierOption);
        var carrierOptionDescription = carrierControl.getCarrierOptionDescriptionForUpdate(carrierOption, getPreferredLanguage());
        var description = edit.getDescription();

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
                    var carrierOptionDescriptionValue = carrierControl.getCarrierOptionDescriptionValue(carrierOptionDescription);

                    carrierOptionDescriptionValue.setDescription(description);
                    carrierControl.updateCarrierOptionDescriptionFromValue(carrierOptionDescriptionValue, partyPK);
                }
            }
        }
    }
    
}
