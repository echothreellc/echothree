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

package com.echothree.model.control.shipment.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.shipment.server.control.FreeOnBoardControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.shipment.server.entity.FreeOnBoard;
import com.echothree.model.data.shipment.server.entity.FreeOnBoardDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("free on board object")
@GraphQLName("FreeOnBoard")
public class FreeOnBoardObject
        extends BaseEntityInstanceObject {
    
    private final FreeOnBoard freeOnBoard; // Always Present
    
    public FreeOnBoardObject(FreeOnBoard freeOnBoard) {
        super(freeOnBoard.getPrimaryKey());
        
        this.freeOnBoard = freeOnBoard;
    }

    private FreeOnBoardDetail freeOnBoardDetail; // Optional, use getFreeOnBoardDetail()
    
    private FreeOnBoardDetail getFreeOnBoardDetail() {
        if(freeOnBoardDetail == null) {
            freeOnBoardDetail = freeOnBoard.getLastDetail();
        }
        
        return freeOnBoardDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("free on board name")
    @GraphQLNonNull
    public String getFreeOnBoardName() {
        return getFreeOnBoardDetail().getFreeOnBoardName();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getFreeOnBoardDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getFreeOnBoardDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var freeOnBoardControl = Session.getModelController(FreeOnBoardControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return freeOnBoardControl.getBestFreeOnBoardDescription(freeOnBoard, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
}
