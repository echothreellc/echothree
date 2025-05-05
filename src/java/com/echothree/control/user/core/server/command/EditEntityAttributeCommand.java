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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.edit.EntityAttributeEdit;
import com.echothree.control.user.core.common.form.EditEntityAttributeForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityAttributeResult;
import com.echothree.control.user.core.common.spec.EntityAttributeUniversalSpec;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceTypeLogic;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.form.ValidationResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.Validator;
import java.util.List;

public class EditEntityAttributeCommand
        extends BaseAbstractEditCommand<EntityAttributeUniversalSpec, EntityAttributeEdit, EditEntityAttributeResult, EntityAttribute, EntityAttribute> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityAttribute.name(), SecurityRoles.Edit.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null),
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, false, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, false, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrackRevisions", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }
    
    /** Creates a new instance of EditEntityAttributeCommand */
    public EditEntityAttributeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected ValidationResult validateEdit(Validator validator) {
        var validationResult = validator.validate(edit, getEditFieldDefinitions());
        
        if(!validationResult.getHasErrors()) {
            entityAttribute = EntityAttributeLogic.getInstance().getEntityAttributeByUniversalSpec(this,
                    spec);
            
            if(!hasExecutionErrors()) {
                var entityAttributeType = entityAttribute.getLastDetail().getEntityAttributeType();
    
                validationResult = CreateEntityAttributeCommand.AdditionalEntityAttributeValidation(edit, validator, entityAttributeType);
            }
        }
        
        return validationResult;
    }
    
    @Override
    public EditEntityAttributeResult getResult() {
        return CoreResultFactory.getEditEntityAttributeResult();
    }

    @Override
    public EntityAttributeEdit getEdit() {
        return CoreEditFactory.getEntityAttributeEdit();
    }

    EntityAttribute entityAttribute = null;
    
    @Override
    public EntityAttribute getEntity(EditEntityAttributeResult result) {
        entityAttribute = EntityAttributeLogic.getInstance().getEntityAttributeByUniversalSpec(this,
                spec, editModeToEntityPermission(editMode));

        return entityAttribute;
    }

    @Override
    public EntityAttribute getLockEntity(EntityAttribute entityAttribute) {
        return entityAttribute;
    }

    @Override
    public void fillInResult(EditEntityAttributeResult result, EntityAttribute entityAttribute) {
        var coreControl = getCoreControl();

        result.setEntityAttribute(coreControl.getEntityAttributeTransfer(getUserVisit(), entityAttribute, null));
    }

    Sequence entityListItemSequence = null;
    UnitOfMeasureType unitOfMeasureType = null;
    
    @Override
    public void doLock(EntityAttributeEdit edit, EntityAttribute entityAttribute) {
        var coreControl = getCoreControl();
        var entityAttributeDescription = coreControl.getEntityAttributeDescription(entityAttribute, getPreferredLanguage());
        var entityAttributeDetail = entityAttribute.getLastDetail();
        var entityAttributeTypeName = entityAttribute.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();
        var entityAttributeType = EntityAttributeTypes.valueOf(entityAttributeTypeName);
        
        edit.setEntityAttributeName(entityAttributeDetail.getEntityAttributeName());
        edit.setTrackRevisions(entityAttributeDetail.getTrackRevisions().toString());
        edit.setSortOrder(entityAttributeDetail.getSortOrder().toString());

        switch(entityAttributeType) {
            case BLOB -> {
                var entityAttributeBlob = coreControl.getEntityAttributeBlobForUpdate(entityAttribute);

                edit.setCheckContentWebAddress(entityAttributeBlob.getCheckContentWebAddress().toString());
            }
            case STRING -> {
                var entityAttributeString = coreControl.getEntityAttributeStringForUpdate(entityAttribute);

                if(entityAttributeString != null) {
                    edit.setValidationPattern(entityAttributeString.getValidationPattern());
                }
            }
            case INTEGER -> {
                var entityAttributeInteger = coreControl.getEntityAttributeIntegerForUpdate(entityAttribute);
                var entityAttributeNumeric = coreControl.getEntityAttributeNumericForUpdate(entityAttribute);

                if(entityAttributeInteger != null) {
                    edit.setUpperLimitIntegerValue(entityAttributeInteger.getUpperLimitIntegerValue().toString());
                    edit.setUpperRangeIntegerValue(entityAttributeInteger.getUpperRangeIntegerValue().toString());
                    edit.setLowerRangeIntegerValue(entityAttributeInteger.getLowerRangeIntegerValue().toString());
                    edit.setLowerLimitIntegerValue(entityAttributeInteger.getLowerLimitIntegerValue().toString());
                }

                if(entityAttributeNumeric != null) {
                    unitOfMeasureType = entityAttributeNumeric.getUnitOfMeasureType();

                    if(unitOfMeasureType != null) {
                        var unitOfMeasureTypeDetail = unitOfMeasureType.getLastDetail();

                        edit.setUnitOfMeasureKindName(unitOfMeasureTypeDetail.getUnitOfMeasureKind().getLastDetail().getUnitOfMeasureKindName());
                        edit.setUnitOfMeasureTypeName(unitOfMeasureTypeDetail.getUnitOfMeasureTypeName());
                    }
                }
            }
            case LONG -> {
                var entityAttributeLong = coreControl.getEntityAttributeLongForUpdate(entityAttribute);
                var entityAttributeNumeric = coreControl.getEntityAttributeNumericForUpdate(entityAttribute);

                if(entityAttributeLong != null) {
                    edit.setUpperLimitLongValue(entityAttributeLong.getUpperLimitLongValue().toString());
                    edit.setUpperRangeLongValue(entityAttributeLong.getUpperRangeLongValue().toString());
                    edit.setLowerRangeLongValue(entityAttributeLong.getLowerRangeLongValue().toString());
                    edit.setLowerLimitLongValue(entityAttributeLong.getLowerLimitLongValue().toString());
                }

                if(entityAttributeNumeric != null) {
                    unitOfMeasureType = entityAttributeNumeric.getUnitOfMeasureType();

                    if(unitOfMeasureType != null) {
                        var unitOfMeasureTypeDetail = unitOfMeasureType.getLastDetail();

                        edit.setUnitOfMeasureKindName(unitOfMeasureTypeDetail.getUnitOfMeasureKind().getLastDetail().getUnitOfMeasureKindName());
                        edit.setUnitOfMeasureTypeName(unitOfMeasureTypeDetail.getUnitOfMeasureTypeName());
                    }
                }
            }
            case LISTITEM, MULTIPLELISTITEM -> {
                var entityAttributeListItem = coreControl.getEntityAttributeListItemForUpdate(entityAttribute);

                if(entityAttributeListItem != null) {
                    entityListItemSequence = entityAttributeListItem.getEntityListItemSequence();

                    edit.setEntityListItemSequenceName(entityListItemSequence == null ? null : entityListItemSequence.getLastDetail().getSequenceName());
                }
            }
            default -> {
            }
        }
        
        if(entityAttributeDescription != null) {
            edit.setDescription(entityAttributeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(EntityAttribute entityAttribute) {
        var coreControl = getCoreControl();
        var entityAttributeName = edit.getEntityAttributeName();
        var duplicateEntityAttribute = coreControl.getEntityAttributeByName(entityAttribute.getLastDetail().getEntityType(),
                entityAttributeName);

        if(duplicateEntityAttribute == null || entityAttribute.equals(duplicateEntityAttribute)) {
            var entityAttributeTypeName = entityAttribute.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();
            var entityListItemSequenceName = entityAttributeTypeName.equals(EntityAttributeTypes.LISTITEM.name())
                    || entityAttributeTypeName.equals(EntityAttributeTypes.MULTIPLELISTITEM.name()) ?
                    edit.getEntityListItemSequenceName() : null;

            if(entityListItemSequenceName != null) {
                var sequenceType = SequenceTypeLogic.getInstance().getSequenceTypeByName(this, SequenceTypes.ENTITY_LIST_ITEM.name());

                if(!hasExecutionErrors()) {
                    var sequenceControl = Session.getModelController(SequenceControl.class);

                    entityListItemSequence = sequenceControl.getSequenceByName(sequenceType, entityListItemSequenceName);

                    if(entityListItemSequence == null) {
                        addExecutionError(ExecutionErrors.UnknownEntityListItemSequenceName.name(), entityListItemSequenceName);
                    }
                }
            }

            if(!hasExecutionErrors()) {
                var unitOfMeasureKindName = edit.getUnitOfMeasureKindName();
                var unitOfMeasureTypeName = edit.getUnitOfMeasureTypeName();
                var parameterCount = (unitOfMeasureKindName == null ? 0 : 1) + (unitOfMeasureTypeName == null ? 0 : 1);
                
                if(parameterCount == 0 || parameterCount == 2) {
                    if(parameterCount == 2) {
                        unitOfMeasureType = UnitOfMeasureTypeLogic.getInstance().getUnitOfMeasureTypeByName(this, unitOfMeasureKindName, unitOfMeasureTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.InvalidParameterCount.name());
                }
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateEntityAttributeName.name(), entityAttributeName);
        }
    }

    @Override
    public void doUpdate(EntityAttribute entityAttribute) {
        var coreControl = getCoreControl();
        var partyPK = getPartyPK();
        var entityAttributeDetailValue = coreControl.getEntityAttributeDetailValueForUpdate(entityAttribute);
        var entityAttributeDescription = coreControl.getEntityAttributeDescriptionForUpdate(entityAttribute, getPreferredLanguage());
        var strCheckContentWebAddress = edit.getCheckContentWebAddress();
        var checkContentWebAddress = strCheckContentWebAddress == null ? null : Boolean.valueOf(strCheckContentWebAddress);
        var validationPattern = edit.getValidationPattern();
        var strUpperRangeIntegerValue = edit.getUpperRangeIntegerValue();
        var upperRangeIntegerValue = strUpperRangeIntegerValue == null ? null : Integer.valueOf(strUpperRangeIntegerValue);
        var strUpperLimitIntegerValue = edit.getUpperLimitIntegerValue();
        var upperLimitIntegerValue = strUpperLimitIntegerValue == null ? null : Integer.valueOf(strUpperLimitIntegerValue);
        var strLowerLimitIntegerValue = edit.getLowerLimitIntegerValue();
        var lowerLimitIntegerValue = strLowerLimitIntegerValue == null ? null : Integer.valueOf(strLowerLimitIntegerValue);
        var strLowerRangeIntegerValue = edit.getLowerRangeIntegerValue();
        var lowerRangeIntegerValue = strLowerRangeIntegerValue == null ? null : Integer.valueOf(strLowerRangeIntegerValue);
        var strUpperRangeLongValue = edit.getUpperRangeLongValue();
        var upperRangeLongValue = strUpperRangeLongValue == null ? null : Long.valueOf(strUpperRangeLongValue);
        var strUpperLimitLongValue = edit.getUpperLimitLongValue();
        var upperLimitLongValue = strUpperLimitLongValue == null ? null : Long.valueOf(strUpperLimitLongValue);
        var strLowerLimitLongValue = edit.getLowerLimitLongValue();
        var lowerLimitLongValue = strLowerLimitLongValue == null ? null : Long.valueOf(strLowerLimitLongValue);
        var strLowerRangeLongValue = edit.getLowerRangeLongValue();
        var lowerRangeLongValue = strLowerRangeLongValue == null ? null : Long.valueOf(strLowerRangeLongValue);
        var description = edit.getDescription();

        entityAttributeDetailValue.setEntityAttributeName(edit.getEntityAttributeName());
        entityAttributeDetailValue.setTrackRevisions(Boolean.valueOf(edit.getTrackRevisions()));
        entityAttributeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        EntityAttributeLogic.getInstance().updateEntityAttributeFromValue(session, entityAttributeDetailValue, partyPK);

        var entityAttributeTypeName = entityAttribute.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();
        var entityAttributeType = EntityAttributeTypes.valueOf(entityAttributeTypeName);

        switch(entityAttributeType) {
            case BLOB -> {
                var entityAttributeBlobValue = coreControl.getEntityAttributeBlobValueForUpdate(entityAttribute);

                entityAttributeBlobValue.setCheckContentWebAddress(checkContentWebAddress);
                coreControl.updateEntityAttributeBlobFromValue(entityAttributeBlobValue, partyPK);
            }
            case STRING -> {
                var entityAttributeString = coreControl.getEntityAttributeStringForUpdate(entityAttribute);

                if(entityAttributeString == null && validationPattern != null) {
                    coreControl.createEntityAttributeString(entityAttribute, validationPattern, partyPK);
                } else if(entityAttributeString != null) {
                    if(validationPattern == null) {
                        coreControl.deleteEntityAttributeString(entityAttributeString, partyPK);
                    } else {
                        var entityAttributeStringValue = coreControl.getEntityAttributeStringValue(entityAttributeString);

                        entityAttributeStringValue.setValidationPattern(validationPattern);
                        coreControl.updateEntityAttributeStringFromValue(entityAttributeStringValue, partyPK);
                    }
                }
            }
            case INTEGER -> {
                var entityAttributeInteger = coreControl.getEntityAttributeIntegerForUpdate(entityAttribute);
                var entityAttributeNumeric = coreControl.getEntityAttributeNumericForUpdate(entityAttribute);

                if(entityAttributeInteger == null && (upperRangeIntegerValue != null || upperLimitIntegerValue != null || lowerLimitIntegerValue != null || lowerRangeIntegerValue != null)) {
                    coreControl.createEntityAttributeInteger(entityAttribute, upperRangeIntegerValue, upperLimitIntegerValue, lowerLimitIntegerValue, lowerRangeIntegerValue, partyPK);
                } else if(entityAttributeInteger != null) {
                    if(upperRangeIntegerValue == null && upperLimitIntegerValue == null && lowerLimitIntegerValue == null && lowerRangeIntegerValue == null) {
                        coreControl.deleteEntityAttributeInteger(entityAttributeInteger, partyPK);
                    } else {
                        var entityAttributeIntegerValue = coreControl.getEntityAttributeIntegerValue(entityAttributeInteger);

                        entityAttributeIntegerValue.setUpperRangeIntegerValue(upperRangeIntegerValue);
                        entityAttributeIntegerValue.setUpperLimitIntegerValue(upperLimitIntegerValue);
                        entityAttributeIntegerValue.setLowerLimitIntegerValue(lowerLimitIntegerValue);
                        entityAttributeIntegerValue.setLowerRangeIntegerValue(lowerRangeIntegerValue);
                        coreControl.updateEntityAttributeIntegerFromValue(entityAttributeIntegerValue, partyPK);
                    }
                }

                if(entityAttributeNumeric == null && unitOfMeasureType != null) {
                    coreControl.createEntityAttributeNumeric(entityAttribute, unitOfMeasureType, partyPK);
                } else if(entityAttributeNumeric != null) {
                    if(unitOfMeasureType == null) {
                        coreControl.deleteEntityAttributeNumeric(entityAttributeNumeric, partyPK);
                    } else {
                        var entityAttributeNumericValue = coreControl.getEntityAttributeNumericValue(entityAttributeNumeric);

                        entityAttributeNumericValue.setUnitOfMeasureTypePK(unitOfMeasureType.getPrimaryKey());
                        coreControl.updateEntityAttributeNumericFromValue(entityAttributeNumericValue, partyPK);
                    }
                }
            }
            case LONG -> {
                var entityAttributeLong = coreControl.getEntityAttributeLongForUpdate(entityAttribute);
                var entityAttributeNumeric = coreControl.getEntityAttributeNumericForUpdate(entityAttribute);

                if(entityAttributeLong == null && (upperRangeLongValue != null || upperLimitLongValue != null || lowerLimitLongValue != null || lowerRangeLongValue != null)) {
                    coreControl.createEntityAttributeLong(entityAttribute, upperRangeLongValue, upperLimitLongValue, lowerLimitLongValue, lowerRangeLongValue, partyPK);
                } else if(entityAttributeLong != null) {
                    if(upperRangeLongValue == null && upperLimitLongValue == null && lowerLimitLongValue == null && lowerRangeLongValue == null) {
                        coreControl.deleteEntityAttributeLong(entityAttributeLong, partyPK);
                    } else {
                        var entityAttributeLongValue = coreControl.getEntityAttributeLongValue(entityAttributeLong);

                        entityAttributeLongValue.setUpperRangeLongValue(upperRangeLongValue);
                        entityAttributeLongValue.setUpperLimitLongValue(upperLimitLongValue);
                        entityAttributeLongValue.setLowerLimitLongValue(lowerLimitLongValue);
                        entityAttributeLongValue.setLowerRangeLongValue(lowerRangeLongValue);
                        coreControl.updateEntityAttributeLongFromValue(entityAttributeLongValue, partyPK);
                    }
                }

                if(entityAttributeNumeric == null && unitOfMeasureType != null) {
                    coreControl.createEntityAttributeNumeric(entityAttribute, unitOfMeasureType, partyPK);
                } else if(entityAttributeNumeric != null) {
                    if(unitOfMeasureType == null) {
                        coreControl.deleteEntityAttributeNumeric(entityAttributeNumeric, partyPK);
                    } else {
                        var entityAttributeNumericValue = coreControl.getEntityAttributeNumericValue(entityAttributeNumeric);

                        entityAttributeNumericValue.setUnitOfMeasureTypePK(unitOfMeasureType.getPrimaryKey());
                        coreControl.updateEntityAttributeNumericFromValue(entityAttributeNumericValue, partyPK);
                    }
                }
            }
            case LISTITEM, MULTIPLELISTITEM -> {
                var entityAttributeListItem = coreControl.getEntityAttributeListItemForUpdate(entityAttribute);

                if(entityAttributeListItem == null && entityListItemSequence != null) {
                    coreControl.createEntityAttributeListItem(entityAttribute, entityListItemSequence, partyPK);
                } else if(entityAttributeListItem != null) {
                    if(entityListItemSequence == null) {
                        coreControl.deleteEntityAttributeListItem(entityAttributeListItem, partyPK);
                    } else {
                        var entityAttributeListItemValue = coreControl.getEntityAttributeListItemValue(entityAttributeListItem);

                        entityAttributeListItemValue.setEntityListItemSequencePK(entityListItemSequence.getPrimaryKey());
                        coreControl.updateEntityAttributeListItemFromValue(entityAttributeListItemValue, partyPK);
                    }
                }
            }
            default -> {
            }
        }
        
        if(entityAttributeDescription == null && description != null) {
            coreControl.createEntityAttributeDescription(entityAttribute, getPreferredLanguage(), description, partyPK);
        } else if(entityAttributeDescription != null) {
            if(description == null) {
                coreControl.deleteEntityAttributeDescription(entityAttributeDescription, partyPK);
            } else {
                var entityAttributeDescriptionValue = coreControl.getEntityAttributeDescriptionValue(entityAttributeDescription);

                entityAttributeDescriptionValue.setDescription(description);
                coreControl.updateEntityAttributeDescriptionFromValue(entityAttributeDescriptionValue, partyPK);
            }
        }
    }
    
}
