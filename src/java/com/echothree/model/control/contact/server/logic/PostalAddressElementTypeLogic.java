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

import com.echothree.model.control.contact.common.exception.UnknownPostalAddressElementTypeNameException;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.data.contact.server.entity.PostalAddressElementType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class PostalAddressElementTypeLogic
        extends BaseLogic {

    @Inject
    ContactControl contactControl;

    protected PostalAddressElementTypeLogic() {
        super();
    }

    public PostalAddressElementType getPostalAddressElementTypeByName(final ExecutionErrorAccumulator eea, final String postalAddressElementTypeName) {
        var postalAddressElementType = contactControl.getPostalAddressElementTypeByName(postalAddressElementTypeName);

        if(postalAddressElementType == null) {
            handleExecutionError(UnknownPostalAddressElementTypeNameException.class, eea,
                    ExecutionErrors.UnknownPostalAddressElementTypeName.name(), postalAddressElementTypeName);
        }

        return postalAddressElementType;
    }

}
