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

package com.echothree.model.control.core.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.common.choice.ProtocolChoicesBean;
import com.echothree.model.control.core.common.choice.ServerChoicesBean;
import com.echothree.model.control.core.common.choice.ServiceChoicesBean;
import com.echothree.model.control.core.common.transfer.ProtocolDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.ProtocolTransfer;
import com.echothree.model.control.core.common.transfer.ServerDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.ServerServiceTransfer;
import com.echothree.model.control.core.common.transfer.ServerTransfer;
import com.echothree.model.control.core.common.transfer.ServiceDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.ServiceTransfer;
import com.echothree.model.control.scale.server.control.ScaleControl;
import com.echothree.model.data.core.server.entity.Protocol;
import com.echothree.model.data.core.server.entity.ProtocolDescription;
import com.echothree.model.data.core.server.entity.Server;
import com.echothree.model.data.core.server.entity.ServerDescription;
import com.echothree.model.data.core.server.entity.ServerService;
import com.echothree.model.data.core.server.entity.Service;
import com.echothree.model.data.core.server.entity.ServiceDescription;
import com.echothree.model.data.core.server.factory.ProtocolDescriptionFactory;
import com.echothree.model.data.core.server.factory.ProtocolDetailFactory;
import com.echothree.model.data.core.server.factory.ProtocolFactory;
import com.echothree.model.data.core.server.factory.ServerDescriptionFactory;
import com.echothree.model.data.core.server.factory.ServerDetailFactory;
import com.echothree.model.data.core.server.factory.ServerFactory;
import com.echothree.model.data.core.server.factory.ServerServiceFactory;
import com.echothree.model.data.core.server.factory.ServiceDescriptionFactory;
import com.echothree.model.data.core.server.factory.ServiceDetailFactory;
import com.echothree.model.data.core.server.factory.ServiceFactory;
import com.echothree.model.data.core.server.value.ProtocolDescriptionValue;
import com.echothree.model.data.core.server.value.ProtocolDetailValue;
import com.echothree.model.data.core.server.value.ServerDescriptionValue;
import com.echothree.model.data.core.server.value.ServerDetailValue;
import com.echothree.model.data.core.server.value.ServerServiceValue;
import com.echothree.model.data.core.server.value.ServiceDescriptionValue;
import com.echothree.model.data.core.server.value.ServiceDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.echothree.util.server.cdi.CommandScope;

