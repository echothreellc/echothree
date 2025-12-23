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

import com.echothree.control.user.offer.common.spec.OfferNameElementUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.offer.common.exception.DuplicateOfferNameElementNameException;
import com.echothree.model.control.offer.common.exception.UnknownOfferNameElementNameException;
import com.echothree.model.control.offer.server.control.OfferNameElementControl;
import com.echothree.model.data.offer.server.entity.OfferNameElement;
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
public class OfferNameElementLogic
        extends BaseLogic {

    protected OfferNameElementLogic() {
        super();
    }

    public static OfferNameElementLogic getInstance() {
        return CDI.current().select(OfferNameElementLogic.class).get();
    }

    public OfferNameElement createOfferNameElement(final ExecutionErrorAccumulator eea, final String offerNameElementName,
            final Integer offset, final Integer length, final String validationPattern, final Language language, final String description,
            final BasePK createdBy) {
        var offerNameElementControl = Session.getModelController(OfferNameElementControl.class);
        var offerNameElement = offerNameElementControl.getOfferNameElementByName(offerNameElementName);

        if(offerNameElement == null) {
            offerNameElement = offerNameElementControl.createOfferNameElement(offerNameElementName, offset, length, validationPattern,
                    createdBy);

            if(description != null) {
                offerNameElementControl.createOfferNameElementDescription(offerNameElement, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateOfferNameElementNameException.class, eea, ExecutionErrors.DuplicateOfferNameElementName.name(),
                    offerNameElementName);
        }

        return offerNameElement;
    }

    public OfferNameElement getOfferNameElementByName(final ExecutionErrorAccumulator eea, final String offerNameElementName,
            final EntityPermission entityPermission) {
        var offerNameElementControl = Session.getModelController(OfferNameElementControl.class);
        var offerNameElement = offerNameElementControl.getOfferNameElementByName(offerNameElementName, entityPermission);

        if(offerNameElement == null) {
            handleExecutionError(UnknownOfferNameElementNameException.class, eea, ExecutionErrors.UnknownOfferNameElementName.name(), offerNameElementName);
        }

        return offerNameElement;
    }

    public OfferNameElement getOfferNameElementByName(final ExecutionErrorAccumulator eea, final String offerNameElementName) {
        return getOfferNameElementByName(eea, offerNameElementName, EntityPermission.READ_ONLY);
    }

    public OfferNameElement getOfferNameElementByNameForUpdate(final ExecutionErrorAccumulator eea, final String offerNameElementName) {
        return getOfferNameElementByName(eea, offerNameElementName, EntityPermission.READ_WRITE);
    }

    public OfferNameElement getOfferNameElementByUniversalSpec(final ExecutionErrorAccumulator eea,
            final OfferNameElementUniversalSpec universalSpec, final EntityPermission entityPermission) {
        var offerNameElementControl = Session.getModelController(OfferNameElementControl.class);
        var offerNameElementName = universalSpec.getOfferNameElementName();
        var parameterCount = (offerNameElementName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);
        OfferNameElement offerNameElement = null;

        if(parameterCount == 1) {
            if(offerNameElementName == null) {
                var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                        ComponentVendors.ECHO_THREE.name(), EntityTypes.OfferNameElement.name());

                if(!eea.hasExecutionErrors()) {
                    offerNameElement = offerNameElementControl.getOfferNameElementByEntityInstance(entityInstance, entityPermission);
                }
            } else {
                offerNameElement = getOfferNameElementByName(eea, offerNameElementName, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return offerNameElement;
    }

    public OfferNameElement getOfferNameElementByUniversalSpec(final ExecutionErrorAccumulator eea,
            final OfferNameElementUniversalSpec universalSpec) {
        return getOfferNameElementByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public OfferNameElement getOfferNameElementByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final OfferNameElementUniversalSpec universalSpec) {
        return getOfferNameElementByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

    public void deleteOfferNameElement(final ExecutionErrorAccumulator eea, final OfferNameElement offerNameElement,
            final BasePK deletedBy) {
        var offerNameElementControl = Session.getModelController(OfferNameElementControl.class);

        offerNameElementControl.deleteOfferNameElement(offerNameElement, deletedBy);
    }

}
