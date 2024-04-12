package nl.omgwtfbbq.delver.conf;

import org.junit.Assert;
import org.junit.Test;

import jakarta.xml.bind.JAXBException;
import java.io.InputStream;

public class ConfigTest {

    @Test
    public void testRead() throws JAXBException {
        InputStream is = Config.class.getResourceAsStream("/delver-conf.xml");
        Config d = Config.read(is);

        Assert.assertEquals(true, d.getHttpConfig().isHttpEnabled());
        Assert.assertEquals(8081, d.getHttpConfig().getHttpPort());

        // 4 in includes, 3 ok, 1 wrong.
        Assert.assertEquals(6, d.getIncludes().size());

        // 2 in excludes.
        Assert.assertEquals(3, d.getExcludes().size());

        Assert.assertTrue(d.getIncludes().get(0).isValid());
        Assert.assertTrue(d.getIncludes().get(1).isValid());
        Assert.assertTrue(d.getIncludes().get(2).isValid());
        Assert.assertFalse(d.getIncludes().get(4).isValid()); // invalid regex.
        Assert.assertTrue(d.getIncludes().get(5).isValid());
    }

    @Test
    public void testWrite() throws JAXBException {
        Config d = new Config();

        Pattern p = new Pattern();
        p.setPattern("nl.bla.some");

        Pattern a = new Pattern();
        a.setPattern("java.lang.*");

        d.addInclude(p);
        d.addExclude(a);

        Config.write(d, System.out);
    }
}
