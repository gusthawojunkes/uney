import colors from 'vuetify/es5/util/colors'

export default {
  ssr: false,
  head: {
    titleTemplate: '%s',
    title: 'Uney',
    htmlAttrs: {
      lang: 'pt-br'
    },
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      { hid: 'description', name: 'description', content: '' },
      { name: 'format-detection', content: 'telephone=no' }
    ],
    link: [
      { rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' }
    ]
  },
  css: [],
  plugins: [],
  components: true,
  buildModules: [
    '@nuxtjs/eslint-module',
    '@nuxtjs/vuetify',
    '@nuxtjs/moment',
  ],
  modules: [
    '@nuxtjs/axios',
    '@nuxtjs/auth-next',
    '@nuxtjs/toast'
  ],
  axios: {
    baseURL: 'http://localhost:5000'
  },
  auth: {
    strategies: {
      local: {
        endpoints: {
          user: false,
          login: { url: '/login', method: 'post' }
        }
      }
    }
  },
  moment: {
    deafaultLocale: 'pt-br'
  },
  toast: {
    position: 'top-right',
    duration: 1250,
    register: [] // https://www.npmjs.com/package/@nuxtjs/toast
  },
  vuetify: {
    customVariables: ['~/assets/variables.scss'],
    theme: {
      dark: true,
      themes: {
        dark: {
          primary: colors.blue.darken2,
          accent: colors.grey.darken3,
          secondary: colors.amber.darken3,
          info: colors.teal.lighten1,
          warning: colors.amber.base,
          error: colors.deepOrange.accent4,
          success: colors.green.accent3
        }
      }
    }
  },
  build: {
  }
}
