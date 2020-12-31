// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.control.user.carrier.common.form.CreateCarrierForm;
import com.echothree.control.user.carrier.common.result.CarrierResultFactory;
import com.echothree.control.user.carrier.common.result.CreateCarrierResult;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.carrier.server.control.CarrierControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.selector.common.SelectorConstants;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.carrier.server.entity.Carrier;
import com.echothree.model.data.carrier.server.entity.CarrierType;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.selector.server.entity.SelectorKind;
import com.echothree.model.data.selector.server.entity.SelectorType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateCarrierCommand
        extends BaseSimpleCommand<CreateCarrierForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Carrier.name(), SecurityRoles.Create.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CarrierName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CarrierTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Name", FieldType.STRING, true, 1L, 60L),
                new FieldDefinition("PreferredLanguageIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PreferredCurrencyIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PreferredJavaTimeZoneName", FieldType.TIME_ZONE_NAME, false, null, null),
                new FieldDefinition("PreferredDateTimeFormatName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("GeoCodeSelectorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ItemSelectorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AccountValidationPattern", FieldType.REGULAR_EXPRESSION, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateCarrierCommand */
    public CreateCarrierCommand(UserVisitPK userVisitPK, CreateCarrierForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var carrierControl = Session.getModelController(CarrierControl.class);
        CreateCarrierResult result = CarrierResultFactory.getCreateCarrierResult();
        String carrierName = form.getCarrierName();
        Carrier carrier = carrierControl.getCarrierByName(carrierName);
        
        if(carrier == null) {
            String carrierTypeName = form.getCarrierTypeName();
            CarrierType carrierType = carrierControl.getCarrierTypeByName(carrierTypeName);
            
            if(carrierType != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                String preferredLanguageIsoName = form.getPreferredLanguageIsoName();
                Language preferredLanguage = preferredLanguageIsoName == null? null: partyControl.getLanguageByIsoName(preferredLanguageIsoName);
                
                if(preferredLanguageIsoName == null || (preferredLanguage != null)) {
                    String preferredJavaTimeZoneName = form.getPreferredJavaTimeZoneName();
                    TimeZone preferredTimeZone = preferredJavaTimeZoneName == null? null: partyControl.getTimeZoneByJavaName(preferredJavaTimeZoneName);
                    
                    if(preferredJavaTimeZoneName == null || (preferredTimeZone != null)) {
                        String preferredDateTimeFormatName = form.getPreferredDateTimeFormatName();
                        DateTimeFormat preferredDateTimeFormat = preferredDateTimeFormatName == null? null: partyControl.getDateTimeFormatByName(preferredDateTimeFormatName);
                        
                        if(preferredDateTimeFormatName == null || (preferredDateTimeFormat != null)) {
                            String preferredCurrencyIsoName = form.getPreferredCurrencyIsoName();
                            Currency preferredCurrency;
                            
                            if(preferredCurrencyIsoName == null)
                                preferredCurrency = null;
                            else {
                                var accountingControl = Session.getModelController(AccountingControl.class);
                                preferredCurrency = accountingControl.getCurrencyByIsoName(preferredCurrencyIsoName);
                            }
                            
                            if(preferredCurrencyIsoName == null || (preferredCurrency != null)) {
                                String geoCodeSelectorName = form.getGeoCodeSelectorName();
                                Selector geoCodeSelector = null;

                                if(geoCodeSelectorName != null) {
                                    var selectorControl = Session.getModelController(SelectorControl.class);
                                    SelectorKind selectorKind = selectorControl.getSelectorKindByName(SelectorConstants.SelectorKind_POSTAL_ADDRESS);

                                    if(selectorKind != null) {
                                        SelectorType selectorType = selectorControl.getSelectorTypeByName(selectorKind,
                                                SelectorConstants.SelectorType_CARRIER);

                                        if(selectorType != null) {
                                            geoCodeSelector = selectorControl.getSelectorByName(selectorType, geoCodeSelectorName);
                                        } else {
                                            addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), SelectorConstants.SelectorKind_POSTAL_ADDRESS,
                                                    SelectorConstants.SelectorType_CARRIER);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), SelectorConstants.SelectorKind_POSTAL_ADDRESS);
                                    }
                                }

                                if(geoCodeSelectorName == null || geoCodeSelector != null) {
                                    String itemSelectorName = form.getItemSelectorName();
                                    Selector itemSelector = null;

                                    if(itemSelectorName != null) {
                                        var selectorControl = Session.getModelController(SelectorControl.class);
                                        SelectorKind selectorKind = selectorControl.getSelectorKindByName(SelectorConstants.SelectorKind_ITEM);

                                        if(selectorKind != null) {
                                            SelectorType selectorType = selectorControl.getSelectorTypeByName(selectorKind,
                                                    SelectorConstants.SelectorType_CARRIER);

                                            if(selectorType != null) {
                                                itemSelector = selectorControl.getSelectorByName(selectorType, itemSelectorName);
                                            } else {
                                                addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), SelectorConstants.SelectorKind_ITEM,
                                                        SelectorConstants.SelectorType_CARRIER);
                                            }
                                        } else {
                                            addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), SelectorConstants.SelectorKind_ITEM);
                                        }
                                    }

                                    if(itemSelectorName == null || itemSelector != null) {
                                        PartyType partyType = partyControl.getPartyTypeByName(PartyTypes.CARRIER.name());
                                        BasePK createdBy = getPartyPK();
                                        String name = form.getName();
                                        String accountValidationPattern = form.getAccountValidationPattern();
                                        var isDefault = Boolean.valueOf(form.getIsDefault());
                                        var sortOrder = Integer.valueOf(form.getSortOrder());

                                        Party party = partyControl.createParty(null, partyType, preferredLanguage, preferredCurrency, preferredTimeZone,
                                                preferredDateTimeFormat, createdBy);
                                        partyControl.createPartyGroup(party, name, createdBy);
                                        carrier = carrierControl.createCarrier(party, carrierName, carrierType, geoCodeSelector, itemSelector,
                                                accountValidationPattern, isDefault, sortOrder, createdBy);
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownItemSelectorName.name(), itemSelectorName);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownGeoCodeSelectorName.name(), geoCodeSelectorName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownCurrencyIsoName.name(), preferredCurrencyIsoName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownDateTimeFormatName.name(), preferredDateTimeFormatName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownJavaTimeZoneName.name(), preferredJavaTimeZoneName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), preferredLanguageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCarrierTypeName.name(), carrierName, carrierTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateCarrierName.name(), carrierName);
        }
        
        if(carrier != null) {
            Party party = carrier.getParty();
            
            result.setEntityRef(party.getPrimaryKey().getEntityRef());
            result.setCarrierName(carrier.getCarrierName());
            result.setPartyName(party.getLastDetail().getPartyName());
        }
        
        return result;
    }
    
}
