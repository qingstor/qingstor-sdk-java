## 基本图片处理

用于对用户存储于 QingStor 对象存储上的图片进行各种基本处理，例如格式转换，裁剪，翻转，水印等。

目前支持的图片格式有:

- png
- tiff
- webp
- jpeg
- pdf
- gif
- svg

> 目前不支持对加密过后的图片进行处理，单张图片最大为 10M 。

您可以参考以下示例使用 ImageProcessClient 对图片进行处理。具体文档说明参考 [API Docs](https://docs.qingcloud.com/qingstor/data_process/image_process/) 。

## 代码片段

```java
    private void imageProcessClientDemo() throws QSException {
        EvnContext evn = new EvnContext("您的 accessKey", "您的 secretKey"); // 用服务端签名时，此处可以为空
        Bucket bucket = new Bucket(
                evn, "zone 名称", "bucket 名称");
        ImageProcessClient client = new ImageProcessClient("head.jpg", bucket);

        // 获取图片信息
        Bucket.ImageProcessOutput imageProcessOutput = client.info().imageProcess();
        String result = inputStream2Json(imageProcessOutput.getBodyInputStream());
        if (imageProcessOutput.getStatueCode() >= 200 && imageProcessOutput.getStatueCode() < 300) {
            // 成功
            ImageInfoBean imageInfo = new Gson().fromJson(result, ImageInfoBean.class);

            System.out.println("Width = " + imageInfo.getWidth() + " px");
            System.out.println("Height = " + imageInfo.getHeight() + " px");
            System.out.println("Orientation = " + imageInfo.getOrientation());
            System.out.println("isAlpha = " + imageInfo.isAlpha());
            System.out.println("Type = " + imageInfo.getType());
            System.out.println("Space = " + imageInfo.getSpace());
        } else { // 失败
            OutputModel outputModel = new Gson().fromJson(result, OutputModel.class);
            outputModel.setStatueCode(imageProcessOutput.getStatueCode());

            System.out.println("获取图片信息请求失败。");
            System.out.println("StatueCode = " + outputModel.getStatueCode());
            System.out.println("Code = " + outputModel.getCode());
            System.out.println("RequestId = " + outputModel.getRequestId());
            System.out.println("Message = " + outputModel.getMessage());
            System.out.println("Url = " + outputModel.getUrl());
        }

        // 对图片进行操作
        ImageProcessClient actionClient = new ImageProcessClient("head.jpg", bucket);
        actionClient.crop(new ImageProcessClient.CropParam.Builder() // 裁剪
                .width(100)
                .height(100)
                .gravity(0) // 0 = center; 1 = north; 2 = east; 3 = south; 4 = west; 5 = north west;
                            // 6 = north east;7 = south west; 8 = south east;; 9 = auto; default = 0.
                .build())
                .rotate(new ImageProcessClient.RotateParam(90)) // 旋转
                .resize(new ImageProcessClient.ResizeParam.Builder() // Resize
                        .width(200)
                        .height(200)
                        .mode(0) // 0 表示固定宽高，缩略填充；1 表示根据宽高自动调节；
                                 // 2 表示按照宽高比为 4:4 进行缩略，若 width 和 height 只设置了其中一个，则按照宽度或者高度等比缩放。默认为 0。
                        .build())
                .waterMark(new ImageProcessClient.WaterMarkParam.Builder("text") // 文字水印
                        .color("#FFFFFF")
                        .dpi(400)
                        .opacity(0.8)
                        .build())
                .waterMarkImage(new ImageProcessClient.WaterMarkImageParam.Builder("https://www.qingcloud.com/static/assets/images/icons/common/footer_logo.svg") // 图片水印
                        .left(400)
                        .top(400)
                        .opacity(1)
                    .build())
                .format(new ImageProcessClient.FormatParam("png")); // 格式转换。支持的格式: jpeg, png, webp, tiff

        // 获取带过期时间的链接下载图片
        RequestHandler requestHandler = actionClient.getImageProgressExpiredUrlRequest(System.currentTimeMillis()/1000 + 1000); // 1000 秒后过期
        System.out.println("Image download url = " + requestHandler.getExpiresRequestUrl());
        // 建立网络连接下载图片
        Bucket.ImageProcessOutput output = actionClient.imageProcess();
        handleImageProcessOutput(output, new File("/您保存文件的路径/文件名.png"));
```

## 使用服务端签名
如您需要使用服务端进行签名操作，请使用 Client 获取对应的 RequestHandler ，然后签名即可。

详细说明请参考[使用服务端签名](./sign_with_server_zh.md)。

下面以本示例中对图片进行旋转 90 度操作为例：

```java
    RequestHandler requestHandler1 = new ImageProcessClient("head.jpg", bucket)
        .rotate(new ImageProcessClient.RotateParam(90))
        .imageProgressRequest();// 旋转
    String stringToSignature = requestHandler1.getStringToSignature();
    // 向服务端请求签名，这里本地模拟。
    String serverAuthorization = QSSignatureUtil.generateSignature("您的 secretKey",
        stringToSignature);
    requestHandler1.setSignature("您的 accesskey", serverAuthorization);
    /**
     * 因客户端跟服务端通讯可能有时间差，而签名计算结果跟时间密切相关。
     * 因此需要将服务端计算签名时所用的时间设置到 request 中。
     * 您可以发送 strToSignature 到服务端以获取服务端签名的时间。
    **/
    requestHandler1.getBuilder().setHeader(QSConstant.HEADER_PARAM_KEY_DATE, gmtTime);
    Bucket.ImageProcessOutput send = (Bucket.ImageProcessOutput) requestHandler1.send();
    handleImageProcessOutput(send, new File("/您保存文件的路径/文件名.jpg"));
```


## 附录

附上述代码引用的相关方法和类：

```java
    private String inputStream2Json(InputStream bodyInputStream) {
        StringBuilder result = new StringBuilder();
        try {
            if (bodyInputStream != null) {
                String readContent;
                BufferedReader reader = new BufferedReader(new InputStreamReader(bodyInputStream));
                while ((readContent = reader.readLine()) != null) {
                    result.append(readContent);
                    result.append(System.getProperty("line.separator"));
                }
                bodyInputStream.close();
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();

    }

```

```java
    class ImageInfoBean {

        /**
         * width : 1134
         * height : 1844
         * orientation : 0
         * alpha : true
         * type : png
         * space : srgb
         */

        private int width;
        private int height;
        private int orientation;
        private boolean alpha;
        private String type;
        private String space;

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getOrientation() {
            return orientation;
        }

        public void setOrientation(int orientation) {
            this.orientation = orientation;
        }

        public boolean isAlpha() {
            return alpha;
        }

        public void setAlpha(boolean alpha) {
            this.alpha = alpha;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSpace() {
            return space;
        }

        public void setSpace(String space) {
            this.space = space;
        }

    }

```

```java
    private void handleImageProcessOutput(Bucket.ImageProcessOutput output, @NotNull File keptFile) {
        try {
            InputStream bodyInputStream = output.getBodyInputStream();
            if (output.getStatueCode() >= 200 && output.getStatueCode() < 300) {
                // 成功，保存图片
                OutputStream out = null;

                out = new FileOutputStream(keptFile);
                int bytesRead = 0;
                byte[] buffer = new byte[1024];
                while ((bytesRead = bodyInputStream.read(buffer, 0, 1024)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
                out.flush();
                out.close();
            } else {
                // 处理错误
                String jsonResult = inputStream2Json(bodyInputStream);
                OutputModel outputModel = new Gson().fromJson(jsonResult, OutputModel.class);
                outputModel.setStatueCode(output.getStatueCode());
                System.out.println("图片处理请求出错。");
                System.out.println("StatueCode = " + outputModel.getStatueCode());
                System.out.println("Code = " + outputModel.getCode());
                System.out.println("RequestId = " + outputModel.getRequestId());
                System.out.println("Message = " + outputModel.getMessage());
                System.out.println("Url = " + outputModel.getUrl());

            }

            if (bodyInputStream != null) {
                bodyInputStream.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

```