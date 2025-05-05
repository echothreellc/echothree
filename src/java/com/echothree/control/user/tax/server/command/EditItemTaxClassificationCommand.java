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

package com.echothree.control.user.tax.server.command;

import com.echothree.control.user.tax.common.edit.ItemTaxClassificationEdit;
import com.echothree.control.user.tax.common.edit.TaxEditFactory;
import com.echothree.control.user.tax.common.form.EditItemTaxClassificationForm;
import com.echothree.control.user.tax.common.result.EditItemTaxClassificationResult;
import com.echothree.control.user.tax.common.result.TaxResultFactory;
import com.echothree.control.user.tax.common.spec.ItemTaxClassificationSpec;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.tax.server.control.TaxControl;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.tax.server.entity.ItemTaxClassification;
import com.echothree.model.data.tax.server.entity.TaxClassification;
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

public class EditItemTaxClassificationCommand
        extends BaseAbstractEditCommand<ItemTaxClassificationSpec, ItemTaxClassificationEdit, EditItemTaxClassificationResult, ItemTaxClassification, ItemTaxClassification> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemTaxClassification.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CountryName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TaxClassificationName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }

    /** Creates a new instance of EditItemTaxClassificationCommand */
    public EditItemTaxClassificationCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditItemTaxClassificationResult getResult() {
        return TaxResultFactory.getEditItemTaxClassificationResult();
    }

    @Override
    public ItemTaxClassificationEdit getEdit() {
        return TaxEditFactory.getItemTaxClassificationEdit();
    }

    GeoCode countryGeoCode;

    @Override
    public ItemTaxClassification getEntity(EditItemTaxClassificationResult result) {
        var itemControl = Session.getModelController(ItemControl.class);
        ItemTaxClassification itemTaxClassification = null;
        var itemName = spec.getItemName();
        var item = itemControl.getItemByName(itemName);
        
        if(item != null) {
            var geoControl = Session.getModelController(GeoControl.class);
            var countryName = spec.getCountryName();
            
            countryGeoCode = geoControl.getCountryByAlias(countryName);
            
            if(countryGeoCode != null) {
                var taxControl = Session.getModelController(TaxControl.class);
                
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    itemTaxClassification = taxControl.getItemTaxClassification(item, countryGeoCode);
                } else { // EditMode.UPDATE
                    itemTaxClassification = taxControl.getItemTaxClassificationForUpdate(item, countryGeoCode);
                }

                if(itemTaxClassification == null) {
                    addExecutionError(ExecutionErrors.UnknownItemTaxClassification.name(), itemName, countryName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCountryName.name(), countryName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
        }

        return itemTaxClassification;
    }

    @Override
    public ItemTaxClassification getLockEntity(ItemTaxClassification itemTaxClassification) {
        return itemTaxClassification;
    }

    @Override
    public void fillInResult(EditItemTaxClassificationResult result, ItemTaxClassification itemTaxClassification) {
        var taxControl = Session.getModelController(TaxControl.class);

        result.setItemTaxClassification(taxControl.getItemTaxClassificationTransfer(getUserVisit(), itemTaxClassification));
    }

    @Override
    public void doLock(ItemTaxClassificationEdit edit, ItemTaxClassification itemTaxClassification) {
        var itemTaxClassificationDetail = itemTaxClassification.getLastDetail();
        
        edit.setTaxClassificationName(itemTaxClassificationDetail.getTaxClassification().getLastDetail().getTaxClassificationName());
    }

    TaxClassification harmonizedTariffScheduleCode;
    
    @Override
    public void canUpdate(ItemTaxClassification itemTaxClassification) {
        var taxControl = Session.getModelController(TaxControl.class);
        var harmonizedTariffScheduleCodeName = edit.getTaxClassificationName();
        
        harmonizedTariffScheduleCode = taxControl.getTaxClassificationByName(countryGeoCode, harmonizedTariffScheduleCodeName);

        if(harmonizedTariffScheduleCode == null) {
            addExecutionError(ExecutionErrors.UnknownTaxClassificationName.name(), spec.getCountryName(), harmonizedTariffScheduleCodeName);
        }
    }

    @Override
    public void doUpdate(ItemTaxClassification itemTaxClassification) {
        var taxControl = Session.getModelController(TaxControl.class);
        var itemTaxClassificationDetailValue = taxControl.getItemTaxClassificationDetailValueForUpdate(itemTaxClassification);
        
        itemTaxClassificationDetailValue.setTaxClassificationPK(harmonizedTariffScheduleCode.getPrimaryKey());
        
        taxControl.updateItemTaxClassificationFromValue(itemTaxClassificationDetailValue, getPartyPK());
    }

}
