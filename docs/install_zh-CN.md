# 安装

## 使用要求

qingstor-sdk-java 适用于 JDK1.8+。如果你需要在 JDK 1.7 上运行, 则需要使用 `<= v2.3.1` 的版本.

## 下载

在 gradle/maven 中将版本替换为你需要的版本, 推荐使用最新的版本.

Gradle:

```gradle
dependencies {
  implementation 'com.yunify:qingstor.sdk.java:2.5.2'
}
```

Maven:

```xml
<dependency>
  <groupId>com.yunify</groupId>
  <artifactId>qingstor.sdk.java</artifactId>
  <version>2.5.2</version>
</dependency>
```

[最新的 SDK jar](https://maven-badges.herokuapp.com/maven-central/com.yunify/qingstor.sdk.java) 可以从 Maven Central 获取.

如果你需要使用 fatJar(所有依赖打包在一起), 你可以从我们的 [发布页面](https://github.com/qingstor/qingstor-sdk-java/releases/latest) 下载.
