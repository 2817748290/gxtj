package com.zhoulin.demo.bean;

import com.google.common.collect.Sets;
import org.springframework.data.domain.Sort;

import java.util.Set;

/**
 * 排序生成器
 */
public class InfoSort {
    public static final String DEFAULT_SORT_KEY = "id";

    private static final Set<String> SORT_KEYS = Sets.newHashSet(
        DEFAULT_SORT_KEY,
            "id",
            "read",
            "likes",
            "score"
    );

    public static Sort generateSort(String key, String directionKey) {
        key = getSortKey(key);

        Sort.Direction direction = Sort.Direction.fromStringOrNull(directionKey);
        if (direction == null) {
            direction = Sort.Direction.DESC;
        }

        return new Sort(direction, key);
    }

    public static String getSortKey(String key) {
        if (!SORT_KEYS.contains(key)) {
            key = DEFAULT_SORT_KEY;
        }

        return key;
    }
}
