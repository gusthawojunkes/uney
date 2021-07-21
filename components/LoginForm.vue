<template>
  <v-card elevation="10">
    <v-form v-model="valid">
      <v-container fluid>
        <v-text-field
          v-model="form.username"
          prepend-inner-icon="mdi-account"
          :rules="usernameRules"
          type="text"
          label="Usuário"
        ></v-text-field>
        <v-text-field
          v-model="form.password"
          prepend-inner-icon="mdi-lock"
          :rules="passwordRules"
          type="password"
          label="Senha"
        ></v-text-field>
        <v-card-actions>
          <v-btn
            color="#63FFAC"
            x-large block
            @click="login"
          >
            Entrar
          </v-btn>
        </v-card-actions>
       
      </v-container>
    </v-form>
  </v-card>
  
</template>

<script>
export default {
    data: () => ({
      loading: false,
      valid: true,
      usernameRules: [
        f => !!f || 'Preencha o campo usuário'
      ],
      passwordRules: [
         f => !!f || 'Preencha o campo senha'
      ],
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
          this.authenticated = true;
        }).catch((err) => {
          return err.message;
        }).finally(() => {
          setTimeout(() => {
            if(this.authenticated) {
              // Do some stuff
            }
          }, 500);
        })

      },
    }
}
</script>
