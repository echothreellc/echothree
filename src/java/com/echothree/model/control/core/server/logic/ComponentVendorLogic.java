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

package com.echothree.model.control.core.server.logic;

import com.echothree.model.control.core.common.exception.UnknownComponentVendorNameException;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class ComponentVendorLogic
        extends BaseLogic {

    private ComponentVendorLogic() {
        super();
    }

    private static class ComponentVendorLogicHolder {
        static ComponentVendorLogic instance = new ComponentVendorLogic();
    }

    public static ComponentVendorLogic getInstance() {
        return ComponentVendorLogicHolder.instance;
    }
    
    public ComponentVendor getComponentVendorByName(final ExecutionErrorAccumulator eea, final String componentVendorName) {
        CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        ComponentVendor componentVendor = coreControl.getComponentVendorByName(componentVendorName);

        if(componentVendor == null) {
            handleExecutionError(UnknownComponentVendorNameException.class, eea, ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
        }

        return componentVendor;
    }

}
