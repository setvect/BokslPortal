<template>
  <div>
    <div class="login_wrapper">
      <div class="animate form login_form">
        <section class="login_content">
          <b-form @submit="onSubmit">
            <h1>복슬 포털</h1>
            <div>
              <b-form-input v-model="form.name" placeholder="Username"></b-form-input>
            </div>
            <div>
              <b-form-input v-model="form.password" placeholder="Password"></b-form-input>
            </div>
            <div>
              <b-button variant="outline-primary">Login</b-button>
            </div>
            <div class="clearfix"></div>
          </b-form>
        </section>
      </div>
    </div>
  </div>
</template>
<script type="text/javascript">
export default {
  data() {
    return {
      form: {
        name: null,
        password: null
      }
    }
  },
  methods: {
    onSubmit() {
      console.log("obSubmit..");
    }
  },
  beforeCreate() {
    document.body.className = "login"
  }
}
</script>

