<template>
    <v-avatar
        size="100"
    >
    <img :src="src">
    </v-avatar>
</template>

<script>
export default {
    props: {
        src: {
            type: String,
            required: true
        }
    },
}
</script>