const note = {
  namespaced: true,
  state: {
    categoryTree: null
  },

  mutations: {
    setTree: (state, categoryTree) => {
      state.categoryTree = categoryTree;
    }
  },

  actions: {
    // 로그인
    loadTree({ commit }) {
      return new Promise((resolve, reject) => {
        VueUtil.get(
          "/note/tree",
          {},
          res => {
            commit("setTree", res.data);
            resolve();
          },
          {
            errorCall: error => {
              reject(error);
            }
          }
        );
      });
    }
  }
};

export default note;
