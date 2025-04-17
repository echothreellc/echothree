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

import com.echothree.model.control.core.common.exception.UnknownEditorNameException;
import com.echothree.model.control.core.server.control.EditorControl;
import com.echothree.model.data.core.server.entity.Editor;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class EditorLogic
        extends BaseLogic {

    private EditorLogic() {
        super();
    }
    
    private static class EditorLogicHolder {
        static EditorLogic instance = new EditorLogic();
    }
    
    public static EditorLogic getInstance() {
        return EditorLogicHolder.instance;
    }

    public Editor getEditorByName(final ExecutionErrorAccumulator eea, final String editorName) {
        var editorControl = Session.getModelController(EditorControl.class);
        var editor = editorControl.getEditorByName(editorName);

        if(editor == null) {
            handleExecutionError(UnknownEditorNameException.class, eea, ExecutionErrors.UnknownEditorName.name(), editorName);
        }

        return editor;
    }
    
}
