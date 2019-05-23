<template>
  <div class="container body">
    <div class="main_container">
      <div class="col-md-3 left_col">
        <div class="left_col scroll-view">
          <div class="navbar nav_title" style="border: 0;">
            <a href="index.html" class="site_title">
              <i class="fa fa-paw"></i>
              <span>복슬포털</span>
            </a>
          </div>

          <div class="clearfix"></div>

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
                      <a href="#">게시판관리</a>
                    </li>
                    <li></li>
                    <li>
                      <a>
                        게시판 유형 #1
                        <span class="fa fa-chevron-down"></span>
                      </a>
                      <ul class="nav child_menu">
                        <li>
                          <a href="#">글</a>
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
                      <a href="#">코드관리</a>
                    </li>
                  </ul>
                </li>
                <li>
                  <a>
                    <i class="fa fa-book"></i> 복슬지식
                    <span class="fa fa-chevron-down"></span>
                  </a>
                </li>
                <li>
                  <a>
                    <i class="fa fa-edit"></i> 복슬노트
                    <span class="fa fa-chevron-down"></span>
                  </a>
                  <ul class="nav child_menu">
                    <li>
                      <a href="#"></a>
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
                      <a href="#">리스트</a>
                    </li>
                    <li>
                      <a href="#">레이어</a>
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
        </div>
      </div>

      <!-- top navigation -->
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
                <a href="/logout.do" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                  로그아웃
                  <span class="glyphicon glyphicon-log-out"></span>
                </a>
              </li>
              <li class="dropdown">
                <a href="javascript:;" class="dropdown-toggle info-number _edit-my" data-toggle="dropdown" aria-expanded="false">
                  비밀번호 변경
                  <i class="glyphicon glyphicon-cog"></i>
                </a>
              </li>
            </ul>
          </nav>
        </div>
      </div>
      <!-- /top navigation -->

      <!-- page content -->
      <div class="right_col">
        <b-button>Button</b-button>
        <b-button variant="danger">Button</b-button>
        <b-button variant="success">Button</b-button>
        <b-button variant="outline-primary">Button</b-button>

        <b-table striped hover :items="items"></b-table>
      </div>
      <!-- /page content -->
    </div>
  </div>
</template>
<script type="text/javascript">
import pageBody from '../app/custom.js'
export default {
  data() {
    return {
      items: [
        { age: 40, first_name: 'Dickerson', last_name: 'Macdonald' },
        { age: 21, first_name: 'Larsen', last_name: 'Shaw' },
        { age: 89, first_name: 'Geneva', last_name: 'Wilson' },
        { age: 38, first_name: 'Jami', last_name: 'Carney' }
      ]
    }

  },
  methods: {
  },
  mounted() {
    console.log('pageBody :', pageBody);
    pageBody.init();
    console.log("#################@!@@@@@@@@@@@@@");
  }
}
</script>

