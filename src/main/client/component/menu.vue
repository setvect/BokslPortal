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
              <router-link :to="{name: 'boardManager'}">게시판관리</router-link>
            </li>
            <li></li>
            <li>
              <a>
                게시판 유형 #1
                <span class="fa fa-chevron-down"></span>
              </a>
              <ul class="nav child_menu">
                <li>
                  <router-link :to="{name: 'board', query: { boardCode: 'BAAABB01' }}">글</router-link>
                </li>
                <li>
                  <a href="#">꿈</a>
                </li>
                <li>
                  <a href="#">책</a>
                </li>
                <li>
                  <a href="#">음악</a>
                </li>
                <li>
                  <a href="#">영화</a>
                </li>
                <li>
                  <a href="#">사진</a>
                </li>
                <li>
                  <a href="#">잡담</a>
                </li>
                <li>
                  <a href="#">기억</a>
                </li>
                <li>
                  <a href="#">인연</a>
                </li>
                <li>
                  <a href="#">소설</a>
                </li>
                <li>
                  <a href="#">기술사</a>
                </li>
              </ul>
            </li>
            <li>
              <a>
                게시판 유형 #1
                <span class="fa fa-chevron-down"></span>
              </a>
              <ul class="nav child_menu">
                <li>
                  <a href="#">메인화면</a>
                </li>
                <li>
                  <a href="#">기타</a>
                </li>
              </ul>
            </li>
            <li>
              <router-link :to="{name: 'code'}">코드관리</router-link>
            </li>
          </ul>
        </li>
        <li>
          <a>
            <i class="fa fa-book"></i> 복슬지식
            <span class="fa fa-chevron-down"></span>
          </a>
          <ul class="nav child_menu">
            <li>
              <router-link :to="{name: 'knowledge'}">복슬지식</router-link>
            </li>
          </ul>
        </li>
        <li>
           <router-link :to="{name: 'note', query: { categorySeq: 0}}">
            <i class="fa fa-edit"></i> 복슬노트
            <span class="fa fa-chevron-down"></span>
          </router-link>
          <note-tree-menu :list="noteCategoryTree" />
        </li>
        <li>
          <a>
            <i class="fa fa-clipboard"></i> 복슬메모
            <span class="fa fa-chevron-down"></span>
          </a>
          <ul class="nav child_menu">
            <li>
              <router-link :to="{name: 'memoList'}">메모 보기</router-link>
            </li>
            <!-- <li>
              <router-link :to="{name: 'memoLayer'}">레이어 보기</router-link>
            </li>-->
          </ul>
        </li>
        <li>
          <a>
            <i class="fa fa-camera"></i> 복슬포토
            <span class="fa fa-chevron-down"></span>
          </a>
          <ul class="nav child_menu">
            <li>
              <router-link :to="{name: 'photoList'}">나의 기록</router-link>
            </li>
            <li>
              <router-link :to="{name: 'photoAdd'}">올리기</router-link>
            </li>
          </ul>
        </li>
        <li>
          <a>
            <i class="fa fa-sitemap"></i> 복슬관계
            <span class="fa fa-chevron-down"></span>
          </a>
          <ul class="nav child_menu">
            <li>
              <router-link :to="{name: 'networkList'}">복슬관계</router-link>
            </li>
          </ul>
        </li>
        <li>
          <a>
            <i class="fa fa-lightbulb-o"></i> 실험실
            <span class="fa fa-chevron-down"></span>
          </a>
          <ul class="nav child_menu">
            <li>
              <router-link :to="{name: 'lotto'}">로또</router-link>
            </li>
            <li>
              <router-link :to="{name: 'select-step1'}">결정장애 해결</router-link>
            </li>
            <li>
              <router-link :to="{name: 'todo'}">할일</router-link>
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
import noteTreeMenu from "./noteTreeMenu";
import pageBody from '../custom.js';
import EventBus from "../utils/event-bus.js";

export default {
  data() {
    return {
      noteCategoryTree: [],
    }
  },
  components: {
    noteTreeMenu
  },
  methods: {
    loadNoteCategory() {
      console.log("loadNoteCategory call.");
      store.dispatch('note/loadTree').then(() => {
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
    EventBus.$on('reloadNoteCategory', () => {
      this.loadNoteCategory();
    });
  }
}
</script>

<style scoped>
.main_menu .fa{
  text-align: left;
}
</style>