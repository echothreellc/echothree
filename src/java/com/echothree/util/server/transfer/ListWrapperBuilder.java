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

package com.echothree.util.server.transfer;

import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.common.transfer.ListWrapper;
import java.util.List;

public class ListWrapperBuilder
        extends BaseWrapperBuilder {
    
    private ListWrapperBuilder() {
        super();
    }

    private static class ListWrapperBuilderHolder {
        static ListWrapperBuilder instance = new ListWrapperBuilder();
    }

    public static ListWrapperBuilder getInstance() {
        return ListWrapperBuilderHolder.instance;
    }
    
    public <E> ListWrapper<E> filter(TransferProperties transferProperties, List<E> list) {
        filterCollection(transferProperties, list);
        
        return new ListWrapper<>(list) ;
    }
    
}
