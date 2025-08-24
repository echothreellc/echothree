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

package com.echothree.model.control.item.server.search;

import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.index.common.IndexConstants;
import com.echothree.model.control.index.common.IndexFieldVariations;
import com.echothree.model.control.index.common.IndexFields;
import com.echothree.model.control.index.common.IndexTypes;
import com.echothree.model.control.index.server.analyzer.BasicAnalyzer;
import com.echothree.model.control.item.common.ItemDescriptionTypes;
import com.echothree.model.control.item.server.analyzer.ItemAnalyzer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemDescriptionLogic;
import com.echothree.model.control.search.common.SearchSortDirections;
import com.echothree.model.control.search.common.SearchSortOrders;
import com.echothree.model.control.search.server.search.BaseSearchEvaluator;
import com.echothree.model.control.search.server.search.EntityInstancePKHolder;
import com.echothree.model.data.core.server.factory.EntityInstanceFactory;
import com.echothree.model.data.item.server.entity.ItemType;
import com.echothree.model.data.item.server.entity.ItemUseType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.search.server.entity.SearchDefaultOperator;
import com.echothree.model.data.search.server.entity.SearchSortDirection;
import com.echothree.model.data.search.server.entity.SearchSortOrder;
import com.echothree.model.data.search.server.entity.SearchType;
import com.echothree.model.data.search.server.entity.SearchUseType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import org.apache.lucene.search.SortField;

