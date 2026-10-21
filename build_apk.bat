@echo off
set JAVA_HOME=E:\XTest\dev\jdk-17.0.9+9
set ANDROID_HOME=E:\XTest\dev\android-sdk
set PATH=%JAVA_HOME%\bin;%PATH%

cd /d E:\XTest\Qorder

echo === 配置环境变量 ===
echo JAVA_HOME=%JAVA_HOME%
echo ANDROID_HOME=%ANDROID_HOME%
echo.

echo === 检查Java版本 ===
java -version
echo.

echo === 构建Debug APK ===
call E:\XTest\dev\gradle-8.5\bin\gradle wrapper --gradle-version 8.5
if %ERRORLEVEL% neq 0 (
    echo Wrapper生成失败！
    exit /b %ERRORLEVEL%
)

call gradlew.bat assembleDebug
if %ERRORLEVEL% neq 0 (
    echo 构建失败！
    exit /b %ERRORLEVEL%
)

echo.
echo === 构建成功！ ===
echo APK位置：
dir /s /b app\build\outputs\apk\debug\*.apk
echo.
echo 按任意键退出...
pause >nul
