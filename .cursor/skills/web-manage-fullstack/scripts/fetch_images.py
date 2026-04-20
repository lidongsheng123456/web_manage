#!/usr/bin/env python3
"""
东神脚手架 - 业务图片抓取脚本
从网络搜索并下载业务相关图片，保存到前端资源目录和后端文件服务目录。
自动检测项目中的 *_ui 目录，适配不同业务前缀 (web_ui, branch_ui, course_ui 等)。

用法:
  # 自动检测 *_ui 目录
  python fetch_images.py -k "猫咪" -n 5 -p D:/project/web_manage
  python fetch_images.py -k "laptop" -n 3 -p D:/project/branch_manage -t sys_product

  # 手动指定前端图片目录
  python fetch_images.py -k "电源" -n 4 -p D:/project/course_manage --img-dir D:/project/course_manage/course_ui/src/assets/img

  # 使用 Pexels API
  python fetch_images.py -k "cat" -n 5 -p D:/project/web_manage --source pexels --api-key YOUR_KEY

输出:
  1. 图片保存到 {project_root}/{xxx}_ui/src/assets/img/{keyword}_{n}.jpg (自动检测)
  2. 图片复制到 {project_root}/files/{keyword}_{n}.jpg (后端文件服务目录)
  3. 打印 SQL UPDATE 语句模板，img_url 格式: http://localhost:{port}/common/files/{filename}
"""

import argparse
import hashlib
import os
import re
import shutil
import sys
import time
import urllib.parse
import urllib.request
import json
from pathlib import Path


# ---------------------------------------------------------------------------
# Image source adapters
# ---------------------------------------------------------------------------

def _headers():
    return {
        "User-Agent": (
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
            "AppleWebKit/537.36 (KHTML, like Gecko) "
            "Chrome/120.0.0.0 Safari/537.36"
        ),
        "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
        "Accept-Language": "zh-CN,zh;q=0.9,en;q=0.8",
    }


def fetch_from_bing(keyword: str, count: int) -> list[str]:
    """Scrape image URLs from Bing image search."""
    urls: list[str] = []
    encoded = urllib.parse.quote(keyword)
    search_url = (
        f"https://www.bing.com/images/search?q={encoded}"
        f"&first=1&count={count * 3}&qft=+filterui:photo-photo"
    )
    req = urllib.request.Request(search_url, headers=_headers())
    try:
        with urllib.request.urlopen(req, timeout=15) as resp:
            html = resp.read().decode("utf-8", errors="ignore")
        # Extract murl (media URL) from Bing's HTML
        pattern = r'murl&quot;:&quot;(https?://[^&]+?\.(jpg|jpeg|png|webp))'
        matches = re.findall(pattern, html, re.IGNORECASE)
        seen = set()
        for url, _ in matches:
            url = url.split("&")[0]
            if url not in seen and len(urls) < count * 2:
                seen.add(url)
                urls.append(url)
    except Exception as e:
        print(f"[WARN] Bing search failed: {e}", file=sys.stderr)
    return urls


def fetch_from_pexels(keyword: str, count: int, api_key: str) -> list[str]:
    """Fetch image URLs from Pexels API (free, requires API key)."""
    urls: list[str] = []
    encoded = urllib.parse.quote(keyword)
    search_url = f"https://api.pexels.com/v1/search?query={encoded}&per_page={count}&orientation=landscape"
    req = urllib.request.Request(search_url, headers={
        **_headers(),
        "Authorization": api_key,
    })
    try:
        with urllib.request.urlopen(req, timeout=15) as resp:
            data = json.loads(resp.read().decode("utf-8"))
        for photo in data.get("photos", []):
            src = photo.get("src", {})
            # Prefer 'large' size for good quality without being too large
            url = src.get("large") or src.get("medium") or src.get("original")
            if url:
                urls.append(url)
    except Exception as e:
        print(f"[WARN] Pexels API failed: {e}", file=sys.stderr)
    return urls


def fetch_from_unsplash(keyword: str, count: int) -> list[str]:
    """Fetch from Unsplash source (no API key needed, random images)."""
    urls: list[str] = []
    for i in range(count):
        # Unsplash source provides random images by keyword
        url = f"https://source.unsplash.com/800x600/?{urllib.parse.quote(keyword)}&sig={i}"
        urls.append(url)
    return urls


# ---------------------------------------------------------------------------
# Download and save
# ---------------------------------------------------------------------------

