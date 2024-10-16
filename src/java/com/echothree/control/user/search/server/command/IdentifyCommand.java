// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.customer.server.search.CustomerSearchEvaluator;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.search.ItemSearchEvaluator;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.search.common.SearchKinds;
import com.echothree.model.control.search.common.SearchTypes;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.search.server.logic.SearchLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.security.server.logic.SecurityRoleLogic;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.control.vendor.server.search.VendorSearchEvaluator;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.control.warehouse.server.search.WarehouseSearchEvaluator;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.search.server.entity.SearchKind;
import com.echothree.model.data.search.server.entity.SearchType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.string.NameResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.message.DummyExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityNamesUtils;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.translator.EntityInstanceAndNames;
import com.echothree.util.server.string.NameCleaner;
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
        var entityInstance = getCoreControl().getEntityInstanceTransfer(getUserVisit(), entityInstanceAndNames.getEntityInstance(), false, false, false, false);

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
            var itemControl = Session.getModelController(ItemControl.class);
            var item = itemControl.getItemByNameThenAlias(target);

            if(item != null) {
                var entityInstance = getCoreControl().getEntityInstanceByBasePK(item.getPrimaryKey());
                var entityInstanceAndNames = EntityNamesUtils.getInstance().getEntityNames(entityInstance);

                entityInstances.add(fillInEntityInstance(entityInstanceAndNames));
            }
        }
    }

    private void checkCompanies(final Party party, final Set<EntityInstanceTransfer> entityInstances, final String target) {
        if(SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(null, party,
                SecurityRoleGroups.Company.name(), SecurityRoles.Search.name())) {
            var partyControl = Session.getModelController(PartyControl.class);
            var partyCompany = partyControl.getPartyCompanyByName(target);

            if(partyCompany != null) {
                var entityInstance = getCoreControl().getEntityInstanceByBasePK(partyCompany.getParty().getPrimaryKey());
                var entityInstanceAndNames = EntityNamesUtils.getInstance().getEntityNames(entityInstance);

                entityInstances.add(fillInEntityInstance(entityInstanceAndNames));
            }
        }
    }

    private void checkDivisions(final Party party, final Set<EntityInstanceTransfer> entityInstances, final String target) {
        if(SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(null, party,
                SecurityRoleGroups.Division.name(), SecurityRoles.Search.name())) {
            var partyControl = Session.getModelController(PartyControl.class);
            var partyDivisions = partyControl.getDivisionsByName(target);

            partyDivisions.stream().map((partyDivision) -> getCoreControl().getEntityInstanceByBasePK(partyDivision.getParty().getPrimaryKey())).map((entityInstance) -> EntityNamesUtils.getInstance().getEntityNames(entityInstance)).forEach((entityInstanceAndNames) -> {
                entityInstances.add(fillInEntityInstance(entityInstanceAndNames));
            });
        }
    }

    private void checkDepartments(final Party party, final Set<EntityInstanceTransfer> entityInstances, final String target) {
        if(SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(null, party,
                SecurityRoleGroups.Department.name(), SecurityRoles.Search.name())) {
            var partyControl = Session.getModelController(PartyControl.class);
            var partyDepartments = partyControl.getDepartmentsByName(target);

            partyDepartments.stream().map((partyDepartment) -> getCoreControl().getEntityInstanceByBasePK(partyDepartment.getParty().getPrimaryKey())).map((entityInstance) -> EntityNamesUtils.getInstance().getEntityNames(entityInstance)).forEach((entityInstanceAndNames) -> {
                entityInstances.add(fillInEntityInstance(entityInstanceAndNames));
            });
        }
    }

    private void checkWarehouses(final Party party, final Set<EntityInstanceTransfer> entityInstances, final String target) {
        if(SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(null, party,
                SecurityRoleGroups.Warehouse.name(), SecurityRoles.Search.name())) {
            var warehouseControl = Session.getModelController(WarehouseControl.class);
            var warehouse = warehouseControl.getWarehouseByName(target);

            if(warehouse != null) {
                var entityInstance = getCoreControl().getEntityInstanceByBasePK(warehouse.getParty().getPrimaryKey());
                var entityInstanceAndNames = EntityNamesUtils.getInstance().getEntityNames(entityInstance);

                entityInstances.add(fillInEntityInstance(entityInstanceAndNames));
            }
        }
    }

    private void checkLocations(final Party party, final Set<EntityInstanceTransfer> entityInstances, final String target) {
        if(SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(null, party,
                SecurityRoleGroups.Location.name(), SecurityRoles.Search.name())) {
            var warehouseControl = Session.getModelController(WarehouseControl.class);
            var locations = warehouseControl.getLocationsByName(target);

            locations.stream().map((location) -> getCoreControl().getEntityInstanceByBasePK(location.getPrimaryKey())).map((entityInstance) -> EntityNamesUtils.getInstance().getEntityNames(entityInstance)).forEach((entityInstanceAndNames) -> {
                entityInstances.add(fillInEntityInstance(entityInstanceAndNames));
            });
        }
    }

    private void checkVendors(final Party party, final Set<EntityInstanceTransfer> entityInstances, final String target) {
        if(SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(null, party,
                SecurityRoleGroups.Vendor.name(), SecurityRoles.Search.name())) {
            var vendorControl = Session.getModelController(VendorControl.class);
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
            var vendorControl = Session.getModelController(VendorControl.class);
            var vendorItems = vendorControl.getVendorItemsByVendorItemName(target);

            vendorItems.stream().map((vendorItem) -> getCoreControl().getEntityInstanceByBasePK(vendorItem.getPrimaryKey())).map((entityInstance) -> EntityNamesUtils.getInstance().getEntityNames(entityInstance)).forEach((entityInstanceAndNames) -> {
                entityInstances.add(fillInEntityInstance(entityInstanceAndNames));
            });
        }
    }

    private void checkComponentVendors(final Party party, final Set<EntityInstanceTransfer> entityInstances, final String target) {
        if(SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(null, party,
                SecurityRoleGroups.ComponentVendor.name(), SecurityRoles.Search.name())) {
            var componentVendor = getCoreControl().getComponentVendorByName(target);

            if(componentVendor != null) {
                var entityInstance = getCoreControl().getEntityInstanceByBasePK(componentVendor.getPrimaryKey());
                var entityInstanceAndNames = EntityNamesUtils.getInstance().getEntityNames(entityInstance);

                entityInstances.add(fillInEntityInstance(entityInstanceAndNames));
            }
        }
    }

    private void checkEntityTypes(final Party party, final Set<EntityInstanceTransfer> entityInstances, final String target) {
        if(SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(null, party,
                SecurityRoleGroups.EntityType.name(), SecurityRoles.Search.name())) {
            var coreControl = Session.getModelController(CoreControl.class);
            var entityTypes = coreControl.getEntityTypesByName(target);

            entityTypes.stream().map((entityType) -> getCoreControl().getEntityInstanceByBasePK(entityType.getPrimaryKey())).map((entityInstance) -> EntityNamesUtils.getInstance().getEntityNames(entityInstance)).forEach((entityInstanceAndNames) -> {
                entityInstances.add(fillInEntityInstance(entityInstanceAndNames));
            });
        }
    }

    private void executeCustomerSearch(final UserVisit userVisit, final Set<EntityInstanceTransfer> entityInstances,
            final SearchLogic searchLogic, final SearchKind searchKind, final SearchType searchType,
            final String firstName, final String middleName, final String lastName, final String q) {
        var customerSearchEvaluator = new CustomerSearchEvaluator(userVisit, searchType,
                searchLogic.getDefaultSearchDefaultOperator(null),
                searchLogic.getDefaultSearchSortOrder(null, searchKind),
                searchLogic.getDefaultSearchSortDirection(null));

        customerSearchEvaluator.setFirstName(firstName);
        customerSearchEvaluator.setFirstNameSoundex(false);
        customerSearchEvaluator.setMiddleName(middleName);
        customerSearchEvaluator.setMiddleNameSoundex(false);
        customerSearchEvaluator.setLastName(lastName);
        customerSearchEvaluator.setLastNameSoundex(false);
        customerSearchEvaluator.setQ(null, q);

        // Avoid using the real ExecutionErrorAccumulator in order to avoid either throwing an Exception or
        // accumulating errors for this UC.
        var dummyExecutionErrorAccumulator = new DummyExecutionErrorAccumulator();
        customerSearchEvaluator.execute(dummyExecutionErrorAccumulator);

        if(!dummyExecutionErrorAccumulator.hasExecutionErrors()) {
            addSearchResults(userVisit, searchType, entityInstances);
        }
    }

    private void searchCustomers(final Party party, final Set<EntityInstanceTransfer> entityInstances, final String target,
            final NameResult nameResult) {
        if(SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(null, party,
                SecurityRoleGroups.Customer.name(), SecurityRoles.Search.name())) {
            var userVisit = getUserVisit();
            var searchLogic = SearchLogic.getInstance();
            var searchKind = searchLogic.getSearchKindByName(null, SearchKinds.CUSTOMER.name());
            var searchType = searchLogic.getSearchTypeByName(null, searchKind, SearchTypes.IDENTIFY.name());

            // First attempt using a first and/or last name isolated from target.
            executeCustomerSearch(userVisit, entityInstances, searchLogic, searchKind, searchType,
                    nameResult.getFirstName(), nameResult.getMiddleName(), nameResult.getLastName(), null);
            // Then attempt searching for target using it as a query string.
            executeCustomerSearch(userVisit, entityInstances, searchLogic, searchKind, searchType,
                    null, null, null, target);
        }
    }

    private void executeVendorSearch(final UserVisit userVisit, final Set<EntityInstanceTransfer> entityInstances,
            final SearchLogic searchLogic, final SearchKind searchKind, final SearchType searchType,
            final String firstName, final String middleName, final String lastName, final String q) {
        var vendorSearchEvaluator = new VendorSearchEvaluator(userVisit, searchType,
                searchLogic.getDefaultSearchDefaultOperator(null),
                searchLogic.getDefaultSearchSortOrder(null, searchKind),
                searchLogic.getDefaultSearchSortDirection(null));

        vendorSearchEvaluator.setFirstName(firstName);
        vendorSearchEvaluator.setFirstNameSoundex(false);
        vendorSearchEvaluator.setMiddleName(middleName);
        vendorSearchEvaluator.setMiddleNameSoundex(false);
        vendorSearchEvaluator.setLastName(lastName);
        vendorSearchEvaluator.setLastNameSoundex(false);
        vendorSearchEvaluator.setQ(null, q);

        // Avoid using the real ExecutionErrorAccumulator in order to avoid either throwing an Exception or
        // accumulating errors for this UC.
        var dummyExecutionErrorAccumulator = new DummyExecutionErrorAccumulator();
        vendorSearchEvaluator.execute(dummyExecutionErrorAccumulator);

        if(!dummyExecutionErrorAccumulator.hasExecutionErrors()) {
            addSearchResults(userVisit, searchType, entityInstances);
        }
    }

    private void searchVendors(final Party party, final Set<EntityInstanceTransfer> entityInstances, final String target,
            final NameResult nameResult) {
        if(SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(null, party,
                SecurityRoleGroups.Vendor.name(), SecurityRoles.Search.name())) {
            var userVisit = getUserVisit();
            var searchLogic = SearchLogic.getInstance();
            var searchKind = searchLogic.getSearchKindByName(null, SearchKinds.VENDOR.name());
            var searchType = searchLogic.getSearchTypeByName(null, searchKind, SearchTypes.IDENTIFY.name());

            // First attempt using a first and/or last name isolated from target.
            executeVendorSearch(userVisit, entityInstances, searchLogic, searchKind, searchType,
                    nameResult.getFirstName(), nameResult.getMiddleName(), nameResult.getLastName(), null);
            // Then attempt searching for target using it as a query string.
            executeVendorSearch(userVisit, entityInstances, searchLogic, searchKind, searchType,
                    null, null, null, target);
        }
    }

    private void executeWarehouseSearch(final UserVisit userVisit, final Set<EntityInstanceTransfer> entityInstances,
            final SearchLogic searchLogic, final SearchKind searchKind, final SearchType searchType, final String q) {
        var warehouseSearchEvaluator = new WarehouseSearchEvaluator(userVisit, searchType,
                searchLogic.getDefaultSearchDefaultOperator(null),
                searchLogic.getDefaultSearchSortOrder(null, searchKind),
                searchLogic.getDefaultSearchSortDirection(null));

        warehouseSearchEvaluator.setQ(null, q);

        // Avoid using the real ExecutionErrorAccumulator in order to avoid either throwing an Exception or
        // accumulating errors for this UC.
        var dummyExecutionErrorAccumulator = new DummyExecutionErrorAccumulator();
        warehouseSearchEvaluator.execute(dummyExecutionErrorAccumulator);

        if(!dummyExecutionErrorAccumulator.hasExecutionErrors()) {
            addSearchResults(userVisit, searchType, entityInstances);
        }
    }

    private void searchWarehouses(final Party party, final Set<EntityInstanceTransfer> entityInstances, final String target) {
        if(SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(null, party,
                SecurityRoleGroups.Warehouse.name(), SecurityRoles.Search.name())) {
            var userVisit = getUserVisit();
            var searchLogic = SearchLogic.getInstance();
            var searchKind = searchLogic.getSearchKindByName(null, SearchKinds.WAREHOUSE.name());
            var searchType = searchLogic.getSearchTypeByName(null, searchKind, SearchTypes.IDENTIFY.name());

            // Attempt searching for target using it as a query string.
            executeWarehouseSearch(userVisit, entityInstances, searchLogic, searchKind, searchType,
                    target);
        }
    }

    private void executeItemSearch(final UserVisit userVisit, final Set<EntityInstanceTransfer> entityInstances,
            final SearchLogic searchLogic, final SearchKind searchKind, final SearchType searchType,
            final String q) {
        var itemSearchEvaluator = new ItemSearchEvaluator(userVisit, null, searchType,
                searchLogic.getDefaultSearchDefaultOperator(null),
                searchLogic.getDefaultSearchSortOrder(null, searchKind),
                searchLogic.getDefaultSearchSortDirection(null),
                null);

        itemSearchEvaluator.setQ(null, q);

        // Avoid using the real ExecutionErrorAccumulator in order to avoid either throwing an Exception or
        // accumulating errors for this UC.
        var dummyExecutionErrorAccumulator = new DummyExecutionErrorAccumulator();
        itemSearchEvaluator.execute(dummyExecutionErrorAccumulator);

        if(!dummyExecutionErrorAccumulator.hasExecutionErrors()) {
            addSearchResults(userVisit, searchType, entityInstances);
        }
    }

    private void searchItems(final Party party, final Set<EntityInstanceTransfer> entityInstances, final String target) {
        if(SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(null, party,
                SecurityRoleGroups.Item.name(), SecurityRoles.Search.name())) {
            var userVisit = getUserVisit();
            var searchLogic = SearchLogic.getInstance();
            var searchKind = searchLogic.getSearchKindByName(null, SearchKinds.ITEM.name());
            var searchType = searchLogic.getSearchTypeByName(null, searchKind, SearchTypes.IDENTIFY.name());

            executeItemSearch(userVisit, entityInstances, searchLogic, searchKind, searchType, target);
        }
    }

    // Add results from any of the BaseSearchEvaluators to the entityInstances.
    private void addSearchResults(final UserVisit userVisit, final SearchType searchType,
            final Set<EntityInstanceTransfer> entityInstances) {
        var searchControl = Session.getModelController(SearchControl.class);
        var userVisitSearch = SearchLogic.getInstance().getUserVisitSearch(null, userVisit, searchType);
        var resultEntityInstances = searchControl.getUserVisitSearchEntityInstances(userVisitSearch);

        for(var resultEntityInstance : resultEntityInstances) {
            var entityInstanceAndNames = EntityNamesUtils.getInstance().getEntityNames(resultEntityInstance);

            entityInstances.add(fillInEntityInstance(entityInstanceAndNames));
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
        checkCompanies(party, entityInstances, target);
        checkDivisions(party, entityInstances, target);
        checkDepartments(party, entityInstances, target);
        checkWarehouses(party, entityInstances, target);
        checkLocations(party, entityInstances, target);
        checkVendors(party, entityInstances, target);
        checkVendorItems(party, entityInstances, target);
        checkComponentVendors(party, entityInstances, target);
        checkEntityTypes(party, entityInstances, target);

        var nameResult = new NameCleaner().getCleansedName(target);
        searchCustomers(party, entityInstances, target, nameResult);
        searchVendors(party, entityInstances, target, nameResult);
        searchWarehouses(party, entityInstances, target);
        searchItems(party, entityInstances, target);

        result.setEntityInstances(entityInstances);
        
        return result;
    }
    
}
