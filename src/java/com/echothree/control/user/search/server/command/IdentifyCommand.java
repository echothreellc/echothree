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

package com.echothree.control.user.search.server.command;

import com.echothree.control.user.search.common.form.IdentifyForm;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.core.common.CoreProperties;
import com.echothree.model.control.core.common.transfer.ComponentVendorTransfer;
import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.model.control.core.common.transfer.EntityTypeTransfer;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.customer.server.search.CustomerSearchEvaluator;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.employee.server.search.EmployeeSearchEvaluator;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class IdentifyCommand
        extends BaseSimpleCommand<IdentifyForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Target", FieldType.STRING, true, null, null)
                ));
    }
    
    /** Creates a new instance of IdentifyCommand */
    public IdentifyCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
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
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var entityInstance = entityInstanceControl.getEntityInstanceTransfer(getUserVisit(), entityInstanceAndNames.getEntityInstance(), false, false, false, false);

        entityInstance.setEntityNames(entityInstanceAndNames.getEntityNames());
        
        return entityInstance;
    }
    
    private void checkSequenceTypes(final Party party, final Set<EntityInstanceTransfer> entityInstances, final String target) {
        var entityInstanceAndNames = EntityNamesUtils.getInstance().getEntityNames(this, party, target, true);
        
        if(entityInstanceAndNames != null) {
            entityInstances.add(fillInEntityInstance(entityInstanceAndNames));
        }
    }
    
    private void checkItems(final Party party, final Set<EntityInstanceTransfer> entityInstances, final String target) {
        if(SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(this, party,
                SecurityRoleGroups.Item.name(), SecurityRoles.Search.name())) {
            var itemControl = Session.getModelController(ItemControl.class);
            var item = itemControl.getItemByNameThenAlias(target);

            if(item != null) {
                var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
                var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(item.getPrimaryKey());
                var entityInstanceAndNames = EntityNamesUtils.getInstance().getEntityNames(entityInstance);

                entityInstances.add(fillInEntityInstance(entityInstanceAndNames));
            }
        }
    }

    private void checkCompanies(final Party party, final Set<EntityInstanceTransfer> entityInstances, final String target) {
        if(SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(this, party,
                SecurityRoleGroups.Company.name(), SecurityRoles.Search.name())) {
            var partyControl = Session.getModelController(PartyControl.class);
            var partyCompany = partyControl.getPartyCompanyByName(target);

            if(partyCompany != null) {
                var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
                var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(partyCompany.getParty().getPrimaryKey());
                var entityInstanceAndNames = EntityNamesUtils.getInstance().getEntityNames(entityInstance);

                entityInstances.add(fillInEntityInstance(entityInstanceAndNames));
            }
        }
    }

    private void checkDivisions(final Party party, final Set<EntityInstanceTransfer> entityInstances, final String target) {
        if(SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(this, party,
                SecurityRoleGroups.Division.name(), SecurityRoles.Search.name())) {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var partyControl = Session.getModelController(PartyControl.class);
            var partyDivisions = partyControl.getDivisionsByName(target);

            partyDivisions.stream().map((partyDivision) -> entityInstanceControl.getEntityInstanceByBasePK(partyDivision.getParty().getPrimaryKey())).map((entityInstance) -> EntityNamesUtils.getInstance().getEntityNames(entityInstance)).forEach((entityInstanceAndNames) -> {
                entityInstances.add(fillInEntityInstance(entityInstanceAndNames));
            });
        }
    }

    private void checkDepartments(final Party party, final Set<EntityInstanceTransfer> entityInstances, final String target) {
        if(SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(this, party,
                SecurityRoleGroups.Department.name(), SecurityRoles.Search.name())) {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var partyControl = Session.getModelController(PartyControl.class);
            var partyDepartments = partyControl.getDepartmentsByName(target);

            partyDepartments.stream().map((partyDepartment) -> entityInstanceControl.getEntityInstanceByBasePK(partyDepartment.getParty().getPrimaryKey())).map((entityInstance) -> EntityNamesUtils.getInstance().getEntityNames(entityInstance)).forEach((entityInstanceAndNames) -> {
                entityInstances.add(fillInEntityInstance(entityInstanceAndNames));
            });
        }
    }

    private void checkWarehouses(final Party party, final Set<EntityInstanceTransfer> entityInstances, final String target) {
        if(SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(this, party,
                SecurityRoleGroups.Warehouse.name(), SecurityRoles.Search.name())) {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var warehouseControl = Session.getModelController(WarehouseControl.class);
            var warehouse = warehouseControl.getWarehouseByName(target);

            if(warehouse != null) {
                var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(warehouse.getParty().getPrimaryKey());
                var entityInstanceAndNames = EntityNamesUtils.getInstance().getEntityNames(entityInstance);

                entityInstances.add(fillInEntityInstance(entityInstanceAndNames));
            }
        }
    }

    private void checkLocations(final Party party, final Set<EntityInstanceTransfer> entityInstances, final String target) {
        if(SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(this, party,
                SecurityRoleGroups.Location.name(), SecurityRoles.Search.name())) {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var warehouseControl = Session.getModelController(WarehouseControl.class);
            var locations = warehouseControl.getLocationsByName(target);

            locations.stream().map((location) -> entityInstanceControl.getEntityInstanceByBasePK(location.getPrimaryKey())).map((entityInstance) -> EntityNamesUtils.getInstance().getEntityNames(entityInstance)).forEach((entityInstanceAndNames) -> {
                entityInstances.add(fillInEntityInstance(entityInstanceAndNames));
            });
        }
    }

    private void checkEmployees(final Party party, final Set<EntityInstanceTransfer> entityInstances, final String target) {
        if(SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(this, party,
                SecurityRoleGroups.Employee.name(), SecurityRoles.Search.name())) {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var employeeControl = Session.getModelController(EmployeeControl.class);
            var partyEmployee = employeeControl.getPartyEmployeeByName(target);

            if(partyEmployee != null) {
                var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(partyEmployee.getParty().getPrimaryKey());
                var entityInstanceAndNames = EntityNamesUtils.getInstance().getEntityNames(entityInstance);

                entityInstances.add(fillInEntityInstance(entityInstanceAndNames));
            }
        }
    }

    private void checkCustomers(final Party party, final Set<EntityInstanceTransfer> entityInstances, final String target) {
        if(SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(this, party,
                SecurityRoleGroups.Customer.name(), SecurityRoles.Search.name())) {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var customerControl = Session.getModelController(CustomerControl.class);
            var customer = customerControl.getCustomerByName(target);

            if(customer != null) {
                var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(customer.getParty().getPrimaryKey());
                var entityInstanceAndNames = EntityNamesUtils.getInstance().getEntityNames(entityInstance);

                entityInstances.add(fillInEntityInstance(entityInstanceAndNames));
            }
        }
    }

    private void checkVendors(final Party party, final Set<EntityInstanceTransfer> entityInstances, final String target) {
        if(SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(this, party,
                SecurityRoleGroups.Vendor.name(), SecurityRoles.Search.name())) {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var vendorControl = Session.getModelController(VendorControl.class);
            var vendor = vendorControl.getVendorByName(target);

            if(vendor != null) {
                var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(vendor.getParty().getPrimaryKey());
                var entityInstanceAndNames = EntityNamesUtils.getInstance().getEntityNames(entityInstance);

                entityInstances.add(fillInEntityInstance(entityInstanceAndNames));
            }
        }
    }

    private void checkVendorItems(final Party party, final Set<EntityInstanceTransfer> entityInstances, final String target) {
        if(SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(this, party,
                SecurityRoleGroups.VendorItem.name(), SecurityRoles.Search.name())) {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var vendorControl = Session.getModelController(VendorControl.class);
            var vendorItems = vendorControl.getVendorItemsByVendorItemName(target);

            vendorItems.stream().map((vendorItem) -> entityInstanceControl.getEntityInstanceByBasePK(vendorItem.getPrimaryKey())).map((entityInstance) -> EntityNamesUtils.getInstance().getEntityNames(entityInstance)).forEach((entityInstanceAndNames) -> {
                entityInstances.add(fillInEntityInstance(entityInstanceAndNames));
            });
        }
    }

    private void checkComponentVendors(final Party party, final Set<EntityInstanceTransfer> entityInstances, final String target) {
        if(SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(this, party,
                SecurityRoleGroups.ComponentVendor.name(), SecurityRoles.Search.name())) {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var componentVendor = getComponentControl().getComponentVendorByName(target);

            if(componentVendor != null) {
                var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(componentVendor.getPrimaryKey());
                var entityInstanceAndNames = EntityNamesUtils.getInstance().getEntityNames(entityInstance);

                entityInstances.add(fillInEntityInstance(entityInstanceAndNames));
            }
        }
    }

    private void checkEntityTypes(final Party party, final Set<EntityInstanceTransfer> entityInstances, final String target) {
        if(SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(this, party,
                SecurityRoleGroups.EntityType.name(), SecurityRoles.Search.name())) {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var entityTypes = getEntityTypeControl().getEntityTypesByName(target);

            entityTypes.stream().map((entityType) -> entityInstanceControl.getEntityInstanceByBasePK(entityType.getPrimaryKey())).map((entityInstance) -> EntityNamesUtils.getInstance().getEntityNames(entityInstance)).forEach((entityInstanceAndNames) -> {
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
        if(SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(this, party,
                SecurityRoleGroups.Customer.name(), SecurityRoles.Search.name())) {
            var searchLogic = SearchLogic.getInstance();
            var searchKind = searchLogic.getSearchKindByName(this, SearchKinds.CUSTOMER.name());

            if(!hasExecutionErrors()) {
                var searchType = searchLogic.getSearchTypeByName(this, searchKind, SearchTypes.IDENTIFY.name());

                if(!hasExecutionErrors()) {
                    var userVisit = getUserVisit();

                    // First attempt using a first and/or last name isolated from target.
                    executeCustomerSearch(userVisit, entityInstances, searchLogic, searchKind, searchType,
                            nameResult.getFirstName(), nameResult.getMiddleName(), nameResult.getLastName(), null);
                    // Then attempt searching for target using it as a query string.
                    executeCustomerSearch(userVisit, entityInstances, searchLogic, searchKind, searchType,
                            null, null, null, target);
                }
            }
        }
    }

    private void executeEmployeeSearch(final UserVisit userVisit, final Set<EntityInstanceTransfer> entityInstances,
            final SearchLogic searchLogic, final SearchKind searchKind, final SearchType searchType,
            final String firstName, final String middleName, final String lastName, final String q) {
        var employeeSearchEvaluator = new EmployeeSearchEvaluator(userVisit, searchType,
                searchLogic.getDefaultSearchDefaultOperator(null),
                searchLogic.getDefaultSearchSortOrder(null, searchKind),
                searchLogic.getDefaultSearchSortDirection(null));

        employeeSearchEvaluator.setFirstName(firstName);
        employeeSearchEvaluator.setFirstNameSoundex(false);
        employeeSearchEvaluator.setMiddleName(middleName);
        employeeSearchEvaluator.setMiddleNameSoundex(false);
        employeeSearchEvaluator.setLastName(lastName);
        employeeSearchEvaluator.setLastNameSoundex(false);
        employeeSearchEvaluator.setQ(null, q);

        // Avoid using the real ExecutionErrorAccumulator in order to avoid either throwing an Exception or
        // accumulating errors for this UC.
        var dummyExecutionErrorAccumulator = new DummyExecutionErrorAccumulator();
        employeeSearchEvaluator.execute(dummyExecutionErrorAccumulator);

        if(!dummyExecutionErrorAccumulator.hasExecutionErrors()) {
            addSearchResults(userVisit, searchType, entityInstances);
        }
    }

    private void searchEmployees(final Party party, final Set<EntityInstanceTransfer> entityInstances, final String target,
            final NameResult nameResult) {
        if(SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(this, party,
                SecurityRoleGroups.Employee.name(), SecurityRoles.Search.name())) {
            var searchLogic = SearchLogic.getInstance();
            var searchKind = searchLogic.getSearchKindByName(this, SearchKinds.EMPLOYEE.name());

            if(!hasExecutionErrors()) {
                var searchType = searchLogic.getSearchTypeByName(this, searchKind, SearchTypes.IDENTIFY.name());

                if(!hasExecutionErrors()) {
                    var userVisit = getUserVisit();

                    // First attempt using a first and/or last name isolated from target.
                    executeEmployeeSearch(userVisit, entityInstances, searchLogic, searchKind, searchType,
                            nameResult.getFirstName(), nameResult.getMiddleName(), nameResult.getLastName(), null);
                    // Then attempt searching for target using it as a query string.
                    executeEmployeeSearch(userVisit, entityInstances, searchLogic, searchKind, searchType,
                            null, null, null, target);
                }
            }
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
        if(SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(this, party,
                SecurityRoleGroups.Vendor.name(), SecurityRoles.Search.name())) {
            var searchLogic = SearchLogic.getInstance();
            var searchKind = searchLogic.getSearchKindByName(this, SearchKinds.VENDOR.name());

            if(!hasExecutionErrors()) {
                var searchType = searchLogic.getSearchTypeByName(this, searchKind, SearchTypes.IDENTIFY.name());

                if(!hasExecutionErrors()) {
                    var userVisit = getUserVisit();

                    // First attempt using a first and/or last name isolated from target.
                    executeVendorSearch(userVisit, entityInstances, searchLogic, searchKind, searchType,
                            nameResult.getFirstName(), nameResult.getMiddleName(), nameResult.getLastName(), null);
                    // Then attempt searching for target using it as a query string.
                    executeVendorSearch(userVisit, entityInstances, searchLogic, searchKind, searchType,
                            null, null, null, target);
                }
            }
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
        if(SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(this, party,
                SecurityRoleGroups.Warehouse.name(), SecurityRoles.Search.name())) {
            var searchLogic = SearchLogic.getInstance();
            var searchKind = searchLogic.getSearchKindByName(this, SearchKinds.WAREHOUSE.name());

            if(!hasExecutionErrors()) {
                var searchType = searchLogic.getSearchTypeByName(this, searchKind, SearchTypes.IDENTIFY.name());

                if(!hasExecutionErrors()) {
                    var userVisit = getUserVisit();

                    // Attempt searching for target using it as a query string.
                    executeWarehouseSearch(userVisit, entityInstances, searchLogic, searchKind, searchType,
                            target);
                }
            }
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
        if(SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(this, party,
                SecurityRoleGroups.Item.name(), SecurityRoles.Search.name())) {
            var searchLogic = SearchLogic.getInstance();
            var searchKind = searchLogic.getSearchKindByName(this, SearchKinds.ITEM.name());

            if(!hasExecutionErrors()) {
                var searchType = searchLogic.getSearchTypeByName(this, searchKind, SearchTypes.IDENTIFY.name());

                if(!hasExecutionErrors()) {
                    var userVisit = getUserVisit();

                    executeItemSearch(userVisit, entityInstances, searchLogic, searchKind, searchType, target);
                }
            }
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
        checkSequenceTypes(party, entityInstances, target); // uses EEA
        if(!hasExecutionErrors()) {
            checkItems(party, entityInstances, target); // uses EEA
        }
        if(!hasExecutionErrors()) {
            checkCompanies(party, entityInstances, target); // uses EEA
        }
        if(!hasExecutionErrors()) {
            checkDivisions(party, entityInstances, target); // uses EEA
        }
        if(!hasExecutionErrors()) {
            checkDepartments(party, entityInstances, target); // uses EEA
        }
        if(!hasExecutionErrors()) {
            checkWarehouses(party, entityInstances, target); // uses EEA
        }
        if(!hasExecutionErrors()) {
            checkLocations(party, entityInstances, target); // uses EEA
        }
        if(!hasExecutionErrors()) {
            checkEmployees(party, entityInstances, target); // uses EEA
        }
        if(!hasExecutionErrors()) {
            checkCustomers(party, entityInstances, target); // uses EEA
        }
        if(!hasExecutionErrors()) {
            checkVendors(party, entityInstances, target); // uses EEA
        }
        if(!hasExecutionErrors()) {
            checkVendorItems(party, entityInstances, target); // uses EEA
        }
        if(!hasExecutionErrors()) {
            checkComponentVendors(party, entityInstances, target); // uses EEA
        }
        if(!hasExecutionErrors()) {
            checkEntityTypes(party, entityInstances, target); // uses EEA
        }

        var nameResult = new NameCleaner().getCleansedName(target);
        if(!hasExecutionErrors()) {
            searchEmployees(party, entityInstances, target, nameResult); // uses EEA
        }
        if(!hasExecutionErrors()) {
            searchCustomers(party, entityInstances, target, nameResult); // uses EEA
        }
        if(!hasExecutionErrors()) {
            searchVendors(party, entityInstances, target, nameResult); // uses EEA
        }
        if(!hasExecutionErrors()) {
            searchWarehouses(party, entityInstances, target); // uses EEA
        }
        if(!hasExecutionErrors()) {
            searchItems(party, entityInstances, target); // uses EEA
        }

        result.setEntityInstances(entityInstances);
        
        return result;
    }
    
}
