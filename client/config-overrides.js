/**
 * @author Yuicon
 */
module.exports = function override(config, env) {
    // do stuff with the webpack config...
    config.module.rules[1].oneOf[1].options.babelrc = true;
    console.log(config.module.rules[1].oneOf[1].options)
    return config;
};