// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.control.user.warehouse.common.edit.WarehouseEdit;
import com.echothree.control.user.warehouse.common.edit.WarehouseEditFactory;
import com.echothree.control.user.warehouse.common.form.EditWarehouseForm;
import com.echothree.control.user.warehouse.common.result.EditWarehouseResult;
import com.echothree.control.user.warehouse.common.result.WarehouseResultFactory;
import com.echothree.control.user.warehouse.common.spec.WarehouseSpec;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.printer.common.PrinterConstants;
import com.echothree.model.control.printer.server.PrinterControl;
import com.echothree.model.control.warehouse.server.WarehouseControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyDetail;
import com.echothree.model.data.party.server.entity.PartyGroup;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.party.server.value.PartyDetailValue;
import com.echothree.model.data.party.server.value.PartyGroupValue;
import com.echothree.model.data.printer.server.entity.PrinterGroup;
import com.echothree.model.data.printer.server.entity.PrinterGroupUseType;
import com.echothree.model.data.printer.server.value.PartyPrinterGroupUseValue;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.warehouse.server.entity.Warehouse;
import com.echothree.model.data.warehouse.server.value.WarehouseValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditWarehouseCommand
        extends BaseEditCommand<WarehouseSpec, WarehouseEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
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
    
    /** Creates a new instance of EditWarehouseCommand */
    public EditWarehouseCommand(UserVisitPK userVisitPK, EditWarehouseForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var warehouseControl = (WarehouseControl)Session.getModelController(WarehouseControl.class);
        EditWarehouseResult result = WarehouseResultFactory.getEditWarehouseResult();
        String originalWarehouseName = spec.getWarehouseName();
        Warehouse warehouse = warehouseControl.getWarehouseByNameForUpdate(originalWarehouseName);
        
        if(warehouse != null) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            var printerControl = (PrinterControl)Session.getModelController(PrinterControl.class);
            Party party = warehouse.getParty();
            
            if(editMode.equals(EditMode.LOCK)) {
                result.setWarehouse(warehouseControl.getWarehouseTransfer(getUserVisit(), warehouse));
                
                if(lockEntity(party)) {
                    WarehouseEdit edit = WarehouseEditFactory.getWarehouseEdit();
                    PartyDetail partyDetail = party.getLastDetail();
                    PartyGroup partyGroup = partyControl.getPartyGroup(party);
                    Language preferredLanguage = partyDetail.getPreferredLanguage();
                    Currency preferredCurrency = partyDetail.getPreferredCurrency();
                    TimeZone preferredTimeZone = partyDetail.getPreferredTimeZone();
                    DateTimeFormat dateTimeFormat = partyDetail.getPreferredDateTimeFormat();
                    
                    result.setEdit(edit);
                    edit.setWarehouseName(warehouse.getWarehouseName());
                    edit.setName(partyGroup == null? null: partyGroup.getName());
                    edit.setPreferredLanguageIsoName(preferredLanguage == null ? null : preferredLanguage.getLanguageIsoName());
                    edit.setPreferredCurrencyIsoName(preferredCurrency == null ? null : preferredCurrency.getCurrencyIsoName());
                    edit.setPreferredJavaTimeZoneName(preferredTimeZone == null ? null : preferredTimeZone.getLastDetail().getJavaTimeZoneName());
                    edit.setPreferredDateTimeFormatName(dateTimeFormat == null ? null : dateTimeFormat.getLastDetail().getDateTimeFormatName());
                    edit.setIsDefault(warehouse.getIsDefault().toString());
                    edit.setSortOrder(warehouse.getSortOrder().toString());
                    edit.setInventoryMovePrinterGroupName(printerControl.getPartyPrinterGroupUseUsingNames(party, PrinterConstants.PrinterGroupUseType_WAREHOUSE_INVENTORY_MOVE).getPrinterGroup().getLastDetail().getPrinterGroupName());
                    edit.setPicklistPrinterGroupName(printerControl.getPartyPrinterGroupUseUsingNames(party, PrinterConstants.PrinterGroupUseType_WAREHOUSE_PACKING_LIST).getPrinterGroup().getLastDetail().getPrinterGroupName());
                    edit.setPackingListPrinterGroupName(printerControl.getPartyPrinterGroupUseUsingNames(party, PrinterConstants.PrinterGroupUseType_WAREHOUSE_PICKLIST).getPrinterGroup().getLastDetail().getPrinterGroupName());
                    edit.setShippingManifestPrinterGroupName(printerControl.getPartyPrinterGroupUseUsingNames(party, PrinterConstants.PrinterGroupUseType_WAREHOUSE_SHIPPING_MANIFEST).getPrinterGroup().getLastDetail().getPrinterGroupName());
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }
                
                result.setEntityLock(getEntityLockTransfer(party));
            } else if(editMode.equals(EditMode.ABANDON)) {
                unlockEntity(party);
            } else if(editMode.equals(EditMode.UPDATE)) {
                WarehouseValue warehouseValue = warehouseControl.getWarehouseValue(warehouse);
                String warehouseName = edit.getWarehouseName();
                Warehouse duplicateWarehouse = warehouseControl.getWarehouseByName(warehouseName);

                if(duplicateWarehouse == null || duplicateWarehouse.getPrimaryKey().equals(warehouseValue.getPrimaryKey())) {
                    String inventoryMovePrinterGroupName = edit.getInventoryMovePrinterGroupName();
                    PrinterGroup inventoryMovePrinterGroup = printerControl.getPrinterGroupByName(inventoryMovePrinterGroupName);

                    if(inventoryMovePrinterGroup != null) {
                        String picklistPrinterGroupName = edit.getPicklistPrinterGroupName();
                        PrinterGroup picklistPrinterGroup = printerControl.getPrinterGroupByName(picklistPrinterGroupName);

                        if(picklistPrinterGroup != null) {
                            String packingListPrinterGroupName = edit.getPackingListPrinterGroupName();
                            PrinterGroup packingListPrinterGroup = printerControl.getPrinterGroupByName(packingListPrinterGroupName);

                            if(packingListPrinterGroup != null) {
                                String shippingManifestPrinterGroupName = edit.getShippingManifestPrinterGroupName();
                                PrinterGroup shippingManifestPrinterGroup = printerControl.getPrinterGroupByName(shippingManifestPrinterGroupName);

                                if(shippingManifestPrinterGroup != null) {
                                    String preferredLanguageIsoName = edit.getPreferredLanguageIsoName();
                                    Language preferredLanguage = preferredLanguageIsoName == null ? null : partyControl.getLanguageByIsoName(preferredLanguageIsoName);

                                    if(preferredLanguageIsoName == null || (preferredLanguage != null)) {
                                        String preferredJavaTimeZoneName = edit.getPreferredJavaTimeZoneName();
                                        TimeZone preferredTimeZone = preferredJavaTimeZoneName == null ? null : partyControl.getTimeZoneByJavaName(preferredJavaTimeZoneName);

                                        if(preferredJavaTimeZoneName == null || (preferredTimeZone != null)) {
                                            String preferredDateTimeFormatName = edit.getPreferredDateTimeFormatName();
                                            DateTimeFormat preferredDateTimeFormat = preferredDateTimeFormatName == null ? null : partyControl.getDateTimeFormatByName(preferredDateTimeFormatName);

                                            if(preferredDateTimeFormatName == null || (preferredDateTimeFormat != null)) {
                                                String preferredCurrencyIsoName = edit.getPreferredCurrencyIsoName();
                                                Currency preferredCurrency;

                                                if(preferredCurrencyIsoName == null) {
                                                    preferredCurrency = null;
                                                } else {
                                                    var accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);

                                                    preferredCurrency = accountingControl.getCurrencyByIsoName(preferredCurrencyIsoName);
                                                }

                                                if(preferredCurrencyIsoName == null || (preferredCurrency != null)) {
                                                    if(lockEntityForUpdate(party)) {
                                                        try {
                                                            PartyDetailValue partyDetailValue = partyControl.getPartyDetailValueForUpdate(party);
                                                            PartyGroup partyGroup = partyControl.getPartyGroupForUpdate(party);
                                                            String name = edit.getName();
                                                            PartyPK updatedBy = getPartyPK();

                                                            warehouseValue.setWarehouseName(warehouseName);
                                                            warehouseValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                                            warehouseValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

                                                            partyDetailValue.setPreferredLanguagePK(preferredLanguage == null ? null : preferredLanguage.getPrimaryKey());
                                                            partyDetailValue.setPreferredTimeZonePK(preferredTimeZone == null ? null : preferredTimeZone.getPrimaryKey());
                                                            partyDetailValue.setPreferredDateTimeFormatPK(preferredDateTimeFormat == null ? null : preferredDateTimeFormat.getPrimaryKey());
                                                            partyDetailValue.setPreferredCurrencyPK(preferredCurrency == null ? null : preferredCurrency.getPrimaryKey());
                                                            
                                                            PrinterGroupUseType printerGroupUseType = printerControl.getPrinterGroupUseTypeByName(PrinterConstants.PrinterGroupUseType_WAREHOUSE_INVENTORY_MOVE);
                                                            PartyPrinterGroupUseValue partyPrinterGroupUseValue = printerControl.getPartyPrinterGroupUseValueForUpdate(party, printerGroupUseType);
                                                            partyPrinterGroupUseValue.setPrinterGroupPK(inventoryMovePrinterGroup.getPrimaryKey());
                                                            printerControl.updatePartyPrinterGroupUseFromValue(partyPrinterGroupUseValue, updatedBy);

                                                            printerGroupUseType = printerControl.getPrinterGroupUseTypeByName(PrinterConstants.PrinterGroupUseType_WAREHOUSE_PACKING_LIST);
                                                            partyPrinterGroupUseValue = printerControl.getPartyPrinterGroupUseValueForUpdate(party, printerGroupUseType);
                                                            partyPrinterGroupUseValue.setPrinterGroupPK(packingListPrinterGroup.getPrimaryKey());
                                                            printerControl.updatePartyPrinterGroupUseFromValue(partyPrinterGroupUseValue, updatedBy);

                                                            printerGroupUseType = printerControl.getPrinterGroupUseTypeByName(PrinterConstants.PrinterGroupUseType_WAREHOUSE_PICKLIST);
                                                            partyPrinterGroupUseValue = printerControl.getPartyPrinterGroupUseValueForUpdate(party, printerGroupUseType);
                                                            partyPrinterGroupUseValue.setPrinterGroupPK(picklistPrinterGroup.getPrimaryKey());
                                                            printerControl.updatePartyPrinterGroupUseFromValue(partyPrinterGroupUseValue, updatedBy);

                                                            printerGroupUseType = printerControl.getPrinterGroupUseTypeByName(PrinterConstants.PrinterGroupUseType_WAREHOUSE_SHIPPING_MANIFEST);
                                                            partyPrinterGroupUseValue = printerControl.getPartyPrinterGroupUseValueForUpdate(party, printerGroupUseType);
                                                            partyPrinterGroupUseValue.setPrinterGroupPK(shippingManifestPrinterGroup.getPrimaryKey());
                                                            printerControl.updatePartyPrinterGroupUseFromValue(partyPrinterGroupUseValue, updatedBy);
                                                            
                                                            if(name != null) {
                                                                if(partyGroup != null) {
                                                                    PartyGroupValue partyGroupValue = partyControl.getPartyGroupValue(partyGroup);

                                                                    partyGroupValue.setName(name);
                                                                    partyControl.updatePartyGroupFromValue(partyGroupValue, updatedBy);
                                                                } else {
                                                                    partyControl.createPartyGroup(party, name, updatedBy);
                                                                }
                                                            } else {
                                                                if(partyGroup != null) {
                                                                    partyControl.deletePartyGroup(partyGroup, updatedBy);
                                                                }
                                                            }

                                                            warehouseControl.updateWarehouseFromValue(warehouseValue, updatedBy);
                                                            partyControl.updatePartyFromValue(partyDetailValue, updatedBy);
                                                        } finally {
                                                            unlockEntity(party);
                                                        }
                                                    } else {
                                                        addExecutionError(ExecutionErrors.EntityLockStale.name());
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
                    addExecutionError(ExecutionErrors.DuplicateWarehouseName.name(), warehouseName);
                }

                if(hasExecutionErrors()) {
                    result.setWarehouse(warehouseControl.getWarehouseTransfer(getUserVisit(), warehouse));
                    result.setEntityLock(getEntityLockTransfer(party));
                }
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWarehouseName.name(), originalWarehouseName);
        }
        
        return result;
    }
    
}
