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

package com.echothree.ui.cli.amazon.batch.order;

import com.echothree.ui.cli.amazon.batch.order.content.AmazonOrders;
import com.echothree.ui.cli.amazon.batch.order.tasks.BuildOrders;
import com.echothree.ui.cli.amazon.batch.order.tasks.DumpOrders;
import com.echothree.ui.cli.amazon.batch.order.tasks.EnterOrders;
import com.echothree.util.common.string.StringUtils;
import com.google.common.base.Splitter;
import java.io.IOException;
import javax.naming.NamingException;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BatchOrderFileHandler {

    private final Log LOG = LogFactory.getLog(this.getClass());

    private static final Splitter TAB_SPLITTER = Splitter.on('\t')
           .trimResults();

    private Configuration configuration;
    private String filename;
    int depth;

    private String indent;

    private void init(Configuration configuration, String filename, int depth) {
        this.configuration = configuration;
        this.filename = filename;
        this.depth = depth;
        
        indent = StringUtils.getInstance().repeatingStringFromChar(' ', depth);
    }

    /** Creates a new instance of BatchOrderFileHandler */
    public BatchOrderFileHandler(Configuration configuration, String filename, int depth) {
        init(configuration, filename, depth);
    }

    /** Creates a new instance of BatchOrderFileHandler */
    public BatchOrderFileHandler(Configuration configuration, String filename) {
        init(configuration, filename, 0);
    }

    public void execute()
            throws IOException, NamingException {
        var amazonOrders = new AmazonOrders();
        
        new BuildOrders(filename, amazonOrders, depth).execute();
        new DumpOrders(amazonOrders).execute();
        new EnterOrders(configuration, amazonOrders, depth).execute();
    }

}
