package controllers;

import com.google.common.collect.ImmutableMap;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import play.db.Database;
import play.db.Databases;
import play.db.evolutions.Evolution;
import play.db.evolutions.Evolutions;

import java.sql.Connection;

public class SampleWithDatabaseTest {
  Database database;

  @Before
  public void createDatabase() {
    database =Databases.inMemory();
//        Databases.inMemory(
//            "mydatabase", ImmutableMap.of("MODE", "MYSQL"), ImmutableMap.of("logStatements", true));
    Evolutions.applyEvolutions(
            database,
            Evolutions.forDefault(
                    new Evolution(
                            1,
                            "create table test (id bigint not null, name varchar(255));",
                            "drop table test;")));
  }

  @After
  public void shutdownDatabase() {

    Evolutions.cleanupEvolutions(database);
    database.shutdown();

  }

  @Test
  public void databaseIsNotNull() {
    Assertions.assertThat(database).isNotNull();
  }

  @Test
  public void migrationViaEvolution() throws Exception {
    Connection connection = database.getConnection();
    connection.prepareStatement("insert into test values (10, 'testing')").execute();

    Assert.assertTrue(
            connection.prepareStatement("select * from test where id = 10").executeQuery().next());
  }
}
