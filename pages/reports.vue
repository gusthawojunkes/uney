<template>
    <v-data-table
        :headers="headers"
        :items="accountData"
        loading="accountData"
        loading-text="Carregando..."
        :items-per-page="10"
        class="elevation-1"
    ></v-data-table>
</template>

<script>
export default {
    data: () => ({
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