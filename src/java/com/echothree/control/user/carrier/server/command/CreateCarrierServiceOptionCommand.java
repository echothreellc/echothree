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

import com.echothree.control.user.carrier.common.form.CreateCarrierServiceOptionForm;
import com.echothree.model.control.carrier.server.control.CarrierControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.selector.common.SelectorKinds;
import com.echothree.model.control.selector.common.SelectorTypes;
import com.echothree.model.control.selector.server.control.SelectorControl;
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
public class CreateCarrierServiceOptionCommand
        extends BaseSimpleCommand<CreateCarrierServiceOptionForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.CarrierServiceOption.name(), SecurityRoles.Create.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CarrierName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CarrierServiceName", FieldType.ENTITY_NAME, true, null, null),
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
                new FieldDefinition("RequiredShipmentSelectorName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreateCarrierServiceOptionCommand */
    public CreateCarrierServiceOptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var carrierControl = Session.getModelController(CarrierControl.class);
        var carrierName = form.getCarrierName();
        var carrier = carrierControl.getCarrierByName(carrierName);
        
        if(carrier != null) {
            var carrierParty = carrier.getParty();
            var carrierServiceName = form.getCarrierServiceName();
            var carrierService = carrierControl.getCarrierServiceByName(carrierParty, carrierServiceName);
            
            if(carrierService != null) {
                var carrierOptionName = form.getCarrierOptionName();
                var carrierOption = carrierControl.getCarrierOptionByName(carrierParty, carrierOptionName);
                
                if(carrierOption != null) {
                    var carrierServiceOption = carrierControl.getCarrierServiceOption(carrierService, carrierOption);
                    
                    if(carrierServiceOption == null) {
                        var selectorControl = Session.getModelController(SelectorControl.class);
                        var selectorKind = selectorControl.getSelectorKindByName(SelectorKinds.POSTAL_ADDRESS.name());

                        if(selectorKind != null) {
                            var selectorType = selectorControl.getSelectorTypeByName(selectorKind, SelectorTypes.CARRIER_SERVICE_OPTION.name());

                            if(selectorType != null) {
                                var recommendedGeoCodeSelectorName = form.getRecommendedGeoCodeSelectorName();
                                Selector recommendedGeoCodeSelector = null;

                                if(recommendedGeoCodeSelectorName != null) {
                                    recommendedGeoCodeSelector = selectorControl.getSelectorByName(selectorType, recommendedGeoCodeSelectorName);
                                }

                                if(recommendedGeoCodeSelectorName == null || recommendedGeoCodeSelector != null) {
                                    var requiredGeoCodeSelectorName = form.getRequiredGeoCodeSelectorName();
                                    Selector requiredGeoCodeSelector = null;

                                    if(requiredGeoCodeSelectorName != null) {
                                        requiredGeoCodeSelector = selectorControl.getSelectorByName(selectorType, requiredGeoCodeSelectorName);
                                    }

                                    if(requiredGeoCodeSelectorName == null || requiredGeoCodeSelector != null) {
                                        selectorKind = selectorControl.getSelectorKindByName(SelectorKinds.ITEM.name());

                                        if(selectorKind != null) {
                                            selectorType = selectorControl.getSelectorTypeByName(selectorKind, SelectorTypes.CARRIER_SERVICE_OPTION.name());

                                            if(selectorType != null) {
                                                var recommendedItemSelectorName = form.getRecommendedItemSelectorName();
                                                Selector recommendedItemSelector = null;

                                                if(recommendedItemSelectorName != null) {
                                                    recommendedItemSelector = selectorControl.getSelectorByName(selectorType, recommendedItemSelectorName);
                                                }

                                                if(recommendedItemSelectorName == null || recommendedItemSelector != null) {
                                                    var requiredItemSelectorName = form.getRequiredItemSelectorName();
                                                    Selector requiredItemSelector = null;

                                                    if(requiredItemSelectorName != null) {
                                                        requiredItemSelector = selectorControl.getSelectorByName(selectorType, requiredItemSelectorName);
                                                    }

                                                    if(requiredItemSelectorName == null || requiredItemSelector != null) {
                                                        selectorKind = selectorControl.getSelectorKindByName(SelectorKinds.ORDER.name());

                                                        if(selectorKind != null) {
                                                            selectorType = selectorControl.getSelectorTypeByName(selectorKind, SelectorTypes.CARRIER_SERVICE_OPTION.name());

                                                            if(selectorType != null) {
                                                                var recommendedOrderSelectorName = form.getRecommendedOrderSelectorName();
                                                                Selector recommendedOrderSelector = null;

                                                                if(recommendedOrderSelectorName != null) {
                                                                    recommendedOrderSelector = selectorControl.getSelectorByName(selectorType, recommendedOrderSelectorName);
                                                                }

                                                                if(recommendedOrderSelectorName == null || recommendedOrderSelector != null) {
                                                                    var requiredOrderSelectorName = form.getRequiredOrderSelectorName();
                                                                    Selector requiredOrderSelector = null;

                                                                    if(requiredOrderSelectorName != null) {
                                                                        requiredOrderSelector = selectorControl.getSelectorByName(selectorType, requiredOrderSelectorName);
                                                                    }

                                                                    if(requiredOrderSelectorName == null || requiredOrderSelector != null) {
                                                                        selectorKind = selectorControl.getSelectorKindByName(SelectorKinds.SHIPMENT.name());

                                                                        if(selectorKind != null) {
                                                                            selectorType = selectorControl.getSelectorTypeByName(selectorKind, SelectorTypes.CARRIER_SERVICE_OPTION.name());

                                                                            if(selectorType != null) {
                                                                                var recommendedShipmentSelectorName = form.getRecommendedShipmentSelectorName();
                                                                                Selector recommendedShipmentSelector = null;

                                                                                if(recommendedShipmentSelectorName != null) {
                                                                                    recommendedShipmentSelector = selectorControl.getSelectorByName(selectorType, recommendedShipmentSelectorName);
                                                                                }

                                                                                if(recommendedShipmentSelectorName == null || recommendedShipmentSelector != null) {
                                                                                    var requiredShipmentSelectorName = form.getRequiredShipmentSelectorName();
                                                                                    Selector requiredShipmentSelector = null;

                                                                                    if(requiredShipmentSelectorName != null) {
                                                                                        requiredShipmentSelector = selectorControl.getSelectorByName(selectorType, requiredShipmentSelectorName);
                                                                                    }

                                                                                    if(requiredShipmentSelectorName == null || requiredShipmentSelector != null) {
                                                                                        var isRecommended = Boolean.valueOf(form.getIsRecommended());
                                                                                        var isRequired = Boolean.valueOf(form.getIsRequired());

                                                                                        carrierControl.createCarrierServiceOption(carrierService,
                                                                                                carrierOption, isRecommended, isRequired,
                                                                                                recommendedGeoCodeSelector, requiredGeoCodeSelector,
                                                                                                recommendedItemSelector, requiredItemSelector,
                                                                                                recommendedOrderSelector, requiredOrderSelector,
                                                                                                recommendedShipmentSelector, requiredShipmentSelector,
                                                                                                getPartyPK());
                                                                                    } else {
                                                                                        addExecutionError(ExecutionErrors.UnknownRequiredShipmentSelectorName.name(), requiredShipmentSelectorName);
                                                                                    }
                                                                                } else {
                                                                                    addExecutionError(ExecutionErrors.UnknownRecommendedShipmentSelectorName.name(), recommendedShipmentSelectorName);
                                                                                }
                                                                            } else {
                                                                                addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(),
                                                                                        SelectorKinds.SHIPMENT.name(),
                                                                                        SelectorTypes.CARRIER_SERVICE_OPTION.name());
                                                                            }
                                                                        } else {
                                                                            addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(),
                                                                                    SelectorKinds.SHIPMENT.name());
                                                                        }
                                                                    } else {
                                                                        addExecutionError(ExecutionErrors.UnknownRequiredOrderSelectorName.name(), requiredOrderSelectorName);
                                                                    }
                                                                } else {
                                                                    addExecutionError(ExecutionErrors.UnknownRecommendedOrderSelectorName.name(), recommendedOrderSelectorName);
                                                                }
                                                            } else {
                                                                addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(),
                                                                        SelectorKinds.ORDER.name(),
                                                                        SelectorTypes.CARRIER_SERVICE_OPTION.name());
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
                                                        SelectorTypes.CARRIER_SERVICE_OPTION.name());
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
                        addExecutionError(ExecutionErrors.DuplicateCarrierServiceOption.name(), carrierName, carrierServiceName, carrierOptionName);
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
        
        return null;
    }
    
}
