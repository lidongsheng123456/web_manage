@echo off
chcp 65001 >nul
setlocal enabledelayedexpansion
title 东神脚手架 - Minor 发版 (新功能)
cd /d "%~dp0..\.."

echo.
echo   ╔══════════════════════════════════╗
echo   ║     东神脚手架 - Minor 发版      ║
echo   ╚══════════════════════════════════╝
echo.

:: 读取当前版本
for /f "tokens=2 delims=:, " %%a in ('findstr /C:"\"version\"" web_ui\package.json') do (
    set "oldVer=%%~a"
    goto :gotVer
)
:gotVer
echo   当前版本: %oldVer%

:: 计算 minor +1, patch 归零
for /f "tokens=1,2,3 delims=." %%a in ("%oldVer%") do (
    set /a "minorNum=%%b+1"
    set "newVer=%%a.!minorNum!.0"
)
echo   目标版本: !newVer!
echo.

:: 更新 package.json
powershell -NoProfile -Command "(Get-Content 'web_ui\package.json' -Raw) -replace '\"version\": \"%oldVer%\"', '\"version\": \"!newVer!\"' | Set-Content 'web_ui\package.json' -NoNewline -Encoding UTF8"
echo   [OK] web_ui/package.json

:: 更新所有 pom.xml
for %%f in (pom.xml web_admin\pom.xml web_common\pom.xml web_framework\pom.xml web_system\pom.xml) do (
    powershell -NoProfile -Command "(Get-Content '%%f' -Raw) -replace '<version>%oldVer%</version>', '<version>!newVer!</version>' | Set-Content '%%f' -NoNewline -Encoding UTF8"
    echo   [OK] %%f
)

:: Git 提交
echo.
echo   Git commit + tag v!newVer! ...
git add -A
git commit -m "chore: release v!newVer!"
git tag "v!newVer!"

:: 推送
echo.
echo   推送到 GitHub ...
git push github master
git push github "v!newVer!"

echo.
echo   ╔══════════════════════════════════╗
echo   ║       v!newVer! 发布成功!        ║
echo   ╚══════════════════════════════════╝
echo.
echo   GitHub Actions: https://github.com/lidongsheng123456/web_manage/actions
echo   Releases: https://github.com/lidongsheng123456/web_manage/releases
echo.
pause
