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

package com.echothree.model.control.party.server.control;


import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.party.common.transfer.PartyApplicationEditorUseTransfer;
import com.echothree.model.control.party.server.transfer.PartyApplicationEditorUseTransferCache;
import com.echothree.model.data.core.server.entity.ApplicationEditor;
import com.echothree.model.data.core.server.entity.ApplicationEditorUse;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.common.pk.PartyApplicationEditorUsePK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyApplicationEditorUse;
import com.echothree.model.data.party.server.factory.PartyApplicationEditorUseDetailFactory;
import com.echothree.model.data.party.server.factory.PartyApplicationEditorUseFactory;
import com.echothree.model.data.party.server.value.PartyApplicationEditorUseDetailValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.APPLICATION_EDITOR_USES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.PARTIES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.PARTY_APPLICATION_EDITOR_USES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.PARTY_APPLICATION_EDITOR_USE_DETAILS_APPLICATION_EDITOR_USE_FK;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.PARTY_APPLICATION_EDITOR_USE_DETAILS_PARTY_FK;
import static com.echothree.model.jooq.server.tables.core.ApplicationEditorUseDetails.ApplicationEditorUseDetails;
import static com.echothree.model.jooq.server.tables.core.ApplicationEditorUses.ApplicationEditorUses;
import static com.echothree.model.jooq.server.tables.party.Parties.Parties;
import static com.echothree.model.jooq.server.tables.party.PartyApplicationEditorUseDetails.PartyApplicationEditorUseDetails;
import static com.echothree.model.jooq.server.tables.party.PartyApplicationEditorUses.PartyApplicationEditorUses;
import static com.echothree.model.jooq.server.tables.party.PartyDetails.PartyDetails;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

