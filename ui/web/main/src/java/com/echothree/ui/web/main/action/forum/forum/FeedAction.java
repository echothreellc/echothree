// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.ui.web.main.action.forum.forum;

import com.echothree.control.user.forum.common.ForumUtil;
import com.echothree.control.user.forum.common.form.GetForumThreadsForm;
import com.echothree.control.user.forum.common.result.GetForumThreadsResult;
import com.echothree.model.control.core.common.MimeTypes;
import com.echothree.model.control.forum.common.ForumConstants;
import com.echothree.model.control.forum.common.ForumOptions;
import com.echothree.model.control.forum.common.transfer.ForumMessagePartTransfer;
import com.echothree.model.control.forum.common.transfer.ForumMessageRoleTransfer;
import com.echothree.model.control.forum.common.transfer.ForumMessageTransfer;
import com.echothree.model.control.forum.common.transfer.ForumThreadTransfer;
import com.echothree.model.control.forum.common.transfer.ForumTransfer;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.common.transfer.PersonTransfer;
import com.echothree.model.control.party.common.transfer.ProfileTransfer;
import com.echothree.model.data.forum.common.ForumMessageConstants;
import com.echothree.model.data.forum.common.ForumThreadConstants;
import com.echothree.ui.web.main.framework.ByteArrayStreamInfo;
import com.echothree.ui.web.main.framework.MainBaseDownloadAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.string.StringUtils;
import com.echothree.util.common.string.UrlUtils;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.util.common.transfer.Limit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import com.google.common.base.Charsets;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndContentImpl;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedOutput;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Forum/Forum/Feed",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    }
)
public class FeedAction
        extends MainBaseDownloadAction {
    
    @Override
    protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        StreamInfo streamInfo;
        GetForumThreadsForm commandForm = ForumUtil.getHome().getGetForumThreadsForm();
        String forumName = request.getParameter(ParameterConstants.FORUM_NAME);
        
        commandForm.setForumName(forumName);
        commandForm.setIncludeFutureForumThreads(Boolean.TRUE.toString());
        
        Set<String> commandFormOptions = new HashSet<>();
        commandFormOptions.add(ForumOptions.ForumThreadIncludeForumMessages);
        commandFormOptions.add(ForumOptions.ForumMessageIncludeForumMessageRoles);
        commandFormOptions.add(ForumOptions.ForumMessageIncludeForumMessageParts);
        commandFormOptions.add(ForumOptions.ForumMessagePartIncludeString);
        commandFormOptions.add(ForumOptions.ForumMessagePartIncludeClob);
        commandForm.setOptions(commandFormOptions);
        
        Map<String, Limit> commandFormLimits = new HashMap<>();
        commandFormLimits.put(ForumThreadConstants.ENTITY_TYPE_NAME, new Limit("20", null));
        commandFormLimits.put(ForumMessageConstants.ENTITY_TYPE_NAME, new Limit("1", null));
        commandForm.setLimits(commandFormLimits);
        
        CommandResult commandResult = ForumUtil.getHome().getForumThreads(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetForumThreadsResult result = (GetForumThreadsResult)executionResult.getResult();
        ForumTransfer forum = result.getForum();
        
        if(forum != null) {
            SyndFeed feed = getFeed(request, response, forum, result.getForumThreads());
            feed.setFeedType("rss_2.0");

            SyndFeedOutput output = new SyndFeedOutput();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(output.outputString(feed).getBytes(Charsets.UTF_8));

            streamInfo = new ByteArrayStreamInfo("application/rss+xml", byteArrayInputStream, null, null);
        } else {
            streamInfo = new ByteArrayStreamInfo("application/rss+xml", new ByteArrayInputStream("".getBytes(Charsets.UTF_8)), null, null);
        }
        
        return streamInfo;
    }
    
    protected SyndContent attemptDescription(ForumMessageTransfer forumMessage, String forumMessagePartyType) {
        SyndContent description = null;
        ForumMessagePartTransfer forumMessagePart = forumMessage.getForumMessageParts().getMap().get(forumMessagePartyType);
        
        if(forumMessagePart != null) {
            description = new SyndContentImpl();
            
            description.setType(MimeTypes.TEXT_HTML.mimeTypeName());
            description.setValue(StringUtils.getInstance().convertToHtml(forumMessagePart.getClob(), forumMessagePart.getMimeType().getMimeTypeName()));
        }
        
        return description;
    }
    
    protected SyndContent attemptDescriptions(ForumMessageTransfer forumMessage) {
        SyndContent description = attemptDescription(forumMessage, ForumConstants.ForumMessagePartType_FEED_SUMMARY);
        
        if(description == null) {
            description = attemptDescription(forumMessage, ForumConstants.ForumMessagePartType_SUMMARY);
        }
        
        if(description == null) {
            description = attemptDescription(forumMessage, ForumConstants.ForumMessagePartType_CONTENT);
        }
        
        return description;
    }
    
    protected void attemptEntryAuthor(ForumMessageTransfer forumMessage, SyndEntry entry) {
        ListWrapper<ForumMessageRoleTransfer> wrappedForumMessageRoles = forumMessage.getForumMessageRoles();
        
        if(wrappedForumMessageRoles.getSize() > 0) {
            ForumMessageRoleTransfer author = null;
            List<ForumMessageRoleTransfer> forumMessageRoles = wrappedForumMessageRoles.getList();
            
            for(ForumMessageRoleTransfer forumMessageRole: forumMessageRoles) {
                if(forumMessageRole.getForumRoleType().getForumRoleTypeName().equals(ForumConstants.ForumRoleType_AUTHOR)) {
                    author = forumMessageRole;
                    break;
                }
            }
            
            if(author != null) {
                PartyTransfer party = author.getParty();
                ProfileTransfer profile = party.getProfile();
                StringBuilder authorBuilder = new StringBuilder();
                
                if(profile != null) {
                    String nickname = profile.getNickname();
                    
                    if(nickname != null) {
                        authorBuilder.append(nickname);
                    }
                }
                
                if(authorBuilder.length() == 0) {
                    PersonTransfer person = party.getPerson();
                    
                    if(person != null) {
                        String firstName = person.getFirstName();
                        String lastName = person.getLastName();
                        
                        if(firstName != null) {
                            authorBuilder.append(firstName);
                        }
                        
                        if(lastName != null) {
                            if(authorBuilder.length() > 0) {
                                authorBuilder.append(' ');
                            }
                            
                            authorBuilder.append(lastName);
                        }
                    }
                }
                
                if(authorBuilder.length() > 0) {
                    entry.setAuthor(authorBuilder.toString());
                }
            }
        }
    }
    
    protected SyndFeed getFeed(HttpServletRequest request, HttpServletResponse response, ForumTransfer forum,
            List<ForumThreadTransfer> forumThreads)
            throws IOException, FeedException {
        UrlUtils urlUtils = UrlUtils.getInstance();
        SyndFeed feed = new SyndFeedImpl();
        List<SyndEntry> entries = new ArrayList<>(forumThreads.size());
        Map<String, String> feedParameters = new HashMap<>(1);
        
        feed.setEncoding("UTF-8");
        feed.setTitle(forum.getDescription());
        feedParameters.put(ParameterConstants.FORUM_NAME, forum.getForumName());
        feed.setLink(urlUtils.buildUrl(request, response, "/Forum/ForumThread/Main", feedParameters));
        feed.setDescription("");
        
        forumThreads.stream().forEach((forumThread) -> {
            ListWrapper<ForumMessageTransfer> forumMessages = forumThread.getForumMessages();
            if (forumMessages.getSize() > 0) {
                SyndEntry entry = new SyndEntryImpl();
                ForumMessageTransfer forumMessage = forumMessages.getList().get(0);
                ForumMessagePartTransfer titleForumMessagePart = forumMessage.getForumMessageParts().getMap().get(ForumConstants.ForumMessagePartType_TITLE);
                Map<String, String> entryParameters = new HashMap<>(1);
                Long unformattedModifiedTime = forumMessage.getEntityInstance().getEntityTime().getUnformattedModifiedTime();
                
                entry.setTitle(titleForumMessagePart.getString());
                entryParameters.put(ParameterConstants.FORUM_THREAD_NAME, forumThread.getForumThreadName());
                entry.setLink(urlUtils.buildUrl(request, response, "/Forum/ForumThread/Review", entryParameters));
                entry.setDescription(attemptDescriptions(forumMessage));
                entry.setPublishedDate(new Date(forumMessage.getEntityInstance().getEntityTime().getUnformattedCreatedTime()));
                
                if(unformattedModifiedTime != null) {
                    entry.setUpdatedDate(new Date(unformattedModifiedTime));
                }
                
                attemptEntryAuthor(forumMessage, entry);
                
                entries.add(entry);
            }
        });
        
        feed.setEntries(entries);
        
        return feed;
    }
    
}
