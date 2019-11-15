<template>
  <!-- sidebar menu -->
  <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
    <div class="menu_section">
      <ul class="nav side-menu">
        <li>
          <a>
            <i class="fa fa-home"></i> 홈피
            <span class="fa fa-chevron-down"></span>
          </a>
          <ul class="nav child_menu">
            <li>
              <router-link :to="{ name: 'boardManager' }">게시판관리</router-link>
            </li>
            <li>
              <router-link :to="{ name: 'boardArticle', query: { boardCode: 'BDAAAA01' } }">글</router-link>
            </li>
            <li>
              <router-link :to="{ name: 'boardArticle', query: { boardCode: 'BDAAAA02' } }">책</router-link>
            </li>
            <li>
              <router-link :to="{ name: 'boardArticle', query: { boardCode: 'BDAAAA03' } }">음악</router-link>
            </li>
            <li>
              <router-link :to="{ name: 'boardArticle', query: { boardCode: 'BDAAAA04' } }">영화</router-link>
            </li>
            <li>
              <router-link :to="{ name: 'boardArticle', query: { boardCode: 'BDAAAA05' } }">사진</router-link>
            </li>
            <li>
              <router-link :to="{ name: 'boardArticle', query: { boardCode: 'BDAAAA06' } }">기억</router-link>
            </li>
            <li>
              <router-link :to="{ name: 'boardArticle', query: { boardCode: 'BDAAAA07' } }">인연</router-link>
            </li>
            <li>
              <router-link :to="{ name: 'boardArticle', query: { boardCode: 'BDAAAA08' } }">잡답</router-link>
            </li>
            <li>
              <router-link :to="{ name: 'boardArticle', query: { boardCode: 'BDAAAA09' } }">꿈</router-link>
            </li>
            <li>
              <router-link :to="{ name: 'boardArticle', query: { boardCode: 'BDAAAA10' } }">기술사</router-link>
            </li>
            <li>
              <router-link :to="{ name: 'boardArticle', query: { boardCode: 'BDAAAA11' } }">소설</router-link>
            </li>
            <li>
              <router-link :to="{ name: 'code' }">코드관리</router-link>lm
            </li>
          </ul>
        </li>
        <li>
          <router-link :to="{ name: 'knowledge' }">
            <i class="fa fa-book"></i> 복슬지식
            <span class="fa fa-chevron-down"></span>
          </router-link>
        </li>
        <li>
          <router-link :to="{ name: 'note', query: { categorySeq: 0 } }">
            <i class="fa fa-edit"></i> 복슬노트
            <span class="fa fa-chevron-down"></span>
          </router-link>
        </li>
        <li>
          <router-link :to="{ name: 'memoList' }">
            <i class="fa fa-clipboard"></i> 복슬메모
            <span class="fa fa-chevron-down"></span>
          </router-link>
        </li>
        <li>
          <a>
            <i class="fa fa-camera"></i> 복슬포토
            <span class="fa fa-chevron-down"></span>
          </a>
          <ul class="nav child_menu">
            <li>
              <router-link :to="{ name: 'photoList' }">나의 기록</router-link>
            </li>
            <li>
              <router-link :to="{ name: 'photoAdd' }">올리기</router-link>
            </li>
          </ul>
        </li>
        <li>
          <router-link :to="{ name: 'networkList' }"><i class="fa fa-sitemap"></i> 복슬관계 <span class="fa fa-chevron-down"></span></router-link>
        </li>
        <li>
          <a>
            <i class="fa fa-lightbulb-o"></i> 실험실
            <span class="fa fa-chevron-down"></span>
          </a>
          <ul class="nav child_menu">
            <li>
              <router-link :to="{ name: 'lotto' }">로또</router-link>
            </li>
            <li>
              <router-link :to="{ name: 'select-step1' }">결정장애 해결</router-link>
            </li>
            <li>
              <router-link :to="{ name: 'todo' }">할일</router-link>
            </li>
          </ul>
        </li>
      </ul>
    </div>
  </div>
  <!-- /sidebar menu -->
</template>

<script>
import Vue from "vue";
import store from "../store/index.js";
import pageBody from "../custom.js";
import EventBus from "../utils/event-bus.js";

export default {
  data() {
    return {
      noteCategoryTree: []
    };
  },
  methods: {
    loadNoteCategory() {
      store.dispatch("note/loadTree").then(() => {
        this.noteCategoryTree = store.state.note.categoryTree.children;
        this.$nextTick(() => {
          pageBody.menuClickEvent();
        });
      });
    }
  },
  mounted() {
    this.loadNoteCategory();
    // 노트 카테고리가 변경될 경우 이벤트 발생시켜 변경사항 반영
    EventBus.$on("reloadNoteCategory", () => {
      this.loadNoteCategory();
    });
  }
};
</script>

<style scoped>
.main_menu .fa {
  text-align: left;
}
</style>
