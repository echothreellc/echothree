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

package com.echothree.model.control.core.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseObject;
import com.echothree.model.data.core.server.entity.LinkTypeTranslation;
import graphql.schema.DataFetchingEnvironment;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLName("LinkTypeTranslation")
public class LinkTypeTranslationObject
        extends BaseObject {

    private final LinkTypeTranslation linkTypeTranslation;

    public LinkTypeTranslationObject(LinkTypeTranslation linkTypeTranslation) {
        this.linkTypeTranslation = linkTypeTranslation;
    }

    @GraphQLField
    public LinkTypeObject getLinkType(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasLinkTypeAccess(env) ? new LinkTypeObject(linkTypeTranslation.getLinkType()) : null;
    }

    @GraphQLField
    @GraphQLNonNull
    public String getLanguageIsoName() {
        return linkTypeTranslation.getLanguage().getLanguageIsoName();
    }

    @GraphQLField
    @GraphQLNonNull
    public String getDescription() {
        return linkTypeTranslation.getDescription();
    }

    @GraphQLField
    public MimeTypeObject getGuidanceMimeType(final DataFetchingEnvironment env) {
        if(CoreSecurityUtils.getHasMimeTypeAccess(env)) {
            var mimeType = linkTypeTranslation.getGuidanceMimeType();
    
            return mimeType == null ? null : new MimeTypeObject(mimeType);
        } else {
            return null;
        }
    }

    @GraphQLField
    public String getGuidance() {
        return linkTypeTranslation.getGuidance();
    }
}
