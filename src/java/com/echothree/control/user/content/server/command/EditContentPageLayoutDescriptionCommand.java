// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.control.user.content.server.command;

import com.echothree.control.user.content.common.edit.ContentEditFactory;
import com.echothree.control.user.content.common.edit.ContentPageLayoutDescriptionEdit;
import com.echothree.control.user.content.common.form.EditContentPageLayoutDescriptionForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.control.user.content.common.spec.ContentPageLayoutDescriptionSpec;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditContentPageLayoutDescriptionCommand
        extends BaseEditCommand<ContentPageLayoutDescriptionSpec, ContentPageLayoutDescriptionEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContentPageLayout.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentPageLayoutName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditContentPageLayoutDescriptionCommand */
    public EditContentPageLayoutDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var contentControl = Session.getModelController(ContentControl.class);
        var result = ContentResultFactory.getEditContentPageLayoutDescriptionResult();
        var contentPageLayoutName = spec.getContentPageLayoutName();
        var contentPageLayout = contentControl.getContentPageLayoutByName(contentPageLayoutName);
        
        if(contentPageLayout != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    var contentPageLayoutDescription = contentControl.getContentPageLayoutDescription(contentPageLayout, language);
                    
                    if(contentPageLayoutDescription != null) {
                        if(editMode.equals(EditMode.LOCK)) {
                            result.setContentPageLayoutDescription(contentControl.getContentPageLayoutDescriptionTransfer(getUserVisit(), contentPageLayoutDescription));

                            if(lockEntity(contentPageLayout)) {
                                var edit = ContentEditFactory.getContentPageLayoutDescriptionEdit();

                                result.setEdit(edit);
                                edit.setDescription(contentPageLayoutDescription.getDescription());
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }

                            result.setEntityLock(getEntityLockTransfer(contentPageLayout));
                        } else { // EditMode.ABANDON
                            unlockEntity(contentPageLayout);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownContentPageLayoutDescription.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    var contentPageLayoutDescriptionValue = contentControl.getContentPageLayoutDescriptionValueForUpdate(contentPageLayout, language);
                    
                    if(contentPageLayoutDescriptionValue != null) {
                        if(lockEntityForUpdate(contentPageLayout)) {
                            try {
                                var description = edit.getDescription();
                                
                                contentPageLayoutDescriptionValue.setDescription(description);
                                
                                contentControl.updateContentPageLayoutDescriptionFromValue(contentPageLayoutDescriptionValue, getPartyPK());
                            } finally {
                                unlockEntity(contentPageLayout);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownContentPageLayoutDescription.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownContentPageLayoutName.name(), contentPageLayoutName);
        }
        
        return result;
    }
    
}
