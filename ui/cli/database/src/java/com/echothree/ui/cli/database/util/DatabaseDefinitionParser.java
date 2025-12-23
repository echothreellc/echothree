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

package com.echothree.ui.cli.database.util;

import com.echothree.ui.cli.database.CustomEntityResolver;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class DatabaseDefinitionParser
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
    // Data
    //
    
    protected Collection<String> myFiles;
    protected Databases myDatabases;
    protected Database currentDatabase;
    protected Component currentComponent;
    protected Table currentTable;
    protected Index currentIndex;
    
    //
    // Public methods
    //
    
    //
    // ContentHandler methods
    //
    
    static final int cStateNowhere = 0;
    static final int cStateInDatabase = 1;
    static final int cStateInColumnTypes = 2;
    static final int cStateInFiles = 3;
    static final int cStateInComponents = 4;
    static final int cStateInComponent = 5;
    static final int cStateInTables = 6;
    static final int cStateInTable = 7;
    static final int cStateInColumns = 8;
    static final int cStateInIndexes = 9;
    static final int cStateInIndex = 10;
    
    int currentState = cStateNowhere;
    
    void startElementInNowhere(String localName, Attributes attrs) throws SAXException {
        if(localName.equals("database")) {
            String attrName = null;

            var attrCount = attrs.getLength();
            for(var i = 0; i < attrCount; i++) {
                if(attrs.getQName(i).equals("name"))
                    attrName = attrs.getValue(i);
                else
                    throw new SAXException("unknown database attribute \"" + attrs.getQName(i) + "\"");
            }
            
            if(attrName != null) {
                currentDatabase = myDatabases.addDatabase(attrName);
                currentState = cStateInDatabase;
            } else {
                var whatsMissing = "";
                if(attrName == null)
                    whatsMissing += "name ";
                throw new SAXException("missing " + whatsMissing + "attribute(s) on database");
            }
        }
    }
    
    void startElementInDatabase(String localName, Attributes attrs) throws SAXException {
        switch(localName) {
            case "files" -> currentState = cStateInFiles;
            case "components" -> currentState = cStateInComponents;
            case "columnTypes" -> currentState = cStateInColumnTypes;
        }
    }
    
    void startElementInFiles(String localName, Attributes attrs) throws SAXException {
        if(localName.equals("file")) {
            String attrName = null;

            var attrCount = attrs.getLength();
            for(var i = 0; i < attrCount; i++) {
                if(attrs.getQName(i).equals("name"))
                    attrName = attrs.getValue(i);
            }
            
            if(attrName != null) {
                myFiles.add(attrName);
            } else {
                var whatsMissing = "";
                if(attrName == null)
                    whatsMissing += "name  ";
                throw new SAXException("missing " + whatsMissing + "attribute(s) on file");
            }
        }
    }
    
    void startElementInComponents(String localName, Attributes attrs) throws SAXException {
        if(localName.equals("component")) {
            String attrName = null;

            var attrCount = attrs.getLength();
            for(var i = 0; i < attrCount; i++) {
                if(attrs.getQName(i).equals("name"))
                    attrName = attrs.getValue(i);
            }
            
            if(attrName != null) {
                try {
                    currentComponent = currentDatabase.addComponent(attrName);
                    currentState = cStateInComponent;
                } catch (Exception e) {
                    throw new SAXException(e);
                }
            } else {
                var whatsMissing = "";
                if(attrName == null)
                    whatsMissing += "name  ";
                throw new SAXException("missing " + whatsMissing + "attribute(s) on component");
            }
        }
    }
    
    void startElementInComponent(String localName, Attributes attrs) throws SAXException {
        if(localName.equals("tables")) {
            currentState = cStateInTables;
        }
    }
    
    void startElementInColumnTypes(String localName, Attributes attrs) throws SAXException {
        if(localName.equals("columnType")) {
            String attrType = null;
            String attrRealType = null;
            String attrMaxLength = null;
            String attrDescription = null;
            String attrDestinationTable = null;
            String attrDestinationColumn = null;
            String attrOnParentDelete = null;

            var attrCount = attrs.getLength();
            for(var i = 0; i < attrCount; i++) {
                if(attrs.getQName(i).equals("type"))
                    attrType = attrs.getValue(i);
                else if(attrs.getQName(i).equals("realType"))
                    attrRealType = attrs.getValue(i);
                else if(attrs.getQName(i).equals("maxLength"))
                    attrMaxLength = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    attrDescription = attrs.getValue(i);
                else if(attrs.getQName(i).equals("destinationTable"))
                    attrDestinationTable = attrs.getValue(i);
                else if(attrs.getQName(i).equals("destinationColumn"))
                    attrDestinationColumn = attrs.getValue(i);
                else if(attrs.getQName(i).equals("onParentDelete"))
                    attrOnParentDelete = attrs.getValue(i);
            }
            
            if(attrType != null && attrRealType != null) {
                try {
                    currentDatabase.addColumnType(attrType, attrRealType, attrMaxLength, attrDescription, attrDestinationTable, attrDestinationColumn,
                            attrOnParentDelete);
                } catch (Exception e) {
                    throw new SAXException(e);
                }
            } else {
                var whatsMissing = "";
                if(attrType == null)
                    whatsMissing += "type  ";
                if(attrRealType == null)
                    whatsMissing += "realType ";
                throw new SAXException("missing " + whatsMissing + "attribute(s) on columnType");
            }
        }
    }
    
    void startElementInTables(String localName, Attributes attrs) throws SAXException {
        if(localName.equals("table")) {
            String attrNamePlural = null;
            String attrNameSingular = null;
            String attrColumnPrefix = null;
            String attrChunkSize = null;
            String attrDescription = null;

            var attrCount = attrs.getLength();
            for(var i = 0; i < attrCount; i++) {
                if(attrs.getQName(i).equals("namePlural"))
                    attrNamePlural = attrs.getValue(i);
                else if(attrs.getQName(i).equals("nameSingular"))
                    attrNameSingular = attrs.getValue(i);
                else if(attrs.getQName(i).equals("columnPrefix"))
                    attrColumnPrefix = attrs.getValue(i);
                else if(attrs.getQName(i).equals("chunkSize"))
                    attrChunkSize = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    attrDescription = attrs.getValue(i);
            }
            
            if(attrNamePlural != null && attrNameSingular != null && attrColumnPrefix != null) {
                try {
                    currentTable = currentDatabase.addTable(currentComponent, attrNamePlural,
                            attrNameSingular, attrColumnPrefix, attrChunkSize, attrDescription);
                    currentState = cStateInTable;
                } catch (Exception e) {
                    throw new SAXException(e);
                }
            } else {
                var whatsMissing = "";
                if(attrNamePlural == null)
                    whatsMissing += "namePlural ";
                if(attrNameSingular == null)
                    whatsMissing += "nameSingular ";
                if(attrColumnPrefix == null)
                    whatsMissing += "columnPrefix ";
                throw new SAXException("missing " + whatsMissing + "attribute(s) on table");
            }
        }
    }
    
    void startElementInTable(String localName, Attributes attrs) throws SAXException {
        if(localName.equals("columns")) {
            currentState = cStateInColumns;
        } else if(localName.equals("indexes")) {
            currentState = cStateInIndexes;
        }
    }
    
    void startElementInColumns(String localName, Attributes attrs) throws SAXException {
        if(localName.equals("column")) {
            String attrName = null;
            String attrType = null;
            String attrMaxLength = null;
            String attrDefaultValue = null;
            String attrSequenceSource = null;
            var attrNullAllowed = false;
            String attrDescription = null;
            String attrDestinationTable = null;
            String attrDestinationColumn = null;
            String attrOnParentDelete = null;

            var attrCount = attrs.getLength();
            for(var i = 0; i < attrCount; i++) {
                if(attrs.getQName(i).equals("name"))
                    attrName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("type"))
                    attrType = attrs.getValue(i);
                else if(attrs.getQName(i).equals("maxLength"))
                    attrMaxLength = attrs.getValue(i);
                else if(attrs.getQName(i).equals("defaultValue"))
                    attrDefaultValue = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sequenceSource"))
                    attrSequenceSource = attrs.getValue(i);
                else if(attrs.getQName(i).equals("nullAllowed"))
                    attrNullAllowed = attrs.getValue(i).equals("true");
                else if(attrs.getQName(i).equals("description"))
                    attrDescription = attrs.getValue(i);
                else if(attrs.getQName(i).equals("destinationTable"))
                    attrDestinationTable = attrs.getValue(i);
                else if(attrs.getQName(i).equals("destinationColumn"))
                    attrDestinationColumn = attrs.getValue(i);
                else if(attrs.getQName(i).equals("onParentDelete"))
                    attrOnParentDelete = attrs.getValue(i);
            }
            
            if(attrName != null && attrType != null) {
                try {
                    currentTable.addColumn(attrName, attrType, attrMaxLength, attrDefaultValue, attrSequenceSource, attrNullAllowed, attrDescription,
                            attrDestinationTable, attrDestinationColumn, attrOnParentDelete);
                } catch (Exception e) {
                    throw new SAXException(e);
                }
            } else {
                var whatsMissing = "";
                if(attrName == null)
                    whatsMissing += "name  ";
                if(attrType == null)
                    whatsMissing += "type ";
                throw new SAXException("missing " + whatsMissing + "attribute(s) on column");
            }
        }
    }
    
    void startElementInIndexes(String localName, Attributes attrs) throws SAXException {
        if(localName.equals("index")) {
            String attrType = null;
            String attrName = null;

            var attrCount = attrs.getLength();
            for(var i = 0; i < attrCount; i++) {
                if(attrs.getQName(i).equals("type"))
                    attrType = attrs.getValue(i);
                else if(attrs.getQName(i).equals("name"))
                    attrName = attrs.getValue(i);
            }
            
            if(attrType != null) {
                try {
                    currentIndex = currentTable.addIndex(attrType, attrName);
                } catch (Exception e) {
                    throw new SAXException(e);
                }
                currentState = cStateInIndex;
            } else {
                var whatsMissing = "";
                if(attrType == null)
                    whatsMissing += "type ";
                throw new SAXException("missing " + whatsMissing + "attribute(s) on index");
            }
        }
    }
    
    void startElementInIndex(String localName, Attributes attrs) throws SAXException {
        if(localName.equals("indexColumn")) {
            String attrName = null;

            var attrCount = attrs.getLength();
            for(var i = 0; i < attrCount; i++) {
                if(attrs.getQName(i).equals("name"))
                    attrName = attrs.getValue(i);
            }
            
            if(attrName != null) {
                try {
                    currentIndex.addIndexColumn(attrName);
                } catch (Exception e) {
                    throw new SAXException(e);
                }
            } else {
                var whatsMissing = "";
                if(attrName == null)
                    whatsMissing += "name  ";
                throw new SAXException("missing " + whatsMissing + "attribute(s) on table");
            }
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName,
            Attributes attrs) throws SAXException {
        switch(currentState) {
            case cStateNowhere -> startElementInNowhere(localName, attrs);
            case cStateInDatabase -> startElementInDatabase(localName, attrs);
            case cStateInFiles -> startElementInFiles(localName, attrs);
            case cStateInComponents -> startElementInComponents(localName, attrs);
            case cStateInComponent -> startElementInComponent(localName, attrs);
            case cStateInColumnTypes -> startElementInColumnTypes(localName, attrs);
            case cStateInTables -> startElementInTables(localName, attrs);
            case cStateInTable -> startElementInTable(localName, attrs);
            case cStateInColumns -> startElementInColumns(localName, attrs);
            case cStateInIndexes -> startElementInIndexes(localName, attrs);
            case cStateInIndex -> startElementInIndex(localName, attrs);
        }
    } // startElement(String,String,StringAttributes)
    
    /** End element. */
    
    void endElementInDatabase(String localName) {
        if(localName.equals("database")) {
            currentState = cStateNowhere;
        }
    }
    
    void endElementInFiles(String localName) {
        if(localName.equals("files")) {
            currentState = cStateInDatabase;
        }
    }
    
    void endElementInComponents(String localName) {
        if(localName.equals("components")) {
            currentState = cStateInDatabase;
        }
    }
    
    void endElementInComponent(String localName) {
        if(localName.equals("component")) {
            currentState = cStateInComponents;
            currentComponent = null;
        }
    }
    
    void endElementInColumnTypes(String localName) {
        if(localName.equals("columnTypes")) {
            currentState = cStateInDatabase;
        }
    }
    
    void endElementInTables(String localName) {
        if(localName.equals("tables")) {
            currentState = cStateInComponent;
        }
    }
    
    void endElementInTable(String localName)
            throws SAXException {
        if(localName.equals("table")) {
            if(currentTable.getColumns().isEmpty()) {
                throw new SAXException("table " + currentTable.getNamePlural() + " has no columns");
            } else {
                Set<Column> leadingColumns = new HashSet<>();

                // Create a Set that contains all the leftmost columns from the index on the table.
                currentTable.getIndexes().forEach((index) -> {
                    leadingColumns.add(index.getIndexColumns().iterator().next());
                });

                for(var column : currentTable.getForeignKeys()) {
                    if(!leadingColumns.contains(column)) {
                        throw new SAXException("foreign key column " + column.getName() + " in table " + currentTable.getNamePlural() +
                                " isn't first column in any indexes");
                    }
                }
            }

            currentState = cStateInTables;
            currentTable = null;
        }
    }
    
    void endElementInColumns(String localName) {
        if(localName.equals("columns")) {
            currentState = cStateInTable;
        }
    }
    
    void endElementInIndexes(String localName) {
        if(localName.equals("indexes")) {
            currentState = cStateInTable;
        }
    }
    
    void endElementInIndex(String localName)
            throws SAXException {
        if(localName.equals("index")) {
            var indexColumns = currentIndex.getIndexColumns().size();
            var isPrimaryKey = currentIndex.getType() == Index.indexPrimaryKey;
            
            if(indexColumns == 0 && !isPrimaryKey) {
                var indexName = currentIndex.getName();
                
                throw new SAXException("index " + (indexName == null? "(no name available)": indexName) + " in table " + currentTable.getNamePlural() + " has no columns");
            }
            
            currentState = cStateInIndexes;
            currentIndex = null;
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        switch(currentState) {
            case cStateNowhere -> {
            }
            case cStateInDatabase -> endElementInDatabase(localName);
            case cStateInFiles -> endElementInFiles(localName);
            case cStateInComponents -> endElementInComponents(localName);
            case cStateInComponent -> endElementInComponent(localName);
            case cStateInColumnTypes -> endElementInColumnTypes(localName);
            case cStateInTables -> endElementInTables(localName);
            case cStateInTable -> endElementInTable(localName);
            case cStateInColumns -> endElementInColumns(localName);
            case cStateInIndexes -> endElementInIndexes(localName);
            case cStateInIndex -> endElementInIndex(localName);
        }
    }
    
    //
    // ErrorHandler methods
    //
    
    /** Warning. */
    @Override
    public void warning(SAXParseException ex)
            throws SAXParseException {
        throw ex;
    } // warning(SAXParseException)
    
    /** Error. */
    @Override
    public void error(SAXParseException ex)
            throws SAXParseException {
        throw ex;
    } // error(SAXParseException)
    
    /** Fatal error. */
    @Override
    public void fatalError(SAXParseException ex)
            throws SAXParseException {
        throw ex;
    } // fatalError(SAXParseException)
    
    //
    // Constructors
    //
    /** Creates a new instance of DatabaseDefinitionParser */
    public DatabaseDefinitionParser(Databases theDatabases) {
        // variables
        myFiles = new ArrayList<>();
        myDatabases = theDatabases;
        
    }

    public void parseResource(XMLReader parser, String arg)
            throws IOException, SAXException {
        parser.parse(new InputSource(DatabaseDefinitionParser.class.getResource(arg).openStream()));
    }
    
    public void parse(String arg)
            throws Exception {
        XMLReader parser = null;

        // create parser
        try {
            parser = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        } catch (Exception e) {
            System.err.println("error: Unable to instantiate parser");
        }
        
        // set parser features
        try {
            parser.setFeature(NAMESPACES_FEATURE_ID, DEFAULT_NAMESPACES);
        } catch (SAXException e) {
            System.err.println("warning: Parser does not support feature ("+NAMESPACES_FEATURE_ID+")");
        }
        
        try {
            parser.setFeature(NAMESPACE_PREFIXES_FEATURE_ID, DEFAULT_NAMESPACE_PREFIXES);
        } catch (SAXException e) {
            System.err.println("warning: Parser does not support feature ("+NAMESPACE_PREFIXES_FEATURE_ID+")");
        }
        
        try {
            parser.setFeature(VALIDATION_FEATURE_ID, DEFAULT_VALIDATION);
        } catch (SAXException e) {
            System.err.println("warning: Parser does not support feature ("+VALIDATION_FEATURE_ID+")");
        }
        
        try {
            parser.setFeature(SCHEMA_VALIDATION_FEATURE_ID, DEFAULT_SCHEMA_VALIDATION);
        } catch (SAXNotRecognizedException e) {
            // ignore
        } catch (SAXNotSupportedException e) {
            System.err.println("warning: Parser does not support feature ("+SCHEMA_VALIDATION_FEATURE_ID+")");
        }
        
        try {
            parser.setFeature(SCHEMA_FULL_CHECKING_FEATURE_ID, DEFAULT_SCHEMA_FULL_CHECKING);
        } catch (SAXNotRecognizedException e) {
            // ignore
        } catch (SAXNotSupportedException e) {
            System.err.println("warning: Parser does not support feature ("+SCHEMA_FULL_CHECKING_FEATURE_ID+")");
        }
        
        try {
            parser.setFeature(DYNAMIC_VALIDATION_FEATURE_ID, DEFAULT_DYNAMIC_VALIDATION);
        } catch (SAXNotRecognizedException e) {
            // ignore
        } catch (SAXNotSupportedException e) {
            System.err.println("warning: Parser does not support feature ("+DYNAMIC_VALIDATION_FEATURE_ID+")");
        }
        
        // parse file
        parser.setContentHandler(this);
        parser.setErrorHandler(this);
        parser.setEntityResolver(new CustomEntityResolver());

        parseResource(parser, arg);

        for(var i : myFiles) {
            parseResource(parser, i);
        }
    }
    
}
