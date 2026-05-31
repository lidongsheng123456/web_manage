import { defineConfig } from 'vitepress'

export default defineConfig({
  title: '东神脚手架',
  description: '基于 Spring Boot 3 + Vue 3 的全栈管理系统文档',
  lang: 'zh-CN',
  lastUpdated: true,
  themeConfig: {
    logo: '/logo.svg',
    nav: [
      { text: '首页', link: '/' },
      { text: '部署指南', link: '/guide/deploy' },
    ],
    sidebar: [
      {
        text: '快速开始',
        items: [
          { text: '项目简介', link: '/guide/introduction' },
          { text: '部署指南', link: '/guide/deploy' },
        ],
      },
      {
        text: '参考',
        items: [
          { text: '技术栈', link: '/reference/tech-stack' },
          { text: '常见问题', link: '/reference/faq' },
        ],
      },
    ],
    socialLinks: [
      { icon: 'github', link: '#' },
    ],
    footer: {
      message: '基于 MulanPSL-2.0 开源协议',
      copyright: 'Copyright © 2025 东神脚手架',
    },
    search: {
      provider: 'local',
    },
    outline: {
      level: [2, 3],
      label: '目录',
    },
    docFooter: {
      prev: '上一篇',
      next: '下一篇',
    },
    lastUpdatedText: '最后更新',
    returnToTopLabel: '返回顶部',
  },
})
