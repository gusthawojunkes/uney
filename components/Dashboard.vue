<template>
    <v-container>
        <v-row>
            <v-col
                v-for="option in options"
                :key="option.path"
                cols="12"
                xl="3"
                lg="4"
                md="4"
                sm="12"
            >
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
                icon: 'mdi-file-table-box-multiple',
                name: 'Histórico',
                path: '/historic',
                pathAction: 'Detalhar',
            },
            {
                icon: 'mdi-chart-box',
                name: 'Relatórios',
                path: '/reports',
                pathAction: 'Gerar',
            },
        ],
    }),
};
</script>
