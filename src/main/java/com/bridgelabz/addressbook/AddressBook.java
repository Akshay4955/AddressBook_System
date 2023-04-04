package com.bridgelabz.addressbook;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.bridgelabz.addressbook.AddressBookConstants.*;

public class AddressBook {

    List<Contact> contactList = new ArrayList<>();

    public enum IOService {FILE_IO, CSV_IO, JSON_IO, DB_IO}

    public void callAddressBook(ArrayList<Contact> contacts) {
        boolean loop = true;
        while (loop) {
            System.out.println("Plz enter what you want to perform : " + '\n' + "press 1 for Edit Contact" +
                    '\n' + "press 2 for print contact" + '\n' + "Enter 3 for add contact" + '\n' +
                    "Enter 4 for delete contact" + '\n' + "Enter 0 to exit");
            try {
                Scanner input = new Scanner(System.in);
                int choice = input.nextInt();
                switch (choice) {
                    case EDIT_CONTACT:
                        if (contacts.isEmpty())
                            System.out.println("Address book is empty");
                        else {
                            editContact(contacts);
                            System.out.println("After editing contact");
                            printContacts(contacts);
                        }
                        break;
                    case PRINT_CONTACT:
                        if (contacts.isEmpty())
                            System.out.println("Address book is empty");
                        else
                            printContacts(contacts);
                        break;
                    case ADD_CONTACT:
                        addNewContacts(contacts);
                        System.out.println("After adding contacts");
                        printContacts(contacts);
                        break;
                    case DELETE_CONTACT:
                        if (contacts.isEmpty())
                            System.out.println("Address book is empty");
                        else
                            deleteContact(contacts);
                        break;
                    default:
                        loop = false;

                }
            } catch (InputMismatchException e) {
                System.out.println("You entered wrong input. Please enter valid input");
            }
        }
    }

    public void addNewContacts(ArrayList<Contact> contacts) {
        System.out.println("Enter how many contacts you want to save");
        try {
            Scanner input = new Scanner(System.in);
            int noOfContacts = input.nextInt();
            for (int i = 0; i < noOfContacts; i++) {
                Contact contact = new Contact();
                System.out.println("Enter First Name of " + (i + 1) + " Contact: ");
                contact.setFirstName(input.next());
                System.out.println("Enter last Name of " + (i + 1) + " Contact: ");
                contact.setLastName(input.next());
                boolean result = contacts.stream().anyMatch(contact::equals);
                if (result) {
                    System.out.println("Contact already available for entered name");
                    System.out.println("If you wish to retry press 1 for exit press 0");
                    int choice = input.nextInt();
                    if (choice == 1)
                        i--;
                    continue;
                }
                System.out.println("Enter address of " + (i + 1) + " Contact: ");
                contact.setAddress(input.next());
                System.out.println("Enter city of " + (i + 1) + " Contact: ");
                contact.setCity(input.next());
                System.out.println("Enter state of " + (i + 1) + " Contact: ");
                contact.setState(input.next());
                System.out.println("Enter ZipCode of " + (i + 1) + " Contact: ");
                contact.setZipCode(input.nextInt());
                System.out.println("Enter phone number of " + (i + 1) + " Contact: ");
                contact.setPhoneNumber(input.nextLong());
                System.out.println("Enter email of " + (i + 1) + " Contact: ");
                contact.setEmail(input.next());
                contacts.add(contact);
            }
        } catch (InputMismatchException e) {
            System.out.println("You entered wrong input. Please enter valid input");
        }
    }

