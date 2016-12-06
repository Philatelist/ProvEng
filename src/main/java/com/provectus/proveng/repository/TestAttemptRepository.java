package com.provectus.proveng.repository;

import com.provectus.proveng.domain.TestAttempt;

public interface TestAttemptRepository {

    void create(TestAttempt testAttempt);

    TestAttempt getById(long id);

    TestAttempt refresh(TestAttempt testAttempt);

}