@CommandScope
public class ServerControl
        extends BaseCoreControl {

    /** Creates a new instance of ServerControl */
    protected ServerControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Protocols
    // --------------------------------------------------------------------------------

    public Protocol createProtocol(String protocolName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultProtocol = getDefaultProtocol();
        var defaultFound = defaultProtocol != null;

        if(defaultFound && isDefault) {
            var defaultProtocolDetailValue = getDefaultProtocolDetailValueForUpdate();

            defaultProtocolDetailValue.setIsDefault(false);
            updateProtocolFromValue(defaultProtocolDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var protocol = ProtocolFactory.getInstance().create();
        var protocolDetail = ProtocolDetailFactory.getInstance().create(protocol, protocolName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        protocol = ProtocolFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                protocol.getPrimaryKey());
        protocol.setActiveDetail(protocolDetail);
        protocol.setLastDetail(protocolDetail);
        protocol.store();

        sendEvent(protocol.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return protocol;
    }

    private static final Map<EntityPermission, String> getProtocolByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM protocols, protocoldetails " +
                        "WHERE prot_activedetailid = protdt_protocoldetailid " +
                        "AND protdt_protocolname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM protocols, protocoldetails " +
                        "WHERE prot_activedetailid = protdt_protocoldetailid " +
                        "AND protdt_protocolname = ? " +
                        "FOR UPDATE");
        getProtocolByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private Protocol getProtocolByName(String protocolName, EntityPermission entityPermission) {
        return ProtocolFactory.getInstance().getEntityFromQuery(entityPermission, getProtocolByNameQueries, protocolName);
    }

    public Protocol getProtocolByName(String protocolName) {
        return getProtocolByName(protocolName, EntityPermission.READ_ONLY);
    }

    public Protocol getProtocolByNameForUpdate(String protocolName) {
        return getProtocolByName(protocolName, EntityPermission.READ_WRITE);
    }

    public ProtocolDetailValue getProtocolDetailValueForUpdate(Protocol protocol) {
        return protocol == null? null: protocol.getLastDetailForUpdate().getProtocolDetailValue().clone();
    }

    public ProtocolDetailValue getProtocolDetailValueByNameForUpdate(String protocolName) {
        return getProtocolDetailValueForUpdate(getProtocolByNameForUpdate(protocolName));
    }

    private static final Map<EntityPermission, String> getDefaultProtocolQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM protocols, protocoldetails " +
                        "WHERE prot_activedetailid = protdt_protocoldetailid " +
                        "AND protdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM protocols, protocoldetails " +
                        "WHERE prot_activedetailid = protdt_protocoldetailid " +
                        "AND protdt_isdefault = 1 " +
                        "FOR UPDATE");
        getDefaultProtocolQueries = Collections.unmodifiableMap(queryMap);
    }

    private Protocol getDefaultProtocol(EntityPermission entityPermission) {
        return ProtocolFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultProtocolQueries);
    }

    public Protocol getDefaultProtocol() {
        return getDefaultProtocol(EntityPermission.READ_ONLY);
    }

    public Protocol getDefaultProtocolForUpdate() {
        return getDefaultProtocol(EntityPermission.READ_WRITE);
    }

    public ProtocolDetailValue getDefaultProtocolDetailValueForUpdate() {
        return getDefaultProtocolForUpdate().getLastDetailForUpdate().getProtocolDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getProtocolsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM protocols, protocoldetails " +
                        "WHERE prot_activedetailid = protdt_protocoldetailid " +
                        "ORDER BY protdt_sortorder, protdt_protocolname " +
                        "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM protocols, protocoldetails " +
                        "WHERE prot_activedetailid = protdt_protocoldetailid " +
                        "FOR UPDATE");
        getProtocolsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Protocol> getProtocols(EntityPermission entityPermission) {
        return ProtocolFactory.getInstance().getEntitiesFromQuery(entityPermission, getProtocolsQueries);
    }

    public List<Protocol> getProtocols() {
        return getProtocols(EntityPermission.READ_ONLY);
    }

    public List<Protocol> getProtocolsForUpdate() {
        return getProtocols(EntityPermission.READ_WRITE);
    }

    public ProtocolTransfer getProtocolTransfer(UserVisit userVisit, Protocol protocol) {
        return protocolTransferCache.getProtocolTransfer(userVisit, protocol);
    }

    public List<ProtocolTransfer> getProtocolTransfers(UserVisit userVisit) {
        var protocols = getProtocols();
        List<ProtocolTransfer> protocolTransfers = new ArrayList<>(protocols.size());

        protocols.forEach((protocol) ->
                protocolTransfers.add(protocolTransferCache.getProtocolTransfer(userVisit, protocol))
        );

        return protocolTransfers;
    }

    public ProtocolChoicesBean getProtocolChoices(String defaultProtocolChoice, Language language, boolean allowNullChoice) {
        var protocols = getProtocols();
        var size = protocols.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultProtocolChoice == null) {
                defaultValue = "";
            }
        }

        for(var protocol : protocols) {
            var protocolDetail = protocol.getLastDetail();

            var label = getBestProtocolDescription(protocol, language);
            var value = protocolDetail.getProtocolName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultProtocolChoice != null && defaultProtocolChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && protocolDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ProtocolChoicesBean(labels, values, defaultValue);
    }

    private void updateProtocolFromValue(ProtocolDetailValue protocolDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(protocolDetailValue.hasBeenModified()) {
            var protocol = ProtocolFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    protocolDetailValue.getProtocolPK());
            var protocolDetail = protocol.getActiveDetailForUpdate();

            protocolDetail.setThruTime(session.START_TIME_LONG);
            protocolDetail.store();

            var protocolPK = protocolDetail.getProtocolPK(); // Not updated
            var protocolName = protocolDetailValue.getProtocolName();
            var isDefault = protocolDetailValue.getIsDefault();
            var sortOrder = protocolDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultProtocol = getDefaultProtocol();
                var defaultFound = defaultProtocol != null && !defaultProtocol.equals(protocol);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultProtocolDetailValue = getDefaultProtocolDetailValueForUpdate();

                    defaultProtocolDetailValue.setIsDefault(false);
                    updateProtocolFromValue(defaultProtocolDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            protocolDetail = ProtocolDetailFactory.getInstance().create(protocolPK, protocolName, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            protocol.setActiveDetail(protocolDetail);
            protocol.setLastDetail(protocolDetail);

            sendEvent(protocolPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateProtocolFromValue(ProtocolDetailValue protocolDetailValue, BasePK updatedBy) {
        updateProtocolFromValue(protocolDetailValue, true, updatedBy);
    }

    private void deleteProtocol(Protocol protocol, boolean checkDefault, BasePK deletedBy) {
        var protocolDetail = protocol.getLastDetailForUpdate();

        deleteServicesByProtocol(protocol, deletedBy);
        deleteProtocolDescriptionsByProtocol(protocol, deletedBy);

        protocolDetail.setThruTime(session.START_TIME_LONG);
        protocol.setActiveDetail(null);
        protocol.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultProtocol = getDefaultProtocol();

            if(defaultProtocol == null) {
                var protocols = getProtocolsForUpdate();

                if(!protocols.isEmpty()) {
                    var iter = protocols.iterator();
                    if(iter.hasNext()) {
                        defaultProtocol = iter.next();
                    }
                    var protocolDetailValue = Objects.requireNonNull(defaultProtocol).getLastDetailForUpdate().getProtocolDetailValue().clone();

                    protocolDetailValue.setIsDefault(true);
                    updateProtocolFromValue(protocolDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(protocol.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteProtocol(Protocol protocol, BasePK deletedBy) {
        deleteProtocol(protocol, true, deletedBy);
    }

    private void deleteProtocols(List<Protocol> protocols, boolean checkDefault, BasePK deletedBy) {
        protocols.forEach((protocol) -> deleteProtocol(protocol, checkDefault, deletedBy));
    }

    public void deleteProtocols(List<Protocol> protocols, BasePK deletedBy) {
        deleteProtocols(protocols, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Protocol Descriptions
    // --------------------------------------------------------------------------------

    public ProtocolDescription createProtocolDescription(Protocol protocol, Language language, String description, BasePK createdBy) {
        var protocolDescription = ProtocolDescriptionFactory.getInstance().create(protocol, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(protocol.getPrimaryKey(), EventTypes.MODIFY, protocolDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return protocolDescription;
    }

    private static final Map<EntityPermission, String> getProtocolDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM protocoldescriptions " +
                        "WHERE protd_prot_protocolid = ? AND protd_lang_languageid = ? AND protd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM protocoldescriptions " +
                        "WHERE protd_prot_protocolid = ? AND protd_lang_languageid = ? AND protd_thrutime = ? " +
                        "FOR UPDATE");
        getProtocolDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ProtocolDescription getProtocolDescription(Protocol protocol, Language language, EntityPermission entityPermission) {
        return ProtocolDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getProtocolDescriptionQueries,
                protocol, language, Session.MAX_TIME);
    }

    public ProtocolDescription getProtocolDescription(Protocol protocol, Language language) {
        return getProtocolDescription(protocol, language, EntityPermission.READ_ONLY);
    }

    public ProtocolDescription getProtocolDescriptionForUpdate(Protocol protocol, Language language) {
        return getProtocolDescription(protocol, language, EntityPermission.READ_WRITE);
    }

    public ProtocolDescriptionValue getProtocolDescriptionValue(ProtocolDescription protocolDescription) {
        return protocolDescription == null? null: protocolDescription.getProtocolDescriptionValue().clone();
    }

    public ProtocolDescriptionValue getProtocolDescriptionValueForUpdate(Protocol protocol, Language language) {
        return getProtocolDescriptionValue(getProtocolDescriptionForUpdate(protocol, language));
    }

    private static final Map<EntityPermission, String> getProtocolDescriptionsByProtocolQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM protocoldescriptions, languages " +
                        "WHERE protd_prot_protocolid = ? AND protd_thrutime = ? AND protd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM protocoldescriptions " +
                        "WHERE protd_prot_protocolid = ? AND protd_thrutime = ? " +
                        "FOR UPDATE");
        getProtocolDescriptionsByProtocolQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ProtocolDescription> getProtocolDescriptionsByProtocol(Protocol protocol, EntityPermission entityPermission) {
        return ProtocolDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getProtocolDescriptionsByProtocolQueries,
                protocol, Session.MAX_TIME);
    }

    public List<ProtocolDescription> getProtocolDescriptionsByProtocol(Protocol protocol) {
        return getProtocolDescriptionsByProtocol(protocol, EntityPermission.READ_ONLY);
    }

    public List<ProtocolDescription> getProtocolDescriptionsByProtocolForUpdate(Protocol protocol) {
        return getProtocolDescriptionsByProtocol(protocol, EntityPermission.READ_WRITE);
    }

    public String getBestProtocolDescription(Protocol protocol, Language language) {
        String description;
        var protocolDescription = getProtocolDescription(protocol, language);

        if(protocolDescription == null && !language.getIsDefault()) {
            protocolDescription = getProtocolDescription(protocol, partyControl.getDefaultLanguage());
        }

        if(protocolDescription == null) {
            description = protocol.getLastDetail().getProtocolName();
        } else {
            description = protocolDescription.getDescription();
        }

        return description;
    }

    public ProtocolDescriptionTransfer getProtocolDescriptionTransfer(UserVisit userVisit, ProtocolDescription protocolDescription) {
        return protocolDescriptionTransferCache.getProtocolDescriptionTransfer(userVisit, protocolDescription);
    }

    public List<ProtocolDescriptionTransfer> getProtocolDescriptionTransfersByProtocol(UserVisit userVisit, Protocol protocol) {
        var protocolDescriptions = getProtocolDescriptionsByProtocol(protocol);
        List<ProtocolDescriptionTransfer> protocolDescriptionTransfers = new ArrayList<>(protocolDescriptions.size());

        protocolDescriptions.forEach((protocolDescription) ->
                protocolDescriptionTransfers.add(protocolDescriptionTransferCache.getProtocolDescriptionTransfer(userVisit, protocolDescription))
        );

        return protocolDescriptionTransfers;
    }

    public void updateProtocolDescriptionFromValue(ProtocolDescriptionValue protocolDescriptionValue, BasePK updatedBy) {
        if(protocolDescriptionValue.hasBeenModified()) {
            var protocolDescription = ProtocolDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    protocolDescriptionValue.getPrimaryKey());

            protocolDescription.setThruTime(session.START_TIME_LONG);
            protocolDescription.store();

            var protocol = protocolDescription.getProtocol();
            var language = protocolDescription.getLanguage();
            var description = protocolDescriptionValue.getDescription();

            protocolDescription = ProtocolDescriptionFactory.getInstance().create(protocol, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(protocol.getPrimaryKey(), EventTypes.MODIFY, protocolDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteProtocolDescription(ProtocolDescription protocolDescription, BasePK deletedBy) {
        protocolDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(protocolDescription.getProtocolPK(), EventTypes.MODIFY, protocolDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteProtocolDescriptionsByProtocol(Protocol protocol, BasePK deletedBy) {
        var protocolDescriptions = getProtocolDescriptionsByProtocolForUpdate(protocol);

        protocolDescriptions.forEach((protocolDescription) ->
                deleteProtocolDescription(protocolDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Services
    // --------------------------------------------------------------------------------

    public Service createService(String serviceName, Integer port, Protocol protocol, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultService = getDefaultService();
        var defaultFound = defaultService != null;

        if(defaultFound && isDefault) {
            var defaultServiceDetailValue = getDefaultServiceDetailValueForUpdate();

            defaultServiceDetailValue.setIsDefault(false);
            updateServiceFromValue(defaultServiceDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var service = ServiceFactory.getInstance().create();
        var serviceDetail = ServiceDetailFactory.getInstance().create(service, serviceName, port, protocol, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        service = ServiceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                service.getPrimaryKey());
        service.setActiveDetail(serviceDetail);
        service.setLastDetail(serviceDetail);
        service.store();

        sendEvent(service.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return service;
    }

    private static final Map<EntityPermission, String> getServiceByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM services, servicedetails " +
                        "WHERE srv_activedetailid = srvdt_servicedetailid " +
                        "AND srvdt_servicename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM services, servicedetails " +
                        "WHERE srv_activedetailid = srvdt_servicedetailid " +
                        "AND srvdt_servicename = ? " +
                        "FOR UPDATE");
        getServiceByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private Service getServiceByName(String serviceName, EntityPermission entityPermission) {
        return ServiceFactory.getInstance().getEntityFromQuery(entityPermission, getServiceByNameQueries, serviceName);
    }

    public Service getServiceByName(String serviceName) {
        return getServiceByName(serviceName, EntityPermission.READ_ONLY);
    }

    public Service getServiceByNameForUpdate(String serviceName) {
        return getServiceByName(serviceName, EntityPermission.READ_WRITE);
    }

    public ServiceDetailValue getServiceDetailValueForUpdate(Service service) {
        return service == null? null: service.getLastDetailForUpdate().getServiceDetailValue().clone();
    }

    public ServiceDetailValue getServiceDetailValueByNameForUpdate(String serviceName) {
        return getServiceDetailValueForUpdate(getServiceByNameForUpdate(serviceName));
    }

    private static final Map<EntityPermission, String> getDefaultServiceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM services, servicedetails " +
                        "WHERE srv_activedetailid = srvdt_servicedetailid " +
                        "AND srvdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM services, servicedetails " +
                        "WHERE srv_activedetailid = srvdt_servicedetailid " +
                        "AND srvdt_isdefault = 1 " +
                        "FOR UPDATE");
        getDefaultServiceQueries = Collections.unmodifiableMap(queryMap);
    }

    private Service getDefaultService(EntityPermission entityPermission) {
        return ServiceFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultServiceQueries);
    }

    public Service getDefaultService() {
        return getDefaultService(EntityPermission.READ_ONLY);
    }

    public Service getDefaultServiceForUpdate() {
        return getDefaultService(EntityPermission.READ_WRITE);
    }

    public ServiceDetailValue getDefaultServiceDetailValueForUpdate() {
        return getDefaultServiceForUpdate().getLastDetailForUpdate().getServiceDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getServicesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM services, servicedetails " +
                        "WHERE srv_activedetailid = srvdt_servicedetailid " +
                        "ORDER BY srvdt_sortorder, srvdt_servicename " +
                        "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM services, servicedetails " +
                        "WHERE srv_activedetailid = srvdt_servicedetailid " +
                        "FOR UPDATE");
        getServicesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Service> getServices(EntityPermission entityPermission) {
        return ServiceFactory.getInstance().getEntitiesFromQuery(entityPermission, getServicesQueries);
    }

    public List<Service> getServices() {
        return getServices(EntityPermission.READ_ONLY);
    }

    public List<Service> getServicesForUpdate() {
        return getServices(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getServicesByProtocolQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM services, servicedetails " +
                        "WHERE srv_activedetailid = srvdt_servicedetailid " +
                        "AND srvdt_prot_protocolid = ? " +
                        "ORDER BY srvdt_sortorder, srvdt_servicename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM services, servicedetails " +
                        "WHERE srv_activedetailid = srvdt_servicedetailid " +
                        "AND srvdt_prot_protocolid = ? " +
                        "FOR UPDATE");
        getServicesByProtocolQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Service> getServicesByProtocol(Protocol protocol, EntityPermission entityPermission) {
        return ServiceFactory.getInstance().getEntitiesFromQuery(entityPermission, getServicesByProtocolQueries,
                protocol);
    }

    public List<Service> getServicesByProtocol(Protocol protocol) {
        return getServicesByProtocol(protocol, EntityPermission.READ_ONLY);
    }

    public List<Service> getServicesByProtocolForUpdate(Protocol protocol) {
        return getServicesByProtocol(protocol, EntityPermission.READ_WRITE);
    }

    public ServiceTransfer getServiceTransfer(UserVisit userVisit, Service service) {
        return serviceTransferCache.getServiceTransfer(userVisit, service);
    }

    public List<ServiceTransfer> getServiceTransfers(UserVisit userVisit) {
        var services = getServices();
        List<ServiceTransfer> serviceTransfers = new ArrayList<>(services.size());

        services.forEach((service) ->
                serviceTransfers.add(serviceTransferCache.getServiceTransfer(userVisit, service))
        );

        return serviceTransfers;
    }

    public ServiceChoicesBean getServiceChoices(String defaultServiceChoice, Language language, boolean allowNullChoice) {
        var services = getServices();
        var size = services.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultServiceChoice == null) {
                defaultValue = "";
            }
        }

        for(var service : services) {
            var serviceDetail = service.getLastDetail();

            var label = getBestServiceDescription(service, language);
            var value = serviceDetail.getServiceName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultServiceChoice != null && defaultServiceChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && serviceDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ServiceChoicesBean(labels, values, defaultValue);
    }

    private void updateServiceFromValue(ServiceDetailValue serviceDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(serviceDetailValue.hasBeenModified()) {
            var service = ServiceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    serviceDetailValue.getServicePK());
            var serviceDetail = service.getActiveDetailForUpdate();

            serviceDetail.setThruTime(session.START_TIME_LONG);
            serviceDetail.store();

            var servicePK = serviceDetail.getServicePK(); // Not updated
            var serviceName = serviceDetailValue.getServiceName();
            var port = serviceDetailValue.getPort();
            var protocolPK = serviceDetailValue.getProtocolPK();
            var isDefault = serviceDetailValue.getIsDefault();
            var sortOrder = serviceDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultService = getDefaultService();
                var defaultFound = defaultService != null && !defaultService.equals(service);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultServiceDetailValue = getDefaultServiceDetailValueForUpdate();

                    defaultServiceDetailValue.setIsDefault(false);
                    updateServiceFromValue(defaultServiceDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            serviceDetail = ServiceDetailFactory.getInstance().create(servicePK, serviceName, port, protocolPK, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            service.setActiveDetail(serviceDetail);
            service.setLastDetail(serviceDetail);

            sendEvent(servicePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateServiceFromValue(ServiceDetailValue serviceDetailValue, BasePK updatedBy) {
        updateServiceFromValue(serviceDetailValue, true, updatedBy);
    }

    private void deleteService(Service service, boolean checkDefault, BasePK deletedBy) {
        var serviceDetail = service.getLastDetailForUpdate();

        deleteServerServicesByService(service, deletedBy);
        deleteServiceDescriptionsByService(service, deletedBy);

        serviceDetail.setThruTime(session.START_TIME_LONG);
        service.setActiveDetail(null);
        service.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultService = getDefaultService();

            if(defaultService == null) {
                var services = getServicesForUpdate();

                if(!services.isEmpty()) {
                    var iter = services.iterator();
                    if(iter.hasNext()) {
                        defaultService = iter.next();
                    }
                    var serviceDetailValue = Objects.requireNonNull(defaultService).getLastDetailForUpdate().getServiceDetailValue().clone();

                    serviceDetailValue.setIsDefault(true);
                    updateServiceFromValue(serviceDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(service.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteService(Service service, BasePK deletedBy) {
        deleteService(service, true, deletedBy);
    }

    private void deleteServices(List<Service> services, boolean checkDefault, BasePK deletedBy) {
        services.forEach((service) -> deleteService(service, checkDefault, deletedBy));
    }

    public void deleteServices(List<Service> services, BasePK deletedBy) {
        deleteServices(services, true, deletedBy);
    }

    public void deleteServicesByProtocol(Protocol protocol, BasePK deletedBy) {
        deleteServices(getServicesByProtocol(protocol), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Service Descriptions
    // --------------------------------------------------------------------------------

    public ServiceDescription createServiceDescription(Service service, Language language, String description, BasePK createdBy) {
        var serviceDescription = ServiceDescriptionFactory.getInstance().create(service, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(service.getPrimaryKey(), EventTypes.MODIFY, serviceDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return serviceDescription;
    }

    private static final Map<EntityPermission, String> getServiceDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM servicedescriptions " +
                        "WHERE srvd_srv_serviceid = ? AND srvd_lang_languageid = ? AND srvd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM servicedescriptions " +
                        "WHERE srvd_srv_serviceid = ? AND srvd_lang_languageid = ? AND srvd_thrutime = ? " +
                        "FOR UPDATE");
        getServiceDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ServiceDescription getServiceDescription(Service service, Language language, EntityPermission entityPermission) {
        return ServiceDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getServiceDescriptionQueries,
                service, language, Session.MAX_TIME);
    }

    public ServiceDescription getServiceDescription(Service service, Language language) {
        return getServiceDescription(service, language, EntityPermission.READ_ONLY);
    }

    public ServiceDescription getServiceDescriptionForUpdate(Service service, Language language) {
        return getServiceDescription(service, language, EntityPermission.READ_WRITE);
    }

    public ServiceDescriptionValue getServiceDescriptionValue(ServiceDescription serviceDescription) {
        return serviceDescription == null? null: serviceDescription.getServiceDescriptionValue().clone();
    }

    public ServiceDescriptionValue getServiceDescriptionValueForUpdate(Service service, Language language) {
        return getServiceDescriptionValue(getServiceDescriptionForUpdate(service, language));
    }

    private static final Map<EntityPermission, String> getServiceDescriptionsByServiceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM servicedescriptions, languages " +
                        "WHERE srvd_srv_serviceid = ? AND srvd_thrutime = ? AND srvd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM servicedescriptions " +
                        "WHERE srvd_srv_serviceid = ? AND srvd_thrutime = ? " +
                        "FOR UPDATE");
        getServiceDescriptionsByServiceQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ServiceDescription> getServiceDescriptionsByService(Service service, EntityPermission entityPermission) {
        return ServiceDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getServiceDescriptionsByServiceQueries,
                service, Session.MAX_TIME);
    }

    public List<ServiceDescription> getServiceDescriptionsByService(Service service) {
        return getServiceDescriptionsByService(service, EntityPermission.READ_ONLY);
    }

    public List<ServiceDescription> getServiceDescriptionsByServiceForUpdate(Service service) {
        return getServiceDescriptionsByService(service, EntityPermission.READ_WRITE);
    }

    public String getBestServiceDescription(Service service, Language language) {
        String description;
        var serviceDescription = getServiceDescription(service, language);

        if(serviceDescription == null && !language.getIsDefault()) {
            serviceDescription = getServiceDescription(service, partyControl.getDefaultLanguage());
        }

        if(serviceDescription == null) {
            description = service.getLastDetail().getServiceName();
        } else {
            description = serviceDescription.getDescription();
        }

        return description;
    }

    public ServiceDescriptionTransfer getServiceDescriptionTransfer(UserVisit userVisit, ServiceDescription serviceDescription) {
        return serviceDescriptionTransferCache.getServiceDescriptionTransfer(userVisit, serviceDescription);
    }

    public List<ServiceDescriptionTransfer> getServiceDescriptionTransfersByService(UserVisit userVisit, Service service) {
        var serviceDescriptions = getServiceDescriptionsByService(service);
        List<ServiceDescriptionTransfer> serviceDescriptionTransfers = new ArrayList<>(serviceDescriptions.size());

        serviceDescriptions.forEach((serviceDescription) ->
                serviceDescriptionTransfers.add(serviceDescriptionTransferCache.getServiceDescriptionTransfer(userVisit, serviceDescription))
        );

        return serviceDescriptionTransfers;
    }

    public void updateServiceDescriptionFromValue(ServiceDescriptionValue serviceDescriptionValue, BasePK updatedBy) {
        if(serviceDescriptionValue.hasBeenModified()) {
            var serviceDescription = ServiceDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    serviceDescriptionValue.getPrimaryKey());

            serviceDescription.setThruTime(session.START_TIME_LONG);
            serviceDescription.store();

            var service = serviceDescription.getService();
            var language = serviceDescription.getLanguage();
            var description = serviceDescriptionValue.getDescription();

            serviceDescription = ServiceDescriptionFactory.getInstance().create(service, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(service.getPrimaryKey(), EventTypes.MODIFY, serviceDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteServiceDescription(ServiceDescription serviceDescription, BasePK deletedBy) {
        serviceDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(serviceDescription.getServicePK(), EventTypes.MODIFY, serviceDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteServiceDescriptionsByService(Service service, BasePK deletedBy) {
        var serviceDescriptions = getServiceDescriptionsByServiceForUpdate(service);

        serviceDescriptions.forEach((serviceDescription) ->
                deleteServiceDescription(serviceDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Servers
    // --------------------------------------------------------------------------------

    public Server createServer(String serverName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultServer = getDefaultServer();
        var defaultFound = defaultServer != null;

        if(defaultFound && isDefault) {
            var defaultServerDetailValue = getDefaultServerDetailValueForUpdate();

            defaultServerDetailValue.setIsDefault(false);
            updateServerFromValue(defaultServerDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var server = ServerFactory.getInstance().create();
        var serverDetail = ServerDetailFactory.getInstance().create(server, serverName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        server = ServerFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, server.getPrimaryKey());
        server.setActiveDetail(serverDetail);
        server.setLastDetail(serverDetail);
        server.store();

        sendEvent(server.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return server;
    }

    private static final Map<EntityPermission, String> getServerByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM servers, serverdetails " +
                        "WHERE serv_activedetailid = servdt_serverdetailid " +
                        "AND servdt_servername = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM servers, serverdetails " +
                        "WHERE serv_activedetailid = servdt_serverdetailid " +
                        "AND servdt_servername = ? " +
                        "FOR UPDATE");
        getServerByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private Server getServerByName(String serverName, EntityPermission entityPermission) {
        return ServerFactory.getInstance().getEntityFromQuery(entityPermission, getServerByNameQueries, serverName);
    }

    public Server getServerByName(String serverName) {
        return getServerByName(serverName, EntityPermission.READ_ONLY);
    }

    public Server getServerByNameForUpdate(String serverName) {
        return getServerByName(serverName, EntityPermission.READ_WRITE);
    }

    public ServerDetailValue getServerDetailValueForUpdate(Server server) {
        return server == null? null: server.getLastDetailForUpdate().getServerDetailValue().clone();
    }

    public ServerDetailValue getServerDetailValueByNameForUpdate(String serverName) {
        return getServerDetailValueForUpdate(getServerByNameForUpdate(serverName));
    }

    private static final Map<EntityPermission, String> getDefaultServerQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM servers, serverdetails " +
                        "WHERE serv_activedetailid = servdt_serverdetailid " +
                        "AND servdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM servers, serverdetails " +
                        "WHERE serv_activedetailid = servdt_serverdetailid " +
                        "AND servdt_isdefault = 1 " +
                        "FOR UPDATE");
        getDefaultServerQueries = Collections.unmodifiableMap(queryMap);
    }

    private Server getDefaultServer(EntityPermission entityPermission) {
        return ServerFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultServerQueries);
    }

    public Server getDefaultServer() {
        return getDefaultServer(EntityPermission.READ_ONLY);
    }

    public Server getDefaultServerForUpdate() {
        return getDefaultServer(EntityPermission.READ_WRITE);
    }

    public ServerDetailValue getDefaultServerDetailValueForUpdate() {
        return getDefaultServerForUpdate().getLastDetailForUpdate().getServerDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getServersQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM servers, serverdetails " +
                        "WHERE serv_activedetailid = servdt_serverdetailid " +
                        "ORDER BY servdt_sortorder, servdt_servername " +
                        "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM servers, serverdetails " +
                        "WHERE serv_activedetailid = servdt_serverdetailid " +
                        "FOR UPDATE");
        getServersQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Server> getServers(EntityPermission entityPermission) {
        return ServerFactory.getInstance().getEntitiesFromQuery(entityPermission, getServersQueries);
    }

    public List<Server> getServers() {
        return getServers(EntityPermission.READ_ONLY);
    }

    public List<Server> getServersForUpdate() {
        return getServers(EntityPermission.READ_WRITE);
    }

    public ServerTransfer getServerTransfer(UserVisit userVisit, Server server) {
        return serverTransferCache.getServerTransfer(userVisit, server);
    }

    public List<ServerTransfer> getServerTransfers(UserVisit userVisit) {
        var servers = getServers();
        List<ServerTransfer> serverTransfers = new ArrayList<>(servers.size());

        servers.forEach((server) ->
                serverTransfers.add(serverTransferCache.getServerTransfer(userVisit, server))
        );

        return serverTransfers;
    }

    public ServerChoicesBean getServerChoices(String defaultServerChoice, Language language, boolean allowNullChoice) {
        var servers = getServers();
        var size = servers.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultServerChoice == null) {
                defaultValue = "";
            }
        }

        for(var server : servers) {
            var serverDetail = server.getLastDetail();

            var label = getBestServerDescription(server, language);
            var value = serverDetail.getServerName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultServerChoice != null && defaultServerChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && serverDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ServerChoicesBean(labels, values, defaultValue);
    }

    private void updateServerFromValue(ServerDetailValue serverDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(serverDetailValue.hasBeenModified()) {
            var server = ServerFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    serverDetailValue.getServerPK());
            var serverDetail = server.getActiveDetailForUpdate();

            serverDetail.setThruTime(session.START_TIME_LONG);
            serverDetail.store();

            var serverPK = serverDetail.getServerPK(); // Not updated
            var serverName = serverDetailValue.getServerName();
            var isDefault = serverDetailValue.getIsDefault();
            var sortOrder = serverDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultServer = getDefaultServer();
                var defaultFound = defaultServer != null && !defaultServer.equals(server);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultServerDetailValue = getDefaultServerDetailValueForUpdate();

                    defaultServerDetailValue.setIsDefault(false);
                    updateServerFromValue(defaultServerDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            serverDetail = ServerDetailFactory.getInstance().create(serverPK, serverName, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            server.setActiveDetail(serverDetail);
            server.setLastDetail(serverDetail);

            sendEvent(serverPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateServerFromValue(ServerDetailValue serverDetailValue, BasePK updatedBy) {
        updateServerFromValue(serverDetailValue, true, updatedBy);
    }

    private void deleteServer(Server server, boolean checkDefault, BasePK deletedBy) {
        var serverDetail = server.getLastDetailForUpdate();

        deleteServerServicesByServer(server, deletedBy);
        deleteServerDescriptionsByServer(server, deletedBy);

        serverDetail.setThruTime(session.START_TIME_LONG);
        server.setActiveDetail(null);
        server.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultServer = getDefaultServer();

            if(defaultServer == null) {
                var servers = getServersForUpdate();

                if(!servers.isEmpty()) {
                    var iter = servers.iterator();
                    if(iter.hasNext()) {
                        defaultServer = iter.next();
                    }
                    var serverDetailValue = Objects.requireNonNull(defaultServer).getLastDetailForUpdate().getServerDetailValue().clone();

                    serverDetailValue.setIsDefault(true);
                    updateServerFromValue(serverDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(server.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteServer(Server server, BasePK deletedBy) {
        deleteServer(server, true, deletedBy);
    }

    private void deleteServers(List<Server> servers, boolean checkDefault, BasePK deletedBy) {
        servers.forEach((server) -> deleteServer(server, checkDefault, deletedBy));
    }

    public void deleteServers(List<Server> servers, BasePK deletedBy) {
        deleteServers(servers, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Server Descriptions
    // --------------------------------------------------------------------------------

    public ServerDescription createServerDescription(Server server, Language language, String description, BasePK createdBy) {
        var serverDescription = ServerDescriptionFactory.getInstance().create(server, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(server.getPrimaryKey(), EventTypes.MODIFY, serverDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return serverDescription;
    }

    private static final Map<EntityPermission, String> getServerDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM serverdescriptions " +
                        "WHERE servd_serv_serverid = ? AND servd_lang_languageid = ? AND servd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM serverdescriptions " +
                        "WHERE servd_serv_serverid = ? AND servd_lang_languageid = ? AND servd_thrutime = ? " +
                        "FOR UPDATE");
        getServerDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ServerDescription getServerDescription(Server server, Language language, EntityPermission entityPermission) {
        return ServerDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getServerDescriptionQueries,
                server, language, Session.MAX_TIME);
    }

    public ServerDescription getServerDescription(Server server, Language language) {
        return getServerDescription(server, language, EntityPermission.READ_ONLY);
    }

    public ServerDescription getServerDescriptionForUpdate(Server server, Language language) {
        return getServerDescription(server, language, EntityPermission.READ_WRITE);
    }

    public ServerDescriptionValue getServerDescriptionValue(ServerDescription serverDescription) {
        return serverDescription == null? null: serverDescription.getServerDescriptionValue().clone();
    }

    public ServerDescriptionValue getServerDescriptionValueForUpdate(Server server, Language language) {
        return getServerDescriptionValue(getServerDescriptionForUpdate(server, language));
    }

    private static final Map<EntityPermission, String> getServerDescriptionsByServerQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM serverdescriptions, languages " +
                        "WHERE servd_serv_serverid = ? AND servd_thrutime = ? AND servd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM serverdescriptions " +
                        "WHERE servd_serv_serverid = ? AND servd_thrutime = ? " +
                        "FOR UPDATE");
        getServerDescriptionsByServerQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ServerDescription> getServerDescriptionsByServer(Server server, EntityPermission entityPermission) {
        return ServerDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getServerDescriptionsByServerQueries,
                server, Session.MAX_TIME);
    }

    public List<ServerDescription> getServerDescriptionsByServer(Server server) {
        return getServerDescriptionsByServer(server, EntityPermission.READ_ONLY);
    }

    public List<ServerDescription> getServerDescriptionsByServerForUpdate(Server server) {
        return getServerDescriptionsByServer(server, EntityPermission.READ_WRITE);
    }

    public String getBestServerDescription(Server server, Language language) {
        String description;
        var serverDescription = getServerDescription(server, language);

        if(serverDescription == null && !language.getIsDefault()) {
            serverDescription = getServerDescription(server, partyControl.getDefaultLanguage());
        }

        if(serverDescription == null) {
            description = server.getLastDetail().getServerName();
        } else {
            description = serverDescription.getDescription();
        }

        return description;
    }

    public ServerDescriptionTransfer getServerDescriptionTransfer(UserVisit userVisit, ServerDescription serverDescription) {
        return serverDescriptionTransferCache.getServerDescriptionTransfer(userVisit, serverDescription);
    }

    public List<ServerDescriptionTransfer> getServerDescriptionTransfersByServer(UserVisit userVisit, Server server) {
        var serverDescriptions = getServerDescriptionsByServer(server);
        List<ServerDescriptionTransfer> serverDescriptionTransfers = new ArrayList<>(serverDescriptions.size());

        serverDescriptions.forEach((serverDescription) ->
                serverDescriptionTransfers.add(serverDescriptionTransferCache.getServerDescriptionTransfer(userVisit, serverDescription))
        );

        return serverDescriptionTransfers;
    }

    public void updateServerDescriptionFromValue(ServerDescriptionValue serverDescriptionValue, BasePK updatedBy) {
        if(serverDescriptionValue.hasBeenModified()) {
            var serverDescription = ServerDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    serverDescriptionValue.getPrimaryKey());

            serverDescription.setThruTime(session.START_TIME_LONG);
            serverDescription.store();

            var server = serverDescription.getServer();
            var language = serverDescription.getLanguage();
            var description = serverDescriptionValue.getDescription();

            serverDescription = ServerDescriptionFactory.getInstance().create(server, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(server.getPrimaryKey(), EventTypes.MODIFY, serverDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteServerDescription(ServerDescription serverDescription, BasePK deletedBy) {
        serverDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(serverDescription.getServerPK(), EventTypes.MODIFY, serverDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteServerDescriptionsByServer(Server server, BasePK deletedBy) {
        var serverDescriptions = getServerDescriptionsByServerForUpdate(server);

        serverDescriptions.forEach((serverDescription) ->
                deleteServerDescription(serverDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Server Services
    // --------------------------------------------------------------------------------

    public ServerService createServerService(Server server, Service service, BasePK createdBy) {
        var serverService = ServerServiceFactory.getInstance().create(server, service, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(server.getPrimaryKey(), EventTypes.MODIFY, serverService.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return serverService;
    }

    private static final Map<EntityPermission, String> getServerServiceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM serverservices " +
                        "WHERE servsrv_serv_serverid = ? AND servsrv_srv_serviceid = ? AND servsrv_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM serverservices " +
                        "WHERE servsrv_serv_serverid = ? AND servsrv_srv_serviceid = ? AND servsrv_thrutime = ? " +
                        "FOR UPDATE");
        getServerServiceQueries = Collections.unmodifiableMap(queryMap);
    }

    private ServerService getServerService(Server server, Service service, EntityPermission entityPermission) {
        return ServerServiceFactory.getInstance().getEntityFromQuery(entityPermission, getServerServiceQueries,
                server, service, Session.MAX_TIME);
    }

    public ServerService getServerService(Server server, Service service) {
        return getServerService(server, service, EntityPermission.READ_ONLY);
    }

    public ServerService getServerServiceForUpdate(Server server, Service service) {
        return getServerService(server, service, EntityPermission.READ_WRITE);
    }

    public ServerServiceValue getServerServiceValue(ServerService serverService) {
        return serverService == null? null: serverService.getServerServiceValue().clone();
    }

    public ServerServiceValue getServerServiceValueForUpdate(Server server, Service service) {
        return getServerServiceValue(getServerServiceForUpdate(server, service));
    }

    private static final Map<EntityPermission, String> getServerServicesByServerQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM serverservices, services, servicedetails " +
                        "WHERE servsrv_serv_serverid = ? AND servsrv_thrutime = ? " +
                        "AND servsrv_srv_serviceid = srv_serviceid AND srv_lastdetailid = srvdt_servicedetailid " +
                        "ORDER BY srvdt_sortorder, srvdt_servicename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM serverservices " +
                        "WHERE servsrv_serv_serverid = ? AND servsrv_thrutime = ? " +
                        "FOR UPDATE");
        getServerServicesByServerQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ServerService> getServerServicesByServer(Server server, EntityPermission entityPermission) {
        return ServerServiceFactory.getInstance().getEntitiesFromQuery(entityPermission, getServerServicesByServerQueries,
                server, Session.MAX_TIME);
    }

    public List<ServerService> getServerServicesByServer(Server server) {
        return getServerServicesByServer(server, EntityPermission.READ_ONLY);
    }

    public List<ServerService> getServerServicesByServerForUpdate(Server server) {
        return getServerServicesByServer(server, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getServerServicesByServiceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM serverservices, services, servicedetails " +
                        "WHERE servsrv_srv_serviceid = ? AND servsrv_thrutime = ? " +
                        "AND servsrv_serv_serverid = serv_serverid AND serv_lastdetailid = servdt_serverdetailid " +
                        "ORDER BY servdt_sortorder, servdt_servername");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM serverservices " +
                        "WHERE servsrv_srv_serviceid = ? AND servsrv_thrutime = ? " +
                        "FOR UPDATE");
        getServerServicesByServiceQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ServerService> getServerServicesByService(Service service, EntityPermission entityPermission) {
        return ServerServiceFactory.getInstance().getEntitiesFromQuery(entityPermission, getServerServicesByServiceQueries,
                service, Session.MAX_TIME);
    }

    public List<ServerService> getServerServicesByService(Service service) {
        return getServerServicesByService(service, EntityPermission.READ_ONLY);
    }

    public List<ServerService> getServerServicesByServiceForUpdate(Service service) {
        return getServerServicesByService(service, EntityPermission.READ_WRITE);
    }

    public ServerServiceTransfer getServerServiceTransfer(UserVisit userVisit, ServerService serverService) {
        return serverServiceTransferCache.getServerServiceTransfer(userVisit, serverService);
    }

    public List<ServerServiceTransfer> getServerServiceTransfersByServer(UserVisit userVisit, Server server) {
        var serverServices = getServerServicesByServer(server);
        List<ServerServiceTransfer> serverServiceTransfers = new ArrayList<>(serverServices.size());

        serverServices.forEach((serverService) ->
                serverServiceTransfers.add(serverServiceTransferCache.getServerServiceTransfer(userVisit, serverService))
        );

        return serverServiceTransfers;
    }

    public void deleteServerService(ServerService serverService, BasePK deletedBy) {
        var scaleControl = Session.getModelController(ScaleControl.class);

        scaleControl.deleteScalesByServerService(serverService, deletedBy);

        serverService.setThruTime(session.START_TIME_LONG);

        sendEvent(serverService.getServerPK(), EventTypes.MODIFY, serverService.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteServerServices(List<ServerService> serverServices, BasePK deletedBy) {
        serverServices.forEach((serverService) ->
                deleteServerService(serverService, deletedBy)
        );
    }

    public void deleteServerServicesByServer(Server server, BasePK deletedBy) {
        deleteServerServices(getServerServicesByServerForUpdate(server), deletedBy);
    }

    public void deleteServerServicesByService(Service service, BasePK deletedBy) {
        deleteServerServices(getServerServicesByServiceForUpdate(service), deletedBy);
    }

}
