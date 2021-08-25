<template>
    <v-card>
        <v-card-title class="text-h5">
            {{ type.transaction === 'credit' ? 'Quanto deseja creditar?' : 'Quanto deseja debitar?' }}
        </v-card-title>
        <v-card-text>
            <v-row>
                <v-col cols="8">
                    <v-text-field
                        v-model="description"
                        label="Descrição"
                        :rules="descRules"
                        outlined
                        counter="40"
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
            <v-btn color="green darken-1" text>Cancelar</v-btn>
            <v-btn color="green darken-1" text @click="createHistoric">Finalizar</v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
export default {
    props: {
        type: { type: Object, default: () => {} },
    },
    data: () => ({
        value: undefined,
        description: undefined,
        valueRules: [
            (value) =>
                (value && !isNaN(parseFloat(value))) || 'Preencha com números!',
            (value) =>
                (value && value >= 0) || 'O valor não pode ser menor que zero!',
        ],
        descRules: [
            (value) => 
                !!value || 'Preencha o campo descrição',
            (value) =>
                (value && value.length < 40) || 'Máximo de 40 caracteres',
        ],
    }),
    methods: {
        isFormValid() {
            if (this.value === undefined || this.description === undefined) {
                this.$toast.error('Preencha os campos');
                return false;
            }
            return true;
        },

        createHistoric() {
            if (!this.isFormValid()) return;
            this.$axios
                .$post('/historic', {
                    value: parseFloat(this.value),
                    description: this.description,
                    operation: this.type.transaction,
                    accountId: 1, // WIP
                })
                .then(() => {
                    this.$toast.success(
                        (this.type.transaction === 'credit' ? 'Creditado' : 'Debitado') +
                            ' com sucesso.'
                    );
                })
                .catch((err) => {
                    this.$toast.error(err);
                }).finally(() => {
                    this.description = undefined;
                    this.value = undefined;
                });
        },
    },
};
</script>
