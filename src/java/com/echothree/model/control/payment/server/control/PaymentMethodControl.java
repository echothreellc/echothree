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

package com.echothree.model.control.payment.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.order.server.control.OrderPaymentPreferenceControl;
import com.echothree.model.control.payment.common.choice.PaymentMethodChoicesBean;
import com.echothree.model.control.payment.common.transfer.PaymentMethodDescriptionTransfer;
import com.echothree.model.control.payment.common.transfer.PaymentMethodTransfer;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.model.data.payment.server.entity.PaymentMethodCheck;
import com.echothree.model.data.payment.server.entity.PaymentMethodCreditCard;
import com.echothree.model.data.payment.server.entity.PaymentMethodDescription;
import com.echothree.model.data.payment.server.entity.PaymentMethodType;
import com.echothree.model.data.payment.server.entity.PaymentProcessor;
import com.echothree.model.data.payment.server.factory.PaymentMethodCheckFactory;
import com.echothree.model.data.payment.server.factory.PaymentMethodCreditCardFactory;
import com.echothree.model.data.payment.server.factory.PaymentMethodDescriptionFactory;
import com.echothree.model.data.payment.server.factory.PaymentMethodDetailFactory;
import com.echothree.model.data.payment.server.factory.PaymentMethodFactory;
import com.echothree.model.data.payment.server.value.PaymentMethodCheckValue;
import com.echothree.model.data.payment.server.value.PaymentMethodCreditCardValue;
import com.echothree.model.data.payment.server.value.PaymentMethodDescriptionValue;
import com.echothree.model.data.payment.server.value.PaymentMethodDetailValue;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import com.echothree.util.server.cdi.CommandScope;

