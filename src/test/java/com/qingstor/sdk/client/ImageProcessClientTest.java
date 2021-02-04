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
package com.qingstor.sdk.client;

import java.util.Base64;
import org.junit.Assert;
import org.junit.Test;

public class ImageProcessClientTest {

    @Test
    public void testBuildOptParamStr() {
        StringBuilder actionString = new StringBuilder();
        ImageProcessClient ipc = new ImageProcessClient(null, null);
        // Rotate
        ImageProcessClient.RotateParam rotateParam = new ImageProcessClient.RotateParam(90);
        ipc.rotate(rotateParam);
        actionString.append("rotate:a_90");
        Assert.assertEquals(ipc.getInput().getAction(), actionString.toString());
        // Crop
        ImageProcessClient.CropParam.Builder cropBuilder =
                new ImageProcessClient.CropParam.Builder();
        ImageProcessClient.CropParam cropParam = cropBuilder.width(300).height(400).build();
        ipc.crop(cropParam);
        actionString.append("|crop:w_300,h_400,g_0");
        Assert.assertEquals(ipc.getInput().getAction(), actionString.toString());
        // Resize
        ImageProcessClient.ResizeParam.Builder resizeBuilder =
                new ImageProcessClient.ResizeParam.Builder();
        ImageProcessClient.ResizeParam resizeParam =
                resizeBuilder.width(500).height(500).mode(1).build();
        ipc.resize(resizeParam);
        actionString.append("|resize:w_500,h_500,m_1");
        Assert.assertEquals(ipc.getInput().getAction(), actionString.toString());
        // Format
        ImageProcessClient.FormatParam formatParam = new ImageProcessClient.FormatParam("png");
        ipc.format(formatParam);
        actionString.append("|format:t_png");
        Assert.assertEquals(ipc.getInput().getAction(), actionString.toString());
        // Text watermark
        String markText = "5rC05Y2w5paH5a2X";
        ImageProcessClient.WaterMarkParam.Builder wrb =
                new ImageProcessClient.WaterMarkParam.Builder(markText);
        ImageProcessClient.WaterMarkParam waterMarkParam = wrb.build();
        ipc.waterMark(waterMarkParam);
        actionString
                .append("|watermark:d_150,p_0.25,t_")
                .append(
                        Base64.getEncoder()
                                .encodeToString(markText.getBytes())
                                .replace("=", "")); // Do base64 encode to mark text in SDK
        Assert.assertEquals(ipc.getInput().getAction(), actionString.toString());
        // Image watermark
        String markUrl = "aHR0cHM6Ly9wZWszYS5xaW5nc3Rvci5jb20vaW1nLWRvYy1lZy9xaW5jbG91ZC5wbmc";
        ImageProcessClient.WaterMarkImageParam.Builder wmib =
                new ImageProcessClient.WaterMarkImageParam.Builder(markUrl);
        ImageProcessClient.WaterMarkImageParam waterMarkImageParam = wmib.build();
        ipc.waterMarkImage(waterMarkImageParam);
        actionString
                .append("|watermark_image:l_0,t_0,p_0.25,u_")
                .append(
                        Base64.getEncoder()
                                .encodeToString(markUrl.getBytes())
                                .replace("=", "")); // Do base64 encode to mark url in SDK
        Assert.assertEquals(ipc.getInput().getAction(), actionString.toString());
        // Get info
        ipc.info();
        actionString.append("|info");
        Assert.assertEquals(ipc.getInput().getAction(), actionString.toString());
    }
}
