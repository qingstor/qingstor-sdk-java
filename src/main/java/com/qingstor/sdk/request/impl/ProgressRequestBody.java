/**
 * ** +------------------------------------------------------------------------- | Copyright (C)
 * 2017 Yunify, Inc. +------------------------------------------------------------------------- |
 * Licensed under the Apache License, Version 2.0 (the "License"); | you may not use this work
 * except in compliance with the License. | You may obtain a copy of the License in the LICENSE
 * file, or at: | | http://www.apache.org/licenses/LICENSE-2.0 | | Unless required by applicable law
 * or agreed to in writing, software | distributed under the License is distributed on an "AS IS"
 * BASIS, | WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. | See the
 * License for the specific language governing permissions and | limitations under the License.
 * +------------------------------------------------------------------------- ***
 */
package com.qingstor.sdk.request.impl;

import com.qingstor.sdk.request.BodyProgressListener;
import com.qingstor.sdk.request.CancellationHandler;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;

/** @author karooli */
public class ProgressRequestBody extends RequestBody {

	private RequestBody requestBody;

	private BodyProgressListener listener;

	private CancellationHandler cancellationHandler;

	public ProgressRequestBody(RequestBody requestBody,
                               BodyProgressListener listener,
                               CancellationHandler cancellationHandler) {
		
		this.requestBody = requestBody;
		this.listener = listener;
		this.cancellationHandler = cancellationHandler;
	}

	@Override
	public MediaType contentType() {
		return requestBody.contentType();
	}

	@Override
	public void writeTo(BufferedSink bufferedSink) throws IOException {
		if (listener != null) {
			// 计算总长度
			Buffer buffer = new Buffer();
			requestBody.writeTo(buffer);
			long size = buffer.size();
			if (size == -1) {
				return;
			}

			// 然后一次写2048大小的内容
			int blockSize = 2048;
			long writeSize = 0;
			while (writeSize + blockSize < size) {
			    if (cancellationHandler != null && cancellationHandler.isCancelled())
			        throw new CancellationHandler.CancellationException();
				buffer.copyTo(bufferedSink.buffer(), writeSize, blockSize);
                bufferedSink.flush();
				writeSize += blockSize;
				listener.onProgress(writeSize, size);
			}

            if (cancellationHandler != null && cancellationHandler.isCancelled())
                throw new CancellationHandler.CancellationException();
			buffer.copyTo(bufferedSink.buffer(), writeSize, size - writeSize);
			bufferedSink.flush();
			listener.onProgress(writeSize, size);
			buffer.clear();
		} else {
			requestBody.writeTo(bufferedSink);
			bufferedSink.flush();
		}
	}
}
