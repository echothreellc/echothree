// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.util.remote.transfer;

import java.io.Serializable;

public class Limit implements Serializable {
    
    private String count;
    private String offset;
    
    private void init(String count, String offset) {
        this.count = count;
        this.offset = offset;
    }
    
    /** Creates a new instance of Limit */
    public Limit(String count, String offset) {
        init(count, offset);
    }
    
    /** Creates a new instance of Limit */
    public Limit(String count) {
        init(count, null);
    }
    
    public String getCount() {
        return count;
    }
    
    public void setCount(String count) {
        this.count = count;
    }
    
    public String getOffset() {
        return offset;
    }
    
    public void setOffset(String offset) {
        this.offset = offset;
    }
    
}
