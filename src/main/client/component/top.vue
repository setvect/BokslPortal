<template>
  <div class="top_nav">
    <div class="nav_menu">
      <nav>
        <div class="nav toggle">
          <a id="menu_toggle">
            <i class="fa fa-bars"></i>
          </a>
        </div>
        <ul class="nav navbar-nav navbar-right">
          <li class>
            <a @click="logout()" href="javascript:void(0)" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
              로그아웃
              <span class="glyphicon glyphicon-log-out"></span>
            </a>
          </li>
          <li class="dropdown">
            <a @click="chnagePassword()" href="javascript:;" class="dropdown-toggle info-number _edit-my" data-toggle="dropdown" aria-expanded="false">
              비밀번호 변경
              <i class="glyphicon glyphicon-cog"></i>
            </a>
          </li>
        </ul>
      </nav>
    </div>
    <password-change ref="password" />
  </div>
</template>
<script>
import passwordChangeComponent from "./passwordChange.vue";

export default {
  components: {
    "password-change": passwordChangeComponent
  },
  methods: {
    logout() {
      this.$store.dispatch('LogOut').then(() => {
        location.reload()
      })
    },
    chnagePassword() {
      this.$refs["password"].open();
    },
  }
}
</script>

