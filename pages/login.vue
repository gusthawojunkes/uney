<template>
  <div>
    <LoginForm />
    <v-footer 
      padless 
      absolute 
      dark
    >
      <v-col
        class="text-center"
        cols="12"
      >
        {{ new Date().getFullYear() }} — <span>Uney</span>
      </v-col>
    </v-footer>
  </div>
</template>

<script>
export default {
    layout: 'auth'
}
</script>