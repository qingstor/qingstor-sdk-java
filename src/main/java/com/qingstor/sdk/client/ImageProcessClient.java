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

package com.qingstor.sdk.client;

import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.request.RequestHandler;
import com.qingstor.sdk.request.ResponseCallBack;
import com.qingstor.sdk.service.Bucket;
import com.qingstor.sdk.service.Bucket.ImageProcessOutput;
import com.qingstor.sdk.utils.Base64;

public class ImageProcessClient {
    private static final String OPSep = "|";

    private String objectName;
    private Bucket bucket;
    private Bucket.ImageProcessInput input;

    public ImageProcessClient(String objectName, Bucket bucket) {
        this.objectName = objectName;
        this.bucket = bucket;
        this.input = new Bucket.ImageProcessInput();
    }

    public ImageProcessOutput imageProcess() throws QSException {
        return this.bucket.imageProcess(this.objectName, this.input);
    }

    public void imageProgressAsync(ResponseCallBack<ImageProcessOutput> callback) throws QSException {
        bucket.imageProcessAsync(objectName, input, callback);
    }

    public RequestHandler imageProcessAsyncRequest(ResponseCallBack<ImageProcessOutput> callback)
            throws QSException {
        return this.bucket.imageProcessAsyncRequest(this.objectName, this.input, callback);
    }

    public RequestHandler imageProgressRequest() throws QSException {
        return bucket.imageProcessRequest(objectName, input);
    }

    public RequestHandler getImageProgressExpiredUrlRequest(long expires) throws QSException {
        return bucket.imageProcessExpiredUrlRequest(this.objectName, this.input, expires);
    }

    public ImageProcessClient info() {
        buildOptParamStr(new InfoParam());
        return this;
    }

    public ImageProcessClient crop(CropParam param) {
        buildOptParamStr(param);
        return this;
    }

    public ImageProcessClient rotate(RotateParam param) {
        buildOptParamStr(param);
        return this;
    }

    public ImageProcessClient resize(ResizeParam param) {
        buildOptParamStr(param);
        return this;
    }

    public ImageProcessClient waterMark(WaterMarkParam param) {
        buildOptParamStr(param);
        return this;
    }

    public ImageProcessClient waterMarkImage(WaterMarkImageParam param) {
        buildOptParamStr(param);
        return this;
    }

    public ImageProcessClient format(FormatParam param) {
        buildOptParamStr(param);
        return this;
    }

    private void buildOptParamStr(ImageParam param) {
        if (isEmptyAction()) {
            input.setAction(param.buildOptParamStr());
        } else {
            StringBuffer sb = new StringBuffer(input.getAction());
            String action = sb.append(OPSep).append(param.buildOptParamStr()).toString();
            input.setAction(action);
        }
    }

