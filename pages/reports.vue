<template>
    <v-container>
        <v-row>
            <v-col
                v-for="option in options"
                :key="option.path"
                cols="12"
                xl="4"
                lg="4"
                md="4"
                sm="12"
            >
                <ChartOption :properties="option"></ChartOption>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
    data: () => ({
        options: [
            {
                icon: 'mdi-calendar-month',
                name: 'Por mês',
                path: '/reports/by-month',
                pathAction: 'Gerar',
            },
            {
                icon: 'mdi-calendar-month',
                name: 'Por ano',
                path: '/reports/by-year',
                pathAction: 'Gerar',
            },
        ],
    }),
};
</script>
