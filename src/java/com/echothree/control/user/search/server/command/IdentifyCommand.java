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

package com.echothree.control.user.search.server.command;

import com.echothree.control.user.search.common.form.IdentifyForm;
import com.echothree.control.user.search.common.result.IdentifyResult;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.security.server.logic.SecurityRoleLogic;
import com.echothree.model.control.vendor.server.VendorControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.vendor.server.entity.Vendor;
import com.echothree.model.data.vendor.server.entity.VendorItem;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.EntityNamesUtils;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.translator.EntityInstanceAndNames;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class IdentifyCommand
        extends BaseSimpleCommand<IdentifyForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Target", FieldType.STRING, true, null, null)
                ));
    }
    
    /** Creates a new instance of IdentifyCommand */
    public IdentifyCommand(UserVisitPK userVisitPK, IdentifyForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void setupSession() {
        super.setupSession();

        // Names are always included in the JumpResult, assembly of them is a little weird, so always disallow this option.
        removeOption(CoreOptions.EntityInstanceIncludeNames);
    }
    
    private EntityInstanceTransfer fillInEntityInstance(EntityInstanceAndNames entityInstanceAndNames) {
        EntityInstanceTransfer entityInstance = getCoreControl().getEntityInstanceTransfer(getUserVisit(), entityInstanceAndNames.getEntityInstance(), false, false, false, false, false);

        entityInstance.setEntityNames(entityInstanceAndNames.getEntityNames());
        
        return entityInstance;
    }
    
    private void checkSequenceTypes(final Party party, final List<EntityInstanceTransfer> entityInstances, final String target) {
        EntityInstanceAndNames entityInstanceAndNames = EntityNamesUtils.getInstance().getEntityNames(party, target, true);
        
        if(entityInstanceAndNames != null) {
            entityInstances.add(fillInEntityInstance(entityInstanceAndNames));
        }
    }
    
    private void checkItems(final Party party, final List<EntityInstanceTransfer> entityInstances, final String target) {
        if(SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(this, party,
                SecurityRoleGroups.Item.name(), SecurityRoles.Search.name())) {
            var itemControl = (ItemControl)Session.getModelController(ItemControl.class);
            Item item = itemControl.getItemByNameThenAlias(target);

            if(item != null) {
                EntityInstance entityInstance = getCoreControl().getEntityInstanceByBasePK(item.getPrimaryKey());
                EntityInstanceAndNames entityInstanceAndNames = EntityNamesUtils.getInstance().getEntityNames(entityInstance);

                entityInstances.add(fillInEntityInstance(entityInstanceAndNames));
            }
        }
    }
    
    private void checkVendors(final Party party, final List<EntityInstanceTransfer> entityInstances, final String target) {
        if(SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(this, party,
                SecurityRoleGroups.Vendor.name(), SecurityRoles.Search.name())) {
            var vendorControl = (VendorControl)Session.getModelController(VendorControl.class);
            Vendor vendor = vendorControl.getVendorByName(target);

            if(vendor != null) {
                EntityInstance entityInstance = getCoreControl().getEntityInstanceByBasePK(vendor.getParty().getPrimaryKey());
                EntityInstanceAndNames entityInstanceAndNames = EntityNamesUtils.getInstance().getEntityNames(entityInstance);

                entityInstances.add(fillInEntityInstance(entityInstanceAndNames));
            }
        }
    }
    
    private void checkVendorItems(final Party party, final List<EntityInstanceTransfer> entityInstances, final String target) {
        if(SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(this, party,
                SecurityRoleGroups.VendorItem.name(), SecurityRoles.Search.name())) {
            var vendorControl = (VendorControl)Session.getModelController(VendorControl.class);
            List<VendorItem> vendorItems = vendorControl.getVendorItemsByVendorItemName(target);

            vendorItems.stream().map((vendorItem) -> getCoreControl().getEntityInstanceByBasePK(vendorItem.getPrimaryKey())).map((entityInstance) -> EntityNamesUtils.getInstance().getEntityNames(entityInstance)).forEach((entityInstanceAndNames) -> {
                entityInstances.add(fillInEntityInstance(entityInstanceAndNames));
            });
        }
    }
    
    @Override
    protected BaseResult execute() {
        IdentifyResult result = SearchResultFactory.getIdentifyResult();
        List<EntityInstanceTransfer> entityInstances = new ArrayList<>();
        String target = form.getTarget();
        Party party = getParty();
        
        // Compile a list of all possible EntityInstances that the target may refer to.
        checkSequenceTypes(party, entityInstances, target);
        checkItems(party, entityInstances, target);
        checkVendors(party, entityInstances, target);
        checkVendorItems(party, entityInstances, target);
        
        result.setEntityInstances(entityInstances);
        
        return result;
    }
    
}
