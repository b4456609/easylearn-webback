package soselab.easyelarn;

import org.junit.Test;
import soselab.easylearn.MCA.ProjectReader;

/**
 * Created by bernie on 1/10/17.
 */
public class MCAtest {
    @Test
    public void test() {
        ProjectReader projectReader = new ProjectReader("easylearn-pack", "{}", "{}", "soselab.easyelarn");
        projectReader.execute();
    }
}
