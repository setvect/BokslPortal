const path = require("path")
const VueLoaderPlugin = require("vue-loader/lib/plugin")
const UglifyJsPlugin = require("uglifyjs-webpack-plugin")
const webpack = require("webpack")
const HtmlWebpackPlugin = require("html-webpack-plugin")

module.exports = env => {
  let clientPath = path.resolve(__dirname, "src/main/client")
  let outputPath = path.resolve(__dirname, env == "production" ? "src/main/webapp" : "out")
  return {
    mode: !env ? "development" : env,
    entry: {
      "app/js/main": './src/main/client/main.js'
    },
    devtool: false,
    output: {
      path: outputPath,
      filename: "[name].js",
      pathinfo: true
    },
    optimization: {
      // minimize: true
      splitChunks: {
        chunks: "all",
        minSize: 300000000,
        cacheGroups: {
          vendors: {
            test: /[\\/]node_modules[\\/]/,
            name: "assets/bundle/js/vendors"
          }
        }
      }
    },
    devServer: {
      contentBase: outputPath,
      publicPath: "/",
      host: "0.0.0.0",
      port: 8081,
      proxy: {
        "**": "http://127.0.0.1:8080"
      },
      inline: true,
      hot: false
    },
    module: {
      rules: [
        {
          test: /\.js$/,
          exclude: /[\\/]node_modules[\\/]/,
          use: [
            {
              loader: "babel-loader",
              options: {
                presets: ['@babel/preset-env']
              }
            }
          ]
        },
        {
          test: /\.vue$/,
          loader: "vue-loader"
        },
        {
          test: /\.css$/,
          use: ["style-loader", "css-loader"]
        }
      ].concat([
        env === "production"
          ? {
              test: /\.(jpe?g|png|gif|svg|ttf|woff|woff2|eot)$/i,
              use: [
                {
                  loader: "url-loader",
                  options: {
                    limit: 1024 * 1024
                  }
                  // loader: "file-loader",
                  // options: {
                  //   name: "[name].[ext]",
                  //   outputPath: "images/"
                  // }
                }
              ]
            }
          : {
              test: /\.(jpe?g|png|gif|svg|ttf|woff|woff2|eot)$/i,
              use: [
                {
                  loader: "url-loader",
                  options: {
                    limit: 1024 * 1024
                  }
                }
              ]
            }
      ])
    },
    resolve: {
      alias: {
        vue$: "vue/dist/vue.esm.js"
      },
      extensions: [".js", ".css", ".vue"]
    },
    plugins: [
      new VueLoaderPlugin(),
      new webpack.SourceMapDevToolPlugin({
        filename: "[name].js.map"
      }),
      new webpack.DefinePlugin({ "global.GENTLY": false }),
      new webpack.ProvidePlugin({
        $: "jquery",
        jquery: "jquery",
        "window.jQuery": "jquery",
        jQuery: "jquery"
      }),
      // new HtmlWebpackPlugin({
      //   filename: "index.html",
      //   template: clientPath + "/index.html",
      //   inject: true,
      //   title: "vue-admin-template",
      //   minify: {
      //     removeComments: true,
      //     collapseWhitespace: true,
      //     removeAttributeQuotes: true
      //   }
      // })
    ]
  }
}
