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

package com.echothree.view.client.web.taglib;

import com.echothree.control.user.campaign.common.CampaignUtil;
import com.echothree.control.user.campaign.remote.form.CreateUserVisitCampaignForm;
import com.echothree.control.user.track.common.TrackUtil;
import com.echothree.control.user.track.remote.form.CreateUserVisitTrackForm;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.view.client.web.WebConstants;
import com.echothree.view.client.web.util.HttpSessionUtils;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

public class UserVisitTag
        extends BaseTag {

    public String getParameter(final HttpServletRequest httpServletRequest, final String name, final String analyticsName) {
        String result = httpServletRequest.getParameter(name);
        
        if(result == null) {
            result = httpServletRequest.getParameter(analyticsName);
        }
        
        return result;
    }
    
    @Override
    public int doStartTag()
            throws JspException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)pageContext.getRequest();
        String campaignValue = getParameter(httpServletRequest, WebConstants.Parameter_Campaign, WebConstants.Parameter_utm_campaign);
        String campaignSourceValue = getParameter(httpServletRequest, WebConstants.Parameter_Source, WebConstants.Parameter_utm_source);
        String campaignMediumValue = getParameter(httpServletRequest, WebConstants.Parameter_Medium, WebConstants.Parameter_utm_medium);
        String campaignTermValue = getParameter(httpServletRequest, WebConstants.Parameter_Term, WebConstants.Parameter_utm_term);
        String campaignContentValue = getParameter(httpServletRequest, WebConstants.Parameter_Content, WebConstants.Parameter_utm_content);
        int campaignParameterCount = (campaignValue == null ? 0 : 1) + (campaignSourceValue == null ? 0 : 1) + (campaignMediumValue == null ? 0 : 1)
                + (campaignTermValue == null ? 0 : 1) + (campaignContentValue == null ? 0 : 1);
        String trackValue = getParameter(httpServletRequest, WebConstants.Parameter_Track, WebConstants.Parameter_trk);
        int trackParameterCount = trackValue == null ? 0 : 1;
        
        UserVisitPK userVisitPK = HttpSessionUtils.getInstance().setupUserVisit(httpServletRequest, (HttpServletResponse)pageContext.getResponse());
        
        if(campaignParameterCount > 0) {
            try {
                CreateUserVisitCampaignForm commandForm = CampaignUtil.getHome().getCreateUserVisitCampaignForm();
                
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
                CreateUserVisitTrackForm commandForm = TrackUtil.getHome().getCreateUserVisitTrackForm();
                
                commandForm.setTrackValue(trackValue);
                
                TrackUtil.getHome().createUserVisitTrack(userVisitPK, commandForm);
            } catch (NamingException ne) {
                throw new JspException(ne);
            }
        }
        
        return SKIP_BODY;
    }
    
}
