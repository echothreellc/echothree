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

package com.echothree.model.control.contact.server.transfer;

import com.echothree.model.control.contact.common.transfer.ContactPostalAddressTransfer;
import com.echothree.model.control.contact.common.workflow.PostalAddressStatusConstants;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.contact.server.entity.ContactPostalAddress;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ContactPostalAddressTransferCache
        extends BaseContactTransferCache<ContactPostalAddress, ContactPostalAddressTransfer> {

    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of ContactPostalAddressTransferCache */
    public ContactPostalAddressTransferCache(ContactControl contactControl) {
        super(contactControl);
    }
    
    public ContactPostalAddressTransfer getContactPostalAddressTransfer(ContactPostalAddress contactPostalAddress) {
        var contactPostalAddressTransfer = get(contactPostalAddress);
        
        if(contactPostalAddressTransfer == null) {
            var geoControl = Session.getModelController(GeoControl.class);
            var partyControl = Session.getModelController(PartyControl.class);
            var personalTitle = contactPostalAddress.getPersonalTitle();
            var personalTitleTransfer = personalTitle == null? null: partyControl.getPersonalTitleTransfer(userVisit, personalTitle);
            var firstName = contactPostalAddress.getFirstName();
            var middleName = contactPostalAddress.getMiddleName();
            var lastName = contactPostalAddress.getLastName();
            var nameSuffix = contactPostalAddress.getNameSuffix();
            var nameSuffixTransfer = nameSuffix == null? null: partyControl.getNameSuffixTransfer(userVisit, nameSuffix);
            var companyName = contactPostalAddress.getCompanyName();
            var attention = contactPostalAddress.getAttention();
            var address1 = contactPostalAddress.getAddress1();
            var address2 = contactPostalAddress.getAddress2();
            var address3 = contactPostalAddress.getAddress3();
            var countyGeoCode = contactPostalAddress.getCountyGeoCode();
            var countyGeoCodeTransfer = countyGeoCode == null? null: geoControl.getCountyTransfer(userVisit, countyGeoCode);
            var countryGeoCodeTransfer = geoControl.getCountryTransfer(userVisit, contactPostalAddress.getCountryGeoCode());
            var isCommercial = contactPostalAddress.getIsCommercial();

            var city = contactPostalAddress.getCity();
            var cityGeoCode = contactPostalAddress.getCityGeoCode();
            var cityGeoCodeTransfer = cityGeoCode == null? null: geoControl.getCityTransfer(userVisit, cityGeoCode);
            
            if(city == null && cityGeoCode != null) {
                city = geoControl.getBestGeoCodeDescription(cityGeoCode, getLanguage(userVisit));
                
                if(city == null) {
                    city = geoControl.getAliasForCity(cityGeoCode);
                }
            }

            var state = contactPostalAddress.getState();
            var stateGeoCode = contactPostalAddress.getStateGeoCode();
            var stateGeoCodeTransfer = stateGeoCode == null? null: geoControl.getStateTransfer(userVisit, stateGeoCode);
            
            if(state == null && stateGeoCode != null) {
                state = geoControl.getBestGeoCodeDescription(stateGeoCode, getLanguage(userVisit));
                
                if(state == null) {
                    state = geoControl.getAliasForState(stateGeoCode);
                }
            }

            var postalCode = contactPostalAddress.getPostalCode();
            var postalCodeGeoCode = contactPostalAddress.getPostalCodeGeoCode();
            var postalCodeGeoCodeTransfer = postalCodeGeoCode == null? null: geoControl.getPostalCodeTransfer(userVisit, postalCodeGeoCode);
            
            if(postalCode == null && postalCodeGeoCode != null) {
                postalCode = geoControl.getBestGeoCodeDescription(postalCodeGeoCode, getLanguage(userVisit));
                
                if(postalCode == null) {
                    postalCode = geoControl.getAliasForPostalCode(postalCodeGeoCode);
                }
            }

            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(contactPostalAddress.getContactMechanismPK());
            var postalAddressStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    PostalAddressStatusConstants.Workflow_POSTAL_ADDRESS_STATUS, entityInstance);
            
            contactPostalAddressTransfer = new ContactPostalAddressTransfer(personalTitleTransfer, firstName, middleName, lastName,
                    nameSuffixTransfer, companyName, attention, address1, address2, address3, city, cityGeoCodeTransfer, countyGeoCodeTransfer,
                    state, stateGeoCodeTransfer, postalCode, postalCodeGeoCodeTransfer, countryGeoCodeTransfer, isCommercial,
                    postalAddressStatusTransfer);
            put(userVisit, contactPostalAddress, contactPostalAddressTransfer);
        }
        
        return contactPostalAddressTransfer;
    }
    
}
