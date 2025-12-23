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

package com.echothree.control.user.icon.common;

import com.echothree.control.user.icon.common.form.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface IconService
        extends IconForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // --------------------------------------------------------------------------------
    //   Icon Usage Types
    // --------------------------------------------------------------------------------
    
    CommandResult createIconUsageType(UserVisitPK userVisitPK, CreateIconUsageTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Icon Usage Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createIconUsageTypeDescription(UserVisitPK userVisitPK, CreateIconUsageTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Icons
    // --------------------------------------------------------------------------------
    
    CommandResult getIconChoices(UserVisitPK userVisitPK, GetIconChoicesForm form);
    
}
