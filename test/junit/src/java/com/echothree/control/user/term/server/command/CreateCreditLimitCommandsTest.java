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

package com.echothree.control.user.term.server.command;

import com.echothree.control.user.term.common.form.TermFormFactory;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.data.accounting.common.pk.CurrencyPK;
import com.echothree.model.data.accounting.common.pk.SymbolPositionPK;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.accounting.server.value.CurrencyValue;
import com.echothree.model.data.customer.common.pk.CustomerTypePK;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.customer.server.value.CustomerTypeValue;
import com.echothree.model.data.party.common.pk.PartyDetailPK;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.common.pk.PartyTypePK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyDetail;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.party.server.value.PartyDetailValue;
import com.echothree.model.data.party.server.value.PartyTypeValue;
import com.echothree.model.data.party.server.value.PartyValue;
import com.echothree.model.data.term.server.entity.CustomerTypeCreditLimit;
import com.echothree.model.data.term.server.entity.PartyCreditLimit;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.common.form.ValidationResult;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseCommand;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.validation.Validator;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
public class CreateCreditLimitCommandsTest {

    public enum Owner {
        CUSTOMER_TYPE,
        CUSTOMER,
        VENDOR
    }

    private static final PartyPK CREATED_BY = new PartyPK(99L);

    private static class Fixture {
        private final Currency currency;
        private final CustomerType customerType = new CustomerType(
                new CustomerTypeValue(new CustomerTypePK(2L), null, null), EntityPermission.READ_ONLY);
        private final Party party;
        private final AccountingControl accountingControl;
        private final TermControl termControl;
        private final BaseCommand command;
        private final BaseForm form;
        private final Supplier<ValidationResult> submission;
        private int executions;
        private int creates;
        private int creditLimitLookups;
        private Long savedCreditLimit;
        private Long savedPotentialCreditLimit;

        Fixture(Owner owner, String separator, int priceLineDigits) {
            currency = new Currency(new CurrencyValue(new CurrencyPK(1L), "USD", "$", new SymbolPositionPK(1L),
                    false, false, false, false, ",", 3, separator, 2, 4, priceLineDigits, 6, 3, "-", false, 0),
                    EntityPermission.READ_ONLY);
            var partyType = new PartyType(new PartyTypeValue(new PartyTypePK(3L), owner.name(), null, null,
                    false, false, false, 0), EntityPermission.READ_ONLY);
            party = new Party(new PartyValue(new PartyPK(4L), null, null), EntityPermission.READ_ONLY) {
                @Override
                public PartyDetail getLastDetail() {
                    return new PartyDetail(new PartyDetailValue(new PartyDetailPK(5L), getPrimaryKey(), "PARTY",
                            partyType.getPrimaryKey(), null, null, null, null, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
                        @Override
                        public PartyType getPartyType() { return partyType; }
                    };
                }
            };
            accountingControl = new AccountingControl() {
                @Override
                public Currency getCurrencyByIsoName(String currencyIsoName) {
                    return "USD".equals(currencyIsoName) ? currency : null;
                }
                @Override
                public Currency getDefaultCurrency() {
                    throw new AssertionError("An explicit currency must not fall back to the default");
                }
            };
            termControl = new TermControl() {
                @Override
                public CustomerTypeCreditLimit getCustomerTypeCreditLimit(CustomerType selectedCustomerType, Currency selectedCurrency) {
                    assertSame(customerType, selectedCustomerType);
                    assertSame(currency, selectedCurrency);
                    creditLimitLookups++;
                    return null;
                }
                @Override
                public PartyCreditLimit getPartyCreditLimit(Party selectedParty, Currency selectedCurrency) {
                    assertSame(party, selectedParty);
                    assertSame(currency, selectedCurrency);
                    creditLimitLookups++;
                    return null;
                }
                @Override
                public CustomerTypeCreditLimit createCustomerTypeCreditLimit(CustomerType selectedCustomerType,
                        Currency selectedCurrency, Long creditLimit, Long potentialCreditLimit, BasePK createdBy) {
                    assertSame(customerType, selectedCustomerType);
                    assertSame(currency, selectedCurrency);
                    captureCreate(creditLimit, potentialCreditLimit, createdBy);
                    return null;
                }
                @Override
                public PartyCreditLimit createPartyCreditLimit(Party selectedParty, Currency selectedCurrency,
                        Long creditLimit, Long potentialCreditLimit, BasePK createdBy) {
                    assertSame(party, selectedParty);
                    assertSame(currency, selectedCurrency);
                    captureCreate(creditLimit, potentialCreditLimit, createdBy);
                    return null;
                }
            };
            if(owner == Owner.CUSTOMER_TYPE) {
                var customerTypeCommand = new CustomerTypeCommand(this);
                command = customerTypeCommand;
                form = customerTypeCommand.getSubmittedForm();
                submission = customerTypeCommand::submit;
            } else {
                var partyCommand = new PartyCommand(this);
                command = partyCommand;
                form = partyCommand.getSubmittedForm();
                submission = partyCommand::submit;
            }
        }

        private void captureCreate(Long creditLimit, Long potentialCreditLimit, BasePK createdBy) {
            assertEquals(CREATED_BY, createdBy);
            creates++;
            savedCreditLimit = creditLimit;
            savedPotentialCreditLimit = potentialCreditLimit;
        }

        void setLimits(String creditLimit, String potentialCreditLimit) {
            form.set("CreditLimit", creditLimit);
            form.set("PotentialCreditLimit", potentialCreditLimit);
        }

        void assertCreated(Long creditLimit, Long potentialCreditLimit) {
            assertFalse(submission.get().getHasErrors());
            assertFalse(command.hasExecutionErrors());
            assertEquals(1, executions);
            assertEquals(1, creditLimitLookups);
            assertEquals(1, creates);
            assertEquals(creditLimit, savedCreditLimit);
            assertEquals(potentialCreditLimit, savedPotentialCreditLimit);
        }

        void assertRejected(String fieldName, String key, Object... values) {
            var originalValue = form.get(fieldName);
            var result = submission.get();
            assertTrue(result.getHasErrors());
            var messages = result.getValidationMessages();
            assertEquals(1, messages.size());
            var error = messages.get(fieldName).next();
            assertEquals(key, error.getKey());
            if(values.length > 0) {
                assertArrayEquals(values, error.getValues());
            }
            assertEquals(originalValue, form.get(fieldName));
            assertEquals(0, executions);
            assertEquals(0, creditLimitLookups);
            assertEquals(0, creates);
        }
    }

