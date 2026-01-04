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

package com.echothree.model.control.selector.server.evaluator;

import com.echothree.model.control.geo.common.GeoCodeTypes;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.selector.common.SelectorNodeTypes;
import com.echothree.model.data.contact.server.entity.ContactMechanism;
import com.echothree.model.data.contact.server.entity.ContactPostalAddress;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.selector.server.entity.SelectorNodeDetail;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.Session;

public class PostalAddressSelectorEvaluator
        extends BaseContactMechanismSelectorEvaluator {
    
    protected GeoControl geoControl = Session.getModelController(GeoControl.class);
    private ContactPostalAddress contactPostalAddress = null;
    
    /** Creates a new instance of PostalAddressSelectorEvaluator */
    public PostalAddressSelectorEvaluator(Session session, BasePK evaluatedBy) {
        super(session, evaluatedBy, PostalAddressSelectorEvaluator.class);
    }
    
    private Boolean evaluateGeoCode(final SelectorNodeDetail snd) {
        var sngc = cachedSelector.getSelectorNodeGeoCodeFromSelectorNodeDetail(snd);
        var gc = sngc.getGeoCode();
        var geoCodeTypeName = gc.getLastDetail().getGeoCodeType().getLastDetail().getGeoCodeTypeName();
        boolean result;
        
        if(geoCodeTypeName.equals(GeoCodeTypes.COUNTRY.name())) {
            result = contactPostalAddress.getCountryGeoCode().equals(gc);
        } else if(geoCodeTypeName.equals(GeoCodeTypes.STATE.name())) {
            var cpagc = contactPostalAddress.getStateGeoCode();
            
            result = cpagc == null? false: cpagc.equals(gc);
        } else if(geoCodeTypeName.equals(GeoCodeTypes.COUNTY.name())) {
            var cpagc = contactPostalAddress.getCountyGeoCode();
            
            result = cpagc == null? false: cpagc.equals(gc);
        } else if(geoCodeTypeName.equals(GeoCodeTypes.CITY.name())) {
            var cpagc = contactPostalAddress.getCityGeoCode();
            
            result = cpagc == null? false: cpagc.equals(gc);
        } else if(geoCodeTypeName.equals(GeoCodeTypes.ZIP_CODE.name())) {
            var cpagc = contactPostalAddress.getPostalCodeGeoCode();
            
            result = cpagc == null? false: cpagc.equals(gc);
        } else {
            if(BaseSelectorEvaluatorDebugFlags.BasePostalAddressSelectorEvaluator)
                log.info("--- unknown geoCodeTypeName (" + geoCodeTypeName + ")");
            result = false;
        }
        
        return snd.getNegate()? !result: result;
    }
    
    @Override
    protected Boolean evaluateSelectorNode(SelectorNodeDetail snd) {
        if(BaseSelectorEvaluatorDebugFlags.BasePostalAddressSelectorEvaluator)
            log.info(">>> BasePostalAddressSelectorEvaluator.evaluateSelectorNode(snd = " + snd + ")");
        Boolean result;
        
        if(snd != null) {
            var snt = snd.getSelectorNodeType();
            var sntn = snt.getSelectorNodeTypeName();
            
            if(sntn.equals(SelectorNodeTypes.GEO_CODE.name())) {
                result = evaluateGeoCode(snd);
            } else {
                result = super.evaluateSelectorNode(snd);
            }
        } else {
            if(BaseSelectorEvaluatorDebugFlags.BasePostalAddressSelectorEvaluator)
                log.info("--- snd was null, evaluating to FALSE");
            result = false;
        }
        
        if(BaseSelectorEvaluatorDebugFlags.BasePostalAddressSelectorEvaluator)
            log.info("<<< BasePostalAddressSelectorEvaluator.evaluateSelectorNode, result = " + result);
        return result;
    }

    public Boolean isPostalAddressSelected(CachedSelector cachedSelector, EntityInstance entityInstance, ContactMechanism contactMechanism) {
        contactPostalAddress = contactControl.getContactPostalAddress(contactMechanism);

        var result = super.isContactMechanismSelected(cachedSelector, entityInstance, contactMechanism);
        
        contactPostalAddress = null;
        
        return result;
    }
    
    public Boolean isPostalAddressSelected(CachedSelector cachedSelector, EntityInstance entityInstance) {
        return isPostalAddressSelected(cachedSelector, entityInstance, getContactMechanismFromEntityInstance(entityInstance));
    }
    
    public Boolean isPostalAddressSelected(CachedSelector cachedSelector, ContactMechanism contactMechanism) {
        return isPostalAddressSelected(cachedSelector, getEntityInstanceByBaseEntity(contactMechanism), contactMechanism);
    }
    
}
