<template>
    <v-container>
        <v-row>
            <v-col v-for="option in options" :key="option.path">
                <DashboardOption :properties="option"></DashboardOption>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
    data: () => ({
        options: [
            {
                icon: 'mdi-wallet',
                name: 'Carteira',
                path: '/wallet',
                pathAction: 'Movimentar',
            },
            {
                icon: 'mdi-chart-box',
                name: 'Relatórios',
                path: '/reports',
                pathAction: 'Detalhar',
            },
        ],
    }),
};
</script>
