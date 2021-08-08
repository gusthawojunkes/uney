<template>
<v-app>
    <v-main>
      <v-container class="fill-height" fluid>
        <v-row align="center" justify="center" dense>
          <v-col cols="10" xs="6" sm="8" md="6" lg="6" xl="2">
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
                  @click="login"
                >Entrar</v-btn>
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

    methods: {
      async login() {
        try {
          const user = await this.$auth.loginWith('local', {
            data: this.form
          })
          this.$auth.setUser(user);
          console.log(this.$auth.loggedIn)
          this.$router.push('/')
        } catch {
          // Error message
        }
      },
    }
}
</script>
