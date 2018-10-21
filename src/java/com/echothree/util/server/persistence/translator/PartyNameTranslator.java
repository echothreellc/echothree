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

package com.echothree.util.server.persistence.translator;

import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.customer.server.CustomerControl;
import com.echothree.model.control.employee.server.EmployeeControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.sequence.common.SequenceConstants;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.customer.server.entity.Customer;
import com.echothree.model.data.employee.server.entity.PartyEmployee;
import com.echothree.model.data.party.remote.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyDetail;
import com.echothree.model.data.party.server.factory.PartyFactory;
import com.echothree.util.common.persistence.EntityNames;
import com.echothree.util.common.persistence.EntityNamesConstants;
import com.echothree.util.remote.transfer.MapWrapper;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PartyNameTranslator
        implements EntityInstanceTranslator, SequenceTypeTranslator {

    private final static Map<String, String> partyTypesToTargets;
    private final static Map<String, String> sequenceTypesToTargets;

    static {
        Map<String, String> targetMap = new HashMap<>();
        
        targetMap.put(PartyConstants.PartyType_EMPLOYEE, EntityNamesConstants.Target_Employee);
        targetMap.put(PartyConstants.PartyType_CUSTOMER, EntityNamesConstants.Target_Customer);
        targetMap.put(PartyConstants.PartyType_COMPANY, EntityNamesConstants.Target_Company);
        targetMap.put(PartyConstants.PartyType_DIVISION, EntityNamesConstants.Target_Division);
        targetMap.put(PartyConstants.PartyType_DEPARTMENT, EntityNamesConstants.Target_Department);
        targetMap.put(PartyConstants.PartyType_VENDOR, EntityNamesConstants.Target_Vendor);
        targetMap.put(PartyConstants.PartyType_CARRIER, EntityNamesConstants.Target_Carrier);
        targetMap.put(PartyConstants.PartyType_WAREHOUSE, EntityNamesConstants.Target_Warehouse);

        partyTypesToTargets = Collections.unmodifiableMap(targetMap);
        
        targetMap = new HashMap<>();
        
        targetMap.put(SequenceConstants.SequenceType_CUSTOMER, EntityNamesConstants.Target_Customer);
        targetMap.put(SequenceConstants.SequenceType_EMPLOYEE, EntityNamesConstants.Target_Employee);
        
        sequenceTypesToTargets = Collections.unmodifiableMap(targetMap);
    }
    
    private EntityNames getNames(final Map<String, String> targetMap, final String key, final PartyDetail partyDetail) {
        String target = targetMap.get(key);
        EntityNames result = null;

        if(target != null) {
            MapWrapper<String> names = new MapWrapper<>(1);

            names.put(EntityNamesConstants.Name_PartyName, partyDetail.getPartyName());

            result = new EntityNames(target, names);
        }

        return result;
    }
    
    @Override
    public EntityNames getNames(final EntityInstance entityInstance) {
        PartyDetail partyDetail = PartyFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                new PartyPK(entityInstance.getEntityUniqueId())).getLastDetail();
        String partyTypeName = partyDetail.getPartyType().getPartyTypeName();

        return getNames(partyTypesToTargets, partyTypeName, partyDetail);
    }

    @Override
    public EntityInstanceAndNames getNames(final String sequenceTypeName, final String value, final boolean includeEntityInstance) {
        EntityInstanceAndNames result = null;
        String target = sequenceTypesToTargets.get(sequenceTypeName);
        Party party = null;
        
        if(target.equals(EntityNamesConstants.Target_Customer)) {
            CustomerControl customerControl = (CustomerControl)Session.getModelController(CustomerControl.class);
            Customer customer = customerControl.getCustomerByName(value);
            
            if(customer != null) {
                party = customer.getParty();
            }
        } else if(target.equals(EntityNamesConstants.Target_Employee)) {
            EmployeeControl employeeControl = (EmployeeControl)Session.getModelController(EmployeeControl.class);
            PartyEmployee partyEmployee = employeeControl.getPartyEmployeeByName(value);
            
            if(partyEmployee != null) {
                party = partyEmployee.getParty();
            }
        }
        
        if(party != null) {
            CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
            EntityNames entityNames = getNames(sequenceTypesToTargets, sequenceTypeName, party.getLastDetail());
            
            result = entityNames == null ? null : new EntityInstanceAndNames(includeEntityInstance? coreControl.getEntityInstanceByBasePK(party.getPrimaryKey()) : null, entityNames);
        }
        
        return result;
    }

}

