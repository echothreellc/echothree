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

package com.echothree.model.control.carrier.server.control;

import com.echothree.model.control.carrier.common.choice.CarrierChoicesBean;
import com.echothree.model.control.carrier.common.choice.CarrierOptionChoicesBean;
import com.echothree.model.control.carrier.common.choice.CarrierServiceChoicesBean;
import com.echothree.model.control.carrier.common.choice.CarrierTypeChoicesBean;
import com.echothree.model.control.carrier.common.transfer.CarrierOptionDescriptionTransfer;
import com.echothree.model.control.carrier.common.transfer.CarrierOptionTransfer;
import com.echothree.model.control.carrier.common.transfer.CarrierServiceDescriptionTransfer;
import com.echothree.model.control.carrier.common.transfer.CarrierServiceOptionTransfer;
import com.echothree.model.control.carrier.common.transfer.CarrierServiceTransfer;
import com.echothree.model.control.carrier.common.transfer.CarrierTransfer;
import com.echothree.model.control.carrier.common.transfer.CarrierTypeDescriptionTransfer;
import com.echothree.model.control.carrier.common.transfer.CarrierTypeTransfer;
import com.echothree.model.control.carrier.common.transfer.PartyCarrierAccountTransfer;
import com.echothree.model.control.carrier.common.transfer.PartyCarrierTransfer;
import com.echothree.model.control.carrier.server.transfer.CarrierOptionDescriptionTransferCache;
import com.echothree.model.control.carrier.server.transfer.CarrierOptionTransferCache;
import com.echothree.model.control.carrier.server.transfer.CarrierServiceDescriptionTransferCache;
import com.echothree.model.control.carrier.server.transfer.CarrierServiceOptionTransferCache;
import com.echothree.model.control.carrier.server.transfer.CarrierServiceTransferCache;
import com.echothree.model.control.carrier.server.transfer.CarrierTransferCache;
import com.echothree.model.control.carrier.server.transfer.CarrierTypeDescriptionTransferCache;
import com.echothree.model.control.carrier.server.transfer.CarrierTypeTransferCache;
import com.echothree.model.control.carrier.server.transfer.PartyCarrierAccountTransferCache;
import com.echothree.model.control.carrier.server.transfer.PartyCarrierTransferCache;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.shipping.server.control.ShippingControl;
import com.echothree.model.data.carrier.common.pk.CarrierOptionPK;
import com.echothree.model.data.carrier.common.pk.CarrierServicePK;
import com.echothree.model.data.carrier.common.pk.CarrierTypePK;
import com.echothree.model.data.carrier.common.pk.PartyCarrierAccountPK;
import com.echothree.model.data.carrier.server.entity.Carrier;
import com.echothree.model.data.carrier.server.entity.CarrierOption;
import com.echothree.model.data.carrier.server.entity.CarrierOptionDescription;
import com.echothree.model.data.carrier.server.entity.CarrierService;
import com.echothree.model.data.carrier.server.entity.CarrierServiceDescription;
import com.echothree.model.data.carrier.server.entity.CarrierServiceOption;
import com.echothree.model.data.carrier.server.entity.CarrierType;
import com.echothree.model.data.carrier.server.entity.CarrierTypeDescription;
import com.echothree.model.data.carrier.server.entity.PartyCarrier;
import com.echothree.model.data.carrier.server.entity.PartyCarrierAccount;
import com.echothree.model.data.carrier.server.factory.CarrierFactory;
import com.echothree.model.data.carrier.server.factory.CarrierOptionDescriptionFactory;
import com.echothree.model.data.carrier.server.factory.CarrierOptionDetailFactory;
import com.echothree.model.data.carrier.server.factory.CarrierOptionFactory;
import com.echothree.model.data.carrier.server.factory.CarrierServiceDescriptionFactory;
import com.echothree.model.data.carrier.server.factory.CarrierServiceDetailFactory;
import com.echothree.model.data.carrier.server.factory.CarrierServiceFactory;
import com.echothree.model.data.carrier.server.factory.CarrierServiceOptionFactory;
import com.echothree.model.data.carrier.server.factory.CarrierTypeDescriptionFactory;
import com.echothree.model.data.carrier.server.factory.CarrierTypeDetailFactory;
import com.echothree.model.data.carrier.server.factory.CarrierTypeFactory;
import com.echothree.model.data.carrier.server.factory.PartyCarrierAccountDetailFactory;
import com.echothree.model.data.carrier.server.factory.PartyCarrierAccountFactory;
import com.echothree.model.data.carrier.server.factory.PartyCarrierFactory;
import com.echothree.model.data.carrier.server.value.CarrierOptionDescriptionValue;
import com.echothree.model.data.carrier.server.value.CarrierOptionDetailValue;
import com.echothree.model.data.carrier.server.value.CarrierServiceDescriptionValue;
import com.echothree.model.data.carrier.server.value.CarrierServiceDetailValue;
import com.echothree.model.data.carrier.server.value.CarrierServiceOptionValue;
import com.echothree.model.data.carrier.server.value.CarrierTypeDescriptionValue;
import com.echothree.model.data.carrier.server.value.CarrierTypeDetailValue;
import com.echothree.model.data.carrier.server.value.CarrierValue;
import com.echothree.model.data.carrier.server.value.PartyCarrierAccountDetailValue;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.carrier.CarrierForeignKeys.CARRIER_OPTIONS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.carrier.CarrierForeignKeys.CARRIER_OPTION_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.carrier.CarrierForeignKeys.CARRIER_SERVICES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.carrier.CarrierForeignKeys.CARRIER_SERVICE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.carrier.CarrierForeignKeys.CARRIER_SERVICE_OPTIONS_CARRIER_OPTION_FK;
import static com.echothree.model.jooq.server.keys.carrier.CarrierForeignKeys.CARRIER_SERVICE_OPTIONS_CARRIER_SERVICE_FK;
import static com.echothree.model.jooq.server.keys.carrier.CarrierForeignKeys.CARRIER_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.carrier.CarrierForeignKeys.CARRIER_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.carrier.CarrierForeignKeys.PARTY_CARRIERS_PARTY_FK;
import static com.echothree.model.jooq.server.keys.carrier.CarrierForeignKeys.PARTY_CARRIER_ACCOUNTS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.carrier.CarrierForeignKeys.PARTY_CARRIER_ACCOUNT_DETAILS_CARRIER_PARTY_FK;
import static com.echothree.model.jooq.server.keys.carrier.CarrierForeignKeys.PARTY_CARRIER_ACCOUNT_DETAILS_PARTY_FK;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.PARTIES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.PARTIES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.campaign.Campaigns.Campaigns;
import static com.echothree.model.jooq.server.tables.carrier.CarrierOptionDescriptions.CarrierOptionDescriptions;
import static com.echothree.model.jooq.server.tables.carrier.CarrierOptionDetails.CarrierOptionDetails;
import static com.echothree.model.jooq.server.tables.carrier.CarrierOptions.CarrierOptions;
import static com.echothree.model.jooq.server.tables.carrier.CarrierServiceDescriptions.CarrierServiceDescriptions;
import static com.echothree.model.jooq.server.tables.carrier.CarrierServiceDetails.CarrierServiceDetails;
import static com.echothree.model.jooq.server.tables.carrier.CarrierServiceOptions.CarrierServiceOptions;
import static com.echothree.model.jooq.server.tables.carrier.CarrierServices.CarrierServices;
import static com.echothree.model.jooq.server.tables.carrier.CarrierTypeDescriptions.CarrierTypeDescriptions;
import static com.echothree.model.jooq.server.tables.carrier.CarrierTypeDetails.CarrierTypeDetails;
import static com.echothree.model.jooq.server.tables.carrier.CarrierTypes.CarrierTypes;
import static com.echothree.model.jooq.server.tables.carrier.Carriers.Carriers;
import static com.echothree.model.jooq.server.tables.carrier.PartyCarrierAccountDetails.PartyCarrierAccountDetails;
import static com.echothree.model.jooq.server.tables.carrier.PartyCarrierAccounts.PartyCarrierAccounts;
import static com.echothree.model.jooq.server.tables.carrier.PartyCarriers.PartyCarriers;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.party.Parties.Parties;
import static com.echothree.model.jooq.server.tables.party.PartyDetails.PartyDetails;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;

