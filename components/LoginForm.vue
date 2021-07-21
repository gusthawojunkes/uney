<template>
     <v-container fluid fill-height>
        <form>
            <v-text-field
                v-model="form.username"
                label="Usuário"
            >
            </v-text-field>
            <v-text-field
                v-model="form.password"
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
     </v-container>
    
</template>

<script>
export default {
    data: () => ({
      form: {
        username: '',
        password: '',
      },
      authenticated: false,
      user: {}
    }),

    methods: {
      login() {
        this.$axios.$post('/login', this.form)
        .then((response) => {
          this.user = response.data;
        }).catch((err) => {
          return err.message;
        }).finally(() => {
          setTimeout(() => {
            this.authenticated = true;
          }, 500);
        })

      }
    }
}
</script>
