<template>
    <div>
        <v-data-table
            v-if="accountData.length"
            :headers="headers"
            :items="accountData"
            loading="accountData"
            loading-text="Carregando..."
            :items-per-page="10"
            class="elevation-1"
        ></v-data-table>
        <div v-else>
            Sem resultados
        </div>
    </div>
</template>

<script>
export default {
    data: () => ({
        loading: null,
        headers: [
            {
                text: 'Data',
                value: 'date',
            },
            {
                text: 'Descrição',
                value: 'description',
                sortable: false,
            },
            {
                text: 'Valor',
                value: 'value',
            },
            {
                text: 'Operação',
                value: 'operation',
                sortable: false,
            }
        ],
        accountData: []
    }),

    mounted() {
        this.getReports()
    },

    methods: {
        getReports() {
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