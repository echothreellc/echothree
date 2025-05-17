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

package com.echothree.model.control.party.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.party.common.transfer.PartyApplicationEditorUseTransfer;
import com.echothree.model.data.core.server.entity.ApplicationEditor;
import com.echothree.model.data.core.server.entity.ApplicationEditorUse;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyApplicationEditorUse;
import com.echothree.model.data.party.server.factory.PartyApplicationEditorUseDetailFactory;
import com.echothree.model.data.party.server.factory.PartyApplicationEditorUseFactory;
import com.echothree.model.data.party.server.value.PartyApplicationEditorUseDetailValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PartyApplicationEditorUseControl
        extends BasePartyControl {

    /** Creates a new instance of PartyApplicationEditorUseControl */
    public PartyApplicationEditorUseControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Party Application Editor Uses
    // --------------------------------------------------------------------------------

    public PartyApplicationEditorUse createPartyApplicationEditorUse(Party party, ApplicationEditorUse applicationEditorUse,
            ApplicationEditor applicationEditor, Integer preferredHeight, Integer preferredWidth, BasePK createdBy) {
        var partyApplicationEditorUse = PartyApplicationEditorUseFactory.getInstance().create();
        var partyApplicationEditorUseDetail = PartyApplicationEditorUseDetailFactory.getInstance().create(partyApplicationEditorUse,
                party, applicationEditorUse, applicationEditor, preferredHeight, preferredWidth, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        partyApplicationEditorUse = PartyApplicationEditorUseFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, partyApplicationEditorUse.getPrimaryKey());
        partyApplicationEditorUse.setActiveDetail(partyApplicationEditorUseDetail);
        partyApplicationEditorUse.setLastDetail(partyApplicationEditorUseDetail);
        partyApplicationEditorUse.store();

        sendEvent(partyApplicationEditorUse.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return partyApplicationEditorUse;
    }

    private static final Map<EntityPermission, String> getPartyApplicationEditorUseQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM partyapplicationeditoruses, partyapplicationeditorusedetails "
                        + "WHERE parappledtruse_activedetailid = parappledtrusedt_partyapplicationeditorusedetailid "
                        + "AND parappledtrusedt_par_partyid = ? AND parappledtrusedt_appledtruse_applicationeditoruseid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM partyapplicationeditoruses, partyapplicationeditorusedetails "
                        + "WHERE parappledtruse_activedetailid = parappledtrusedt_partyapplicationeditorusedetailid "
                        + "AND parappledtrusedt_par_partyid = ? AND parappledtrusedt_appledtruse_applicationeditoruseid = ? "
                        + "FOR UPDATE");
        getPartyApplicationEditorUseQueries = Collections.unmodifiableMap(queryMap);
    }

    private PartyApplicationEditorUse getPartyApplicationEditorUse(Party party, ApplicationEditorUse applicationEditorUse, EntityPermission entityPermission) {
        return PartyApplicationEditorUseFactory.getInstance().getEntityFromQuery(entityPermission, getPartyApplicationEditorUseQueries,
                party, applicationEditorUse);
    }

    public PartyApplicationEditorUse getPartyApplicationEditorUse(Party party, ApplicationEditorUse applicationEditorUse) {
        return getPartyApplicationEditorUse(party, applicationEditorUse, EntityPermission.READ_ONLY);
    }

    public PartyApplicationEditorUse getPartyApplicationEditorUseForUpdate(Party party, ApplicationEditorUse applicationEditorUse) {
        return getPartyApplicationEditorUse(party, applicationEditorUse, EntityPermission.READ_WRITE);
    }

    public PartyApplicationEditorUseDetailValue getPartyApplicationEditorUseDetailValueForUpdate(PartyApplicationEditorUse partyApplicationEditorUse) {
        return partyApplicationEditorUse == null? null: partyApplicationEditorUse.getLastDetailForUpdate().getPartyApplicationEditorUseDetailValue().clone();
    }

    public PartyApplicationEditorUseDetailValue getPartyApplicationEditorUseDetailValueForUpdate(Party party, ApplicationEditorUse applicationEditorUse) {
        return getPartyApplicationEditorUseDetailValueForUpdate(getPartyApplicationEditorUseForUpdate(party, applicationEditorUse));
    }

    private static final Map<EntityPermission, String> getPartyApplicationEditorUsesByPartyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM partyapplicationeditoruses, partyapplicationeditorusedetails, applicationeditoruses, applicationeditorusedetails "
                        + "WHERE parappledtruse_activedetailid = parappledtrusedt_partyapplicationeditorusedetailid AND parappledtrusedt_par_partyid = ? "
                        + "AND parappledtrusedt_appledtruse_applicationeditoruseid = appledtruse_applicationeditoruseid AND appledtruse_lastdetailid = appledtrusedt_applicationeditorusedetailid "
                        + "ORDER BY appledtrusedt_sortorder, appledtrusedt_applicationeditorusename "
                        + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM partyapplicationeditoruses, partyapplicationeditorusedetails "
                        + "WHERE parappledtruse_activedetailid = parappledtrusedt_partyapplicationeditorusedetailid AND parappledtrusedt_par_partyid = ? "
                        + "FOR UPDATE");
        getPartyApplicationEditorUsesByPartyQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyApplicationEditorUse> getPartyApplicationEditorUsesByParty(Party party, EntityPermission entityPermission) {
        return PartyApplicationEditorUseFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyApplicationEditorUsesByPartyQueries,
                party);
    }

    public List<PartyApplicationEditorUse> getPartyApplicationEditorUsesByParty(Party party) {
        return getPartyApplicationEditorUsesByParty(party, EntityPermission.READ_ONLY);
    }

    public List<PartyApplicationEditorUse> getPartyApplicationEditorUsesByPartyForUpdate(Party party) {
        return getPartyApplicationEditorUsesByParty(party, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPartyApplicationEditorUsesByApplicationEditorUseQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM partyapplicationeditoruses, partyapplicationeditorusedetails, parties, partydetails "
                        + "WHERE parappledtruse_activedetailid = parappledtrusedt_partyapplicationeditorusedetailid AND parappledtrusedt_appledtruse_applicationeditoruseid = ? "
                        + "AND parappledtrusedt_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid "
                        + "ORDER BY pardt_partyname "
                        + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM partyapplicationeditoruses, partyapplicationeditorusedetails, parties, partydetails "
                        + "WHERE parappledtruse_activedetailid = parappledtrusedt_partyapplicationeditorusedetailid AND parappledtrusedt_appledtruse_applicationeditoruseid = ? "
                        + "FOR UPDATE");
        getPartyApplicationEditorUsesByApplicationEditorUseQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyApplicationEditorUse> getPartyApplicationEditorUsesByApplicationEditorUse(ApplicationEditorUse applicationEditorUse,
            EntityPermission entityPermission) {
        return PartyApplicationEditorUseFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyApplicationEditorUsesByApplicationEditorUseQueries,
                applicationEditorUse);
    }

    public List<PartyApplicationEditorUse> getPartyApplicationEditorUsesByApplicationEditorUse(ApplicationEditorUse applicationEditorUse) {
        return getPartyApplicationEditorUsesByApplicationEditorUse(applicationEditorUse, EntityPermission.READ_ONLY);
    }

    public List<PartyApplicationEditorUse> getPartyApplicationEditorUsesByApplicationEditorUseForUpdate(ApplicationEditorUse applicationEditorUse) {
        return getPartyApplicationEditorUsesByApplicationEditorUse(applicationEditorUse, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPartyApplicationEditorUsesByApplicationEditorQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM partyapplicationeditoruses, partyapplicationeditorusedetails, parties, partydetails, applicationeditoruses, applicationeditorusedetails "
                        + "WHERE parappledtruse_activedetailid = parappledtrusedt_partyapplicationeditorusedetailid AND parappledtrusedt_appledtr_applicationeditorid = ? "
                        + "AND parappledtrusedt_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid "
                        + "AND parappledtrusedt_appledtruse_applicationeditoruseid = appledtruse_applicationeditoruseid AND appledtruse_lastdetailid = appledtrusedt_applicationeditorusedetailid "
                        + "ORDER BY pardt_partyname, appledtrusedt_sortorder, appledtrusedt_applicationeditorusename "
                        + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM partyapplicationeditoruses, partyapplicationeditorusedetails "
                        + "WHERE parappledtruse_activedetailid = parappledtrusedt_partyapplicationeditorusedetailid AND parappledtrusedt_appledtr_applicationeditorid = ? "
                        + "FOR UPDATE");
        getPartyApplicationEditorUsesByApplicationEditorQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyApplicationEditorUse> getPartyApplicationEditorUsesByApplicationEditor(ApplicationEditor applicationEditor,
            EntityPermission entityPermission) {
        return PartyApplicationEditorUseFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyApplicationEditorUsesByApplicationEditorQueries,
                applicationEditor);
    }

    public List<PartyApplicationEditorUse> getPartyApplicationEditorUsesByApplicationEditor(ApplicationEditor applicationEditor) {
        return getPartyApplicationEditorUsesByApplicationEditor(applicationEditor, EntityPermission.READ_ONLY);
    }

    public List<PartyApplicationEditorUse> getPartyApplicationEditorUsesByApplicationEditorForUpdate(ApplicationEditor applicationEditor) {
        return getPartyApplicationEditorUsesByApplicationEditor(applicationEditor, EntityPermission.READ_WRITE);
    }

    public PartyApplicationEditorUseTransfer getPartyApplicationEditorUseTransfer(UserVisit userVisit, PartyApplicationEditorUse partyApplicationEditorUse) {
        return getPartyTransferCaches(userVisit).getPartyApplicationEditorUseTransferCache().getPartyApplicationEditorUseTransfer(partyApplicationEditorUse);
    }

    public List<PartyApplicationEditorUseTransfer> getPartyApplicationEditorUseTransfers(List<PartyApplicationEditorUse> partyApplicationEditorUses, UserVisit userVisit) {
        List<PartyApplicationEditorUseTransfer> partyApplicationEditorUseTransfers = new ArrayList<>(partyApplicationEditorUses.size());
        var partyApplicationEditorUseTransferCache = getPartyTransferCaches(userVisit).getPartyApplicationEditorUseTransferCache();

        partyApplicationEditorUses.forEach((partyApplicationEditorUse) ->
                partyApplicationEditorUseTransfers.add(partyApplicationEditorUseTransferCache.getPartyApplicationEditorUseTransfer(partyApplicationEditorUse))
        );

        return partyApplicationEditorUseTransfers;
    }

    public List<PartyApplicationEditorUseTransfer> getPartyApplicationEditorUseTransfersByParty(UserVisit userVisit, Party party) {
        return getPartyApplicationEditorUseTransfers(getPartyApplicationEditorUsesByParty(party), userVisit);
    }

    public List<PartyApplicationEditorUseTransfer> getPartyApplicationEditorUseTransfersByApplicationEditorUse(UserVisit userVisit, ApplicationEditorUse applicationEditorUse) {
        return getPartyApplicationEditorUseTransfers(getPartyApplicationEditorUsesByApplicationEditorUse(applicationEditorUse), userVisit);
    }

    public List<PartyApplicationEditorUseTransfer> getPartyApplicationEditorUseTransfersByApplicationEditor(UserVisit userVisit, ApplicationEditor applicationEditor) {
        return getPartyApplicationEditorUseTransfers(getPartyApplicationEditorUsesByApplicationEditor(applicationEditor), userVisit);
    }

    public void updatePartyApplicationEditorUseFromValue(PartyApplicationEditorUseDetailValue partyApplicationEditorUseDetailValue, BasePK updatedBy) {
        if(partyApplicationEditorUseDetailValue.hasBeenModified()) {
            var partyApplicationEditorUse = PartyApplicationEditorUseFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    partyApplicationEditorUseDetailValue.getPartyApplicationEditorUsePK());
            var partyApplicationEditorUseDetail = partyApplicationEditorUse.getActiveDetailForUpdate();

            partyApplicationEditorUseDetail.setThruTime(session.START_TIME_LONG);
            partyApplicationEditorUseDetail.store();

            var partyApplicationEditorUsePK = partyApplicationEditorUseDetail.getPartyApplicationEditorUsePK(); // Not updated
            var partyPK = partyApplicationEditorUseDetail.getPartyPK(); // Not updated
            var applicationEditorUsePK = partyApplicationEditorUseDetail.getApplicationEditorUsePK(); // Not updated
            var applicationEditorPK = partyApplicationEditorUseDetailValue.getApplicationEditorPK();
            var preferredHeight = partyApplicationEditorUseDetailValue.getPreferredHeight();
            var preferredWidth = partyApplicationEditorUseDetailValue.getPreferredWidth();

            partyApplicationEditorUseDetail = PartyApplicationEditorUseDetailFactory.getInstance().create(partyApplicationEditorUsePK, partyPK,
                    applicationEditorUsePK, applicationEditorPK, preferredHeight, preferredWidth, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            partyApplicationEditorUse.setActiveDetail(partyApplicationEditorUseDetail);
            partyApplicationEditorUse.setLastDetail(partyApplicationEditorUseDetail);

            sendEvent(partyApplicationEditorUsePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void deletePartyApplicationEditorUse(PartyApplicationEditorUse partyApplicationEditorUse, BasePK deletedBy) {
        var partyApplicationEditorUseDetail = partyApplicationEditorUse.getLastDetailForUpdate();

        partyApplicationEditorUseDetail.setThruTime(session.START_TIME_LONG);
        partyApplicationEditorUse.setActiveDetail(null);
        partyApplicationEditorUse.store();

        sendEvent(partyApplicationEditorUse.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deletePartyApplicationEditorUses(List<PartyApplicationEditorUse> partyApplicationEditorUses, BasePK deletedBy) {
        partyApplicationEditorUses.forEach((partyApplicationEditorUse) ->
                deletePartyApplicationEditorUse(partyApplicationEditorUse, deletedBy)
        );
    }

    public void deletePartyApplicationEditorUsesByParty(Party party, BasePK deletedBy) {
        deletePartyApplicationEditorUses(getPartyApplicationEditorUsesByPartyForUpdate(party), deletedBy);
    }

    public void deletePartyApplicationEditorUsesByParty(ApplicationEditorUse applicationEditorUse, BasePK deletedBy) {
        deletePartyApplicationEditorUses(getPartyApplicationEditorUsesByApplicationEditorUseForUpdate(applicationEditorUse), deletedBy);
    }

    public void deletePartyApplicationEditorUsesByApplicationEditor(ApplicationEditor applicationEditor, BasePK deletedBy) {
        deletePartyApplicationEditorUses(getPartyApplicationEditorUsesByApplicationEditorForUpdate(applicationEditor), deletedBy);
    }

}
