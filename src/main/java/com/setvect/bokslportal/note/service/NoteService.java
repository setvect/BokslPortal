package com.setvect.bokslportal.note.service;

import com.setvect.bokslportal.note.repository.NoteCategoryRepository;
import com.setvect.bokslportal.note.vo.NoteCategoryVo;
import com.setvect.bokslportal.util.TreeNode;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@Log4j2
public class NoteService {
  @Autowired
  private NoteCategoryRepository noteCategoryRepository;

  /**
   * 기본 분류 정보를 넣는다.
   */
  public void init() {
    List<NoteCategoryVo> categoryList = noteCategoryRepository.findAll();
    if (categoryList.size() != 0) {
      return;
    }

    List<NoteCategoryVo> allCategory = new ArrayList<>();

    NoteCategoryVo root = new NoteCategoryVo();
    root.setRegDate(new Date());
    root.setName("ROOT");
    root.setParent(root);
    allCategory.add(root);

    NoteCategoryVo category1 = new NoteCategoryVo();
    category1.setRegDate(root.getRegDate());
    category1.setName("카테고리1");
    category1.setParent(root);
    allCategory.add(category1);

    NoteCategoryVo category2 = new NoteCategoryVo();
    category2.setRegDate(root.getRegDate());
    category2.setName("카테고리2");
    category2.setParent(root);
    allCategory.add(category2);

    NoteCategoryVo category2_1 = new NoteCategoryVo();
    category2_1.setRegDate(root.getRegDate());
    category2_1.setName("카테고리2-1");
    category2_1.setParent(category2);
    allCategory.add(category2_1);

    NoteCategoryVo category2_2 = new NoteCategoryVo();
    category2_2.setRegDate(root.getRegDate());
    category2_2.setName("카테고리2-2");
    category2_2.setParent(category2);
    allCategory.add(category2_2);

    NoteCategoryVo category3 = new NoteCategoryVo();
    category3.setRegDate(root.getRegDate());
    category3.setName("카테고리3");
    category3.setParent(root);
    allCategory.add(category3);

    noteCategoryRepository.saveAll(allCategory);
  }


  /**
   * @return 분류 폴더 구조
   */
  public TreeNode<NoteCategoryVo> getCategoryTree() {
    List<NoteCategoryVo> folderAll = noteCategoryRepository.listCategory();

    // 일련번호와 부모 아이디가 같은 경우는 root 폴더.
    Optional<NoteCategoryVo> data = folderAll.stream().filter(NoteCategoryVo::isRoot).findAny();
    if (!data.isPresent()) {
      return null;
    }
    NoteCategoryVo rootData = data.get();

    Map<NoteCategoryVo, List<NoteCategoryVo>> folderListByParentId = folderAll.stream()
      .filter(category -> !category.isRoot())
      .collect(Collectors.groupingBy(NoteCategoryVo::getParent, Collectors.toList()));

    TreeNode<NoteCategoryVo> rootNode = new TreeNode<>(rootData);

    findSubFolder(rootNode, folderListByParentId);
    return rootNode;
  }

  /**
   * 전체 폴더에서 rootNode의 자식을 찾음
   *
   * @param rootNode             트리구조 노드
   * @param folderListByParentId
   */
  private void findSubFolder(final TreeNode<NoteCategoryVo> rootNode,
                             final Map<NoteCategoryVo, List<NoteCategoryVo>> folderListByParentId) {
    NoteCategoryVo category = rootNode.getData();
    List<NoteCategoryVo> children = folderListByParentId.get(category);
    if (children == null) {
      return;
    }
    children.stream().forEach(c -> {
      TreeNode<NoteCategoryVo> currentNode = rootNode.addChild(c);
      findSubFolder(currentNode, folderListByParentId);
    });
  }

  /**
   * @param categorySeq 기준 카테고리
   * @return ROOT부터 기준 카테고리 까지 경로 제공
   */
  public List<NoteCategoryVo> geCategoryPath(int categorySeq) {
    TreeNode<NoteCategoryVo> tree = getCategoryTree();
    Spliterator<TreeNode<NoteCategoryVo>> spliterator = Spliterators.spliteratorUnknownSize(tree.iterator(), Spliterator.ORDERED);
    TreeNode<NoteCategoryVo> baseCategory = StreamSupport.stream(spliterator, false)
      .filter(category -> category.getData().getCategorySeq() == categorySeq).findAny().get();

    return baseCategory.getPath();
  }

  /**
   * 해당 카테고리 하위에 포함된 모든 카테고리 반환
   *
   * @param categorySeq 기준 카테고리 일련번호
   * @return 카테고리 일련번호
   */
  public Set<Integer> getSubCategorySeq(int categorySeq) {
    NoteCategoryVo category = noteCategoryRepository.findById(categorySeq).get();
    TreeNode<NoteCategoryVo> tree = getCategoryTree();
    TreeNode<NoteCategoryVo> subTree = tree.getTreeNode(category);
    List<TreeNode<NoteCategoryVo>> childList = subTree.exploreTree();
    return childList.stream().map(c -> c.getData().getCategorySeq()).collect(Collectors.toSet());
  }

  /**
   * 모튼 카테고리 정보를 변경. <br>
   * 기존에 저장된 카테고리 중 category에 없는 카테고리는 삭제함.
   *
   * @param category 상하위 관계를 같는 카테고리
   */
  public void updateCategory(NoteCategoryVo category) {
    List<NoteCategoryVo> allCategory = noteCategoryRepository.listCategory();

    category.setParent(category);
    applyParent(category, category.getChildren());
    noteCategoryRepository.save(category);

    Stream<NoteCategoryVo> categoryStream = flat(category);
    Set<Integer> currentCategorySet = categoryStream.map(cat -> cat.getCategorySeq()).collect(Collectors.toSet());

    List<NoteCategoryVo> deleteCategory = allCategory.stream().filter(cat -> !currentCategorySet.contains(cat.getCategorySeq())).map(cat -> {
      cat.setDeleteF(true);
      return cat;
    }).collect(Collectors.toList());

    noteCategoryRepository.saveAll(deleteCategory);
  }

  /**
   * @param category
   * @return 계층형 카테고리를 선형적으로 나눔
   */
  private Stream<NoteCategoryVo> flat(NoteCategoryVo category) {
    return Stream.concat(
      Stream.of(category),
      category.getChildren().stream().flatMap(cat -> flat(cat)));
  }

  /**
   * 부모 카테고리 지정
   *
   * @param parent   부모 카테고리
   * @param children
   */
  private void applyParent(NoteCategoryVo parent, List<NoteCategoryVo> children) {
    children.forEach(category -> {
      category.setParent(parent);
      applyParent(category, category.getChildren());
    });
  }

}
