// +-------------------------------------------------------------------------
// | Copyright (C) 2016 Yunify, Inc.
// +-------------------------------------------------------------------------
// | Licensed under the Apache License, Version 2.0 (the "License");
// | you may not use this work except in compliance with the License.
// | You may obtain a copy of the License in the LICENSE file, or at:
// |
// | http://www.apache.org/licenses/LICENSE-2.0
// |
// | Unless required by applicable law or agreed to in writing, software
// | distributed under the License is distributed on an "AS IS" BASIS,
// | WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// | See the License for the specific language governing permissions and
// | limitations under the License.
// +-------------------------------------------------------------------------

package com.qingstor.sdk.service;

import com.qingstor.sdk.annotation.ParamAnnotation;
import com.qingstor.sdk.config.EvnContext;
import com.qingstor.sdk.constants.QSConstant;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.model.OutputModel;
import com.qingstor.sdk.model.RequestInputModel;
import com.qingstor.sdk.request.RequestHandler;
import com.qingstor.sdk.request.ResourceRequestFactory;
import com.qingstor.sdk.request.ResponseCallBack;
import com.qingstor.sdk.service.Types.BucketModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// QingStorService: QingStor provides low-cost and reliable online storage service with unlimited storage space, high read and write performance, high reliability and data safety, fine-grained access control, and easy to use API.
public class QingStor {
    private String zone;
    private EvnContext evnContext;
    private String bucketName;

    public QingStor(EvnContext evnContext) {
        this(evnContext, QSConstant.STOR_DEFAULT_ZONE);
    }

    public QingStor(EvnContext evnContext, String zone) {
        this.evnContext = evnContext;
        this.zone = zone;
    }

    /*
     *
     * @param input
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/service/get.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public ListBucketsOutput listBuckets(ListBucketsInput input) throws QSException {

        if (input == null) {
            input = new ListBucketsInput();
        }

        RequestHandler requestHandler = this.listBucketsRequest(input);

        OutputModel backModel = requestHandler.send();
        if (backModel != null) {
            return (ListBucketsOutput) backModel;
        }
        return null;
    }

    /*
     *
     * @param input
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/service/get.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler listBucketsRequest(ListBucketsInput input) throws QSException {

        if (input == null) {
            input = new ListBucketsInput();
        }

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "ListBuckets");
        context.put("APIName", "ListBuckets");
        context.put("ServiceName", "Get Service");
        context.put("RequestMethod", "GET");
        context.put("RequestURI", "/");
        context.put("bucketNameInput", this.bucketName);

        RequestHandler requestHandler =
                ResourceRequestFactory.getResourceRequest()
                        .getRequest(context, input, ListBucketsOutput.class);

        return requestHandler;
    }

    /*
     *
     * @param input
     * @param callback
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/service/get.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void listBucketsAsync(
            ListBucketsInput input, ResponseCallBack<ListBucketsOutput> callback)
            throws QSException {

        if (input == null) {
            input = new ListBucketsInput();
        }

        RequestHandler requestHandler = this.listBucketsAsyncRequest(input, callback);

        requestHandler.sendAsync();
    }

    /*
     *
     * @param input
     * @param callback
     * @throws QSException
     *
     * Documentation URL: https://docs.qingcloud.com/qingstor/api/service/get.html
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RequestHandler listBucketsAsyncRequest(
            ListBucketsInput input, ResponseCallBack<ListBucketsOutput> callback)
            throws QSException {
        if (input == null) {
            input = new ListBucketsInput();
        }

        Map context = new HashMap();
        context.put(QSConstant.PARAM_KEY_REQUEST_ZONE, this.zone);
        context.put(QSConstant.EVN_CONTEXT_KEY, this.evnContext);
        context.put("OperationName", "ListBuckets");
        context.put("APIName", "ListBuckets");
        context.put("ServiceName", "Get Service");
        context.put("RequestMethod", "GET");
        context.put("RequestURI", "/");
        context.put("bucketNameInput", this.bucketName);

        if (callback == null) {
            throw new QSException("callback can't be null");
        }

        RequestHandler requestHandler =
                ResourceRequestFactory.getResourceRequest()
                        .getRequestAsync(context, input, callback);
        return requestHandler;
    }

    public static class ListBucketsInput extends RequestInputModel {

        // Limits results to buckets that in the location

        private String location;

        public void setLocation(String location) {
            this.location = location;
        }

        @ParamAnnotation(paramType = "header", paramName = "Location")
        public String getLocation() {
            return this.location;
        }

        @Override
        public String validateParam() {

            return null;
        }
    }

    public static class ListBucketsOutput extends OutputModel {

        // Buckets information

        private List<BucketModel> buckets;

        public void setBuckets(List<BucketModel> buckets) {
            this.buckets = buckets;
        }

        @ParamAnnotation(paramType = "query", paramName = "buckets")
        public List<BucketModel> getBuckets() {
            return this.buckets;
        } // Bucket count

        private Long count;

        public void setCount(Long count) {
            this.count = count;
        }

        @ParamAnnotation(paramType = "query", paramName = "count")
        public Long getCount() {
            return this.count;
        }
    }

    @Deprecated
    public com.qingstor.sdk.service.Bucket getBucket(String bucketName) {
        return new com.qingstor.sdk.service.Bucket(this.evnContext, this.zone, bucketName);
    }

    public com.qingstor.sdk.service.Bucket getBucket(String bucketName, String zone) {
        return new com.qingstor.sdk.service.Bucket(this.evnContext, zone, bucketName);
    }
}
