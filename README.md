# 备忘录App - Qorder

一个使用Jetpack Compose开发的Android记事本/备忘录应用。

## 功能特性

- ✅ 创建、编辑、删除备忘录
- ✅ 设置定时提醒
- ✅ 通知栏提醒功能
- ✅ Material 3现代化界面
- ✅ 离线数据存储（Room数据库）

## 环境准备

### 1. 安装JDK 17

项目需要JDK 17或更高版本。

下载地址：https://www.oracle.com/java/technologies/downloads/#java17

安装后设置环境变量：
```bash
# Windows
setx JAVA_HOME "C:\Program Files\Java\jdk-17"
```

### 2. 安装Android SDK

#### 方法1：使用Android SDK命令行工具（推荐）

1. 下载Android SDK命令行工具：
   https://developer.android.com/studio#command-tools

2. 解压到 `E:\Android\Sdk`（或你选择的位置）

3. 设置环境变量：
```bash
# Windows
setx ANDROID_HOME "E:\Android\Sdk"
setx PATH "%PATH%;%ANDROID_HOME%\cmdline-tools\latest\bin;%ANDROID_HOME%\platform-tools"
```

4. 安装必要的SDK组件：
```bash
sdkmanager --licenses
sdkmanager "platforms;android-34" "build-tools;34.0.0" "platform-tools"
```

#### 方法2：使用Android Studio

1. 下载并安装Android Studio：https://developer.android.com/studio

2. 打开Android Studio，进入 Settings > Languages & Frameworks > Android SDK

3. 安装以下组件：
   - SDK Platforms: Android 14.0 (API 34)
   - SDK Tools: Android SDK Build-Tools 34
   - SDK Tools: Android SDK Command-line Tools

4. 记住SDK位置，通常在：`C:\Users\你的用户名\AppData\Local\Android\Sdk`

### 3. 配置项目

在项目根目录创建 `local.properties` 文件：

```properties
# Windows示例（根据你的实际路径修改）
sdk.dir=E:\\Android\\Sdk
# 或者
# sdk.dir=C:\\Users\\Administrator.PC-20210322BALU\\AppData\\Local\\Android\\Sdk
```

## 构建APK

### 方式1：使用Gradle Wrapper（首次会自动下载）

```bash
# 在项目根目录执行
cd E:\XTest\Qorder

# 构建debug APK
gradlew.bat assembleDebug

# 或者使用gradlew（Linux/Mac）
./gradlew assembleDebug
```

构建完成后，APK文件位于：
`app/build/outputs/apk/debug/app-debug.apk`

### 方式2：使用Android Studio

1. 用Android Studio打开 `E:\XTest\Qorder` 目录
2. 等待Gradle同步完成
3. 点击菜单 Build > Build Bundle(s) / APK(s) > Build APK(s)
4. 构建完成后点击通知中的 "locate" 查看APK

## 安装到手机

### 方法1：通过数据线（如果有）
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

### 方法2：通过微信/QQ传输（推荐）
1. 将 `app-debug.apk` 文件发送到手机
2. 在手机上打开文件，允许安装未知来源应用
3. 完成安装

## 项目结构

```
Qorder/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/qorder/
│   │   │   ├── data/           # 数据层（Room数据库）
│   │   │   │   ├── Note.kt
│   │   │   │   ├── NoteDao.kt
│   │   │   │   ├── NoteDatabase.kt
│   │   │   │   └── NoteRepository.kt
│   │   │   ├── receiver/       # 广播接收器
│   │   │   │   ├── ReminderReceiver.kt
│   │   │   │   └── BootReceiver.kt
│   │   │   ├── ui/            # UI界面（Compose）
│   │   │   │   ├── theme/
│   │   │   │   ├── NoteListScreen.kt
│   │   │   │   └── NoteEditScreen.kt
│   │   │   ├── util/          # 工具类
│   │   │   │   └── ReminderManager.kt
│   │   │   ├── viewmodel/     # ViewModel
│   │   │   │   └── NoteViewModel.kt
│   │   │   ├── MainActivity.kt
│   │   │   └── QorderApp.kt
│   │   ├── res/               # 资源文件
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

## 技术栈

- **语言**: Kotlin 1.9.20
- **UI框架**: Jetpack Compose + Material 3
- **数据库**: Room 2.6.1
- **架构**: MVVM
- **最低支持版本**: Android 8.0 (API 26)
- **目标版本**: Android 14 (API 34)

## 使用说明

1. **创建备忘录**：点击右下角 + 按钮
2. **编辑备忘录**：点击备忘录卡片进入编辑
3. **设置提醒**：在编辑页面开启"定时提醒"，选择日期时间
4. **删除备忘录**：在列表页点击卡片右上角删除图标

## 注意事项

- Android 13+ 需要授予通知权限才能接收提醒
- 定时提醒使用系统闹钟服务，即使应用关闭也能触发
- 数据存储在本地数据库，不会上传到云端

## 后续功能

- [ ] 图片/文档附件支持
- [ ] 搜索功能
- [ ] 分类标签
- [ ] 数据备份/恢复
- [ ] 深色模式优化

## 问题排查

### Gradle下载慢
配置国内镜像，编辑 `gradle/wrapper/gradle-wrapper.properties`：
```properties
distributionUrl=https\://mirrors.cloud.tencent.com/gradle/gradle-8.5-bin.zip
```

### SDK组件下载慢
使用国内镜像：
```bash
sdkmanager --proxy=http --proxy_host=mirrors.neusoft.edu.cn --proxy_port=80
```

### 构建失败
- 确保JDK版本 >= 17
- 确保已安装Android SDK Platform 34
- 清理缓存：`gradlew.bat clean`

## 许可证

MIT License
