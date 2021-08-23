<template>
    <v-btn color="#2ed37b" @click="goBack">
        <v-icon>mdi-chevron-left</v-icon>
        Voltar
    </v-btn>
</template>

<script>
export default {
    methods: {
        goBack() {
            window.history.length > 2
                ? this.$router.go(-1)
                : this.$router.push('/');
        },
    },
};
</script>
