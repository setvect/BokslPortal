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
          <a>
            <i class="fa fa-edit"></i> 복슬노트
            <span class="fa fa-chevron-down"></span>
          </a>
          <ul class="nav child_menu">
            <li>
              <router-link :to="{name: 'note'}">노트1</router-link>
            </li>
            <li>
              <a href="#">공부</a>
              <ul class="nav child_menu">
                <li>
                  <a href="#">프로그래밍</a>
                </li>
                <li>
                  <a href="#">음악</a>
                </li>
              </ul>
            </li>
            <li>
              <a href="#">복슬이</a>
            </li>
          </ul>
        </li>
        <li>
          <a>
            <i class="fa fa-clipboard"></i> 복슬메모
            <span class="fa fa-chevron-down"></span>
          </a>
          <ul class="nav child_menu">
            <li>
              <router-link :to="{name: 'memoList'}">목록 보기</router-link>
            </li>
            <li>
              <router-link :to="{name: 'memoLayer'}">레이어 보기</router-link>
            </li>
          </ul>
        </li>
        <li>
          <a>
            <i class="fa fa-camera"></i> 복슬포토
            <span class="fa fa-chevron-down"></span>
          </a>
          <ul class="nav child_menu">
            <li>
              <a href="#">나의 기록</a>
            </li>
            <li>
              <a href="#">올리기</a>
            </li>
          </ul>
        </li>
        <li>
          <a>
            <i class="fa fa-sitemap"></i> 복슬관계
            <span class="fa fa-chevron-down"></span>
          </a>
        </li>
        <li>
          <a>
            <i class="fa fa-lightbulb-o"></i>실험실
            <span class="fa fa-chevron-down"></span>
          </a>
          <ul class="nav child_menu">
            <li>
              <a href="#">로또</a>
            </li>
            <li>
              <a href="#">결정장애 해결</a>
            </li>
            <li>
              <a href="#">TODO</a>
            </li>
          </ul>
        </li>
      </ul>
    </div>
  </div>
  <!-- /sidebar menu -->
</template>
<script>
export default {
  data() {
    return {
      tableData: [{
        boardCode: 'MainCode',
        name: 'SubCode1',
        codeValue: '값1',
        orderNo: 10,
      }, {
        boardCode: 'MainCode',
        name: 'SubCode2',
        codeValue: '값2',
        orderNo: 10,
      }, {
        boardCode: 'MainCode',
        name: 'SubCode3',
        codeValue: '값3',
        orderNo: 10,
      }, {
        boardCode: 'MainCode',
        name: 'SubCode4',
        codeValue: '값4',
        orderNo: 10,
      }],
      dialogFormVisible: false,
      form: {
        boardCode: 'ROOT',
        name: '',
        codeValue: '',
        orderNo: 10,
      },
      formLabelWidth: '120px',
      type: 'code',
    }
  },
  methods: {
    edit() {
      console.log("수정")
    },
    remove() {
      console.log("삭제")
    },
    changePage(page) {
      console.log("page", page);
    },
    addPage() {
      this.$router.push({ name: 'board-manager-add' })
    },
    readPage() {
      this.$router.push({ name: 'board-manager-read' })
    }
  }
}
</script>