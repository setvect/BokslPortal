<template>
  <div>
    <h5>{{item.name}}</h5>
    <b-row>
      <b-col sm="2" class="head">
        <label>코드</label>
      </b-col>
      <b-col sm="10">{{item.boardCode}}</b-col>
    </b-row>
    <b-row>
      <b-col sm="2" class="head">
        <label>이름</label>
      </b-col>
      <b-col sm="10">{{item.name}}</b-col>
    </b-row>
    <b-row>
      <b-col sm="2" class="head">
        <label>업로드 용량제한</label>
      </b-col>
      <b-col sm="10">{{item.uploadLimit}}</b-col>
    </b-row>
    <b-row>
      <b-col sm="2" class="head">
        <label>댓글 사용</label>
      </b-col>
      <b-col sm="10">{{item.commentF}}</b-col>
    </b-row>
    <b-row>
      <b-col sm="2" class="head">
        <label>파일 업로드</label>
      </b-col>
      <b-col sm="10">{{item.attachF}}</b-col>
    </b-row>
    <b-row>
      <b-col sm="2" class="head">
        <label>암호화 글</label>
      </b-col>
      <b-col sm="10">{{item.encodeF}}</b-col>
    </b-row>
    <b-row>
      <b-col>
        <b-button @click="listPage()" type="button" variant="info">목록</b-button>
      </b-col>
      <b-col cols="auto">
        <b-button @click="editPage()" type="button" variant="info">수정</b-button>
        <b-button @click="deleteProc()" type="button" variant="danger">삭제</b-button>
      </b-col>
    </b-row>
  </div>
</template>

<script>
export default {
  data() {
    return {
      item: {
        boardCode: "BDAABB00",
        name: '공지사항',
        uploadLimit: 1000,
        commentF: false,
        attachF: true,
        encodeF: false
      }
    }
  },
  methods: {
    editPage() {
      console.log("수정")
    },
    removeProc() {
      console.log("삭제")
    },
    listPage() {
      this.$router.push({ name: 'boardManagerList' })
    },
  }
}
</script>
<style scoped>
  .head{
    font-weight: bold;
  }
</style>
>

</style>
