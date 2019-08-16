// 어플리케이션 전역적으로 사용하는 함수 모음
// mixins에서 사용
export default {
  methods: {
    // 입력 항목을 기준으로 validation check 함수
    validateState(ref) {
      console.log('ref :', ref);
      console.log('this.veeFields :', this.veeFields);
      if (this.veeFields[ref] && (this.veeFields[ref].dirty || this.veeFields[ref].validated)) {
        return !this.veeErrors.has(ref)
      }
      return null
    }
  }
}
