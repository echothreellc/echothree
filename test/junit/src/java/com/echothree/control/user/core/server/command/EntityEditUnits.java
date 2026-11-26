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

package com.echothree.control.user.core.server.command;

import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureEquivalent;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindDetail;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureTypeDetail;
import com.echothree.model.data.uom.server.value.UnitOfMeasureKindDetailValue;
import com.echothree.model.data.uom.server.value.UnitOfMeasureKindValue;
import com.echothree.model.data.uom.server.value.UnitOfMeasureTypeDetailValue;
import com.echothree.model.data.uom.server.value.UnitOfMeasureTypeValue;
import com.echothree.util.server.persistence.EntityPermission;
import java.util.List;

/**
 * Uses the real optional-pair checks and conversion with a single base length unit.
 */
class EntityEditUnits {

    static UnitOfMeasureTypeLogic logic() {
        var kindDetail = new UnitOfMeasureKindDetail(EntityEditFixture.construct(UnitOfMeasureKindDetailValue.class), EntityPermission.READ_ONLY);
        var typeDetail = new UnitOfMeasureTypeDetail(EntityEditFixture.construct(UnitOfMeasureTypeDetailValue.class), EntityPermission.READ_ONLY);
        EntityEditFixture.inject(kindDetail.getUnitOfMeasureKindDetailValue(), "unitOfMeasureKindName", "LENGTH");
        EntityEditFixture.inject(typeDetail.getUnitOfMeasureTypeDetailValue(), "unitOfMeasureTypeName", "METER");
        var kind = new UnitOfMeasureKind(EntityEditFixture.construct(UnitOfMeasureKindValue.class), EntityPermission.READ_ONLY) {
            @Override
            public UnitOfMeasureKindDetail getLastDetail() {
                return kindDetail;
            }
        };
        var type = new UnitOfMeasureType(EntityEditFixture.construct(UnitOfMeasureTypeValue.class), EntityPermission.READ_ONLY) {
            @Override
            public UnitOfMeasureTypeDetail getLastDetail() {
                return typeDetail;
            }
        };
        var control = new UomControl() {
            @Override
            public UnitOfMeasureKind getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(String useType) {
                return kind;
            }

            @Override
            public UnitOfMeasureType getUnitOfMeasureTypeByName(UnitOfMeasureKind selectedKind, String name) {
                return name.equals("METER") ? type : null;
            }

            @Override
            public List<UnitOfMeasureType> getUnitOfMeasureTypesByUnitOfMeasureKind(UnitOfMeasureKind selectedKind) {
                return List.of(type);
            }

            @Override
            public List<UnitOfMeasureEquivalent> getUnitOfMeasureEquivalentsByFromUnitOfMeasureType(UnitOfMeasureType selectedType) {
                return List.of();
            }

            @Override
            public List<UnitOfMeasureEquivalent> getUnitOfMeasureEquivalentsByToUnitOfMeasureType(UnitOfMeasureType selectedType) {
                return List.of();
            }
        };
        var logic = new UnitOfMeasureTypeLogic() {
        };
        EntityEditFixture.inject(logic, "uomControl", control);
        return logic;
    }

}
