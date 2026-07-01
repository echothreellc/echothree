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

package com.echothree.control.user.contact.server.command;

import com.echothree.control.user.contact.common.form.GetPostalAddressFormatsForm;
import com.echothree.control.user.contact.common.result.ContactResultFactory;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.data.contact.server.entity.PostalAddressFormat;
import com.echothree.model.data.contact.server.factory.PostalAddressFormatFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetPostalAddressFormatsCommand
        extends BasePaginatedMultipleEntitiesCommand<PostalAddressFormat, GetPostalAddressFormatsForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of();
    }

    @Inject
    ContactControl contactControl;

    /** Creates a new instance of GetPostalAddressFormatsCommand */
    public GetPostalAddressFormatsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        // No form fields.
    }

    @Override
    protected Long getTotalEntities() {
        return contactControl.countPostalAddressFormats();
    }

    @Override
    protected Collection<PostalAddressFormat> getEntities() {
        return contactControl.getPostalAddressFormats();
    }

    @Override
    protected BaseResult getResult(Collection<PostalAddressFormat> entities) {
        var result = ContactResultFactory.getGetPostalAddressFormatsResult();

        if(entities != null) {
            if(session.hasLimit(PostalAddressFormatFactory.class)) {
                result.setPostalAddressFormatCount(getTotalEntities());
            }

            result.setPostalAddressFormats(contactControl.getPostalAddressFormatTransfers(getUserVisit(), entities));
        }

        return result;
    }

}
