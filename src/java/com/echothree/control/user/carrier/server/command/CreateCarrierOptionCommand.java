// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.carrier.remote.form.CreateCarrierOptionForm;
import com.echothree.model.control.carrier.server.CarrierControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.selector.common.SelectorConstants;
import com.echothree.model.control.selector.server.SelectorControl;
import com.echothree.model.data.carrier.server.entity.Carrier;
import com.echothree.model.data.carrier.server.entity.CarrierOption;
import com.echothree.model.data.party.remote.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.selector.server.entity.SelectorKind;
import com.echothree.model.data.selector.server.entity.SelectorType;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateCarrierOptionCommand
        extends BaseSimpleCommand<CreateCarrierOptionForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.CarrierOption.name(), SecurityRoles.Create.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CarrierName", FieldType.ENTITY_NAME, true, null, null),
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
    
    /** Creates a new instance of CreateCarrierOptionCommand */
    public CreateCarrierOptionCommand(UserVisitPK userVisitPK, CreateCarrierOptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        CarrierControl carrierControl = (CarrierControl)Session.getModelController(CarrierControl.class);
        String carrierName = form.getCarrierName();
        Carrier carrier = carrierControl.getCarrierByName(carrierName);
        
        if(carrier != null) {
            Party carrierParty = carrier.getParty();
            String carrierOptionName = form.getCarrierOptionName();
            CarrierOption carrierOption = carrierControl.getCarrierOptionByName(carrierParty, carrierOptionName);
            
            if(carrierOption == null) {
                SelectorControl selectorControl = (SelectorControl)Session.getModelController(SelectorControl.class);
                SelectorKind selectorKind = selectorControl.getSelectorKindByName(SelectorConstants.SelectorKind_POSTAL_ADDRESS);

                if(selectorKind != null) {
                    SelectorType selectorType = selectorControl.getSelectorTypeByName(selectorKind, SelectorConstants.SelectorType_CARRIER_OPTION);

                    if(selectorType != null) {
                        String recommendedGeoCodeSelectorName = form.getRecommendedGeoCodeSelectorName();
                        Selector recommendedGeoCodeSelector = null;

                        if(recommendedGeoCodeSelectorName != null) {
                            recommendedGeoCodeSelector = selectorControl.getSelectorByName(selectorType, recommendedGeoCodeSelectorName);
                        }

                        if(recommendedGeoCodeSelectorName == null || recommendedGeoCodeSelector != null) {
                            String requiredGeoCodeSelectorName = form.getRequiredGeoCodeSelectorName();
                            Selector requiredGeoCodeSelector = null;

                            if(requiredGeoCodeSelectorName != null) {
                                requiredGeoCodeSelector = selectorControl.getSelectorByName(selectorType, requiredGeoCodeSelectorName);
                            }

                            if(requiredGeoCodeSelectorName == null || requiredGeoCodeSelector != null) {
                                selectorKind = selectorControl.getSelectorKindByName(SelectorConstants.SelectorKind_ITEM);

                                if(selectorKind != null) {
                                    selectorType = selectorControl.getSelectorTypeByName(selectorKind, SelectorConstants.SelectorType_CARRIER_OPTION);

                                    if(selectorType != null) {
                                        String recommendedItemSelectorName = form.getRecommendedItemSelectorName();
                                        Selector recommendedItemSelector = null;

                                        if(recommendedItemSelectorName != null) {
                                            recommendedItemSelector = selectorControl.getSelectorByName(selectorType, recommendedItemSelectorName);
                                        }

                                        if(recommendedItemSelectorName == null || recommendedItemSelector != null) {
                                            String requiredItemSelectorName = form.getRequiredItemSelectorName();
                                            Selector requiredItemSelector = null;

                                            if(requiredItemSelectorName != null) {
                                                requiredItemSelector = selectorControl.getSelectorByName(selectorType, requiredItemSelectorName);
                                            }

                                            if(requiredItemSelectorName == null || requiredItemSelector != null) {
                                                selectorKind = selectorControl.getSelectorKindByName(SelectorConstants.SelectorKind_ORDER);

                                                if(selectorKind != null) {
                                                    selectorType = selectorControl.getSelectorTypeByName(selectorKind, SelectorConstants.SelectorType_CARRIER_OPTION);

                                                    if(selectorType != null) {
                                                        String recommendedOrderSelectorName = form.getRecommendedOrderSelectorName();
                                                        Selector recommendedOrderSelector = null;

                                                        if(recommendedOrderSelectorName != null) {
                                                            recommendedOrderSelector = selectorControl.getSelectorByName(selectorType, recommendedOrderSelectorName);
                                                        }

                                                        if(recommendedOrderSelectorName == null || recommendedOrderSelector != null) {
                                                            String requiredOrderSelectorName = form.getRequiredOrderSelectorName();
                                                            Selector requiredOrderSelector = null;

                                                            if(requiredOrderSelectorName != null) {
                                                                requiredOrderSelector = selectorControl.getSelectorByName(selectorType, requiredOrderSelectorName);
                                                            }

                                                            if(requiredOrderSelectorName == null || requiredOrderSelector != null) {
                                                                selectorKind = selectorControl.getSelectorKindByName(SelectorConstants.SelectorKind_SHIPMENT);

                                                                if(selectorKind != null) {
                                                                    selectorType = selectorControl.getSelectorTypeByName(selectorKind, SelectorConstants.SelectorType_CARRIER_OPTION);

                                                                    if(selectorType != null) {
                                                                        String recommendedShipmentSelectorName = form.getRecommendedShipmentSelectorName();
                                                                        Selector recommendedShipmentSelector = null;

                                                                        if(recommendedShipmentSelectorName != null) {
                                                                            recommendedShipmentSelector = selectorControl.getSelectorByName(selectorType, recommendedShipmentSelectorName);
                                                                        }

                                                                        if(recommendedShipmentSelectorName == null || recommendedShipmentSelector != null) {
                                                                            String requiredShipmentSelectorName = form.getRequiredShipmentSelectorName();
                                                                            Selector requiredShipmentSelector = null;

                                                                            if(requiredShipmentSelectorName != null) {
                                                                                requiredShipmentSelector = selectorControl.getSelectorByName(selectorType, requiredShipmentSelectorName);
                                                                            }

                                                                            if(requiredShipmentSelectorName == null || requiredShipmentSelector != null) {
                                                                                PartyPK createdBy = getPartyPK();
                                                                                Boolean isRecommended = Boolean.valueOf(form.getIsRecommended());
                                                                                Boolean isRequired = Boolean.valueOf(form.getIsRequired());
                                                                                Boolean isDefault = Boolean.valueOf(form.getIsDefault());
                                                                                Integer sortOrder = Integer.valueOf(form.getSortOrder());
                                                                                String description = form.getDescription();

                                                                                carrierOption = carrierControl.createCarrierOption(carrierParty,
                                                                                        carrierOptionName, isRecommended, isRequired,
                                                                                        recommendedGeoCodeSelector, requiredGeoCodeSelector,
                                                                                        recommendedItemSelector, requiredItemSelector,
                                                                                        recommendedOrderSelector, requiredOrderSelector,
                                                                                        recommendedShipmentSelector, requiredShipmentSelector,
                                                                                        isDefault, sortOrder, createdBy);

                                                                                if(description != null) {
                                                                                    carrierControl.createCarrierOptionDescription(carrierOption,
                                                                                            getPreferredLanguage(), description, createdBy);
                                                                                }
                                                                            } else {
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
                                                                    addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(),
                                                                            SelectorConstants.SelectorKind_SHIPMENT);
                                                                }
                                                            } else {
                                                                addExecutionError(ExecutionErrors.UnknownRequiredOrderSelectorName.name(), requiredOrderSelectorName);
                                                            }
                                                        } else {
                                                            addExecutionError(ExecutionErrors.UnknownRecommendedOrderSelectorName.name(), recommendedOrderSelectorName);
                                                        }
                                                    } else {
                                                        addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), SelectorConstants.SelectorKind_ORDER,
                                                                SelectorConstants.SelectorType_CARRIER_OPTION);
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
                addExecutionError(ExecutionErrors.DuplicateCarrierOptionName.name(), carrierName, carrierOptionName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCarrierName.name(), carrierName);
        }
        
        return null;
    }
    
}