    public void editContact(ArrayList<Contact> contacts) {
        System.out.println("Please Enter the name whose details you want to change");
        try {
            Scanner input = new Scanner(System.in);
            String name = input.next();
            for (Contact contact : contacts) {
                if (name.equalsIgnoreCase(contact.getFirstName())) {
                    boolean loop = true;
                    while (loop) {
                        System.out.println("What you want to change :" + '\n' + "Press 1 for first name " + '\n' +
                                "Press 2 for last name " + '\n' + "Enter 3 for ADDRESS " + '\n' + "Enter 4 for city"
                                + '\n' + "Enter 5 for STATE" + '\n' + "Enter 6 for zip code" + '\n' + "Enter 7 for phone number"
                                + '\n' + "Enter 8 for EMAIL" + '\n' + "Enter 0 to stop editing");
                        int choice = input.nextInt();
                        switch (choice) {
                            case FIRST_NAME:
                                System.out.println("Please Enter the name to be updated");
                                contact.setFirstName(input.next());
                                break;
                            case LAST_NAME:
                                System.out.println("Please Enter the lastname to be updated");
                                contact.setLastName(input.next());
                                break;
                            case ADDRESS:
                                System.out.println("Please Enter the address to be updated");
                                contact.setAddress(input.next());
                                break;
                            case CITY:
                                System.out.println("Please Enter the city to be updated");
                                contact.setCity(input.next());
                                break;
                            case STATE:
                                System.out.println("Please enter the STATE to be updated");
                                contact.setState(input.next());
                                break;
                            case ZIP_CODE:
                                System.out.println("Please Enter the zip code to be updated");
                                contact.setZipCode(input.nextInt());
                                break;
                            case PHONE_NUMBER:
                                System.out.println("Please Enter the phone number to be updated");
                                contact.setPhoneNumber(input.nextLong());
                                break;
                            case EMAIL:
                                System.out.println("Please Enter the EMAIL to be updated");
                                contact.setEmail(input.next());
                                break;
                            case STOP_EDITING:
                                loop = false;
                                break;
                            default:
                                System.out.println("You Entered wrong input");

                        }
                    }
                } else
                    System.out.println("Contact not available for entered name");
            }
        } catch (InputMismatchException e) {
            System.out.println("You entered wrong input. Please enter valid input");
        }
    }

    public void deleteContact(ArrayList<Contact> contacts) {
        System.out.println("Plz enter the name whose details you want to delete");
        try {
            Scanner input = new Scanner(System.in);
            String name = input.next();
            for (int i = 0; i < contacts.size(); i++) {
                if (name.equalsIgnoreCase(contacts.get(i).getFirstName())) {
                    contacts.remove(i);
                } else
                    System.out.println("Contact not available for entered name");
            }
        } catch (InputMismatchException e) {
            System.out.println("You entered wrong input. Please enter valid input");
        }
    }

    public void printContacts(ArrayList<Contact> contacts) {
        System.out.println(contacts);
    }

    public void searchContactsWithCity(Map<String, ArrayList<Contact>> addressBooks) {
        System.out.println("Please enter city name");
        Scanner input = new Scanner(System.in);
        String cityName = input.next();
        List<Contact> listOfContacts = addressBooks.values().stream().flatMap(Collection::stream)
                .filter(p -> p.getCity().equalsIgnoreCase(cityName)).collect(Collectors.toList());
        System.out.println(listOfContacts);
    }

    public void getContactByCityAndState(Map<String, ArrayList<Contact>> addressBooks) {
        List<Contact> myContactList = addressBooks.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
        Map<String, List<Contact>> myContactListByCity = myContactList.stream().collect(Collectors.groupingBy(Contact::getCity));
        System.out.println(myContactListByCity);
        Map<String, List<Contact>> myContactListByState = myContactList.stream().collect(Collectors.groupingBy(Contact::getState));
        System.out.println(myContactListByState);
    }

    public void getNumberContacts(Map<String, ArrayList<Contact>> addressBooks) {
        System.out.println("Please enter choice parameter ");
        System.out.println("Press 1 for City" + '\n' + "Press 2 for State");
        try {
            Scanner input = new Scanner(System.in);
            int choice = input.nextInt();
            switch (choice) {
                case BY_CITY:
                    System.out.println("Please enter city name");
                    String cityName = input.next();
                    long countByCity = addressBooks.values().stream().flatMap(Collection::stream).filter(p -> p.getCity().equalsIgnoreCase(cityName)).count();
                    System.out.println("Count of contacts with " + cityName + " are " + countByCity);
                    break;
                case BY_STATE:
                    System.out.println("Please enter State name");
                    String stateName = input.next();
                    long countByState = addressBooks.values().stream().flatMap(Collection::stream).filter(p -> p.getCity().equalsIgnoreCase(stateName)).count();
                    System.out.println("Count of contacts with " + stateName + " are " + countByState);
                    break;
                default:
                    System.out.println("You entered wrong input");
            }
        } catch (InputMismatchException e) {
            System.out.println("You entered wrong input. Please enter valid input");
        }

    }

