// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

package com.echothree.model.control.document.server.logic;

import com.echothree.control.user.document.common.spec.DocumentUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.MimeTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.document.common.exception.UnknownDocumentNameException;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.document.server.entity.Document;
import com.echothree.model.data.document.server.entity.DocumentType;
import com.echothree.model.data.document.server.entity.PartyDocument;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.lowagie.text.pdf.PdfReader;
import java.io.IOException;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class DocumentLogic
        extends BaseLogic {

    @Inject
    DocumentControl documentControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    protected DocumentLogic() {
        super();
    }

    public static DocumentLogic getInstance() {
        return CDI.current().select(DocumentLogic.class).get();
    }

    public Integer getPages(final MimeType mimeType, final ByteArray blob, final String clob) {
        Integer pages = null;
        var entityAttributeTypeName = mimeType.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();

        if(entityAttributeTypeName.equals(EntityAttributeTypes.BLOB.name())) {
            var mimeTypeName = mimeType.getLastDetail().getMimeTypeName();

            if(mimeTypeName.equals(MimeTypes.APPLICATION_PDF.mimeTypeName()) && blob != null) {
                try {
                    var pdfReader = new PdfReader(blob.getByteArrayInputStream());

                    pages = pdfReader.getNumberOfPages();
                } catch(IOException ioe) {
                    // Nothing, pages stays null.
                }
            }
        }

        return pages;
    }

    public Document createDocument(final ExecutionErrorAccumulator ema, final DocumentType documentType, final MimeType mimeType,
            final Language preferredLanguage, final String description, final ByteArray blob, final String clob, final PartyPK createdBy) {
        var pages = getPages(mimeType, blob, clob);
        Document document = null;
        var hasErrors = false;

        if(pages != null) {
            var documentTypeDetail = documentType.getLastDetail();
            var maximumPages = documentTypeDetail.getMaximumPages();

            if(maximumPages != null) {
                if(pages > maximumPages) {
                    hasErrors = true;
                    addExecutionError(ema, ExecutionErrors.DocumentExceedesMaximumPages.name());
                }
            }
        }

        if(!hasErrors) {
            document = documentControl.createDocument(documentType, mimeType, pages, createdBy);

            if(blob != null) {
                documentControl.createDocumentBlob(document, blob, createdBy);
            } else if(clob != null) {
                documentControl.createDocumentClob(document, clob, createdBy);
            }

            if(description != null) {
                documentControl.createDocumentDescription(document, preferredLanguage, description, createdBy);
            }
        }

        return document;
    }

    public Document getDocumentByName(final ExecutionErrorAccumulator eea, final String documentName,
            final EntityPermission entityPermission) {
        var document = documentControl.getDocumentByName(documentName, entityPermission);

        if(document == null) {
            handleExecutionError(UnknownDocumentNameException.class, eea, ExecutionErrors.UnknownDocumentName.name(),
                    documentName);
        }

        return document;
    }

    public Document getDocumentByName(final ExecutionErrorAccumulator eea, final String documentName) {
        return getDocumentByName(eea, documentName, EntityPermission.READ_ONLY);
    }

    public Document getDocumentByNameForUpdate(final ExecutionErrorAccumulator eea, final String documentName) {
        return getDocumentByName(eea, documentName, EntityPermission.READ_WRITE);
    }

    public Document getDocumentByUniversalSpec(final ExecutionErrorAccumulator eea,
            final DocumentUniversalSpec universalSpec, final EntityPermission entityPermission) {
        Document document = null;
        var documentName = universalSpec.getDocumentName();
        var possibleEntitySpecs = entityInstanceLogic.countPossibleEntitySpecs(universalSpec);
        var parameterCount = (documentName == null ? 0 : 1) + possibleEntitySpecs;

        switch(parameterCount) {
            case 1 -> {
                if(possibleEntitySpecs == 1) {
                    var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.Document.name());

                    if(eea == null || !eea.hasExecutionErrors()) {
                        document = documentControl.getDocumentByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    document = getDocumentByName(eea, documentName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea,
                            ExecutionErrors.InvalidParameterCount.name());
        }

        return document;
    }

    public Document getDocumentByUniversalSpec(final ExecutionErrorAccumulator eea,
            final DocumentUniversalSpec universalSpec) {
        return getDocumentByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public Document getDocumentByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final DocumentUniversalSpec universalSpec) {
        return getDocumentByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

    public PartyDocument createPartyDocument(final ExecutionErrorAccumulator ema, final Party party, final DocumentType documentType, final MimeType mimeType,
            final Boolean isDefault, final Integer sortOrder,  final Language preferredLanguage, final String description, final ByteArray blob,
            final String clob, final PartyPK createdBy) {
        var document = createDocument(ema, documentType, mimeType, preferredLanguage, description, blob, clob, createdBy);

        return document == null ? null : documentControl.createPartyDocument(party, document, isDefault, sortOrder, createdBy);
    }

}
