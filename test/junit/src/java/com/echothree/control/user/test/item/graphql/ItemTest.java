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

package com.echothree.control.user.test.item.graphql;

import com.echothree.control.user.test.common.graphql.GraphQlTestCase;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.Test;

public class ItemTest
        extends GraphQlTestCase {

    @Test
    public void itemByItemName()
            throws Exception {
        var itemBody = executeUsingPost("""
                query {
                    item(itemName: "minimal") {
                        itemName
                        id
                    }
                }
                """);
        
        assertThat(getMap(itemBody, "data.item")).isNotNull();
    }
    
    @Test
    public void itemByAlias()
            throws Exception {
        var itemBody = executeUsingPost("""
                query {
                    item(itemNameOrAlias: "test_other") {
                        itemName
                        id
                    }
                }
                """);
        
        assertThat(getMap(itemBody, "data.item")).isNotNull();
    }
    @Test
    public void itemByItemNameUsingAlias()
            throws Exception {
        var itemBody = executeUsingPost("""
                query {
                    item(itemName: "test_other") {
                        itemName
                        id
                    }
                }
                """);
        
        assertThat(getMap(itemBody, "data.item")).isNull();
    }
    
    @Test
    public void itemById()
            throws Exception {
        var itemBodyByItemName = executeUsingPost("""
                query {
                    item(itemName: "minimal") {
                        itemName
                        id
                    }
                }
                """);
        
        var id = getString(itemBodyByItemName, "data.item.id");
        
        var itemBodyById = executeUsingPost("""
                query {
                    item(id: "%s") {
                        itemName
                        id
                    }
                }
                """.formatted(id));
        
        assertThat(getMap(itemBodyById, "data.item")).isNotNull();
    }
    
    
}
