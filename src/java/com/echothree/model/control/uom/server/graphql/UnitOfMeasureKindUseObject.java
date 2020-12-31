// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.control.user.uom.server.command.GetUnitOfMeasureKindCommand;
import com.echothree.control.user.uom.server.command.GetUnitOfMeasureKindUseTypeCommand;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUse;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("unit of measure kind use object")
@GraphQLName("UnitOfMeasureKindUse")
public class UnitOfMeasureKindUseObject {
    
    private final UnitOfMeasureKindUse unitOfMeasureKindUse; // Always Present
    
    public UnitOfMeasureKindUseObject(UnitOfMeasureKindUse unitOfMeasureKindUse) {
        this.unitOfMeasureKindUse = unitOfMeasureKindUse;
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
    
    private Boolean hasUnitOfMeasureKindUseTypeAccess;
    
    private boolean getHasUnitOfMeasureKindUseTypeAccess(final DataFetchingEnvironment env) {
        if(hasUnitOfMeasureKindUseTypeAccess == null) {
            GraphQlContext context = env.getContext();
            BaseSingleEntityCommand baseSingleEntityCommand = new GetUnitOfMeasureKindUseTypeCommand(context.getUserVisitPK(), null);
            
            baseSingleEntityCommand.security();
            
            hasUnitOfMeasureKindUseTypeAccess = !baseSingleEntityCommand.hasSecurityMessages();
        }
        
        return hasUnitOfMeasureKindUseTypeAccess;
    }
    
    @GraphQLField
    @GraphQLDescription("unit of measure kind use type")
    public UnitOfMeasureKindUseTypeObject getUnitOfMeasureKindUseType(final DataFetchingEnvironment env) {
        return getHasUnitOfMeasureKindUseTypeAccess(env) ? new UnitOfMeasureKindUseTypeObject(unitOfMeasureKindUse.getUnitOfMeasureKindUseType()) : null;
    }
    
    @GraphQLField
    @GraphQLDescription("unit of measure kind")
    public UnitOfMeasureKindObject getUnitOfMeasureKind(final DataFetchingEnvironment env) {
        return getHasUnitOfMeasureKindAccess(env) ? new UnitOfMeasureKindObject(unitOfMeasureKindUse.getUnitOfMeasureKind()) : null;
    }

}