public class ItemSearchEvaluator
        extends BaseSearchEvaluator {
    
    private String itemNameOrAlias;
    private ItemType itemType;
    private ItemUseType itemUseType;
    private WorkflowStep itemStatusWorkflowStep;
    List<WorkflowStep> itemStatusWorkflowSteps;
    
    /** Creates a new instance of ItemSearchEvaluator */
    public ItemSearchEvaluator(UserVisit userVisit, Language language, SearchType searchType, SearchDefaultOperator searchDefaultOperator, SearchSortOrder searchSortOrder,
            SearchSortDirection searchSortDirection, SearchUseType searchUseType) {
        super(userVisit, searchDefaultOperator, searchType, searchSortOrder, searchSortDirection, searchUseType, ComponentVendors.ECHO_THREE.name(),
                EntityTypes.Item.name(), IndexTypes.ITEM.name(), language, null);
        
        setField(ItemDescriptionLogic.getInstance().getIndexDefaultItemDescriptionTypeName());
    }

    /** Counts the number of search parameters. */
    @Override
    protected int countParameters() {
        return super.countParameters() + (itemNameOrAlias == null ? 0 : 1) + (itemType == null ? 0 : 1)
                + (itemUseType == null ? 0 : 1) + (itemStatusWorkflowStep == null ? 0 : 1) + (itemStatusWorkflowSteps == null ? 0 : 1);
    }

    /** Determines if the result of the search may be cached. For Items, the only field that may be used is the description, "q." If any
     * others are utilized, the result may not be cached.
     */
    @Override
    protected boolean isResultCachable() {
        return countParameters() == 1 && q != null;
    }
    
    public EntityInstancePKHolder getEntityInstancePKHolderByItemType(ItemType itemType) {
            return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                    "SELECT _PK_ " +
                    "FROM entityinstances, items, itemdetails " +
                    "WHERE itm_activedetailid = itmdt_itemdetailid AND itmdt_ityp_itemtypeid = ? " +
                    "AND eni_ent_entitytypeid = ? AND itm_itemid = eni_entityuniqueid"),
                    itemType, entityType);
    }
    
    public EntityInstancePKHolder getEntityInstancePKHolderByItemUseType(ItemUseType itemUseType) {
            return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                    "SELECT _PK_ " +
                    "FROM entityinstances, items, itemdetails " +
                    "WHERE itm_activedetailid = itmdt_itemdetailid AND itmdt_iutyp_itemusetypeid = ? " +
                    "AND eni_ent_entitytypeid = ? AND itm_itemid = eni_entityuniqueid"),
                    itemUseType, entityType);
    }
    
    public EntityInstancePKHolder getEntityInstancePKHolderByItemStatusWorkflowStep(WorkflowStep itemStatusWorkflowStep) {
            return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                    "SELECT _PK_ " +
                    "FROM entityinstances, items, itemdetails, workflowentitystatuses " +
                    "WHERE itm_activedetailid = itmdt_itemdetailid " +
                    "AND eni_ent_entitytypeid = ? AND itm_itemid = eni_entityuniqueid " +
                    "AND eni_entityinstanceid = wkfles_eni_entityinstanceid AND wkfles_wkfls_workflowstepid = ? AND wkfles_thrutime = ?"),
                    entityType, itemStatusWorkflowStep, Session.MAX_TIME);
    }
 
    /**
     * Returns the itemNameOrAlias.
     * @return the itemNameOrAlias
     */
    public String getItemNameOrAlias() {
        return itemNameOrAlias;
    }

    /**
     * Sets the itemNameOrAlias.
     * @param itemNameOrAlias the itemNameOrAlias to set
     */
    public void setItemNameOrAlias(String itemNameOrAlias) {
        this.itemNameOrAlias = itemNameOrAlias;
    }

    /**
     * Returns the itemType.
     * @return the itemType
     */
    public ItemType getItemType() {
        return itemType;
    }

    /**
     * Sets the itemType.
     * @param itemType the itemType to set
     */
    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    /**
     * Returns the itemUseType.
     * @return the itemUseType
     */
    public ItemUseType getItemUseType() {
        return itemUseType;
    }

    /**
     * Sets the itemUseType.
     * @param itemUseType the itemUseType to set
     */
    public void setItemUseType(ItemUseType itemUseType) {
        this.itemUseType = itemUseType;
    }

    /**
     * Returns the itemStatusWorkflowStep.
     * @return the itemStatusWorkflowStep
     */
    public WorkflowStep getItemStatusWorkflowStep() {
        return itemStatusWorkflowStep;
    }

    /**
     * Sets the itemStatusWorkflowStep.
     * @param itemStatusWorkflowStep the itemStatusWorkflowStep to set
     */
    public void setItemStatusWorkflowStep(WorkflowStep itemStatusWorkflowStep) {
        this.itemStatusWorkflowStep = itemStatusWorkflowStep;
    }

    /**
     * Sets the itemStatusWorkflowSteps.
     * @param itemStatusWorkflowSteps the itemStatusWorkflowSteps to set
     */
    public void setItemStatusWorkflowSteps(List<WorkflowStep> itemStatusWorkflowSteps) {
        this.itemStatusWorkflowSteps = itemStatusWorkflowSteps;
    }

    @Override
    public SortField[] getSortFields(String searchSortOrderName) {
        SortField[] sortFields = null;
        var reverse = searchSortDirection.getLastDetail().getSearchSortDirectionName().equals(SearchSortDirections.DESCENDING.name());
        
        if(searchSortOrderName.equals(SearchSortOrders.SCORE.name())) {
            sortFields = new SortField[]{
                new SortField(null, SortField.Type.SCORE, reverse),
                new SortField(ItemDescriptionTypes.DEFAULT_DESCRIPTION.name() + IndexConstants.INDEX_FIELD_VARIATION_SEPARATOR + IndexFieldVariations.sortable.name(), SortField.Type.STRING, reverse)
            };
        } else if(searchSortOrderName.equals(SearchSortOrders.DEFAULT_DESCRIPTION.name())) {
            sortFields = new SortField[]{new SortField(ItemDescriptionTypes.DEFAULT_DESCRIPTION.name() + IndexConstants.INDEX_FIELD_VARIATION_SEPARATOR + IndexFieldVariations.sortable.name(), SortField.Type.STRING, reverse)};
        } else if(searchSortOrderName.equals(SearchSortOrders.ITEM_NAME.name())) {
            sortFields = new SortField[]{new SortField(IndexFields.itemName.name() + IndexConstants.INDEX_FIELD_VARIATION_SEPARATOR + IndexFieldVariations.sortable.name(), SortField.Type.STRING, reverse)};
        } else if(searchSortOrderName.equals(SearchSortOrders.CREATED_TIME.name())) {
            sortFields = new SortField[]{new SortField(IndexFields.createdTime.name(), SortField.Type.LONG, reverse)};
        } else if(searchSortOrderName.equals(SearchSortOrders.MODIFIED_TIME.name())) {
            sortFields = new SortField[]{new SortField(IndexFields.modifiedTime.name(), SortField.Type.LONG, reverse)};
        }
        
        return sortFields;
    }
    
    @Override
    public BasicAnalyzer getAnalyzer(final ExecutionErrorAccumulator eea, final Language language) {
        return new ItemAnalyzer(eea, language, entityType);
    }
    
    @Override
    protected EntityInstancePKHolder executeSearch(final ExecutionErrorAccumulator eea) {
        EntityInstancePKHolder resultSet = null;
        
        if(itemNameOrAlias == null) {
            resultSet = super.executeSearch(eea);
            
            if(resultSet == null || resultSet.size() > 0) {
                if(q != null) {
                    if(resultSet == null || resultSet.size() > 0) {
                        var entityInstancePKHolder = executeQuery(eea);
                        
                        if(resultSet == null) {
                            resultSet = entityInstancePKHolder;
                        } else {
                            resultSet.retainAll(entityInstancePKHolder);
                        }
                    }
                }
            }
            
            if(resultSet == null || resultSet.size() > 0) {
                if(itemType != null) {
                    var entityInstancePKHolder = getEntityInstancePKHolderByItemType(itemType);
                    
                    if(resultSet == null) {
                        resultSet = entityInstancePKHolder;
                    } else {
                        resultSet.retainAll(entityInstancePKHolder);
                    }
                }
            }
            
            if(resultSet == null || resultSet.size() > 0) {
                if(itemUseType != null) {
                    var entityInstancePKHolder = getEntityInstancePKHolderByItemUseType(itemUseType);
                    
                    if(resultSet == null) {
                        resultSet = entityInstancePKHolder;
                    } else {
                        resultSet.retainAll(entityInstancePKHolder);
                    }
                }
            }

            if(resultSet == null || resultSet.size() > 0) {
                if(itemStatusWorkflowStep != null) {
                    var entityInstancePKHolder = getEntityInstancePKHolderByItemStatusWorkflowStep(itemStatusWorkflowStep);

                    if(resultSet == null) {
                        resultSet = entityInstancePKHolder;
                    } else {
                        resultSet.retainAll(entityInstancePKHolder);
                    }
                }
            }

            if(resultSet == null || resultSet.size() > 0) {
                if(itemStatusWorkflowSteps != null) {
                    EntityInstancePKHolder entityInstancePKHolder = null;
                    
                    for(var workflowStep : itemStatusWorkflowSteps) {
                        var individualPKHolder = getEntityInstancePKHolderByItemStatusWorkflowStep(workflowStep);
                        
                        if(entityInstancePKHolder == null) {
                            entityInstancePKHolder = individualPKHolder;
                        } else {
                            entityInstancePKHolder.addAll(individualPKHolder);
                        }
                    }
                    
                    if(resultSet == null) {
                        resultSet = entityInstancePKHolder;
                    } else {
                        resultSet.retainAll(entityInstancePKHolder);
                    }
                }
            }
        } else {
            var itemControl = Session.getModelController(ItemControl.class);
            var item = itemControl.getItemByNameThenAlias(itemNameOrAlias);
            
            if(item != null) {
                var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);

                resultSet = new EntityInstancePKHolder(1);
                resultSet.add(entityInstanceControl.getEntityInstanceByBasePK(item.getPrimaryKey()).getPrimaryKey(), null);
            }
        }
        
        return resultSet;
    }

}
