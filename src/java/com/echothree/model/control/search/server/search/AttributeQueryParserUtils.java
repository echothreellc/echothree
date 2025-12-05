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

package com.echothree.model.control.search.server.search;

import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.exception.InvalidEntityAttributeTypeException;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.index.common.IndexConstants;
import com.echothree.model.control.index.common.IndexFields;
import com.echothree.model.control.index.common.IndexSubfields;
import com.echothree.model.control.search.common.exception.MissingRequiredSubfieldException;
import com.echothree.model.control.search.common.exception.MissingValueException;
import com.echothree.model.control.search.common.exception.MissingValueUnitOfMeasureTypeNameException;
import com.echothree.model.control.search.common.exception.UnknownSubfieldException;
import com.echothree.model.control.search.common.exception.UnknownValueUnitOfMeasureTypeNameException;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.AboveMaximumLimitException;
import com.echothree.util.common.exception.BelowMinimumLimitException;
import com.echothree.util.common.exception.InvalidFormatException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.string.DecimalUtils;
import com.echothree.util.common.string.StringUtils;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.ThreadSession;
import com.google.common.base.Splitter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.apache.lucene.document.DoublePoint;
import org.apache.lucene.document.FloatPoint;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BoostQuery;
import org.apache.lucene.search.Query;

