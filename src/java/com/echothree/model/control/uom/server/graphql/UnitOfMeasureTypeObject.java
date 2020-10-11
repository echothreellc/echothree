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

import com.echothree.control.user.accounting.server.command.GetSymbolPositionCommand;
import com.echothree.control.user.uom.server.command.GetUnitOfMeasureKindCommand;
import com.echothree.model.control.accounting.server.graphql.SymbolPositionObject;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureTypeDescription;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureTypeDetail;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("unit of measure type object")
@GraphQLName("UnitOfMeasureType")
public class UnitOfMeasureTypeObject
        extends BaseEntityInstanceObject {
    
    private final UnitOfMeasureType unitOfMeasureType; // Always Present
    
    public UnitOfMeasureTypeObject(UnitOfMeasureType unitOfMeasureType) {
        super(unitOfMeasureType.getPrimaryKey());
        
        this.unitOfMeasureType = unitOfMeasureType;
    }

    private UnitOfMeasureTypeDetail unitOfMeasureTypeDetail; // Optional, use getUnitOfMeasureTypeDetail()
    
    private UnitOfMeasureTypeDetail getUnitOfMeasureTypeDetail() {
        if(unitOfMeasureTypeDetail == null) {
            unitOfMeasureTypeDetail = unitOfMeasureType.getLastDetail();
        }
        
        return unitOfMeasureTypeDetail;
    }
    
    private UnitOfMeasureTypeDescription unitOfMeasureTypeDescription; // Optional, use getUnitOfMeasureTypeDescription()
    
    private UnitOfMeasureTypeDescription getUnitOfMeasureTypeDescription(final DataFetchingEnvironment env) {
        if(unitOfMeasureTypeDescription == null) {
            var uomControl = (UomControl)Session.getModelController(UomControl.class);
            var userControl = (UserControl)Session.getModelController(UserControl.class);
            GraphQlContext context = env.getContext();

            unitOfMeasureTypeDescription = uomControl.getBestUnitOfMeasureTypeDescription(unitOfMeasureType, userControl.getPreferredLanguageFromUserVisit(context.getUserVisit()));
        }
        
        return unitOfMeasureTypeDescription;
    }
    
    private Boolean hasUnitOfMeasureKindAccess;
    
    private boolean getHasUnitOfMeasureKindAccess(final DataFetchingEnvironment env) {
        if(hasUnitOfMeasureKindAccess == null) {
            GraphQlContext context = env.getContext();
            BaseSingleEntityCommand baseSingleEntityCommand = new GetUnitOfMeasureKindCommand(context.getUserVisitPK(), null);
            
            baseSingleEntityCommand.security();
            
            hasUnitOfMeasureKindAccess = !baseSingleEntityCommand.hasSecurityMessages();
        }
        
        return hasUnitOfMeasureKindAccess;
    }
    
    private Boolean hasSymbolPositionAccess;
    
    private boolean getHasSymbolPositionAccess(final DataFetchingEnvironment env) {
        if(hasSymbolPositionAccess == null) {
            GraphQlContext context = env.getContext();
            BaseSingleEntityCommand baseSingleEntityCommand = new GetSymbolPositionCommand(context.getUserVisitPK(), null);
            
            baseSingleEntityCommand.security();
            
            hasSymbolPositionAccess = !baseSingleEntityCommand.hasSecurityMessages();
        }
        
        return hasSymbolPositionAccess;
    }
    
    @GraphQLField
    @GraphQLDescription("unit of measure kind")
    public UnitOfMeasureKindObject getUnitOfMeasureKind(final DataFetchingEnvironment env) {
        return getHasUnitOfMeasureKindAccess(env) ? new UnitOfMeasureKindObject(getUnitOfMeasureTypeDetail().getUnitOfMeasureKind()) : null;
    }
    
    @GraphQLField
    @GraphQLDescription("unit of measure type name")
    @GraphQLNonNull
    public String getUnitOfMeasureTypeName() {
        return getUnitOfMeasureTypeDetail().getUnitOfMeasureTypeName();
    }
    
    @GraphQLField
    @GraphQLDescription("symbol position")
    public SymbolPositionObject getSymbolPosition(final DataFetchingEnvironment env) {
        return getHasSymbolPositionAccess(env) ? new SymbolPositionObject(getUnitOfMeasureTypeDetail().getSymbolPosition()) : null;
    }
    
    @GraphQLField
    @GraphQLDescription("suppress symbol separator")
    @GraphQLNonNull
    public boolean getSuppressSymbolSeparator() {
        return getUnitOfMeasureTypeDetail().getSuppressSymbolSeparator();
    }
    
    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getUnitOfMeasureTypeDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getUnitOfMeasureTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("singular description")
    @GraphQLNonNull
    public String getSingularDescription(final DataFetchingEnvironment env) {
        var uomControl = (UomControl)Session.getModelController(UomControl.class);
        
        return uomControl.getBestSingularUnitOfMeasureTypeDescription(unitOfMeasureType, getUnitOfMeasureTypeDescription(env));
    }
    
    @GraphQLField
    @GraphQLDescription("plural description")
    @GraphQLNonNull
    public String getPluralDescription(final DataFetchingEnvironment env) {
        var uomControl = (UomControl)Session.getModelController(UomControl.class);
        
        return uomControl.getBestPluralUnitOfMeasureTypeDescription(unitOfMeasureType, getUnitOfMeasureTypeDescription(env));
    }
    
    @GraphQLField
    @GraphQLDescription("symbol")
    @GraphQLNonNull
    public String getSymbol(final DataFetchingEnvironment env) {
        var uomControl = (UomControl)Session.getModelController(UomControl.class);
        
        return uomControl.getBestUnitOfMeasureTypeDescriptionSymbol(unitOfMeasureType, getUnitOfMeasureTypeDescription(env));
    }
    
}
