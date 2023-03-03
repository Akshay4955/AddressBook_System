package com.bridgelabz.addressbook;

import java.util.ArrayList;
import java.util.Scanner;

public class AddressBookMain {
    Scanner input = new Scanner(System.in);
    ArrayList<Contact> contacts = new ArrayList<>();
    public static void main(String[] args) {
        System.out.println("Welcome to AddressBook program....!!!!");
        AddressBookMain addressBook = new AddressBookMain();
        addressBook.addNewContacts();
        System.out.println("Before Editing Contacts: ");
        for (Contact x : addressBook.contacts) {
            System.out.println(x.firstName + " " + x.lastName + " " + x.address + " " + x.city
                    + " " + x.state + " " + x.zipCode + " " + x.phoneNumber + " " + x.email);
        }
        addressBook.editContact();
        System.out.println("After Editing Contacts: ");
        for (Contact x : addressBook.contacts) {
            System.out.println(x.firstName + " " + x.lastName + " " + x.address + " " + x.city
            + " " + x.state + " " + x.zipCode + " " + x.phoneNumber + " " + x.email);
        }
    }

    public void addNewContacts() {
        System.out.println("Enter how many contacts you want to save");
        int noOfContacts = input.nextInt();

        for (int i = 0; i < noOfContacts; i++) {
            Contact contact = new Contact();
            System.out.println("Enter First Name of " + (i + 1) + " Contact: " );
            contact.firstName = input.next();
            System.out.println("Enter last Name of " + (i + 1) + " Contact: " );
            contact.lastName = input.next();
            System.out.println("Enter address of " + (i + 1) + " Contact: " );
            contact.address = input.next();
            System.out.println("Enter city of " + (i + 1) + " Contact: " );
            contact.city = input.next();
            System.out.println("Enter state of " + (i + 1) + " Contact: " );
            contact.state = input.next();
            System.out.println("Enter ZipCode of " + (i + 1) + " Contact: " );
            contact.zipCode = input.nextInt();
            System.out.println("Enter phone number of " + (i + 1) + " Contact: " );
            contact.phoneNumber = input.nextLong();
            System.out.println("Enter email of " + (i + 1) + " Contact: " );
            contact.email = input.next();

            contacts.add(contact);
        }
    }

    public void editContact() {
        System.out.println("Please Enter the name whose details you want to change");
        String name = input.next();
        for (int i = 0; i < contacts.size(); i++) {
            if (name.equalsIgnoreCase(contacts.get(i).firstName)) {
                System.out.println("What you want to change :" + '\n' + "Press 1 for first name " + '\n' +
                        "Press 2 for last name " + '\n' + "Enter 3 for address " + '\n' + "Enter 4 for city"
                + '\n' + "Enter 5 for state" + '\n' + "Enter 6 for zip code" + '\n' + "Enter 7 for phone number"
                + '\n' + "Enter 8 for email");
                int choice = input.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("Please Enter the name to be updated");
                        contacts.get(i).firstName = input.next();
                        break;
                    case 2:
                        System.out.println("Please Enter the lastname to be updated");
                        contacts.get(i).lastName = input.next();
                        break;
                    case 3:
                        System.out.println("Please Enter the address to be updated");
                        contacts.get(i).address = input.next();
                        break;
                    case 4:
                        System.out.println("Please Enter the city to be updated");
                        contacts.get(i).city = input.next();
                        break;
                    case 5:
                        System.out.println("Please enter the state to be updated");
                        contacts.get(i).state = input.next();
                        break;
                    case 6:
                        System.out.println("Please Enter the zip code to be updated");
                        contacts.get(i).zipCode = input.nextInt();
                        break;
                    case 7:
                        System.out.println("Please Enter the phone number to be updated");
                        contacts.get(i).phoneNumber = input.nextLong();
                        break;
                    case 8:
                        System.out.println("Please Enter the email to be updated");
                        contacts.get(i).email = input.next();
                        break;
                    default:
                        System.out.println("You Entered wrong input");

                }

            }
        }
    }
}
