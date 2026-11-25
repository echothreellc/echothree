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

import com.echothree.model.control.contact.common.exception.UnknownPostalAddressLineElementException;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.data.contact.server.entity.PostalAddressLine;
import com.echothree.model.data.contact.server.entity.PostalAddressLineElement;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class PostalAddressLineElementLogic
        extends BaseLogic {

    @Inject
    ContactControl contactControl;

    @Inject
    PostalAddressLineLogic postalAddressLineLogic;

    protected PostalAddressLineElementLogic() {
        super();
    }

    public PostalAddressLineElement getPostalAddressLineElement(final ExecutionErrorAccumulator eea, final PostalAddressLine postalAddressLine,
            final Integer postalAddressLineElementSortOrder, final EntityPermission entityPermission) {
        var postalAddressLineElement = contactControl.getPostalAddressLineElement(postalAddressLine, postalAddressLineElementSortOrder, entityPermission);

        if(postalAddressLineElement == null) {
            var postalAddressLineDetail = postalAddressLine.getLastDetail();

            handleExecutionError(UnknownPostalAddressLineElementException.class, eea, ExecutionErrors.UnknownPostalAddressLineElement.name(),
                    postalAddressLineDetail.getPostalAddressFormat().getLastDetail().getPostalAddressFormatName(),
                    postalAddressLineDetail.getPostalAddressLineSortOrder().toString(), postalAddressLineElementSortOrder.toString());
        }

        return postalAddressLineElement;
    }

    public PostalAddressLineElement getPostalAddressLineElement(final ExecutionErrorAccumulator eea, final PostalAddressLine postalAddressLine,
            final Integer postalAddressLineElementSortOrder) {
        return getPostalAddressLineElement(eea, postalAddressLine, postalAddressLineElementSortOrder, EntityPermission.READ_ONLY);
    }

    public PostalAddressLineElement getPostalAddressLineElementForUpdate(final ExecutionErrorAccumulator eea, final PostalAddressLine postalAddressLine,
            final Integer postalAddressLineElementSortOrder) {
        return getPostalAddressLineElement(eea, postalAddressLine, postalAddressLineElementSortOrder, EntityPermission.READ_WRITE);
    }

    public PostalAddressLineElement getPostalAddressLineElement(final ExecutionErrorAccumulator eea, final String postalAddressFormatName,
            final Integer postalAddressLineSortOrder, final Integer postalAddressLineElementSortOrder, final EntityPermission entityPermission) {
        var postalAddressLine = postalAddressLineLogic.getPostalAddressLine(eea, postalAddressFormatName, postalAddressLineSortOrder);
        PostalAddressLineElement postalAddressLineElement = null;

        if(!hasExecutionErrors(eea)) {
            postalAddressLineElement = getPostalAddressLineElement(eea, postalAddressLine, postalAddressLineElementSortOrder, entityPermission);
        }

        return postalAddressLineElement;
    }

    public PostalAddressLineElement getPostalAddressLineElement(final ExecutionErrorAccumulator eea, final String postalAddressFormatName,
            final Integer postalAddressLineSortOrder, final Integer postalAddressLineElementSortOrder) {
        return getPostalAddressLineElement(eea, postalAddressFormatName, postalAddressLineSortOrder, postalAddressLineElementSortOrder, EntityPermission.READ_ONLY);
    }

    public PostalAddressLineElement getPostalAddressLineElementForUpdate(final ExecutionErrorAccumulator eea, final String postalAddressFormatName,
            final Integer postalAddressLineSortOrder, final Integer postalAddressLineElementSortOrder) {
        return getPostalAddressLineElement(eea, postalAddressFormatName, postalAddressLineSortOrder, postalAddressLineElementSortOrder, EntityPermission.READ_WRITE);
    }

}
