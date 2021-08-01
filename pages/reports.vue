<template>
    <v-container>
        <v-row>
            <v-col cols="3">
                <v-combobox
                    v-model="selectedOperation"
                    :items="operationsToSelect"
                    label="Operação"
                    clearable
                    outlined
                    dense
                ></v-combobox>
            </v-col>
            <!-- <v-spacer></v-spacer> -->
            <v-col >
                <v-btn>
                    <span>Filtrar</span>
                    <v-icon small>mdi-filter</v-icon>
                </v-btn>
            </v-col>
        </v-row>
        <v-row>
            <v-col v-if="accountData.length">
                <v-data-table
                    :headers="headers"
                    :items="accountData"
                    loading="accountData"
                    loading-text="Carregando..."
                    :items-per-page="10"
                    class="elevation-1"
                ></v-data-table>
            </v-col>
            <v-col v-else>
                <NoData />
            </v-col>
        </v-row>
    </v-container>
    
</template>

<script>
export default {
    data: () => ({
        loading: null,
        headers: [
            { text: 'Data',      value: 'date' },
            { text: 'Descrição', value: 'description', sortable: false },
            { text: 'Valor',     value: 'value' },
            { text: 'Operação',  value: 'operation',   sortable: false }
        ],
        accountData: [],
        selectedOperation: [],
        operationsToSelect: [ 'Crédito', 'Débito' ],
    }),

    mounted() {
        this.getReports();
    },

    methods: {
        getReports(_params = null) {
            this.$axios.$get('/historic')
            .then((response) => {
                this.prepare(response);
            }).catch((err) => {
                this.error = err;
            }).finally(() => {
                this.loading = false;
            })
        },
        prepare(data) {
            data.forEach(item => {
                const historicModel = {};
                historicModel.date = this.$moment(item.created_at).format('DD/MM/YYYY');
                historicModel.description = item.description;
                historicModel.value = this.getFormattedCurrency(item.value);
                historicModel.operation = this.getFormattedOperation(item.operation);
                this.accountData.push(historicModel);
            });
        },
        getFormattedOperation(operation) {
            if (operation === 'credit') return 'Crédito';
            else if (operation === 'debit') return 'Débito';
            else return 'Desconhecido';
        },
        getFormattedCurrency(value) {
            return value.toLocaleString('pt-br', { style: 'currency', currency: 'BRL' });
        },
    }
}
</script>