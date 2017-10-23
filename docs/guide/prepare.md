## 准备工作

为了能够使用 qingstor-sdk ，您需要先在我们的控制台申请 [API密钥](https://console.qingcloud.com/access_keys/) ， 包括 `API密钥ID(access_key_id)` 和 `API密钥的私钥(secret_access_key)` 。

`API密钥ID` 将作为参数包含在每一个请求中发送；而 `API密钥的私钥` 负责生成请求串的签名， `API密钥的私钥` 需要被妥善保管，请勿外传。
