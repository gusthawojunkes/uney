<template>
    <v-card>
        <v-card-title class="text-h5">
            {{ type === 'credit' ? 'Quanto deseja creditar?' : 'Quanto deseja debitar?' }}
        </v-card-title>
        <v-card-text>
            <v-text-field
            v-model="historic.value"
            label="Valor"
            placeholder="R$ 0,00"
            outlined
            type="number"
          ></v-text-field>
        </v-card-text>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
            color="green darken-1"
            text
            @click="dialog = false"

            >Cancelar</v-btn>
            <v-btn
            color="green darken-1"
            text
            @click="dialog = false"

            >Finalizar</v-btn>
        </v-card-actions>
      </v-card>
</template>

<script>
export default {
    props: {
        type: {
            type: String,
            default: '',
        }
    },
    data: () => ({
        historic: {
            value: undefined,
            description: undefined,
            operation: undefined,
            account: undefined
        },
        success: false,
        error: undefined
    }),
    methods: {
        new() {
            this.$axios.$post('/historic', this.historic)
            .then((response) => {
                this.success = response !== undefined;
            }).catch((err) => {
                this.success = false;
                this.error = err;
            })
        }
        
    }
}
</script>