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

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("sequence object")
@GraphQLName("Sequence")
public class SequenceObject
        extends BaseEntityInstanceObject {

    private final Sequence sequence; // Always Present

    public SequenceObject(Sequence sequence) {
        super(sequence.getPrimaryKey());

        this.sequence = sequence;
    }

    private SequenceDetail sequenceDetail; // Optional, sequence getSequenceDetail()

    private SequenceDetail getSequenceDetail() {
        if(sequenceDetail == null) {
            sequenceDetail = sequence.getLastDetail();
        }

        return sequenceDetail;
    }

    @GraphQLField
    @GraphQLDescription("sequence type")
    public SequenceTypeObject getSequenceType(final DataFetchingEnvironment env) {
        return SequenceSecurityUtils.getHasSequenceTypeAccess(env) ? new SequenceTypeObject(getSequenceDetail().getSequenceType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("sequence name")
    public String getSequenceName() {
        return getSequenceDetail().getSequenceName();
    }

    @GraphQLField
    @GraphQLDescription("mask")
    public String getMask() {
        return getSequenceDetail().getMask();
    }

    @GraphQLField
    @GraphQLDescription("chunk size")
    @GraphQLNonNull
    public int getChunkSize() {
        return getSequenceDetail().getChunkSize();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getSequenceDetail().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getSequenceDetail().getSortOrder();
    }

    @GraphQLField
    @GraphQLDescription("value")
    public String getValue() {
        var sequenceControl = Session.getModelController(SequenceControl.class);

        return sequenceControl.getSequenceValue(sequence).getValue();
    }

    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return sequenceControl.getBestSequenceDescription(sequence, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

}
