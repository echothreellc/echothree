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

package com.echothree.control.user.icon.server;

import com.echothree.control.user.icon.common.IconRemote;
import com.echothree.control.user.icon.common.form.*;
import com.echothree.control.user.icon.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

@Stateless
public class IconBean
        extends IconFormsImpl
        implements IconRemote, IconLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "IconBean is alive!";
    }
    
    // --------------------------------------------------------------------------------
    //   Icon Usage Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createIconUsageType(UserVisitPK userVisitPK, CreateIconUsageTypeForm form) {
        return CDI.current().select(CreateIconUsageTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Icon Usage Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createIconUsageTypeDescription(UserVisitPK userVisitPK, CreateIconUsageTypeDescriptionForm form) {
        return CDI.current().select(CreateIconUsageTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Icons
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult getIconChoices(UserVisitPK userVisitPK, GetIconChoicesForm form) {
        return CDI.current().select(GetIconChoicesCommand.class).get().run(userVisitPK, form);
    }
    
}
