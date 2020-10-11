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

package com.echothree.control.user.warehouse.server.command;

import com.echothree.control.user.warehouse.common.edit.WarehouseEdit;
import com.echothree.control.user.warehouse.common.edit.WarehouseEditFactory;
import com.echothree.control.user.warehouse.common.form.EditWarehouseForm;
import com.echothree.control.user.warehouse.common.result.EditWarehouseResult;
import com.echothree.control.user.warehouse.common.result.WarehouseResultFactory;
import com.echothree.control.user.warehouse.common.spec.WarehouseSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.printer.common.PrinterConstants;
import com.echothree.model.control.printer.server.PrinterControl;
import com.echothree.model.control.warehouse.server.WarehouseControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
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
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditWarehouseCommand
        extends BaseAbstractEditCommand<WarehouseSpec, WarehouseEdit, EditWarehouseResult, Party, Party> {
    
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
    public EditWarehouseResult getResult() {
        return WarehouseResultFactory.getEditWarehouseResult();
    }

    @Override
    public WarehouseEdit getEdit() {
        return WarehouseEditFactory.getWarehouseEdit();
    }

    @Override
    public Party getEntity(EditWarehouseResult result) {
        var warehouseControl = (WarehouseControl)Session.getModelController(WarehouseControl.class);
        Warehouse warehouse;
        var warehouseName = spec.getWarehouseName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            warehouse = warehouseControl.getWarehouseByName(warehouseName);
        } else { // EditMode.UPDATE
            warehouse = warehouseControl.getWarehouseByNameForUpdate(warehouseName);
        }

        if(warehouse != null) {
            result.setWarehouse(warehouseControl.getWarehouseTransfer(getUserVisit(), warehouse));
        } else {
            addExecutionError(ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
        }

        return warehouse.getParty();
    }

    @Override
    public Party getLockEntity(Party party) {
        return party;
    }

    @Override
    public void fillInResult(EditWarehouseResult result, Party party) {
        var warehouseControl = (WarehouseControl)Session.getModelController(WarehouseControl.class);

        result.setWarehouse(warehouseControl.getWarehouseTransfer(getUserVisit(), party));
    }

    @Override
    public void doLock(WarehouseEdit edit, Party party) {
        var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        var printerControl = (PrinterControl)Session.getModelController(PrinterControl.class);
        var warehouseControl = (WarehouseControl)Session.getModelController(WarehouseControl.class);
        var warehouse = warehouseControl.getWarehouse(party);
        var partyDetail = party.getLastDetail();
        var partyGroup = partyControl.getPartyGroup(party);
        var preferredLanguage = partyDetail.getPreferredLanguage();
        var preferredCurrency = partyDetail.getPreferredCurrency();
        var preferredTimeZone = partyDetail.getPreferredTimeZone();
        var dateTimeFormat = partyDetail.getPreferredDateTimeFormat();

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
    }

    PrinterGroup inventoryMovePrinterGroup;
    PrinterGroup picklistPrinterGroup;
    PrinterGroup packingListPrinterGroup;
    PrinterGroup shippingManifestPrinterGroup;
    Language preferredLanguage;
    TimeZone preferredTimeZone;
    DateTimeFormat preferredDateTimeFormat;
    Currency preferredCurrency;

    @Override
    public void canUpdate(Party party) {
        var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        var printerControl = (PrinterControl)Session.getModelController(PrinterControl.class);
        var warehouseControl = (WarehouseControl)Session.getModelController(WarehouseControl.class);
        var warehouse = warehouseControl.getWarehouseForUpdate(party);
        var warehouseName = edit.getWarehouseName();
        var duplicateWarehouse = warehouseControl.getWarehouseByName(warehouseName);

        if(duplicateWarehouse == null || duplicateWarehouse.getPrimaryKey().equals(warehouse.getPrimaryKey())) {
            var inventoryMovePrinterGroupName = edit.getInventoryMovePrinterGroupName();

            inventoryMovePrinterGroup = printerControl.getPrinterGroupByName(inventoryMovePrinterGroupName);

            if(inventoryMovePrinterGroup != null) {
                var picklistPrinterGroupName = edit.getPicklistPrinterGroupName();

                picklistPrinterGroup = printerControl.getPrinterGroupByName(picklistPrinterGroupName);

                if(picklistPrinterGroup != null) {
                    var packingListPrinterGroupName = edit.getPackingListPrinterGroupName();

                    packingListPrinterGroup = printerControl.getPrinterGroupByName(packingListPrinterGroupName);

                    if(packingListPrinterGroup != null) {
                        var shippingManifestPrinterGroupName = edit.getShippingManifestPrinterGroupName();

                        shippingManifestPrinterGroup = printerControl.getPrinterGroupByName(shippingManifestPrinterGroupName);

                        if(shippingManifestPrinterGroup != null) {
                            var preferredLanguageIsoName = edit.getPreferredLanguageIsoName();

                            preferredLanguage = preferredLanguageIsoName == null ? null : partyControl.getLanguageByIsoName(preferredLanguageIsoName);

                            if(preferredLanguageIsoName == null || (preferredLanguage != null)) {
                                var preferredJavaTimeZoneName = edit.getPreferredJavaTimeZoneName();

                                preferredTimeZone = preferredJavaTimeZoneName == null ? null : partyControl.getTimeZoneByJavaName(preferredJavaTimeZoneName);

                                if(preferredJavaTimeZoneName == null || (preferredTimeZone != null)) {
                                    var preferredDateTimeFormatName = edit.getPreferredDateTimeFormatName();

                                    preferredDateTimeFormat = preferredDateTimeFormatName == null ? null : partyControl.getDateTimeFormatByName(preferredDateTimeFormatName);

                                    if(preferredDateTimeFormatName == null || (preferredDateTimeFormat != null)) {
                                        var preferredCurrencyIsoName = edit.getPreferredCurrencyIsoName();

                                        if(preferredCurrencyIsoName == null) {
                                            preferredCurrency = null;
                                        } else {
                                            var accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);

                                            preferredCurrency = accountingControl.getCurrencyByIsoName(preferredCurrencyIsoName);
                                        }

                                        if(preferredCurrencyIsoName != null && (preferredCurrency == null)) {
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
    }

    @Override
    public void doUpdate(Party party) {
        var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        var printerControl = (PrinterControl)Session.getModelController(PrinterControl.class);
        var warehouseControl = (WarehouseControl)Session.getModelController(WarehouseControl.class);
        var warehouse = warehouseControl.getWarehouseForUpdate(party);
        WarehouseValue warehouseValue = warehouseControl.getWarehouseValue(warehouse);
        PartyDetailValue partyDetailValue = partyControl.getPartyDetailValueForUpdate(party);
        PartyGroup partyGroup = partyControl.getPartyGroupForUpdate(party);
        String name = edit.getName();
        PartyPK updatedBy = getPartyPK();

        warehouseValue.setWarehouseName(edit.getWarehouseName());
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
    }

}
