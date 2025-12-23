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

package com.echothree.model.control.offer.server.logic;

import com.echothree.control.user.offer.common.spec.UseUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.offer.common.exception.CannotDeleteUseInUseException;
import com.echothree.model.control.offer.common.exception.DuplicateUseNameException;
import com.echothree.model.control.offer.common.exception.UnknownDefaultUseException;
import com.echothree.model.control.offer.common.exception.UnknownUseNameException;
import com.echothree.model.control.offer.server.control.OfferUseControl;
import com.echothree.model.control.offer.server.control.UseControl;
import com.echothree.model.data.offer.server.entity.Use;
import com.echothree.model.data.offer.server.entity.UseType;
import com.echothree.model.data.offer.server.value.UseDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class UseLogic
        extends BaseLogic {

    protected UseLogic() {
        super();
    }

    public static UseLogic getInstance() {
        return CDI.current().select(UseLogic.class).get();
    }

    public Use createUse(final ExecutionErrorAccumulator eea, final String useName, final UseType useType,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var useControl = Session.getModelController(UseControl.class);
        var use = useControl.getUseByName(useName);

        if(use == null) {
            use = useControl.createUse(useName, useType, isDefault, sortOrder, createdBy);

            if(description != null) {
                useControl.createUseDescription(use, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateUseNameException.class, eea, ExecutionErrors.DuplicateUseName.name(), useName);
        }

        return use;
    }

    public Use getUseByName(final ExecutionErrorAccumulator eea, final String useName,
            final EntityPermission entityPermission) {
        var useControl = Session.getModelController(UseControl.class);
        var use = useControl.getUseByName(useName, entityPermission);

        if(use == null) {
            handleExecutionError(UnknownUseNameException.class, eea, ExecutionErrors.UnknownUseName.name(), useName);
        }

        return use;
    }

    public Use getUseByName(final ExecutionErrorAccumulator eea, final String useName) {
        return getUseByName(eea, useName, EntityPermission.READ_ONLY);
    }

    public Use getUseByNameForUpdate(final ExecutionErrorAccumulator eea, final String useName) {
        return getUseByName(eea, useName, EntityPermission.READ_WRITE);
    }

    public Use getUseByUniversalSpec(final ExecutionErrorAccumulator eea,
            final UseUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        var useControl = Session.getModelController(UseControl.class);
        var useName = universalSpec.getUseName();
        var parameterCount = (useName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);
        Use use = null;

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    use = useControl.getDefaultUse(entityPermission);

                    if(use == null) {
                        handleExecutionError(UnknownDefaultUseException.class, eea, ExecutionErrors.UnknownDefaultUse.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(useName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.Use.name());

                    if(!eea.hasExecutionErrors()) {
                        use = useControl.getUseByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    use = getUseByName(eea, useName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return use;
    }

    public Use getUseByUniversalSpec(final ExecutionErrorAccumulator eea,
            final UseUniversalSpec universalSpec, boolean allowDefault) {
        return getUseByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public Use getUseByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final UseUniversalSpec universalSpec, boolean allowDefault) {
        return getUseByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void updateUseFromValue(UseDetailValue useDetailValue, BasePK updatedBy) {
        var useControl = Session.getModelController(UseControl.class);

        useControl.updateUseFromValue(useDetailValue, updatedBy);
    }

    public void deleteUse(final ExecutionErrorAccumulator eea, final Use use, final BasePK deletedBy) {
        var offerUseControl = Session.getModelController(OfferUseControl.class);

        if(offerUseControl.countOfferUsesByUse(use) == 0) {
            var useControl = Session.getModelController(UseControl.class);

            useControl.deleteUse(use, deletedBy);
        } else {
            handleExecutionError(CannotDeleteUseInUseException.class, eea, ExecutionErrors.CannotDeleteUseInUse.name());
        }
    }
    
}
