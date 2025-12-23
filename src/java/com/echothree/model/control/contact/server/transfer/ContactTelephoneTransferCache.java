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

package com.echothree.model.control.contact.server.transfer;

import com.echothree.model.control.contact.common.transfer.ContactTelephoneTransfer;
import com.echothree.model.control.contact.common.workflow.TelephoneStatusConstants;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.contact.server.entity.ContactTelephone;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ContactTelephoneTransferCache
        extends BaseContactTransferCache<ContactTelephone, ContactTelephoneTransfer> {
    
    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);

    /** Creates a new instance of ContactTelephoneTransferCache */
    protected ContactTelephoneTransferCache() {
        super();
    }
    
    public ContactTelephoneTransfer getContactTelephoneTransfer(UserVisit userVisit, ContactTelephone contactTelephone) {
        var contactTelephoneTransfer = get(contactTelephone);
        
        if(contactTelephoneTransfer == null) {
            var geoControl = Session.getModelController(GeoControl.class);
            var countryGeoCode = geoControl.getCountryTransfer(userVisit, contactTelephone.getCountryGeoCode());
            var areaCode = contactTelephone.getAreaCode();
            var telephoneNumber = contactTelephone.getTelephoneNumber();
            var telephoneExtension = contactTelephone.getTelephoneExtension();

            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(contactTelephone.getContactMechanismPK());
            var telephoneStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    TelephoneStatusConstants.Workflow_TELEPHONE_STATUS, entityInstance);
            
            contactTelephoneTransfer = new ContactTelephoneTransfer(countryGeoCode, areaCode, telephoneNumber, telephoneExtension,
                    telephoneStatusTransfer);
            put(userVisit, contactTelephone, contactTelephoneTransfer);
        }
        
        return contactTelephoneTransfer;
    }
    
}
