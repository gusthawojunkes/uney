<template>
    <v-avatar size="100" color="primary">
        {{ getNickname() }}
    </v-avatar>
</template>

<script>
export default {
    props: {
        username: {
            type: String,
            required: true,
        },
    },
    methods: {
        getNickname() {
            const nickname = this.username;

            const names = nickname.split(' ');
            if (names.length === 1)
                return names[0].substring(0, 2).toUpperCase();
            return (names[0].charAt(0) + names[1].charAt(0)).toUpperCase();
        },
    },
};
</script>
