package com.echothree.control.user.invoice.server.command;

import com.echothree.control.user.invoice.common.edit.InvoiceEditFactory;
import com.echothree.control.user.invoice.common.edit.InvoiceTypeEdit;
import com.echothree.control.user.invoice.common.result.EditInvoiceTypeResult;
import com.echothree.control.user.invoice.common.result.InvoiceResultFactory;
import com.echothree.control.user.invoice.common.spec.InvoiceTypeSpec;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.invoice.server.entity.InvoiceType;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditInvoiceTypeCommand
        extends BaseAbstractEditCommand<InvoiceTypeSpec, InvoiceTypeEdit, EditInvoiceTypeResult, InvoiceType, InvoiceType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.InvoiceType.name(), SecurityRoles.Edit.name())
                        ))
                ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InvoiceTypeName", FieldType.ENTITY_NAME, true, null, null)
                );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InvoiceTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentInvoiceTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("InvoiceSequenceTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                );
    }

    @Inject
    InvoiceControl invoiceControl;

    @Inject
    SequenceControl sequenceControl;

    /** Creates a new instance of EditInvoiceTypeCommand */
    public EditInvoiceTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditInvoiceTypeResult getResult() {
        return InvoiceResultFactory.getEditInvoiceTypeResult();
    }

    @Override
    public InvoiceTypeEdit getEdit() {
        return InvoiceEditFactory.getInvoiceTypeEdit();
    }

    @Override
    public InvoiceType getEntity(EditInvoiceTypeResult result) {
        var invoiceTypeName = spec.getInvoiceTypeName();
        var invoiceType = invoiceControl.getInvoiceTypeByName(invoiceTypeName, editModeToEntityPermission(editMode));

        if(invoiceType == null) {
            addExecutionError(ExecutionErrors.UnknownInvoiceTypeName.name(), invoiceTypeName);
        }

        return invoiceType;
    }

    @Override
    public InvoiceType getLockEntity(InvoiceType invoiceType) {
        return invoiceType;
    }

    @Override
    public void fillInResult(EditInvoiceTypeResult result, InvoiceType invoiceType) {
        result.setInvoiceType(invoiceControl.getInvoiceTypeTransfer(getUserVisit(), invoiceType));
    }

    @Override
    public void doLock(InvoiceTypeEdit edit, InvoiceType invoiceType) {
        var invoiceTypeDescription = invoiceControl.getInvoiceTypeDescription(invoiceType, getPreferredLanguage());
        var invoiceTypeDetail = invoiceType.getLastDetail();
        var parentInvoiceType = invoiceTypeDetail.getParentInvoiceType();
        var invoiceSequenceType = invoiceTypeDetail.getInvoiceSequenceType();

        edit.setInvoiceTypeName(invoiceTypeDetail.getInvoiceTypeName());
        edit.setParentInvoiceTypeName(parentInvoiceType == null? null: parentInvoiceType.getLastDetail().getInvoiceTypeName());
        edit.setInvoiceSequenceTypeName(invoiceSequenceType == null? null: invoiceSequenceType.getLastDetail().getSequenceTypeName());
        edit.setIsDefault(invoiceTypeDetail.getIsDefault().toString());
        edit.setSortOrder(invoiceTypeDetail.getSortOrder().toString());

        if(invoiceTypeDescription != null) {
            edit.setDescription(invoiceTypeDescription.getDescription());
        }
    }

    InvoiceType parentInvoiceType;
    SequenceType invoiceSequenceType;

    @Override
    public void canUpdate(InvoiceType invoiceType) {
        var invoiceTypeName = edit.getInvoiceTypeName();
        var duplicateInvoiceType = invoiceControl.getInvoiceTypeByName(invoiceTypeName);

        if(duplicateInvoiceType == null || invoiceType.equals(duplicateInvoiceType)) {
            var parentInvoiceTypeName = edit.getParentInvoiceTypeName();

            if(parentInvoiceTypeName != null) {
                parentInvoiceType = invoiceControl.getInvoiceTypeByName(parentInvoiceTypeName);
            }

            if(parentInvoiceTypeName == null || parentInvoiceType != null) {
                if(invoiceControl.isParentInvoiceTypeSafe(invoiceType, parentInvoiceType)) {
                    var invoiceSequenceTypeName = edit.getInvoiceSequenceTypeName();

                    if(invoiceSequenceTypeName != null) {
                        invoiceSequenceType = sequenceControl.getSequenceTypeByName(invoiceSequenceTypeName);
                    }

                    if(invoiceSequenceTypeName != null && invoiceSequenceType == null) {
                        addExecutionError(ExecutionErrors.UnknownInvoiceSequenceTypeName.name(), invoiceSequenceTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.InvalidParentInvoiceType.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownParentInvoiceTypeName.name(), parentInvoiceTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateInvoiceTypeName.name(), invoiceTypeName);
        }
    }

    @Override
    public void doUpdate(InvoiceType invoiceType) {
        var partyPK = getPartyPK();
        var invoiceTypeDetailValue = invoiceControl.getInvoiceTypeDetailValueForUpdate(invoiceType);
        var invoiceTypeDescription = invoiceControl.getInvoiceTypeDescriptionForUpdate(invoiceType, getPreferredLanguage());
        var description = edit.getDescription();

        invoiceTypeDetailValue.setInvoiceTypeName(edit.getInvoiceTypeName());
        invoiceTypeDetailValue.setParentInvoiceTypePK(parentInvoiceType == null? null: parentInvoiceType.getPrimaryKey());
        invoiceTypeDetailValue.setInvoiceSequenceTypePK(invoiceSequenceType == null? null: invoiceSequenceType.getPrimaryKey());
        invoiceTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        invoiceTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        invoiceControl.updateInvoiceTypeFromValue(invoiceTypeDetailValue, partyPK);

        if(invoiceTypeDescription == null && description != null) {
            invoiceControl.createInvoiceTypeDescription(invoiceType, getPreferredLanguage(), description, partyPK);
        } else if(invoiceTypeDescription != null && description == null) {
            invoiceControl.deleteInvoiceTypeDescription(invoiceTypeDescription, partyPK);
        } else if(invoiceTypeDescription != null && description != null) {
            var invoiceTypeDescriptionValue = invoiceControl.getInvoiceTypeDescriptionValue(invoiceTypeDescription);

            invoiceTypeDescriptionValue.setDescription(description);
            invoiceControl.updateInvoiceTypeDescriptionFromValue(invoiceTypeDescriptionValue, partyPK);
        }
    }

}
