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

package com.echothree.control.user.communication.server.server;

import com.echothree.control.user.communication.common.form.CreateCommunicationEventForm;
import com.echothree.model.control.accounting.common.AccountingConstants;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.communication.common.CommunicationConstants;
import com.echothree.model.control.communication.common.workflow.IncomingCustomerEmailConstants;
import com.echothree.model.control.communication.server.control.CommunicationControl;
import com.echothree.model.control.contact.common.ContactMechanismPurposes;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.contact.server.logic.ContactEmailAddressLogic;
import com.echothree.model.control.core.common.MimeTypes;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.customer.common.workflow.CustomerCreditStatusConstants;
import com.echothree.model.control.customer.common.workflow.CustomerStatusConstants;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.document.common.DocumentConstants;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.control.offer.server.control.SourceControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.control.workeffort.server.logic.WorkEffortLogic;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.enterprise.context.Dependent;

@Dependent
public class CreateCommunicationEventCommand
        extends BaseSimpleCommand<CreateCommunicationEventForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CommunicationSourceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CommunicationEventTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ClobDocument", FieldType.STRING, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreateCommunicationEventCommand */
    public CreateCommunicationEventCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var communicationControl = Session.getModelController(CommunicationControl.class);
        var communicationSourceName = form.getCommunicationSourceName();
        var communicationSource = communicationControl.getCommunicationSourceByName(communicationSourceName);

        if(communicationSource != null) {
            var workEffortLogic = WorkEffortLogic.getInstance();
            var communicationEmailSource = communicationControl.getCommunicationEmailSource(communicationSource);
            var receiveWorkEffortScope = communicationEmailSource.getReceiveWorkEffortScope();
            var preparedWorkEffort = workEffortLogic.prepareForWorkEffort(this, receiveWorkEffortScope, null, null, null);

            if(!hasExecutionErrors()) {
                var communicationEventTypeName = form.getCommunicationEventTypeName();
                var communicationEventType = communicationControl.getCommunicationEventTypeByName(communicationEventTypeName);

                if(communicationEventType != null) {
                    var contactControl = Session.getModelController(ContactControl.class);
                    var documentControl = Session.getModelController(DocumentControl.class);
                    var workflowControl = Session.getModelController(WorkflowControl.class);
                    var createdBy = getPartyPK();
                    Party party = null;
                    PartyContactMechanism partyContactMechanism = null;
                    var communicationSourceType = communicationSource.getLastDetail().getCommunicationSourceType();
                    var communicationSourceTypeName = communicationSourceType.getCommunicationSourceTypeName();
                    var blobDocument = form.getBlobDocument();
                    var clobDocument = form.getClobDocument();

                    communicationEventTypeName = communicationEventType.getCommunicationEventTypeName();

                    if(communicationSourceTypeName.equals(CommunicationConstants.CommunicationSourceType_EMAIL)
                            && communicationEventTypeName.equals(CommunicationConstants.CommunicationEventType_EMAIL)
                            && clobDocument == null && blobDocument != null) {
                        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
                        String description = null;
                        String emailAddress = null;

                        try {
                            var props = System.getProperties();
                            var mailSession = javax.mail.Session.getInstance(props);
                            var bais = new ByteArrayInputStream(blobDocument.byteArrayValue());
                            var mimeMessage = new MimeMessage(mailSession, bais);

                            description = mimeMessage.getSubject();

                            if(description != null) {
                                var length = description.length();

                                if(length > 80) {
                                    description = description.substring(0, 80);
                                } else if(length == 0) {
                                    description = null;
                                }
                            }

                            var addresses = mimeMessage.getFrom();
                            if(addresses != null && addresses.length > 0) {
                                var address = addresses[0];

                                if(address instanceof InternetAddress) {
                                    emailAddress = ((InternetAddress)address).getAddress();
                                }
                            }

                            if(emailAddress != null) {
                                var emailAddressLength = emailAddress.length();

                                if(emailAddressLength > 80 || emailAddressLength == 0) {
                                    addExecutionError(ExecutionErrors.InvalidEmailAddressLength.name(), emailAddress);
                                }
                            }
                        } catch(MessagingException me) {
                            addExecutionError(ExecutionErrors.InvalidRfc822Message.name());
                        }

                        if(!hasExecutionErrors()) {
                            var partyContactMechanisms = contactControl.getPartyContactMechanismsByEmailAddress(emailAddress);

                            if(partyContactMechanisms.isEmpty()) {
                                var sourceControl = Session.getModelController(SourceControl.class);
                                var source = sourceControl.getDefaultSource();

                                if(source != null) {
                                    var customerControl = Session.getModelController(CustomerControl.class);
                                    var customerType = customerControl.getDefaultCustomerType();

                                    if(customerType != null) {
                                        var termControl = Session.getModelController(TermControl.class);
                                        var customerTypeDetail = customerType.getLastDetail();
                                        var term = customerTypeDetail.getDefaultTerm();

                                        if(term == null) {
                                            term = termControl.getDefaultTerm();
                                        }

                                        if(term != null) {
                                            var defaultArGlAccount = customerTypeDetail.getDefaultArGlAccount();

                                            if(defaultArGlAccount == null) {
                                                var accountingControl = Session.getModelController(AccountingControl.class);
                                                var glAccountCategory = accountingControl.getGlAccountCategoryByName(AccountingConstants.GlAccountCategory_ACCOUNTS_RECEIVABLE);

                                                if(glAccountCategory != null) {
                                                    defaultArGlAccount = accountingControl.getDefaultGlAccount(glAccountCategory);
                                                } else {
                                                    addExecutionError(ExecutionErrors.UnknownGlAccountCategoryName.name(), AccountingConstants.GlAccountCategory_ACCOUNTS_RECEIVABLE);
                                                }
                                            }

                                            if(defaultArGlAccount != null) {
                                                var partyControl = Session.getModelController(PartyControl.class);
                                                var defaultOfferUse = source.getLastDetail().getOfferUse();
                                                var partyType = partyControl.getPartyTypeByName(PartyTypes.CUSTOMER.name());

                                                party = partyControl.createParty(null, partyType, null, null, null, null, createdBy);

                                                customerControl.createCustomer(party, customerType, defaultOfferUse, null, null, defaultArGlAccount,
                                                        customerTypeDetail.getDefaultHoldUntilComplete(), customerTypeDetail.getDefaultAllowBackorders(),
                                                        customerTypeDetail.getDefaultAllowSubstitutions(), customerTypeDetail.getDefaultAllowCombiningShipments(),
                                                        customerTypeDetail.getDefaultRequireReference(), customerTypeDetail.getDefaultAllowReferenceDuplicates(),
                                                        customerTypeDetail.getDefaultReferenceValidationPattern(), createdBy);

                                                partyContactMechanism = ContactEmailAddressLogic.getInstance().createContactEmailAddress(party,
                                                        emailAddress, false, null, ContactMechanismPurposes.PRIMARY_EMAIL.name(), createdBy);

                                                termControl.createPartyTerm(party, term, customerTypeDetail.getDefaultTaxable(), createdBy);

                                                var customerTypeCreditLimits = termControl.getCustomerTypeCreditLimitsByCustomerType(customerType);
                                                for(var customerTypeCreditLimit : customerTypeCreditLimits) {
                                                    termControl.createPartyCreditLimit(party, customerTypeCreditLimit.getCurrency(), customerTypeCreditLimit.getCreditLimit(),
                                                            customerTypeCreditLimit.getPotentialCreditLimit(), createdBy);
                                                }

                                                var customerStatusWorkflow = workflowControl.getWorkflowByName(CustomerStatusConstants.Workflow_CUSTOMER_STATUS);
                                                var customerStatusWorkflowEntrance = customerTypeDetail.getDefaultCustomerStatus();

                                                if(customerStatusWorkflowEntrance == null) {
                                                    customerStatusWorkflowEntrance = workflowControl.getDefaultWorkflowEntrance(customerStatusWorkflow);
                                                }

                                                var customerCreditStatusWorkflow = workflowControl.getWorkflowByName(CustomerCreditStatusConstants.Workflow_CUSTOMER_CREDIT_STATUS);
                                                var customerCreditStatusWorkflowEntrance = customerTypeDetail.getDefaultCustomerCreditStatus();

                                                if(customerCreditStatusWorkflowEntrance == null) {
                                                    customerCreditStatusWorkflowEntrance = workflowControl.getDefaultWorkflowEntrance(customerCreditStatusWorkflow);
                                                }

                                                var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(party.getPrimaryKey());
                                                workflowControl.addEntityToWorkflow(customerStatusWorkflowEntrance, entityInstance, null, null, createdBy);
                                                workflowControl.addEntityToWorkflow(customerCreditStatusWorkflowEntrance, entityInstance, null, null, createdBy);
                                            } else {
                                                addExecutionError(ExecutionErrors.UnknownDefaultArGlAccount.name());
                                            }
                                        } else {
                                            addExecutionError(ExecutionErrors.UnknownDefaultTerm.name());
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownDefaultCustomerType.name());
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownDefaultSource.name());
                                }
                            } else {
                                partyContactMechanism = partyContactMechanisms.get(0);

                                party = partyContactMechanism.getLastDetail().getParty();
                            }
                        }

                        if(!hasExecutionErrors()) {
                            var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
                            var documentType = documentControl.getDocumentTypeByName(DocumentConstants.DocumentType_COMMUNICATION_EVENT_EMAIL);
                            var mimeType = mimeTypeControl.getMimeTypeByName(MimeTypes.MESSAGE_RFC822.mimeTypeName());

                            var document = documentControl.createDocument(documentType, mimeType, null, createdBy);
                            documentControl.createDocumentBlob(document, blobDocument, createdBy);

                            if(description != null) {
                                documentControl.createDocumentDescription(document, getPreferredLanguage(), description, createdBy);
                            }

                            var communicationEventPurpose = communicationControl.getDefaultCommunicationEventPurpose();
                            var communicationEvent = communicationControl.createCommunicationEvent(communicationEventType, communicationSource, communicationEventPurpose, null, null,
                                    partyContactMechanism, document, createdBy);

                            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(communicationEvent.getPrimaryKey());
                            var workEffort = WorkEffortLogic.getInstance().createWorkEffort(preparedWorkEffort, entityInstance, createdBy);

                            var senderRoleType = communicationControl.getCommunicationEventRoleTypeByName(CommunicationConstants.CommunicationEventRoleType_SENDER);
                            communicationControl.createCommunicationEventRole(communicationEvent, party, senderRoleType, createdBy);

                            var communicationEventWorkflow = workflowControl.getWorkflowByName(IncomingCustomerEmailConstants.Workflow_INCOMING_CUSTOMER_EMAIL);
                            var communicationEventWorkflowEntrance = workflowControl.getWorkflowEntranceByName(communicationEventWorkflow,
                                    IncomingCustomerEmailConstants.WorkflowEntrance_NEW_COMMUNICATION_EVENT);
                            workflowControl.addEntityToWorkflow(communicationEventWorkflowEntrance, entityInstance, workEffort, null, createdBy);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.InvalidCommunicationEvent.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownCommunicationEventTypeName.name(), communicationEventTypeName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCommunicationSourceName.name(), communicationSourceName);
        }

        return null;
    }
    
}
