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

package com.echothree.control.user.warehouse.server.command;

import com.echothree.control.user.warehouse.common.form.CreateWarehouseForm;
import com.echothree.control.user.warehouse.common.result.WarehouseResultFactory;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.printer.common.PrinterConstants;
import com.echothree.model.control.printer.server.control.PrinterControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.warehouse.server.logic.WarehouseLogic;
import com.echothree.model.control.warehouse.server.logic.WarehouseTypeLogic;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.warehouse.server.entity.Warehouse;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CreateWarehouseCommand
        extends BaseSimpleCommand<CreateWarehouseForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Warehouse.name(), SecurityRoles.Create.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WarehouseTypeName", FieldType.ENTITY_NAME, true, null, null),
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
        );
    }
    
    /** Creates a new instance of CreateWarehouseCommand */
    public CreateWarehouseCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = WarehouseResultFactory.getCreateWarehouseResult();
        Warehouse warehouse = null;
        var partyControl = Session.getModelController(PartyControl.class);
        var preferredLanguageIsoName = form.getPreferredLanguageIsoName();
        var preferredLanguage = preferredLanguageIsoName == null? null: partyControl.getLanguageByIsoName(preferredLanguageIsoName);

        if(preferredLanguageIsoName == null || (preferredLanguage != null)) {
            var preferredJavaTimeZoneName = form.getPreferredJavaTimeZoneName();
            var preferredTimeZone = preferredJavaTimeZoneName == null? null: partyControl.getTimeZoneByJavaName(preferredJavaTimeZoneName);

            if(preferredJavaTimeZoneName == null || (preferredTimeZone != null)) {
                var preferredDateTimeFormatName = form.getPreferredDateTimeFormatName();
                var preferredDateTimeFormat = preferredDateTimeFormatName == null? null: partyControl.getDateTimeFormatByName(preferredDateTimeFormatName);

                if(preferredDateTimeFormatName == null || (preferredDateTimeFormat != null)) {
                    var preferredCurrencyIsoName = form.getPreferredCurrencyIsoName();
                    Currency preferredCurrency;

                    if(preferredCurrencyIsoName == null) {
                        preferredCurrency = null;
                    } else {
                        var accountingControl = Session.getModelController(AccountingControl.class);

                        preferredCurrency = accountingControl.getCurrencyByIsoName(preferredCurrencyIsoName);
                    }

                    if(preferredCurrencyIsoName == null || (preferredCurrency != null)) {
                        var printerControl = Session.getModelController(PrinterControl.class);
                        var inventoryMovePrinterGroupName = form.getInventoryMovePrinterGroupName();
                        var inventoryMovePrinterGroup = printerControl.getPrinterGroupByName(inventoryMovePrinterGroupName);

                        if(inventoryMovePrinterGroup != null) {
                            var picklistPrinterGroupName = form.getPicklistPrinterGroupName();
                            var picklistPrinterGroup = printerControl.getPrinterGroupByName(picklistPrinterGroupName);

                            if(picklistPrinterGroup != null) {
                                var packingListPrinterGroupName = form.getPackingListPrinterGroupName();
                                var packingListPrinterGroup = printerControl.getPrinterGroupByName(packingListPrinterGroupName);

                                if(packingListPrinterGroup != null) {
                                    var shippingManifestPrinterGroupName = form.getShippingManifestPrinterGroupName();
                                    var shippingManifestPrinterGroup = printerControl.getPrinterGroupByName(shippingManifestPrinterGroupName);

                                    if(shippingManifestPrinterGroup != null) {
                                        var warehouseType = WarehouseTypeLogic.getInstance().getWarehouseTypeByName(this, form.getWarehouseTypeName());

                                        if(!hasExecutionErrors()) {
                                            var warehouseName = form.getWarehouseName();
                                            var name = form.getName();
                                            var isDefault = Boolean.valueOf(form.getIsDefault());
                                            var sortOrder = Integer.valueOf(form.getSortOrder());
                                            var createdBy = getPartyPK();

                                            warehouse = WarehouseLogic.getInstance().createWarehouse(this, warehouseName,
                                                    warehouseType, preferredLanguage, preferredCurrency, preferredTimeZone,
                                                    preferredDateTimeFormat, name, isDefault, sortOrder, createdBy);

                                            if(!hasExecutionErrors()) {
                                                var party = warehouse.getParty();
                                                var printerGroupUseType = printerControl.getPrinterGroupUseTypeByName(PrinterConstants.PrinterGroupUseType_WAREHOUSE_INVENTORY_MOVE);
                                                printerControl.createPartyPrinterGroupUse(party, printerGroupUseType, inventoryMovePrinterGroup, createdBy);

                                                printerGroupUseType = printerControl.getPrinterGroupUseTypeByName(PrinterConstants.PrinterGroupUseType_WAREHOUSE_PACKING_LIST);
                                                printerControl.createPartyPrinterGroupUse(party, printerGroupUseType, packingListPrinterGroup, createdBy);

                                                printerGroupUseType = printerControl.getPrinterGroupUseTypeByName(PrinterConstants.PrinterGroupUseType_WAREHOUSE_PICKLIST);
                                                printerControl.createPartyPrinterGroupUse(party, printerGroupUseType, picklistPrinterGroup, createdBy);

                                                printerGroupUseType = printerControl.getPrinterGroupUseTypeByName(PrinterConstants.PrinterGroupUseType_WAREHOUSE_SHIPPING_MANIFEST);
                                                printerControl.createPartyPrinterGroupUse(party, printerGroupUseType, shippingManifestPrinterGroup, createdBy);
                                            }
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

        if(warehouse != null) {
            var party = warehouse.getParty();
            
            result.setEntityRef(party.getPrimaryKey().getEntityRef());
            result.setWarehouseName(warehouse.getWarehouseName());
            result.setPartyName(party.getLastDetail().getPartyName());
        }
        
        return result;
    }
    
}
