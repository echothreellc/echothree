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

package com.echothree.model.control.party.server.logic;

import com.echothree.model.control.party.common.exception.UnknownDateTimeFormatNameException;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class DateTimeFormatLogic
        extends BaseLogic {

    protected DateTimeFormatLogic() {
        super();
    }

    public static DateTimeFormatLogic getInstance() {
        return CDI.current().select(DateTimeFormatLogic.class).get();
    }
    
    public DateTimeFormat getDateTimeFormatByName(final ExecutionErrorAccumulator eea, final String dateTimeFormatName) {
        var partyControl = Session.getModelController(PartyControl.class);
        var dateTimeFormat = partyControl.getDateTimeFormatByName(dateTimeFormatName);

        if(dateTimeFormat == null) {
            handleExecutionError(UnknownDateTimeFormatNameException.class, eea, ExecutionErrors.UnknownDateTimeFormatName.name(), dateTimeFormatName);
        }

        return dateTimeFormat;
    }
    
}
