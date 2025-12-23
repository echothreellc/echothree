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
import com.echothree.control.user.search.common.edit.SearchKindEdit;
import com.echothree.control.user.search.common.form.SearchFormFactory;
import com.echothree.control.user.search.common.result.EditSearchKindResult;
import com.echothree.control.user.search.common.spec.SearchSpecFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.command.EditMode;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SearchKindsHandler
        extends BaseHandler {

    SearchService searchService;

    /** Creates a new instance of SearchKindsHandler */
    public SearchKindsHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            searchService = SearchUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("searchKind")) {
            var spec = SearchSpecFactory.getSearchKindSpec();
            var editForm = SearchFormFactory.getEditSearchKindForm();

            spec.set(getAttrsMap(attrs));

            editForm.setSpec(spec);
            editForm.setEditMode(EditMode.LOCK);

            var commandResult = searchService.editSearchKind(initialDataParser.getUserVisit(), editForm);
            
            if(commandResult.hasErrors()) {
                if(commandResult.containsExecutionError(ExecutionErrors.UnknownSearchKindName.name())) {
                    var createForm = SearchFormFactory.getCreateSearchKindForm();

                    createForm.set(getAttrsMap(attrs));

                    commandResult = searchService.createSearchKind(initialDataParser.getUserVisit(), createForm);
                    
                    if(commandResult.hasErrors()) {
                        getLogger().error(commandResult.toString());
                    }
                } else {
                    getLogger().error(commandResult.toString());
                }
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditSearchKindResult)executionResult.getResult();

                if(result != null) {
                    var edit = (SearchKindEdit)result.getEdit();
                    var isDefault = attrs.getValue("isDefault");
                    var sortOrder = attrs.getValue("sortOrder");
                    var changed = false;
                    
                    if(!edit.getIsDefault().equals(isDefault)) {
                        edit.setIsDefault(isDefault);
                        changed = true;
                    }

                    if(!edit.getSortOrder().equals(sortOrder)) {
                        edit.setSortOrder(sortOrder);
                        changed = true;
                    }

                    if(changed) {
                        editForm.setEdit(edit);
                        editForm.setEditMode(EditMode.UPDATE);
                        
                        commandResult = searchService.editSearchKind(initialDataParser.getUserVisit(), editForm);

                        if(commandResult.hasErrors()) {
                            getLogger().error(commandResult.toString());
                        }
                    } else {
                        editForm.setEdit(null);
                        editForm.setEditMode(EditMode.ABANDON);
                        
                        commandResult = searchService.editSearchKind(initialDataParser.getUserVisit(), editForm);

                        if(commandResult.hasErrors()) {
                            getLogger().error(commandResult.toString());
                        }
                    }
                }
            }
            
            initialDataParser.pushHandler(new SearchKindHandler(initialDataParser, this, spec.getSearchKindName()));
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("searchKinds")) {
            initialDataParser.popHandler();
        }
    }
}