#!/usr/bin/env pwsh
<#
.SYNOPSIS
    东神脚手架一键发版脚本

.DESCRIPTION
    自动更新所有版本号（package.json + 5个pom.xml）→ 提交 → 打tag → 推送触发GitHub Release

.PARAMETER Version
    目标版本号，支持以下方式：
      .\release.ps1 patch   → 小版本 +1 (2.0.1 → 2.0.2) 日常修复、微调
      .\release.ps1 minor   → 中版本 +1 (2.0.1 → 2.1.0) 新增功能、优化
      .\release.ps1 major   → 大版本 +1 (2.0.1 → 3.0.0) 重大重构、不兼容变更
      .\release.ps1 2.5.0   → 直接指定版本号

.EXAMPLE
    .\release.ps1 patch
    .\release.ps1 minor
    .\release.ps1 major
    .\release.ps1 2.1.0
#>

param(
    [Parameter(Mandatory=$true, Position=0)]
    [string]$Version
)

$ErrorActionPreference = "Stop"
$root = Split-Path (Split-Path $PSScriptRoot -Parent) -Parent

# 读取当前版本
$pkgFile = Join-Path $root "web_ui/package.json"
$pkgContent = Get-Content $pkgFile -Raw
if ($pkgContent -match '"version"\s*:\s*"([^"]+)"') {
    $oldVersion = $Matches[1]
} else {
    Write-Error "无法读取当前版本号"; exit 1
}

# 解析语义化版本
$parts = $oldVersion.Split('.')
$major = [int]$parts[0]
$minor = [int]$parts[1]
$patch = [int]$parts[2]

# 计算新版本
switch ($Version.ToLower()) {
    "patch" { $patch++; $Version = "$major.$minor.$patch" }
    "minor" { $minor++; $patch = 0; $Version = "$major.$minor.$patch" }
    "major" { $major++; $minor = 0; $patch = 0; $Version = "$major.$minor.$patch" }
}

Write-Host ""
Write-Host "  ╔══════════════════════════════════╗" -ForegroundColor Cyan
Write-Host "  ║     东神脚手架 - 一键发版        ║" -ForegroundColor Cyan
Write-Host "  ╚══════════════════════════════════╝" -ForegroundColor Cyan
Write-Host ""
Write-Host "  当前版本: $oldVersion" -ForegroundColor DarkGray
Write-Host "  目标版本: $Version" -ForegroundColor Green
Write-Host ""

if ($oldVersion -eq $Version) {
    Write-Error "新版本号与当前版本相同，请指定不同版本"; exit 1
}

# 1. 更新 package.json
$pkgContent = $pkgContent -replace "`"version`"\s*:\s*`"$oldVersion`"", "`"version`": `"$Version`""
Set-Content $pkgFile $pkgContent -NoNewline -Encoding UTF8
Write-Host "  [OK] web_ui/package.json" -ForegroundColor Green

# 2. 更新所有 pom.xml
$pomFiles = @("pom.xml", "web_admin/pom.xml", "web_common/pom.xml", "web_framework/pom.xml", "web_system/pom.xml")
foreach ($pom in $pomFiles) {
    $filePath = Join-Path $root $pom
    $content = Get-Content $filePath -Raw
    $content = $content -replace "<version>$oldVersion</version>", "<version>$Version</version>"
    Set-Content $filePath $content -NoNewline -Encoding UTF8
    Write-Host "  [OK] $pom" -ForegroundColor Green
}

# 3. Git 提交 + Tag
Write-Host ""
Write-Host ">>> Git commit & tag v$Version ..." -ForegroundColor Cyan
Set-Location $root
git add -A
git commit -m "chore: release v$Version"
git tag "v$Version"

# 4. 推送
Write-Host ""
Write-Host ">>> 推送到 GitHub ..." -ForegroundColor Cyan
git push github master
git push github "v$Version"

Write-Host ""
Write-Host "  ╔══════════════════════════════════╗" -ForegroundColor Green
Write-Host "  ║  v$Version 发布成功!              ║" -ForegroundColor Green
Write-Host "  ╚══════════════════════════════════╝" -ForegroundColor Green
Write-Host ""
Write-Host "  GitHub Actions: https://github.com/lidongsheng123456/web_manage/actions" -ForegroundColor Yellow
Write-Host "  Releases: https://github.com/lidongsheng123456/web_manage/releases" -ForegroundColor Yellow
Write-Host ""
