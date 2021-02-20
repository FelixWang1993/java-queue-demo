import com.huazai.activemq.springboot.BootQueueProviderMain;
import com.huazai.activemq.springboot.queue.QueueProviderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.JMSException;

/**
 * @author pyh
 * @date 2021/1/16 19:25
 */
@SpringBootTest(classes = BootQueueProviderMain.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class QueueProviderTest {
    @Autowired
    private QueueProviderService queueProviderService;

    @Test
    public void testSend() throws JMSException {
        queueProviderService.productMessage();
    }
}
