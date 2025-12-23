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

package com.echothree.model.control.core.server.graphql;

import com.echothree.model.control.core.server.control.FontControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.core.server.entity.FontWeight;
import com.echothree.model.data.core.server.entity.FontWeightDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("font weight object")
@GraphQLName("FontWeight")
public class FontWeightObject
        extends BaseEntityInstanceObject {
    
    private final FontWeight fontWeight; // Always Present
    
    public FontWeightObject(FontWeight fontWeight) {
        super(fontWeight.getPrimaryKey());
        
        this.fontWeight = fontWeight;
    }

    private FontWeightDetail fontWeightDetail; // Optional, use getFontWeightDetail()
    
    private FontWeightDetail getFontWeightDetail() {
        if(fontWeightDetail == null) {
            fontWeightDetail = fontWeight.getLastDetail();
        }
        
        return fontWeightDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("font weight name")
    @GraphQLNonNull
    public String getFontWeightName() {
        return getFontWeightDetail().getFontWeightName();
    }
    
    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getFontWeightDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getFontWeightDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var fontControl = Session.getModelController(FontControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return fontControl.getBestFontWeightDescription(fontWeight, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
}
