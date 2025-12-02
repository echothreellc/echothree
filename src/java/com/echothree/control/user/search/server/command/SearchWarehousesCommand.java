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

import com.echothree.control.user.search.common.form.SearchWarehousesForm;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.search.common.SearchKinds;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.search.server.logic.SearchLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.warehouse.server.search.WarehouseSearchEvaluator;
import com.echothree.model.data.party.server.entity.PartyAliasType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import com.google.common.base.Splitter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class SearchWarehousesCommand
        extends BaseSimpleCommand<SearchWarehousesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Warehouse.name(), SecurityRoles.Search.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Q", FieldType.STRING, false, null, null),
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyAliasTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Alias", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CreatedSince", FieldType.DATE_TIME, false, null, null),
                new FieldDefinition("ModifiedSince", FieldType.DATE_TIME, false, null, null),
                new FieldDefinition("Fields", FieldType.STRING, false, null, null)
                ));
    }

    /** Creates a new instance of SearchWarehousesCommand */
    public SearchWarehousesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = SearchResultFactory.getSearchWarehousesResult();
        var searchControl = Session.getModelController(SearchControl.class);
        var searchKind = searchControl.getSearchKindByName(SearchKinds.WAREHOUSE.name());
        
        if(searchKind != null) {
            var searchTypeName = form.getSearchTypeName();
            var searchType = searchControl.getSearchTypeByName(searchKind, searchTypeName);
            
            if(searchType != null) {
                var partyAliasTypeName = form.getPartyAliasTypeName();
                var alias = form.getAlias();
                PartyAliasType partyAliasType = null;

                if(partyAliasTypeName != null) {
                    var partyControl = Session.getModelController(PartyControl.class);
                    var partyType = partyControl.getPartyTypeByName(PartyTypes.WAREHOUSE.name());

                    if(partyType != null) {
                        partyAliasType = partyControl.getPartyAliasTypeByName(partyType, partyAliasTypeName);

                        if(partyAliasType == null) {
                            addExecutionError(ExecutionErrors.UnknownPartyAliasTypeName.name(), PartyTypes.WAREHOUSE.name(), partyAliasTypeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownPartyTypeName.name(), PartyTypes.WAREHOUSE.name());
                    }
                }

                if(!hasExecutionErrors()) {
                    var searchLogic = SearchLogic.getInstance();
                    var userVisit = getUserVisit();
                    var warehouseSearchEvaluator = new WarehouseSearchEvaluator(userVisit, searchType,
                            searchLogic.getDefaultSearchDefaultOperator(null), searchLogic.getDefaultSearchSortOrder(null, searchKind),
                            searchLogic.getDefaultSearchSortDirection(null));
                    var createdSince = form.getCreatedSince();
                    var modifiedSince = form.getModifiedSince();
                    var fields = form.getFields();

                    warehouseSearchEvaluator.setQ(this, form.getQ());
                    warehouseSearchEvaluator.setPartyAliasType(partyAliasType);
                    warehouseSearchEvaluator.setAlias(alias);
                    warehouseSearchEvaluator.setWarehouseName(form.getWarehouseName());
                    warehouseSearchEvaluator.setPartyName(form.getPartyName());
                    warehouseSearchEvaluator.setCreatedSince(createdSince == null ? null : Long.valueOf(createdSince));
                    warehouseSearchEvaluator.setModifiedSince(modifiedSince == null ? null : Long.valueOf(modifiedSince));
                    warehouseSearchEvaluator.setFields(fields == null ? null : Splitter.on(':').trimResults().omitEmptyStrings().splitToList(fields).toArray(new String[0]));

                    if(!hasExecutionErrors()) {
                        result.setCount(warehouseSearchEvaluator.execute(this));
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownSearchTypeName.name(), SearchKinds.WAREHOUSE.name(), searchTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSearchKindName.name(), SearchKinds.WAREHOUSE.name());
        }
        
        return result;
    }
}
