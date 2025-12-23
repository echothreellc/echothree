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

package com.echothree.model.control.uom.server.graphql;

import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUse;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("unit of measure kind use object")
@GraphQLName("UnitOfMeasureKindUse")
public class UnitOfMeasureKindUseObject
        implements BaseGraphQl {
    
    private final UnitOfMeasureKindUse unitOfMeasureKindUse; // Always Present
    
    public UnitOfMeasureKindUseObject(UnitOfMeasureKindUse unitOfMeasureKindUse) {
        this.unitOfMeasureKindUse = unitOfMeasureKindUse;
    }
    
    @GraphQLField
    @GraphQLDescription("unit of measure kind use type")
    public UnitOfMeasureKindUseTypeObject getUnitOfMeasureKindUseType(final DataFetchingEnvironment env) {
        return UomSecurityUtils.getHasUnitOfMeasureKindUseTypeAccess(env) ? new UnitOfMeasureKindUseTypeObject(unitOfMeasureKindUse.getUnitOfMeasureKindUseType()) : null;
    }
    
    @GraphQLField
    @GraphQLDescription("unit of measure kind")
    public UnitOfMeasureKindObject getUnitOfMeasureKind(final DataFetchingEnvironment env) {
        return UomSecurityUtils.getHasUnitOfMeasureKindAccess(env) ? new UnitOfMeasureKindObject(unitOfMeasureKindUse.getUnitOfMeasureKind()) : null;
    }

}
