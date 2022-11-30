package ru.practicum.explorewithme.service.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class StatsServiceImplTest {

    @Autowired
    private StatsServiceImpl statsService;
    @Autowired
    private EntityManager entityManager;


    @BeforeEach
    void setUp() {

    }

    @Test
    void saveEndpointHit() {

    }

    @Test
    void getViewStats() {

    }
}