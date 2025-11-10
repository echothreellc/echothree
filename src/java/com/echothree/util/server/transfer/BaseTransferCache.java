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

package com.echothree.util.server.transfer;

import com.echothree.model.control.comment.common.transfer.CommentListWrapper;
import com.echothree.model.control.comment.server.control.CommentControl;
import com.echothree.model.control.core.common.transfer.EntityAliasTypeTransfer;
import com.echothree.model.control.core.common.transfer.EntityAttributeGroupTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.core.server.control.EntityAliasControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.rating.common.transfer.RatingListWrapper;
import com.echothree.model.control.rating.server.control.RatingControl;
import com.echothree.model.control.tag.common.transfer.TagScopeTransfer;
import com.echothree.model.control.tag.server.control.TagControl;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.workeffort.server.control.WorkEffortControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.TransferOptionDependencyException;
import com.echothree.util.common.string.Inet4AddressUtils;
import com.echothree.util.common.transfer.BaseOptions;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.common.transfer.MapWrapper;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.ThreadSession;
import com.echothree.util.server.string.DateUtils;
import com.echothree.util.server.string.PercentUtils;
import com.echothree.util.server.string.UnitOfMeasureUtils;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class BaseTransferCache<K extends BaseEntity, V extends BaseTransfer> {
    
    private Log log = null;
    
    protected UserVisit userVisit;
    protected Session session;
    protected Map<K, V> transferCache;
    
    private UomControl uomControl;
    private UserControl userControl;
    private Party party;
    private Language language;
    private Currency currency;
    private TimeZone timeZone;
    private DateTimeFormat dateTimeFormat;

    boolean includeEntityInstance;
    boolean includeEntityAppearance;
    boolean includeEntityVisit;
    boolean includeNames;
    boolean includeUuid;
    boolean includeEntityAliasTypes;
    boolean includeEntityAttributeGroups;
    boolean includeTagScopes;
    
    /** Creates a new instance of BaseTransferCache */
    protected BaseTransferCache(UserVisit userVisit) {
        this.userVisit = userVisit;
        
        session = ThreadSession.currentSession();
        transferCache = new HashMap<>();
        
        var options = session.getOptions();
        if(options != null) {
            includeEntityAliasTypes = options.contains(BaseOptions.BaseIncludeEntityAliasTypes);
            includeEntityAttributeGroups = options.contains(BaseOptions.BaseIncludeEntityAttributeGroups);
            includeTagScopes = options.contains(BaseOptions.BaseIncludeTagScopes);
        }
    }
    
    protected Log getLog() {
        if(log == null) {
            log = LogFactory.getLog(this.getClass());
        }
        
        return log;
    }
    
    protected void put(final UserVisit userVisit, final K key, final V value, final EntityInstance entityInstance) {
        transferCache.put(key, value);

        if(includeEntityInstance) {
            setupEntityInstance(userVisit, key, entityInstance, value);
        }
    }

    protected void put(final UserVisit userVisit, final K key, final V value) {
        put(userVisit, key, value, null);
    }

    protected V get(Object key) {
        return transferCache.get(key);
    }
    
    protected UomControl getUomControl() {
        if(uomControl == null) {
            uomControl = Session.getModelController(UomControl.class);
        }
        
        return uomControl;
    }
    
    protected UserControl getUserControl() {
        if(userControl == null) {
            userControl = Session.getModelController(UserControl.class);
        }
        
        return userControl;
    }

    protected PartyPK getPartyPK(final UserVisit userVisit) {
        if(party == null) {
            getParty(userVisit);
        }

        return party == null? null: party.getPrimaryKey();
    }

    protected Party getParty(final UserVisit userVisit) {
        if(party == null) {
            party = getUserControl().getPartyFromUserVisit(userVisit);
        }

        return party;
    }

    protected Language getLanguage(final UserVisit userVisit) {
        if(language == null) {
            language = getUserControl().getPreferredLanguageFromUserVisit(userVisit);
        }
        
        return language;
    }
    
    protected Currency getCurrency(final UserVisit userVisit) {
        if(currency == null) {
            currency = getUserControl().getPreferredCurrencyFromUserVisit(userVisit);
        }
        
        return currency;
    }
    
    protected TimeZone getTimeZone(final UserVisit userVisit) {
        if(timeZone == null) {
            timeZone = getUserControl().getPreferredTimeZoneFromUserVisit(userVisit);
        }
        
        return timeZone;
    }
    
    protected DateTimeFormat getDateTimeFormat(final UserVisit userVisit) {
        if(dateTimeFormat == null) {
            dateTimeFormat = getUserControl().getPreferredDateTimeFormatFromUserVisit(userVisit);
        }
        
        return dateTimeFormat;
    }

    protected String formatTypicalDateTime(final UserVisit userVisit, Long time) {
        return DateUtils.getInstance().formatTypicalDateTime(userVisit, time);
    }
    
    protected String formatFractionalPercent(Integer percent) {
        return PercentUtils.getInstance().formatFractionalPercent(percent);
    }
    
    protected String formatInet4Address(Integer inet4Address) {
        return Inet4AddressUtils.getInstance().formatInet4Address(inet4Address);
    }
    
    protected String formatUnitOfMeasure(final UserVisit userVisit, UnitOfMeasureKind unitOfMeasureKind, Long measure) {
        return UnitOfMeasureUtils.getInstance().formatUnitOfMeasure(userVisit, unitOfMeasureKind, measure);
    }

    /**
     * Returns the includeEntityAliasTypes.
     * @return the includeEntityAliasTypes
     */
    protected boolean getIncludeEntityAliasTypes() {
        return includeEntityAliasTypes;
    }

    /**
     * Sets the includeEntityAliasTypes.
     * @param includeEntityAliasTypes the includeEntityAliasTypes to set
     */
    protected void setIncludeEntityAliasTypes(boolean includeEntityAliasTypes) {
        this.includeEntityAliasTypes = includeEntityAliasTypes;
    }

    protected void setupEntityAliasTypes(final UserVisit userVisit, EntityInstance entityInstance, V transfer) {
        var entityAliasControl = Session.getModelController(EntityAliasControl.class);
        var entityAliasTypeTransfers = entityAliasControl.getEntityAliasTypeTransfersByEntityType(userVisit, entityInstance.getEntityType(), entityInstance);
        var mapWrapper = new MapWrapper<EntityAliasTypeTransfer>(entityAliasTypeTransfers.size());

        entityAliasTypeTransfers.forEach((entityAliasTypeTransfer) -> {
            mapWrapper.put(entityAliasTypeTransfer.getEntityAliasTypeName(), entityAliasTypeTransfer);
        });

        transfer.setEntityAliasTypes(mapWrapper);
    }

    /**
     * Returns the includeEntityAttributeGroups.
     * @return the includeEntityAttributeGroups
     */
    protected boolean getIncludeEntityAttributeGroups() {
        return includeEntityAttributeGroups;
    }

    /**
     * Sets the includeEntityAttributeGroups.
     * @param includeEntityAttributeGroups the includeEntityAttributeGroups to set
     */
    protected void setIncludeEntityAttributeGroups(boolean includeEntityAttributeGroups) {
        this.includeEntityAttributeGroups = includeEntityAttributeGroups;
    }

    protected void setupEntityAttributeGroups(final UserVisit userVisit, EntityInstance entityInstance, V transfer) {
        var coreControl = Session.getModelController(CoreControl.class);
        var entityAttributeGroupTransfers = coreControl.getEntityAttributeGroupTransfersByEntityType(userVisit, entityInstance.getEntityType(), entityInstance);
        var mapWrapper = new MapWrapper<EntityAttributeGroupTransfer>(entityAttributeGroupTransfers.size());

        entityAttributeGroupTransfers.forEach((entityAttributeGroupTransfer) -> {
            mapWrapper.put(entityAttributeGroupTransfer.getEntityAttributeGroupName(), entityAttributeGroupTransfer);
        });

        transfer.setEntityAttributeGroups(mapWrapper);
    }

    /**
     * Returns the includeTagScopes.
     * @return the includeTagScopes
     */
    protected boolean getIncludeTagScopes() {
        return includeTagScopes;
    }

    /**
     * Sets the includeTagScopes.
     * @param includeTagScopes the includeTagScopes to set
     */
    protected void setIncludeTagScopes(boolean includeTagScopes) {
        this.includeTagScopes = includeTagScopes;
    }

    protected void setupTagScopes(final UserVisit userVisit, EntityInstance entityInstance, V transfer) {
        var tagControl = Session.getModelController(TagControl.class);
        var tagScopes = tagControl.getTagScopesByEntityType(entityInstance.getEntityType());
        var mapWrapper = new MapWrapper<TagScopeTransfer>(tagScopes.size());

        tagScopes.stream().map((tagScope) -> {
            // We get a copy of the TagScopeTransfer since we'll be modifying a field on it. Because there may be multiple instances of the
            // TransferObject that we're building at this point, they may each have their own List of Tags within a given TagScope, so we do
            // not want the tags property to be shared among all of them.
            var tagScopeTransfer = tagControl.getTagScopeTransfer(userVisit, tagScope).copy();
            tagScopeTransfer.setTags(new ListWrapper<>(tagControl.getTagTransfersByTagScopeAndEntityInstance(userVisit, tagScope, entityInstance)));
            return tagScopeTransfer;
        }).forEach((tagScopeTransfer) -> {
            mapWrapper.put(tagScopeTransfer.getTagScopeName(), tagScopeTransfer);
        });

        transfer.setTagScopes(mapWrapper);
    }


    /**
     * Returns the includeEntityInstance.
     * @return the includeEntityInstance
     */
    protected boolean getIncludeEntityInstance() {
        return includeEntityInstance;
    }

    /**
     * Sets the setupBaseTransfer.
     * @param includeEntityInstance the setupBaseTransfer to set
     */
    protected void setIncludeEntityInstance(boolean includeEntityInstance) {
        this.includeEntityInstance = includeEntityInstance;
    }

    /**
     * Returns the includeEntityAppearance.
     * @return the includeEntityAppearance
     */
    protected boolean getIncludeEntityAppearance() {
        return includeEntityAppearance;
    }

    /**
     * Sets the includeEntityAppearance.
     * @param includeEntityAppearance the includeEntityAppearance to set
     */
    protected void setIncludeEntityAppearance(boolean includeEntityAppearance) {
        this.includeEntityAppearance = includeEntityAppearance;
    }

    /**
     * Returns the includeEntityVisit.
     * @return the includeEntityVisit
     */
    protected boolean getIncludeEntityVisit() {
        return includeEntityVisit;
    }

    /**
     * Sets the includeEntityVisit.
     * @param includeEntityVisit the includeEntityVisit to set
     */
    protected void setIncludeEntityVisit(boolean includeEntityVisit) {
        this.includeEntityVisit = includeEntityVisit;
    }

    /**
     * Returns the includeNames.
     * @return the includeNames
     */
    protected boolean getIncludeNames() {
        return includeNames;
    }

    /**
     * Sets the includeNames.
     * @param includeNames the includeNames to set
     */
    protected void setIncludeNames(boolean includeNames) {
        this.includeNames = includeNames;
    }

    /**
     * Returns the includeUuid.
     * @return the includeUuid
     */
    protected boolean getIncludeUuid() {
        return includeUuid;
    }

    /**
     * Sets the includeUuid.
     * @param includeUuid the includeUuid to set
     */
    protected void setIncludeUuid(boolean includeUuid) {
        this.includeUuid = includeUuid;
    }

    protected void setupEntityInstance(final UserVisit userVisit, final K baseEntity, EntityInstance entityInstance, final V transfer) {
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);

        if(entityInstance == null) {
            entityInstance = entityInstanceControl.getEntityInstanceByBasePK(baseEntity.getPrimaryKey());
        }

        // Check to make sure entityInstance is not null. This may happen in a case where a non-versioned entity was
        // converted to a versioned one.
        if(entityInstance != null) {
            transfer.setEntityInstance(entityInstanceControl.getEntityInstanceTransfer(userVisit, entityInstance, includeEntityAppearance,
                    includeEntityVisit, includeNames, includeUuid));

            if(includeEntityAliasTypes || includeEntityAttributeGroups || includeTagScopes) {
                if(includeEntityAliasTypes) {
                    setupEntityAliasTypes(userVisit, entityInstance, transfer);
                }

                if(includeEntityAttributeGroups) {
                    setupEntityAttributeGroups(userVisit, entityInstance, transfer);
                }

                if(includeTagScopes) {
                    setupTagScopes(userVisit, entityInstance, transfer);
                }
            }
        }
    }

    protected EntityInstance setupComments(final UserVisit userVisit, final K commentedEntity, EntityInstance commentedEntityInstance, final V transfer, final String commentTypeName) {
        var commentControl = Session.getModelController(CommentControl.class);
        
        if(commentedEntityInstance == null) {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            
            commentedEntityInstance = entityInstanceControl.getEntityInstanceByBasePK(commentedEntity.getPrimaryKey());
        }

        var commentType = commentControl.getCommentTypeByName(commentedEntityInstance.getEntityType(), commentTypeName);
        transfer.addComments(commentTypeName, new CommentListWrapper(commentControl.getCommentTypeTransfer(userVisit, commentType),
                commentControl.getCommentTransfersByCommentedEntityInstanceAndCommentType(userVisit, commentedEntityInstance, commentType)));
        
        return commentedEntityInstance;
    }

    protected EntityInstance setupRatings(final UserVisit userVisit, final K ratedEntity, EntityInstance ratedEntityInstance, final V transfer, final String ratingTypeName) {
        var ratingControl = Session.getModelController(RatingControl.class);
        
        if(ratedEntityInstance == null) {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            
            ratedEntityInstance = entityInstanceControl.getEntityInstanceByBasePK(ratedEntity.getPrimaryKey());
        }

        var ratingType = ratingControl.getRatingTypeByName(ratedEntityInstance.getEntityType(), ratingTypeName);
        transfer.addRatings(ratingTypeName, new RatingListWrapper(ratingControl.getRatingTypeTransfer(userVisit, ratingType),
                ratingControl.getRatingTransfersByRatedEntityInstanceAndRatingType(userVisit, ratedEntityInstance, ratingType)));

        return ratedEntityInstance;
    }

    protected EntityInstance setupOwnedWorkEfforts(final UserVisit userVisit, final K baseEntity, EntityInstance owningEntityInstance, final V transfer) {
        var workEffortControl = Session.getModelController(WorkEffortControl.class);
        
        if(owningEntityInstance == null) {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            
            owningEntityInstance = entityInstanceControl.getEntityInstanceByBasePK(baseEntity.getPrimaryKey());
        }
        
        for(var workEffort: workEffortControl.getWorkEffortTransfersByOwningEntityInstance(userVisit, owningEntityInstance)) {
            transfer.addOwnedWorkEffort(workEffort.getWorkEffortScope().getWorkEffortType().getWorkEffortTypeName(), workEffort);
        }

        return owningEntityInstance;
    }
    
    protected void verifyOptionDependency(String dependentOption, String dependsOnOption) {
        var options = session.getOptions();
        
        if(!options.contains(dependsOnOption)) {
            // Throwing an Exception for this seems harsh, but failure to meet the requirements could result in an NPE or other Exceptions.
            throw new TransferOptionDependencyException(dependentOption + " requires that " + dependsOnOption + " be set as well");
        }
    }
    
}
