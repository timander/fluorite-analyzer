package net.timandersen;

import net.timandersen.repository.ActivityRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:contexts/application.xml")
public class DataImporterTest {

  @Autowired
  ActivityRepository repository;

  @Autowired
  JdbcTemplate jdbcTemplate;

  @Before
  public void setUp() {
    jdbcTemplate.execute("delete from Activity");
  }


  @Test
  public void importCsvFile() {
    DataImporter dataImporter = new DataImporter();
    dataImporter.setActivityRepository(repository);
    URL resource = getClass().getClassLoader().getResource("small-log.csv");
    dataImporter.importCsvFile(new File(resource.getFile()));
    assertEquals(10, repository.findAll().size());
  }

}
