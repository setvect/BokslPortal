<template>
  <div>
    <div class="login_wrapper">
      <div class="animate form login_form">
        <section class="login_content">
          <b-form>
            <h1>복슬 포털</h1>
            <div>
              <b-form-input v-model="form.username" placeholder="Username"></b-form-input>
            </div>
            <div>
              <b-form-input type="password" v-model="form.password" placeholder="Password"></b-form-input>
            </div>
            <div>
              <b-button @click.prevent="loginProc" variant="outline-secondary">Login</b-button>
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
        username: 'boksl',
        password: 'boksl'
      },
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  beforeCreate() {
    document.body.className = "login"
  },
  methods: {
    loginProc() {
      console.log("login...");
      this.$store.dispatch('Login', this.form).then(() => {
        this.$router.push({ path: this.redirect || '/' })
      }).catch((e) => {
        alert("아이디 또는 비밀번호가 틀렸다.")
      }).finally(() => {
        this.loading = false
      })
    }
  },
}
</script>

