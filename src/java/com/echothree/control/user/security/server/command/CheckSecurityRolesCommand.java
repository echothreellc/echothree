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

package com.echothree.control.user.security.server.command;

import com.echothree.control.user.security.common.form.CheckSecurityRolesForm;
import com.echothree.control.user.security.common.result.SecurityResultFactory;
import com.echothree.model.control.security.server.logic.SecurityRoleLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
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
    public CheckSecurityRolesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = SecurityResultFactory.getCheckSecurityRolesResult();
        var userVisit = getUserVisit();
        var resultSecurityRoles = new StringBuilder();
        
        if(userVisit != null) {
            var userKeyDetail = userVisit.getUserKey().getLastDetail();
            var party = userKeyDetail.getParty();
            
            if(party != null) {
                var securityRoleLogic = SecurityRoleLogic.getInstance();
                var formSecurityRoles = form.getSecurityRoles();
                var securityRolesToCheck = Splitter.on(':').trimResults().omitEmptyStrings().splitToList(formSecurityRoles).toArray(new String[0]);
                var securityRolesToCheckLength = securityRolesToCheck.length;
                
                for(var i = 0; i < securityRolesToCheckLength; i++) {
                    var securityRoleToCheck = Splitter.on('.').trimResults().omitEmptyStrings().splitToList(securityRolesToCheck[i]).toArray(new String[0]);
                    var securityRoleToCheckLength = securityRoleToCheck.length;
                    
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
