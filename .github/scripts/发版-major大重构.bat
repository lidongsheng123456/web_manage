@echo off
chcp 65001 >nul
title 东神脚手架 - Major 发版 (大重构)
cd /d "%~dp0..\.."
powershell -NoProfile -ExecutionPolicy Bypass -File ".github\scripts\release.ps1" major
echo.
pause
