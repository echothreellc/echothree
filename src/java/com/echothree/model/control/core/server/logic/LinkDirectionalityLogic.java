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

package com.echothree.model.control.core.server.logic;

import com.echothree.control.user.core.common.spec.LinkDirectionalityUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.CannotDeleteLinkDirectionalityInUseException;
import com.echothree.model.control.core.common.exception.DuplicateLinkDirectionalityNameException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.common.exception.UnknownDefaultLinkDirectionalityException;
import com.echothree.model.control.core.common.exception.UnknownLinkDirectionalityNameException;
import com.echothree.model.control.core.server.control.LinkDirectionalityControl;
import com.echothree.model.data.core.server.entity.LinkDirectionality;
import com.echothree.model.data.core.server.value.LinkDirectionalityDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.model.control.core.server.control.LinkTypeControl;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class LinkDirectionalityLogic
    extends BaseLogic {

    @Inject
    LinkDirectionalityControl linkDirectionalityControl;

    @Inject
    LinkTypeControl linkTypeControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    protected LinkDirectionalityLogic() {
        super();
    }

    public LinkDirectionality createLinkDirectionality(final ExecutionErrorAccumulator eea, final String linkDirectionalityName,
            final Boolean isDefault, final Integer sortOrder,
            final Language language, final String description, final BasePK createdBy) {
        var linkDirectionality = linkDirectionalityControl.getLinkDirectionalityByName(linkDirectionalityName);

        if(linkDirectionality == null) {
            linkDirectionality = linkDirectionalityControl.createLinkDirectionality(linkDirectionalityName,
                    isDefault, sortOrder, createdBy);

            if(description != null) {
                linkDirectionalityControl.createLinkDirectionalityDescription(linkDirectionality, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateLinkDirectionalityNameException.class, eea, ExecutionErrors.DuplicateLinkDirectionalityName.name(), linkDirectionalityName);
        }

        return linkDirectionality;
    }

    public LinkDirectionality getLinkDirectionalityByName(final ExecutionErrorAccumulator eea, final String linkDirectionalityName,
            final EntityPermission entityPermission) {
        var linkDirectionality = linkDirectionalityControl.getLinkDirectionalityByName(linkDirectionalityName, entityPermission);

        if(linkDirectionality == null) {
            handleExecutionError(UnknownLinkDirectionalityNameException.class, eea, ExecutionErrors.UnknownLinkDirectionalityName.name(), linkDirectionalityName);
        }

        return linkDirectionality;
    }

    public LinkDirectionality getLinkDirectionalityByName(final ExecutionErrorAccumulator eea, final String linkDirectionalityName) {
        return getLinkDirectionalityByName(eea, linkDirectionalityName, EntityPermission.READ_ONLY);
    }

    public LinkDirectionality getLinkDirectionalityByNameForUpdate(final ExecutionErrorAccumulator eea, final String linkDirectionalityName) {
        return getLinkDirectionalityByName(eea, linkDirectionalityName, EntityPermission.READ_WRITE);
    }

    public LinkDirectionality getLinkDirectionalityByUniversalSpec(final ExecutionErrorAccumulator eea,
            final LinkDirectionalityUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        LinkDirectionality linkDirectionality = null;
        var linkDirectionalityName = universalSpec.getLinkDirectionalityName();
        var parameterCount = (linkDirectionalityName == null ? 0 : 1) + entityInstanceLogic.countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    linkDirectionality = linkDirectionalityControl.getDefaultLinkDirectionality(entityPermission);

                    if(linkDirectionality == null) {
                        handleExecutionError(UnknownDefaultLinkDirectionalityException.class, eea, ExecutionErrors.UnknownDefaultLinkDirectionality.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(linkDirectionalityName == null) {
                    var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.LinkDirectionality.name());

                    if(eea == null || !eea.hasExecutionErrors()) {
                        linkDirectionality = linkDirectionalityControl.getLinkDirectionalityByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    linkDirectionality = getLinkDirectionalityByName(eea, linkDirectionalityName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return linkDirectionality;
    }

    public LinkDirectionality getLinkDirectionalityByUniversalSpec(final ExecutionErrorAccumulator eea,
            final LinkDirectionalityUniversalSpec universalSpec, boolean allowDefault) {
        return getLinkDirectionalityByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public LinkDirectionality getLinkDirectionalityByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final LinkDirectionalityUniversalSpec universalSpec, boolean allowDefault) {
        return getLinkDirectionalityByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void updateLinkDirectionalityFromValue(final LinkDirectionalityDetailValue linkDirectionalityDetailValue,
            final BasePK updatedBy) {
        linkDirectionalityControl.updateLinkDirectionalityFromValue(linkDirectionalityDetailValue, updatedBy);
    }

    public void deleteLinkDirectionality(final ExecutionErrorAccumulator eea, final LinkDirectionality linkDirectionality,
            final BasePK deletedBy) {
        if(linkTypeControl.countLinkTypes(linkTypeControl.getLinkTypeFilter(null, null, null, null, linkDirectionality, null)) != 0) {
            handleExecutionError(CannotDeleteLinkDirectionalityInUseException.class, eea, ExecutionErrors.CannotDeleteLinkDirectionalityInUse.name(), linkDirectionality.getLastDetail().getLinkDirectionalityName());
        } else {
            linkDirectionalityControl.deleteLinkDirectionality(linkDirectionality, deletedBy);
        }
    }

}
