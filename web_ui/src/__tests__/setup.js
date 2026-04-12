import { vi } from 'vitest'

// Mock import.meta.env
vi.stubGlobal('import.meta', {
    env: {
        VUE_APP_BASEURL: '/api',
        VUE_APP_API_TARGET: 'http://localhost:8088'
    }
})

// Mock Element Plus
vi.mock('element-plus', () => ({
    ElMessage: {
        error: vi.fn(),
        success: vi.fn(),
        warning: vi.fn(),
        info: vi.fn(),
    },
    ElMessageBox: {
        confirm: vi.fn().mockResolvedValue(true),
    },
}))