    private boolean isEmptyAction() {
        return input.getAction() == null ? true : false;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public Bucket getBucket() {
        return bucket;
    }

    public void setBucket(Bucket bucket) {
        this.bucket = bucket;
    }

    public Bucket.ImageProcessInput getInput() {
        return this.input;
    }

    public void setInput(Bucket.ImageProcessInput input) {
        this.input = input;
    }

    public interface ImageParam {
        public String buildOptParamStr();
    }

    public static class InfoParam implements ImageParam {
        @Override
        public String buildOptParamStr() {
            return new StringBuffer("info").toString();
        }
    }

    public static class CropParam implements ImageParam {
        private int width;
        private int height;
        private int gravity;

        public static class Builder {
            private int width;
            private int height;
            private int gravity;

            public Builder width(int val) {
                this.width = val;
                return this;
            }

            public Builder height(int val) {
                this.height = val;
                return this;
            }

            public Builder gravity(int val) {
                this.gravity = val;
                return this;
            }

            public CropParam build() {
                return new CropParam(this);
            }
        }

        private CropParam(Builder builder) {
            this.width = builder.width;
            this.height = builder.height;
            this.gravity = builder.gravity;
        }

        @Override
        public String buildOptParamStr() {
            StringBuffer sb = new StringBuffer("crop:");
            sb.append("w_")
                    .append(this.width)
                    .append(",h_")
                    .append(this.height)
                    .append(",g_")
                    .append(this.gravity);
            return sb.toString();
        }
    }

    public static class RotateParam implements ImageParam {
        private int angle;

        public RotateParam(int angle) {
            this.angle = angle;
        }

        @Override
        public String buildOptParamStr() {
            return new StringBuffer("rotate:a_").append(angle).toString();
        }
    }

    public static class ResizeParam implements ImageParam {
        private int width;
        private int height;
        private int mode;

        public static class Builder {
            private int width;
            private int height;
            private int mode;

            public Builder width(int val) {
                this.width = val;
                return this;
            }

            public Builder height(int val) {
                this.height = val;
                return this;
            }

            public Builder mode(int mode) {
                this.mode = mode;
                return this;
            }

            public ResizeParam build() {
                return new ResizeParam(this);
            }
        }

        private ResizeParam(Builder builder) {
            this.width = builder.width;
            this.height = builder.height;
            this.mode = builder.mode;
        }

        @Override
        public String buildOptParamStr() {
            StringBuffer sb = new StringBuffer("resize:");
            sb.append("w_");
            sb.append(this.width);
            sb.append(",h_");
            sb.append(this.height);
            sb.append(",m_");
            sb.append(this.mode);
            return sb.toString();
        }
    }

    public static class WaterMarkParam implements ImageParam {
        private int dpi;
        private double opacity;
        private String text;
        private String color;

        public static class Builder {
            private int dpi = 150;
            private double opacity = 0.25;
            private String text;
            private String color;

            public Builder(String text) {
                this.text = text;
            }

            public Builder dpi(int val) {
                this.dpi = val;
                return this;
            }

            public Builder opacity(double val) {
                this.opacity = val;
                return this;
            }

            public Builder color(String val) {
                this.color = val;
                return this;
            }

            public WaterMarkParam build() {
                return new WaterMarkParam(this);
            }
        }

        private WaterMarkParam(Builder builder) {
            this.dpi = builder.dpi;
            this.opacity = builder.opacity;
            this.text = builder.text;
            this.color = builder.color;
        }

        @Override
        public String buildOptParamStr() {
            StringBuffer sb = new StringBuffer("watermark:");
            sb.append("d_");
            sb.append(this.dpi);
            sb.append(",p_");
            sb.append(this.opacity);
            sb.append(",t_");
            sb.append(Base64.encode(text.getBytes()).replace("=", ""));
            if (this.color != null) {
                sb.append(",c_");
                String encode = Base64.encode(color.getBytes());
                sb.append(encode.replace("=", ""));
            }
            return sb.toString();
        }
    }

    public static class WaterMarkImageParam implements ImageParam {
        private int left;
        private int top;
        private double opacity;
        private String url;

        public static class Builder {
            private int left;
            private int top;
            private double opacity = 0.25;
            private String url;

            public Builder(String url) {
                this.url = url;
            }

            public Builder left(int val) {
                this.left = val;
                return this;
            }

            public Builder top(int val) {
                this.top = val;
                return this;
            }

            public Builder opacity(double val) {
                this.opacity = val;
                return this;
            }

            public WaterMarkImageParam build() {
                return new WaterMarkImageParam(this);
            }
        }

        private WaterMarkImageParam(Builder builder) {
            this.left = builder.left;
            this.top = builder.top;
            this.opacity = builder.opacity;
            this.url = builder.url;
        }

        @Override
        public String buildOptParamStr() {
            StringBuffer sb = new StringBuffer("watermark_image:");
            sb.append("l_");
            sb.append(this.left);
            sb.append(",t_");
            sb.append(this.top);
            sb.append(",p_");
            sb.append(this.opacity);
            sb.append(",u_");
            sb.append(Base64.encode(url.getBytes()).replace("=", ""));
            return sb.toString();
        }
    }

    public static class FormatParam implements ImageParam {
        public String type;

        public FormatParam(String type) {
            this.type = type;
        }

        @Override
        public String buildOptParamStr() {
            return new StringBuffer("format:t_").append(this.type).toString();
        }
    }
}
