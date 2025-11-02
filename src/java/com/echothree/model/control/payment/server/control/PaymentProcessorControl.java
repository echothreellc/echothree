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
import com.echothree.model.control.payment.common.choice.PaymentProcessorChoicesBean;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorDescriptionTransfer;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorTransfer;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.payment.common.pk.PaymentProcessorPK;
import com.echothree.model.data.payment.server.entity.PaymentProcessor;
import com.echothree.model.data.payment.server.entity.PaymentProcessorDescription;
import com.echothree.model.data.payment.server.entity.PaymentProcessorType;
import com.echothree.model.data.payment.server.factory.PaymentProcessorDescriptionFactory;
import com.echothree.model.data.payment.server.factory.PaymentProcessorDetailFactory;
import com.echothree.model.data.payment.server.factory.PaymentProcessorFactory;
import com.echothree.model.data.payment.server.value.PaymentProcessorDescriptionValue;
import com.echothree.model.data.payment.server.value.PaymentProcessorDetailValue;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PaymentProcessorControl
        extends BasePaymentControl {

    /** Creates a new instance of PaymentProcessorControl */
    protected PaymentProcessorControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Payment Processors
    // --------------------------------------------------------------------------------
    
    public PaymentProcessor createPaymentProcessor(String paymentProcessorName, PaymentProcessorType paymentProcessorType,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultPaymentProcessor = getDefaultPaymentProcessor();
        var defaultFound = defaultPaymentProcessor != null;
        
        if(defaultFound && isDefault) {
            var defaultPaymentProcessorDetailValue = getDefaultPaymentProcessorDetailValueForUpdate();
            
            defaultPaymentProcessorDetailValue.setIsDefault(false);
            updatePaymentProcessorFromValue(defaultPaymentProcessorDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var paymentProcessor = PaymentProcessorFactory.getInstance().create();
        var paymentProcessorDetail = PaymentProcessorDetailFactory.getInstance().create(session,
                paymentProcessor, paymentProcessorName, paymentProcessorType, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        paymentProcessor = PaymentProcessorFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                paymentProcessor.getPrimaryKey());
        paymentProcessor.setActiveDetail(paymentProcessorDetail);
        paymentProcessor.setLastDetail(paymentProcessorDetail);
        paymentProcessor.store();
        
        sendEvent(paymentProcessor.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return paymentProcessor;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.PaymentProcessor */
    public PaymentProcessor getPaymentProcessorByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new PaymentProcessorPK(entityInstance.getEntityUniqueId());

        return PaymentProcessorFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public PaymentProcessor getPaymentProcessorByEntityInstance(final EntityInstance entityInstance) {
        return getPaymentProcessorByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PaymentProcessor getPaymentProcessorByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getPaymentProcessorByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorDetailValue getPaymentProcessorDetailValueForUpdate(PaymentProcessor paymentProcessor) {
        return paymentProcessor.getLastDetailForUpdate().getPaymentProcessorDetailValue().clone();
    }

    public PaymentProcessor getPaymentProcessorByName(String paymentProcessorName, EntityPermission entityPermission) {
        PaymentProcessor paymentProcessor;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentprocessors, paymentprocessordetails " +
                        "WHERE pprc_activedetailid = pprcdt_paymentprocessordetailid AND pprcdt_paymentprocessorname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentprocessors, paymentprocessordetails " +
                        "WHERE pprc_activedetailid = pprcdt_paymentprocessordetailid AND pprcdt_paymentprocessorname = ? " +
                        "FOR UPDATE";
            }

            var ps = PaymentProcessorFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, paymentProcessorName);
            
            paymentProcessor = PaymentProcessorFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return paymentProcessor;
    }
    
    public PaymentProcessor getPaymentProcessorByName(String paymentProcessorName) {
        return getPaymentProcessorByName(paymentProcessorName, EntityPermission.READ_ONLY);
    }
    
    public PaymentProcessor getPaymentProcessorByNameForUpdate(String paymentProcessorName) {
        return getPaymentProcessorByName(paymentProcessorName, EntityPermission.READ_WRITE);
    }
    
    public PaymentProcessorDetailValue getPaymentProcessorDetailValueByNameForUpdate(String paymentProcessorName) {
        return getPaymentProcessorDetailValueForUpdate(getPaymentProcessorByNameForUpdate(paymentProcessorName));
    }

    private List<PaymentProcessor> getPaymentProcessors(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM paymentprocessors, paymentprocessordetails " +
                    "WHERE pprc_activedetailid = pprcdt_paymentprocessordetailid " +
                    "ORDER BY pprcdt_sortorder, pprcdt_paymentprocessorname";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM paymentprocessors, paymentprocessordetails " +
                    "WHERE pprc_activedetailid = pprcdt_paymentprocessordetailid " +
                    "FOR UPDATE";
        }

        var ps = PaymentProcessorFactory.getInstance().prepareStatement(query);
        
        return PaymentProcessorFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<PaymentProcessor> getPaymentProcessors() {
        return getPaymentProcessors(EntityPermission.READ_ONLY);
    }
    
    public List<PaymentProcessor> getPaymentProcessorsForUpdate() {
        return getPaymentProcessors(EntityPermission.READ_WRITE);
    }

    public PaymentProcessor getDefaultPaymentProcessor(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM paymentprocessors, paymentprocessordetails " +
                    "WHERE pprc_activedetailid = pprcdt_paymentprocessordetailid AND pprcdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM paymentprocessors, paymentprocessordetails " +
                    "WHERE pprc_activedetailid = pprcdt_paymentprocessordetailid AND pprcdt_isdefault = 1 " +
                    "FOR UPDATE";
        }

        var ps = PaymentProcessorFactory.getInstance().prepareStatement(query);
        
        return PaymentProcessorFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public PaymentProcessor getDefaultPaymentProcessor() {
        return getDefaultPaymentProcessor(EntityPermission.READ_ONLY);
    }
    
    public PaymentProcessor getDefaultPaymentProcessorForUpdate() {
        return getDefaultPaymentProcessor(EntityPermission.READ_WRITE);
    }
    
    public PaymentProcessorDetailValue getDefaultPaymentProcessorDetailValueForUpdate() {
        return getPaymentProcessorDetailValueForUpdate(getDefaultPaymentProcessorForUpdate());
    }
    
    public PaymentProcessorChoicesBean getPaymentProcessorChoices(String defaultPaymentProcessorChoice, Language language,
            boolean allowNullChoice) {
        var paymentProcessors = getPaymentProcessors();
        var size = paymentProcessors.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
        }
        
        for(var paymentProcessor : paymentProcessors) {
            var paymentProcessorDetail = paymentProcessor.getLastDetail();
            var label = getBestPaymentProcessorDescription(paymentProcessor, language);
            var value = paymentProcessorDetail.getPaymentProcessorName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultPaymentProcessorChoice != null && defaultPaymentProcessorChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && paymentProcessorDetail.getIsDefault()))
                defaultValue = value;
        }
        
        return new PaymentProcessorChoicesBean(labels, values, defaultValue);
    }
    
    public PaymentProcessorTransfer getPaymentProcessorTransfer(UserVisit userVisit, PaymentProcessor paymentProcessor) {
        return getPaymentTransferCaches(userVisit).getPaymentProcessorTransferCache().getTransfer(paymentProcessor);
    }

    public List<PaymentProcessorTransfer> getPaymentProcessorTransfers(UserVisit userVisit, Collection<PaymentProcessor> paymentProcessors) {
        List<PaymentProcessorTransfer> paymentProcessorTransfers = new ArrayList<>(paymentProcessors.size());
        var paymentProcessorTransferCache = getPaymentTransferCaches(userVisit).getPaymentProcessorTransferCache();

        paymentProcessors.forEach((paymentProcessor) ->
                paymentProcessorTransfers.add(paymentProcessorTransferCache.getTransfer(paymentProcessor))
        );

        return paymentProcessorTransfers;
    }

    public List<PaymentProcessorTransfer> getPaymentProcessorTransfers(UserVisit userVisit) {
        return getPaymentProcessorTransfers(userVisit, getPaymentProcessors());
    }

    private void updatePaymentProcessorFromValue(PaymentProcessorDetailValue paymentProcessorDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(paymentProcessorDetailValue.hasBeenModified()) {
            var paymentProcessor = PaymentProcessorFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     paymentProcessorDetailValue.getPaymentProcessorPK());
            var paymentProcessorDetail = paymentProcessor.getActiveDetailForUpdate();
            
            paymentProcessorDetail.setThruTime(session.START_TIME_LONG);
            paymentProcessorDetail.store();

            var paymentProcessorPK = paymentProcessorDetail.getPaymentProcessorPK(); // Not updated
            var paymentProcessorName = paymentProcessorDetailValue.getPaymentProcessorName();
            var paymentProcessorTypePK = paymentProcessorDetail.getPaymentProcessorTypePK(); // Not updated
            var isDefault = paymentProcessorDetailValue.getIsDefault();
            var sortOrder = paymentProcessorDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultPaymentProcessor = getDefaultPaymentProcessor();
                var defaultFound = defaultPaymentProcessor != null && !defaultPaymentProcessor.equals(paymentProcessor);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultPaymentProcessorDetailValue = getDefaultPaymentProcessorDetailValueForUpdate();
                    
                    defaultPaymentProcessorDetailValue.setIsDefault(false);
                    updatePaymentProcessorFromValue(defaultPaymentProcessorDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            paymentProcessorDetail = PaymentProcessorDetailFactory.getInstance().create(paymentProcessorPK,
                    paymentProcessorName, paymentProcessorTypePK, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            paymentProcessor.setActiveDetail(paymentProcessorDetail);
            paymentProcessor.setLastDetail(paymentProcessorDetail);
            
            sendEvent(paymentProcessorPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updatePaymentProcessorFromValue(PaymentProcessorDetailValue paymentProcessorDetailValue, BasePK updatedBy) {
        updatePaymentProcessorFromValue(paymentProcessorDetailValue, true, updatedBy);
    }
    
    public void deletePaymentProcessor(PaymentProcessor paymentProcessor, BasePK deletedBy) {
        var paymentMethodControl = Session.getModelController(PaymentMethodControl.class);
        var paymentProcessorActionControl = Session.getModelController(PaymentProcessorActionControl.class);
        var paymentProcessorTransactionControl = Session.getModelController(PaymentProcessorTransactionControl.class);

        paymentProcessorActionControl.deletePaymentProcessorActionsByPaymentProcessor(paymentProcessor, deletedBy);
        paymentMethodControl.deletePaymentMethodsByPaymentProcessor(paymentProcessor, deletedBy);
        paymentProcessorTransactionControl.deletePaymentProcessorTransactionsByPaymentProcessor(paymentProcessor, deletedBy);
        deletePaymentProcessorDescriptionsByPaymentProcessor(paymentProcessor, deletedBy);

        var paymentProcessorDetail = paymentProcessor.getLastDetailForUpdate();
        paymentProcessorDetail.setThruTime(session.START_TIME_LONG);
        paymentProcessorDetail.store();
        paymentProcessor.setActiveDetail(null);
        
        // Check for default, and pick one if necessary
        var defaultPaymentProcessor = getDefaultPaymentProcessor();
        if(defaultPaymentProcessor == null) {
            var paymentProcessors = getPaymentProcessorsForUpdate();
            
            if(!paymentProcessors.isEmpty()) {
                var iter = paymentProcessors.iterator();
                if(iter.hasNext()) {
                    defaultPaymentProcessor = iter.next();
                }
                var paymentProcessorDetailValue = Objects.requireNonNull(defaultPaymentProcessor).getLastDetailForUpdate().getPaymentProcessorDetailValue().clone();
                
                paymentProcessorDetailValue.setIsDefault(true);
                updatePaymentProcessorFromValue(paymentProcessorDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(paymentProcessor.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Payment Processor Descriptions
    // --------------------------------------------------------------------------------
    
    public PaymentProcessorDescription createPaymentProcessorDescription(PaymentProcessor paymentProcessor, Language language,
            String description, BasePK createdBy) {
        var paymentProcessorDescription = PaymentProcessorDescriptionFactory.getInstance().create(session,
                paymentProcessor, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(paymentProcessor.getPrimaryKey(), EventTypes.MODIFY, paymentProcessorDescription.getPrimaryKey(),
                EventTypes.CREATE, createdBy);
        
        return paymentProcessorDescription;
    }
    
    private PaymentProcessorDescription getPaymentProcessorDescription(PaymentProcessor paymentProcessor, Language language,
            EntityPermission entityPermission) {
        PaymentProcessorDescription paymentProcessorDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentprocessordescriptions " +
                        "WHERE pprcd_pprc_paymentprocessorid = ? AND pprcd_lang_languageid = ? AND pprcd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentprocessordescriptions " +
                        "WHERE pprcd_pprc_paymentprocessorid = ? AND pprcd_lang_languageid = ? AND pprcd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = PaymentProcessorDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, paymentProcessor.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            paymentProcessorDescription = PaymentProcessorDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return paymentProcessorDescription;
    }
    
    public PaymentProcessorDescription getPaymentProcessorDescription(PaymentProcessor paymentProcessor, Language language) {
        return getPaymentProcessorDescription(paymentProcessor, language, EntityPermission.READ_ONLY);
    }
    
    public PaymentProcessorDescription getPaymentProcessorDescriptionForUpdate(PaymentProcessor paymentProcessor, Language language) {
        return getPaymentProcessorDescription(paymentProcessor, language, EntityPermission.READ_WRITE);
    }
    
    public PaymentProcessorDescriptionValue getPaymentProcessorDescriptionValue(PaymentProcessorDescription paymentProcessorDescription) {
        return paymentProcessorDescription == null? null: paymentProcessorDescription.getPaymentProcessorDescriptionValue().clone();
    }
    
    public PaymentProcessorDescriptionValue getPaymentProcessorDescriptionValueForUpdate(PaymentProcessor paymentProcessor, Language language) {
        return getPaymentProcessorDescriptionValue(getPaymentProcessorDescriptionForUpdate(paymentProcessor, language));
    }
    
    private List<PaymentProcessorDescription> getPaymentProcessorDescriptions(PaymentProcessor paymentProcessor, EntityPermission entityPermission) {
        List<PaymentProcessorDescription> paymentProcessorDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentprocessordescriptions, languages " +
                        "WHERE pprcd_pprc_paymentprocessorid = ? AND pprcd_thrutime = ? AND pprcd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM paymentprocessordescriptions " +
                        "WHERE pprcd_pprc_paymentprocessorid = ? AND pprcd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = PaymentProcessorDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, paymentProcessor.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            paymentProcessorDescriptions = PaymentProcessorDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return paymentProcessorDescriptions;
    }
    
    public List<PaymentProcessorDescription> getPaymentProcessorDescriptions(PaymentProcessor paymentProcessor) {
        return getPaymentProcessorDescriptions(paymentProcessor, EntityPermission.READ_ONLY);
    }
    
    public List<PaymentProcessorDescription> getPaymentProcessorDescriptionsForUpdate(PaymentProcessor paymentProcessor) {
        return getPaymentProcessorDescriptions(paymentProcessor, EntityPermission.READ_WRITE);
    }
    
    public String getBestPaymentProcessorDescription(PaymentProcessor paymentProcessor, Language language) {
        String description;
        var paymentProcessorDescription = getPaymentProcessorDescription(paymentProcessor, language);
        
        if(paymentProcessorDescription == null && !language.getIsDefault()) {
            paymentProcessorDescription = getPaymentProcessorDescription(paymentProcessor, partyControl.getDefaultLanguage());
        }
        
        if(paymentProcessorDescription == null) {
            description = paymentProcessor.getLastDetail().getPaymentProcessorName();
        } else {
            description = paymentProcessorDescription.getDescription();
        }
        
        return description;
    }
    
    public PaymentProcessorDescriptionTransfer getPaymentProcessorDescriptionTransfer(UserVisit userVisit, PaymentProcessorDescription paymentProcessorDescription) {
        return getPaymentTransferCaches(userVisit).getPaymentProcessorDescriptionTransferCache().getTransfer(paymentProcessorDescription);
    }
    
    public List<PaymentProcessorDescriptionTransfer> getPaymentProcessorDescriptionTransfers(UserVisit userVisit, PaymentProcessor paymentProcessor) {
        var paymentProcessorDescriptions = getPaymentProcessorDescriptions(paymentProcessor);
        List<PaymentProcessorDescriptionTransfer> paymentProcessorDescriptionTransfers = new ArrayList<>(paymentProcessorDescriptions.size());
        var paymentProcessorDescriptionTransferCache = getPaymentTransferCaches(userVisit).getPaymentProcessorDescriptionTransferCache();
        
        paymentProcessorDescriptions.forEach((paymentProcessorDescription) ->
                paymentProcessorDescriptionTransfers.add(paymentProcessorDescriptionTransferCache.getTransfer(paymentProcessorDescription))
        );
        
        return paymentProcessorDescriptionTransfers;
    }
    
    public void updatePaymentProcessorDescriptionFromValue(PaymentProcessorDescriptionValue paymentProcessorDescriptionValue, BasePK updatedBy) {
        if(paymentProcessorDescriptionValue.hasBeenModified()) {
            var paymentProcessorDescription = PaymentProcessorDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     paymentProcessorDescriptionValue.getPrimaryKey());
            
            paymentProcessorDescription.setThruTime(session.START_TIME_LONG);
            paymentProcessorDescription.store();

            var paymentProcessor = paymentProcessorDescription.getPaymentProcessor();
            var language = paymentProcessorDescription.getLanguage();
            var description = paymentProcessorDescriptionValue.getDescription();
            
            paymentProcessorDescription = PaymentProcessorDescriptionFactory.getInstance().create(paymentProcessor, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(paymentProcessor.getPrimaryKey(), EventTypes.MODIFY,
                    paymentProcessorDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deletePaymentProcessorDescription(PaymentProcessorDescription paymentProcessorDescription, BasePK deletedBy) {
        paymentProcessorDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(paymentProcessorDescription.getPaymentProcessorPK(), EventTypes.MODIFY,
                paymentProcessorDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deletePaymentProcessorDescriptionsByPaymentProcessor(PaymentProcessor paymentProcessor, BasePK deletedBy) {
        var paymentProcessorDescriptions = getPaymentProcessorDescriptionsForUpdate(paymentProcessor);
        
        paymentProcessorDescriptions.forEach((paymentProcessorDescription) -> 
                deletePaymentProcessorDescription(paymentProcessorDescription, deletedBy)
        );
    }
    
}
