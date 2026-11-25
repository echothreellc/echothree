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

import com.echothree.control.user.contact.common.spec.PostalAddressFormatUniversalSpec;
import com.echothree.model.control.contact.common.exception.UnknownPostalAddressFormatNameException;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.contact.server.entity.PostalAddressFormat;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class PostalAddressFormatLogic
        extends BaseLogic {

    @Inject
    ContactControl contactControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    protected PostalAddressFormatLogic() {
        super();
    }

    public PostalAddressFormat getPostalAddressFormatByName(final ExecutionErrorAccumulator eea, final String postalAddressFormatName,
            final EntityPermission entityPermission) {
        var postalAddressFormat = contactControl.getPostalAddressFormatByName(postalAddressFormatName, entityPermission);

        if(postalAddressFormat == null) {
            handleExecutionError(UnknownPostalAddressFormatNameException.class, eea, ExecutionErrors.UnknownPostalAddressFormatName.name(), postalAddressFormatName);
        }

        return postalAddressFormat;
    }

    public PostalAddressFormat getPostalAddressFormatByName(final ExecutionErrorAccumulator eea, final String postalAddressFormatName) {
        return getPostalAddressFormatByName(eea, postalAddressFormatName, EntityPermission.READ_ONLY);
    }

    public PostalAddressFormat getPostalAddressFormatByNameForUpdate(final ExecutionErrorAccumulator eea, final String postalAddressFormatName) {
        return getPostalAddressFormatByName(eea, postalAddressFormatName, EntityPermission.READ_WRITE);
    }

    public PostalAddressFormat getPostalAddressFormatByUniversalSpec(final ExecutionErrorAccumulator eea,
            final PostalAddressFormatUniversalSpec universalSpec, final EntityPermission entityPermission) {
        PostalAddressFormat postalAddressFormat = null;
        var postalAddressFormatName = universalSpec.getPostalAddressFormatName();
        var parameterCount = (postalAddressFormatName == null ? 0 : 1) + entityInstanceLogic.countPossibleEntitySpecs(universalSpec);

        if(parameterCount == 1) {
            if(postalAddressFormatName == null) {
                var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                        ComponentVendors.ECHO_THREE.name(), EntityTypes.PostalAddressFormat.name());

                if(eea == null || !eea.hasExecutionErrors()) {
                    postalAddressFormat = contactControl.getPostalAddressFormatByEntityInstance(entityInstance, entityPermission);
                }
            } else {
                postalAddressFormat = getPostalAddressFormatByName(eea, postalAddressFormatName, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return postalAddressFormat;
    }

    public PostalAddressFormat getPostalAddressFormatByUniversalSpec(final ExecutionErrorAccumulator eea,
            final PostalAddressFormatUniversalSpec universalSpec) {
        return getPostalAddressFormatByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public PostalAddressFormat getPostalAddressFormatByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final PostalAddressFormatUniversalSpec universalSpec) {
        return getPostalAddressFormatByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

}
