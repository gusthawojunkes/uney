<template>
    <v-container>
        <div v-for="acc in accounts" :key="acc.id">
            <div>{{ acc.name }}</div>
            <div>{{ acc.email }}</div>
            <div>{{ acc.username }}</div>
            <div>{{ $moment(acc.birth).format('DD/MM/YYYY') }}</div>
        </div>
    </v-container>
</template>

<script>
export default {
    data: () => ({
        account: {
            id: undefined,
            name: undefined,
            email: undefined,
            username: undefined,
            birth: undefined,
        },
        accounts: [],
    }),
    created() {
        this.getAccountData();
    },
    methods: {
        getAccountData() {
            this.$axios
                .get('/users')
                .then((response) => {
                    this.accounts = response.data;
                })
                .catch((err) => {
                    this.$toast.error(err);
                });
        },
    },
};
</script>
