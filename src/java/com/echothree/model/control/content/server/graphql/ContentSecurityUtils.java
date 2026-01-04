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

package com.echothree.model.control.content.server.graphql;

import com.echothree.control.user.content.server.command.GetContentCatalogCommand;
import com.echothree.control.user.content.server.command.GetContentCatalogItemCommand;
import com.echothree.control.user.content.server.command.GetContentCatalogItemsCommand;
import com.echothree.control.user.content.server.command.GetContentCatalogsCommand;
import com.echothree.control.user.content.server.command.GetContentCategoriesCommand;
import com.echothree.control.user.content.server.command.GetContentCategoryCommand;
import com.echothree.control.user.content.server.command.GetContentCategoryItemCommand;
import com.echothree.control.user.content.server.command.GetContentCategoryItemsCommand;
import com.echothree.control.user.content.server.command.GetContentCollectionCommand;
import com.echothree.control.user.content.server.command.GetContentPageAreaCommand;
import com.echothree.control.user.content.server.command.GetContentPageAreasCommand;
import com.echothree.control.user.content.server.command.GetContentPageCommand;
import com.echothree.control.user.content.server.command.GetContentPageLayoutAreaCommand;
import com.echothree.control.user.content.server.command.GetContentPageLayoutAreasCommand;
import com.echothree.control.user.content.server.command.GetContentPageLayoutCommand;
import com.echothree.control.user.content.server.command.GetContentPageLayoutsCommand;
import com.echothree.control.user.content.server.command.GetContentPagesCommand;
import com.echothree.control.user.content.server.command.GetContentSectionCommand;
import com.echothree.control.user.content.server.command.GetContentSectionsCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.schema.DataFetchingEnvironment;

public interface ContentSecurityUtils {

    static boolean getHasContentCollectionAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetContentCollectionCommand.class);
    }
    
    static boolean getHasContentCatalogsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetContentCatalogsCommand.class);
    }
    
    static boolean getHasContentCatalogAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetContentCatalogCommand.class);
    }
    
    static boolean getHasContentCatalogItemsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetContentCatalogItemsCommand.class);
    }
    
    static boolean getHasContentCatalogItemAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetContentCatalogItemCommand.class);
    }

    static boolean getHasContentCategoriesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetContentCategoriesCommand.class);
    }
        
    static boolean getHasContentCategoryAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetContentCategoryCommand.class);
    }
        
     static boolean getHasContentCategoryItemsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetContentCategoryItemsCommand.class);
    }
    
    static boolean getHasContentCategoryItemAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetContentCategoryItemCommand.class);
    }

   static boolean getHasContentSectionsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetContentSectionsCommand.class);
    }

    static boolean getHasContentSectionAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetContentSectionCommand.class);
    }

    static boolean getHasContentPageLayoutsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetContentPageLayoutsCommand.class);
    }

    static boolean getHasContentPageLayoutAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetContentPageLayoutCommand.class);
    }

    static boolean getHasContentPageLayoutAreasAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetContentPageLayoutAreasCommand.class);
    }

    static boolean getHasContentPageLayoutAreaAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetContentPageLayoutAreaCommand.class);
    }

    static boolean getHasContentPagesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetContentPagesCommand.class);
    }

    static boolean getHasContentPageAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetContentPageCommand.class);
    }

    static boolean getHasContentPageAreasAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetContentPageAreasCommand.class);
    }

    static boolean getHasContentPageAreaAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetContentPageAreaCommand.class);
    }

}
