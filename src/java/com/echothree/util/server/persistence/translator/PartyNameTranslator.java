// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.security.server.logic.SecurityRoleLogic;
import com.echothree.model.control.sequence.common.SequenceConstants;
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
        var targetMap = new HashMap<String, String>();
        
        targetMap.put(PartyConstants.PartyType_EMPLOYEE, Targets.Employee.toString());
        targetMap.put(PartyConstants.PartyType_CUSTOMER, Targets.Customer.toString());
        targetMap.put(PartyConstants.PartyType_COMPANY, Targets.Company.toString());
        targetMap.put(PartyConstants.PartyType_DIVISION, Targets.Division.toString());
        targetMap.put(PartyConstants.PartyType_DEPARTMENT, Targets.Department.toString());
        targetMap.put(PartyConstants.PartyType_VENDOR, Targets.Vendor.toString());
        targetMap.put(PartyConstants.PartyType_CARRIER, Targets.Carrier.toString());
        targetMap.put(PartyConstants.PartyType_WAREHOUSE, Targets.Warehouse.toString());

        partyTypesToTargets = Collections.unmodifiableMap(targetMap);
        
        targetMap = new HashMap<>();
        
        targetMap.put(SequenceConstants.SequenceType_CUSTOMER, Targets.Customer.toString());
        targetMap.put(SequenceConstants.SequenceType_EMPLOYEE, Targets.Employee.toString());
        
        sequenceTypesToTargets = Collections.unmodifiableMap(targetMap);
    }
    
    private EntityNames getNames(final Map<String, String> targetMap, final String key, final PartyDetail partyDetail) {
        EntityNames result = null;
        var target = targetMap.get(key);

        if(target != null) {
            var names = new MapWrapper<String>(1);

            names.put(Names.PartyName.toString(), partyDetail.getPartyName());

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
    public EntityInstanceAndNames getNames(final Party requestingParty, final String sequenceTypeName, final String value,
            final boolean includeEntityInstance) {
        EntityInstanceAndNames result = null;
        var target = sequenceTypesToTargets.get(sequenceTypeName);
        Party party = null;
        
        if(target.equals(Targets.Customer.toString()) &&
                SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(null, requestingParty,
                        SecurityRoleGroups.Customer.name(), SecurityRoles.Search.name())) {
            var customerControl = (CustomerControl)Session.getModelController(CustomerControl.class);
            var customer = customerControl.getCustomerByName(value);
            
            if(customer != null) {
                party = customer.getParty();
            }
        } else if(target.equals(Targets.Employee.toString()) &&
                SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(null, requestingParty,
                        SecurityRoleGroups.Employee.name(), SecurityRoles.Search.name())) {
            var employeeControl = (EmployeeControl)Session.getModelController(EmployeeControl.class);
            var partyEmployee = employeeControl.getPartyEmployeeByName(value);
            
            if(partyEmployee != null) {
                party = partyEmployee.getParty();
            }
        }
        
        if(party != null) {
            var coreControl = (CoreControl)Session.getModelController(CoreControl.class);
            var entityNames = getNames(sequenceTypesToTargets, sequenceTypeName, party.getLastDetail());
            
            result = entityNames == null ? null : new EntityInstanceAndNames(includeEntityInstance? coreControl.getEntityInstanceByBasePK(party.getPrimaryKey()) : null, entityNames);
        }
        
        return result;
    }

}

