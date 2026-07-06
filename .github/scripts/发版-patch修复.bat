@echo off
chcp 65001 >nul
setlocal
title Patch Release
cd /d "%~dp0..\.."
echo.
echo   === Patch Release ===
echo.
node .github\scripts\bump-version.js patch
if errorlevel 1 (echo   ERROR! & pause & exit /b 1)
for /f "tokens=2 delims=:, " %%a in ('findstr /C:"\"version\"" web_ui\package.json') do set "ver=%%~a"
echo.
echo   Git commit + tag v%ver% ...
git add -A
git commit -m "chore: release v%ver%"
git tag "v%ver%"
echo   Push to GitHub ...
git push github master
git push github "v%ver%"
echo.
echo   === v%ver% Released! ===
echo   https://github.com/lidongsheng123456/web_manage/actions
echo.
pause
