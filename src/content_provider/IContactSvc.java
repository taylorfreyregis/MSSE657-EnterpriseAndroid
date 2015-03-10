package content_provider;

import java.util.List;

public interface IContactSvc {

    Contact createContact(Contact contact);

    Contact updateContact(Contact contact);

    Contact deleteContact(Contact contact);

    List<Contact> readContacts();

    Contact readContact(int index);

    Contact readContact(String contactName);
}
