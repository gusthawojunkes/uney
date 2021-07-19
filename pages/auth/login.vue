<template>
  <form>
    <v-text-field
        v-model="username"
        label="Usuário"
    >
    </v-text-field>
    <v-text-field
        v-model="password"
        label="Senha"
    >
    </v-text-field>

    <v-btn
      class="mr-4"
      @click="login"
    >
      Entrar
    </v-btn>
  </form>
</template>

<script>
export default {
    layout: 'auth',
    data: () => ({
      username: '',
      password: '',
    }),
    methods: {
        async login() {
           await this.$axios.$post('/login').then((response) => {
               
           }).catch((err) => {
               console.error(err)
           }).finally(() => {

           })
        }
    }
}
</script>

<style>

</style>