public class AttributeQueryParserUtils
        extends BaseLogic {

    final protected ExecutionErrorAccumulator eea;
    final protected Set<String> dateFields = new HashSet<>();
    final protected Set<String> dateTimeFields = new HashSet<>();
    final protected Set<String> intFields = new HashSet<>();
    final protected Set<String> longFields = new HashSet<>();
    final protected Set<String> floatFields = new HashSet<>();
    final protected Set<String> doubleFields = new HashSet<>();
    final protected EntityType entityType;
    final protected UserVisit userVisit;
    
    protected CoreControl coreControl;
    protected UserControl userControl;
    protected Map<String, EntityAttribute> entityAttributes;

    public AttributeQueryParserUtils(final ExecutionErrorAccumulator eea, final Set<String> dateFields, final Set<String> dateTimeFields,
            final Set<String> intFields, final Set<String> longFields, final Set<String> floatFields, final Set<String> doubleFields,
            final EntityType entityType, final UserVisit userVisit) {
        this.eea = eea;
        this.entityType = entityType;
        this.userVisit = userVisit;
        
        if(dateFields != null) {
            this.dateFields.addAll(dateFields);
        }

        if(dateTimeFields != null) {
            this.dateTimeFields.addAll(dateTimeFields);
        }

        if(intFields != null) {
            this.intFields.addAll(intFields);
        }

        if(longFields != null) {
            this.longFields.addAll(longFields);
        }

        if(floatFields != null) {
            this.floatFields.addAll(floatFields);
        }

        if(doubleFields != null) {
            this.doubleFields.addAll(doubleFields);
        }

        this.dateTimeFields.add(IndexFields.createdTime.name());
        this.dateTimeFields.add(IndexFields.modifiedTime.name());
        this.dateTimeFields.add(IndexFields.deletedTime.name());
    }

    protected CoreControl getCoreControl() {
        if(coreControl == null) {
            coreControl = Session.getModelController(CoreControl.class);
        }

        return coreControl;
    }
    
    public UserControl getUserControl() {
        if(userControl == null) {
            userControl = Session.getModelController(UserControl.class);
        }
        
        return userControl;
    }
    
    protected EntityAttribute getEntityAttribute(final String entityAttributeName) {
        EntityAttribute entityAttribute;
        
        if(entityAttributes == null) {
            entityAttributes = new HashMap<>(1);
        }
        
        if(entityAttributes.containsKey(entityAttributeName)) {
            entityAttribute = entityAttributes.get(entityAttributeName);
        } else {
            entityAttribute = getCoreControl().getEntityAttributeByName(entityType, entityAttributeName);
            
            entityAttributes.put(entityAttributeName, entityAttribute);
        }
        
        return entityAttribute;
    }
    
    protected Integer dateValueOf(final String fieldValue) {
        var dateTimeFormatDetail = getUserControl().getPreferredDateTimeFormatFromUserVisit(userVisit).getLastDetail();
        var zoneId = ZoneId.of(getUserControl().getPreferredTimeZoneFromUserVisit(userVisit).getLastDetail().getJavaTimeZoneName());
        DateTimeFormatter dtf = null;
        ZonedDateTime zdt = null;
        
        if(fieldValue.equalsIgnoreCase("TODAY")) {
            zdt = ZonedDateTime.ofInstant(Instant.ofEpochMilli(ThreadSession.currentSession().getStartTime()), zoneId);
        } else {
            var javaShortDateFormat = DateTimeFormatter.ofPattern(dateTimeFormatDetail.getJavaShortDateFormat()).withZone(zoneId);

            try {
                zdt = ZonedDateTime.parse(fieldValue, javaShortDateFormat);
            } catch (IllegalArgumentException iae1) {
                handleExecutionError(InvalidFormatException.class, eea, ExecutionErrors.InvalidFormat.name(), fieldValue);
            }
        }
        
        if(zdt != null) {
            dtf = DateTimeFormatter.ofPattern("yyyyMMdd").withZone(zoneId);
        }
        
        return zdt == null ? null : Integer.valueOf(dtf.format(zdt));
    }
    
    protected Long dateTimeValueOf(final String fieldValue) {
        Long result = null;
        
        if(fieldValue.equalsIgnoreCase("NOW")) {
            result = ThreadSession.currentSession().getStartTimeLong();
        } else {
            var dateTimeFormatDetail = getUserControl().getPreferredDateTimeFormatFromUserVisit(userVisit).getLastDetail();
            var zoneId = ZoneId.of(getUserControl().getPreferredTimeZoneFromUserVisit(userVisit).getLastDetail().getJavaTimeZoneName());
            var dtf = DateTimeFormatter.ofPattern(dateTimeFormatDetail.getJavaShortDateFormat() + " " + dateTimeFormatDetail.getJavaTimeFormatSeconds()).withZone(zoneId);
            ZonedDateTime zdt = null;
            
            try {
                zdt = ZonedDateTime.parse(fieldValue, dtf);
            } catch(IllegalArgumentException iae1) {
                try {
                    dtf = DateTimeFormatter.ofPattern(dateTimeFormatDetail.getJavaShortDateFormat() + " " + dateTimeFormatDetail.getJavaTimeFormat()).withZone(zoneId);
                    zdt = ZonedDateTime.parse(fieldValue, dtf);
                } catch(IllegalArgumentException iae2) {
                    try {
                        dtf = DateTimeFormatter.ofPattern(dateTimeFormatDetail.getJavaShortDateFormat()).withZone(zoneId);
                        zdt = ZonedDateTime.parse(fieldValue, dtf);
                    } catch(IllegalArgumentException iae3) {
                        handleExecutionError(InvalidFormatException.class, eea, ExecutionErrors.InvalidFormat.name(), fieldValue);
                    }
                }
            }
            
            if(zdt != null) {
                result = zdt.toInstant().toEpochMilli();
            }
        }
        
        return result;
    }
    
    protected Integer intValueOf(final String fieldValue) {
        Integer result = null;
        
        if(fieldValue.equalsIgnoreCase("MAX_VALUE")) {
            result = Integer.MAX_VALUE;
        } else if(fieldValue.equalsIgnoreCase("MIN_VALUE")) {
            result = Integer.MIN_VALUE;
        } else {
            try {
                result = Integer.valueOf(fieldValue);
            } catch(NumberFormatException nfe) {
                handleExecutionError(InvalidFormatException.class, eea, ExecutionErrors.InvalidFormat.name(), fieldValue);
            }
        }
        
        return result;
    }
    
    private boolean checkIntegerRange(final int testLimit, Integer minimumLimit, Integer maximumLimit) {
        var valid = true;
        
        if(minimumLimit != null) {
            if(testLimit < minimumLimit) {
                handleExecutionError(BelowMinimumLimitException.class, eea, ExecutionErrors.BelowMinimumLimit.name(), Integer.toString(testLimit));
                valid = false;
            }
        }

        if(maximumLimit != null) {
            if(testLimit > maximumLimit) {
                handleExecutionError(AboveMaximumLimitException.class, eea, ExecutionErrors.AboveMaximumLimit.name(), Integer.toString(testLimit));
                valid = false;
            }
        }
        
        return valid;
    }

    protected Integer intValueOf(final String fieldValue, final Integer minimumValue, final Integer maximumValue) {
        Integer value = null;
        
        if(fieldValue.equalsIgnoreCase("MAX_VALUE")) {
            value = maximumValue == null ? Integer.MAX_VALUE : maximumValue;
        } else if(fieldValue.equalsIgnoreCase("MIN_VALUE")) {
            value = minimumValue == null ? Integer.MIN_VALUE : minimumValue;
        }
        
        try {
            value = Integer.valueOf(fieldValue);

            checkIntegerRange(value, minimumValue, maximumValue);
        } catch(NumberFormatException nfe) {
            handleExecutionError(InvalidFormatException.class, eea, ExecutionErrors.InvalidFormat.name(), fieldValue);
        }
        
        return value;
    }

    protected Long longValueOf(final String fieldValue) {
        Long result = null;

        if(fieldValue.equalsIgnoreCase("MAX_VALUE")) {
            result = Long.MAX_VALUE;
        } else if(fieldValue.equalsIgnoreCase("MIN_VALUE")) {
            result = Long.MIN_VALUE;
        } else {
            try {
                result = Long.valueOf(fieldValue);
            } catch(NumberFormatException nfe) {
                handleExecutionError(InvalidFormatException.class, eea, ExecutionErrors.InvalidFormat.name(), fieldValue);
            }
        }

        return result;
    }

    protected Float floatValueOf(final String fieldValue) {
        Float result = null;

        if(fieldValue.equalsIgnoreCase("MAX_VALUE")) {
            result = Float.MAX_VALUE;
        } else if(fieldValue.equalsIgnoreCase("MIN_VALUE")) {
            result = Float.MIN_VALUE;
        } else {
            try {
                result = Float.valueOf(fieldValue);
            } catch(NumberFormatException nfe) {
                handleExecutionError(InvalidFormatException.class, eea, ExecutionErrors.InvalidFormat.name(), fieldValue);
            }
        }

        return result;
    }

    protected Double doubleValueOf(final String fieldValue) {
        Double result = null;

        if(fieldValue.equalsIgnoreCase("MAX_VALUE")) {
            result = Double.MAX_VALUE;
        } else if(fieldValue.equalsIgnoreCase("MIN_VALUE")) {
            result = Double.MIN_VALUE;
        } else {
            try {
                result = Double.valueOf(fieldValue);
            } catch(NumberFormatException nfe) {
                handleExecutionError(InvalidFormatException.class, eea, ExecutionErrors.InvalidFormat.name(), fieldValue);
            }
        }

        return result;
    }

    protected Integer latitudeValueOf(final String fieldValue) {
        return intValueOf(DecimalUtils.getInstance().parse("-", ".", 6, fieldValue), -90000000, 90000000);
    }
    
    protected Integer longitudeValueOf(final String fieldValue) {
        return intValueOf(DecimalUtils.getInstance().parse("-", ".", 6, fieldValue), -180000000, 180000000);
    }
    
    protected Long measurementValueOf(final String unitOfMeasureKindUseTypeName, final String rawFieldValue) {
        Long value = null;
        var space = StringUtils.getInstance().getSpace(rawFieldValue);
        
        if(space != null) {
            var splitFieldValue = Splitter.onPattern(Pattern.quote(space)).trimResults().omitEmptyStrings().splitToList(rawFieldValue).toArray(new String[0]);

            if(splitFieldValue.length == 2 && splitFieldValue[0].length() > 0 && splitFieldValue[1].length() > 0) {
                var validatedValue = longValueOf(splitFieldValue[0]);

                if(!eea.hasExecutionErrors()) {
                    value = UnitOfMeasureTypeLogic.getInstance().checkUnitOfMeasure(eea, unitOfMeasureKindUseTypeName, validatedValue.toString(),
                            splitFieldValue[1], MissingValueException.class, ExecutionErrors.MissingValue.name(),
                            MissingValueUnitOfMeasureTypeNameException.class, ExecutionErrors.MissingValueUnitOfMeasureTypeName.name(),
                            UnknownValueUnitOfMeasureTypeNameException.class, ExecutionErrors.UnknownValueUnitOfMeasureTypeName.name());
                }
            } else {
                handleExecutionError(InvalidFormatException.class, eea, ExecutionErrors.InvalidFormat.name(), rawFieldValue);
            }
        } else {
            handleExecutionError(InvalidFormatException.class, eea, ExecutionErrors.InvalidFormat.name(), rawFieldValue);
        }
        
        return value;
    }
    
    protected BooleanQuery.Builder newBooleanQuery() {
        return new BooleanQuery.Builder();
    }
    
    protected Query getBooleanQuery(List<BooleanClause> clauses) {
        BooleanQuery.Builder builder;
        
        if(clauses.isEmpty()) {
            builder = null; // all clause words were filtered away by the analyzer.
        } else {
            builder = newBooleanQuery();
            
            clauses.forEach((clause) -> {
                builder.add(clause);
            });
        }
        
        return builder == null ? null : builder.build();
    }
  
    protected Query getFieldQuery(String rawField, String[] rawFields, Map<String,Float> boosts, String queryText, boolean quoted)
            throws ParseException {
        Query query = null;
        
        // rawField is null when multiple fields are being searched by default.
        if(rawField == null) {
            var clauses = new ArrayList<BooleanClause>(rawFields.length);
            
            for(var i = 0; i < rawFields.length; i++) {
                var q = getFieldQuery(rawFields[i], rawFields, boosts, queryText, quoted);
                
                if(q != null) {
                    //I f the user passes a map of boosts
                    if(boosts != null) {
                        // Get the boost from the map and apply it.
                        var boost = boosts.get(rawFields[i]);
                        
                        if (boost != null) {
                            q = new BoostQuery(q, boost);
                        }
                    }
                    
                    clauses.add(new BooleanClause(q, BooleanClause.Occur.SHOULD));
                }
            }
            
            if (clauses.isEmpty()) {
                // happens for stopwords
                return null;
            } else {
                query = getBooleanQuery(clauses);
            }
        } else {
            var splitField = Splitter.onPattern(Pattern.quote(IndexConstants.INDEX_SUBFIELD_SEPARATOR)).trimResults().omitEmptyStrings().splitToList(rawField).toArray(new String[0]);
            var field = splitField[0];

            if(dateFields.contains(field) || dateTimeFields.contains(field)) {
                 query = newTermQuery(new Term(rawField, queryText));
            } else {
                var entityAttribute = getEntityAttribute(field);

                if(entityAttribute != null) {
                    var entityAttributeDetail = entityAttribute.getLastDetail();

                    // Do a case-sensative comparison vs. how the database handles the request. Lucene is case-sensative for field names.
                    if(entityAttributeDetail.getEntityAttributeName().equals(field)) {
                        var entityAttributeTypeName = entityAttributeDetail.getEntityAttributeType().getEntityAttributeTypeName();

                        if(entityAttributeTypeName.equals(EntityAttributeTypes.INTEGER.name())
                                || entityAttributeTypeName.equals(EntityAttributeTypes.LONG.name())
                                || entityAttributeTypeName.equals(EntityAttributeTypes.DATE.name())
                                || entityAttributeTypeName.equals(EntityAttributeTypes.TIME.name())
                                || entityAttributeTypeName.equals(EntityAttributeTypes.GEOPOINT.name())) {
                            query = newTermQuery(new Term(rawField, queryText));
                        }
                    }
                }
            }
        }
        
        return query;
    }
    
    private static final Set<String> excludedEntityAttributeTypeNames;
    
    static {
        var set = new HashSet<String>(5);

        set.add(EntityAttributeTypes.BOOLEAN.name());
        set.add(EntityAttributeTypes.NAME.name());
        set.add(EntityAttributeTypes.MULTIPLELISTITEM.name());
        set.add(EntityAttributeTypes.LISTITEM.name());
        set.add(EntityAttributeTypes.STRING.name());
        set.add(EntityAttributeTypes.CLOB.name());
        
        excludedEntityAttributeTypeNames = Collections.unmodifiableSet(set);
    }

    public Query newTermQuery(final Term term) {
        Query query = null;
        
        if(!eea.hasExecutionErrors()) {
            var rawField = term.field();
            var splitField = Splitter.onPattern(Pattern.quote(IndexConstants.INDEX_SUBFIELD_SEPARATOR)).trimResults().omitEmptyStrings().splitToList(rawField).toArray(new String[0]);
            var field = splitField[0];
            var subfield = splitField.length > 1 ? splitField[1] : null;
            
            if(dateFields.contains(field)) {
                var val = dateValueOf(term.text());

                if(!eea.hasExecutionErrors()) {
                    query = IntPoint.newSetQuery(field, val);
                }
            } else if(dateTimeFields.contains(field)) {
                var val = dateTimeValueOf(term.text());

                if(!eea.hasExecutionErrors()) {
                    query = LongPoint.newSetQuery(field, val);
                }
            } else if(intFields.contains(field)) {
                var val = intValueOf(term.text());

                if(!eea.hasExecutionErrors()) {
                    query = IntPoint.newSetQuery(field, val);
                }
            } else if(longFields.contains(field)) {
                var val = longValueOf(term.text());

                if(!eea.hasExecutionErrors()) {
                    query = LongPoint.newSetQuery(field, val);
                }
            } else if(floatFields.contains(field)) {
                var val = floatValueOf(term.text());

                if(!eea.hasExecutionErrors()) {
                    query = FloatPoint.newSetQuery(field, val);
                }
            } else if(doubleFields.contains(field)) {
                var val = doubleValueOf(term.text());

                if(!eea.hasExecutionErrors()) {
                    query = DoublePoint.newSetQuery(field, val);
                }
            } else {
                var entityAttribute = getEntityAttribute(field);

                if(entityAttribute != null) {
                    var entityAttributeDetail = entityAttribute.getLastDetail();

                    // Do a case-sensitive comparison vs. how the database handles the request. Lucene is case-sensitive for field names.
                    if(entityAttributeDetail.getEntityAttributeName().equals(field)) {
                        var entityAttributeTypeName = entityAttributeDetail.getEntityAttributeType().getEntityAttributeTypeName();

                        if(entityAttributeTypeName.equals(EntityAttributeTypes.INTEGER.name())) {
                            var val = intValueOf(term.text());

                            if(!eea.hasExecutionErrors()) {
                                query = IntPoint.newSetQuery(field, val);
                            }
                        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.LONG.name())) {
                            var val = longValueOf(term.text());

                            if(!eea.hasExecutionErrors()) {
                                query = LongPoint.newSetQuery(field, val);
                            }
                        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.DATE.name())) {
                            var val = dateValueOf(term.text());

                            if(!eea.hasExecutionErrors()) {
                                query = IntPoint.newSetQuery(field, val);
                            }
                        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.TIME.name())) {
                            var val = dateTimeValueOf(term.text());

                            if(!eea.hasExecutionErrors()) {
                                query = LongPoint.newSetQuery(field, val);
                            }
                        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.GEOPOINT.name())) {
                            if(subfield == null) {
                                var entityTypeDetail = entityType.getLastDetail();

                                handleExecutionError(MissingRequiredSubfieldException.class, eea, ExecutionErrors.MissingRequiredSubfield.name(),
                                        entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(), entityTypeDetail.getEntityTypeName(),
                                        entityAttributeTypeName);
                            } else {
                                if(subfield.equals(IndexSubfields.latitude.name())) {
                                    var val = latitudeValueOf(term.text());

                                    if(!eea.hasExecutionErrors()) {
                                        query = IntPoint.newSetQuery(field + IndexConstants.INDEX_SUBFIELD_SEPARATOR + subfield, val);
                                    }
                                } else if(subfield.equals(IndexSubfields.longitude.name())) {
                                    var val = longitudeValueOf(term.text());

                                    if(!eea.hasExecutionErrors()) {
                                        query = IntPoint.newSetQuery(field + IndexConstants.INDEX_SUBFIELD_SEPARATOR + subfield, val);
                                    }
                                } else if(subfield.equals(IndexSubfields.elevation.name())) {
                                    var val = measurementValueOf(UomConstants.UnitOfMeasureKindUseType_ELEVATION, term.text());

                                    if(!eea.hasExecutionErrors()) {
                                        query = LongPoint.newSetQuery(field + IndexConstants.INDEX_SUBFIELD_SEPARATOR + subfield, val);
                                    }
                                } else if(subfield.equals(IndexSubfields.altitude.name())) {
                                    var val = measurementValueOf(UomConstants.UnitOfMeasureKindUseType_ALTITUDE, term.text());

                                    if(!eea.hasExecutionErrors()) {
                                        query = LongPoint.newSetQuery(field + IndexConstants.INDEX_SUBFIELD_SEPARATOR + subfield, val);
                                    }
                                } else {
                                    var entityTypeDetail = entityType.getLastDetail();

                                    handleExecutionError(UnknownSubfieldException.class, eea, ExecutionErrors.UnknownSubfield.name(),
                                            entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(), entityTypeDetail.getEntityTypeName(),
                                            entityAttributeTypeName, subfield);
                                }
                            }
                        } else if(!excludedEntityAttributeTypeNames.contains(entityAttributeTypeName)) {
                            var entityTypeDetail = entityType.getLastDetail();

                            handleExecutionError(InvalidEntityAttributeTypeException.class, eea, ExecutionErrors.InvalidEntityAttributeType.name(),
                                    entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(), entityTypeDetail.getEntityTypeName(),
                                    entityAttributeTypeName);
                        }
                    }
                }
            }
        }
        
        return query;
    }

    public Query newRangeQuery(final String rawField, String[] rawFields, final String min, final String max, final boolean startInclusive, final boolean endInclusive) {
        Query query = null;
        
        if(rawField == null) {
            var clauses = new ArrayList<BooleanClause>(rawFields.length);
            
            for(var i = 0; i < rawFields.length; i++) {
                clauses.add(new BooleanClause(newRangeQuery(rawFields[i], null, min, max, startInclusive, endInclusive), BooleanClause.Occur.SHOULD));
            }
            
            query = getBooleanQuery(clauses);
        } else {
            if(!eea.hasExecutionErrors()) {
                var splitField = Splitter.onPattern(Pattern.quote(IndexConstants.INDEX_SUBFIELD_SEPARATOR)).trimResults().omitEmptyStrings().splitToList(rawField).toArray(new String[0]);
                var field = splitField[0];
                var subfield = splitField.length > 1 ? splitField[1] : null;

                if(dateFields.contains(field)) {
                    var valMin = dateValueOf(min);
                    var valMax = dateValueOf(max);

                    if(!eea.hasExecutionErrors()) {
                        query = IntPoint.newRangeQuery(field, startInclusive? valMin: Math.addExact(valMin, 1) , endInclusive? valMax: Math.addExact(valMax, -1));
                    }
                } else if(dateTimeFields.contains(field)) {
                    var valMin = dateTimeValueOf(min);
                    var valMax = dateTimeValueOf(max);

                    if(!eea.hasExecutionErrors()) {
                        query = LongPoint.newRangeQuery(field, startInclusive? valMin: Math.addExact(valMin, 1) , endInclusive? valMax: Math.addExact(valMax, -1));
                    }
                } if(intFields.contains(field)) {
                    var valMin = intValueOf(min);
                    var valMax = intValueOf(max);

                    if(!eea.hasExecutionErrors()) {
                        query = IntPoint.newRangeQuery(field, startInclusive? valMin: Math.addExact(valMin, 1) , endInclusive? valMax: Math.addExact(valMax, -1));
                    }
                } else if(longFields.contains(field)) {
                    var valMin = longValueOf(min);
                    var valMax = longValueOf(max);

                    if(!eea.hasExecutionErrors()) {
                        query = LongPoint.newRangeQuery(field, startInclusive? valMin: Math.addExact(valMin, 1) , endInclusive? valMax: Math.addExact(valMax, -1));
                    }
                }  else if(floatFields.contains(field)) {
                    var valMin = floatValueOf(min);
                    var valMax = floatValueOf(max);

                    if(!eea.hasExecutionErrors()) {
                        query = FloatPoint.newRangeQuery(field, startInclusive? valMin: FloatPoint.nextDown(valMin) , endInclusive? valMax: FloatPoint.nextUp(valMax));
                    }
                } else {
                    var entityAttribute = getEntityAttribute(field);

                    if(entityAttribute != null) {
                        var entityAttributeDetail = entityAttribute.getLastDetail();

                        // Do a case-sensitive comparison vs. how the database handles the request. Lucene is case-sensative for field names.
                        if(entityAttributeDetail.getEntityAttributeName().equals(field)) {
                            var entityAttributeTypeName = entityAttributeDetail.getEntityAttributeType().getEntityAttributeTypeName();

                            if(entityAttributeTypeName.equals(EntityAttributeTypes.INTEGER.name())) {
                                var valMin = intValueOf(min);
                                var valMax = intValueOf(max);

                                if(!eea.hasExecutionErrors()) {
                                    query = IntPoint.newRangeQuery(field, startInclusive? valMin: Math.addExact(valMin, 1), endInclusive? valMax: Math.addExact(valMax, -1));
                                }
                            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.LONG.name())) {
                                var valMin = longValueOf(min);
                                var valMax = longValueOf(max);

                                if(!eea.hasExecutionErrors()) {
                                    query = LongPoint.newRangeQuery(field, startInclusive? valMin: Math.addExact(valMin, 1), endInclusive? valMax: Math.addExact(valMax, -1));
                                }
                            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.DATE.name())) {
                                var valMin = dateValueOf(min);
                                var valMax = dateValueOf(max);

                                if(!eea.hasExecutionErrors()) {
                                    query = IntPoint.newRangeQuery(field, startInclusive? valMin: Math.addExact(valMin, 1), endInclusive? valMax: Math.addExact(valMax, -1));
                                }
                            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.TIME.name())) {
                                var valMin = dateTimeValueOf(min);
                                var valMax = dateTimeValueOf(max);

                                if(!eea.hasExecutionErrors()) {
                                    query = LongPoint.newRangeQuery(field, startInclusive? valMin: Math.addExact(valMin, 1), endInclusive? valMax: Math.addExact(valMax, -1));
                                }
                            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.GEOPOINT.name())) {
                                if(subfield == null) {
                                    var entityTypeDetail = entityType.getLastDetail();

                                    handleExecutionError(MissingRequiredSubfieldException.class, eea, ExecutionErrors.MissingRequiredSubfield.name(),
                                            entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(), entityTypeDetail.getEntityTypeName(),
                                            entityAttributeTypeName);
                                } else {
                                    if(subfield.equals(IndexSubfields.latitude.name())) {
                                        var valMin = latitudeValueOf(min);
                                        var valMax = latitudeValueOf(max);

                                        if(!eea.hasExecutionErrors()) {
                                            query = IntPoint.newRangeQuery(field + IndexConstants.INDEX_SUBFIELD_SEPARATOR + subfield,
                                                    startInclusive ? valMin : Math.addExact(valMin, 1), endInclusive ? valMax : Math.addExact(valMax, -1));
                                        }
                                    } else if(subfield.equals(IndexSubfields.longitude.name())) {
                                        var valMin = longitudeValueOf(min);
                                        var valMax = longitudeValueOf(max);

                                        if(!eea.hasExecutionErrors()) {
                                            query = IntPoint.newRangeQuery(field + IndexConstants.INDEX_SUBFIELD_SEPARATOR + subfield,
                                                    startInclusive ? valMin : Math.addExact(valMin, 1), endInclusive ? valMax : Math.addExact(valMax, -1));
                                        }
                                    } else if(subfield.equals(IndexSubfields.elevation.name())) {
                                        var valMin = measurementValueOf(UomConstants.UnitOfMeasureKindUseType_ELEVATION, min);
                                        var valMax = measurementValueOf(UomConstants.UnitOfMeasureKindUseType_ELEVATION, max);

                                        if(!eea.hasExecutionErrors()) {
                                            query = LongPoint.newRangeQuery(field + IndexConstants.INDEX_SUBFIELD_SEPARATOR + subfield,
                                                    startInclusive ? valMin : Math.addExact(valMin, 1), endInclusive ? valMax : Math.addExact(valMax, -1));
                                        }
                                    } else if(subfield.equals(IndexSubfields.altitude.name())) {
                                        var valMin = measurementValueOf(UomConstants.UnitOfMeasureKindUseType_ALTITUDE, min);
                                        var valMax = measurementValueOf(UomConstants.UnitOfMeasureKindUseType_ALTITUDE, max);

                                        if(!eea.hasExecutionErrors()) {
                                            query = LongPoint.newRangeQuery(field + IndexConstants.INDEX_SUBFIELD_SEPARATOR + subfield,
                                                    startInclusive ? valMin : Math.addExact(valMin, 1), endInclusive ? valMax : Math.addExact(valMax, -1));
                                        }
                                    }
                                }
                            } else if(!excludedEntityAttributeTypeNames.contains(entityAttributeTypeName)) {
                                var entityTypeDetail = entityType.getLastDetail();

                                handleExecutionError(InvalidEntityAttributeTypeException.class, eea, ExecutionErrors.InvalidEntityAttributeType.name(),
                                        entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(), entityTypeDetail.getEntityTypeName(),
                                        entityAttributeTypeName);
                            }
                        }
                    }
                }
            }
        }
        
        return query;
    }

}
