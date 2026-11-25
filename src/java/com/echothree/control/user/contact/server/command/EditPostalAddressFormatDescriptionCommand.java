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

import com.echothree.control.user.contact.common.edit.ContactEditFactory;
import com.echothree.control.user.contact.common.edit.PostalAddressFormatDescriptionEdit;
import com.echothree.control.user.contact.common.result.ContactResultFactory;
import com.echothree.control.user.contact.common.result.EditPostalAddressFormatDescriptionResult;
import com.echothree.control.user.contact.common.spec.PostalAddressFormatDescriptionSpec;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.contact.server.logic.PostalAddressFormatLogic;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.data.contact.server.entity.PostalAddressFormat;
import com.echothree.model.data.contact.server.entity.PostalAddressFormatDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditPostalAddressFormatDescriptionCommand
        extends BaseAbstractEditCommand<PostalAddressFormatDescriptionSpec, PostalAddressFormatDescriptionEdit, EditPostalAddressFormatDescriptionResult, PostalAddressFormatDescription, PostalAddressFormat> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PostalAddressFormatName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    ContactControl contactControl;

    @Inject
    LanguageLogic languageLogic;

    @Inject
    PostalAddressFormatLogic postalAddressFormatLogic;

    /** Creates a new instance of EditPostalAddressFormatDescriptionCommand */
    public EditPostalAddressFormatDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditPostalAddressFormatDescriptionResult getResult() {
        return ContactResultFactory.getEditPostalAddressFormatDescriptionResult();
    }

    @Override
    public PostalAddressFormatDescriptionEdit getEdit() {
        return ContactEditFactory.getPostalAddressFormatDescriptionEdit();
    }

    @Override
    public PostalAddressFormatDescription getEntity(EditPostalAddressFormatDescriptionResult result) {
        PostalAddressFormatDescription postalAddressFormatDescription = null;
        var postalAddressFormatName = spec.getPostalAddressFormatName();
        var languageIsoName = spec.getLanguageIsoName();
        var postalAddressFormat = postalAddressFormatLogic.getPostalAddressFormatByName(this, postalAddressFormatName);
        var language = languageLogic.getLanguageByName(this, languageIsoName);

        if(!hasExecutionErrors()) {
            postalAddressFormatDescription = contactControl.getPostalAddressFormatDescription(postalAddressFormat, language,
                    editModeToEntityPermission(editMode));

            if(postalAddressFormatDescription == null) {
                addExecutionError(ExecutionErrors.UnknownPostalAddressFormatDescription.name(), postalAddressFormatName, languageIsoName);
            }
        }

        return postalAddressFormatDescription;
    }

    @Override
    public PostalAddressFormat getLockEntity(PostalAddressFormatDescription postalAddressFormatDescription) {
        return postalAddressFormatDescription.getPostalAddressFormat();
    }

    @Override
    public void fillInResult(EditPostalAddressFormatDescriptionResult result, PostalAddressFormatDescription postalAddressFormatDescription) {
        result.setPostalAddressFormatDescription(contactControl.getPostalAddressFormatDescriptionTransfer(getUserVisit(), postalAddressFormatDescription));
    }

    @Override
    public void doLock(PostalAddressFormatDescriptionEdit edit, PostalAddressFormatDescription postalAddressFormatDescription) {
        edit.setDescription(postalAddressFormatDescription.getDescription());
    }

    @Override
    public void doUpdate(PostalAddressFormatDescription postalAddressFormatDescription) {
        var postalAddressFormatDescriptionValue = contactControl.getPostalAddressFormatDescriptionValue(postalAddressFormatDescription);

        postalAddressFormatDescriptionValue.setDescription(edit.getDescription());

        contactControl.updatePostalAddressFormatDescriptionFromValue(postalAddressFormatDescriptionValue, getPartyPK());
    }

}
