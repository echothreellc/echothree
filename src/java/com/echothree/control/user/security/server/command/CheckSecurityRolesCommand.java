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

package com.echothree.control.user.security.server.command;

import com.echothree.control.user.security.common.form.CheckSecurityRolesForm;
import com.echothree.control.user.security.common.result.CheckSecurityRolesResult;
import com.echothree.control.user.security.common.result.SecurityResultFactory;
import com.echothree.model.control.security.server.logic.SecurityRoleLogic;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserKeyDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.google.common.base.Splitter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CheckSecurityRolesCommand
        extends BaseSimpleCommand<CheckSecurityRolesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SecurityRoles", FieldType.STRING, true, 1L, null)
                ));
    }
    
    /** Creates a new instance of CheckSecurityRolesCommand */
    public CheckSecurityRolesCommand(UserVisitPK userVisitPK, CheckSecurityRolesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        CheckSecurityRolesResult result = SecurityResultFactory.getCheckSecurityRolesResult();
        UserVisit userVisit = getUserVisit();
        StringBuilder resultSecurityRoles = new StringBuilder();
        
        if(userVisit != null) {
            UserKeyDetail userKeyDetail = userVisit.getUserKey().getLastDetail();
            Party party = userKeyDetail.getParty();
            
            if(party != null) {
                SecurityRoleLogic securityRoleLogic = SecurityRoleLogic.getInstance();
                String formSecurityRoles = form.getSecurityRoles();
                String []securityRolesToCheck = Splitter.on(':').trimResults().omitEmptyStrings().splitToList(formSecurityRoles).toArray(new String[0]);
                int securityRolesToCheckLength = securityRolesToCheck.length;
                
                for(int i = 0; i < securityRolesToCheckLength; i++) {
                    String []securityRoleToCheck = Splitter.on('.').trimResults().omitEmptyStrings().splitToList(securityRolesToCheck[i]).toArray(new String[0]);
                    int securityRoleToCheckLength = securityRoleToCheck.length;
                    
                    if(securityRoleToCheckLength == 2) {
                        if(securityRoleLogic.hasSecurityRoleUsingNames(null, party, securityRoleToCheck[0], securityRoleToCheck[1])) {
                            if(resultSecurityRoles.length() > 0) {
                                resultSecurityRoles.append(':');
                            }
                            resultSecurityRoles.append(securityRolesToCheck[i]);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.InvalidSecurityRoleToCheckFormat.name(), securityRolesToCheck[i]);
                    }
                }
            }
        }
        
        result.setSecurityRoles(resultSecurityRoles.toString());
        
        return result;
    }
    
}
