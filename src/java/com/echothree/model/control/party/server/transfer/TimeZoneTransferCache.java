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

package com.echothree.model.control.party.server.transfer;

import com.echothree.model.control.party.common.PartyProperties;
import com.echothree.model.control.party.common.transfer.TimeZoneTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.server.persistence.Session;

public class TimeZoneTransferCache
        extends BasePartyTransferCache<TimeZone, TimeZoneTransfer> {

    PartyControl partyControl = Session.getModelController(PartyControl.class);

    TransferProperties transferProperties;
    boolean filterJavaTimeZoneName;
    boolean filterUnixTimeZoneName;
    boolean filterIsDefault;
    boolean filterSortOrder;
    boolean filterDescription;
    boolean filterEntityInstance;
    
    /** Creates a new instance of TimeZoneTransferCache */
    public TimeZoneTransferCache() {
        super();

        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            var properties = transferProperties.getProperties(TimeZoneTransfer.class);
            
            if(properties != null) {
                filterJavaTimeZoneName = !properties.contains(PartyProperties.JAVA_TIME_ZONE_NAME);
                filterUnixTimeZoneName = !properties.contains(PartyProperties.UNIX_TIME_ZONE_NAME);
                filterIsDefault = !properties.contains(PartyProperties.IS_DEFAULT);
                filterSortOrder = !properties.contains(PartyProperties.SORT_ORDER);
                filterDescription = !properties.contains(PartyProperties.DESCRIPTION);
                filterEntityInstance = !properties.contains(PartyProperties.ENTITY_INSTANCE);
            }
        }
        
        setIncludeEntityInstance(!filterEntityInstance);
    }

    @Override
    public TimeZoneTransfer getTransfer(TimeZone timeZone) {
        var timeZoneTransfer = get(timeZone);
        
        if(timeZoneTransfer == null) {
            var timeZoneDetail = timeZone.getLastDetail();
            var javaTimeZoneName = filterJavaTimeZoneName ? null : timeZoneDetail.getJavaTimeZoneName();
            var unixTimeZoneName = filterUnixTimeZoneName ? null : timeZoneDetail.getUnixTimeZoneName();
            var isDefault = filterIsDefault ? null : timeZoneDetail.getIsDefault();
            var sortOrder = filterSortOrder ? null : timeZoneDetail.getSortOrder();
            var description = filterDescription ? null : partyControl.getBestTimeZoneDescription(timeZone, getLanguage(userVisit));
            
            timeZoneTransfer = new TimeZoneTransfer(javaTimeZoneName, unixTimeZoneName, isDefault, sortOrder, description);
            put(userVisit, timeZone, timeZoneTransfer);
        }
        
        return timeZoneTransfer;
    }
    
}
