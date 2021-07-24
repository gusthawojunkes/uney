<template>
  <v-row>
    <v-card elevation="12">
      <v-form v-model="valid">
        <v-container fluid>
          <v-card-title>
            Login
          </v-card-title>
          <v-card-text>
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
          </v-card-text>
          <v-card-actions>
            <v-btn
              class="ma-2"
              color="#63FFAC"
              x-large block
              :loading="loading"
              :disabled="loading"
              @click="loader = 'loading'"
              @submit.prevent="login"
            >
              <span>
                Entrar
              </span>
            </v-btn>
          </v-card-actions>
        </v-container>
      </v-form>
    </v-card>
  </v-row>
</template>

<script>
export default {
    data: () => ({
      valid: true,
      loader: null,
      loading: false,
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
    }),

    watch: {
      loader () {
        const l = this.loader;
        this[l] = !this[l];
        setTimeout(() => (
          this[l] = false
        ), 1500);
        this.loader = null;
      },
    },

    methods: {
      login() {
        this.$axios.$post('/login', this.form)
        .then((response) => {
          sessionStorage.setItem('USER_LOGIN', response.data)
          this.authenticated = true;
        }).catch((err) => {
          return err.message;
        }).finally(() => {
          setTimeout(() => {
            this.authenticated = true;
            if (this.authenticated) {
              this.$router.push('/')
            }
          }, 1500);
        })

      },
    }
}
</script>

<style scoped>
  @keyframes loader {
    from {
      transform: rotate(0);
    }
    to {
      transform: rotate(360deg);
    }
  }
</style>
