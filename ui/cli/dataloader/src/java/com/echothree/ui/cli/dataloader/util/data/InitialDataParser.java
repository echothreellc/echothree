// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.ui.cli.dataloader.util.data;

import com.echothree.control.user.authentication.common.AuthenticationUtil;
import com.echothree.control.user.party.client.helper.NameSuffixesHelper;
import com.echothree.control.user.party.client.helper.PersonalTitlesHelper;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.NowhereHandler;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class InitialDataParser
        extends DefaultHandler {
    //
    // Constants
    //
    
    // feature ids
    
    /** Namespaces feature id (http://xml.org/sax/features/namespaces). */
    protected static final String NAMESPACES_FEATURE_ID = "http://xml.org/sax/features/namespaces";
    
    /** Namespace prefixes feature id (http://xml.org/sax/features/namespace-prefixes). */
    protected static final String NAMESPACE_PREFIXES_FEATURE_ID = "http://xml.org/sax/features/namespace-prefixes";
    
    /** Validation feature id (http://xml.org/sax/features/validation). */
    protected static final String VALIDATION_FEATURE_ID = "http://xml.org/sax/features/validation";
    
    /** Schema validation feature id (http://apache.org/xml/features/validation/schema). */
    protected static final String SCHEMA_VALIDATION_FEATURE_ID = "http://apache.org/xml/features/validation/schema";
    
    /** Schema full checking feature id (http://apache.org/xml/features/validation/schema-full-checking). */
    protected static final String SCHEMA_FULL_CHECKING_FEATURE_ID = "http://apache.org/xml/features/validation/schema-full-checking";
    
    /** Dynamic validation feature id (http://apache.org/xml/features/validation/dynamic). */
    protected static final String DYNAMIC_VALIDATION_FEATURE_ID = "http://apache.org/xml/features/validation/dynamic";
    
    // default settings
    
    /** Default namespaces support (true). */
    protected static final boolean DEFAULT_NAMESPACES = true;
    
    /** Default namespace prefixes (false). */
    protected static final boolean DEFAULT_NAMESPACE_PREFIXES = false;
    
    /** Default validation support (false). */
    protected static final boolean DEFAULT_VALIDATION = false;
    
    /** Default Schema validation support (false). */
    protected static final boolean DEFAULT_SCHEMA_VALIDATION = false;
    
    /** Default Schema full checking support (false). */
    protected static final boolean DEFAULT_SCHEMA_FULL_CHECKING = false;
    
    /** Default dynamic validation support (false). */
    protected static final boolean DEFAULT_DYNAMIC_VALIDATION = false;

    //
    // ContentHandler methods
    //
    
    String name;
    UserVisitPK userVisitPK;
    
    /** Start document. */
    @Override
    public void startDocument()
            throws SAXException {
        try {
            // load caches
            loadPersonalTitles();
            loadNameSuffixes();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
    }
    
    /** End document. */
    @Override
    public void endDocument()
            throws SAXException {
        clearUserVisit();
        
        personalTitles = null;
        nameSuffixes = null;
    }
    
    public UserVisitPK getUserVisit()
            throws SAXException {
        if(userVisitPK == null) {
            try {
                userVisitPK = AuthenticationUtil.getHome().getDataLoaderUserVisit();
            } catch (NamingException ne) {
                throw new SAXException(ne);
            }
        }
        
        return userVisitPK;
    }
    
    public void clearUserVisit()
            throws SAXException {
        if(userVisitPK != null) {
            try {
                AuthenticationUtil.getHome().invalidateUserVisit(userVisitPK);
                userVisitPK = null;
            } catch (NamingException ne) {
                throw new SAXException(ne);
            }
            
            userVisitPK = null;
        }
    }
    
    Map<String, String> personalTitles = null;
    
    public void loadPersonalTitles()
            throws NamingException, SAXException {
        var personalTitleChoices = PersonalTitlesHelper.getInstance().getPersonalTitleChoices(getUserVisit(), true);
        var valueIter = personalTitleChoices.getValues().iterator();
        var labelIter = personalTitleChoices.getLabels().iterator();
        
        personalTitles = new HashMap<>(personalTitleChoices.getLabels().size());
        while(valueIter.hasNext()) {
            var value = valueIter.next();
            var label = labelIter.next();
            
            addPersonalTitle(value, label);
        }
    }
    
    public void addPersonalTitle(String value, String label) {
        personalTitles.put(label, value);
    }
    
    public Map<String, String> getPersonalTitles() {
        return personalTitles;
    }
    
    Map<String, String> nameSuffixes = null;
    
    public void loadNameSuffixes()
            throws NamingException, SAXException {
        var nameSuffixChoices = NameSuffixesHelper.getInstance().getNameSuffixChoices(getUserVisit(), true);
        var valueIter = nameSuffixChoices.getValues().iterator();
        var labelIter = nameSuffixChoices.getLabels().iterator();
        
        nameSuffixes = new HashMap<>(nameSuffixChoices.getLabels().size());
        while(valueIter.hasNext()) {
            var value = valueIter.next();
            var label = labelIter.next();
            
            addNameSuffix(value, label);
        }
    }
    
    public void addNameSuffix(String value, String label) {
        nameSuffixes.put(label, value);
    }
    
    public Map<String, String> getNameSuffixes() {
        return nameSuffixes;
    }
    
    Deque<BaseHandler> handlers = null;
    
    void setupHandlers() {
        handlers = new ArrayDeque<>();
        
        pushHandler(new NowhereHandler(this, null));
    }
    
    public BaseHandler getDefaultHandler() {
        return handlers.peek();
    }
    
    public void pushHandler(BaseHandler handler) {
        handlers.push(handler);
    }
    
    public void popHandler() {
        handlers.pop();
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        try {
            getDefaultHandler().startElement(namespaceURI, localName, qName, attrs);
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        getDefaultHandler().endElement(namespaceURI, localName, qName);
    }
    
    @Override
    public void characters(char ch[], int start, int length)
            throws SAXException {
        getDefaultHandler().characters(ch, start, length);
    }
    
    /** Ignorable whitespace. */
    @Override
    public void ignorableWhitespace(char ch[], int start, int length)
            throws SAXException {
        getDefaultHandler().ignorableWhitespace(ch, start, length);
    }
    
    /** Processing instruction. */
    @Override
    public void processingInstruction(String target, String data)
            throws SAXException {
        getDefaultHandler().processingInstruction(target, data);
    }
    
    //
    // ErrorHandler methods
    //
    
    /** Warning. */
    @Override
    public void warning(SAXParseException ex)
            throws SAXException {
        printError("Warning", ex);
    }
    
    /** Error. */
    @Override
    public void error(SAXParseException ex)
            throws SAXException {
        printError("Error", ex);
    }
    
    /** Fatal error. */
    @Override
    public void fatalError(SAXParseException ex)
            throws SAXException {
        printError("Fatal Error", ex);
        throw ex;
    }
    
    //
    // Protected methods
    //
    
    /** Prints the error message. */
    protected void printError(String type, SAXParseException ex) {
        var systemId = ex.getSystemId();

        System.err.print("[");
        System.err.print(type);
        System.err.print("] ");

        if (ex== null) {
            System.out.println("!!!");
        }

        if (systemId != null) {
            var index = systemId.lastIndexOf('/');

            if (index != -1) {
                systemId = systemId.substring(index + 1);
            }
            
            System.err.print(systemId);
        }
        System.err.print(':');
        System.err.print(ex.getLineNumber());
        System.err.print(':');
        System.err.print(ex.getColumnNumber());
        System.err.print(": ");
        System.err.print(ex.getMessage());
        System.err.println();
        System.err.flush();
    }
    
    //
    // Constructors
    //
    
    /** Creates a new instance of InitialDataParser */
    public InitialDataParser(final String name)
            throws Exception {
        this.name = name;
    }

    public void execute()
            throws IOException, SAXException, ParserConfigurationException {
        XMLReader parser;

        // create parser
        try {
            parser = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        } catch(Exception e) {
            System.err.println("error: Unable to instantiate parser");
            throw e;
        }

        // set parser features
        try {
            parser.setFeature(NAMESPACES_FEATURE_ID, DEFAULT_NAMESPACES);
        } catch(SAXException e) {
            System.err.println("warning: Parser does not support feature (" + NAMESPACES_FEATURE_ID + ")");
        }

        try {
            parser.setFeature(NAMESPACE_PREFIXES_FEATURE_ID, DEFAULT_NAMESPACE_PREFIXES);
        } catch(SAXException e) {
            System.err.println("warning: Parser does not support feature (" + NAMESPACE_PREFIXES_FEATURE_ID + ")");
        }

        try {
            parser.setFeature(VALIDATION_FEATURE_ID, DEFAULT_VALIDATION);
        } catch(SAXException e) {
            System.err.println("warning: Parser does not support feature (" + VALIDATION_FEATURE_ID + ")");
        }

        try {
            parser.setFeature(SCHEMA_VALIDATION_FEATURE_ID, DEFAULT_SCHEMA_VALIDATION);
        } catch(SAXNotRecognizedException e) {
            // ignore
        } catch(SAXNotSupportedException e) {
            System.err.println("warning: Parser does not support feature (" + SCHEMA_VALIDATION_FEATURE_ID + ")");
        }

        try {
            parser.setFeature(SCHEMA_FULL_CHECKING_FEATURE_ID, DEFAULT_SCHEMA_FULL_CHECKING);
        } catch(SAXNotRecognizedException e) {
            // ignore
        } catch(SAXNotSupportedException e) {
            System.err.println("warning: Parser does not support feature (" + SCHEMA_FULL_CHECKING_FEATURE_ID + ")");
        }

        try {
            parser.setFeature(DYNAMIC_VALIDATION_FEATURE_ID, DEFAULT_DYNAMIC_VALIDATION);
        } catch(SAXNotRecognizedException e) {
            // ignore
        } catch(SAXNotSupportedException e) {
            System.err.println("warning: Parser does not support feature (" + DYNAMIC_VALIDATION_FEATURE_ID + ")");
        }

        // parse file
        parser.setContentHandler(this);
        parser.setErrorHandler(this);

        // handlers
        setupHandlers();

        parser.parse(new InputSource(InitialDataParser.class.getResource(name).openStream()));
    }
    
}
