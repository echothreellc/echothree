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

package com.echothree.model.control.uom.server.graphql;

import com.echothree.control.user.uom.server.command.GetUnitOfMeasureKindUsesCommand;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUse;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUseType;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.List;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("unit of measure kind use type object")
@GraphQLName("UnitOfMeasureKindUseType")
public class UnitOfMeasureKindUseTypeObject
        extends BaseEntityInstanceObject {
    
    private final UnitOfMeasureKindUseType unitOfMeasureKindUseType; // Always Present
    
    public UnitOfMeasureKindUseTypeObject(UnitOfMeasureKindUseType unitOfMeasureKindUseType) {
        super(unitOfMeasureKindUseType.getPrimaryKey());
        
        this.unitOfMeasureKindUseType = unitOfMeasureKindUseType;
    }
    
    private Boolean hasUnitOfMeasureKindUsesAccess;
    
    private boolean getHasUnitOfMeasureKindUsesAccess(final DataFetchingEnvironment env) {
        if(hasUnitOfMeasureKindUsesAccess == null) {
            GraphQlContext context = env.getContext();
            BaseMultipleEntitiesCommand baseMultipleEntitiesCommand = new GetUnitOfMeasureKindUsesCommand(context.getUserVisitPK(), null);
            
            baseMultipleEntitiesCommand.security();
            
            hasUnitOfMeasureKindUsesAccess = !baseMultipleEntitiesCommand.hasSecurityMessages();
        }
        
        return hasUnitOfMeasureKindUsesAccess;
    }

    @GraphQLField
    @GraphQLDescription("unit of measure kind use type name")
    @GraphQLNonNull
    public String getUnitOfMeasureKindUseTypeName() {
        return unitOfMeasureKindUseType.getUnitOfMeasureKindUseTypeName();
    }
    
    @GraphQLField
    @GraphQLDescription("allow multiple")
    @GraphQLNonNull
    public boolean getAllowMultiple() {
        return unitOfMeasureKindUseType.getAllowMultiple();
    }
    
    @GraphQLField
    @GraphQLDescription("allow fraction digits")
    @GraphQLNonNull
    public boolean getAllowFractionDigits() {
        return unitOfMeasureKindUseType.getAllowFractionDigits();
    }
    
    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return unitOfMeasureKindUseType.getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return unitOfMeasureKindUseType.getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var uomControl = (UomControl)Session.getModelController(UomControl.class);
        var userControl = (UserControl)Session.getModelController(UserControl.class);
        GraphQlContext context = env.getContext();
        
        return uomControl.getBestUnitOfMeasureKindUseTypeDescription(unitOfMeasureKindUseType, userControl.getPreferredLanguageFromUserVisit(context.getUserVisit()));
    }
    
    @GraphQLField
    @GraphQLDescription("unit of measure kind uses")
    public List<UnitOfMeasureKindUseObject> getUnitOfMeasureKindUses(final DataFetchingEnvironment env) {
        if(getHasUnitOfMeasureKindUsesAccess(env)) {
            var uomControl = (UomControl)Session.getModelController(UomControl.class);
            List<UnitOfMeasureKindUse> entities = uomControl.getUnitOfMeasureKindUsesByUnitOfMeasureKindUseType(unitOfMeasureKindUseType);
            List<UnitOfMeasureKindUseObject> unitOfMeasureKindUses = new ArrayList<>(entities.size());
            
            entities.forEach((entity) -> {
                unitOfMeasureKindUses.add(new UnitOfMeasureKindUseObject(entity));
            });

            return unitOfMeasureKindUses;
        } else {
            return null;
        }
    }
    
    @GraphQLField
    @GraphQLDescription("unit of measure kind use count")
    public Long getUnitOfMeasureKindUseCount(final DataFetchingEnvironment env) {
        if(getHasUnitOfMeasureKindUsesAccess(env)) {
            var uomControl = (UomControl)Session.getModelController(UomControl.class);

            return uomControl.countUnitOfMeasureKindUsesByUnitOfMeasureKindUseType(unitOfMeasureKindUseType);
        } else {
            return null;
        }
    }
    
}
