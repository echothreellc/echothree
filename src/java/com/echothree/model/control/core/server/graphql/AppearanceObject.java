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

import com.echothree.model.control.core.server.control.AppearanceControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.core.server.entity.Appearance;
import com.echothree.model.data.core.server.entity.AppearanceDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.List;

@GraphQLDescription("appearance object")
@GraphQLName("Appearance")
public class AppearanceObject
        extends BaseEntityInstanceObject {
    
    private final Appearance appearance; // Always Present
    
    public AppearanceObject(Appearance appearance) {
        super(appearance.getPrimaryKey());
        
        this.appearance = appearance;
    }

    private AppearanceDetail appearanceDetail; // Optional, use getAppearanceDetail()
    
    private AppearanceDetail getAppearanceDetail() {
        if(appearanceDetail == null) {
            appearanceDetail = appearance.getLastDetail();
        }
        
        return appearanceDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("appearance name")
    @GraphQLNonNull
    public String getAppearanceName() {
        return getAppearanceDetail().getAppearanceName();
    }
    
    @GraphQLField
    @GraphQLDescription("text color")
    public ColorObject getTextColor() {
        var textColor = getAppearanceDetail().getTextColor();
        
        return textColor == null ? null : new ColorObject(textColor);
    }
    
    @GraphQLField
    @GraphQLDescription("background color")
    public ColorObject getBackgroundColor() {
        var backgroundColor = getAppearanceDetail().getBackgroundColor();
        
        return backgroundColor == null ? null : new ColorObject(backgroundColor);
    }
    
    @GraphQLField
    @GraphQLDescription("font style")
    public FontStyleObject getFontStyle() {
        var fontStyle = getAppearanceDetail().getFontStyle();
        
        return fontStyle == null ? null : new FontStyleObject(fontStyle);
    }
    
    @GraphQLField
    @GraphQLDescription("font weight")
    public FontWeightObject getFontWeight() {
        var fontWeight = getAppearanceDetail().getFontWeight();
        
        return fontWeight == null ? null : new FontWeightObject(fontWeight);
    }
    
    @GraphQLField
    @GraphQLDescription("appearance text decorations")
    @GraphQLNonNull
    public List<AppearanceTextDecorationObject> getAppearanceTextDecorations() {
        var appearanceControl = Session.getModelController(AppearanceControl.class);
        var entities = appearanceControl.getAppearanceTextDecorationsByAppearance(appearance);
        var objects = new ArrayList<AppearanceTextDecorationObject>(entities.size());
        
        entities.forEach((entity) -> {
            objects.add(new AppearanceTextDecorationObject(entity));
        });
        
        return objects;
    }
    
    @GraphQLField
    @GraphQLDescription("appearance text transformations")
    @GraphQLNonNull
    public List<AppearanceTextTransformationObject> getAppearanceTextTransformations() {
        var appearanceControl = Session.getModelController(AppearanceControl.class);
        var entities = appearanceControl.getAppearanceTextTransformationsByAppearance(appearance);
        var objects = new ArrayList<AppearanceTextTransformationObject>(entities.size());
        
        entities.forEach((entity) -> {
            objects.add(new AppearanceTextTransformationObject(entity));
        });
        
        return objects;
    }
    
    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getAppearanceDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getAppearanceDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var appearanceControl = Session.getModelController(AppearanceControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return appearanceControl.getBestAppearanceDescription(appearance, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
}