    private static class CustomerTypeCommand extends CreateCustomerTypeCreditLimitCommand {
        private final Fixture fixture;

        CustomerTypeCommand(Fixture fixture) {
            this.fixture = fixture;
            accountingControl = fixture.accountingControl;
            termControl = fixture.termControl;
            customerControl = new CustomerControl() {
                @Override
                public CustomerType getCustomerTypeByName(String customerTypeName) {
                    assertEquals("TYPE", customerTypeName);
                    return fixture.customerType;
                }
            };
            form = TermFormFactory.getCreateCustomerTypeCreditLimitForm();
            form.setCustomerTypeName("TYPE");
            form.setCurrencyIsoName("USD");
        }

        BaseForm getSubmittedForm() { return form; }

        ValidationResult submit() {
            var validator = new Validator(this) {
                @Override
                public AccountingControl getAccountingControl() { return fixture.accountingControl; }
            };
            setupValidator(validator);
            var result = validate(validator);
            if(!result.getHasErrors()) {
                fixture.executions++;
                execute();
            }
            return result;
        }

        @Override
        public PartyPK getPartyPK() { return CREATED_BY; }
    }

    private static class PartyCommand extends CreatePartyCreditLimitCommand {
        private final Fixture fixture;

        PartyCommand(Fixture fixture) {
            this.fixture = fixture;
            accountingControl = fixture.accountingControl;
            termControl = fixture.termControl;
            partyControl = new PartyControl() {
                @Override
                public Party getPartyByName(String partyName) {
                    assertEquals("PARTY", partyName);
                    return fixture.party;
                }
            };
            form = TermFormFactory.getCreatePartyCreditLimitForm();
            form.setPartyName("PARTY");
            form.setCurrencyIsoName("USD");
        }

        BaseForm getSubmittedForm() { return form; }

        ValidationResult submit() {
            var validator = new Validator(this) {
                @Override
                public AccountingControl getAccountingControl() { return fixture.accountingControl; }
            };
            setupValidator(validator);
            var result = validate(validator);
            if(!result.getHasErrors()) {
                fixture.executions++;
                execute();
            }
            return result;
        }

        @Override
        public PartyPK getPartyPK() { return CREATED_BY; }
    }

    private static Stream<Arguments> validAmounts() {
        return Stream.of(Owner.values()).flatMap(owner -> Stream.of(
                Arguments.of(owner, "99.99", 9999L),
                Arguments.of(owner, "21474836.48", 2147483648L),
                Arguments.of(owner, "92233720368547758.07", Long.MAX_VALUE),
                Arguments.of(owner, "MAX_VALUE", Long.MAX_VALUE),
                Arguments.of(owner, "0", 0L)));
    }

    @ParameterizedTest
    @MethodSource("validAmounts")
    public void createsWithExactScaledValuesAcrossTheLongRange(Owner owner, String input, Long expected) {
        var fixture = new Fixture(owner, ".", 2);
        fixture.setLimits(input, input);
        fixture.assertCreated(expected, expected);
    }

