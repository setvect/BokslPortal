package com.setvect.bokslportal.export;

import com.setvect.bokslportal.MainTestBase;
import com.setvect.bokslportal.attach.service.AttachFileService;
import com.setvect.bokslportal.attach.vo.AttachFileVo;
import com.setvect.bokslportal.common.GenericPage;
import com.setvect.bokslportal.note.repository.NoteRepository;
import com.setvect.bokslportal.note.service.NoteSearch;
import com.setvect.bokslportal.note.service.NoteService;
import com.setvect.bokslportal.note.vo.NoteCategoryVo;
import com.setvect.bokslportal.note.vo.NoteVo;
import com.setvect.bokslportal.util.TreeNode;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Markdown 형태의 Note 정보를 파일로 저장
 */
public class ExportNoteDataTest extends MainTestBase {

  private static final File BASE_DIR = new File("temp/export");

  @Autowired
  private NoteService noteService;
  @Autowired
  private NoteRepository noteRepository;
  @Autowired
  private AttachFileService attachFileService;

  @Test
  public void getCategoryTree() {
    TreeNode<NoteCategoryVo> root = noteService.getCategoryTree();
    BASE_DIR.mkdir();
    retrieveChildren(root, BASE_DIR);
  }

  private void retrieveChildren(TreeNode<NoteCategoryVo> currentCategory, File baseDir) {
    List<TreeNode<NoteCategoryVo>> children = currentCategory.getChildren();

    NoteSearch param = new NoteSearch();
    HashSet<Integer> categorySeqSet = new HashSet<>(Collections.singletonList(currentCategory.getData().getCategorySeq()));
    param.setCategorySeqSet(categorySeqSet);
    param.setReturnCount(Integer.MAX_VALUE);
    GenericPage<NoteVo> page = noteRepository.getPagingList(param);
    List<NoteVo> list = page.getList();

    System.out.println(baseDir.getAbsoluteFile());
    list.stream().filter(NoteVo::isMarkdownF).forEach(note -> {
      System.out.println("\t" + note.getTitle());

      String fileName = replaceInvalidCharacters(note.getTitle()) + ".md";
      File noteFile = new File(baseDir, fileName);
      try {
        String content = note.getContent();

        content = replaceAttachFile(content, baseDir);

        FileUtils.write(noteFile, content, "utf-8");
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

    for (TreeNode<NoteCategoryVo> child : children) {
      NoteCategoryVo subCategory = child.getData();
      File subDir = new File(baseDir, subCategory.getName());
      System.out.printf("%s 생성\n", subDir.getAbsoluteFile());
      subDir.mkdir();
      retrieveChildren(child, subDir);
    }
  }

  private String replaceAttachFile(String content, File baseDir) throws IOException {
    String regex = "!\\[file]\\(/attach/image\\?attachFileSeq=(\\d+)\\)";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(content);
    StringBuffer sb = new StringBuffer();

    while (matcher.find()) {
      int attachSeq = Integer.parseInt(matcher.group(1));
      System.out.println("Found sequence number: " + attachSeq);
      AttachFileVo attachFile = attachFileService.getAttachFile(attachSeq);

      File attachPath = attachFile.getSavePath();
      if (!attachPath.exists()) {
        throw new RuntimeException(attachPath.getAbsolutePath() + " 파일 없어요.");
      }

      String fileName = "./img/" + attachPath.getAbsoluteFile().getName();
      File dest = new File(baseDir, fileName);
      FileUtils.copyFile(attachPath.getAbsoluteFile(), dest);

      matcher.appendReplacement(sb, "![dest](" + fileName + ")");
    }
    matcher.appendTail(sb);
    return sb.toString();
  }

  public static String replaceInvalidCharacters(String fileName) {
    return fileName.replaceAll("[\\\\/:*?\"<>|]", "_");
  }
}
