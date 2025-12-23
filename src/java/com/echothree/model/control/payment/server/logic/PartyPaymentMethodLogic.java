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

package com.echothree.model.control.payment.server.logic;

import com.echothree.control.user.payment.common.edit.PartyPaymentMethodEdit;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.payment.common.PaymentMethodTypes;
import com.echothree.model.control.payment.common.exception.UnknownPartyPaymentMethodNameException;
import com.echothree.model.control.payment.server.control.PartyPaymentMethodControl;
import com.echothree.model.control.payment.server.control.PaymentMethodControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.payment.server.entity.PartyPaymentMethod;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.model.data.payment.server.entity.PaymentMethodCreditCard;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.regex.Pattern;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class PartyPaymentMethodLogic
    extends BaseLogic {

    protected PartyPaymentMethodLogic() {
        super();
    }

    public static PartyPaymentMethodLogic getInstance() {
        return CDI.current().select(PartyPaymentMethodLogic.class).get();
    }

    private String getDigitsOnly(String s) {
        var digitsOnly = new StringBuilder();

        for(var i = 0; i < s.length(); i++) {
            var c = s.charAt(i);

            if(Character.isDigit(c)) {
                digitsOnly.append(c);
            }
        }

        return digitsOnly.toString();
    }

    private boolean isValid(String number) {
        var digitsOnly = getDigitsOnly(number);
        var sum = 0;
        var timesTwo = false;

        for(var i = digitsOnly.length() - 1; i >= 0; i--) {
            var digit = Integer.parseInt(digitsOnly.substring(i, i + 1));
            int addend;

            if(timesTwo) {
                addend = digit * 2;

                if (addend > 9) {
                    addend -= 9;
                }
            } else {
                addend = digit;
            }

            sum += addend;
            timesTwo = !timesTwo;
        }

        var modulus = sum % 10;

        return modulus == 0;
    }

    public void checkPartyType(final ExecutionErrorAccumulator ema, final Party party) {
        var partyTypeName = party.getLastDetail().getPartyType().getPartyTypeName();

        if(!partyTypeName.equals(PartyTypes.CUSTOMER.name())) {
            ema.addExecutionError(ExecutionErrors.InvalidPartyType.name(), partyTypeName);
        }
    }

    public void checkNameOnCard(final ExecutionErrorAccumulator ema, final PartyPaymentMethodEdit ppme, final PaymentMethodCreditCard paymentMethodCreditCard) {
        var partyControl = Session.getModelController(PartyControl.class);
        var personalTitleId = ppme.getPersonalTitleId();
        var personalTitle = personalTitleId == null? null: partyControl.convertPersonalTitleIdToEntity(personalTitleId, EntityPermission.READ_ONLY);

        if(personalTitleId == null || personalTitle != null) {
            var nameSuffixId = ppme.getNameSuffixId();
            var nameSuffix = nameSuffixId == null? null: partyControl.convertNameSuffixIdToEntity(nameSuffixId, EntityPermission.READ_ONLY);

            if(nameSuffixId == null || nameSuffix != null) {
                if(paymentMethodCreditCard.getRequireNameOnCard()) {
                    if(ppme.getFirstName() == null || ppme.getLastName() == null) {
                        ema.addExecutionError(ExecutionErrors.MissingNameOnCard.name());
                    }
                }
            } else {
                ema.addExecutionError(ExecutionErrors.UnknownNameSuffixId.name());
            }
        } else {
            ema.addExecutionError(ExecutionErrors.UnknownPersonalTitleId.name());
        }
    }

    public void checkNumber(final ExecutionErrorAccumulator ema, final PartyPaymentMethodEdit ppme, final PaymentMethodCreditCard paymentMethodCreditCard) {
        var number = ppme.getNumber();

        if(number != null) {
            var cardNumberValidationPattern = paymentMethodCreditCard.getCardNumberValidationPattern();
            var validCardNumber = true;

            if(cardNumberValidationPattern != null) {
                var m = Pattern.compile(cardNumberValidationPattern).matcher(number);

                if(!m.matches()) {
                    validCardNumber = false;
                }
            }

            if(!validCardNumber || !isValid(number)) {
                ema.addExecutionError(ExecutionErrors.InvalidNumber.name());
            }
        } else {
            ema.addExecutionError(ExecutionErrors.MissingNumber.name());
        }
    }

    public void checkExpirationDate(final Session session, final ExecutionErrorAccumulator ema, final Party party, final PartyPaymentMethodEdit ppme,
            final PaymentMethodCreditCard paymentMethodCreditCard) {
        var strExpirationMonth = ppme.getExpirationMonth();
        var strExpirationYear = ppme.getExpirationYear();

        if(strExpirationMonth != null && strExpirationYear != null) {
            if(paymentMethodCreditCard.getCheckExpirationDate()) {
                var userControl = Session.getModelController(UserControl.class);
                var timeZone = userControl.getPreferredTimeZoneFromParty(party);
                int expirationMonth = Integer.valueOf(strExpirationMonth);
                int expirationYear = Integer.valueOf(strExpirationYear);
                var dt = ZonedDateTime.ofInstant(Instant.ofEpochMilli(session.getStartTime()), ZoneId.of(timeZone.getLastDetail().getJavaTimeZoneName()));
                var validExpirationDate = true;
                var thisYear = dt.getYear();

                if(!(expirationYear < thisYear)) {
                    if(expirationYear == thisYear && expirationMonth < dt.getMonthValue()) {
                        validExpirationDate = false;
                    }
                } else {
                    validExpirationDate = false;
                }

                if(!validExpirationDate) {
                    ema.addExecutionError(ExecutionErrors.InvalidExpirationDate.name(), strExpirationMonth, strExpirationYear);
                }
            }
        } else if(paymentMethodCreditCard.getRequireExpirationDate()) {
            if(strExpirationMonth == null) {
                ema.addExecutionError(ExecutionErrors.MissingExpirationMonth.name());
            }

            if(strExpirationYear == null) {
                ema.addExecutionError(ExecutionErrors.MissingExpirationYear.name());
            }
        }
    }

    public void checkSecurityCode(final ExecutionErrorAccumulator ema, final PartyPaymentMethodEdit ppme, final PaymentMethodCreditCard paymentMethodCreditCard) {
        var securityCode = ppme.getSecurityCode();

        if(securityCode != null) {
            var securityCodeValidationPattern = paymentMethodCreditCard.getSecurityCodeValidationPattern();
            var validSecurityCode = true;

            if(securityCodeValidationPattern != null) {
                var m = Pattern.compile(securityCodeValidationPattern).matcher(securityCode);

                if(!m.matches()) {
                    validSecurityCode = false;
                }
            }

            if(!validSecurityCode) {
                ema.addExecutionError(ExecutionErrors.InvalidSecurityCode.name());
            }
        } else if(paymentMethodCreditCard.getRequireSecurityCode()) {
            ema.addExecutionError(ExecutionErrors.MissingSecurityCode.name());
        }
    }

    public void checkBillingContactMechanism(final ExecutionErrorAccumulator ema, final Party party, final PartyPaymentMethodEdit ppme,
            final PaymentMethodCreditCard paymentMethodCreditCard) {
        var contactControl = Session.getModelController(ContactControl.class);
        var billingContactMechanismName = ppme.getBillingContactMechanismName();
        var billingContactMechanism = billingContactMechanismName == null? null: contactControl.getContactMechanismByName(billingContactMechanismName);

        if(billingContactMechanism != null) {
            var billingPartyContactMechanism = billingContactMechanism == null? null: contactControl.getPartyContactMechanism(party, billingContactMechanism);

            if(billingPartyContactMechanism == null) {
                 ema.addExecutionError(ExecutionErrors.UnknownPartyContactMechanism.name(), party.getLastDetail().getPartyName(), billingContactMechanismName);
            }
        } else {
            if(billingContactMechanismName != null && billingContactMechanism == null) {
                ema.addExecutionError(ExecutionErrors.UnknownBillingContactMechanismName.name(), billingContactMechanismName);
            } else if(paymentMethodCreditCard.getRequireBilling()) {
                ema.addExecutionError(ExecutionErrors.MissingBillingContactMechanismName.name());
            }
        }
    }

    public void checkIssuer(final ExecutionErrorAccumulator ema, final Party party, final PartyPaymentMethodEdit ppme,
            final PaymentMethodCreditCard paymentMethodCreditCard) {
        var contactControl = Session.getModelController(ContactControl.class);
        var issuerName = ppme.getIssuerName();
        var issuerContactMechanismName = ppme.getIssuerContactMechanismName();
        var issuerContactMechanism = issuerContactMechanismName == null ? null : contactControl.getContactMechanismByName(issuerContactMechanismName);

        if(issuerName != null && issuerContactMechanism != null) {
            var issuerPartyContactMechanism = issuerContactMechanism == null ? null : contactControl.getPartyContactMechanism(party, issuerContactMechanism);

            if(issuerPartyContactMechanism == null) {
                ema.addExecutionError(ExecutionErrors.UnknownPartyContactMechanism.name(), party.getLastDetail().getPartyName(), issuerContactMechanismName);
            }
        } else {
            if(paymentMethodCreditCard.getRequireIssuer()) {
                if(issuerName == null) {
                    ema.addExecutionError(ExecutionErrors.MissingIssuerName.name());
                }

                if(issuerContactMechanismName != null && issuerContactMechanism == null) {
                    ema.addExecutionError(ExecutionErrors.UnknownIssuerContactMechanismName.name(), issuerContactMechanismName);
                } else {
                    ema.addExecutionError(ExecutionErrors.MissingIssuerContactMechanismName.name());
                }
            } else {
                if(issuerContactMechanismName != null && issuerContactMechanism == null) {
                    ema.addExecutionError(ExecutionErrors.UnknownIssuerContactMechanismName.name(), issuerContactMechanismName);
                }
            }
        }
    }

    public void checkCreditCard(final Session session, final ExecutionErrorAccumulator ema, final Party party, final PaymentMethod paymentMethod,
            final PartyPaymentMethodEdit ppme) {
        var paymentMethodControl = Session.getModelController(PaymentMethodControl.class);
        var paymentMethodCreditCard = paymentMethodControl.getPaymentMethodCreditCard(paymentMethod);

        if(paymentMethodCreditCard.getRequestNameOnCard()) {
            checkNameOnCard(ema, ppme, paymentMethodCreditCard);
        } else {
            ppme.setPersonalTitleId(null);
            ppme.setFirstName(null);
            ppme.setMiddleName(null);
            ppme.setLastName(null);
            ppme.setNameSuffixId(null);
            ppme.setName(null);
        }

        if(paymentMethodCreditCard.getCheckCardNumber()) {
            checkNumber(ema, ppme, paymentMethodCreditCard);
        }

        if(paymentMethodCreditCard.getRequestExpirationDate()) {
            checkExpirationDate(session, ema, party, ppme, paymentMethodCreditCard);
        } else {
            ppme.setExpirationMonth(null);
            ppme.setExpirationYear(null);
        }

        if(paymentMethodCreditCard.getRequestSecurityCode()) {
            checkSecurityCode(ema, ppme, paymentMethodCreditCard);
        } else {
            ppme.setSecurityCode(null);
        }

        if(paymentMethodCreditCard.getRequestBilling()) {
            checkBillingContactMechanism(ema, party, ppme, paymentMethodCreditCard);
        } else {
            ppme.setBillingContactMechanismName(null);
        }

        if(paymentMethodCreditCard.getRequestIssuer()) {
            checkIssuer(ema, party, ppme, paymentMethodCreditCard);
        } else {
            ppme.setIssuerName(null);
            ppme.setIssuerContactMechanismName(null);
        }
    }

    public void checkPaymentMethodType(final Session session, final ExecutionErrorAccumulator ema, final Party party, final PaymentMethod paymentMethod,
            final PartyPaymentMethodEdit ppme) {
        var paymentMethodType = paymentMethod.getLastDetail().getPaymentMethodType();
        var paymentMethodTypeName = paymentMethodType.getLastDetail().getPaymentMethodTypeName();

        if(paymentMethodTypeName.equals(PaymentMethodTypes.CREDIT_CARD.name())) {
            checkCreditCard(session, ema, party, paymentMethod, ppme);
        } else {
            ema.addExecutionError(ExecutionErrors.InvalidPaymentMethodType.name(), paymentMethodTypeName);
        }
    }

    public void checkPartyPaymentMethod(final Session session, final UserVisit userVisit, final ExecutionErrorAccumulator ema, final Party party,
            final PaymentMethod paymentMethod, final PartyPaymentMethodEdit ppme) {
        checkPartyType(ema, party);

        if(!ema.hasExecutionErrors()) {
            checkPaymentMethodType(session, ema, party, paymentMethod, ppme);
        }
    }

    private PartyPaymentMethod getPartyPaymentMethodByName(final ExecutionErrorAccumulator eea, final String partyPaymentMethodName,
            final EntityPermission entityPermission) {
        var partyPaymentMethodControl = Session.getModelController(PartyPaymentMethodControl.class);
        var partyPaymentMethod = partyPaymentMethodControl.getPartyPaymentMethodByName(partyPaymentMethodName, entityPermission);

        if(partyPaymentMethod == null) {
            handleExecutionError(UnknownPartyPaymentMethodNameException.class, eea, ExecutionErrors.UnknownPartyPaymentMethodName.name(), partyPaymentMethodName);
        }

        return partyPaymentMethod;
    }

    public PartyPaymentMethod getPartyPaymentMethodByName(final ExecutionErrorAccumulator eea,
            final String partyPaymentMethodName) {
        return getPartyPaymentMethodByName(eea, partyPaymentMethodName, EntityPermission.READ_ONLY);
    }

    public PartyPaymentMethod getPartyPaymentMethodByNameForUpdate(final ExecutionErrorAccumulator eea,
            final String partyPaymentMethodName) {
        return getPartyPaymentMethodByName(eea, partyPaymentMethodName, EntityPermission.READ_WRITE);
    }

    public void deletePartyPaymentMethod(final ExecutionErrorAccumulator eea, final PartyPaymentMethod partyPaymentMethod,
            final PartyPK deletedBy) {
        var partyPaymentMethodControl = Session.getModelController(PartyPaymentMethodControl.class);

        // TODO: Check to see if this payment method is in use on any open orders,
        // or orders that currently are allowing returns to be made against them.
        // If that's the case, the PPM shouldn't be deleted.
        partyPaymentMethodControl.deletePartyPaymentMethod(partyPaymentMethod, deletedBy);
    }

    public void deletePartyPaymentMethod(final ExecutionErrorAccumulator eea, final String partyPaymentMethodName,
            final PartyPK deletedBy) {
        var partyPaymentMethod = getPartyPaymentMethodByNameForUpdate(eea, partyPaymentMethodName);

        if(!eea.hasExecutionErrors()) {
            deletePartyPaymentMethod(eea, partyPaymentMethod, deletedBy);
        }
    }

}
