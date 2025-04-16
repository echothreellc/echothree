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

package com.echothree.model.control.core.server.graphql;

import com.echothree.model.control.core.server.control.FontControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.core.server.entity.FontStyle;
import com.echothree.model.data.core.server.entity.FontStyleDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("font style object")
@GraphQLName("FontStyle")
public class FontStyleObject
        extends BaseEntityInstanceObject {
    
    private final FontStyle fontStyle; // Always Present
    
    public FontStyleObject(FontStyle fontStyle) {
        super(fontStyle.getPrimaryKey());
        
        this.fontStyle = fontStyle;
    }

    private FontStyleDetail fontStyleDetail; // Optional, use getFontStyleDetail()
    
    private FontStyleDetail getFontStyleDetail() {
        if(fontStyleDetail == null) {
            fontStyleDetail = fontStyle.getLastDetail();
        }
        
        return fontStyleDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("font style name")
    @GraphQLNonNull
    public String getFontStyleName() {
        return getFontStyleDetail().getFontStyleName();
    }
    
    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getFontStyleDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getFontStyleDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var fontControl = Session.getModelController(FontControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return fontControl.getBestFontStyleDescription(fontStyle, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
}
