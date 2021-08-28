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
                            @click="markAsFavorite(item.id, item.favorite)"
                        >
                            <v-icon
                                dark
                                :color="getFavoriteColor(item.favorite)"
                            >
                                mdi-star
                            </v-icon>
                        </v-btn>
                    </template>
                    <template #[`item.actions`]="{ item }">
                        <v-btn icon color="blue" @click="edit(item.id)">
                            <v-icon dark> mdi-pencil </v-icon>
                        </v-btn>
                        <v-btn icon color="red" @click="remove(item.id)">
                            <v-icon dark> mdi-delete </v-icon>
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
            {
                text: 'Favorito',
                value: 'favorite',
                sortable: false,
                align: 'center',
            },
            { text: 'Data', value: 'date', sortable: true, align: 'center' },
            { text: 'Descrição', value: 'description', sortable: false },
            { text: 'Valor', value: 'value', sortable: true },
            { text: 'Operação', value: 'operation', sortable: false },
            {
                text: 'Ações',
                value: 'actions',
                sortable: false,
                align: 'center',
            },
        ],
        accountData: [],
    }),

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
                    this.$toast.error(err.message);
                })
                .finally(() => {
                    this.loading = false;
                });
        },

        prepare(data) {
            data.forEach((item) => {
                const historic = {};
                historic.id = item.id;
                historic.favorite = item.favorite;
                historic.date = this.$moment(item.created_at).format(
                    'DD/MM/YYYY'
                );
                historic.description = item.description;
                historic.value = this.$currency(item.value);
                historic.operation = this.$operation(item.operation);
                this.accountData.push(historic);
            });
        },
        markAsFavorite(id, isFavorite) {
            this.$axios
                .$patch('/historic/favorite/' + id, {
                    favorite: !isFavorite,
                })
                .then((response) => {
                    const historic = response;
                    const index = this.getIndex(id);
                    this.accountData[index].favorite = historic.favorite;
                })
                .catch((err) => {
                    this.$toast.error(err.message);
                });
        },
        getFavoriteColor(favorite = false) {
            return favorite === true ? 'yellow' : 'grey';
        },
        remove(id) {
            this.$axios
                .$delete('/historic/' + id, {
                    accountId: 1, // TODO
                })
                .then((response) => {
                    // const balance = response;
                    const index = this.getIndex(id);
                    this.accountData.splice(index, 1);
                    this.$toast.success('Removido com sucesso');
                })
                .catch((err) => {
                    this.$toast.error(err.message);
                });
        },
        edit(id) {},
        getIndex(id) {
            return this.accountData.findIndex((historic) => historic.id === id);
        },
    },
};
</script>
