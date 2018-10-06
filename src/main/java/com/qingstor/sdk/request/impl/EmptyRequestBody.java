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

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * @author karooli
 *
 */
public class EmptyRequestBody extends RequestBody {

	private String contentType;

	private int contentLength = 0;

	public EmptyRequestBody(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public long contentLength() {
		return this.contentLength;
	}

	@Override
	public void writeTo(BufferedSink sink) {
	}

	@Override
	public MediaType contentType() {
		return MediaType.parse(this.contentType);
	}

}
