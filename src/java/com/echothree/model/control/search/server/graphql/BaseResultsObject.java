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

package com.echothree.model.control.search.server.graphql;

import com.echothree.control.user.search.common.form.BaseGetResultsForm;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.exception.InvalidEntityAttributeTypeException;
import com.echothree.model.control.core.common.exception.UnknownComponentVendorNameException;
import com.echothree.model.control.core.common.exception.UnknownEntityTypeNameException;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.search.common.exception.BaseSearchException;
import com.echothree.model.control.search.server.logic.SearchLogic;
import com.echothree.model.control.search.server.logic.UserVisitSearchFacetLogic;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.Collection;

public abstract class BaseResultsObject<F extends BaseGetResultsForm>
        implements BaseGraphQl {

    final private String componentVendorName;
    final private String entityTypeName;
    final private String searchKindName;
    final private F form;

    protected BaseResultsObject(String componentVendorName, String entityTypeName, String searchKindName, F form) {
        this.componentVendorName = componentVendorName;
        this.entityTypeName = entityTypeName;
        this.searchKindName = searchKindName;
        this.form = form;
    }

    private UserVisitSearch userVisitSearch;

    protected UserVisitSearch getUserVisitSearch(final DataFetchingEnvironment env) {
        if(form != null && userVisitSearch == null) {
            try {
                var userVisit = BaseGraphQl.getUserVisit(env);
                
                userVisitSearch = SearchLogic.getInstance().getUserVisitSearchByName(null, userVisit,
                        searchKindName, form.getSearchTypeName());
            } catch (BaseSearchException bse) {
                // Leave userVisitSearch null.
            }
        }
        
        return userVisitSearch;
    }

    protected long getTotalCount(final DataFetchingEnvironment env) {
        var userVisitSearch = getUserVisitSearch(env);

        return userVisitSearch == null ? 0 : SearchLogic.getInstance().countSearchResults(userVisitSearch.getSearch());
    }

    // Substantial portions of this are duplicated in BaseGetResultsFacetsCommand.
    protected Collection<UserVisitSearchFacetObject> getUserVisitSearchFacetObjects(final DataFetchingEnvironment env) {
        final var userVisitSearchFacetObject = new ArrayList<UserVisitSearchFacetObject>();
        var failed = false;

        try {
            final var entityType = EntityTypeLogic.getInstance().getEntityTypeByName(null, componentVendorName, entityTypeName);
            final var userVisitSearch = getUserVisitSearch(env);

            if(userVisitSearch != null) {
                final var coreControl = Session.getModelController(CoreControl.class);
                final var entityAttributes = coreControl.getEntityAttributesByEntityType(entityType);

                entityAttributes.forEach((entityAttribute) -> {
                    final var entityAttributeTypeName = entityAttribute.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();

                    if(entityAttributeTypeName.equals(EntityAttributeTypes.LISTITEM.name())
                            || entityAttributeTypeName.equals(EntityAttributeTypes.MULTIPLELISTITEM.name())
                            || entityAttributeTypeName.equals(EntityAttributeTypes.INTEGER.name())
                            || entityAttributeTypeName.equals(EntityAttributeTypes.LONG.name())) {
                        final var userVisitSearchFacet = UserVisitSearchFacetLogic.getInstance().getUserVisitSearchFacetObject(null, userVisitSearch, entityAttribute);

                        // Only add it to the list of facets if it contains results.
                        if(userVisitSearchFacet != null && ((userVisitSearchFacet.getEntityListItems() != null && !userVisitSearchFacet.getEntityListItems().isEmpty())
                                || (userVisitSearchFacet.getEntityIntegerRanges() != null && !userVisitSearchFacet.getEntityIntegerRanges().isEmpty())
                                || (userVisitSearchFacet.getEntityLongRanges() != null && !userVisitSearchFacet.getEntityLongRanges().isEmpty()))) {
                            userVisitSearchFacetObject.add(userVisitSearchFacet);
                        }
                    }
                });
            }
        } catch (UnknownComponentVendorNameException | UnknownEntityTypeNameException | InvalidEntityAttributeTypeException e) {
            failed = true;
        }

        return failed ? null : userVisitSearchFacetObject;
    }

    @GraphQLField
    @GraphQLDescription("facets")
    public Collection<UserVisitSearchFacetObject> getFacets(final DataFetchingEnvironment env) {
        return getUserVisitSearchFacetObjects(env);
    }

}
