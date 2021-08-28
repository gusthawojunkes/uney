<template>
    <div>
        <v-list-item>
            <v-list-item-content>
                <v-list-item-title class="text-center">
                    <div v-if="avatarSrc">
                        <Avatar :src="avatarSrc" />
                    </div>
                    <div v-else>
                        <AvatarDefault :username="username" />
                    </div>
                </v-list-item-title>
                <div class="text-h6 text-center">
                    {{ username }}
                </div>
                <v-list-item-subtitle class="text-center">
                    {{ balance }}
                </v-list-item-subtitle>
            </v-list-item-content>
        </v-list-item>

        <v-divider></v-divider>

        <v-list dense nav shaped>
            <v-list-item
                v-for="item in navItems"
                :key="item.title"
                link
                :to="item.to"
            >
                <v-list-item-icon>
                    <v-icon>{{ item.icon }}</v-icon>
                </v-list-item-icon>

                <v-list-item-content>
                    <v-list-item-title>
                        {{ item.title }}
                    </v-list-item-title>
                </v-list-item-content>
            </v-list-item>
        </v-list>
    </div>
</template>

<script>
export default {
    props: {
        navItems: {
            type: Array,
            required: true,
        },
    },
    data: () => ({
        username: 'Gusthawo Junkes',
        balance: 0.0,
        avatarSrc: 'https://github.com/gusthawojunkes.png?size=100',
        error: undefined,
    }),
    created() {
        this.getBalance();
    },
    methods: {
        getBalance() {
            const accountId = 1;
            this.$axios
                .$get(`/account/${accountId}/balance`)
                .then((response) => {
                    const balance = this.$currency(
                        response !== undefined ? response : 0
                    );
                    this.balance = balance;
                    sessionStorage.setItem('balance', balance);
                })
                .catch((err) => {
                    this.error = err;
                });
        },
    },
};
</script>
