<template>
    <div class="root">
        <h1>Hello World</h1>
        <pre>{{$data}}</pre>
    </div>
</template>


<script lang="ts">
    import { Component, Vue } from 'vue-property-decorator'

    @Component
    export default class Application extends Vue {
        private foo: number = 1

        mounted() {
            this.stuff()
        }

        async stuff() {
            console.log('before async stuff (foo=%s)', this.foo)
            await new Promise(resolve => setTimeout(resolve, 1000))
            this.foo = 999
            console.log('after async stuff (foo=%s)', this.foo)
        }
    }
</script>


<style scoped lang="less">
    .root {
        color: fade(red, 50%);
    }
</style>
