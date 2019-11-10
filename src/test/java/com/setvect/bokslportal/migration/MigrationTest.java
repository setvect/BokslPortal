package com.setvect.bokslportal.migration;

import com.setvect.bokslportal.BokslPortalConstant;
import com.setvect.bokslportal.MainTestBase;
import com.setvect.bokslportal.attach.repository.AttachFileRepository;
import com.setvect.bokslportal.attach.service.AttachFileModule;
import com.setvect.bokslportal.attach.vo.AttachFileVo;
import com.setvect.bokslportal.board.repository.BoardArticleRepository;
import com.setvect.bokslportal.board.repository.BoardManagerRepository;
import com.setvect.bokslportal.board.vo.BoardArticleVo;
import com.setvect.bokslportal.board.vo.BoardManagerVo;
import com.setvect.bokslportal.code.repository.CodeRepository;
import com.setvect.bokslportal.code.vo.CodeVo;
import com.setvect.bokslportal.comment.repository.CommentRepository;
import com.setvect.bokslportal.comment.service.CommentModule;
import com.setvect.bokslportal.comment.vo.CommentVo;
import com.setvect.bokslportal.knowledge.repository.KnowledgeRepository;
import com.setvect.bokslportal.knowledge.vo.KnowledgeVo;
import com.setvect.bokslportal.memo.repository.MemoCategoryRepository;
import com.setvect.bokslportal.memo.repository.MemoRepository;
import com.setvect.bokslportal.memo.vo.MemoCategoryVo;
import com.setvect.bokslportal.memo.vo.MemoVo;
import com.setvect.bokslportal.network.repository.NetworkRepository;
import com.setvect.bokslportal.network.vo.NetworkVo;
import com.setvect.bokslportal.note.repository.NoteCategoryRepository;
import com.setvect.bokslportal.note.repository.NoteRepository;
import com.setvect.bokslportal.note.service.NoteService;
import com.setvect.bokslportal.note.vo.NoteCategoryVo;
import com.setvect.bokslportal.note.vo.NoteVo;
import com.setvect.bokslportal.photo.repository.PhotoRepository;
import com.setvect.bokslportal.photo.vo.PhotoVo;
import com.setvect.bokslportal.user.repository.UserRepository;
import com.setvect.bokslportal.user.vo.UserVo;
import com.setvect.bokslportal.util.TreeNode;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MigrationTest extends MainTestBase {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BoardManagerRepository boardManagerRepository;

  @Autowired
  private BoardArticleRepository boardArticleRepository;

  @Autowired
  private AttachFileRepository attachFileRepository;

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private KnowledgeRepository knowledgeRepository;

  @Autowired
  private NoteCategoryRepository noteCategoryRepository;

  @Autowired
  private NoteRepository noteRepository;

  @Autowired
  private NoteService noteService;

  @Autowired
  private CodeRepository codeRepository;

  @Autowired
  private MemoRepository memoRepository;

  @Autowired
  private MemoCategoryRepository memoCategoryRepository;

  @Autowired
  private NetworkRepository networkRepository;

  @Autowired
  private PhotoRepository photoRepository;

  @Test
  public void migration() throws SQLException, ClassNotFoundException {
    migrationBoard();
    migrationComment();
    migrationCode();
    migrationKnowledge();
    migrationNote();
    migrationMemo();
    migrationNetwork();
    migrationPhoto();

    return;
  }

  private void migrationPhoto() throws SQLException, ClassNotFoundException {
    photoRepository.deleteAll();
    Connection conn = connectionPhoto();
    PreparedStatement ps = conn.prepareStatement("SELECT * FROM TBBA_PHOTO ");
    ResultSet rs = ps.executeQuery();
    int count = 0;
    while (rs.next()) {
      PhotoVo photo = new PhotoVo();
      photo.setShotDate(rs.getTimestamp("SHOT_DATE"));
      photo.setShotDateType(PhotoVo.ShotDateType.valueOf(rs.getString("SHOT_DATE_TYPE")) );
      photo.setDirectory(rs.getString("DIRECTORY"));
      photo.setLatitude(rs.getDouble("LATITUDE"));
      photo.setLongitude(rs.getDouble("LONGITUDE"));
      photo.setMemo(rs.getString("MEMO"));
      photo.setName(rs.getString("NAME"));
      photo.setOrientation(rs.getInt("ORIENTATION"));
      photo.setPhotoId(rs.getString("PHOTO_ID"));
      photo.setRegData(rs.getTimestamp("REG_DATE"));

      photoRepository.saveAndFlush(photo);
      count++;
    }
    rs.close();
    ps.close();
    conn.close();
    System.out.println("포토 마이그레이션 끝 " + count);
  }

  private void migrationNetwork() throws SQLException, ClassNotFoundException {
    networkRepository.deleteAll();
    Connection conn = connection();
    PreparedStatement ps = conn.prepareStatement("SELECT * FROM TBFA_NETWORK ORDER BY NETWORK_SEQ ASC ");
    ResultSet rs = ps.executeQuery();
    int count = 0;
    while (rs.next()) {
      NetworkVo network = new NetworkVo();
      network.setContent(rs.getString("CONTENT"));
      network.setDeleteF(rs.getString("DELETE_F").equals("Y"));
      network.setTitle(rs.getString("TITLE"));
      network.setRegDate(rs.getTimestamp("REG_DATE"));
      network.setEditDate(rs.getTimestamp("EDIT_DATE"));

      networkRepository.saveAndFlush(network);
      count++;
    }
    rs.close();
    ps.close();
    conn.close();
    System.out.println("네트워크 마이그레이션 끝 " + count);
  }

  private void migrationMemo() throws SQLException, ClassNotFoundException {
    memoRepository.deleteAll();
    memoCategoryRepository.deleteAll();

    Connection conn = connection();
    PreparedStatement ps = conn.prepareStatement("SELECT * FROM TBCB_WORKSPACE ORDER BY WORKSPACE_SEQ");
    ResultSet rs = ps.executeQuery();


    int count = 0;
    while (rs.next()) {
      MemoCategoryVo category = new MemoCategoryVo();
      category.setName(rs.getString("TITLE"));
      memoCategoryRepository.save(category);

      PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM TBCA_CTMEMO WHERE WORKSPACE_SEQ = ? ORDER BY CTMEMO_SEQ ASC");
      ps1.setInt(1, rs.getInt("WORKSPACE_SEQ"));
      ResultSet rs1 = ps1.executeQuery();

      while (rs1.next()) {
        count++;
        MemoVo article = new MemoVo();
        article.setCategory(category);
        article.setContent(rs1.getString("CONTENT"));
        article.setDeleteF(rs1.getString("DELETE_F").equals("Y"));
        article.setBgCss(rs1.getString("BG_CSS"));
        article.setFontCss(rs1.getString("FONT_CSS"));
        article.setWidth(rs1.getInt("WIDTH"));
        article.setHeight(rs1.getInt("HEIGHT"));
        article.setPositionX(rs1.getInt("POSITION_X"));
        article.setPositionY(rs1.getInt("POSITION_Y"));
        article.setZIndex(rs1.getInt("Z_INDEX"));
        article.setEditDate(rs1.getTimestamp("UPT_DATE"));
        article.setRegDate(rs1.getTimestamp("REG_DATE"));
        memoRepository.saveAndFlush(article);
      }
      rs1.close();
      ps1.close();

    }
    rs.close();
    ps.close();
    conn.close();

    System.out.println("메모 마이그레이션 끝 " + count);
  }

  private void migrationCode() throws SQLException, ClassNotFoundException {
    codeRepository.deleteAll();
    Connection conn = connection();
    PreparedStatement ps = conn.prepareStatement("SELECT * FROM TBYC_CODE ORDER BY CODE_SEQ ASC ");
    ResultSet rs = ps.executeQuery();
    int count = 0;
    while (rs.next()) {
      CodeVo code = new CodeVo();
      code.setMajorCode(rs.getString("MAJOR_CODE"));
      code.setMinorCode(rs.getString("MINOR_CODE"));
      code.setCodeValue(rs.getString("CODE_VALUE"));
      code.setOrderNo(rs.getInt("ORDER_NO"));
      codeRepository.saveAndFlush(code);
      count++;
    }
    rs.close();
    ps.close();
    conn.close();
    System.out.println("코드 마이그레이션 끝 " + count);

  }

  private void migrationNote() throws SQLException, ClassNotFoundException {
    noteRepository.deleteAll();
    TreeNode<NoteCategoryVo> currentTree = noteService.getCategoryTree();
    if (currentTree != null) {
      List<TreeNode<NoteCategoryVo>> categoryAll = currentTree.exploreTree();
      Collections.reverse(categoryAll);
      categoryAll.forEach(c -> {
        noteCategoryRepository.delete(c.getData());
      });
    }

    List<TreeNode<CategoryTemp>> categoryList = getTreeNodes();
    Connection conn = connection();
    //<이전 DB 카테고리 일련번호, 신규 DB 카테고리 >
    Map<Integer, NoteCategoryVo> oldAndNew = new HashMap<>();
    int count = 0;
    for (TreeNode<CategoryTemp> category : categoryList) {
//    String space = StringUtils.repeat("__", category.getLevel());
//    System.out.println(space + category.getData().getName());
      CategoryTemp categoryNode = category.getData();
      NoteCategoryVo noteCategory = new NoteCategoryVo();
      noteCategory.setName(categoryNode.getName());
      noteCategory.setRegDate(categoryNode.getRegDate());
      noteCategory.setDeleteF(categoryNode.isDeleteF());
      if (categoryNode.getCategorySeq() == categoryNode.getParentId()) {
        noteCategory.setParent(noteCategory);
      } else {
        noteCategory.setParent(oldAndNew.get(categoryNode.getParentId()));
      }
      noteCategoryRepository.saveAndFlush(noteCategory);

      PreparedStatement ps = conn.prepareStatement("SELECT * FROM TBDB_NOTE WHERE CATEGORY_SEQ = ?");
      ps.setInt(1, categoryNode.getCategorySeq());
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        NoteVo note = new NoteVo();
        note.setCategory(noteCategory);
        note.setCategorySeq(noteCategory.getCategorySeq());
        note.setEditDate(rs.getTimestamp("UPT_DATE"));
        note.setContent(rs.getString("CONTENT"));
        note.setDeleteF(rs.getString("DELETE_F").equals("Y"));
        note.setRegDate(rs.getTimestamp("REG_DATE"));
        note.setTitle(rs.getString("TITLE"));

        noteRepository.saveAndFlush(note);

        int articleSeq = rs.getInt("NOTE_SEQ");
        addAttachFile(conn, AttachFileModule.NOTE, note.getNoteSeq(), articleSeq);
        count++;

        if (count % 100 == 0) {
          System.out.println("Note: " + noteCategory.getName() + ", count: " + count);
        }
      }
      rs.close();
      ps.close();
      oldAndNew.put(categoryNode.getCategorySeq(), noteCategory);
    }

    conn.close();
    System.out.println(" 노트 마이그레이션 끝 " + count);
  }

  private List<TreeNode<CategoryTemp>> getTreeNodes() throws ClassNotFoundException, SQLException {
    Connection conn = connection();
    PreparedStatement ps = conn.prepareStatement("SELECT * FROM TBDA_NOTE_CATEGORY");
    ResultSet rs = ps.executeQuery();
    List<CategoryTemp> sourceCategories = new ArrayList<>();
    while (rs.next()) {
      CategoryTemp category = new CategoryTemp();
      category.setName(rs.getString("NAME"));
      category.setRegDate(rs.getTimestamp("REG_DATE"));
      category.setDeleteF(rs.getString("DELETE_F").equals("Y"));
      category.setCategorySeq(rs.getInt("CATEGORY_SEQ"));
      category.setParentId(rs.getInt("PARENT_ID"));
      sourceCategories.add(category);
    }

    CategoryTemp rootCategory = sourceCategories.stream().filter(s -> s.getCategorySeq() == s.getParentId()).findAny().get();
    TreeNode<CategoryTemp> tree = new TreeNode<>(rootCategory);
    makeCategoryTree(tree, sourceCategories);
    List<TreeNode<CategoryTemp>> categoryList = tree.exploreTree();
    rs.close();
    ps.close();
    conn.close();
    return categoryList;
  }

  private void makeCategoryTree(TreeNode<CategoryTemp> tree, List<CategoryTemp> sourceCategories) {
    int id = tree.getData().getCategorySeq();
    sourceCategories.stream().filter(category -> category.getParentId() == id && category.getCategorySeq() != id).forEach(category -> {
      TreeNode<CategoryTemp> node = tree.addChild(category);
      makeCategoryTree(node, sourceCategories);
    });
  }

  private void migrationKnowledge() throws SQLException, ClassNotFoundException {
    knowledgeRepository.deleteAll();
    Connection conn = connection();
    PreparedStatement ps = conn.prepareStatement("SELECT * FROM TBEA_KNOWLEDGE ORDER BY KNOWLEDGE_SEQ ASC ");
    ResultSet rs = ps.executeQuery();
    int count = 0;
    while (rs.next()) {
      KnowledgeVo knowledgeVo = new KnowledgeVo();
      knowledgeVo.setProblem(rs.getString("PROBLEM"));
      knowledgeVo.setSolution(rs.getString("SOLUTION"));
      knowledgeVo.setRegDate(rs.getTimestamp("REG_DATE"));
      knowledgeVo.setClassifyC(rs.getString("CLASSIFY_C"));
      knowledgeVo.setDeleteF(rs.getString("DELETE_F").equals("Y"));

      knowledgeRepository.save(knowledgeVo);
      count++;
    }
    rs.close();
    ps.close();
    conn.close();
    System.out.println("지식 마이그레이션 끝 " + count);

  }

  private void migrationComment() throws SQLException, ClassNotFoundException {
    commentRepository.deleteAll();
    Connection conn = connection();
    PreparedStatement ps = conn.prepareStatement("SELECT * FROM TBGA_COMMENT ORDER BY COMMENT_SEQ ASC");
    ResultSet rs = ps.executeQuery();

    UserVo user = getUserVo();
    int count = 0;
    while (rs.next()) {
      CommentVo comment = new CommentVo();
      comment.setUser(user);
      comment.setContent(rs.getString("CONTENT"));
      comment.setModuleName(CommentModule.MAIN);
      comment.setModuleId("1");
      comment.setRegDate(rs.getTimestamp("REG_DATE"));

      commentRepository.saveAndFlush(comment);
      count++;
    }
    rs.close();
    ps.close();
    conn.close();
    System.out.println("코멘트 마이그레이션 끝 " + count);
  }

  private void migrationBoard() throws ClassNotFoundException, SQLException {
    boardArticleRepository.deleteAll();
    boardManagerRepository.deleteAll();

    Connection conn = connection();
    PreparedStatement ps = conn.prepareStatement("SELECT * FROM TBBA_BOARD_MANAGER");
    ResultSet rs = ps.executeQuery();

    UserVo user = getUserVo();

    while (rs.next()) {
      BoardManagerVo board = new BoardManagerVo();
      board.setName(rs.getString("NAME"));
      board.setBoardCode(rs.getString("BOARD_CODE"));
      board.setAttachF(rs.getBoolean("ATTACH_F"));
      board.setCommentF(rs.getBoolean("COMMENT_F"));
      board.setDeleteF(rs.getBoolean("DELETE_F"));
      board.setEncryptF(rs.getBoolean("ENCODE_F"));
      board.setReplyF(rs.getBoolean("REPLY_F"));
      boardManagerRepository.save(board);


      PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM TBBB_BOARD_ARTICLE WHERE BOARD_CODE = ? ORDER BY ARTICLE_SEQ ASC");
      ps1.setString(1, board.getBoardCode());
      ResultSet rs1 = ps1.executeQuery();
      System.out.println("BoardCode: " + board.getBoardCode());
      int count = 0;
      while (rs1.next()) {
        count++;
        BoardArticleVo article = new BoardArticleVo();
        article.setBoardManager(board);
        article.setContent(rs1.getString("CONTENT"));
        article.setIp(rs1.getString("IP"));
        article.setEncryptF(rs1.getBoolean("ENCODE_F"));
        article.setDeleteF(rs1.getBoolean("DELETE_F"));
        article.setHitCount(rs1.getInt("HIT_COUNT"));
        article.setRegDate(rs1.getTimestamp("REG_DATE"));
        article.setTitle(rs1.getString("TITLE"));
        article.setUser(user);
        boardArticleRepository.save(article);

        int articleSeq = rs1.getInt("ARTICLE_SEQ");
        addAttachFile(conn, AttachFileModule.BOARD, article.getBoardArticleSeq(), articleSeq);

        if (count % 100 == 0) {
          System.out.println("BoardCode: " + board.getBoardCode() + ", count: " + count);
        }
      }
      System.out.println("BoardCode: " + board.getBoardCode() + ", count: " + count + " 끝.");
      rs1.close();
      ps1.close();

    }
    rs.close();
    ps.close();
    conn.close();
    System.out.println(" 게시판 마이그레이션 끝 ");
  }

  private UserVo getUserVo() {
    return userRepository.findById(BokslPortalConstant.Login.ID).get();
  }

  /**
   * 첨부파일 등록
   *
   * @param conn
   * @param type
   * @param newSeq 새로운 일련번호
   * @param orgSeq 이전 일련번호
   */
  private void addAttachFile(Connection conn, AttachFileModule type, int newSeq, int orgSeq) throws SQLException {
    PreparedStatement ps = conn.prepareStatement("SELECT * FROM TBYA_ATTACH_FILE where MODULE_NAME =? AND MODULE_ID = ?");
    ps.setString(1, type.name());
    ps.setString(2, String.valueOf(orgSeq));
    ResultSet rs = ps.executeQuery();

    while (rs.next()) {
      AttachFileVo attach = new AttachFileVo();
      attach.setModuleName(type);
      attach.setModuleId(String.valueOf(newSeq));
      attach.setOriginalName(rs.getString("ORIGINAL_NAME"));
      attach.setSaveName(rs.getString("SAVE_NAME"));
      attach.setSize(rs.getInt("SIZE"));
      attach.setRegDate(rs.getTimestamp("REG_DATE"));
      attachFileRepository.save(attach);
    }

    rs.close();
    ps.close();
  }


  public Connection connection() throws ClassNotFoundException, SQLException {
    Class.forName("org.h2.Driver");
    String url = "jdbc:h2:file:D:/intellij-project/BokslPortal/temp/nowhappy/literatureboy";
    Connection conn = DriverManager.getConnection(url, "sa", "");
    return conn;
  }

  public Connection connectionPhoto() throws ClassNotFoundException, SQLException {
    Class.forName("org.h2.Driver");
    String url = "jdbc:h2:file:D:/intellij-project/BokslPortal/temp/photo/bokslPhoto";
    Connection conn = DriverManager.getConnection(url, "sa", "qhrtmf123");
    return conn;
  }

}
