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

package com.echothree.control.user.content.server.command;

import com.echothree.control.user.content.common.form.GetContentWebAddressForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.data.content.server.entity.ContentWebAddress;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetContentWebAddressCommand
        extends BaseSingleEntityCommand<ContentWebAddress, GetContentWebAddressForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentWebAddressName", FieldType.HOST_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetContentWebAddressCommand */
    public GetContentWebAddressCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected ContentWebAddress getEntity() {
        var contentControl = Session.getModelController(ContentControl.class);
        var contentWebAddressName = form.getContentWebAddressName();
        var contentWebAddress = contentControl.getContentWebAddressByName(contentWebAddressName);
        
        if(contentWebAddress != null) {
            sendEvent(contentWebAddress.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        } else {
            addExecutionError(ExecutionErrors.UnknownContentWebAddressName.name(), contentWebAddressName);
        }
        
        return contentWebAddress;
    }
    
    @Override
    protected BaseResult getResult(ContentWebAddress contentWebAddress) {
        var result = ContentResultFactory.getGetContentWebAddressResult();
        
        if(contentWebAddress != null) {
            var contentControl = Session.getModelController(ContentControl.class);
            
            result.setContentWebAddress(contentControl.getContentWebAddressTransfer(getUserVisit(), contentWebAddress));
        }
        
        return result;
    }
    
}
