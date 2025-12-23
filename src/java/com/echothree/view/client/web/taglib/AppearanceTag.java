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

import com.echothree.model.control.core.common.FontStyles;
import com.echothree.model.control.core.common.FontWeights;
import com.echothree.model.control.core.common.TextDecorations;
import com.echothree.model.control.core.common.TextTransformations;
import com.echothree.model.control.core.common.transfer.AppearanceTransfer;
import com.echothree.model.control.core.common.transfer.ColorTransfer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.jsp.JspException;

public class AppearanceTag
        extends BaseTag {
    
    protected AppearanceTransfer appearance;
    
    private void init() {
        appearance = null;
    }
    
    /** Creates a new instance of AppearanceTag */
    public AppearanceTag() {
        super();
        init();
    }
    
    @Override
    public void release() {
        super.release();
        init();
    }
    
    public void setAppearance(AppearanceTransfer appearance) {
        this.appearance = appearance;
    }

    protected static Map<String, String> fontStyles;
    protected static Map<String, String> fontWeights;
    protected static Map<String, String> textDecorations;
    protected static Map<String, String> textTransformations;
    
    static {
        Map<String, String> map = new HashMap<>(3);
        
        map.put(FontStyles.NORMAL.name(), "normal");
        map.put(FontStyles.ITALIC.name(), "italic");
        map.put(FontStyles.OBLIQUE.name(), "oblique");
        
        fontStyles = Collections.unmodifiableMap(map);
        
        map = new HashMap<>(4);
        
        map.put(FontWeights.LIGHTER.name(), "lighter");
        map.put(FontWeights.NORMAL.name(), "normal");
        map.put(FontWeights.BOLD.name(), "bold");
        map.put(FontWeights.BOLDER.name(), "bolder");
        
        fontWeights = Collections.unmodifiableMap(map);
        
        map = new HashMap<>(3);
        
        map.put(TextDecorations.OVERLINE.name(), "overline");
        map.put(TextDecorations.LINE_THROUGH.name(), "line-through");
        map.put(TextDecorations.UNDERLINE.name(), "underline");
        
        textDecorations = Collections.unmodifiableMap(map);
        
        map = new HashMap<>(3);
        
        map.put(TextTransformations.UPPERCASE.name(), "uppercase");
        map.put(TextTransformations.LOWERCASE.name(), "lowercase");
        map.put(TextTransformations.CAPITALIZE.name(), "capitalize");
        
        textTransformations = Collections.unmodifiableMap(map);
    }
    
    protected StringBuilder colorToRgb(StringBuilder style, ColorTransfer color) {
        return style.append("rgb(").append(color.getRed()).append(',').append(color.getGreen()).append(',').append(color.getBlue()).append(')');
    }
    
    @Override
    public int doStartTag()
            throws JspException {
        
        if(appearance != null) {
            var style = new StringBuilder("<span");
            List<StringBuilder> appliedStyles = new ArrayList<>();
            var textColor = appearance.getTextColor();
            var backgroundColor = appearance.getBackgroundColor();
            var fontStyle = appearance.getFontStyle();
            var fontWeight = appearance.getFontWeight();
            var appearanceTextDecorations = appearance.getAppearanceTextDecorations().getList();
            var appearanceTextTransformations = appearance.getAppearanceTextTransformations().getList();
            
            // color:rgb(255,0,0);
            if(textColor != null) {
                var appliedStyle = new StringBuilder("color:");
                
                appliedStyles.add(colorToRgb(appliedStyle, textColor).append(';'));
            }
            
            // background-color:rgb(255,0,0);
            if(backgroundColor != null) {
                var appliedStyle = new StringBuilder("background-color:");
                
                appliedStyles.add(colorToRgb(appliedStyle, backgroundColor).append(';'));
            }
            
            // font-style:normal;
            if(fontStyle != null) {
                appliedStyles.add(new StringBuilder("font-style:").append(fontStyles.get(fontStyle.getFontStyleName())).append(';'));
            }
            
            // font-weight:normal;
            if(fontWeight != null) {
                appliedStyles.add(new StringBuilder("font-weight:").append(fontWeights.get(fontWeight.getFontWeightName())).append(';'));
            }
            
            // text-decoration:overline; (may be multiple, separated by spaces)
            if(appearanceTextDecorations != null && !appearanceTextDecorations.isEmpty()) {
                var appliedStyle = new StringBuilder("text-decoration:");
                var secondOrLater = false;
                
                for(var appearanceTextDecoration : appearanceTextDecorations) {
                    if(secondOrLater) {
                        appliedStyle.append(' ');
                    } else {
                        secondOrLater = true;
                    }
                    
                    appliedStyle.append(textDecorations.get(appearanceTextDecoration.getTextDecoration().getTextDecorationName()));
                }
                
                appliedStyles.add(appliedStyle.append(';'));
            }
            
            // text-transform:uppercase; (may be multiple, separated by spaces)
            if(appearanceTextTransformations != null && !appearanceTextTransformations.isEmpty()) {
                var appliedStyle = new StringBuilder("text-transform:");
                var secondOrLater = false;
                
                for(var appearanceTextTransformation : appearanceTextTransformations) {
                    if(secondOrLater) {
                        appliedStyle.append(' ');
                    } else {
                        secondOrLater = true;
                    }
                    
                    appliedStyle.append(textTransformations.get(appearanceTextTransformation.getTextTransformation().getTextTransformationName()));
                }
                
                appliedStyles.add(appliedStyle.append(';'));
            }
            
            if(!appliedStyles.isEmpty()) {
                style.append(" style=\"");
                appliedStyles.forEach((appliedStyle) -> {
                    style.append(appliedStyle);
                });
                style.append("\"");
            }
            
            style.append(">");
            
            try {
                pageContext.getOut().write(style.toString());
            } catch(IOException ioe) {
                throw new JspException(ioe);
            }
        }

        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doAfterBody()
            throws JspException {

        if(appearance != null) {
            try {
                pageContext.getOut().write("</span>");
            } catch(IOException ioe) {
                throw new JspException(ioe);
            }
        }
        
        return EVAL_PAGE;
    }
    
}
