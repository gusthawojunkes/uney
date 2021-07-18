<template>
  <div>
    <h1>Bem-vindo(a) à {{ brand }}</h1> 
    {{users}}
  </div>
</template>

<script>
export default {
  data() {
    return {
      brand: 'Uney',
      users: []
    }
  },

  async asyncData({params, $axios}) {
    const users = await $axios.$get('/users');
    console.log(users);
  }
}
</script>
