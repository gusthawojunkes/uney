<template>
<v-app>
    <v-main>
      <v-container class="fill-height" fluid>
        <v-row align="center" justify="center" dense>
          <v-col cols="12" sm="8" md="4" lg="4">
            <v-card elevation="0">
              <v-card-title>
                <span class="text-caption text-sm-body-2 text-md-body-1 text-lg-h6">
                  Login
                </span>
                
                </v-card-title>
              <v-card-text>
                <v-form v-model="formIsValid">
                  <v-text-field 
                    v-model="form.username"
                    label="Usuário"
                    prepend-inner-icon="mdi-account" 
                    :rules="usernameRules"
                    type="text"
                    class="rounded-0" 
                    outlined
                  ></v-text-field>
                  <v-text-field 
                    v-model="form.password"
                    label="Senha"
                    prepend-inner-icon="mdi-lock" 
                    :rules="passwordRules"
                    type="password" 
                    class="rounded-0" 
                    outlined
                  ></v-text-field>
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-btn 
                  class="rounded-0"
                  color="#2ed37b" 
                  x-large block
                  :loading="loading"
                  :disabled="loading"
                  @click="loader = 'loading'"
                  @submit.prevent="login"
                >Login</v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
export default {
    data: () => ({
      formIsValid: false,
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
        this.login();
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
            if (this.authenticated) {
              this.$router.push('/');
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
