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

import com.echothree.control.user.core.common.edit.EntityAttributeEdit;
import com.echothree.control.user.core.common.form.CreateEntityAttributeForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceTypeLogic;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.control.workflow.server.logic.WorkflowLogic;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityAttributeType;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.form.ValidationResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.Validator;
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
    private final static List<FieldDefinition> WORKFLOW_FORM_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> OTHER_FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityAttribute.name(), SecurityRoles.Create.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null),
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, false, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityAttributeTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrackRevisions", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
        
        BLOB_FORM_FIELD_DEFINITIONS = List.of(
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
                new FieldDefinition("EntityListItemSequenceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, false, null, null)
        );
        
        STRING_FORM_FIELD_DEFINITIONS = List.of(
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
                new FieldDefinition("EntityListItemSequenceName", FieldType.NULL, false, null, null),
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, false, null, null)
        );
        
        INTEGER_FORM_FIELD_DEFINITIONS = List.of(
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
                new FieldDefinition("EntityListItemSequenceName", FieldType.NULL, false, null, null),
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, false, null, null)
        );
        
        LONG_FORM_FIELD_DEFINITIONS = List.of(
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
                new FieldDefinition("EntityListItemSequenceName", FieldType.NULL, false, null, null),
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, false, null, null)
        );
        
        LISTITEM_FORM_FIELD_DEFINITIONS = List.of(
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
                new FieldDefinition("EntityListItemSequenceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, false, null, null)
        );
        
        MULTIPLELISTITEM_FORM_FIELD_DEFINITIONS = List.of(
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
                new FieldDefinition("EntityListItemSequenceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, false, null, null)
        );

        WORKFLOW_FORM_FIELD_DEFINITIONS = List.of(
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
                new FieldDefinition("EntityListItemSequenceName", FieldType.NULL, false, null, null),
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, true, null, null)
        );

        OTHER_FORM_FIELD_DEFINITIONS = List.of(
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
                new FieldDefinition("EntityListItemSequenceName", FieldType.NULL, false, null, null),
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, false, null, null)
        );
    }
    
    /** Creates a new instance of CreateEntityAttributeCommand */
    public CreateEntityAttributeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    public static ValidationResult AdditionalEntityAttributeValidation(EntityAttributeEdit edit, Validator validator,
            EntityAttributeType entityAttributeType) {
        return switch(EntityAttributeTypes.valueOf(entityAttributeType.getEntityAttributeTypeName())) {
            case BLOB -> validator.validate(edit, BLOB_FORM_FIELD_DEFINITIONS);
            case STRING -> validator.validate(edit, STRING_FORM_FIELD_DEFINITIONS);
            case INTEGER -> validator.validate(edit, INTEGER_FORM_FIELD_DEFINITIONS);
            case LONG -> validator.validate(edit, LONG_FORM_FIELD_DEFINITIONS);
            case LISTITEM -> validator.validate(edit, LISTITEM_FORM_FIELD_DEFINITIONS);
            case MULTIPLELISTITEM -> validator.validate(edit, MULTIPLELISTITEM_FORM_FIELD_DEFINITIONS);
            case WORKFLOW -> validator.validate(edit, WORKFLOW_FORM_FIELD_DEFINITIONS);
            default -> validator.validate(edit, OTHER_FORM_FIELD_DEFINITIONS);
        };
    }
    
    @Override
    protected ValidationResult validate() {
        var validator = new Validator(this);
        var validationResult = validator.validate(form, FORM_FIELD_DEFINITIONS);
        
        if(!validationResult.getHasErrors()) {
            var entityAttributeType = EntityAttributeLogic.getInstance().getEntityAttributeTypeByName(this, form.getEntityAttributeTypeName());
            
            if(!hasExecutionErrors()) {
                validationResult = AdditionalEntityAttributeValidation(form, validator, entityAttributeType);
            }
        }
        
        return validationResult;
    }
    
    @Override
    protected BaseResult execute() {
        var result = CoreResultFactory.getCreateEntityAttributeResult();
        EntityAttribute entityAttribute = null;
        
        if(!hasExecutionErrors()) {
            var unitOfMeasureKindName = form.getUnitOfMeasureKindName();
            var unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
            var parameterCount = (unitOfMeasureKindName == null ? 0 : 1) + (unitOfMeasureTypeName == null ? 0 : 1);

            if(parameterCount == 0 || parameterCount == 2) {
                var entityType = EntityTypeLogic.getInstance().getEntityTypeByUniversalSpec(this, form);
                var entityAttributeTypeName = form.getEntityAttributeTypeName();
                var entityAttributeType = EntityAttributeLogic.getInstance().getEntityAttributeTypeByName(this, entityAttributeTypeName);

                if(!hasExecutionErrors()) {
                    var entityListItemSequenceName = entityAttributeTypeName.equals(EntityAttributeTypes.LISTITEM.name())
                            || entityAttributeTypeName.equals(EntityAttributeTypes.MULTIPLELISTITEM.name()) ?
                            form.getEntityListItemSequenceName() : null;
                    Sequence entityListItemSequence = null;

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
                        UnitOfMeasureType unitOfMeasureType = null;

                        if(parameterCount == 2) {
                            unitOfMeasureType = UnitOfMeasureTypeLogic.getInstance().getUnitOfMeasureTypeByName(this, unitOfMeasureKindName, unitOfMeasureTypeName);
                        }

                        if(!hasExecutionErrors()) {
                            var workflowName = form.getWorkflowName();
                            Workflow workflow = null;

                            if(workflowName != null) {
                                workflow = WorkflowLogic.getInstance().getWorkflowByName(this, workflowName);
                            }

                            if(!hasExecutionErrors()) {
                                var partyPK = getPartyPK();
                                var entityAttributeName = form.getEntityAttributeName();
                                var trackRevisions = Boolean.valueOf(form.getTrackRevisions());
                                var strCheckContentWebAddress = form.getCheckContentWebAddress();
                                var checkContentWebAddress = strCheckContentWebAddress == null ? null : Boolean.valueOf(strCheckContentWebAddress);
                                var validationPattern = form.getValidationPattern();
                                var strUpperRangeIntegerValue = form.getUpperRangeIntegerValue();
                                var upperRangeIntegerValue = strUpperRangeIntegerValue == null ? null : Integer.valueOf(strUpperRangeIntegerValue);
                                var strUpperLimitIntegerValue = form.getUpperLimitIntegerValue();
                                var upperLimitIntegerValue = strUpperLimitIntegerValue == null ? null : Integer.valueOf(strUpperLimitIntegerValue);
                                var strLowerLimitIntegerValue = form.getLowerLimitIntegerValue();
                                var lowerLimitIntegerValue = strLowerLimitIntegerValue == null ? null : Integer.valueOf(strLowerLimitIntegerValue);
                                var strLowerRangeIntegerValue = form.getLowerRangeIntegerValue();
                                var lowerRangeIntegerValue = strLowerRangeIntegerValue == null ? null : Integer.valueOf(strLowerRangeIntegerValue);
                                var strUpperRangeLongValue = form.getUpperRangeLongValue();
                                var upperRangeLongValue = strUpperRangeLongValue == null ? null : Long.valueOf(strUpperRangeLongValue);
                                var strUpperLimitLongValue = form.getUpperLimitLongValue();
                                var upperLimitLongValue = strUpperLimitLongValue == null ? null : Long.valueOf(strUpperLimitLongValue);
                                var strLowerLimitLongValue = form.getLowerLimitLongValue();
                                var lowerLimitLongValue = strLowerLimitLongValue == null ? null : Long.valueOf(strLowerLimitLongValue);
                                var strLowerRangeLongValue = form.getLowerRangeLongValue();
                                var lowerRangeLongValue = strLowerRangeLongValue == null ? null : Long.valueOf(strLowerRangeLongValue);
                                var sortOrder = Integer.valueOf(form.getSortOrder());
                                var description = form.getDescription();

                                entityAttribute = EntityAttributeLogic.getInstance().createEntityAttribute(this, entityType,
                                        entityAttributeName, entityAttributeType, trackRevisions, checkContentWebAddress,
                                        validationPattern, upperRangeIntegerValue, upperLimitIntegerValue, lowerLimitIntegerValue,
                                        lowerRangeIntegerValue, upperRangeLongValue, upperLimitLongValue, lowerLimitLongValue,
                                        lowerRangeLongValue, entityListItemSequence, unitOfMeasureType, workflow, sortOrder,
                                        partyPK, getPreferredLanguage(), description);
                            }
                        }
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.InvalidParameterCount.name());
            }
        }
        
        if(entityAttribute != null) {
            var basePK = entityAttribute.getPrimaryKey();

            result.setEntityAttributeName(entityAttribute.getLastDetail().getEntityAttributeName());
            result.setEntityRef(basePK.getEntityRef());
        }
        
        return result;
    }

}
