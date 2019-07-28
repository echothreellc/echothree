// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.model.control.selector.server.logic;

import com.echothree.model.control.selector.common.exception.UnknownSelectorKindNameException;
import com.echothree.model.control.selector.server.SelectorControl;
import com.echothree.model.data.selector.server.entity.SelectorKind;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class SelectorKindLogic
        extends BaseLogic {

    private SelectorKindLogic() {
        super();
    }
    
    private static class SelectorKindLogicHolder {
        static SelectorKindLogic instance = new SelectorKindLogic();
    }
    
    public static SelectorKindLogic getInstance() {
        return SelectorKindLogicHolder.instance;
    }

    public SelectorKind getSelectorKindByName(final ExecutionErrorAccumulator eea, final String selectorKindName) {
        var selectorControl = (SelectorControl)Session.getModelController(SelectorControl.class);
        var selectorKind = selectorControl.getSelectorKindByName(selectorKindName);

        if(selectorKind == null) {
            handleExecutionError(UnknownSelectorKindNameException.class, eea, ExecutionErrors.UnknownSelectorKindName.name(), selectorKindName);
        }

        return selectorKind;
    }

}
