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

package com.echothree.model.control.associate.common.transfer;

import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class AssociateTransfer
        extends BaseTransfer {
    
    private AssociateProgramTransfer associateProgram;
    private String associateName;
    private PartyTransfer party;
    private String description;
    private MimeTypeTransfer summaryMimeType;
    private String summary;
    
    /** Creates a new instance of AssociateTransfer */
    public AssociateTransfer(AssociateProgramTransfer associateProgram, String associateName, PartyTransfer party,
            String description, MimeTypeTransfer summaryMimeType, String summary) {
        this.associateProgram = associateProgram;
        this.associateName = associateName;
        this.party = party;
        this.description = description;
        this.summaryMimeType = summaryMimeType;
        this.summary = summary;
    }
    
    public AssociateProgramTransfer getAssociateProgram() {
        return associateProgram;
    }
    
    public void setAssociateProgram(AssociateProgramTransfer associateProgram) {
        this.associateProgram = associateProgram;
    }
    
    public String getAssociateName() {
        return associateName;
    }
    
    public void setAssociateName(String associateName) {
        this.associateName = associateName;
    }
    
    public PartyTransfer getParty() {
        return party;
    }
    
    public void setParty(PartyTransfer party) {
        this.party = party;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public MimeTypeTransfer getSummaryMimeType() {
        return summaryMimeType;
    }
    
    public void setSummaryMimeType(MimeTypeTransfer summaryMimeType) {
        this.summaryMimeType = summaryMimeType;
    }
    
    public String getSummary() {
        return summary;
    }
    
    public void setSummary(String summary) {
        this.summary = summary;
    }
    
}
