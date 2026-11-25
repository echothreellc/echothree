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

package com.echothree.model.control.contact.server.logic;

import com.echothree.control.user.contact.common.spec.PostalAddressLineUniversalSpec;
import com.echothree.model.control.contact.common.exception.UnknownPostalAddressLineException;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.contact.server.entity.PostalAddressFormat;
import com.echothree.model.data.contact.server.entity.PostalAddressLine;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class PostalAddressLineLogic
        extends BaseLogic {

    @Inject
    ContactControl contactControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    PostalAddressFormatLogic postalAddressFormatLogic;

    protected PostalAddressLineLogic() {
        super();
    }

    public PostalAddressLine getPostalAddressLine(final ExecutionErrorAccumulator eea, final PostalAddressFormat postalAddressFormat,
            final Integer postalAddressLineSortOrder, final EntityPermission entityPermission) {
        var postalAddressLine = contactControl.getPostalAddressLine(postalAddressFormat, postalAddressLineSortOrder, entityPermission);

        if(postalAddressLine == null) {
            handleExecutionError(UnknownPostalAddressLineException.class, eea, ExecutionErrors.UnknownPostalAddressLine.name(),
                    postalAddressFormat.getLastDetail().getPostalAddressFormatName(), postalAddressLineSortOrder.toString());
        }

        return postalAddressLine;
    }

    public PostalAddressLine getPostalAddressLine(final ExecutionErrorAccumulator eea, final PostalAddressFormat postalAddressFormat,
            final Integer postalAddressLineSortOrder) {
        return getPostalAddressLine(eea, postalAddressFormat, postalAddressLineSortOrder, EntityPermission.READ_ONLY);
    }

    public PostalAddressLine getPostalAddressLineForUpdate(final ExecutionErrorAccumulator eea, final PostalAddressFormat postalAddressFormat,
            final Integer postalAddressLineSortOrder) {
        return getPostalAddressLine(eea, postalAddressFormat, postalAddressLineSortOrder, EntityPermission.READ_WRITE);
    }

    public PostalAddressLine getPostalAddressLine(final ExecutionErrorAccumulator eea, final String postalAddressFormatName,
            final Integer postalAddressLineSortOrder, final EntityPermission entityPermission) {
        var postalAddressFormat = postalAddressFormatLogic.getPostalAddressFormatByName(eea, postalAddressFormatName);
        PostalAddressLine postalAddressLine = null;

        if(!hasExecutionErrors(eea)) {
            postalAddressLine = getPostalAddressLine(eea, postalAddressFormat, postalAddressLineSortOrder, entityPermission);
        }

        return postalAddressLine;
    }

    public PostalAddressLine getPostalAddressLine(final ExecutionErrorAccumulator eea, final String postalAddressFormatName,
            final Integer postalAddressLineSortOrder) {
        return getPostalAddressLine(eea, postalAddressFormatName, postalAddressLineSortOrder, EntityPermission.READ_ONLY);
    }

    public PostalAddressLine getPostalAddressLineForUpdate(final ExecutionErrorAccumulator eea, final String postalAddressFormatName,
            final Integer postalAddressLineSortOrder) {
        return getPostalAddressLine(eea, postalAddressFormatName, postalAddressLineSortOrder, EntityPermission.READ_WRITE);
    }

    public PostalAddressLine getPostalAddressLineByUniversalSpec(final ExecutionErrorAccumulator eea,
            final PostalAddressLineUniversalSpec universalSpec, final EntityPermission entityPermission) {
        PostalAddressLine postalAddressLine = null;
        var postalAddressFormatName = universalSpec.getPostalAddressFormatName();
        var postalAddressLineSortOrder = universalSpec.getPostalAddressLineSortOrder();
        var possibleEntitySpecs = entityInstanceLogic.countPossibleEntitySpecs(universalSpec);

        if(postalAddressFormatName != null && postalAddressLineSortOrder != null && possibleEntitySpecs == 0) {
            postalAddressLine = getPostalAddressLine(eea, postalAddressFormatName, Integer.valueOf(postalAddressLineSortOrder), entityPermission);
        } else if(postalAddressFormatName == null && postalAddressLineSortOrder == null && possibleEntitySpecs == 1) {
            var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.PostalAddressLine.name());

            if(!hasExecutionErrors(eea)) {
                postalAddressLine = contactControl.getPostalAddressLineByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return postalAddressLine;
    }

    public PostalAddressLine getPostalAddressLineByUniversalSpec(final ExecutionErrorAccumulator eea,
            final PostalAddressLineUniversalSpec universalSpec) {
        return getPostalAddressLineByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public PostalAddressLine getPostalAddressLineByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final PostalAddressLineUniversalSpec universalSpec) {
        return getPostalAddressLineByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

}
