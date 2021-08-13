<template>
<v-row justify="center" dense> 
    <v-col xs="12" sm="10" md="5" lg="5">
        <v-dialog
            v-model="creditDialog"
            scrollable
            max-width="900px"
        >
        <template #activator="{ on, attrs }">
            <v-card
                v-ripple
                elevation="10"
                class="text-center"
                dark
                v-bind="attrs"
                v-on="on"
            >Creditar</v-card>
        </template>
            <div>TODO</div>
        </v-dialog>
    </v-col>
    <v-col xs="12" sm="10" md="5" lg="5">
        <v-dialog
            v-model="debitDialog"
            scrollable
            max-width="900px"
        >
        <template #activator="{ on, attrs }">
            <v-card
                v-ripple
                elevation="10"
                class="text-center"
                dark
                v-bind="attrs"
                v-on="on"
            >Debitar</v-card>
        </template>
            <div>TODO</div>
        </v-dialog>
    </v-col>
    
  </v-row>
</template>

<script>
export default {
    data: () => ({
        creditDialog: false,
        debitDialog: false,
    }),
    methods: {

    }
}
</script>