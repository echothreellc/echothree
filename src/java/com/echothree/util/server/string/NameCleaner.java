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

package com.echothree.util.server.string;

import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.util.common.string.BaseNameCleaner;
import com.echothree.util.server.persistence.Session;

public final class NameCleaner
        extends BaseNameCleaner {

    /** Creates a new instance of NameCleaner */
    public NameCleaner() {
        var partyControl = Session.getModelController(PartyControl.class);

        loadPersonalTitles(partyControl);
        loadNameSuffixes(partyControl);
    }

    protected final void loadPersonalTitles(PartyControl partyControl) {
        setupPersonalTitles(partyControl.getPersonalTitleChoices(null, false));
    }

    protected void loadNameSuffixes(PartyControl partyControl) {
        setupNameSuffixes(partyControl.getNameSuffixChoices(null, false));
    }

}