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

package com.echothree.model.control.batch.server.search;

import com.echothree.model.control.batch.server.control.BatchControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.search.server.search.BaseSearchEvaluator;
import com.echothree.model.control.search.server.search.EntityInstancePKHolder;
import com.echothree.model.data.batch.server.entity.Batch;
import com.echothree.model.data.batch.server.entity.BatchAliasType;
import com.echothree.model.data.batch.server.entity.BatchType;
import com.echothree.model.data.core.server.factory.EntityInstanceFactory;
import com.echothree.model.data.search.server.entity.SearchDefaultOperator;
import com.echothree.model.data.search.server.entity.SearchSortDirection;
import com.echothree.model.data.search.server.entity.SearchSortOrder;
import com.echothree.model.data.search.server.entity.SearchType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class BatchSearchEvaluator
        extends BaseSearchEvaluator {
    
    protected BatchType batchType;
    private WorkflowStep batchStatusWorkflowStep;
    private String batchName;
    private BatchAliasType batchAliasType;
    private String alias;
    
    protected BatchSearchEvaluator(UserVisit userVisit, SearchType searchType, SearchDefaultOperator searchDefaultOperator, SearchSortOrder searchSortOrder,
            SearchSortDirection searchSortDirection, BatchType batchType) {
        super(userVisit, searchDefaultOperator, searchType, searchSortOrder, searchSortDirection, null, ComponentVendors.ECHO_THREE.name(),
                EntityTypes.Batch.name(), null, null, null);
        
        this.batchType = batchType;
    }
    
    public WorkflowStep getBatchStatusWorkflowStep() {
        return batchStatusWorkflowStep;
    }

    public void setBatchStatusWorkflowStep(WorkflowStep batchStatusWorkflowStep) {
        this.batchStatusWorkflowStep = batchStatusWorkflowStep;
    }
    
    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public BatchAliasType getBatchAliasType() {
        return batchAliasType;
    }

    public void setBatchAliasType(BatchAliasType batchAliasType) {
        this.batchAliasType = batchAliasType;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
    
    /** Counts the number of search parameters, not including batchType. */
    @Override
    protected int countParameters() {
        return super.countParameters() + (batchStatusWorkflowStep == null ? 0 : 1) + (batchName == null ? 0 : 1) + (batchAliasType == null ? 0 : 1)
                + (alias == null ? 0 : 1);
    }

    public EntityInstancePKHolder getEntityInstancePKHolderByBatchType(BatchType batchType) {
        return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                "SELECT _PK_ "
                + "FROM entityinstances, batches, batchdetails "
                + "WHERE btch_activedetailid = btchdt_batchdetailid "
                + "AND btchdt_btchtyp_batchtypeid = ? "
                + "AND eni_ent_entitytypeid = ? AND btch_batchid = eni_entityuniqueid"),
                batchType, entityType);
    }
    
    public EntityInstancePKHolder getEntityInstancePKHolderByBatchStatusWorkflowStep(WorkflowStep batchStatusWorkflowStep) {
            return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                    "SELECT _PK_ "
                    + "FROM entityinstances, batches, batchdetails, workflowentitystatuses "
                    + "WHERE btch_activedetailid = btchdt_batchdetailid "
                    + "AND btchdt_btchtyp_batchtypeid = ? "
                    + "AND eni_ent_entitytypeid = ? AND btch_batchid = eni_entityuniqueid "
                    + "AND eni_entityinstanceid = wkfles_eni_entityinstanceid AND wkfles_wkfls_workflowstepid = ? AND wkfles_thrutime = ?"),
                    batchType, entityType, batchStatusWorkflowStep, Session.MAX_TIME);
    }
 
    /** Subclasses should override and always call their super's executeSearch() */
    @Override
    protected EntityInstancePKHolder executeSearch(final ExecutionErrorAccumulator eea) {
        EntityInstancePKHolder resultSet = null;
        var parameterCount = (batchName == null ? 0 : 1) + (alias == null ? 0 : 1);

        if(parameterCount == 0) {
            resultSet = super.executeSearch(eea);
            
            if(batchStatusWorkflowStep != null && (resultSet == null || resultSet.size() > 0)) {
                var entityInstancePKHolder = getEntityInstancePKHolderByBatchStatusWorkflowStep(batchStatusWorkflowStep);

                if(resultSet == null) {
                    resultSet = entityInstancePKHolder;
                } else {
                    resultSet.retainAll(entityInstancePKHolder);
                }
            }
            
            if(countParameters() == 0 && (resultSet == null || resultSet.size() > 0)) {
                var entityInstancePKHolder = getEntityInstancePKHolderByBatchType(batchType);

                if(resultSet == null) {
                    resultSet = entityInstancePKHolder;
                } else {
                    resultSet.retainAll(entityInstancePKHolder);
                }
            }
        } else {
            var batchControl = Session.getModelController(BatchControl.class);
            Batch batch = null;

            if(parameterCount == 1) {
                if(batchAliasType == null) {
                    batchAliasType = batchControl.getDefaultBatchAliasType(batchType);
                }

                if(batchAliasType != null) {
                    batch = batchControl.getBatchByAlias(batchAliasType, alias);
                }

                if(batchName != null) {
                    batch = batchControl.getBatchByName(batchType, batchName);
                }

                if(batch != null) {
                    var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);

                    resultSet = new EntityInstancePKHolder(1);
                    resultSet.add(entityInstanceControl.getEntityInstanceByBasePK(batch.getPrimaryKey()).getPrimaryKey(), null);
                }
            }
        }
        
        return resultSet;
    }

}
