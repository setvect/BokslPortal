import axios from "axios";

// 어플리케이션 전역적으로 사용하는 함수 모음
// mixins에서 사용
export default {
  methods: {
    // 입력 항목을 기준으로 validation check 함수
    validateState(ref) {
      let tokens = ref.split('.');
      let field;
      let scope = null;

      if (tokens.length === 1) {
        field = tokens[0];
      } else if (tokens.length === 2) {
        scope = tokens[0];
        field = tokens[1];
      }

      let veeFields = this.veeFields;
      if (scope) {
        veeFields = this.veeFields["$" + scope];
      }
      if (!veeFields) {
        return null;
      }
      if (veeFields[field] && (veeFields[field].dirty || veeFields[field].validated)) {
        return !this.veeErrors.has(field, scope);
      }
      return null;
    },
    // 첨부파일 다운로드
    downloadFile(attachFileSeq, fileName) {
      axios({
          method: 'get',
          url: `/attach/download/${attachFileSeq}`,
          responseType: 'arraybuffer'
        })
        .then(response => {
          this.forceFileDownload(response, fileName)
        })
        .catch(() => console.log('error occured'))
    },
    forceFileDownload(response, fileName) {
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', fileName);
      document.body.appendChild(link);
      link.click();
    },
    clearHtml(html) {
      return CommonUtil.clearHtml(html);
    }
  }
};
