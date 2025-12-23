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

package com.echothree.util.server.persistence.translator;

import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyDetail;
import com.echothree.model.data.party.server.factory.PartyFactory;
import com.echothree.util.common.persistence.EntityNames;
import com.echothree.util.common.persistence.Names;
import com.echothree.util.common.persistence.Targets;
import com.echothree.util.common.transfer.MapWrapper;
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
        var partyTypesToTargetsMap = new HashMap<String, String>();

        partyTypesToTargetsMap.put(PartyTypes.EMPLOYEE.name(), Targets.Employee.name());
        partyTypesToTargetsMap.put(PartyTypes.CUSTOMER.name(), Targets.Customer.name());
        partyTypesToTargetsMap.put(PartyTypes.COMPANY.name(), Targets.Company.name());
        partyTypesToTargetsMap.put(PartyTypes.DIVISION.name(), Targets.Division.name());
        partyTypesToTargetsMap.put(PartyTypes.DEPARTMENT.name(), Targets.Department.name());
        partyTypesToTargetsMap.put(PartyTypes.VENDOR.name(), Targets.Vendor.name());
        partyTypesToTargetsMap.put(PartyTypes.CARRIER.name(), Targets.Carrier.name());
        partyTypesToTargetsMap.put(PartyTypes.WAREHOUSE.name(), Targets.Warehouse.name());

        partyTypesToTargets = Collections.unmodifiableMap(partyTypesToTargetsMap);
        
        var sequenceTypesToTargetsMap = new HashMap<String, String>();

        sequenceTypesToTargetsMap.put(SequenceTypes.CUSTOMER.name(), Targets.Customer.name());
        sequenceTypesToTargetsMap.put(SequenceTypes.EMPLOYEE.name(), Targets.Employee.name());
        
        sequenceTypesToTargets = Collections.unmodifiableMap(sequenceTypesToTargetsMap);
    }
    
    private EntityNames getNames(final Map<String, String> targetMap, final String key, final PartyDetail partyDetail) {
        EntityNames result = null;
        var target = targetMap.get(key);

        if(target != null) {
            var names = new MapWrapper<String>(1);

            names.put(Names.PartyName.name(), partyDetail.getPartyName());

            result = new EntityNames(target, names);
        }

        return result;
    }
    
    @Override
    public EntityNames getNames(final EntityInstance entityInstance) {
        var partyDetail = PartyFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                new PartyPK(entityInstance.getEntityUniqueId())).getLastDetail();
        var partyTypeName = partyDetail.getPartyType().getPartyTypeName();

        return getNames(partyTypesToTargets, partyTypeName, partyDetail);
    }

    @Override
    public EntityInstanceAndNames getNames(final String sequenceTypeName, final String value,
            final boolean includeEntityInstance) {
        EntityInstanceAndNames result = null;
        var target = sequenceTypesToTargets.get(sequenceTypeName);
        Party party = null;
        
        if(target.equals(Targets.Customer.name())) {
            var customerControl = Session.getModelController(CustomerControl.class);
            var customer = customerControl.getCustomerByName(value);
            
            if(customer != null) {
                party = customer.getParty();
            }
        } else if(target.equals(Targets.Employee.name())) {
            var employeeControl = Session.getModelController(EmployeeControl.class);
            var partyEmployee = employeeControl.getPartyEmployeeByName(value);
            
            if(partyEmployee != null) {
                party = partyEmployee.getParty();
            }
        }
        
        if(party != null) {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var entityNames = getNames(sequenceTypesToTargets, sequenceTypeName, party.getLastDetail());
            
            result = entityNames == null ? null : new EntityInstanceAndNames(includeEntityInstance? entityInstanceControl.getEntityInstanceByBasePK(party.getPrimaryKey()) : null, entityNames);
        }
        
        return result;
    }

}

