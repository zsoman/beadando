package hu.unideb.inf;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestKoordinata.class, TestSudoku9x9.class,
		TestSudoku16x16.class, TestSudokuXML9x9.class,
		TestSudokuXML16x16.class })
public class TestOsszes {

}