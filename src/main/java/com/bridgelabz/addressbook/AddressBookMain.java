package com.bridgelabz.addressbook;

import java.util.*;

import static com.bridgelabz.addressbook.AddressBookConstants.*;

public class AddressBookMain {
    HashMap<String, ArrayList<Contact>> addressBooks = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Welcome to AddressBook program....!!!!");
        AddressBookMain addressBookMain = new AddressBookMain();
        AddressBook addressBook = new AddressBook();
        boolean loop = true;
        while (loop) {
            System.out.println("Enter what you want to perform");
            System.out.println("Press 1 to create new address book" + '\n' + "Press 2 to perform operation on existing address book" +
                    '\n' + "Press 3 to search contacts with city " + '\n' + "Press 4 to get person with city" +
                    '\n' + "Press 5 to get number of contacts by city" + '\n' + "Press 6 to get sorted contacts by name/City/State/Zip" +
                    '\n' +"Press 7 for write to file" + '\n' +"Press 8 for read from file" + '\n' +"Press 9 for write to CSV" +
                    '\n' +"Press 10 for read from CSV" + '\n' +"Press 11 for write to JSON" + '\n' +"Press 12 for read from JSON" +
                    '\n' + "Press 0 to exit");
            try {
                Scanner input = new Scanner(System.in);
                int option = input.nextInt();
                switch (option) {
                    case CREATE_ADDRESS_BOOK:
                        addressBookMain.createAddressBook();
                        break;
                    case OPERATE_EXISTING:
                        System.out.println("Plz enter key belong to address book");
                        String inputKey = input.next().toLowerCase();
                        if (addressBookMain.addressBooks.containsKey(inputKey))
                            addressBook.callAddressBook(addressBookMain.addressBooks.get(inputKey));
                        else
                            System.out.println("Entered key address book not available");
                        break;
                    case SEARCH_CONTACTS:
                        addressBook.searchContactsWithCity(addressBookMain.addressBooks);
                        break;
                    case GET_PERSON_WITH_CITY:
                        addressBook.getContactByCityAndState(addressBookMain.addressBooks);
                        break;
                    case GET_NO_OF_CONTACTS_BY_CITY:
                        addressBook.getNumberContacts(addressBookMain.addressBooks);
                        break;
                    case GET_SORTED_CONTACTS:
                        addressBook.getSortedContacts(addressBookMain.addressBooks);
                        break;
                    case WRITE_TO_FILE:
                        addressBook.writeData(AddressBook.IOService.FILE_IO, addressBookMain.addressBooks);
                        break;
                    case READ_FROM_FILE:
                        addressBook.readData(AddressBook.IOService.FILE_IO);
                        break;
                    case WRITE_TO_CSV:
                        addressBook.writeData(AddressBook.IOService.CSV_IO, addressBookMain.addressBooks);
                        break;
                    case READ_FROM_CSV:
                        addressBook.readData(AddressBook.IOService.CSV_IO);
                        break;
                    case WRITE_TO_JSON:
                        addressBook.writeData(AddressBook.IOService.JSON_IO, addressBookMain.addressBooks);
                        break;
                    case READ_FROM_JSON:
                        addressBook.readData(AddressBook.IOService.JSON_IO);
                        break;
                    case EXIT:
                        loop = false;
                        break;
                    default:
                        System.out.println("Entered Wrong input");
                }
            }catch (InputMismatchException e) {
                System.out.println("You entered wrong input. Please enter valid input");
            }
        }
    }

    public void createAddressBook() {
        System.out.println("Create address book of your choice");
        ArrayList<Contact> contacts = new ArrayList<>();
        System.out.println("Enter unique key name");
        Scanner input = new Scanner(System.in);
        String name = input.next().toLowerCase();
        if (!addressBooks.containsKey(name))
            addressBooks.put(name, contacts);
        else
            System.out.println("Entered key is already available");
    }
}