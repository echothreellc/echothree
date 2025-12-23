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

/*
 * $Id: $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.echothree.ui.web.main.framework;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * This is an abstract base class that minimizes the amount of special coding
 * that needs to be written to download a file. All that is required to use
 * this class is to extend it and implement the <code>getStreamInfo()</code>
 * method so that it returns the relevant information for the file (or other
 * stream) to be downloaded. Optionally, the <code>getBufferSize()</code>
 * method may be overridden to customize the size of the buffer used to
 * transfer the file.
 *
 * @since Struts 1.2.6
 */
public abstract class MainBaseDownloadAction
        extends MainBaseAction<ActionForm> {

    protected MainBaseDownloadAction() {
        super();
    }

    protected MainBaseDownloadAction(boolean partyRequired, boolean forceChangeCheck) {
        super(partyRequired, forceChangeCheck);
    }

    /**
     * If the <code>getBufferSize()</code> method is not overridden, this is
     * the buffer size that will be used to transfer the data to the servlet
     * output stream.
     */
    protected static final int DEFAULT_BUFFER_SIZE = 4096;

    /**
     * Returns the information on the file, or other stream, to be downloaded
     * by this action. This method must be implemented by an extending class.
     *
     * @param mapping  The ActionMapping used to select this instance.
     * @param form     The optional ActionForm bean for this request (if
     *                 any).
     * @param request  The HTTP request we are processing.
     * @param response The HTTP response we are creating.
     * @return The information for the file to be downloaded.
     * @throws Exception if an exception occurs.
     */
    protected abstract StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception;

    /**
     * Returns the size of the buffer to be used in transferring the data to
     * the servlet output stream. This method may be overridden by an
     * extending class in order to customize the buffer size.
     *
     * @return The size of the transfer buffer, in bytes.
     */
    protected int getBufferSize() {
        return DEFAULT_BUFFER_SIZE;
    }

    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     *
     * @param mapping  The ActionMapping used to select this instance.
     * @param form     The optional ActionForm bean for this request (if
     *                 any).
     * @param request  The HTTP request we are processing.
     * @param response The HTTP response we are creating.
     * @return The forward to which control should be transferred, or
     *         <code>null</code> if the response has been completed.
     * @throws Exception if an exception occurs.
     */
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        String forwardKey = null;
        var info = getStreamInfo(mapping, form, request, response);

        if(info == null) {
            forwardKey = ForwardConstants.ERROR_404;
        } else {
            var contentType = info.getContentType();
            var stream = info.getInputStream();

            try {
                var contentDisposition = info.getContentDisposition();
                
                response.setContentType(contentType);
                
                if(contentDisposition != null) {
                    var filename = info.getFilename();
                    var value = new StringBuilder(contentDisposition.length() + (filename == null ? 0 : 2 + filename.length()));
                    
                    value.append(contentDisposition);
                    if(filename != null) {
                        value.append("; filename=\"").append(filename).append("\"");
                    }
                    
                    response.addHeader("Content-Disposition", value.toString());
                }
                
                copy(stream, response.getOutputStream());
            } finally {
                if (stream != null) {
                    stream.close();
                }
            }
        }

        // Tell Struts that we are done with the response.
        return forwardKey == null ? null : mapping.findForward(forwardKey);
    }

    /**
     * Copy bytes from an <code>InputStream</code> to an
     * <code>OutputStream</code>.
     *
     * @param input  The <code>InputStream</code> to read from.
     * @param output The <code>OutputStream</code> to write to.
     * @return the number of bytes copied
     * @throws IOException In case of an I/O problem
     */
    public int copy(InputStream input, OutputStream output)
        throws IOException {
        var buffer = new byte[getBufferSize()];
        var count = 0;
        var n = 0;

        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }

        return count;
    }

    /**
     * The information on a file, or other stream, to be downloaded by the
     * <code>DownloadAction</code>.
     */
    public static interface StreamInfo {
        
        /**
         * Returns the content type of the stream to be downloaded.
         *
         * @return The content type of the stream.
         */
        String getContentType();

        /**
         * Returns an input stream on the content to be downloaded. This
         * stream will be closed by the <code>DownloadAction</code>.
         *
         * @return The input stream for the content to be downloaded.
         * @throws IOException if an error occurs
         */
        InputStream getInputStream()
            throws IOException;
        
        String getContentDisposition();
        
        String getFilename();
        
    }

}
