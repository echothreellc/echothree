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
import com.echothree.model.control.payment.common.choice.PaymentProcessorTypeChoicesBean;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorTypeDescriptionTransfer;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorTypeTransfer;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.payment.common.pk.PaymentProcessorTypePK;
import com.echothree.model.data.payment.server.entity.PaymentProcessorType;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeDescription;
import com.echothree.model.data.payment.server.factory.PaymentProcessorTypeDescriptionFactory;
import com.echothree.model.data.payment.server.factory.PaymentProcessorTypeDetailFactory;
import com.echothree.model.data.payment.server.factory.PaymentProcessorTypeFactory;
import com.echothree.model.data.payment.server.value.PaymentProcessorTypeDescriptionValue;
import com.echothree.model.data.payment.server.value.PaymentProcessorTypeDetailValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PaymentProcessorTypeControl
        extends BasePaymentControl {

    /** Creates a new instance of PaymentProcessorTypeControl */
    protected PaymentProcessorTypeControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Payment Processor Types
    // --------------------------------------------------------------------------------

    public PaymentProcessorType createPaymentProcessorType(final String paymentProcessorTypeName, Boolean isDefault,
            final Integer sortOrder, final BasePK createdBy) {
        var defaultPaymentProcessorType = getDefaultPaymentProcessorType();
        var defaultFound = defaultPaymentProcessorType != null;

        if(defaultFound && isDefault) {
            var defaultPaymentProcessorTypeDetailValue = getDefaultPaymentProcessorTypeDetailValueForUpdate();

            defaultPaymentProcessorTypeDetailValue.setIsDefault(false);
            updatePaymentProcessorTypeFromValue(defaultPaymentProcessorTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var paymentProcessorType = PaymentProcessorTypeFactory.getInstance().create();
        var paymentProcessorTypeDetail = PaymentProcessorTypeDetailFactory.getInstance().create(session,
                paymentProcessorType, paymentProcessorTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        paymentProcessorType = PaymentProcessorTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, paymentProcessorType.getPrimaryKey());
        paymentProcessorType.setActiveDetail(paymentProcessorTypeDetail);
        paymentProcessorType.setLastDetail(paymentProcessorTypeDetail);
        paymentProcessorType.store();

        sendEvent(paymentProcessorType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return paymentProcessorType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.PaymentProcessorType */
    public PaymentProcessorType getPaymentProcessorTypeByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new PaymentProcessorTypePK(entityInstance.getEntityUniqueId());

        return PaymentProcessorTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public PaymentProcessorType getPaymentProcessorTypeByEntityInstance(final EntityInstance entityInstance) {
        return getPaymentProcessorTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorType getPaymentProcessorTypeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getPaymentProcessorTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPaymentProcessorTypeByNameQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortypes, paymentprocessortypedetails " +
                    "WHERE pprctyp_paymentprocessortypeid = pprctypdt_pprctyp_paymentprocessortypeid AND pprctypdt_paymentprocessortypename = ? AND pprctypdt_thrutime = ?",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " + "FROM paymentprocessortypes, paymentprocessortypedetails " +
                    "WHERE pprctyp_paymentprocessortypeid = pprctypdt_pprctyp_paymentprocessortypeid AND pprctypdt_paymentprocessortypename = ? AND pprctypdt_thrutime = ? " +
                    "FOR UPDATE");

    public PaymentProcessorType getPaymentProcessorTypeByName(final String paymentProcessorTypeName, final EntityPermission entityPermission) {
        return PaymentProcessorTypeFactory.getInstance().getEntityFromQuery(entityPermission, getPaymentProcessorTypeByNameQueries,
                paymentProcessorTypeName, Session.MAX_TIME);
    }

    public PaymentProcessorType getPaymentProcessorTypeByName(final String paymentProcessorTypeName) {
        return getPaymentProcessorTypeByName(paymentProcessorTypeName, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorType getPaymentProcessorTypeByNameForUpdate(final String paymentProcessorTypeName) {
        return getPaymentProcessorTypeByName(paymentProcessorTypeName, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorTypeDetailValue getPaymentProcessorTypeDetailValueForUpdate(final PaymentProcessorType paymentProcessorType) {
        return paymentProcessorType == null ? null: paymentProcessorType.getLastDetailForUpdate().getPaymentProcessorTypeDetailValue().clone();
    }

    public PaymentProcessorTypeDetailValue getPaymentProcessorTypeDetailValueByNameForUpdate(final String paymentProcessorTypeName) {
        return getPaymentProcessorTypeDetailValueForUpdate(getPaymentProcessorTypeByNameForUpdate(paymentProcessorTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultPaymentProcessorTypeQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortypes, paymentprocessortypedetails " +
                    "WHERE pprctyp_paymentprocessortypeid = pprctypdt_pprctyp_paymentprocessortypeid AND pprctypdt_isdefault = 1 AND pprctypdt_thrutime = ?",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortypes, paymentprocessortypedetails " +
                    "WHERE pprctyp_paymentprocessortypeid = pprctypdt_pprctyp_paymentprocessortypeid AND pprctypdt_isdefault = 1 AND pprctypdt_thrutime = ? " +
                    "FOR UPDATE");

    public PaymentProcessorType getDefaultPaymentProcessorType(final EntityPermission entityPermission) {
        return PaymentProcessorTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultPaymentProcessorTypeQueries,
                Session.MAX_TIME);
    }

    public PaymentProcessorType getDefaultPaymentProcessorType() {
        return getDefaultPaymentProcessorType(EntityPermission.READ_ONLY);
    }

    public PaymentProcessorType getDefaultPaymentProcessorTypeForUpdate() {
        return getDefaultPaymentProcessorType(EntityPermission.READ_WRITE);
    }

    public PaymentProcessorTypeDetailValue getDefaultPaymentProcessorTypeDetailValueForUpdate() {
        return getDefaultPaymentProcessorTypeForUpdate().getLastDetailForUpdate().getPaymentProcessorTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getPaymentProcessorTypesQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " + "FROM paymentprocessortypes, paymentprocessortypedetails " +
                    "WHERE pprctyp_paymentprocessortypeid = pprctypdt_pprctyp_paymentprocessortypeid AND pprctypdt_thrutime = ? " +
                    "ORDER BY pprctypdt_sortorder, pprctypdt_paymentprocessortypename",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " + "FROM paymentprocessortypes, paymentprocessortypedetails " +
                    "WHERE pprctyp_paymentprocessortypeid = pprctypdt_pprctyp_paymentprocessortypeid AND pprctypdt_thrutime = ? " +
                    "FOR UPDATE");

    private List<PaymentProcessorType> getPaymentProcessorTypes(final EntityPermission entityPermission) {
        return PaymentProcessorTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getPaymentProcessorTypesQueries,
                Session.MAX_TIME);
    }

    public List<PaymentProcessorType> getPaymentProcessorTypes() {
        return getPaymentProcessorTypes(EntityPermission.READ_ONLY);
    }

    public List<PaymentProcessorType> getPaymentProcessorTypesForUpdate() {
        return getPaymentProcessorTypes(EntityPermission.READ_WRITE);
    }

    public PaymentProcessorTypeTransfer getPaymentProcessorTypeTransfer(final UserVisit userVisit,
            final PaymentProcessorType paymentProcessorType) {
        return paymentProcessorTypeTransferCache.getTransfer(userVisit, paymentProcessorType);
    }

    public List<PaymentProcessorTypeTransfer> getPaymentProcessorTypeTransfers(final UserVisit userVisit,
            final Collection<PaymentProcessorType> paymentProcessorTypes) {
        var paymentProcessorTypeTransfers = new ArrayList<PaymentProcessorTypeTransfer>(paymentProcessorTypes.size());

        paymentProcessorTypes.forEach((paymentProcessorType) ->
                paymentProcessorTypeTransfers.add(paymentProcessorTypeTransferCache.getTransfer(userVisit, paymentProcessorType))
        );

        return paymentProcessorTypeTransfers;
    }

    public List<PaymentProcessorTypeTransfer> getPaymentProcessorTypeTransfers(final UserVisit userVisit) {
        return getPaymentProcessorTypeTransfers(userVisit, getPaymentProcessorTypes());
    }

    public PaymentProcessorTypeChoicesBean getPaymentProcessorTypeChoices(final String defaultPaymentProcessorTypeChoice,
            final Language language, final boolean allowNullChoice) {
        var paymentProcessorTypes = getPaymentProcessorTypes();
        var size = paymentProcessorTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultPaymentProcessorTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var paymentProcessorType : paymentProcessorTypes) {
            var paymentProcessorTypeDetail = paymentProcessorType.getLastDetail();

            var label = getBestPaymentProcessorTypeDescription(paymentProcessorType, language);
            var value = paymentProcessorTypeDetail.getPaymentProcessorTypeName();

            labels.add(label == null ? value : label);
            values.add(value);

            var usingDefaultChoice = Objects.equals(defaultPaymentProcessorTypeChoice, value);
            if(usingDefaultChoice || (defaultValue == null && paymentProcessorTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new PaymentProcessorTypeChoicesBean(labels, values, defaultValue);
    }

    private void updatePaymentProcessorTypeFromValue(final PaymentProcessorTypeDetailValue paymentProcessorTypeDetailValue,
            final boolean checkDefault, final BasePK updatedBy) {
        if(paymentProcessorTypeDetailValue.hasBeenModified()) {
            var paymentProcessorType = PaymentProcessorTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    paymentProcessorTypeDetailValue.getPaymentProcessorTypePK());
            var paymentProcessorTypeDetail = paymentProcessorType.getActiveDetailForUpdate();

            paymentProcessorTypeDetail.setThruTime(session.START_TIME_LONG);
            paymentProcessorTypeDetail.store();

            var paymentProcessorTypePK = paymentProcessorTypeDetail.getPaymentProcessorTypePK();
            var paymentProcessorTypeName = paymentProcessorTypeDetailValue.getPaymentProcessorTypeName();
            var isDefault = paymentProcessorTypeDetailValue.getIsDefault();
            var sortOrder = paymentProcessorTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultPaymentProcessorType = getDefaultPaymentProcessorType();
                var defaultFound = defaultPaymentProcessorType != null && !defaultPaymentProcessorType.equals(paymentProcessorType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultPaymentProcessorTypeDetailValue = getDefaultPaymentProcessorTypeDetailValueForUpdate();

                    defaultPaymentProcessorTypeDetailValue.setIsDefault(false);
                    updatePaymentProcessorTypeFromValue(defaultPaymentProcessorTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            paymentProcessorTypeDetail = PaymentProcessorTypeDetailFactory.getInstance().create(paymentProcessorTypePK,
                    paymentProcessorTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            paymentProcessorType.setActiveDetail(paymentProcessorTypeDetail);
            paymentProcessorType.setLastDetail(paymentProcessorTypeDetail);

            sendEvent(paymentProcessorTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updatePaymentProcessorTypeFromValue(final PaymentProcessorTypeDetailValue paymentProcessorTypeDetailValue,
            final BasePK updatedBy) {
        updatePaymentProcessorTypeFromValue(paymentProcessorTypeDetailValue, true, updatedBy);
    }

    public void deletePaymentProcessorType(final PaymentProcessorType paymentProcessorType, final BasePK deletedBy) {
        var paymentProcessorTypeCodeTypeControl = Session.getModelController(PaymentProcessorTypeCodeTypeControl.class);
        var paymentProcessorTypeActionControl = Session.getModelController(PaymentProcessorTypeActionControl.class);

        paymentProcessorTypeCodeTypeControl.deletePaymentProcessorTypeCodeTypesByPaymentProcessorType(paymentProcessorType, deletedBy);
        paymentProcessorTypeActionControl.deletePaymentProcessorTypeActionsByPaymentProcessorType(paymentProcessorType, deletedBy);
        deletePaymentProcessorTypeDescriptionsByPaymentProcessorType(paymentProcessorType, deletedBy);

        var paymentProcessorTypeDetail = paymentProcessorType.getLastDetailForUpdate();
        paymentProcessorTypeDetail.setThruTime(session.START_TIME_LONG);
        paymentProcessorTypeDetail.store();
        paymentProcessorType.setActiveDetail(null);

        // Check for default, and pick one if necessary
        var defaultPaymentProcessorType = getDefaultPaymentProcessorType();
        if(defaultPaymentProcessorType == null) {
            var paymentProcessorTypes = getPaymentProcessorTypesForUpdate();

            if(!paymentProcessorTypes.isEmpty()) {
                var iter = paymentProcessorTypes.iterator();
                if(iter.hasNext()) {
                    defaultPaymentProcessorType = iter.next();
                }
                var paymentProcessorTypeDetailValue = Objects.requireNonNull(defaultPaymentProcessorType).getLastDetailForUpdate().getPaymentProcessorTypeDetailValue().clone();

                paymentProcessorTypeDetailValue.setIsDefault(true);
                updatePaymentProcessorTypeFromValue(paymentProcessorTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(paymentProcessorType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Payment Processor Type Descriptions
    // --------------------------------------------------------------------------------

    public PaymentProcessorTypeDescription createPaymentProcessorTypeDescription(final PaymentProcessorType paymentProcessorType,
            final Language language, final String description, final BasePK createdBy) {
        var paymentProcessorTypeDescription = PaymentProcessorTypeDescriptionFactory.getInstance().create(paymentProcessorType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(paymentProcessorType.getPrimaryKey(), EventTypes.MODIFY, paymentProcessorTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return paymentProcessorTypeDescription;
    }

    private static final Map<EntityPermission, String> getPaymentProcessorTypeDescriptionQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortypedescriptions " +
                    "WHERE pprctypd_pprctyp_paymentprocessortypeid = ? AND pprctypd_lang_languageid = ? AND pprctypd_thrutime = ?",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortypedescriptions " +
                    "WHERE pprctypd_pprctyp_paymentprocessortypeid = ? AND pprctypd_lang_languageid = ? AND pprctypd_thrutime = ? " +
                    "FOR UPDATE");

    private PaymentProcessorTypeDescription getPaymentProcessorTypeDescription(final PaymentProcessorType paymentProcessorType,
            final Language language, final EntityPermission entityPermission) {
        return PaymentProcessorTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getPaymentProcessorTypeDescriptionQueries,
                paymentProcessorType, language, Session.MAX_TIME);
    }

    public PaymentProcessorTypeDescription getPaymentProcessorTypeDescription(final PaymentProcessorType paymentProcessorType,
            final Language language) {
        return getPaymentProcessorTypeDescription(paymentProcessorType, language, EntityPermission.READ_ONLY);
    }

    public PaymentProcessorTypeDescription getPaymentProcessorTypeDescriptionForUpdate(final PaymentProcessorType paymentProcessorType,
            final Language language) {
        return getPaymentProcessorTypeDescription(paymentProcessorType, language, EntityPermission.READ_WRITE);
    }

    public PaymentProcessorTypeDescriptionValue getPaymentProcessorTypeDescriptionValue(final PaymentProcessorTypeDescription paymentProcessorTypeDescription) {
        return paymentProcessorTypeDescription == null ? null: paymentProcessorTypeDescription.getPaymentProcessorTypeDescriptionValue().clone();
    }

    public PaymentProcessorTypeDescriptionValue getPaymentProcessorTypeDescriptionValueForUpdate(final PaymentProcessorType paymentProcessorType,
            final Language language) {
        return getPaymentProcessorTypeDescriptionValue(getPaymentProcessorTypeDescriptionForUpdate(paymentProcessorType, language));
    }

    private static final Map<EntityPermission, String> getPaymentProcessorTypeDescriptionsByPaymentProcessorTypeQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortypedescriptions, languages " +
                    "WHERE pprctypd_pprctyp_paymentprocessortypeid = ? AND pprctypd_thrutime = ? AND pprctypd_lang_languageid = lang_languageid " +
                    "ORDER BY lang_sortorder, lang_languageisoname",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM paymentprocessortypedescriptions " +
                    "WHERE pprctypd_pprctyp_paymentprocessortypeid = ? AND pprctypd_thrutime = ? " +
                    "FOR UPDATE");

    private List<PaymentProcessorTypeDescription> getPaymentProcessorTypeDescriptionsByPaymentProcessorType(final PaymentProcessorType paymentProcessorType,
            final EntityPermission entityPermission) {
        return PaymentProcessorTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission,
                getPaymentProcessorTypeDescriptionsByPaymentProcessorTypeQueries,
                paymentProcessorType, Session.MAX_TIME);
    }

    public List<PaymentProcessorTypeDescription> getPaymentProcessorTypeDescriptionsByPaymentProcessorType(final PaymentProcessorType paymentProcessorType) {
        return getPaymentProcessorTypeDescriptionsByPaymentProcessorType(paymentProcessorType, EntityPermission.READ_ONLY);
    }

    public List<PaymentProcessorTypeDescription> getPaymentProcessorTypeDescriptionsByPaymentProcessorTypeForUpdate(final PaymentProcessorType paymentProcessorType) {
        return getPaymentProcessorTypeDescriptionsByPaymentProcessorType(paymentProcessorType, EntityPermission.READ_WRITE);
    }

    public String getBestPaymentProcessorTypeDescription(final PaymentProcessorType paymentProcessorType, final Language language) {
        var paymentProcessorTypeDescription = getPaymentProcessorTypeDescription(paymentProcessorType, language);
        String description;

        if(paymentProcessorTypeDescription == null && !language.getIsDefault()) {
            paymentProcessorTypeDescription = getPaymentProcessorTypeDescription(paymentProcessorType, partyControl.getDefaultLanguage());
        }

        if(paymentProcessorTypeDescription == null) {
            description = paymentProcessorType.getLastDetail().getPaymentProcessorTypeName();
        } else {
            description = paymentProcessorTypeDescription.getDescription();
        }

        return description;
    }

    public PaymentProcessorTypeDescriptionTransfer getPaymentProcessorTypeDescriptionTransfer(final UserVisit userVisit,
            final PaymentProcessorTypeDescription paymentProcessorTypeDescription) {
        return paymentProcessorTypeDescriptionTransferCache.getTransfer(userVisit, paymentProcessorTypeDescription);
    }

    public List<PaymentProcessorTypeDescriptionTransfer> getPaymentProcessorTypeDescriptionTransfersByPaymentProcessorType(final UserVisit userVisit,
            final PaymentProcessorType paymentProcessorType) {
        var paymentProcessorTypeDescriptions = getPaymentProcessorTypeDescriptionsByPaymentProcessorType(paymentProcessorType);
        var paymentProcessorTypeDescriptionTransfers = new ArrayList<PaymentProcessorTypeDescriptionTransfer>(paymentProcessorTypeDescriptions.size());

        paymentProcessorTypeDescriptions.forEach((paymentProcessorTypeDescription) ->
                paymentProcessorTypeDescriptionTransfers.add(paymentProcessorTypeDescriptionTransferCache.getTransfer(userVisit, paymentProcessorTypeDescription))
        );

        return paymentProcessorTypeDescriptionTransfers;
    }

    public void updatePaymentProcessorTypeDescriptionFromValue(final PaymentProcessorTypeDescriptionValue paymentProcessorTypeDescriptionValue,
            final BasePK updatedBy) {
        if(paymentProcessorTypeDescriptionValue.hasBeenModified()) {
            var paymentProcessorTypeDescription = PaymentProcessorTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, paymentProcessorTypeDescriptionValue.getPrimaryKey());

            paymentProcessorTypeDescription.setThruTime(session.START_TIME_LONG);
            paymentProcessorTypeDescription.store();

            var paymentProcessorType = paymentProcessorTypeDescription.getPaymentProcessorType();
            var language = paymentProcessorTypeDescription.getLanguage();
            var description = paymentProcessorTypeDescriptionValue.getDescription();

            paymentProcessorTypeDescription = PaymentProcessorTypeDescriptionFactory.getInstance().create(paymentProcessorType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(paymentProcessorType.getPrimaryKey(), EventTypes.MODIFY, paymentProcessorTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePaymentProcessorTypeDescription(final PaymentProcessorTypeDescription paymentProcessorTypeDescription, final BasePK deletedBy) {
        paymentProcessorTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(paymentProcessorTypeDescription.getPaymentProcessorTypePK(), EventTypes.MODIFY, paymentProcessorTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deletePaymentProcessorTypeDescriptionsByPaymentProcessorType(final PaymentProcessorType paymentProcessorType, final BasePK deletedBy) {
        var paymentProcessorTypeDescriptions = getPaymentProcessorTypeDescriptionsByPaymentProcessorTypeForUpdate(paymentProcessorType);

        paymentProcessorTypeDescriptions.forEach((paymentProcessorTypeDescription) -> {
            deletePaymentProcessorTypeDescription(paymentProcessorTypeDescription, deletedBy);
        });
    }

}
