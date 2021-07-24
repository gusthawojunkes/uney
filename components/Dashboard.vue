<template>
    <v-container>
        <v-row>
            <v-col
                v-for="item in dashItems"
                :key="item"
                cols="sm"
            >
                <v-card
                    class="pa-2"
                    shaped
                    tile
                >
                {{item.title}}
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
  export default {
    data: () => ({
      dashItems: [
            {
            title: 'Movimentar carteira',
            content: '100'
            },
            {
            title: 'Balanço deste mês',
            content: ''
            },
            {
            title: 'Movimentado no ano',
            content: 'R$ 5000'
            },
      ]
    }),
  }
</script>