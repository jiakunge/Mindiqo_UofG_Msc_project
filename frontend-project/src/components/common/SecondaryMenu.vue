<template>
    <div>
    <b-card no-body>
        <b-tabs card>
        <b-tab v-for=" (item,index) in tags" :key="index" :title-link-class="linkClass(index)">
            <template #title>
            <strong>{{item.tagName}}</strong>
            </template>
            <b-card-text>Tab contents {{ index }}</b-card-text>
        </b-tab>
        </b-tabs>
    </b-card>
    </div>
</template>

<script>
export default {

}
</script>

<style>

</style>