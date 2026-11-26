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

package com.echothree.model.control.core.server.logic;

import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.exception.MismatchedEntityAttributeTypeException;
import com.echothree.model.control.core.common.exception.MismatchedEntityTypeException;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import javax.inject.Inject;

public abstract class BaseEntityAttributeValueLogic
        extends BaseLogic {

    @Inject
    protected EntityInstanceLogic entityInstanceLogic;

    protected BaseEntityAttributeValueLogic() {
        super();
    }

    protected void checkEntityType(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final EntityInstance entityInstance) {
        if(!entityInstance.getEntityType().equals(entityAttribute.getLastDetail().getEntityType())) {
            var expectedEntityTypeDetail = entityAttribute.getLastDetail().getEntityType().getLastDetail();
            var suppliedEntityTypeDetail = entityInstance.getEntityType().getLastDetail();

            handleExecutionError(MismatchedEntityTypeException.class, eea, ExecutionErrors.MismatchedEntityType.name(),
                    expectedEntityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                    expectedEntityTypeDetail.getEntityTypeName(),
                    suppliedEntityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                    suppliedEntityTypeDetail.getEntityTypeName());
        }
    }

    protected void checkEntityType(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final EntityAttributeTypes entityAttributeType) {
        var entityAttributeTypeName = entityAttributeType.name();

        if(!entityAttribute.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName().equals(entityAttributeTypeName)) {
            var expectedEntityTypeDetail = entityAttribute.getLastDetail().getEntityType().getLastDetail();

            handleExecutionError(MismatchedEntityAttributeTypeException.class, eea, ExecutionErrors.MismatchedEntityAttributeType.name(),
                    expectedEntityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                    expectedEntityTypeDetail.getEntityTypeName(),
                    entityAttributeTypeName);
        }
    }

}
