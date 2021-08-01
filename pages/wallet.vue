<template>
    <v-container fluid pa-0>
        <v-row justify="center" dense>

            <v-dialog
                v-model="creditDialog"
                max-width="600px"
            >
                <Operation :type="credit" />
            </v-dialog>

            <v-col xs="12" sm="10" md="5" lg="5">
                <v-card
                    v-ripple
                    elevation="10"
                    class="text-center"
                >
                    <span>Creditar</span>
                </v-card>        
            </v-col>

            <v-dialog
                v-model="debitDialog"
                max-width="600px"
            >
                <Operation :type="debit"/>
            </v-dialog>
            
            <v-col xs="12" sm="10" md="5" lg="5">
                <v-card
                    v-ripple
                    elevation="10"
                    class="text-center"
                >
                    <span>Debitar</span>
                </v-card>
            </v-col>
        </v-row>    
    </v-container>
</template>

<script>
export default {
    data: () => ({
        Operationbalance: null,
        creditDialog: false,
        debitDialog: false,
    }),
    methods: {

    }
}
</script>