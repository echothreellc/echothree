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

package com.echothree.model.control.payment.server.logic;

import com.echothree.control.user.payment.common.edit.PartyPaymentMethodEdit;
import com.echothree.model.control.contact.server.ContactControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.payment.common.PaymentConstants;
import com.echothree.model.control.payment.common.exception.UnknownPartyPaymentMethodNameException;
import com.echothree.model.control.payment.server.PaymentControl;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.data.contact.server.entity.ContactMechanism;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.party.server.entity.NameSuffix;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PersonalTitle;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.payment.server.entity.PartyPaymentMethod;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.model.data.payment.server.entity.PaymentMethodCreditCard;
import com.echothree.model.data.payment.server.entity.PaymentMethodType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PartyPaymentMethodLogic
    extends BaseLogic {

    private PartyPaymentMethodLogic() {
        super();
    }

    private static class PartyPaymentMethodLogicHolder {
        static PartyPaymentMethodLogic instance = new PartyPaymentMethodLogic();
    }

    public static PartyPaymentMethodLogic getInstance() {
        return PartyPaymentMethodLogicHolder.instance;
    }

    private String getDigitsOnly(String s) {
        StringBuilder digitsOnly = new StringBuilder();

        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if(Character.isDigit(c)) {
                digitsOnly.append(c);
            }
        }

        return digitsOnly.toString();
    }

    private boolean isValid(String number) {
        String digitsOnly = getDigitsOnly(number);
        int sum = 0;
        boolean timesTwo = false;

        for(int i = digitsOnly.length() - 1; i >= 0; i--) {
            int digit = Integer.parseInt(digitsOnly.substring(i, i + 1));
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

        int modulus = sum % 10;

        return modulus == 0;
    }

    public void checkPartyType(final ExecutionErrorAccumulator ema, final Party party) {
        String partyTypeName = party.getLastDetail().getPartyType().getPartyTypeName();

        if(!partyTypeName.equals(PartyConstants.PartyType_CUSTOMER)) {
            ema.addExecutionError(ExecutionErrors.InvalidPartyType.name(), partyTypeName);
        }
    }

    public void checkNameOnCard(final ExecutionErrorAccumulator ema, final PartyPaymentMethodEdit ppme, final PaymentMethodCreditCard paymentMethodCreditCard) {
        var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        String personalTitleId = ppme.getPersonalTitleId();
        PersonalTitle personalTitle = personalTitleId == null? null: partyControl.convertPersonalTitleIdToEntity(personalTitleId, EntityPermission.READ_ONLY);

        if(personalTitleId == null || personalTitle != null) {
            String nameSuffixId = ppme.getNameSuffixId();
            NameSuffix nameSuffix = nameSuffixId == null? null: partyControl.convertNameSuffixIdToEntity(nameSuffixId, EntityPermission.READ_ONLY);

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
        String number = ppme.getNumber();

        if(number != null) {
            String cardNumberValidationPattern = paymentMethodCreditCard.getCardNumberValidationPattern();
            boolean validCardNumber = true;

            if(cardNumberValidationPattern != null) {
                Matcher m = Pattern.compile(cardNumberValidationPattern).matcher(number);

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
        String strExpirationMonth = ppme.getExpirationMonth();
        String strExpirationYear = ppme.getExpirationYear();

        if(strExpirationMonth != null && strExpirationYear != null) {
            if(paymentMethodCreditCard.getCheckExpirationDate()) {
                var userControl = (UserControl)Session.getModelController(UserControl.class);
                TimeZone timeZone = userControl.getPreferredTimeZoneFromParty(party);
                int expirationMonth = Integer.valueOf(strExpirationMonth);
                int expirationYear = Integer.valueOf(strExpirationYear);
                ZonedDateTime dt = ZonedDateTime.ofInstant(Instant.ofEpochMilli(session.START_TIME), ZoneId.of(timeZone.getLastDetail().getJavaTimeZoneName()));
                boolean validExpirationDate = true;
                int thisYear = dt.getYear();

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
        String securityCode = ppme.getSecurityCode();

        if(securityCode != null) {
            String securityCodeValidationPattern = paymentMethodCreditCard.getSecurityCodeValidationPattern();
            boolean validSecurityCode = true;

            if(securityCodeValidationPattern != null) {
                Matcher m = Pattern.compile(securityCodeValidationPattern).matcher(securityCode);

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
        var contactControl = (ContactControl)Session.getModelController(ContactControl.class);
        String billingContactMechanismName = ppme.getBillingContactMechanismName();
        ContactMechanism billingContactMechanism = billingContactMechanismName == null? null: contactControl.getContactMechanismByName(billingContactMechanismName);

        if(billingContactMechanism != null) {
            PartyContactMechanism billingPartyContactMechanism = billingContactMechanism == null? null: contactControl.getPartyContactMechanism(party, billingContactMechanism);

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
        var contactControl = (ContactControl)Session.getModelController(ContactControl.class);
        String issuerName = ppme.getIssuerName();
        String issuerContactMechanismName = ppme.getIssuerContactMechanismName();
        ContactMechanism issuerContactMechanism = issuerContactMechanismName == null ? null : contactControl.getContactMechanismByName(issuerContactMechanismName);

        if(issuerName != null && issuerContactMechanism != null) {
            PartyContactMechanism issuerPartyContactMechanism = issuerContactMechanism == null ? null : contactControl.getPartyContactMechanism(party, issuerContactMechanism);

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
        var paymentControl = (PaymentControl)Session.getModelController(PaymentControl.class);
        PaymentMethodCreditCard paymentMethodCreditCard = paymentControl.getPaymentMethodCreditCard(paymentMethod);

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
        PaymentMethodType paymentMethodType = paymentMethod.getLastDetail().getPaymentMethodType();
        String paymentMethodTypeName = paymentMethodType.getPaymentMethodTypeName();

        if(paymentMethodTypeName.equals(PaymentConstants.PaymentMethodType_CREDIT_CARD)) {
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
        var paymentControl = (PaymentControl)Session.getModelController(PaymentControl.class);
        PartyPaymentMethod partyPaymentMethod = null;

        partyPaymentMethod = paymentControl.getPartyPaymentMethodByName(partyPaymentMethodName, entityPermission);

        if(partyPaymentMethod == null) {
            handleExecutionError(UnknownPartyPaymentMethodNameException.class, eea, ExecutionErrors.UnknownPartyPaymentMethodName.name(), partyPaymentMethodName);
        }

        return partyPaymentMethod;
    }

    public PartyPaymentMethod getPartyPaymentMethodByName(final ExecutionErrorAccumulator eea, final String partyPaymentMethodName) {
        return getPartyPaymentMethodByName(eea, partyPaymentMethodName, EntityPermission.READ_ONLY);
    }

    public PartyPaymentMethod getPartyPaymentMethodByNameForUpdate(final ExecutionErrorAccumulator eea, final String partyPaymentMethodTypeName,
            final String partyPaymentMethodName) {
        return getPartyPaymentMethodByName(eea, partyPaymentMethodName, EntityPermission.READ_WRITE);
    }
    
}
