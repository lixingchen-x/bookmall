package com.lxc.repository;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public abstract class BaseRepositoryTest {

    private final int PAGE_SIZE = 10;

    protected Pageable pageable = PageRequest.of(0, PAGE_SIZE, new Sort(Sort.Direction.ASC, "id"));
}

