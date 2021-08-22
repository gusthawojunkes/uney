<template>
    <v-card class="pa-2" elevation="10" tile>
        <v-card-title>
            <v-icon>{{ properties.icon }}</v-icon>
            &nbsp; {{ properties.name }}
        </v-card-title>
        <v-card-actions class="d-flex justify-end">
            <v-btn :to="properties.path">
                {{ properties.pathAction }}
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
export default {
    props: {
        properties: { type: Object, default: () => {} },
    },
};
</script>
