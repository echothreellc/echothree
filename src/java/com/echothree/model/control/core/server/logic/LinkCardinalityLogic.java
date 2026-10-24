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

import com.echothree.control.user.core.common.spec.LinkCardinalityUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.CannotDeleteLinkCardinalityInUseException;
import com.echothree.model.control.core.common.exception.DuplicateLinkCardinalityNameException;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.common.exception.UnknownDefaultLinkCardinalityException;
import com.echothree.model.control.core.common.exception.UnknownLinkCardinalityNameException;
import com.echothree.model.control.core.server.control.LinkCardinalityControl;
import com.echothree.model.control.core.server.control.LinkTypeControl;
import com.echothree.model.data.core.server.entity.LinkCardinality;
import com.echothree.model.data.core.server.value.LinkCardinalityDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class LinkCardinalityLogic
        extends BaseLogic {

    @Inject
    LinkCardinalityControl linkCardinalityControl;

    @Inject
    LinkTypeControl linkTypeControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    protected LinkCardinalityLogic() {
        super();
    }

    public LinkCardinality createLinkCardinality(final ExecutionErrorAccumulator eea, final String linkCardinalityName,
            final Boolean isDefault, final Integer sortOrder,
            final Language language, final String description, final BasePK createdBy) {
        var linkCardinality = linkCardinalityControl.getLinkCardinalityByName(linkCardinalityName);

        if(linkCardinality == null) {
            linkCardinality = linkCardinalityControl.createLinkCardinality(linkCardinalityName,
                    isDefault, sortOrder, createdBy);

            if(description != null) {
                linkCardinalityControl.createLinkCardinalityDescription(linkCardinality, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateLinkCardinalityNameException.class, eea, ExecutionErrors.DuplicateLinkCardinalityName.name(), linkCardinalityName);
        }

        return linkCardinality;
    }

    public LinkCardinality getLinkCardinalityByName(final ExecutionErrorAccumulator eea, final String linkCardinalityName,
            final EntityPermission entityPermission) {
        var linkCardinality = linkCardinalityControl.getLinkCardinalityByName(linkCardinalityName, entityPermission);

        if(linkCardinality == null) {
            handleExecutionError(UnknownLinkCardinalityNameException.class, eea, ExecutionErrors.UnknownLinkCardinalityName.name(), linkCardinalityName);
        }

        return linkCardinality;
    }

    public LinkCardinality getLinkCardinalityByName(final ExecutionErrorAccumulator eea, final String linkCardinalityName) {
        return getLinkCardinalityByName(eea, linkCardinalityName, EntityPermission.READ_ONLY);
    }

    public LinkCardinality getLinkCardinalityByNameForUpdate(final ExecutionErrorAccumulator eea, final String linkCardinalityName) {
        return getLinkCardinalityByName(eea, linkCardinalityName, EntityPermission.READ_WRITE);
    }

    public LinkCardinality getLinkCardinalityByUniversalSpec(final ExecutionErrorAccumulator eea,
            final LinkCardinalityUniversalSpec linkCardinalityUniversalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        LinkCardinality linkCardinality = null;
        var linkCardinalityName = linkCardinalityUniversalSpec.getLinkCardinalityName();
        var parameterCount = (linkCardinalityName == null ? 0 : 1) + entityInstanceLogic.countPossibleEntitySpecs(linkCardinalityUniversalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    linkCardinality = linkCardinalityControl.getDefaultLinkCardinality(entityPermission);

                    if(linkCardinality == null) {
                        handleExecutionError(UnknownDefaultLinkCardinalityException.class, eea, ExecutionErrors.UnknownDefaultLinkCardinality.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(linkCardinalityName == null) {
                    var entityInstance = entityInstanceLogic.getEntityInstance(eea, linkCardinalityUniversalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.LinkCardinality.name());

                    if(!hasExecutionErrors(eea)) {
                        linkCardinality = linkCardinalityControl.getLinkCardinalityByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    linkCardinality = getLinkCardinalityByName(eea, linkCardinalityName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return linkCardinality;
    }

    public LinkCardinality getLinkCardinalityByUniversalSpec(final ExecutionErrorAccumulator eea,
            final LinkCardinalityUniversalSpec linkCardinalityUniversalSpec, boolean allowDefault) {
        return getLinkCardinalityByUniversalSpec(eea, linkCardinalityUniversalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public LinkCardinality getLinkCardinalityByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final LinkCardinalityUniversalSpec linkCardinalityUniversalSpec, boolean allowDefault) {
        return getLinkCardinalityByUniversalSpec(eea, linkCardinalityUniversalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void updateLinkCardinalityFromValue(final LinkCardinalityDetailValue linkCardinalityDetailValue,
            final BasePK updatedBy) {
        linkCardinalityControl.updateLinkCardinalityFromValue(linkCardinalityDetailValue, updatedBy);
    }

    public void deleteLinkCardinality(final ExecutionErrorAccumulator eea, final LinkCardinality linkCardinality,
            final BasePK deletedBy) {
        if(linkTypeControl.countLinkTypes(linkTypeControl.getLinkTypeFilter(null, null, null, linkCardinality, null, null)) != 0) {
            handleExecutionError(CannotDeleteLinkCardinalityInUseException.class, eea, ExecutionErrors.CannotDeleteLinkCardinalityInUse.name(), linkCardinality.getLastDetail().getLinkCardinalityName());
        } else {
            linkCardinalityControl.deleteLinkCardinality(linkCardinality, deletedBy);
        }
    }

}
