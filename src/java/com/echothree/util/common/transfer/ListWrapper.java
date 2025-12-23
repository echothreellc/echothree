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

package com.echothree.util.common.transfer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListWrapper<E>
        implements BaseWrapper<E> {
    
    private List<E> list;

    /** Creates a new instance of ListWrapper from a List */
    public ListWrapper(List<E> list) {
        this.list = list;
    }

    /** Creates a new instance of ListWrapper from a Collection */
    public ListWrapper(Collection<E> set) {
        this.list = new ArrayList<>(set);
    }

    @Override
    public List<E> getList() {
        return list;
    }
    
    @Override
    public Collection<E> getCollection() {
        return list;
    }
    
    @Override
    public int getSize() {
        return list.size();
    }
    
}
