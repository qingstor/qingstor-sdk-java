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

import org.junit.Assert;
import org.junit.Test;

public class ImageProcessClientTest {

    @Test
    public void testBuildOptParamStr() {
        ImageProcessClient ipc = new ImageProcessClient(null, null);

        ImageProcessClient.RotateParam rotateParam = new ImageProcessClient.RotateParam(90);
        ipc.rotate(rotateParam);
        Assert.assertEquals(ipc.getInput().getAction(), "rotate:a_90");

        ImageProcessClient.CropParam.Builder cropBuilder =
                new ImageProcessClient.CropParam.Builder();
        ImageProcessClient.CropParam cropParam = cropBuilder.width(300).height(400).build();
        ipc.crop(cropParam);
        Assert.assertEquals(ipc.getInput().getAction(), "rotate:a_90|crop:w_300,h_400,g_0");

        ImageProcessClient.ResizeParam.Builder resizeBuilder =
                new ImageProcessClient.ResizeParam.Builder();
        ImageProcessClient.ResizeParam resizeParam =
                resizeBuilder.width(500).height(500).mode(1).build();
        ipc.resize(resizeParam);
        Assert.assertEquals(
                ipc.getInput().getAction(),
                "rotate:a_90|crop:w_300,h_400,g_0|resize:w_500,h_500,m_1");

        ImageProcessClient.FormatParam formatParam = new ImageProcessClient.FormatParam("png");
        ipc.format(formatParam);
        Assert.assertEquals(
                ipc.getInput().getAction(),
                "rotate:a_90|crop:w_300,h_400,g_0|resize:w_500,h_500,m_1|format:t_png");

        ImageProcessClient.WaterMarkParam.Builder wrb =
                new ImageProcessClient.WaterMarkParam.Builder("5rC05Y2w5paH5a2X");
        ImageProcessClient.WaterMarkParam waterMarkParam = wrb.build();
        ipc.waterMark(waterMarkParam);
        Assert.assertEquals(
                ipc.getInput().getAction(),
                "rotate:a_90|crop:w_300,h_400,g_0|resize:w_500,h_500,m_1|format:t_png"
                        + "|watermark:d_150,p_0.25,t_5rC05Y2w5paH5a2X,c_");

        String encodedText = "aHR0cHM6Ly9wZWszYS5xaW5nc3Rvci5jb20vaW1nLWRvYy1lZy9xaW5jbG91ZC5wbmc";
        ImageProcessClient.WaterMarkImageParam.Builder wmib =
                new ImageProcessClient.WaterMarkImageParam.Builder(encodedText);
        ImageProcessClient.WaterMarkImageParam waterMarkImageParam = wmib.build();
        ipc.waterMarkImage(waterMarkImageParam);
        Assert.assertEquals(
                ipc.getInput().getAction(),
                "rotate:a_90|crop:w_300,h_400,g_0|resize:w_500,h_500,m_1|format:t_png|watermark"
                        + ":d_150,p_0.25,t_5rC05Y2w5paH5a2X,c_|watermark_image:l_0,t_0,p_0.25,u_aHR0cHM"
                        + "6Ly9wZWszYS5xaW5nc3Rvci5jb20vaW1nLWRvYy1lZy9xaW5jbG91ZC5wbmc");

        ipc.info();
        Assert.assertEquals(
                ipc.getInput().getAction(),
                "rotate:a_90|crop:w_300,h_400,g_0|resize:w_500,h_500,m_1|format:t_png|watermark"
                        + ":d_150,p_0.25,t_5rC05Y2w5paH5a2X,c_|watermark_image:l_0,t_0,p_0.25,u_aHR0cHM"
                        + "6Ly9wZWszYS5xaW5nc3Rvci5jb20vaW1nLWRvYy1lZy9xaW5jbG91ZC5wbmc|info");
    }
}
