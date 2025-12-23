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

package com.echothree.ui.cli.dataloader.util.data.handler.search;

import com.echothree.control.user.search.common.SearchUtil;
import com.echothree.control.user.search.common.SearchService;
import com.echothree.control.user.search.common.edit.SearchKindDescriptionEdit;
import com.echothree.control.user.search.common.form.SearchFormFactory;
import com.echothree.control.user.search.common.result.EditSearchKindDescriptionResult;
import com.echothree.control.user.search.common.spec.SearchSpecFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.command.EditMode;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SearchKindHandler
        extends BaseHandler {

    SearchService searchService;
    String searchKindName;

    /** Creates a new instance of SearchKindHandler */
    public SearchKindHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String searchKindName)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            searchService = SearchUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }

        this.searchKindName = searchKindName;
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("searchKindDescription")) {
            var spec = SearchSpecFactory.getSearchKindDescriptionSpec();
            var editForm = SearchFormFactory.getEditSearchKindDescriptionForm();

            spec.setSearchKindName(searchKindName);
            spec.set(getAttrsMap(attrs));

            editForm.setSpec(spec);
            editForm.setEditMode(EditMode.LOCK);

            var commandResult = searchService.editSearchKindDescription(initialDataParser.getUserVisit(), editForm);
            
            if(commandResult.hasErrors()) {
                if(commandResult.containsExecutionError(ExecutionErrors.UnknownSearchKindDescription.name())) {
                    var createForm = SearchFormFactory.getCreateSearchKindDescriptionForm();

                    createForm.setSearchKindName(searchKindName);
                    createForm.set(getAttrsMap(attrs));

                    commandResult = searchService.createSearchKindDescription(initialDataParser.getUserVisit(), createForm);
                    
                    if(commandResult.hasErrors()) {
                        getLogger().error(commandResult.toString());
                    }
                } else {
                    getLogger().error(commandResult.toString());
                }
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditSearchKindDescriptionResult)executionResult.getResult();

                if(result != null) {
                    var edit = (SearchKindDescriptionEdit)result.getEdit();
                    var description = attrs.getValue("description");
                    var changed = false;
                    
                    if(!edit.getDescription().equals(description)) {
                        edit.setDescription(description);
                        changed = true;
                    }

                    if(changed) {
                        editForm.setEdit(edit);
                        editForm.setEditMode(EditMode.UPDATE);
                        
                        commandResult = searchService.editSearchKindDescription(initialDataParser.getUserVisit(), editForm);

                        if(commandResult.hasErrors()) {
                            getLogger().error(commandResult.toString());
                        }
                    } else {
                        editForm.setEdit(null);
                        editForm.setEditMode(EditMode.ABANDON);
                        
                        commandResult = searchService.editSearchKindDescription(initialDataParser.getUserVisit(), editForm);

                        if(commandResult.hasErrors()) {
                            getLogger().error(commandResult.toString());
                        }
                    }
                }
            }
        } else if(localName.equals("searchType")) {
            var commandForm = SearchFormFactory.getCreateSearchTypeForm();

            commandForm.setSearchKindName(searchKindName);
            commandForm.set(getAttrsMap(attrs));

            searchService.createSearchType(initialDataParser.getUserVisit(), commandForm);

            initialDataParser.pushHandler(new SearchTypeHandler(initialDataParser, this, searchKindName, commandForm.getSearchTypeName()));
        } else if(localName.equals("searchSortOrder")) {
            var commandForm = SearchFormFactory.getCreateSearchSortOrderForm();

            commandForm.setSearchKindName(searchKindName);
            commandForm.set(getAttrsMap(attrs));

            searchService.createSearchSortOrder(initialDataParser.getUserVisit(), commandForm);

            initialDataParser.pushHandler(new SearchSortOrderHandler(initialDataParser, this, searchKindName, commandForm.getSearchSortOrderName()));
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("searchKind")) {
            initialDataParser.popHandler();
        }
    }
}