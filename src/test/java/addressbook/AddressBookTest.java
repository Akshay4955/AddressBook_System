package addressbook;

import com.bridgelabz.addressbook.AddressBook;
import com.bridgelabz.addressbook.Contact;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class AddressBookTest {
    @Test
    public void givenAddressBookContactInDB_WhenRetrieved_ShouldMatchContactCount() {
        AddressBook addressBook = new AddressBook();
        List<Contact> contactList = addressBook.readDBData();
        Assert.assertEquals(6, contactList.size());
    }

    @Test
    public void updateDataForGivenContact_WhenUpdated_ShouldSyncWithDatabase() {
        AddressBook addressBook = new AddressBook();
        List<Contact> contactList = addressBook.readDBData();
        addressBook.updateContactAddress("Mangesh", "mangeshm72@gmail.com");
        boolean result = addressBook.checkContactInSyncWithDB("Mangesh");
        Assert.assertTrue(result);
    }
}
