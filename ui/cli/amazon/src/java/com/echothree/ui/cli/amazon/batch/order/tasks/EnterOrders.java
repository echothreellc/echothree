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

package com.echothree.ui.cli.amazon.batch.order.tasks;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.result.GetCurrencyResult;
import com.echothree.control.user.authentication.common.AuthenticationUtil;
import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.control.user.contact.common.result.CreateContactPostalAddressResult;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.CreateCustomerResult;
import com.echothree.control.user.sales.common.SalesUtil;
import com.echothree.control.user.sales.common.result.CreateSalesOrderBatchResult;
import com.echothree.control.user.sales.common.result.CreateSalesOrderLineResult;
import com.echothree.control.user.sales.common.result.CreateSalesOrderPaymentPreferenceResult;
import com.echothree.control.user.sales.common.result.CreateSalesOrderResult;
import com.echothree.control.user.search.common.SearchUtil;
import com.echothree.control.user.search.common.result.GetCustomerResultsResult;
import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.model.control.contact.common.ContactMechanismTypes;
import com.echothree.model.control.contact.common.transfer.PartyContactMechanismTransfer;
import com.echothree.model.control.customer.common.transfer.CustomerResultTransfer;
import com.echothree.model.control.geo.common.GeoConstants;
import com.echothree.model.control.party.common.PartyOptions;
import com.echothree.model.control.sales.common.workflow.SalesOrderBatchStatusConstants;
import com.echothree.model.control.sales.common.workflow.SalesOrderStatusConstants;
import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.control.search.common.SearchTypes;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.ui.cli.amazon.batch.order.content.AmazonOrder;
import com.echothree.ui.cli.amazon.batch.order.content.AmazonOrderLine;
import com.echothree.ui.cli.amazon.batch.order.content.AmazonOrderShipmentGroup;
import com.echothree.ui.cli.amazon.batch.order.content.AmazonOrders;
import com.echothree.util.client.string.NameCleaner;
import com.echothree.util.common.string.ContactPostalAddressUtils;
import com.echothree.util.common.string.NameResult;
import com.echothree.util.common.string.StringUtils;
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

    private final Log LOG = LogFactory.getLog(this.getClass());

    private static final Splitter TAB_SPLITTER = Splitter.on('\t')
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
        var commandForm = AccountingUtil.getHome().getGetCurrencyForm();

        commandForm.setCurrencyIsoName(currencyIsoName);

        var commandResult = AccountingUtil.getHome().getCurrency(getUserVisit(), commandForm);
        CurrencyTransfer currency = null;
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetCurrencyResult)executionResult.getResult();

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
        var rawValue = value.toCharArray();
        var size = rawValue.length;
        var cleanWhole = new StringBuilder(size);
        var checkMinusSign = minusSign != null;
        var charMinusSign = checkMinusSign? minusSign.charAt(0): 0;
        var minusSignFound = false;
        var charFractionSeparator = fractionSeparator == null? '\u0000': fractionSeparator.charAt(0);
        var intFractionDigits = fractionDigits == null? 0: fractionDigits;
        var cleanFraction = new StringBuilder(intFractionDigits);
        var separatorFound = false;
        var result = new StringBuilder(size);

        for(var i = 0; i < size; i++) {
            var testChar = rawValue[i];

            if(testChar >= '0' && testChar <= '9') {
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

        for(var i = cleanFraction.length(); i < intFractionDigits; i++) {
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

        for(var order : amazonOrders.getAmazonOrders().values()) {
            for(var orderShipmentGroup : order.getAmazonOrderShipmentGroups().values()) {
                batchAmount = orderShipmentGroup.getAmazonOrderLines().values().stream().map((orderLine) -> orderLine.getTotalPrice()).map((strAmount) -> parseCurrencyFieldValue(currency, strAmount)).map((amount) -> amount).reduce(batchAmount, (accumulator, _item) -> accumulator + _item);
            }
        }

        // TODO: Format according to the currency rules.
        return new StringBuilder(Long.toString(batchAmount / 100)).append('.').append(Long.toString(batchAmount % 100)).toString();
    }

    public String createSalesOrderBatch(String currencyIsoName, String paymentMethodName, String count, String amount)
            throws NamingException {
        String batchName = null;
        var commandForm = SalesUtil.getHome().getCreateSalesOrderBatchForm();

        commandForm.setCurrencyIsoName(currencyIsoName);
        commandForm.setPaymentMethodName(paymentMethodName);
        commandForm.setCount(count);
        commandForm.setAmount(amount);

        var commandResult = SalesUtil.getHome().createSalesOrderBatch(getUserVisit(), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (CreateSalesOrderBatchResult)executionResult.getResult();

            batchName = result.getBatchName();
        }

        return batchName;
    }

    public void createBatch(AmazonOrders amazonOrders, String currencyIsoName, String paymentMethodName)
            throws NamingException {
        long batchCount = getBatchCount(amazonOrders);

        if(batchCount > 0) {
            var currency = getCurrency(currencyIsoName);
            var batchAmount = getBatchAmount(amazonOrders, currency);
            var batchName = createSalesOrderBatch(currencyIsoName, paymentMethodName, Long.toString(batchCount), batchAmount);

            amazonOrders.setBatchName(batchName);

            LOG.info(indent + "Created sales order batch: " + batchName + " containing " + batchCount + " order" + (batchCount > 1 ? "s" : "") + ", valued at " + currency.getSymbol() + batchAmount + ".");
        } else {
            LOG.info(indent + "Empty batch.");
        }
    }

    public void createOrderLine(AmazonOrderLine orderLine)
            throws NamingException {
        var createSalesOrderLineForm = SalesUtil.getHome().getCreateSalesOrderLineForm();
        
        createSalesOrderLineForm.setOrderName(orderLine.getOrderShipmentGroup().getOrder().getOrderName());
        createSalesOrderLineForm.setItemName(orderLine.getSku());
        createSalesOrderLineForm.setQuantity(orderLine.getQuantityPurchased());
        createSalesOrderLineForm.setUnitAmount(orderLine.getPrice());
        createSalesOrderLineForm.setDescription(StringUtils.getInstance().left(orderLine.getItemName(), 80));

        var commandResult = SalesUtil.getHome().createSalesOrderLine(getUserVisit(), createSalesOrderLineForm);
        if(commandResult.hasErrors()) {
            LOG.error(indent + commandResult);
        } else {
            var executionResult = commandResult.getExecutionResult();
            var createSalesOrderLineResult = (CreateSalesOrderLineResult)executionResult.getResult();
            
            orderLine.setOrderLineSequence(createSalesOrderLineResult.getOrderLineSequence());
        }
    }
    
    public void createOrderLines(String currencyIsoName, String paymentMethodName, AmazonOrderShipmentGroup orderShipmentGroup)
            throws NamingException {
        for(var orderLine : orderShipmentGroup.getAmazonOrderLines().values()) {
            createOrderLine(orderLine);
        }
    }

    public void createOrderShipmentGroups(String currencyIsoName, String paymentMethodName, AmazonOrder order)
            throws NamingException {
        for(var orderShipmentGroup : order.getAmazonOrderShipmentGroups().values()) {
            // TODO: create the shipment group.
            
            createOrderLines(currencyIsoName, paymentMethodName, orderShipmentGroup);
        }
    }

    public List<CustomerResultTransfer> searchCustomers(String customerTypeName, NameResult nameResult)
            throws NamingException {
        List<CustomerResultTransfer> customerResults = null;
        var searchCustomersForm = SearchUtil.getHome().getSearchCustomersForm();

        searchCustomersForm.setSearchTypeName(SearchTypes.ORDER_ENTRY.name());
        searchCustomersForm.setCustomerTypeName(customerTypeName);
        searchCustomersForm.setFirstName(nameResult.getFirstName());
        searchCustomersForm.setFirstNameSoundex(String.valueOf(false));
        searchCustomersForm.setMiddleName(nameResult.getMiddleName());
        searchCustomersForm.setMiddleNameSoundex(String.valueOf(false));
        searchCustomersForm.setLastName(nameResult.getLastName());
        searchCustomersForm.setLastNameSoundex(String.valueOf(false));

        var searchCustomersResult = SearchUtil.getHome().searchCustomers(getUserVisit(), searchCustomersForm);
        if(searchCustomersResult.hasErrors()) {
            LOG.error(indent + searchCustomersResult);
        } else {
            var getCustomerResultsForm = SearchUtil.getHome().getGetCustomerResultsForm();

            getCustomerResultsForm.setSearchTypeName(SearchTypes.ORDER_ENTRY.name());

            Set<String> options = new HashSet<>();
            options.add(SearchOptions.CustomerResultIncludeCustomer);
            options.add(PartyOptions.PartyIncludePartyContactMechanisms);
            getCustomerResultsForm.setOptions(ContactPostalAddressUtils.getInstance().addOptions(options));

            var getCustomerResultsResult = SearchUtil.getHome().getCustomerResults(getUserVisit(), getCustomerResultsForm);
            var executionResult = getCustomerResultsResult.getExecutionResult();
            var result = (GetCustomerResultsResult)executionResult.getResult();

            customerResults = result.getCustomerResults();
        }

        return customerResults == null ? new ArrayList<>(0) : customerResults;
    }

    public int getPostalAddressCount(List<PartyContactMechanismTransfer> partyContactMechanisms) {
        var count = 0;

        count = partyContactMechanisms.stream().map((partyContactMechanism) -> partyContactMechanism.getContactMechanism().getContactMechanismType().getContactMechanismTypeName().equals(ContactMechanismTypes.POSTAL_ADDRESS.name()) ? 1 : 0).reduce(count, Integer::sum);

        return count;
    }

    public void locateCustomer(String customerTypeName, AmazonOrder order)
            throws NamingException {
        String customerName = null;
        String partyName = null;
        var buyerNameResult = getNameCleaner().getCleansedName(order.getBuyerName());
        var customerResults = searchCustomers(customerTypeName, buyerNameResult);

        if(!customerResults.isEmpty()) {
            for(var customerResult : customerResults) {
                var customer = customerResult.getCustomer();
                var partyContactMechanisms = customer.getPartyContactMechanisms().getList();
                var useCustomer = false;

                if(getPostalAddressCount(partyContactMechanisms) == 0) {
                    // If there are no contact mechanisms, then use this customer. Nothing else to compare.
                    LOG.info(indent + "No postal addresses found, using this customer.");
                    useCustomer = true;
                } else {
                    var matchesRemaining = order.getAmazonOrderShipmentGroups().size();

                    for(var amazonOrderShipmentGroup : order.getAmazonOrderShipmentGroups().values()) {
                        var recipientNameResult = getNameCleaner().getCleansedName(amazonOrderShipmentGroup.getRecipientName());

                        amazonOrderShipmentGroup.setRecipientNameResult(recipientNameResult);

                        for(var partyContactMechanism : partyContactMechanisms) {
                            // Compare, and if all fields match, decrement matchesRemaining and break out of further comparisons.
                            var contactMechanism = partyContactMechanism.getContactMechanism();

                            if(contactMechanism.getContactMechanismType().getContactMechanismTypeName().equals(ContactMechanismTypes.POSTAL_ADDRESS.name())) {
                                var contactPostalAddress = contactMechanism.getContactPostalAddress();
                                var personalTitle = contactPostalAddress.getPersonalTitle();
                                var firstName = contactPostalAddress.getFirstName();
                                var middleName = contactPostalAddress.getMiddleName();
                                var lastName = contactPostalAddress.getLastName();
                                var nameSuffix = contactPostalAddress.getNameSuffix();
                                var address1 = contactPostalAddress.getAddress1();
                                var address2 = contactPostalAddress.getAddress2();
                                var city = contactPostalAddress.getCity();
                                var state = contactPostalAddress.getState();
                                var postalCode = contactPostalAddress.getPostalCode();
                                var countryGeoCode = contactPostalAddress.getCountryGeoCode();

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

                    LOG.info(indent + "Using customer " + customerName + ".");

                    break;
                } else {
                    // Clear the contact mechanism name of any found addresses if the customer isn't going to be used.
                    order.getAmazonOrderShipmentGroups().values().forEach((amazonOrderShipmentGroup) -> {
                        amazonOrderShipmentGroup.setContactMechanismName(null);
                    });
                }
            }
        }

        // New customer needed if partyName is still null.
        if(partyName == null) {
            var commandForm = PartyUtil.getHome().getCreateCustomerForm();

            commandForm.setCustomerTypeName(customerTypeName);
            commandForm.setPersonalTitleId(buyerNameResult.getPersonalTitleChoice());
            commandForm.setFirstName(buyerNameResult.getFirstName());
            commandForm.setMiddleName(buyerNameResult.getMiddleName());
            commandForm.setLastName(buyerNameResult.getLastName());
            commandForm.setNameSuffixId(buyerNameResult.getPersonalTitleChoice());
            commandForm.setEmailAddress(order.getBuyerEmail());
            commandForm.setAllowSolicitation(String.valueOf(false));

            var commandResult = PartyUtil.getHome().createCustomer(getUserVisit(), commandForm);

            if(commandResult.hasErrors()) {
                LOG.error(indent + "createCustomer: " + commandResult);
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (CreateCustomerResult)executionResult.getResult();
                
                customerName = result.getCustomerName();
                partyName = result.getPartyName();
                
                LOG.info(indent + "Created customer " + customerName + " (\"" + buyerNameResult.getFormattedName() + "\").");
            }
        }

        if(partyName != null) {
            for(var amazonOrderShipmentGroup : order.getAmazonOrderShipmentGroups().values()) {
                if(amazonOrderShipmentGroup.getContactMechanismName() == null) {
                    var commandForm = ContactUtil.getHome().getCreateContactPostalAddressForm();
                    var recipientNameResult = amazonOrderShipmentGroup.getRecipientNameResult();

                    if(recipientNameResult == null) {
                        recipientNameResult = getNameCleaner().getCleansedName(amazonOrderShipmentGroup.getRecipientName());
                    }

                    commandForm.setPartyName(partyName);
                    commandForm.setAllowSolicitation(String.valueOf(false));
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
                    commandForm.setIsCommercial(String.valueOf(false));

                    var commandResult = ContactUtil.getHome().createContactPostalAddress(getUserVisit(), commandForm);

                    if(commandResult.hasErrors()) {
                        LOG.error(indent + "createContactPostalAddress: " + commandResult);
                    } else {
                        var executionResult = commandResult.getExecutionResult();
                        var result = (CreateContactPostalAddressResult)executionResult.getResult();
                        var contactMechanismName = result.getContactMechanismName();

                        amazonOrderShipmentGroup.setContactMechanismName(contactMechanismName);

                        LOG.info(indent + "Created postal address " + contactMechanismName + " for customer " + customerName + ".");
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
            var commandForm = SalesUtil.getHome().getCreateSalesOrderForm();
            
            commandForm.setBatchName(order.getAmazonOrders().getBatchName());
            commandForm.setSourceName(sourceName);
            commandForm.setCurrencyIsoName(currencyIsoName);
            commandForm.setTermName(termName);
            commandForm.setBillToPartyName(order.getPartyName());

            var commandResult = SalesUtil.getHome().createSalesOrder(getUserVisit(), commandForm);

            if(commandResult.hasErrors()) {
                LOG.error(indent + "createSalesOrder: " + commandResult);
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (CreateSalesOrderResult)executionResult.getResult();
                var orderName = result.getOrderName();

                order.setOrderName(orderName);

                LOG.info(indent + "Created sales order " + orderName + ".");
                
                createOrderPaymentPreference(order, paymentMethodName);
            }
            
            createOrderShipmentGroups(currencyIsoName, paymentMethodName, order);
        }
    }

    public void createOrderPaymentPreference(AmazonOrder order, String paymentMethodName)
            throws NamingException {
        var commandForm = SalesUtil.getHome().getCreateSalesOrderPaymentPreferenceForm();

        commandForm.setOrderName(order.getOrderName());
        commandForm.setPaymentMethodName(paymentMethodName);
        commandForm.setSortOrder("1");

        var commandResult = SalesUtil.getHome().createSalesOrderPaymentPreference(getUserVisit(), commandForm);

        if(commandResult.hasErrors()) {
            LOG.error(indent + "createSalesOrderPaymentPreference: " + commandResult);
        } else {
            var executionResult = commandResult.getExecutionResult();
            var result = (CreateSalesOrderPaymentPreferenceResult)executionResult.getResult();

            LOG.info(indent + "Created sales order payment preference #" + result.getOrderPaymentPreferenceSequence() + ".");
        }
    }
    
    public void setOrderBatchStatus(AmazonOrders orders, String salesOrderBatchStatusChoice)
            throws NamingException {
        var commandForm = SalesUtil.getHome().getSetSalesOrderBatchStatusForm();
        var batchName = orders.getBatchName();

        commandForm.setBatchName(batchName);
        commandForm.setSalesOrderBatchStatusChoice(salesOrderBatchStatusChoice);

        var commandResult = SalesUtil.getHome().setSalesOrderBatchStatus(getUserVisit(), commandForm);

        if(commandResult.hasErrors()) {
            LOG.error(indent + "setSalesOrderBatchStatus: " + commandResult);
        } else {
            LOG.info(indent + "Batch entry status set to " + salesOrderBatchStatusChoice + " for " + batchName + ".");
        }
    }

    public void setOrderBatchStatus(AmazonOrders orders)
            throws NamingException {
        setOrderBatchStatus(orders, SalesOrderBatchStatusConstants.WorkflowDestination_ENTRY_TO_AUDIT);
        setOrderBatchStatus(orders, SalesOrderBatchStatusConstants.WorkflowDestination_AUDIT_TO_COMPLETE);
    }

    public void setOrderStatus(AmazonOrder order)
            throws NamingException {
        var commandForm = SalesUtil.getHome().getSetSalesOrderStatusForm();
        var orderName = order.getOrderName();

        commandForm.setOrderName(orderName);
        commandForm.setSalesOrderStatusChoice(SalesOrderStatusConstants.WorkflowDestination_ENTRY_ALLOCATED_TO_BATCH_AUDIT);

        var commandResult = SalesUtil.getHome().setSalesOrderStatus(getUserVisit(), commandForm);

        if(commandResult.hasErrors()) {
            LOG.error(indent + "setSalesOrderStatus: " + commandResult);
        } else {
            LOG.info(indent + "Order entry completed for " + orderName + ".");
        }
    }
    
    public void createOrders(AmazonOrders amazonOrders, String sourceName, String currencyIsoName, String termName, String paymentMethodName, String customerTypeName,
            Map<String, String> shippingMethods)
        throws NamingException {
        for(var order : amazonOrders.getAmazonOrders().values()) {
            createOrder(sourceName, currencyIsoName, termName, paymentMethodName, customerTypeName, order);

            if(order.getOrderName() != null) {
                // TODO: should check to make sure all the lines were entered as well before attempting.
                setOrderStatus(order);
            }
        }
    }

    private String getProperty(String property, boolean required) {
        var value = configuration.getString(property);
        
        if(value == null && required) {
            LOG.error(property + " is a required property");
        }
        
        return value;
    }
    
    private void addShippingMethod(Map<String, String> shippingMethods, String shipMethod) {
        var value = getProperty("com.echothree.ui.cli.amazon.shippingMethodName." + shipMethod, true);

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
        var sourceName = getProperty("com.echothree.ui.cli.amazon.sourceName", false);
        var currencyIsoName = getProperty("com.echothree.ui.cli.amazon.currencyIsoName", true);
        var termName = getProperty("com.echothree.ui.cli.amazon.termName", false);
        var paymentMethodName = getProperty("com.echothree.ui.cli.amazon.paymentMethodName", true);
        var customerTypeName = getProperty("com.echothree.ui.cli.amazon.customerTypeName", false);
        var shippingMethods = getShippingMethods();

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
