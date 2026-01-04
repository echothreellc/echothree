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

package com.echothree.model.control.offer.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.offer.server.control.OfferNameElementControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.offer.server.entity.OfferNameElement;
import com.echothree.model.data.offer.server.entity.OfferNameElementDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("offer name element object")
@GraphQLName("OfferNameElement")
public class OfferNameElementObject
        extends BaseEntityInstanceObject {
    
    private final OfferNameElement offerNameElement; // Always Present
    
    public OfferNameElementObject(OfferNameElement offerNameElement) {
        super(offerNameElement.getPrimaryKey());
        
        this.offerNameElement = offerNameElement;
    }

    private OfferNameElementDetail offerNameElementDetail; // Optional, use getOfferNameElementDetail()
    
    private OfferNameElementDetail getOfferNameElementDetail() {
        if(offerNameElementDetail == null) {
            offerNameElementDetail = offerNameElement.getLastDetail();
        }
        
        return offerNameElementDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("use type name")
    @GraphQLNonNull
    public String getOfferNameElementName() {
        return getOfferNameElementDetail().getOfferNameElementName();
    }
    
    @GraphQLField
    @GraphQLDescription("offset")
    @GraphQLNonNull
    public int getOffset() {
        return getOfferNameElementDetail().getOffset();
    }

    @GraphQLField
    @GraphQLDescription("length")
    @GraphQLNonNull
    public int getLength() {
        return getOfferNameElementDetail().getLength();
    }

    @GraphQLField
    @GraphQLDescription("validation pattern")
    public String getValidationPattern() {
        return getOfferNameElementDetail().getValidationPattern();
    }

    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var offerNameElementControl = Session.getModelController(OfferNameElementControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return offerNameElementControl.getBestOfferNameElementDescription(offerNameElement, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
}
