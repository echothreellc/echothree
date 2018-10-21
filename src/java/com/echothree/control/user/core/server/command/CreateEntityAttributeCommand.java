// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.remote.edit.EntityAttributeEdit;
import com.echothree.control.user.core.remote.form.CreateEntityAttributeForm;
import com.echothree.control.user.core.remote.result.CoreResultFactory;
import com.echothree.control.user.core.remote.result.CreateEntityAttributeResult;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.common.SequenceConstants;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceTypeLogic;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.data.core.server.entity.ComponentVendorDetail;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityAttributeDetail;
import com.echothree.model.data.core.server.entity.EntityAttributeType;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.EntityTypeDetail;
import com.echothree.model.data.party.remote.pk.PartyPK;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.remote.form.ValidationResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.Validator;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateEntityAttributeCommand
        extends BaseSimpleCommand<CreateEntityAttributeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    private final static List<FieldDefinition> BLOB_FORM_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> STRING_FORM_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> INTEGER_FORM_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> LONG_FORM_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> LISTITEM_FORM_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> MULTIPLELISTITEM_FORM_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> OTHER_FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityAttribute.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityAttributeTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrackRevisions", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
        
        BLOB_FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CheckContentWebAddress", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("ValidationPattern", FieldType.NULL, false, null, null),
                new FieldDefinition("UpperRangeIntegerValue", FieldType.SIGNED_INTEGER, false, null, null),
                new FieldDefinition("UpperLimitIntegerValue", FieldType.SIGNED_INTEGER, false, null, null),
                new FieldDefinition("LowerLimitIntegerValue", FieldType.SIGNED_INTEGER, false, null, null),
                new FieldDefinition("LowerRangeIntegerValue", FieldType.SIGNED_INTEGER, false, null, null),
                new FieldDefinition("UpperRangeLongValue", FieldType.SIGNED_LONG, false, null, null),
                new FieldDefinition("UpperLimitLongValue", FieldType.SIGNED_LONG, false, null, null),
                new FieldDefinition("LowerLimitLongValue", FieldType.SIGNED_LONG, false, null, null),
                new FieldDefinition("LowerRangeLongValue", FieldType.SIGNED_LONG, false, null, null),
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityListItemSequenceName", FieldType.ENTITY_NAME, false, null, null)
                ));
        
        STRING_FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CheckContentWebAddress", FieldType.NULL, false, null, null),
                new FieldDefinition("ValidationPattern", FieldType.REGULAR_EXPRESSION, false, null, null),
                new FieldDefinition("UpperRangeIntegerValue", FieldType.NULL, false, null, null),
                new FieldDefinition("UpperLimitIntegerValue", FieldType.NULL, false, null, null),
                new FieldDefinition("LowerLimitIntegerValue", FieldType.NULL, false, null, null),
                new FieldDefinition("LowerRangeIntegerValue", FieldType.NULL, false, null, null),
                new FieldDefinition("UpperRangeLongValue", FieldType.NULL, false, null, null),
                new FieldDefinition("UpperLimitLongValue", FieldType.NULL, false, null, null),
                new FieldDefinition("LowerLimitLongValue", FieldType.NULL, false, null, null),
                new FieldDefinition("LowerRangeLongValue", FieldType.NULL, false, null, null),
                new FieldDefinition("UnitOfMeasureKindName", FieldType.NULL, false, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.NULL, false, null, null),
                new FieldDefinition("EntityListItemSequenceName", FieldType.NULL, false, null, null)
                ));
        
        INTEGER_FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CheckContentWebAddress", FieldType.NULL, false, null, null),
                new FieldDefinition("ValidationPattern", FieldType.NULL, false, null, null),
                new FieldDefinition("UpperRangeIntegerValue", FieldType.SIGNED_INTEGER, false, null, null),
                new FieldDefinition("UpperLimitIntegerValue", FieldType.SIGNED_INTEGER, false, null, null),
                new FieldDefinition("LowerLimitIntegerValue", FieldType.SIGNED_INTEGER, false, null, null),
                new FieldDefinition("LowerRangeIntegerValue", FieldType.SIGNED_INTEGER, false, null, null),
                new FieldDefinition("UpperRangeLongValue", FieldType.NULL, false, null, null),
                new FieldDefinition("UpperLimitLongValue", FieldType.NULL, false, null, null),
                new FieldDefinition("LowerLimitLongValue", FieldType.NULL, false, null, null),
                new FieldDefinition("LowerRangeLongValue", FieldType.NULL, false, null, null),
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityListItemSequenceName", FieldType.NULL, false, null, null)
                ));
        
        LONG_FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CheckContentWebAddress", FieldType.NULL, false, null, null),
                new FieldDefinition("ValidationPattern", FieldType.NULL, false, null, null),
                new FieldDefinition("UpperRangeIntegerValue", FieldType.NULL, false, null, null),
                new FieldDefinition("UpperLimitIntegerValue", FieldType.NULL, false, null, null),
                new FieldDefinition("LowerLimitIntegerValue", FieldType.NULL, false, null, null),
                new FieldDefinition("LowerRangeIntegerValue", FieldType.NULL, false, null, null),
                new FieldDefinition("UpperRangeLongValue", FieldType.SIGNED_LONG, false, null, null),
                new FieldDefinition("UpperLimitLongValue", FieldType.SIGNED_LONG, false, null, null),
                new FieldDefinition("LowerLimitLongValue", FieldType.SIGNED_LONG, false, null, null),
                new FieldDefinition("LowerRangeLongValue", FieldType.SIGNED_LONG, false, null, null),
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityListItemSequenceName", FieldType.NULL, false, null, null)
                ));
        
        LISTITEM_FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CheckContentWebAddress", FieldType.NULL, false, null, null),
                new FieldDefinition("ValidationPattern", FieldType.NULL, false, null, null),
                new FieldDefinition("UpperRangeIntegerValue", FieldType.NULL, false, null, null),
                new FieldDefinition("UpperLimitIntegerValue", FieldType.NULL, false, null, null),
                new FieldDefinition("LowerLimitIntegerValue", FieldType.NULL, false, null, null),
                new FieldDefinition("LowerRangeIntegerValue", FieldType.NULL, false, null, null),
                new FieldDefinition("UpperRangeLongValue", FieldType.NULL, false, null, null),
                new FieldDefinition("UpperLimitLongValue", FieldType.NULL, false, null, null),
                new FieldDefinition("LowerLimitLongValue", FieldType.NULL, false, null, null),
                new FieldDefinition("LowerRangeLongValue", FieldType.NULL, false, null, null),
                new FieldDefinition("UnitOfMeasureKindName", FieldType.NULL, false, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.NULL, false, null, null),
                new FieldDefinition("EntityListItemSequenceName", FieldType.ENTITY_NAME, false, null, null)
                ));
        
        MULTIPLELISTITEM_FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CheckContentWebAddress", FieldType.NULL, false, null, null),
                new FieldDefinition("ValidationPattern", FieldType.NULL, false, null, null),
                new FieldDefinition("UpperRangeIntegerValue", FieldType.NULL, false, null, null),
                new FieldDefinition("UpperLimitIntegerValue", FieldType.NULL, false, null, null),
                new FieldDefinition("LowerLimitIntegerValue", FieldType.NULL, false, null, null),
                new FieldDefinition("LowerRangeIntegerValue", FieldType.NULL, false, null, null),
                new FieldDefinition("UpperRangeLongValue", FieldType.NULL, false, null, null),
                new FieldDefinition("UpperLimitLongValue", FieldType.NULL, false, null, null),
                new FieldDefinition("LowerLimitLongValue", FieldType.NULL, false, null, null),
                new FieldDefinition("LowerRangeLongValue", FieldType.NULL, false, null, null),
                new FieldDefinition("UnitOfMeasureKindName", FieldType.NULL, false, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.NULL, false, null, null),
                new FieldDefinition("EntityListItemSequenceName", FieldType.ENTITY_NAME, false, null, null)
                ));
        
        OTHER_FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CheckContentWebAddress", FieldType.NULL, false, null, null),
                new FieldDefinition("ValidationPattern", FieldType.NULL, false, null, null),
                new FieldDefinition("UpperRangeIntegerValue", FieldType.NULL, false, null, null),
                new FieldDefinition("UpperLimitIntegerValue", FieldType.NULL, false, null, null),
                new FieldDefinition("LowerLimitIntegerValue", FieldType.NULL, false, null, null),
                new FieldDefinition("LowerRangeIntegerValue", FieldType.NULL, false, null, null),
                new FieldDefinition("UpperRangeLongValue", FieldType.NULL, false, null, null),
                new FieldDefinition("UpperLimitLongValue", FieldType.NULL, false, null, null),
                new FieldDefinition("LowerLimitLongValue", FieldType.NULL, false, null, null),
                new FieldDefinition("LowerRangeLongValue", FieldType.NULL, false, null, null),
                new FieldDefinition("UnitOfMeasureKindName", FieldType.NULL, false, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.NULL, false, null, null),
                new FieldDefinition("EntityListItemSequenceName", FieldType.NULL, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreateEntityAttributeCommand */
    public CreateEntityAttributeCommand(UserVisitPK userVisitPK, CreateEntityAttributeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    public static ValidationResult AdditionalEntityAttributeValidation(EntityAttributeEdit edit, Validator validator,
            EntityAttributeType entityAttributeType) {
        ValidationResult validationResult;
        
        switch (EntityAttributeTypes.valueOf(entityAttributeType.getEntityAttributeTypeName())) {
            case BLOB:
                validationResult = validator.validate(edit, BLOB_FORM_FIELD_DEFINITIONS);
                break;
            case STRING:
                validationResult = validator.validate(edit, STRING_FORM_FIELD_DEFINITIONS);
                break;
            case INTEGER:
                validationResult = validator.validate(edit, INTEGER_FORM_FIELD_DEFINITIONS);
                break;
            case LONG:
                validationResult = validator.validate(edit, LONG_FORM_FIELD_DEFINITIONS);
                break;
            case LISTITEM:
                validationResult = validator.validate(edit, LISTITEM_FORM_FIELD_DEFINITIONS);
                break;
            case MULTIPLELISTITEM:
                validationResult = validator.validate(edit, MULTIPLELISTITEM_FORM_FIELD_DEFINITIONS);
                break;
            default:
                validationResult = validator.validate(edit, OTHER_FORM_FIELD_DEFINITIONS);
                break;
        }
        
        return validationResult;
    }
    
    @Override
    protected ValidationResult validate() {
        Validator validator = new Validator(this);
        ValidationResult validationResult = validator.validate(form, FORM_FIELD_DEFINITIONS);
        
        if(!validationResult.getHasErrors()) {
            EntityAttributeType entityAttributeType = EntityAttributeLogic.getInstance().getEntityAttributeTypeByName(this, form.getEntityAttributeTypeName());
            
            if(!hasExecutionErrors()) {
                validationResult = AdditionalEntityAttributeValidation(form, validator, entityAttributeType);
            }
        }
        
        return validationResult;
    }
    
    @Override
    protected BaseResult execute() {
        CreateEntityAttributeResult result = CoreResultFactory.getCreateEntityAttributeResult();
        EntityAttribute entityAttribute = null;
        
        if(!hasExecutionErrors()) {
            String unitOfMeasureKindName = form.getUnitOfMeasureKindName();
            String unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
            int parameterCount = (unitOfMeasureKindName == null ? 0 : 1) + (unitOfMeasureTypeName == null ? 0 : 1);

            if(parameterCount == 0 || parameterCount == 2) {
                String componentVendorName = form.getComponentVendorName();
                String entityTypeName = form.getEntityTypeName();
                String entityAttributeTypeName = form.getEntityAttributeTypeName();
                EntityType entityType = EntityTypeLogic.getInstance().getEntityTypeByName(this, componentVendorName, entityTypeName);
                EntityAttributeType entityAttributeType = EntityAttributeLogic.getInstance().getEntityAttributeTypeByName(this, entityAttributeTypeName);

                if(!hasExecutionErrors()) {
                    String entityListItemSequenceName = entityAttributeTypeName.equals(EntityAttributeTypes.LISTITEM.name())
                            || entityAttributeTypeName.equals(EntityAttributeTypes.MULTIPLELISTITEM.name()) ?
                            form.getEntityListItemSequenceName() : null;
                    Sequence entityListItemSequence = null;

                    if(entityListItemSequenceName != null) {
                        SequenceType sequenceType = SequenceTypeLogic.getInstance().getSequenceTypeByName(this, SequenceConstants.SequenceType_ENTITY_LIST_ITEM);

                        if(!hasExecutionErrors()) {
                            SequenceControl sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);

                            entityListItemSequence = sequenceControl.getSequenceByName(sequenceType, entityListItemSequenceName);

                            if(entityListItemSequence == null) {
                                addExecutionError(ExecutionErrors.UnknownEntityListItemSequenceName.name(), entityListItemSequenceName);
                            }
                        }
                    }

                    if(!hasExecutionErrors()) {
                        UnitOfMeasureType unitOfMeasureType = null;

                        if(parameterCount == 2) {
                            unitOfMeasureType = UnitOfMeasureTypeLogic.getInstance().getUnitOfMeasureTypeByName(this, unitOfMeasureKindName, unitOfMeasureTypeName);
                        }

                        if(!hasExecutionErrors()) {
                            PartyPK partyPK = getPartyPK();
                            String entityAttributeName = form.getEntityAttributeName();
                            Boolean trackRevisions = Boolean.valueOf(form.getTrackRevisions());
                            String strCheckContentWebAddress = form.getCheckContentWebAddress();
                            Boolean checkContentWebAddress = strCheckContentWebAddress == null ? null : Boolean.valueOf(strCheckContentWebAddress);
                            String validationPattern = form.getValidationPattern();
                            String strUpperRangeIntegerValue = form.getUpperRangeIntegerValue();
                            Integer upperRangeIntegerValue = strUpperRangeIntegerValue == null ? null : Integer.valueOf(strUpperRangeIntegerValue);
                            String strUpperLimitIntegerValue = form.getUpperLimitIntegerValue();
                            Integer upperLimitIntegerValue = strUpperLimitIntegerValue == null ? null : Integer.valueOf(strUpperLimitIntegerValue);
                            String strLowerLimitIntegerValue = form.getLowerLimitIntegerValue();
                            Integer lowerLimitIntegerValue = strLowerLimitIntegerValue == null ? null : Integer.valueOf(strLowerLimitIntegerValue);
                            String strLowerRangeIntegerValue = form.getLowerRangeIntegerValue();
                            Integer lowerRangeIntegerValue = strLowerRangeIntegerValue == null ? null : Integer.valueOf(strLowerRangeIntegerValue);
                            String strUpperRangeLongValue = form.getUpperRangeLongValue();
                            Long upperRangeLongValue = strUpperRangeLongValue == null ? null : Long.valueOf(strUpperRangeLongValue);
                            String strUpperLimitLongValue = form.getUpperLimitLongValue();
                            Long upperLimitLongValue = strUpperLimitLongValue == null ? null : Long.valueOf(strUpperLimitLongValue);
                            String strLowerLimitLongValue = form.getLowerLimitLongValue();
                            Long lowerLimitLongValue = strLowerLimitLongValue == null ? null : Long.valueOf(strLowerLimitLongValue);
                            String strLowerRangeLongValue = form.getLowerRangeLongValue();
                            Long lowerRangeLongValue = strLowerRangeLongValue == null ? null : Long.valueOf(strLowerRangeLongValue);
                            Integer sortOrder = Integer.valueOf(form.getSortOrder());
                            String description = form.getDescription();

                            entityAttribute = EntityAttributeLogic.getInstance().createEntityAttribute(this, entityType,
                                    entityAttributeName, entityAttributeType, trackRevisions, checkContentWebAddress,
                                    validationPattern, upperRangeIntegerValue, upperLimitIntegerValue, lowerLimitIntegerValue,
                                    lowerRangeIntegerValue, upperRangeLongValue, upperLimitLongValue, lowerLimitLongValue,
                                    lowerRangeLongValue, entityListItemSequence, unitOfMeasureType, sortOrder, partyPK,
                                    getPreferredLanguage(), description);
                        }
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.InvalidParameterCount.name());
            }
        }
        
        if(entityAttribute != null) {
            EntityAttributeDetail entityAttributeDetail = entityAttribute.getLastDetail();
            EntityTypeDetail entityTypeDetail = entityAttributeDetail.getEntityType().getLastDetail();
            ComponentVendorDetail componentVendorDetail = entityTypeDetail.getComponentVendor().getLastDetail();
            
            result.setComponentVendorName(componentVendorDetail.getComponentVendorName());
            result.setEntityTypeName(entityTypeDetail.getEntityTypeName());
            result.setEntityAttributeName(entityAttributeDetail.getEntityAttributeName());
            result.setEntityRef(entityAttribute.getPrimaryKey().getEntityRef());
        }
        
        return result;
    }

}
