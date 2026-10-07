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

package com.echothree.model.control.graphql.server.control;

import com.echothree.model.data.graphql.server.entity.IdempotentResponse;
import com.echothree.model.data.graphql.server.factory.IdempotentResponseFactory;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.tables.graphql.IdempotentResponses.IdempotentResponses;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EncryptionUtils;
import com.echothree.util.server.persistence.EntityPermission;
import javax.inject.Inject;

@CommandScope
public class IdempotentResponseControl
        extends BaseModelControl {

    /** Creates a new instance of IdempotentResponseControl */
    protected IdempotentResponseControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Idempotent Responses
    // --------------------------------------------------------------------------------

    @Inject
    protected IdempotentResponseFactory idempotentResponseFactory;

    public IdempotentResponse createIdempotentResponse(UserVisit userVisit, String idempotencyKeyHash, String queryHash, String variablesHash,
            String operationNameHash, Long startTime, Long completedTime, String response, Long retainUntilTime) {
        return idempotentResponseFactory.create(userVisit, idempotencyKeyHash, queryHash, variablesHash, operationNameHash,
                startTime, completedTime, response, retainUntilTime);
    }

    public IdempotentResponse getIdempotentResponse(UserVisit userVisit, String idempotencyKeyHash, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(IdempotentResponses.fields())
                .from(IdempotentResponses)
                .where(IdempotentResponses.USER_VISIT.eq(userVisit.getPrimaryKey()),
                        IdempotentResponses.IDEMPOTENCY_KEY_HASH.eq(idempotencyKeyHash));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return idempotentResponseFactory.getEntityFromQuery(entityPermission, query);
    }

    public IdempotentResponse getIdempotentResponse(UserVisit userVisit, String idempotencyKeyHash) {
        return getIdempotentResponse(userVisit, idempotencyKeyHash, EntityPermission.READ_ONLY);
    }

    public IdempotentResponse getIdempotentResponseForUpdate(UserVisit userVisit, String idempotencyKeyHash) {
        return getIdempotentResponse(userVisit, idempotencyKeyHash, EntityPermission.READ_WRITE);
    }

    public String encodeIdempotentResponse(String response) {
        return EncryptionUtils.getInstance().encrypt(idempotentResponseFactory.getEntityTypeName(),
                IdempotentResponseFactory.IDMPRSP_RESPONSE, response);
    }

    public String decodeIdempotentResponse(IdempotentResponse idempotentResponse) {
        return EncryptionUtils.getInstance().decrypt(idempotentResponseFactory.getEntityTypeName(),
                IdempotentResponseFactory.IDMPRSP_RESPONSE, idempotentResponse.getResponse());
    }

    public void removeIdempotentResponse(IdempotentResponse idempotentResponse) {
        idempotentResponse.remove();
    }

    public void removeIdempotentResponses() {
        var idempotentResponsePKs = session.getDslContext()
                .select(IdempotentResponses.IDEMPOTENT_RESPONSE)
                .from(IdempotentResponses)
                .forUpdate()
                .fetch(IdempotentResponses.IDEMPOTENT_RESPONSE);

        idempotentResponseFactory.remove(idempotentResponsePKs);
    }

    public void removeIdempotentResponsesByRetainUntilTime(Long retainUntilTime) {
        var idempotentResponsePKs = session.getDslContext()
                .select(IdempotentResponses.IDEMPOTENT_RESPONSE)
                .from(IdempotentResponses)
                .where(IdempotentResponses.RETAIN_UNTIL_TIME.le(retainUntilTime))
                .forUpdate()
                .fetch(IdempotentResponses.IDEMPOTENT_RESPONSE);

        idempotentResponseFactory.remove(idempotentResponsePKs);
    }

}
