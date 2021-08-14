<template>
    <v-card>
        <v-card-title class="text-h5">
            {{ type === 'credit' ? 'Quanto deseja creditar?' : 'Quanto deseja debitar?' }}
        </v-card-title>
        <v-card-text>
            <v-row>
                <v-col cols="8">
                    <v-text-field
                        v-model="description"
                        label="Descrição"
                        :rules="descRules"
                        outlined
                    ></v-text-field>
                </v-col>
                <v-col cols="4">
                    <v-text-field
                        v-model="value"
                        label="Valor"
                        :rules="valueRules"
                        placeholder="R$ 0,00"
                        outlined
                    ></v-text-field>
                </v-col>
            </v-row>
        </v-card-text>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                color="green darken-1"
                text
            >Cancelar</v-btn>
            <v-btn
                color="green darken-1"
                text
                @click="createHistoric"
            >Finalizar</v-btn>
        </v-card-actions>
      </v-card>
</template>

<script>
export default {
    props: {
        type: { type: String, default: '' }
    },
    data: () => ({
        value: undefined,
        description: undefined,
        success: false,
        error: undefined,
        valueRules: [
            value => (value && !isNaN(parseFloat(value))) || 'Preencha com números!',
            value => (value && value >= 0) || 'O valor não pode ser menor que zero!'
        ],
        descRules: [
           value => (!!value) || 'Preencha o campo descrição'
        ],
    }),
    methods: {
        createHistoric() {
            this.$axios.$post('/historic', {
                value: this.value,
                description: this.description,
                operation: this.type,
                accountId: 1 // WIP
            }).then((response) => {
                this.success = response !== undefined;
            }).catch((err) => {
                this.success = false;
                this.error = err;
            })
        },
    }
}
</script>