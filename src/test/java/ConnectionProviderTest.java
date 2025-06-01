import mft.model.repository.PersonDA;

import java.sql.SQLException;

public class ConnectionProviderTest {
    public static void main(String[] args) throws Exception {
        // try with resource
        try(PersonDA personDA = new PersonDA()){

        }catch (Exception e){

        }
    }
}
