// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.communication.common.CommunicationConstants;
import com.echothree.model.control.communication.server.CommunicationControl;
import com.echothree.model.control.contact.server.ContactControl;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.customer.server.CustomerControl;
import com.echothree.model.control.document.common.DocumentConstants;
import com.echothree.model.control.document.server.DocumentControl;
import com.echothree.model.control.offer.server.OfferControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.term.server.TermControl;
import com.echothree.model.control.workeffort.server.logic.WorkEffortLogic;
import com.echothree.model.control.workeffort.server.logic.WorkEffortLogic.PreparedWorkEffort;
import com.echothree.model.control.customer.common.workflow.CustomerCreditStatusConstants;
import com.echothree.model.control.customer.common.workflow.CustomerStatusConstants;
import com.echothree.model.control.communication.common.workflow.IncomingCustomerEmailConstants;
import com.echothree.model.control.contact.common.ContactMechanismPurposes;
import com.echothree.model.control.contact.server.logic.ContactEmailAddressLogic;
import com.echothree.model.control.core.common.MimeTypes;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.accounting.server.entity.GlAccountCategory;
import com.echothree.model.data.communication.server.entity.CommunicationEmailSource;
import com.echothree.model.data.communication.server.entity.CommunicationEvent;
import com.echothree.model.data.communication.server.entity.CommunicationEventPurpose;
import com.echothree.model.data.communication.server.entity.CommunicationEventRoleType;
import com.echothree.model.data.communication.server.entity.CommunicationEventType;
import com.echothree.model.data.communication.server.entity.CommunicationSource;
import com.echothree.model.data.communication.server.entity.CommunicationSourceType;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.customer.server.entity.CustomerTypeDetail;
import com.echothree.model.data.document.server.entity.Document;
import com.echothree.model.data.document.server.entity.DocumentType;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.offer.server.entity.Source;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.term.server.entity.CustomerTypeCreditLimit;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workeffort.server.entity.WorkEffort;
import com.echothree.model.data.workeffort.server.entity.WorkEffortScope;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
    public CreateCommunicationEventCommand(UserVisitPK userVisitPK, CreateCommunicationEventForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        CommunicationControl communicationControl = (CommunicationControl)Session.getModelController(CommunicationControl.class);
        String communicationSourceName = form.getCommunicationSourceName();
        CommunicationSource communicationSource = communicationControl.getCommunicationSourceByName(communicationSourceName);

        if(communicationSource != null) {
            WorkEffortLogic workEffortLogic = WorkEffortLogic.getInstance();
            CommunicationEmailSource communicationEmailSource = communicationControl.getCommunicationEmailSource(communicationSource);
            WorkEffortScope receiveWorkEffortScope = communicationEmailSource.getReceiveWorkEffortScope();
            PreparedWorkEffort preparedWorkEffort = workEffortLogic.prepareForWorkEffort(this, receiveWorkEffortScope, null, null, null);

            if(!hasExecutionErrors()) {
                String communicationEventTypeName = form.getCommunicationEventTypeName();
                CommunicationEventType communicationEventType = communicationControl.getCommunicationEventTypeByName(communicationEventTypeName);

                if(communicationEventType != null) {
                    ContactControl contactControl = (ContactControl)Session.getModelController(ContactControl.class);
                    CoreControl coreControl = getCoreControl();
                    DocumentControl documentControl = (DocumentControl)Session.getModelController(DocumentControl.class);
                    WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
                    PartyPK createdBy = getPartyPK();
                    Party party = null;
                    PartyContactMechanism partyContactMechanism = null;
                    CommunicationSourceType communicationSourceType = communicationSource.getLastDetail().getCommunicationSourceType();
                    String communicationSourceTypeName = communicationSourceType.getCommunicationSourceTypeName();
                    ByteArray blobDocument = form.getBlobDocument();
                    String clobDocument = form.getClobDocument();

                    communicationEventTypeName = communicationEventType.getCommunicationEventTypeName();

                    if(communicationSourceTypeName.equals(CommunicationConstants.CommunicationSourceType_EMAIL)
                            && communicationEventTypeName.equals(CommunicationConstants.CommunicationEventType_EMAIL)
                            && clobDocument == null && blobDocument != null) {
                        String description = null;
                        String emailAddress = null;

                        try {
                            Properties props = System.getProperties();
                            javax.mail.Session mailSession = javax.mail.Session.getInstance(props);
                            ByteArrayInputStream bais = new ByteArrayInputStream(blobDocument.byteArrayValue());
                            MimeMessage mimeMessage = new MimeMessage(mailSession, bais);

                            description = mimeMessage.getSubject();

                            if(description != null) {
                                int length = description.length();

                                if(length > 80) {
                                    description = description.substring(0, 80);
                                } else if(length == 0) {
                                    description = null;
                                }
                            }

                            Address[] addresses = mimeMessage.getFrom();
                            if(addresses != null && addresses.length > 0) {
                                Address address = addresses[0];

                                if(address instanceof InternetAddress) {
                                    emailAddress = ((InternetAddress)address).getAddress();
                                }
                            }

                            if(emailAddress != null) {
                                int emailAddressLength = emailAddress.length();

                                if(emailAddressLength > 80 || emailAddressLength == 0) {
                                    addExecutionError(ExecutionErrors.InvalidEmailAddressLength.name(), emailAddress);
                                }
                            }
                        } catch(MessagingException me) {
                            addExecutionError(ExecutionErrors.InvalidRfc822Message.name());
                        }

                        if(!hasExecutionErrors()) {
                            List<PartyContactMechanism> partyContactMechanisms = contactControl.getPartyContactMechanismsByEmailAddress(emailAddress);

                            if(partyContactMechanisms.isEmpty()) {
                                OfferControl offerControl = (OfferControl)Session.getModelController(OfferControl.class);
                                Source source = offerControl.getDefaultSource();

                                if(source != null) {
                                    CustomerControl customerControl = (CustomerControl)Session.getModelController(CustomerControl.class);
                                    CustomerType customerType = customerControl.getDefaultCustomerType();

                                    if(customerType != null) {
                                        TermControl termControl = (TermControl)Session.getModelController(TermControl.class);
                                        CustomerTypeDetail customerTypeDetail = customerType.getLastDetail();
                                        Term term = customerTypeDetail.getDefaultTerm();

                                        if(term == null) {
                                            term = termControl.getDefaultTerm();
                                        }

                                        if(term != null) {
                                            GlAccount defaultArGlAccount = customerTypeDetail.getDefaultArGlAccount();

                                            if(defaultArGlAccount == null) {
                                                AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
                                                GlAccountCategory glAccountCategory = accountingControl.getGlAccountCategoryByName(AccountingConstants.GlAccountCategory_ACCOUNTS_RECEIVABLE);

                                                if(glAccountCategory != null) {
                                                    defaultArGlAccount = accountingControl.getDefaultGlAccount(glAccountCategory);
                                                } else {
                                                    addExecutionError(ExecutionErrors.UnknownGlAccountCategoryName.name(), AccountingConstants.GlAccountCategory_ACCOUNTS_RECEIVABLE);
                                                }
                                            }

                                            if(defaultArGlAccount != null) {
                                                PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                                                OfferUse defaultOfferUse = source.getLastDetail().getOfferUse();
                                                PartyType partyType = partyControl.getPartyTypeByName(PartyConstants.PartyType_CUSTOMER);

                                                party = partyControl.createParty(null, partyType, null, null, null, null, createdBy);

                                                customerControl.createCustomer(party, customerType, defaultOfferUse, null, null, defaultArGlAccount,
                                                        customerTypeDetail.getDefaultHoldUntilComplete(), customerTypeDetail.getDefaultAllowBackorders(),
                                                        customerTypeDetail.getDefaultAllowSubstitutions(), customerTypeDetail.getDefaultAllowCombiningShipments(),
                                                        customerTypeDetail.getDefaultRequireReference(), customerTypeDetail.getDefaultAllowReferenceDuplicates(),
                                                        customerTypeDetail.getDefaultReferenceValidationPattern(), createdBy);

                                                partyContactMechanism = ContactEmailAddressLogic.getInstance().createContactEmailAddress(party,
                                                        emailAddress, false, null, ContactMechanismPurposes.PRIMARY_EMAIL.name(), createdBy);

                                                termControl.createPartyTerm(party, term, customerTypeDetail.getDefaultTaxable(), createdBy);

                                                List<CustomerTypeCreditLimit> customerTypeCreditLimits = termControl.getCustomerTypeCreditLimitsByCustomerType(customerType);
                                                for(CustomerTypeCreditLimit customerTypeCreditLimit: customerTypeCreditLimits) {
                                                    termControl.createPartyCreditLimit(party, customerTypeCreditLimit.getCurrency(), customerTypeCreditLimit.getCreditLimit(),
                                                            customerTypeCreditLimit.getPotentialCreditLimit(), createdBy);
                                                }

                                                Workflow customerStatusWorkflow = workflowControl.getWorkflowByName(CustomerStatusConstants.Workflow_CUSTOMER_STATUS);
                                                WorkflowEntrance customerStatusWorkflowEntrance = customerTypeDetail.getDefaultCustomerStatus();

                                                if(customerStatusWorkflowEntrance == null) {
                                                    customerStatusWorkflowEntrance = workflowControl.getDefaultWorkflowEntrance(customerStatusWorkflow);
                                                }

                                                Workflow customerCreditStatusWorkflow = workflowControl.getWorkflowByName(CustomerCreditStatusConstants.Workflow_CUSTOMER_CREDIT_STATUS);
                                                WorkflowEntrance customerCreditStatusWorkflowEntrance = customerTypeDetail.getDefaultCustomerCreditStatus();

                                                if(customerCreditStatusWorkflowEntrance == null) {
                                                    customerCreditStatusWorkflowEntrance = workflowControl.getDefaultWorkflowEntrance(customerCreditStatusWorkflow);
                                                }

                                                EntityInstance entityInstance = getCoreControl().getEntityInstanceByBasePK(party.getPrimaryKey());
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
                            DocumentType documentType = documentControl.getDocumentTypeByName(DocumentConstants.DocumentType_COMMUNICATION_EVENT_EMAIL);
                            MimeType mimeType = coreControl.getMimeTypeByName(MimeTypes.MESSAGE_RFC822.mimeTypeName());

                            Document document = documentControl.createDocument(documentType, mimeType, null, createdBy);
                            documentControl.createDocumentBlob(document, blobDocument, createdBy);

                            if(description != null) {
                                documentControl.createDocumentDescription(document, getPreferredLanguage(), description, createdBy);
                            }

                            CommunicationEventPurpose communicationEventPurpose = communicationControl.getDefaultCommunicationEventPurpose();
                            CommunicationEvent communicationEvent = communicationControl.createCommunicationEvent(communicationEventType, communicationSource, communicationEventPurpose, null, null,
                                    partyContactMechanism, document, createdBy);

                            EntityInstance entityInstance = getCoreControl().getEntityInstanceByBasePK(communicationEvent.getPrimaryKey());
                            WorkEffort workEffort = WorkEffortLogic.getInstance().createWorkEffort(preparedWorkEffort, entityInstance, createdBy);

                            CommunicationEventRoleType senderRoleType = communicationControl.getCommunicationEventRoleTypeByName(CommunicationConstants.CommunicationEventRoleType_SENDER);
                            communicationControl.createCommunicationEventRole(communicationEvent, party, senderRoleType, createdBy);

                            Workflow communicationEventWorkflow = workflowControl.getWorkflowByName(IncomingCustomerEmailConstants.Workflow_INCOMING_CUSTOMER_EMAIL);
                            WorkflowEntrance communicationEventWorkflowEntrance = workflowControl.getWorkflowEntranceByName(communicationEventWorkflow,
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
