<template>
  <v-app dark>
    <v-navigation-drawer
      v-model="drawer"
      :mini-variant="miniVariant"
      :clipped="clipped"
      fixed
      app
    >
      <Navbar :nav-items="navItems"/>
    </v-navigation-drawer>
    <v-app-bar
      :clipped-left="clipped"
      color="#2ed37b"
      fixed
      app
    >
    <v-app-bar-nav-icon @click.stop="drawer = !drawer" />
    <div>
      {{ brand }}
    </div>

    <v-spacer></v-spacer>
    <div>
      <v-btn 
        icon
        @click="$auth.logout()"
      >
        <v-icon>mdi-exit-to-app</v-icon>
      </v-btn>
    </div>
    

    </v-app-bar>
    <v-main>
      <v-container>
        <Nuxt />
      </v-container>
    </v-main>
    <div v-if="useFooter">
      <Footer />
    </div>
    
  </v-app>
</template>

<script>
export default {

  // middleware: 'auth',

  data: () => ({
    brand: 'Uney',
    drawer: false,
    useFooter: false,
    navItems: [
      {
        icon: 'mdi-apps',
        title: 'Dashboard',
        to: '/'
      },
      {
        icon: 'mdi-account',
        title: 'Conta',
        to: '/account'
      },
    ],
    clipped: false,
    miniVariant: false
  }),
  
}
</script>
