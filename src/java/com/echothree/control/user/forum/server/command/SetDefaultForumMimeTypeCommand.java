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

package com.echothree.control.user.forum.server.command;

import com.echothree.control.user.forum.common.form.SetDefaultForumMimeTypeForm;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class SetDefaultForumMimeTypeCommand
        extends BaseSimpleCommand<SetDefaultForumMimeTypeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ForumName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("MimeTypeName", FieldType.MIME_TYPE, true, null, null)
                ));
    }
    
    /** Creates a new instance of SetDefaultForumMimeTypeCommand */
    public SetDefaultForumMimeTypeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var forumControl = Session.getModelController(ForumControl.class);
        var forumName = form.getForumName();
        var forum = forumControl.getForumByName(forumName);
        
        if(forum != null) {
            var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
            var mimeTypeName = form.getMimeTypeName();
            var mimeType = mimeTypeControl.getMimeTypeByName(mimeTypeName);
            
            if(mimeType != null) {
                var forumMimeTypeValue = forumControl.getForumMimeTypeValueForUpdate(forum, mimeType);
                
                if(forumMimeTypeValue != null) {
                    forumMimeTypeValue.setIsDefault(true);
                    forumControl.updateForumMimeTypeFromValue(forumMimeTypeValue, getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.UnknownForumMimeType.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownMimeTypeName.name(), mimeTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownForumName.name(), forumName);
        }
        
        return null;
    }
    
}
