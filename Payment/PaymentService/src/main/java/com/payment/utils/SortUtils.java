package com.payment.utils;

import org.springframework.data.domain.Sort;

public class SortUtils {

  /**
   * Private constructor because all methods are static, no need to create object.
   */
  private SortUtils() {

  }

  public static Sort ascendingSort(String... columns) {
    return new Sort(Sort.Direction.ASC, columns);
  }

  public static Sort descendingSort(String... columns) {
    return new Sort(Sort.Direction.DESC, columns);
  }

}
