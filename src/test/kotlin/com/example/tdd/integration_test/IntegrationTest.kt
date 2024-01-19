//package com.example.tdd.integration_test
//
//import org.junit.jupiter.api.BeforeAll
//import org.junit.jupiter.api.TestInstance
//import org.slf4j.Logger
//import org.slf4j.LoggerFactory
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.test.annotation.Rollback
//import org.springframework.test.context.ActiveProfiles
//import org.springframework.test.context.jdbc.Sql
//import org.springframework.transaction.annotation.Transactional
//import org.testcontainers.containers.MySQLContainer
//import org.testcontainers.containers.output.Slf4jLogConsumer
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql


@ActiveProfiles("test")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@Sql("classpath:/db/init.sql")
@Sql("classpath:/db/dml.sql")
abstract class IntegrationTest