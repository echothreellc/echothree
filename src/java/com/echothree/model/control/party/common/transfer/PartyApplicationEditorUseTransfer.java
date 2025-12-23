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

package com.echothree.model.control.party.common.transfer;

import com.echothree.model.control.core.common.transfer.ApplicationEditorTransfer;
import com.echothree.model.control.core.common.transfer.ApplicationEditorUseTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class PartyApplicationEditorUseTransfer
        extends BaseTransfer {
    
    private PartyTransfer party;
    private ApplicationEditorUseTransfer applicationEditorUse;
    private ApplicationEditorTransfer applicationEditor;
    private Integer preferredHeight;
    private Integer preferredWidth;
    
    /** Creates a new instance of PartyApplicationEditorUseTransfer */
    public PartyApplicationEditorUseTransfer(PartyTransfer party, ApplicationEditorUseTransfer applicationEditorUse,
            ApplicationEditorTransfer applicationEditor, Integer preferredHeight, Integer preferredWidth) {
        this.party = party;
        this.applicationEditorUse = applicationEditorUse;
        this.applicationEditor = applicationEditor;
        this.preferredHeight = preferredHeight;
        this.preferredWidth = preferredWidth;
    }

    /**
     * Returns the party.
     * @return the party
     */
    public PartyTransfer getParty() {
        return party;
    }

    /**
     * Sets the party.
     * @param party the party to set
     */
    public void setParty(PartyTransfer party) {
        this.party = party;
    }

    /**
     * Returns the applicationEditorUse.
     * @return the applicationEditorUse
     */
    public ApplicationEditorUseTransfer getApplicationEditorUse() {
        return applicationEditorUse;
    }

    /**
     * Sets the applicationEditorUse.
     * @param applicationEditorUse the applicationEditorUse to set
     */
    public void setApplicationEditorUse(ApplicationEditorUseTransfer applicationEditorUse) {
        this.applicationEditorUse = applicationEditorUse;
    }

    /**
     * Returns the applicationEditor.
     * @return the applicationEditor
     */
    public ApplicationEditorTransfer getApplicationEditor() {
        return applicationEditor;
    }

    /**
     * Sets the applicationEditor.
     * @param applicationEditor the applicationEditor to set
     */
    public void setApplicationEditor(ApplicationEditorTransfer applicationEditor) {
        this.applicationEditor = applicationEditor;
    }

    /**
     * Returns the preferredHeight.
     * @return the preferredHeight
     */
    public Integer getPreferredHeight() {
        return preferredHeight;
    }

    /**
     * Sets the preferredHeight.
     * @param preferredHeight the preferredHeight to set
     */
    public void setPreferredHeight(Integer preferredHeight) {
        this.preferredHeight = preferredHeight;
    }

    /**
     * Returns the preferredWidth.
     * @return the preferredWidth
     */
    public Integer getPreferredWidth() {
        return preferredWidth;
    }

    /**
     * Sets the preferredWidth.
     * @param preferredWidth the preferredWidth to set
     */
    public void setPreferredWidth(Integer preferredWidth) {
        this.preferredWidth = preferredWidth;
    }

}
