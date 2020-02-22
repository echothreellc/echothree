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
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.core.common.CoreProperties;
import com.echothree.model.control.core.common.transfer.ComponentVendorTransfer;
import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.model.control.core.common.transfer.EntityTypeTransfer;
import com.echothree.model.control.customer.server.search.CustomerSearchEvaluator;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.search.common.SearchConstants;
import com.echothree.model.control.search.server.SearchControl;
import com.echothree.model.control.search.server.logic.SearchLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.security.server.logic.SecurityRoleLogic;
import com.echothree.model.control.vendor.server.VendorControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.EntityNamesUtils;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.translator.EntityInstanceAndNames;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        // Ensure we're able to compare instances of and generate a HashCode for the EntityInstanceTransfers
        removeFilteredTransferProperty(EntityInstanceTransfer.class, CoreProperties.ENTITY_TYPE);
        removeFilteredTransferProperty(EntityTypeTransfer.class, CoreProperties.COMPONENT_VENDOR);
        removeFilteredTransferProperty(EntityTypeTransfer.class, CoreProperties.ENTITY_TYPE_NAME);
        removeFilteredTransferProperty(ComponentVendorTransfer.class, CoreProperties.COMPONENT_VENDOR_NAME);
    }
    
    private EntityInstanceTransfer fillInEntityInstance(EntityInstanceAndNames entityInstanceAndNames) {
        var entityInstance = getCoreControl().getEntityInstanceTransfer(getUserVisit(), entityInstanceAndNames.getEntityInstance(), false, false, false, false, false);

        entityInstance.setEntityNames(entityInstanceAndNames.getEntityNames());
        
        return entityInstance;
    }
    
    private void checkSequenceTypes(final Party party, final Set<EntityInstanceTransfer> entityInstances, final String target) {
        var entityInstanceAndNames = EntityNamesUtils.getInstance().getEntityNames(party, target, true);
        
        if(entityInstanceAndNames != null) {
            entityInstances.add(fillInEntityInstance(entityInstanceAndNames));
        }
    }
    
    private void checkItems(final Party party, final Set<EntityInstanceTransfer> entityInstances, final String target) {
        if(SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(null, party,
                SecurityRoleGroups.Item.name(), SecurityRoles.Search.name())) {
            var itemControl = (ItemControl)Session.getModelController(ItemControl.class);
            var item = itemControl.getItemByNameThenAlias(target);

            if(item != null) {
                var entityInstance = getCoreControl().getEntityInstanceByBasePK(item.getPrimaryKey());
                var entityInstanceAndNames = EntityNamesUtils.getInstance().getEntityNames(entityInstance);

                entityInstances.add(fillInEntityInstance(entityInstanceAndNames));
            }
        }
    }
    
    private void checkVendors(final Party party, final Set<EntityInstanceTransfer> entityInstances, final String target) {
        if(SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(null, party,
                SecurityRoleGroups.Vendor.name(), SecurityRoles.Search.name())) {
            var vendorControl = (VendorControl)Session.getModelController(VendorControl.class);
            var vendor = vendorControl.getVendorByName(target);

            if(vendor != null) {
                var entityInstance = getCoreControl().getEntityInstanceByBasePK(vendor.getParty().getPrimaryKey());
                var entityInstanceAndNames = EntityNamesUtils.getInstance().getEntityNames(entityInstance);

                entityInstances.add(fillInEntityInstance(entityInstanceAndNames));
            }
        }
    }
    
    private void checkVendorItems(final Party party, final Set<EntityInstanceTransfer> entityInstances, final String target) {
        if(SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(null, party,
                SecurityRoleGroups.VendorItem.name(), SecurityRoles.Search.name())) {
            var vendorControl = (VendorControl)Session.getModelController(VendorControl.class);
            var vendorItems = vendorControl.getVendorItemsByVendorItemName(target);

            vendorItems.stream().map((vendorItem) -> getCoreControl().getEntityInstanceByBasePK(vendorItem.getPrimaryKey())).map((entityInstance) -> EntityNamesUtils.getInstance().getEntityNames(entityInstance)).forEach((entityInstanceAndNames) -> {
                entityInstances.add(fillInEntityInstance(entityInstanceAndNames));
            });
        }
    }

    private void executeCustomerSearch(final String firstName, final String middleName, final String lastName, final String q) {
        var searchLogic = SearchLogic.getInstance();
        var searchKind = SearchLogic.getInstance().getSearchKindByName(null, SearchConstants.SearchKind_CUSTOMER);
        var searchType = SearchLogic.getInstance().getSearchTypeByName(null, searchKind, SearchConstants.SearchType_IDENTIFY);
        var userVisit = getUserVisit();

        var customerSearchEvaluator = new CustomerSearchEvaluator(userVisit, searchType,
                searchLogic.getDefaultSearchDefaultOperator(null), searchLogic.getDefaultSearchSortOrder(null, searchKind),
                searchLogic.getDefaultSearchSortDirection(null));

        customerSearchEvaluator.setFirstName(firstName);
        customerSearchEvaluator.setFirstNameSoundex(false);
        customerSearchEvaluator.setMiddleName(middleName);
        customerSearchEvaluator.setMiddleNameSoundex(false);
        customerSearchEvaluator.setLastName(lastName);
        customerSearchEvaluator.setLastNameSoundex(false);
        customerSearchEvaluator.setQ(null, q);

        customerSearchEvaluator.execute(this);
    }

    private void searchCustomers(final Party party, final Set<EntityInstanceTransfer> entityInstances, final String target) {
        if(SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(null, party,
                SecurityRoleGroups.Customer.name(), SecurityRoles.Search.name())) {
            // TODO: Name Parsing
            executeCustomerSearch("Test", null, "Customer", null);

            var searchControl = (SearchControl)Session.getModelController(SearchControl.class);
            var userVisitSearch = SearchLogic.getInstance().getUserVisitSearchByName(null, getUserVisit(), SearchConstants.SearchKind_CUSTOMER, SearchConstants.SearchType_IDENTIFY);
            var customerResultEntityInstances = searchControl.getCustomerResultEntityInstances(userVisitSearch);

            for(var customerResultEntityInstance : customerResultEntityInstances) {
                var entityInstanceAndNames = EntityNamesUtils.getInstance().getEntityNames(customerResultEntityInstance);

                entityInstances.add(fillInEntityInstance(entityInstanceAndNames));
            }

            // TODO: Add in results
            // TODO: Search by q
        }
    }
    
    @Override
    protected BaseResult execute() {
        var result = SearchResultFactory.getIdentifyResult();
        var entityInstances = new HashSet<EntityInstanceTransfer>();
        var target = form.getTarget();
        var party = getParty();
        
        // Compile a list of all possible EntityInstances that the target may refer to.
        checkSequenceTypes(party, entityInstances, target);
        checkItems(party, entityInstances, target);
        checkVendors(party, entityInstances, target);
        checkVendorItems(party, entityInstances, target);

        searchCustomers(party, entityInstances, target);
        
        result.setEntityInstances(entityInstances);
        
        return result;
    }
    
}
