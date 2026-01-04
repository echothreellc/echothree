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

package com.echothree.control.user.track.common.result;

import com.echothree.control.user.track.common.edit.TrackDescriptionEdit;
import com.echothree.model.control.track.common.transfer.TrackDescriptionTransfer;
import com.echothree.util.common.command.BaseEditResult;

public interface EditTrackDescriptionResult
        extends BaseEditResult<TrackDescriptionEdit> {
    
    TrackDescriptionTransfer getTrackDescription();
    void setTrackDescription(TrackDescriptionTransfer trackDescription);
    
}
