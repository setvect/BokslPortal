const board = {
  namespaced: true,
  state: {
    boardManager: null
  },

  mutations: {
    setBoardManager: (state, boardManager) => {
      state.boardManager = boardManager;
    }
  },

  actions: {
    loadBoardManager({
      commit
    }, boardCode) {
      return new Promise((resolve, reject) => {
        VueUtil.get(
          `/board-manager/item/${boardCode}`, {},
          res => {
            commit("setBoardManager", res.data);
            resolve();
          }, {
            errorCall: error => {
              reject(error);
            }
          }
        );
      });
    }
  }
};

export default board;
