// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.model.control.communication.server.transfer;

import com.echothree.model.control.communication.common.CommunicationConstants;
import com.echothree.model.control.communication.common.CommunicationOptions;
import com.echothree.model.control.communication.common.transfer.CommunicationEmailSourceTransfer;
import com.echothree.model.control.communication.common.transfer.CommunicationSourceTransfer;
import com.echothree.model.control.communication.common.transfer.CommunicationSourceTypeTransfer;
import com.echothree.model.control.communication.server.control.CommunicationControl;
import com.echothree.model.data.communication.server.entity.CommunicationSource;
import com.echothree.model.data.communication.server.entity.CommunicationSourceDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import java.util.Set;

public class CommunicationSourceTransferCache
        extends BaseCommunicationTransferCache<CommunicationSource, CommunicationSourceTransfer> {
    
    boolean includeRelated;
    
    /** Creates a new instance of CommunicationSourceTransferCache */
    public CommunicationSourceTransferCache(UserVisit userVisit, CommunicationControl communicationControl) {
        super(userVisit, communicationControl);
        
        var options = session.getOptions();
        if(options != null) {
            includeRelated = options.contains(CommunicationOptions.CommunicationSourceIncludeRelated);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public CommunicationSourceTransfer getCommunicationSourceTransfer(CommunicationSource communicationSource) {
        CommunicationSourceTransfer communicationSourceTransfer = get(communicationSource);
        
        if(communicationSourceTransfer == null) {
            CommunicationSourceDetail communicationSourceDetail = communicationSource.getLastDetail();
            String communicationSourceName = communicationSourceDetail.getCommunicationSourceName();
            CommunicationSourceTypeTransfer communicationSourceTypeTransfer = communicationControl.getCommunicationSourceTypeTransfer(userVisit,
                    communicationSourceDetail.getCommunicationSourceType());
            Integer sortOrder = communicationSourceDetail.getSortOrder();
            String description = communicationControl.getBestCommunicationSourceDescription(communicationSource, getLanguage());
            CommunicationEmailSourceTransfer communicationEmailSourceTransfer = null;
            
            if(includeRelated) {
                String communicationSourceTypeName = communicationSourceTypeTransfer.getCommunicationSourceTypeName();
                
                if(communicationSourceTypeName.equals(CommunicationConstants.CommunicationSourceType_EMAIL)) {
                    communicationEmailSourceTransfer = communicationControl.getCommunicationEmailSourceTransfer(userVisit,
                            communicationControl.getCommunicationEmailSource(communicationSource));
                }
            }
            
            communicationSourceTransfer = new CommunicationSourceTransfer(communicationSourceName, communicationSourceTypeTransfer, 
                    sortOrder, description, communicationEmailSourceTransfer);
            put(communicationSource, communicationSourceTransfer);
        }
        
        return communicationSourceTransfer;
    }
    
}
