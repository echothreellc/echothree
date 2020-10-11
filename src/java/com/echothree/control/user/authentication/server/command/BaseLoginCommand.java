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

package com.echothree.control.user.authentication.server.command;

import com.echothree.model.control.contact.common.ContactMechanismPurposes;
import com.echothree.model.control.contact.common.ContactMechanismTypes;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.control.user.common.UserConstants;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.data.contact.server.entity.ContactMechanism;
import com.echothree.model.data.contact.server.entity.ContactMechanismPurpose;
import com.echothree.model.data.contact.server.entity.ContactMechanismType;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.contact.server.entity.PartyContactMechanismPurpose;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.common.pk.PartyRelationshipPK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyRelationship;
import com.echothree.model.data.party.server.entity.PartyTypePasswordStringPolicy;
import com.echothree.model.data.party.server.entity.PartyTypePasswordStringPolicyDetail;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserKey;
import com.echothree.model.data.user.server.entity.UserLoginPassword;
import com.echothree.model.data.user.server.entity.UserLoginPasswordEncoderType;
import com.echothree.model.data.user.server.entity.UserLoginPasswordString;
import com.echothree.model.data.user.server.entity.UserLoginPasswordType;
import com.echothree.model.data.user.server.entity.UserLoginStatus;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.user.server.value.UserKeyDetailValue;
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
    protected BaseLoginCommand(UserVisitPK userVisitPK, F form, CommandSecurityDefinition commandSecurityDefinition,
            List<FieldDefinition> formFieldDefinition) {
        super(userVisitPK, form, commandSecurityDefinition, formFieldDefinition, false);
    }
    
    protected UserLoginPasswordString checkPassword(String password, Party party, String userLoginPasswordTypeName,
            boolean deleteOnSuccess) {
        UserControl userControl = getUserControl();
        UserLoginPasswordString result = null;
        UserLoginPasswordType userLoginPasswordType = userControl.getUserLoginPasswordTypeByName(userLoginPasswordTypeName);
        UserLoginPassword userLoginPassword = deleteOnSuccess? userControl.getUserLoginPasswordForUpdate(party, userLoginPasswordType):
            userControl.getUserLoginPassword(party, userLoginPasswordType);
        
        if(userLoginPassword != null) {
            UserLoginPasswordEncoderType userLoginPasswordEncoderType = userLoginPassword.getUserLoginPasswordType().getUserLoginPasswordEncoderType();
            String userLoginPasswordEncoderTypeName = userLoginPasswordEncoderType.getUserLoginPasswordEncoderTypeName();
            UserLoginPasswordString userLoginPasswordString = userControl.getUserLoginPasswordString(userLoginPassword);
            
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
    protected boolean checkPasswords(UserLoginStatus userLoginStatus, String password, Party party, boolean doStatusChecks) {
        UserControl userControl = getUserControl();
        UserLoginPasswordString result = checkPassword(password, party, UserConstants.UserLoginPasswordType_STRING, false);
        
        if(result == null) {
            result = checkPassword(password, party, UserConstants.UserLoginPasswordType_RECOVERED_STRING, true);
        }
        
        if(result == null) {
            addExecutionError(ExecutionErrors.IncorrectPassword.name());
        } else if(doStatusChecks) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            PartyTypePasswordStringPolicy partyTypePasswordStringPolicy = partyControl.getPartyTypePasswordStringPolicy(party.getLastDetail().getPartyType());
            
            if(partyTypePasswordStringPolicy != null) {
                PartyTypePasswordStringPolicyDetail partyTypePasswordStringPolicyDetail = partyTypePasswordStringPolicy.getLastDetail();
                Long maximumPasswordLifetime = partyTypePasswordStringPolicyDetail.getMaximumPasswordLifetime();
                Integer expiredLoginsPermitted = partyTypePasswordStringPolicyDetail.getExpiredLoginsPermitted();
                
                if(maximumPasswordLifetime != null) {
                    Long expirationWarningTime = partyTypePasswordStringPolicyDetail.getExpirationWarningTime();
                    long changedTime = result.getChangedTime();
                    
                    if((session.START_TIME - changedTime) > maximumPasswordLifetime) {
                        userLoginStatus.setExpiredCount(userLoginStatus.getExpiredCount() + 1);
                        
                        addExecutionWarning(ExecutionWarnings.PasswordExpired.name());
                    } else if(expirationWarningTime != null) {
                        long expirationTime = changedTime + maximumPasswordLifetime;
                        long warningTime = expirationTime - expirationWarningTime;
                        
                        if(session.START_TIME > warningTime) {
                            var uomControl = (UomControl)Session.getModelController(UomControl.class);
                            UnitOfMeasureKind timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);
                            String remainingTime = UnitOfMeasureUtils.getInstance().formatUnitOfMeasure(getUserVisit(), timeUnitOfMeasureKind, Long.valueOf(expirationTime - session.START_TIME));
                            
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

    protected void clearLoginFailures(UserLoginStatus userLoginStatus) {
        // Audit trail for callers of this function should be created by the callers.
        userLoginStatus.setFailureCount(0);
        userLoginStatus.setFirstFailureTime(null);
        userLoginStatus.setLastFailureTime(null);
   }

    protected void successfulLogin(UserLoginStatus userLoginStatus, Party party, PartyRelationship partyRelationship, Integer remoteInet4Address) {
        var contactControl = (ContactControl)Session.getModelController(ContactControl.class);
        UserControl userControl = getUserControl();
        UserVisit userVisit = getUserVisitForUpdate();
        UserKey userKey = userVisit.getUserKey();
        UserKeyDetailValue userKeyDetailValue = userControl.getUserKeyDetailValueByPKForUpdate(userKey.getLastDetail().getPrimaryKey());
        PartyContactMechanism partyContactMechanism = contactControl.getPartyContactMechanismByInet4Address(party, remoteInet4Address);
        
        userControl.associatePartyToUserVisit(userVisit, party, partyRelationship, session.START_TIME_LONG);
        
        // Only update the UserKeyDetail if the party has changed
        PartyPK partyPK = party.getPrimaryKey();
        PartyRelationshipPK partyRelationshipPK = partyRelationship == null? null: partyRelationship.getPrimaryKey();
        PartyPK userKeyPartyPK = userKeyDetailValue.getPartyPK();
        PartyRelationshipPK userKeyPartyRelationshipPK = userKeyDetailValue.getPartyRelationshipPK();
        
        if(userKeyPartyPK == null || !userKeyPartyPK.equals(partyPK)
                || userKeyPartyRelationshipPK == null || !userKeyPartyRelationshipPK.equals(partyRelationshipPK)) {
            userKeyDetailValue.setPartyPK(partyPK);
            userKeyDetailValue.setPartyRelationshipPK(partyRelationshipPK);
            userControl.updateUserKeyFromValue(userKeyDetailValue);
        }

        clearLoginFailures(userLoginStatus);

        userLoginStatus.setLastLoginTime(session.START_TIME_LONG);
        
        if(partyContactMechanism == null) {
            String contactMechanismName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(null, SequenceTypes.CONTACT_MECHANISM.name());
            ContactMechanismType contactMechanismType = contactControl.getContactMechanismTypeByName(ContactMechanismTypes.INET_4.name());
            ContactMechanism contactMechanism = contactControl.createContactMechanism(contactMechanismName,
                    contactMechanismType, Boolean.FALSE, partyPK);
            
            contactControl.createContactInet4Address(contactMechanism, remoteInet4Address, partyPK);
            partyContactMechanism = contactControl.createPartyContactMechanism(party, contactMechanism,
                    null, Boolean.FALSE, 1, partyPK);
        }
        
        ContactMechanismPurpose contactMechanismPurpose = contactControl.getContactMechanismPurposeByName(ContactMechanismPurposes.INET_4_LOGIN.name());
        PartyContactMechanismPurpose partyContactMechanismPurpose = contactControl.getPartyContactMechanismPurpose(partyContactMechanism,
                contactMechanismPurpose);
        if(partyContactMechanismPurpose == null) {
            contactControl.createPartyContactMechanismPurpose(partyContactMechanism, contactMechanismPurpose, Boolean.FALSE, 1, partyPK);
        }
        
        // TODO: Create audit trail
    }
    
    protected void unsuccessfulLogin(UserLoginStatus userLoginStatus) {
        Integer failureCount = userLoginStatus.getFailureCount();
        
        userLoginStatus.setFailureCount(failureCount + 1);
        if(userLoginStatus.getFirstFailureTime() == null) {
            userLoginStatus.setFirstFailureTime(session.START_TIME_LONG);
        }
        userLoginStatus.setLastFailureTime(session.START_TIME_LONG);
        
        // TODO: Create audit trail
    }
    
}
