@echo off
chcp 65001 >nul
title 东神脚手架 - Patch 发版 (修复)
cd /d "%~dp0..\.."
powershell -NoProfile -ExecutionPolicy Bypass -File ".github\scripts\release.ps1" patch
echo.
pause
