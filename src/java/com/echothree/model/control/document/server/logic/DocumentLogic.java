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

package com.echothree.model.control.document.server.logic;

import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.MimeTypes;
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
import com.echothree.util.server.persistence.Session;
import com.lowagie.text.pdf.PdfReader;
import java.io.IOException;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class DocumentLogic
        extends BaseLogic {

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
            var documentControl = Session.getModelController(DocumentControl.class);

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

    public PartyDocument createPartyDocument(final ExecutionErrorAccumulator ema, final Party party, final DocumentType documentType, final MimeType mimeType,
            final Boolean isDefault, final Integer sortOrder,  final Language preferredLanguage, final String description, final ByteArray blob,
            final String clob, final PartyPK createdBy) {
        var documentControl = Session.getModelController(DocumentControl.class);
        var document = createDocument(ema, documentType, mimeType, preferredLanguage, description, blob, clob, createdBy);

        return document == null ? null : documentControl.createPartyDocument(party, document, isDefault, sortOrder, createdBy);
    }

}
