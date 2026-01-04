// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

import com.echothree.control.user.item.common.edit.ItemCountryOfOriginEdit;
import com.echothree.control.user.item.common.edit.ItemEditFactory;
import com.echothree.control.user.item.common.form.EditItemCountryOfOriginForm;
import com.echothree.control.user.item.common.result.EditItemCountryOfOriginResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.control.user.item.common.spec.ItemCountryOfOriginSpec;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemCountryOfOrigin;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.PercentUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditItemCountryOfOriginCommand
        extends BaseAbstractEditCommand<ItemCountryOfOriginSpec, ItemCountryOfOriginEdit, EditItemCountryOfOriginResult, ItemCountryOfOrigin, Item> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CountryName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Percent", FieldType.FRACTIONAL_PERCENT, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditItemCountryOfOriginCommand */
    public EditItemCountryOfOriginCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditItemCountryOfOriginResult getResult() {
        return ItemResultFactory.getEditItemCountryOfOriginResult();
    }

    @Override
    public ItemCountryOfOriginEdit getEdit() {
        return ItemEditFactory.getItemCountryOfOriginEdit();
    }

    @Override
    public ItemCountryOfOrigin getEntity(EditItemCountryOfOriginResult result) {
        var itemControl = Session.getModelController(ItemControl.class);
        ItemCountryOfOrigin itemCountryOfOrigin = null;
        var itemName = spec.getItemName();
        var item = itemControl.getItemByName(itemName);

        if(item != null) {
            var geoControl = Session.getModelController(GeoControl.class);
            var countryName = spec.getCountryName();
            var countryGeoCode = geoControl.getCountryByAlias(countryName);
            
            if(countryGeoCode != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    itemCountryOfOrigin = itemControl.getItemCountryOfOrigin(item, countryGeoCode);
                } else { // EditMode.UPDATE
                    itemCountryOfOrigin = itemControl.getItemCountryOfOriginForUpdate(item, countryGeoCode);
                }

                if(itemCountryOfOrigin == null) {
                    addExecutionError(ExecutionErrors.UnknownItemCountryOfOrigin.name(), itemName, countryName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCountryName.name(), countryName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
        }

        return itemCountryOfOrigin;
    }

    @Override
    public Item getLockEntity(ItemCountryOfOrigin itemCountryOfOrigin) {
        return itemCountryOfOrigin.getItem();
    }

    @Override
    public void fillInResult(EditItemCountryOfOriginResult result, ItemCountryOfOrigin itemCountryOfOrigin) {
        var itemControl = Session.getModelController(ItemControl.class);

        result.setItemCountryOfOrigin(itemControl.getItemCountryOfOriginTransfer(getUserVisit(), itemCountryOfOrigin));
    }

    @Override
    public void doLock(ItemCountryOfOriginEdit edit, ItemCountryOfOrigin itemCountryOfOrigin) {
        edit.setPercent(PercentUtils.getInstance().formatFractionalPercent(itemCountryOfOrigin.getPercent()));
    }

    @Override
    public void doUpdate(ItemCountryOfOrigin itemCountryOfOrigin) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemCountryOfOriginValue = itemControl.getItemCountryOfOriginValue(itemCountryOfOrigin);

        itemCountryOfOriginValue.setPercent(Integer.valueOf(edit.getPercent()));

        itemControl.updateItemCountryOfOriginFromValue(itemCountryOfOriginValue, getPartyPK());
    }
    
}
