package com.bridgelabz.addressbook;

public class AddressBookConstants {
    public static final int CREATE_ADDRESS_BOOK = 1, OPERATE_EXISTING = 2, SEARCH_CONTACTS = 3, GET_PERSON_WITH_CITY = 4, GET_NO_OF_CONTACTS_BY_CITY = 5,
            GET_SORTED_CONTACTS = 6, WRITE_TO_FILE = 7, READ_FROM_FILE = 8, WRITE_TO_CSV = 9, READ_FROM_CSV = 10, WRITE_TO_JSON = 11,
            READ_FROM_JSON = 12, EXIT = 0;
    public static final int EDIT_CONTACT = 1, PRINT_CONTACT = 2, ADD_CONTACT = 3, DELETE_CONTACT = 4;

    public static final int FIRST_NAME = 1, LAST_NAME = 2, ADDRESS = 3, CITY = 4, STATE = 5, ZIP_CODE = 6, PHONE_NUMBER = 7, EMAIL = 8, STOP_EDITING = 0;

    public static final int BY_CITY = 1, BY_STATE = 2;

    public static final int SORT_BY_NAME = 1, SORT_BY_CITY = 2, SORT_BY_STATE = 3, SORT_BY_ZIPCODE = 4;
}
