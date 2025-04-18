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

package com.echothree.model.control.core.server.logic;

import com.echothree.model.control.core.common.exception.UnknownApplicationEditorException;
import com.echothree.model.control.core.common.exception.UnknownApplicationEditorUseNameException;
import com.echothree.model.control.core.common.exception.UnknownApplicationNameException;
import com.echothree.model.control.core.server.control.ApplicationControl;
import com.echothree.model.data.core.server.entity.Application;
import com.echothree.model.data.core.server.entity.ApplicationEditor;
import com.echothree.model.data.core.server.entity.ApplicationEditorUse;
import com.echothree.model.data.core.server.entity.Editor;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class ApplicationLogic
        extends BaseLogic {
    
    private ApplicationLogic() {
        super();
    }
    
    private static class ApplicationLogicHolder {
        static ApplicationLogic instance = new ApplicationLogic();
    }
    
    public static ApplicationLogic getInstance() {
        return ApplicationLogicHolder.instance;
    }
    
    public Application getApplicationByName(final ExecutionErrorAccumulator eea, final String applicationName) {
        var applicationControl = Session.getModelController(ApplicationControl.class);
        var application = applicationControl.getApplicationByName(applicationName);

        if(application == null) {
            handleExecutionError(UnknownApplicationNameException.class, eea, ExecutionErrors.UnknownApplicationName.name(), applicationName);
        }

        return application;
    }
    
    public ApplicationEditor getApplicationEditor(final ExecutionErrorAccumulator eea, final Application application, final Editor editor) {
        var applicationControl = Session.getModelController(ApplicationControl.class);
        var applicationEditor = applicationControl.getApplicationEditor(application, editor);
        
        if(applicationEditor == null) {
            handleExecutionError(UnknownApplicationEditorException.class, eea, ExecutionErrors.UnknownApplicationEditor.name(),
                    application.getLastDetail().getApplicationName(), editor.getLastDetail().getEditorName());
        }
        
        return applicationEditor;
    }

    public ApplicationEditorUse getApplicationEditorUseByName(final ExecutionErrorAccumulator eea, final Application application,
            final String applicationEditorUseName) {
        var applicationControl = Session.getModelController(ApplicationControl.class);
        var applicationEditorUse = applicationControl.getApplicationEditorUseByName(application, applicationEditorUseName);

        if(applicationEditorUse == null) {
            handleExecutionError(UnknownApplicationEditorUseNameException.class, eea, ExecutionErrors.UnknownApplicationEditorUseName.name(),
                    application.getLastDetail().getApplicationName(), applicationEditorUseName);
        }

        return applicationEditorUse;
    }
    
}
