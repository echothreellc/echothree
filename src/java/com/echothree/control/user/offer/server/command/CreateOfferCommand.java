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

package com.echothree.control.user.offer.server.command;

import com.echothree.control.user.offer.common.form.CreateOfferForm;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.model.control.filter.common.FilterConstants;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.offer.server.logic.OfferLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.selector.common.SelectorConstants;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.filter.server.entity.FilterType;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyCompany;
import com.echothree.model.data.party.server.entity.PartyDepartment;
import com.echothree.model.data.party.server.entity.PartyDivision;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.selector.server.entity.SelectorKind;
import com.echothree.model.data.selector.server.entity.SelectorType;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateOfferCommand
        extends BaseSimpleCommand<CreateOfferForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Offer.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OfferName", FieldType.ENTITY_NAME, true, null, 20L),
                new FieldDefinition("SalesOrderSequenceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyCompanyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyDivisionName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyDepartmentName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("OfferItemSelectorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("OfferItemPriceFilterName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreateOfferCommand */
    public CreateOfferCommand(UserVisitPK userVisitPK, CreateOfferForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = OfferResultFactory.getCreateOfferResult();
        Offer offer = null;
        String salesOrderSequenceName = form.getSalesOrderSequenceName();
        Sequence salesOrderSequence = null;

        if(salesOrderSequenceName != null) {
            var sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
            SequenceType sequenceType = sequenceControl.getSequenceTypeByName(SequenceTypes.SALES_ORDER.name());

            if(sequenceType != null) {
                salesOrderSequence = sequenceControl.getSequenceByName(sequenceType, salesOrderSequenceName);
            } else {
                addExecutionError(ExecutionErrors.UnknownSequenceTypeName.name(), SequenceTypes.SALES_ORDER.name());
            }
        }

        if(salesOrderSequenceName == null || salesOrderSequence != null) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String companyName = form.getCompanyName();
            PartyCompany partyCompany = companyName == null? partyControl.getDefaultPartyCompany():
                partyControl.getPartyCompanyByName(companyName);
            Party partyCompanyParty = partyCompany == null? null: partyCompany.getParty();

            if(companyName == null || partyCompanyParty != null) {
                String divisionName = form.getDivisionName();
                PartyDivision partyDivision = divisionName == null? partyControl.getDefaultPartyDivision(partyCompanyParty):
                    partyControl.getPartyDivisionByName(partyCompanyParty, divisionName);
                Party partyDivisionParty = partyDivision == null? null: partyDivision.getParty();

                if(divisionName == null || partyDivisionParty != null) {
                    String departmentName = form.getDepartmentName();
                    PartyDepartment partyDepartment = departmentName == null? partyControl.getDefaultPartyDepartment(partyDivisionParty):
                        partyControl.getPartyDepartmentByName(partyDivisionParty, departmentName);
                    Party partyDepartmentParty = partyDepartment == null? null: partyDepartment.getParty();

                    if(departmentName == null || partyDepartmentParty != null) {
                        String offerItemSelectorName = form.getOfferItemSelectorName();
                        Selector offerItemSelector = null;

                        if(offerItemSelectorName != null) {
                            var selectorControl = (SelectorControl)Session.getModelController(SelectorControl.class);
                            SelectorKind selectorKind = selectorControl.getSelectorKindByName(SelectorConstants.SelectorKind_ITEM);

                            if(selectorKind != null) {
                                SelectorType selectorType = selectorControl.getSelectorTypeByName(selectorKind,
                                        SelectorConstants.SelectorType_OFFER);

                                if(selectorType != null) {
                                    offerItemSelector = selectorControl.getSelectorByName(selectorType, offerItemSelectorName);
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), SelectorConstants.SelectorType_OFFER);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), SelectorConstants.SelectorKind_ITEM);
                            }
                        }

                        if(offerItemSelectorName == null || offerItemSelector != null) {
                            String offerItemPriceFilterName = form.getOfferItemPriceFilterName();
                            Filter offerItemPriceFilter = null;

                            if(offerItemPriceFilterName != null) {
                                var filterControl = (FilterControl)Session.getModelController(FilterControl.class);
                                FilterKind filterKind = filterControl.getFilterKindByName(FilterConstants.FilterKind_PRICE);
                                FilterType filterType = filterControl.getFilterTypeByName(filterKind, FilterConstants.FilterType_OFFER_ITEM_PRICE);

                                if(filterType != null) {
                                    offerItemPriceFilter = filterControl.getFilterByName(filterType, offerItemPriceFilterName);
                                }
                            }

                            if(offerItemPriceFilterName == null || offerItemPriceFilter != null) {
                                var offerName = form.getOfferName();
                                var isDefault = Boolean.valueOf(form.getIsDefault());
                                var sortOrder = Integer.valueOf(form.getSortOrder());
                                var description = form.getDescription();

                                offer = OfferLogic.getInstance().createOffer(this, offerName, salesOrderSequence,
                                        partyDepartmentParty, offerItemSelector, offerItemPriceFilter, isDefault, sortOrder,
                                        getPreferredLanguage(), description, getPartyPK());
                            } else {
                                addExecutionError(ExecutionErrors.UnknownOfferItemPriceFilterName.name(), offerItemPriceFilterName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownOfferItemSelectorName.name(), offerItemSelectorName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownDepartmentName.name(), departmentName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownDivisionName.name(), divisionName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCompanyName.name(), companyName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSalesOrderSequenceName.name(), salesOrderSequenceName);
        }

        if(offer != null) {
            result.setOfferName(offer.getLastDetail().getOfferName());
            result.setEntityRef(offer.getPrimaryKey().getEntityRef());
        }
        
        return result;
    }
    
}
