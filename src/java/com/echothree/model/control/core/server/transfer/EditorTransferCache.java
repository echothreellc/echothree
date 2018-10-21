// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.core.remote.transfer.EditorTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.data.core.server.entity.Editor;
import com.echothree.model.data.core.server.entity.EditorDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class EditorTransferCache
        extends BaseCoreTransferCache<Editor, EditorTransfer> {

    /** Creates a new instance of EditorTransferCache */
    public EditorTransferCache(UserVisit userVisit, CoreControl coreControl) {
        super(userVisit, coreControl);
        
        setIncludeEntityInstance(true);
    }

    public EditorTransfer getEditorTransfer(Editor editor) {
        EditorTransfer editorTransfer = get(editor);

        if(editorTransfer == null) {
            EditorDetail editorDetail = editor.getLastDetail();
            String editorName = editorDetail.getEditorName();
            Boolean hasDimensions = editorDetail.getHasDimensions();
            Integer minimumHeight = editorDetail.getMinimumHeight();
            Integer minimumWidth = editorDetail.getMinimumWidth();
            Integer maximumHeight = editorDetail.getMaximumHeight();
            Integer maximumWidth = editorDetail.getMaximumWidth();
            Integer defaultHeight = editorDetail.getDefaultHeight();
            Integer defaultWidth = editorDetail.getDefaultWidth();
            Boolean isDefault = editorDetail.getIsDefault();
            Integer sortOrder = editorDetail.getSortOrder();
            String description = coreControl.getBestEditorDescription(editor, getLanguage());

            editorTransfer = new EditorTransfer(editorName, hasDimensions, minimumHeight, minimumWidth, maximumHeight, maximumWidth, defaultHeight,
                    defaultWidth, isDefault, sortOrder, description);
            put(editor, editorTransfer);
        }

        return editorTransfer;
    }

}
