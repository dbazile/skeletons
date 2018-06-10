const pkg = require('./package.json')
const tsconfig = require('./tsconfig.json')
const { execSync } = require('child_process')


const {
    NODE_ENV = 'development',
    API_PROXY = 'http://localhost:3001',
    BASE_URL = '/',
    COMPILE_TARGET = NODE_ENV === 'production' ? tsconfig.compilerOptions.target : 'es2017',
    GIT_REV = execSync('git rev-parse HEAD 2>/dev/null || echo -').toString().trim(),
} = process.env


module.exports = {
    baseUrl: BASE_URL,
    outputDir: 'dist/public',

    chainWebpack(config) {
        config
            .plugin('html')
            .tap(([options]) => [{...options, minify: false, templateParameters: { BASE_URL, GIT_REV, PACKAGE: pkg }}])

        config
            .module
            .rule('ts')
            .use('ts-loader')
            .loader('ts-loader')
            .tap(options => ({...options, compilerOptions: { target: COMPILE_TARGET }}))
    },

    devServer: {
        clientLogLevel: 'warning',
        historyApiFallback: true,
        proxy: {
            '/api': {
                target: API_PROXY,
                pathRewrite: {'^/api': ''},
                changeOrigin: true,
            },
        },
    },
}
