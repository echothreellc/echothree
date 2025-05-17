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

import com.echothree.model.control.core.server.control.ColorControl;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.core.server.entity.Color;
import com.echothree.model.data.core.server.entity.ColorDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("color object")
@GraphQLName("Color")
public class ColorObject
        extends BaseEntityInstanceObject {
    
    private final Color color; // Always Present
    
    public ColorObject(Color color) {
        super(color.getPrimaryKey());
        
        this.color = color;
    }

    private ColorDetail colorDetail; // Optional, use getColorDetail()
    
    private ColorDetail getColorDetail() {
        if(colorDetail == null) {
            colorDetail = color.getLastDetail();
        }
        
        return colorDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("color name")
    @GraphQLNonNull
    public String getColorName() {
        return getColorDetail().getColorName();
    }
    
    @GraphQLField
    @GraphQLDescription("red")
    @GraphQLNonNull
    public int getRed() {
        return getColorDetail().getRed();
    }
    
    @GraphQLField
    @GraphQLDescription("green")
    @GraphQLNonNull
    public int getGreen() {
        return getColorDetail().getGreen();
    }
    
    @GraphQLField
    @GraphQLDescription("blue")
    @GraphQLNonNull
    public int getBlue() {
        return getColorDetail().getBlue();
    }
    
    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getColorDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getColorDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var colorControl = Session.getModelController(ColorControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return colorControl.getBestColorDescription(color, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
}
