<template>
  <div>
    <b-modal ref="addModal" title="BootstrapVue">
      <p class="my-4">Hello from modal!</p>
    </b-modal>
  </div>
</template>

<script>
export default {
  methods: {
    open() {
      this.$refs['addModal'].show()
    }
  },
}
</script>