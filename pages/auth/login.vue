<template>
  <LoginForm />
</template>

<script>
export default {
    layout: 'auth',

    methods: {
      login() {
        this.$axios.$post('/login')
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

<style>

</style>