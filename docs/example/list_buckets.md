## List Buckets

### Code Snippet

Initialize the EnvContext with accesskeyid and secretaccesskey

```
EnvContext env = new EnvContext(accessKey,accessSecret);
String zoneKey = "pek3a";

```

Then you can list Buckets

```Java
    private void listBuckets(EnvContext context) {
        QingStor stor = new QingStor(context);
        try {
            QingStor.ListBucketsOutput output = stor.listBuckets(null);
            if (output.getStatueCode() == 200) {
                System.out.println("Count = " + output.getCount());

                List<Types.BucketModel> buckets = output.getBuckets();
                System.out.println("buckets = " + new Gson().toJson(buckets));

            } else {
                handleError(output);
            }
        } catch (QSException e) {
            e.printStackTrace();
        }
    }
```