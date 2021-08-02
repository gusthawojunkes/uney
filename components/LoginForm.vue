<template>
<!-- <v-container fill-heigth fluid>
  <v-row justify="center">
    <v-col cols="4">
      <v-card elevation="12">
        
        <v-form v-model="valid">
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
            <span>Entrar</span>
            </v-btn>
          </v-card-actions>
        </v-form>
      </v-card>
    </v-col>
  </v-row>
</v-container> -->

<v-app>
    <v-main>
      <v-container class="fill-height" fluid>
        <v-row align="center" justify="center" dense>
          <v-col cols="12" sm="8" md="4" lg="4">
            <v-card elevation="0">
              <v-card-text>
                <v-form>
                  <v-text-field label="Enter your email" name="email" prepend-inner-icon="mdi-email" type="email" class="rounded-0" outlined></v-text-field>
                  <v-text-field label="Enter your password" name="password" prepend-inner-icon="mdi-lock" type="password" class="rounded-0" outlined></v-text-field>
                  <v-card-actions>
                    <v-btn class="rounded-0" color="#000000" x-large block dark>Login</v-btn>
                  </v-card-actions>
                  
                </v-form>
              </v-card-text>
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
