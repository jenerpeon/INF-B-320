package internetkaufhaus;

import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * The Class AbstractIntegrationTests.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration
@Transactional
public abstract class AbstractIntegrationTests {}