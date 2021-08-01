// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.control.user.search.common.form.SearchVendorsForm;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.control.user.search.common.result.SearchVendorsResult;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.search.common.SearchConstants;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.vendor.server.search.VendorSearchEvaluator;
import com.echothree.model.control.search.server.logic.SearchLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.search.server.entity.SearchKind;
import com.echothree.model.data.search.server.entity.SearchType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import com.google.common.base.Splitter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SearchVendorsCommand
        extends BaseSimpleCommand<SearchVendorsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Vendor.name(), SecurityRoles.Search.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FirstName", FieldType.STRING, false, 1L, 20L),
                new FieldDefinition("FirstNameSoundex", FieldType.BOOLEAN, false, null, null),
                new FieldDefinition("MiddleName", FieldType.STRING, false, 1L, 20L),
                new FieldDefinition("MiddleNameSoundex", FieldType.BOOLEAN, false, null, null),
                new FieldDefinition("LastName", FieldType.STRING, false, 1L, 20L),
                new FieldDefinition("LastNameSoundex", FieldType.BOOLEAN, false, null, null),
                new FieldDefinition("Name", FieldType.STRING, false, null, null),
                new FieldDefinition("VendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CreatedSince", FieldType.DATE_TIME, false, null, null),
                new FieldDefinition("ModifiedSince", FieldType.DATE_TIME, false, null, null),
                new FieldDefinition("Fields", FieldType.STRING, false, null, null)
                ));
    }

    /** Creates a new instance of SearchVendorsCommand */
    public SearchVendorsCommand(UserVisitPK userVisitPK, SearchVendorsForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        SearchVendorsResult result = SearchResultFactory.getSearchVendorsResult();
        var searchControl = Session.getModelController(SearchControl.class);
        SearchKind searchKind = searchControl.getSearchKindByName(SearchConstants.SearchKind_VENDOR);
        
        if(searchKind != null) {
            String searchTypeName = form.getSearchTypeName();
            SearchType searchType = searchControl.getSearchTypeByName(searchKind, searchTypeName);
            
            if(searchType != null) {
                SearchLogic searchLogic = SearchLogic.getInstance();
                UserVisit userVisit = getUserVisit();
                VendorSearchEvaluator vendorSearchEvaluator = new VendorSearchEvaluator(userVisit, searchType,
                        searchLogic.getDefaultSearchDefaultOperator(null), searchLogic.getDefaultSearchSortOrder(null, searchKind),
                        searchLogic.getDefaultSearchSortDirection(null));
                String createdSince = form.getCreatedSince();
                String modifiedSince = form.getModifiedSince();
                String fields = form.getFields();
                
                vendorSearchEvaluator.setFirstName(form.getFirstName());
                vendorSearchEvaluator.setFirstNameSoundex(Boolean.parseBoolean(form.getFirstNameSoundex()));
                vendorSearchEvaluator.setMiddleName(form.getMiddleName());
                vendorSearchEvaluator.setMiddleNameSoundex(Boolean.parseBoolean(form.getMiddleNameSoundex()));
                vendorSearchEvaluator.setLastName(form.getLastName());
                vendorSearchEvaluator.setLastNameSoundex(Boolean.parseBoolean(form.getLastNameSoundex()));
                vendorSearchEvaluator.setQ(this, form.getName());
                vendorSearchEvaluator.setVendorName(form.getVendorName());
                vendorSearchEvaluator.setCreatedSince(createdSince == null ? null : Long.valueOf(createdSince));
                vendorSearchEvaluator.setModifiedSince(modifiedSince == null ? null : Long.valueOf(modifiedSince));
                vendorSearchEvaluator.setFields(fields == null ? null : Splitter.on(':').trimResults().omitEmptyStrings().splitToList(fields).toArray(new String[0]));
                
                if(!hasExecutionErrors()) {
                    result.setCount(vendorSearchEvaluator.execute(this));
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownSearchTypeName.name(), SearchConstants.SearchKind_VENDOR, searchTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSearchKindName.name(), SearchConstants.SearchKind_VENDOR);
        }
        
        return result;
    }
}
