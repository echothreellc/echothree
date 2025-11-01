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

import com.echothree.control.user.party.common.spec.LanguageSpec;
import com.echothree.control.user.party.common.spec.LanguageUuid;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.exception.UnknownLanguageIsoNameException;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class LanguageLogic
        extends BaseLogic {

    protected LanguageLogic() {
        super();
    }

    public static LanguageLogic getInstance() {
        return CDI.current().select(LanguageLogic.class).get();
    }
    
    public Language getLanguageByName(final ExecutionErrorAccumulator eea, final String languageIsoName,
            final EntityPermission entityPermission) {
        var partyControl = Session.getModelController(PartyControl.class);
        var language = partyControl.getLanguageByIsoName(languageIsoName, entityPermission);

        if(language == null) {
            handleExecutionError(UnknownLanguageIsoNameException.class, eea, ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
        }

        return language;
    }
    
    public Language getLanguageByName(final ExecutionErrorAccumulator eea, final String name) {
        return getLanguageByName(eea, name, EntityPermission.READ_ONLY);
    }
    
    public Language getLanguageByNameForUpdate(final ExecutionErrorAccumulator eea, final String name) {
        return getLanguageByName(eea, name, EntityPermission.READ_WRITE);
    }
    
    public Language getLanguageByUuid(final ExecutionErrorAccumulator eea, final String uuid, final EntityPermission entityPermission) {
        Language projectLanguage = null;
        
        var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, (String)null, uuid,
                ComponentVendors.ECHO_THREE.name(), EntityTypes.Language.name());

        if(eea == null || !eea.hasExecutionErrors()) {
            var partyControl = Session.getModelController(PartyControl.class);
            
            projectLanguage = partyControl.getLanguageByEntityInstance(entityInstance, entityPermission);
        }

        return projectLanguage;
    }
    
    public Language getLanguageByUuid(final ExecutionErrorAccumulator eea, final String uuid) {
        return getLanguageByUuid(eea, uuid, EntityPermission.READ_ONLY);
    }
    
    public Language getLanguageByUuidForUpdate(final ExecutionErrorAccumulator eea, final String uuid) {
        return getLanguageByUuid(eea, uuid, EntityPermission.READ_WRITE);
    }
    
    public Language getLanguage(final ExecutionErrorAccumulator eea, final LanguageSpec spec, final LanguageUuid uuid,
            final EntityPermission entityPermission) {
        Language language = null;
        var languageIsoName = spec.getLanguageIsoName();
        var languageUuid = uuid.getLanguageUuid();
        var parameterCount = (languageIsoName == null ? 0 : 1) + (languageUuid == null ? 0 : 1);

        if (parameterCount == 1) {
            language = languageIsoName == null
                    ? getLanguageByUuid(eea, languageUuid, entityPermission)
                    : getLanguageByName(eea, languageIsoName, entityPermission);
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }
        
        return language;
    }
    
    public Language getLanguage(final ExecutionErrorAccumulator eea, final LanguageSpec spec, final LanguageUuid uuid) {
        return getLanguage(eea, spec, uuid, EntityPermission.READ_ONLY);
    }
    
    public Language getLanguageForUpdate(final ExecutionErrorAccumulator eea, final LanguageSpec spec, final LanguageUuid uuid) {
        return getLanguage(eea, spec, uuid, EntityPermission.READ_WRITE);
    }
    
}
