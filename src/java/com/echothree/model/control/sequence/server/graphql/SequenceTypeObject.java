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
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.sequence.common.SequenceConstants;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.sequence.server.entity.SequenceTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("sequence type object")
@GraphQLName("SequenceType")
public class SequenceTypeObject
        extends BaseEntityInstanceObject {

    private final SequenceType sequenceType; // Always Present

    public SequenceTypeObject(SequenceType sequenceType) {
        super(sequenceType.getPrimaryKey());

        this.sequenceType = sequenceType;
    }

    private SequenceTypeDetail sequenceTypeDetail; // Optional, sequenceType getSequenceTypeDetail()

    private SequenceTypeDetail getSequenceTypeDetail() {
        if(sequenceTypeDetail == null) {
            sequenceTypeDetail = sequenceType.getLastDetail();
        }

        return sequenceTypeDetail;
    }

    @GraphQLField
    @GraphQLDescription("sequence type name")
    public String getSequenceTypeName() {
        return getSequenceTypeDetail().getSequenceTypeName();
    }

    @GraphQLField
    @GraphQLDescription("prefix")
    public String getPrefix() {
        return getSequenceTypeDetail().getPrefix();
    }

    @GraphQLField
    @GraphQLDescription("suffix")
    public String getSuffix() {
        return getSequenceTypeDetail().getSuffix();
    }

    @GraphQLField
    @GraphQLDescription("sequence encoder type")
    public SequenceEncoderTypeObject getSequenceEncoderType(final DataFetchingEnvironment env) {
        return SequenceSecurityUtils.getHasSequenceEncoderTypeAccess(env) ? new SequenceEncoderTypeObject(getSequenceTypeDetail().getSequenceEncoderType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("sequence checksum type")
    public SequenceChecksumTypeObject getSequenceChecksumType(final DataFetchingEnvironment env) {
        return SequenceSecurityUtils.getHasSequenceChecksumTypeAccess(env) ? new SequenceChecksumTypeObject(getSequenceTypeDetail().getSequenceChecksumType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("chunk size")
    public Integer getChunkSize() {
        return getSequenceTypeDetail().getChunkSize();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getSequenceTypeDetail().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getSequenceTypeDetail().getSortOrder();
    }

    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return sequenceControl.getBestSequenceTypeDescription(sequenceType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("sequences")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<SequenceObject> getSequences(final DataFetchingEnvironment env) {
        if(SequenceSecurityUtils.getHasSequencesAccess(env)) {
            var sequenceControl = Session.getModelController(SequenceControl.class);
            var totalCount = sequenceControl.countSequencesBySequenceType(sequenceType);

            try(var objectLimiter = new ObjectLimiter(env, SequenceConstants.COMPONENT_VENDOR_NAME, SequenceConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = sequenceControl.getSequencesBySequenceType(sequenceType);
                var sequences = entities.stream().map(SequenceObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, sequences);
            }
        } else {
            return Connections.emptyConnection();
        }
    }
    
}
