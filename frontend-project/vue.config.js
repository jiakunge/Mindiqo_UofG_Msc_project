const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    host: 'localhost', // ip
    port: 8090,  
    proxy: {
      '/api': { // pass to backend server 
        target: 'http://localhost:8080',
        changeOrigin: true
      }
    }
  }
})
