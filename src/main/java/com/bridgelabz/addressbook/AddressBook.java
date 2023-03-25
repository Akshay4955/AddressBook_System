package com.bridgelabz.addressbook;

import java.util.*;

public class AddressBook {

    public void callAddressBook(ArrayList<Contact> contacts) {
        boolean loop = true;
        while (loop) {
            System.out.println("Plz enter what you want to perform : " + '\n' + "press 1 for Edit Contact" +
                    '\n' + "press 2 for print contact" + '\n' + "Enter 3 for add contact" + '\n' +
                    "Enter 4 for delete contact" + '\n' + "Enter 0 to exit");
            final int editContact = 1, printContact = 2, addContact = 3, deleteContact = 4;
            try {
                Scanner input = new Scanner(System.in);
                int choice = input.nextInt();
                switch (choice) {
                    case editContact:
                        if (contacts.isEmpty())
                            System.out.println("Address book is empty");
                        else {
                            editContact(contacts);
                            System.out.println("After editing contact");
                            printContacts(contacts);
                        }
                        break;
                    case printContact:
                        if (contacts.isEmpty())
                            System.out.println("Address book is empty");
                        else
                            printContacts(contacts);
                        break;
                    case addContact:
                        addNewContacts(contacts);
                        System.out.println("After adding contacts");
                        printContacts(contacts);
                        break;
                    case deleteContact:
                        if (contacts.isEmpty())
                            System.out.println("Address book is empty");
                        else
                            deleteContact(contacts);
                        break;
                    default:
                        loop = false;

                }
            }catch (InputMismatchException e) {
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
        }catch (InputMismatchException e) {
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
                        final int firstName = 1, lastName = 2, address = 3, city = 4, state = 5, zipCode = 6, phoneNumber = 7, email = 8, stopEditing = 0;
                        System.out.println("What you want to change :" + '\n' + "Press 1 for first name " + '\n' +
                                "Press 2 for last name " + '\n' + "Enter 3 for address " + '\n' + "Enter 4 for city"
                                + '\n' + "Enter 5 for state" + '\n' + "Enter 6 for zip code" + '\n' + "Enter 7 for phone number"
                                + '\n' + "Enter 8 for email" + '\n' + "Enter 0 to stop editing");
                        int choice = input.nextInt();
                        switch (choice) {
                            case firstName:
                                System.out.println("Please Enter the name to be updated");
                                contact.setFirstName(input.next());
                                break;
                            case lastName:
                                System.out.println("Please Enter the lastname to be updated");
                                contact.setLastName(input.next());
                                break;
                            case address:
                                System.out.println("Please Enter the address to be updated");
                                contact.setAddress(input.next());
                                break;
                            case city:
                                System.out.println("Please Enter the city to be updated");
                                contact.setCity(input.next());
                                break;
                            case state:
                                System.out.println("Please enter the state to be updated");
                                contact.setState(input.next());
                                break;
                            case zipCode:
                                System.out.println("Please Enter the zip code to be updated");
                                contact.setZipCode(input.nextInt());
                                break;
                            case phoneNumber:
                                System.out.println("Please Enter the phone number to be updated");
                                contact.setPhoneNumber(input.nextLong());
                                break;
                            case email:
                                System.out.println("Please Enter the email to be updated");
                                contact.setEmail(input.next());
                                break;
                            case stopEditing:
                                loop = false;
                                break;
                            default:
                                System.out.println("You Entered wrong input");

                        }
                    }
                } else
                    System.out.println("Contact not available for entered name");
            }
        }catch (InputMismatchException e) {
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
        }catch (InputMismatchException e) {
            System.out.println("You entered wrong input. Please enter valid input");
        }
    }

    public void printContacts(ArrayList<Contact> contacts) {
        System.out.println(contacts);
    }
}

