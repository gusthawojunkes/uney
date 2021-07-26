<template>
    <div>

    </div>
</template>

<script>
export default {
    props: {
        type: {
            type: String,
            required: true
        }
    },
    data: () => ({
        historic: {
            value: undefined,
            description: undefined,
            operation: this.type,
            account: undefined
        },
        success: false,
        error: undefined
    }),
    methods: {
        new() {
            this.$axios.$post('/historic', this.historic)
            .then((response) => {
                this.success = response !== undefined;
            }).catch((err) => {
                this.success = false;
                this.error = err;
            })
        }
        
    }
}
</script>