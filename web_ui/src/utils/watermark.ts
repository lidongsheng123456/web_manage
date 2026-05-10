/**
 * 页面水印工具 — Canvas 生成 + MutationObserver 防删除
 */
let watermarkContainer: HTMLDivElement | null = null
let observer: MutationObserver | null = null

function createWatermarkBase64(text: string): string {
    const canvas = document.createElement('canvas')
    canvas.width = 300
    canvas.height = 200
    const ctx = canvas.getContext('2d')!
    ctx.clearRect(0, 0, canvas.width, canvas.height)
    ctx.font = '15px Arial'
    ctx.fillStyle = 'rgba(0, 0, 0, 0.08)'
    ctx.rotate((-20 * Math.PI) / 180)
    ctx.fillText(text, 0, canvas.height / 2)
    return canvas.toDataURL('image/png')
}

function createWatermarkDom(text: string): HTMLDivElement {
    const div = document.createElement('div')
    div.id = '__watermark__'
    div.style.cssText = `
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    z-index: 99999;
    pointer-events: none;
    background-repeat: repeat;
    background-image: url(${createWatermarkBase64(text)});
  `
    return div
}

export function setWatermark(text: string): void {
    removeWatermark()
    watermarkContainer = createWatermarkDom(text)
    document.body.appendChild(watermarkContainer)

    // MutationObserver 防删除 / 防篡改
    observer = new MutationObserver((mutations) => {
        for (const mutation of mutations) {
            // 水印被移除
            for (const node of mutation.removedNodes) {
                if (node === watermarkContainer) {
                    document.body.appendChild(watermarkContainer!)
                    return
                }
            }
            // 水印属性被篡改
            if (mutation.type === 'attributes' && mutation.target === watermarkContainer) {
                removeWatermark()
                watermarkContainer = createWatermarkDom(text)
                document.body.appendChild(watermarkContainer)
                observeWatermark(text)
                return
            }
        }
    })
    observeWatermark(text)
}

function observeWatermark(text: string): void {
    if (!observer || !watermarkContainer) return
    observer.observe(document.body, { childList: true })
    observer.observe(watermarkContainer, { attributes: true })
}

export function removeWatermark(): void {
    if (observer) {
        observer.disconnect()
        observer = null
    }
    if (watermarkContainer && watermarkContainer.parentNode) {
        watermarkContainer.parentNode.removeChild(watermarkContainer)
    }
    watermarkContainer = null
}
