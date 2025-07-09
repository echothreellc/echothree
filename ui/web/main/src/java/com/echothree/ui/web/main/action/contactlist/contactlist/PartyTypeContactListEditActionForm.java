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

package com.echothree.ui.web.main.action.contactlist.contactlist;

import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;

@SproutForm(name="PartyTypeContactListEdit")
public class PartyTypeContactListEditActionForm
        extends BaseActionForm {
    
    private String contactListName;
    private String partyTypeName;
    private Boolean addWhenCreated;
    
    public void setContactListName(String contactListName) {
        this.contactListName = contactListName;
    }
    
    public String getContactListName() {
        return contactListName;
    }
    
    public String getPartyTypeName() {
        return partyTypeName;
    }
    
    public void setPartyTypeName(String partyTypeName) {
        this.partyTypeName = partyTypeName;
    }
    
    public Boolean getAddWhenCreated() {
        return addWhenCreated;
    }

    public void setAddWhenCreated(Boolean addWhenCreated) {
        this.addWhenCreated = addWhenCreated;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);

        addWhenCreated = false;
    }

}
