package org.vlog.project.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

public class ProjectUtils {

    public static Pageable pagination(String sort, Integer page_num, Integer page_size) {
        String checkedSort = Optional.ofNullable(sort).orElse("-id");
        Integer checkedPageNum = Optional.ofNullable(page_num).orElse(0);
        Integer checkedSize = Optional.ofNullable(page_size).orElse(10);
        Sort.Direction direction = null;

        if (checkedSort.contains("-")) {
            direction = Sort.Direction.DESC;
            checkedSort = checkedSort.substring(1);
        } else {
            direction = Sort.DEFAULT_DIRECTION;
        }
        return PageRequest.of(checkedPageNum, checkedSize, Sort.by(direction, checkedSort));
    }

}
