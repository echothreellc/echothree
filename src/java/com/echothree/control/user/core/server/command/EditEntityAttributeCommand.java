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

import com.echothree.control.user.core.remote.edit.CoreEditFactory;
import com.echothree.control.user.core.remote.edit.EntityAttributeEdit;
import com.echothree.control.user.core.remote.form.EditEntityAttributeForm;
import com.echothree.control.user.core.remote.result.CoreResultFactory;
import com.echothree.control.user.core.remote.result.EditEntityAttributeResult;
import com.echothree.control.user.core.remote.spec.EntityAttributeSpec;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.common.SequenceConstants;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceTypeLogic;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityAttributeBlob;
import com.echothree.model.data.core.server.entity.EntityAttributeDescription;
import com.echothree.model.data.core.server.entity.EntityAttributeDetail;
import com.echothree.model.data.core.server.entity.EntityAttributeInteger;
import com.echothree.model.data.core.server.entity.EntityAttributeListItem;
import com.echothree.model.data.core.server.entity.EntityAttributeLong;
import com.echothree.model.data.core.server.entity.EntityAttributeNumeric;
import com.echothree.model.data.core.server.entity.EntityAttributeString;
import com.echothree.model.data.core.server.entity.EntityAttributeType;
import com.echothree.model.data.core.server.value.EntityAttributeBlobValue;
import com.echothree.model.data.core.server.value.EntityAttributeDescriptionValue;
import com.echothree.model.data.core.server.value.EntityAttributeDetailValue;
import com.echothree.model.data.core.server.value.EntityAttributeIntegerValue;
import com.echothree.model.data.core.server.value.EntityAttributeListItemValue;
import com.echothree.model.data.core.server.value.EntityAttributeLongValue;
import com.echothree.model.data.core.server.value.EntityAttributeNumericValue;
import com.echothree.model.data.core.server.value.EntityAttributeStringValue;
import com.echothree.model.data.party.remote.pk.PartyPK;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureTypeDetail;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.form.ValidationResult;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.Validator;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditEntityAttributeCommand
        extends BaseAbstractEditCommand<EntityAttributeSpec, EntityAttributeEdit, EditEntityAttributeResult, EntityAttribute, EntityAttribute> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityAttributeGroup.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrackRevisions", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditEntityAttributeCommand */
    public EditEntityAttributeCommand(UserVisitPK userVisitPK, EditEntityAttributeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected ValidationResult validateEdit(Validator validator) {
        ValidationResult validationResult = validator.validate(edit, getEditFieldDefinitions());
        
        if(!validationResult.getHasErrors()) {
            entityAttribute = EntityAttributeLogic.getInstance().getEntityAttributeByName(this,
                    spec.getComponentVendorName(), spec.getEntityTypeName(), spec.getEntityAttributeName());
            
            if(!hasExecutionErrors()) {
                EntityAttributeType entityAttributeType = entityAttribute.getLastDetail().getEntityAttributeType();
    
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
        entityAttribute = EntityAttributeLogic.getInstance().getEntityAttributeByName(this,
                spec.getComponentVendorName(), spec.getEntityTypeName(), spec.getEntityAttributeName(),
                editModeToEntityPermission(editMode));

        return entityAttribute;
    }

    @Override
    public EntityAttribute getLockEntity(EntityAttribute entityAttribute) {
        return entityAttribute;
    }

    @Override
    public void fillInResult(EditEntityAttributeResult result, EntityAttribute entityAttribute) {
        CoreControl coreControl = getCoreControl();

        result.setEntityAttribute(coreControl.getEntityAttributeTransfer(getUserVisit(), entityAttribute, null));
    }

    Sequence entityListItemSequence = null;
    UnitOfMeasureType unitOfMeasureType = null;
    
    @Override
    public void doLock(EntityAttributeEdit edit, EntityAttribute entityAttribute) {
        CoreControl coreControl = getCoreControl();
        EntityAttributeDescription entityAttributeDescription = coreControl.getEntityAttributeDescription(entityAttribute, getPreferredLanguage());
        EntityAttributeDetail entityAttributeDetail = entityAttribute.getLastDetail();
        String entityAttributeTypeName = entityAttribute.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();
        EntityAttributeTypes entityAttributeType = EntityAttributeTypes.valueOf(entityAttributeTypeName);
        
        edit.setEntityAttributeName(entityAttributeDetail.getEntityAttributeName());
        edit.setTrackRevisions(entityAttributeDetail.getTrackRevisions().toString());
        edit.setSortOrder(entityAttributeDetail.getSortOrder().toString());
        
        switch(entityAttributeType) {
            case BLOB:
                EntityAttributeBlob entityAttributeBlob = coreControl.getEntityAttributeBlobForUpdate(entityAttribute);
                
                edit.setCheckContentWebAddress(entityAttributeBlob.getCheckContentWebAddress().toString());
                break;
            case STRING:
                EntityAttributeString entityAttributeString = coreControl.getEntityAttributeStringForUpdate(entityAttribute);
                
                if(entityAttributeString != null) {
                    edit.setValidationPattern(entityAttributeString.getValidationPattern());
                }
                break;
            case INTEGER: {
                EntityAttributeInteger entityAttributeInteger = coreControl.getEntityAttributeIntegerForUpdate(entityAttribute);
                EntityAttributeNumeric entityAttributeNumeric = coreControl.getEntityAttributeNumericForUpdate(entityAttribute);
                
                if(entityAttributeInteger != null) {
                    edit.setUpperLimitIntegerValue(entityAttributeInteger.getUpperLimitIntegerValue().toString());
                    edit.setUpperRangeIntegerValue(entityAttributeInteger.getUpperRangeIntegerValue().toString());
                    edit.setLowerRangeIntegerValue(entityAttributeInteger.getLowerRangeIntegerValue().toString());
                    edit.setLowerLimitIntegerValue(entityAttributeInteger.getLowerLimitIntegerValue().toString());
                }
                
                if(entityAttributeNumeric != null) {
                    unitOfMeasureType = entityAttributeNumeric.getUnitOfMeasureType();

                    if(unitOfMeasureType != null) {
                        UnitOfMeasureTypeDetail unitOfMeasureTypeDetail = unitOfMeasureType.getLastDetail();

                        edit.setUnitOfMeasureKindName(unitOfMeasureTypeDetail.getUnitOfMeasureKind().getLastDetail().getUnitOfMeasureKindName());
                        edit.setUnitOfMeasureTypeName(unitOfMeasureTypeDetail.getUnitOfMeasureTypeName());
                    }
                }
            }
            break;
            case LONG: {
                EntityAttributeLong entityAttributeLong = coreControl.getEntityAttributeLongForUpdate(entityAttribute);
                EntityAttributeNumeric entityAttributeNumeric = coreControl.getEntityAttributeNumericForUpdate(entityAttribute);
                
                if(entityAttributeLong != null) {
                    edit.setUpperLimitLongValue(entityAttributeLong.getUpperLimitLongValue().toString());
                    edit.setUpperRangeLongValue(entityAttributeLong.getUpperRangeLongValue().toString());
                    edit.setLowerRangeLongValue(entityAttributeLong.getLowerRangeLongValue().toString());
                    edit.setLowerLimitLongValue(entityAttributeLong.getLowerLimitLongValue().toString());
                }
                
                if(entityAttributeNumeric != null) {
                    unitOfMeasureType = entityAttributeNumeric.getUnitOfMeasureType();

                    if(unitOfMeasureType != null) {
                        UnitOfMeasureTypeDetail unitOfMeasureTypeDetail = unitOfMeasureType.getLastDetail();

                        edit.setUnitOfMeasureKindName(unitOfMeasureTypeDetail.getUnitOfMeasureKind().getLastDetail().getUnitOfMeasureKindName());
                        edit.setUnitOfMeasureTypeName(unitOfMeasureTypeDetail.getUnitOfMeasureTypeName());
                    }
                }
            }
            break;
            case LISTITEM:
            case MULTIPLELISTITEM:
                EntityAttributeListItem entityAttributeListItem = coreControl.getEntityAttributeListItemForUpdate(entityAttribute);
                
                if(entityAttributeListItem != null) {
                    entityListItemSequence = entityAttributeListItem.getEntityListItemSequence();
                    
                    edit.setEntityListItemSequenceName(entityListItemSequence == null ? null : entityListItemSequence.getLastDetail().getSequenceName());
                }
                break;
            default:
                break;
        }
        
        if(entityAttributeDescription != null) {
            edit.setDescription(entityAttributeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(EntityAttribute entityAttribute) {
        CoreControl coreControl = getCoreControl();
        String entityAttributeName = edit.getEntityAttributeName();
        EntityAttribute duplicateEntityAttribute = coreControl.getEntityAttributeByName(entityAttribute.getLastDetail().getEntityType(),
                entityAttributeName);

        if(duplicateEntityAttribute == null || entityAttribute.equals(duplicateEntityAttribute)) {
            String entityAttributeTypeName = entityAttribute.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();
            String entityListItemSequenceName = entityAttributeTypeName.equals(EntityAttributeTypes.LISTITEM.name())
                    || entityAttributeTypeName.equals(EntityAttributeTypes.MULTIPLELISTITEM.name()) ?
                    edit.getEntityListItemSequenceName() : null;

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
                String unitOfMeasureKindName = edit.getUnitOfMeasureKindName();
                String unitOfMeasureTypeName = edit.getUnitOfMeasureTypeName();
                int parameterCount = (unitOfMeasureKindName == null ? 0 : 1) + (unitOfMeasureTypeName == null ? 0 : 1);
                
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
        CoreControl coreControl = getCoreControl();
        PartyPK partyPK = getPartyPK();
        EntityAttributeDetailValue entityAttributeDetailValue = coreControl.getEntityAttributeDetailValueForUpdate(entityAttribute);
        EntityAttributeDescription entityAttributeDescription = coreControl.getEntityAttributeDescriptionForUpdate(entityAttribute, getPreferredLanguage());
        String strCheckContentWebAddress = edit.getCheckContentWebAddress();
        Boolean checkContentWebAddress = strCheckContentWebAddress == null ? null : Boolean.valueOf(strCheckContentWebAddress);
        String validationPattern = edit.getValidationPattern();
        String strUpperRangeIntegerValue = edit.getUpperRangeIntegerValue();
        Integer upperRangeIntegerValue = strUpperRangeIntegerValue == null ? null : Integer.valueOf(strUpperRangeIntegerValue);
        String strUpperLimitIntegerValue = edit.getUpperLimitIntegerValue();
        Integer upperLimitIntegerValue = strUpperLimitIntegerValue == null ? null : Integer.valueOf(strUpperLimitIntegerValue);
        String strLowerLimitIntegerValue = edit.getLowerLimitIntegerValue();
        Integer lowerLimitIntegerValue = strLowerLimitIntegerValue == null ? null : Integer.valueOf(strLowerLimitIntegerValue);
        String strLowerRangeIntegerValue = edit.getLowerRangeIntegerValue();
        Integer lowerRangeIntegerValue = strLowerRangeIntegerValue == null ? null : Integer.valueOf(strLowerRangeIntegerValue);
        String strUpperRangeLongValue = edit.getUpperRangeLongValue();
        Long upperRangeLongValue = strUpperRangeLongValue == null ? null : Long.valueOf(strUpperRangeLongValue);
        String strUpperLimitLongValue = edit.getUpperLimitLongValue();
        Long upperLimitLongValue = strUpperLimitLongValue == null ? null : Long.valueOf(strUpperLimitLongValue);
        String strLowerLimitLongValue = edit.getLowerLimitLongValue();
        Long lowerLimitLongValue = strLowerLimitLongValue == null ? null : Long.valueOf(strLowerLimitLongValue);
        String strLowerRangeLongValue = edit.getLowerRangeLongValue();
        Long lowerRangeLongValue = strLowerRangeLongValue == null ? null : Long.valueOf(strLowerRangeLongValue);
        String description = edit.getDescription();

        entityAttributeDetailValue.setEntityAttributeName(edit.getEntityAttributeName());
        entityAttributeDetailValue.setTrackRevisions(Boolean.valueOf(edit.getTrackRevisions()));
        entityAttributeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        EntityAttributeLogic.getInstance().updateEntityAttributeFromValue(session, entityAttributeDetailValue, partyPK);

        String entityAttributeTypeName = entityAttribute.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();
        EntityAttributeTypes entityAttributeType = EntityAttributeTypes.valueOf(entityAttributeTypeName);
        
        switch(entityAttributeType) {
            case BLOB:
                EntityAttributeBlobValue entityAttributeBlobValue = coreControl.getEntityAttributeBlobValueForUpdate(entityAttribute);
                
                entityAttributeBlobValue.setCheckContentWebAddress(checkContentWebAddress);
                coreControl.updateEntityAttributeBlobFromValue(entityAttributeBlobValue, partyPK);
                break;
            case STRING:
                EntityAttributeString entityAttributeString = coreControl.getEntityAttributeStringForUpdate(entityAttribute);
                
                if(entityAttributeString == null && validationPattern != null) {
                    coreControl.createEntityAttributeString(entityAttribute, validationPattern, partyPK);
                } else if(entityAttributeString != null) {
                    if(validationPattern == null) {
                        coreControl.deleteEntityAttributeString(entityAttributeString, partyPK);
                    } else {
                        EntityAttributeStringValue entityAttributeStringValue = coreControl.getEntityAttributeStringValue(entityAttributeString);
                        
                        entityAttributeStringValue.setValidationPattern(validationPattern);
                        coreControl.updateEntityAttributeStringFromValue(entityAttributeStringValue, partyPK);
                    }
                }
                break;
            case INTEGER: {
                EntityAttributeInteger entityAttributeInteger = coreControl.getEntityAttributeIntegerForUpdate(entityAttribute);
                EntityAttributeNumeric entityAttributeNumeric = coreControl.getEntityAttributeNumericForUpdate(entityAttribute);
                
                if(entityAttributeInteger == null && (upperRangeIntegerValue != null || upperLimitIntegerValue != null
                        || lowerLimitIntegerValue != null || lowerRangeIntegerValue != null)) {
                    coreControl.createEntityAttributeInteger(entityAttribute, upperRangeIntegerValue, upperLimitIntegerValue,
                            lowerLimitIntegerValue, lowerRangeIntegerValue, partyPK);
                } else if(entityAttributeInteger != null) {
                    if(upperRangeIntegerValue == null && upperLimitIntegerValue == null && lowerLimitIntegerValue == null
                            && lowerRangeIntegerValue == null) {
                        coreControl.deleteEntityAttributeInteger(entityAttributeInteger, partyPK);
                    } else {
                        EntityAttributeIntegerValue entityAttributeIntegerValue = coreControl.getEntityAttributeIntegerValue(entityAttributeInteger);
                        
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
                        EntityAttributeNumericValue entityAttributeNumericValue = coreControl.getEntityAttributeNumericValue(entityAttributeNumeric);
                        
                        entityAttributeNumericValue.setUnitOfMeasureTypePK(unitOfMeasureType.getPrimaryKey());
                        coreControl.updateEntityAttributeNumericFromValue(entityAttributeNumericValue, partyPK);
                    }
                }
            }
            break;
            case LONG: {
                EntityAttributeLong entityAttributeLong = coreControl.getEntityAttributeLongForUpdate(entityAttribute);
                EntityAttributeNumeric entityAttributeNumeric = coreControl.getEntityAttributeNumericForUpdate(entityAttribute);
                
                if(entityAttributeLong == null && (upperRangeLongValue != null || upperLimitLongValue != null
                        || lowerLimitLongValue != null || lowerRangeLongValue != null)) {
                    coreControl.createEntityAttributeLong(entityAttribute, upperRangeLongValue, upperLimitLongValue,
                            lowerLimitLongValue, lowerRangeLongValue, partyPK);
                } else if(entityAttributeLong != null) {
                    if(upperRangeLongValue == null && upperLimitLongValue == null && lowerLimitLongValue == null
                            && lowerRangeLongValue == null) {
                        coreControl.deleteEntityAttributeLong(entityAttributeLong, partyPK);
                    } else {
                        EntityAttributeLongValue entityAttributeLongValue = coreControl.getEntityAttributeLongValue(entityAttributeLong);
                        
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
                        EntityAttributeNumericValue entityAttributeNumericValue = coreControl.getEntityAttributeNumericValue(entityAttributeNumeric);
                        
                        entityAttributeNumericValue.setUnitOfMeasureTypePK(unitOfMeasureType.getPrimaryKey());
                        coreControl.updateEntityAttributeNumericFromValue(entityAttributeNumericValue, partyPK);
                    }
                }
            }
            break;
            case LISTITEM:
            case MULTIPLELISTITEM:
                EntityAttributeListItem entityAttributeListItem = coreControl.getEntityAttributeListItemForUpdate(entityAttribute);
                
                if(entityAttributeListItem == null && entityListItemSequence != null) {
                    coreControl.createEntityAttributeListItem(entityAttribute, entityListItemSequence, partyPK);
                } else if(entityAttributeListItem != null) {
                    if(entityAttributeListItem == null) {
                        coreControl.deleteEntityAttributeListItem(entityAttributeListItem, partyPK);
                    } else {
                        EntityAttributeListItemValue entityAttributeListItemValue = coreControl.getEntityAttributeListItemValue(entityAttributeListItem);
                        
                        entityAttributeListItemValue.setEntityListItemSequencePK(entityListItemSequence.getPrimaryKey());
                        coreControl.updateEntityAttributeListItemFromValue(entityAttributeListItemValue, partyPK);
                    }
                }
                break;
            default:
                break;
        }
        
        if(entityAttributeDescription == null && description != null) {
            coreControl.createEntityAttributeDescription(entityAttribute, getPreferredLanguage(), description, partyPK);
        } else if(entityAttributeDescription != null) {
            if(description == null) {
                coreControl.deleteEntityAttributeDescription(entityAttributeDescription, partyPK);
            } else {
                EntityAttributeDescriptionValue entityAttributeDescriptionValue = coreControl.getEntityAttributeDescriptionValue(entityAttributeDescription);

                entityAttributeDescriptionValue.setDescription(description);
                coreControl.updateEntityAttributeDescriptionFromValue(entityAttributeDescriptionValue, partyPK);
            }
        }
    }
    
}
