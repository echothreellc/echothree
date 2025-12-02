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

package com.echothree.control.user.content.server.command;

import com.echothree.control.user.content.common.form.GetContentPageForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.model.control.associate.server.logic.AssociateReferralLogic;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.data.content.server.entity.ContentCollection;
import com.echothree.model.data.content.server.entity.ContentPage;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetContentPageCommand
        extends BaseSingleEntityCommand<ContentPage, GetContentPageForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentWebAddressName", FieldType.HOST_NAME, false, null, null),
                new FieldDefinition("ContentCollectionName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ContentSectionName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ContentPageName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AssociateProgramName", FieldType.STRING, false, null, null),
                new FieldDefinition("AssociateName", FieldType.STRING, false, null, null),
                new FieldDefinition("AssociatePartyContactMechanismName", FieldType.STRING, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetContentPageCommand */
    public GetContentPageCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected ContentPage getEntity() {
        var contentWebAddressName = form.getContentWebAddressName();
        var contentCollectionName = form.getContentCollectionName();
        var parameterCount = (contentWebAddressName == null ? 0 : 1) + (contentCollectionName == null ? 0 : 1);
        ContentPage contentPage = null;

        if(parameterCount == 1) {
            var contentControl = Session.getModelController(ContentControl.class);
            ContentCollection contentCollection = null;

            if(contentWebAddressName != null) {
                var contentWebAddress = contentControl.getContentWebAddressByName(contentWebAddressName);

                if(contentWebAddress != null) {
                    contentCollection = contentWebAddress.getLastDetail().getContentCollection();
                } else {
                    addExecutionError(ExecutionErrors.UnknownContentWebAddressName.name(), contentWebAddressName);
                }
            } else {
                contentCollection = contentControl.getContentCollectionByName(contentCollectionName);

                if(contentCollection == null) {
                    addExecutionError(ExecutionErrors.UnknownContentCollectionName.name(), contentCollectionName);
                }
            }

            if(!hasExecutionErrors()) {
                var contentSectionName = form.getContentSectionName();
                var contentPageName = form.getContentPageName();
                var partyPK = getPartyPK();

                var contentSection = contentSectionName == null ? contentControl.getDefaultContentSection(contentCollection)
                        : contentControl.getContentSectionByName(contentCollection, contentSectionName);

                if(contentSection != null) {
                    contentPage = contentPageName == null ? contentControl.getDefaultContentPage(contentSection)
                            : contentControl.getContentPageByName(contentSection, contentPageName);

                    if(contentPage != null) {
                        AssociateReferralLogic.getInstance().handleAssociateReferral(session, this, form, getUserVisitForUpdate(),
                                contentPage.getPrimaryKey(), partyPK);

                        if(!hasExecutionErrors()) {
                            sendEvent(contentPage.getPrimaryKey(), EventTypes.READ, null, null, partyPK);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownContentPageName.name(),
                                contentCollection.getLastDetail().getContentCollectionName(), contentSectionName, contentPageName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownContentSectionName.name(),
                            contentCollection.getLastDetail().getContentCollectionName(), contentSectionName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return contentPage;
    }
    
    @Override
    protected BaseResult getResult(ContentPage contentPage) {
        var result = ContentResultFactory.getGetContentPageResult();

        if (contentPage != null) {
            var contentControl = Session.getModelController(ContentControl.class);

            result.setContentPage(contentControl.getContentPageTransfer(getUserVisit(), contentPage));
        }

        return result;
    }
    
}
