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
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class CarrierControl
        extends BaseModelControl {
    
    /** Creates a new instance of CarrierControl */
    protected CarrierControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Carrier Transfer Caches
    // --------------------------------------------------------------------------------

    @Inject
    CarrierTransferCache carrierTransferCache;

    @Inject
    CarrierTypeTransferCache carrierTypeTransferCache;

    @Inject
    CarrierTypeDescriptionTransferCache carrierTypeDescriptionTransferCache;

    @Inject
    CarrierServiceDescriptionTransferCache carrierServiceDescriptionTransferCache;

    @Inject
    CarrierServiceTransferCache carrierServiceTransferCache;

    @Inject
    CarrierOptionDescriptionTransferCache carrierOptionDescriptionTransferCache;

    @Inject
    CarrierOptionTransferCache carrierOptionTransferCache;

    @Inject
    PartyCarrierTransferCache partyCarrierTransferCache;

    @Inject
    PartyCarrierAccountTransferCache partyCarrierAccountTransferCache;

    @Inject
    CarrierServiceOptionTransferCache carrierServiceOptionTransferCache;

    // --------------------------------------------------------------------------------
    //   Carrier Types
    // --------------------------------------------------------------------------------

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

        var carrierType = CarrierTypeFactory.getInstance().create();
        var carrierTypeDetail = CarrierTypeDetailFactory.getInstance().create(carrierType,
                carrierTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        carrierType = CarrierTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                carrierType.getPrimaryKey());
        carrierType.setActiveDetail(carrierTypeDetail);
        carrierType.setLastDetail(carrierTypeDetail);
        carrierType.store();

        sendEvent(carrierType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return carrierType;
    }

    private static final Map<EntityPermission, String> getCarrierTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM carriertypes, carriertypedetails " +
                "WHERE crrtyp_activedetailid = crrtypdt_carriertypedetailid " +
                "AND crrtypdt_carriertypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM carriertypes, carriertypedetails " +
                "WHERE crrtyp_activedetailid = crrtypdt_carriertypedetailid " +
                "AND crrtypdt_carriertypename = ? " +
                "FOR UPDATE");
        getCarrierTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private CarrierType getCarrierTypeByName(String carrierTypeName, EntityPermission entityPermission) {
        return CarrierTypeFactory.getInstance().getEntityFromQuery(entityPermission, getCarrierTypeByNameQueries, carrierTypeName);
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

    private static final Map<EntityPermission, String> getDefaultCarrierTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM carriertypes, carriertypedetails " +
                "WHERE crrtyp_activedetailid = crrtypdt_carriertypedetailid " +
                "AND crrtypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM carriertypes, carriertypedetails " +
                "WHERE crrtyp_activedetailid = crrtypdt_carriertypedetailid " +
                "AND crrtypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultCarrierTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private CarrierType getDefaultCarrierType(EntityPermission entityPermission) {
        return CarrierTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultCarrierTypeQueries);
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

    private static final Map<EntityPermission, String> getCarrierTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM carriertypes, carriertypedetails " +
                "WHERE crrtyp_activedetailid = crrtypdt_carriertypedetailid " +
                "ORDER BY crrtypdt_sortorder, crrtypdt_carriertypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM carriertypes, carriertypedetails " +
                "WHERE crrtyp_activedetailid = crrtypdt_carriertypedetailid " +
                "FOR UPDATE");
        getCarrierTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<CarrierType> getCarrierTypes(EntityPermission entityPermission) {
        return CarrierTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getCarrierTypesQueries);
    }

    public List<CarrierType> getCarrierTypes() {
        return getCarrierTypes(EntityPermission.READ_ONLY);
    }

    public List<CarrierType> getCarrierTypesForUpdate() {
        return getCarrierTypes(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getCarrierTypesByParentCarrierTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM carriertypes, carriertypedetails " +
                "WHERE crrtyp_activedetailid = crrtypdt_carriertypedetailid AND crrtypdt_parentcarriertypeid = ? " +
                "ORDER BY crrtypdt_sortorder, crrtypdt_carriertypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM carriertypes, carriertypedetails " +
                "WHERE crrtyp_activedetailid = crrtypdt_carriertypedetailid AND crrtypdt_parentcarriertypeid = ? " +
                "FOR UPDATE");
        getCarrierTypesByParentCarrierTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<CarrierType> getCarrierTypesByParentCarrierType(CarrierType parentCarrierType, EntityPermission entityPermission) {
        return CarrierTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getCarrierTypesByParentCarrierTypeQueries,
                parentCarrierType);
    }

    public List<CarrierType> getCarrierTypesByParentCarrierType(CarrierType parentCarrierType) {
        return getCarrierTypesByParentCarrierType(parentCarrierType, EntityPermission.READ_ONLY);
    }

    public List<CarrierType> getCarrierTypesByParentCarrierTypeForUpdate(CarrierType parentCarrierType) {
        return getCarrierTypesByParentCarrierType(parentCarrierType, EntityPermission.READ_WRITE);
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

    public List<CarrierTypeTransfer> getCarrierTypeTransfersByParentCarrierType(UserVisit userVisit,
            CarrierType parentCarrierType) {
        return getCarrierTypeTransfers(userVisit, getCarrierTypesByParentCarrierType(parentCarrierType));
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
            var carrierType = CarrierTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     carrierTypeDetailValue.getCarrierTypePK());
            var carrierTypeDetail = carrierType.getActiveDetailForUpdate();

            carrierTypeDetail.setThruTime(session.START_TIME_LONG);
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

            carrierTypeDetail = CarrierTypeDetailFactory.getInstance().create(carrierTypePK,
                    carrierTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

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
        carrierTypeDetail.setThruTime(session.START_TIME_LONG);
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

    public CarrierTypeDescription createCarrierTypeDescription(CarrierType carrierType,
            Language language, String description, BasePK createdBy) {
        var carrierTypeDescription = CarrierTypeDescriptionFactory.getInstance().create(carrierType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(carrierType.getPrimaryKey(), EventTypes.MODIFY, carrierTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return carrierTypeDescription;
    }

    private static final Map<EntityPermission, String> getCarrierTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM carriertypedescriptions " +
                "WHERE crrtypd_crrtyp_carriertypeid = ? AND crrtypd_lang_languageid = ? AND crrtypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM carriertypedescriptions " +
                "WHERE crrtypd_crrtyp_carriertypeid = ? AND crrtypd_lang_languageid = ? AND crrtypd_thrutime = ? " +
                "FOR UPDATE");
        getCarrierTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private CarrierTypeDescription getCarrierTypeDescription(CarrierType carrierType,
            Language language, EntityPermission entityPermission) {
        return CarrierTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getCarrierTypeDescriptionQueries,
                carrierType, language, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getCarrierTypeDescriptionsByCarrierTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM carriertypedescriptions, languages " +
                "WHERE crrtypd_crrtyp_carriertypeid = ? AND crrtypd_thrutime = ? AND crrtypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM carriertypedescriptions " +
                "WHERE crrtypd_crrtyp_carriertypeid = ? AND crrtypd_thrutime = ? " +
                "FOR UPDATE");
        getCarrierTypeDescriptionsByCarrierTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<CarrierTypeDescription> getCarrierTypeDescriptionsByCarrierType(CarrierType carrierType,
            EntityPermission entityPermission) {
        return CarrierTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getCarrierTypeDescriptionsByCarrierTypeQueries,
                carrierType, Session.MAX_TIME);
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
            var carrierTypeDescription = CarrierTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    carrierTypeDescriptionValue.getPrimaryKey());

            carrierTypeDescription.setThruTime(session.START_TIME_LONG);
            carrierTypeDescription.store();

            var carrierType = carrierTypeDescription.getCarrierType();
            var language = carrierTypeDescription.getLanguage();
            var description = carrierTypeDescriptionValue.getDescription();

            carrierTypeDescription = CarrierTypeDescriptionFactory.getInstance().create(carrierType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(carrierType.getPrimaryKey(), EventTypes.MODIFY, carrierTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteCarrierTypeDescription(CarrierTypeDescription carrierTypeDescription, BasePK deletedBy) {
        carrierTypeDescription.setThruTime(session.START_TIME_LONG);

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

        var carrier = CarrierFactory.getInstance().create(party, carrierName, carrierType, geoCodeSelector, itemSelector, accountValidationPattern,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, carrier.getPrimaryKey(), null, createdBy);
        
        return carrier;
    }
    
    private Carrier getCarrier(Party party, EntityPermission entityPermission) {
        Carrier carrier;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM carriers " +
                        "WHERE crr_par_partyid = ? AND crr_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM carriers " +
                        "WHERE crr_par_partyid = ? AND crr_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = CarrierFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            carrier = CarrierFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return carrier;
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
        Carrier carrier;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM carriers " +
                        "WHERE crr_carriername = ? AND crr_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM carriers " +
                        "WHERE crr_carriername = ? AND crr_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = CarrierFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, carrierName);
            ps.setLong(2, Session.MAX_TIME);
            
            carrier = CarrierFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return carrier;
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
        Carrier carrier;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM carriers " +
                        "WHERE crr_isdefault = 1 AND crr_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM carriers " +
                        "WHERE crr_isdefault = 1 AND crr_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = CarrierFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, Session.MAX_TIME);
            
            carrier = CarrierFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return carrier;
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
        List<Carrier> carriers;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM carriers " +
                        "WHERE crr_thrutime = ? " +
                        "ORDER BY crr_sortorder, crr_carriername";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM carriers " +
                        "WHERE crr_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = CarrierFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, Session.MAX_TIME);
            
            carriers = CarrierFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return carriers;
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
            var carrier = CarrierFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    carrierValue.getPrimaryKey());
            
            carrier.setThruTime(session.START_TIME_LONG);
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
            
            carrier = CarrierFactory.getInstance().create(partyPK, carrierName, carrierTypePK, geoCodeSelectorPK, itemSelectorPK, accountValidationPattern,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(partyPK, EventTypes.MODIFY, carrier.getPrimaryKey(), null, updatedBy);
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
        
        carrier.setThruTime(session.START_TIME_LONG);
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
        
        sendEvent(carrier.getPartyPK(), EventTypes.MODIFY, carrier.getPrimaryKey(), null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Carrier Services
    // --------------------------------------------------------------------------------
    
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

        var carrierService = CarrierServiceFactory.getInstance().create();
        var carrierServiceDetail = CarrierServiceDetailFactory.getInstance().create(session,
                carrierService, carrierParty, carrierServiceName, geoCodeSelector, itemSelector, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        carrierService = CarrierServiceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                carrierService.getPrimaryKey());
        carrierService.setActiveDetail(carrierServiceDetail);
        carrierService.setLastDetail(carrierServiceDetail);
        carrierService.store();
        
        sendEvent(carrierService.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return carrierService;
    }
    
    private List<CarrierService> getCarrierServices(Party carrierParty, EntityPermission entityPermission) {
        List<CarrierService> carrierPartyPriorities;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM carrierservices, carrierservicedetails " +
                        "WHERE crrsrv_activedetailid = crrsrvdt_carrierservicedetailid AND crrsrvdt_carrierpartyid = ? " +
                        "ORDER BY crrsrvdt_sortorder, crrsrvdt_carrierservicename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM carrierservices, carrierservicedetails " +
                        "WHERE crrsrv_activedetailid = crrsrvdt_carrierservicedetailid AND crrsrvdt_carrierpartyid = ? " +
                        "FOR UPDATE";
            }

            var ps = CarrierServiceFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, carrierParty.getPrimaryKey().getEntityId());
            
            carrierPartyPriorities = CarrierServiceFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return carrierPartyPriorities;
    }
    
    public List<CarrierService> getCarrierServices(Party carrierParty) {
        return getCarrierServices(carrierParty, EntityPermission.READ_ONLY);
    }
    
    public List<CarrierService> getCarrierServicesForUpdate(Party carrierParty) {
        return getCarrierServices(carrierParty, EntityPermission.READ_WRITE);
    }
    
    private CarrierService getDefaultCarrierService(Party carrierParty, EntityPermission entityPermission) {
        CarrierService carrierService;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM carrierservices, carrierservicedetails " +
                        "WHERE crrsrv_activedetailid = crrsrvdt_carrierservicedetailid " +
                        "AND crrsrvdt_carrierpartyid = ? AND crrsrvdt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM carrierservices, carrierservicedetails " +
                        "WHERE crrsrv_activedetailid = crrsrvdt_carrierservicedetailid " +
                        "AND crrsrvdt_carrierpartyid = ? AND crrsrvdt_isdefault = 1 " +
                        "FOR UPDATE";
            }

            var ps = CarrierServiceFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, carrierParty.getPrimaryKey().getEntityId());
            
            carrierService = CarrierServiceFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return carrierService;
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
        CarrierService carrierService;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM carrierservices, carrierservicedetails " +
                        "WHERE crrsrv_activedetailid = crrsrvdt_carrierservicedetailid " +
                        "AND crrsrvdt_carrierpartyid = ? AND crrsrvdt_carrierservicename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM carrierservices, carrierservicedetails " +
                        "WHERE crrsrv_activedetailid = crrsrvdt_carrierservicedetailid " +
                        "AND crrsrvdt_carrierpartyid = ? AND crrsrvdt_carrierservicename = ? " +
                        "FOR UPDATE";
            }

            var ps = CarrierServiceFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, carrierParty.getPrimaryKey().getEntityId());
            ps.setString(2, carrierServiceName);
            
            carrierService = CarrierServiceFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return carrierService;
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
    
    public List<CarrierServiceTransfer> getCarrierServiceTransfers(UserVisit userVisit, Party carrierParty) {
        var carrierPartyPriorities = getCarrierServices(carrierParty);
        List<CarrierServiceTransfer> carrierServiceTransfers = new ArrayList<>(carrierPartyPriorities.size());
        
        carrierPartyPriorities.forEach((carrierService) ->
                carrierServiceTransfers.add(carrierServiceTransferCache.getCarrierServiceTransfer(userVisit, carrierService))
        );
        
        return carrierServiceTransfers;
    }
    
    private void updateCarrierServiceFromValue(CarrierServiceDetailValue carrierServiceDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(carrierServiceDetailValue.hasBeenModified()) {
            var carrierService = CarrierServiceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     carrierServiceDetailValue.getCarrierServicePK());
            var carrierServiceDetail = carrierService.getActiveDetailForUpdate();
            
            carrierServiceDetail.setThruTime(session.START_TIME_LONG);
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
            
            carrierServiceDetail = CarrierServiceDetailFactory.getInstance().create(carrierServicePK, carrierPartyPK,
                    carrierServiceName, geoCodeSelectorPK, itemSelectorPK, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            carrierService.setActiveDetail(carrierServiceDetail);
            carrierService.setLastDetail(carrierServiceDetail);
            
            sendEvent(carrierServicePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateCarrierServiceFromValue(CarrierServiceDetailValue carrierServiceDetailValue, BasePK updatedBy) {
        updateCarrierServiceFromValue(carrierServiceDetailValue, true, updatedBy);
    }
    
    private void deleteCarrierService(CarrierService carrierService, boolean checkDefault, BasePK deletedBy) {
        var shippingControl = Session.getModelController(ShippingControl.class);
        
        deleteCarrierServiceDescriptionsByCarrierService(carrierService, deletedBy);
        deleteCarrierServiceOptionsByCarrierService(carrierService, deletedBy);
        shippingControl.deleteShippingMethodCarrierServicesByCarrierService(carrierService, deletedBy);

        var carrierServiceDetail = carrierService.getLastDetailForUpdate();
        carrierServiceDetail.setThruTime(session.START_TIME_LONG);
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
    
    public CarrierServiceDescription createCarrierServiceDescription(CarrierService carrierService, Language language, String description,
            BasePK createdBy) {
        var carrierServiceDescription = CarrierServiceDescriptionFactory.getInstance().create(carrierService,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(carrierService.getPrimaryKey(), EventTypes.MODIFY, carrierServiceDescription.getPrimaryKey(),
                null, createdBy);
        
        return carrierServiceDescription;
    }
    
    private CarrierServiceDescription getCarrierServiceDescription(CarrierService carrierService, Language language, EntityPermission entityPermission) {
        CarrierServiceDescription carrierServiceDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM carrierservicedescriptions " +
                        "WHERE crrsrvd_crrsrv_carrierserviceid = ? AND crrsrvd_lang_languageid = ? AND crrsrvd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM carrierservicedescriptions " +
                        "WHERE crrsrvd_crrsrv_carrierserviceid = ? AND crrsrvd_lang_languageid = ? AND crrsrvd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = CarrierServiceDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, carrierService.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            carrierServiceDescription = CarrierServiceDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return carrierServiceDescription;
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
        List<CarrierServiceDescription> carrierServiceDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM carrierservicedescriptions, languages " +
                        "WHERE crrsrvd_crrsrv_carrierserviceid = ? AND crrsrvd_thrutime = ? AND crrsrvd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM carrierservicedescriptions " +
                        "WHERE crrsrvd_crrsrv_carrierserviceid = ? AND crrsrvd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = CarrierServiceDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, carrierService.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            carrierServiceDescriptions = CarrierServiceDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return carrierServiceDescriptions;
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
            var carrierServiceDescription = CarrierServiceDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     carrierServiceDescriptionValue.getPrimaryKey());
            
            carrierServiceDescription.setThruTime(session.START_TIME_LONG);
            carrierServiceDescription.store();

            var carrierService = carrierServiceDescription.getCarrierService();
            var language = carrierServiceDescription.getLanguage();
            var description = carrierServiceDescriptionValue.getDescription();
            
            carrierServiceDescription = CarrierServiceDescriptionFactory.getInstance().create(carrierService, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(carrierService.getPrimaryKey(), EventTypes.MODIFY, carrierServiceDescription.getPrimaryKey(),
                    null, updatedBy);
        }
    }
    
    public void deleteCarrierServiceDescription(CarrierServiceDescription carrierServiceDescription, BasePK deletedBy) {
        carrierServiceDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(carrierServiceDescription.getCarrierServicePK(), EventTypes.MODIFY,
                carrierServiceDescription.getPrimaryKey(), null, deletedBy);
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

        var carrierOption = CarrierOptionFactory.getInstance().create();
        var carrierOptionDetail = CarrierOptionDetailFactory.getInstance().create(carrierOption, carrierParty, carrierOptionName, isRecommended,
                isRequired, recommendedGeoCodeSelector, requiredGeoCodeSelector, recommendedItemSelector, requiredItemSelector, recommendedOrderSelector,
                requiredOrderSelector, recommendedShipmentSelector, requiredShipmentSelector, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        carrierOption = CarrierOptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                carrierOption.getPrimaryKey());
        carrierOption.setActiveDetail(carrierOptionDetail);
        carrierOption.setLastDetail(carrierOptionDetail);
        carrierOption.store();
        
        sendEvent(carrierOption.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return carrierOption;
    }
    
    private List<CarrierOption> getCarrierOptions(Party carrierParty, EntityPermission entityPermission) {
        List<CarrierOption> carrierPartyPriorities;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM carrieroptions, carrieroptiondetails " +
                        "WHERE crropt_activedetailid = crroptdt_carrieroptiondetailid AND crroptdt_carrierpartyid = ? " +
                        "ORDER BY crroptdt_sortorder, crroptdt_carrieroptionname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM carrieroptions, carrieroptiondetails " +
                        "WHERE crropt_activedetailid = crroptdt_carrieroptiondetailid AND crroptdt_carrierpartyid = ? " +
                        "FOR UPDATE";
            }

            var ps = CarrierOptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, carrierParty.getPrimaryKey().getEntityId());
            
            carrierPartyPriorities = CarrierOptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return carrierPartyPriorities;
    }
    
    public List<CarrierOption> getCarrierOptions(Party carrierParty) {
        return getCarrierOptions(carrierParty, EntityPermission.READ_ONLY);
    }
    
    public List<CarrierOption> getCarrierOptionsForUpdate(Party carrierParty) {
        return getCarrierOptions(carrierParty, EntityPermission.READ_WRITE);
    }
    
    private CarrierOption getDefaultCarrierOption(Party carrierParty, EntityPermission entityPermission) {
        CarrierOption carrierOption;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM carrieroptions, carrieroptiondetails " +
                        "WHERE crropt_activedetailid = crroptdt_carrieroptiondetailid " +
                        "AND crroptdt_carrierpartyid = ? AND crroptdt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM carrieroptions, carrieroptiondetails " +
                        "WHERE crropt_activedetailid = crroptdt_carrieroptiondetailid " +
                        "AND crroptdt_carrierpartyid = ? AND crroptdt_isdefault = 1 " +
                        "FOR UPDATE";
            }

            var ps = CarrierOptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, carrierParty.getPrimaryKey().getEntityId());
            
            carrierOption = CarrierOptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return carrierOption;
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
        CarrierOption carrierOption;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM carrieroptions, carrieroptiondetails " +
                        "WHERE crropt_activedetailid = crroptdt_carrieroptiondetailid " +
                        "AND crroptdt_carrierpartyid = ? AND crroptdt_carrieroptionname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM carrieroptions, carrieroptiondetails " +
                        "WHERE crropt_activedetailid = crroptdt_carrieroptiondetailid " +
                        "AND crroptdt_carrierpartyid = ? AND crroptdt_carrieroptionname = ? " +
                        "FOR UPDATE";
            }

            var ps = CarrierOptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, carrierParty.getPrimaryKey().getEntityId());
            ps.setString(2, carrierOptionName);
            
            carrierOption = CarrierOptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return carrierOption;
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
    
    public List<CarrierOptionTransfer> getCarrierOptionTransfers(UserVisit userVisit, Party carrierParty) {
        var carrierPartyPriorities = getCarrierOptions(carrierParty);
        List<CarrierOptionTransfer> carrierOptionTransfers = new ArrayList<>(carrierPartyPriorities.size());
        
        carrierPartyPriorities.forEach((carrierOption) ->
                carrierOptionTransfers.add(carrierOptionTransferCache.getCarrierOptionTransfer(userVisit, carrierOption))
        );
        
        return carrierOptionTransfers;
    }
    
    private void updateCarrierOptionFromValue(CarrierOptionDetailValue carrierOptionDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(carrierOptionDetailValue.hasBeenModified()) {
            var carrierOption = CarrierOptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     carrierOptionDetailValue.getCarrierOptionPK());
            var carrierOptionDetail = carrierOption.getActiveDetailForUpdate();
            
            carrierOptionDetail.setThruTime(session.START_TIME_LONG);
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
            var requiredShipmentSelectorPK = carrierOptionDetailValue.getRequiredItemSelectorPK();
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
            
            carrierOptionDetail = CarrierOptionDetailFactory.getInstance().create(carrierOptionPK, carrierPartyPK, carrierOptionName, isRecommended, isRequired,
                    recommendedGeoCodeSelectorPK, requiredGeoCodeSelectorPK, recommendedItemSelectorPK, requiredItemSelectorPK, recommendedOrderSelectorPK,
                    requiredOrderSelectorPK, recommendedShipmentSelectorPK, requiredShipmentSelectorPK, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
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
        carrierOptionDetail.setThruTime(session.START_TIME_LONG);
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
    
    public CarrierOptionDescription createCarrierOptionDescription(CarrierOption carrierOption, Language language, String description,
            BasePK createdBy) {
        var carrierOptionDescription = CarrierOptionDescriptionFactory.getInstance().create(carrierOption,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(carrierOption.getPrimaryKey(), EventTypes.MODIFY, carrierOptionDescription.getPrimaryKey(),
                null, createdBy);
        
        return carrierOptionDescription;
    }
    
    private CarrierOptionDescription getCarrierOptionDescription(CarrierOption carrierOption, Language language, EntityPermission entityPermission) {
        CarrierOptionDescription carrierOptionDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM carrieroptiondescriptions " +
                        "WHERE crroptd_crropt_carrieroptionid = ? AND crroptd_lang_languageid = ? AND crroptd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM carrieroptiondescriptions " +
                        "WHERE crroptd_crropt_carrieroptionid = ? AND crroptd_lang_languageid = ? AND crroptd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = CarrierOptionDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, carrierOption.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            carrierOptionDescription = CarrierOptionDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return carrierOptionDescription;
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
        List<CarrierOptionDescription> carrierOptionDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM carrieroptiondescriptions, languages " +
                        "WHERE crroptd_crropt_carrieroptionid = ? AND crroptd_thrutime = ? AND crroptd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM carrieroptiondescriptions " +
                        "WHERE crroptd_crropt_carrieroptionid = ? AND crroptd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = CarrierOptionDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, carrierOption.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            carrierOptionDescriptions = CarrierOptionDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return carrierOptionDescriptions;
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
            var carrierOptionDescription = CarrierOptionDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     carrierOptionDescriptionValue.getPrimaryKey());
            
            carrierOptionDescription.setThruTime(session.START_TIME_LONG);
            carrierOptionDescription.store();

            var carrierOption = carrierOptionDescription.getCarrierOption();
            var language = carrierOptionDescription.getLanguage();
            var description = carrierOptionDescriptionValue.getDescription();
            
            carrierOptionDescription = CarrierOptionDescriptionFactory.getInstance().create(carrierOption, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(carrierOption.getPrimaryKey(), EventTypes.MODIFY, carrierOptionDescription.getPrimaryKey(),
                    null, updatedBy);
        }
    }
    
    public void deleteCarrierOptionDescription(CarrierOptionDescription carrierOptionDescription, BasePK deletedBy) {
        carrierOptionDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(carrierOptionDescription.getCarrierOptionPK(), EventTypes.MODIFY,
                carrierOptionDescription.getPrimaryKey(), null, deletedBy);
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
    
    public CarrierServiceOption createCarrierServiceOption(CarrierService carrierService, CarrierOption carrierOption, Boolean isRecommended,
            Boolean isRequired, Selector recommendedGeoCodeSelector, Selector requiredGeoCodeSelector, Selector recommendedItemSelector,
            Selector requiredItemSelector, Selector recommendedOrderSelector, Selector requiredOrderSelector, Selector recommendedShipmentSelector,
            Selector requiredShipmentSelector, BasePK createdBy) {
        var carrierServiceOption = CarrierServiceOptionFactory.getInstance().create(carrierService, carrierOption, isRecommended, isRequired,
                recommendedGeoCodeSelector, requiredGeoCodeSelector, recommendedItemSelector, requiredItemSelector, recommendedOrderSelector,
                requiredOrderSelector, recommendedShipmentSelector, requiredShipmentSelector, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEvent(carrierService.getPrimaryKey(), EventTypes.MODIFY, carrierServiceOption.getPrimaryKey(),
                null, createdBy);
        
        return carrierServiceOption;
    }
    
    private CarrierServiceOption getCarrierServiceOption(CarrierService carrierService, CarrierOption carrierOption,
            EntityPermission entityPermission) {
        CarrierServiceOption carrierServiceOption;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM carrierserviceoptions " +
                        "WHERE crrsrvopt_crrsrv_carrierserviceid = ? AND crrsrvopt_crropt_carrieroptionid = ? AND crrsrvopt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM carrierserviceoptions " +
                        "WHERE crrsrvopt_crrsrv_carrierserviceid = ? AND crrsrvopt_crropt_carrieroptionid = ? AND crrsrvopt_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = CarrierServiceOptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, carrierService.getPrimaryKey().getEntityId());
            ps.setLong(2, carrierOption.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            carrierServiceOption = CarrierServiceOptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return carrierServiceOption;
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
        List<CarrierServiceOption> carrierServiceOptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM carrierserviceoptions, carrieroptions, carrieroptiondetails " +
                        "WHERE crrsrvopt_crrsrv_carrierserviceid = ? AND crrsrvopt_thrutime = ? " +
                        "AND crrsrvopt_crropt_carrieroptionid = crropt_carrieroptionid AND crropt_activedetailid = crroptdt_carrieroptiondetailid " +
                        "ORDER BY crroptdt_sortorder, crroptdt_carrieroptionname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM carrierserviceoptions " +
                        "WHERE crrsrvopt_crrsrv_carrierserviceid = ? AND crrsrvopt_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = CarrierServiceOptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, carrierService.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            carrierServiceOptions = CarrierServiceOptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return carrierServiceOptions;
    }
    
    public List<CarrierServiceOption> getCarrierServiceOptionsByCarrierService(CarrierService carrierService) {
        return getCarrierServiceOptionsByCarrierService(carrierService, EntityPermission.READ_ONLY);
    }
    
    public List<CarrierServiceOption> getCarrierServiceOptionsByCarrierServiceForUpdate(CarrierService carrierService) {
        return getCarrierServiceOptionsByCarrierService(carrierService, EntityPermission.READ_WRITE);
    }
    
    private List<CarrierServiceOption> getCarrierServiceOptionsByCarrierOption(CarrierOption carrierOption, EntityPermission entityPermission) {
        List<CarrierServiceOption> carrierServiceOptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM carrierserviceoptions, carrierservices, carrierservicedetails " +
                        "WHERE crrsrvopt_crropt_carrieroptionid = ? AND crrsrvopt_thrutime = ? " +
                        "AND crrsrvopt_crrsrv_carrierserviceid = crrsrv_carrierserviceid AND crrsrv_activedetailid = crrsrvdt_carrierservicedetailid " +
                        "ORDER BY crrsrvdt_sortorder, crrsrvdt_carrierservicename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM carrierserviceoptions " +
                        "WHERE crrsrvopt_crropt_carrieroptionid = ? AND crrsrvopt_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = CarrierServiceOptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, carrierOption.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            carrierServiceOptions = CarrierServiceOptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return carrierServiceOptions;
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
            var carrierServiceOption = CarrierServiceOptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     carrierServiceOptionValue.getPrimaryKey());
            
            carrierServiceOption.setThruTime(session.START_TIME_LONG);
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
            var requiredShipmentSelectorPK = carrierServiceOptionValue.getRequiredItemSelectorPK();
            
            carrierServiceOption = CarrierServiceOptionFactory.getInstance().create(carrierServicePK, carrierOptionPK, isRecommended, isRequired,
                    recommendedGeoCodeSelectorPK, requiredGeoCodeSelectorPK, recommendedItemSelectorPK, requiredItemSelectorPK, recommendedOrderSelectorPK,
                    requiredOrderSelectorPK, recommendedShipmentSelectorPK, requiredShipmentSelectorPK, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(carrierServicePK, EventTypes.MODIFY, carrierServiceOption.getPrimaryKey(), null, updatedBy);
        }
    }
    
    public void deleteCarrierServiceOption(CarrierServiceOption carrierServiceOption, BasePK deletedBy) {
        carrierServiceOption.setThruTime(session.START_TIME_LONG);
        
        sendEvent(carrierServiceOption.getCarrierService().getPrimaryKey(), EventTypes.MODIFY,
                carrierServiceOption.getPrimaryKey(), null, deletedBy);
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
    
    public PartyCarrier createPartyCarrier(Party party, Party carrierParty, BasePK createdBy) {
        var partyCarrier = PartyCarrierFactory.getInstance().create(party, carrierParty, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partyCarrier.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partyCarrier;
    }
    
    public long countPartyCarriersByParty(Party party) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM partycarriers " +
                "WHERE pcrr_par_partyid = ? AND pcrr_thrutime = ?",
                party, Session.MAX_TIME_LONG);
    }

    private PartyCarrier getPartyCarrier(Party party, Party carrierParty, EntityPermission entityPermission) {
        PartyCarrier partyCarrier;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partycarriers " +
                        "WHERE pcrr_par_partyid = ? AND pcrr_carrierpartyid = ? AND pcrr_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partycarriers " +
                        "WHERE pcrr_par_partyid = ? AND pcrr_carrierpartyid = ? AND pcrr_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = PartyCarrierFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, carrierParty.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            partyCarrier = PartyCarrierFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyCarrier;
    }
    
    public PartyCarrier getPartyCarrier(Party party, Party carrierParty) {
        return getPartyCarrier(party, carrierParty, EntityPermission.READ_ONLY);
    }
    
    public PartyCarrier getPartyCarrierForUpdate(Party party, Party carrierParty) {
        return getPartyCarrier(party, carrierParty, EntityPermission.READ_WRITE);
    }
    
    private List<PartyCarrier> getPartyCarriersByParty(Party party, EntityPermission entityPermission) {
        List<PartyCarrier> partyCarriers;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partycarriers, parties, partydetails " +
                        "WHERE pcrr_par_partyid = ? AND pcrr_thrutime = ? AND pcrr_par_partyid = par_partyid " +
                        "AND par_activedetailid = pardt_partydetailid " +
                        "ORDER BY pardt_partyname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partycarriers " +
                        "WHERE pcrr_par_partyid = ? AND pcrr_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = PartyCarrierFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partyCarriers = PartyCarrierFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyCarriers;
    }
    
    public List<PartyCarrier> getPartyCarriersByParty(Party party) {
        return getPartyCarriersByParty(party, EntityPermission.READ_ONLY);
    }
    
    public List<PartyCarrier> getPartyCarriersByPartyForUpdate(Party party) {
        return getPartyCarriersByParty(party, EntityPermission.READ_WRITE);
    }
    
    private List<PartyCarrier> getPartyCarriersByCarrierParty(Party carrierParty, EntityPermission entityPermission) {
        List<PartyCarrier> partyCarriers;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partycarriers, carriers " +
                        "WHERE pcrr_carrierpartyid = ? AND pcrr_thrutime = ? AND pcrr_carrierpartyid = crr_par_partyid " +
                        "AND crr_thrutime = ? " +
                        "ORDER BY pardt_partyname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partycarriers " +
                        "WHERE pcrr_par_partyid = ? AND pcrr_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = PartyCarrierFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, carrierParty.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                ps.setLong(3, Session.MAX_TIME);
            }
            
            partyCarriers = PartyCarrierFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyCarriers;
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
    
    public List<PartyCarrierTransfer> getPartyCarrierTransfersByParty(UserVisit userVisit, List<PartyCarrier> partyCarriers) {
        List<PartyCarrierTransfer> partyCarrierTransfers = new ArrayList<>(partyCarriers.size());

        partyCarriers.forEach((partyCarrier) ->
                partyCarrierTransfers.add(partyCarrierTransferCache.getPartyCarrierTransfer(userVisit, partyCarrier))
        );

        return partyCarrierTransfers;
    }

    public List<PartyCarrierTransfer> getPartyCarrierTransfersByParty(UserVisit userVisit, Party party) {
        return getPartyCarrierTransfersByParty(userVisit, getPartyCarriersByParty(party));
    }

    public List<PartyCarrierTransfer> getPartyCarrierTransfersByCarrierParty(UserVisit userVisit, Party carrierParty) {
        return getPartyCarrierTransfersByParty(userVisit, getPartyCarriersByCarrierParty(carrierParty));
    }

    public void deletePartyCarrier(PartyCarrier partyCarrier, BasePK deletedBy) {
        partyCarrier.setThruTime(session.START_TIME_LONG);
        
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

    public PartyCarrierAccount createPartyCarrierAccount(Party party, Party carrierParty, String account, Boolean alwaysUseThirdPartyBilling, BasePK createdBy) {
        var partyCarrierAccount = PartyCarrierAccountFactory.getInstance().create();
        var partyCarrierAccountDetail = PartyCarrierAccountDetailFactory.getInstance().create(partyCarrierAccount, party, carrierParty,
                account, alwaysUseThirdPartyBilling, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        partyCarrierAccount = PartyCarrierAccountFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, partyCarrierAccount.getPrimaryKey());
        partyCarrierAccount.setActiveDetail(partyCarrierAccountDetail);
        partyCarrierAccount.setLastDetail(partyCarrierAccountDetail);
        partyCarrierAccount.store();

        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partyCarrierAccount.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return partyCarrierAccount;
    }

    private static final Map<EntityPermission, String> getPartyCarrierAccountQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partycarrieraccounts, partycarrieraccountdetails " +
                "WHERE pcrract_activedetailid = pcrractdt_partycarrieraccountdetailid " +
                "AND pcrractdt_par_partyid = ? AND pcrractdt_carrierpartyid = ? " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partycarrieraccounts, partycarrieraccountdetails " +
                "WHERE pcrract_activedetailid = pcrractdt_partycarrieraccountdetailid " +
                "AND pcrractdt_par_partyid = ? AND pcrractdt_carrierpartyid = ? " +
                "FOR UPDATE");
        getPartyCarrierAccountQueries = Collections.unmodifiableMap(queryMap);
    }

    private PartyCarrierAccount getPartyCarrierAccount(Party party, Party carrierParty, EntityPermission entityPermission) {
        return PartyCarrierAccountFactory.getInstance().getEntityFromQuery(entityPermission, getPartyCarrierAccountQueries,
                party, carrierParty);
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

    private static final Map<EntityPermission, String> getPartyCarrierAccountsByPartyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partycarrieraccounts, partycarrieraccountdetails, parties, partydetails " +
                "WHERE pcrract_activedetailid = pcrractdt_partycarrieraccountdetailid " +
                "AND pcrractdt_par_partyid = ? " +
                "AND pcrractdt_carrierpartyid = par_partyid AND par_lastdetailid = pardt_partydetailid " +
                "ORDER BY pardt_partyname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partycarrieraccounts, partycarrieraccountdetails " +
                "WHERE pcrract_activedetailid = pcrractdt_partycarrieraccountdetailid " +
                "AND pcrractdt_par_partyid = ? " +
                "FOR UPDATE");
        getPartyCarrierAccountsByPartyQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyCarrierAccount> getPartyCarrierAccountsByParty(Party party, EntityPermission entityPermission) {
        return PartyCarrierAccountFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyCarrierAccountsByPartyQueries,
                party);
    }

    public List<PartyCarrierAccount> getPartyCarrierAccountsByParty(Party party) {
        return getPartyCarrierAccountsByParty(party, EntityPermission.READ_ONLY);
    }

    public List<PartyCarrierAccount> getPartyCarrierAccountsByPartyForUpdate(Party party) {
        return getPartyCarrierAccountsByParty(party, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPartyCarrierAccountsByCarrierPartyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partycarrieraccounts, partycarrieraccountdetails, parties, partydetails " +
                "WHERE pcrract_activedetailid = pcrractdt_partycarrieraccountdetailid " +
                "AND pcrractdt_carrierpartyid = ? " +
                "AND pcrractdt_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid " +
                "ORDER BY pardt_partyname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partycarrieraccounts, partycarrieraccountdetails " +
                "WHERE pcrract_activedetailid = pcrractdt_partycarrieraccountdetailid " +
                "AND pcrractdt_carrierpartyid = ? " +
                "FOR UPDATE");
        getPartyCarrierAccountsByCarrierPartyQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyCarrierAccount> getPartyCarrierAccountsByCarrierParty(Party carrierParty, EntityPermission entityPermission) {
        return PartyCarrierAccountFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyCarrierAccountsByCarrierPartyQueries,
                carrierParty);
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
            var partyCarrierAccount = PartyCarrierAccountFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     partyCarrierAccountDetailValue.getPartyCarrierAccountPK());
            var partyCarrierAccountDetail = partyCarrierAccount.getActiveDetailForUpdate();

            partyCarrierAccountDetail.setThruTime(session.START_TIME_LONG);
            partyCarrierAccountDetail.store();

            var partyCarrierAccountPK = partyCarrierAccountDetail.getPartyCarrierAccountPK(); // Not updated
            var partyPK = partyCarrierAccountDetail.getPartyPK(); // Not updated
            var carrierPartyPK = partyCarrierAccountDetail.getCarrierPartyPK(); // Not updated
            var account = partyCarrierAccountDetailValue.getAccount();
            var alwaysUseThirdPartyBilling = partyCarrierAccountDetailValue.getAlwaysUseThirdPartyBilling();

            partyCarrierAccountDetail = PartyCarrierAccountDetailFactory.getInstance().create(partyCarrierAccountPK, partyPK, carrierPartyPK, account,
                    alwaysUseThirdPartyBilling, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            partyCarrierAccount.setActiveDetail(partyCarrierAccountDetail);
            partyCarrierAccount.setLastDetail(partyCarrierAccountDetail);

            sendEvent(partyPK, EventTypes.MODIFY, partyCarrierAccountPK, EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePartyCarrierAccount(PartyCarrierAccount partyCarrierAccount, BasePK deletedBy) {
        var partyCarrierAccountDetail = partyCarrierAccount.getLastDetailForUpdate();

        partyCarrierAccountDetail.setThruTime(session.START_TIME_LONG);
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