def sanitize_filename(keyword: str) -> str:
    """Convert keyword to a safe filename component."""
    # Replace spaces/special chars with underscores
    safe = re.sub(r'[^\w\u4e00-\u9fff]', '_', keyword)
    safe = re.sub(r'_+', '_', safe).strip('_')
    return safe[:30] if safe else "image"


def download_image(url: str, dest_path: Path, index: int) -> bool:
    """Download a single image to dest_path. Returns True on success."""
    req = urllib.request.Request(url, headers=_headers())
    try:
        with urllib.request.urlopen(req, timeout=20) as resp:
            content_type = resp.headers.get("Content-Type", "")
            data = resp.read()

            # Validate it's actually an image
            if len(data) < 1000:
                print(f"  [SKIP] #{index} too small ({len(data)} bytes)", file=sys.stderr)
                return False

            # Detect extension from content type or URL
            ext = ".jpg"
            if "png" in content_type or url.lower().endswith(".png"):
                ext = ".png"
            elif "webp" in content_type or url.lower().endswith(".webp"):
                ext = ".webp"

            # If dest_path doesn't have proper extension, fix it
            if dest_path.suffix.lower() not in ('.jpg', '.jpeg', '.png', '.webp'):
                dest_path = dest_path.with_suffix(ext)

            dest_path.parent.mkdir(parents=True, exist_ok=True)
            dest_path.write_bytes(data)
            print(f"  [OK] #{index} -> {dest_path.name} ({len(data) // 1024}KB)")
            return True
    except Exception as e:
        print(f"  [FAIL] #{index} {url[:80]}... : {e}", file=sys.stderr)
        return False


# ---------------------------------------------------------------------------
# Auto-detect UI directory
# ---------------------------------------------------------------------------

def detect_ui_img_dir(project_root: Path) -> Path | None:
    """Auto-detect *_ui/src/assets/img directory under project root."""
    candidates = []
    for d in project_root.iterdir():
        if d.is_dir() and d.name.endswith("_ui"):
            img_dir = d / "src" / "assets" / "img"
            if img_dir.exists():
                candidates.append(img_dir)
            else:
                # *_ui exists but img dir not yet created - still a valid candidate
                candidates.append(img_dir)
    if len(candidates) == 1:
        return candidates[0]
    if len(candidates) > 1:
        print(f"[INFO] 检测到多个 *_ui 目录:", file=sys.stderr)
        for i, c in enumerate(candidates):
            print(f"  [{i + 1}] {c.parent.parent.parent.name}_ui", file=sys.stderr)
        print(f"  使用第一个: {candidates[0]}", file=sys.stderr)
        return candidates[0]
    return None


# ---------------------------------------------------------------------------
# Main logic
# ---------------------------------------------------------------------------