@CommandScope
public class CarrierControl
        extends BaseModelControl {
    
    @Inject
    protected ShippingControl shippingControl;

    /** Creates a new instance of CarrierControl */
    protected CarrierControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Carrier Types
    // --------------------------------------------------------------------------------

    @Inject
    protected CarrierTypeDetailFactory carrierTypeDetailFactory;

    @Inject
    protected CarrierTypeFactory carrierTypeFactory;

    @Inject
    CarrierTypeTransferCache carrierTypeTransferCache;

    public CarrierType createCarrierType(String carrierTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultCarrierType = getDefaultCarrierType();
        var defaultFound = defaultCarrierType != null;

        if(defaultFound && isDefault) {
            var defaultCarrierTypeDetailValue = getDefaultCarrierTypeDetailValueForUpdate();

            defaultCarrierTypeDetailValue.setIsDefault(false);
            updateCarrierTypeFromValue(defaultCarrierTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var carrierType = carrierTypeFactory.create();
        var carrierTypeDetail = carrierTypeDetailFactory.create(carrierType,
                carrierTypeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        carrierType = carrierTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                carrierType.getPrimaryKey());
        carrierType.setActiveDetail(carrierTypeDetail);
        carrierType.setLastDetail(carrierTypeDetail);
        carrierType.store();

        sendEvent(carrierType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return carrierType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.CarrierType */
    public CarrierType getCarrierTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new CarrierTypePK(entityInstance.getEntityUniqueId());

        return carrierTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public CarrierType getCarrierTypeByEntityInstance(EntityInstance entityInstance) {
        return getCarrierTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public CarrierType getCarrierTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getCarrierTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countCarrierTypes() {
        return session.getDslContext()
                .selectCount()
                .from(CarrierTypes)
                .join(CarrierTypeDetails).onKey(CARRIER_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private CarrierType getCarrierTypeByName(String carrierTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CarrierTypes.fields())
                .from(CarrierTypes)
                .join(CarrierTypeDetails).onKey(CARRIER_TYPES_ACTIVE_DETAIL_FK)
                .where(CarrierTypeDetails.CARRIER_TYPE_NAME.eq(carrierTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return carrierTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public CarrierType getCarrierTypeByName(String carrierTypeName) {
        return getCarrierTypeByName(carrierTypeName, EntityPermission.READ_ONLY);
    }

    public CarrierType getCarrierTypeByNameForUpdate(String carrierTypeName) {
        return getCarrierTypeByName(carrierTypeName, EntityPermission.READ_WRITE);
    }

    public CarrierTypeDetailValue getCarrierTypeDetailValueForUpdate(CarrierType carrierType) {
        return carrierType == null? null: carrierType.getLastDetailForUpdate().getCarrierTypeDetailValue().clone();
    }

    public CarrierTypeDetailValue getCarrierTypeDetailValueByNameForUpdate(String carrierTypeName) {
        return getCarrierTypeDetailValueForUpdate(getCarrierTypeByNameForUpdate(carrierTypeName));
    }

    private CarrierType getDefaultCarrierType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CarrierTypes.fields())
                .from(CarrierTypes)
                .join(CarrierTypeDetails).onKey(CARRIER_TYPES_ACTIVE_DETAIL_FK)
                .where(CarrierTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return carrierTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public CarrierType getDefaultCarrierType() {
        return getDefaultCarrierType(EntityPermission.READ_ONLY);
    }

    public CarrierType getDefaultCarrierTypeForUpdate() {
        return getDefaultCarrierType(EntityPermission.READ_WRITE);
    }

    public CarrierTypeDetailValue getDefaultCarrierTypeDetailValueForUpdate() {
        return getDefaultCarrierTypeForUpdate().getLastDetailForUpdate().getCarrierTypeDetailValue().clone();
    }

    private List<CarrierType> getCarrierTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CarrierTypes.fields())
                .from(CarrierTypes)
                .join(CarrierTypeDetails).onKey(CARRIER_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(CarrierTypeDetails.SORT_ORDER, CarrierTypeDetails.CARRIER_TYPE_NAME),
                    CarrierTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return carrierTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<CarrierType> getCarrierTypes() {
        return getCarrierTypes(EntityPermission.READ_ONLY);
    }

    public List<CarrierType> getCarrierTypesForUpdate() {
        return getCarrierTypes(EntityPermission.READ_WRITE);
    }







    public CarrierTypeTransfer getCarrierTypeTransfer(UserVisit userVisit, CarrierType carrierType) {
        return carrierTypeTransferCache.getCarrierTypeTransfer(userVisit, carrierType);
    }

    public List<CarrierTypeTransfer> getCarrierTypeTransfers(UserVisit userVisit, Collection<CarrierType> carrierTypes) {
        List<CarrierTypeTransfer> carrierTypeTransfers = new ArrayList<>(carrierTypes.size());

        carrierTypes.forEach((carrierType) ->
                carrierTypeTransfers.add(carrierTypeTransferCache.getCarrierTypeTransfer(userVisit, carrierType))
        );

        return carrierTypeTransfers;
    }

    public List<CarrierTypeTransfer> getCarrierTypeTransfers(UserVisit userVisit) {
        return getCarrierTypeTransfers(userVisit, getCarrierTypes());
    }



    public CarrierTypeChoicesBean getCarrierTypeChoices(String defaultCarrierTypeChoice, Language language, boolean allowNullChoice) {
        var carrierTypes = getCarrierTypes();
        var size = carrierTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultCarrierTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var carrierType : carrierTypes) {
            var carrierTypeDetail = carrierType.getLastDetail();

            var label = getBestCarrierTypeDescription(carrierType, language);
            var value = carrierTypeDetail.getCarrierTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultCarrierTypeChoice != null && defaultCarrierTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && carrierTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new CarrierTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateCarrierTypeFromValue(CarrierTypeDetailValue carrierTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(carrierTypeDetailValue.hasBeenModified()) {
            var carrierType = carrierTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     carrierTypeDetailValue.getCarrierTypePK());
            var carrierTypeDetail = carrierType.getActiveDetailForUpdate();

            carrierTypeDetail.setThruTime(session.getStartTime());
            carrierTypeDetail.store();

            var carrierTypePK = carrierTypeDetail.getCarrierTypePK(); // Not updated
            var carrierTypeName = carrierTypeDetailValue.getCarrierTypeName();
            var isDefault = carrierTypeDetailValue.getIsDefault();
            var sortOrder = carrierTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultCarrierType = getDefaultCarrierType();
                var defaultFound = defaultCarrierType != null && !defaultCarrierType.equals(carrierType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultCarrierTypeDetailValue = getDefaultCarrierTypeDetailValueForUpdate();

                    defaultCarrierTypeDetailValue.setIsDefault(false);
                    updateCarrierTypeFromValue(defaultCarrierTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            carrierTypeDetail = carrierTypeDetailFactory.create(carrierTypePK,
                    carrierTypeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

            carrierType.setActiveDetail(carrierTypeDetail);
            carrierType.setLastDetail(carrierTypeDetail);

            sendEvent(carrierTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateCarrierTypeFromValue(CarrierTypeDetailValue carrierTypeDetailValue, BasePK updatedBy) {
        updateCarrierTypeFromValue(carrierTypeDetailValue, true, updatedBy);
    }

    public void deleteCarrierType(CarrierType carrierType, BasePK deletedBy) {
        deleteCarrierTypeDescriptionsByCarrierType(carrierType, deletedBy);
        // TODO: deleteCarriersByCarrierType(carrierType, deletedBy);

        var carrierTypeDetail = carrierType.getLastDetailForUpdate();
        carrierTypeDetail.setThruTime(session.getStartTime());
        carrierType.setActiveDetail(null);
        carrierType.store();

        // Check for default, and pick one if necessary
        var defaultCarrierType = getDefaultCarrierType();
        if(defaultCarrierType == null) {
            var carrierTypes = getCarrierTypesForUpdate();

            if(!carrierTypes.isEmpty()) {
                var iter = carrierTypes.iterator();
                if(iter.hasNext()) {
                    defaultCarrierType = iter.next();
                }
                var carrierTypeDetailValue = Objects.requireNonNull(defaultCarrierType).getLastDetailForUpdate().getCarrierTypeDetailValue().clone();

                carrierTypeDetailValue.setIsDefault(true);
                updateCarrierTypeFromValue(carrierTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(carrierType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Carrier Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected CarrierTypeDescriptionFactory carrierTypeDescriptionFactory;

    @Inject
    CarrierTypeDescriptionTransferCache carrierTypeDescriptionTransferCache;

    public CarrierTypeDescription createCarrierTypeDescription(CarrierType carrierType,
            Language language, String description, BasePK createdBy) {
        var carrierTypeDescription = carrierTypeDescriptionFactory.create(carrierType,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(carrierType.getPrimaryKey(), EventTypes.MODIFY, carrierTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return carrierTypeDescription;
    }

    private CarrierTypeDescription getCarrierTypeDescription(CarrierType carrierType,
            Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CarrierTypeDescriptions.fields())
                .from(CarrierTypeDescriptions)
                .where(CarrierTypeDescriptions.CARRIER_TYPE.eq(carrierType.getPrimaryKey()),
                        CarrierTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        CarrierTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return carrierTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public CarrierTypeDescription getCarrierTypeDescription(CarrierType carrierType, Language language) {
        return getCarrierTypeDescription(carrierType, language, EntityPermission.READ_ONLY);
    }

    public CarrierTypeDescription getCarrierTypeDescriptionForUpdate(CarrierType carrierType, Language language) {
        return getCarrierTypeDescription(carrierType, language, EntityPermission.READ_WRITE);
    }

    public CarrierTypeDescriptionValue getCarrierTypeDescriptionValue(CarrierTypeDescription carrierTypeDescription) {
        return carrierTypeDescription == null? null: carrierTypeDescription.getCarrierTypeDescriptionValue().clone();
    }

    public CarrierTypeDescriptionValue getCarrierTypeDescriptionValueForUpdate(CarrierType carrierType, Language language) {
        return getCarrierTypeDescriptionValue(getCarrierTypeDescriptionForUpdate(carrierType, language));
    }

    private List<CarrierTypeDescription> getCarrierTypeDescriptionsByCarrierType(CarrierType carrierType,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CarrierTypeDescriptions.fields())
                    .from(CarrierTypeDescriptions)
                    .join(Languages).onKey(CARRIER_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(CarrierTypeDescriptions.CARRIER_TYPE.eq(carrierType.getPrimaryKey()),
                            CarrierTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    CarrierTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CarrierTypeDescriptions.fields())
                    .from(CarrierTypeDescriptions)
                    .where(CarrierTypeDescriptions.CARRIER_TYPE.eq(carrierType.getPrimaryKey()),
                            CarrierTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return carrierTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<CarrierTypeDescription> getCarrierTypeDescriptionsByCarrierType(CarrierType carrierType) {
        return getCarrierTypeDescriptionsByCarrierType(carrierType, EntityPermission.READ_ONLY);
    }

    public List<CarrierTypeDescription> getCarrierTypeDescriptionsByCarrierTypeForUpdate(CarrierType carrierType) {
        return getCarrierTypeDescriptionsByCarrierType(carrierType, EntityPermission.READ_WRITE);
    }

    public String getBestCarrierTypeDescription(CarrierType carrierType, Language language) {
        String description;
        var carrierTypeDescription = getCarrierTypeDescription(carrierType, language);

        if(carrierTypeDescription == null && !language.getIsDefault()) {
            carrierTypeDescription = getCarrierTypeDescription(carrierType, partyControl.getDefaultLanguage());
        }

        if(carrierTypeDescription == null) {
            description = carrierType.getLastDetail().getCarrierTypeName();
        } else {
            description = carrierTypeDescription.getDescription();
        }

        return description;
    }

    public CarrierTypeDescriptionTransfer getCarrierTypeDescriptionTransfer(UserVisit userVisit, CarrierTypeDescription carrierTypeDescription) {
        return carrierTypeDescriptionTransferCache.getCarrierTypeDescriptionTransfer(userVisit, carrierTypeDescription);
    }

    public List<CarrierTypeDescriptionTransfer> getCarrierTypeDescriptionTransfersByCarrierType(UserVisit userVisit, CarrierType carrierType) {
        var carrierTypeDescriptions = getCarrierTypeDescriptionsByCarrierType(carrierType);
        List<CarrierTypeDescriptionTransfer> carrierTypeDescriptionTransfers = new ArrayList<>(carrierTypeDescriptions.size());

        carrierTypeDescriptions.forEach((carrierTypeDescription) ->
                carrierTypeDescriptionTransfers.add(carrierTypeDescriptionTransferCache.getCarrierTypeDescriptionTransfer(userVisit, carrierTypeDescription))
        );

        return carrierTypeDescriptionTransfers;
    }

    public void updateCarrierTypeDescriptionFromValue(CarrierTypeDescriptionValue carrierTypeDescriptionValue, BasePK updatedBy) {
        if(carrierTypeDescriptionValue.hasBeenModified()) {
            var carrierTypeDescription = carrierTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    carrierTypeDescriptionValue.getPrimaryKey());

            carrierTypeDescription.setThruTime(session.getStartTime());
            carrierTypeDescription.store();

            var carrierType = carrierTypeDescription.getCarrierType();
            var language = carrierTypeDescription.getLanguage();
            var description = carrierTypeDescriptionValue.getDescription();

            carrierTypeDescription = carrierTypeDescriptionFactory.create(carrierType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(carrierType.getPrimaryKey(), EventTypes.MODIFY, carrierTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteCarrierTypeDescription(CarrierTypeDescription carrierTypeDescription, BasePK deletedBy) {
        carrierTypeDescription.setThruTime(session.getStartTime());

        sendEvent(carrierTypeDescription.getCarrierTypePK(), EventTypes.MODIFY, carrierTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteCarrierTypeDescriptionsByCarrierType(CarrierType carrierType, BasePK deletedBy) {
        var carrierTypeDescriptions = getCarrierTypeDescriptionsByCarrierTypeForUpdate(carrierType);

        carrierTypeDescriptions.forEach((carrierTypeDescription) -> 
                deleteCarrierTypeDescription(carrierTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Carriers
    // --------------------------------------------------------------------------------

    @Inject
    protected CarrierFactory carrierFactory;

    @Inject
    CarrierTransferCache carrierTransferCache;

    public Carrier createCarrier(Party party, String carrierName, CarrierType carrierType, Selector geoCodeSelector, Selector itemSelector,
            String accountValidationPattern, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultCarrier = getDefaultCarrier();
        var defaultFound = defaultCarrier != null;
        
        if(defaultFound && isDefault) {
            var defaultCarrierValue = getDefaultCarrierValueForUpdate();
            
            defaultCarrierValue.setIsDefault(false);
            updateCarrierFromValue(defaultCarrierValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var carrier = carrierFactory.create(party, carrierName, carrierType, geoCodeSelector, itemSelector, accountValidationPattern,
                isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, carrier.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return carrier;
    }

    public long countCarriers() {
        return session.getDslContext()
                .selectCount()
                .from(Carriers)
                .where(Carriers.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countCarriersByCarrierType(final CarrierType carrierType) {
        return session.getDslContext()
                .selectCount()
                .from(Campaigns)
                .where(Carriers.CARRIER_TYPE.eq(carrierType.getPrimaryKey()),
                        Carriers.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countCarriersByGeoCodeSelector(final Selector geoCodeSelector) {
        return session.getDslContext()
                .selectCount()
                .from(Campaigns)
                .where(Carriers.GEO_CODE_SELECTOR.eq(geoCodeSelector.getPrimaryKey()),
                        Carriers.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countCarriersByItemSelector(final Selector itemSelector) {
        return session.getDslContext()
                .selectCount()
                .from(Campaigns)
                .where(Carriers.ITEM_SELECTOR.eq(itemSelector.getPrimaryKey()),
                        Carriers.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private Carrier getCarrier(Party party, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Carriers.fields())
                .from(Carriers)
                .where(Carriers.PARTY.eq(party.getPrimaryKey()),
                        Carriers.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return carrierFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public Carrier getCarrier(Party party) {
        return getCarrier(party, EntityPermission.READ_ONLY);
    }
    
    public Carrier getCarrierForUpdate(Party party) {
        return getCarrier(party, EntityPermission.READ_WRITE);
    }
    
    public CarrierValue getCarrierValueUpdate(Party party) {
        return getCarrierForUpdate(party).getCarrierValue().clone();
    }
    
    private Carrier getCarrierByName(String carrierName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Carriers.fields())
                .from(Carriers)
                .where(Carriers.CARRIER_NAME.eq(carrierName),
                        Carriers.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return carrierFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public Carrier getCarrierByName(String carrierName) {
        return getCarrierByName(carrierName, EntityPermission.READ_ONLY);
    }
    
    public Carrier getCarrierByNameForUpdate(String carrierName) {
        return getCarrierByName(carrierName, EntityPermission.READ_WRITE);
    }
    
    public CarrierValue getCarrierValueByNameForUpdate(String carrierName) {
        return getCarrierByNameForUpdate(carrierName).getCarrierValue().clone();
    }
    
    private Carrier getDefaultCarrier(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Carriers.fields())
                .from(Carriers)
                .where(Carriers.IS_DEFAULT.eq(true),
                        Carriers.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return carrierFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public Carrier getDefaultCarrier() {
        return getDefaultCarrier(EntityPermission.READ_ONLY);
    }
    
    public Carrier getDefaultCarrierForUpdate() {
        return getDefaultCarrier(EntityPermission.READ_WRITE);
    }
    
    public CarrierValue getDefaultCarrierValueForUpdate() {
        return getDefaultCarrierForUpdate().getCarrierValue().clone();
    }
    
    private List<Carrier> getCarriers(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Carriers.fields())
                .from(Carriers)
                .where(Carriers.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(Carriers.SORT_ORDER, Carriers.CARRIER_NAME),
                    CarrierFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return carrierFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<Carrier> getCarriers() {
        return getCarriers(EntityPermission.READ_ONLY);
    }
    
    public List<Carrier> getCarriersForUpdate() {
        return getCarriers(EntityPermission.READ_WRITE);
    }
    
    public CarrierTransfer getCarrierTransfer(UserVisit userVisit, Carrier carrier) {
        return carrierTransferCache.getCarrierTransfer(userVisit, carrier);
    }
    
    public CarrierTransfer getCarrierTransfer(UserVisit userVisit, Party party) {
        return getCarrierTransfer(userVisit, getCarrier(party));
    }
    
    public List<CarrierTransfer> getCarrierTransfers(UserVisit userVisit, Collection<Carrier> carriers) {
        var carrierTransfers = new ArrayList<CarrierTransfer>(carriers.size());

        carriers.forEach((carrier) -> carrierTransfers.add(carrierTransferCache.getCarrierTransfer(userVisit, carrier)));

        return carrierTransfers;
    }

    public List<CarrierTransfer> getCarrierTransfers(UserVisit userVisit) {
        var carriers = getCarriers();
        List<CarrierTransfer> carrierTransfers = new ArrayList<>(carriers.size());
        
        carriers.forEach((carrier) -> {
            carrierTransfers.add(carrierTransferCache.getCarrierTransfer(userVisit, carrier));
        });
        
        return carrierTransfers;
    }
    
    public CarrierChoicesBean getCarrierChoices(String defaultCarrierChoice, Language language, boolean allowNullChoice) {
        var carriers = getCarriers();
        var size = carriers.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultCarrierChoice == null) {
                defaultValue = "";
            }
        }

        for(var carrier : carriers) {
            var partyGroup = partyControl.getPartyGroup(carrier.getParty());

            var label = partyGroup.getName();
            var value = carrier.getCarrierName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultCarrierChoice != null && defaultCarrierChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && carrier.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new CarrierChoicesBean(labels, values, defaultValue);
    }

    private void updateCarrierFromValue(CarrierValue carrierValue, boolean checkDefault, BasePK updatedBy) {
        if(carrierValue.hasBeenModified()) {
            var carrier = carrierFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    carrierValue.getPrimaryKey());
            
            carrier.setThruTime(session.getStartTime());
            carrier.store();

            var partyPK = carrier.getPartyPK();
            var carrierName = carrierValue.getCarrierName();
            var carrierTypePK = carrier.getCarrierTypePK(); // Not updated
            var geoCodeSelectorPK = carrierValue.getGeoCodeSelectorPK();
            var itemSelectorPK = carrierValue.getItemSelectorPK();
            var accountValidationPattern = carrierValue.getAccountValidationPattern();
            var isDefault = carrierValue.getIsDefault();
            var sortOrder = carrierValue.getSortOrder();
            
            if(checkDefault) {
                var defaultCarrier = getDefaultCarrier();
                var defaultFound = defaultCarrier != null && !defaultCarrier.equals(carrier);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultCarrierValue = getDefaultCarrierValueForUpdate();
                    
                    defaultCarrierValue.setIsDefault(false);
                    updateCarrierFromValue(defaultCarrierValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            carrier = carrierFactory.create(partyPK, carrierName, carrierTypePK, geoCodeSelectorPK, itemSelectorPK, accountValidationPattern,
                    isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(partyPK, EventTypes.MODIFY, carrier.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updateCarrierFromValue(CarrierValue carrierValue, BasePK updatedBy) {
        updateCarrierFromValue(carrierValue, true, updatedBy);
    }
    
    public void deleteCarrier(Carrier carrier, BasePK deletedBy) {
        var party = carrier.getPartyForUpdate();
        
        deletePartyCarriersByCarrierParty(party, deletedBy);
        deletePartyCarrierAccountsByCarrierParty(party, deletedBy);
        deleteCarrierServicesByCarrierParty(party, deletedBy);
        deleteCarrierOptionsByCarrierParty(party, deletedBy);
        
        partyControl.deleteParty(party, deletedBy);
        
        carrier.setThruTime(session.getStartTime());
        carrier.store();
        
        // Check for default, and pick one if necessary
        var defaultCarrier = getDefaultCarrier();
        if(defaultCarrier == null) {
            var carriers = getCarriersForUpdate();
            
            if(!carriers.isEmpty()) {
                var iter = carriers.iterator();
                if(iter.hasNext()) {
                    defaultCarrier = iter.next();
                }

                var defaultCarrierValue = defaultCarrier.getCarrierValue();
                
                defaultCarrierValue.setIsDefault(true);
                updateCarrierFromValue(defaultCarrierValue, false, deletedBy);
            }
        }
        
        sendEvent(carrier.getPartyPK(), EventTypes.MODIFY, carrier.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Carrier Services
    // --------------------------------------------------------------------------------

    @Inject
    protected CarrierServiceDetailFactory carrierServiceDetailFactory;

    @Inject
    protected CarrierServiceFactory carrierServiceFactory;

    @Inject
    CarrierServiceTransferCache carrierServiceTransferCache;

    public CarrierService createCarrierService(Party carrierParty, String carrierServiceName, Selector geoCodeSelector, Selector itemSelector,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultCarrierService = getDefaultCarrierService(carrierParty);
        var defaultFound = defaultCarrierService != null;
        
        if(defaultFound && isDefault) {
            var defaultCarrierServiceDetailValue = getDefaultCarrierServiceDetailValueForUpdate(carrierParty);
            
            defaultCarrierServiceDetailValue.setIsDefault(false);
            updateCarrierServiceFromValue(defaultCarrierServiceDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var carrierService = carrierServiceFactory.create();
        var carrierServiceDetail = carrierServiceDetailFactory.create(
                carrierService, carrierParty, carrierServiceName, geoCodeSelector, itemSelector, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME);
        
        // Convert to R/W
        carrierService = carrierServiceFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                carrierService.getPrimaryKey());
        carrierService.setActiveDetail(carrierServiceDetail);
        carrierService.setLastDetail(carrierServiceDetail);
        carrierService.store();
        
        sendEvent(carrierService.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return carrierService;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.CarrierService */
    public CarrierService getCarrierServiceByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new CarrierServicePK(entityInstance.getEntityUniqueId());

        return carrierServiceFactory.getEntityFromPK(entityPermission, pk);
    }

    public CarrierService getCarrierServiceByEntityInstance(EntityInstance entityInstance) {
        return getCarrierServiceByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public CarrierService getCarrierServiceByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getCarrierServiceByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countCarrierServicesByCarrierParty(final Party carrierParty) {
        return session.getDslContext()
                .selectCount()
                .from(CarrierServices)
                .join(CarrierServiceDetails).onKey(CARRIER_SERVICES_ACTIVE_DETAIL_FK)
                .where(CarrierServiceDetails.CARRIER_PARTY.eq(carrierParty.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countCarrierServicesByGeoCodeSelector(final Selector geoCodeSelector) {
        return session.getDslContext()
                .selectCount()
                .from(CarrierServices)
                .join(CarrierServiceDetails).onKey(CARRIER_SERVICES_ACTIVE_DETAIL_FK)
                .where(CarrierServiceDetails.GEO_CODE_SELECTOR.eq(geoCodeSelector.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countCarrierServicesByItemSelector(final Selector itemSelector) {
        return session.getDslContext()
                .selectCount()
                .from(CarrierServices)
                .join(CarrierServiceDetails).onKey(CARRIER_SERVICES_ACTIVE_DETAIL_FK)
                .where(CarrierServiceDetails.ITEM_SELECTOR.eq(itemSelector.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<CarrierService> getCarrierServices(Party carrierParty, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CarrierServices.fields())
                .from(CarrierServices)
                .join(CarrierServiceDetails).onKey(CARRIER_SERVICES_ACTIVE_DETAIL_FK)
                .where(CarrierServiceDetails.CARRIER_PARTY.eq(carrierParty.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(CarrierServiceDetails.SORT_ORDER, CarrierServiceDetails.CARRIER_SERVICE_NAME),
                    CarrierServiceFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return carrierServiceFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CarrierService> getCarrierServices(Party carrierParty) {
        return getCarrierServices(carrierParty, EntityPermission.READ_ONLY);
    }
    
    public List<CarrierService> getCarrierServicesForUpdate(Party carrierParty) {
        return getCarrierServices(carrierParty, EntityPermission.READ_WRITE);
    }
    
    private CarrierService getDefaultCarrierService(Party carrierParty, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CarrierServices.fields())
                .from(CarrierServices)
                .join(CarrierServiceDetails).onKey(CARRIER_SERVICES_ACTIVE_DETAIL_FK)
                .where(CarrierServiceDetails.CARRIER_PARTY.eq(carrierParty.getPrimaryKey()),
                        CarrierServiceDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return carrierServiceFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CarrierService getDefaultCarrierService(Party carrierParty) {
        return getDefaultCarrierService(carrierParty, EntityPermission.READ_ONLY);
    }
    
    public CarrierService getDefaultCarrierServiceForUpdate(Party carrierParty) {
        return getDefaultCarrierService(carrierParty, EntityPermission.READ_WRITE);
    }
    
    public CarrierServiceDetailValue getDefaultCarrierServiceDetailValueForUpdate(Party carrierParty) {
        return getDefaultCarrierServiceForUpdate(carrierParty).getLastDetailForUpdate().getCarrierServiceDetailValue().clone();
    }
    
    private CarrierService getCarrierServiceByName(Party carrierParty, String carrierServiceName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CarrierServices.fields())
                .from(CarrierServices)
                .join(CarrierServiceDetails).onKey(CARRIER_SERVICES_ACTIVE_DETAIL_FK)
                .where(CarrierServiceDetails.CARRIER_PARTY.eq(carrierParty.getPrimaryKey()),
                        CarrierServiceDetails.CARRIER_SERVICE_NAME.eq(carrierServiceName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return carrierServiceFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CarrierService getCarrierServiceByName(Party carrierParty, String carrierServiceName) {
        return getCarrierServiceByName(carrierParty, carrierServiceName, EntityPermission.READ_ONLY);
    }
    
    public CarrierService getCarrierServiceByNameForUpdate(Party carrierParty, String carrierServiceName) {
        return getCarrierServiceByName(carrierParty, carrierServiceName, EntityPermission.READ_WRITE);
    }
    
    public CarrierServiceDetailValue getCarrierServiceDetailValueForUpdate(CarrierService carrierService) {
        return carrierService == null? null: carrierService.getLastDetailForUpdate().getCarrierServiceDetailValue().clone();
    }
    
    public CarrierServiceDetailValue getCarrierServiceDetailValueByNameForUpdate(Party carrierParty, String carrierServiceName) {
        return getCarrierServiceDetailValueForUpdate(getCarrierServiceByNameForUpdate(carrierParty, carrierServiceName));
    }
    
    public CarrierServiceChoicesBean getCarrierServiceChoices(String defaultCarrierServiceChoice, Language language,
            boolean allowNullChoice, Party carrierParty) {
        var carrierPartyPriorities = getCarrierServices(carrierParty);
        var size = carrierPartyPriorities.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultCarrierServiceChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var carrierService : carrierPartyPriorities) {
            var carrierServiceDetail = carrierService.getLastDetail();
            var label = getBestCarrierServiceDescription(carrierService, language);
            var value = carrierServiceDetail.getCarrierServiceName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultCarrierServiceChoice != null && defaultCarrierServiceChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && carrierServiceDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new CarrierServiceChoicesBean(labels, values, defaultValue);
    }
    
    public CarrierServiceTransfer getCarrierServiceTransfer(UserVisit userVisit, CarrierService carrierService) {
        return carrierServiceTransferCache.getCarrierServiceTransfer(userVisit, carrierService);
    }

    public List<CarrierServiceTransfer> getCarrierServiceTransfers(UserVisit userVisit, Collection<CarrierService> carrierServices) {
        List<CarrierServiceTransfer> carrierServiceTransfers = new ArrayList<>(carrierServices.size());

        carrierServices.forEach((carrierService) ->
                carrierServiceTransfers.add(carrierServiceTransferCache.getCarrierServiceTransfer(userVisit, carrierService))
        );

        return carrierServiceTransfers;
    }

    public List<CarrierServiceTransfer> getCarrierServiceTransfers(UserVisit userVisit, Party carrierParty) {
        return getCarrierServiceTransfers(userVisit, getCarrierServices(carrierParty));
    }
    
    private void updateCarrierServiceFromValue(CarrierServiceDetailValue carrierServiceDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(carrierServiceDetailValue.hasBeenModified()) {
            var carrierService = carrierServiceFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     carrierServiceDetailValue.getCarrierServicePK());
            var carrierServiceDetail = carrierService.getActiveDetailForUpdate();
            
            carrierServiceDetail.setThruTime(session.getStartTime());
            carrierServiceDetail.store();

            var carrierServicePK = carrierServiceDetail.getCarrierServicePK();
            var carrierParty = carrierServiceDetail.getCarrierParty();
            var carrierPartyPK = carrierParty.getPrimaryKey();
            var carrierServiceName = carrierServiceDetailValue.getCarrierServiceName();
            var geoCodeSelectorPK = carrierServiceDetailValue.getGeoCodeSelectorPK();
            var itemSelectorPK = carrierServiceDetailValue.getItemSelectorPK();
            var isDefault = carrierServiceDetailValue.getIsDefault();
            var sortOrder = carrierServiceDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultCarrierService = getDefaultCarrierService(carrierParty);
                var defaultFound = defaultCarrierService != null && !defaultCarrierService.equals(carrierService);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultCarrierServiceDetailValue = getDefaultCarrierServiceDetailValueForUpdate(carrierParty);
                    
                    defaultCarrierServiceDetailValue.setIsDefault(false);
                    updateCarrierServiceFromValue(defaultCarrierServiceDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            carrierServiceDetail = carrierServiceDetailFactory.create(carrierServicePK, carrierPartyPK,
                    carrierServiceName, geoCodeSelectorPK, itemSelectorPK, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            carrierService.setActiveDetail(carrierServiceDetail);
            carrierService.setLastDetail(carrierServiceDetail);
            
            sendEvent(carrierServicePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateCarrierServiceFromValue(CarrierServiceDetailValue carrierServiceDetailValue, BasePK updatedBy) {
        updateCarrierServiceFromValue(carrierServiceDetailValue, true, updatedBy);
    }
    
    private void deleteCarrierService(CarrierService carrierService, boolean checkDefault, BasePK deletedBy) {
        
        deleteCarrierServiceDescriptionsByCarrierService(carrierService, deletedBy);
        deleteCarrierServiceOptionsByCarrierService(carrierService, deletedBy);
        shippingControl.deleteShippingMethodCarrierServicesByCarrierService(carrierService, deletedBy);

        var carrierServiceDetail = carrierService.getLastDetailForUpdate();
        carrierServiceDetail.setThruTime(session.getStartTime());
        carrierService.setActiveDetail(null);
        carrierService.store();
        
        if(checkDefault) {
            // Check for default, and pick one if necessary
            var carrierParty = carrierServiceDetail.getCarrierParty();
            var defaultCarrierService = getDefaultCarrierService(carrierParty);
            
            if(defaultCarrierService == null) {
                var carrierPartyPriorities = getCarrierServicesForUpdate(carrierParty);
                
                if(!carrierPartyPriorities.isEmpty()) {
                    var iter = carrierPartyPriorities.iterator();
                    
                    if(iter.hasNext()) {
                        defaultCarrierService = iter.next();
                    }

                    var carrierServiceDetailValue = Objects.requireNonNull(defaultCarrierService).getLastDetailForUpdate().getCarrierServiceDetailValue().clone();
                    
                    carrierServiceDetailValue.setIsDefault(true);
                    updateCarrierServiceFromValue(carrierServiceDetailValue, false, deletedBy);
                }
            }
        }
        
        sendEvent(carrierService.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteCarrierService(CarrierService carrierService, BasePK deletedBy) {
        deleteCarrierService(carrierService, true, deletedBy);
    }
    
    public void deleteCarrierServicesByCarrierParty(Party carrierParty, BasePK deletedBy) {
        var carrierPartyPriorities = getCarrierServicesForUpdate(carrierParty);
        
        carrierPartyPriorities.forEach((carrierService) -> {
            deleteCarrierService(carrierService, false, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Carrier Service Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected CarrierServiceDescriptionFactory carrierServiceDescriptionFactory;

    @Inject
    CarrierServiceDescriptionTransferCache carrierServiceDescriptionTransferCache;

    public CarrierServiceDescription createCarrierServiceDescription(CarrierService carrierService, Language language, String description,
            BasePK createdBy) {
        var carrierServiceDescription = carrierServiceDescriptionFactory.create(carrierService,
                language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(carrierService.getPrimaryKey(), EventTypes.MODIFY, carrierServiceDescription.getPrimaryKey(),
                EventTypes.CREATE, createdBy);
        
        return carrierServiceDescription;
    }
    
    private CarrierServiceDescription getCarrierServiceDescription(CarrierService carrierService, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CarrierServiceDescriptions.fields())
                .from(CarrierServiceDescriptions)
                .where(CarrierServiceDescriptions.CARRIER_SERVICE.eq(carrierService.getPrimaryKey()),
                        CarrierServiceDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        CarrierServiceDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return carrierServiceDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CarrierServiceDescription getCarrierServiceDescription(CarrierService carrierService, Language language) {
        return getCarrierServiceDescription(carrierService, language, EntityPermission.READ_ONLY);
    }
    
    public CarrierServiceDescription getCarrierServiceDescriptionForUpdate(CarrierService carrierService, Language language) {
        return getCarrierServiceDescription(carrierService, language, EntityPermission.READ_WRITE);
    }
    
    public CarrierServiceDescriptionValue getCarrierServiceDescriptionValue(CarrierServiceDescription carrierServiceDescription) {
        return carrierServiceDescription == null? null: carrierServiceDescription.getCarrierServiceDescriptionValue().clone();
    }
    
    public CarrierServiceDescriptionValue getCarrierServiceDescriptionValueForUpdate(CarrierService carrierService, Language language) {
        return getCarrierServiceDescriptionValue(getCarrierServiceDescriptionForUpdate(carrierService, language));
    }
    
    private List<CarrierServiceDescription> getCarrierServiceDescriptionsByCarrierService(CarrierService carrierService, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CarrierServiceDescriptions.fields())
                    .from(CarrierServiceDescriptions)
                    .join(Languages).onKey(CARRIER_SERVICE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(CarrierServiceDescriptions.CARRIER_SERVICE.eq(carrierService.getPrimaryKey()),
                            CarrierServiceDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    CarrierServiceDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CarrierServiceDescriptions.fields())
                    .from(CarrierServiceDescriptions)
                    .where(CarrierServiceDescriptions.CARRIER_SERVICE.eq(carrierService.getPrimaryKey()),
                            CarrierServiceDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return carrierServiceDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CarrierServiceDescription> getCarrierServiceDescriptionsByCarrierService(CarrierService carrierService) {
        return getCarrierServiceDescriptionsByCarrierService(carrierService, EntityPermission.READ_ONLY);
    }
    
    public List<CarrierServiceDescription> getCarrierServiceDescriptionsByCarrierServiceForUpdate(CarrierService carrierService) {
        return getCarrierServiceDescriptionsByCarrierService(carrierService, EntityPermission.READ_WRITE);
    }
    
    public String getBestCarrierServiceDescription(CarrierService carrierService, Language language) {
        String description;
        var carrierServiceDescription = getCarrierServiceDescription(carrierService, language);
        
        if(carrierServiceDescription == null && !language.getIsDefault()) {
            carrierServiceDescription = getCarrierServiceDescription(carrierService, partyControl.getDefaultLanguage());
        }
        
        if(carrierServiceDescription == null) {
            description = carrierService.getLastDetail().getCarrierServiceName();
        } else {
            description = carrierServiceDescription.getDescription();
        }
        
        return description;
    }
    
    public CarrierServiceDescriptionTransfer getCarrierServiceDescriptionTransfer(UserVisit userVisit, CarrierServiceDescription carrierServiceDescription) {
        return carrierServiceDescriptionTransferCache.getCarrierServiceDescriptionTransfer(userVisit, carrierServiceDescription);
    }
    
    public List<CarrierServiceDescriptionTransfer> getCarrierServiceDescriptionTransfers(UserVisit userVisit, CarrierService carrierService) {
        var carrierServiceDescriptions = getCarrierServiceDescriptionsByCarrierService(carrierService);
        List<CarrierServiceDescriptionTransfer> carrierServiceDescriptionTransfers = new ArrayList<>(carrierServiceDescriptions.size());
        
        carrierServiceDescriptions.forEach((carrierServiceDescription) ->
                carrierServiceDescriptionTransfers.add(carrierServiceDescriptionTransferCache.getCarrierServiceDescriptionTransfer(userVisit, carrierServiceDescription))
        );
        
        return carrierServiceDescriptionTransfers;
    }
    
    public void updateCarrierServiceDescriptionFromValue(CarrierServiceDescriptionValue carrierServiceDescriptionValue, BasePK updatedBy) {
        if(carrierServiceDescriptionValue.hasBeenModified()) {
            var carrierServiceDescription = carrierServiceDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     carrierServiceDescriptionValue.getPrimaryKey());
            
            carrierServiceDescription.setThruTime(session.getStartTime());
            carrierServiceDescription.store();

            var carrierService = carrierServiceDescription.getCarrierService();
            var language = carrierServiceDescription.getLanguage();
            var description = carrierServiceDescriptionValue.getDescription();
            
            carrierServiceDescription = carrierServiceDescriptionFactory.create(carrierService, language,
                    description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(carrierService.getPrimaryKey(), EventTypes.MODIFY, carrierServiceDescription.getPrimaryKey(),
                    EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteCarrierServiceDescription(CarrierServiceDescription carrierServiceDescription, BasePK deletedBy) {
        carrierServiceDescription.setThruTime(session.getStartTime());
        
        sendEvent(carrierServiceDescription.getCarrierServicePK(), EventTypes.MODIFY,
                carrierServiceDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteCarrierServiceDescriptionsByCarrierService(CarrierService carrierService, BasePK deletedBy) {
        var carrierServiceDescriptions = getCarrierServiceDescriptionsByCarrierServiceForUpdate(carrierService);
        
        carrierServiceDescriptions.forEach((carrierServiceDescription) -> 
                deleteCarrierServiceDescription(carrierServiceDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Carrier Options
    // --------------------------------------------------------------------------------

    @Inject
    protected CarrierOptionDetailFactory carrierOptionDetailFactory;

    @Inject
    protected CarrierOptionFactory carrierOptionFactory;

    @Inject
    CarrierOptionTransferCache carrierOptionTransferCache;

    public CarrierOption createCarrierOption(Party carrierParty, String carrierOptionName, Boolean isRecommended, Boolean isRequired,
            Selector recommendedGeoCodeSelector, Selector requiredGeoCodeSelector, Selector recommendedItemSelector, Selector requiredItemSelector,
            Selector recommendedOrderSelector, Selector requiredOrderSelector, Selector recommendedShipmentSelector, Selector requiredShipmentSelector,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultCarrierOption = getDefaultCarrierOption(carrierParty);
        var defaultFound = defaultCarrierOption != null;
        
        if(defaultFound && isDefault) {
            var defaultCarrierOptionDetailValue = getDefaultCarrierOptionDetailValueForUpdate(carrierParty);
            
            defaultCarrierOptionDetailValue.setIsDefault(false);
            updateCarrierOptionFromValue(defaultCarrierOptionDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var carrierOption = carrierOptionFactory.create();
        var carrierOptionDetail = carrierOptionDetailFactory.create(carrierOption, carrierParty, carrierOptionName, isRecommended,
                isRequired, recommendedGeoCodeSelector, requiredGeoCodeSelector, recommendedItemSelector, requiredItemSelector, recommendedOrderSelector,
                requiredOrderSelector, recommendedShipmentSelector, requiredShipmentSelector, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        carrierOption = carrierOptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                carrierOption.getPrimaryKey());
        carrierOption.setActiveDetail(carrierOptionDetail);
        carrierOption.setLastDetail(carrierOptionDetail);
        carrierOption.store();
        
        sendEvent(carrierOption.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return carrierOption;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.CarrierOption */
    public CarrierOption getCarrierOptionByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new CarrierOptionPK(entityInstance.getEntityUniqueId());

        return carrierOptionFactory.getEntityFromPK(entityPermission, pk);
    }

    public CarrierOption getCarrierOptionByEntityInstance(EntityInstance entityInstance) {
        return getCarrierOptionByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public CarrierOption getCarrierOptionByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getCarrierOptionByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countCarrierOptionsByCarrierParty(final Party carrierParty) {
        return session.getDslContext()
                .selectCount()
                .from(CarrierOptions)
                .join(CarrierOptionDetails).onKey(CARRIER_OPTIONS_ACTIVE_DETAIL_FK)
                .where(CarrierOptionDetails.CARRIER_PARTY.eq(carrierParty.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countCarrierOptionsByRecommendedGeoCodeSelector(final Selector recommendedGeoCodeSelector) {
        return session.getDslContext()
                .selectCount()
                .from(CarrierOptions)
                .join(CarrierOptionDetails).onKey(CARRIER_OPTIONS_ACTIVE_DETAIL_FK)
                .where(CarrierOptionDetails.RECOMMENDED_GEO_CODE_SELECTOR.eq(recommendedGeoCodeSelector.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countCarrierOptionsByRequiredGeoCodeSelector(final Selector requiredGeoCodeSelector) {
        return session.getDslContext()
                .selectCount()
                .from(CarrierOptions)
                .join(CarrierOptionDetails).onKey(CARRIER_OPTIONS_ACTIVE_DETAIL_FK)
                .where(CarrierOptionDetails.REQUIRED_GEO_CODE_SELECTOR.eq(requiredGeoCodeSelector.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countCarrierOptionsByRecommendedItemSelector(final Selector recommendedItemSelector) {
        return session.getDslContext()
                .selectCount()
                .from(CarrierOptions)
                .join(CarrierOptionDetails).onKey(CARRIER_OPTIONS_ACTIVE_DETAIL_FK)
                .where(CarrierOptionDetails.RECOMMENDED_ITEM_SELECTOR.eq(recommendedItemSelector.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countCarrierOptionsByRequiredItemSelector(final Selector requiredItemSelector) {
        return session.getDslContext()
                .selectCount()
                .from(CarrierOptions)
                .join(CarrierOptionDetails).onKey(CARRIER_OPTIONS_ACTIVE_DETAIL_FK)
                .where(CarrierOptionDetails.REQUIRED_ITEM_SELECTOR.eq(requiredItemSelector.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countCarrierOptionsByRecommendedOrderSelector(final Selector recommendedOrderSelector) {
        return session.getDslContext()
                .selectCount()
                .from(CarrierOptions)
                .join(CarrierOptionDetails).onKey(CARRIER_OPTIONS_ACTIVE_DETAIL_FK)
                .where(CarrierOptionDetails.RECOMMENDED_ORDER_SELECTOR.eq(recommendedOrderSelector.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countCarrierOptionsByRequiredOrderSelector(final Selector requiredOrderSelector) {
        return session.getDslContext()
                .selectCount()
                .from(CarrierOptions)
                .join(CarrierOptionDetails).onKey(CARRIER_OPTIONS_ACTIVE_DETAIL_FK)
                .where(CarrierOptionDetails.REQUIRED_ORDER_SELECTOR.eq(requiredOrderSelector.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countCarrierOptionsByRecommendedShipmentSelector(final Selector recommendedShipmentSelector) {
        return session.getDslContext()
                .selectCount()
                .from(CarrierOptions)
                .join(CarrierOptionDetails).onKey(CARRIER_OPTIONS_ACTIVE_DETAIL_FK)
                .where(CarrierOptionDetails.RECOMMENDED_SHIPMENT_SELECTOR.eq(recommendedShipmentSelector.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countCarrierOptionsByRequiredShipmentSelector(final Selector requiredShipmentSelector) {
        return session.getDslContext()
                .selectCount()
                .from(CarrierOptions)
                .join(CarrierOptionDetails).onKey(CARRIER_OPTIONS_ACTIVE_DETAIL_FK)
                .where(CarrierOptionDetails.REQUIRED_SHIPMENT_SELECTOR.eq(requiredShipmentSelector.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<CarrierOption> getCarrierOptions(Party carrierParty, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CarrierOptions.fields())
                .from(CarrierOptions)
                .join(CarrierOptionDetails).onKey(CARRIER_OPTIONS_ACTIVE_DETAIL_FK)
                .where(CarrierOptionDetails.CARRIER_PARTY.eq(carrierParty.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(CarrierOptionDetails.SORT_ORDER, CarrierOptionDetails.CARRIER_OPTION_NAME),
                    CarrierOptionFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return carrierOptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CarrierOption> getCarrierOptions(Party carrierParty) {
        return getCarrierOptions(carrierParty, EntityPermission.READ_ONLY);
    }
    
    public List<CarrierOption> getCarrierOptionsForUpdate(Party carrierParty) {
        return getCarrierOptions(carrierParty, EntityPermission.READ_WRITE);
    }
    
    private CarrierOption getDefaultCarrierOption(Party carrierParty, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CarrierOptions.fields())
                .from(CarrierOptions)
                .join(CarrierOptionDetails).onKey(CARRIER_OPTIONS_ACTIVE_DETAIL_FK)
                .where(CarrierOptionDetails.CARRIER_PARTY.eq(carrierParty.getPrimaryKey()),
                        CarrierOptionDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return carrierOptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CarrierOption getDefaultCarrierOption(Party carrierParty) {
        return getDefaultCarrierOption(carrierParty, EntityPermission.READ_ONLY);
    }
    
    public CarrierOption getDefaultCarrierOptionForUpdate(Party carrierParty) {
        return getDefaultCarrierOption(carrierParty, EntityPermission.READ_WRITE);
    }
    
    public CarrierOptionDetailValue getDefaultCarrierOptionDetailValueForUpdate(Party carrierParty) {
        return getDefaultCarrierOptionForUpdate(carrierParty).getLastDetailForUpdate().getCarrierOptionDetailValue().clone();
    }
    
    private CarrierOption getCarrierOptionByName(Party carrierParty, String carrierOptionName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CarrierOptions.fields())
                .from(CarrierOptions)
                .join(CarrierOptionDetails).onKey(CARRIER_OPTIONS_ACTIVE_DETAIL_FK)
                .where(CarrierOptionDetails.CARRIER_PARTY.eq(carrierParty.getPrimaryKey()),
                        CarrierOptionDetails.CARRIER_OPTION_NAME.eq(carrierOptionName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return carrierOptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CarrierOption getCarrierOptionByName(Party carrierParty, String carrierOptionName) {
        return getCarrierOptionByName(carrierParty, carrierOptionName, EntityPermission.READ_ONLY);
    }
    
    public CarrierOption getCarrierOptionByNameForUpdate(Party carrierParty, String carrierOptionName) {
        return getCarrierOptionByName(carrierParty, carrierOptionName, EntityPermission.READ_WRITE);
    }
    
    public CarrierOptionDetailValue getCarrierOptionDetailValueForUpdate(CarrierOption carrierOption) {
        return carrierOption == null? null: carrierOption.getLastDetailForUpdate().getCarrierOptionDetailValue().clone();
    }
    
    public CarrierOptionDetailValue getCarrierOptionDetailValueByNameForUpdate(Party carrierParty, String carrierOptionName) {
        return getCarrierOptionDetailValueForUpdate(getCarrierOptionByNameForUpdate(carrierParty, carrierOptionName));
    }
    
    public CarrierOptionChoicesBean getCarrierOptionChoices(String defaultCarrierOptionChoice, Language language,
            boolean allowNullChoice, Party carrierParty) {
        var carrierPartyPriorities = getCarrierOptions(carrierParty);
        var size = carrierPartyPriorities.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultCarrierOptionChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var carrierOption : carrierPartyPriorities) {
            var carrierOptionDetail = carrierOption.getLastDetail();
            var label = getBestCarrierOptionDescription(carrierOption, language);
            var value = carrierOptionDetail.getCarrierOptionName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultCarrierOptionChoice != null && defaultCarrierOptionChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && carrierOptionDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new CarrierOptionChoicesBean(labels, values, defaultValue);
    }
    
    public CarrierOptionTransfer getCarrierOptionTransfer(UserVisit userVisit, CarrierOption carrierOption) {
        return carrierOptionTransferCache.getCarrierOptionTransfer(userVisit, carrierOption);
    }
    
    public List<CarrierOptionTransfer> getCarrierOptionTransfers(UserVisit userVisit, Collection<CarrierOption> carrierOptions) {
        List<CarrierOptionTransfer> carrierOptionTransfers = new ArrayList<>(carrierOptions.size());
        
        carrierOptions.forEach((carrierOption) ->
                carrierOptionTransfers.add(carrierOptionTransferCache.getCarrierOptionTransfer(userVisit, carrierOption))
        );
        
        return carrierOptionTransfers;
    }
    
    public List<CarrierOptionTransfer> getCarrierOptionTransfers(UserVisit userVisit, Party carrierParty) {
        return getCarrierOptionTransfers(userVisit, getCarrierOptions(carrierParty));
    }
    
    private void updateCarrierOptionFromValue(CarrierOptionDetailValue carrierOptionDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(carrierOptionDetailValue.hasBeenModified()) {
            var carrierOption = carrierOptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     carrierOptionDetailValue.getCarrierOptionPK());
            var carrierOptionDetail = carrierOption.getActiveDetailForUpdate();
            
            carrierOptionDetail.setThruTime(session.getStartTime());
            carrierOptionDetail.store();

            var carrierOptionPK = carrierOptionDetail.getCarrierOptionPK();
            var carrierParty = carrierOptionDetail.getCarrierParty();
            var carrierPartyPK = carrierParty.getPrimaryKey();
            var carrierOptionName = carrierOptionDetailValue.getCarrierOptionName();
            var isRecommended = carrierOptionDetailValue.getIsRecommended();
            var isRequired = carrierOptionDetailValue.getIsRequired();
            var recommendedGeoCodeSelectorPK = carrierOptionDetailValue.getRecommendedGeoCodeSelectorPK();
            var requiredGeoCodeSelectorPK = carrierOptionDetailValue.getRequiredGeoCodeSelectorPK();
            var recommendedItemSelectorPK = carrierOptionDetailValue.getRecommendedItemSelectorPK();
            var requiredItemSelectorPK = carrierOptionDetailValue.getRequiredItemSelectorPK();
            var recommendedOrderSelectorPK = carrierOptionDetailValue.getRecommendedOrderSelectorPK();
            var requiredOrderSelectorPK = carrierOptionDetailValue.getRequiredOrderSelectorPK();
            var recommendedShipmentSelectorPK = carrierOptionDetailValue.getRecommendedShipmentSelectorPK();
            var requiredShipmentSelectorPK = carrierOptionDetailValue.getRequiredShipmentSelectorPK();
            var isDefault = carrierOptionDetailValue.getIsDefault();
            var sortOrder = carrierOptionDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultCarrierOption = getDefaultCarrierOption(carrierParty);
                var defaultFound = defaultCarrierOption != null && !defaultCarrierOption.equals(carrierOption);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultCarrierOptionDetailValue = getDefaultCarrierOptionDetailValueForUpdate(carrierParty);
                    
                    defaultCarrierOptionDetailValue.setIsDefault(false);
                    updateCarrierOptionFromValue(defaultCarrierOptionDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            carrierOptionDetail = carrierOptionDetailFactory.create(carrierOptionPK, carrierPartyPK, carrierOptionName, isRecommended, isRequired,
                    recommendedGeoCodeSelectorPK, requiredGeoCodeSelectorPK, recommendedItemSelectorPK, requiredItemSelectorPK, recommendedOrderSelectorPK,
                    requiredOrderSelectorPK, recommendedShipmentSelectorPK, requiredShipmentSelectorPK, isDefault, sortOrder, session.getStartTime(),
                    Session.MAX_TIME);
            
            carrierOption.setActiveDetail(carrierOptionDetail);
            carrierOption.setLastDetail(carrierOptionDetail);
            
            sendEvent(carrierOptionPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateCarrierOptionFromValue(CarrierOptionDetailValue carrierOptionDetailValue, BasePK updatedBy) {
        updateCarrierOptionFromValue(carrierOptionDetailValue, true, updatedBy);
    }
    
    private void deleteCarrierOption(CarrierOption carrierOption, boolean checkDefault, BasePK deletedBy) {
        deleteCarrierOptionDescriptionsByCarrierOption(carrierOption, deletedBy);
        deleteCarrierServiceOptionsByCarrierOption(carrierOption, deletedBy);

        var carrierOptionDetail = carrierOption.getLastDetailForUpdate();
        carrierOptionDetail.setThruTime(session.getStartTime());
        carrierOption.setActiveDetail(null);
        carrierOption.store();
        
        if(checkDefault) {
            // Check for default, and pick one if necessary
            var carrierParty = carrierOptionDetail.getCarrierParty();
            var defaultCarrierOption = getDefaultCarrierOption(carrierParty);
            
            if(defaultCarrierOption == null) {
                var carrierPartyPriorities = getCarrierOptionsForUpdate(carrierParty);
                
                if(!carrierPartyPriorities.isEmpty()) {
                    var iter = carrierPartyPriorities.iterator();
                    
                    if(iter.hasNext()) {
                        defaultCarrierOption = iter.next();
                    }

                    var carrierOptionDetailValue = Objects.requireNonNull(defaultCarrierOption).getLastDetailForUpdate().getCarrierOptionDetailValue().clone();
                    
                    carrierOptionDetailValue.setIsDefault(true);
                    updateCarrierOptionFromValue(carrierOptionDetailValue, false, deletedBy);
                }
            }
        }
        
        sendEvent(carrierOption.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteCarrierOption(CarrierOption carrierOption, BasePK deletedBy) {
        deleteCarrierOption(carrierOption, true, deletedBy);
    }
    
    public void deleteCarrierOptionsByCarrierParty(Party carrierParty, BasePK deletedBy) {
        var carrierPartyPriorities = getCarrierOptionsForUpdate(carrierParty);
        
        carrierPartyPriorities.forEach((carrierOption) -> {
            deleteCarrierOption(carrierOption, false, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Carrier Option Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected CarrierOptionDescriptionFactory carrierOptionDescriptionFactory;

    @Inject
    CarrierOptionDescriptionTransferCache carrierOptionDescriptionTransferCache;

    public CarrierOptionDescription createCarrierOptionDescription(CarrierOption carrierOption, Language language, String description,
            BasePK createdBy) {
        var carrierOptionDescription = carrierOptionDescriptionFactory.create(carrierOption,
                language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(carrierOption.getPrimaryKey(), EventTypes.MODIFY, carrierOptionDescription.getPrimaryKey(),
                EventTypes.CREATE, createdBy);
        
        return carrierOptionDescription;
    }
    
    private CarrierOptionDescription getCarrierOptionDescription(CarrierOption carrierOption, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CarrierOptionDescriptions.fields())
                .from(CarrierOptionDescriptions)
                .where(CarrierOptionDescriptions.CARRIER_OPTION.eq(carrierOption.getPrimaryKey()),
                        CarrierOptionDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        CarrierOptionDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return carrierOptionDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CarrierOptionDescription getCarrierOptionDescription(CarrierOption carrierOption, Language language) {
        return getCarrierOptionDescription(carrierOption, language, EntityPermission.READ_ONLY);
    }
    
    public CarrierOptionDescription getCarrierOptionDescriptionForUpdate(CarrierOption carrierOption, Language language) {
        return getCarrierOptionDescription(carrierOption, language, EntityPermission.READ_WRITE);
    }
    
    public CarrierOptionDescriptionValue getCarrierOptionDescriptionValue(CarrierOptionDescription carrierOptionDescription) {
        return carrierOptionDescription == null? null: carrierOptionDescription.getCarrierOptionDescriptionValue().clone();
    }
    
    public CarrierOptionDescriptionValue getCarrierOptionDescriptionValueForUpdate(CarrierOption carrierOption, Language language) {
        return getCarrierOptionDescriptionValue(getCarrierOptionDescriptionForUpdate(carrierOption, language));
    }
    
    private List<CarrierOptionDescription> getCarrierOptionDescriptionsByCarrierOption(CarrierOption carrierOption, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CarrierOptionDescriptions.fields())
                    .from(CarrierOptionDescriptions)
                    .join(Languages).onKey(CARRIER_OPTION_DESCRIPTIONS_LANGUAGE_FK)
                    .where(CarrierOptionDescriptions.CARRIER_OPTION.eq(carrierOption.getPrimaryKey()),
                            CarrierOptionDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    CarrierOptionDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CarrierOptionDescriptions.fields())
                    .from(CarrierOptionDescriptions)
                    .where(CarrierOptionDescriptions.CARRIER_OPTION.eq(carrierOption.getPrimaryKey()),
                            CarrierOptionDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return carrierOptionDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CarrierOptionDescription> getCarrierOptionDescriptionsByCarrierOption(CarrierOption carrierOption) {
        return getCarrierOptionDescriptionsByCarrierOption(carrierOption, EntityPermission.READ_ONLY);
    }
    
    public List<CarrierOptionDescription> getCarrierOptionDescriptionsByCarrierOptionForUpdate(CarrierOption carrierOption) {
        return getCarrierOptionDescriptionsByCarrierOption(carrierOption, EntityPermission.READ_WRITE);
    }
    
    public String getBestCarrierOptionDescription(CarrierOption carrierOption, Language language) {
        String description;
        var carrierOptionDescription = getCarrierOptionDescription(carrierOption, language);
        
        if(carrierOptionDescription == null && !language.getIsDefault()) {
            carrierOptionDescription = getCarrierOptionDescription(carrierOption, partyControl.getDefaultLanguage());
        }
        
        if(carrierOptionDescription == null) {
            description = carrierOption.getLastDetail().getCarrierOptionName();
        } else {
            description = carrierOptionDescription.getDescription();
        }
        
        return description;
    }
    
    public CarrierOptionDescriptionTransfer getCarrierOptionDescriptionTransfer(UserVisit userVisit, CarrierOptionDescription carrierOptionDescription) {
        return carrierOptionDescriptionTransferCache.getCarrierOptionDescriptionTransfer(userVisit, carrierOptionDescription);
    }
    
    public List<CarrierOptionDescriptionTransfer> getCarrierOptionDescriptionTransfers(UserVisit userVisit, CarrierOption carrierOption) {
        var carrierOptionDescriptions = getCarrierOptionDescriptionsByCarrierOption(carrierOption);
        List<CarrierOptionDescriptionTransfer> carrierOptionDescriptionTransfers = new ArrayList<>(carrierOptionDescriptions.size());
        
        carrierOptionDescriptions.forEach((carrierOptionDescription) ->
                carrierOptionDescriptionTransfers.add(carrierOptionDescriptionTransferCache.getCarrierOptionDescriptionTransfer(userVisit, carrierOptionDescription))
        );
        
        return carrierOptionDescriptionTransfers;
    }
    
    public void updateCarrierOptionDescriptionFromValue(CarrierOptionDescriptionValue carrierOptionDescriptionValue, BasePK updatedBy) {
        if(carrierOptionDescriptionValue.hasBeenModified()) {
            var carrierOptionDescription = carrierOptionDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     carrierOptionDescriptionValue.getPrimaryKey());
            
            carrierOptionDescription.setThruTime(session.getStartTime());
            carrierOptionDescription.store();

            var carrierOption = carrierOptionDescription.getCarrierOption();
            var language = carrierOptionDescription.getLanguage();
            var description = carrierOptionDescriptionValue.getDescription();
            
            carrierOptionDescription = carrierOptionDescriptionFactory.create(carrierOption, language,
                    description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(carrierOption.getPrimaryKey(), EventTypes.MODIFY, carrierOptionDescription.getPrimaryKey(),
                    EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteCarrierOptionDescription(CarrierOptionDescription carrierOptionDescription, BasePK deletedBy) {
        carrierOptionDescription.setThruTime(session.getStartTime());
        
        sendEvent(carrierOptionDescription.getCarrierOptionPK(), EventTypes.MODIFY,
                carrierOptionDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteCarrierOptionDescriptionsByCarrierOption(CarrierOption carrierOption, BasePK deletedBy) {
        var carrierOptionDescriptions = getCarrierOptionDescriptionsByCarrierOptionForUpdate(carrierOption);
        
        carrierOptionDescriptions.forEach((carrierOptionDescription) -> 
                deleteCarrierOptionDescription(carrierOptionDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Carrier Service Options
    // --------------------------------------------------------------------------------

    @Inject
    protected CarrierServiceOptionFactory carrierServiceOptionFactory;

    @Inject
    CarrierServiceOptionTransferCache carrierServiceOptionTransferCache;

    public CarrierServiceOption createCarrierServiceOption(CarrierService carrierService, CarrierOption carrierOption, Boolean isRecommended,
            Boolean isRequired, Selector recommendedGeoCodeSelector, Selector requiredGeoCodeSelector, Selector recommendedItemSelector,
            Selector requiredItemSelector, Selector recommendedOrderSelector, Selector requiredOrderSelector, Selector recommendedShipmentSelector,
            Selector requiredShipmentSelector, BasePK createdBy) {
        var carrierServiceOption = carrierServiceOptionFactory.create(carrierService, carrierOption, isRecommended, isRequired,
                recommendedGeoCodeSelector, requiredGeoCodeSelector, recommendedItemSelector, requiredItemSelector, recommendedOrderSelector,
                requiredOrderSelector, recommendedShipmentSelector, requiredShipmentSelector, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(carrierService.getPrimaryKey(), EventTypes.MODIFY, carrierServiceOption.getPrimaryKey(),
                EventTypes.CREATE, createdBy);
        
        return carrierServiceOption;
    }

    public long countCarrierServiceOptionsByCarrierService(final CarrierService carrierService) {
        return session.getDslContext()
                .selectCount()
                .from(CarrierServiceOptions)
                .where(CarrierServiceOptions.CARRIER_SERVICE.eq(carrierService.getPrimaryKey()),
                        CarrierServiceOptions.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countCarrierServiceOptionsByCarrierOption(final CarrierOption carrierOption) {
        return session.getDslContext()
                .selectCount()
                .from(CarrierServiceOptions)
                .where(CarrierServiceOptions.CARRIER_OPTION.eq(carrierOption.getPrimaryKey()),
                        CarrierServiceOptions.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countCarrierServiceOptionsByRecommendedGeoCodeSelector(final Selector recommendedGeoCodeSelector) {
        return session.getDslContext()
                .selectCount()
                .from(CarrierServiceOptions)
                .where(CarrierServiceOptions.RECOMMENDED_GEO_CODE_SELECTOR.eq(recommendedGeoCodeSelector.getPrimaryKey()),
                        CarrierServiceOptions.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countCarrierServiceOptionsByRequiredGeoCodeSelector(final Selector requiredGeoCodeSelector) {
        return session.getDslContext()
                .selectCount()
                .from(CarrierServiceOptions)
                .where(CarrierServiceOptions.REQUIRED_GEO_CODE_SELECTOR.eq(requiredGeoCodeSelector.getPrimaryKey()),
                        CarrierServiceOptions.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countCarrierServiceOptionsByRecommendedItemSelector(final Selector recommendedItemSelector) {
        return session.getDslContext()
                .selectCount()
                .from(CarrierServiceOptions)
                .where(CarrierServiceOptions.RECOMMENDED_ITEM_SELECTOR.eq(recommendedItemSelector.getPrimaryKey()),
                        CarrierServiceOptions.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countCarrierServiceOptionsByRequiredItemSelector(final Selector requiredItemSelector) {
        return session.getDslContext()
                .selectCount()
                .from(CarrierServiceOptions)
                .where(CarrierServiceOptions.REQUIRED_ITEM_SELECTOR.eq(requiredItemSelector.getPrimaryKey()),
                        CarrierServiceOptions.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countCarrierServiceOptionsByRecommendedOrderSelector(final Selector recommendedOrderSelector) {
        return session.getDslContext()
                .selectCount()
                .from(CarrierServiceOptions)
                .where(CarrierServiceOptions.RECOMMENDED_ORDER_SELECTOR.eq(recommendedOrderSelector.getPrimaryKey()),
                        CarrierServiceOptions.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countCarrierServiceOptionsByRequiredOrderSelector(final Selector requiredOrderSelector) {
        return session.getDslContext()
                .selectCount()
                .from(CarrierServiceOptions)
                .where(CarrierServiceOptions.REQUIRED_ORDER_SELECTOR.eq(requiredOrderSelector.getPrimaryKey()),
                        CarrierServiceOptions.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countCarrierServiceOptionsByRecommendedShipmentSelector(final Selector recommendedShipmentSelector) {
        return session.getDslContext()
                .selectCount()
                .from(CarrierServiceOptions)
                .where(CarrierServiceOptions.RECOMMENDED_SHIPMENT_SELECTOR.eq(recommendedShipmentSelector.getPrimaryKey()),
                        CarrierServiceOptions.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countCarrierServiceOptionsByRequiredShipmentSelector(final Selector requiredShipmentSelector) {
        return session.getDslContext()
                .selectCount()
                .from(CarrierServiceOptions)
                .where(CarrierServiceOptions.REQUIRED_SHIPMENT_SELECTOR.eq(requiredShipmentSelector.getPrimaryKey()),
                        CarrierServiceOptions.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private CarrierServiceOption getCarrierServiceOption(CarrierService carrierService, CarrierOption carrierOption,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CarrierServiceOptions.fields())
                .from(CarrierServiceOptions)
                .where(CarrierServiceOptions.CARRIER_SERVICE.eq(carrierService.getPrimaryKey()),
                        CarrierServiceOptions.CARRIER_OPTION.eq(carrierOption.getPrimaryKey()),
                        CarrierServiceOptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return carrierServiceOptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CarrierServiceOption getCarrierServiceOption(CarrierService carrierService, CarrierOption carrierOption) {
        return getCarrierServiceOption(carrierService, carrierOption, EntityPermission.READ_ONLY);
    }
    
    public CarrierServiceOption getCarrierServiceOptionForUpdate(CarrierService carrierService, CarrierOption carrierOption) {
        return getCarrierServiceOption(carrierService, carrierOption, EntityPermission.READ_WRITE);
    }
    
    public CarrierServiceOptionValue getCarrierServiceOptionValue(CarrierServiceOption carrierServiceOption) {
        return carrierServiceOption == null? null: carrierServiceOption.getCarrierServiceOptionValue().clone();
    }
    
    public CarrierServiceOptionValue getCarrierServiceOptionValueForUpdate(CarrierService carrierService, CarrierOption carrierOption) {
        return getCarrierServiceOptionValue(getCarrierServiceOptionForUpdate(carrierService, carrierOption));
    }
    
    private List<CarrierServiceOption> getCarrierServiceOptionsByCarrierService(CarrierService carrierService, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CarrierServiceOptions.fields())
                    .from(CarrierServiceOptions)
                    .join(CarrierOptions).onKey(CARRIER_SERVICE_OPTIONS_CARRIER_OPTION_FK)
                    .join(CarrierOptionDetails).onKey(CARRIER_OPTIONS_ACTIVE_DETAIL_FK)
                    .where(CarrierServiceOptions.CARRIER_SERVICE.eq(carrierService.getPrimaryKey()),
                            CarrierServiceOptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(CarrierOptionDetails.SORT_ORDER, CarrierOptionDetails.CARRIER_OPTION_NAME),
                    CarrierServiceOptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CarrierServiceOptions.fields())
                    .from(CarrierServiceOptions)
                    .where(CarrierServiceOptions.CARRIER_SERVICE.eq(carrierService.getPrimaryKey()),
                            CarrierServiceOptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return carrierServiceOptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CarrierServiceOption> getCarrierServiceOptionsByCarrierService(CarrierService carrierService) {
        return getCarrierServiceOptionsByCarrierService(carrierService, EntityPermission.READ_ONLY);
    }
    
    public List<CarrierServiceOption> getCarrierServiceOptionsByCarrierServiceForUpdate(CarrierService carrierService) {
        return getCarrierServiceOptionsByCarrierService(carrierService, EntityPermission.READ_WRITE);
    }
    
    private List<CarrierServiceOption> getCarrierServiceOptionsByCarrierOption(CarrierOption carrierOption, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CarrierServiceOptions.fields())
                    .from(CarrierServiceOptions)
                    .join(CarrierServices).onKey(CARRIER_SERVICE_OPTIONS_CARRIER_SERVICE_FK)
                    .join(CarrierServiceDetails).onKey(CARRIER_SERVICES_ACTIVE_DETAIL_FK)
                    .where(CarrierServiceOptions.CARRIER_OPTION.eq(carrierOption.getPrimaryKey()),
                            CarrierServiceOptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(CarrierServiceDetails.SORT_ORDER, CarrierServiceDetails.CARRIER_SERVICE_NAME),
                    CarrierServiceOptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CarrierServiceOptions.fields())
                    .from(CarrierServiceOptions)
                    .where(CarrierServiceOptions.CARRIER_OPTION.eq(carrierOption.getPrimaryKey()),
                            CarrierServiceOptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return carrierServiceOptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CarrierServiceOption> getCarrierServiceOptionsByCarrierOption(CarrierOption carrierOption) {
        return getCarrierServiceOptionsByCarrierOption(carrierOption, EntityPermission.READ_ONLY);
    }
    
    public List<CarrierServiceOption> getCarrierServiceOptionsByCarrierOptionForUpdate(CarrierOption carrierOption) {
        return getCarrierServiceOptionsByCarrierOption(carrierOption, EntityPermission.READ_WRITE);
    }
    
    public CarrierServiceOptionTransfer getCarrierServiceOptionTransfer(UserVisit userVisit, CarrierServiceOption carrierServiceOption) {
        return carrierServiceOptionTransferCache.getCarrierServiceOptionTransfer(userVisit, carrierServiceOption);
    }
    
    public List<CarrierServiceOptionTransfer> getCarrierServiceOptionTransfers(UserVisit userVisit, Collection<CarrierServiceOption> carrierServiceOptions) {
        List<CarrierServiceOptionTransfer> carrierServiceOptionTransfers = new ArrayList<>(carrierServiceOptions.size());
        
        carrierServiceOptions.forEach((carrierServiceOption) ->
                carrierServiceOptionTransfers.add(carrierServiceOptionTransferCache.getCarrierServiceOptionTransfer(userVisit, carrierServiceOption))
        );
        
        return carrierServiceOptionTransfers;
    }
    
    public List<CarrierServiceOptionTransfer> getCarrierServiceOptionTransfersByCarrierService(UserVisit userVisit,
            CarrierService carrierService) {
        return getCarrierServiceOptionTransfers(userVisit, getCarrierServiceOptionsByCarrierService(carrierService));
    }
    
    public List<CarrierServiceOptionTransfer> getCarrierServiceOptionTransfersByCarrierOption(UserVisit userVisit,
            CarrierOption carrierOption) {
        return getCarrierServiceOptionTransfers(userVisit, getCarrierServiceOptionsByCarrierOption(carrierOption));
    }
    
    public void updateCarrierServiceOptionFromValue(CarrierServiceOptionValue carrierServiceOptionValue, BasePK updatedBy) {
        if(carrierServiceOptionValue.hasBeenModified()) {
            var carrierServiceOption = carrierServiceOptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     carrierServiceOptionValue.getPrimaryKey());
            
            carrierServiceOption.setThruTime(session.getStartTime());
            carrierServiceOption.store();

            var carrierServicePK = carrierServiceOption.getCarrierServicePK();
            var carrierOptionPK = carrierServiceOption.getCarrierOptionPK();
            var isRecommended = carrierServiceOptionValue.getIsRecommended();
            var isRequired = carrierServiceOptionValue.getIsRequired();
            var recommendedGeoCodeSelectorPK = carrierServiceOptionValue.getRecommendedGeoCodeSelectorPK();
            var requiredGeoCodeSelectorPK = carrierServiceOptionValue.getRequiredGeoCodeSelectorPK();
            var recommendedItemSelectorPK = carrierServiceOptionValue.getRecommendedItemSelectorPK();
            var requiredItemSelectorPK = carrierServiceOptionValue.getRequiredItemSelectorPK();
            var recommendedOrderSelectorPK = carrierServiceOptionValue.getRecommendedOrderSelectorPK();
            var requiredOrderSelectorPK = carrierServiceOptionValue.getRequiredOrderSelectorPK();
            var recommendedShipmentSelectorPK = carrierServiceOptionValue.getRecommendedShipmentSelectorPK();
            var requiredShipmentSelectorPK = carrierServiceOptionValue.getRequiredShipmentSelectorPK();
            
            carrierServiceOption = carrierServiceOptionFactory.create(carrierServicePK, carrierOptionPK, isRecommended, isRequired,
                    recommendedGeoCodeSelectorPK, requiredGeoCodeSelectorPK, recommendedItemSelectorPK, requiredItemSelectorPK, recommendedOrderSelectorPK,
                    requiredOrderSelectorPK, recommendedShipmentSelectorPK, requiredShipmentSelectorPK, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(carrierServicePK, EventTypes.MODIFY, carrierServiceOption.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteCarrierServiceOption(CarrierServiceOption carrierServiceOption, BasePK deletedBy) {
        carrierServiceOption.setThruTime(session.getStartTime());
        
        sendEvent(carrierServiceOption.getCarrierService().getPrimaryKey(), EventTypes.MODIFY,
                carrierServiceOption.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteCarrierServiceOptions(List<CarrierServiceOption> carrierServiceOptions, BasePK deletedBy) {
        carrierServiceOptions.forEach((carrierServiceOption) -> 
                deleteCarrierServiceOption(carrierServiceOption, deletedBy)
        );
    }
    
    public void deleteCarrierServiceOptionsByCarrierService(CarrierService carrierService, BasePK deletedBy) {
        deleteCarrierServiceOptions(getCarrierServiceOptionsByCarrierServiceForUpdate(carrierService), deletedBy);
    }
    
    public void deleteCarrierServiceOptionsByCarrierOption(CarrierOption carrierOption, BasePK deletedBy) {
        deleteCarrierServiceOptions(getCarrierServiceOptionsByCarrierOptionForUpdate(carrierOption), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Carriers
    // --------------------------------------------------------------------------------

    @Inject
    protected PartyCarrierFactory partyCarrierFactory;

    @Inject
    PartyCarrierTransferCache partyCarrierTransferCache;

    public PartyCarrier createPartyCarrier(Party party, Party carrierParty, BasePK createdBy) {
        var partyCarrier = partyCarrierFactory.create(party, carrierParty, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partyCarrier.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partyCarrier;
    }

    public long countPartyCarriersByParty(final Party party) {
        return session.getDslContext()
                .selectCount()
                .from(PartyCarriers)
                .where(PartyCarriers.PARTY.eq(party.getPrimaryKey()),
                        PartyCarriers.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyCarriersByCarrierParty(final Party carrierParty) {
        return session.getDslContext()
                .selectCount()
                .from(PartyCarriers)
                .where(PartyCarriers.CARRIER_PARTY.eq(carrierParty.getPrimaryKey()),
                        PartyCarriers.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private PartyCarrier getPartyCarrier(Party party, Party carrierParty, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyCarriers.fields())
                .from(PartyCarriers)
                .where(PartyCarriers.PARTY.eq(party.getPrimaryKey()),
                        PartyCarriers.CARRIER_PARTY.eq(carrierParty.getPrimaryKey()),
                        PartyCarriers.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyCarrierFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public PartyCarrier getPartyCarrier(Party party, Party carrierParty) {
        return getPartyCarrier(party, carrierParty, EntityPermission.READ_ONLY);
    }
    
    public PartyCarrier getPartyCarrierForUpdate(Party party, Party carrierParty) {
        return getPartyCarrier(party, carrierParty, EntityPermission.READ_WRITE);
    }
    
    private List<PartyCarrier> getPartyCarriersByParty(Party party, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyCarriers.fields())
                    .from(PartyCarriers)
                    .join(Parties).onKey(PARTY_CARRIERS_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_ACTIVE_DETAIL_FK)
                    .where(PartyCarriers.PARTY.eq(party.getPrimaryKey()),
                            PartyCarriers.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyDetails.PARTY_NAME),
                    PartyCarrierFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyCarriers.fields())
                    .from(PartyCarriers)
                    .where(PartyCarriers.PARTY.eq(party.getPrimaryKey()),
                            PartyCarriers.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyCarrierFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PartyCarrier> getPartyCarriersByParty(Party party) {
        return getPartyCarriersByParty(party, EntityPermission.READ_ONLY);
    }
    
    public List<PartyCarrier> getPartyCarriersByPartyForUpdate(Party party) {
        return getPartyCarriersByParty(party, EntityPermission.READ_WRITE);
    }
    
    private List<PartyCarrier> getPartyCarriersByCarrierParty(Party carrierParty, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyCarriers.fields())
                    .from(PartyCarriers)
                    .join(Carriers).on(PartyCarriers.CARRIER_PARTY.eq(Carriers.PARTY))
                    .where(PartyCarriers.CARRIER_PARTY.eq(carrierParty.getPrimaryKey()),
                            PartyCarriers.THRU_TIME.eq(Session.MAX_TIME),
                            Carriers.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyDetails.PARTY_NAME),
                    PartyCarrierFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyCarriers.fields())
                    .from(PartyCarriers)
                    .where(PartyCarriers.PARTY.eq(carrierParty.getPrimaryKey()),
                            PartyCarriers.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyCarrierFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PartyCarrier> getPartyCarriersByCarrierParty(Party carrierParty) {
        return getPartyCarriersByCarrierParty(carrierParty, EntityPermission.READ_ONLY);
    }
    
    public List<PartyCarrier> getPartyCarriersByCarrierPartyForUpdate(Party carrierParty) {
        return getPartyCarriersByCarrierParty(carrierParty, EntityPermission.READ_WRITE);
    }
    
    public PartyCarrierTransfer getPartyCarrierTransfer(UserVisit userVisit, PartyCarrier partyCarrier) {
        return partyCarrierTransferCache.getPartyCarrierTransfer(userVisit, partyCarrier);
    }
    
    public List<PartyCarrierTransfer> getPartyCarrierTransfersByParty(UserVisit userVisit, Collection<PartyCarrier> partyCarriers) {
        List<PartyCarrierTransfer> partyCarrierTransfers = new ArrayList<>(partyCarriers.size());

        partyCarriers.forEach((partyCarrier) ->
                partyCarrierTransfers.add(partyCarrierTransferCache.getPartyCarrierTransfer(userVisit, partyCarrier))
        );

        return partyCarrierTransfers;
    }

    public List<PartyCarrierTransfer> getPartyCarrierTransfersByParty(UserVisit userVisit, List<PartyCarrier> partyCarriers) {
        return getPartyCarrierTransfersByParty(userVisit, (Collection<PartyCarrier>)partyCarriers);
    }

    public List<PartyCarrierTransfer> getPartyCarrierTransfersByParty(UserVisit userVisit, Party party) {
        return getPartyCarrierTransfersByParty(userVisit, getPartyCarriersByParty(party));
    }

    public List<PartyCarrierTransfer> getPartyCarrierTransfersByCarrierParty(UserVisit userVisit, Party carrierParty) {
        return getPartyCarrierTransfersByParty(userVisit, getPartyCarriersByCarrierParty(carrierParty));
    }

    public void deletePartyCarrier(PartyCarrier partyCarrier, BasePK deletedBy) {
        partyCarrier.setThruTime(session.getStartTime());
        
        sendEvent(partyCarrier.getParty().getPrimaryKey(), EventTypes.MODIFY, partyCarrier.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deletePartyCarriers(List<PartyCarrier> partyCarriers, BasePK deletedBy) {
        partyCarriers.forEach((partyCarrier) -> 
                deletePartyCarrier(partyCarrier, deletedBy)
        );
    }
    
    public void deletePartyCarriersByParty(Party party, BasePK deletedBy) {
        deletePartyCarriers(getPartyCarriersByPartyForUpdate(party), deletedBy);
    }
    
    public void deletePartyCarriersByCarrierParty(Party carrierParty, BasePK deletedBy) {
        deletePartyCarriers(getPartyCarriersByCarrierPartyForUpdate(carrierParty), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Carrier Accounts
    // --------------------------------------------------------------------------------

    @Inject
    protected PartyCarrierAccountDetailFactory partyCarrierAccountDetailFactory;

    @Inject
    protected PartyCarrierAccountFactory partyCarrierAccountFactory;

    @Inject
    PartyCarrierAccountTransferCache partyCarrierAccountTransferCache;

    public PartyCarrierAccount createPartyCarrierAccount(Party party, Party carrierParty, String account, Boolean alwaysUseThirdPartyBilling, BasePK createdBy) {
        var partyCarrierAccount = partyCarrierAccountFactory.create();
        var partyCarrierAccountDetail = partyCarrierAccountDetailFactory.create(partyCarrierAccount, party, carrierParty,
                account, alwaysUseThirdPartyBilling, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        partyCarrierAccount = partyCarrierAccountFactory.getEntityFromPK(EntityPermission.READ_WRITE, partyCarrierAccount.getPrimaryKey());
        partyCarrierAccount.setActiveDetail(partyCarrierAccountDetail);
        partyCarrierAccount.setLastDetail(partyCarrierAccountDetail);
        partyCarrierAccount.store();

        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partyCarrierAccount.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return partyCarrierAccount;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.PartyCarrierAccount */
    public PartyCarrierAccount getPartyCarrierAccountByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new PartyCarrierAccountPK(entityInstance.getEntityUniqueId());

        return partyCarrierAccountFactory.getEntityFromPK(entityPermission, pk);
    }

    public PartyCarrierAccount getPartyCarrierAccountByEntityInstance(EntityInstance entityInstance) {
        return getPartyCarrierAccountByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PartyCarrierAccount getPartyCarrierAccountByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getPartyCarrierAccountByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countPartyCarrierAccountsByParty(final Party party) {
        return session.getDslContext()
                .selectCount()
                .from(PartyCarrierAccounts)
                .join(PartyCarrierAccountDetails).onKey(PARTY_CARRIER_ACCOUNTS_ACTIVE_DETAIL_FK)
                .where(PartyCarrierAccountDetails.PARTY.eq(party.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyCarrierAccountsByCarrierParty(final Party carrierParty) {
        return session.getDslContext()
                .selectCount()
                .from(PartyCarrierAccounts)
                .join(PartyCarrierAccountDetails).onKey(PARTY_CARRIER_ACCOUNTS_ACTIVE_DETAIL_FK)
                .where(PartyCarrierAccountDetails.CARRIER_PARTY.eq(carrierParty.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private PartyCarrierAccount getPartyCarrierAccount(Party party, Party carrierParty, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyCarrierAccounts.fields())
                .from(PartyCarrierAccounts)
                .join(PartyCarrierAccountDetails).onKey(PARTY_CARRIER_ACCOUNTS_ACTIVE_DETAIL_FK)
                .where(PartyCarrierAccountDetails.PARTY.eq(party.getPrimaryKey()),
                        PartyCarrierAccountDetails.CARRIER_PARTY.eq(carrierParty.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyCarrierAccountFactory.getEntityFromQuery(entityPermission, query);
    }

    public PartyCarrierAccount getPartyCarrierAccount(Party party, Party carrierParty) {
        return getPartyCarrierAccount(party, carrierParty, EntityPermission.READ_ONLY);
    }

    public PartyCarrierAccount getPartyCarrierAccountForUpdate(Party party, Party carrierParty) {
        return getPartyCarrierAccount(party, carrierParty, EntityPermission.READ_WRITE);
    }

    public PartyCarrierAccountDetailValue getPartyCarrierAccountDetailValueForUpdate(PartyCarrierAccount partyCarrierAccount) {
        return partyCarrierAccount == null? null: partyCarrierAccount.getLastDetailForUpdate().getPartyCarrierAccountDetailValue().clone();
    }

    public PartyCarrierAccountDetailValue getPartyCarrierAccountDetailValueForUpdate(Party party, Party carrierParty) {
        return getPartyCarrierAccountDetailValueForUpdate(getPartyCarrierAccountForUpdate(party, carrierParty));
    }

    private List<PartyCarrierAccount> getPartyCarrierAccountsByParty(Party party, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyCarrierAccounts.fields())
                    .from(PartyCarrierAccounts)
                    .join(PartyCarrierAccountDetails).onKey(PARTY_CARRIER_ACCOUNTS_ACTIVE_DETAIL_FK)
                    .join(Parties).onKey(PARTY_CARRIER_ACCOUNT_DETAILS_CARRIER_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .where(PartyCarrierAccountDetails.PARTY.eq(party.getPrimaryKey()))
                    .orderBy(PartyDetails.PARTY_NAME),
                    PartyCarrierAccountFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyCarrierAccounts.fields())
                    .from(PartyCarrierAccounts)
                    .join(PartyCarrierAccountDetails).onKey(PARTY_CARRIER_ACCOUNTS_ACTIVE_DETAIL_FK)
                    .where(PartyCarrierAccountDetails.PARTY.eq(party.getPrimaryKey()))
                    .forUpdate();
        };

        return partyCarrierAccountFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PartyCarrierAccount> getPartyCarrierAccountsByParty(Party party) {
        return getPartyCarrierAccountsByParty(party, EntityPermission.READ_ONLY);
    }

    public List<PartyCarrierAccount> getPartyCarrierAccountsByPartyForUpdate(Party party) {
        return getPartyCarrierAccountsByParty(party, EntityPermission.READ_WRITE);
    }

    private List<PartyCarrierAccount> getPartyCarrierAccountsByCarrierParty(Party carrierParty, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyCarrierAccounts.fields())
                    .from(PartyCarrierAccounts)
                    .join(PartyCarrierAccountDetails).onKey(PARTY_CARRIER_ACCOUNTS_ACTIVE_DETAIL_FK)
                    .join(Parties).onKey(PARTY_CARRIER_ACCOUNT_DETAILS_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .where(PartyCarrierAccountDetails.CARRIER_PARTY.eq(carrierParty.getPrimaryKey()))
                    .orderBy(PartyDetails.PARTY_NAME),
                    PartyCarrierAccountFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyCarrierAccounts.fields())
                    .from(PartyCarrierAccounts)
                    .join(PartyCarrierAccountDetails).onKey(PARTY_CARRIER_ACCOUNTS_ACTIVE_DETAIL_FK)
                    .where(PartyCarrierAccountDetails.CARRIER_PARTY.eq(carrierParty.getPrimaryKey()))
                    .forUpdate();
        };

        return partyCarrierAccountFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PartyCarrierAccount> getPartyCarrierAccountsByCarrierParty(Party carrierParty) {
        return getPartyCarrierAccountsByCarrierParty(carrierParty, EntityPermission.READ_ONLY);
    }

    public List<PartyCarrierAccount> getPartyCarrierAccountsByCarrierPartyForUpdate(Party carrierParty) {
        return getPartyCarrierAccountsByCarrierParty(carrierParty, EntityPermission.READ_WRITE);
    }

    public PartyCarrierAccountTransfer getPartyCarrierAccountTransfer(UserVisit userVisit, PartyCarrierAccount partyCarrierAccount) {
        return partyCarrierAccountTransferCache.getPartyCarrierAccountTransfer(userVisit, partyCarrierAccount);
    }

    public List<PartyCarrierAccountTransfer> getPartyCarrierAccountTransfers(UserVisit userVisit, Collection<PartyCarrierAccount> partyCarrierAccounts) {
        List<PartyCarrierAccountTransfer> partyCarrierAccountTransfers = new ArrayList<>(partyCarrierAccounts.size());

        partyCarrierAccounts.forEach((partyCarrierAccount) ->
                partyCarrierAccountTransfers.add(partyCarrierAccountTransferCache.getPartyCarrierAccountTransfer(userVisit, partyCarrierAccount))
        );

        return partyCarrierAccountTransfers;
    }

    public List<PartyCarrierAccountTransfer> getPartyCarrierAccountTransfersByParty(UserVisit userVisit, Party party) {
        return getPartyCarrierAccountTransfers(userVisit, getPartyCarrierAccountsByParty(party));
    }

    public List<PartyCarrierAccountTransfer> getPartyCarrierAccountTransfersByCarrierParty(UserVisit userVisit, Party carrierParty) {
        return getPartyCarrierAccountTransfers(userVisit, getPartyCarrierAccountsByCarrierParty(carrierParty));
    }

    public void updatePartyCarrierAccountFromValue(PartyCarrierAccountDetailValue partyCarrierAccountDetailValue, BasePK updatedBy) {
        if(partyCarrierAccountDetailValue.hasBeenModified()) {
            var partyCarrierAccount = partyCarrierAccountFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     partyCarrierAccountDetailValue.getPartyCarrierAccountPK());
            var partyCarrierAccountDetail = partyCarrierAccount.getActiveDetailForUpdate();

            partyCarrierAccountDetail.setThruTime(session.getStartTime());
            partyCarrierAccountDetail.store();

            var partyCarrierAccountPK = partyCarrierAccountDetail.getPartyCarrierAccountPK(); // Not updated
            var partyPK = partyCarrierAccountDetail.getPartyPK(); // Not updated
            var carrierPartyPK = partyCarrierAccountDetail.getCarrierPartyPK(); // Not updated
            var account = partyCarrierAccountDetailValue.getAccount();
            var alwaysUseThirdPartyBilling = partyCarrierAccountDetailValue.getAlwaysUseThirdPartyBilling();

            partyCarrierAccountDetail = partyCarrierAccountDetailFactory.create(partyCarrierAccountPK, partyPK, carrierPartyPK, account,
                    alwaysUseThirdPartyBilling, session.getStartTime(), Session.MAX_TIME);

            partyCarrierAccount.setActiveDetail(partyCarrierAccountDetail);
            partyCarrierAccount.setLastDetail(partyCarrierAccountDetail);

            sendEvent(partyPK, EventTypes.MODIFY, partyCarrierAccountPK, EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePartyCarrierAccount(PartyCarrierAccount partyCarrierAccount, BasePK deletedBy) {
        var partyCarrierAccountDetail = partyCarrierAccount.getLastDetailForUpdate();

        partyCarrierAccountDetail.setThruTime(session.getStartTime());
        partyCarrierAccount.setActiveDetail(null);
        partyCarrierAccount.store();

        sendEvent(partyCarrierAccountDetail.getPartyPK(), EventTypes.MODIFY, partyCarrierAccount.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deletePartyCarrierAccounts(List<PartyCarrierAccount> partyCarrierAccounts, BasePK deletedBy) {
        partyCarrierAccounts.forEach((partyCarrierAccount) -> 
                deletePartyCarrierAccount(partyCarrierAccount, deletedBy)
        );
    }

    public void deletePartyCarrierAccountsByParty(Party party, BasePK deletedBy) {
        deletePartyCarrierAccounts(getPartyCarrierAccountsByPartyForUpdate(party), deletedBy);
    }

    public void deletePartyCarrierAccountsByCarrierParty(Party carrierParty, BasePK deletedBy) {
        deletePartyCarrierAccounts(getPartyCarrierAccountsByCarrierPartyForUpdate(carrierParty), deletedBy);
    }

}
