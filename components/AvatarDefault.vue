<template>
    <v-avatar
        size="100"
        color="primary"
    >
    {{ username }}
    </v-avatar>
</template>

<script>
export default {
    props: {
        username: {
            type: String,
            required: true
        }
    },
}
</script>