@CommandScope
public class PaymentMethodControl
        extends BasePaymentControl {

    /** Creates a new instance of PaymentMethodControl */
    protected PaymentMethodControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Payment Methods
    // --------------------------------------------------------------------------------
    
    public PaymentMethod createPaymentMethod(String paymentMethodName, PaymentMethodType paymentMethodType, PaymentProcessor paymentProcessor,
            Selector itemSelector, Selector salesOrderItemSelector, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultPaymentMethod = getDefaultPaymentMethod();
        var defaultFound = defaultPaymentMethod != null;
        
        if(defaultFound && isDefault) {
            var defaultPaymentMethodDetailValue = getDefaultPaymentMethodDetailValueForUpdate();
            
            defaultPaymentMethodDetailValue.setIsDefault(false);
            updatePaymentMethodFromValue(defaultPaymentMethodDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var paymentMethod = PaymentMethodFactory.getInstance().create();
        var paymentMethodDetail = PaymentMethodDetailFactory.getInstance().create(paymentMethod, paymentMethodName, paymentMethodType,
                paymentProcessor, itemSelector, salesOrderItemSelector, isDefault, sortOrder, session.getStartTimeLong(), Session.MAX_TIME_LONG);
        
        // Convert to R/W
        paymentMethod = PaymentMethodFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, paymentMethod.getPrimaryKey());
        paymentMethod.setActiveDetail(paymentMethodDetail);
        paymentMethod.setLastDetail(paymentMethodDetail);
        paymentMethod.store();
        
        sendEvent(paymentMethod.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return paymentMethod;
    }
    
    public PaymentMethodDetailValue getPaymentMethodDetailValueForUpdate(PaymentMethod paymentMethod) {
        return paymentMethod.getLastDetailForUpdate().getPaymentMethodDetailValue().clone();
    }
    
    public PaymentMethod getPaymentMethodByName(String paymentMethodName, EntityPermission entityPermission) {
        PaymentMethod paymentMethod;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethods, paymentmethoddetails " +
                        "WHERE pm_activedetailid = pmdt_paymentmethoddetailid AND pmdt_paymentmethodname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethods, paymentmethoddetails " +
                        "WHERE pm_activedetailid = pmdt_paymentmethoddetailid AND pmdt_paymentmethodname = ? " +
                        "FOR UPDATE";
            }

            var ps = PaymentMethodFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, paymentMethodName);
            
            paymentMethod = PaymentMethodFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return paymentMethod;
    }
    
    public PaymentMethod getPaymentMethodByName(String paymentMethodName) {
        return getPaymentMethodByName(paymentMethodName, EntityPermission.READ_ONLY);
    }
    
    public PaymentMethod getPaymentMethodByNameForUpdate(String paymentMethodName) {
        return getPaymentMethodByName(paymentMethodName, EntityPermission.READ_WRITE);
    }
    
    public PaymentMethodDetailValue getPaymentMethodDetailValueByNameForUpdate(String paymentMethodName) {
        return getPaymentMethodDetailValueForUpdate(getPaymentMethodByNameForUpdate(paymentMethodName));
    }
    
    private List<PaymentMethod> getPaymentMethods(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM paymentmethods, paymentmethoddetails " +
                    "WHERE pm_activedetailid = pmdt_paymentmethoddetailid " +
                    "ORDER BY pmdt_sortorder, pmdt_paymentmethodname";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM paymentmethods, paymentmethoddetails " +
                    "WHERE pm_activedetailid = pmdt_paymentmethoddetailid " +
                    "FOR UPDATE";
        }

        var ps = PaymentMethodFactory.getInstance().prepareStatement(query);
        
        return PaymentMethodFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<PaymentMethod> getPaymentMethods() {
        return getPaymentMethods(EntityPermission.READ_ONLY);
    }
    
    public List<PaymentMethod> getPaymentMethodsForUpdate() {
        return getPaymentMethods(EntityPermission.READ_WRITE);
    }
    
    private List<PaymentMethod> getPaymentMethodsByPaymentMethodType(PaymentMethodType paymentMethodType, EntityPermission entityPermission) {
        List<PaymentMethod> paymentMethod;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethods, paymentmethoddetails " +
                        "WHERE pm_activedetailid = pmdt_paymentmethoddetailid AND pmdt_pmtyp_paymentmethodtypeid = ? " +
                        "ORDER BY pmdt_sortorder, pmdt_paymentmethodname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethods, paymentmethoddetails " +
                        "WHERE pm_activedetailid = pmdt_paymentmethoddetailid AND pmdt_pmtyp_paymentmethodtypeid = ? " +
                        "FOR UPDATE";
            }

            var ps = PaymentMethodFactory.getInstance().prepareStatement(query);

            ps.setLong(1, paymentMethodType.getPrimaryKey().getEntityId());

            paymentMethod = PaymentMethodFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return paymentMethod;
    }

    public List<PaymentMethod> getPaymentMethodsByPaymentMethodType(PaymentMethodType paymentMethodType) {
        return getPaymentMethodsByPaymentMethodType(paymentMethodType, EntityPermission.READ_ONLY);
    }

    public List<PaymentMethod> getPaymentMethodsByPaymentMethodTypeForUpdate(PaymentMethodType paymentMethodType) {
        return getPaymentMethodsByPaymentMethodType(paymentMethodType, EntityPermission.READ_WRITE);
    }

    private List<PaymentMethod> getPaymentMethodsByPaymentProcessor(PaymentProcessor paymentProcessor, EntityPermission entityPermission) {
        List<PaymentMethod> paymentMethod;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethods, paymentmethoddetails " +
                        "WHERE pm_activedetailid = pmdt_paymentmethoddetailid AND pmdt_pprc_paymentprocessorid = ? " +
                        "ORDER BY pmdt_sortorder, pmdt_paymentmethodname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethods, paymentmethoddetails " +
                        "WHERE pm_activedetailid = pmdt_paymentmethoddetailid AND pmdt_pprc_paymentprocessorid = ? " +
                        "FOR UPDATE";
            }

            var ps = PaymentMethodFactory.getInstance().prepareStatement(query);

            ps.setLong(1, paymentProcessor.getPrimaryKey().getEntityId());

            paymentMethod = PaymentMethodFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return paymentMethod;
    }

    public List<PaymentMethod> getPaymentMethodsByPaymentProcessor(PaymentProcessor paymentProcessor) {
        return getPaymentMethodsByPaymentProcessor(paymentProcessor, EntityPermission.READ_ONLY);
    }

    public List<PaymentMethod> getPaymentMethodsByPaymentProcessorForUpdate(PaymentProcessor paymentProcessor) {
        return getPaymentMethodsByPaymentProcessor(paymentProcessor, EntityPermission.READ_WRITE);
    }

    private PaymentMethod getDefaultPaymentMethod(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM paymentmethods, paymentmethoddetails " +
                    "WHERE pm_activedetailid = pmdt_paymentmethoddetailid AND pmdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM paymentmethods, paymentmethoddetails " +
                    "WHERE pm_activedetailid = pmdt_paymentmethoddetailid AND pmdt_isdefault = 1 " +
                    "FOR UPDATE";
        }

        var ps = PaymentMethodFactory.getInstance().prepareStatement(query);
        
        return PaymentMethodFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public PaymentMethod getDefaultPaymentMethod() {
        return getDefaultPaymentMethod(EntityPermission.READ_ONLY);
    }
    
    public PaymentMethod getDefaultPaymentMethodForUpdate() {
        return getDefaultPaymentMethod(EntityPermission.READ_WRITE);
    }
    
    public PaymentMethodDetailValue getDefaultPaymentMethodDetailValueForUpdate() {
        return getPaymentMethodDetailValueForUpdate(getDefaultPaymentMethodForUpdate());
    }
    
    public PaymentMethodChoicesBean getPaymentMethodChoices(String defaultPaymentMethodChoice, Language language, boolean allowNullChoice,
            List<PaymentMethod> paymentMethods) {
        var size = paymentMethods.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");
        }

        for(var paymentMethod : paymentMethods) {
            var paymentMethodDetail = paymentMethod.getLastDetail();
            var label = getBestPaymentMethodDescription(paymentMethod, language);
            var value = paymentMethodDetail.getPaymentMethodName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultPaymentMethodChoice != null && defaultPaymentMethodChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && paymentMethodDetail.getIsDefault()))
                defaultValue = value;
        }

        return new PaymentMethodChoicesBean(labels, values, defaultValue);
    }

    public PaymentMethodChoicesBean getPaymentMethodChoices(String defaultPaymentMethodChoice, Language language, boolean allowNullChoice) {
        return getPaymentMethodChoices(defaultPaymentMethodChoice, language, allowNullChoice, getPaymentMethods());
    }

    public PaymentMethodChoicesBean getPaymentMethodChoicesByPaymentMethodType(String defaultPaymentMethodChoice, Language language, boolean allowNullChoice,
            PaymentMethodType paymentMethodType) {
        return getPaymentMethodChoices(defaultPaymentMethodChoice, language, allowNullChoice, getPaymentMethodsByPaymentMethodType(paymentMethodType));
    }

    public PaymentMethodTransfer getPaymentMethodTransfer(UserVisit userVisit, PaymentMethod paymentMethod) {
        return paymentMethodTransferCache.getTransfer(userVisit, paymentMethod);
    }
    
    public List<PaymentMethodTransfer> getPaymentMethodTransfers(UserVisit userVisit, Collection<PaymentMethod> paymentMethods) {
        List<PaymentMethodTransfer> paymentMethodTransfers = new ArrayList<>(paymentMethods.size());

        paymentMethods.forEach((paymentMethod) ->
                paymentMethodTransfers.add(paymentMethodTransferCache.getTransfer(userVisit, paymentMethod))
        );

        return paymentMethodTransfers;
    }

    public List<PaymentMethodTransfer> getPaymentMethodTransfers(UserVisit userVisit) {
        return getPaymentMethodTransfers(userVisit, getPaymentMethods());
    }

    public List<PaymentMethodTransfer> getPaymentMethodTransfersByPaymentMethodType(UserVisit userVisit, PaymentMethodType paymentMethodType) {
        return getPaymentMethodTransfers(userVisit, getPaymentMethodsByPaymentMethodType(paymentMethodType));
    }

    private void updatePaymentMethodFromValue(PaymentMethodDetailValue paymentMethodDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(paymentMethodDetailValue.hasBeenModified()) {
            var paymentMethod = PaymentMethodFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     paymentMethodDetailValue.getPaymentMethodPK());
            var paymentMethodDetail = paymentMethod.getActiveDetailForUpdate();
            
            paymentMethodDetail.setThruTime(session.getStartTimeLong());
            paymentMethodDetail.store();

            var paymentMethodPK = paymentMethodDetail.getPaymentMethodPK(); // Not updated
            var paymentMethodName = paymentMethodDetailValue.getPaymentMethodName();
            var paymentMethodTypePK = paymentMethodDetail.getPaymentMethodTypePK(); // Not updated
            var paymentProcessorPK = paymentMethodDetailValue.getPaymentProcessorPK();
            var itemSelectorPK = paymentMethodDetailValue.getItemSelectorPK();
            var salesOrderItemSelectorPK = paymentMethodDetailValue.getSalesOrderItemSelectorPK();
            var isDefault = paymentMethodDetailValue.getIsDefault();
            var sortOrder = paymentMethodDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultPaymentMethod = getDefaultPaymentMethod();
                var defaultFound = defaultPaymentMethod != null && !defaultPaymentMethod.equals(paymentMethod);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultPaymentMethodDetailValue = getDefaultPaymentMethodDetailValueForUpdate();
                    
                    defaultPaymentMethodDetailValue.setIsDefault(false);
                    updatePaymentMethodFromValue(defaultPaymentMethodDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            paymentMethodDetail = PaymentMethodDetailFactory.getInstance().create(paymentMethodPK, paymentMethodName, paymentMethodTypePK, paymentProcessorPK,
                    itemSelectorPK, salesOrderItemSelectorPK, isDefault, sortOrder, session.getStartTimeLong(), Session.MAX_TIME_LONG);
            
            paymentMethod.setActiveDetail(paymentMethodDetail);
            paymentMethod.setLastDetail(paymentMethodDetail);
            
            sendEvent(paymentMethodPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updatePaymentMethodFromValue(PaymentMethodDetailValue paymentMethodDetailValue, BasePK updatedBy) {
        updatePaymentMethodFromValue(paymentMethodDetailValue, true, updatedBy);
    }
    
    public void deletePaymentMethod(PaymentMethod paymentMethod, BasePK deletedBy) {
        var customerControl = Session.getModelController(CustomerControl.class);
        var orderPaymentPreferenceControl = Session.getModelController(OrderPaymentPreferenceControl.class);
        var partyPaymentMethodControl = Session.getModelController(PartyPaymentMethodControl.class);

        customerControl.deleteCustomerTypePaymentMethodsByPaymentMethod(paymentMethod, deletedBy);
        orderPaymentPreferenceControl.deleteOrderPaymentPreferencesByPaymentMethod(paymentMethod, deletedBy);
        partyPaymentMethodControl.deletePartyPaymentMethodsByPaymentMethod(paymentMethod, deletedBy);
        deletePaymentMethodDescriptionsByPaymentMethod(paymentMethod, deletedBy);

        var paymentMethodDetail = paymentMethod.getLastDetailForUpdate();
        paymentMethodDetail.setThruTime(session.getStartTimeLong());
        paymentMethodDetail.store();
        paymentMethod.setActiveDetail(null);
        
        // Check for default, and pick one if necessary
        var defaultPaymentMethod = getDefaultPaymentMethod();
        if(defaultPaymentMethod == null) {
            var paymentMethods = getPaymentMethodsForUpdate();
            
            if(!paymentMethods.isEmpty()) {
                var iter = paymentMethods.iterator();
                if(iter.hasNext()) {
                    defaultPaymentMethod = iter.next();
                }
                var paymentMethodDetailValue = Objects.requireNonNull(defaultPaymentMethod).getLastDetailForUpdate().getPaymentMethodDetailValue().clone();
                
                paymentMethodDetailValue.setIsDefault(true);
                updatePaymentMethodFromValue(paymentMethodDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(paymentMethod.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deletePaymentMethodsByPaymentProcessor(PaymentProcessor paymentProcessor, BasePK deletedBy) {
        var paymentMethods = getPaymentMethodsByPaymentProcessorForUpdate(paymentProcessor);
        
        paymentMethods.forEach((paymentMethod) -> 
                deletePaymentMethod(paymentMethod, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Payment Method Descriptions
    // --------------------------------------------------------------------------------
    
    public PaymentMethodDescription createPaymentMethodDescription(PaymentMethod paymentMethod, Language language,
            String description, BasePK createdBy) {
        var paymentMethodDescription = PaymentMethodDescriptionFactory.getInstance().create(session,
                paymentMethod, language, description, session.getStartTimeLong(), Session.MAX_TIME_LONG);
        
        sendEvent(paymentMethod.getPrimaryKey(), EventTypes.MODIFY, paymentMethodDescription.getPrimaryKey(),
                EventTypes.CREATE, createdBy);
        
        return paymentMethodDescription;
    }
    
    private PaymentMethodDescription getPaymentMethodDescription(PaymentMethod paymentMethod, Language language,
            EntityPermission entityPermission) {
        PaymentMethodDescription paymentMethodDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethoddescriptions " +
                        "WHERE pmd_pm_paymentmethodid = ? AND pmd_lang_languageid = ? AND pmd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethoddescriptions " +
                        "WHERE pmd_pm_paymentmethodid = ? AND pmd_lang_languageid = ? AND pmd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = PaymentMethodDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, paymentMethod.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            paymentMethodDescription = PaymentMethodDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return paymentMethodDescription;
    }
    
    public PaymentMethodDescription getPaymentMethodDescription(PaymentMethod paymentMethod, Language language) {
        return getPaymentMethodDescription(paymentMethod, language, EntityPermission.READ_ONLY);
    }
    
    public PaymentMethodDescription getPaymentMethodDescriptionForUpdate(PaymentMethod paymentMethod, Language language) {
        return getPaymentMethodDescription(paymentMethod, language, EntityPermission.READ_WRITE);
    }
    
    public PaymentMethodDescriptionValue getPaymentMethodDescriptionValue(PaymentMethodDescription paymentMethodDescription) {
        return paymentMethodDescription == null? null: paymentMethodDescription.getPaymentMethodDescriptionValue().clone();
    }
    
    public PaymentMethodDescriptionValue getPaymentMethodDescriptionValueForUpdate(PaymentMethod paymentMethod, Language language) {
        return getPaymentMethodDescriptionValue(getPaymentMethodDescriptionForUpdate(paymentMethod, language));
    }
    
    private List<PaymentMethodDescription> getPaymentMethodDescriptions(PaymentMethod paymentMethod, EntityPermission entityPermission) {
        List<PaymentMethodDescription> paymentMethodDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethoddescriptions, languages " +
                        "WHERE pmd_pm_paymentmethodid = ? AND pmd_thrutime = ? AND pmd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethoddescriptions " +
                        "WHERE pmd_pm_paymentmethodid = ? AND pmd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = PaymentMethodDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, paymentMethod.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            paymentMethodDescriptions = PaymentMethodDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return paymentMethodDescriptions;
    }
    
    public List<PaymentMethodDescription> getPaymentMethodDescriptions(PaymentMethod paymentMethod) {
        return getPaymentMethodDescriptions(paymentMethod, EntityPermission.READ_ONLY);
    }
    
    public List<PaymentMethodDescription> getPaymentMethodDescriptionsForUpdate(PaymentMethod paymentMethod) {
        return getPaymentMethodDescriptions(paymentMethod, EntityPermission.READ_WRITE);
    }
    
    public String getBestPaymentMethodDescription(PaymentMethod paymentMethod, Language language) {
        String description;
        var paymentMethodDescription = getPaymentMethodDescription(paymentMethod, language);
        
        if(paymentMethodDescription == null && !language.getIsDefault()) {
            paymentMethodDescription = getPaymentMethodDescription(paymentMethod, partyControl.getDefaultLanguage());
        }
        
        if(paymentMethodDescription == null) {
            description = paymentMethod.getLastDetail().getPaymentMethodName();
        } else {
            description = paymentMethodDescription.getDescription();
        }
        
        return description;
    }
    
    public PaymentMethodDescriptionTransfer getPaymentMethodDescriptionTransfer(UserVisit userVisit, PaymentMethodDescription paymentMethodDescription) {
        return paymentMethodDescriptionTransferCache.getTransfer(userVisit, paymentMethodDescription);
    }
    
    public List<PaymentMethodDescriptionTransfer> getPaymentMethodDescriptionTransfers(UserVisit userVisit, PaymentMethod paymentMethod) {
        var paymentMethodDescriptions = getPaymentMethodDescriptions(paymentMethod);
        List<PaymentMethodDescriptionTransfer> paymentMethodDescriptionTransfers = new ArrayList<>(paymentMethodDescriptions.size());
        
        paymentMethodDescriptions.forEach((paymentMethodDescription) ->
                paymentMethodDescriptionTransfers.add(paymentMethodDescriptionTransferCache.getTransfer(userVisit, paymentMethodDescription))
        );
        
        return paymentMethodDescriptionTransfers;
    }
    
    public void updatePaymentMethodDescriptionFromValue(PaymentMethodDescriptionValue paymentMethodDescriptionValue, BasePK updatedBy) {
        if(paymentMethodDescriptionValue.hasBeenModified()) {
            var paymentMethodDescription = PaymentMethodDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     paymentMethodDescriptionValue.getPrimaryKey());
            
            paymentMethodDescription.setThruTime(session.getStartTimeLong());
            paymentMethodDescription.store();

            var paymentMethod = paymentMethodDescription.getPaymentMethod();
            var language = paymentMethodDescription.getLanguage();
            var description = paymentMethodDescriptionValue.getDescription();
            
            paymentMethodDescription = PaymentMethodDescriptionFactory.getInstance().create(paymentMethod, language,
                    description, session.getStartTimeLong(), Session.MAX_TIME_LONG);
            
            sendEvent(paymentMethod.getPrimaryKey(), EventTypes.MODIFY,
                    paymentMethodDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deletePaymentMethodDescription(PaymentMethodDescription paymentMethodDescription, BasePK deletedBy) {
        paymentMethodDescription.setThruTime(session.getStartTimeLong());
        
        sendEvent(paymentMethodDescription.getPaymentMethodPK(), EventTypes.MODIFY,
                paymentMethodDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deletePaymentMethodDescriptionsByPaymentMethod(PaymentMethod paymentMethod, BasePK deletedBy) {
        var paymentMethodDescriptions = getPaymentMethodDescriptionsForUpdate(paymentMethod);
        
        paymentMethodDescriptions.forEach((paymentMethodDescription) -> 
                deletePaymentMethodDescription(paymentMethodDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Payment Method Checks
    // --------------------------------------------------------------------------------
    
    public PaymentMethodCheck createPaymentMethodCheck(PaymentMethod paymentMethod, Integer holdDays, BasePK createdBy) {
        var paymentMethodCheck = PaymentMethodCheckFactory.getInstance().create(paymentMethod, holdDays,
                session.getStartTimeLong(), Session.MAX_TIME_LONG);
        
        sendEvent(paymentMethod.getPrimaryKey(), EventTypes.MODIFY, paymentMethodCheck.getPrimaryKey(),
                EventTypes.CREATE, createdBy);
        
        return paymentMethodCheck;
    }
    
    private PaymentMethodCheck getPaymentMethodCheck(PaymentMethod paymentMethod, EntityPermission entityPermission) {
        PaymentMethodCheck paymentMethodCheck;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethodchecks " +
                        "WHERE pmchk_pm_paymentmethodid = ? AND pmchk_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethodchecks " +
                        "WHERE pmchk_pm_paymentmethodid = ? AND pmchk_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = PaymentMethodCheckFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, paymentMethod.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            paymentMethodCheck = PaymentMethodCheckFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return paymentMethodCheck;
    }
    
    public PaymentMethodCheck getPaymentMethodCheck(PaymentMethod paymentMethod) {
        return getPaymentMethodCheck(paymentMethod, EntityPermission.READ_ONLY);
    }
    
    public PaymentMethodCheck getPaymentMethodCheckForUpdate(PaymentMethod paymentMethod) {
        return getPaymentMethodCheck(paymentMethod, EntityPermission.READ_WRITE);
    }
    
    public PaymentMethodCheckValue getPaymentMethodCheckValueForUpdate(PaymentMethod paymentMethod) {
        var paymentMethodCheck = getPaymentMethodCheckForUpdate(paymentMethod);
        
        return paymentMethodCheck == null? null: paymentMethodCheck.getPaymentMethodCheckValue().clone();
    }
    
    public void updatePaymentMethodCheckFromValue(PaymentMethodCheckValue paymentMethodCheckValue, BasePK updatedBy) {
        if(paymentMethodCheckValue.hasBeenModified()) {
            var paymentMethodCheck = PaymentMethodCheckFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     paymentMethodCheckValue.getPrimaryKey());
            
            paymentMethodCheck.setThruTime(session.getStartTimeLong());
            paymentMethodCheck.store();

            var paymentMethodPK = paymentMethodCheck.getPaymentMethodPK(); // Not updated
            var holdDays = paymentMethodCheckValue.getHoldDays();
            
            paymentMethodCheck = PaymentMethodCheckFactory.getInstance().create(paymentMethodPK, holdDays,
                    session.getStartTimeLong(), Session.MAX_TIME_LONG);
            
            sendEvent(paymentMethodPK, EventTypes.MODIFY, paymentMethodCheck.getPrimaryKey(),
                    EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deletePaymentMethodCheck(PaymentMethodCheck paymentMethodCheck, BasePK deletedBy) {
        paymentMethodCheck.setThruTime(session.getStartTimeLong());
        
        sendEvent(paymentMethodCheck.getPaymentMethodPK(), EventTypes.MODIFY,
                paymentMethodCheck.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Payment Method Credit Cards
    // --------------------------------------------------------------------------------
    
    public PaymentMethodCreditCard createPaymentMethodCreditCard(PaymentMethod paymentMethod, Boolean requestNameOnCard,
            Boolean requireNameOnCard, Boolean checkCardNumber, Boolean requestExpirationDate, Boolean requireExpirationDate,
            Boolean checkExpirationDate, Boolean requestSecurityCode, Boolean requireSecurityCode,
            String cardNumberValidationPattern, String securityCodeValidationPattern, Boolean retainCreditCard,
            Boolean retainSecurityCode, Boolean requestBilling, Boolean requireBilling, Boolean requestIssuer, Boolean requireIssuer, BasePK createdBy) {
        var paymentMethodCreditCard = PaymentMethodCreditCardFactory.getInstance().create(session,
                paymentMethod, requestNameOnCard, requireNameOnCard, checkCardNumber, requestExpirationDate, requireExpirationDate,
                checkExpirationDate, requestSecurityCode, requireSecurityCode, cardNumberValidationPattern,
                securityCodeValidationPattern, retainCreditCard, retainSecurityCode, requestBilling, requireBilling, requestIssuer, requireIssuer,
                session.getStartTimeLong(), Session.MAX_TIME_LONG);
        
        sendEvent(paymentMethod.getPrimaryKey(), EventTypes.MODIFY, paymentMethodCreditCard.getPrimaryKey(),
                EventTypes.CREATE, createdBy);
        
        return paymentMethodCreditCard;
    }
    
    private PaymentMethodCreditCard getPaymentMethodCreditCard(PaymentMethod paymentMethod, EntityPermission entityPermission) {
        PaymentMethodCreditCard paymentMethodCreditCard;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethodcreditcards " +
                        "WHERE pmcc_pm_paymentmethodid = ? AND pmcc_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentmethodcreditcards " +
                        "WHERE pmcc_pm_paymentmethodid = ? AND pmcc_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = PaymentMethodCreditCardFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, paymentMethod.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            paymentMethodCreditCard = PaymentMethodCreditCardFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return paymentMethodCreditCard;
    }
    
    public PaymentMethodCreditCard getPaymentMethodCreditCard(PaymentMethod paymentMethod) {
        return getPaymentMethodCreditCard(paymentMethod, EntityPermission.READ_ONLY);
    }
    
    public PaymentMethodCreditCard getPaymentMethodCreditCardForUpdate(PaymentMethod paymentMethod) {
        return getPaymentMethodCreditCard(paymentMethod, EntityPermission.READ_WRITE);
    }
    
    public PaymentMethodCreditCardValue getPaymentMethodCreditCardValueForUpdate(PaymentMethod paymentMethod) {
        var paymentMethodCreditCard = getPaymentMethodCreditCardForUpdate(paymentMethod);
        
        return paymentMethodCreditCard == null? null: paymentMethodCreditCard.getPaymentMethodCreditCardValue().clone();
    }
    
    public void updatePaymentMethodCreditCardFromValue(PaymentMethodCreditCardValue paymentMethodCreditCardValue, BasePK updatedBy) {
        if(paymentMethodCreditCardValue.hasBeenModified()) {
            var paymentMethodCreditCard = PaymentMethodCreditCardFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     paymentMethodCreditCardValue.getPrimaryKey());
            
            paymentMethodCreditCard.setThruTime(session.getStartTimeLong());
            paymentMethodCreditCard.store();

            var paymentMethodPK = paymentMethodCreditCard.getPaymentMethodPK(); // Not updated
            var requestNameOnCard = paymentMethodCreditCardValue.getRequestNameOnCard();
            var reqireNameOnCard = paymentMethodCreditCardValue.getRequireNameOnCard();
            var checkCardNumber = paymentMethodCreditCardValue.getCheckCardNumber();
            var requestExpirationDate = paymentMethodCreditCardValue.getRequireExpirationDate();
            var requireExpirationDate = paymentMethodCreditCardValue.getRequireExpirationDate();
            var checkExpirationDate = paymentMethodCreditCardValue.getCheckExpirationDate();
            var requestSecurityCode = paymentMethodCreditCardValue.getRequestSecurityCode();
            var requireSecurityCode = paymentMethodCreditCardValue.getRequireSecurityCode();
            var cardNumberValidationPattern = paymentMethodCreditCardValue.getCardNumberValidationPattern();
            var securityCodeValidationPattern = paymentMethodCreditCardValue.getSecurityCodeValidationPattern();
            var retainCreditCard = paymentMethodCreditCardValue.getRetainCreditCard();
            var retainSecurityCode = paymentMethodCreditCardValue.getRetainSecurityCode();
            var requestBilling = paymentMethodCreditCardValue.getRequestBilling();
            var requireBilling = paymentMethodCreditCardValue.getRequireBilling();
            var requestIssuer = paymentMethodCreditCardValue.getRequestIssuer();
            var requireIssuer = paymentMethodCreditCardValue.getRequireIssuer();
            
            paymentMethodCreditCard = PaymentMethodCreditCardFactory.getInstance().create(paymentMethodPK,
                    requestNameOnCard, reqireNameOnCard, checkCardNumber, requestExpirationDate, requireExpirationDate,
                    checkExpirationDate, requestSecurityCode, requireSecurityCode, cardNumberValidationPattern,
                    securityCodeValidationPattern, retainCreditCard, retainSecurityCode, requestBilling, requireBilling, requestIssuer, requireIssuer,
                    session.getStartTimeLong(), Session.MAX_TIME_LONG);
            
            sendEvent(paymentMethodPK, EventTypes.MODIFY, paymentMethodCreditCard.getPrimaryKey(),
                    EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deletePaymentMethodCreditCard(PaymentMethodCreditCard paymentMethodCreditCard, BasePK deletedBy) {
        paymentMethodCreditCard.setThruTime(session.getStartTimeLong());
        
        sendEvent(paymentMethodCreditCard.getPaymentMethodPK(), EventTypes.MODIFY,
                paymentMethodCreditCard.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
 }
