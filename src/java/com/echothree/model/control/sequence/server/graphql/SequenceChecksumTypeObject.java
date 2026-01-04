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

package com.echothree.model.control.sequence.server.graphql;

import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.sequence.server.entity.SequenceChecksumType;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("sequence checksum type object")
@GraphQLName("SequenceChecksumType")
public class SequenceChecksumTypeObject
        implements BaseGraphQl {
    
    private final SequenceChecksumType sequenceChecksumType; // Always Present
    
    public SequenceChecksumTypeObject(SequenceChecksumType sequenceChecksumType) {
        this.sequenceChecksumType = sequenceChecksumType;
    }

    @GraphQLField
    @GraphQLDescription("sequence checksum type name")
    @GraphQLNonNull
    public String getSequenceChecksumTypeName() {
        return sequenceChecksumType.getSequenceChecksumTypeName();
    }
    
    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return sequenceChecksumType.getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return sequenceChecksumType.getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return sequenceControl.getBestSequenceChecksumTypeDescription(sequenceChecksumType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
}
