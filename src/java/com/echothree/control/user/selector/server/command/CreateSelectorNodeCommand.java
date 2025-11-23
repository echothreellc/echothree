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

package com.echothree.control.user.selector.server.command;

import com.echothree.control.user.selector.common.form.CreateSelectorNodeForm;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.payment.server.control.PaymentMethodControl;
import com.echothree.model.control.payment.server.control.PaymentProcessorControl;
import com.echothree.model.control.selector.common.SelectorNodeTypes;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.control.selector.server.logic.SelectorNodeTypeLogic;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategory;
import com.echothree.model.data.core.server.entity.EntityListItem;
import com.echothree.model.data.employee.server.entity.ResponsibilityType;
import com.echothree.model.data.employee.server.entity.SkillType;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.item.server.entity.ItemCategory;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.model.data.payment.server.entity.PaymentProcessor;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.selector.server.entity.SelectorBooleanType;
import com.echothree.model.data.selector.server.entity.SelectorNode;
import com.echothree.model.data.selector.server.entity.SelectorNodeType;
import com.echothree.model.data.training.server.entity.TrainingClass;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.vendor.server.entity.ItemPurchasingCategory;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.form.ValidationResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.Validator;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CreateSelectorNodeCommand
        extends BaseSimpleCommand<CreateSelectorNodeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> booleanFormFieldDefinitions;
    private final static List<FieldDefinition> entityListItemFormFieldDefinitions;
    private final static List<FieldDefinition> responsibilityTypeFormFieldDefinitions;
    private final static List<FieldDefinition> skillTypeFormFieldDefinitions;
    private final static List<FieldDefinition> trainingClassFormFieldDefinitions;
    private final static List<FieldDefinition> workflowStepFormFieldDefinitions;
    private final static List<FieldDefinition> itemCategoryFormFieldDefinitions;
    private final static List<FieldDefinition> itemAccountingCategoryFormFieldDefinitions;
    private final static List<FieldDefinition> itemPurchasingCategoryFormFieldDefinitions;
    private final static List<FieldDefinition> paymentMethodFormFieldDefinitions;
    private final static List<FieldDefinition> paymentProcessorFormFieldDefinitions;
    private final static List<FieldDefinition> geoCodeFormFieldDefinitions;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SelectorKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SelectorTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SelectorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SelectorNodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsRootSelectorNode", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SelectorNodeTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Negate", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
        
        booleanFormFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SelectorBooleanTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LeftSelectorNodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("RightSelectorNodeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        entityListItemFormFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityListItemName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        responsibilityTypeFormFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ResponsibilityTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        skillTypeFormFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SkillTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        trainingClassFormFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TrainingClassName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        workflowStepFormFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowStepName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        itemCategoryFormFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemCategoryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CheckParents", FieldType.BOOLEAN, true, null, null)
                ));
        
        itemAccountingCategoryFormFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemAccountingCategoryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CheckParents", FieldType.BOOLEAN, true, null, null)
                ));
        
        itemPurchasingCategoryFormFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemPurchasingCategoryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CheckParents", FieldType.BOOLEAN, true, null, null)
                ));
        
        paymentMethodFormFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PaymentMethodName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        paymentProcessorFormFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PaymentProcessorName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        geoCodeFormFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GeoCodeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CountryName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreateSelectorNodeCommand */
    public CreateSelectorNodeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected ValidationResult validate() {
        var validator = new Validator(this);
        var validationResult = validator.validate(form, FORM_FIELD_DEFINITIONS);
        
        if(!validationResult.getHasErrors()) {
            var selectorNodeType = SelectorNodeTypeLogic.getInstance().getSelectorNodeTypeByName(this, form.getSelectorNodeTypeName());
            
            if(!hasExecutionErrors()) {
                var selectorNodeTypeEnum = SelectorNodeTypes.valueOf(selectorNodeType.getSelectorNodeTypeName());

                switch(selectorNodeTypeEnum) {
                    case BOOLEAN ->
                            validationResult = validator.validate(form, booleanFormFieldDefinitions);
                    case ENTITY_LIST_ITEM ->
                            validationResult = validator.validate(form, entityListItemFormFieldDefinitions);
                    case RESPONSIBILITY_TYPE ->
                            validationResult = validator.validate(form, responsibilityTypeFormFieldDefinitions);
                    case SKILL_TYPE ->
                            validationResult = validator.validate(form, skillTypeFormFieldDefinitions);
                    case TRAINING_CLASS ->
                            validationResult = validator.validate(form, trainingClassFormFieldDefinitions);
                    case WORKFLOW_STEP ->
                            validationResult = validator.validate(form, workflowStepFormFieldDefinitions);
                    case ITEM_CATEGORY ->
                            validationResult = validator.validate(form, itemCategoryFormFieldDefinitions);
                    case ITEM_ACCOUNTING_CATEGORY ->
                            validationResult = validator.validate(form, itemAccountingCategoryFormFieldDefinitions);
                    case ITEM_PURCHASING_CATEGORY ->
                            validationResult = validator.validate(form, itemPurchasingCategoryFormFieldDefinitions);
                    case PAYMENT_METHOD ->
                            validationResult = validator.validate(form, paymentMethodFormFieldDefinitions);
                    case PAYMENT_PROCESSOR ->
                            validationResult = validator.validate(form, paymentProcessorFormFieldDefinitions);
                    case GEO_CODE ->
                            validationResult = validator.validate(form, geoCodeFormFieldDefinitions);
                    default -> {
                    }
                }
            }
        }
        
        return validationResult;
    }
    
    private abstract class BaseSelectorNodeType {
        SelectorControl selectorControl;
        SelectorNodeType selectorNodeType;
        
        private BaseSelectorNodeType(SelectorControl selectorControl, String selectorNodeTypeName) {
            this.selectorControl = selectorControl;
            selectorNodeType = selectorControl.getSelectorNodeTypeByName(selectorNodeTypeName);
            
            if(selectorNodeType == null) {
                addExecutionError(ExecutionErrors.UnknownSelectorNodeTypeName.name(), selectorNodeTypeName);
            }
        }
        
        abstract void execute(SelectorNode selectorNode, PartyPK partyPK);
    }
    
    private abstract class AccountingSelectorNodeType
        extends BaseSelectorNodeType {
        AccountingControl accountingControl = Session.getModelController(AccountingControl.class);

        private AccountingSelectorNodeType(SelectorControl selectorControl, String selectorNodeTypeName) {
            super(selectorControl, selectorNodeTypeName);
        }
    }
    
    private abstract class CoreSelectorNodeType
        extends BaseSelectorNodeType {
        private CoreSelectorNodeType(SelectorControl selectorControl, String selectorNodeTypeName) {
            super(selectorControl, selectorNodeTypeName);
        }
    }
    
    private abstract class EmployeeSelectorNodeType
        extends BaseSelectorNodeType {
        EmployeeControl employeeControl = Session.getModelController(EmployeeControl.class);

        private EmployeeSelectorNodeType(SelectorControl selectorControl, String selectorNodeTypeName) {
            super(selectorControl, selectorNodeTypeName);
        }
    }
    
    private abstract class GeoSelectorNodeType
        extends BaseSelectorNodeType {
        GeoControl geoControl = Session.getModelController(GeoControl.class);

        private GeoSelectorNodeType(SelectorControl selectorControl, String selectorNodeTypeName) {
            super(selectorControl, selectorNodeTypeName);
        }
    }
    
    private abstract class ItemSelectorNodeType
        extends BaseSelectorNodeType {
        ItemControl itemControl = Session.getModelController(ItemControl.class);

        private ItemSelectorNodeType(SelectorControl selectorControl, String selectorNodeTypeName) {
            super(selectorControl, selectorNodeTypeName);
        }
    }

    private abstract class PaymentProcessorSelectorNodeType
            extends BaseSelectorNodeType {
        PaymentProcessorControl paymentProcessorControl = Session.getModelController(PaymentProcessorControl.class);

        private PaymentProcessorSelectorNodeType(SelectorControl selectorControl, String selectorNodeTypeName) {
            super(selectorControl, selectorNodeTypeName);
        }
    }

    private abstract class PaymentMethodSelectorNodeType
            extends BaseSelectorNodeType {
        PaymentMethodControl paymentMethodControl = Session.getModelController(PaymentMethodControl.class);

        private PaymentMethodSelectorNodeType(SelectorControl selectorControl, String selectorNodeTypeName) {
            super(selectorControl, selectorNodeTypeName);
        }
    }

    private abstract class TrainingSelectorNodeType
        extends BaseSelectorNodeType {
        TrainingControl trainingControl = Session.getModelController(TrainingControl.class);

        private TrainingSelectorNodeType(SelectorControl selectorControl, String selectorNodeTypeName) {
            super(selectorControl, selectorNodeTypeName);
        }
    }
    
    private abstract class VendorSelectorNodeType
        extends BaseSelectorNodeType {
        VendorControl vendorControl = Session.getModelController(VendorControl.class);

        private VendorSelectorNodeType(SelectorControl selectorControl, String selectorNodeTypeName) {
            super(selectorControl, selectorNodeTypeName);
        }
    }
    
    private abstract class WorkSelectorNodeType
        extends BaseSelectorNodeType {
        WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);

        private WorkSelectorNodeType(SelectorControl selectorControl, String selectorNodeTypeName) {
            super(selectorControl, selectorNodeTypeName);
        }
    }
    
    private class BooleanNodeType
        extends BaseSelectorNodeType {
        SelectorBooleanType selectorBooleanType = null;
        SelectorNode leftSelectorNode = null;
        SelectorNode rightSelectorNode = null;
        
        private BooleanNodeType(SelectorControl selectorControl, Selector selector) {
            super(selectorControl, SelectorNodeTypes.BOOLEAN.name());
            
            if(!hasExecutionErrors()) {
                var selectorBooleanTypeName = form.getSelectorBooleanTypeName();
                selectorBooleanType = selectorControl.getSelectorBooleanTypeByName(selectorBooleanTypeName);
                
                if(selectorBooleanType != null) {
                    var leftSelectorNodeName = form.getLeftSelectorNodeName();
                    leftSelectorNode = selectorControl.getSelectorNodeByName(selector, leftSelectorNodeName);
                    
                    if(leftSelectorNode != null) {
                        var rightSelectorNodeName = form.getRightSelectorNodeName();
                        rightSelectorNode = selectorControl.getSelectorNodeByName(selector, rightSelectorNodeName);
                        
                        if(rightSelectorNode != null) {
                            if(rightSelectorNode.equals(leftSelectorNode)) {
                                addExecutionError(ExecutionErrors.IdenticalLeftAndRightSelectorNodes.name(), leftSelectorNodeName, rightSelectorNodeName);
                            }
                            
                            // TODO: Circular Node Check
                            // TODO: Sensible Root Node Check
                        } else {
                            addExecutionError(ExecutionErrors.UnknownRightSelectorNodeName.name(), rightSelectorNodeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownLeftSelectorNodeName.name(), leftSelectorNodeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownSelectorBooleanTypeName.name(), selectorBooleanTypeName);
                }
            }
        }
        
        @Override
        public void execute(SelectorNode selectorNode, PartyPK partyPK) {
            selectorControl.createSelectorNodeBoolean(selectorNode, selectorBooleanType, leftSelectorNode, rightSelectorNode,
                    partyPK);
        }
    }
    
    private class EntityListItemNodeType
        extends CoreSelectorNodeType {
        EntityListItem entityListItem = null;
        
        private EntityListItemNodeType(SelectorControl selectorControl) {
            super(selectorControl, SelectorNodeTypes.ENTITY_LIST_ITEM.name());
            
            if(!hasExecutionErrors()) {
                var componentVendorName = form.getComponentVendorName();
                var componentVendor = componentControl.getComponentVendorByName(componentVendorName);
                
                if(componentVendor != null) {
                    var entityTypeName = form.getEntityTypeName();
                    var entityType = entityTypeControl.getEntityTypeByName(componentVendor, entityTypeName);
                    
                    if(entityType != null) {
                        var entityAttributeName = form.getEntityAttributeName();
                        var entityAttribute = coreControl.getEntityAttributeByName(entityType, entityAttributeName);
                        
                        if(entityAttribute != null) {
                            var entityListItemName = form.getEntityListItemName();
                            entityListItem = coreControl.getEntityListItemByName(entityAttribute, entityListItemName);
                            
                            if(entityListItem == null) {
                                addExecutionError(ExecutionErrors.UnknownEntityListItemName.name(), entityListItemName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownEntityAttributeName.name(), entityAttributeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownEntityTypeName.name(), entityTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
                }
            }
        }
        
        @Override
        public void execute(SelectorNode selectorNode, PartyPK partyPK) {
            selectorControl.createSelectorNodeEntityListItem(selectorNode, entityListItem, partyPK);
        }
    }
    
    private class ResponsibilityNodeType
        extends EmployeeSelectorNodeType {
        private ResponsibilityType responsiblityType = null;

        private ResponsibilityNodeType(SelectorControl selectorControl) {
            super(selectorControl, SelectorNodeTypes.RESPONSIBILITY_TYPE.name());
            
            if(!hasExecutionErrors()) {
                var responsiblityTypeName = form.getResponsibilityTypeName();
                
                responsiblityType = employeeControl.getResponsibilityTypeByName(responsiblityTypeName);
                
                if(responsiblityType == null) {
                    addExecutionError(ExecutionErrors.UnknownResponsibilityTypeName.name(), responsiblityTypeName);
                }
            }
        }
        
        @Override
        public void execute(SelectorNode selectorNode, PartyPK partyPK) {
            selectorControl.createSelectorNodeResponsibilityType(selectorNode, responsiblityType, partyPK);
        }
    }
    
    private class SkillNodeType
        extends EmployeeSelectorNodeType {
        private SkillType skillType = null;
        
        private SkillNodeType(SelectorControl selectorControl) {
            super(selectorControl, SelectorNodeTypes.SKILL_TYPE.name());
            
            if(!hasExecutionErrors()) {
                var skillTypeName = form.getSkillTypeName();
                
                skillType = employeeControl.getSkillTypeByName(skillTypeName);
                
                if(skillType == null) {
                    addExecutionError(ExecutionErrors.UnknownSkillTypeName.name(), skillTypeName);
                }
            }
        }
        
        @Override
        public void execute(SelectorNode selectorNode, PartyPK partyPK) {
            selectorControl.createSelectorNodeSkillType(selectorNode, skillType, partyPK);
        }
    }
    
    private class TrainingClassNodeType
        extends TrainingSelectorNodeType {
        private TrainingClass trainingClass = null;

        private TrainingClassNodeType(SelectorControl selectorControl) {
            super(selectorControl, SelectorNodeTypes.TRAINING_CLASS.name());
            
            if(!hasExecutionErrors()) {
                var trainingClassName = form.getTrainingClassName();
                
                trainingClass = trainingControl.getTrainingClassByName(trainingClassName);
                
                if(trainingClass == null) {
                    addExecutionError(ExecutionErrors.UnknownTrainingClassName.name(), trainingClassName);
                }
            }
        }
        
        @Override
        public void execute(SelectorNode selectorNode, PartyPK partyPK) {
            selectorControl.createSelectorNodeTrainingClass(selectorNode, trainingClass, partyPK);
        }
    }
    
    private class WorkflowStepNodeType
        extends WorkSelectorNodeType {
        private WorkflowStep workflowStep = null;

        private WorkflowStepNodeType(SelectorControl selectorControl) {
            super(selectorControl, SelectorNodeTypes.TRAINING_CLASS.name());
            
            if(!hasExecutionErrors()) {
                var workflowName = form.getWorkflowName();
                var workflow = workflowControl.getWorkflowByName(workflowName);
                
                if(workflow != null) {
                    var workflowStepName = form.getWorkflowStepName();
                    
                    workflowStep = workflowControl.getWorkflowStepByName(workflow, workflowStepName);
                    
                    if(workflowStep == null) {
                        addExecutionError(ExecutionErrors.UnknownWorkflowStepName.name(), workflowStepName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownWorkflowName.name(), workflowName);
                }
            }
        }
        
        @Override
        public void execute(SelectorNode selectorNode, PartyPK partyPK) {
            selectorControl.createSelectorNodeWorkflowStep(selectorNode, workflowStep, partyPK);
        }
    }
    
    private class ItemCategoryNodeType
        extends ItemSelectorNodeType {
        private ItemCategory itemCategory = null;

        private ItemCategoryNodeType(SelectorControl selectorControl) {
            super(selectorControl, SelectorNodeTypes.ITEM_CATEGORY.name());
            
            if(!hasExecutionErrors()) {
                var itemCategoryName = form.getItemCategoryName();
                
                itemCategory = itemControl.getItemCategoryByName(itemCategoryName);
                
                if(itemCategory == null) {
                    addExecutionError(ExecutionErrors.UnknownItemCategoryName.name(), itemCategoryName);
                }
            }
        }
        
        @Override
        public void execute(SelectorNode selectorNode, PartyPK partyPK) {
            var checkParents = Boolean.valueOf(form.getCheckParents());
            
            selectorControl.createSelectorNodeItemCategory(selectorNode, itemCategory, checkParents, partyPK);
        }
    }
    
    private class ItemAccountingCategoryNodeType
        extends AccountingSelectorNodeType {
        private ItemAccountingCategory itemAccountingCategory = null;

        private ItemAccountingCategoryNodeType(SelectorControl selectorControl) {
            super(selectorControl, SelectorNodeTypes.ITEM_ACCOUNTING_CATEGORY.name());
            
            if(!hasExecutionErrors()) {
                var itemAccountingCategoryName = form.getItemAccountingCategoryName();
                
                itemAccountingCategory = accountingControl.getItemAccountingCategoryByName(itemAccountingCategoryName);
                
                if(itemAccountingCategory == null) {
                    addExecutionError(ExecutionErrors.UnknownItemAccountingCategoryName.name(), itemAccountingCategoryName);
                }
            }
        }
        
        @Override
        public void execute(SelectorNode selectorNode, PartyPK partyPK) {
            var checkParents = Boolean.valueOf(form.getCheckParents());
            
            selectorControl.createSelectorNodeItemAccountingCategory(selectorNode, itemAccountingCategory, checkParents, partyPK);
        }
    }
    
    private class ItemPurchasingCategoryNodeType
        extends VendorSelectorNodeType {
        private ItemPurchasingCategory itemPurchasingCategory = null;
        
        private ItemPurchasingCategoryNodeType(SelectorControl selectorControl) {
            super(selectorControl, SelectorNodeTypes.ITEM_PURCHASING_CATEGORY.name());
            
            if(!hasExecutionErrors()) {
                var itemPurchasingCategoryName = form.getItemPurchasingCategoryName();
                
                itemPurchasingCategory = vendorControl.getItemPurchasingCategoryByName(itemPurchasingCategoryName);
                
                if(itemPurchasingCategory == null) {
                    addExecutionError(ExecutionErrors.UnknownItemPurchasingCategoryName.name(), itemPurchasingCategoryName);
                }
            }
        }
        
        @Override
        public void execute(SelectorNode selectorNode, PartyPK partyPK) {
            var checkParents = Boolean.valueOf(form.getCheckParents());
            
            selectorControl.createSelectorNodeItemPurchasingCategory(selectorNode, itemPurchasingCategory, checkParents, partyPK);
        }
    }
    
    private class PaymentMethodNodeType
        extends PaymentMethodSelectorNodeType {
        private PaymentMethod paymentMethod = null;

        private PaymentMethodNodeType(SelectorControl selectorControl) {
            super(selectorControl, SelectorNodeTypes.PAYMENT_METHOD.name());
            
            if(!hasExecutionErrors()) {
                var paymentMethodName = form.getPaymentMethodName();
                
                paymentMethod = paymentMethodControl.getPaymentMethodByName(paymentMethodName);
                
                if(paymentMethod == null) {
                    addExecutionError(ExecutionErrors.UnknownPaymentMethodName.name(), paymentMethodName);
                }
            }
        }
        
        @Override
        public void execute(SelectorNode selectorNode, PartyPK partyPK) {
            selectorControl.createSelectorNodePaymentMethod(selectorNode, paymentMethod, partyPK);
        }
    }
    
    private class PaymentProcessorNodeType
        extends PaymentProcessorSelectorNodeType {
        private PaymentProcessor paymentProcessor = null;

        private PaymentProcessorNodeType(SelectorControl selectorControl) {
            super(selectorControl, SelectorNodeTypes.PAYMENT_PROCESSOR.name());
            
            if(!hasExecutionErrors()) {
                var paymentProcessorName = form.getPaymentProcessorName();
                
                paymentProcessor = paymentProcessorControl.getPaymentProcessorByName(paymentProcessorName);
                
                if(paymentProcessor == null) {
                    addExecutionError(ExecutionErrors.UnknownPaymentProcessorName.name(), paymentProcessorName);
                }
            }
        }
        
        @Override
        public void execute(SelectorNode selectorNode, PartyPK partyPK) {
            selectorControl.createSelectorNodePaymentProcessor(selectorNode, paymentProcessor, partyPK);
        }
    }
    
    private class GeoCodeNodeType
        extends GeoSelectorNodeType {
        private GeoCode geoCode = null;

        private GeoCodeNodeType(SelectorControl selectorControl) {
            super(selectorControl, SelectorNodeTypes.PAYMENT_PROCESSOR.name());
            
            if(!hasExecutionErrors()) {
                var geoCodeName = form.getGeoCodeName();
                var countryName = form.getCountryName();
                var parameterCount = (geoCodeName == null ? 0 : 1) + (countryName == null ? 0 : 1);
                
                if(parameterCount == 1) {
                    if(countryName != null) {
                        geoCode = geoControl.getCountryByAlias(countryName);
                        
                        if(geoCode == null) {
                            addExecutionError(ExecutionErrors.UnknownCountryName.name(), countryName);
                        }
                    } else {
                        geoCode = geoControl.getGeoCodeByName(geoCodeName);
                        
                        if(geoCode == null) {
                            addExecutionError(ExecutionErrors.UnknownGeoCodeName.name(), geoCodeName);
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.InvalidParameterCount.name());
                }
            }
        }
        
        @Override
        public void execute(SelectorNode selectorNode, PartyPK partyPK) {
            selectorControl.createSelectorNodeGeoCode(selectorNode, geoCode, partyPK);
        }
    }
    
    @Override
    protected BaseResult execute() {
        if(!hasExecutionErrors()) {
            var selectorControl = Session.getModelController(SelectorControl.class);
            var selectorKindName = form.getSelectorKindName();
            var selectorKind = selectorControl.getSelectorKindByName(selectorKindName);

            if(selectorKind != null) {
                var selectorTypeName = form.getSelectorTypeName();
                var selectorType = selectorControl.getSelectorTypeByName(selectorKind, selectorTypeName);

                if(selectorType != null) {
                    var selectorName = form.getSelectorName();
                    var selector = selectorControl.getSelectorByName(selectorType, selectorName);

                    if(selector != null) {
                        var selectorNodeName = form.getSelectorNodeName();
                        var selectorNode = selectorControl.getSelectorNodeByName(selector, selectorNodeName);

                        if(selectorNode == null) {
                            var selectorNodeTypeName = form.getSelectorNodeTypeName();
                            var selectorNodeType = selectorControl.getSelectorNodeTypeByName(selectorNodeTypeName);

                            if(selectorNodeType != null) {
                                selectorNodeTypeName = selectorNodeType.getSelectorNodeTypeName();
                                
                                if(selectorControl.getSelectorNodeTypeUse(selectorKind, selectorNodeType) != null) {
                                    var selectorNodeTypeEnum = SelectorNodeTypes.valueOf(selectorNodeTypeName);
                                    BaseSelectorNodeType baseSelectorNodeType = null;

                                    switch(selectorNodeTypeEnum) {
                                        case BOOLEAN ->
                                                baseSelectorNodeType = new BooleanNodeType(selectorControl, selector);
                                        case ENTITY_LIST_ITEM ->
                                                baseSelectorNodeType = new EntityListItemNodeType(selectorControl);
                                        case RESPONSIBILITY_TYPE ->
                                                baseSelectorNodeType = new ResponsibilityNodeType(selectorControl);
                                        case SKILL_TYPE ->
                                                baseSelectorNodeType = new SkillNodeType(selectorControl);
                                        case TRAINING_CLASS ->
                                                baseSelectorNodeType = new TrainingClassNodeType(selectorControl);
                                        case WORKFLOW_STEP ->
                                                baseSelectorNodeType = new WorkflowStepNodeType(selectorControl);
                                        case ITEM_CATEGORY ->
                                                baseSelectorNodeType = new ItemCategoryNodeType(selectorControl);
                                        case ITEM_ACCOUNTING_CATEGORY ->
                                                baseSelectorNodeType = new ItemAccountingCategoryNodeType(selectorControl);
                                        case ITEM_PURCHASING_CATEGORY ->
                                                baseSelectorNodeType = new ItemPurchasingCategoryNodeType(selectorControl);
                                        case PAYMENT_METHOD ->
                                                baseSelectorNodeType = new PaymentMethodNodeType(selectorControl);
                                        case PAYMENT_PROCESSOR ->
                                                baseSelectorNodeType = new PaymentProcessorNodeType(selectorControl);
                                        case GEO_CODE ->
                                                baseSelectorNodeType = new GeoCodeNodeType(selectorControl);
                                        default -> {
                                        }
                                    }

                                    if(!hasExecutionErrors()) {
                                        var partyPK = getPartyPK();
                                        var isRootSelectorNode = Boolean.valueOf(form.getIsRootSelectorNode());
                                        var negate = Boolean.valueOf(form.getNegate());
                                        var description = form.getDescription();

                                        selectorNode = selectorControl.createSelectorNode(selector, selectorNodeName, isRootSelectorNode,
                                                selectorNodeType, negate, partyPK);

                                        baseSelectorNodeType.execute(selectorNode, partyPK);

                                        if(description != null) {
                                            var language = getPreferredLanguage();

                                            selectorControl.createSelectorNodeDescription(selectorNode, language, description, partyPK);
                                        }
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownSelectorNodeTypeUse.name(), selectorKindName, selectorNodeTypeName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), selectorTypeName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.DuplicateSelectorNodeName.name(), selectorNodeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownSelectorName.name(), selectorName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), selectorTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), selectorKindName);
            }
        }
        
        return null;
    }
    
}
