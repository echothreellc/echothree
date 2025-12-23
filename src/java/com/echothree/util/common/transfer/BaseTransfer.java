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

package com.echothree.util.common.transfer;

import com.echothree.model.control.comment.common.transfer.CommentListWrapper;
import com.echothree.model.control.core.common.transfer.EntityAliasTypeTransfer;
import com.echothree.model.control.core.common.transfer.EntityAttributeGroupTransfer;
import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.model.control.rating.common.transfer.RatingListWrapper;
import com.echothree.model.control.tag.common.transfer.TagScopeTransfer;
import com.echothree.model.control.workeffort.common.transfer.WorkEffortTransfer;
import java.io.Serializable;

public class BaseTransfer
        implements Serializable {
    
    private EntityInstanceTransfer entityInstance;
    private MapWrapper<EntityAliasTypeTransfer> entityAliasTypes;
    private MapWrapper<EntityAttributeGroupTransfer> entityAttributeGroups;
    private MapWrapper<TagScopeTransfer> tagScopes;
    private MapWrapper<CommentListWrapper> comments;
    private MapWrapper<RatingListWrapper> ratings;
    private MapWrapper<WorkEffortTransfer> ownedWorkEfforts;
    
    public EntityInstanceTransfer getEntityInstance() {
        return entityInstance;
    }
    
    public void setEntityInstance(EntityInstanceTransfer entityInstance) {
        this.entityInstance = entityInstance;
    }

    public MapWrapper<EntityAliasTypeTransfer> getEntityAliasTypes() {
        return entityAliasTypes;
    }

    public void setEntityAliasTypes(MapWrapper<EntityAliasTypeTransfer> entityAliasTypes) {
        this.entityAliasTypes = entityAliasTypes;
    }

    public MapWrapper<EntityAttributeGroupTransfer> getEntityAttributeGroups() {
        return entityAttributeGroups;
    }

    public void setEntityAttributeGroups(MapWrapper<EntityAttributeGroupTransfer> entityAttributeGroups) {
        this.entityAttributeGroups = entityAttributeGroups;
    }

    public MapWrapper<TagScopeTransfer> getTagScopes() {
        return tagScopes;
    }
    
    public void setTagScopes(MapWrapper<TagScopeTransfer> tagScopes) {
        this.tagScopes = tagScopes;
    }
    
    public MapWrapper<CommentListWrapper> getComments() {
        return comments;
    }

    public void setComments(MapWrapper<CommentListWrapper> comments) {
        this.comments = comments;
    }

    public void addComments(String commentTypeName, CommentListWrapper wrappedComments) {
        if(comments == null) {
            comments = new MapWrapper<>();
        }

        comments.put(commentTypeName, wrappedComments);
    }

    public MapWrapper<RatingListWrapper> getRatings() {
        return ratings;
    }

    public void setRatings(MapWrapper<RatingListWrapper> ratings) {
        this.ratings = ratings;
    }

    public void addRatings(String ratingTypeName, RatingListWrapper wrappedRatings) {
        if(ratings == null) {
            ratings = new MapWrapper<>();
        }

        ratings.put(ratingTypeName, wrappedRatings);
    }

    public MapWrapper<WorkEffortTransfer> getOwnedWorkEfforts() {
        return ownedWorkEfforts;
    }

    public void setOwnedWorkEfforts(MapWrapper<WorkEffortTransfer> ownedWorkEfforts) {
        this.ownedWorkEfforts = ownedWorkEfforts;
    }

    public void addOwnedWorkEffort(String workEffortTypeName, WorkEffortTransfer ownedWorkEffort) {
        if(ownedWorkEfforts == null) {
            ownedWorkEfforts = new MapWrapper<>(1);
        }

        ownedWorkEfforts.put(workEffortTypeName, ownedWorkEffort);
    }

}
