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

package com.echothree.control.user.authentication.server.command;

import com.echothree.model.control.contact.common.ContactMechanismPurposes;
import com.echothree.model.control.contact.common.ContactMechanismTypes;
import com.echothree.model.control.contact.server.control.ContactControl;
import static com.echothree.model.control.party.common.PartyTypes.CUSTOMER;
import static com.echothree.model.control.party.common.PartyTypes.EMPLOYEE;
import static com.echothree.model.control.party.common.PartyTypes.VENDOR;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import static com.echothree.model.control.security.common.SecurityRoleGroups.Employee;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.user.common.UserConstants;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyRelationship;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.user.server.entity.UserLoginPasswordString;
import com.echothree.model.data.user.server.entity.UserLoginStatus;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.ExecutionWarnings;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.Sha1Utils;
import com.echothree.util.server.string.UnitOfMeasureUtils;
import java.util.List;

public abstract class BaseLoginCommand<F extends BaseForm>
        extends BaseSimpleCommand<F> {
    
    /** Creates a new instance of BaseLoginCommand */
    protected BaseLoginCommand(final CommandSecurityDefinition commandSecurityDefinition,
            final List<FieldDefinition> formFieldDefinition) {
        super(commandSecurityDefinition, formFieldDefinition, false);
    }
    
    protected UserLoginPasswordString checkPassword(final String password, final Party party, final String userLoginPasswordTypeName,
            final boolean deleteOnSuccess) {
        var userControl = getUserControl();
        var userLoginPasswordType = userControl.getUserLoginPasswordTypeByName(userLoginPasswordTypeName);
        var userLoginPassword = deleteOnSuccess? userControl.getUserLoginPasswordForUpdate(party, userLoginPasswordType):
            userControl.getUserLoginPassword(party, userLoginPasswordType);
        UserLoginPasswordString result = null;

        if(userLoginPassword != null) {
            var userLoginPasswordEncoderType = userLoginPassword.getUserLoginPasswordType().getUserLoginPasswordEncoderType();
            var userLoginPasswordEncoderTypeName = userLoginPasswordEncoderType.getUserLoginPasswordEncoderTypeName();
            var userLoginPasswordString = userControl.getUserLoginPasswordString(userLoginPassword);
            
            if(userLoginPasswordEncoderTypeName.equals(UserConstants.UserLoginPasswordEncoderType_SHA1)) {
                result = Sha1Utils.getInstance().encode(userLoginPasswordString.getSalt(), password).equals(userLoginPasswordString.getPassword()) ? userLoginPasswordString: null;
            } else if(userLoginPasswordEncoderTypeName.equals(UserConstants.UserLoginPasswordEncoderType_TEXT)) {
                result = password.equals(userLoginPasswordString.getPassword())? userLoginPasswordString: null;
            }
            
            if(deleteOnSuccess && result != null) {
                userControl.deleteUserLoginPassword(userLoginPassword, getPartyPK());
            }
        }
        
        return result;
    }
    
    // TODO: Recovered password should become regular password if that ends up being the password that matches, also,
    // the recovered password should be deleted if the user logs in using their regular one. Changing a password should also
    // make sure a recovered password does not exist.
    protected boolean checkPasswords(final UserLoginStatus userLoginStatus, final String password, final Party party, final boolean doStatusChecks) {
        var result = checkPassword(password, party, UserConstants.UserLoginPasswordType_STRING, false);
        
        if(result == null) {
            result = checkPassword(password, party, UserConstants.UserLoginPasswordType_RECOVERED_STRING, true);
        }
        
        if(result == null) {
            addExecutionError(ExecutionErrors.IncorrectPassword.name());
        } else if(doStatusChecks) {
            var partyControl = Session.getModelController(PartyControl.class);
            var partyTypePasswordStringPolicy = partyControl.getPartyTypePasswordStringPolicy(party.getLastDetail().getPartyType());
            
            if(partyTypePasswordStringPolicy != null) {
                var partyTypePasswordStringPolicyDetail = partyTypePasswordStringPolicy.getLastDetail();
                var maximumPasswordLifetime = partyTypePasswordStringPolicyDetail.getMaximumPasswordLifetime();
                var expiredLoginsPermitted = partyTypePasswordStringPolicyDetail.getExpiredLoginsPermitted();
                
                if(maximumPasswordLifetime != null) {
                    var expirationWarningTime = partyTypePasswordStringPolicyDetail.getExpirationWarningTime();
                    var changedTime = result.getChangedTime();
                    
                    if((session.START_TIME - changedTime) > maximumPasswordLifetime) {
                        userLoginStatus.setExpiredCount(userLoginStatus.getExpiredCount() + 1);
                        
                        addExecutionWarning(ExecutionWarnings.PasswordExpired.name());
                    } else if(expirationWarningTime != null) {
                        var expirationTime = changedTime + maximumPasswordLifetime;
                        var warningTime = expirationTime - expirationWarningTime;
                        
                        if(session.START_TIME > warningTime) {
                            var uomControl = Session.getModelController(UomControl.class);
                            var timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);
                            var remainingTime = UnitOfMeasureUtils.getInstance().formatUnitOfMeasure(getUserVisit(), timeUnitOfMeasureKind, Long.valueOf(expirationTime - session.START_TIME));
                            
                            addExecutionWarning(ExecutionWarnings.PasswordExpiration.name(), remainingTime);
                        }
                    }
                }
                
                if(expiredLoginsPermitted != null && userLoginStatus.getExpiredCount() > expiredLoginsPermitted) {
                    result = null;
                    addExecutionError(ExecutionErrors.MaximumExpiredLoginsPermittedExceeded.name(), expiredLoginsPermitted);
                }

                if(userLoginStatus.getForceChange()) {
                    addExecutionWarning(ExecutionWarnings.ForcePasswordChange.name());
                }
            }
        }
        
        return result != null;
    }

    protected String getSecurityRoleGroupName(final PartyType partyType) {
        String securityRoleGroupName = null;
        var partyTypeName = partyType.getPartyTypeName();

        if(partyTypeName.equals(CUSTOMER.name())) {
            securityRoleGroupName = SecurityRoleGroups.Customer.name();
        } else if(partyTypeName.equals(EMPLOYEE.name())) {
            securityRoleGroupName = Employee.name();
        } else if(partyTypeName.equals(VENDOR.name())) {
            securityRoleGroupName = SecurityRoleGroups.Vendor.name();
        }

        return securityRoleGroupName;
    }

    protected void clearLoginFailures(final UserLoginStatus userLoginStatus) {
        // Audit trail for callers of this function should be created by the callers.
        userLoginStatus.setFailureCount(0);
        userLoginStatus.setFirstFailureTime(null);
        userLoginStatus.setLastFailureTime(null);
    }

    protected void addRemoteInet4AddressToParty(final Party party, final Integer remoteInet4Address) {
        if(remoteInet4Address != null) {
            var contactControl = Session.getModelController(ContactControl.class);
            var partyPK = party.getPrimaryKey();
            var partyContactMechanism = contactControl.getPartyContactMechanismByInet4Address(party, remoteInet4Address);

            if(partyContactMechanism == null) {
                var contactMechanismName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(null, SequenceTypes.CONTACT_MECHANISM.name());
                var contactMechanismType = contactControl.getContactMechanismTypeByName(ContactMechanismTypes.INET_4.name());
                var contactMechanism = contactControl.createContactMechanism(contactMechanismName, contactMechanismType, Boolean.FALSE, partyPK);

                contactControl.createContactInet4Address(contactMechanism, remoteInet4Address, partyPK);
                partyContactMechanism = contactControl.createPartyContactMechanism(party, contactMechanism, null, Boolean.FALSE, 1, partyPK);
            }

            var contactMechanismPurpose = contactControl.getContactMechanismPurposeByName(ContactMechanismPurposes.INET_4_LOGIN.name());
            var partyContactMechanismPurpose = contactControl.getPartyContactMechanismPurpose(partyContactMechanism, contactMechanismPurpose);
            if(partyContactMechanismPurpose == null) {
                contactControl.createPartyContactMechanismPurpose(partyContactMechanism, contactMechanismPurpose, Boolean.FALSE, 1, partyPK);
            }
        }
    }

    protected void successfulLogin(final UserLoginStatus userLoginStatus, final Party party, final PartyRelationship partyRelationship,
            final Integer remoteInet4Address) {
        var userControl = getUserControl();
        var userVisit = getUserVisitForUpdate();
        var userKey = userVisit.getUserKey();
        var userKeyDetailValue = userControl.getUserKeyDetailValueByPKForUpdate(userKey.getLastDetail().getPrimaryKey());

        userControl.associatePartyToUserVisit(userVisit, party, partyRelationship, session.START_TIME_LONG);
        
        // Only update the UserKeyDetail if the party has changed
        var partyPK = party.getPrimaryKey();
        var partyRelationshipPK = partyRelationship == null? null: partyRelationship.getPrimaryKey();
        var userKeyPartyPK = userKeyDetailValue.getPartyPK();
        var userKeyPartyRelationshipPK = userKeyDetailValue.getPartyRelationshipPK();
        
        if(userKeyPartyPK == null || !userKeyPartyPK.equals(partyPK)
                || userKeyPartyRelationshipPK == null || !userKeyPartyRelationshipPK.equals(partyRelationshipPK)) {
            userKeyDetailValue.setPartyPK(partyPK);
            userKeyDetailValue.setPartyRelationshipPK(partyRelationshipPK);
            userControl.updateUserKeyFromValue(userKeyDetailValue);
        }

        clearLoginFailures(userLoginStatus);

        userLoginStatus.setLastLoginTime(session.START_TIME_LONG);

        addRemoteInet4AddressToParty(party, remoteInet4Address);

        // TODO: Create audit trail
    }
    
    protected void unsuccessfulLogin(final UserLoginStatus userLoginStatus) {
        var failureCount = userLoginStatus.getFailureCount();
        
        userLoginStatus.setFailureCount(failureCount + 1);
        if(userLoginStatus.getFirstFailureTime() == null) {
            userLoginStatus.setFirstFailureTime(session.START_TIME_LONG);
        }
        userLoginStatus.setLastFailureTime(session.START_TIME_LONG);
        
        // TODO: Create audit trail
    }
    
}
