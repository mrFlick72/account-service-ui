var path = require('path');

const BUID_DIR = path.resolve(__dirname + "../../../../target/classes/static");

module.exports = {
    entry: {
        account: path.resolve(__dirname, './app/index.js'),
    },
    resolve: {
        extensions: [".js", ".jsx"]
    },
    module: {
        rules: [
            {
                test: /\.css$/,
                use: ['style-loader', 'css-loader']
            },
            {
                test: path.join(__dirname, "."),
                exclude: path.resolve(__dirname, "node_modules"),
                use: {
                    loader: "babel-loader",
                    options: {
                        presets: ['@babel/env', '@babel/react']
                    }
                }

            }
        ]
    },
    output: {
        filename: 'app/bundle.js',
        path: BUID_DIR
    }
};