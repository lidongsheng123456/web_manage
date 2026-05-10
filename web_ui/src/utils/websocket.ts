import { ElNotification } from 'element-plus'
import SockJS from 'sockjs-client'
import Stomp from 'stompjs'

let stompClient: Stomp.Client | null = null
let retryCount = 0
const MAX_RETRIES = 10

export function connectWebSocket() {
    const socket = new SockJS('/api/ws')
    stompClient = Stomp.over(socket)
    // 关闭 debug 日志
    stompClient.debug = () => { }

    stompClient.connect({}, () => {
        retryCount = 0
        // 订阅全局通知
        stompClient?.subscribe('/topic/notification', (message) => {
            try {
                const data = JSON.parse(message.body)
                ElNotification({
                    title: data.title || '系统通知',
                    message: data.content,
                    type: 'info',
                    duration: 5000,
                })
            } catch (e) {
                console.error('WebSocket 消息解析失败', e)
            }
        })
    }, (error) => {
        retryCount++
        if (retryCount > MAX_RETRIES) {
            console.error('WebSocket 重连已达上限，停止重连')
            return
        }
        const delay = Math.min(5000 * Math.pow(2, retryCount - 1), 60000)
        console.error(`WebSocket 连接失败，${delay / 1000}秒后重试 (${retryCount}/${MAX_RETRIES})`, error)
        setTimeout(connectWebSocket, delay)
    })
}

export function disconnectWebSocket() {
    if (stompClient && stompClient.connected) {
        stompClient.disconnect(() => { })
    }
    stompClient = null
}
