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

import com.echothree.model.control.contact.common.exception.UnknownPostalAddressFormatNameException;
import com.echothree.model.control.contact.server.control.ContactControl;
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

}
