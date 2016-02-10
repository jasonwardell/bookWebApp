package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jwardell
 */
public class AuthorService {
 private List<Author> authorList = new ArrayList<>();

Author aL1 = new Author();
Author aL2 = new Author();
Author aL3 = new Author();
 
    public List getAuthors() {
        aL1.setAuthorName("James");
        aL1.setAuthorId(0);
        aL1.setDateAdded(new Date(2/7/16));
        aL2.setAuthorName("George");
        aL2.setAuthorId(1);
        aL2.setDateAdded(new Date(2/7/16));
        aL3.setAuthorName("Susan");
        aL3.setAuthorId(2);
        aL3.setDateAdded(new Date(2/7/16));
        
        authorList.add(aL1);
        authorList.add(aL2);
        authorList.add(aL3);
        
        return authorList;
    }
    
}
