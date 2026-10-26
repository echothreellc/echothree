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

package com.echothree.control.user.core.common.result;

import com.echothree.model.control.core.common.transfer.EntityTypeTransfer;
import com.echothree.model.control.core.common.transfer.LinkCardinalityTransfer;
import com.echothree.model.control.core.common.transfer.LinkDirectionalityTransfer;
import com.echothree.model.control.core.common.transfer.LinkTypeTransfer;
import com.echothree.util.common.command.BaseResult;
import java.util.List;

public interface GetLinkTypesResult
        extends BaseResult {

    LinkTypeTransfer getParentLinkType();
    void setParentLinkType(LinkTypeTransfer parentLinkType);

    EntityTypeTransfer getSourceEntityType();
    void setSourceEntityType(EntityTypeTransfer sourceEntityType);

    EntityTypeTransfer getTargetEntityType();
    void setTargetEntityType(EntityTypeTransfer targetEntityType);

    LinkCardinalityTransfer getLinkCardinality();
    void setLinkCardinality(LinkCardinalityTransfer linkCardinality);

    LinkDirectionalityTransfer getLinkDirectionality();
    void setLinkDirectionality(LinkDirectionalityTransfer linkDirectionality);

    LinkTypeTransfer getInverseLinkType();
    void setInverseLinkType(LinkTypeTransfer inverseLinkType);

    Long getLinkTypeCount();
    void setLinkTypeCount(Long linkTypeCount);

    List<LinkTypeTransfer> getLinkTypes();
    void setLinkTypes(List<LinkTypeTransfer> linkTypes);

}
