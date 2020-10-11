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
import com.echothree.control.user.uom.server.command.GetUnitOfMeasureTypeCommand;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindDetail;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUse;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.List;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("unit of measure kind object")
@GraphQLName("UnitOfMeasureKind")
public class UnitOfMeasureKindObject
        extends BaseEntityInstanceObject {
    
    private final UnitOfMeasureKind unitOfMeasureKind; // Always Present
    
    public UnitOfMeasureKindObject(UnitOfMeasureKind unitOfMeasureKind) {
        super(unitOfMeasureKind.getPrimaryKey());
        
        this.unitOfMeasureKind = unitOfMeasureKind;
    }

    private UnitOfMeasureKindDetail unitOfMeasureKindDetail; // Optional, use getUnitOfMeasureKindDetail()
    
    private UnitOfMeasureKindDetail getUnitOfMeasureKindDetail() {
        if(unitOfMeasureKindDetail == null) {
            unitOfMeasureKindDetail = unitOfMeasureKind.getLastDetail();
        }
        
        return unitOfMeasureKindDetail;
    }
    
    private Boolean hasUnitOfMeasureTypeAccess;
    
    private boolean getHasUnitOfMeasureTypeAccess(final DataFetchingEnvironment env) {
        if(hasUnitOfMeasureTypeAccess == null) {
            GraphQlContext context = env.getContext();
            BaseSingleEntityCommand baseSingleEntityCommand = new GetUnitOfMeasureTypeCommand(context.getUserVisitPK(), null);
            
            baseSingleEntityCommand.security();
            
            hasUnitOfMeasureTypeAccess = !baseSingleEntityCommand.hasSecurityMessages();
        }
        
        return hasUnitOfMeasureTypeAccess;
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
    @GraphQLDescription("unit of measure kind name")
    @GraphQLNonNull
    public String getUnitOfMeasureKindName() {
        return getUnitOfMeasureKindDetail().getUnitOfMeasureKindName();
    }
    
    @GraphQLField
    @GraphQLDescription("fraction digits")
    @GraphQLNonNull
    public int getFractionDigits() {
        return getUnitOfMeasureKindDetail().getFractionDigits();
    }
    
    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getUnitOfMeasureKindDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getUnitOfMeasureKindDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var uomControl = (UomControl)Session.getModelController(UomControl.class);
        var userControl = (UserControl)Session.getModelController(UserControl.class);
        GraphQlContext context = env.getContext();
        
        return uomControl.getBestUnitOfMeasureKindDescription(unitOfMeasureKind, userControl.getPreferredLanguageFromUserVisit(context.getUserVisit()));
    }
    
    @GraphQLField
    @GraphQLDescription("unit of measure types")
    public List<UnitOfMeasureTypeObject> getUnitOfMeasureTypes(final DataFetchingEnvironment env) {
        if(getHasUnitOfMeasureTypeAccess(env)) {
            var uomControl = (UomControl)Session.getModelController(UomControl.class);
            List<UnitOfMeasureType> entities = uomControl.getUnitOfMeasureTypesByUnitOfMeasureKind(unitOfMeasureKind);
            List<UnitOfMeasureTypeObject> unitOfMeasureTypes = new ArrayList<>(entities.size());
            
            entities.forEach((entity) -> {
                unitOfMeasureTypes.add(new UnitOfMeasureTypeObject(entity));
            });

            return unitOfMeasureTypes;
        } else {
            return null;
        }
    }
    
    @GraphQLField
    @GraphQLDescription("unit of measure type count")
    public Long getUnitOfMeasureTypeCount(final DataFetchingEnvironment env) {
        if(getHasUnitOfMeasureTypeAccess(env)) {
            var uomControl = (UomControl)Session.getModelController(UomControl.class);

            return uomControl.countUnitOfMeasureTypesByUnitOfMeasureKind(unitOfMeasureKind);
        } else {
            return null;
        }
    }
    
    @GraphQLField
    @GraphQLDescription("unit of measure kind uses")
    public List<UnitOfMeasureKindUseObject> getUnitOfMeasureKindUses(final DataFetchingEnvironment env) {
        if(getHasUnitOfMeasureKindUsesAccess(env)) {
            var uomControl = (UomControl)Session.getModelController(UomControl.class);
            List<UnitOfMeasureKindUse> entities = uomControl.getUnitOfMeasureKindUsesByUnitOfMeasureKind(unitOfMeasureKind);
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

            return uomControl.countUnitOfMeasureKindUsesByUnitOfMeasureKind(unitOfMeasureKind);
        } else {
            return null;
        }
    }
    
}