    public void getSortedContacts(Map<String, ArrayList<Contact>> addressBooks) {
        System.out.println("Please enter the choice parameter by which you want sort");
        System.out.println("Press 1 for Name" + '\n' + "Press 2 for City" + '\n' + "Press 3 for State" + '\n' + "Press 4 for ZipCode");
        try {
            Scanner input = new Scanner(System.in);
            int choice = input.nextInt();
            switch (choice) {
                case SORT_BY_NAME:
                    System.out.println(addressBooks.values().stream().flatMap(Collection::stream)
                            .sorted((Comparator.comparing(Contact::getFirstName))).collect(Collectors.toList()));
                    break;
                case SORT_BY_CITY:
                    System.out.println(addressBooks.values().stream().flatMap(Collection::stream)
                            .sorted((Comparator.comparing(Contact::getCity))).collect(Collectors.toList()));
                    break;
                case SORT_BY_STATE:
                    System.out.println(addressBooks.values().stream().flatMap(Collection::stream)
                            .sorted((Comparator.comparing(Contact::getState))).collect(Collectors.toList()));
                    break;
                case SORT_BY_ZIPCODE:
                    System.out.println(addressBooks.values().stream().flatMap(Collection::stream)
                            .sorted((Comparator.comparing(Contact::getZipCode))).collect(Collectors.toList()));
                    break;
                default:
                    System.out.println("You entered wrong input");
            }
        } catch (InputMismatchException e) {
            System.out.println("You entered wrong input. Please enter valid input");
        }
    }

    public void writeData(IOService ioService, HashMap<String, ArrayList<Contact>> addressBooks) {
        if (ioService == IOService.FILE_IO) {
            new AddressBookFileIO().writeDataToFile(addressBooks);
        } else if (ioService == IOService.CSV_IO) {
            new AddressBookCSV().writeDataToCSV(addressBooks);
        } else if (ioService == IOService.JSON_IO) {
            new AddressBookJSON().writeDataToJSON(addressBooks);
        }
    }

    public void readData(IOService ioService) {
        if (ioService == IOService.FILE_IO) {
            new AddressBookFileIO().readDataFromFile();
        } else if (ioService == IOService.CSV_IO) {
            new AddressBookCSV().readDataFromCSV();
        } else if (ioService == IOService.JSON_IO) {
            new AddressBookJSON().readDataFromJSON();
        }
    }

    public List<Contact> readDBData() {
        this.contactList = new AddressBookDBIO().readDataFromDB();
        return this.contactList;
    }

    public List<Contact> getContactsForGivenDateRange(LocalDate startDate, LocalDate endDate) {
        List<Contact> contactListDate = new AddressBookDBIO().getContactsForDateRange(startDate, endDate);
        return contactListDate;
    }

    public void updateContactAddress(String firstName, String email) {
        int update = new AddressBookDBIO().updateContactAddress(firstName, email);
        if (update > 0) {
            Contact contact = contactList.stream().filter(Contact -> Contact.getFirstName().equalsIgnoreCase(firstName))
                    .findFirst().orElse(null);
            if (contact != null) {
                contact.setEmail(email);
            }
        }
    }

    public boolean checkContactInSyncWithDB(String name) {
        List<Contact> contactFromDB = new AddressBookDBIO().getContactFromDB(name);
        return contactFromDB.get(0).equals(getContactWithName(name));
    }

    private Contact getContactWithName(String name) {
        List<Contact> nameList = contactList.stream().filter(Contact -> Contact.getFirstName().equalsIgnoreCase(name)).collect(Collectors.toList());
        return nameList.get(0);
    }


    public int getNoOfContactByCity(String city) {
        return new AddressBookDBIO().getNoOfContactsByCity(city);
    }


    public int getNoOfContactByState(String state) {
        return new AddressBookDBIO().getNoOfContactsByState(state);
    }
}

