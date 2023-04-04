package addressbook;

import com.bridgelabz.addressbook.AddressBook;
import com.bridgelabz.addressbook.Contact;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
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

    @Test
    public void givenContactInDB_WhenRetrievedForGivenDateRange_ShouldMatchCount() {
        AddressBook addressBook = new AddressBook();
        LocalDate startDate = LocalDate.of(2022, 04, 22);
        LocalDate endDate = LocalDate.now();
        List<Contact> contactsForGivenDateRange = addressBook.getContactsForGivenDateRange(startDate, endDate);
        Assert.assertEquals(4, contactsForGivenDateRange.size());
    }

    @Test
    public void givenCityAndContactInDB_WhenRetrievedContactByCity_ShouldMatchCount() {
        AddressBook addressBook = new AddressBook();
        int noOfContact = addressBook.getNoOfContactByCity("Pune");
        Assert.assertEquals(3, noOfContact);
    }

    @Test
    public void givenStateAndContactInDB_WhenRetrievedContactByState_ShouldMatchCount() {
        AddressBook addressBook = new AddressBook();
        int noOfContact = addressBook.getNoOfContactByState("Maharashtra");
        Assert.assertEquals(6, noOfContact);
    }
}
