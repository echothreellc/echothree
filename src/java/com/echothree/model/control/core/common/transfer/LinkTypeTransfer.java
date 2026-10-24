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

package com.echothree.model.control.core.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class LinkTypeTransfer extends BaseTransfer {

    private String linkTypeName;
    private LinkTypeTransfer parentLinkType;
    private EntityTypeTransfer sourceEntityType;
    private EntityTypeTransfer targetEntityType;
    private LinkCardinalityTransfer linkCardinality;
    private LinkDirectionalityTransfer linkDirectionality;
    private Boolean isTransitive;
    private LinkTypeTransfer inverseLinkType;
    private Integer sortOrder;
    private String description;
    private MimeTypeTransfer guidanceMimeType;
    private String guidance;

    public LinkTypeTransfer(String linkTypeName, LinkTypeTransfer parentLinkType, EntityTypeTransfer sourceEntityType, EntityTypeTransfer targetEntityType, LinkCardinalityTransfer linkCardinality, LinkDirectionalityTransfer linkDirectionality, Boolean isTransitive, LinkTypeTransfer inverseLinkType, Integer sortOrder, String description, MimeTypeTransfer guidanceMimeType, String guidance) {
        this.linkTypeName = linkTypeName;
        this.parentLinkType = parentLinkType;
        this.sourceEntityType = sourceEntityType;
        this.targetEntityType = targetEntityType;
        this.linkCardinality = linkCardinality;
        this.linkDirectionality = linkDirectionality;
        this.isTransitive = isTransitive;
        this.inverseLinkType = inverseLinkType;
        this.sortOrder = sortOrder;
        this.description = description;
        this.guidanceMimeType = guidanceMimeType;
        this.guidance = guidance;
    }

    public String getLinkTypeName() {
        return linkTypeName;
    }

    public void setLinkTypeName(String linkTypeName) {
        this.linkTypeName = linkTypeName;
    }

    public LinkTypeTransfer getParentLinkType() {
        return parentLinkType;
    }

    public void setParentLinkType(LinkTypeTransfer parentLinkType) {
        this.parentLinkType = parentLinkType;
    }

    public EntityTypeTransfer getSourceEntityType() {
        return sourceEntityType;
    }

    public void setSourceEntityType(EntityTypeTransfer sourceEntityType) {
        this.sourceEntityType = sourceEntityType;
    }

    public EntityTypeTransfer getTargetEntityType() {
        return targetEntityType;
    }

    public void setTargetEntityType(EntityTypeTransfer targetEntityType) {
        this.targetEntityType = targetEntityType;
    }

    public LinkCardinalityTransfer getLinkCardinality() {
        return linkCardinality;
    }

    public void setLinkCardinality(LinkCardinalityTransfer linkCardinality) {
        this.linkCardinality = linkCardinality;
    }

    public LinkDirectionalityTransfer getLinkDirectionality() {
        return linkDirectionality;
    }

    public void setLinkDirectionality(LinkDirectionalityTransfer linkDirectionality) {
        this.linkDirectionality = linkDirectionality;
    }

    public Boolean getIsTransitive() {
        return isTransitive;
    }

    public void setIsTransitive(Boolean isTransitive) {
        this.isTransitive = isTransitive;
    }

    public LinkTypeTransfer getInverseLinkType() {
        return inverseLinkType;
    }

    public void setInverseLinkType(LinkTypeTransfer inverseLinkType) {
        this.inverseLinkType = inverseLinkType;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MimeTypeTransfer getGuidanceMimeType() {
        return guidanceMimeType;
    }

    public void setGuidanceMimeType(MimeTypeTransfer guidanceMimeType) {
        this.guidanceMimeType = guidanceMimeType;
    }

    public String getGuidance() {
        return guidance;
    }

    public void setGuidance(String guidance) {
        this.guidance = guidance;
    }

}
