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

package com.echothree.ui.cli.amazon.batch.order;

import com.echothree.util.common.string.StringUtils;
import java.io.File;
import java.io.IOException;
import javax.naming.NamingException;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BatchOrderDirectoryHandler {

    private final Log LOG = LogFactory.getLog(this.getClass());

    private Configuration configuration;
    private String directory;

    private void init(Configuration configuration, String directory) {
        this.configuration = configuration;
        this.directory = directory;
    }

    /** Creates a new instance of BatchOrderDirectoryHandler */
    public BatchOrderDirectoryHandler(Configuration configuration, String directory) {
        init(configuration, directory);
    }

    /** Creates a new instance of BatchOrderDirectoryHandler */
    public BatchOrderDirectoryHandler(Configuration configuration) {
        init(configuration, null);
    }

    /**
     * Returns the directory.
     * @return the directory
     */
    public String getDirectory() {
        return directory;
    }

    /**
     * Sets the directory.
     * @param directory the directory to set
     */
    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public void execute()
            throws IOException, NamingException {
        LOG.info("Using directory: " + directory);

        recurseFile(new File(directory), 0);
    }

    private void recurseFile(File file, int depth)
            throws IOException, NamingException {
        var indent = StringUtils.getInstance().repeatingStringFromChar(' ', depth);

        if(file.isDirectory()) {
            String contents[] = file.list();

            LOG.info(indent + "Directory: " + file.getName());

            for(var i = 0; i < contents.length; i++) {
                recurseFile(new File(new StringBuilder(file.getAbsolutePath()).append('/').append(contents[i]).toString()), depth + 1);
            }
        } else {
            LOG.info(indent + "File: " + file.getName());

            new BatchOrderFileHandler(configuration, file.getAbsolutePath(), depth + 1).execute();
        }
    }

}
