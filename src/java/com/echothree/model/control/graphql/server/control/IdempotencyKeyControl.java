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

import com.echothree.model.data.graphql.server.entity.IdempotencyKey;
import com.echothree.model.data.graphql.server.factory.IdempotencyKeyFactory;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.tables.graphql.IdempotencyKeys.IdempotencyKeys;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EncryptionUtils;
import com.echothree.util.server.persistence.EntityPermission;
import javax.inject.Inject;

@CommandScope
public class IdempotencyKeyControl
        extends BaseModelControl {

    /** Creates a new instance of IdempotencyKeyControl */
    protected IdempotencyKeyControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Idempotency Keys
    // --------------------------------------------------------------------------------

    @Inject
    protected IdempotencyKeyFactory idempotencyKeyFactory;

    public IdempotencyKey createIdempotencyKey(UserVisit userVisit, String idempotencyKeyHash, String queryHash, String variablesHash,
            String operationNameHash, Long startTime, Long completedTime, String response, Long retainUntilTime) {
        return idempotencyKeyFactory.create(userVisit, idempotencyKeyHash, queryHash, variablesHash, operationNameHash,
                startTime, completedTime, response, retainUntilTime);
    }

    public IdempotencyKey getIdempotencyKey(UserVisit userVisit, String idempotencyKeyHash, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(IdempotencyKeys.fields())
                .from(IdempotencyKeys)
                .where(IdempotencyKeys.USER_VISIT.eq(userVisit.getPrimaryKey()),
                        IdempotencyKeys.IDEMPOTENCY_KEY_HASH.eq(idempotencyKeyHash));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return idempotencyKeyFactory.getEntityFromQuery(entityPermission, query);
    }

    public IdempotencyKey getIdempotencyKey(UserVisit userVisit, String idempotencyKeyHash) {
        return getIdempotencyKey(userVisit, idempotencyKeyHash, EntityPermission.READ_ONLY);
    }

    public IdempotencyKey getIdempotencyKeyForUpdate(UserVisit userVisit, String idempotencyKeyHash) {
        return getIdempotencyKey(userVisit, idempotencyKeyHash, EntityPermission.READ_WRITE);
    }

    public String encodeIdempotencyKeyResponse(String response) {
        return EncryptionUtils.getInstance().encrypt(idempotencyKeyFactory.getEntityTypeName(),
                IdempotencyKeyFactory.IDMPKEY_RESPONSE, response);
    }

    public String decodeIdempotencyKeyResponse(IdempotencyKey idempotencyKey) {
        return EncryptionUtils.getInstance().decrypt(idempotencyKeyFactory.getEntityTypeName(),
                IdempotencyKeyFactory.IDMPKEY_RESPONSE, idempotencyKey.getResponse());
    }

    public void removeIdempotencyKey(IdempotencyKey idempotencyKey) {
        idempotencyKey.remove();
    }

    public void removeIdempotencyKeys() {
        var idempotencyKeyPKs = session.getDslContext()
                .select(IdempotencyKeys.IDEMPOTENCY_KEY)
                .from(IdempotencyKeys)
                .forUpdate()
                .fetch(IdempotencyKeys.IDEMPOTENCY_KEY);

        idempotencyKeyFactory.remove(idempotencyKeyPKs);
    }

    public void removeIdempotencyKeysByRetainUntilTime(Long retainUntilTime) {
        var idempotencyKeyPKs = session.getDslContext()
                .select(IdempotencyKeys.IDEMPOTENCY_KEY)
                .from(IdempotencyKeys)
                .where(IdempotencyKeys.RETAIN_UNTIL_TIME.le(retainUntilTime))
                .forUpdate()
                .fetch(IdempotencyKeys.IDEMPOTENCY_KEY);

        idempotencyKeyFactory.remove(idempotencyKeyPKs);
    }

}
