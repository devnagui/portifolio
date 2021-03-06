package br.com.devnagui.project.fiscal.manter;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import  org.junit.Assert;
import org.apache.log4j.Logger;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.QueryDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.database.annotations.TestDataSource;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.orm.jpa.annotation.JpaEntityManagerFactory;

import br.com.devnagui.project.dbunitils.InsertOrUpdateWithCommitLoadStrategy;
import br.com.devnagui.project.ejb.impl.UserBusinessEJB;
import br.com.devnagui.project.objetodepagina.fiscal.manter.UserPageObject;

@JpaEntityManagerFactory(configFile = "test-persistence.xml", persistenceUnit = "test-persistence-unit")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ManagerUserTestSuite {

    private static final Logger LOG = Logger.getLogger(ManagerUserTestSuite.class);


    @TestedObject
    @InjectIntoByType(target = "userBusinessEJB")
    private UserBusinessEJB userBusinessEJB;

    @InjectIntoByType
    @PersistenceContext(unitName = "test-persistence-unit")
    private EntityManager entityManager;
    @TestDataSource
    private static DataSource dataSource;
    private WebDriver driver;

    public static final String VALID_USER_REGRISTRATION = "00688711";
    public static final String VALID_USER_NAME = "ANTONIO VALDIR ROSENO";

    private static final String INVALID_REGISTRATION_NUMBER = "########";
    private static final String INVALID_USER_NAME = "####### ####### #######";

    public static final String DATASET_ENABLED_USER = "datasetEnabledUser.xml";

    @Before
    public void openBrowser() throws DatabaseUnitException, SQLException {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
    }

    @After
    public void closeBrowser() throws DatabaseUnitException, SQLException {
        LOG.info("Finalizando os Testes funcionais.");
        driver.close();
        cleanDatabaseBeforeTests();
    }

    @Test
    public void testEnableUser() throws Exception {
        cleanDatabaseBeforeTests();
        UserPageObject userPageObject = UserPageObject.getInstance(driver);
        userPageObject.createNewUser(VALID_USER_REGRISTRATION, VALID_USER_NAME);
        Assert.assertTrue(userPageObject.isUserValid());
    }

    @Test
    public void testCreateNewUser() throws Exception {
        cleanDatabaseBeforeTests();
        UserPageObject userPageObject = UserPageObject.getInstance(driver);
        userPageObject.createNewUser(VALID_USER_NAME, VALID_USER_REGRISTRATION);
        Assert.assertTrue(userPageObject.isUserValid());
    }

    @Test
    @DataSet(value = { DATASET_ENABLED_USER }, loadStrategy = InsertOrUpdateWithCommitLoadStrategy.class)
    public void testDesabilitarFiscal() throws Exception {

        UserPageObject userPageObject = UserPageObject.getInstance(driver);
        userPageObject.disableUser();
        Assert.assertTrue(userPageObject.isFiscalDesabilitadoComSucesso());
    }

    /**
     * Serve para...
     * 
     * @throws SQLException
     * @throws DatabaseUnitException
     */
    private void cleanDatabaseBeforeTests() throws DatabaseUnitException, SQLException {
        LOG.info("Cleaning all previous data");
        org.dbunit.ext.oracle.OracleConnection connection = new org.dbunit.ext.oracle.OracleConnection(dataSource.getConnection(), "PROJECT");

        QueryDataSet queryDataSet = new QueryDataSet(connection);
        queryDataSet.addTable("USER", "SELECT F.* FROM PROJECT.USER u where U.REGISTRATION_NUMBER = '"
                + VALID_USER_REGRISTRATION + "'");
        DatabaseOperation.DELETE.execute(connection, queryDataSet);
        connection.getConnection().commit();
    }
}
