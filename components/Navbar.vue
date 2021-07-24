<template>
    <div>
        <v-list-item>
            <v-list-item-content>
                <v-list-item-title class="text-center">
                    <div v-if="avatarSrc">
                        <Avatar :src="avatarSrc"/>
                    </div>
                    <div v-else>
                        <AvatarDefault :username="username" />
                    </div>
                </v-list-item-title>
                <div class="text-h6 text-center">
                    {{ username }}
                </div>
                <v-list-item-subtitle class="text-center">
                    R$ {{ balance ? balance : 0}}
                </v-list-item-subtitle>
            </v-list-item-content>
        </v-list-item>

        <v-divider></v-divider>

        <v-list
            dense
            nav
            shaped
        >
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
            required: true
        }
    },
    data () {
      return {
        username: 'Junkes',
        balance: null,
        avatarSrc: '', //   https://cdn.vuetifyjs.com/images/john.jpg
        right: null,
      }
    },
  }
</script>