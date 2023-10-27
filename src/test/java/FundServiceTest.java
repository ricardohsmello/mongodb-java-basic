import br.com.ricas.domain.entity.service.FundServiceImpl;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class FundServiceTest {

    @Mock
    private MongoCollection<Document> fundsCollection;

    private FundServiceImpl fundService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        fundService = new FundServiceImpl();
        fundService.fundsCollection = fundsCollection;
    }

    @Test
    public void testFilterGreaterThan100() {


    }
}
