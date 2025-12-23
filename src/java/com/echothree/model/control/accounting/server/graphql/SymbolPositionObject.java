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

package com.echothree.model.control.accounting.server.graphql;

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.accounting.server.entity.SymbolPosition;
import com.echothree.model.data.accounting.server.entity.SymbolPositionDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("symbol position object")
@GraphQLName("SymbolPosition")
public class SymbolPositionObject
        extends BaseEntityInstanceObject {
    
    private final SymbolPosition symbolPosition; // Always Present
    
    public SymbolPositionObject(SymbolPosition symbolPosition) {
        super(symbolPosition.getPrimaryKey());
        
        this.symbolPosition = symbolPosition;
    }

    private SymbolPositionDetail symbolPositionDetail; // Optional, use getSymbolPositionDetail()
    
    private SymbolPositionDetail getSymbolPositionDetail() {
        if(symbolPositionDetail == null) {
            symbolPositionDetail = symbolPosition.getLastDetail();
        }
        
        return symbolPositionDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("symbol position name")
    @GraphQLNonNull
    public String getSymbolPositionName() {
        return getSymbolPositionDetail().getSymbolPositionName();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getSymbolPositionDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getSymbolPositionDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return accountingControl.getBestSymbolPositionDescription(symbolPosition, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
}
