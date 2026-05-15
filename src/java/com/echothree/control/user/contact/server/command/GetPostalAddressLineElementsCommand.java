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

import com.echothree.control.user.contact.common.form.GetPostalAddressLineElementsForm;
import com.echothree.control.user.contact.common.result.ContactResultFactory;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.data.contact.server.entity.PostalAddressLine;
import com.echothree.model.data.contact.server.entity.PostalAddressLineElement;
import com.echothree.model.data.contact.server.factory.PostalAddressLineElementFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetPostalAddressLineElementsCommand
        extends BasePaginatedMultipleEntitiesCommand<PostalAddressLineElement, GetPostalAddressLineElementsForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PostalAddressFormatName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PostalAddressLineSortOrder", FieldType.SIGNED_INTEGER, true, null, null)
        );
    }
    
    @Inject
    ContactControl contactControl;

    /** Creates a new instance of GetPostalAddressLineElementsCommand */
    public GetPostalAddressLineElementsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    private PostalAddressLine postalAddressLine;

    @Override
    protected void handleForm() {
        var postalAddressFormatName = form.getPostalAddressFormatName();
        var postalAddressFormat = contactControl.getPostalAddressFormatByName(postalAddressFormatName);

        if(postalAddressFormat != null) {
            var postalAddressLineSortOrder = Integer.valueOf(form.getPostalAddressLineSortOrder());

            postalAddressLine = contactControl.getPostalAddressLine(postalAddressFormat, postalAddressLineSortOrder);

            if(postalAddressLine == null) {
                addExecutionError(ExecutionErrors.UnknownPostalAddressLine.name(),
                        postalAddressFormat.getLastDetail().getPostalAddressFormatName(),
                        postalAddressLineSortOrder);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPostalAddressFormatName.name(), postalAddressFormatName);
        }
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : contactControl.countPostalAddressLineElementsByPostalAddressLine(postalAddressLine);
    }

    @Override
    protected Collection<PostalAddressLineElement> getEntities() {
        return hasExecutionErrors() ? null : contactControl.getPostalAddressLineElementsByPostalAddressLine(postalAddressLine);
    }

    @Override
    protected BaseResult getResult(Collection<PostalAddressLineElement> entities) {
        var result = ContactResultFactory.getGetPostalAddressLineElementsResult();
        
        if(entities != null) {
            var userVisit = getUserVisit();

            result.setPostalAddressLine(contactControl.getPostalAddressLineTransfer(userVisit, postalAddressLine));

            if(session.hasLimit(PostalAddressLineElementFactory.class)) {
                result.setPostalAddressLineElementCount(getTotalEntities());
            }

            result.setPostalAddressLineElements(contactControl.getPostalAddressLineElementTransfersByPostalAddressLine(userVisit, postalAddressLine));
        }
        
        return result;
    }
    
}