def main():
    parser = argparse.ArgumentParser(
        description="东神脚手架 - 业务图片抓取工具 (自动检测 *_ui 目录)",
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog=__doc__,
    )
    parser.add_argument("--keyword", "-k", required=True, help="搜索关键词，如 '猫咪' 'laptop' '电源'")
    parser.add_argument("--count", "-n", type=int, default=5, help="下载图片数量 (默认 5)")
    parser.add_argument("--project-root", "-p", required=True, help="项目根目录路径 (如 D:/project/web_manage)")
    parser.add_argument("--img-dir", default="", help="直接指定前端图片目录 (跳过自动检测)")
    parser.add_argument("--files-dir", default="", help="直接指定后端文件目录 (默认 {project_root}/files)")
    parser.add_argument("--backend-port", type=int, default=8088, help="后端服务端口 (默认 8088)")
    parser.add_argument("--source", choices=["bing", "pexels", "unsplash"], default="bing",
                        help="图片来源 (默认 bing)")
    parser.add_argument("--api-key", default="", help="Pexels API Key (仅 source=pexels 时需要)")
    parser.add_argument("--table", "-t", default="", help="目标数据库表名 (用于生成 SQL，如 sys_article)")
    parser.add_argument("--id-start", type=int, default=1, help="SQL 中 id 起始值 (默认 1)")
    parser.add_argument("--filename-prefix", default="", help="文件名前缀 (默认使用 keyword)")

    args = parser.parse_args()

    project_root = Path(args.project_root).resolve()

    # Resolve UI image directory: --img-dir > auto-detect > fallback error
    if args.img_dir:
        ui_img_dir = Path(args.img_dir).resolve()
    else:
        detected = detect_ui_img_dir(project_root)
        if detected:
            ui_img_dir = detected
        else:
            print(f"[ERROR] 未在 {project_root} 下检测到 *_ui/src/assets/img 目录", file=sys.stderr)
            print(f"  请使用 --img-dir 手动指定前端图片目录", file=sys.stderr)
            sys.exit(1)

    backend_files_dir = Path(args.files_dir).resolve() if args.files_dir else project_root / "files"
    base_name = args.filename_prefix or sanitize_filename(args.keyword)

    print(f"=== 东神脚手架图片抓取工具 ===")
    print(f"关键词: {args.keyword}")
    print(f"数量: {args.count}")
    print(f"来源: {args.source}")
    print(f"前端图片目录: {ui_img_dir}")
    print(f"后端文件目录: {backend_files_dir}")
    print()

    # 1. Fetch image URLs
    print(f"[1/3] 搜索图片 '{args.keyword}'...")
    if args.source == "pexels":
        if not args.api_key:
            print("[ERROR] Pexels 需要 API Key，请使用 --api-key 参数", file=sys.stderr)
            sys.exit(1)
        image_urls = fetch_from_pexels(args.keyword, args.count, args.api_key)
    elif args.source == "unsplash":
        image_urls = fetch_from_unsplash(args.keyword, args.count)
    else:
        image_urls = fetch_from_bing(args.keyword, args.count)

    if not image_urls:
        print("[ERROR] 未找到任何图片", file=sys.stderr)
        sys.exit(1)

    print(f"  找到 {len(image_urls)} 个候选 URL")
    print()

    # 2. Download images
    print(f"[2/3] 下载图片...")
    ui_img_dir.mkdir(parents=True, exist_ok=True)
    backend_files_dir.mkdir(parents=True, exist_ok=True)

    downloaded: list[str] = []  # list of filenames
    idx = 0
    for url in image_urls:
        if len(downloaded) >= args.count:
            break
        idx += 1
        filename = f"{base_name}_{len(downloaded) + 1}.jpg"
        ui_path = ui_img_dir / filename

        if download_image(url, ui_path, idx):
            # Use the actual saved filename (extension may have changed)
            actual_name = None
            for f in ui_img_dir.iterdir():
                if f.stem == ui_path.stem or f.name == filename:
                    actual_name = f.name
                    break
            if not actual_name:
                actual_name = filename

            # Copy to backend files directory
            src_file = ui_img_dir / actual_name
            dst_file = backend_files_dir / actual_name
            if src_file.exists():
                shutil.copy2(src_file, dst_file)
                downloaded.append(actual_name)

        # Polite delay between requests
        time.sleep(0.5)

    print()
    print(f"  成功下载 {len(downloaded)}/{args.count} 张图片")
    print()

    # 3. Generate SQL and summary
    print(f"[3/3] 生成 SQL 语句...")
    print()
    base_url = f"http://localhost:{args.backend_port}/common/files"

    print("-- ============================================")
    print("-- 图片 URL 列表 (可直接用于 img_url 字段)")
    print("-- ============================================")
    for i, fname in enumerate(downloaded):
        url = f"{base_url}/{fname}"
        print(f"-- [{i + 1}] {url}")
    print()

    if args.table:
        print(f"-- UPDATE 语句 (表: {args.table})")
        for i, fname in enumerate(downloaded):
            url = f"{base_url}/{fname}"
            row_id = args.id_start + i
            print(f"UPDATE `{args.table}` SET `img_url` = '{url}' WHERE `id` = {row_id};")
        print()

        print(f"-- INSERT 模板 (表: {args.table})")
        print(f"-- INSERT INTO `{args.table}` (`name`, `img_url`, ...) VALUES")
        for i, fname in enumerate(downloaded):
            url = f"{base_url}/{fname}"
            comma = "," if i < len(downloaded) - 1 else ";"
            print(f"--   ('名称{i + 1}', '{url}', ...){comma}")
    else:
        print("-- 提示: 使用 --table 参数指定表名可生成 UPDATE/INSERT SQL")
        print("-- 例: --table sys_article")

    print()
    print("-- ============================================")
    print("-- 前端引用方式")
    print("-- ============================================")
    for fname in downloaded:
        print(f"-- import img from '@/assets/img/{fname}'")

    print()
    print("=== 完成 ===")


if __name__ == "__main__":
    main()
