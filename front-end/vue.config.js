const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  transpileDependencies: ["vuetify"],
  pages: {
    index: {
      // entry for the page
      entry: 'src/main.js',
      title: 'Omni Trade',
    },
  }
});
