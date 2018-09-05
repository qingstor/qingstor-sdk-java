/****
 +-------------------------------------------------------------------------
 | Copyright (C) 2017 Yunify, Inc.
 +-------------------------------------------------------------------------
 | Licensed under the Apache License, Version 2.0 (the "License");
 | you may not use this work except in compliance with the License.
 | You may obtain a copy of the License in the LICENSE file, or at:
 |
 | http://www.apache.org/licenses/LICENSE-2.0
 |
 | Unless required by applicable law or agreed to in writing, software
 | distributed under the License is distributed on an "AS IS" BASIS,
 | WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 | See the License for the specific language governing permissions and
 | limitations under the License.
 +-------------------------------------------------------------------------
*****/
package com.qingstor.sdk.request.impl;

import com.qingstor.sdk.utils.QSLoggerUtil;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Logger;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * @author karooli
 *
 */
public class MultiFileuploadRequestBody extends RequestBody {

	private static Logger logger = QSLoggerUtil.setLoggerHanlder(MultiFileuploadRequestBody.class.getName());

	private String contentType;

	private long contentLength;

	private RandomAccessFile file;

	public MultiFileuploadRequestBody(String contentType, RandomAccessFile rFile, long contentLength) {
		logger.info("----InputStreamUploadBody----");
		this.contentLength = contentLength;
		this.contentType = contentType;
		this.file = rFile;
	}

	@Override
	public long contentLength() {
		return this.contentLength;
	}

	@Override
	public MediaType contentType() {
		return MediaType.parse(this.contentType);
	}

	@Override
	public void writeTo(BufferedSink sink) throws IOException {

		int readSize = 1024;
		int bytes = 0;
		byte[] bufferOut = new byte[readSize];
		int count = (int) (contentLength / readSize);
		int leftCount = (int) (contentLength % readSize);
		while (count > 0 && (bytes = file.read(bufferOut)) != -1) {
			sink.write(bufferOut, 0, bytes);
			count--;
		}
		if (count >= 0 && leftCount > 0) {
			bufferOut = new byte[leftCount];
			if ((bytes = file.read(bufferOut)) != -1) {
				sink.write(bufferOut, 0, bytes);
			}
		}
	}
}
