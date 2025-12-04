package scripts;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class TestAsd {
    @Id
    @GeneratedValue
    Long id;

    String name;
}
