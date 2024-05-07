/*
 * Copyright (C) 2021 Yunify, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this work except in compliance with the License.
 * You may obtain a copy of the License in the LICENSE file, or at:
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qingstor.sdk.request.impl;

import java.io.IOException;
import java.io.InputStream;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @author karooli */
public class InputStreamUploadBody extends RequestBody {

    private static final Logger log = LoggerFactory.getLogger(InputStreamUploadBody.class);

    private String contentType;

    private long contentLength;

    private InputStream file;

    private long offset;

    public InputStreamUploadBody(String contentType, InputStream rFile, long contentLength) {
        this(contentType, rFile, contentLength, 0);
    }

    public InputStreamUploadBody(
            String contentType, InputStream rFile, long contentLength, long offset) {
        log.debug("----InputStreamUploadBody----");
        this.contentLength = contentLength;
        this.contentType = contentType;
        this.file = rFile;
        this.offset = offset;
    }

    @Override
    public MediaType contentType() {
        return MediaType.parse(this.contentType);
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {

        try {
            if (contentLength > 0) {
                writeWithContentLength(sink, offset);
            } else {
                writeAll(sink);
            }
            sink.flush();
        } finally {
            if (file != null) {
                file.close();
            }
        }
    }

    private void writeWithContentLength(BufferedSink sink, long offset) throws IOException {
        log.debug("---writeWithContentLength----");
        int readSize = 1024;
        int bytes = 0;
        byte[] bufferOut = new byte[readSize];
        long count = contentLength / readSize;
        long leftCount = contentLength % readSize;
        long iReadLength = 0;
        if (offset > 0) file.skip(offset);
        while (count > 0 && (bytes = file.read(bufferOut)) != -1) {
            sink.write(bufferOut, 0, bytes);
            count--;
            iReadLength += bytes;
            if (bytes != readSize) {
                count = (contentLength - iReadLength) / readSize;
                leftCount = (contentLength - iReadLength) % readSize;
            }
        }
        if (count == 0 && leftCount > 0) {
            bufferOut = new byte[(int) leftCount];
            if ((bytes = file.read(bufferOut)) != -1) {
                sink.write(bufferOut, 0, bytes);
            }
        }
    }

    private void writeAll(BufferedSink sink) throws IOException {
        log.debug("---writeAll----");
        int readSize = 1024;
        int bytes = 0;
        byte[] bufferOut = new byte[readSize];

        while ((bytes = file.read(bufferOut)) != -1) {
            sink.write(bufferOut, 0, bytes);
        }
    }
}
