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

package com.echothree.ui.cli.dataloader.util.data;

import com.echothree.control.user.authentication.common.AuthenticationUtil;
import com.echothree.control.user.party.client.helper.NameSuffixesHelper;
import com.echothree.control.user.party.client.helper.PersonalTitlesHelper;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.NowhereHandler;
import java.io.FileNotFoundException;
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
import org.xml.sax.Locator;
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
    private Locator documentLocator;
    private int sourceLine = -1;
    private int sourceColumn = -1;
    private final Deque<String> elements = new ArrayDeque<>();

    @Override
    public void setDocumentLocator(Locator locator) {
        documentLocator = locator;
        captureSourcePosition();
    }

    public String getSourceLocation() {
        return getSourceLocation(sourceLine, sourceColumn);
    }

    private void captureSourcePosition() {
        if(documentLocator != null) {
            sourceLine = documentLocator.getLineNumber();
            sourceColumn = documentLocator.getColumnNumber();
        }
    }

    private String getSourceLocation(int lineNumber, int columnNumber) {
        var location = new StringBuilder(name == null ? "XML input" : name);

        if(lineNumber > 0) {
            location.append(':').append(lineNumber);
            if(columnNumber > 0) {
                location.append(':').append(columnNumber);
            }
        }

        var element = elements.peek();
        if(element != null) {
            location.append(" <").append(element).append('>');
        }

        return location.toString();
    }
    
    /** Start document. */
    @Override
    public void startDocument()
            throws SAXException {
        captureSourcePosition();

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
        captureSourcePosition();
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
        captureSourcePosition();
        elements.push(qName.isEmpty() ? localName : qName);

        try {
            getDefaultHandler().startElement(namespaceURI, localName, qName, attrs);
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        captureSourcePosition();
        getDefaultHandler().endElement(namespaceURI, localName, qName);
        elements.pop();
    }
    
    @Override
    public void characters(char ch[], int start, int length)
            throws SAXException {
        captureSourcePosition();
        getDefaultHandler().characters(ch, start, length);
    }
    
    /** Ignorable whitespace. */
    @Override
    public void ignorableWhitespace(char ch[], int start, int length)
            throws SAXException {
        captureSourcePosition();
        getDefaultHandler().ignorableWhitespace(ch, start, length);
    }
    
    /** Processing instruction. */
    @Override
    public void processingInstruction(String target, String data)
            throws SAXException {
        captureSourcePosition();
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
        var location = getSourceLocation(ex.getLineNumber(), ex.getColumnNumber());

        throw new SAXParseException(location + ": " + ex.getMessage(), ex.getPublicId(), ex.getSystemId(),
                ex.getLineNumber(), ex.getColumnNumber(), ex);
    }
    
    //
    // Protected methods
    //
    
    /** Prints the error message. */
    protected void printError(String type, SAXParseException ex) {
        var location = getSourceLocation(ex.getLineNumber(), ex.getColumnNumber());

        System.err.println("[" + type + "] " + location + ": " + ex.getMessage());
    }
    
    //
    // Constructors
    //
    
    /** Creates a new instance of InitialDataParser */
    public InitialDataParser(final String name) {
        this.name = name;
    }

    public void execute()
            throws IOException, SAXException, ParserConfigurationException {
        var resource = name == null ? null : InitialDataParser.class.getResource(name);

        if(resource == null) {
            var requestedResource = name == null ? "(no resource name specified)" : name;
            throw new FileNotFoundException("Data Loader XML resource not found " + requestedResource);
        }

        XMLReader parser;

        // create parser
        try {
            parser = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        } catch(Exception e) {
            System.err.println("error: Unable to instantiate parser for " + name);
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

        var systemId = resource.toExternalForm();
        try(var inputStream = resource.openStream()) {
            var inputSource = new InputSource(inputStream);
            inputSource.setSystemId(systemId);
            parser.parse(inputSource);
        } catch(SAXParseException e) {
            throw e;
        } catch(SAXException | RuntimeException e) {
            throw new SAXParseException(getSourceLocation() + ": " + e.getMessage(), null, systemId,
                    sourceLine, sourceColumn, e);
        } catch(IOException e) {
            throw new IOException(getSourceLocation() + ": " + e.getMessage(), e);
        } finally {
            documentLocator = null;
            sourceLine = -1;
            sourceColumn = -1;
            elements.clear();
        }
    }
    
}
