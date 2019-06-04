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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.edit.ItemEditFactory;
import com.echothree.control.user.item.common.edit.ItemHarmonizedTariffScheduleCodeEdit;
import com.echothree.control.user.item.common.form.EditItemHarmonizedTariffScheduleCodeForm;
import com.echothree.control.user.item.common.result.EditItemHarmonizedTariffScheduleCodeResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.control.user.item.common.spec.ItemHarmonizedTariffScheduleCodeSpec;
import com.echothree.model.control.geo.server.GeoControl;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCode;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeUse;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeUseType;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemHarmonizedTariffScheduleCode;
import com.echothree.model.data.item.server.entity.ItemHarmonizedTariffScheduleCodeDetail;
import com.echothree.model.data.item.server.value.ItemHarmonizedTariffScheduleCodeDetailValue;
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

public class EditItemHarmonizedTariffScheduleCodeCommand
        extends BaseAbstractEditCommand<ItemHarmonizedTariffScheduleCodeSpec, ItemHarmonizedTariffScheduleCodeEdit, EditItemHarmonizedTariffScheduleCodeResult, ItemHarmonizedTariffScheduleCode, ItemHarmonizedTariffScheduleCode> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemHarmonizedTariffScheduleCode.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CountryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("HarmonizedTariffScheduleCodeUseTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("HarmonizedTariffScheduleCodeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }

    /** Creates a new instance of EditItemHarmonizedTariffScheduleCodeCommand */
    public EditItemHarmonizedTariffScheduleCodeCommand(UserVisitPK userVisitPK, EditItemHarmonizedTariffScheduleCodeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditItemHarmonizedTariffScheduleCodeResult getResult() {
        return ItemResultFactory.getEditItemHarmonizedTariffScheduleCodeResult();
    }

    @Override
    public ItemHarmonizedTariffScheduleCodeEdit getEdit() {
        return ItemEditFactory.getItemHarmonizedTariffScheduleCodeEdit();
    }

    GeoCode countryGeoCode;
    HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType;

    @Override
    public ItemHarmonizedTariffScheduleCode getEntity(EditItemHarmonizedTariffScheduleCodeResult result) {
        var itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        ItemHarmonizedTariffScheduleCode itemHarmonizedTariffScheduleCode = null;
        String itemName = spec.getItemName();
        Item item = itemControl.getItemByName(itemName);
        
        if(item != null) {
            var geoControl = (GeoControl)Session.getModelController(GeoControl.class);
            String countryName = spec.getCountryName();
            
            countryGeoCode = geoControl.getCountryByAlias(countryName);
            
            if(countryGeoCode != null) {
                String harmonizedTariffScheduleCodeUseTypeName = spec.getHarmonizedTariffScheduleCodeUseTypeName();
                
                harmonizedTariffScheduleCodeUseType = itemControl.getHarmonizedTariffScheduleCodeUseTypeByName(harmonizedTariffScheduleCodeUseTypeName);

                if(harmonizedTariffScheduleCodeUseType != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        itemHarmonizedTariffScheduleCode = itemControl.getItemHarmonizedTariffScheduleCode(item,
                                countryGeoCode, harmonizedTariffScheduleCodeUseType);
                    } else { // EditMode.UPDATE
                        itemHarmonizedTariffScheduleCode = itemControl.getItemHarmonizedTariffScheduleCodeForUpdate(item, countryGeoCode,
                                harmonizedTariffScheduleCodeUseType);
                    }

                    if(itemHarmonizedTariffScheduleCode == null) {
                        addExecutionError(ExecutionErrors.UnknownItemHarmonizedTariffScheduleCode.name(), itemName, countryName, harmonizedTariffScheduleCodeUseTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownHarmonizedTariffScheduleCodeUseTypeName.name(), harmonizedTariffScheduleCodeUseTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCountryName.name(), countryName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
        }

        return itemHarmonizedTariffScheduleCode;
    }

    @Override
    public ItemHarmonizedTariffScheduleCode getLockEntity(ItemHarmonizedTariffScheduleCode itemHarmonizedTariffScheduleCode) {
        return itemHarmonizedTariffScheduleCode;
    }

    @Override
    public void fillInResult(EditItemHarmonizedTariffScheduleCodeResult result, ItemHarmonizedTariffScheduleCode itemHarmonizedTariffScheduleCode) {
        var itemControl = (ItemControl)Session.getModelController(ItemControl.class);

        result.setItemHarmonizedTariffScheduleCode(itemControl.getItemHarmonizedTariffScheduleCodeTransfer(getUserVisit(), itemHarmonizedTariffScheduleCode));
    }

    @Override
    public void doLock(ItemHarmonizedTariffScheduleCodeEdit edit, ItemHarmonizedTariffScheduleCode itemHarmonizedTariffScheduleCode) {
        ItemHarmonizedTariffScheduleCodeDetail itemHarmonizedTariffScheduleCodeDetail = itemHarmonizedTariffScheduleCode.getLastDetail();
        
        edit.setHarmonizedTariffScheduleCodeName(itemHarmonizedTariffScheduleCodeDetail.getHarmonizedTariffScheduleCode().getLastDetail().getHarmonizedTariffScheduleCodeName());
    }

    HarmonizedTariffScheduleCode harmonizedTariffScheduleCode;
    
    @Override
    public void canUpdate(ItemHarmonizedTariffScheduleCode itemHarmonizedTariffScheduleCode) {
        var itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        String harmonizedTariffScheduleCodeName = edit.getHarmonizedTariffScheduleCodeName();
        
        harmonizedTariffScheduleCode = itemControl.getHarmonizedTariffScheduleCodeByName(countryGeoCode, harmonizedTariffScheduleCodeName);

        if(harmonizedTariffScheduleCode != null) {
            HarmonizedTariffScheduleCodeUse harmonizedTariffScheduleCodeUse = itemControl.getHarmonizedTariffScheduleCodeUse(harmonizedTariffScheduleCode,
                    harmonizedTariffScheduleCodeUseType);

            if(harmonizedTariffScheduleCodeUse == null) {
                addExecutionError(ExecutionErrors.UnknownHarmonizedTariffScheduleCodeUse.name(), spec.getCountryName(), harmonizedTariffScheduleCodeName,
                        spec.getHarmonizedTariffScheduleCodeUseTypeName());
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownHarmonizedTariffScheduleCodeName.name(), spec.getCountryName(), harmonizedTariffScheduleCodeName);
        }
    }

    @Override
    public void doUpdate(ItemHarmonizedTariffScheduleCode itemHarmonizedTariffScheduleCode) {
        var itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        ItemHarmonizedTariffScheduleCodeDetailValue itemHarmonizedTariffScheduleCodeDetailValue = itemControl.getItemHarmonizedTariffScheduleCodeDetailValueForUpdate(itemHarmonizedTariffScheduleCode);
        
        itemHarmonizedTariffScheduleCodeDetailValue.setHarmonizedTariffScheduleCodePK(harmonizedTariffScheduleCode.getPrimaryKey());
        
        itemControl.updateItemHarmonizedTariffScheduleCodeFromValue(itemHarmonizedTariffScheduleCodeDetailValue, getPartyPK());
    }

}
