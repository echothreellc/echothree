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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.core.common.transfer.PartyApplicationEditorUseTransfer;
import com.echothree.model.control.core.server.control.ApplicationControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.core.server.entity.PartyApplicationEditorUse;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PartyApplicationEditorUseTransferCache
        extends BaseCoreTransferCache<PartyApplicationEditorUse, PartyApplicationEditorUseTransfer> {

    PartyControl partyControl = Session.getModelController(PartyControl.class);
    ApplicationControl applicationControl = Session.getModelController(ApplicationControl.class);

    /** Creates a new instance of PartyApplicationEditorUseTransferCache */
    public PartyApplicationEditorUseTransferCache(UserVisit userVisit) {
        super(userVisit);
        
        setIncludeEntityInstance(true);
    }

    public PartyApplicationEditorUseTransfer getPartyApplicationEditorUseTransfer(PartyApplicationEditorUse partyApplicationEditorUse) {
        var partyApplicationEditorUseTransfer = get(partyApplicationEditorUse);

        if(partyApplicationEditorUseTransfer == null) {
            var partyApplicationEditorUseDetail = partyApplicationEditorUse.getLastDetail();
            var partyTransfer = partyControl.getPartyTransfer(userVisit, partyApplicationEditorUseDetail.getParty());
            var applicationEditorUseTransfer = applicationControl.getApplicationEditorUseTransfer(userVisit, partyApplicationEditorUseDetail.getApplicationEditorUse());
            var applicationEditor = partyApplicationEditorUseDetail.getApplicationEditor();
            var applicationEditorTransfer = applicationEditor == null ? null : applicationControl.getApplicationEditorTransfer(userVisit, applicationEditor);
            var preferredHeight = partyApplicationEditorUseDetail.getPreferredHeight();
            var preferredWidth = partyApplicationEditorUseDetail.getPreferredWidth();

            partyApplicationEditorUseTransfer = new PartyApplicationEditorUseTransfer(partyTransfer, applicationEditorUseTransfer, applicationEditorTransfer,
                    preferredHeight, preferredWidth);
            put(partyApplicationEditorUse, partyApplicationEditorUseTransfer);
        }

        return partyApplicationEditorUseTransfer;
    }

}
