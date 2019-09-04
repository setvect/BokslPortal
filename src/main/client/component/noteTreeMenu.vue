
<template>
  <ul class="nav child_menu">
    <li v-for="item in list" :key="item.data.categorySeq">
      <router-link :to="{name: 'note', query: { categorySeq: item.data.categorySeq }}">{{item.data.name}}</router-link>
      <template v-if="item.children.length">
        <note-tree-menu :list="item.children" />
      </template>
    </li>
  </ul>
</template>

<script>
export default {
  name: "note-tree-menu",
  props: {
    list: {
      type: Array,
      default: []
    },
  },
  mounted() {
  }
}
</script>