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

import com.echothree.model.control.core.common.transfer.ApplicationEditorTransfer;
import com.echothree.model.control.core.common.transfer.ApplicationEditorUseTransfer;
import com.echothree.model.control.core.common.transfer.PartyApplicationEditorUseTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.core.server.entity.ApplicationEditor;
import com.echothree.model.data.core.server.entity.PartyApplicationEditorUse;
import com.echothree.model.data.core.server.entity.PartyApplicationEditorUseDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PartyApplicationEditorUseTransferCache
        extends BaseCoreTransferCache<PartyApplicationEditorUse, PartyApplicationEditorUseTransfer> {

    PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);

    /** Creates a new instance of PartyApplicationEditorUseTransferCache */
    public PartyApplicationEditorUseTransferCache(UserVisit userVisit, CoreControl coreControl) {
        super(userVisit, coreControl);
        
        setIncludeEntityInstance(true);
    }

    public PartyApplicationEditorUseTransfer getPartyApplicationEditorUseTransfer(PartyApplicationEditorUse partyApplicationEditorUse) {
        PartyApplicationEditorUseTransfer partyApplicationEditorUseTransfer = get(partyApplicationEditorUse);

        if(partyApplicationEditorUseTransfer == null) {
            PartyApplicationEditorUseDetail partyApplicationEditorUseDetail = partyApplicationEditorUse.getLastDetail();
            PartyTransfer partyTransfer = partyControl.getPartyTransfer(userVisit, partyApplicationEditorUseDetail.getParty());
            ApplicationEditorUseTransfer applicationEditorUseTransfer = coreControl.getApplicationEditorUseTransfer(userVisit, partyApplicationEditorUseDetail.getApplicationEditorUse());
            ApplicationEditor applicationEditor = partyApplicationEditorUseDetail.getApplicationEditor();
            ApplicationEditorTransfer applicationEditorTransfer = applicationEditor == null ? null : coreControl.getApplicationEditorTransfer(userVisit, applicationEditor);
            Integer preferredHeight = partyApplicationEditorUseDetail.getPreferredHeight();
            Integer preferredWidth = partyApplicationEditorUseDetail.getPreferredWidth();

            partyApplicationEditorUseTransfer = new PartyApplicationEditorUseTransfer(partyTransfer, applicationEditorUseTransfer, applicationEditorTransfer,
                    preferredHeight, preferredWidth);
            put(partyApplicationEditorUse, partyApplicationEditorUseTransfer);
        }

        return partyApplicationEditorUseTransfer;
    }

}
