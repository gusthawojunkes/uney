<template>
    <v-container>
        <v-row justify="center" dense>
            <v-col xs="12" sm="10" md="5" lg="5">
                <v-dialog v-model="creditDialog.modal" max-width="60%">
                    <template #activator="{ on, attrs }">
                        <v-card
                            v-ripple
                            persistent
                            elevation="10"
                            class="text-center"
                            dark
                            v-bind="attrs"
                            v-on="on"
                        >
                            <v-card-title>
                                <v-icon color="blue"
                                    >mdi-arrow-up-circle</v-icon
                                >
                                &nbsp; Creditar
                            </v-card-title>
                        </v-card>
                    </template>
                    <Operation :type="'credit'"></Operation>
                </v-dialog>
            </v-col>
            <v-col xs="12" sm="10" md="5" lg="5">
                <v-dialog v-model="debitDialog.modal" max-width="60%">
                    <template #activator="{ on, attrs }">
                        <v-card
                            v-ripple
                            persistent
                            elevation="10"
                            class="text-center"
                            dark
                            v-bind="attrs"
                            v-on="on"
                        >
                            <v-card-title>
                                <v-icon color="red"
                                    >mdi-arrow-down-circle</v-icon
                                >
                                &nbsp; Debitar
                            </v-card-title>
                        </v-card>
                    </template>
                    <Operation :type="'debit'"></Operation>
                </v-dialog>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
    data: () => ({
        creditDialog: { modal: false },
        debitDialog: { modal: false },
    }),
};
</script>
