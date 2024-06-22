// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.form.CreateMimeTypeUsageForm;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeUsage;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;

public class CreateMimeTypeUsageCommand
        extends BaseSimpleCommand<CreateMimeTypeUsageForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("MimeTypeName", FieldType.MIME_TYPE, true, null, null),
                new FieldDefinition("MimeTypeUsageTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateMimeTypeUsageCommand */
    public CreateMimeTypeUsageCommand(UserVisitPK userVisitPK, CreateMimeTypeUsageForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var coreControl = Session.getModelController(CoreControl.class);
        String mimeTypeName = form.getMimeTypeName();
        MimeType mimeType = coreControl.getMimeTypeByName(mimeTypeName);

        if(mimeType != null) {
            String mimeTypeUsageTypeName = form.getMimeTypeUsageTypeName();
            MimeTypeUsageType mimeTypeUsageType = coreControl.getMimeTypeUsageTypeByName(mimeTypeUsageTypeName);

            if(mimeTypeUsageType != null) {
                if(mimeTypeUsageTypeName.equals(MimeTypeUsageTypes.IMAGE.name())) {
                    boolean imageReaderFound = false;
                    boolean imageWriterFound = false;
                    String[] readerMimeTypes = ImageIO.getReaderMIMETypes();
                    String[] writerMimeTypes = ImageIO.getWriterMIMETypes();

                    for(int i = 0; i < readerMimeTypes.length; i++) {
                        if(readerMimeTypes[i].equals(mimeTypeName)) {
                            imageReaderFound = true;
                            break;
                        }
                    }

                    if(!imageReaderFound) {
                        addExecutionError(ExecutionErrors.UnknownImageReader.name(), mimeTypeName);
                    }

                    for(int i = 0; i < writerMimeTypes.length; i++) {
                        if(writerMimeTypes[i].equals(mimeTypeName)) {
                            imageWriterFound = true;
                            break;
                        }
                    }

                    if(!imageWriterFound) {
                        addExecutionError(ExecutionErrors.UnknownImageWriter.name(), mimeTypeName);
                    }
                }

                if(!hasExecutionErrors()) {
                    MimeTypeUsage mimeTypeUsage = coreControl.getMimeTypeUsage(mimeType, mimeTypeUsageType);

                    if(mimeTypeUsage == null) {
                        coreControl.createMimeTypeUsage(mimeType, mimeTypeUsageType);
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateMimeTypeUsage.name(), mimeTypeName, mimeTypeUsageTypeName);
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownMimeTypeUsageTypeName.name(), mimeTypeUsageTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownMimeTypeName.name(), mimeTypeName);
        }

        return null;
    }

}
