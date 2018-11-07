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

package com.echothree.control.user.warehouse.server.command;

import com.echothree.control.user.warehouse.common.form.CreateWarehouseForm;
import com.echothree.control.user.warehouse.common.result.CreateWarehouseResult;
import com.echothree.control.user.warehouse.common.result.WarehouseResultFactory;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.printer.common.PrinterConstants;
import com.echothree.model.control.printer.server.PrinterControl;
import com.echothree.model.control.warehouse.server.WarehouseControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.printer.server.entity.PrinterGroup;
import com.echothree.model.data.printer.server.entity.PrinterGroupUseType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.warehouse.server.entity.Warehouse;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateWarehouseCommand
        extends BaseSimpleCommand<CreateWarehouseForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Name", FieldType.STRING, false, 1L, 60L),
                new FieldDefinition("PreferredLanguageIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PreferredCurrencyIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PreferredJavaTimeZoneName", FieldType.TIME_ZONE_NAME, false, null, null),
                new FieldDefinition("PreferredDateTimeFormatName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("InventoryMovePrinterGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PicklistPrinterGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PackingListPrinterGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ShippingManifestPrinterGroupName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateWarehouseCommand */
    public CreateWarehouseCommand(UserVisitPK userVisitPK, CreateWarehouseForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        WarehouseControl warehouseControl = (WarehouseControl)Session.getModelController(WarehouseControl.class);
        CreateWarehouseResult result = WarehouseResultFactory.getCreateWarehouseResult();
        String warehouseName = form.getWarehouseName();
        Warehouse warehouse = warehouseControl.getWarehouseByName(warehouseName);
        
        if(warehouse == null) {
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
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
                        
                        if(preferredCurrencyIsoName == null) {
                            preferredCurrency = null;
                        } else {
                            AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
                            
                            preferredCurrency = accountingControl.getCurrencyByIsoName(preferredCurrencyIsoName);
                        }
                        
                        if(preferredCurrencyIsoName == null || (preferredCurrency != null)) {
                            PrinterControl printerControl = (PrinterControl)Session.getModelController(PrinterControl.class);
                            String inventoryMovePrinterGroupName = form.getInventoryMovePrinterGroupName();
                            PrinterGroup inventoryMovePrinterGroup = printerControl.getPrinterGroupByName(inventoryMovePrinterGroupName);
                            
                            if(inventoryMovePrinterGroup != null) {
                                String picklistPrinterGroupName = form.getPicklistPrinterGroupName();
                                PrinterGroup picklistPrinterGroup = printerControl.getPrinterGroupByName(picklistPrinterGroupName);
                                
                                if(picklistPrinterGroup != null) {
                                    String packingListPrinterGroupName = form.getPackingListPrinterGroupName();
                                    PrinterGroup packingListPrinterGroup = printerControl.getPrinterGroupByName(packingListPrinterGroupName);
                                    
                                    if(packingListPrinterGroup != null) {
                                        String shippingManifestPrinterGroupName = form.getShippingManifestPrinterGroupName();
                                        PrinterGroup shippingManifestPrinterGroup = printerControl.getPrinterGroupByName(shippingManifestPrinterGroupName);
                                        
                                        if(shippingManifestPrinterGroup != null) {
                                            PartyType partyType = partyControl.getPartyTypeByName(PartyConstants.PartyType_WAREHOUSE);
                                            BasePK createdBy = getPartyPK();
                                            String name = form.getName();
                                            Boolean isDefault = Boolean.valueOf(form.getIsDefault());
                                            Integer sortOrder = Integer.valueOf(form.getSortOrder());
                                            
                                            Party party = partyControl.createParty(null, partyType, preferredLanguage, preferredCurrency, preferredTimeZone, preferredDateTimeFormat, createdBy);
                                            
                                            if(name != null) {
                                                partyControl.createPartyGroup(party, name, createdBy);
                                            }
                                            
                                            warehouse = warehouseControl.createWarehouse(party, warehouseName, isDefault, sortOrder, createdBy);
                                            
                                            PrinterGroupUseType printerGroupUseType = printerControl.getPrinterGroupUseTypeByName(PrinterConstants.PrinterGroupUseType_WAREHOUSE_INVENTORY_MOVE);
                                            printerControl.createPartyPrinterGroupUse(party, printerGroupUseType, inventoryMovePrinterGroup, createdBy);
                                            
                                            printerGroupUseType = printerControl.getPrinterGroupUseTypeByName(PrinterConstants.PrinterGroupUseType_WAREHOUSE_PACKING_LIST);
                                            printerControl.createPartyPrinterGroupUse(party, printerGroupUseType, packingListPrinterGroup, createdBy);
                                            
                                            printerGroupUseType = printerControl.getPrinterGroupUseTypeByName(PrinterConstants.PrinterGroupUseType_WAREHOUSE_PICKLIST);
                                            printerControl.createPartyPrinterGroupUse(party, printerGroupUseType, picklistPrinterGroup, createdBy);
                                            
                                            printerGroupUseType = printerControl.getPrinterGroupUseTypeByName(PrinterConstants.PrinterGroupUseType_WAREHOUSE_SHIPPING_MANIFEST);
                                            printerControl.createPartyPrinterGroupUse(party, printerGroupUseType, shippingManifestPrinterGroup, createdBy);
                                        } else {
                                            addExecutionError(ExecutionErrors.UnknownShippingManifestPrinterGroupName.name(), shippingManifestPrinterGroupName);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownPackingListPrinterGroupName.name(), packingListPrinterGroupName);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownPicklistPrinterGroupName.name(), picklistPrinterGroupName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownInventoryMovePrinterGroupName.name(), inventoryMovePrinterGroupName);
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
            addExecutionError(ExecutionErrors.DuplicateWarehouseName.name(), warehouseName);
        }
        
        if(warehouse != null) {
            Party party = warehouse.getParty();
            
            result.setEntityRef(party.getPrimaryKey().getEntityRef());
            result.setWarehouseName(warehouse.getWarehouseName());
            result.setPartyName(party.getLastDetail().getPartyName());
        }
        
        return result;
    }
    
}
