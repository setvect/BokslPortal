package com.setvect.bokslportal.lotto.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/lab/lotto/")
public class LottoController {
  @RequestMapping(value = "/luck", method = RequestMethod.GET)
  public ResponseEntity<List<Set<Integer>>> luck(HttpServletRequest request, HttpServletResponse response) {
    LocalDate today = LocalDate.now();
    List<Set<Integer>> luckList = makeLottoNumber(today.hashCode());
    return new ResponseEntity<>(luckList, HttpStatus.OK);
  }

  /**
   * @param seed
   *          랜덤 씨드
   * @return 운명의 번호
   */
  public static List<Set<Integer>> makeLottoNumber(int seed) {
    List<Set<Integer>> result = new ArrayList<>();
    Random random = new Random(seed);
    int lotteryCount = random.nextInt(5) + 1;

    for (int i = 0; i < lotteryCount; i++) {
      Set<Integer> r = new TreeSet<>();
      while (true) {
        r.add(random.nextInt(45) + 1);
        if (r.size() == 6) {
          break;
        }
      }
      result.add(r);
    }
    return result;
  }
}
