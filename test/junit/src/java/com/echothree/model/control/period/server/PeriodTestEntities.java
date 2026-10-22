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

package com.echothree.model.control.period.server;

import com.echothree.model.data.period.common.pk.*;
import com.echothree.model.data.period.server.entity.*;
import com.echothree.model.data.period.server.value.*;
import com.echothree.util.server.persistence.EntityPermission;

public final class PeriodTestEntities {

    private PeriodTestEntities() { }

    public static PeriodKind kind(long id, String name) {
        var pk = new PeriodKindPK(id);
        var detailPK = new PeriodKindDetailPK(id);
        var detail = new PeriodKindDetail(new PeriodKindDetailValue(detailPK, pk, name, null, false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY);
        return new PeriodKind(new PeriodKindValue(pk, detailPK, detailPK), EntityPermission.READ_ONLY) {
            @Override
            public PeriodKindDetail getLastDetail() {
                return detail;
            }
        };
    }

    public static PeriodType type(long id, PeriodKind kind, String name) {
        var pk = new PeriodTypePK(id);
        var detailPK = new PeriodTypeDetailPK(id);
        var detail = new PeriodTypeDetail(new PeriodTypeDetailValue(detailPK, pk, kind.getPrimaryKey(), name, null, null, false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
            @Override
            public PeriodKind getPeriodKind() {
                return kind;
            }
        };
        return new PeriodType(new PeriodTypeValue(pk, detailPK, detailPK), EntityPermission.READ_ONLY) {
            @Override
            public PeriodTypeDetail getLastDetail() {
                return detail;
            }
        };
    }

    public static Period period(long id, PeriodKind kind, String name, PeriodType type, Period parent) {
        var pk = new PeriodPK(id);
        var detailPK = new PeriodDetailPK(id);
        var detail = new PeriodDetail(new PeriodDetailValue(detailPK, pk, kind.getPrimaryKey(), name,
                parent == null ? null : parent.getPrimaryKey(), type == null ? null : type.getPrimaryKey(), 100L, 200L, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
            @Override
            public PeriodKind getPeriodKind() {
                return kind;
            }

            @Override
            public PeriodType getPeriodType() {
                return type;
            }

            @Override
            public Period getParentPeriod() {
                return parent;
            }
        };
        return new Period(new PeriodValue(pk, detailPK, detailPK), EntityPermission.READ_ONLY) {
            @Override
            public PeriodDetail getLastDetail() {
                return detail;
            }
        };
    }

}
