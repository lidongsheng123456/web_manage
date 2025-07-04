module.exports = {
    chainWebpack: (config) => {
        config.plugin('define').tap((definitions) => {
            Object.assign(definitions[0], {
                __VUE_OPTIONS_API__: 'true',
                __VUE_PROD_DEVTOOLS__: 'false',
                __VUE_PROD_HYDRATION_MISMATCH_DETAILS__: 'false'
            })
            return definitions
        })
        config.plugin('html')
            .tap(args => {
                args[0].title = "东神脚手架";
                return args;
            })
    },
    lintOnSave: false,//关闭eslint校验
    devServer: {
        port: 81,
        client: {
            overlay: false
        }
    }
}
