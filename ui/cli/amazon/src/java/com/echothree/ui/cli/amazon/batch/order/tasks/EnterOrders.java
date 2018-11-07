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

package com.echothree.ui.cli.amazon.batch.order.tasks;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.form.GetCurrencyForm;
import com.echothree.control.user.accounting.common.result.GetCurrencyResult;
import com.echothree.control.user.authentication.common.AuthenticationUtil;
import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.control.user.contact.common.form.CreateContactPostalAddressForm;
import com.echothree.control.user.contact.common.result.CreateContactPostalAddressResult;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.form.CreateCustomerForm;
import com.echothree.control.user.party.common.result.CreateCustomerResult;
import com.echothree.control.user.sales.common.SalesUtil;
import com.echothree.control.user.sales.common.form.CreateSalesOrderBatchForm;
import com.echothree.control.user.sales.common.form.CreateSalesOrderForm;
import com.echothree.control.user.sales.common.form.CreateSalesOrderLineForm;
import com.echothree.control.user.sales.common.form.CreateSalesOrderPaymentPreferenceForm;
import com.echothree.control.user.sales.common.form.SetSalesOrderBatchStatusForm;
import com.echothree.control.user.sales.common.form.SetSalesOrderStatusForm;
import com.echothree.control.user.sales.common.result.CreateSalesOrderBatchResult;
import com.echothree.control.user.sales.common.result.CreateSalesOrderLineResult;
import com.echothree.control.user.sales.common.result.CreateSalesOrderPaymentPreferenceResult;
import com.echothree.control.user.sales.common.result.CreateSalesOrderResult;
import com.echothree.control.user.search.common.SearchUtil;
import com.echothree.control.user.search.common.form.GetCustomerResultsForm;
import com.echothree.control.user.search.common.form.SearchCustomersForm;
import com.echothree.control.user.search.common.result.GetCustomerResultsResult;
import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.model.control.contact.common.ContactConstants;
import com.echothree.model.control.contact.common.transfer.ContactMechanismTransfer;
import com.echothree.model.control.contact.common.transfer.ContactPostalAddressTransfer;
import com.echothree.model.control.contact.common.transfer.PartyContactMechanismTransfer;
import com.echothree.model.control.customer.common.transfer.CustomerTransfer;
import com.echothree.model.control.geo.common.GeoConstants;
import com.echothree.model.control.geo.common.transfer.CountryTransfer;
import com.echothree.model.control.party.common.PartyOptions;
import com.echothree.model.control.party.common.transfer.NameSuffixTransfer;
import com.echothree.model.control.party.common.transfer.PersonalTitleTransfer;
import com.echothree.model.control.search.common.SearchConstants;
import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.control.search.common.transfer.CustomerResultTransfer;
import com.echothree.model.control.sales.common.workflow.SalesOrderBatchStatusConstants;
import com.echothree.model.control.sales.common.workflow.SalesOrderStatusConstants;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.ui.cli.amazon.batch.order.content.AmazonOrder;
import com.echothree.ui.cli.amazon.batch.order.content.AmazonOrderLine;
import com.echothree.ui.cli.amazon.batch.order.content.AmazonOrderShipmentGroup;
import com.echothree.ui.cli.amazon.batch.order.content.AmazonOrders;
import com.echothree.util.client.string.NameCleaner;
import com.echothree.util.client.string.NameCleaner.NameResult;
import com.echothree.util.common.string.ContactPostalAddressUtils;
import com.echothree.util.common.string.StringUtils;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.google.common.base.Splitter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EnterOrders {

    private Log log = log = LogFactory.getLog(this.getClass());

    private static final Splitter TabSplitter = Splitter.on('\t')
           .trimResults();

    private Configuration configuration;
    AmazonOrders amazonOrders;
    int depth;

    private String indent;

    private void init(Configuration configuration, AmazonOrders amazonOrders, int depth) {
        this.configuration = configuration;
        this.amazonOrders = amazonOrders;
        this.depth = depth;
        
        indent = StringUtils.getInstance().repeatingStringFromChar(' ', depth);
    }

    /** Creates a new instance of EnterOrders */
    public EnterOrders(Configuration configuration, AmazonOrders amazonOrders, int depth) {
        init(configuration, amazonOrders, depth);
    }

    UserVisitPK userVisitPK = null;

    private UserVisitPK getUserVisit()
            throws NamingException {
        if(userVisitPK == null) {
            userVisitPK = AuthenticationUtil.getHome().getDataLoaderUserVisit();
        }

        return userVisitPK;
    }

    private void clearUserVisit()
            throws NamingException {
        if(userVisitPK != null) {
            AuthenticationUtil.getHome().invalidateUserVisit(userVisitPK);
            userVisitPK = null;
        }
    }

    NameCleaner nameCleaner = null;

    private NameCleaner getNameCleaner()
            throws NamingException {
        if(nameCleaner == null) {
            nameCleaner = new NameCleaner(getUserVisit());
        }

        return nameCleaner;
    }

    public CurrencyTransfer getCurrency(String currencyIsoName)
            throws NamingException {
        GetCurrencyForm commandForm = AccountingUtil.getHome().getGetCurrencyForm();

        commandForm.setCurrencyIsoName(currencyIsoName);

        CommandResult commandResult = AccountingUtil.getHome().getCurrency(getUserVisit(), commandForm);
        CurrencyTransfer currency = null;
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetCurrencyResult result = (GetCurrencyResult)executionResult.getResult();

            currency = result.getCurrency();
        }

        return currency;
    }

    /**
     *
     * @param minusSign Character to use when looking for a minus sign in the value, may be null if amount is positive-only
     * @param fractionSeparator Character that separates the whole part of the value from the decimal portion
     * @param fractionDigits Number of characters to look for in the decimal portion of the value
     * @param value Value being parsed
     */
    public String parseCurrencyFieldValue(String minusSign, String fractionSeparator, Integer fractionDigits, String value) {
        char []rawValue = value.toCharArray();
        int size = rawValue.length;
        StringBuilder cleanWhole = new StringBuilder(size);
        boolean checkMinusSign = minusSign != null;
        char charMinusSign = checkMinusSign? minusSign.charAt(0): 0;
        boolean minusSignFound = false;
        char charFractionSeparator = fractionSeparator == null? '\u0000': fractionSeparator.charAt(0);
        int intFractionDigits = fractionDigits == null? 0: fractionDigits;
        StringBuilder cleanFraction = new StringBuilder(intFractionDigits);
        boolean separatorFound = false;
        StringBuilder result = new StringBuilder(size);

        for(int i = 0; i < size; i++) {
            char testChar = rawValue[i];

            if(testChar >= '\u0030' && testChar <= '\u0039') {
                if(separatorFound) {
                    cleanFraction.append(testChar);
                    if(cleanFraction.length() == intFractionDigits) {
                        break;
                    }
                } else {
                    cleanWhole.append(testChar);
                }
            } else if(testChar == charFractionSeparator) {
                separatorFound = true;
            } else if(checkMinusSign && testChar == charMinusSign) {
                minusSignFound = true;
            }
        }

        for(int i = cleanFraction.length(); i < intFractionDigits; i++) {
            cleanFraction.append('0');
        }

        if(minusSignFound) {
            result.append('-');
        }

        return result.append(cleanWhole).append(cleanFraction).toString();
    }

    public Long parseCurrencyFieldValue(CurrencyTransfer currency, String value) {
        return Long.valueOf(parseCurrencyFieldValue(currency.getMinusSign(), currency.getFractionSeparator(), currency.getPriceUnitFractionDigits(), value));
    }

    public int getBatchCount(AmazonOrders amazonOrders) {
        return amazonOrders.getAmazonOrders().size();
    }

    public String getBatchAmount(AmazonOrders amazonOrders, CurrencyTransfer currency)
            throws NamingException {
        long batchAmount = 0;

        for(AmazonOrder order : amazonOrders.getAmazonOrders().values()) {
            for(AmazonOrderShipmentGroup orderShipmentGroup : order.getAmazonOrderShipmentGroups().values()) {
                batchAmount = orderShipmentGroup.getAmazonOrderLines().values().stream().map((orderLine) -> orderLine.getTotalPrice()).map((strAmount) -> parseCurrencyFieldValue(currency, strAmount)).map((amount) -> amount).reduce(batchAmount, (accumulator, _item) -> accumulator + _item);
            }
        }

        // TODO: Format according to the currency rules.
        return new StringBuilder(Long.toString(batchAmount / 100)).append('.').append(Long.toString(batchAmount % 100)).toString();
    }

    public String createSalesOrderBatch(String currencyIsoName, String paymentMethodName, String count, String amount)
            throws NamingException {
        String batchName = null;
        CreateSalesOrderBatchForm commandForm = SalesUtil.getHome().getCreateSalesOrderBatchForm();

        commandForm.setCurrencyIsoName(currencyIsoName);
        commandForm.setPaymentMethodName(paymentMethodName);
        commandForm.setCount(count);
        commandForm.setAmount(amount);

        CommandResult commandResult = SalesUtil.getHome().createSalesOrderBatch(getUserVisit(), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            CreateSalesOrderBatchResult result = (CreateSalesOrderBatchResult)executionResult.getResult();

            batchName = result.getBatchName();
        }

        return batchName;
    }

    public void createBatch(AmazonOrders amazonOrders, String currencyIsoName, String paymentMethodName)
            throws NamingException {
        long batchCount = getBatchCount(amazonOrders);

        if(batchCount > 0) {
            CurrencyTransfer currency = getCurrency(currencyIsoName);
            String batchAmount = getBatchAmount(amazonOrders, currency);
            String batchName = createSalesOrderBatch(currencyIsoName, paymentMethodName, Long.toString(batchCount), batchAmount);

            amazonOrders.setBatchName(batchName);

            log.info(indent + "Created sales order batch: " + batchName + " containing " + batchCount + " order" + (batchCount > 1 ? "s" : "") + ", valued at " + currency.getSymbol() + batchAmount + ".");
        } else {
            log.info(indent + "Empty batch.");
        }
    }

    public void createOrderLine(AmazonOrderLine orderLine)
            throws NamingException {
        CreateSalesOrderLineForm createSalesOrderLineForm = SalesUtil.getHome().getCreateSalesOrderLineForm();
        
        createSalesOrderLineForm.setOrderName(orderLine.getOrderShipmentGroup().getOrder().getOrderName());
        createSalesOrderLineForm.setItemName(orderLine.getSku());
        createSalesOrderLineForm.setQuantity(orderLine.getQuantityPurchased());
        createSalesOrderLineForm.setUnitAmount(orderLine.getPrice());
        createSalesOrderLineForm.setDescription(StringUtils.getInstance().left(orderLine.getItemName(), 80));

        CommandResult commandResult = SalesUtil.getHome().createSalesOrderLine(getUserVisit(), createSalesOrderLineForm);
        if(commandResult.hasErrors()) {
            log.error(indent + commandResult);
        } else {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            CreateSalesOrderLineResult createSalesOrderLineResult = (CreateSalesOrderLineResult)executionResult.getResult();
            
            orderLine.setOrderLineSequence(createSalesOrderLineResult.getOrderLineSequence());
        }
    }
    
    public void createOrderLines(String currencyIsoName, String paymentMethodName, AmazonOrderShipmentGroup orderShipmentGroup)
            throws NamingException {
        for(AmazonOrderLine orderLine : orderShipmentGroup.getAmazonOrderLines().values()) {
            createOrderLine(orderLine);
        }
    }

    public void createOrderShipmentGroups(String currencyIsoName, String paymentMethodName, AmazonOrder order)
            throws NamingException {
        for(AmazonOrderShipmentGroup orderShipmentGroup : order.getAmazonOrderShipmentGroups().values()) {
            // TODO: create the shipment group.
            
            createOrderLines(currencyIsoName, paymentMethodName, orderShipmentGroup);
        }
    }

    public List<CustomerResultTransfer> searchCustomers(String customerTypeName, NameResult nameResult)
            throws NamingException {
        List<CustomerResultTransfer> customerResults = null;
        SearchCustomersForm searchCustomersForm = SearchUtil.getHome().getSearchCustomersForm();

        searchCustomersForm.setSearchTypeName(SearchConstants.SearchType_ORDER_ENTRY);
        searchCustomersForm.setCustomerTypeName(customerTypeName);
        searchCustomersForm.setFirstName(nameResult.getFirstName());
        searchCustomersForm.setFirstNameSoundex(Boolean.FALSE.toString());
        searchCustomersForm.setMiddleName(nameResult.getMiddleName());
        searchCustomersForm.setMiddleNameSoundex(Boolean.FALSE.toString());
        searchCustomersForm.setLastName(nameResult.getLastName());
        searchCustomersForm.setLastNameSoundex(Boolean.FALSE.toString());

        CommandResult searchCustomersResult = SearchUtil.getHome().searchCustomers(getUserVisit(), searchCustomersForm);
        if(searchCustomersResult.hasErrors()) {
            log.error(indent + searchCustomersResult);
        } else {
            GetCustomerResultsForm getCustomerResultsForm = SearchUtil.getHome().getGetCustomerResultsForm();

            getCustomerResultsForm.setSearchTypeName(SearchConstants.SearchType_ORDER_ENTRY);

            Set<String> options = new HashSet<>();
            options.add(SearchOptions.CustomerResultIncludeCustomer);
            options.add(PartyOptions.PartyIncludePartyContactMechanisms);
            getCustomerResultsForm.setOptions(ContactPostalAddressUtils.getInstance().addOptions(options));

            CommandResult getCustomerResultsResult = SearchUtil.getHome().getCustomerResults(getUserVisit(), getCustomerResultsForm);
            ExecutionResult executionResult = getCustomerResultsResult.getExecutionResult();
            GetCustomerResultsResult result = (GetCustomerResultsResult)executionResult.getResult();

            customerResults = result.getCustomerResults();
        }

        return customerResults == null ? new ArrayList<>(0) : customerResults;
    }

    public int getPostalAddressCount(List<PartyContactMechanismTransfer> partyContactMechanisms) {
        int count = 0;

        count = partyContactMechanisms.stream().map((partyContactMechanism) -> partyContactMechanism.getContactMechanism().getContactMechanismType().getContactMechanismTypeName().equals(ContactConstants.ContactMechanismType_POSTAL_ADDRESS) ? 1 : 0).reduce(count, Integer::sum);

        return count;
    }

    public void locateCustomer(String customerTypeName, AmazonOrder order)
            throws NamingException {
        String customerName = null;
        String partyName = null;
        NameResult buyerNameResult = getNameCleaner().getCleansedName(order.getBuyerName());
        List<CustomerResultTransfer> customerResults = searchCustomers(customerTypeName, buyerNameResult);

        if(!customerResults.isEmpty()) {
            for(CustomerResultTransfer customerResult : customerResults) {
                CustomerTransfer customer = customerResult.getCustomer();
                List<PartyContactMechanismTransfer> partyContactMechanisms = customer.getPartyContactMechanisms().getList();
                boolean useCustomer = false;

                if(getPostalAddressCount(partyContactMechanisms) == 0) {
                    // If there are no contact mechanisms, then use this customer. Nothing else to compare.
                    log.info(indent + "No postal addresses found, using this customer.");
                    useCustomer = true;
                } else {
                    int matchesRemaining = order.getAmazonOrderShipmentGroups().size();

                    for(AmazonOrderShipmentGroup amazonOrderShipmentGroup : order.getAmazonOrderShipmentGroups().values()) {
                        NameResult recipientNameResult = getNameCleaner().getCleansedName(amazonOrderShipmentGroup.getRecipientName());

                        amazonOrderShipmentGroup.setRecipientNameResult(recipientNameResult);

                        for(PartyContactMechanismTransfer partyContactMechanism : partyContactMechanisms) {
                            // Compare, and if all fields match, decrement matchesRemaining and break out of further comparisons.
                            ContactMechanismTransfer contactMechanism = partyContactMechanism.getContactMechanism();

                            if(contactMechanism.getContactMechanismType().getContactMechanismTypeName().equals(ContactConstants.ContactMechanismType_POSTAL_ADDRESS)) {
                                ContactPostalAddressTransfer contactPostalAddress = contactMechanism.getContactPostalAddress();
                                PersonalTitleTransfer personalTitle = contactPostalAddress.getPersonalTitle();
                                String firstName = contactPostalAddress.getFirstName();
                                String middleName = contactPostalAddress.getMiddleName();
                                String lastName = contactPostalAddress.getLastName();
                                NameSuffixTransfer nameSuffix = contactPostalAddress.getNameSuffix();
                                String address1 = contactPostalAddress.getAddress1();
                                String address2 = contactPostalAddress.getAddress2();
                                String city = contactPostalAddress.getCity();
                                String state = contactPostalAddress.getState();
                                String postalCode = contactPostalAddress.getPostalCode();
                                CountryTransfer countryGeoCode = contactPostalAddress.getCountryGeoCode();

                                if(contactPostalAddress.getCompanyName() == null // Always null.
                                        && contactPostalAddress.getAttention() == null // Always null.
                                        && contactPostalAddress.getAddress3() == null // Always null.
                                        && ((personalTitle == null && recipientNameResult.getPersonalTitleChoice() == null) || (personalTitle != null && personalTitle.getPersonalTitleId().equals(recipientNameResult.getPersonalTitleChoice())))
                                        && ((firstName == null && recipientNameResult.getFirstName() == null) || (firstName != null && firstName.equalsIgnoreCase(recipientNameResult.getFirstName())))
                                        && ((middleName == null && recipientNameResult.getMiddleName() == null) || (middleName != null && middleName.equalsIgnoreCase(recipientNameResult.getMiddleName())))
                                        && ((lastName == null && recipientNameResult.getLastName() == null) || (lastName != null && lastName.equalsIgnoreCase(recipientNameResult.getLastName())))
                                        && ((nameSuffix == null && recipientNameResult.getNameSuffixChoice() == null) || (nameSuffix != null && nameSuffix.getNameSuffixId().equals(recipientNameResult.getNameSuffixChoice())))
                                        && ((address1 == null && amazonOrderShipmentGroup.getShipAddress1() == null) || (address1 != null && address1.equalsIgnoreCase(amazonOrderShipmentGroup.getShipAddress1())))
                                        && ((address2 == null && amazonOrderShipmentGroup.getShipAddress2() == null) || (address2 != null && address2.equalsIgnoreCase(amazonOrderShipmentGroup.getShipAddress2())))
                                        && ((city == null && amazonOrderShipmentGroup.getShipCity() == null) || (city != null && city.equalsIgnoreCase(amazonOrderShipmentGroup.getShipCity())))
                                        && ((state == null && amazonOrderShipmentGroup.getShipState() == null) || (state != null && state.equalsIgnoreCase(amazonOrderShipmentGroup.getShipState())))
                                        && ((postalCode == null && amazonOrderShipmentGroup.getShipZip() == null) || (postalCode != null && postalCode.equalsIgnoreCase(amazonOrderShipmentGroup.getShipZip())))
                                        && countryGeoCode.getGeoCodeAliases().getMap().get(GeoConstants.GeoCodeAliasType_ISO_2_LETTER).getAlias().equals(amazonOrderShipmentGroup.getShipCountry())) {

                                    amazonOrderShipmentGroup.setContactMechanismName(contactMechanism.getContactMechanismName());
                                    matchesRemaining--;
                                    break;
                                }
                            }
                        }
                    }

                    // If all ship-to addresses were found on this customer, use them.
                    useCustomer = matchesRemaining == 0;
                }

                if(useCustomer) {
                    customerName = customer.getCustomerName();
                    partyName = customer.getPartyName();

                    log.info(indent + "Using customer " + customerName + ".");

                    break;
                } else {
                    // Clear the contact mechanism name of any found addresses if the customer isn't going to be used.
                    order.getAmazonOrderShipmentGroups().values().stream().forEach((amazonOrderShipmentGroup) -> {
                        amazonOrderShipmentGroup.setContactMechanismName(null);
                    });
                }
            }
        }

        // New customer needed if partyName is still null.
        if(partyName == null) {
            CreateCustomerForm commandForm = PartyUtil.getHome().getCreateCustomerForm();

            commandForm.setCustomerTypeName(customerTypeName);
            commandForm.setPersonalTitleId(buyerNameResult.getPersonalTitleChoice());
            commandForm.setFirstName(buyerNameResult.getFirstName());
            commandForm.setMiddleName(buyerNameResult.getMiddleName());
            commandForm.setLastName(buyerNameResult.getLastName());
            commandForm.setNameSuffixId(buyerNameResult.getPersonalTitleChoice());
            commandForm.setEmailAddress(order.getBuyerEmail());
            commandForm.setAllowSolicitation(Boolean.FALSE.toString());

            CommandResult commandResult = PartyUtil.getHome().createCustomer(getUserVisit(), commandForm);

            if(commandResult.hasErrors()) {
                log.error(indent + "createCustomer: " + commandResult);
            } else {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                CreateCustomerResult result = (CreateCustomerResult)executionResult.getResult();
                
                customerName = result.getCustomerName();
                partyName = result.getPartyName();
                
                log.info(indent + "Created customer " + customerName + " (\"" + buyerNameResult.getFormattedName() + "\").");
            }
        }

        if(partyName != null) {
            for(AmazonOrderShipmentGroup amazonOrderShipmentGroup : order.getAmazonOrderShipmentGroups().values()) {
                if(amazonOrderShipmentGroup.getContactMechanismName() == null) {
                    CreateContactPostalAddressForm commandForm = ContactUtil.getHome().getCreateContactPostalAddressForm();
                    NameResult recipientNameResult = amazonOrderShipmentGroup.getRecipientNameResult();

                    if(recipientNameResult == null) {
                        recipientNameResult = getNameCleaner().getCleansedName(amazonOrderShipmentGroup.getRecipientName());
                    }

                    commandForm.setPartyName(partyName);
                    commandForm.setAllowSolicitation(Boolean.FALSE.toString());
                    commandForm.setPersonalTitleId(recipientNameResult.getPersonalTitleChoice());
                    commandForm.setFirstName(recipientNameResult.getFirstName());
                    commandForm.setMiddleName(recipientNameResult.getMiddleName());
                    commandForm.setLastName(recipientNameResult.getLastName());
                    commandForm.setNameSuffixId(recipientNameResult.getNameSuffixChoice());
                    commandForm.setAddress1(amazonOrderShipmentGroup.getShipAddress1());
                    commandForm.setAddress2(amazonOrderShipmentGroup.getShipAddress2());
                    commandForm.setCity(amazonOrderShipmentGroup.getShipCity());
                    commandForm.setState(amazonOrderShipmentGroup.getShipState());
                    commandForm.setPostalCode(amazonOrderShipmentGroup.getShipZip());
                    commandForm.setCountryName(amazonOrderShipmentGroup.getShipCountry());
                    commandForm.setIsCommercial(Boolean.FALSE.toString());

                    CommandResult commandResult = ContactUtil.getHome().createContactPostalAddress(getUserVisit(), commandForm);

                    if(commandResult.hasErrors()) {
                        log.error(indent + "createContactPostalAddress: " + commandResult);
                    } else {
                        ExecutionResult executionResult = commandResult.getExecutionResult();
                        CreateContactPostalAddressResult result = (CreateContactPostalAddressResult)executionResult.getResult();
                        String contactMechanismName = result.getContactMechanismName();

                        amazonOrderShipmentGroup.setContactMechanismName(contactMechanismName);

                        log.info(indent + "Created postal address " + contactMechanismName + " for customer " + customerName + ".");
                    }
                }
            }
        }

        order.setCustomerName(customerName);
        order.setPartyName(partyName);
    }

    public void createOrder(String sourceName, String currencyIsoName, String termName, String paymentMethodName, String customerTypeName, AmazonOrder order)
            throws NamingException {
        locateCustomer(customerTypeName, order);

        if(order.getCustomerName() != null) {
            CreateSalesOrderForm commandForm = SalesUtil.getHome().getCreateSalesOrderForm();
            
            commandForm.setBatchName(order.getAmazonOrders().getBatchName());
            commandForm.setSourceName(sourceName);
            commandForm.setCurrencyIsoName(currencyIsoName);
            commandForm.setTermName(termName);
            commandForm.setBillToPartyName(order.getPartyName());
            
            CommandResult commandResult = SalesUtil.getHome().createSalesOrder(getUserVisit(), commandForm);

            if(commandResult.hasErrors()) {
                log.error(indent + "createSalesOrder: " + commandResult);
            } else {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                CreateSalesOrderResult result = (CreateSalesOrderResult)executionResult.getResult();
                String orderName = result.getOrderName();

                order.setOrderName(orderName);

                log.info(indent + "Created sales order " + orderName + ".");
                
                createOrderPaymentPreference(order, paymentMethodName);
            }
            
            createOrderShipmentGroups(currencyIsoName, paymentMethodName, order);
        }
    }

    public void createOrderPaymentPreference(AmazonOrder order, String paymentMethodName)
            throws NamingException {
        CreateSalesOrderPaymentPreferenceForm commandForm = SalesUtil.getHome().getCreateSalesOrderPaymentPreferenceForm();

        commandForm.setOrderName(order.getOrderName());
        commandForm.setPaymentMethodName(paymentMethodName);
        commandForm.setSortOrder("1");
        
        CommandResult commandResult = SalesUtil.getHome().createSalesOrderPaymentPreference(getUserVisit(), commandForm);

        if(commandResult.hasErrors()) {
            log.error(indent + "createSalesOrderPaymentPreference: " + commandResult);
        } else {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            CreateSalesOrderPaymentPreferenceResult result = (CreateSalesOrderPaymentPreferenceResult)executionResult.getResult();

            log.info(indent + "Created sales order payment preference #" + result.getOrderPaymentPreferenceSequence() + ".");
        }
    }
    
    public void setOrderBatchStatus(AmazonOrders orders, String salesOrderBatchStatusChoice)
            throws NamingException {
        SetSalesOrderBatchStatusForm commandForm = SalesUtil.getHome().getSetSalesOrderBatchStatusForm();
        String batchName = orders.getBatchName();

        commandForm.setBatchName(batchName);
        commandForm.setSalesOrderBatchStatusChoice(salesOrderBatchStatusChoice);

        CommandResult commandResult = SalesUtil.getHome().setSalesOrderBatchStatus(getUserVisit(), commandForm);

        if(commandResult.hasErrors()) {
            log.error(indent + "setSalesOrderBatchStatus: " + commandResult);
        } else {
            log.info(indent + "Batch entry status set to " + salesOrderBatchStatusChoice + " for " + batchName + ".");
        }
    }

    public void setOrderBatchStatus(AmazonOrders orders)
            throws NamingException {
        setOrderBatchStatus(orders, SalesOrderBatchStatusConstants.WorkflowDestination_ENTRY_TO_AUDIT);
        setOrderBatchStatus(orders, SalesOrderBatchStatusConstants.WorkflowDestination_AUDIT_TO_COMPLETE);
    }

    public void setOrderStatus(AmazonOrder order)
            throws NamingException {
        SetSalesOrderStatusForm commandForm = SalesUtil.getHome().getSetSalesOrderStatusForm();
        String orderName = order.getOrderName();

        commandForm.setOrderName(orderName);
        commandForm.setSalesOrderStatusChoice(SalesOrderStatusConstants.WorkflowDestination_ENTRY_ALLOCATED_TO_BATCH_AUDIT);

        CommandResult commandResult = SalesUtil.getHome().setSalesOrderStatus(getUserVisit(), commandForm);

        if(commandResult.hasErrors()) {
            log.error(indent + "setSalesOrderStatus: " + commandResult);
        } else {
            log.info(indent + "Order entry completed for " + orderName + ".");
        }
    }
    
    public void createOrders(AmazonOrders amazonOrders, String sourceName, String currencyIsoName, String termName, String paymentMethodName, String customerTypeName,
            Map<String, String> shippingMethods)
        throws NamingException {
        for(AmazonOrder order : amazonOrders.getAmazonOrders().values()) {
            createOrder(sourceName, currencyIsoName, termName, paymentMethodName, customerTypeName, order);

            if(order.getOrderName() != null) {
                // TODO: should check to make sure all the lines were entered as well before attempting.
                setOrderStatus(order);
            }
        }
    }

    private String getProperty(String property, boolean required) {
        String value = configuration.getString(property);
        
        if(value == null && required) {
            log.error(property + " is a required property");
        }
        
        return value;
    }
    
    private void addShippingMethod(Map<String, String> shippingMethods, String shipMethod) {
        String value = getProperty("com.echothree.ui.cli.amazon.shippingMethodName." + shipMethod, true);

        if(value != null) {
            shippingMethods.put(shipMethod, value);
        }
    }
    
    private Map<String, String> getShippingMethods() {
        Map<String, String> shippingMethods = new HashMap<>();
        
        addShippingMethod(shippingMethods, "standard");
        addShippingMethod(shippingMethods, "expedited");
        
        return shippingMethods;
    }

    public void execute()
            throws NamingException {
        String sourceName = getProperty("com.echothree.ui.cli.amazon.sourceName", false);
        String currencyIsoName = getProperty("com.echothree.ui.cli.amazon.currencyIsoName", true);
        String termName = getProperty("com.echothree.ui.cli.amazon.termName", false);
        String paymentMethodName = getProperty("com.echothree.ui.cli.amazon.paymentMethodName", true);
        String customerTypeName = getProperty("com.echothree.ui.cli.amazon.customerTypeName", false);
        Map<String, String> shippingMethods = getShippingMethods();

        if(currencyIsoName != null && paymentMethodName != null && shippingMethods.size() == 2) {
            createBatch(amazonOrders, currencyIsoName, paymentMethodName);

            if(amazonOrders.getBatchName() != null) {
                createOrders(amazonOrders, sourceName, currencyIsoName, termName, paymentMethodName, customerTypeName, shippingMethods);
                
                // TODO: should check to make sure all the orders were entered successfully before attempting.
                setOrderBatchStatus(amazonOrders);
            }
        }
        
        clearUserVisit();
    }

}
