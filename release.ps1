#!/usr/bin/env pwsh
# 一键发版脚本 - 更新所有版本号并推送 tag 触发 GitHub Release
# 用法: .\release.ps1 2.0.2

param(
    [Parameter(Mandatory=$true, Position=0)]
    [string]$Version
)

$ErrorActionPreference = "Stop"
$root = $PSScriptRoot

Write-Host ">>> 升级版本到 v$Version" -ForegroundColor Cyan

# 读取当前版本 (从 package.json)
$pkgFile = Join-Path $root "web_ui/package.json"
$pkgContent = Get-Content $pkgFile -Raw
if ($pkgContent -match '"version"\s*:\s*"([^"]+)"') {
    $oldVersion = $Matches[1]
} else {
    Write-Error "无法读取当前版本号"; exit 1
}
Write-Host "  当前版本: $oldVersion -> $Version" -ForegroundColor DarkGray

# 1. 更新前端 package.json
$pkgContent = $pkgContent -replace "`"version`"\s*:\s*`"$oldVersion`"", "`"version`": `"$Version`""
Set-Content $pkgFile $pkgContent -NoNewline -Encoding UTF8
Write-Host "  [OK] web_ui/package.json" -ForegroundColor Green

# 2. 更新所有 pom.xml (只替换 oldVersion -> Version)
$pomFiles = @("pom.xml", "web_admin/pom.xml", "web_common/pom.xml", "web_framework/pom.xml", "web_system/pom.xml")
foreach ($pom in $pomFiles) {
    $filePath = Join-Path $root $pom
    $content = Get-Content $filePath -Raw
    $content = $content -replace "<version>$oldVersion</version>", "<version>$Version</version>"
    Set-Content $filePath $content -NoNewline -Encoding UTF8
    Write-Host "  [OK] $pom" -ForegroundColor Green
}

# 3. Git 提交 + Tag
Write-Host "`n>>> Git commit & tag..." -ForegroundColor Cyan
git add -A
git commit -m "chore: release v$Version"
git tag "v$Version"

# 4. 推送到 GitHub
Write-Host "`n>>> 推送到 GitHub..." -ForegroundColor Cyan
git push github master
git push github "v$Version"

Write-Host "`n>>> 完成! v$Version 已发布" -ForegroundColor Green
Write-Host "    GitHub Actions: https://github.com/lidongsheng123456/web_manage/actions" -ForegroundColor Yellow
