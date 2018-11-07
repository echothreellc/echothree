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

package com.echothree.model.control.contact.server.transfer;

import com.echothree.model.control.contact.common.transfer.ContactPostalAddressTransfer;
import com.echothree.model.control.contact.server.ContactControl;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.geo.common.transfer.CityTransfer;
import com.echothree.model.control.geo.common.transfer.CountryTransfer;
import com.echothree.model.control.geo.common.transfer.CountyTransfer;
import com.echothree.model.control.geo.common.transfer.PostalCodeTransfer;
import com.echothree.model.control.geo.common.transfer.StateTransfer;
import com.echothree.model.control.geo.server.GeoControl;
import com.echothree.model.control.party.common.transfer.NameSuffixTransfer;
import com.echothree.model.control.party.common.transfer.PersonalTitleTransfer;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.contact.common.workflow.PostalAddressStatusConstants;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.contact.server.entity.ContactPostalAddress;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.party.server.entity.NameSuffix;
import com.echothree.model.data.party.server.entity.PersonalTitle;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ContactPostalAddressTransferCache
        extends BaseContactTransferCache<ContactPostalAddress, ContactPostalAddressTransfer> {
    
    CoreControl coreControl;
    WorkflowControl workflowControl;
    
    /** Creates a new instance of ContactPostalAddressTransferCache */
    public ContactPostalAddressTransferCache(UserVisit userVisit, ContactControl contactControl) {
        super(userVisit, contactControl);
        
        coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
    }
    
    public ContactPostalAddressTransfer getContactPostalAddressTransfer(ContactPostalAddress contactPostalAddress) {
        ContactPostalAddressTransfer contactPostalAddressTransfer = get(contactPostalAddress);
        
        if(contactPostalAddressTransfer == null) {
            GeoControl geoControl = (GeoControl)Session.getModelController(GeoControl.class);
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            PersonalTitle personalTitle = contactPostalAddress.getPersonalTitle();
            PersonalTitleTransfer personalTitleTransfer = personalTitle == null? null: partyControl.getPersonalTitleTransfer(userVisit, personalTitle);
            String firstName = contactPostalAddress.getFirstName();
            String middleName = contactPostalAddress.getMiddleName();
            String lastName = contactPostalAddress.getLastName();
            NameSuffix nameSuffix = contactPostalAddress.getNameSuffix();
            NameSuffixTransfer nameSuffixTransfer = nameSuffix == null? null: partyControl.getNameSuffixTransfer(userVisit, nameSuffix);
            String companyName = contactPostalAddress.getCompanyName();
            String attention = contactPostalAddress.getAttention();
            String address1 = contactPostalAddress.getAddress1();
            String address2 = contactPostalAddress.getAddress2();
            String address3 = contactPostalAddress.getAddress3();
            GeoCode countyGeoCode = contactPostalAddress.getCountyGeoCode();
            CountyTransfer countyGeoCodeTransfer = countyGeoCode == null? null: geoControl.getCountyTransfer(userVisit, countyGeoCode);
            CountryTransfer countryGeoCodeTransfer = geoControl.getCountryTransfer(userVisit, contactPostalAddress.getCountryGeoCode());
            Boolean isCommercial = contactPostalAddress.getIsCommercial();
            
            String city = contactPostalAddress.getCity();
            GeoCode cityGeoCode = contactPostalAddress.getCityGeoCode();
            CityTransfer cityGeoCodeTransfer = cityGeoCode == null? null: geoControl.getCityTransfer(userVisit, cityGeoCode);
            
            if(city == null && cityGeoCode != null) {
                city = geoControl.getBestGeoCodeDescription(cityGeoCode, getLanguage());
                
                if(city == null) {
                    city = geoControl.getAliasForCity(cityGeoCode);
                }
            }
            
            String state = contactPostalAddress.getState();
            GeoCode stateGeoCode = contactPostalAddress.getStateGeoCode();
            StateTransfer stateGeoCodeTransfer = stateGeoCode == null? null: geoControl.getStateTransfer(userVisit, stateGeoCode);
            
            if(state == null && stateGeoCode != null) {
                state = geoControl.getBestGeoCodeDescription(stateGeoCode, getLanguage());
                
                if(state == null) {
                    state = geoControl.getAliasForState(stateGeoCode);
                }
            }
            
            String postalCode = contactPostalAddress.getPostalCode();
            GeoCode postalCodeGeoCode = contactPostalAddress.getPostalCodeGeoCode();
            PostalCodeTransfer postalCodeGeoCodeTransfer = postalCodeGeoCode == null? null: geoControl.getPostalCodeTransfer(userVisit, postalCodeGeoCode);
            
            if(postalCode == null && postalCodeGeoCode != null) {
                postalCode = geoControl.getBestGeoCodeDescription(postalCodeGeoCode, getLanguage());
                
                if(postalCode == null) {
                    postalCode = geoControl.getAliasForPostalCode(postalCodeGeoCode);
                }
            }
            
            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(contactPostalAddress.getContactMechanismPK());
            WorkflowEntityStatusTransfer postalAddressStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    PostalAddressStatusConstants.Workflow_POSTAL_ADDRESS_STATUS, entityInstance);
            
            contactPostalAddressTransfer = new ContactPostalAddressTransfer(personalTitleTransfer, firstName, middleName, lastName,
                    nameSuffixTransfer, companyName, attention, address1, address2, address3, city, cityGeoCodeTransfer, countyGeoCodeTransfer,
                    state, stateGeoCodeTransfer, postalCode, postalCodeGeoCodeTransfer, countryGeoCodeTransfer, isCommercial,
                    postalAddressStatusTransfer);
            put(contactPostalAddress, contactPostalAddressTransfer);
        }
        
        return contactPostalAddressTransfer;
    }
    
}
