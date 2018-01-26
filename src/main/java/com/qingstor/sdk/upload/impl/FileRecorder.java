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
package com.qingstor.sdk.upload.impl;

import com.qingstor.sdk.upload.Recorder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Date;

/**
 * An impl of {@link Recorder}. Records will be kept in the upload as files. <br>
 * 实现分段上传时上传进度的接口方法 <br>
 * Created by chengww on 2018/1/23.
 */
public final class FileRecorder implements Recorder {

    private String directory;

    public FileRecorder(String directory) throws IOException {
        this.directory = directory;
        File f = new File(directory);
        if (!f.exists()) {
            boolean r = f.mkdirs();
            if (!r) {
                throw new IOException("Make dir failed.");
            }
            return;
        }
        if (!f.isDirectory()) {
            throw new IOException("Can not make dir.");
        }
    }

    private static String hash(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] hash = digest.digest(base.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte aHash : hash) {
                hexString.append(Integer.toString((aHash & 0xff) + 0x100, 16).substring(1));
            }
            return hexString.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * A recorder who records progress of multi uploads. <br>
     * 纪录分段上传进度
     *
     * @param key  key recorded in the upload. 持久化的键
     * @param data data recorded in the upload. 持久化的内容
     */
    @Override
    public void set(String key, byte[] data) {
        File f = new File(directory, hash(key));
        FileOutputStream fo = null;
        try {
            fo = new FileOutputStream(f);
            fo.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (fo != null) {
            try {
                fo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get the progress of multi uploads. <br>
     * 获取分段上传进度
     *
     * @param key key recorded in the upload. 持久化的键
     */
    @Override
    public byte[] get(String key) {
        File f = new File(directory, hash(key));
        FileInputStream fi = null;
        byte[] data = null;
        int read = 0;
        try {
            if (outOfDate(f)) {
                f.delete();
                return null;
            }
            data = new byte[(int) f.length()];
            fi = new FileInputStream(f);
            read = fi.read(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (fi != null) {
            try {
                fi.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (read == 0) {
            return null;
        }
        return data;
    }

    private boolean outOfDate(File f) {
        return (f.lastModified() + 1000 * 3600 * 24 * 5) < new Date().getTime();
    }

    /**
     * Delete the progress of multi uploads. <br>
     * 删除已上传文件的进度文件
     *
     * @param key key recorded in the upload. 持久化的键
     */
    @Override
    public void del(String key) {
        File f = new File(directory, hash(key));
        f.delete();
    }
}

