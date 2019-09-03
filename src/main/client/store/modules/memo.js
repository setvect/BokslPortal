const memo = {
  namespaced: true,
  state: {
    categoryList: null
  },

  mutations: {
    setCategoryList: (state, categoryList) => {
      state.categoryList = categoryList;
    }
  },

  actions: {
    // 로그인
    loadCategory({ commit }) {
      return new Promise((resolve, reject) => {
        VueUtil.get(
          "/memo/category/list",
          {},
          res => {
            commit("setCategoryList", res.data);
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

export default memo;
