@echo off
chcp 65001 >nul
title 东神脚手架 - Minor 发版 (新功能)
cd /d "%~dp0..\.."
powershell -NoProfile -ExecutionPolicy Bypass -File ".github\scripts\release.ps1" minor
echo.
pause
