<template>
    <v-container>
        <v-row>
            <v-col v-if="accountData.length === 0">
                <NoData />
            </v-col>
            <v-col v-else>
                <v-data-table
                    :headers="headers"
                    :items="accountData"
                    loading="accountData"
                    loading-text="Carregando..."
                    :items-per-page="15"
                    class="elevation-1"
                >
                <template #[`item.favorite`]="{ item }">
                    <v-btn 
                        icon 
                        @click="markAsFavorite(item.id)"
                    >
                        <v-icon 
                            dark
                            :color="item.favorite === 1 ? 'yellow' : 'grey'"
                        >
                            mdi-star
                        </v-icon>
                    </v-btn>
                    
                </template>
                </v-data-table>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
    data: () => ({
        loading: null,
        headers: [
            { text: 'Favorito',  value: 'favorite',    sortable: false, align: 'center' },
            { text: 'Data',      value: 'date',        sortable: true,  align: 'center' },
            { text: 'Descrição', value: 'description', sortable: false                  },
            { text: 'Valor',     value: 'value',       sortable: true                   },
            { text: 'Operação',  value: 'operation',   sortable: false                  },
        ],
        accountData: [],
    }),

    watch: {
        selectedOperation(operation) {
            this.filterOperation(operation);
        },
    },

    mounted() {
        this.getReports();
    },

    methods: {
        getReports(_params = null) {
            this.$axios
                .$get('/historic')
                .then((response) => {
                    this.prepare(response);
                })
                .catch((err) => {
                    this.error = err;
                })
                .finally(() => {
                    this.loading = false;
                });
        },

        prepare(data) {
            data.forEach((item) => {
                const historicModel = {};
                historicModel.id = item.id;
                historicModel.favorite = 0;
                historicModel.date = this.$moment(item.created_at).format('DD/MM/YYYY');
                historicModel.description = item.description;
                historicModel.value = this.$currency(item.value);
                historicModel.operation = this.$operation(item.operation);
                this.accountData.push(historicModel);
            });
        },
        markAsFavorite(id) {}
    },
};
</script>
