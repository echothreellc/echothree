// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
// Copyright 1999-2004 The Apache Software Foundation.
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

package com.echothree.view.client.web.taglib;

import com.echothree.control.user.campaign.common.CampaignUtil;
import com.echothree.control.user.track.common.TrackUtil;
import com.echothree.view.client.web.WebConstants;
import com.echothree.view.client.web.util.HttpSessionUtils;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

public class UserVisitTag
        extends BaseTag {

    protected boolean secureUserKey;

    private void init() {
        secureUserKey = false;
    }

    /** Creates a new instance of PreferredDateTimeFormatTag */
    public UserVisitTag() {
        super();
        init();
    }

    @Override
    public void release() {
        super.release();
        init();
    }

    public void setSecureUserKey(Boolean secureUserKey) {
        this.secureUserKey = secureUserKey;
    }

    public String getParameter(final HttpServletRequest httpServletRequest, final String name, final String analyticsName) {
        var result = httpServletRequest.getParameter(name);
        
        if(result == null) {
            result = httpServletRequest.getParameter(analyticsName);
        }
        
        return result;
    }
    
    @Override
    public int doStartTag()
            throws JspException {
        var httpServletRequest = (HttpServletRequest)pageContext.getRequest();
        var campaignValue = getParameter(httpServletRequest, WebConstants.Parameter_Campaign, WebConstants.Parameter_utm_campaign);
        var campaignSourceValue = getParameter(httpServletRequest, WebConstants.Parameter_Source, WebConstants.Parameter_utm_source);
        var campaignMediumValue = getParameter(httpServletRequest, WebConstants.Parameter_Medium, WebConstants.Parameter_utm_medium);
        var campaignTermValue = getParameter(httpServletRequest, WebConstants.Parameter_Term, WebConstants.Parameter_utm_term);
        var campaignContentValue = getParameter(httpServletRequest, WebConstants.Parameter_Content, WebConstants.Parameter_utm_content);
        var campaignParameterCount = (campaignValue == null ? 0 : 1) + (campaignSourceValue == null ? 0 : 1) + (campaignMediumValue == null ? 0 : 1)
                + (campaignTermValue == null ? 0 : 1) + (campaignContentValue == null ? 0 : 1);
        var trackValue = getParameter(httpServletRequest, WebConstants.Parameter_Track, WebConstants.Parameter_trk);
        var trackParameterCount = trackValue == null ? 0 : 1;

        var userVisitPK = HttpSessionUtils.getInstance().setupUserVisit(httpServletRequest, (HttpServletResponse)pageContext.getResponse(), secureUserKey);
        
        if(campaignParameterCount > 0) {
            try {
                var commandForm = CampaignUtil.getHome().getCreateUserVisitCampaignForm();
                
                commandForm.setCampaignValue(campaignValue);
                commandForm.setCampaignSourceValue(campaignSourceValue);
                commandForm.setCampaignMediumValue(campaignMediumValue);
                commandForm.setCampaignTermValue(campaignTermValue);
                commandForm.setCampaignContentValue(campaignContentValue);
                
                CampaignUtil.getHome().createUserVisitCampaign(userVisitPK, commandForm);
            } catch (NamingException ne) {
                throw new JspException(ne);
            }
        }
        
        if(trackParameterCount > 0) {
            try {
                var commandForm = TrackUtil.getHome().getCreateUserVisitTrackForm();
                
                commandForm.setTrackValue(trackValue);
                
                TrackUtil.getHome().createUserVisitTrack(userVisitPK, commandForm);
            } catch (NamingException ne) {
                throw new JspException(ne);
            }
        }
        
        return SKIP_BODY;
    }
    
}
