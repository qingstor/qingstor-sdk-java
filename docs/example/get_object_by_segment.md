## GetDownObjectMulti Example

### Code Snippet

Initialize the Bucket service with access-key-id and secret-access-key

```
EnvContext env = new EnvContext(accessKey,accessSecret);
String zoneKey = "pek3a";
String bucketName = "testBucketName";
Bucket bucket = new Bucket(env, zoneKey, bucketName);

```

then you can get object multi:

```

        OutputStream outa = null;

        //Size of every parts.
        long partSize = 5000000L;

        int index = 0;
        try {
            while (true) {
                Bucket.GetObjectInput putObjectInput = new Bucket.GetObjectInput();

                long start = partSize * index;

                long end = ((index + 1) * partSize - 1);
                putObjectInput.setRange("bytes=" + start + "-" + end);

                Bucket.GetObjectOutput output = bucket.getObject("testMultiMd5MM.zip", putObjectInput);

                int iLength = 0;
                File ff = new File("/Users/chengww/Downloads/testMultiDownload.zip");

                if (output != null && output.getBodyInputStream() != null && output.getStatueCode() == 206) {

                    outa = new FileOutputStream(ff, true);

                    int bytesRead = 0;

                    byte[] buffer = new byte[1024];

                    while ((bytesRead = output.getBodyInputStream().read(buffer, 0, 1024)) != -1) {
                        outa.write(buffer, 0, bytesRead);
                        iLength += bytesRead;
                    }
                    outa.flush();

                    output.getBodyInputStream().close();

                }

                if (iLength < partSize) {
                    System.out.println("ff.length = " + ff.length());
                    break;
                }

                index++;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        } finally {
            if (null != outa) {
                try {
                    outa.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        System.out.println("======================下载完成");

```
