// 어플리케이션 전역적으로 사용하는 함수 모음
// mixins에서 사용
export default {
  methods: {
    // 입력 항목을 기준으로 validation check 함수
    validateState(field, scope) {
      let veeFields = this.veeFields;
      if(scope){
        veeFields = this.veeFields["$" + scope];
      }
      if(!veeFields){
        return null;
      }
      if (veeFields[field] && (veeFields[field].dirty || veeFields[field].validated)) {
        return !this.veeErrors.has(field, scope);
      }
      return null;
    },
  }
};