@CommandScope
public class PartyApplicationEditorUseControl
        extends BaseModelControl {

    /** Creates a new instance of PartyApplicationEditorUseControl */
    protected PartyApplicationEditorUseControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Party Application Editor Uses
    // --------------------------------------------------------------------------------

    @Inject
    protected PartyApplicationEditorUseDetailFactory partyApplicationEditorUseDetailFactory;

    @Inject
    protected PartyApplicationEditorUseFactory partyApplicationEditorUseFactory;

    @Inject
    PartyApplicationEditorUseTransferCache partyApplicationEditorUseTransferCache;

    public PartyApplicationEditorUse createPartyApplicationEditorUse(Party party, ApplicationEditorUse applicationEditorUse,
            ApplicationEditor applicationEditor, Integer preferredHeight, Integer preferredWidth, BasePK createdBy) {
        var partyApplicationEditorUse = partyApplicationEditorUseFactory.create();
        var partyApplicationEditorUseDetail = partyApplicationEditorUseDetailFactory.create(partyApplicationEditorUse,
                party, applicationEditorUse, applicationEditor, preferredHeight, preferredWidth, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        partyApplicationEditorUse = partyApplicationEditorUseFactory.getEntityFromPK(EntityPermission.READ_WRITE, partyApplicationEditorUse.getPrimaryKey());
        partyApplicationEditorUse.setActiveDetail(partyApplicationEditorUseDetail);
        partyApplicationEditorUse.setLastDetail(partyApplicationEditorUseDetail);
        partyApplicationEditorUse.store();

        sendEvent(partyApplicationEditorUse.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return partyApplicationEditorUse;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.PartyApplicationEditorUse */
    public PartyApplicationEditorUse getPartyApplicationEditorUseByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new PartyApplicationEditorUsePK(entityInstance.getEntityUniqueId());

        return partyApplicationEditorUseFactory.getEntityFromPK(entityPermission, pk);
    }

    public PartyApplicationEditorUse getPartyApplicationEditorUseByEntityInstance(EntityInstance entityInstance) {
        return getPartyApplicationEditorUseByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PartyApplicationEditorUse getPartyApplicationEditorUseByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getPartyApplicationEditorUseByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countPartyApplicationEditorUsesByParty(final Party party) {
        return session.getDslContext()
                .selectCount()
                .from(PartyApplicationEditorUses)
                .join(PartyApplicationEditorUseDetails).onKey(PARTY_APPLICATION_EDITOR_USES_ACTIVE_DETAIL_FK)
                .where(PartyApplicationEditorUseDetails.PARTY.eq(party.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyApplicationEditorUsesByApplicationEditorUse(final ApplicationEditorUse applicationEditorUse) {
        return session.getDslContext()
                .selectCount()
                .from(PartyApplicationEditorUses)
                .join(PartyApplicationEditorUseDetails).onKey(PARTY_APPLICATION_EDITOR_USES_ACTIVE_DETAIL_FK)
                .where(PartyApplicationEditorUseDetails.APPLICATION_EDITOR_USE.eq(applicationEditorUse.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyApplicationEditorUsesByApplicationEditor(final ApplicationEditor applicationEditor) {
        return session.getDslContext()
                .selectCount()
                .from(PartyApplicationEditorUses)
                .join(PartyApplicationEditorUseDetails).onKey(PARTY_APPLICATION_EDITOR_USES_ACTIVE_DETAIL_FK)
                .where(PartyApplicationEditorUseDetails.APPLICATION_EDITOR.eq(applicationEditor.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private PartyApplicationEditorUse getPartyApplicationEditorUse(Party party, ApplicationEditorUse applicationEditorUse, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyApplicationEditorUses.fields())
                .from(PartyApplicationEditorUses)
                .join(PartyApplicationEditorUseDetails).onKey(PARTY_APPLICATION_EDITOR_USES_ACTIVE_DETAIL_FK)
                .where(PartyApplicationEditorUseDetails.PARTY.eq(party.getPrimaryKey()),
                        PartyApplicationEditorUseDetails.APPLICATION_EDITOR_USE.eq(applicationEditorUse.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyApplicationEditorUseFactory.getEntityFromQuery(entityPermission, query);
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

    private List<PartyApplicationEditorUse> getPartyApplicationEditorUsesByParty(Party party, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyApplicationEditorUses.fields())
                    .from(PartyApplicationEditorUses)
                    .join(PartyApplicationEditorUseDetails).onKey(PARTY_APPLICATION_EDITOR_USES_ACTIVE_DETAIL_FK)
                    .join(ApplicationEditorUses).onKey(PARTY_APPLICATION_EDITOR_USE_DETAILS_APPLICATION_EDITOR_USE_FK)
                    .join(ApplicationEditorUseDetails).onKey(APPLICATION_EDITOR_USES_LAST_DETAIL_FK)
                    .where(PartyApplicationEditorUseDetails.PARTY.eq(party.getPrimaryKey()))
                    .orderBy(ApplicationEditorUseDetails.SORT_ORDER, ApplicationEditorUseDetails.APPLICATION_EDITOR_USE_NAME),
                    PartyApplicationEditorUseFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyApplicationEditorUses.fields())
                    .from(PartyApplicationEditorUses)
                    .join(PartyApplicationEditorUseDetails).onKey(PARTY_APPLICATION_EDITOR_USES_ACTIVE_DETAIL_FK)
                    .where(PartyApplicationEditorUseDetails.PARTY.eq(party.getPrimaryKey()))
                    .forUpdate();
        };

        return partyApplicationEditorUseFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PartyApplicationEditorUse> getPartyApplicationEditorUsesByParty(Party party) {
        return getPartyApplicationEditorUsesByParty(party, EntityPermission.READ_ONLY);
    }

    public List<PartyApplicationEditorUse> getPartyApplicationEditorUsesByPartyForUpdate(Party party) {
        return getPartyApplicationEditorUsesByParty(party, EntityPermission.READ_WRITE);
    }

    private List<PartyApplicationEditorUse> getPartyApplicationEditorUsesByApplicationEditorUse(ApplicationEditorUse applicationEditorUse,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyApplicationEditorUses.fields())
                    .from(PartyApplicationEditorUses)
                    .join(PartyApplicationEditorUseDetails).onKey(PARTY_APPLICATION_EDITOR_USES_ACTIVE_DETAIL_FK)
                    .join(Parties).onKey(PARTY_APPLICATION_EDITOR_USE_DETAILS_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .where(PartyApplicationEditorUseDetails.APPLICATION_EDITOR_USE.eq(applicationEditorUse.getPrimaryKey()))
                    .orderBy(PartyDetails.PARTY_NAME),
                    PartyApplicationEditorUseFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyApplicationEditorUses.fields())
                    .from(PartyApplicationEditorUses)
                    .join(PartyApplicationEditorUseDetails).onKey(PARTY_APPLICATION_EDITOR_USES_ACTIVE_DETAIL_FK)
                    .where(PartyApplicationEditorUseDetails.APPLICATION_EDITOR_USE.eq(applicationEditorUse.getPrimaryKey()))
                    .forUpdate();
        };

        return partyApplicationEditorUseFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PartyApplicationEditorUse> getPartyApplicationEditorUsesByApplicationEditorUse(ApplicationEditorUse applicationEditorUse) {
        return getPartyApplicationEditorUsesByApplicationEditorUse(applicationEditorUse, EntityPermission.READ_ONLY);
    }

    public List<PartyApplicationEditorUse> getPartyApplicationEditorUsesByApplicationEditorUseForUpdate(ApplicationEditorUse applicationEditorUse) {
        return getPartyApplicationEditorUsesByApplicationEditorUse(applicationEditorUse, EntityPermission.READ_WRITE);
    }

    private List<PartyApplicationEditorUse> getPartyApplicationEditorUsesByApplicationEditor(ApplicationEditor applicationEditor,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyApplicationEditorUses.fields())
                    .from(PartyApplicationEditorUses)
                    .join(PartyApplicationEditorUseDetails).onKey(PARTY_APPLICATION_EDITOR_USES_ACTIVE_DETAIL_FK)
                    .join(Parties).onKey(PARTY_APPLICATION_EDITOR_USE_DETAILS_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .join(ApplicationEditorUses).onKey(PARTY_APPLICATION_EDITOR_USE_DETAILS_APPLICATION_EDITOR_USE_FK)
                    .join(ApplicationEditorUseDetails).onKey(APPLICATION_EDITOR_USES_LAST_DETAIL_FK)
                    .where(PartyApplicationEditorUseDetails.APPLICATION_EDITOR.eq(applicationEditor.getPrimaryKey()))
                    .orderBy(PartyDetails.PARTY_NAME, ApplicationEditorUseDetails.SORT_ORDER, ApplicationEditorUseDetails.APPLICATION_EDITOR_USE_NAME),
                    PartyApplicationEditorUseFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyApplicationEditorUses.fields())
                    .from(PartyApplicationEditorUses)
                    .join(PartyApplicationEditorUseDetails).onKey(PARTY_APPLICATION_EDITOR_USES_ACTIVE_DETAIL_FK)
                    .where(PartyApplicationEditorUseDetails.APPLICATION_EDITOR.eq(applicationEditor.getPrimaryKey()))
                    .forUpdate();
        };

        return partyApplicationEditorUseFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PartyApplicationEditorUse> getPartyApplicationEditorUsesByApplicationEditor(ApplicationEditor applicationEditor) {
        return getPartyApplicationEditorUsesByApplicationEditor(applicationEditor, EntityPermission.READ_ONLY);
    }

    public List<PartyApplicationEditorUse> getPartyApplicationEditorUsesByApplicationEditorForUpdate(ApplicationEditor applicationEditor) {
        return getPartyApplicationEditorUsesByApplicationEditor(applicationEditor, EntityPermission.READ_WRITE);
    }

    public PartyApplicationEditorUseTransfer getPartyApplicationEditorUseTransfer(UserVisit userVisit, PartyApplicationEditorUse partyApplicationEditorUse) {
        return partyApplicationEditorUseTransferCache.getPartyApplicationEditorUseTransfer(userVisit, partyApplicationEditorUse);
    }

    public List<PartyApplicationEditorUseTransfer> getPartyApplicationEditorUseTransfers(List<PartyApplicationEditorUse> partyApplicationEditorUses, UserVisit userVisit) {
        List<PartyApplicationEditorUseTransfer> partyApplicationEditorUseTransfers = new ArrayList<>(partyApplicationEditorUses.size());

        partyApplicationEditorUses.forEach((partyApplicationEditorUse) ->
                partyApplicationEditorUseTransfers.add(partyApplicationEditorUseTransferCache.getPartyApplicationEditorUseTransfer(userVisit, partyApplicationEditorUse))
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
            var partyApplicationEditorUse = partyApplicationEditorUseFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    partyApplicationEditorUseDetailValue.getPartyApplicationEditorUsePK());
            var partyApplicationEditorUseDetail = partyApplicationEditorUse.getActiveDetailForUpdate();

            partyApplicationEditorUseDetail.setThruTime(session.getStartTime());
            partyApplicationEditorUseDetail.store();

            var partyApplicationEditorUsePK = partyApplicationEditorUseDetail.getPartyApplicationEditorUsePK(); // Not updated
            var partyPK = partyApplicationEditorUseDetail.getPartyPK(); // Not updated
            var applicationEditorUsePK = partyApplicationEditorUseDetail.getApplicationEditorUsePK(); // Not updated
            var applicationEditorPK = partyApplicationEditorUseDetailValue.getApplicationEditorPK();
            var preferredHeight = partyApplicationEditorUseDetailValue.getPreferredHeight();
            var preferredWidth = partyApplicationEditorUseDetailValue.getPreferredWidth();

            partyApplicationEditorUseDetail = partyApplicationEditorUseDetailFactory.create(partyApplicationEditorUsePK, partyPK,
                    applicationEditorUsePK, applicationEditorPK, preferredHeight, preferredWidth, session.getStartTime(), Session.MAX_TIME);

            partyApplicationEditorUse.setActiveDetail(partyApplicationEditorUseDetail);
            partyApplicationEditorUse.setLastDetail(partyApplicationEditorUseDetail);

            sendEvent(partyApplicationEditorUsePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void deletePartyApplicationEditorUse(PartyApplicationEditorUse partyApplicationEditorUse, BasePK deletedBy) {
        var partyApplicationEditorUseDetail = partyApplicationEditorUse.getLastDetailForUpdate();

        partyApplicationEditorUseDetail.setThruTime(session.getStartTime());
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
