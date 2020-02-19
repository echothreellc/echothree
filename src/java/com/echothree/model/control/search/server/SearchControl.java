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

package com.echothree.model.control.search.server;


import com.echothree.model.control.batch.server.BatchControl;
import com.echothree.model.control.contact.server.ContactControl;
import com.echothree.model.control.content.server.ContentControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.customer.server.CustomerControl;
import com.echothree.model.control.employee.server.EmployeeControl;
import com.echothree.model.control.forum.server.ForumControl;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.offer.server.OfferControl;
import com.echothree.model.control.sales.server.SalesControl;
import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.control.search.common.choice.SearchCheckSpellingActionTypeChoicesBean;
import com.echothree.model.control.search.common.choice.SearchDefaultOperatorChoicesBean;
import com.echothree.model.control.search.common.choice.SearchKindChoicesBean;
import com.echothree.model.control.search.common.choice.SearchResultActionTypeChoicesBean;
import com.echothree.model.control.search.common.choice.SearchSortDirectionChoicesBean;
import com.echothree.model.control.search.common.choice.SearchSortOrderChoicesBean;
import com.echothree.model.control.search.common.choice.SearchTypeChoicesBean;
import com.echothree.model.control.search.common.choice.SearchUseTypeChoicesBean;
import com.echothree.model.control.search.common.transfer.ContactMechanismResultTransfer;
import com.echothree.model.control.search.common.transfer.ContentCategoryResultTransfer;
import com.echothree.model.control.search.common.transfer.CustomerResultTransfer;
import com.echothree.model.control.search.common.transfer.EmployeeResultTransfer;
import com.echothree.model.control.search.common.transfer.EntityListItemResultTransfer;
import com.echothree.model.control.search.common.transfer.EntityTypeResultTransfer;
import com.echothree.model.control.search.common.transfer.ForumMessageResultTransfer;
import com.echothree.model.control.search.common.transfer.HarmonizedTariffScheduleCodeResultTransfer;
import com.echothree.model.control.search.common.transfer.ItemResultTransfer;
import com.echothree.model.control.search.common.transfer.LeaveResultTransfer;
import com.echothree.model.control.search.common.transfer.OfferResultTransfer;
import com.echothree.model.control.search.common.transfer.SalesOrderBatchResultTransfer;
import com.echothree.model.control.search.common.transfer.SalesOrderResultTransfer;
import com.echothree.model.control.search.common.transfer.SearchCheckSpellingActionTypeDescriptionTransfer;
import com.echothree.model.control.search.common.transfer.SearchCheckSpellingActionTypeTransfer;
import com.echothree.model.control.search.common.transfer.SearchDefaultOperatorDescriptionTransfer;
import com.echothree.model.control.search.common.transfer.SearchDefaultOperatorTransfer;
import com.echothree.model.control.search.common.transfer.SearchKindDescriptionTransfer;
import com.echothree.model.control.search.common.transfer.SearchKindTransfer;
import com.echothree.model.control.search.common.transfer.SearchResultActionTypeDescriptionTransfer;
import com.echothree.model.control.search.common.transfer.SearchResultActionTypeTransfer;
import com.echothree.model.control.search.common.transfer.SearchSortDirectionDescriptionTransfer;
import com.echothree.model.control.search.common.transfer.SearchSortDirectionTransfer;
import com.echothree.model.control.search.common.transfer.SearchSortOrderDescriptionTransfer;
import com.echothree.model.control.search.common.transfer.SearchSortOrderTransfer;
import com.echothree.model.control.search.common.transfer.SearchTypeDescriptionTransfer;
import com.echothree.model.control.search.common.transfer.SearchTypeTransfer;
import com.echothree.model.control.search.common.transfer.SearchUseTypeDescriptionTransfer;
import com.echothree.model.control.search.common.transfer.SearchUseTypeTransfer;
import com.echothree.model.control.search.common.transfer.SecurityRoleGroupResultTransfer;
import com.echothree.model.control.search.common.transfer.SecurityRoleResultTransfer;
import com.echothree.model.control.search.common.transfer.UseResultTransfer;
import com.echothree.model.control.search.common.transfer.UseTypeResultTransfer;
import com.echothree.model.control.search.common.transfer.VendorResultTransfer;
import com.echothree.model.control.search.server.transfer.SearchCheckSpellingActionTypeDescriptionTransferCache;
import com.echothree.model.control.search.server.transfer.SearchCheckSpellingActionTypeTransferCache;
import com.echothree.model.control.search.server.transfer.SearchDefaultOperatorDescriptionTransferCache;
import com.echothree.model.control.search.server.transfer.SearchDefaultOperatorTransferCache;
import com.echothree.model.control.search.server.transfer.SearchKindTransferCache;
import com.echothree.model.control.search.server.transfer.SearchResultActionTypeDescriptionTransferCache;
import com.echothree.model.control.search.server.transfer.SearchResultActionTypeTransferCache;
import com.echothree.model.control.search.server.transfer.SearchSortDirectionDescriptionTransferCache;
import com.echothree.model.control.search.server.transfer.SearchSortDirectionTransferCache;
import com.echothree.model.control.search.server.transfer.SearchSortOrderTransferCache;
import com.echothree.model.control.search.server.transfer.SearchTransferCaches;
import com.echothree.model.control.search.server.transfer.SearchTypeTransferCache;
import com.echothree.model.control.search.server.transfer.SearchUseTypeDescriptionTransferCache;
import com.echothree.model.control.search.server.transfer.SearchUseTypeTransferCache;
import com.echothree.model.control.security.server.SecurityControl;
import com.echothree.model.control.vendor.server.VendorControl;
import com.echothree.model.control.sales.common.workflow.SalesOrderStatusConstants;
import com.echothree.model.control.search.server.graphql.CustomerResultObject;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.batch.common.pk.BatchPK;
import com.echothree.model.data.batch.server.entity.Batch;
import com.echothree.model.data.contact.common.pk.ContactMechanismPK;
import com.echothree.model.data.contact.server.entity.ContactMechanism;
import com.echothree.model.data.contact.server.entity.ContactMechanismDetail;
import com.echothree.model.data.contact.server.factory.ContactMechanismFactory;
import com.echothree.model.data.content.common.pk.ContentCategoryPK;
import com.echothree.model.data.content.server.entity.ContentCatalogDetail;
import com.echothree.model.data.content.server.entity.ContentCategory;
import com.echothree.model.data.content.server.entity.ContentCategoryDetail;
import com.echothree.model.data.content.server.factory.ContentCategoryFactory;
import com.echothree.model.data.core.common.pk.EntityListItemPK;
import com.echothree.model.data.core.common.pk.EntityTypePK;
import com.echothree.model.data.core.server.entity.EntityAttributeDetail;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityListItem;
import com.echothree.model.data.core.server.entity.EntityListItemDetail;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.EntityTypeDetail;
import com.echothree.model.data.core.server.factory.EntityListItemFactory;
import com.echothree.model.data.core.server.factory.EntityTypeFactory;
import com.echothree.model.data.employee.common.pk.LeavePK;
import com.echothree.model.data.employee.server.entity.Leave;
import com.echothree.model.data.employee.server.factory.LeaveFactory;
import com.echothree.model.data.forum.common.pk.ForumMessagePK;
import com.echothree.model.data.forum.server.entity.ForumMessage;
import com.echothree.model.data.forum.server.factory.ForumMessageFactory;
import com.echothree.model.data.index.server.entity.Index;
import com.echothree.model.data.index.server.entity.IndexField;
import com.echothree.model.data.index.server.entity.IndexType;
import com.echothree.model.data.item.common.pk.HarmonizedTariffScheduleCodePK;
import com.echothree.model.data.item.common.pk.ItemPK;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCode;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeDetail;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.factory.HarmonizedTariffScheduleCodeFactory;
import com.echothree.model.data.item.server.factory.ItemFactory;
import com.echothree.model.data.offer.common.pk.OfferPK;
import com.echothree.model.data.offer.common.pk.UsePK;
import com.echothree.model.data.offer.common.pk.UseTypePK;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferDetail;
import com.echothree.model.data.offer.server.entity.Use;
import com.echothree.model.data.offer.server.entity.UseDetail;
import com.echothree.model.data.offer.server.entity.UseType;
import com.echothree.model.data.offer.server.entity.UseTypeDetail;
import com.echothree.model.data.offer.server.factory.OfferFactory;
import com.echothree.model.data.offer.server.factory.UseFactory;
import com.echothree.model.data.offer.server.factory.UseTypeFactory;
import com.echothree.model.data.order.common.pk.OrderPK;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.factory.OrderFactory;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.search.common.CachedExecutedSearchResultConstants;
import com.echothree.model.data.search.common.SearchResultConstants;
import com.echothree.model.data.search.common.pk.PartySearchTypePreferencePK;
import com.echothree.model.data.search.common.pk.SearchCheckSpellingActionTypePK;
import com.echothree.model.data.search.common.pk.SearchDefaultOperatorPK;
import com.echothree.model.data.search.common.pk.SearchKindPK;
import com.echothree.model.data.search.common.pk.SearchResultActionTypePK;
import com.echothree.model.data.search.common.pk.SearchSortDirectionPK;
import com.echothree.model.data.search.common.pk.SearchSortOrderPK;
import com.echothree.model.data.search.common.pk.SearchTypePK;
import com.echothree.model.data.search.common.pk.SearchUseTypePK;
import com.echothree.model.data.search.server.entity.CachedExecutedSearch;
import com.echothree.model.data.search.server.entity.CachedExecutedSearchResult;
import com.echothree.model.data.search.server.entity.CachedSearch;
import com.echothree.model.data.search.server.entity.CachedSearchIndexField;
import com.echothree.model.data.search.server.entity.CachedSearchStatus;
import com.echothree.model.data.search.server.entity.PartySearchTypePreference;
import com.echothree.model.data.search.server.entity.PartySearchTypePreferenceDetail;
import com.echothree.model.data.search.server.entity.Search;
import com.echothree.model.data.search.server.entity.SearchCheckSpellingActionType;
import com.echothree.model.data.search.server.entity.SearchCheckSpellingActionTypeDescription;
import com.echothree.model.data.search.server.entity.SearchCheckSpellingActionTypeDetail;
import com.echothree.model.data.search.server.entity.SearchDefaultOperator;
import com.echothree.model.data.search.server.entity.SearchDefaultOperatorDescription;
import com.echothree.model.data.search.server.entity.SearchDefaultOperatorDetail;
import com.echothree.model.data.search.server.entity.SearchKind;
import com.echothree.model.data.search.server.entity.SearchKindDescription;
import com.echothree.model.data.search.server.entity.SearchKindDetail;
import com.echothree.model.data.search.server.entity.SearchResult;
import com.echothree.model.data.search.server.entity.SearchResultAction;
import com.echothree.model.data.search.server.entity.SearchResultActionType;
import com.echothree.model.data.search.server.entity.SearchResultActionTypeDescription;
import com.echothree.model.data.search.server.entity.SearchResultActionTypeDetail;
import com.echothree.model.data.search.server.entity.SearchSortDirection;
import com.echothree.model.data.search.server.entity.SearchSortDirectionDescription;
import com.echothree.model.data.search.server.entity.SearchSortDirectionDetail;
import com.echothree.model.data.search.server.entity.SearchSortOrder;
import com.echothree.model.data.search.server.entity.SearchSortOrderDescription;
import com.echothree.model.data.search.server.entity.SearchSortOrderDetail;
import com.echothree.model.data.search.server.entity.SearchType;
import com.echothree.model.data.search.server.entity.SearchTypeDescription;
import com.echothree.model.data.search.server.entity.SearchTypeDetail;
import com.echothree.model.data.search.server.entity.SearchUseType;
import com.echothree.model.data.search.server.entity.SearchUseTypeDescription;
import com.echothree.model.data.search.server.entity.SearchUseTypeDetail;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.search.server.factory.CachedExecutedSearchFactory;
import com.echothree.model.data.search.server.factory.CachedExecutedSearchResultFactory;
import com.echothree.model.data.search.server.factory.CachedSearchFactory;
import com.echothree.model.data.search.server.factory.CachedSearchIndexFieldFactory;
import com.echothree.model.data.search.server.factory.CachedSearchStatusFactory;
import com.echothree.model.data.search.server.factory.PartySearchTypePreferenceDetailFactory;
import com.echothree.model.data.search.server.factory.PartySearchTypePreferenceFactory;
import com.echothree.model.data.search.server.factory.SearchCheckSpellingActionTypeDescriptionFactory;
import com.echothree.model.data.search.server.factory.SearchCheckSpellingActionTypeDetailFactory;
import com.echothree.model.data.search.server.factory.SearchCheckSpellingActionTypeFactory;
import com.echothree.model.data.search.server.factory.SearchDefaultOperatorDescriptionFactory;
import com.echothree.model.data.search.server.factory.SearchDefaultOperatorDetailFactory;
import com.echothree.model.data.search.server.factory.SearchDefaultOperatorFactory;
import com.echothree.model.data.search.server.factory.SearchFactory;
import com.echothree.model.data.search.server.factory.SearchKindDescriptionFactory;
import com.echothree.model.data.search.server.factory.SearchKindDetailFactory;
import com.echothree.model.data.search.server.factory.SearchKindFactory;
import com.echothree.model.data.search.server.factory.SearchResultActionFactory;
import com.echothree.model.data.search.server.factory.SearchResultActionTypeDescriptionFactory;
import com.echothree.model.data.search.server.factory.SearchResultActionTypeDetailFactory;
import com.echothree.model.data.search.server.factory.SearchResultActionTypeFactory;
import com.echothree.model.data.search.server.factory.SearchResultFactory;
import com.echothree.model.data.search.server.factory.SearchSortDirectionDescriptionFactory;
import com.echothree.model.data.search.server.factory.SearchSortDirectionDetailFactory;
import com.echothree.model.data.search.server.factory.SearchSortDirectionFactory;
import com.echothree.model.data.search.server.factory.SearchSortOrderDescriptionFactory;
import com.echothree.model.data.search.server.factory.SearchSortOrderDetailFactory;
import com.echothree.model.data.search.server.factory.SearchSortOrderFactory;
import com.echothree.model.data.search.server.factory.SearchTypeDescriptionFactory;
import com.echothree.model.data.search.server.factory.SearchTypeDetailFactory;
import com.echothree.model.data.search.server.factory.SearchTypeFactory;
import com.echothree.model.data.search.server.factory.SearchUseTypeDescriptionFactory;
import com.echothree.model.data.search.server.factory.SearchUseTypeDetailFactory;
import com.echothree.model.data.search.server.factory.SearchUseTypeFactory;
import com.echothree.model.data.search.server.factory.UserVisitSearchFactory;
import com.echothree.model.data.search.server.value.CachedExecutedSearchResultValue;
import com.echothree.model.data.search.server.value.PartySearchTypePreferenceDetailValue;
import com.echothree.model.data.search.server.value.SearchCheckSpellingActionTypeDescriptionValue;
import com.echothree.model.data.search.server.value.SearchCheckSpellingActionTypeDetailValue;
import com.echothree.model.data.search.server.value.SearchDefaultOperatorDescriptionValue;
import com.echothree.model.data.search.server.value.SearchDefaultOperatorDetailValue;
import com.echothree.model.data.search.server.value.SearchKindDescriptionValue;
import com.echothree.model.data.search.server.value.SearchKindDetailValue;
import com.echothree.model.data.search.server.value.SearchResultActionTypeDescriptionValue;
import com.echothree.model.data.search.server.value.SearchResultActionTypeDetailValue;
import com.echothree.model.data.search.server.value.SearchResultValue;
import com.echothree.model.data.search.server.value.SearchSortDirectionDescriptionValue;
import com.echothree.model.data.search.server.value.SearchSortDirectionDetailValue;
import com.echothree.model.data.search.server.value.SearchSortOrderDescriptionValue;
import com.echothree.model.data.search.server.value.SearchSortOrderDetailValue;
import com.echothree.model.data.search.server.value.SearchTypeDescriptionValue;
import com.echothree.model.data.search.server.value.SearchTypeDetailValue;
import com.echothree.model.data.search.server.value.SearchUseTypeDescriptionValue;
import com.echothree.model.data.search.server.value.SearchUseTypeDetailValue;
import com.echothree.model.data.security.common.pk.SecurityRoleGroupPK;
import com.echothree.model.data.security.common.pk.SecurityRolePK;
import com.echothree.model.data.security.server.entity.SecurityRole;
import com.echothree.model.data.security.server.entity.SecurityRoleDetail;
import com.echothree.model.data.security.server.entity.SecurityRoleGroup;
import com.echothree.model.data.security.server.factory.SecurityRoleFactory;
import com.echothree.model.data.security.server.factory.SecurityRoleGroupFactory;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SearchControl
        extends BaseModelControl {
    
    /** Creates a new instance of SearchControl */
    public SearchControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Search Transfer Caches
    // --------------------------------------------------------------------------------

    private SearchTransferCaches searchTransferCaches = null;

    public SearchTransferCaches getSearchTransferCaches(UserVisit userVisit) {
        if(searchTransferCaches == null) {
            searchTransferCaches = new SearchTransferCaches(userVisit, this);
        }

        return searchTransferCaches;
    }

    // --------------------------------------------------------------------------------
    //   Search Use Types
    // --------------------------------------------------------------------------------

    public SearchUseType createSearchUseType(String searchUseTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        SearchUseType defaultSearchUseType = getDefaultSearchUseType();
        boolean defaultFound = defaultSearchUseType != null;

        if(defaultFound && isDefault) {
            SearchUseTypeDetailValue defaultSearchUseTypeDetailValue = getDefaultSearchUseTypeDetailValueForUpdate();

            defaultSearchUseTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateSearchUseTypeFromValue(defaultSearchUseTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        SearchUseType searchUseType = SearchUseTypeFactory.getInstance().create();
        SearchUseTypeDetail searchUseTypeDetail = SearchUseTypeDetailFactory.getInstance().create(searchUseType, searchUseTypeName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        searchUseType = SearchUseTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, searchUseType.getPrimaryKey());
        searchUseType.setActiveDetail(searchUseTypeDetail);
        searchUseType.setLastDetail(searchUseTypeDetail);
        searchUseType.store();

        sendEventUsingNames(searchUseType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return searchUseType;
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.SearchUseType */
    public SearchUseType getSearchUseTypeByEntityInstance(EntityInstance entityInstance) {
        SearchUseTypePK pk = new SearchUseTypePK(entityInstance.getEntityUniqueId());
        SearchUseType searchUseType = SearchUseTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return searchUseType;
    }

    private static final Map<EntityPermission, String> getSearchUseTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM searchusetypes, searchusetypedetails " +
                "WHERE srchutyp_activedetailid = srchutypdt_searchusetypedetailid " +
                "AND srchutypdt_searchusetypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM searchusetypes, searchusetypedetails " +
                "WHERE srchutyp_activedetailid = srchutypdt_searchusetypedetailid " +
                "AND srchutypdt_searchusetypename = ? " +
                "FOR UPDATE");
        getSearchUseTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private SearchUseType getSearchUseTypeByName(String searchUseTypeName, EntityPermission entityPermission) {
        return SearchUseTypeFactory.getInstance().getEntityFromQuery(entityPermission, getSearchUseTypeByNameQueries, searchUseTypeName);
    }

    public SearchUseType getSearchUseTypeByName(String searchUseTypeName) {
        return getSearchUseTypeByName(searchUseTypeName, EntityPermission.READ_ONLY);
    }

    public SearchUseType getSearchUseTypeByNameForUpdate(String searchUseTypeName) {
        return getSearchUseTypeByName(searchUseTypeName, EntityPermission.READ_WRITE);
    }

    public SearchUseTypeDetailValue getSearchUseTypeDetailValueForUpdate(SearchUseType searchUseType) {
        return searchUseType == null? null: searchUseType.getLastDetailForUpdate().getSearchUseTypeDetailValue().clone();
    }

    public SearchUseTypeDetailValue getSearchUseTypeDetailValueByNameForUpdate(String searchUseTypeName) {
        return getSearchUseTypeDetailValueForUpdate(getSearchUseTypeByNameForUpdate(searchUseTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultSearchUseTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM searchusetypes, searchusetypedetails " +
                "WHERE srchutyp_activedetailid = srchutypdt_searchusetypedetailid " +
                "AND srchutypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM searchusetypes, searchusetypedetails " +
                "WHERE srchutyp_activedetailid = srchutypdt_searchusetypedetailid " +
                "AND srchutypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultSearchUseTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private SearchUseType getDefaultSearchUseType(EntityPermission entityPermission) {
        return SearchUseTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultSearchUseTypeQueries);
    }

    public SearchUseType getDefaultSearchUseType() {
        return getDefaultSearchUseType(EntityPermission.READ_ONLY);
    }

    public SearchUseType getDefaultSearchUseTypeForUpdate() {
        return getDefaultSearchUseType(EntityPermission.READ_WRITE);
    }

    public SearchUseTypeDetailValue getDefaultSearchUseTypeDetailValueForUpdate() {
        return getDefaultSearchUseTypeForUpdate().getLastDetailForUpdate().getSearchUseTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getSearchUseTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM searchusetypes, searchusetypedetails " +
                "WHERE srchutyp_activedetailid = srchutypdt_searchusetypedetailid " +
                "ORDER BY srchutypdt_sortorder, srchutypdt_searchusetypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM searchusetypes, searchusetypedetails " +
                "WHERE srchutyp_activedetailid = srchutypdt_searchusetypedetailid " +
                "FOR UPDATE");
        getSearchUseTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<SearchUseType> getSearchUseTypes(EntityPermission entityPermission) {
        return SearchUseTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getSearchUseTypesQueries);
    }

    public List<SearchUseType> getSearchUseTypes() {
        return getSearchUseTypes(EntityPermission.READ_ONLY);
    }

    public List<SearchUseType> getSearchUseTypesForUpdate() {
        return getSearchUseTypes(EntityPermission.READ_WRITE);
    }

   public SearchUseTypeTransfer getSearchUseTypeTransfer(UserVisit userVisit, SearchUseType searchUseType) {
        return getSearchTransferCaches(userVisit).getSearchUseTypeTransferCache().getSearchUseTypeTransfer(searchUseType);
    }

    public List<SearchUseTypeTransfer> getSearchUseTypeTransfers(UserVisit userVisit) {
        List<SearchUseType> searchUseTypes = getSearchUseTypes();
        List<SearchUseTypeTransfer> searchUseTypeTransfers = new ArrayList<>(searchUseTypes.size());
        SearchUseTypeTransferCache searchUseTypeTransferCache = getSearchTransferCaches(userVisit).getSearchUseTypeTransferCache();

        searchUseTypes.stream().forEach((searchUseType) -> {
            searchUseTypeTransfers.add(searchUseTypeTransferCache.getSearchUseTypeTransfer(searchUseType));
        });

        return searchUseTypeTransfers;
    }

    public SearchUseTypeChoicesBean getSearchUseTypeChoices(String defaultSearchUseTypeChoice, Language language, boolean allowNullChoice) {
        List<SearchUseType> searchUseTypes = getSearchUseTypes();
        int size = searchUseTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultSearchUseTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(SearchUseType searchUseType: searchUseTypes) {
            SearchUseTypeDetail searchUseTypeDetail = searchUseType.getLastDetail();

            String label = getBestSearchUseTypeDescription(searchUseType, language);
            String value = searchUseTypeDetail.getSearchUseTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultSearchUseTypeChoice == null? false: defaultSearchUseTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && searchUseTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new SearchUseTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateSearchUseTypeFromValue(SearchUseTypeDetailValue searchUseTypeDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(searchUseTypeDetailValue.hasBeenModified()) {
            SearchUseType searchUseType = SearchUseTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     searchUseTypeDetailValue.getSearchUseTypePK());
            SearchUseTypeDetail searchUseTypeDetail = searchUseType.getActiveDetailForUpdate();

            searchUseTypeDetail.setThruTime(session.START_TIME_LONG);
            searchUseTypeDetail.store();

            SearchUseTypePK searchUseTypePK = searchUseTypeDetail.getSearchUseTypePK(); // Not updated
            String searchUseTypeName = searchUseTypeDetailValue.getSearchUseTypeName();
            Boolean isDefault = searchUseTypeDetailValue.getIsDefault();
            Integer sortOrder = searchUseTypeDetailValue.getSortOrder();

            if(checkDefault) {
                SearchUseType defaultSearchUseType = getDefaultSearchUseType();
                boolean defaultFound = defaultSearchUseType != null && !defaultSearchUseType.equals(searchUseType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    SearchUseTypeDetailValue defaultSearchUseTypeDetailValue = getDefaultSearchUseTypeDetailValueForUpdate();

                    defaultSearchUseTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateSearchUseTypeFromValue(defaultSearchUseTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            searchUseTypeDetail = SearchUseTypeDetailFactory.getInstance().create(searchUseTypePK, searchUseTypeName, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            searchUseType.setActiveDetail(searchUseTypeDetail);
            searchUseType.setLastDetail(searchUseTypeDetail);

            sendEventUsingNames(searchUseTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateSearchUseTypeFromValue(SearchUseTypeDetailValue searchUseTypeDetailValue, BasePK updatedBy) {
        updateSearchUseTypeFromValue(searchUseTypeDetailValue, true, updatedBy);
    }

    private void deleteSearchUseType(SearchUseType searchUseType, boolean checkDefault, BasePK deletedBy) {
        SearchUseTypeDetail searchUseTypeDetail = searchUseType.getLastDetailForUpdate();

        deleteSearchUseTypeDescriptionsBySearchUseType(searchUseType, deletedBy);
        deleteSearchesBySearchUseType(searchUseType, deletedBy);
        
        searchUseTypeDetail.setThruTime(session.START_TIME_LONG);
        searchUseType.setActiveDetail(null);
        searchUseType.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            SearchUseType defaultSearchUseType = getDefaultSearchUseType();

            if(defaultSearchUseType == null) {
                List<SearchUseType> searchUseTypes = getSearchUseTypesForUpdate();

                if(!searchUseTypes.isEmpty()) {
                    Iterator<SearchUseType> iter = searchUseTypes.iterator();
                    if(iter.hasNext()) {
                        defaultSearchUseType = iter.next();
                    }
                    SearchUseTypeDetailValue searchUseTypeDetailValue = defaultSearchUseType.getLastDetailForUpdate().getSearchUseTypeDetailValue().clone();

                    searchUseTypeDetailValue.setIsDefault(Boolean.TRUE);
                    updateSearchUseTypeFromValue(searchUseTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(searchUseType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteSearchUseType(SearchUseType searchUseType, BasePK deletedBy) {
        deleteSearchUseType(searchUseType, true, deletedBy);
    }

    private void deleteSearchUseTypes(List<SearchUseType> searchUseTypes, boolean checkDefault, BasePK deletedBy) {
        searchUseTypes.stream().forEach((searchUseType) -> {
            deleteSearchUseType(searchUseType, checkDefault, deletedBy);
        });
    }

    public void deleteSearchUseTypes(List<SearchUseType> searchUseTypes, BasePK deletedBy) {
        deleteSearchUseTypes(searchUseTypes, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Search Use Type Descriptions
    // --------------------------------------------------------------------------------

    public SearchUseTypeDescription createSearchUseTypeDescription(SearchUseType searchUseType, Language language, String description, BasePK createdBy) {
        SearchUseTypeDescription searchUseTypeDescription = SearchUseTypeDescriptionFactory.getInstance().create(searchUseType, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(searchUseType.getPrimaryKey(), EventTypes.MODIFY.name(), searchUseTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return searchUseTypeDescription;
    }

    private static final Map<EntityPermission, String> getSearchUseTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM searchusetypedescriptions " +
                "WHERE srchutypd_srchutyp_searchusetypeid = ? AND srchutypd_lang_languageid = ? AND srchutypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM searchusetypedescriptions " +
                "WHERE srchutypd_srchutyp_searchusetypeid = ? AND srchutypd_lang_languageid = ? AND srchutypd_thrutime = ? " +
                "FOR UPDATE");
        getSearchUseTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private SearchUseTypeDescription getSearchUseTypeDescription(SearchUseType searchUseType, Language language, EntityPermission entityPermission) {
        return SearchUseTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getSearchUseTypeDescriptionQueries,
                searchUseType, language, Session.MAX_TIME);
    }

    public SearchUseTypeDescription getSearchUseTypeDescription(SearchUseType searchUseType, Language language) {
        return getSearchUseTypeDescription(searchUseType, language, EntityPermission.READ_ONLY);
    }

    public SearchUseTypeDescription getSearchUseTypeDescriptionForUpdate(SearchUseType searchUseType, Language language) {
        return getSearchUseTypeDescription(searchUseType, language, EntityPermission.READ_WRITE);
    }

    public SearchUseTypeDescriptionValue getSearchUseTypeDescriptionValue(SearchUseTypeDescription searchUseTypeDescription) {
        return searchUseTypeDescription == null? null: searchUseTypeDescription.getSearchUseTypeDescriptionValue().clone();
    }

    public SearchUseTypeDescriptionValue getSearchUseTypeDescriptionValueForUpdate(SearchUseType searchUseType, Language language) {
        return getSearchUseTypeDescriptionValue(getSearchUseTypeDescriptionForUpdate(searchUseType, language));
    }

    private static final Map<EntityPermission, String> getSearchUseTypeDescriptionsBySearchUseTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM searchusetypedescriptions, languages " +
                "WHERE srchutypd_srchutyp_searchusetypeid = ? AND srchutypd_thrutime = ? AND srchutypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM searchusetypedescriptions " +
                "WHERE srchutypd_srchutyp_searchusetypeid = ? AND srchutypd_thrutime = ? " +
                "FOR UPDATE");
        getSearchUseTypeDescriptionsBySearchUseTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<SearchUseTypeDescription> getSearchUseTypeDescriptionsBySearchUseType(SearchUseType searchUseType, EntityPermission entityPermission) {
        return SearchUseTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getSearchUseTypeDescriptionsBySearchUseTypeQueries,
                searchUseType, Session.MAX_TIME);
    }

    public List<SearchUseTypeDescription> getSearchUseTypeDescriptionsBySearchUseType(SearchUseType searchUseType) {
        return getSearchUseTypeDescriptionsBySearchUseType(searchUseType, EntityPermission.READ_ONLY);
    }

    public List<SearchUseTypeDescription> getSearchUseTypeDescriptionsBySearchUseTypeForUpdate(SearchUseType searchUseType) {
        return getSearchUseTypeDescriptionsBySearchUseType(searchUseType, EntityPermission.READ_WRITE);
    }

    public String getBestSearchUseTypeDescription(SearchUseType searchUseType, Language language) {
        String description;
        SearchUseTypeDescription searchUseTypeDescription = getSearchUseTypeDescription(searchUseType, language);

        if(searchUseTypeDescription == null && !language.getIsDefault()) {
            searchUseTypeDescription = getSearchUseTypeDescription(searchUseType, getPartyControl().getDefaultLanguage());
        }

        if(searchUseTypeDescription == null) {
            description = searchUseType.getLastDetail().getSearchUseTypeName();
        } else {
            description = searchUseTypeDescription.getDescription();
        }

        return description;
    }

    public SearchUseTypeDescriptionTransfer getSearchUseTypeDescriptionTransfer(UserVisit userVisit, SearchUseTypeDescription searchUseTypeDescription) {
        return getSearchTransferCaches(userVisit).getSearchUseTypeDescriptionTransferCache().getSearchUseTypeDescriptionTransfer(searchUseTypeDescription);
    }

    public List<SearchUseTypeDescriptionTransfer> getSearchUseTypeDescriptionTransfersBySearchUseType(UserVisit userVisit, SearchUseType searchUseType) {
        List<SearchUseTypeDescription> searchUseTypeDescriptions = getSearchUseTypeDescriptionsBySearchUseType(searchUseType);
        List<SearchUseTypeDescriptionTransfer> searchUseTypeDescriptionTransfers = new ArrayList<>(searchUseTypeDescriptions.size());
        SearchUseTypeDescriptionTransferCache searchUseTypeDescriptionTransferCache = getSearchTransferCaches(userVisit).getSearchUseTypeDescriptionTransferCache();

        searchUseTypeDescriptions.stream().forEach((searchUseTypeDescription) -> {
            searchUseTypeDescriptionTransfers.add(searchUseTypeDescriptionTransferCache.getSearchUseTypeDescriptionTransfer(searchUseTypeDescription));
        });

        return searchUseTypeDescriptionTransfers;
    }

    public void updateSearchUseTypeDescriptionFromValue(SearchUseTypeDescriptionValue searchUseTypeDescriptionValue, BasePK updatedBy) {
        if(searchUseTypeDescriptionValue.hasBeenModified()) {
            SearchUseTypeDescription searchUseTypeDescription = SearchUseTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    searchUseTypeDescriptionValue.getPrimaryKey());

            searchUseTypeDescription.setThruTime(session.START_TIME_LONG);
            searchUseTypeDescription.store();

            SearchUseType searchUseType = searchUseTypeDescription.getSearchUseType();
            Language language = searchUseTypeDescription.getLanguage();
            String description = searchUseTypeDescriptionValue.getDescription();

            searchUseTypeDescription = SearchUseTypeDescriptionFactory.getInstance().create(searchUseType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(searchUseType.getPrimaryKey(), EventTypes.MODIFY.name(), searchUseTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteSearchUseTypeDescription(SearchUseTypeDescription searchUseTypeDescription, BasePK deletedBy) {
        searchUseTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(searchUseTypeDescription.getSearchUseTypePK(), EventTypes.MODIFY.name(), searchUseTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteSearchUseTypeDescriptionsBySearchUseType(SearchUseType searchUseType, BasePK deletedBy) {
        List<SearchUseTypeDescription> searchUseTypeDescriptions = getSearchUseTypeDescriptionsBySearchUseTypeForUpdate(searchUseType);

        searchUseTypeDescriptions.stream().forEach((searchUseTypeDescription) -> {
            deleteSearchUseTypeDescription(searchUseTypeDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Search Result Action Types
    // --------------------------------------------------------------------------------

    public SearchResultActionType createSearchResultActionType(String searchResultActionTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        SearchResultActionType defaultSearchResultActionType = getDefaultSearchResultActionType();
        boolean defaultFound = defaultSearchResultActionType != null;

        if(defaultFound && isDefault) {
            SearchResultActionTypeDetailValue defaultSearchResultActionTypeDetailValue = getDefaultSearchResultActionTypeDetailValueForUpdate();

            defaultSearchResultActionTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateSearchResultActionTypeFromValue(defaultSearchResultActionTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        SearchResultActionType searchResultActionType = SearchResultActionTypeFactory.getInstance().create();
        SearchResultActionTypeDetail searchResultActionTypeDetail = SearchResultActionTypeDetailFactory.getInstance().create(searchResultActionType, searchResultActionTypeName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        searchResultActionType = SearchResultActionTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, searchResultActionType.getPrimaryKey());
        searchResultActionType.setActiveDetail(searchResultActionTypeDetail);
        searchResultActionType.setLastDetail(searchResultActionTypeDetail);
        searchResultActionType.store();

        sendEventUsingNames(searchResultActionType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return searchResultActionType;
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.SearchResultActionType */
    public SearchResultActionType getSearchResultActionTypeByEntityInstance(EntityInstance entityInstance) {
        SearchResultActionTypePK pk = new SearchResultActionTypePK(entityInstance.getEntityUniqueId());
        SearchResultActionType searchResultActionType = SearchResultActionTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return searchResultActionType;
    }

    private static final Map<EntityPermission, String> getSearchResultActionTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM searchresultactiontypes, searchresultactiontypedetails " +
                "WHERE srchracttyp_activedetailid = srchracttypdt_searchresultactiontypedetailid " +
                "AND srchracttypdt_searchresultactiontypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM searchresultactiontypes, searchresultactiontypedetails " +
                "WHERE srchracttyp_activedetailid = srchracttypdt_searchresultactiontypedetailid " +
                "AND srchracttypdt_searchresultactiontypename = ? " +
                "FOR UPDATE");
        getSearchResultActionTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private SearchResultActionType getSearchResultActionTypeByName(String searchResultActionTypeName, EntityPermission entityPermission) {
        return SearchResultActionTypeFactory.getInstance().getEntityFromQuery(entityPermission, getSearchResultActionTypeByNameQueries, searchResultActionTypeName);
    }

    public SearchResultActionType getSearchResultActionTypeByName(String searchResultActionTypeName) {
        return getSearchResultActionTypeByName(searchResultActionTypeName, EntityPermission.READ_ONLY);
    }

    public SearchResultActionType getSearchResultActionTypeByNameForUpdate(String searchResultActionTypeName) {
        return getSearchResultActionTypeByName(searchResultActionTypeName, EntityPermission.READ_WRITE);
    }

    public SearchResultActionTypeDetailValue getSearchResultActionTypeDetailValueForUpdate(SearchResultActionType searchResultActionType) {
        return searchResultActionType == null? null: searchResultActionType.getLastDetailForUpdate().getSearchResultActionTypeDetailValue().clone();
    }

    public SearchResultActionTypeDetailValue getSearchResultActionTypeDetailValueByNameForUpdate(String searchResultActionTypeName) {
        return getSearchResultActionTypeDetailValueForUpdate(getSearchResultActionTypeByNameForUpdate(searchResultActionTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultSearchResultActionTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM searchresultactiontypes, searchresultactiontypedetails " +
                "WHERE srchracttyp_activedetailid = srchracttypdt_searchresultactiontypedetailid " +
                "AND srchracttypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM searchresultactiontypes, searchresultactiontypedetails " +
                "WHERE srchracttyp_activedetailid = srchracttypdt_searchresultactiontypedetailid " +
                "AND srchracttypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultSearchResultActionTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private SearchResultActionType getDefaultSearchResultActionType(EntityPermission entityPermission) {
        return SearchResultActionTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultSearchResultActionTypeQueries);
    }

    public SearchResultActionType getDefaultSearchResultActionType() {
        return getDefaultSearchResultActionType(EntityPermission.READ_ONLY);
    }

    public SearchResultActionType getDefaultSearchResultActionTypeForUpdate() {
        return getDefaultSearchResultActionType(EntityPermission.READ_WRITE);
    }

    public SearchResultActionTypeDetailValue getDefaultSearchResultActionTypeDetailValueForUpdate() {
        return getDefaultSearchResultActionTypeForUpdate().getLastDetailForUpdate().getSearchResultActionTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getSearchResultActionTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM searchresultactiontypes, searchresultactiontypedetails " +
                "WHERE srchracttyp_activedetailid = srchracttypdt_searchresultactiontypedetailid " +
                "ORDER BY srchracttypdt_sortorder, srchracttypdt_searchresultactiontypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM searchresultactiontypes, searchresultactiontypedetails " +
                "WHERE srchracttyp_activedetailid = srchracttypdt_searchresultactiontypedetailid " +
                "FOR UPDATE");
        getSearchResultActionTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<SearchResultActionType> getSearchResultActionTypes(EntityPermission entityPermission) {
        return SearchResultActionTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getSearchResultActionTypesQueries);
    }

    public List<SearchResultActionType> getSearchResultActionTypes() {
        return getSearchResultActionTypes(EntityPermission.READ_ONLY);
    }

    public List<SearchResultActionType> getSearchResultActionTypesForUpdate() {
        return getSearchResultActionTypes(EntityPermission.READ_WRITE);
    }

   public SearchResultActionTypeTransfer getSearchResultActionTypeTransfer(UserVisit userVisit, SearchResultActionType searchResultActionType) {
        return getSearchTransferCaches(userVisit).getSearchResultActionTypeTransferCache().getSearchResultActionTypeTransfer(searchResultActionType);
    }

    public List<SearchResultActionTypeTransfer> getSearchResultActionTypeTransfers(UserVisit userVisit) {
        List<SearchResultActionType> searchResultActionTypes = getSearchResultActionTypes();
        List<SearchResultActionTypeTransfer> searchResultActionTypeTransfers = new ArrayList<>(searchResultActionTypes.size());
        SearchResultActionTypeTransferCache searchResultActionTypeTransferCache = getSearchTransferCaches(userVisit).getSearchResultActionTypeTransferCache();

        searchResultActionTypes.stream().forEach((searchResultActionType) -> {
            searchResultActionTypeTransfers.add(searchResultActionTypeTransferCache.getSearchResultActionTypeTransfer(searchResultActionType));
        });

        return searchResultActionTypeTransfers;
    }

    public SearchResultActionTypeChoicesBean getSearchResultActionTypeChoices(String defaultSearchResultActionTypeChoice, Language language, boolean allowNullChoice) {
        List<SearchResultActionType> searchResultActionTypes = getSearchResultActionTypes();
        int size = searchResultActionTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultSearchResultActionTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(SearchResultActionType searchResultActionType: searchResultActionTypes) {
            SearchResultActionTypeDetail searchResultActionTypeDetail = searchResultActionType.getLastDetail();

            String label = getBestSearchResultActionTypeDescription(searchResultActionType, language);
            String value = searchResultActionTypeDetail.getSearchResultActionTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultSearchResultActionTypeChoice == null? false: defaultSearchResultActionTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && searchResultActionTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new SearchResultActionTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateSearchResultActionTypeFromValue(SearchResultActionTypeDetailValue searchResultActionTypeDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(searchResultActionTypeDetailValue.hasBeenModified()) {
            SearchResultActionType searchResultActionType = SearchResultActionTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     searchResultActionTypeDetailValue.getSearchResultActionTypePK());
            SearchResultActionTypeDetail searchResultActionTypeDetail = searchResultActionType.getActiveDetailForUpdate();

            searchResultActionTypeDetail.setThruTime(session.START_TIME_LONG);
            searchResultActionTypeDetail.store();

            SearchResultActionTypePK searchResultActionTypePK = searchResultActionTypeDetail.getSearchResultActionTypePK(); // Not updated
            String searchResultActionTypeName = searchResultActionTypeDetailValue.getSearchResultActionTypeName();
            Boolean isDefault = searchResultActionTypeDetailValue.getIsDefault();
            Integer sortOrder = searchResultActionTypeDetailValue.getSortOrder();

            if(checkDefault) {
                SearchResultActionType defaultSearchResultActionType = getDefaultSearchResultActionType();
                boolean defaultFound = defaultSearchResultActionType != null && !defaultSearchResultActionType.equals(searchResultActionType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    SearchResultActionTypeDetailValue defaultSearchResultActionTypeDetailValue = getDefaultSearchResultActionTypeDetailValueForUpdate();

                    defaultSearchResultActionTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateSearchResultActionTypeFromValue(defaultSearchResultActionTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            searchResultActionTypeDetail = SearchResultActionTypeDetailFactory.getInstance().create(searchResultActionTypePK, searchResultActionTypeName, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            searchResultActionType.setActiveDetail(searchResultActionTypeDetail);
            searchResultActionType.setLastDetail(searchResultActionTypeDetail);

            sendEventUsingNames(searchResultActionTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateSearchResultActionTypeFromValue(SearchResultActionTypeDetailValue searchResultActionTypeDetailValue, BasePK updatedBy) {
        updateSearchResultActionTypeFromValue(searchResultActionTypeDetailValue, true, updatedBy);
    }

    private void deleteSearchResultActionType(SearchResultActionType searchResultActionType, boolean checkDefault, BasePK deletedBy) {
        SearchResultActionTypeDetail searchResultActionTypeDetail = searchResultActionType.getLastDetailForUpdate();

        deleteSearchResultActionsBySearchResultActionType(searchResultActionType, deletedBy);
        deleteSearchResultActionTypeDescriptionsBySearchResultActionType(searchResultActionType, deletedBy);
        
        searchResultActionTypeDetail.setThruTime(session.START_TIME_LONG);
        searchResultActionType.setActiveDetail(null);
        searchResultActionType.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            SearchResultActionType defaultSearchResultActionType = getDefaultSearchResultActionType();

            if(defaultSearchResultActionType == null) {
                List<SearchResultActionType> searchResultActionTypes = getSearchResultActionTypesForUpdate();

                if(!searchResultActionTypes.isEmpty()) {
                    Iterator<SearchResultActionType> iter = searchResultActionTypes.iterator();
                    if(iter.hasNext()) {
                        defaultSearchResultActionType = iter.next();
                    }
                    SearchResultActionTypeDetailValue searchResultActionTypeDetailValue = defaultSearchResultActionType.getLastDetailForUpdate().getSearchResultActionTypeDetailValue().clone();

                    searchResultActionTypeDetailValue.setIsDefault(Boolean.TRUE);
                    updateSearchResultActionTypeFromValue(searchResultActionTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(searchResultActionType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteSearchResultActionType(SearchResultActionType searchResultActionType, BasePK deletedBy) {
        deleteSearchResultActionType(searchResultActionType, true, deletedBy);
    }

    private void deleteSearchResultActionTypes(List<SearchResultActionType> searchResultActionTypes, boolean checkDefault, BasePK deletedBy) {
        searchResultActionTypes.stream().forEach((searchResultActionType) -> {
            deleteSearchResultActionType(searchResultActionType, checkDefault, deletedBy);
        });
    }

    public void deleteSearchResultActionTypes(List<SearchResultActionType> searchResultActionTypes, BasePK deletedBy) {
        deleteSearchResultActionTypes(searchResultActionTypes, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Search Result Action Type Descriptions
    // --------------------------------------------------------------------------------

    public SearchResultActionTypeDescription createSearchResultActionTypeDescription(SearchResultActionType searchResultActionType, Language language, String description, BasePK createdBy) {
        SearchResultActionTypeDescription searchResultActionTypeDescription = SearchResultActionTypeDescriptionFactory.getInstance().create(searchResultActionType, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(searchResultActionType.getPrimaryKey(), EventTypes.MODIFY.name(), searchResultActionTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return searchResultActionTypeDescription;
    }

    private static final Map<EntityPermission, String> getSearchResultActionTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM searchresultactiontypedescriptions " +
                "WHERE srchracttypd_srchracttyp_searchresultactiontypeid = ? AND srchracttypd_lang_languageid = ? AND srchracttypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM searchresultactiontypedescriptions " +
                "WHERE srchracttypd_srchracttyp_searchresultactiontypeid = ? AND srchracttypd_lang_languageid = ? AND srchracttypd_thrutime = ? " +
                "FOR UPDATE");
        getSearchResultActionTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private SearchResultActionTypeDescription getSearchResultActionTypeDescription(SearchResultActionType searchResultActionType, Language language, EntityPermission entityPermission) {
        return SearchResultActionTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getSearchResultActionTypeDescriptionQueries,
                searchResultActionType, language, Session.MAX_TIME);
    }

    public SearchResultActionTypeDescription getSearchResultActionTypeDescription(SearchResultActionType searchResultActionType, Language language) {
        return getSearchResultActionTypeDescription(searchResultActionType, language, EntityPermission.READ_ONLY);
    }

    public SearchResultActionTypeDescription getSearchResultActionTypeDescriptionForUpdate(SearchResultActionType searchResultActionType, Language language) {
        return getSearchResultActionTypeDescription(searchResultActionType, language, EntityPermission.READ_WRITE);
    }

    public SearchResultActionTypeDescriptionValue getSearchResultActionTypeDescriptionValue(SearchResultActionTypeDescription searchResultActionTypeDescription) {
        return searchResultActionTypeDescription == null? null: searchResultActionTypeDescription.getSearchResultActionTypeDescriptionValue().clone();
    }

    public SearchResultActionTypeDescriptionValue getSearchResultActionTypeDescriptionValueForUpdate(SearchResultActionType searchResultActionType, Language language) {
        return getSearchResultActionTypeDescriptionValue(getSearchResultActionTypeDescriptionForUpdate(searchResultActionType, language));
    }

    private static final Map<EntityPermission, String> getSearchResultActionTypeDescriptionsBySearchResultActionTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM searchresultactiontypedescriptions, languages " +
                "WHERE srchracttypd_srchracttyp_searchresultactiontypeid = ? AND srchracttypd_thrutime = ? AND srchracttypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM searchresultactiontypedescriptions " +
                "WHERE srchracttypd_srchracttyp_searchresultactiontypeid = ? AND srchracttypd_thrutime = ? " +
                "FOR UPDATE");
        getSearchResultActionTypeDescriptionsBySearchResultActionTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<SearchResultActionTypeDescription> getSearchResultActionTypeDescriptionsBySearchResultActionType(SearchResultActionType searchResultActionType, EntityPermission entityPermission) {
        return SearchResultActionTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getSearchResultActionTypeDescriptionsBySearchResultActionTypeQueries,
                searchResultActionType, Session.MAX_TIME);
    }

    public List<SearchResultActionTypeDescription> getSearchResultActionTypeDescriptionsBySearchResultActionType(SearchResultActionType searchResultActionType) {
        return getSearchResultActionTypeDescriptionsBySearchResultActionType(searchResultActionType, EntityPermission.READ_ONLY);
    }

    public List<SearchResultActionTypeDescription> getSearchResultActionTypeDescriptionsBySearchResultActionTypeForUpdate(SearchResultActionType searchResultActionType) {
        return getSearchResultActionTypeDescriptionsBySearchResultActionType(searchResultActionType, EntityPermission.READ_WRITE);
    }

    public String getBestSearchResultActionTypeDescription(SearchResultActionType searchResultActionType, Language language) {
        String description;
        SearchResultActionTypeDescription searchResultActionTypeDescription = getSearchResultActionTypeDescription(searchResultActionType, language);

        if(searchResultActionTypeDescription == null && !language.getIsDefault()) {
            searchResultActionTypeDescription = getSearchResultActionTypeDescription(searchResultActionType, getPartyControl().getDefaultLanguage());
        }

        if(searchResultActionTypeDescription == null) {
            description = searchResultActionType.getLastDetail().getSearchResultActionTypeName();
        } else {
            description = searchResultActionTypeDescription.getDescription();
        }

        return description;
    }

    public SearchResultActionTypeDescriptionTransfer getSearchResultActionTypeDescriptionTransfer(UserVisit userVisit, SearchResultActionTypeDescription searchResultActionTypeDescription) {
        return getSearchTransferCaches(userVisit).getSearchResultActionTypeDescriptionTransferCache().getSearchResultActionTypeDescriptionTransfer(searchResultActionTypeDescription);
    }

    public List<SearchResultActionTypeDescriptionTransfer> getSearchResultActionTypeDescriptionTransfersBySearchResultActionType(UserVisit userVisit, SearchResultActionType searchResultActionType) {
        List<SearchResultActionTypeDescription> searchResultActionTypeDescriptions = getSearchResultActionTypeDescriptionsBySearchResultActionType(searchResultActionType);
        List<SearchResultActionTypeDescriptionTransfer> searchResultActionTypeDescriptionTransfers = new ArrayList<>(searchResultActionTypeDescriptions.size());
        SearchResultActionTypeDescriptionTransferCache searchResultActionTypeDescriptionTransferCache = getSearchTransferCaches(userVisit).getSearchResultActionTypeDescriptionTransferCache();

        searchResultActionTypeDescriptions.stream().forEach((searchResultActionTypeDescription) -> {
            searchResultActionTypeDescriptionTransfers.add(searchResultActionTypeDescriptionTransferCache.getSearchResultActionTypeDescriptionTransfer(searchResultActionTypeDescription));
        });

        return searchResultActionTypeDescriptionTransfers;
    }

    public void updateSearchResultActionTypeDescriptionFromValue(SearchResultActionTypeDescriptionValue searchResultActionTypeDescriptionValue, BasePK updatedBy) {
        if(searchResultActionTypeDescriptionValue.hasBeenModified()) {
            SearchResultActionTypeDescription searchResultActionTypeDescription = SearchResultActionTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    searchResultActionTypeDescriptionValue.getPrimaryKey());

            searchResultActionTypeDescription.setThruTime(session.START_TIME_LONG);
            searchResultActionTypeDescription.store();

            SearchResultActionType searchResultActionType = searchResultActionTypeDescription.getSearchResultActionType();
            Language language = searchResultActionTypeDescription.getLanguage();
            String description = searchResultActionTypeDescriptionValue.getDescription();

            searchResultActionTypeDescription = SearchResultActionTypeDescriptionFactory.getInstance().create(searchResultActionType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(searchResultActionType.getPrimaryKey(), EventTypes.MODIFY.name(), searchResultActionTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteSearchResultActionTypeDescription(SearchResultActionTypeDescription searchResultActionTypeDescription, BasePK deletedBy) {
        searchResultActionTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(searchResultActionTypeDescription.getSearchResultActionTypePK(), EventTypes.MODIFY.name(), searchResultActionTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteSearchResultActionTypeDescriptionsBySearchResultActionType(SearchResultActionType searchResultActionType, BasePK deletedBy) {
        List<SearchResultActionTypeDescription> searchResultActionTypeDescriptions = getSearchResultActionTypeDescriptionsBySearchResultActionTypeForUpdate(searchResultActionType);

        searchResultActionTypeDescriptions.stream().forEach((searchResultActionTypeDescription) -> {
            deleteSearchResultActionTypeDescription(searchResultActionTypeDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Search Check Spelling Action Types
    // --------------------------------------------------------------------------------

    public SearchCheckSpellingActionType createSearchCheckSpellingActionType(String searchCheckSpellingActionTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        SearchCheckSpellingActionType defaultSearchCheckSpellingActionType = getDefaultSearchCheckSpellingActionType();
        boolean defaultFound = defaultSearchCheckSpellingActionType != null;

        if(defaultFound && isDefault) {
            SearchCheckSpellingActionTypeDetailValue defaultSearchCheckSpellingActionTypeDetailValue = getDefaultSearchCheckSpellingActionTypeDetailValueForUpdate();

            defaultSearchCheckSpellingActionTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateSearchCheckSpellingActionTypeFromValue(defaultSearchCheckSpellingActionTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        SearchCheckSpellingActionType searchCheckSpellingActionType = SearchCheckSpellingActionTypeFactory.getInstance().create();
        SearchCheckSpellingActionTypeDetail searchCheckSpellingActionTypeDetail = SearchCheckSpellingActionTypeDetailFactory.getInstance().create(searchCheckSpellingActionType, searchCheckSpellingActionTypeName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        searchCheckSpellingActionType = SearchCheckSpellingActionTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, searchCheckSpellingActionType.getPrimaryKey());
        searchCheckSpellingActionType.setActiveDetail(searchCheckSpellingActionTypeDetail);
        searchCheckSpellingActionType.setLastDetail(searchCheckSpellingActionTypeDetail);
        searchCheckSpellingActionType.store();

        sendEventUsingNames(searchCheckSpellingActionType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return searchCheckSpellingActionType;
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.SearchCheckSpellingActionType */
    public SearchCheckSpellingActionType getSearchCheckSpellingActionTypeByEntityInstance(EntityInstance entityInstance) {
        SearchCheckSpellingActionTypePK pk = new SearchCheckSpellingActionTypePK(entityInstance.getEntityUniqueId());
        SearchCheckSpellingActionType searchCheckSpellingActionType = SearchCheckSpellingActionTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return searchCheckSpellingActionType;
    }

    private static final Map<EntityPermission, String> getSearchCheckSpellingActionTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM searchcheckspellingactiontypes, searchcheckspellingactiontypedetails " +
                "WHERE srchcksacttyp_activedetailid = srchcksacttypdt_searchcheckspellingactiontypedetailid " +
                "AND srchcksacttypdt_searchcheckspellingactiontypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM searchcheckspellingactiontypes, searchcheckspellingactiontypedetails " +
                "WHERE srchcksacttyp_activedetailid = srchcksacttypdt_searchcheckspellingactiontypedetailid " +
                "AND srchcksacttypdt_searchcheckspellingactiontypename = ? " +
                "FOR UPDATE");
        getSearchCheckSpellingActionTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private SearchCheckSpellingActionType getSearchCheckSpellingActionTypeByName(String searchCheckSpellingActionTypeName, EntityPermission entityPermission) {
        return SearchCheckSpellingActionTypeFactory.getInstance().getEntityFromQuery(entityPermission, getSearchCheckSpellingActionTypeByNameQueries, searchCheckSpellingActionTypeName);
    }

    public SearchCheckSpellingActionType getSearchCheckSpellingActionTypeByName(String searchCheckSpellingActionTypeName) {
        return getSearchCheckSpellingActionTypeByName(searchCheckSpellingActionTypeName, EntityPermission.READ_ONLY);
    }

    public SearchCheckSpellingActionType getSearchCheckSpellingActionTypeByNameForUpdate(String searchCheckSpellingActionTypeName) {
        return getSearchCheckSpellingActionTypeByName(searchCheckSpellingActionTypeName, EntityPermission.READ_WRITE);
    }

    public SearchCheckSpellingActionTypeDetailValue getSearchCheckSpellingActionTypeDetailValueForUpdate(SearchCheckSpellingActionType searchCheckSpellingActionType) {
        return searchCheckSpellingActionType == null? null: searchCheckSpellingActionType.getLastDetailForUpdate().getSearchCheckSpellingActionTypeDetailValue().clone();
    }

    public SearchCheckSpellingActionTypeDetailValue getSearchCheckSpellingActionTypeDetailValueByNameForUpdate(String searchCheckSpellingActionTypeName) {
        return getSearchCheckSpellingActionTypeDetailValueForUpdate(getSearchCheckSpellingActionTypeByNameForUpdate(searchCheckSpellingActionTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultSearchCheckSpellingActionTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM searchcheckspellingactiontypes, searchcheckspellingactiontypedetails " +
                "WHERE srchcksacttyp_activedetailid = srchcksacttypdt_searchcheckspellingactiontypedetailid " +
                "AND srchcksacttypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM searchcheckspellingactiontypes, searchcheckspellingactiontypedetails " +
                "WHERE srchcksacttyp_activedetailid = srchcksacttypdt_searchcheckspellingactiontypedetailid " +
                "AND srchcksacttypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultSearchCheckSpellingActionTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private SearchCheckSpellingActionType getDefaultSearchCheckSpellingActionType(EntityPermission entityPermission) {
        return SearchCheckSpellingActionTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultSearchCheckSpellingActionTypeQueries);
    }

    public SearchCheckSpellingActionType getDefaultSearchCheckSpellingActionType() {
        return getDefaultSearchCheckSpellingActionType(EntityPermission.READ_ONLY);
    }

    public SearchCheckSpellingActionType getDefaultSearchCheckSpellingActionTypeForUpdate() {
        return getDefaultSearchCheckSpellingActionType(EntityPermission.READ_WRITE);
    }

    public SearchCheckSpellingActionTypeDetailValue getDefaultSearchCheckSpellingActionTypeDetailValueForUpdate() {
        return getDefaultSearchCheckSpellingActionTypeForUpdate().getLastDetailForUpdate().getSearchCheckSpellingActionTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getSearchCheckSpellingActionTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM searchcheckspellingactiontypes, searchcheckspellingactiontypedetails " +
                "WHERE srchcksacttyp_activedetailid = srchcksacttypdt_searchcheckspellingactiontypedetailid " +
                "ORDER BY srchcksacttypdt_sortorder, srchcksacttypdt_searchcheckspellingactiontypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM searchcheckspellingactiontypes, searchcheckspellingactiontypedetails " +
                "WHERE srchcksacttyp_activedetailid = srchcksacttypdt_searchcheckspellingactiontypedetailid " +
                "FOR UPDATE");
        getSearchCheckSpellingActionTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<SearchCheckSpellingActionType> getSearchCheckSpellingActionTypes(EntityPermission entityPermission) {
        return SearchCheckSpellingActionTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getSearchCheckSpellingActionTypesQueries);
    }

    public List<SearchCheckSpellingActionType> getSearchCheckSpellingActionTypes() {
        return getSearchCheckSpellingActionTypes(EntityPermission.READ_ONLY);
    }

    public List<SearchCheckSpellingActionType> getSearchCheckSpellingActionTypesForUpdate() {
        return getSearchCheckSpellingActionTypes(EntityPermission.READ_WRITE);
    }

   public SearchCheckSpellingActionTypeTransfer getSearchCheckSpellingActionTypeTransfer(UserVisit userVisit, SearchCheckSpellingActionType searchCheckSpellingActionType) {
        return getSearchTransferCaches(userVisit).getSearchCheckSpellingActionTypeTransferCache().getSearchCheckSpellingActionTypeTransfer(searchCheckSpellingActionType);
    }

    public List<SearchCheckSpellingActionTypeTransfer> getSearchCheckSpellingActionTypeTransfers(UserVisit userVisit) {
        List<SearchCheckSpellingActionType> searchCheckSpellingActionTypes = getSearchCheckSpellingActionTypes();
        List<SearchCheckSpellingActionTypeTransfer> searchCheckSpellingActionTypeTransfers = new ArrayList<>(searchCheckSpellingActionTypes.size());
        SearchCheckSpellingActionTypeTransferCache searchCheckSpellingActionTypeTransferCache = getSearchTransferCaches(userVisit).getSearchCheckSpellingActionTypeTransferCache();

        searchCheckSpellingActionTypes.stream().forEach((searchCheckSpellingActionType) -> {
            searchCheckSpellingActionTypeTransfers.add(searchCheckSpellingActionTypeTransferCache.getSearchCheckSpellingActionTypeTransfer(searchCheckSpellingActionType));
        });

        return searchCheckSpellingActionTypeTransfers;
    }

    public SearchCheckSpellingActionTypeChoicesBean getSearchCheckSpellingActionTypeChoices(String defaultSearchCheckSpellingActionTypeChoice, Language language, boolean allowNullChoice) {
        List<SearchCheckSpellingActionType> searchCheckSpellingActionTypes = getSearchCheckSpellingActionTypes();
        int size = searchCheckSpellingActionTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultSearchCheckSpellingActionTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(SearchCheckSpellingActionType searchCheckSpellingActionType: searchCheckSpellingActionTypes) {
            SearchCheckSpellingActionTypeDetail searchCheckSpellingActionTypeDetail = searchCheckSpellingActionType.getLastDetail();

            String label = getBestSearchCheckSpellingActionTypeDescription(searchCheckSpellingActionType, language);
            String value = searchCheckSpellingActionTypeDetail.getSearchCheckSpellingActionTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultSearchCheckSpellingActionTypeChoice == null? false: defaultSearchCheckSpellingActionTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && searchCheckSpellingActionTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new SearchCheckSpellingActionTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateSearchCheckSpellingActionTypeFromValue(SearchCheckSpellingActionTypeDetailValue searchCheckSpellingActionTypeDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(searchCheckSpellingActionTypeDetailValue.hasBeenModified()) {
            SearchCheckSpellingActionType searchCheckSpellingActionType = SearchCheckSpellingActionTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     searchCheckSpellingActionTypeDetailValue.getSearchCheckSpellingActionTypePK());
            SearchCheckSpellingActionTypeDetail searchCheckSpellingActionTypeDetail = searchCheckSpellingActionType.getActiveDetailForUpdate();

            searchCheckSpellingActionTypeDetail.setThruTime(session.START_TIME_LONG);
            searchCheckSpellingActionTypeDetail.store();

            SearchCheckSpellingActionTypePK searchCheckSpellingActionTypePK = searchCheckSpellingActionTypeDetail.getSearchCheckSpellingActionTypePK(); // Not updated
            String searchCheckSpellingActionTypeName = searchCheckSpellingActionTypeDetailValue.getSearchCheckSpellingActionTypeName();
            Boolean isDefault = searchCheckSpellingActionTypeDetailValue.getIsDefault();
            Integer sortOrder = searchCheckSpellingActionTypeDetailValue.getSortOrder();

            if(checkDefault) {
                SearchCheckSpellingActionType defaultSearchCheckSpellingActionType = getDefaultSearchCheckSpellingActionType();
                boolean defaultFound = defaultSearchCheckSpellingActionType != null && !defaultSearchCheckSpellingActionType.equals(searchCheckSpellingActionType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    SearchCheckSpellingActionTypeDetailValue defaultSearchCheckSpellingActionTypeDetailValue = getDefaultSearchCheckSpellingActionTypeDetailValueForUpdate();

                    defaultSearchCheckSpellingActionTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateSearchCheckSpellingActionTypeFromValue(defaultSearchCheckSpellingActionTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            searchCheckSpellingActionTypeDetail = SearchCheckSpellingActionTypeDetailFactory.getInstance().create(searchCheckSpellingActionTypePK, searchCheckSpellingActionTypeName, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            searchCheckSpellingActionType.setActiveDetail(searchCheckSpellingActionTypeDetail);
            searchCheckSpellingActionType.setLastDetail(searchCheckSpellingActionTypeDetail);

            sendEventUsingNames(searchCheckSpellingActionTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateSearchCheckSpellingActionTypeFromValue(SearchCheckSpellingActionTypeDetailValue searchCheckSpellingActionTypeDetailValue, BasePK updatedBy) {
        updateSearchCheckSpellingActionTypeFromValue(searchCheckSpellingActionTypeDetailValue, true, updatedBy);
    }

    private void deleteSearchCheckSpellingActionType(SearchCheckSpellingActionType searchCheckSpellingActionType, boolean checkDefault, BasePK deletedBy) {
        SearchCheckSpellingActionTypeDetail searchCheckSpellingActionTypeDetail = searchCheckSpellingActionType.getLastDetailForUpdate();

        deleteSearchCheckSpellingActionTypeDescriptionsBySearchCheckSpellingActionType(searchCheckSpellingActionType, deletedBy);
        
        searchCheckSpellingActionTypeDetail.setThruTime(session.START_TIME_LONG);
        searchCheckSpellingActionType.setActiveDetail(null);
        searchCheckSpellingActionType.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            SearchCheckSpellingActionType defaultSearchCheckSpellingActionType = getDefaultSearchCheckSpellingActionType();

            if(defaultSearchCheckSpellingActionType == null) {
                List<SearchCheckSpellingActionType> searchCheckSpellingActionTypes = getSearchCheckSpellingActionTypesForUpdate();

                if(!searchCheckSpellingActionTypes.isEmpty()) {
                    Iterator<SearchCheckSpellingActionType> iter = searchCheckSpellingActionTypes.iterator();
                    if(iter.hasNext()) {
                        defaultSearchCheckSpellingActionType = iter.next();
                    }
                    SearchCheckSpellingActionTypeDetailValue searchCheckSpellingActionTypeDetailValue = defaultSearchCheckSpellingActionType.getLastDetailForUpdate().getSearchCheckSpellingActionTypeDetailValue().clone();

                    searchCheckSpellingActionTypeDetailValue.setIsDefault(Boolean.TRUE);
                    updateSearchCheckSpellingActionTypeFromValue(searchCheckSpellingActionTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(searchCheckSpellingActionType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteSearchCheckSpellingActionType(SearchCheckSpellingActionType searchCheckSpellingActionType, BasePK deletedBy) {
        deleteSearchCheckSpellingActionType(searchCheckSpellingActionType, true, deletedBy);
    }

    private void deleteSearchCheckSpellingActionTypes(List<SearchCheckSpellingActionType> searchCheckSpellingActionTypes, boolean checkDefault, BasePK deletedBy) {
        searchCheckSpellingActionTypes.stream().forEach((searchCheckSpellingActionType) -> {
            deleteSearchCheckSpellingActionType(searchCheckSpellingActionType, checkDefault, deletedBy);
        });
    }

    public void deleteSearchCheckSpellingActionTypes(List<SearchCheckSpellingActionType> searchCheckSpellingActionTypes, BasePK deletedBy) {
        deleteSearchCheckSpellingActionTypes(searchCheckSpellingActionTypes, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Search Check Spelling Action Type Descriptions
    // --------------------------------------------------------------------------------

    public SearchCheckSpellingActionTypeDescription createSearchCheckSpellingActionTypeDescription(SearchCheckSpellingActionType searchCheckSpellingActionType, Language language, String description, BasePK createdBy) {
        SearchCheckSpellingActionTypeDescription searchCheckSpellingActionTypeDescription = SearchCheckSpellingActionTypeDescriptionFactory.getInstance().create(searchCheckSpellingActionType, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(searchCheckSpellingActionType.getPrimaryKey(), EventTypes.MODIFY.name(), searchCheckSpellingActionTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return searchCheckSpellingActionTypeDescription;
    }

    private static final Map<EntityPermission, String> getSearchCheckSpellingActionTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM searchcheckspellingactiontypedescriptions " +
                "WHERE srchcksacttypd_srchcksacttyp_searchcheckspellingactiontypeid = ? AND srchcksacttypd_lang_languageid = ? AND srchcksacttypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM searchcheckspellingactiontypedescriptions " +
                "WHERE srchcksacttypd_srchcksacttyp_searchcheckspellingactiontypeid = ? AND srchcksacttypd_lang_languageid = ? AND srchcksacttypd_thrutime = ? " +
                "FOR UPDATE");
        getSearchCheckSpellingActionTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private SearchCheckSpellingActionTypeDescription getSearchCheckSpellingActionTypeDescription(SearchCheckSpellingActionType searchCheckSpellingActionType, Language language, EntityPermission entityPermission) {
        return SearchCheckSpellingActionTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getSearchCheckSpellingActionTypeDescriptionQueries,
                searchCheckSpellingActionType, language, Session.MAX_TIME);
    }

    public SearchCheckSpellingActionTypeDescription getSearchCheckSpellingActionTypeDescription(SearchCheckSpellingActionType searchCheckSpellingActionType, Language language) {
        return getSearchCheckSpellingActionTypeDescription(searchCheckSpellingActionType, language, EntityPermission.READ_ONLY);
    }

    public SearchCheckSpellingActionTypeDescription getSearchCheckSpellingActionTypeDescriptionForUpdate(SearchCheckSpellingActionType searchCheckSpellingActionType, Language language) {
        return getSearchCheckSpellingActionTypeDescription(searchCheckSpellingActionType, language, EntityPermission.READ_WRITE);
    }

    public SearchCheckSpellingActionTypeDescriptionValue getSearchCheckSpellingActionTypeDescriptionValue(SearchCheckSpellingActionTypeDescription searchCheckSpellingActionTypeDescription) {
        return searchCheckSpellingActionTypeDescription == null? null: searchCheckSpellingActionTypeDescription.getSearchCheckSpellingActionTypeDescriptionValue().clone();
    }

    public SearchCheckSpellingActionTypeDescriptionValue getSearchCheckSpellingActionTypeDescriptionValueForUpdate(SearchCheckSpellingActionType searchCheckSpellingActionType, Language language) {
        return getSearchCheckSpellingActionTypeDescriptionValue(getSearchCheckSpellingActionTypeDescriptionForUpdate(searchCheckSpellingActionType, language));
    }

    private static final Map<EntityPermission, String> getSearchCheckSpellingActionTypeDescriptionsBySearchCheckSpellingActionTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM searchcheckspellingactiontypedescriptions, languages " +
                "WHERE srchcksacttypd_srchcksacttyp_searchcheckspellingactiontypeid = ? AND srchcksacttypd_thrutime = ? AND srchcksacttypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM searchcheckspellingactiontypedescriptions " +
                "WHERE srchcksacttypd_srchcksacttyp_searchcheckspellingactiontypeid = ? AND srchcksacttypd_thrutime = ? " +
                "FOR UPDATE");
        getSearchCheckSpellingActionTypeDescriptionsBySearchCheckSpellingActionTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<SearchCheckSpellingActionTypeDescription> getSearchCheckSpellingActionTypeDescriptionsBySearchCheckSpellingActionType(SearchCheckSpellingActionType searchCheckSpellingActionType, EntityPermission entityPermission) {
        return SearchCheckSpellingActionTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getSearchCheckSpellingActionTypeDescriptionsBySearchCheckSpellingActionTypeQueries,
                searchCheckSpellingActionType, Session.MAX_TIME);
    }

    public List<SearchCheckSpellingActionTypeDescription> getSearchCheckSpellingActionTypeDescriptionsBySearchCheckSpellingActionType(SearchCheckSpellingActionType searchCheckSpellingActionType) {
        return getSearchCheckSpellingActionTypeDescriptionsBySearchCheckSpellingActionType(searchCheckSpellingActionType, EntityPermission.READ_ONLY);
    }

    public List<SearchCheckSpellingActionTypeDescription> getSearchCheckSpellingActionTypeDescriptionsBySearchCheckSpellingActionTypeForUpdate(SearchCheckSpellingActionType searchCheckSpellingActionType) {
        return getSearchCheckSpellingActionTypeDescriptionsBySearchCheckSpellingActionType(searchCheckSpellingActionType, EntityPermission.READ_WRITE);
    }

    public String getBestSearchCheckSpellingActionTypeDescription(SearchCheckSpellingActionType searchCheckSpellingActionType, Language language) {
        String description;
        SearchCheckSpellingActionTypeDescription searchCheckSpellingActionTypeDescription = getSearchCheckSpellingActionTypeDescription(searchCheckSpellingActionType, language);

        if(searchCheckSpellingActionTypeDescription == null && !language.getIsDefault()) {
            searchCheckSpellingActionTypeDescription = getSearchCheckSpellingActionTypeDescription(searchCheckSpellingActionType, getPartyControl().getDefaultLanguage());
        }

        if(searchCheckSpellingActionTypeDescription == null) {
            description = searchCheckSpellingActionType.getLastDetail().getSearchCheckSpellingActionTypeName();
        } else {
            description = searchCheckSpellingActionTypeDescription.getDescription();
        }

        return description;
    }

    public SearchCheckSpellingActionTypeDescriptionTransfer getSearchCheckSpellingActionTypeDescriptionTransfer(UserVisit userVisit, SearchCheckSpellingActionTypeDescription searchCheckSpellingActionTypeDescription) {
        return getSearchTransferCaches(userVisit).getSearchCheckSpellingActionTypeDescriptionTransferCache().getSearchCheckSpellingActionTypeDescriptionTransfer(searchCheckSpellingActionTypeDescription);
    }

    public List<SearchCheckSpellingActionTypeDescriptionTransfer> getSearchCheckSpellingActionTypeDescriptionTransfersBySearchCheckSpellingActionType(UserVisit userVisit, SearchCheckSpellingActionType searchCheckSpellingActionType) {
        List<SearchCheckSpellingActionTypeDescription> searchCheckSpellingActionTypeDescriptions = getSearchCheckSpellingActionTypeDescriptionsBySearchCheckSpellingActionType(searchCheckSpellingActionType);
        List<SearchCheckSpellingActionTypeDescriptionTransfer> searchCheckSpellingActionTypeDescriptionTransfers = new ArrayList<>(searchCheckSpellingActionTypeDescriptions.size());
        SearchCheckSpellingActionTypeDescriptionTransferCache searchCheckSpellingActionTypeDescriptionTransferCache = getSearchTransferCaches(userVisit).getSearchCheckSpellingActionTypeDescriptionTransferCache();

        searchCheckSpellingActionTypeDescriptions.stream().forEach((searchCheckSpellingActionTypeDescription) -> {
            searchCheckSpellingActionTypeDescriptionTransfers.add(searchCheckSpellingActionTypeDescriptionTransferCache.getSearchCheckSpellingActionTypeDescriptionTransfer(searchCheckSpellingActionTypeDescription));
        });

        return searchCheckSpellingActionTypeDescriptionTransfers;
    }

    public void updateSearchCheckSpellingActionTypeDescriptionFromValue(SearchCheckSpellingActionTypeDescriptionValue searchCheckSpellingActionTypeDescriptionValue, BasePK updatedBy) {
        if(searchCheckSpellingActionTypeDescriptionValue.hasBeenModified()) {
            SearchCheckSpellingActionTypeDescription searchCheckSpellingActionTypeDescription = SearchCheckSpellingActionTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    searchCheckSpellingActionTypeDescriptionValue.getPrimaryKey());

            searchCheckSpellingActionTypeDescription.setThruTime(session.START_TIME_LONG);
            searchCheckSpellingActionTypeDescription.store();

            SearchCheckSpellingActionType searchCheckSpellingActionType = searchCheckSpellingActionTypeDescription.getSearchCheckSpellingActionType();
            Language language = searchCheckSpellingActionTypeDescription.getLanguage();
            String description = searchCheckSpellingActionTypeDescriptionValue.getDescription();

            searchCheckSpellingActionTypeDescription = SearchCheckSpellingActionTypeDescriptionFactory.getInstance().create(searchCheckSpellingActionType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(searchCheckSpellingActionType.getPrimaryKey(), EventTypes.MODIFY.name(), searchCheckSpellingActionTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteSearchCheckSpellingActionTypeDescription(SearchCheckSpellingActionTypeDescription searchCheckSpellingActionTypeDescription, BasePK deletedBy) {
        searchCheckSpellingActionTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(searchCheckSpellingActionTypeDescription.getSearchCheckSpellingActionTypePK(), EventTypes.MODIFY.name(), searchCheckSpellingActionTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteSearchCheckSpellingActionTypeDescriptionsBySearchCheckSpellingActionType(SearchCheckSpellingActionType searchCheckSpellingActionType, BasePK deletedBy) {
        List<SearchCheckSpellingActionTypeDescription> searchCheckSpellingActionTypeDescriptions = getSearchCheckSpellingActionTypeDescriptionsBySearchCheckSpellingActionTypeForUpdate(searchCheckSpellingActionType);

        searchCheckSpellingActionTypeDescriptions.stream().forEach((searchCheckSpellingActionTypeDescription) -> {
            deleteSearchCheckSpellingActionTypeDescription(searchCheckSpellingActionTypeDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Search Default Operators
    // --------------------------------------------------------------------------------

    public SearchDefaultOperator createSearchDefaultOperator(String searchDefaultOperatorName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        SearchDefaultOperator defaultSearchDefaultOperator = getDefaultSearchDefaultOperator();
        boolean defaultFound = defaultSearchDefaultOperator != null;

        if(defaultFound && isDefault) {
            SearchDefaultOperatorDetailValue defaultSearchDefaultOperatorDetailValue = getDefaultSearchDefaultOperatorDetailValueForUpdate();

            defaultSearchDefaultOperatorDetailValue.setIsDefault(Boolean.FALSE);
            updateSearchDefaultOperatorFromValue(defaultSearchDefaultOperatorDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        SearchDefaultOperator searchDefaultOperator = SearchDefaultOperatorFactory.getInstance().create();
        SearchDefaultOperatorDetail searchDefaultOperatorDetail = SearchDefaultOperatorDetailFactory.getInstance().create(searchDefaultOperator, searchDefaultOperatorName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        searchDefaultOperator = SearchDefaultOperatorFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, searchDefaultOperator.getPrimaryKey());
        searchDefaultOperator.setActiveDetail(searchDefaultOperatorDetail);
        searchDefaultOperator.setLastDetail(searchDefaultOperatorDetail);
        searchDefaultOperator.store();

        sendEventUsingNames(searchDefaultOperator.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return searchDefaultOperator;
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.SearchDefaultOperator */
    public SearchDefaultOperator getSearchDefaultOperatorByEntityInstance(EntityInstance entityInstance) {
        SearchDefaultOperatorPK pk = new SearchDefaultOperatorPK(entityInstance.getEntityUniqueId());
        SearchDefaultOperator searchDefaultOperator = SearchDefaultOperatorFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return searchDefaultOperator;
    }

    private static final Map<EntityPermission, String> getSearchDefaultOperatorByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM searchdefaultoperators, searchdefaultoperatordetails " +
                "WHERE srchdefop_activedetailid = srchdefopdt_searchdefaultoperatordetailid " +
                "AND srchdefopdt_searchdefaultoperatorname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM searchdefaultoperators, searchdefaultoperatordetails " +
                "WHERE srchdefop_activedetailid = srchdefopdt_searchdefaultoperatordetailid " +
                "AND srchdefopdt_searchdefaultoperatorname = ? " +
                "FOR UPDATE");
        getSearchDefaultOperatorByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private SearchDefaultOperator getSearchDefaultOperatorByName(String searchDefaultOperatorName, EntityPermission entityPermission) {
        return SearchDefaultOperatorFactory.getInstance().getEntityFromQuery(entityPermission, getSearchDefaultOperatorByNameQueries, searchDefaultOperatorName);
    }

    public SearchDefaultOperator getSearchDefaultOperatorByName(String searchDefaultOperatorName) {
        return getSearchDefaultOperatorByName(searchDefaultOperatorName, EntityPermission.READ_ONLY);
    }

    public SearchDefaultOperator getSearchDefaultOperatorByNameForUpdate(String searchDefaultOperatorName) {
        return getSearchDefaultOperatorByName(searchDefaultOperatorName, EntityPermission.READ_WRITE);
    }

    public SearchDefaultOperatorDetailValue getSearchDefaultOperatorDetailValueForUpdate(SearchDefaultOperator searchDefaultOperator) {
        return searchDefaultOperator == null? null: searchDefaultOperator.getLastDetailForUpdate().getSearchDefaultOperatorDetailValue().clone();
    }

    public SearchDefaultOperatorDetailValue getSearchDefaultOperatorDetailValueByNameForUpdate(String searchDefaultOperatorName) {
        return getSearchDefaultOperatorDetailValueForUpdate(getSearchDefaultOperatorByNameForUpdate(searchDefaultOperatorName));
    }

    private static final Map<EntityPermission, String> getDefaultSearchDefaultOperatorQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM searchdefaultoperators, searchdefaultoperatordetails " +
                "WHERE srchdefop_activedetailid = srchdefopdt_searchdefaultoperatordetailid " +
                "AND srchdefopdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM searchdefaultoperators, searchdefaultoperatordetails " +
                "WHERE srchdefop_activedetailid = srchdefopdt_searchdefaultoperatordetailid " +
                "AND srchdefopdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultSearchDefaultOperatorQueries = Collections.unmodifiableMap(queryMap);
    }

    private SearchDefaultOperator getDefaultSearchDefaultOperator(EntityPermission entityPermission) {
        return SearchDefaultOperatorFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultSearchDefaultOperatorQueries);
    }

    public SearchDefaultOperator getDefaultSearchDefaultOperator() {
        return getDefaultSearchDefaultOperator(EntityPermission.READ_ONLY);
    }

    public SearchDefaultOperator getDefaultSearchDefaultOperatorForUpdate() {
        return getDefaultSearchDefaultOperator(EntityPermission.READ_WRITE);
    }

    public SearchDefaultOperatorDetailValue getDefaultSearchDefaultOperatorDetailValueForUpdate() {
        return getDefaultSearchDefaultOperatorForUpdate().getLastDetailForUpdate().getSearchDefaultOperatorDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getSearchDefaultOperatorsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM searchdefaultoperators, searchdefaultoperatordetails " +
                "WHERE srchdefop_activedetailid = srchdefopdt_searchdefaultoperatordetailid " +
                "ORDER BY srchdefopdt_sortorder, srchdefopdt_searchdefaultoperatorname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM searchdefaultoperators, searchdefaultoperatordetails " +
                "WHERE srchdefop_activedetailid = srchdefopdt_searchdefaultoperatordetailid " +
                "FOR UPDATE");
        getSearchDefaultOperatorsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<SearchDefaultOperator> getSearchDefaultOperators(EntityPermission entityPermission) {
        return SearchDefaultOperatorFactory.getInstance().getEntitiesFromQuery(entityPermission, getSearchDefaultOperatorsQueries);
    }

    public List<SearchDefaultOperator> getSearchDefaultOperators() {
        return getSearchDefaultOperators(EntityPermission.READ_ONLY);
    }

    public List<SearchDefaultOperator> getSearchDefaultOperatorsForUpdate() {
        return getSearchDefaultOperators(EntityPermission.READ_WRITE);
    }

   public SearchDefaultOperatorTransfer getSearchDefaultOperatorTransfer(UserVisit userVisit, SearchDefaultOperator searchDefaultOperator) {
        return getSearchTransferCaches(userVisit).getSearchDefaultOperatorTransferCache().getSearchDefaultOperatorTransfer(searchDefaultOperator);
    }

    public List<SearchDefaultOperatorTransfer> getSearchDefaultOperatorTransfers(UserVisit userVisit) {
        List<SearchDefaultOperator> searchDefaultOperators = getSearchDefaultOperators();
        List<SearchDefaultOperatorTransfer> searchDefaultOperatorTransfers = new ArrayList<>(searchDefaultOperators.size());
        SearchDefaultOperatorTransferCache searchDefaultOperatorTransferCache = getSearchTransferCaches(userVisit).getSearchDefaultOperatorTransferCache();

        searchDefaultOperators.stream().forEach((searchDefaultOperator) -> {
            searchDefaultOperatorTransfers.add(searchDefaultOperatorTransferCache.getSearchDefaultOperatorTransfer(searchDefaultOperator));
        });

        return searchDefaultOperatorTransfers;
    }

    public SearchDefaultOperatorChoicesBean getSearchDefaultOperatorChoices(String defaultSearchDefaultOperatorChoice, Language language, boolean allowNullChoice) {
        List<SearchDefaultOperator> searchDefaultOperators = getSearchDefaultOperators();
        int size = searchDefaultOperators.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultSearchDefaultOperatorChoice == null) {
                defaultValue = "";
            }
        }

        for(SearchDefaultOperator searchDefaultOperator: searchDefaultOperators) {
            SearchDefaultOperatorDetail searchDefaultOperatorDetail = searchDefaultOperator.getLastDetail();

            String label = getBestSearchDefaultOperatorDescription(searchDefaultOperator, language);
            String value = searchDefaultOperatorDetail.getSearchDefaultOperatorName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultSearchDefaultOperatorChoice == null? false: defaultSearchDefaultOperatorChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && searchDefaultOperatorDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new SearchDefaultOperatorChoicesBean(labels, values, defaultValue);
    }

    private void updateSearchDefaultOperatorFromValue(SearchDefaultOperatorDetailValue searchDefaultOperatorDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(searchDefaultOperatorDetailValue.hasBeenModified()) {
            SearchDefaultOperator searchDefaultOperator = SearchDefaultOperatorFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     searchDefaultOperatorDetailValue.getSearchDefaultOperatorPK());
            SearchDefaultOperatorDetail searchDefaultOperatorDetail = searchDefaultOperator.getActiveDetailForUpdate();

            searchDefaultOperatorDetail.setThruTime(session.START_TIME_LONG);
            searchDefaultOperatorDetail.store();

            SearchDefaultOperatorPK searchDefaultOperatorPK = searchDefaultOperatorDetail.getSearchDefaultOperatorPK(); // Not updated
            String searchDefaultOperatorName = searchDefaultOperatorDetailValue.getSearchDefaultOperatorName();
            Boolean isDefault = searchDefaultOperatorDetailValue.getIsDefault();
            Integer sortOrder = searchDefaultOperatorDetailValue.getSortOrder();

            if(checkDefault) {
                SearchDefaultOperator defaultSearchDefaultOperator = getDefaultSearchDefaultOperator();
                boolean defaultFound = defaultSearchDefaultOperator != null && !defaultSearchDefaultOperator.equals(searchDefaultOperator);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    SearchDefaultOperatorDetailValue defaultSearchDefaultOperatorDetailValue = getDefaultSearchDefaultOperatorDetailValueForUpdate();

                    defaultSearchDefaultOperatorDetailValue.setIsDefault(Boolean.FALSE);
                    updateSearchDefaultOperatorFromValue(defaultSearchDefaultOperatorDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            searchDefaultOperatorDetail = SearchDefaultOperatorDetailFactory.getInstance().create(searchDefaultOperatorPK, searchDefaultOperatorName, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            searchDefaultOperator.setActiveDetail(searchDefaultOperatorDetail);
            searchDefaultOperator.setLastDetail(searchDefaultOperatorDetail);

            sendEventUsingNames(searchDefaultOperatorPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateSearchDefaultOperatorFromValue(SearchDefaultOperatorDetailValue searchDefaultOperatorDetailValue, BasePK updatedBy) {
        updateSearchDefaultOperatorFromValue(searchDefaultOperatorDetailValue, true, updatedBy);
    }

    private void deleteSearchDefaultOperator(SearchDefaultOperator searchDefaultOperator, boolean checkDefault, BasePK deletedBy) {
        SearchDefaultOperatorDetail searchDefaultOperatorDetail = searchDefaultOperator.getLastDetailForUpdate();

        deleteSearchDefaultOperatorDescriptionsBySearchDefaultOperator(searchDefaultOperator, deletedBy);
        deletePartySearchTypePreferencesBySearchDefaultOperator(searchDefaultOperator, deletedBy);
        deleteCachedSearchesBySearchDefaultOperator(searchDefaultOperator, deletedBy);

        searchDefaultOperatorDetail.setThruTime(session.START_TIME_LONG);
        searchDefaultOperator.setActiveDetail(null);
        searchDefaultOperator.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            SearchDefaultOperator defaultSearchDefaultOperator = getDefaultSearchDefaultOperator();

            if(defaultSearchDefaultOperator == null) {
                List<SearchDefaultOperator> searchDefaultOperators = getSearchDefaultOperatorsForUpdate();

                if(!searchDefaultOperators.isEmpty()) {
                    Iterator<SearchDefaultOperator> iter = searchDefaultOperators.iterator();
                    if(iter.hasNext()) {
                        defaultSearchDefaultOperator = iter.next();
                    }
                    SearchDefaultOperatorDetailValue searchDefaultOperatorDetailValue = defaultSearchDefaultOperator.getLastDetailForUpdate().getSearchDefaultOperatorDetailValue().clone();

                    searchDefaultOperatorDetailValue.setIsDefault(Boolean.TRUE);
                    updateSearchDefaultOperatorFromValue(searchDefaultOperatorDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(searchDefaultOperator.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteSearchDefaultOperator(SearchDefaultOperator searchDefaultOperator, BasePK deletedBy) {
        deleteSearchDefaultOperator(searchDefaultOperator, true, deletedBy);
    }

    private void deleteSearchDefaultOperators(List<SearchDefaultOperator> searchDefaultOperators, boolean checkDefault, BasePK deletedBy) {
        searchDefaultOperators.stream().forEach((searchDefaultOperator) -> {
            deleteSearchDefaultOperator(searchDefaultOperator, checkDefault, deletedBy);
        });
    }

    public void deleteSearchDefaultOperators(List<SearchDefaultOperator> searchDefaultOperators, BasePK deletedBy) {
        deleteSearchDefaultOperators(searchDefaultOperators, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Search Default Operator Descriptions
    // --------------------------------------------------------------------------------

    public SearchDefaultOperatorDescription createSearchDefaultOperatorDescription(SearchDefaultOperator searchDefaultOperator, Language language, String description, BasePK createdBy) {
        SearchDefaultOperatorDescription searchDefaultOperatorDescription = SearchDefaultOperatorDescriptionFactory.getInstance().create(searchDefaultOperator, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(searchDefaultOperator.getPrimaryKey(), EventTypes.MODIFY.name(), searchDefaultOperatorDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return searchDefaultOperatorDescription;
    }

    private static final Map<EntityPermission, String> getSearchDefaultOperatorDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM searchdefaultoperatordescriptions " +
                "WHERE srchdefopd_srchdefop_searchdefaultoperatorid = ? AND srchdefopd_lang_languageid = ? AND srchdefopd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM searchdefaultoperatordescriptions " +
                "WHERE srchdefopd_srchdefop_searchdefaultoperatorid = ? AND srchdefopd_lang_languageid = ? AND srchdefopd_thrutime = ? " +
                "FOR UPDATE");
        getSearchDefaultOperatorDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private SearchDefaultOperatorDescription getSearchDefaultOperatorDescription(SearchDefaultOperator searchDefaultOperator, Language language, EntityPermission entityPermission) {
        return SearchDefaultOperatorDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getSearchDefaultOperatorDescriptionQueries,
                searchDefaultOperator, language, Session.MAX_TIME);
    }

    public SearchDefaultOperatorDescription getSearchDefaultOperatorDescription(SearchDefaultOperator searchDefaultOperator, Language language) {
        return getSearchDefaultOperatorDescription(searchDefaultOperator, language, EntityPermission.READ_ONLY);
    }

    public SearchDefaultOperatorDescription getSearchDefaultOperatorDescriptionForUpdate(SearchDefaultOperator searchDefaultOperator, Language language) {
        return getSearchDefaultOperatorDescription(searchDefaultOperator, language, EntityPermission.READ_WRITE);
    }

    public SearchDefaultOperatorDescriptionValue getSearchDefaultOperatorDescriptionValue(SearchDefaultOperatorDescription searchDefaultOperatorDescription) {
        return searchDefaultOperatorDescription == null? null: searchDefaultOperatorDescription.getSearchDefaultOperatorDescriptionValue().clone();
    }

    public SearchDefaultOperatorDescriptionValue getSearchDefaultOperatorDescriptionValueForUpdate(SearchDefaultOperator searchDefaultOperator, Language language) {
        return getSearchDefaultOperatorDescriptionValue(getSearchDefaultOperatorDescriptionForUpdate(searchDefaultOperator, language));
    }

    private static final Map<EntityPermission, String> getSearchDefaultOperatorDescriptionsBySearchDefaultOperatorQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM searchdefaultoperatordescriptions, languages " +
                "WHERE srchdefopd_srchdefop_searchdefaultoperatorid = ? AND srchdefopd_thrutime = ? AND srchdefopd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM searchdefaultoperatordescriptions " +
                "WHERE srchdefopd_srchdefop_searchdefaultoperatorid = ? AND srchdefopd_thrutime = ? " +
                "FOR UPDATE");
        getSearchDefaultOperatorDescriptionsBySearchDefaultOperatorQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<SearchDefaultOperatorDescription> getSearchDefaultOperatorDescriptionsBySearchDefaultOperator(SearchDefaultOperator searchDefaultOperator, EntityPermission entityPermission) {
        return SearchDefaultOperatorDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getSearchDefaultOperatorDescriptionsBySearchDefaultOperatorQueries,
                searchDefaultOperator, Session.MAX_TIME);
    }

    public List<SearchDefaultOperatorDescription> getSearchDefaultOperatorDescriptionsBySearchDefaultOperator(SearchDefaultOperator searchDefaultOperator) {
        return getSearchDefaultOperatorDescriptionsBySearchDefaultOperator(searchDefaultOperator, EntityPermission.READ_ONLY);
    }

    public List<SearchDefaultOperatorDescription> getSearchDefaultOperatorDescriptionsBySearchDefaultOperatorForUpdate(SearchDefaultOperator searchDefaultOperator) {
        return getSearchDefaultOperatorDescriptionsBySearchDefaultOperator(searchDefaultOperator, EntityPermission.READ_WRITE);
    }

    public String getBestSearchDefaultOperatorDescription(SearchDefaultOperator searchDefaultOperator, Language language) {
        String description;
        SearchDefaultOperatorDescription searchDefaultOperatorDescription = getSearchDefaultOperatorDescription(searchDefaultOperator, language);

        if(searchDefaultOperatorDescription == null && !language.getIsDefault()) {
            searchDefaultOperatorDescription = getSearchDefaultOperatorDescription(searchDefaultOperator, getPartyControl().getDefaultLanguage());
        }

        if(searchDefaultOperatorDescription == null) {
            description = searchDefaultOperator.getLastDetail().getSearchDefaultOperatorName();
        } else {
            description = searchDefaultOperatorDescription.getDescription();
        }

        return description;
    }

    public SearchDefaultOperatorDescriptionTransfer getSearchDefaultOperatorDescriptionTransfer(UserVisit userVisit, SearchDefaultOperatorDescription searchDefaultOperatorDescription) {
        return getSearchTransferCaches(userVisit).getSearchDefaultOperatorDescriptionTransferCache().getSearchDefaultOperatorDescriptionTransfer(searchDefaultOperatorDescription);
    }

    public List<SearchDefaultOperatorDescriptionTransfer> getSearchDefaultOperatorDescriptionTransfersBySearchDefaultOperator(UserVisit userVisit, SearchDefaultOperator searchDefaultOperator) {
        List<SearchDefaultOperatorDescription> searchDefaultOperatorDescriptions = getSearchDefaultOperatorDescriptionsBySearchDefaultOperator(searchDefaultOperator);
        List<SearchDefaultOperatorDescriptionTransfer> searchDefaultOperatorDescriptionTransfers = new ArrayList<>(searchDefaultOperatorDescriptions.size());
        SearchDefaultOperatorDescriptionTransferCache searchDefaultOperatorDescriptionTransferCache = getSearchTransferCaches(userVisit).getSearchDefaultOperatorDescriptionTransferCache();

        searchDefaultOperatorDescriptions.stream().forEach((searchDefaultOperatorDescription) -> {
            searchDefaultOperatorDescriptionTransfers.add(searchDefaultOperatorDescriptionTransferCache.getSearchDefaultOperatorDescriptionTransfer(searchDefaultOperatorDescription));
        });

        return searchDefaultOperatorDescriptionTransfers;
    }

    public void updateSearchDefaultOperatorDescriptionFromValue(SearchDefaultOperatorDescriptionValue searchDefaultOperatorDescriptionValue, BasePK updatedBy) {
        if(searchDefaultOperatorDescriptionValue.hasBeenModified()) {
            SearchDefaultOperatorDescription searchDefaultOperatorDescription = SearchDefaultOperatorDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    searchDefaultOperatorDescriptionValue.getPrimaryKey());

            searchDefaultOperatorDescription.setThruTime(session.START_TIME_LONG);
            searchDefaultOperatorDescription.store();

            SearchDefaultOperator searchDefaultOperator = searchDefaultOperatorDescription.getSearchDefaultOperator();
            Language language = searchDefaultOperatorDescription.getLanguage();
            String description = searchDefaultOperatorDescriptionValue.getDescription();

            searchDefaultOperatorDescription = SearchDefaultOperatorDescriptionFactory.getInstance().create(searchDefaultOperator, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(searchDefaultOperator.getPrimaryKey(), EventTypes.MODIFY.name(), searchDefaultOperatorDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteSearchDefaultOperatorDescription(SearchDefaultOperatorDescription searchDefaultOperatorDescription, BasePK deletedBy) {
        searchDefaultOperatorDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(searchDefaultOperatorDescription.getSearchDefaultOperatorPK(), EventTypes.MODIFY.name(), searchDefaultOperatorDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteSearchDefaultOperatorDescriptionsBySearchDefaultOperator(SearchDefaultOperator searchDefaultOperator, BasePK deletedBy) {
        List<SearchDefaultOperatorDescription> searchDefaultOperatorDescriptions = getSearchDefaultOperatorDescriptionsBySearchDefaultOperatorForUpdate(searchDefaultOperator);

        searchDefaultOperatorDescriptions.stream().forEach((searchDefaultOperatorDescription) -> {
            deleteSearchDefaultOperatorDescription(searchDefaultOperatorDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Search Sort Directions
    // --------------------------------------------------------------------------------

    public SearchSortDirection createSearchSortDirection(String searchSortDirectionName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        SearchSortDirection defaultSearchSortDirection = getDefaultSearchSortDirection();
        boolean defaultFound = defaultSearchSortDirection != null;

        if(defaultFound && isDefault) {
            SearchSortDirectionDetailValue defaultSearchSortDirectionDetailValue = getDefaultSearchSortDirectionDetailValueForUpdate();

            defaultSearchSortDirectionDetailValue.setIsDefault(Boolean.FALSE);
            updateSearchSortDirectionFromValue(defaultSearchSortDirectionDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        SearchSortDirection searchSortDirection = SearchSortDirectionFactory.getInstance().create();
        SearchSortDirectionDetail searchSortDirectionDetail = SearchSortDirectionDetailFactory.getInstance().create(searchSortDirection, searchSortDirectionName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        searchSortDirection = SearchSortDirectionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, searchSortDirection.getPrimaryKey());
        searchSortDirection.setActiveDetail(searchSortDirectionDetail);
        searchSortDirection.setLastDetail(searchSortDirectionDetail);
        searchSortDirection.store();

        sendEventUsingNames(searchSortDirection.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return searchSortDirection;
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.SearchSortDirection */
    public SearchSortDirection getSearchSortDirectionByEntityInstance(EntityInstance entityInstance) {
        SearchSortDirectionPK pk = new SearchSortDirectionPK(entityInstance.getEntityUniqueId());
        SearchSortDirection searchSortDirection = SearchSortDirectionFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return searchSortDirection;
    }

    private static final Map<EntityPermission, String> getSearchSortDirectionByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM searchsortdirections, searchsortdirectiondetails " +
                "WHERE srchsrtdir_activedetailid = srchsrtdirdt_searchsortdirectiondetailid " +
                "AND srchsrtdirdt_searchsortdirectionname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM searchsortdirections, searchsortdirectiondetails " +
                "WHERE srchsrtdir_activedetailid = srchsrtdirdt_searchsortdirectiondetailid " +
                "AND srchsrtdirdt_searchsortdirectionname = ? " +
                "FOR UPDATE");
        getSearchSortDirectionByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private SearchSortDirection getSearchSortDirectionByName(String searchSortDirectionName, EntityPermission entityPermission) {
        return SearchSortDirectionFactory.getInstance().getEntityFromQuery(entityPermission, getSearchSortDirectionByNameQueries, searchSortDirectionName);
    }

    public SearchSortDirection getSearchSortDirectionByName(String searchSortDirectionName) {
        return getSearchSortDirectionByName(searchSortDirectionName, EntityPermission.READ_ONLY);
    }

    public SearchSortDirection getSearchSortDirectionByNameForUpdate(String searchSortDirectionName) {
        return getSearchSortDirectionByName(searchSortDirectionName, EntityPermission.READ_WRITE);
    }

    public SearchSortDirectionDetailValue getSearchSortDirectionDetailValueForUpdate(SearchSortDirection searchSortDirection) {
        return searchSortDirection == null? null: searchSortDirection.getLastDetailForUpdate().getSearchSortDirectionDetailValue().clone();
    }

    public SearchSortDirectionDetailValue getSearchSortDirectionDetailValueByNameForUpdate(String searchSortDirectionName) {
        return getSearchSortDirectionDetailValueForUpdate(getSearchSortDirectionByNameForUpdate(searchSortDirectionName));
    }

    private static final Map<EntityPermission, String> getDefaultSearchSortDirectionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM searchsortdirections, searchsortdirectiondetails " +
                "WHERE srchsrtdir_activedetailid = srchsrtdirdt_searchsortdirectiondetailid " +
                "AND srchsrtdirdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM searchsortdirections, searchsortdirectiondetails " +
                "WHERE srchsrtdir_activedetailid = srchsrtdirdt_searchsortdirectiondetailid " +
                "AND srchsrtdirdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultSearchSortDirectionQueries = Collections.unmodifiableMap(queryMap);
    }

    private SearchSortDirection getDefaultSearchSortDirection(EntityPermission entityPermission) {
        return SearchSortDirectionFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultSearchSortDirectionQueries);
    }

    public SearchSortDirection getDefaultSearchSortDirection() {
        return getDefaultSearchSortDirection(EntityPermission.READ_ONLY);
    }

    public SearchSortDirection getDefaultSearchSortDirectionForUpdate() {
        return getDefaultSearchSortDirection(EntityPermission.READ_WRITE);
    }

    public SearchSortDirectionDetailValue getDefaultSearchSortDirectionDetailValueForUpdate() {
        return getDefaultSearchSortDirectionForUpdate().getLastDetailForUpdate().getSearchSortDirectionDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getSearchSortDirectionsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM searchsortdirections, searchsortdirectiondetails " +
                "WHERE srchsrtdir_activedetailid = srchsrtdirdt_searchsortdirectiondetailid " +
                "ORDER BY srchsrtdirdt_sortorder, srchsrtdirdt_searchsortdirectionname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM searchsortdirections, searchsortdirectiondetails " +
                "WHERE srchsrtdir_activedetailid = srchsrtdirdt_searchsortdirectiondetailid " +
                "FOR UPDATE");
        getSearchSortDirectionsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<SearchSortDirection> getSearchSortDirections(EntityPermission entityPermission) {
        return SearchSortDirectionFactory.getInstance().getEntitiesFromQuery(entityPermission, getSearchSortDirectionsQueries);
    }

    public List<SearchSortDirection> getSearchSortDirections() {
        return getSearchSortDirections(EntityPermission.READ_ONLY);
    }

    public List<SearchSortDirection> getSearchSortDirectionsForUpdate() {
        return getSearchSortDirections(EntityPermission.READ_WRITE);
    }

   public SearchSortDirectionTransfer getSearchSortDirectionTransfer(UserVisit userVisit, SearchSortDirection searchSortDirection) {
        return getSearchTransferCaches(userVisit).getSearchSortDirectionTransferCache().getSearchSortDirectionTransfer(searchSortDirection);
    }

    public List<SearchSortDirectionTransfer> getSearchSortDirectionTransfers(UserVisit userVisit) {
        List<SearchSortDirection> searchSortDirections = getSearchSortDirections();
        List<SearchSortDirectionTransfer> searchSortDirectionTransfers = new ArrayList<>(searchSortDirections.size());
        SearchSortDirectionTransferCache searchSortDirectionTransferCache = getSearchTransferCaches(userVisit).getSearchSortDirectionTransferCache();

        searchSortDirections.stream().forEach((searchSortDirection) -> {
            searchSortDirectionTransfers.add(searchSortDirectionTransferCache.getSearchSortDirectionTransfer(searchSortDirection));
        });

        return searchSortDirectionTransfers;
    }

    public SearchSortDirectionChoicesBean getSearchSortDirectionChoices(String defaultSearchSortDirectionChoice, Language language, boolean allowNullChoice) {
        List<SearchSortDirection> searchSortDirections = getSearchSortDirections();
        int size = searchSortDirections.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultSearchSortDirectionChoice == null) {
                defaultValue = "";
            }
        }

        for(SearchSortDirection searchSortDirection: searchSortDirections) {
            SearchSortDirectionDetail searchSortDirectionDetail = searchSortDirection.getLastDetail();

            String label = getBestSearchSortDirectionDescription(searchSortDirection, language);
            String value = searchSortDirectionDetail.getSearchSortDirectionName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultSearchSortDirectionChoice == null? false: defaultSearchSortDirectionChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && searchSortDirectionDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new SearchSortDirectionChoicesBean(labels, values, defaultValue);
    }

    private void updateSearchSortDirectionFromValue(SearchSortDirectionDetailValue searchSortDirectionDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(searchSortDirectionDetailValue.hasBeenModified()) {
            SearchSortDirection searchSortDirection = SearchSortDirectionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     searchSortDirectionDetailValue.getSearchSortDirectionPK());
            SearchSortDirectionDetail searchSortDirectionDetail = searchSortDirection.getActiveDetailForUpdate();

            searchSortDirectionDetail.setThruTime(session.START_TIME_LONG);
            searchSortDirectionDetail.store();

            SearchSortDirectionPK searchSortDirectionPK = searchSortDirectionDetail.getSearchSortDirectionPK(); // Not updated
            String searchSortDirectionName = searchSortDirectionDetailValue.getSearchSortDirectionName();
            Boolean isDefault = searchSortDirectionDetailValue.getIsDefault();
            Integer sortOrder = searchSortDirectionDetailValue.getSortOrder();

            if(checkDefault) {
                SearchSortDirection defaultSearchSortDirection = getDefaultSearchSortDirection();
                boolean defaultFound = defaultSearchSortDirection != null && !defaultSearchSortDirection.equals(searchSortDirection);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    SearchSortDirectionDetailValue defaultSearchSortDirectionDetailValue = getDefaultSearchSortDirectionDetailValueForUpdate();

                    defaultSearchSortDirectionDetailValue.setIsDefault(Boolean.FALSE);
                    updateSearchSortDirectionFromValue(defaultSearchSortDirectionDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            searchSortDirectionDetail = SearchSortDirectionDetailFactory.getInstance().create(searchSortDirectionPK, searchSortDirectionName, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            searchSortDirection.setActiveDetail(searchSortDirectionDetail);
            searchSortDirection.setLastDetail(searchSortDirectionDetail);

            sendEventUsingNames(searchSortDirectionPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateSearchSortDirectionFromValue(SearchSortDirectionDetailValue searchSortDirectionDetailValue, BasePK updatedBy) {
        updateSearchSortDirectionFromValue(searchSortDirectionDetailValue, true, updatedBy);
    }

    private void deleteSearchSortDirection(SearchSortDirection searchSortDirection, boolean checkDefault, BasePK deletedBy) {
        SearchSortDirectionDetail searchSortDirectionDetail = searchSortDirection.getLastDetailForUpdate();

        deleteSearchSortDirectionDescriptionsBySearchSortDirection(searchSortDirection, deletedBy);
        deletePartySearchTypePreferencesBySearchSortDirection(searchSortDirection, deletedBy);
        deleteCachedSearchesBySearchSortDirection(searchSortDirection, deletedBy);

        searchSortDirectionDetail.setThruTime(session.START_TIME_LONG);
        searchSortDirection.setActiveDetail(null);
        searchSortDirection.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            SearchSortDirection defaultSearchSortDirection = getDefaultSearchSortDirection();

            if(defaultSearchSortDirection == null) {
                List<SearchSortDirection> searchSortDirections = getSearchSortDirectionsForUpdate();

                if(!searchSortDirections.isEmpty()) {
                    Iterator<SearchSortDirection> iter = searchSortDirections.iterator();
                    if(iter.hasNext()) {
                        defaultSearchSortDirection = iter.next();
                    }
                    SearchSortDirectionDetailValue searchSortDirectionDetailValue = defaultSearchSortDirection.getLastDetailForUpdate().getSearchSortDirectionDetailValue().clone();

                    searchSortDirectionDetailValue.setIsDefault(Boolean.TRUE);
                    updateSearchSortDirectionFromValue(searchSortDirectionDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(searchSortDirection.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteSearchSortDirection(SearchSortDirection searchSortDirection, BasePK deletedBy) {
        deleteSearchSortDirection(searchSortDirection, true, deletedBy);
    }

    private void deleteSearchSortDirections(List<SearchSortDirection> searchSortDirections, boolean checkDefault, BasePK deletedBy) {
        searchSortDirections.stream().forEach((searchSortDirection) -> {
            deleteSearchSortDirection(searchSortDirection, checkDefault, deletedBy);
        });
    }

    public void deleteSearchSortDirections(List<SearchSortDirection> searchSortDirections, BasePK deletedBy) {
        deleteSearchSortDirections(searchSortDirections, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Search Sort Direction Descriptions
    // --------------------------------------------------------------------------------

    public SearchSortDirectionDescription createSearchSortDirectionDescription(SearchSortDirection searchSortDirection, Language language, String description, BasePK createdBy) {
        SearchSortDirectionDescription searchSortDirectionDescription = SearchSortDirectionDescriptionFactory.getInstance().create(searchSortDirection, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(searchSortDirection.getPrimaryKey(), EventTypes.MODIFY.name(), searchSortDirectionDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return searchSortDirectionDescription;
    }

    private static final Map<EntityPermission, String> getSearchSortDirectionDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM searchsortdirectiondescriptions " +
                "WHERE srchsrtdird_srchsrtdir_searchsortdirectionid = ? AND srchsrtdird_lang_languageid = ? AND srchsrtdird_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM searchsortdirectiondescriptions " +
                "WHERE srchsrtdird_srchsrtdir_searchsortdirectionid = ? AND srchsrtdird_lang_languageid = ? AND srchsrtdird_thrutime = ? " +
                "FOR UPDATE");
        getSearchSortDirectionDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private SearchSortDirectionDescription getSearchSortDirectionDescription(SearchSortDirection searchSortDirection, Language language, EntityPermission entityPermission) {
        return SearchSortDirectionDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getSearchSortDirectionDescriptionQueries,
                searchSortDirection, language, Session.MAX_TIME);
    }

    public SearchSortDirectionDescription getSearchSortDirectionDescription(SearchSortDirection searchSortDirection, Language language) {
        return getSearchSortDirectionDescription(searchSortDirection, language, EntityPermission.READ_ONLY);
    }

    public SearchSortDirectionDescription getSearchSortDirectionDescriptionForUpdate(SearchSortDirection searchSortDirection, Language language) {
        return getSearchSortDirectionDescription(searchSortDirection, language, EntityPermission.READ_WRITE);
    }

    public SearchSortDirectionDescriptionValue getSearchSortDirectionDescriptionValue(SearchSortDirectionDescription searchSortDirectionDescription) {
        return searchSortDirectionDescription == null? null: searchSortDirectionDescription.getSearchSortDirectionDescriptionValue().clone();
    }

    public SearchSortDirectionDescriptionValue getSearchSortDirectionDescriptionValueForUpdate(SearchSortDirection searchSortDirection, Language language) {
        return getSearchSortDirectionDescriptionValue(getSearchSortDirectionDescriptionForUpdate(searchSortDirection, language));
    }

    private static final Map<EntityPermission, String> getSearchSortDirectionDescriptionsBySearchSortDirectionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM searchsortdirectiondescriptions, languages " +
                "WHERE srchsrtdird_srchsrtdir_searchsortdirectionid = ? AND srchsrtdird_thrutime = ? AND srchsrtdird_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM searchsortdirectiondescriptions " +
                "WHERE srchsrtdird_srchsrtdir_searchsortdirectionid = ? AND srchsrtdird_thrutime = ? " +
                "FOR UPDATE");
        getSearchSortDirectionDescriptionsBySearchSortDirectionQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<SearchSortDirectionDescription> getSearchSortDirectionDescriptionsBySearchSortDirection(SearchSortDirection searchSortDirection, EntityPermission entityPermission) {
        return SearchSortDirectionDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getSearchSortDirectionDescriptionsBySearchSortDirectionQueries,
                searchSortDirection, Session.MAX_TIME);
    }

    public List<SearchSortDirectionDescription> getSearchSortDirectionDescriptionsBySearchSortDirection(SearchSortDirection searchSortDirection) {
        return getSearchSortDirectionDescriptionsBySearchSortDirection(searchSortDirection, EntityPermission.READ_ONLY);
    }

    public List<SearchSortDirectionDescription> getSearchSortDirectionDescriptionsBySearchSortDirectionForUpdate(SearchSortDirection searchSortDirection) {
        return getSearchSortDirectionDescriptionsBySearchSortDirection(searchSortDirection, EntityPermission.READ_WRITE);
    }

    public String getBestSearchSortDirectionDescription(SearchSortDirection searchSortDirection, Language language) {
        String description;
        SearchSortDirectionDescription searchSortDirectionDescription = getSearchSortDirectionDescription(searchSortDirection, language);

        if(searchSortDirectionDescription == null && !language.getIsDefault()) {
            searchSortDirectionDescription = getSearchSortDirectionDescription(searchSortDirection, getPartyControl().getDefaultLanguage());
        }

        if(searchSortDirectionDescription == null) {
            description = searchSortDirection.getLastDetail().getSearchSortDirectionName();
        } else {
            description = searchSortDirectionDescription.getDescription();
        }

        return description;
    }

    public SearchSortDirectionDescriptionTransfer getSearchSortDirectionDescriptionTransfer(UserVisit userVisit, SearchSortDirectionDescription searchSortDirectionDescription) {
        return getSearchTransferCaches(userVisit).getSearchSortDirectionDescriptionTransferCache().getSearchSortDirectionDescriptionTransfer(searchSortDirectionDescription);
    }

    public List<SearchSortDirectionDescriptionTransfer> getSearchSortDirectionDescriptionTransfersBySearchSortDirection(UserVisit userVisit, SearchSortDirection searchSortDirection) {
        List<SearchSortDirectionDescription> searchSortDirectionDescriptions = getSearchSortDirectionDescriptionsBySearchSortDirection(searchSortDirection);
        List<SearchSortDirectionDescriptionTransfer> searchSortDirectionDescriptionTransfers = new ArrayList<>(searchSortDirectionDescriptions.size());
        SearchSortDirectionDescriptionTransferCache searchSortDirectionDescriptionTransferCache = getSearchTransferCaches(userVisit).getSearchSortDirectionDescriptionTransferCache();

        searchSortDirectionDescriptions.stream().forEach((searchSortDirectionDescription) -> {
            searchSortDirectionDescriptionTransfers.add(searchSortDirectionDescriptionTransferCache.getSearchSortDirectionDescriptionTransfer(searchSortDirectionDescription));
        });

        return searchSortDirectionDescriptionTransfers;
    }

    public void updateSearchSortDirectionDescriptionFromValue(SearchSortDirectionDescriptionValue searchSortDirectionDescriptionValue, BasePK updatedBy) {
        if(searchSortDirectionDescriptionValue.hasBeenModified()) {
            SearchSortDirectionDescription searchSortDirectionDescription = SearchSortDirectionDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    searchSortDirectionDescriptionValue.getPrimaryKey());

            searchSortDirectionDescription.setThruTime(session.START_TIME_LONG);
            searchSortDirectionDescription.store();

            SearchSortDirection searchSortDirection = searchSortDirectionDescription.getSearchSortDirection();
            Language language = searchSortDirectionDescription.getLanguage();
            String description = searchSortDirectionDescriptionValue.getDescription();

            searchSortDirectionDescription = SearchSortDirectionDescriptionFactory.getInstance().create(searchSortDirection, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(searchSortDirection.getPrimaryKey(), EventTypes.MODIFY.name(), searchSortDirectionDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteSearchSortDirectionDescription(SearchSortDirectionDescription searchSortDirectionDescription, BasePK deletedBy) {
        searchSortDirectionDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(searchSortDirectionDescription.getSearchSortDirectionPK(), EventTypes.MODIFY.name(), searchSortDirectionDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteSearchSortDirectionDescriptionsBySearchSortDirection(SearchSortDirection searchSortDirection, BasePK deletedBy) {
        List<SearchSortDirectionDescription> searchSortDirectionDescriptions = getSearchSortDirectionDescriptionsBySearchSortDirectionForUpdate(searchSortDirection);

        searchSortDirectionDescriptions.stream().forEach((searchSortDirectionDescription) -> {
            deleteSearchSortDirectionDescription(searchSortDirectionDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Search Kinds
    // --------------------------------------------------------------------------------

    public SearchKind createSearchKind(String searchKindName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        SearchKind defaultSearchKind = getDefaultSearchKind();
        boolean defaultFound = defaultSearchKind != null;

        if(defaultFound && isDefault) {
            SearchKindDetailValue defaultSearchKindDetailValue = getDefaultSearchKindDetailValueForUpdate();

            defaultSearchKindDetailValue.setIsDefault(Boolean.FALSE);
            updateSearchKindFromValue(defaultSearchKindDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        SearchKind searchKind = SearchKindFactory.getInstance().create();
        SearchKindDetail searchKindDetail = SearchKindDetailFactory.getInstance().create(searchKind, searchKindName, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        searchKind = SearchKindFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                searchKind.getPrimaryKey());
        searchKind.setActiveDetail(searchKindDetail);
        searchKind.setLastDetail(searchKindDetail);
        searchKind.store();

        sendEventUsingNames(searchKind.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return searchKind;
    }

    private static final Map<EntityPermission, String> getSearchKindByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM searchkinds, searchkinddetails "
                + "WHERE srchk_activedetailid = srchkdt_searchkinddetailid AND srchkdt_searchkindname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM searchkinds, searchkinddetails "
                + "WHERE srchk_activedetailid = srchkdt_searchkinddetailid AND srchkdt_searchkindname = ? "
                + "FOR UPDATE");
        getSearchKindByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private SearchKind getSearchKindByName(String searchKindName, EntityPermission entityPermission) {
        return SearchKindFactory.getInstance().getEntityFromQuery(entityPermission, getSearchKindByNameQueries,
                searchKindName);
    }

    public SearchKind getSearchKindByName(String searchKindName) {
        return getSearchKindByName(searchKindName, EntityPermission.READ_ONLY);
    }

    public SearchKind getSearchKindByNameForUpdate(String searchKindName) {
        return getSearchKindByName(searchKindName, EntityPermission.READ_WRITE);
    }

    public SearchKindDetailValue getSearchKindDetailValueForUpdate(SearchKind searchKind) {
        return searchKind == null? null: searchKind.getLastDetailForUpdate().getSearchKindDetailValue().clone();
    }

    public SearchKindDetailValue getSearchKindDetailValueByNameForUpdate(String searchKindName) {
        return getSearchKindDetailValueForUpdate(getSearchKindByNameForUpdate(searchKindName));
    }

    private static final Map<EntityPermission, String> getDefaultSearchKindQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM searchkinds, searchkinddetails "
                + "WHERE srchk_activedetailid = srchkdt_searchkinddetailid AND srchkdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM searchkinds, searchkinddetails "
                + "WHERE srchk_activedetailid = srchkdt_searchkinddetailid AND srchkdt_isdefault = 1 "
                + "FOR UPDATE");
        getDefaultSearchKindQueries = Collections.unmodifiableMap(queryMap);
    }

    private SearchKind getDefaultSearchKind(EntityPermission entityPermission) {
        return SearchKindFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultSearchKindQueries);
    }

    public SearchKind getDefaultSearchKind() {
        return getDefaultSearchKind(EntityPermission.READ_ONLY);
    }

    public SearchKind getDefaultSearchKindForUpdate() {
        return getDefaultSearchKind(EntityPermission.READ_WRITE);
    }

    public SearchKindDetailValue getDefaultSearchKindDetailValueForUpdate() {
        return getDefaultSearchKind(EntityPermission.READ_WRITE).getLastDetailForUpdate().getSearchKindDetailValue();
    }

    private static final Map<EntityPermission, String> getSearchKindsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM searchkinds, searchkinddetails "
                + "WHERE srchk_activedetailid = srchkdt_searchkinddetailid "
                + "ORDER BY srchkdt_sortorder, srchkdt_searchkindname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM searchkinds, searchkinddetails "
                + "WHERE srchk_activedetailid = srchkdt_searchkinddetailid "
                + "FOR UPDATE");
        getSearchKindsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<SearchKind> getSearchKinds(EntityPermission entityPermission) {
        return SearchKindFactory.getInstance().getEntitiesFromQuery(entityPermission, getSearchKindsQueries);
    }

    public List<SearchKind> getSearchKinds() {
        return getSearchKinds(EntityPermission.READ_ONLY);
    }

    public List<SearchKind> getSearchKindsForUpdate() {
        return getSearchKinds(EntityPermission.READ_WRITE);
    }

    public SearchKindChoicesBean getSearchKindChoices(String defaultSearchKindChoice, Language language, boolean allowNullChoice) {
        List<SearchKind> searchKinds = getSearchKinds();
        int size = searchKinds.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultSearchKindChoice == null) {
                defaultValue = "";
            }
        }

        for(SearchKind searchKind: searchKinds) {
            SearchKindDetail searchKindDetail = searchKind.getLastDetail();

            String label = getBestSearchKindDescription(searchKind, language);
            String value = searchKindDetail.getSearchKindName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultSearchKindChoice == null? false: defaultSearchKindChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && searchKindDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new SearchKindChoicesBean(labels, values, defaultValue);
    }

    public SearchKindTransfer getSearchKindTransfer(UserVisit userVisit, SearchKind searchKind) {
        return getSearchTransferCaches(userVisit).getSearchKindTransferCache().getSearchKindTransfer(searchKind);
    }

    public List<SearchKindTransfer> getSearchKindTransfers(UserVisit userVisit) {
        List<SearchKind> searchKinds = getSearchKinds();
        List<SearchKindTransfer> searchKindTransfers = new ArrayList<>(searchKinds.size());
        SearchKindTransferCache searchKindTransferCache = getSearchTransferCaches(userVisit).getSearchKindTransferCache();

        searchKinds.stream().forEach((searchKind) -> {
            searchKindTransfers.add(searchKindTransferCache.getSearchKindTransfer(searchKind));
        });

        return searchKindTransfers;
    }

    private void updateSearchKindFromValue(SearchKindDetailValue searchKindDetailValue, boolean checkDefault, BasePK updatedBy) {
        SearchKind searchKind = SearchKindFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, searchKindDetailValue.getSearchKindPK());
        SearchKindDetail searchKindDetail = searchKind.getActiveDetailForUpdate();

        searchKindDetail.setThruTime(session.START_TIME_LONG);
        searchKindDetail.store();

        SearchKindPK searchKindPK = searchKindDetail.getSearchKindPK();
        String searchKindName = searchKindDetailValue.getSearchKindName();
        Boolean isDefault = searchKindDetailValue.getIsDefault();
        Integer sortOrder = searchKindDetailValue.getSortOrder();

        if(checkDefault) {
            SearchKind defaultSearchKind = getDefaultSearchKind();
            boolean defaultFound = defaultSearchKind != null && !defaultSearchKind.equals(searchKind);

            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                SearchKindDetailValue defaultSearchKindDetailValue = getDefaultSearchKindDetailValueForUpdate();

                defaultSearchKindDetailValue.setIsDefault(Boolean.FALSE);
                updateSearchKindFromValue(defaultSearchKindDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = Boolean.TRUE;
            }
        }

        searchKindDetail = SearchKindDetailFactory.getInstance().create(searchKindPK, searchKindName, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        searchKind.setActiveDetail(searchKindDetail);
        searchKind.setLastDetail(searchKindDetail);
        searchKind.store();

        sendEventUsingNames(searchKindPK, EventTypes.MODIFY.name(), null, null, updatedBy);
    }

    public void updateSearchKindFromValue(SearchKindDetailValue searchKindDetailValue, BasePK updatedBy) {
        updateSearchKindFromValue(searchKindDetailValue, true, updatedBy);
    }

    private void deleteSearchKind(SearchKind searchKind, boolean checkDefault, BasePK deletedBy) {
        deleteSearchTypesBySearchKind(searchKind, deletedBy);
        deleteSearchKindDescriptionsBySearchKind(searchKind, deletedBy);

        SearchKindDetail searchKindDetail = searchKind.getLastDetailForUpdate();
        searchKindDetail.setThruTime(session.START_TIME_LONG);
        searchKind.setActiveDetail(null);
        searchKind.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            SearchKind defaultSearchKind = getDefaultSearchKind();

            if(defaultSearchKind == null) {
                List<SearchKind> searchKinds = getSearchKindsForUpdate();

                if(!searchKinds.isEmpty()) {
                    Iterator<SearchKind> iter = searchKinds.iterator();
                    if(iter.hasNext()) {
                        defaultSearchKind = iter.next();
                    }
                    SearchKindDetailValue searchKindDetailValue = defaultSearchKind.getLastDetailForUpdate().getSearchKindDetailValue().clone();

                    searchKindDetailValue.setIsDefault(Boolean.TRUE);
                    updateSearchKindFromValue(searchKindDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(searchKind.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteSearchKind(SearchKind searchKind, BasePK deletedBy) {
        deleteSearchKind(searchKind, true, deletedBy);
    }

    private void deleteSearchKinds(List<SearchKind> searchKinds, boolean checkDefault, BasePK deletedBy) {
        searchKinds.stream().forEach((searchKind) -> {
            deleteSearchKind(searchKind, checkDefault, deletedBy);
        });
    }

    public void deleteSearchKinds(List<SearchKind> searchKinds, BasePK deletedBy) {
        deleteSearchKinds(searchKinds, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Search Kind Descriptions
    // --------------------------------------------------------------------------------

    public SearchKindDescription createSearchKindDescription(SearchKind searchKind, Language language, String description,
            BasePK createdBy) {
        SearchKindDescription searchKindDescription = SearchKindDescriptionFactory.getInstance().create(searchKind,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(searchKind.getPrimaryKey(), EventTypes.MODIFY.name(), searchKindDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return searchKindDescription;
    }

    private static final Map<EntityPermission, String> getSearchKindDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM searchkinddescriptions "
                + "WHERE srchkd_srchk_searchkindid = ? AND srchkd_lang_languageid = ? AND srchkd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM searchkinddescriptions "
                + "WHERE srchkd_srchk_searchkindid = ? AND srchkd_lang_languageid = ? AND srchkd_thrutime = ? "
                + "FOR UPDATE");
        getSearchKindDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private SearchKindDescription getSearchKindDescription(SearchKind searchKind, Language language, EntityPermission entityPermission) {
        return SearchKindDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getSearchKindDescriptionQueries,
                searchKind, language, Session.MAX_TIME);
    }

    public SearchKindDescription getSearchKindDescription(SearchKind searchKind, Language language) {
        return getSearchKindDescription(searchKind, language, EntityPermission.READ_ONLY);
    }

    public SearchKindDescription getSearchKindDescriptionForUpdate(SearchKind searchKind, Language language) {
        return getSearchKindDescription(searchKind, language, EntityPermission.READ_WRITE);
    }

    public SearchKindDescriptionValue getSearchKindDescriptionValue(SearchKindDescription searchKindDescription) {
        return searchKindDescription == null? null: searchKindDescription.getSearchKindDescriptionValue().clone();
    }

    public SearchKindDescriptionValue getSearchKindDescriptionValueForUpdate(SearchKind searchKind, Language language) {
        return getSearchKindDescriptionValue(getSearchKindDescriptionForUpdate(searchKind, language));
    }

    private static final Map<EntityPermission, String> getSearchKindDescriptionsBySearchKindQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM searchkinddescriptions, languages "
                + "WHERE srchkd_srchk_searchkindid = ? AND srchkd_thrutime = ? AND srchkd_lang_languageid = lang_languageid "
                + "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM searchkinddescriptions "
                + "WHERE srchkd_srchk_searchkindid = ? AND srchkd_thrutime = ? "
                + "FOR UPDATE");
        getSearchKindDescriptionsBySearchKindQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<SearchKindDescription> getSearchKindDescriptionsBySearchKind(SearchKind searchKind, EntityPermission entityPermission) {
        return SearchKindDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getSearchKindDescriptionsBySearchKindQueries,
                searchKind, Session.MAX_TIME);
    }

    public List<SearchKindDescription> getSearchKindDescriptionsBySearchKind(SearchKind searchKind) {
        return getSearchKindDescriptionsBySearchKind(searchKind, EntityPermission.READ_ONLY);
    }

    public List<SearchKindDescription> getSearchKindDescriptionsBySearchKindForUpdate(SearchKind searchKind) {
        return getSearchKindDescriptionsBySearchKind(searchKind, EntityPermission.READ_WRITE);
    }

    public String getBestSearchKindDescription(SearchKind searchKind, Language language) {
        String description;
        SearchKindDescription searchKindDescription = getSearchKindDescription(searchKind, language);

        if(searchKindDescription == null && !language.getIsDefault()) {
            searchKindDescription = getSearchKindDescription(searchKind, getPartyControl().getDefaultLanguage());
        }

        if(searchKindDescription == null) {
            description = searchKind.getLastDetail().getSearchKindName();
        } else {
            description = searchKindDescription.getDescription();
        }

        return description;
    }

    public SearchKindDescriptionTransfer getSearchKindDescriptionTransfer(UserVisit userVisit, SearchKindDescription searchKindDescription) {
        return getSearchTransferCaches(userVisit).getSearchKindDescriptionTransferCache().getSearchKindDescriptionTransfer(searchKindDescription);
    }

    public List<SearchKindDescriptionTransfer> getSearchKindDescriptionTransfersBySearchKind(UserVisit userVisit, SearchKind searchKind) {
        List<SearchKindDescription> searchKindDescriptions = getSearchKindDescriptionsBySearchKind(searchKind);
        List<SearchKindDescriptionTransfer> searchKindDescriptionTransfers = new ArrayList<>(searchKindDescriptions.size());

        searchKindDescriptions.stream().forEach((searchKindDescription) -> {
            searchKindDescriptionTransfers.add(getSearchTransferCaches(userVisit).getSearchKindDescriptionTransferCache().getSearchKindDescriptionTransfer(searchKindDescription));
        });

        return searchKindDescriptionTransfers;
    }

    public void updateSearchKindDescriptionFromValue(SearchKindDescriptionValue searchKindDescriptionValue, BasePK updatedBy) {
        if(searchKindDescriptionValue.hasBeenModified()) {
            SearchKindDescription searchKindDescription = SearchKindDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     searchKindDescriptionValue.getPrimaryKey());

            searchKindDescription.setThruTime(session.START_TIME_LONG);
            searchKindDescription.store();

            SearchKind searchKind = searchKindDescription.getSearchKind();
            Language language = searchKindDescription.getLanguage();
            String description = searchKindDescriptionValue.getDescription();

            searchKindDescription = SearchKindDescriptionFactory.getInstance().create(searchKind, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(searchKind.getPrimaryKey(), EventTypes.MODIFY.name(), searchKindDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteSearchKindDescription(SearchKindDescription searchKindDescription, BasePK deletedBy) {
        searchKindDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(searchKindDescription.getSearchKindPK(), EventTypes.MODIFY.name(), searchKindDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteSearchKindDescriptionsBySearchKind(SearchKind searchKind, BasePK deletedBy) {
        List<SearchKindDescription> searchKindDescriptions = getSearchKindDescriptionsBySearchKindForUpdate(searchKind);

        searchKindDescriptions.stream().forEach((searchKindDescription) -> {
            deleteSearchKindDescription(searchKindDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Search Types
    // --------------------------------------------------------------------------------

    public SearchType createSearchType(SearchKind searchKind, String searchTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        SearchType defaultSearchType = getDefaultSearchType(searchKind);
        boolean defaultFound = defaultSearchType != null;

        if(defaultFound && isDefault) {
            SearchTypeDetailValue defaultSearchTypeDetailValue = getDefaultSearchTypeDetailValueForUpdate(searchKind);

            defaultSearchTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateSearchTypeFromValue(defaultSearchTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        SearchType searchType = SearchTypeFactory.getInstance().create();
        SearchTypeDetail searchTypeDetail = SearchTypeDetailFactory.getInstance().create(session, searchType, searchKind, searchTypeName, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        searchType = SearchTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                searchType.getPrimaryKey());
        searchType.setActiveDetail(searchTypeDetail);
        searchType.setLastDetail(searchTypeDetail);
        searchType.store();

        sendEventUsingNames(searchType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return searchType;
    }

    private static final Map<EntityPermission, String> getSearchTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM searchtypes, searchtypedetails "
                + "WHERE srchtyp_activedetailid = srchtypdt_searchtypedetailid AND srchtypdt_srchk_searchkindid = ? "
                + "ORDER BY srchtypdt_sortorder, srchtypdt_searchtypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM searchtypes, searchtypedetails "
                + "WHERE srchtyp_activedetailid = srchtypdt_searchtypedetailid AND srchtypdt_srchk_searchkindid = ? "
                + "FOR UPDATE");
        getSearchTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<SearchType> getSearchTypes(SearchKind searchKind, EntityPermission entityPermission) {
        return SearchTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getSearchTypesQueries,
                searchKind);
    }

    public List<SearchType> getSearchTypes(SearchKind searchKind) {
        return getSearchTypes(searchKind, EntityPermission.READ_ONLY);
    }

    public List<SearchType> getSearchTypesForUpdate(SearchKind searchKind) {
        return getSearchTypes(searchKind, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getDefaultSearchTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM searchtypes, searchtypedetails "
                + "WHERE srchtyp_activedetailid = srchtypdt_searchtypedetailid "
                + "AND srchtypdt_srchk_searchkindid = ? AND srchtypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM searchtypes, searchtypedetails "
                + "WHERE srchtyp_activedetailid = srchtypdt_searchtypedetailid "
                + "AND srchtypdt_srchk_searchkindid = ? AND srchtypdt_isdefault = 1 "
                + "FOR UPDATE");
        getDefaultSearchTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private SearchType getDefaultSearchType(SearchKind searchKind, EntityPermission entityPermission) {
        return SearchTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultSearchTypeQueries,
                searchKind);
    }

    public SearchType getDefaultSearchType(SearchKind searchKind) {
        return getDefaultSearchType(searchKind, EntityPermission.READ_ONLY);
    }

    public SearchType getDefaultSearchTypeForUpdate(SearchKind searchKind) {
        return getDefaultSearchType(searchKind, EntityPermission.READ_WRITE);
    }

    public SearchTypeDetailValue getDefaultSearchTypeDetailValueForUpdate(SearchKind searchKind) {
        return getDefaultSearchTypeForUpdate(searchKind).getLastDetailForUpdate().getSearchTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getSearchTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM searchtypes, searchtypedetails "
                + "WHERE srchtyp_activedetailid = srchtypdt_searchtypedetailid "
                + "AND srchtypdt_srchk_searchkindid = ? AND srchtypdt_searchtypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM searchtypes, searchtypedetails "
                + "WHERE srchtyp_activedetailid = srchtypdt_searchtypedetailid "
                + "AND srchtypdt_srchk_searchkindid = ? AND srchtypdt_searchtypename = ? "
                + "FOR UPDATE");
        getSearchTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private SearchType getSearchTypeByName(SearchKind searchKind, String searchTypeName, EntityPermission entityPermission) {
        return SearchTypeFactory.getInstance().getEntityFromQuery(entityPermission, getSearchTypeByNameQueries,
                searchKind, searchTypeName);
    }

    public SearchType getSearchTypeByName(SearchKind searchKind, String searchTypeName) {
        return getSearchTypeByName(searchKind, searchTypeName, EntityPermission.READ_ONLY);
    }

    public SearchType getSearchTypeByNameForUpdate(SearchKind searchKind, String searchTypeName) {
        return getSearchTypeByName(searchKind, searchTypeName, EntityPermission.READ_WRITE);
    }

    public SearchTypeDetailValue getSearchTypeDetailValueForUpdate(SearchType searchType) {
        return searchType == null? null: searchType.getLastDetailForUpdate().getSearchTypeDetailValue().clone();
    }

    public SearchTypeDetailValue getSearchTypeDetailValueByNameForUpdate(SearchKind searchKind, String searchTypeName) {
        return getSearchTypeDetailValueForUpdate(getSearchTypeByNameForUpdate(searchKind, searchTypeName));
    }

    public SearchTypeChoicesBean getSearchTypeChoices(String defaultSearchTypeChoice, Language language,
            boolean allowNullChoice, SearchKind searchKind) {
        List<SearchType> searchTypes = getSearchTypes(searchKind);
        int size = searchTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultSearchTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(SearchType searchType: searchTypes) {
            SearchTypeDetail searchTypeDetail = searchType.getLastDetail();
            String label = getBestSearchTypeDescription(searchType, language);
            String value = searchTypeDetail.getSearchTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultSearchTypeChoice == null? false: defaultSearchTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && searchTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new SearchTypeChoicesBean(labels, values, defaultValue);
    }

    public SearchTypeTransfer getSearchTypeTransfer(UserVisit userVisit, SearchType searchType) {
        return getSearchTransferCaches(userVisit).getSearchTypeTransferCache().getSearchTypeTransfer(searchType);
    }

    public List<SearchTypeTransfer> getSearchTypeTransfersBySearchKind(UserVisit userVisit, SearchKind searchKind) {
        List<SearchType> searchTypes = getSearchTypes(searchKind);
        List<SearchTypeTransfer> searchTypeTransfers = new ArrayList<>(searchTypes.size());
        SearchTypeTransferCache searchTypeTransferCache = getSearchTransferCaches(userVisit).getSearchTypeTransferCache();

        searchTypes.stream().forEach((searchType) -> {
            searchTypeTransfers.add(searchTypeTransferCache.getSearchTypeTransfer(searchType));
        });

        return searchTypeTransfers;
    }

    private void updateSearchTypeFromValue(SearchTypeDetailValue searchTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(searchTypeDetailValue.hasBeenModified()) {
            SearchType searchType = SearchTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     searchTypeDetailValue.getSearchTypePK());
            SearchTypeDetail searchTypeDetail = searchType.getActiveDetailForUpdate();

            searchTypeDetail.setThruTime(session.START_TIME_LONG);
            searchTypeDetail.store();

            SearchTypePK searchTypePK = searchTypeDetail.getSearchTypePK();
            SearchKind searchKind = searchTypeDetail.getSearchKind();
            SearchKindPK searchKindPK = searchKind.getPrimaryKey();
            String searchTypeName = searchTypeDetailValue.getSearchTypeName();
            Boolean isDefault = searchTypeDetailValue.getIsDefault();
            Integer sortOrder = searchTypeDetailValue.getSortOrder();

            if(checkDefault) {
                SearchType defaultSearchType = getDefaultSearchType(searchKind);
                boolean defaultFound = defaultSearchType != null && !defaultSearchType.equals(searchType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    SearchTypeDetailValue defaultSearchTypeDetailValue = getDefaultSearchTypeDetailValueForUpdate(searchKind);

                    defaultSearchTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateSearchTypeFromValue(defaultSearchTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            searchTypeDetail = SearchTypeDetailFactory.getInstance().create(searchTypePK, searchKindPK, searchTypeName, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            searchType.setActiveDetail(searchTypeDetail);
            searchType.setLastDetail(searchTypeDetail);

            sendEventUsingNames(searchTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateSearchTypeFromValue(SearchTypeDetailValue searchTypeDetailValue, BasePK updatedBy) {
        updateSearchTypeFromValue(searchTypeDetailValue, true, updatedBy);
    }

    public void deleteSearchType(SearchType searchType, BasePK deletedBy) {
        deleteSearchTypeDescriptionsBySearchType(searchType, deletedBy);
        deletePartySearchTypePreferencesBySearchType(searchType, deletedBy);
        deleteSearchesBySearchType(searchType, deletedBy);
        
        removeUserVisitSearchesBySearchType(searchType);

        SearchTypeDetail searchTypeDetail = searchType.getLastDetailForUpdate();
        searchTypeDetail.setThruTime(session.START_TIME_LONG);
        searchType.setActiveDetail(null);
        searchType.store();

        // Check for default, and pick one if necessary
        SearchKind searchKind = searchTypeDetail.getSearchKind();
        SearchType defaultSearchType = getDefaultSearchType(searchKind);
        if(defaultSearchType == null) {
            List<SearchType> searchTypes = getSearchTypesForUpdate(searchKind);

            if(!searchTypes.isEmpty()) {
                Iterator<SearchType> iter = searchTypes.iterator();
                if(iter.hasNext()) {
                    defaultSearchType = iter.next();
                }
                SearchTypeDetailValue searchTypeDetailValue = defaultSearchType.getLastDetailForUpdate().getSearchTypeDetailValue().clone();

                searchTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateSearchTypeFromValue(searchTypeDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(searchType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteSearchTypesBySearchKind(SearchKind searchKind, BasePK deletedBy) {
        List<SearchType> searchTypes = getSearchTypesForUpdate(searchKind);

        searchTypes.stream().forEach((searchType) -> {
            deleteSearchType(searchType, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Search Type Descriptions
    // --------------------------------------------------------------------------------

    public SearchTypeDescription createSearchTypeDescription(SearchType searchType, Language language, String description,
            BasePK createdBy) {
        SearchTypeDescription searchTypeDescription = SearchTypeDescriptionFactory.getInstance().create(searchType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(searchType.getPrimaryKey(), EventTypes.MODIFY.name(), searchTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return searchTypeDescription;
    }

    private static final Map<EntityPermission, String> getSearchTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM searchtypedescriptions "
                + "WHERE srchtypd_srchtyp_searchtypeid = ? AND srchtypd_lang_languageid = ? AND srchtypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM searchtypedescriptions "
                + "WHERE srchtypd_srchtyp_searchtypeid = ? AND srchtypd_lang_languageid = ? AND srchtypd_thrutime = ? "
                + "FOR UPDATE");
        getSearchTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private SearchTypeDescription getSearchTypeDescription(SearchType searchType, Language language, EntityPermission entityPermission) {
        return SearchTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getSearchTypeDescriptionQueries,
                searchType, language, Session.MAX_TIME);
    }

    public SearchTypeDescription getSearchTypeDescription(SearchType searchType, Language language) {
        return getSearchTypeDescription(searchType, language, EntityPermission.READ_ONLY);
    }

    public SearchTypeDescription getSearchTypeDescriptionForUpdate(SearchType searchType, Language language) {
        return getSearchTypeDescription(searchType, language, EntityPermission.READ_WRITE);
    }

    public SearchTypeDescriptionValue getSearchTypeDescriptionValue(SearchTypeDescription searchTypeDescription) {
        return searchTypeDescription == null? null: searchTypeDescription.getSearchTypeDescriptionValue().clone();
    }

    public SearchTypeDescriptionValue getSearchTypeDescriptionValueForUpdate(SearchType searchType, Language language) {
        return getSearchTypeDescriptionValue(getSearchTypeDescriptionForUpdate(searchType, language));
    }

    private static final Map<EntityPermission, String> getSearchTypeDescriptionsBySearchTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM searchtypedescriptions, languages "
                + "WHERE srchtypd_srchtyp_searchtypeid = ? AND srchtypd_thrutime = ? AND srchtypd_lang_languageid = lang_languageid "
                + "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM searchtypedescriptions "
                + "WHERE srchtypd_srchtyp_searchtypeid = ? AND srchtypd_thrutime = ? "
                + "FOR UPDATE");
        getSearchTypeDescriptionsBySearchTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<SearchTypeDescription> getSearchTypeDescriptionsBySearchType(SearchType searchType, EntityPermission entityPermission) {
        return SearchTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getSearchTypeDescriptionsBySearchTypeQueries,
                searchType, Session.MAX_TIME);
    }

    public List<SearchTypeDescription> getSearchTypeDescriptionsBySearchType(SearchType searchType) {
        return getSearchTypeDescriptionsBySearchType(searchType, EntityPermission.READ_ONLY);
    }

    public List<SearchTypeDescription> getSearchTypeDescriptionsBySearchTypeForUpdate(SearchType searchType) {
        return getSearchTypeDescriptionsBySearchType(searchType, EntityPermission.READ_WRITE);
    }

    public String getBestSearchTypeDescription(SearchType searchType, Language language) {
        String description;
        SearchTypeDescription searchTypeDescription = getSearchTypeDescription(searchType, language);

        if(searchTypeDescription == null && !language.getIsDefault()) {
            searchTypeDescription = getSearchTypeDescription(searchType, getPartyControl().getDefaultLanguage());
        }

        if(searchTypeDescription == null) {
            description = searchType.getLastDetail().getSearchTypeName();
        } else {
            description = searchTypeDescription.getDescription();
        }

        return description;
    }

    public SearchTypeDescriptionTransfer getSearchTypeDescriptionTransfer(UserVisit userVisit, SearchTypeDescription searchTypeDescription) {
        return getSearchTransferCaches(userVisit).getSearchTypeDescriptionTransferCache().getSearchTypeDescriptionTransfer(searchTypeDescription);
    }

    public List<SearchTypeDescriptionTransfer> getSearchTypeDescriptionTransfersBySearchType(UserVisit userVisit, SearchType searchType) {
        List<SearchTypeDescription> searchTypeDescriptions = getSearchTypeDescriptionsBySearchType(searchType);
        List<SearchTypeDescriptionTransfer> searchTypeDescriptionTransfers = new ArrayList<>(searchTypeDescriptions.size());

        searchTypeDescriptions.stream().forEach((searchTypeDescription) -> {
            searchTypeDescriptionTransfers.add(getSearchTransferCaches(userVisit).getSearchTypeDescriptionTransferCache().getSearchTypeDescriptionTransfer(searchTypeDescription));
        });

        return searchTypeDescriptionTransfers;
    }

    public void updateSearchTypeDescriptionFromValue(SearchTypeDescriptionValue searchTypeDescriptionValue, BasePK updatedBy) {
        if(searchTypeDescriptionValue.hasBeenModified()) {
            SearchTypeDescription searchTypeDescription = SearchTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     searchTypeDescriptionValue.getPrimaryKey());

            searchTypeDescription.setThruTime(session.START_TIME_LONG);
            searchTypeDescription.store();

            SearchType searchType = searchTypeDescription.getSearchType();
            Language language = searchTypeDescription.getLanguage();
            String description = searchTypeDescriptionValue.getDescription();

            searchTypeDescription = SearchTypeDescriptionFactory.getInstance().create(searchType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(searchType.getPrimaryKey(), EventTypes.MODIFY.name(), searchTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteSearchTypeDescription(SearchTypeDescription searchTypeDescription, BasePK deletedBy) {
        searchTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(searchTypeDescription.getSearchTypePK(), EventTypes.MODIFY.name(), searchTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteSearchTypeDescriptionsBySearchType(SearchType searchType, BasePK deletedBy) {
        List<SearchTypeDescription> searchTypeDescriptions = getSearchTypeDescriptionsBySearchTypeForUpdate(searchType);

        searchTypeDescriptions.stream().forEach((searchTypeDescription) -> {
            deleteSearchTypeDescription(searchTypeDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Search Sort Orders
    // --------------------------------------------------------------------------------

    public SearchSortOrder createSearchSortOrder(SearchKind searchKind, String searchSortOrderName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        SearchSortOrder defaultSearchSortOrder = getDefaultSearchSortOrder(searchKind);
        boolean defaultFound = defaultSearchSortOrder != null;

        if(defaultFound && isDefault) {
            SearchSortOrderDetailValue defaultSearchSortOrderDetailValue = getDefaultSearchSortOrderDetailValueForUpdate(searchKind);

            defaultSearchSortOrderDetailValue.setIsDefault(Boolean.FALSE);
            updateSearchSortOrderFromValue(defaultSearchSortOrderDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        SearchSortOrder searchSortOrder = SearchSortOrderFactory.getInstance().create();
        SearchSortOrderDetail searchSortOrderDetail = SearchSortOrderDetailFactory.getInstance().create(session, searchSortOrder, searchKind, searchSortOrderName, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        searchSortOrder = SearchSortOrderFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                searchSortOrder.getPrimaryKey());
        searchSortOrder.setActiveDetail(searchSortOrderDetail);
        searchSortOrder.setLastDetail(searchSortOrderDetail);
        searchSortOrder.store();

        sendEventUsingNames(searchSortOrder.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return searchSortOrder;
    }

    private static final Map<EntityPermission, String> getSearchSortOrdersQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM searchsortorders, searchsortorderdetails "
                + "WHERE srchsrtord_activedetailid = srchsrtorddt_searchsortorderdetailid AND srchsrtorddt_srchk_searchkindid = ? "
                + "ORDER BY srchsrtorddt_sortorder, srchsrtorddt_searchsortordername");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM searchsortorders, searchsortorderdetails "
                + "WHERE srchsrtord_activedetailid = srchsrtorddt_searchsortorderdetailid AND srchsrtorddt_srchk_searchkindid = ? "
                + "FOR UPDATE");
        getSearchSortOrdersQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<SearchSortOrder> getSearchSortOrders(SearchKind searchKind, EntityPermission entityPermission) {
        return SearchSortOrderFactory.getInstance().getEntitiesFromQuery(entityPermission, getSearchSortOrdersQueries,
                searchKind);
    }

    public List<SearchSortOrder> getSearchSortOrders(SearchKind searchKind) {
        return getSearchSortOrders(searchKind, EntityPermission.READ_ONLY);
    }

    public List<SearchSortOrder> getSearchSortOrdersForUpdate(SearchKind searchKind) {
        return getSearchSortOrders(searchKind, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getDefaultSearchSortOrderQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM searchsortorders, searchsortorderdetails "
                + "WHERE srchsrtord_activedetailid = srchsrtorddt_searchsortorderdetailid "
                + "AND srchsrtorddt_srchk_searchkindid = ? AND srchsrtorddt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM searchsortorders, searchsortorderdetails "
                + "WHERE srchsrtord_activedetailid = srchsrtorddt_searchsortorderdetailid "
                + "AND srchsrtorddt_srchk_searchkindid = ? AND srchsrtorddt_isdefault = 1 "
                + "FOR UPDATE");
        getDefaultSearchSortOrderQueries = Collections.unmodifiableMap(queryMap);
    }

    private SearchSortOrder getDefaultSearchSortOrder(SearchKind searchKind, EntityPermission entityPermission) {
        return SearchSortOrderFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultSearchSortOrderQueries,
                searchKind);
    }

    public SearchSortOrder getDefaultSearchSortOrder(SearchKind searchKind) {
        return getDefaultSearchSortOrder(searchKind, EntityPermission.READ_ONLY);
    }

    public SearchSortOrder getDefaultSearchSortOrderForUpdate(SearchKind searchKind) {
        return getDefaultSearchSortOrder(searchKind, EntityPermission.READ_WRITE);
    }

    public SearchSortOrderDetailValue getDefaultSearchSortOrderDetailValueForUpdate(SearchKind searchKind) {
        return getDefaultSearchSortOrderForUpdate(searchKind).getLastDetailForUpdate().getSearchSortOrderDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getSearchSortOrderByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM searchsortorders, searchsortorderdetails "
                + "WHERE srchsrtord_activedetailid = srchsrtorddt_searchsortorderdetailid "
                + "AND srchsrtorddt_srchk_searchkindid = ? AND srchsrtorddt_searchsortordername = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM searchsortorders, searchsortorderdetails "
                + "WHERE srchsrtord_activedetailid = srchsrtorddt_searchsortorderdetailid "
                + "AND srchsrtorddt_srchk_searchkindid = ? AND srchsrtorddt_searchsortordername = ? "
                + "FOR UPDATE");
        getSearchSortOrderByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private SearchSortOrder getSearchSortOrderByName(SearchKind searchKind, String searchSortOrderName, EntityPermission entityPermission) {
        return SearchSortOrderFactory.getInstance().getEntityFromQuery(entityPermission, getSearchSortOrderByNameQueries,
                searchKind, searchSortOrderName);
    }

    public SearchSortOrder getSearchSortOrderByName(SearchKind searchKind, String searchSortOrderName) {
        return getSearchSortOrderByName(searchKind, searchSortOrderName, EntityPermission.READ_ONLY);
    }

    public SearchSortOrder getSearchSortOrderByNameForUpdate(SearchKind searchKind, String searchSortOrderName) {
        return getSearchSortOrderByName(searchKind, searchSortOrderName, EntityPermission.READ_WRITE);
    }

    public SearchSortOrderDetailValue getSearchSortOrderDetailValueForUpdate(SearchSortOrder searchSortOrder) {
        return searchSortOrder == null? null: searchSortOrder.getLastDetailForUpdate().getSearchSortOrderDetailValue().clone();
    }

    public SearchSortOrderDetailValue getSearchSortOrderDetailValueByNameForUpdate(SearchKind searchKind, String searchSortOrderName) {
        return getSearchSortOrderDetailValueForUpdate(getSearchSortOrderByNameForUpdate(searchKind, searchSortOrderName));
    }

    public SearchSortOrderChoicesBean getSearchSortOrderChoices(String defaultSearchSortOrderChoice, Language language,
            boolean allowNullChoice, SearchKind searchKind) {
        List<SearchSortOrder> searchSortOrders = getSearchSortOrders(searchKind);
        int size = searchSortOrders.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultSearchSortOrderChoice == null) {
                defaultValue = "";
            }
        }

        for(SearchSortOrder searchSortOrder: searchSortOrders) {
            SearchSortOrderDetail searchSortOrderDetail = searchSortOrder.getLastDetail();
            String label = getBestSearchSortOrderDescription(searchSortOrder, language);
            String value = searchSortOrderDetail.getSearchSortOrderName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultSearchSortOrderChoice == null? false: defaultSearchSortOrderChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && searchSortOrderDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new SearchSortOrderChoicesBean(labels, values, defaultValue);
    }

    public SearchSortOrderTransfer getSearchSortOrderTransfer(UserVisit userVisit, SearchSortOrder searchSortOrder) {
        return getSearchTransferCaches(userVisit).getSearchSortOrderTransferCache().getSearchSortOrderTransfer(searchSortOrder);
    }

    public List<SearchSortOrderTransfer> getSearchSortOrderTransfersBySearchKind(UserVisit userVisit, SearchKind searchKind) {
        List<SearchSortOrder> searchSortOrders = getSearchSortOrders(searchKind);
        List<SearchSortOrderTransfer> searchSortOrderTransfers = new ArrayList<>(searchSortOrders.size());
        SearchSortOrderTransferCache searchSortOrderTransferCache = getSearchTransferCaches(userVisit).getSearchSortOrderTransferCache();

        searchSortOrders.stream().forEach((searchSortOrder) -> {
            searchSortOrderTransfers.add(searchSortOrderTransferCache.getSearchSortOrderTransfer(searchSortOrder));
        });

        return searchSortOrderTransfers;
    }

    private void updateSearchSortOrderFromValue(SearchSortOrderDetailValue searchSortOrderDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(searchSortOrderDetailValue.hasBeenModified()) {
            SearchSortOrder searchSortOrder = SearchSortOrderFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     searchSortOrderDetailValue.getSearchSortOrderPK());
            SearchSortOrderDetail searchSortOrderDetail = searchSortOrder.getActiveDetailForUpdate();

            searchSortOrderDetail.setThruTime(session.START_TIME_LONG);
            searchSortOrderDetail.store();

            SearchSortOrderPK searchSortOrderPK = searchSortOrderDetail.getSearchSortOrderPK();
            SearchKind searchKind = searchSortOrderDetail.getSearchKind();
            SearchKindPK searchKindPK = searchKind.getPrimaryKey();
            String searchSortOrderName = searchSortOrderDetailValue.getSearchSortOrderName();
            Boolean isDefault = searchSortOrderDetailValue.getIsDefault();
            Integer sortOrder = searchSortOrderDetailValue.getSortOrder();

            if(checkDefault) {
                SearchSortOrder defaultSearchSortOrder = getDefaultSearchSortOrder(searchKind);
                boolean defaultFound = defaultSearchSortOrder != null && !defaultSearchSortOrder.equals(searchSortOrder);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    SearchSortOrderDetailValue defaultSearchSortOrderDetailValue = getDefaultSearchSortOrderDetailValueForUpdate(searchKind);

                    defaultSearchSortOrderDetailValue.setIsDefault(Boolean.FALSE);
                    updateSearchSortOrderFromValue(defaultSearchSortOrderDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            searchSortOrderDetail = SearchSortOrderDetailFactory.getInstance().create(searchSortOrderPK, searchKindPK, searchSortOrderName, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            searchSortOrder.setActiveDetail(searchSortOrderDetail);
            searchSortOrder.setLastDetail(searchSortOrderDetail);

            sendEventUsingNames(searchSortOrderPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateSearchSortOrderFromValue(SearchSortOrderDetailValue searchSortOrderDetailValue, BasePK updatedBy) {
        updateSearchSortOrderFromValue(searchSortOrderDetailValue, true, updatedBy);
    }

    public void deleteSearchSortOrder(SearchSortOrder searchSortOrder, BasePK deletedBy) {
        deleteSearchSortOrderDescriptionsBySearchSortOrder(searchSortOrder, deletedBy);
        deletePartySearchTypePreferencesBySearchSortOrder(searchSortOrder, deletedBy);
        deleteCachedSearchesBySearchSortOrder(searchSortOrder, deletedBy);
        
        SearchSortOrderDetail searchSortOrderDetail = searchSortOrder.getLastDetailForUpdate();
        searchSortOrderDetail.setThruTime(session.START_TIME_LONG);
        searchSortOrder.setActiveDetail(null);
        searchSortOrder.store();

        // Check for default, and pick one if necessary
        SearchKind searchKind = searchSortOrderDetail.getSearchKind();
        SearchSortOrder defaultSearchSortOrder = getDefaultSearchSortOrder(searchKind);
        if(defaultSearchSortOrder == null) {
            List<SearchSortOrder> searchSortOrders = getSearchSortOrdersForUpdate(searchKind);

            if(!searchSortOrders.isEmpty()) {
                Iterator<SearchSortOrder> iter = searchSortOrders.iterator();
                if(iter.hasNext()) {
                    defaultSearchSortOrder = iter.next();
                }
                SearchSortOrderDetailValue searchSortOrderDetailValue = defaultSearchSortOrder.getLastDetailForUpdate().getSearchSortOrderDetailValue().clone();

                searchSortOrderDetailValue.setIsDefault(Boolean.TRUE);
                updateSearchSortOrderFromValue(searchSortOrderDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(searchSortOrder.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteSearchSortOrdersBySearchKind(SearchKind searchKind, BasePK deletedBy) {
        List<SearchSortOrder> searchSortOrders = getSearchSortOrdersForUpdate(searchKind);

        searchSortOrders.stream().forEach((searchSortOrder) -> {
            deleteSearchSortOrder(searchSortOrder, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Search Sort Order Descriptions
    // --------------------------------------------------------------------------------

    public SearchSortOrderDescription createSearchSortOrderDescription(SearchSortOrder searchSortOrder, Language language, String description,
            BasePK createdBy) {
        SearchSortOrderDescription searchSortOrderDescription = SearchSortOrderDescriptionFactory.getInstance().create(searchSortOrder,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(searchSortOrder.getPrimaryKey(), EventTypes.MODIFY.name(), searchSortOrderDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return searchSortOrderDescription;
    }

    private static final Map<EntityPermission, String> getSearchSortOrderDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM searchsortorderdescriptions "
                + "WHERE srchsrtordd_srchsrtord_searchsortorderid = ? AND srchsrtordd_lang_languageid = ? AND srchsrtordd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM searchsortorderdescriptions "
                + "WHERE srchsrtordd_srchsrtord_searchsortorderid = ? AND srchsrtordd_lang_languageid = ? AND srchsrtordd_thrutime = ? "
                + "FOR UPDATE");
        getSearchSortOrderDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private SearchSortOrderDescription getSearchSortOrderDescription(SearchSortOrder searchSortOrder, Language language, EntityPermission entityPermission) {
        return SearchSortOrderDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getSearchSortOrderDescriptionQueries,
                searchSortOrder, language, Session.MAX_TIME);
    }

    public SearchSortOrderDescription getSearchSortOrderDescription(SearchSortOrder searchSortOrder, Language language) {
        return getSearchSortOrderDescription(searchSortOrder, language, EntityPermission.READ_ONLY);
    }

    public SearchSortOrderDescription getSearchSortOrderDescriptionForUpdate(SearchSortOrder searchSortOrder, Language language) {
        return getSearchSortOrderDescription(searchSortOrder, language, EntityPermission.READ_WRITE);
    }

    public SearchSortOrderDescriptionValue getSearchSortOrderDescriptionValue(SearchSortOrderDescription searchSortOrderDescription) {
        return searchSortOrderDescription == null? null: searchSortOrderDescription.getSearchSortOrderDescriptionValue().clone();
    }

    public SearchSortOrderDescriptionValue getSearchSortOrderDescriptionValueForUpdate(SearchSortOrder searchSortOrder, Language language) {
        return getSearchSortOrderDescriptionValue(getSearchSortOrderDescriptionForUpdate(searchSortOrder, language));
    }

    private static final Map<EntityPermission, String> getSearchSortOrderDescriptionsBySearchSortOrderQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM searchsortorderdescriptions, languages "
                + "WHERE srchsrtordd_srchsrtord_searchsortorderid = ? AND srchsrtordd_thrutime = ? AND srchsrtordd_lang_languageid = lang_languageid "
                + "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM searchsortorderdescriptions "
                + "WHERE srchsrtordd_srchsrtord_searchsortorderid = ? AND srchsrtordd_thrutime = ? "
                + "FOR UPDATE");
        getSearchSortOrderDescriptionsBySearchSortOrderQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<SearchSortOrderDescription> getSearchSortOrderDescriptionsBySearchSortOrder(SearchSortOrder searchSortOrder, EntityPermission entityPermission) {
        return SearchSortOrderDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getSearchSortOrderDescriptionsBySearchSortOrderQueries,
                searchSortOrder, Session.MAX_TIME);
    }

    public List<SearchSortOrderDescription> getSearchSortOrderDescriptionsBySearchSortOrder(SearchSortOrder searchSortOrder) {
        return getSearchSortOrderDescriptionsBySearchSortOrder(searchSortOrder, EntityPermission.READ_ONLY);
    }

    public List<SearchSortOrderDescription> getSearchSortOrderDescriptionsBySearchSortOrderForUpdate(SearchSortOrder searchSortOrder) {
        return getSearchSortOrderDescriptionsBySearchSortOrder(searchSortOrder, EntityPermission.READ_WRITE);
    }

    public String getBestSearchSortOrderDescription(SearchSortOrder searchSortOrder, Language language) {
        String description;
        SearchSortOrderDescription searchSortOrderDescription = getSearchSortOrderDescription(searchSortOrder, language);

        if(searchSortOrderDescription == null && !language.getIsDefault()) {
            searchSortOrderDescription = getSearchSortOrderDescription(searchSortOrder, getPartyControl().getDefaultLanguage());
        }

        if(searchSortOrderDescription == null) {
            description = searchSortOrder.getLastDetail().getSearchSortOrderName();
        } else {
            description = searchSortOrderDescription.getDescription();
        }

        return description;
    }

    public SearchSortOrderDescriptionTransfer getSearchSortOrderDescriptionTransfer(UserVisit userVisit, SearchSortOrderDescription searchSortOrderDescription) {
        return getSearchTransferCaches(userVisit).getSearchSortOrderDescriptionTransferCache().getSearchSortOrderDescriptionTransfer(searchSortOrderDescription);
    }

    public List<SearchSortOrderDescriptionTransfer> getSearchSortOrderDescriptionTransfersBySearchSortOrder(UserVisit userVisit, SearchSortOrder searchSortOrder) {
        List<SearchSortOrderDescription> searchSortOrderDescriptions = getSearchSortOrderDescriptionsBySearchSortOrder(searchSortOrder);
        List<SearchSortOrderDescriptionTransfer> searchSortOrderDescriptionTransfers = new ArrayList<>(searchSortOrderDescriptions.size());

        searchSortOrderDescriptions.stream().forEach((searchSortOrderDescription) -> {
            searchSortOrderDescriptionTransfers.add(getSearchTransferCaches(userVisit).getSearchSortOrderDescriptionTransferCache().getSearchSortOrderDescriptionTransfer(searchSortOrderDescription));
        });

        return searchSortOrderDescriptionTransfers;
    }

    public void updateSearchSortOrderDescriptionFromValue(SearchSortOrderDescriptionValue searchSortOrderDescriptionValue, BasePK updatedBy) {
        if(searchSortOrderDescriptionValue.hasBeenModified()) {
            SearchSortOrderDescription searchSortOrderDescription = SearchSortOrderDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     searchSortOrderDescriptionValue.getPrimaryKey());

            searchSortOrderDescription.setThruTime(session.START_TIME_LONG);
            searchSortOrderDescription.store();

            SearchSortOrder searchSortOrder = searchSortOrderDescription.getSearchSortOrder();
            Language language = searchSortOrderDescription.getLanguage();
            String description = searchSortOrderDescriptionValue.getDescription();

            searchSortOrderDescription = SearchSortOrderDescriptionFactory.getInstance().create(searchSortOrder, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(searchSortOrder.getPrimaryKey(), EventTypes.MODIFY.name(), searchSortOrderDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteSearchSortOrderDescription(SearchSortOrderDescription searchSortOrderDescription, BasePK deletedBy) {
        searchSortOrderDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(searchSortOrderDescription.getSearchSortOrderPK(), EventTypes.MODIFY.name(), searchSortOrderDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteSearchSortOrderDescriptionsBySearchSortOrder(SearchSortOrder searchSortOrder, BasePK deletedBy) {
        List<SearchSortOrderDescription> searchSortOrderDescriptions = getSearchSortOrderDescriptionsBySearchSortOrderForUpdate(searchSortOrder);

        searchSortOrderDescriptions.stream().forEach((searchSortOrderDescription) -> {
            deleteSearchSortOrderDescription(searchSortOrderDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Searches
    // --------------------------------------------------------------------------------
    
    public Search createSearch(Party party, Boolean partyVerified, SearchType searchType, Long executedTime, SearchUseType searchUseType,
            CachedSearch cachedSearch, BasePK createdBy) {
        Search search = SearchFactory.getInstance().create(party, partyVerified, searchType, executedTime, searchUseType, cachedSearch,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(search.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return search;
    }
    
    private static final Map<EntityPermission, String> getSearchesByPartyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM searchresults "
                + "WHERE srch_par_partyid = ?"); // TODO: ORDER BY needed.
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM searchresults "
                + "WHERE srch_par_partyid = ? "
                + "FOR UPDATE");
        getSearchesByPartyQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Search> getSearchesByParty(Party party, EntityPermission entityPermission) {
        return SearchFactory.getInstance().getEntitiesFromQuery(entityPermission, getSearchesByPartyQueries,
                party);
    }

    public List<Search> getSearchesByParty(Party party) {
        return getSearchesByParty(party, EntityPermission.READ_ONLY);
    }
    
    public List<Search> getSearchesByPartyForUpdate(Party party) {
        return getSearchesByParty(party, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getSearchesBySearchTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM searchresults "
                + "WHERE srch_srchtyp_searchtypeid = ?"); // TODO: ORDER BY needed.
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM searchresults "
                + "WHERE srch_srchtyp_searchtypeid = ? "
                + "FOR UPDATE");
        getSearchesBySearchTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Search> getSearchesBySearchType(SearchType searchType, EntityPermission entityPermission) {
        return SearchFactory.getInstance().getEntitiesFromQuery(entityPermission, getSearchesBySearchTypeQueries,
                searchType);
    }

    public List<Search> getSearchesBySearchType(SearchType searchType) {
        return getSearchesBySearchType(searchType, EntityPermission.READ_ONLY);
    }
    
    public List<Search> getSearchesBySearchTypeForUpdate(SearchType searchType) {
        return getSearchesBySearchType(searchType, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getSearchesBySearchUseTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM searchresults "
                + "WHERE srch_srchutyp_searchusetypeid = ?"); // TODO: ORDER BY needed.
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM searchresults "
                + "WHERE srch_srchutyp_searchusetypeid = ? "
                + "FOR UPDATE");
        getSearchesBySearchUseTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Search> getSearchesBySearchUseType(SearchUseType searchUseType, EntityPermission entityPermission) {
        return SearchFactory.getInstance().getEntitiesFromQuery(entityPermission, getSearchesBySearchUseTypeQueries,
                searchUseType);
    }

    public List<Search> getSearchesBySearchUseType(SearchUseType searchUseType) {
        return getSearchesBySearchUseType(searchUseType, EntityPermission.READ_ONLY);
    }
    
    public List<Search> getSearchesBySearchUseTypeForUpdate(SearchUseType searchUseType) {
        return getSearchesBySearchUseType(searchUseType, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getSearchesByCachedSearchQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM searchresults "
                + "WHERE srch_csrch_cachedsearchid = ?"); // TODO: ORDER BY needed.
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM searchresults "
                + "WHERE srch_csrch_cachedsearchid = ? "
                + "FOR UPDATE");
        getSearchesByCachedSearchQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Search> getSearchesByCachedSearch(CachedSearch cachedSearch, EntityPermission entityPermission) {
        return SearchFactory.getInstance().getEntitiesFromQuery(entityPermission, getSearchesByCachedSearchQueries,
                cachedSearch);
    }

    public List<Search> getSearchesByCachedSearch(CachedSearch cachedSearch) {
        return getSearchesByCachedSearch(cachedSearch, EntityPermission.READ_ONLY);
    }
    
    public List<Search> getSearchesByCachedSearchForUpdate(CachedSearch cachedSearch) {
        return getSearchesByCachedSearch(cachedSearch, EntityPermission.READ_WRITE);
    }
    
    public void deleteSearch(Search search, BasePK deletedBy) {
        if(search.getCachedSearchPK() == null) {
            removeSearchResultsBySearch(search);
        }
        
        deleteSearchResultActionsBySearch(search, deletedBy);
        
        search.setThruTime(session.START_TIME_LONG);
        search.store();
        
        sendEventUsingNames(search.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteSearches(List<Search> searches, BasePK deletedBy) {
        searches.stream().forEach((search) -> {
            deleteSearch(search, deletedBy);
        });
    }

    public void deleteSearchesByParty(Party party, BasePK deletedBy) {
        deleteSearches(getSearchesByParty(party), deletedBy);
    }

    public void deleteSearchesBySearchType(SearchType searchType, BasePK deletedBy) {
        deleteSearches(getSearchesBySearchTypeForUpdate(searchType), deletedBy);
    }

    public void deleteSearchesBySearchUseType(SearchUseType searchUseType, BasePK deletedBy) {
        deleteSearches(getSearchesBySearchUseTypeForUpdate(searchUseType), deletedBy);
    }

    public void deleteSearchesByCachedSearch(CachedSearch cachedSearch, BasePK deletedBy) {
        deleteSearches(getSearchesByCachedSearchForUpdate(cachedSearch), deletedBy);
    }
    
    public void removeSearch(Search search) {
        getCoreControl().removeEntityInstanceByBasePK(search.getPrimaryKey());
        search.remove();
    }
    
    // --------------------------------------------------------------------------------
    //   Search Results
    // --------------------------------------------------------------------------------
    
    public void createSearchResults(Collection<SearchResultValue> searchResults) {
        SearchResultFactory.getInstance().create(searchResults);
    }
    
    public boolean searchResultExists(Search search, EntityInstance entityInstance) {
        return session.queryForInteger(
                "SELECT COUNT(*) " +
                "FROM searchresults " +
                "WHERE srchr_srch_searchid = ? AND srchr_eni_entityinstanceid = ?",
                search, entityInstance) == 1;
    }
    
    private static final Map<EntityPermission, String> getSearchResultsBySearchQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM searchresults "
                + "WHERE srchr_srch_searchid = ? "
                + "ORDER BY srchr_sortorder");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM searchresults "
                + "WHERE srchr_srch_searchid = ? "
                + "FOR UPDATE");
        getSearchResultsBySearchQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<SearchResult> getSearchResultsBySearch(Search search, EntityPermission entityPermission) {
        return SearchResultFactory.getInstance().getEntitiesFromQuery(entityPermission, getSearchResultsBySearchQueries,
                search);
    }

    public List<SearchResult> getSearchResultsBySearch(Search search) {
        return getSearchResultsBySearch(search, EntityPermission.READ_ONLY);
    }
    
    public List<SearchResult> getSearchResultsBySearchForUpdate(Search search) {
        return getSearchResultsBySearch(search, EntityPermission.READ_WRITE);
    }
    
    public int countSearchResults(Search search) {
        return session.queryForInteger(
                "SELECT COUNT(*) " +
                "FROM searchresults " +
                "WHERE srchr_srch_searchid = ?",
                search);
    }

    public void removeSearchResultsBySearch(Search search) {
        session.query(
                "DELETE FROM searchresults "
                + "WHERE srchr_srch_searchid = ?",
                search);
    }
    
    public void removeSearchResultsByEntityInstance(EntityInstance entityInstance) {
        session.query(
                "DELETE FROM searchresults "
                + "WHERE srchr_eni_entityinstanceid = ?",
                entityInstance);
    }
    
    // --------------------------------------------------------------------------------
    //   Cached Searches
    // --------------------------------------------------------------------------------

    public CachedSearch createCachedSearch(Index index, String querySha1Hash, String query, String parsedQuerySha1Hash, String parsedQuery,
            SearchDefaultOperator searchDefaultOperator, SearchSortOrder searchSortOrder, SearchSortDirection searchSortDirection, BasePK createdBy) {
        CachedSearch cachedSearch = CachedSearchFactory.getInstance().create(index, querySha1Hash, query, parsedQuerySha1Hash, parsedQuery,
                searchDefaultOperator, searchSortOrder, searchSortDirection, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(cachedSearch.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return cachedSearch;
    }
    
    private static final Map<EntityPermission, String> getCachedSearchQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM cachedsearches "
                + "WHERE csrch_idx_indexid = ? AND csrch_querysha1hash = ? AND csrch_parsedquerysha1hash = ? "
                + "AND csrch_srchdefop_searchdefaultoperatorid = ? AND csrch_srchsrtord_searchsortorderid = ? AND csrch_srchsrtdir_searchsortdirectionid = ? "
                + "AND csrch_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM cachedsearches "
                + "WHERE csrch_idx_indexid = ? AND csrch_querysha1hash = ? AND csrch_parsedquerysha1hash = ? "
                + "AND csrch_srchdefop_searchdefaultoperatorid = ? AND csrch_srchsrtord_searchsortorderid = ? AND csrch_srchsrtdir_searchsortdirectionid = ? "
                + "AND csrch_thrutime = ? "
                + "FOR UPDATE");
        getCachedSearchQueries = Collections.unmodifiableMap(queryMap);
    }

    private CachedSearch getCachedSearch(Index index, String querySha1Hash, String parsedQuerySha1Hash, SearchDefaultOperator searchDefaultOperator,
            SearchSortOrder searchSortOrder, SearchSortDirection searchSortDirection, EntityPermission entityPermission) {
        return CachedSearchFactory.getInstance().getEntityFromQuery(entityPermission, getCachedSearchQueries,
                index, querySha1Hash, parsedQuerySha1Hash, searchDefaultOperator, searchSortOrder, searchSortDirection, Session.MAX_TIME_LONG);
    }

    public CachedSearch getCachedSearch(Index index, String querySha1Hash, String parsedQuerySha1Hash, SearchDefaultOperator searchDefaultOperator,
            SearchSortOrder searchSortOrder, SearchSortDirection searchSortDirection) {
        return getCachedSearch(index, querySha1Hash, parsedQuerySha1Hash, searchDefaultOperator, searchSortOrder, searchSortDirection,
                EntityPermission.READ_ONLY);
    }

    public CachedSearch getCachedSearchForUpdate(Index index, String querySha1Hash, String parsedQuerySha1Hash, SearchDefaultOperator searchDefaultOperator,
            SearchSortOrder searchSortOrder, SearchSortDirection searchSortDirection) {
        return getCachedSearch(index, querySha1Hash, parsedQuerySha1Hash, searchDefaultOperator, searchSortOrder, searchSortDirection,
                EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getCachedSearchesByIndexQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM cachedsearches "
                + "WHERE csrch_idx_indexid = ? AND csrch_thrutime = ? "
                + "FOR UPDATE");
        getCachedSearchesByIndexQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<CachedSearch> getCachedSearchesByIndex(Index index, EntityPermission entityPermission) {
        return CachedSearchFactory.getInstance().getEntitiesFromQuery(entityPermission, getCachedSearchesByIndexQueries,
                index, Session.MAX_TIME_LONG);
    }

    public List<CachedSearch> getCachedSearchesByIndexForUpdate(Index index) {
        return getCachedSearchesByIndex(index, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getCachedSearchQueriesBySearchDefaultOperator;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM cachedsearches "
                + "WHERE csrch_srchdefop_searchdefaultoperatorid = ? AND csrch_thrutime = ? "
                + "FOR UPDATE");
        getCachedSearchQueriesBySearchDefaultOperator = Collections.unmodifiableMap(queryMap);
    }

    private List<CachedSearch> getCachedSearchesBySearchDefaultOperator(SearchDefaultOperator searchDefaultOperator, EntityPermission entityPermission) {
        return CachedSearchFactory.getInstance().getEntitiesFromQuery(entityPermission, getCachedSearchQueriesBySearchDefaultOperator,
                searchDefaultOperator, Session.MAX_TIME_LONG);
    }

    public List<CachedSearch> getCachedSearchesBySearchDefaultOperatorForUpdate(SearchDefaultOperator searchDefaultOperator) {
        return getCachedSearchesBySearchDefaultOperator(searchDefaultOperator, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getCachedSearchQueriesBySearchSortOrder;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM cachedsearches "
                + "WHERE csrch_srchsrtord_searchsortorderid = ? AND csrch_thrutime = ? "
                + "FOR UPDATE");
        getCachedSearchQueriesBySearchSortOrder = Collections.unmodifiableMap(queryMap);
    }

    private List<CachedSearch> getCachedSearchesBySearchSortOrder(SearchSortOrder searchSortOrder, EntityPermission entityPermission) {
        return CachedSearchFactory.getInstance().getEntitiesFromQuery(entityPermission, getCachedSearchQueriesBySearchSortOrder,
                searchSortOrder, Session.MAX_TIME_LONG);
    }

    public List<CachedSearch> getCachedSearchesBySearchSortOrderForUpdate(SearchSortOrder searchSortOrder) {
        return getCachedSearchesBySearchSortOrder(searchSortOrder, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getCachedSearchQueriesBySearchSortDirection;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM cachedsearches "
                + "WHERE csrch_srchsrtdir_searchsortdirectionid = ? AND csrch_thrutime = ? "
                + "FOR UPDATE");
        getCachedSearchQueriesBySearchSortDirection = Collections.unmodifiableMap(queryMap);
    }

    private List<CachedSearch> getCachedSearchesBySearchSortDirection(SearchSortDirection searchSortDirection, EntityPermission entityPermission) {
        return CachedSearchFactory.getInstance().getEntitiesFromQuery(entityPermission, getCachedSearchQueriesBySearchSortDirection,
                searchSortDirection, Session.MAX_TIME_LONG);
    }

    public List<CachedSearch> getCachedSearchesBySearchSortDirectionForUpdate(SearchSortDirection searchSortDirection) {
        return getCachedSearchesBySearchSortDirection(searchSortDirection, EntityPermission.READ_WRITE);
    }
    
    public void deleteCachedSearch(CachedSearch cachedSearch, BasePK deletedBy) {
        CachedExecutedSearch cachedExecutedSearch = getCachedExecutedSearchForUpdate(cachedSearch);
        
        if(cachedExecutedSearch != null) {
            deleteCachedExecutedSearch(cachedExecutedSearch, deletedBy);
        }
        
        deleteCachedSearchIndexFieldsByCachedSearch(cachedSearch, deletedBy);
        deleteSearchesByCachedSearch(cachedSearch, deletedBy);
        
        cachedSearch.setThruTime(session.START_TIME_LONG);
        cachedSearch.store();

        sendEventUsingNames(cachedSearch.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deletedCachedSearches(List<CachedSearch> cachedSearches, BasePK deletedBy) {
        cachedSearches.stream().forEach((cachedSearch) -> {
            deleteCachedSearch(cachedSearch, deletedBy);
        });
    }
    
    public void deleteCachedSearchesByIndex(Index index, BasePK deletedBy) {
        deletedCachedSearches(getCachedSearchesByIndexForUpdate(index), deletedBy);
    }
    
    public void deleteCachedSearchesBySearchDefaultOperator(SearchDefaultOperator searchDefaultOperator, BasePK deletedBy) {
        deletedCachedSearches(getCachedSearchesBySearchDefaultOperatorForUpdate(searchDefaultOperator), deletedBy);
    }
    
    public void deleteCachedSearchesBySearchSortOrder(SearchSortOrder searchSortOrder, BasePK deletedBy) {
        deletedCachedSearches(getCachedSearchesBySearchSortOrderForUpdate(searchSortOrder), deletedBy);
    }
    
    public void deleteCachedSearchesBySearchSortDirection(SearchSortDirection searchSortDirection, BasePK deletedBy) {
        deletedCachedSearches(getCachedSearchesBySearchSortDirectionForUpdate(searchSortDirection), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Cached Search Statuses
    // --------------------------------------------------------------------------------

    public CachedSearchStatus createCachedSearchStatus(CachedSearch cachedSearch) {
        return CachedSearchStatusFactory.getInstance().create(cachedSearch, Boolean.TRUE);
    }

    private static final Map<EntityPermission, String> getCachedSearchStatusQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM cachedsearchstatuses " +
                "WHERE csrchst_csrch_cachedsearchid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM cachedsearchstatuses " +
                "WHERE csrchst_csrch_cachedsearchid = ? " +
                "FOR UPDATE");
        getCachedSearchStatusQueries = Collections.unmodifiableMap(queryMap);
    }

    private CachedSearchStatus getCachedSearchStatus(CachedSearch cachedSearch, EntityPermission entityPermission) {
        return CachedSearchStatusFactory.getInstance().getEntityFromQuery(entityPermission, getCachedSearchStatusQueries, cachedSearch);
    }

    public CachedSearchStatus getCachedSearchStatus(CachedSearch cachedSearch) {
        CachedSearchStatus cachedSearchStatus = getCachedSearchStatus(cachedSearch, EntityPermission.READ_ONLY);

        return cachedSearchStatus == null ? createCachedSearchStatus(cachedSearch) : cachedSearchStatus;
    }

    public CachedSearchStatus getCachedSearchStatusForUpdate(CachedSearch cachedSearch) {
        CachedSearchStatus cachedSearchStatus = getCachedSearchStatus(cachedSearch, EntityPermission.READ_WRITE);

        return cachedSearchStatus == null
                ? CachedSearchStatusFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, createCachedSearchStatus(cachedSearch).getPrimaryKey())
                : cachedSearchStatus;
    }

    // --------------------------------------------------------------------------------
    //   Cached Search Index Fields
    // --------------------------------------------------------------------------------

    public CachedSearchIndexField createCachedSearchIndexField(CachedSearch cachedSearch, IndexField indexField, BasePK createdBy) {
        CachedSearchIndexField cachedSearchIndexField = CachedSearchIndexFieldFactory.getInstance().create(cachedSearch, indexField, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(cachedSearch.getPrimaryKey(), EventTypes.MODIFY.name(), cachedSearchIndexField.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return cachedSearchIndexField;
    }
    
    private static final Map<EntityPermission, String> getCachedSearchIndexFieldQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM cachedsearchindexfields " +
                "WHERE csrchidxfld_csrch_cachedsearchid = ? AND csrchidxfld_idxfld_indexfieldid = ? AND csrchidxfld_thrutime = ?");
        getCachedSearchIndexFieldQueries = Collections.unmodifiableMap(queryMap);
    }

    private CachedSearchIndexField getCachedSearchIndexField(CachedSearch cachedSearch, IndexField indexField, EntityPermission entityPermission) {
        return CachedSearchIndexFieldFactory.getInstance().getEntityFromQuery(entityPermission, getCachedSearchIndexFieldQueries,
                cachedSearch, indexField, Session.MAX_TIME_LONG);
    }

    public CachedSearchIndexField getCachedSearchIndexField(CachedSearch cachedSearch, IndexField indexField) {
        return getCachedSearchIndexField(cachedSearch, indexField, EntityPermission.READ_ONLY);
    }

    private static final Map<EntityPermission, String> getCachedSearchIndexFieldsByCachedSearchQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM cachedsearchindexfields " +
                "WHERE csrchidxfld_csrch_cachedsearchid = ? AND csrchidxfld_thrutime = ? " +
                "FOR UPDATE");
        getCachedSearchIndexFieldsByCachedSearchQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<CachedSearchIndexField> getCachedSearchIndexFieldsByCachedSearch(CachedSearch cachedSearch, EntityPermission entityPermission) {
        return CachedSearchIndexFieldFactory.getInstance().getEntitiesFromQuery(entityPermission, getCachedSearchIndexFieldsByCachedSearchQueries,
                cachedSearch, Session.MAX_TIME_LONG);
    }

    public List<CachedSearchIndexField> getCachedSearchIndexFieldsByCachedSearchForUpdate(CachedSearch cachedSearch) {
        return getCachedSearchIndexFieldsByCachedSearch(cachedSearch, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getCachedSearchIndexFieldsBySearchIndexFieldTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM cachedsearchindexfields " +
                "WHERE csrchidxfld_idxfld_indexfieldid = ? AND csrchidxfld_thrutime = ? " +
                "FOR UPDATE");
        getCachedSearchIndexFieldsBySearchIndexFieldTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<CachedSearchIndexField> getCachedSearchIndexFieldsByIndexField(IndexField indexField, EntityPermission entityPermission) {
        return CachedSearchIndexFieldFactory.getInstance().getEntitiesFromQuery(entityPermission, getCachedSearchIndexFieldsBySearchIndexFieldTypeQueries,
                indexField, Session.MAX_TIME_LONG);
    }

    public List<CachedSearchIndexField> getCachedSearchIndexFieldsByIndexFieldForUpdate(IndexField indexField) {
        return getCachedSearchIndexFieldsByIndexField(indexField, EntityPermission.READ_WRITE);
    }

    
    public void deleteCachedSearchIndexField(CachedSearchIndexField cachedSearchIndexField, BasePK deletedBy) {
        cachedSearchIndexField.setThruTime(session.START_TIME_LONG);
        cachedSearchIndexField.store();

        sendEventUsingNames(cachedSearchIndexField.getCachedSearchPK(), EventTypes.MODIFY.name(), cachedSearchIndexField.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteCachedSearchIndexFields(List<CachedSearchIndexField> cachedSearchIndexFields, BasePK deletedBy) {
        cachedSearchIndexFields.stream().forEach((cachedSearchIndexField) -> {
            deleteCachedSearchIndexField(cachedSearchIndexField, deletedBy);
        });
    }
    
    public void deleteCachedSearchIndexFieldsByCachedSearch(CachedSearch cachedSearch, BasePK deletedBy) {
        deleteCachedSearchIndexFields(getCachedSearchIndexFieldsByCachedSearchForUpdate(cachedSearch), deletedBy);
    }

    public void deleteCachedSearchIndexFieldsByIndexField(IndexField indexField, BasePK deletedBy) {
        deleteCachedSearchIndexFields(getCachedSearchIndexFieldsByIndexFieldForUpdate(indexField), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Cached Executed Searches
    // --------------------------------------------------------------------------------

    public CachedExecutedSearch createCachedExecutedSearch(CachedSearch cachedSearch, BasePK createdBy) {
        CachedExecutedSearch cachedExecutedSearch = CachedExecutedSearchFactory.getInstance().create(cachedSearch, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(cachedSearch.getPrimaryKey(), EventTypes.MODIFY.name(), cachedExecutedSearch.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return cachedExecutedSearch;
    }
    
    private static final Map<EntityPermission, String> getCachedExecutedSearchQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM cachedexecutedsearches "
                + "WHERE cxsrch_csrch_cachedsearchid = ? AND cxsrch_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM cachedexecutedsearches "
                + "WHERE cxsrch_csrch_cachedsearchid = ? AND cxsrch_thrutime = ? "
                + "FOR UPDATE");
        getCachedExecutedSearchQueries = Collections.unmodifiableMap(queryMap);
    }

    private CachedExecutedSearch getCachedExecutedSearch(CachedSearch cachedSearch, EntityPermission entityPermission) {
        return CachedExecutedSearchFactory.getInstance().getEntityFromQuery(entityPermission, getCachedExecutedSearchQueries,
                cachedSearch, Session.MAX_TIME_LONG);
    }

    public CachedExecutedSearch getCachedExecutedSearch(CachedSearch cachedSearch) {
        return getCachedExecutedSearch(cachedSearch, EntityPermission.READ_ONLY);
    }
    
    public CachedExecutedSearch getCachedExecutedSearchForUpdate(CachedSearch cachedSearch) {
        return getCachedExecutedSearch(cachedSearch, EntityPermission.READ_WRITE);
    }
    
    public void deleteCachedExecutedSearch(CachedExecutedSearch cachedExecutedSearch, BasePK deletedBy) {
        removeCachedExecutedSearchResultsByCachedExecutedSearch(cachedExecutedSearch);
        
        cachedExecutedSearch.setThruTime(session.START_TIME_LONG);
        cachedExecutedSearch.store();

        sendEventUsingNames(cachedExecutedSearch.getCachedSearchPK(), EventTypes.MODIFY.name(), cachedExecutedSearch.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Cached Executed Search Results
    // --------------------------------------------------------------------------------

    public void createCachedExecutedSearchResults(Collection<CachedExecutedSearchResultValue> cachedExecutedSearchResults) {
        CachedExecutedSearchResultFactory.getInstance().create(cachedExecutedSearchResults);
    }
    
    public boolean cachedExecutedSearchResultExists(CachedExecutedSearch cachedExecutedSearch, EntityInstance entityInstance) {
        return session.queryForInteger(
                "SELECT COUNT(*) " +
                "FROM cachedexecutedsearchresults " +
                "WHERE cxsrchr_cxsrch_cachedexecutedsearchid = ? AND cxsrchr_eni_entityinstanceid = ?",
                cachedExecutedSearch, entityInstance) == 1;
    }
    
    private static final Map<EntityPermission, String> getCachedExecutedSearchResultsByCachedExecutedSearchQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM cachedexecutedsearchresults "
                + "WHERE cxsrchr_cxsrch_cachedexecutedsearchid = ? "
                + "ORDER BY cxsrchr_sortorder");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM cachedexecutedsearchresults "
                + "WHERE cxsrchr_cxsrch_cachedexecutedsearchid = ? "
                + "FOR UPDATE");
        getCachedExecutedSearchResultsByCachedExecutedSearchQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<CachedExecutedSearchResult> getCachedExecutedSearchResultsByCachedExecutedSearch(CachedExecutedSearch cachedExecutedSearch, EntityPermission entityPermission) {
        return CachedExecutedSearchResultFactory.getInstance().getEntitiesFromQuery(entityPermission, getCachedExecutedSearchResultsByCachedExecutedSearchQueries,
                cachedExecutedSearch);
    }

    public List<CachedExecutedSearchResult> getCachedExecutedSearchResultsByCachedExecutedSearch(CachedExecutedSearch cachedExecutedSearch) {
        return getCachedExecutedSearchResultsByCachedExecutedSearch(cachedExecutedSearch, EntityPermission.READ_ONLY);
    }
    
    public List<CachedExecutedSearchResult> getCachedExecutedSearchResultsByCachedExecutedSearchForUpdate(CachedExecutedSearch cachedExecutedSearch) {
        return getCachedExecutedSearchResultsByCachedExecutedSearch(cachedExecutedSearch, EntityPermission.READ_WRITE);
    }
    
    public int countCachedExecutedSearchResults(CachedExecutedSearch cachedExecutedSearch) {
        return session.queryForInteger(
                "SELECT COUNT(*) "
                + "FROM cachedexecutedsearchresults "
                + "WHERE cxsrchr_cxsrch_cachedexecutedsearchid = ?",
                cachedExecutedSearch);
    }

    public void removeCachedExecutedSearchResultsByCachedExecutedSearch(CachedExecutedSearch cachedExecutedSearch) {
        session.query(
                "DELETE FROM cachedexecutedsearchresults "
                + "WHERE cxsrchr_cxsrch_cachedexecutedsearchid = ?",
                cachedExecutedSearch);
    }

    public void removeCachedExecutedSearchResultsByEntityInstance(EntityInstance entityInstance) {
        session.query(
                "DELETE FROM cachedexecutedsearchresults "
                + "WHERE cxsrchr_eni_entityinstanceid = ?",
                entityInstance);
    }
    
    // --------------------------------------------------------------------------------
    //   User Visit Searches
    // --------------------------------------------------------------------------------

    public UserVisitSearch createUserVisitSearch(UserVisit userVisit, SearchType searchType, Search search) {
        return UserVisitSearchFactory.getInstance().create(userVisit, searchType, search);
    }
    
    private static final Map<EntityPermission, String> getUserVisitSearchQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM uservisitsearches "
                + "WHERE uvissrch_uvis_uservisitid = ? AND uvissrch_srchtyp_searchtypeid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM uservisitsearches "
                + "WHERE uvissrch_uvis_uservisitid = ? AND uvissrch_srchtyp_searchtypeid = ? "
                + "FOR UPDATE");
        getUserVisitSearchQueries = Collections.unmodifiableMap(queryMap);
    }

    private UserVisitSearch getUserVisitSearch(UserVisit userVisit, SearchType searchType, EntityPermission entityPermission) {
        return UserVisitSearchFactory.getInstance().getEntityFromQuery(entityPermission, getUserVisitSearchQueries,
                userVisit, searchType);
    }

    public UserVisitSearch getUserVisitSearch(UserVisit userVisit, SearchType searchType) {
        return getUserVisitSearch(userVisit, searchType, EntityPermission.READ_ONLY);
    }
    
    public UserVisitSearch getUserVisitSearchForUpdate(UserVisit userVisit, SearchType searchType) {
        return getUserVisitSearch(userVisit, searchType, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getUserVisitSearchesByUserVisitQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM uservisitsearches "
                + "WHERE uvissrch_uvis_uservisitid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM uservisitsearches "
                + "WHERE uvissrch_uvis_uservisitid = ? "
                + "FOR UPDATE");
        getUserVisitSearchesByUserVisitQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<UserVisitSearch> getUserVisitSearchesByUserVisit(UserVisit userVisit, EntityPermission entityPermission) {
        return UserVisitSearchFactory.getInstance().getEntitiesFromQuery(entityPermission, getUserVisitSearchesByUserVisitQueries,
                userVisit);
    }

    public List<UserVisitSearch> getUserVisitSearchesByUserVisit(UserVisit userVisit) {
        return getUserVisitSearchesByUserVisit(userVisit, EntityPermission.READ_ONLY);
    }
    
    public List<UserVisitSearch> getUserVisitSearchesByUserVisitForUpdate(UserVisit userVisit) {
        return getUserVisitSearchesByUserVisit(userVisit, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getUserVisitSearchesBySearchTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM uservisitsearches "
                + "WHERE uvissrch_srchtyp_searchtypeid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM uservisitsearches "
                + "WHERE uvissrch_srchtyp_searchtypeid = ? "
                + "FOR UPDATE");
        getUserVisitSearchesBySearchTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<UserVisitSearch> getUserVisitSearchesBySearchType(SearchType searchType, EntityPermission entityPermission) {
        return UserVisitSearchFactory.getInstance().getEntitiesFromQuery(entityPermission, getUserVisitSearchesBySearchTypeQueries,
                searchType);
    }

    public List<UserVisitSearch> getUserVisitSearchesBySearchType(SearchType searchType) {
        return getUserVisitSearchesBySearchType(searchType, EntityPermission.READ_ONLY);
    }
    
    public List<UserVisitSearch> getUserVisitSearchesBySearchTypeForUpdate(SearchType searchType) {
        return getUserVisitSearchesBySearchType(searchType, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getUserVisitSearchesBySearchQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM uservisitsearches "
                + "WHERE uvissrch_srch_searchid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM uservisitsearches "
                + "WHERE uvissrch_srch_searchid = ? "
                + "FOR UPDATE");
        getUserVisitSearchesBySearchQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<UserVisitSearch> getUserVisitSearchesBySearch(Search search, EntityPermission entityPermission) {
        return UserVisitSearchFactory.getInstance().getEntitiesFromQuery(entityPermission, getUserVisitSearchesBySearchQueries,
                search);
    }

    public List<UserVisitSearch> getUserVisitSearchesBySearch(Search search) {
        return getUserVisitSearchesBySearch(search, EntityPermission.READ_ONLY);
    }
    
    public List<UserVisitSearch> getUserVisitSearchesBySearchForUpdate(Search search) {
        return getUserVisitSearchesBySearch(search, EntityPermission.READ_WRITE);
    }
    
    public void removeUserVisitSearch(UserVisitSearch userVisitSearch) {
        Search search = userVisitSearch.getSearch();
        
        // If it isn't a CachedSearch, then the results need to be removed.
        if(search.getCachedSearch() == null) {
            removeSearchResultsBySearch(search);
        }
        
        userVisitSearch.remove();
    }
    
    public void removeUserVisitSearches(List<UserVisitSearch> userVisitSearches) {
        userVisitSearches.stream().forEach((userVisitSearch) -> {
            removeUserVisitSearch(userVisitSearch);
        });
    }
    
    public void removeUserVisitSearchesByUserVisit(UserVisit userVisit) {
        removeUserVisitSearches(getUserVisitSearchesByUserVisitForUpdate(userVisit));
    }
    
    public void removeUserVisitSearchesBySearchType(SearchType searchType) {
        removeUserVisitSearches(getUserVisitSearchesBySearchTypeForUpdate(searchType));
    }
    
    public void removeUserVisitSearchesBySearch(Search search) {
        removeUserVisitSearches(getUserVisitSearchesBySearchForUpdate(search));
    }
    
    // --------------------------------------------------------------------------------
    //   Party Search Type Preferences
    // --------------------------------------------------------------------------------

    public PartySearchTypePreference createPartySearchTypePreference(Party party, SearchType searchType, SearchDefaultOperator searchDefaultOperator,
            SearchSortOrder searchSortOrder, SearchSortDirection searchSortDirection, BasePK createdBy) {
        PartySearchTypePreference partySearchTypePreference = PartySearchTypePreferenceFactory.getInstance().create();
        PartySearchTypePreferenceDetail partySearchTypePreferenceDetail = PartySearchTypePreferenceDetailFactory.getInstance().create(partySearchTypePreference,
                party, searchType, searchDefaultOperator, searchSortOrder, searchSortDirection, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        partySearchTypePreference = PartySearchTypePreferenceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, partySearchTypePreference.getPrimaryKey());
        partySearchTypePreference.setActiveDetail(partySearchTypePreferenceDetail);
        partySearchTypePreference.setLastDetail(partySearchTypePreferenceDetail);
        partySearchTypePreference.store();

        sendEventUsingNames(party.getPrimaryKey(), EventTypes.MODIFY.name(), partySearchTypePreference.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return partySearchTypePreference;
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.PartySearchTypePreference */
    public PartySearchTypePreference getPartySearchTypePreferenceByEntityInstance(EntityInstance entityInstance) {
        PartySearchTypePreferencePK pk = new PartySearchTypePreferencePK(entityInstance.getEntityUniqueId());
        PartySearchTypePreference partySearchTypePreference = PartySearchTypePreferenceFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return partySearchTypePreference;
    }

    private static final Map<EntityPermission, String> getPartySearchTypePreferenceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM partysearchtypepreferences, partysearchtypepreferencedetails "
                + "WHERE parsrchtypprf_activedetailid = parsrchtypprfdt_partysearchtypepreferencedetailid "
                + "AND parsrchtypprfdt_par_partyid = ? AND parsrchtypprfdt_srchtyp_searchtypeid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM partysearchtypepreferences, partysearchtypepreferencedetails "
                + "WHERE parsrchtypprf_activedetailid = parsrchtypprfdt_partysearchtypepreferencedetailid "
                + "AND parsrchtypprfdt_par_partyid = ? AND parsrchtypprfdt_srchtyp_searchtypeid = ? "
                + "FOR UPDATE");
        getPartySearchTypePreferenceQueries = Collections.unmodifiableMap(queryMap);
    }

    private PartySearchTypePreference getPartySearchTypePreference(Party party, SearchType searchType, EntityPermission entityPermission) {
        return PartySearchTypePreferenceFactory.getInstance().getEntityFromQuery(entityPermission, getPartySearchTypePreferenceQueries, party, searchType);
    }

    public PartySearchTypePreference getPartySearchTypePreference(Party party, SearchType searchType) {
        return getPartySearchTypePreference(party, searchType, EntityPermission.READ_ONLY);
    }

    public PartySearchTypePreference getPartySearchTypePreferenceForUpdate(Party party, SearchType searchType) {
        return getPartySearchTypePreference(party, searchType, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPartySearchTypePreferencesByPartyForUpdateQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM partysearchtypepreferences, partysearchtypepreferencedetails "
                + "WHERE parsrchtypprf_activedetailid = parsrchtypprfdt_partysearchtypepreferencedetailid "
                + "AND parsrchtypprfdt_par_partyid = ? "
                + "FOR UPDATE");
        getPartySearchTypePreferencesByPartyForUpdateQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<PartySearchTypePreference> getPartySearchTypePreferencesByPartyForUpdate(Party party) {
        return PartySearchTypePreferenceFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, getPartySearchTypePreferencesByPartyForUpdateQueries,
                party);
    }

    private static final Map<EntityPermission, String> getPartySearchTypePreferencesBySearchTypeForUpdateQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM partysearchtypepreferences, partysearchtypepreferencedetails "
                + "WHERE parsrchtypprf_activedetailid = parsrchtypprfdt_partysearchtypepreferencedetailid "
                + "AND parsrchtypprfdt_srchtyp_searchtypeid = ? "
                + "FOR UPDATE");
        getPartySearchTypePreferencesBySearchTypeForUpdateQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<PartySearchTypePreference> getPartySearchTypePreferencesBySearchTypeForUpdate(SearchType searchType) {
        return PartySearchTypePreferenceFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, getPartySearchTypePreferencesBySearchTypeForUpdateQueries,
                searchType);
    }

    private static final Map<EntityPermission, String> getPartySearchTypePreferencesBySearchDefaultOperatorForUpdateQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM partysearchtypepreferences, partysearchtypepreferencedetails "
                + "WHERE parsrchtypprf_activedetailid = parsrchtypprfdt_partysearchtypepreferencedetailid "
                + "AND parsrchtypprfdt_srchdefop_searchdefaultoperatorid = ? "
                + "FOR UPDATE");
        getPartySearchTypePreferencesBySearchDefaultOperatorForUpdateQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<PartySearchTypePreference> getPartySearchTypePreferencesBySearchDefaultOperatorForUpdate(SearchDefaultOperator searchDefaultOperator) {
        return PartySearchTypePreferenceFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, getPartySearchTypePreferencesBySearchDefaultOperatorForUpdateQueries,
                searchDefaultOperator);
    }

    private static final Map<EntityPermission, String> getPartySearchTypePreferencesBySearchSortOrderForUpdateQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM partysearchtypepreferences, partysearchtypepreferencedetails "
                + "WHERE parsrchtypprf_activedetailid = parsrchtypprfdt_partysearchtypepreferencedetailid "
                + "AND parsrchtypprfdt_srchsrtord_searchsortorderid = ? "
                + "FOR UPDATE");
        getPartySearchTypePreferencesBySearchSortOrderForUpdateQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<PartySearchTypePreference> getPartySearchTypePreferencesBySearchSortOrderForUpdate(SearchSortOrder searchSortOrder) {
        return PartySearchTypePreferenceFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, getPartySearchTypePreferencesBySearchSortOrderForUpdateQueries,
                searchSortOrder);
    }

    private static final Map<EntityPermission, String> getPartySearchTypePreferencesBySearchSortDirectionForUpdateQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM partysearchtypepreferences, partysearchtypepreferencedetails "
                + "WHERE parsrchtypprf_activedetailid = parsrchtypprfdt_partysearchtypepreferencedetailid "
                + "AND parsrchtypprfdt_srchsrtdir_searchsortdirectionid = ? "
                + "FOR UPDATE");
        getPartySearchTypePreferencesBySearchSortDirectionForUpdateQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<PartySearchTypePreference> getPartySearchTypePreferencesBySearchSortDirectionForUpdate(SearchSortDirection searchSortDirection) {
        return PartySearchTypePreferenceFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, getPartySearchTypePreferencesBySearchSortDirectionForUpdateQueries,
                searchSortDirection);
    }

    public PartySearchTypePreferenceDetailValue getPartySearchTypePreferenceDetailValueForUpdate(PartySearchTypePreference partySearchTypePreference) {
        return partySearchTypePreference == null? null: partySearchTypePreference.getLastDetailForUpdate().getPartySearchTypePreferenceDetailValue().clone();
    }

    public PartySearchTypePreferenceDetailValue getPartySearchTypePreferenceDetailValueForUpdate(Party party, SearchType searchType) {
        return getPartySearchTypePreferenceDetailValueForUpdate(getPartySearchTypePreferenceForUpdate(party, searchType));
    }

    public void updatePartySearchTypePreferenceFromValue(PartySearchTypePreferenceDetailValue partySearchTypePreferenceDetailValue, BasePK updatedBy) {
        if(partySearchTypePreferenceDetailValue.hasBeenModified()) {
            PartySearchTypePreference partySearchTypePreference = PartySearchTypePreferenceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     partySearchTypePreferenceDetailValue.getPartySearchTypePreferencePK());
            PartySearchTypePreferenceDetail partySearchTypePreferenceDetail = partySearchTypePreference.getActiveDetailForUpdate();

            partySearchTypePreferenceDetail.setThruTime(session.START_TIME_LONG);
            partySearchTypePreferenceDetail.store();

            PartySearchTypePreferencePK partySearchTypePreferencePK = partySearchTypePreferenceDetail.getPartySearchTypePreferencePK(); // Not updated
            PartyPK partyPK = partySearchTypePreferenceDetail.getPartyPK(); // Not updated
            SearchTypePK searchTypePK = partySearchTypePreferenceDetail.getSearchTypePK(); // Not updated
            SearchDefaultOperatorPK searchDefaultOperatorPK = partySearchTypePreferenceDetailValue.getSearchDefaultOperatorPK();
            SearchSortOrderPK searchSortOrderPK = partySearchTypePreferenceDetailValue.getSearchSortOrderPK();
            SearchSortDirectionPK searchSortDirectionPK = partySearchTypePreferenceDetailValue.getSearchSortDirectionPK();
            
            partySearchTypePreferenceDetail = PartySearchTypePreferenceDetailFactory.getInstance().create(partySearchTypePreferencePK, partyPK, searchTypePK,
                    searchDefaultOperatorPK, searchSortOrderPK, searchSortDirectionPK, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            partySearchTypePreference.setActiveDetail(partySearchTypePreferenceDetail);
            partySearchTypePreference.setLastDetail(partySearchTypePreferenceDetail);

            sendEventUsingNames(partyPK, EventTypes.MODIFY.name(), partySearchTypePreferencePK, EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deletePartySearchTypePreference(PartySearchTypePreference partySearchTypePreference, BasePK deletedBy) {
        PartySearchTypePreferenceDetail partySearchTypePreferenceDetail = partySearchTypePreference.getLastDetailForUpdate();

        partySearchTypePreferenceDetail.setThruTime(session.START_TIME_LONG);
        partySearchTypePreference.setActiveDetail(null);
        partySearchTypePreference.store();

        sendEventUsingNames(partySearchTypePreferenceDetail.getPartyPK(), EventTypes.MODIFY.name(), partySearchTypePreference.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }

    public void deletePartySearchTypePreferences(List<PartySearchTypePreference> partySearchTypePreferences, BasePK deletedBy) {
        partySearchTypePreferences.stream().forEach((partySearchTypePreference) -> {
            deletePartySearchTypePreference(partySearchTypePreference, deletedBy);
        });
    }

    public void deletePartySearchTypePreferencesByParty(Party party, BasePK deletedBy) {
        deletePartySearchTypePreferences(getPartySearchTypePreferencesByPartyForUpdate(party), deletedBy);
    }
    
    public void deletePartySearchTypePreferencesBySearchType(SearchType searchType, BasePK deletedBy) {
        deletePartySearchTypePreferences(getPartySearchTypePreferencesBySearchTypeForUpdate(searchType), deletedBy);
    }
    
    public void deletePartySearchTypePreferencesBySearchDefaultOperator(SearchDefaultOperator searchDefaultOperator, BasePK deletedBy) {
        deletePartySearchTypePreferences(getPartySearchTypePreferencesBySearchDefaultOperatorForUpdate(searchDefaultOperator), deletedBy);
    }
    
    public void deletePartySearchTypePreferencesBySearchSortOrder(SearchSortOrder searchSortOrder, BasePK deletedBy) {
        deletePartySearchTypePreferences(getPartySearchTypePreferencesBySearchSortOrderForUpdate(searchSortOrder), deletedBy);
    }
    
    public void deletePartySearchTypePreferencesBySearchSortDirection(SearchSortDirection searchSortDirection, BasePK deletedBy) {
        deletePartySearchTypePreferences(getPartySearchTypePreferencesBySearchSortDirectionForUpdate(searchSortDirection), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Search Result Actions
    // --------------------------------------------------------------------------------
    
    public SearchResultAction createSearchResultAction(Search search, SearchResultActionType searchResultActionType, Long actionTime,
            EntityInstance entityInstance, BasePK createdBy) {
        SearchResultAction searchResultAction = SearchResultActionFactory.getInstance().create(search, searchResultActionType, actionTime, entityInstance,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(search.getPrimaryKey(), EventTypes.MODIFY.name(), searchResultAction.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return searchResultAction;
    }
    
    public boolean searchResultActionExists(Search search, SearchResultActionType searchResultActionType, EntityInstance entityInstance) {
        return session.queryForInteger(
                "SELECT COUNT(*) "
                + "FROM searchresultactions "
                + "WHERE srchract_srch_searchid = ? AND srchract_srchracttyp_searchresultactiontypeid = ? AND srchract_eni_entityinstanceid = ? "
                + "AND srchract_thrutime = ?",
                search, searchResultActionType, entityInstance, Session.MAX_TIME) == 1;
    }
    
    private static final Map<EntityPermission, String> getSearchResultActionsBySearchQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM searchresultactions " +
                "WHERE srchract_srch_searchid = ? AND srchract_thrutime = ? " +
                "FOR UPDATE");
        getSearchResultActionsBySearchQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<SearchResultAction> getSearchResultActionsBySearch(Search search, EntityPermission entityPermission) {
        return SearchResultActionFactory.getInstance().getEntitiesFromQuery(entityPermission, getSearchResultActionsBySearchQueries,
                search, Session.MAX_TIME);
    }

    public List<SearchResultAction> getSearchResultActionsBySearchForUpdate(Search search) {
        return getSearchResultActionsBySearch(search, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getSearchResultActionsBySearchResultActionTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM searchresultactions " +
                "WHERE srchract_srchracttyp_searchresultactiontypeid = ? AND srchract_thrutime = ? " +
                "FOR UPDATE");
        getSearchResultActionsBySearchResultActionTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<SearchResultAction> getSearchResultActionsBySearchResultActionType(SearchResultActionType searchResultActionType, EntityPermission entityPermission) {
        return SearchResultActionFactory.getInstance().getEntitiesFromQuery(entityPermission, getSearchResultActionsBySearchResultActionTypeQueries,
                searchResultActionType, Session.MAX_TIME);
    }

    public List<SearchResultAction> getSearchResultActionsBySearchResultActionTypeForUpdate(SearchResultActionType searchResultActionType) {
        return getSearchResultActionsBySearchResultActionType(searchResultActionType, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getSearchResultActionsByEntityInstanceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM searchresultactions " +
                "WHERE srchract_eni_entityinstanceid = ? AND srchract_thrutime = ? " +
                "FOR UPDATE");
        getSearchResultActionsByEntityInstanceQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<SearchResultAction> getSearchResultActionsByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        return SearchResultActionFactory.getInstance().getEntitiesFromQuery(entityPermission, getSearchResultActionsByEntityInstanceQueries,
                entityInstance, Session.MAX_TIME);
    }

    public List<SearchResultAction> getSearchResultActionsByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getSearchResultActionsByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public void deleteSearchResultAction(SearchResultAction searchResultAction, BasePK deletedBy) {
        searchResultAction.setThruTime(session.START_TIME_LONG);
        searchResultAction.store();
        
        sendEventUsingNames(searchResultAction.getSearch().getPrimaryKey(), EventTypes.DELETE.name(), null, EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteSearchResultActions(List<SearchResultAction> searchResultActions, BasePK deletedBy) {
        searchResultActions.stream().forEach((searchResultAction) -> {
            deleteSearchResultAction(searchResultAction, deletedBy);
        });
    }
    
    public void deleteSearchResultActionsBySearch(Search search, BasePK deletedBy) {
        deleteSearchResultActions(getSearchResultActionsBySearchForUpdate(search), deletedBy);
    }
    
    public void deleteSearchResultActionsBySearchResultActionType(SearchResultActionType searchResultActionType, BasePK deletedBy) {
        deleteSearchResultActions(getSearchResultActionsBySearchResultActionTypeForUpdate(searchResultActionType), deletedBy);
    }
    
    public void deleteSearchResultActionsByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteSearchResultActions(getSearchResultActionsByEntityInstanceForUpdate(entityInstance), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Customer Searches
    // --------------------------------------------------------------------------------
    
    public List<CustomerResultTransfer> getCustomerResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        List<CustomerResultTransfer> customerResultTransfers = new ArrayList<>();
        boolean includeCustomer = false;
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeCustomer = options.contains(SearchOptions.CustomerResultIncludeCustomer);
        }
        
        try (ResultSet rs = getCustomerResults(userVisit, userVisitSearch)) {
            var customerControl = (CustomerControl)Session.getModelController(CustomerControl.class);

            while(rs.next()) {
                Party party = getPartyControl().getPartyByPK(new PartyPK(rs.getLong(1)));

                customerResultTransfers.add(new CustomerResultTransfer(party.getLastDetail().getPartyName(),
                        includeCustomer ? customerControl.getCustomerTransfer(userVisit, party) : null));
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return customerResultTransfers;
    }
    
    public List<CustomerResultObject> getCustomerResultObjects(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        List<CustomerResultObject> customerResultObjects = new ArrayList<>();
        
        try (ResultSet rs = getCustomerResults(userVisit, userVisitSearch)) {
            while(rs.next()) {
                Party party = getPartyControl().getPartyByPK(new PartyPK(rs.getLong(1)));

                customerResultObjects.add(new CustomerResultObject(party));
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return customerResultObjects;
    }
    
    public ResultSet getCustomerResults(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        Search search = userVisitSearch.getSearch();
        ResultSet rs = null;
        
        try {
            PreparedStatement ps = SearchResultFactory.getInstance().prepareStatement(
                    "SELECT eni_entityuniqueid " +
                    "FROM searchresults, entityinstances " +
                    "WHERE srchr_srch_searchid = ? AND srchr_eni_entityinstanceid = eni_entityinstanceid " +
                    "ORDER BY srchr_sortorder, srchr_eni_entityinstanceid " +
                    "_LIMIT_");
            
            ps.setLong(1, search.getPrimaryKey().getEntityId());
            
            rs = ps.executeQuery();
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return rs;
    }
    
    // --------------------------------------------------------------------------------
    //   Employee Searches
    // --------------------------------------------------------------------------------

    public List<EmployeeResultTransfer> getEmployeeResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        Search search = userVisitSearch.getSearch();
        List<EmployeeResultTransfer> employeeResultTransfers = new ArrayList<>();
        boolean includeEmployee = false;
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeEmployee = options.contains(SearchOptions.EmployeeResultIncludeEmployee);
        }

        try {
            var employeeControl = (EmployeeControl)Session.getModelController(EmployeeControl.class);
            PreparedStatement ps = SearchResultFactory.getInstance().prepareStatement(
                    "SELECT eni_entityuniqueid " +
                    "FROM searchresults, entityinstances " +
                    "WHERE srchr_srch_searchid = ? AND srchr_eni_entityinstanceid = eni_entityinstanceid " +
                    "ORDER BY srchr_sortorder, srchr_eni_entityinstanceid " +
                    "_LIMIT_");

            ps.setLong(1, search.getPrimaryKey().getEntityId());

            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    Party party = getPartyControl().getPartyByPK(new PartyPK(Long.valueOf(rs.getLong(1))));

                    employeeResultTransfers.add(new EmployeeResultTransfer(party.getLastDetail().getPartyName(),
                            includeEmployee ? employeeControl.getEmployeeTransfer(userVisit, party) : null));
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return employeeResultTransfers;
    }

    // --------------------------------------------------------------------------------
    //   Leave Searches
    // --------------------------------------------------------------------------------
    
    public List<LeaveResultTransfer> getLeaveResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        Search search = userVisitSearch.getSearch();
        List<LeaveResultTransfer> leaveResultTransfers = new ArrayList<>();
        boolean includeLeave = false;
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeLeave = options.contains(SearchOptions.LeaveResultIncludeLeave);
        }
        
        try {
            var employeeControl = (EmployeeControl)Session.getModelController(EmployeeControl.class);
            PreparedStatement ps = SearchResultFactory.getInstance().prepareStatement(
                    "SELECT eni_entityuniqueid " +
                    "FROM searchresults, entityinstances " +
                    "WHERE srchr_srch_searchid = ? AND srchr_eni_entityinstanceid = eni_entityinstanceid " +
                    "ORDER BY srchr_sortorder, srchr_eni_entityinstanceid " +
                    "_LIMIT_");
            
            ps.setLong(1, search.getPrimaryKey().getEntityId());
            
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    Leave leave = LeaveFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, new LeavePK(Long.valueOf(rs.getLong(1))));

                    leaveResultTransfers.add(new LeaveResultTransfer(leave.getLastDetail().getLeaveName(),
                            includeLeave ? employeeControl.getLeaveTransfer(userVisit, leave) : null));
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return leaveResultTransfers;
    }
    
    // --------------------------------------------------------------------------------
    //   Item Searches
    // --------------------------------------------------------------------------------
    
    public List<ItemResultTransfer> getItemResultTransfers(UserVisitSearch userVisitSearch) {
        Search search = userVisitSearch.getSearch();
        CachedSearch cachedSearch = search.getCachedSearch();
        List<ItemResultTransfer> itemResultTransfers;
        boolean includeItem = false;
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeItem = options.contains(SearchOptions.ItemResultIncludeItem);
        }
        
        if(cachedSearch == null) {
            itemResultTransfers = new ArrayList<>(countSearchResults(search));

            try {
                var itemControl = (ItemControl)Session.getModelController(ItemControl.class);
                PreparedStatement ps = SearchResultFactory.getInstance().prepareStatement(
                        "SELECT eni_entityuniqueid "
                        + "FROM searchresults, entityinstances "
                        + "WHERE srchr_srch_searchid = ? AND srchr_eni_entityinstanceid = eni_entityinstanceid "
                        + "ORDER BY srchr_sortorder, srchr_eni_entityinstanceid "
                        + "_LIMIT_");

                ps.setLong(1, search.getPrimaryKey().getEntityId());

                try (ResultSet rs = ps.executeQuery()) {
                    while(rs.next()) {
                        Item item = ItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, new ItemPK(Long.valueOf(rs.getLong(1))));

                        itemResultTransfers.add(new ItemResultTransfer(item.getLastDetail().getItemName(),
                                includeItem ? itemControl.getItemTransfer(userVisitSearch.getUserVisit(), item) : null));
                    }
                } catch (SQLException se) {
                    throw new PersistenceDatabaseException(se);
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        } else {
            CachedExecutedSearch cachedExecutedSearch = getCachedExecutedSearch(cachedSearch);
            
            itemResultTransfers = new ArrayList<>(countCachedExecutedSearchResults(cachedExecutedSearch));
            
            session.copyLimit(SearchResultConstants.ENTITY_TYPE_NAME, CachedExecutedSearchResultConstants.ENTITY_TYPE_NAME);
            
            try {
                var itemControl = (ItemControl)Session.getModelController(ItemControl.class);
                PreparedStatement ps = CachedExecutedSearchResultFactory.getInstance().prepareStatement(
                        "SELECT eni_entityuniqueid "
                        + "FROM cachedexecutedsearchresults, entityinstances "
                        + "WHERE cxsrchr_cxsrch_cachedexecutedsearchid = ? AND cxsrchr_eni_entityinstanceid = eni_entityinstanceid "
                        + "ORDER BY cxsrchr_sortorder, cxsrchr_eni_entityinstanceid "
                        + "_LIMIT_");

                ps.setLong(1, cachedExecutedSearch.getPrimaryKey().getEntityId());

                try (ResultSet rs = ps.executeQuery()) {
                    while(rs.next()) {
                        Item item = ItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, new ItemPK(Long.valueOf(rs.getLong(1))));

                        itemResultTransfers.add(new ItemResultTransfer(item.getLastDetail().getItemName(),
                                includeItem ? itemControl.getItemTransfer(userVisitSearch.getUserVisit(), item) : null));
                    }
                } catch (SQLException se) {
                    throw new PersistenceDatabaseException(se);
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        }
        
        return itemResultTransfers;
    }
    
    // --------------------------------------------------------------------------------
    //   Vendor Searches
    // --------------------------------------------------------------------------------

    public List<VendorResultTransfer> getVendorResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        Search search = userVisitSearch.getSearch();
        List<VendorResultTransfer> vendorResultTransfers = new ArrayList<>();
        boolean includeVendor = false;
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeVendor = options.contains(SearchOptions.VendorResultIncludeVendor);
        }

        try {
            var vendorControl = (VendorControl)Session.getModelController(VendorControl.class);
            PreparedStatement ps = SearchResultFactory.getInstance().prepareStatement(
                    "SELECT eni_entityuniqueid " +
                    "FROM searchresults, entityinstances " +
                    "WHERE srchr_srch_searchid = ? AND srchr_eni_entityinstanceid = eni_entityinstanceid " +
                    "ORDER BY srchr_sortorder, srchr_eni_entityinstanceid " +
                    "_LIMIT_");

            ps.setLong(1, search.getPrimaryKey().getEntityId());

            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    Party party = getPartyControl().getPartyByPK(new PartyPK(Long.valueOf(rs.getLong(1))));

                    vendorResultTransfers.add(new VendorResultTransfer(party.getLastDetail().getPartyName(),
                            includeVendor ? vendorControl.getVendorTransfer(userVisit, party) : null));
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return vendorResultTransfers;
    }

    // --------------------------------------------------------------------------------
    //   Forum Message Searches
    // --------------------------------------------------------------------------------

    public List<ForumMessageResultTransfer> getForumMessageResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        Search search = userVisitSearch.getSearch();
        List<ForumMessageResultTransfer> forumMessageResultTransfers = new ArrayList<>();
        boolean includeForumMessage = false;
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeForumMessage = options.contains(SearchOptions.ForumMessageResultIncludeForumMessage);
        }

        try {
            var forumControl = (ForumControl)Session.getModelController(ForumControl.class);
            PreparedStatement ps = SearchResultFactory.getInstance().prepareStatement(
                    "SELECT eni_entityuniqueid " +
                    "FROM searchresults, entityinstances " +
                    "WHERE srchr_srch_searchid = ? AND srchr_eni_entityinstanceid = eni_entityinstanceid " +
                    "ORDER BY srchr_sortorder, srchr_eni_entityinstanceid " +
                    "_LIMIT_");

            ps.setLong(1, search.getPrimaryKey().getEntityId());

            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    ForumMessage forumMessage = ForumMessageFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, new ForumMessagePK(Long.valueOf(rs.getLong(1))));

                    forumMessageResultTransfers.add(new ForumMessageResultTransfer(forumMessage.getLastDetail().getForumMessageName(),
                            includeForumMessage ? forumControl.getForumMessageTransfer(userVisit, forumMessage) : null));
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return forumMessageResultTransfers;
    }

    // --------------------------------------------------------------------------------
    //   Sales Order Batch Searches
    // --------------------------------------------------------------------------------
    
    public List<SalesOrderBatchResultTransfer> getSalesOrderBatchResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        Search search = userVisitSearch.getSearch();
        List<SalesOrderBatchResultTransfer> salesOrderBatchResultTransfers = new ArrayList<>();
        boolean includeSalesOrderBatch = false;
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeSalesOrderBatch = options.contains(SearchOptions.SalesOrderBatchResultIncludeSalesOrderBatch);
        }
        
        try {
            var batchControl = (BatchControl)Session.getModelController(BatchControl.class);
            var salesControl = (SalesControl)Session.getModelController(SalesControl.class);
            PreparedStatement ps = SearchResultFactory.getInstance().prepareStatement(
                    "SELECT eni_entityuniqueid " +
                    "FROM searchresults, entityinstances " +
                    "WHERE srchr_srch_searchid = ? AND srchr_eni_entityinstanceid = eni_entityinstanceid " +
                    "ORDER BY srchr_sortorder, srchr_eni_entityinstanceid " +
                    "_LIMIT_");
            
            ps.setLong(1, search.getPrimaryKey().getEntityId());
            
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    Batch batch = batchControl.getBatchByPK(new BatchPK(Long.valueOf(rs.getLong(1))));

                    salesOrderBatchResultTransfers.add(new SalesOrderBatchResultTransfer(batch.getLastDetail().getBatchName(),
                            includeSalesOrderBatch ? salesControl.getSalesOrderBatchTransfer(userVisit, batch) : null));
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return salesOrderBatchResultTransfers;
    }
    
    // --------------------------------------------------------------------------------
    //   Sales Order Searches
    // --------------------------------------------------------------------------------
    
    public List<SalesOrderResultTransfer> getSalesOrderResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        Search search = userVisitSearch.getSearch();
        List<SalesOrderResultTransfer> salesOrderResultTransfers = new ArrayList<>();
        
        try {
            var coreControl = getCoreControl();
            var workflowControl = getWorkflowControl();
            PreparedStatement ps = SearchResultFactory.getInstance().prepareStatement(
                    "SELECT eni_entityuniqueid " +
                    "FROM searchresults, entityinstances " +
                    "WHERE srchr_srch_searchid = ? AND srchr_eni_entityinstanceid = eni_entityinstanceid " +
                    "ORDER BY srchr_sortorder, srchr_eni_entityinstanceid " +
                    "_LIMIT_");
            
            ps.setLong(1, search.getPrimaryKey().getEntityId());
            
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    OrderPK orderPK = new OrderPK(Long.valueOf(rs.getLong(1)));
                    Order order = OrderFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, orderPK);

                    EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(orderPK);
                    WorkflowEntityStatusTransfer orderStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                            SalesOrderStatusConstants.Workflow_SALES_ORDER_STATUS, entityInstance);

                    salesOrderResultTransfers.add(new SalesOrderResultTransfer(order.getLastDetail().getOrderName(), orderStatusTransfer));
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return salesOrderResultTransfers;
    }
    
    // --------------------------------------------------------------------------------
    //   Entity List Item Searches
    // --------------------------------------------------------------------------------
    
    public List<EntityListItemResultTransfer> getEntityListItemResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        Search search = userVisitSearch.getSearch();
        CachedSearch cachedSearch = search.getCachedSearch();
        List<EntityListItemResultTransfer> entityListItemResultTransfers;
        boolean includeEntityListItem = false;
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeEntityListItem = options.contains(SearchOptions.EntityListItemResultIncludeEntityListItem);
        }
        
        if(cachedSearch == null) {
            entityListItemResultTransfers = new ArrayList<>(countSearchResults(search));

            try {
                var coreControl = (CoreControl)Session.getModelController(CoreControl.class);
                PreparedStatement ps = SearchResultFactory.getInstance().prepareStatement(
                        "SELECT eni_entityuniqueid "
                        + "FROM searchresults, entityinstances "
                        + "WHERE srchr_srch_searchid = ? AND srchr_eni_entityinstanceid = eni_entityinstanceid "
                        + "ORDER BY srchr_sortorder, srchr_eni_entityinstanceid "
                        + "_LIMIT_");

                ps.setLong(1, search.getPrimaryKey().getEntityId());

                try (ResultSet rs = ps.executeQuery()) {
                    while(rs.next()) {
                        EntityListItem entityListItem = EntityListItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, new EntityListItemPK(Long.valueOf(rs.getLong(1))));
                        EntityListItemDetail entityListItemDetail = entityListItem.getLastDetail();
                        EntityAttributeDetail entityAttributeDetail = entityListItemDetail.getEntityAttribute().getLastDetail();
                        EntityTypeDetail entityTypeDetail = entityAttributeDetail.getEntityType().getLastDetail();

                        entityListItemResultTransfers.add(new EntityListItemResultTransfer(entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                                entityTypeDetail.getEntityTypeName(), entityAttributeDetail.getEntityAttributeName(), entityListItemDetail.getEntityListItemName(),
                                includeEntityListItem ? coreControl.getEntityListItemTransfer(userVisit, entityListItem, null) : null));
                    }
                } catch (SQLException se) {
                    throw new PersistenceDatabaseException(se);
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        } else {
            CachedExecutedSearch cachedExecutedSearch = getCachedExecutedSearch(cachedSearch);
            
            entityListItemResultTransfers = new ArrayList<>(countCachedExecutedSearchResults(cachedExecutedSearch));
            
            session.copyLimit(SearchResultConstants.ENTITY_TYPE_NAME, CachedExecutedSearchResultConstants.ENTITY_TYPE_NAME);
            
            try {
                var coreControl = (CoreControl)Session.getModelController(CoreControl.class);
                PreparedStatement ps = CachedExecutedSearchResultFactory.getInstance().prepareStatement(
                        "SELECT eni_entityuniqueid "
                        + "FROM cachedexecutedsearchresults, entityinstances "
                        + "WHERE cxsrchr_cxsrch_cachedexecutedsearchid = ? AND cxsrchr_eni_entityinstanceid = eni_entityinstanceid "
                        + "ORDER BY cxsrchr_sortorder, cxsrchr_eni_entityinstanceid "
                        + "_LIMIT_");

                ps.setLong(1, cachedExecutedSearch.getPrimaryKey().getEntityId());

                try (ResultSet rs = ps.executeQuery()) {
                    while(rs.next()) {
                        EntityListItem entityListItem = EntityListItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, new EntityListItemPK(Long.valueOf(rs.getLong(1))));
                        EntityListItemDetail entityListItemDetail = entityListItem.getLastDetail();
                        EntityAttributeDetail entityAttributeDetail = entityListItemDetail.getEntityAttribute().getLastDetail();
                        EntityTypeDetail entityTypeDetail = entityAttributeDetail.getEntityType().getLastDetail();

                        entityListItemResultTransfers.add(new EntityListItemResultTransfer(entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                                entityTypeDetail.getEntityTypeName(), entityAttributeDetail.getEntityAttributeName(), entityListItemDetail.getEntityListItemName(),
                                includeEntityListItem ? coreControl.getEntityListItemTransfer(userVisit, entityListItem, null) : null));
                    }
                } catch (SQLException se) {
                    throw new PersistenceDatabaseException(se);
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        }
        
        return entityListItemResultTransfers;
    }
    
    // --------------------------------------------------------------------------------
    //   Content Category Searches
    // --------------------------------------------------------------------------------
    
    public List<ContentCategoryResultTransfer> getContentCategoryResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        Search search = userVisitSearch.getSearch();
        List<ContentCategoryResultTransfer> contentCategoryResultTransfers = new ArrayList<>();
        boolean includeContentCategory = false;
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeContentCategory = options.contains(SearchOptions.ContentCategoryResultIncludeContentCategory);
        }
        
        try {
            var contentControl = (ContentControl)Session.getModelController(ContentControl.class);
            PreparedStatement ps = SearchResultFactory.getInstance().prepareStatement(
                    "SELECT eni_entityuniqueid " +
                    "FROM searchresults, entityinstances " +
                    "WHERE srchr_srch_searchid = ? AND srchr_eni_entityinstanceid = eni_entityinstanceid " +
                    "ORDER BY srchr_sortorder, srchr_eni_entityinstanceid " +
                    "_LIMIT_");
            
            ps.setLong(1, search.getPrimaryKey().getEntityId());
            
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    ContentCategory contentCategory = ContentCategoryFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, new ContentCategoryPK(Long.valueOf(rs.getLong(1))));
                    ContentCategoryDetail contentCategoryDetail = contentCategory.getLastDetail();
                    ContentCatalogDetail contentCatalogDetail = contentCategoryDetail.getContentCatalog().getLastDetail();

                    contentCategoryResultTransfers.add(new ContentCategoryResultTransfer(contentCatalogDetail.getContentCollection().getLastDetail().getContentCollectionName(),
                            contentCatalogDetail.getContentCatalogName(), contentCategoryDetail.getContentCategoryName(),
                            includeContentCategory ? contentControl.getContentCategoryTransfer(userVisit, contentCategory) : null));
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contentCategoryResultTransfers;
    }
    
    // --------------------------------------------------------------------------------
    //   Security Role Group Searches
    // --------------------------------------------------------------------------------
    
    public List<SecurityRoleGroupResultTransfer> getSecurityRoleGroupResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        Search search = userVisitSearch.getSearch();
        List<SecurityRoleGroupResultTransfer> SecurityRoleGroupResultTransfers = new ArrayList<>();
        boolean includeSecurityRoleGroup = false;
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeSecurityRoleGroup = options.contains(SearchOptions.SecurityRoleGroupResultIncludeSecurityRoleGroup);
        }
        
        try {
            var securityControl = (SecurityControl)Session.getModelController(SecurityControl.class);
            PreparedStatement ps = SearchResultFactory.getInstance().prepareStatement(
                    "SELECT eni_entityuniqueid " +
                    "FROM searchresults, entityinstances " +
                    "WHERE srchr_srch_searchid = ? AND srchr_eni_entityinstanceid = eni_entityinstanceid " +
                    "ORDER BY srchr_sortorder, srchr_eni_entityinstanceid " +
                    "_LIMIT_");
            
            ps.setLong(1, search.getPrimaryKey().getEntityId());
            
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    SecurityRoleGroup securityRoleGroup = SecurityRoleGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, new SecurityRoleGroupPK(Long.valueOf(rs.getLong(1))));

                    SecurityRoleGroupResultTransfers.add(new SecurityRoleGroupResultTransfer(securityRoleGroup.getLastDetail().getSecurityRoleGroupName(),
                            includeSecurityRoleGroup ? securityControl.getSecurityRoleGroupTransfer(userVisit, securityRoleGroup) : null));
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return SecurityRoleGroupResultTransfers;
    }
    
    // --------------------------------------------------------------------------------
    //   Security Role Searches
    // --------------------------------------------------------------------------------
    
    public List<SecurityRoleResultTransfer> getSecurityRoleResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        Search search = userVisitSearch.getSearch();
        List<SecurityRoleResultTransfer> securityRoleResultTransfers = new ArrayList<>();
        boolean includeSecurityRole = false;
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeSecurityRole = options.contains(SearchOptions.SecurityRoleResultIncludeSecurityRole);
        }
        
        try {
            var securityControl = (SecurityControl)Session.getModelController(SecurityControl.class);
            PreparedStatement ps = SearchResultFactory.getInstance().prepareStatement(
                    "SELECT eni_entityuniqueid " +
                    "FROM searchresults, entityinstances " +
                    "WHERE srchr_srch_searchid = ? AND srchr_eni_entityinstanceid = eni_entityinstanceid " +
                    "ORDER BY srchr_sortorder, srchr_eni_entityinstanceid " +
                    "_LIMIT_");
            
            ps.setLong(1, search.getPrimaryKey().getEntityId());
            
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    SecurityRole securityRole = SecurityRoleFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, new SecurityRolePK(Long.valueOf(rs.getLong(1))));
                    SecurityRoleDetail securityRoleDetail = securityRole.getLastDetail();

                    securityRoleResultTransfers.add(new SecurityRoleResultTransfer(securityRoleDetail.getSecurityRoleName(),
                            securityRoleDetail.getSecurityRoleGroup().getLastDetail().getSecurityRoleGroupName(),
                            includeSecurityRole ? securityControl.getSecurityRoleTransfer(userVisit, securityRole) : null));
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return securityRoleResultTransfers;
    }
    
    // --------------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Searches
    // --------------------------------------------------------------------------------
    
    public List<HarmonizedTariffScheduleCodeResultTransfer> getHarmonizedTariffScheduleCodeResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        Search search = userVisitSearch.getSearch();
        List<HarmonizedTariffScheduleCodeResultTransfer> harmonizedTariffScheduleCodeResultTransfers = new ArrayList<>();
        boolean includeHarmonizedTariffScheduleCode = false;
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeHarmonizedTariffScheduleCode = options.contains(SearchOptions.HarmonizedTariffScheduleCodeResultIncludeHarmonizedTariffScheduleCode);
        }
        
        try {
            var itemControl = (ItemControl)Session.getModelController(ItemControl.class);
            PreparedStatement ps = SearchResultFactory.getInstance().prepareStatement(
                    "SELECT eni_entityuniqueid " +
                    "FROM searchresults, entityinstances " +
                    "WHERE srchr_srch_searchid = ? AND srchr_eni_entityinstanceid = eni_entityinstanceid " +
                    "ORDER BY srchr_sortorder, srchr_eni_entityinstanceid " +
                    "_LIMIT_");
            
            ps.setLong(1, search.getPrimaryKey().getEntityId());
            
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    HarmonizedTariffScheduleCode harmonizedTariffScheduleCode = HarmonizedTariffScheduleCodeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, new HarmonizedTariffScheduleCodePK(Long.valueOf(rs.getLong(1))));
                    HarmonizedTariffScheduleCodeDetail harmonizedTariffScheduleCodeDetail = harmonizedTariffScheduleCode.getLastDetail();

                    harmonizedTariffScheduleCodeResultTransfers.add(new HarmonizedTariffScheduleCodeResultTransfer(
                            harmonizedTariffScheduleCodeDetail.getCountryGeoCode().getLastDetail().getGeoCodeName(),
                            harmonizedTariffScheduleCodeDetail.getHarmonizedTariffScheduleCodeName(),
                            includeHarmonizedTariffScheduleCode ? itemControl.getHarmonizedTariffScheduleCodeTransfer(userVisit, harmonizedTariffScheduleCode) : null));
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return harmonizedTariffScheduleCodeResultTransfers;
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Type Searches
    // --------------------------------------------------------------------------------
    
    public List<EntityTypeResultTransfer> getEntityTypeResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        Search search = userVisitSearch.getSearch();
        List<EntityTypeResultTransfer> entityTypeResultTransfers = new ArrayList<>();
        boolean includeEntityType = false;
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeEntityType = options.contains(SearchOptions.EntityTypeResultIncludeEntityType);
        }
        
        try {
            var coreControl = (CoreControl)Session.getModelController(CoreControl.class);
            PreparedStatement ps = SearchResultFactory.getInstance().prepareStatement(
                    "SELECT eni_entityuniqueid " +
                    "FROM searchresults, entityinstances " +
                    "WHERE srchr_srch_searchid = ? AND srchr_eni_entityinstanceid = eni_entityinstanceid " +
                    "ORDER BY srchr_sortorder, srchr_eni_entityinstanceid " +
                    "_LIMIT_");
            
            ps.setLong(1, search.getPrimaryKey().getEntityId());
            
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    EntityType entityType = EntityTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, new EntityTypePK(Long.valueOf(rs.getLong(1))));
                    EntityTypeDetail entityTypeDetail = entityType.getLastDetail();

                    entityTypeResultTransfers.add(new EntityTypeResultTransfer(entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                            entityTypeDetail.getEntityTypeName(), includeEntityType ? coreControl.getEntityTypeTransfer(userVisit, entityType) : null));
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityTypeResultTransfers;
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Searches
    // --------------------------------------------------------------------------------
    
    public List<ContactMechanismResultTransfer> getContactMechanismResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        Search search = userVisitSearch.getSearch();
        List<ContactMechanismResultTransfer> contactMechanismResultTransfers = new ArrayList<>();
        boolean includeContactMechanism = false;
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeContactMechanism = options.contains(SearchOptions.ContactMechanismResultIncludeContactMechanism);
        }
        
        try {
            var contactControl = (ContactControl)Session.getModelController(ContactControl.class);
            PreparedStatement ps = SearchResultFactory.getInstance().prepareStatement(
                    "SELECT eni_entityuniqueid " +
                    "FROM searchresults, entityinstances " +
                    "WHERE srchr_srch_searchid = ? AND srchr_eni_entityinstanceid = eni_entityinstanceid " +
                    "ORDER BY srchr_sortorder, srchr_eni_entityinstanceid " +
                    "_LIMIT_");
            
            ps.setLong(1, search.getPrimaryKey().getEntityId());
            
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    ContactMechanism contactMechanism = ContactMechanismFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, new ContactMechanismPK(Long.valueOf(rs.getLong(1))));
                    ContactMechanismDetail contactMechanismDetail = contactMechanism.getLastDetail();

                    contactMechanismResultTransfers.add(new ContactMechanismResultTransfer(contactMechanismDetail.getContactMechanismName(),
                            includeContactMechanism ? contactControl.getContactMechanismTransfer(userVisit, contactMechanism) : null));
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return contactMechanismResultTransfers;
    }

    // --------------------------------------------------------------------------------
    //   Offer Searches
    // --------------------------------------------------------------------------------
    
    public List<OfferResultTransfer> getOfferResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        Search search = userVisitSearch.getSearch();
        List<OfferResultTransfer> offerResultTransfers = new ArrayList<>();
        boolean includeOffer = false;
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeOffer = options.contains(SearchOptions.OfferResultIncludeOffer);
        }
        
        try {
            var offerControl = (OfferControl)Session.getModelController(OfferControl.class);
            PreparedStatement ps = SearchResultFactory.getInstance().prepareStatement(
                    "SELECT eni_entityuniqueid " +
                    "FROM searchresults, entityinstances " +
                    "WHERE srchr_srch_searchid = ? AND srchr_eni_entityinstanceid = eni_entityinstanceid " +
                    "ORDER BY srchr_sortorder, srchr_eni_entityinstanceid " +
                    "_LIMIT_");
            
            ps.setLong(1, search.getPrimaryKey().getEntityId());
            
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    Offer offer = OfferFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, new OfferPK(Long.valueOf(rs.getLong(1))));
                    OfferDetail offerDetail = offer.getLastDetail();

                    offerResultTransfers.add(new OfferResultTransfer(offerDetail.getOfferName(),
                            includeOffer ? offerControl.getOfferTransfer(userVisit, offer) : null));
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return offerResultTransfers;
    }
    
    // --------------------------------------------------------------------------------
    //   Use Searches
    // --------------------------------------------------------------------------------
    
    public List<UseResultTransfer> getUseResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        Search search = userVisitSearch.getSearch();
        List<UseResultTransfer> useResultTransfers = new ArrayList<>();
        boolean includeUse = false;
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeUse = options.contains(SearchOptions.UseResultIncludeUse);
        }
        
        try {
            var offerControl = (OfferControl)Session.getModelController(OfferControl.class);
            PreparedStatement ps = SearchResultFactory.getInstance().prepareStatement(
                    "SELECT eni_entityuniqueid " +
                    "FROM searchresults, entityinstances " +
                    "WHERE srchr_srch_searchid = ? AND srchr_eni_entityinstanceid = eni_entityinstanceid " +
                    "ORDER BY srchr_sortorder, srchr_eni_entityinstanceid " +
                    "_LIMIT_");
            
            ps.setLong(1, search.getPrimaryKey().getEntityId());
            
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    Use use = UseFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, new UsePK(Long.valueOf(rs.getLong(1))));
                    UseDetail useDetail = use.getLastDetail();

                    useResultTransfers.add(new UseResultTransfer(useDetail.getUseName(),
                            includeUse ? offerControl.getUseTransfer(userVisit, use) : null));
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return useResultTransfers;
    }
    
    // --------------------------------------------------------------------------------
    //   Use Type Searches
    // --------------------------------------------------------------------------------
    
    public List<UseTypeResultTransfer> getUseTypeResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        Search search = userVisitSearch.getSearch();
        List<UseTypeResultTransfer> useTypeResultTransfers = new ArrayList<>();
        boolean includeUseType = false;
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeUseType = options.contains(SearchOptions.UseTypeResultIncludeUseType);
        }
        
        try {
            var offerControl = (OfferControl)Session.getModelController(OfferControl.class);
            PreparedStatement ps = SearchResultFactory.getInstance().prepareStatement(
                    "SELECT eni_entityuniqueid " +
                    "FROM searchresults, entityinstances " +
                    "WHERE srchr_srch_searchid = ? AND srchr_eni_entityinstanceid = eni_entityinstanceid " +
                    "ORDER BY srchr_sortorder, srchr_eni_entityinstanceid " +
                    "_LIMIT_");
            
            ps.setLong(1, search.getPrimaryKey().getEntityId());
            
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    UseType useType = UseTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, new UseTypePK(Long.valueOf(rs.getLong(1))));
                    UseTypeDetail useTypeDetail = useType.getLastDetail();

                    useTypeResultTransfers.add(new UseTypeResultTransfer(useTypeDetail.getUseTypeName(),
                            includeUseType ? offerControl.getUseTypeTransfer(userVisit, useType) : null));
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return useTypeResultTransfers;
    }
    
}