    private static Stream<Arguments> invalidAmounts() {
        return Stream.of(Owner.values()).flatMap(owner -> Stream.of("CreditLimit", "PotentialCreditLimit")
                .flatMap(fieldName -> Stream.of(
                        Arguments.of(owner, fieldName, "abc", "InvalidFormat", null),
                        Arguments.of(owner, fieldName, ".", "InvalidFormat", null),
                        Arguments.of(owner, fieldName, "1a2", "InvalidFormat", null),
                        Arguments.of(owner, fieldName, "1.2.3", "InvalidFormat", null),
                        Arguments.of(owner, fieldName, "--1", "InvalidFormat", null),
                        Arguments.of(owner, fieldName, "$1.00", "InvalidFormat", null),
                        Arguments.of(owner, fieldName, "1,000.00", "InvalidFormat", null),
                        Arguments.of(owner, fieldName, "MIN_VALUE", "InvalidFormat", null),
                        Arguments.of(owner, fieldName, "-1.00", "MinimumValue", "0.00"),
                        Arguments.of(owner, fieldName, "-0", "MinimumValue", "0.00"),
                        Arguments.of(owner, fieldName, "1.234", "ExcessFractionalDigits", 2),
                        Arguments.of(owner, fieldName, "0.000", "ExcessFractionalDigits", 2),
                        Arguments.of(owner, fieldName, "92233720368547758.08", "MaximumValue", "92233720368547758.07"))));
    }

    @ParameterizedTest
    @MethodSource("invalidAmounts")
    public void invalidAmountsCannotReachCreationAndRemainAvailableForCorrection(Owner owner, String fieldName,
            String input, String errorKey, Object parameter) {
        var fixture = new Fixture(owner, ".", 2);
        fixture.setLimits("12.34", "56.78");
        fixture.form.set(fieldName, input);
        fixture.assertRejected(fieldName, errorKey, parameter == null ? new Object[0] : new Object[]{parameter});
    }

    @ParameterizedTest
    @EnumSource(Owner.class)
    public void absentAndBlankPairsDoNotCreateACreditLimit(Owner owner) {
        for(var input : new String[]{null, "", "  "}) {
            var fixture = new Fixture(owner, ".", 2);
            fixture.setLimits(input, input);
            assertFalse(fixture.submission.get().getHasErrors());
            assertFalse(fixture.command.hasExecutionErrors());
            assertNull(fixture.form.get("CreditLimit"));
            assertNull(fixture.form.get("PotentialCreditLimit"));
            assertEquals(1, fixture.executions);
            assertEquals(0, fixture.creates);
        }
    }

    @ParameterizedTest
    @EnumSource(Owner.class)
    public void independentlyNullableLimitsStayNullWhenTheOtherIsExplicitZero(Owner owner) {
        for(var absentField : new String[]{"CreditLimit", "PotentialCreditLimit"}) {
            for(var input : new String[]{null, "", "  "}) {
                var fixture = new Fixture(owner, ".", 2);
                fixture.setLimits("0", "0");
                fixture.form.set(absentField, input);
                fixture.assertCreated(absentField.equals("CreditLimit") ? null : 0L,
                        absentField.equals("PotentialCreditLimit") ? null : 0L);
                assertNull(fixture.form.get(absentField));
            }
        }
    }

    private static Stream<Arguments> currencyFormats() {
        return Stream.of(Owner.values()).flatMap(owner -> Stream.of(
                Arguments.of(owner, 0, "12", 12L, "12,0"),
                Arguments.of(owner, 2, "12,34", 1234L, "12,340"),
                Arguments.of(owner, 6, "12,345678", 12345678L, "12,3456789")));
    }

    @ParameterizedTest
    @MethodSource("currencyFormats")
    public void submittedCurrencyDeterminesSeparatorAndPriceLinePrecision(Owner owner, int precision,
            String validAmount, Long expected, String excessiveAmount) {
        var fixture = new Fixture(owner, ",", precision);
        fixture.setLimits(validAmount, validAmount);
        fixture.assertCreated(expected, expected);
        var invalidFixture = new Fixture(owner, ",", precision);
        invalidFixture.setLimits(excessiveAmount, null);
        invalidFixture.assertRejected("CreditLimit", "ExcessFractionalDigits", precision);
        var wrongSeparatorFixture = new Fixture(owner, ",", precision);
        wrongSeparatorFixture.setLimits("12.34", null);
        wrongSeparatorFixture.assertRejected("CreditLimit", "InvalidFormat");
    }

    @ParameterizedTest
    @EnumSource(Owner.class)
    public void unknownSubmittedCurrencyStopsCreationWithoutUsingTheDefault(Owner owner) {
        var fixture = new Fixture(owner, ".", 2);
        fixture.setLimits("1.00", null);
        fixture.form.set("CurrencyIsoName", "BAD");
        fixture.assertRejected("CreditLimit", "UnknownCurrencyIsoName", "BAD");
    }
}
