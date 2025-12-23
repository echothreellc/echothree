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

package com.echothree.view.client.web;

public interface WebConstants {

    String Method_DELETE = "DELETE";
    String Method_GET = "GET";
    String Method_HEAD = "HEAD";
    String Method_OPTIONS = "OPTIONS";
    String Method_POST = "POST";
    String Method_PUT = "PUT";

    String Attribute_SECURITY_ROLES = "com.echothree.view.client.web.taglib.securityRoles";
    String Attribute_ENTITY_REFS = "com.echothree.view.client.web.taglib.entityRefs";
    String Attribute_HTML_FILTER_HOLDER = "com.echothree.view.client.web.taglib.htmlFilterHolder";
    String Attribute_STACK_TRACE = "stackTrace";
    String Attribute_TRANSFER_PROPERTIES_HOLDER = "com.echothree.view.client.web.taglib.transferPropertiesHolder";
    String Attribute_REPEAT_HOLDER = "com.echothree.view.client.web.taglib.repeatHolder";
    
    String Cookie_USER_KEY = "UserKey";
    
    String Session_USER_VISIT = "UserVisit";
    
    String Parameter_Campaign = "Campaign";
    String Parameter_Content = "Content";
    String Parameter_DT_ID_ATTRIBUTE = "DtIdAttribute";
    String Parameter_DT_ORDER_PARAMETER = "DtOrderParameter";
    String Parameter_DT_PAGE_PARAMETER = "DtPageParameter";
    String Parameter_DT_SORT_PARAMETER = "DtSortParameter";
    String Parameter_Medium = "Medium";
    String Parameter_SUBMIT_BUTTON = "submitButton";
    String Parameter_Source = "Source";
    String Parameter_Term = "Term";
    String Parameter_Track = "Track";
    String Parameter_trk = "trk";
    String Parameter_utm_campaign = "utm_campaign";
    String Parameter_utm_content = "utm_content";
    String Parameter_utm_medium = "utm_medium";
    String Parameter_utm_source = "utm_source";
    String Parameter_utm_term = "utm_term";
    
    String Attribute_DT_ID_ATTRIBUTE = "dtIdAttribute";
    String Attribute_DT_SORT_PARAMETER = "dtSortParameter";
    String Attribute_DT_PAGE_PARAMETER = "dtPageParameter";
    String Attribute_DT_ORDER_PARAMETER = "dtOrderParameter";

    // Must match client's ForwardConstants
    String Forward_Error404 = "Error404";
    String Forward_Error500 = "Error500";

}
