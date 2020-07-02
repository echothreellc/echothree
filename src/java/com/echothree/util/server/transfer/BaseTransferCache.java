// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.model.control.comment.server.CommentControl;
import com.echothree.model.control.core.common.transfer.EntityAttributeGroupTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.rating.common.transfer.RatingListWrapper;
import com.echothree.model.control.rating.server.RatingControl;
import com.echothree.model.control.tag.common.transfer.TagScopeTransfer;
import com.echothree.model.control.tag.server.TagControl;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.control.workeffort.common.transfer.WorkEffortTransfer;
import com.echothree.model.control.workeffort.server.WorkEffortControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.comment.server.entity.CommentType;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.rating.server.entity.RatingType;
import com.echothree.model.data.tag.server.entity.TagScope;
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
import com.echothree.util.server.string.PercentUtils;
import com.echothree.util.server.string.UnitOfMeasureUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    
    private java.util.TimeZone javaTimeZone;
    private SimpleDateFormat sdfShortDateFormat;
    private SimpleDateFormat sdfTimeFormatSeconds;

    boolean includeEntityInstance;
    boolean includeEntityAppearance;
    boolean includeNames;
    boolean includeKey;
    boolean includeGuid;
    boolean includeUlid;
    boolean includeEntityAttributeGroups;
    boolean includeTagScopes;
    
    /** Creates a new instance of BaseTransferCache */
    protected BaseTransferCache(UserVisit userVisit) {
        this.userVisit = userVisit;
        
        session = ThreadSession.currentSession();
        transferCache = new HashMap<>();
        
        Set<String> options = session.getOptions();
        if(options != null) {
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
    
    protected void put(final K key, final V value, final EntityInstance entityInstance) {
        transferCache.put(key, value);

        if(includeEntityInstance) {
            setupEntityInstance(key, entityInstance, value);
        }
    }

    protected void put(final K key, final V value) {
        put(key, value, null);
    }

    protected V get(Object key) {
        return transferCache.get(key);
    }
    
    protected UomControl getUomControl() {
        if(uomControl == null) {
            uomControl = (UomControl)Session.getModelController(UomControl.class);
        }
        
        return uomControl;
    }
    
    protected UserControl getUserControl() {
        if(userControl == null) {
            userControl = (UserControl)Session.getModelController(UserControl.class);
        }
        
        return userControl;
    }

    protected PartyPK getPartyPK() {
        if(party == null) {
            getParty();
        }

        return party == null? null: party.getPrimaryKey();
    }

    protected Party getParty() {
        if(party == null) {
            party = getUserControl().getPartyFromUserVisit(userVisit);
        }

        return party;
    }

    protected Language getLanguage() {
        if(language == null) {
            language = getUserControl().getPreferredLanguageFromUserVisit(userVisit);
        }
        
        return language;
    }
    
    protected Currency getCurrency() {
        if(currency == null) {
            currency = getUserControl().getPreferredCurrencyFromUserVisit(userVisit);
        }
        
        return currency;
    }
    
    protected TimeZone getTimeZone() {
        if(timeZone == null) {
            timeZone = getUserControl().getPreferredTimeZoneFromUserVisit(userVisit);
        }
        
        return timeZone;
    }
    
    protected java.util.TimeZone getJavaTimeZone() {
        if(javaTimeZone == null) {
            javaTimeZone = java.util.TimeZone.getTimeZone(getTimeZone().getLastDetail().getJavaTimeZoneName());
        }
        
        return javaTimeZone;
    }
    
    protected DateTimeFormat getDateTimeFormat() {
        if(dateTimeFormat == null) {
            dateTimeFormat = getUserControl().getPreferredDateTimeFormatFromUserVisit(userVisit);
        }
        
        return dateTimeFormat;
    }
    
    protected String formatDateUsingShortDateFormat(Date time) {
        if(sdfShortDateFormat == null) {
            sdfShortDateFormat = new SimpleDateFormat(getDateTimeFormat().getLastDetail().getJavaShortDateFormat());
            sdfShortDateFormat.setTimeZone(getJavaTimeZone());
        }
        
        return sdfShortDateFormat.format(time);
    }
    
    protected String formatTimeUsingTimeFormatSeconds(Date time) {
        if(sdfTimeFormatSeconds == null) {
            sdfTimeFormatSeconds = new SimpleDateFormat(getDateTimeFormat().getLastDetail().getJavaTimeFormatSeconds());
            sdfTimeFormatSeconds.setTimeZone(getJavaTimeZone());
        }
        
        return sdfTimeFormatSeconds.format(time);
    }
    
    protected String formatTypicalDateTime(Date time) {
        return new StringBuilder(formatDateUsingShortDateFormat(time)).append(' ').append(formatTimeUsingTimeFormatSeconds(time)).toString();
    }
    
    protected String formatTypicalDateTime(Long time) {
        return time == null? null: formatTypicalDateTime(new Date(time));
    }
    
    protected String formatFractionalPercent(Integer percent) {
        return PercentUtils.getInstance().formatFractionalPercent(percent);
    }
    
    protected String formatInet4Address(Integer inet4Address) {
        return Inet4AddressUtils.getInstance().formatInet4Address(inet4Address);
    }
    
    protected String formatUnitOfMeasure(UnitOfMeasureKind unitOfMeasureKind, Long measure) {
        return UnitOfMeasureUtils.getInstance().formatUnitOfMeasure(userVisit, unitOfMeasureKind, measure);
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

    protected void setupEntityAttributeGroups(CoreControl coreControl, EntityInstance entityInstance, V transfer) {
        List<EntityAttributeGroupTransfer> entityAttributeGroupTransfers = coreControl.getEntityAttributeGroupTransfersByEntityType(userVisit, entityInstance.getEntityType(), entityInstance);
        MapWrapper<EntityAttributeGroupTransfer> mapWrapper = new MapWrapper<>(entityAttributeGroupTransfers.size());

        entityAttributeGroupTransfers.stream().forEach((entityAttributeGroupTransfer) -> {
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

    protected void setupTagScopes(CoreControl coreControl, EntityInstance entityInstance, V transfer) {
        TagControl tagControl = (TagControl)Session.getModelController(TagControl.class);
        List<TagScope> tagScopes = tagControl.getTagScopesByEntityType(entityInstance.getEntityType());
        MapWrapper<TagScopeTransfer> mapWrapper = new MapWrapper<>(tagScopes.size());

        tagScopes.stream().map((tagScope) -> {
            // We get a copy of the TagScopeTransfer since we'll be modifying a field on it. Because there may be multiple instances of the
            // TransferObject that we're building at this point, they may each have their own List of Tags within a given TagScope, so we do
            // not want the tags property to be shared among all of them.
            TagScopeTransfer tagScopeTransfer = tagControl.getTagScopeTransfer(userVisit, tagScope).copy();
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
     * Returns the includeKey.
     * @return the includeKey
     */
    protected boolean getIncludeKey() {
        return includeKey;
    }

    /**
     * Sets the includeKey.
     * @param includeKey the includeKey to set
     */
    protected void setIncludeKey(boolean includeKey) {
        this.includeKey = includeKey;
    }

    /**
     * Returns the includeGuid.
     * @return the includeGuid
     */
    protected boolean getIncludeGuid() {
        return includeGuid;
    }

    /**
     * Sets the includeGuid.
     * @param includeGuid the includeGuid to set
     */
    protected void setIncludeGuid(boolean includeGuid) {
        this.includeGuid = includeGuid;
    }
    
    /**
     * Sets the includeUlid.
     * @param includeUlid the includeUlid to set
     */
    protected void setIncludeUlid(boolean includeUlid) {
        this.includeUlid = includeUlid;
    }
    
    protected void setupEntityInstance(final K baseEntity, EntityInstance entityInstance, final V transfer) {
        CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        
        if(entityInstance == null) {
            entityInstance = coreControl.getEntityInstanceByBasePK(baseEntity.getPrimaryKey());
        }

        // Check to make sure entityInstance is not null. This may happen in a case where a non-versioned entity was
        // converted to a versioned one.
        if(entityInstance != null) {
            transfer.setEntityInstance(coreControl.getEntityInstanceTransfer(userVisit, entityInstance, includeEntityAppearance, includeNames, includeKey,
                    includeGuid, includeUlid));

            if(includeEntityAttributeGroups || includeTagScopes) {
                if(includeEntityAttributeGroups) {
                    setupEntityAttributeGroups(coreControl, entityInstance, transfer);
                }

                if(includeTagScopes) {
                    setupTagScopes(coreControl, entityInstance, transfer);
                }
            }
        }
    }

    protected EntityInstance setupComments(final K commentedEntity, EntityInstance commentedEntityInstance, final V transfer, final String commentTypeName) {
        CommentControl commentControl = (CommentControl)Session.getModelController(CommentControl.class);
        
        if(commentedEntityInstance == null) {
            CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
            
            commentedEntityInstance = coreControl.getEntityInstanceByBasePK(commentedEntity.getPrimaryKey());
        }
        
        CommentType commentType = commentControl.getCommentTypeByName(commentedEntityInstance.getEntityType(), commentTypeName);
        transfer.addComments(commentTypeName, new CommentListWrapper(commentControl.getCommentTypeTransfer(userVisit, commentType),
                commentControl.getCommentTransfersByCommentedEntityInstanceAndCommentType(userVisit, commentedEntityInstance, commentType)));
        
        return commentedEntityInstance;
    }

    protected EntityInstance setupRatings(final K ratedEntity, EntityInstance ratedEntityInstance, final V transfer, final String ratingTypeName) {
        RatingControl ratingControl = (RatingControl)Session.getModelController(RatingControl.class);
        
        if(ratedEntityInstance == null) {
            CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
            
            ratedEntityInstance = coreControl.getEntityInstanceByBasePK(ratedEntity.getPrimaryKey());
        }
        
        RatingType ratingType = ratingControl.getRatingTypeByName(ratedEntityInstance.getEntityType(), ratingTypeName);
        transfer.addRatings(ratingTypeName, new RatingListWrapper(ratingControl.getRatingTypeTransfer(userVisit, ratingType),
                ratingControl.getRatingTransfersByRatedEntityInstanceAndRatingType(userVisit, ratedEntityInstance, ratingType)));

        return ratedEntityInstance;
    }

    protected EntityInstance setupOwnedWorkEfforts(final K baseEntity, EntityInstance owningEntityInstance, final V transfer) {
        WorkEffortControl workEffortControl = (WorkEffortControl)Session.getModelController(WorkEffortControl.class);
        
        if(owningEntityInstance == null) {
            CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
            
            owningEntityInstance = coreControl.getEntityInstanceByBasePK(baseEntity.getPrimaryKey());
        }
        
        for(WorkEffortTransfer workEffort: workEffortControl.getWorkEffortTransfersByOwningEntityInstance(userVisit, owningEntityInstance)) {
            transfer.addOwnedWorkEffort(workEffort.getWorkEffortScope().getWorkEffortType().getWorkEffortTypeName(), workEffort);
        }

        return owningEntityInstance;
    }
    
    protected void verifyOptionDependency(String dependentOption, String dependsOnOption) {
        Set<String> options = session.getOptions();
        
        if(!options.contains(dependsOnOption)) {
            // Throwing an Exception for this seems harsh, but failure to meet the requirements could result in an NPE or other Exceptions.
            throw new TransferOptionDependencyException(dependentOption + " requires that " + dependsOnOption + " be set as well");
        }
    }
    
}
